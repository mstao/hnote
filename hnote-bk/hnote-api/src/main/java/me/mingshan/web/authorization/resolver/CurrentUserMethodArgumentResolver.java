package me.mingshan.web.authorization.resolver;

import me.mingshan.common.annotation.CurrentUser;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.web.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @Description:
 * @Author: Minsghan
 * @Date: Created in 22:12 2017/10/14
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        if (methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }

        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {
        Object source = nativeWebRequest.getAttribute(Constants.CURRENT_USER_ID,
                            RequestAttributes.SCOPE_REQUEST);
        if (source == null) {
            return null;
        }

        long currentUserId = (long)source;
        User user = null;
        if (currentUserId != 0) {
            return userService.findById(currentUserId);
        }
        // 400 - Bad Request
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
