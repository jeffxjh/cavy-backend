package com.jh.cavybackend.filter;

import com.auth0.jwt.interfaces.Claim;
import com.jh.cavybackend.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

//@Component//无需添加此注解，在启动类添加@ServletComponentScan注解后，会自动将带有@WebFilter的注解进行注入！
@Slf4j
@Component
@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        if (uri.contains("/user/login") || uri.contains("/user/logout") || uri.contains("/user/register") || uri.contains("/verify")
                    || uri.contains("swagger")
                    || uri.contains("druid")
                    || uri.endsWith(".jpg") || uri.endsWith(".css") || uri.endsWith(".jpeg") || uri.endsWith(".js")) {
            //放过这些不需要检查的资源文件
            chain.doFilter(request, response);
            return;
        }

        response.setCharacterEncoding("UTF-8");
        //获取 header里的token
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {
            if (token == null) {
                response.getWriter().write("missing token！");
                return;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("illegality token！");
                return;
            }
            Integer id = userData.get("id").asInt();
            String realName = userData.get("realName").asString();
            String userName = userData.get("userName").asString();
            //拦截器 拿到用户信息，放到request中
            request.setAttribute("id", id);
            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}