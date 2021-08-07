package com.jh.cavy.jwt.jwt;

import cn.hutool.core.util.StrUtil;
import com.jh.cavy.jwt.web.HttpRequestHeaders;
import com.jh.cavy.jwt.common.Resquest.RequestHeadHolder;
import com.jh.cavy.jwt.common.Result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@Component//无需添加此注解，在启动类添加@ServletComponentScan注解后，会自动将带有@WebFilter的注解进行注入！
@Slf4j
@Component
@WebFilter(filterName = "JwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private JwtProperties jwtProperties;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    private boolean isWebSocketRequest(HttpServletRequest req) {
        return ("websocket".equalsIgnoreCase(req.getHeader(HttpRequestHeaders.Upgrade.getName()))
                        && "upgrade".equalsIgnoreCase(req.getHeader(HttpRequestHeaders.Connection.getName())));
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
        String token = jwtTokenUtil.getToken(request);

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {
            //判断是否websocket
            if (isWebSocketRequest(request)){
                //前端将token放在请求头Sec-WebSocket-Protocol中
                String protocol = request.getHeader("Sec-WebSocket-Protocol");
                if (StrUtil.isNotBlank(protocol)) {
                    token = protocol;
                    //new WebSocket("ws://localhost:8011",[tokenValue]);
                    //如果前端使用这种方式传递token  response必须设置下面的head 将token返回
                    response.addHeader("Sec-WebSocket-Protocol", protocol);
                }
                if (StrUtil.isBlank(token)) {
                    log.error("websocket missing token！");
                    response.getWriter().write("websocket missing token！");
                    return;
                }
            }
            if (StrUtil.isBlank(token)) {
                log.error("missing token！");
                response.getWriter().write("missing token！");
                return;
            }
            //auth0login走下面逻辑
            //Map<String, Claim> userData = JwtUtil.verifyToken(token);
            //if (userData == null) {
            //    response.getWriter().write("illegality token！");
            //    return;
            //}
            //Integer id = userData.get("id").asInt();
            //String realName = userData.get("realName").asString();
            //String userName = userData.get("userName").asString();

            //jjwtlogin走下面逻辑
            JwtUser jwtUser = jwtTokenUtil.validateToken(token);
            if (jwtUser == null) {
                //下面这种这两种方式返回前端会出现跨域问题 只能重定向到异常处理接口再统一异常处理中处理
                //response.getWriter().write("illegality token！");
                //returnJson(response, JSON.toJSONString(new ResultVO<>(ResultCode.TOKEN_EXPIRED.getCode(), "登录信息已失效,请重新登录")));
                //return;
                // 异常捕获，发送到expiredJwtException
                request.setAttribute("exception", ResultCode.TOKEN_EXPIRED.getCode());
                //将异常分发到/expiredJwtException控制器
                request.getRequestDispatcher("/expiredjwtexception").forward(request, response);
                return;
            }
            String realName = jwtUser.getUsername();
            String userName = jwtUser.getAccount();
            RequestHeadHolder.setAccount(userName);
            RequestHeadHolder.setRealName(realName);
            //拦截器 拿到用户信息，放到request中
            //request.setAttribute("id", id);
            request.setAttribute("realName", realName);
            request.setAttribute("userName", userName);
            chain.doFilter(request, response);
        }
    }

    private void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    @Override
    public void destroy() {
    }
}