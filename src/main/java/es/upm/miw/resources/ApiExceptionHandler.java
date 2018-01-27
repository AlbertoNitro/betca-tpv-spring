package es.upm.miw.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.upm.miw.resources.exceptions.ErrorMessage;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.UserFieldInvalidException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserIdNotFoundException.class})
    @ResponseBody
    public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception, request.getRequestURI().toString());
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserFieldAlreadyExistException.class, UserFieldInvalidException.class})
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception, "");
        return errorMessage;
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenException.class})
    @ResponseBody
    public ErrorMessage forbiddenRequest(Exception exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception, "");
        return errorMessage;
    }

}
