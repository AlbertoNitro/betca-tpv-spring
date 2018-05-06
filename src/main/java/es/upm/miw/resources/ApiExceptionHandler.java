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

import es.upm.miw.resources.exceptions.ArticleBadRequestException;
import es.upm.miw.resources.exceptions.ArticleNotFoundException;
import es.upm.miw.resources.exceptions.ArticlesFamilyCreationException;
import es.upm.miw.resources.exceptions.ArticlesFamilyNotFoudException;
import es.upm.miw.resources.exceptions.BudgetIdNotFoundException;
import es.upm.miw.resources.exceptions.CashierClosedException;
import es.upm.miw.resources.exceptions.CashierCreateException;
import es.upm.miw.resources.exceptions.CashierMovementException;
import es.upm.miw.resources.exceptions.ErrorMessage;
import es.upm.miw.resources.exceptions.FieldAlreadyExistException;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.InvoiceIdNotFoundException;
import es.upm.miw.resources.exceptions.NotFoundException;
import es.upm.miw.resources.exceptions.OrderAlreadyExistException;
import es.upm.miw.resources.exceptions.OrderException;
import es.upm.miw.resources.exceptions.OrderIdNotFoundException;
import es.upm.miw.resources.exceptions.TicketIdNotFoundException;
import es.upm.miw.resources.exceptions.UserIdNotFoundException;
import es.upm.miw.resources.exceptions.VoucherConsumedException;
import es.upm.miw.resources.exceptions.VoucherReferenceNotFoundException;
import es.upm.miw.resources.exceptions.UserFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NotFoundException.class,
            
            ArticleNotFoundException.class,
            ArticlesFamilyNotFoudException.class,
            BudgetIdNotFoundException.class,
            FileException.class,
            InvoiceIdNotFoundException.class,
            OrderException.class,
            OrderIdNotFoundException.class,
            TicketIdNotFoundException.class,
            UserIdNotFoundException.class,
            VoucherReferenceNotFoundException.class
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
            
        ArticleBadRequestException.class,
        ArticlesFamilyCreationException.class,
        CashierClosedException.class,
        CashierCreateException.class,
        CashierMovementException.class,
        FieldInvalidException.class,
        OrderAlreadyExistException.class,
        UserFieldAlreadyExistException.class,
        VoucherConsumedException.class
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

    // Exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class,})
    @ResponseBody
    public ErrorMessage exception(Exception exception) {
        exception.printStackTrace();
        return new ErrorMessage(exception, exception.getStackTrace().toString());
    }

}
