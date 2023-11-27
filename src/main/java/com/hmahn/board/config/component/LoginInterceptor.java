package com.hmahn.board.config.component;

import com.hmahn.board.domain.user.User;
import com.hmahn.board.web.user.dto.UserSessionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("[LoginInterceptor - preHandle]");

        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("user") != null){
            UserSessionDto user = (UserSessionDto) session.getAttribute("user");

            if(StringUtils.isEmpty(user.getPassword()) && !StringUtils.isEmpty(user.getEmail())){
                response.sendRedirect("/user/changePassword");
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("[LoginInterceptor - postHandle]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception ex) throws Exception {
        logger.info("[LoginInterceptor - afterCompletion]");
    }
}