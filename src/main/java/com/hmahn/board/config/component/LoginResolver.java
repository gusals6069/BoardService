package com.hmahn.board.config.component;

import com.hmahn.board.annotation.LoginUser;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.RequiredArgsConstructor;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean chk1 = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean chk2 = UserSessionDto.class.equals(parameter.getParameterType());

        return chk1 && chk2;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user");
    }
}