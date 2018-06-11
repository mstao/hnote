package me.mingshan.web.authorization.interceptor;


import me.mingshan.common.annotation.Authorization;

import me.mingshan.facade.model.Token;
import me.mingshan.facade.service.TokenService;
import me.mingshan.web.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * The custom interceptor that checks out the current request has authorization.
 * @Author: Minsghan
 * @Date: Created in 19:27 2017/10/14
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenServicer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // Checks out the annotation of authorization that is method level.
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        // Gets authorization string from request header.
        String authorization = request.getHeader(Constants.AUTHORIZATION);

        // Gets the model of Token from authorization string.
        Token token = tokenServicer.getToken(authorization);
        // Checks out the token that is from Redis,
        if (tokenServicer.checkToken(token)) {
            // Puts userId into request.
            request.setAttribute(Constants.CURRENT_USER_ID, token.getUserId());
            return true;
        }

        // If verify token failed, and the current method has the annotation of authorization,
        // sets the response code to 401.
        // The 401 code means unauthorized.
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
