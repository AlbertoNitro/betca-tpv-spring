package es.upm.miw.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.upm.miw.resources.exceptions.ArticleCodeNotFoundException;
import es.upm.miw.resources.exceptions.CashierClosedException;
import es.upm.miw.resources.exceptions.CashierCreateException;
import es.upm.miw.resources.exceptions.ErrorMessage;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.ProviderFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.ProviderIdNotFoundException;
import es.upm.miw.resources.exceptions.TicketIdNotFoundException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({TicketIdNotFoundException.class, UserIdNotFoundException.class, 
        FileException.class, ArticleCodeNotFoundException.class, ProviderIdNotFoundException.class})
    @ResponseBody
    public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

    // Exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class, UserFieldAlreadyExistException.class, FieldInvalidException.class, CashierClosedException.class,
            CashierCreateException.class, ProviderFieldAlreadyExistException.class})
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception, "");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    @ResponseBody
    public ErrorMessage forbiddenRequest(Exception exception) {
        return new ErrorMessage(exception, "");
    }
    
}
