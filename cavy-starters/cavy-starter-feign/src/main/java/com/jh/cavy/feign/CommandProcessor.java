package com.jh.cavy.feign;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.google.auto.service.AutoService;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("com.jh.cavy.feign.RestFeignClient")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class CommandProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private Locale locale;

    private String readFile(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    /**
     * 需要包装一下 或者 在idea shareVM配置 -Djps.track.ap.dependencies=false
     *
     * @param iface
     * @param wrapper
     * @param <T>
     * @return
     */
    private static <T> T jbUnwrap(Class<? extends T> iface, T wrapper) {
        T unwrapped = null;
        try {
            final Class<?> apiWrappers = wrapper.getClass().getClassLoader().loadClass("org.jetbrains.jps.javac.APIWrappers");
            final Method unwrapMethod = apiWrappers.getDeclaredMethod("unwrap", Class.class, Object.class);
            unwrapped = iface.cast(unwrapMethod.invoke(null, iface, wrapper));
        } catch (Throwable ignored) {
        }
        return unwrapped != null ? unwrapped : wrapper;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        locale = processingEnvironment.getLocale();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(RestFeignClient.class)) {
            ProcessingEnvironment unwrappedprocessingEnv = jbUnwrap(ProcessingEnvironment.class, processingEnv);

            TreePath path = Trees.instance(unwrappedprocessingEnv).getPath(element);
            JavaFileObject sourceFile = path.getCompilationUnit().getSourceFile();

            CompilationUnit cu;
            try {
                cu = StaticJavaParser.parse(sourceFile.openInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ModifyVisitor modifyVisitor = new ModifyVisitor();
            modifyVisitor.visit(cu, null);
            String content = cu.toString();

            //String content = null;
            //try {
            //    content = readFile(sourceFile.openInputStream());
            //} catch (IOException e) {
            //    throw new RuntimeException(e);
            //}
            TypeElement classElement = (TypeElement) element;
            String className = classElement.getQualifiedName().toString();
            String packageName = ((PackageElement) classElement.getEnclosingElement()).getQualifiedName().toString();
            String proxyClassName = classElement.getSimpleName() + "Proxy";

            Map<String, ExecutableElement> commandMethods = new HashMap<>();
            for (Element enclosedElement : classElement.getEnclosedElements()) {
                if (enclosedElement.getKind() == ElementKind.METHOD
                            && enclosedElement.getAnnotation(Access.class) != null) {
                    ExecutableElement method = (ExecutableElement) enclosedElement;
                    String commandValue = method.getAnnotation(Access.class).type();
                    commandMethods.put(commandValue, method);
                }
            }

            //try {

            //    JavaFileObject fileObjectOf = elementUtils.getFileObjectOf(element);
            //    fileObjectOf.delete();

            //FileObject resource = filer.getResource(StandardLocation.CLASS_OUTPUT, packageName, proxyClassName);
            //resource.delete();
            //} catch (IOException e) {
            //    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to delete original class: " + e.getMessage(), element);
            //}
            try {
                generateProxyClass(packageName, proxyClassName, className, commandMethods, content);
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to generate proxy class: " + e.getMessage(), element);
            }

            //try {
            //    JavaFileObject sourceFile1 = filer.createSourceFile(packageName + "." + proxyClassName, element);
            //    Writer writer = sourceFile1.openWriter();
            //    writer.write(content);
            //
            //    writer.flush();
            //    writer.close();
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}


        }
        return true;
    }

    private void generateProxyClass(String packageName, String proxyClassName, String targetClassName,
                                    Map<String, ExecutableElement> commandMethods, String content) throws IOException {
        String fullProxyClassName = packageName + "." + proxyClassName;
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(fullProxyClassName);

        try (Writer writer = builderFile.openWriter()) {
            writer.write(content);
        }
    }

    private static class ModifyVisitor extends ModifierVisitor<Object> {
        @Override
        public Visitable visit(ClassOrInterfaceDeclaration n, Object arg) {
            //添加 openfeign注解
            //n.addExtendedType( n.getName().asString());
            Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(RestFeignClient.class);
            if (annotationByClass.isPresent()) {
                NormalAnnotationExpr annotationExpr = (NormalAnnotationExpr) annotationByClass.get();
                NodeList<MemberValuePair> pairs = annotationExpr.getPairs();
                Optional<MemberValuePair> isMicroOptional = pairs.stream().filter(pair -> pair.getNameAsString().equals("isMicro") && pair.getValue() != null && !pair.getValue().toString().isBlank()).findFirst();
                if (isMicroOptional.isPresent()) {
                    Set<MemberValuePair> pairsFilters = pairs.stream().filter(pair -> !pair.getNameAsString().equals("isMicro")).collect(Collectors.toSet());
                    NormalAnnotationExpr openFeignAnnotation = new NormalAnnotationExpr(new com.github.javaparser.ast.expr.Name("org.springframework.cloud.openfeign.FeignClient"), new NodeList<>(pairsFilters));
                    n.addAnnotation(openFeignAnnotation);
                }
                //添加 RestController 注解
                if (!(n.getAnnotationByName("org.springframework.web.bind.annotation.RestController").isPresent()
                              && n.getAnnotationByName("org.springframework.stereotype.Controller").isPresent())) {
                    MarkerAnnotationExpr restControllerAnnotation = new MarkerAnnotationExpr(new com.github.javaparser.ast.expr.Name("org.springframework.web.bind.annotation.RestController"));
                    n.addAnnotation(restControllerAnnotation);
                }
                List<AnnotationExpr> resultAnnotationList = n.getAnnotations().stream().filter(a -> !a.getName().asString().equals("RestFeignClient")).toList();
                n.setAnnotations(new NodeList<>(resultAnnotationList));
                n.setName(n.getName().asString()+ "Proxy" );
            }

            return super.visit(n, arg);
        }

        @Override
        public Visitable visit(MethodDeclaration n, Object arg) {
            Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(Access.class);
            if (annotationByClass.isPresent()) {
                NormalAnnotationExpr annotationExpr = (NormalAnnotationExpr) annotationByClass.get();
                NodeList<MemberValuePair> pairs = annotationExpr.getPairs();
                boolean isGet = pairs.stream().anyMatch(pair -> pair.getNameAsString().equals("method") && "GET".equalsIgnoreCase(pair.getValue().toString()));
                List<MemberValuePair> pairList = new ArrayList<>() {{
                    add(new MemberValuePair("value", new StringLiteralExpr(n.getNameAsString())));
                }};
                List<MemberValuePair> pathList = pairs.stream().filter(pair -> pair.getNameAsString().equals("path") && pair.getValue() != null).toList();
                if (!pathList.isEmpty()) {
                    pairList = pathList;
                }
                NormalAnnotationExpr openFeignAnnotation = new NormalAnnotationExpr(new Name(isGet ? "org.springframework.web.bind.annotation.GetMapping" : "org.springframework.web.bind.annotation.PostMapping"), new NodeList<>(pairList));
                n.addAnnotation(openFeignAnnotation);
                List<AnnotationExpr> resultAnnotationList = n.getAnnotations().stream().filter(a -> !a.getName().asString().equals("Access")).toList();
                n.setAnnotations(new NodeList<>(resultAnnotationList));
            }
            return super.visit(n, arg);
        }
    }
}