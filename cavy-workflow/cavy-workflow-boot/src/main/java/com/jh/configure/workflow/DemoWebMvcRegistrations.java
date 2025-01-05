//package com.jh.configure.workflow;
//
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//@Component
//public class DemoWebMvcRegistrations  implements WebMvcRegistrations {
//    @Override
//    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//        return new DemoRequestMappingHandlerMapping();
//    }
//    /** 注意这个地方不能直接将bean 直接注入到spring 环境，
//     * 否则你会发现里面每一个controller 都会被映射2次
//     * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry#getMappingsByUrl
//     **/
//    public static class DemoRequestMappingHandlerMapping extends RequestMappingHandlerMapping implements WebMvcRegistrations {
//        @Override
//        protected boolean isHandler(Class<?> beanType) {
//            return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
//                            // 当前类上存在RequestMapping 但是不存在FeignClient注解
//                            (AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class)
//                                     && !AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class)
//                            ));
//        }
//
//
//    }
//}
