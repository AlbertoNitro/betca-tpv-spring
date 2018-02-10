package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TicketController;
import es.upm.miw.dtos.input.TicketCreationDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";

    @Autowired
    private TicketController ticketController;

    @RequestMapping(method = RequestMethod.POST, produces = "application/pdf")
    public @ResponseBody byte[] createTicket(@RequestBody TicketCreationDto ticketCreationDto) throws FieldInvalidException {
        this.validateFieldObject(ticketCreationDto.getCard(), "Card debe ser positivo");
        this.validateFieldObject(ticketCreationDto.getCash(), "Cash debe ser positivo");
        this.validateFieldObject(ticketCreationDto.getVoucher(), "Voucher debe ser positivo");
        if (ticketCreationDto.getShoppingCart() == null || ticketCreationDto.getShoppingCart().isEmpty()) {
            throw new FieldInvalidException("Shopping no debe estar vacia");
        }
        Optional<byte[]> pdf = this.ticketController.createTicket(ticketCreationDto);
        if (!pdf.isPresent()) {
            throw new FieldInvalidException("Codigo de articulo no encontrado");
        } else {
            return pdf.get();
        }
    }

    private void validateFieldObject(BigDecimal decimal, String msg) throws FieldInvalidException {
        if (decimal == null || decimal.signum() == -1) {
            throw new FieldInvalidException(msg);
        }
    }

}
