package com.hmahn.board.web.exception;

import com.hmahn.board.domain.exception.ErrorStatus;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController implements ErrorController {
    @Override
    public String getErrorPath(){
        return null; // 스프링부트 2.3부터는 해당 방법을 사용하지 않는다.
    }

    @GetMapping("/error")
    public String exceptionHandler(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        ErrorStatus errorStatus; // 에러상태 객체
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if( statusCode == 204){
                errorStatus = ErrorStatus.DATA_NOT_FOUND;
            }else if(statusCode == 404){
                errorStatus = ErrorStatus.PAGE_NOT_FOUND;
            }else if(statusCode == 403){
                errorStatus = ErrorStatus.ROLE_NOT_FOUND;
            }else if(statusCode == 409){
                errorStatus = ErrorStatus.REQUEST_CONFLICT;
            }else{
                errorStatus = ErrorStatus.REQUEST_EXCEPTION;
            }

            String errorCode = errorStatus.getStatus().toString();
            String errorMessage = errorStatus.getMessage();

            if(errorMessage.equals("") || errorMessage == null){
                errorMessage = request.getParameter("message").toString();
            }

            model.addAttribute("errorCode", errorCode);
            model.addAttribute("errorMessage", errorMessage);
        }
        return "exception/error";
    }
}
