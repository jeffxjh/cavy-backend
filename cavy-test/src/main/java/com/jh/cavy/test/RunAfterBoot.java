//package com.jh.cavy.test;
//
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
//import com.github.javaparser.ast.body.MethodDeclaration;
//import com.github.javaparser.ast.expr.*;
//import com.github.javaparser.ast.visitor.ModifierVisitor;
//import com.github.javaparser.ast.visitor.Visitable;
//import com.jh.cavy.feign.Access;
//import com.jh.cavy.feign.RestFeignClient;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//public class RunAfterBoot implements ApplicationRunner {
//    private static final String FILE_PATH = "D:\\projects\\cavy-backend\\cavy-test\\src\\main\\java\\com\\jh\\TestService.java";
//
//
//    private static class ModifyVisitor extends ModifierVisitor<Object> {
//        @Override
//        public Visitable visit(ClassOrInterfaceDeclaration n, Object arg) {
//            //添加 openfeign注解
//            Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(RestFeignClient.class);
//            if (annotationByClass.isPresent()) {
//                NormalAnnotationExpr annotationExpr = (NormalAnnotationExpr) annotationByClass.get();
//                NodeList<MemberValuePair> pairs = annotationExpr.getPairs();
//                Optional<MemberValuePair> isMicroOptional = pairs.stream().filter(pair -> pair.getNameAsString().equals("isMicro") && pair.getValue() != null && !pair.getValue().toString().isBlank()).findFirst();
//                if (isMicroOptional.isPresent()) {
//                    Set<MemberValuePair> pairsFilters = pairs.stream().filter(pair -> !pair.getNameAsString().equals("isMicro")).collect(Collectors.toSet());
//                    NormalAnnotationExpr openFeignAnnotation = new NormalAnnotationExpr(new Name("org.springframework.cloud.openfeign.FeignClient"), new NodeList<>(pairsFilters));
//                    n.addAnnotation(openFeignAnnotation);
//                }
//
//            }
//            //添加 RestController 注解
//            if (!(n.getAnnotationByName("org.springframework.web.bind.annotation.RestController").isPresent()
//                          && n.getAnnotationByName("org.springframework.stereotype.Controller").isPresent())) {
//                MarkerAnnotationExpr restControllerAnnotation = new MarkerAnnotationExpr(new Name("org.springframework.web.bind.annotation.RestController"));
//                n.addAnnotation(restControllerAnnotation);
//            }
//            return super.visit(n, arg);
//        }
//
//        @Override
//        public Visitable visit(MethodDeclaration n, Object arg) {
//            Optional<AnnotationExpr> annotationByClass = n.getAnnotationByClass(Access.class);
//            if (annotationByClass.isPresent()) {
//                NormalAnnotationExpr annotationExpr = (NormalAnnotationExpr) annotationByClass.get();
//                NodeList<MemberValuePair> pairs = annotationExpr.getPairs();
//                boolean isGet = pairs.stream().anyMatch(pair -> pair.getNameAsString().equals("method") && "GET".equalsIgnoreCase(pair.getValue().toString()));
//                List<MemberValuePair> pairList = new ArrayList<>() {{
//                    add(new MemberValuePair("value", new StringLiteralExpr(n.getNameAsString())));
//                }};
//                List<MemberValuePair> pathList = pairs.stream().filter(pair -> pair.getNameAsString().equals("path") && pair.getValue() != null).toList();
//                if (!pathList.isEmpty()) {
//                    pairList.addAll(pathList);
//                }
//                NormalAnnotationExpr openFeignAnnotation = new NormalAnnotationExpr(new Name(isGet ? "org.springframework.web.bind.annotation.GetMapping" : "org.springframework.web.bind.annotation.PostMapping"), new NodeList<>(pairList));
//                n.addAnnotation(openFeignAnnotation);
//            }
//            return super.visit(n, arg);
//        }
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
//        ModifyVisitor modifyVisitor = new ModifyVisitor();
//        modifyVisitor.visit(cu, null);
//        System.out.println(cu.toString());
//    }
//}
