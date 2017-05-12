package com.orange.core.web.exception;


import com.orange.common.base.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * Created by chzq on 2017/4/1.
 */

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    private final static String EXPTION_MSG_KEY = "message";

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletRequest request, Exception ex)
    {
        ex.printStackTrace();
    }

    @ExceptionHandler(BusinessException.class)
    public void handleBizExp(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        logger.error("BusinessException handler" + ex.getMessage() );
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handSql(Exception ex){
        logger.error("SQL Exception " + ex.getMessage());
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("sql_error");
        ex.printStackTrace();
        return mv;
    }
}
