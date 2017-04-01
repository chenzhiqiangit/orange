package orange.web.exception;


import com.orange.base.BaseController;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public void handleException(HttpServletRequest request, Exception ex)
    {
        logger.error("exception handler" + ex.getMessage() );
        request.getSession(true).setAttribute(EXPTION_MSG_KEY, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public void handleBizExp(HttpServletRequest request, Exception ex){
        logger.error("Business exception handler  " + ex.getMessage() );
        request.getSession(true).setAttribute(EXPTION_MSG_KEY, ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handSql(Exception ex){
        logger.error("SQL Exception " + ex.getMessage());
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("sql_error");
        return mv;
    }
}
