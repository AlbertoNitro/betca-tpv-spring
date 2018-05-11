package es.upm.miw.resources;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.upm.miw.resources.exceptions.ArticlesFamilyCreationException;
import es.upm.miw.resources.exceptions.ArticlesFamilyNotFoudException;
import es.upm.miw.resources.exceptions.CashierException;
import es.upm.miw.resources.exceptions.ErrorMessage;
import es.upm.miw.resources.exceptions.FieldAlreadyExistException;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.InvoiceException;
import es.upm.miw.resources.exceptions.NotFoundException;
import es.upm.miw.resources.exceptions.OrderException;
import es.upm.miw.resources.exceptions.TicketIdNotFoundException;
import es.upm.miw.resources.exceptions.VoucherException;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class,
            
            ArticlesFamilyNotFoudException.class,
            FileException.class,

            OrderException.class,
            TicketIdNotFoundException.class
    })
    @ResponseBody
    public ErrorMessage notFoundRequest(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(exception, request.getRequestURI());
    }

    // Exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        DuplicateKeyException.class,
        MethodArgumentNotValidException.class,
        HttpMessageNotReadableException.class,
            
        FieldAlreadyExistException.class,
        InvoiceException.class,
            
        ArticlesFamilyCreationException.class,
        CashierException.class,
        FieldInvalidException.class,
        VoucherException.class
    })
    @ResponseBody
    public ErrorMessage badRequest(Exception exception) {
        return new ErrorMessage(exception, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
        AccessDeniedException.class,
        ForbiddenException.class
    })
    @ResponseBody
    public ErrorMessage forbiddenRequest(Exception exception) {
        return new ErrorMessage(exception, "");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class,})
    @ResponseBody
    public ErrorMessage exception(Exception exception) {
        exception.printStackTrace();
        return new ErrorMessage(exception, exception.getStackTrace().toString());
    }

}
