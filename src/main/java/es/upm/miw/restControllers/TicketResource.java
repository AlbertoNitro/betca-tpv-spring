package es.upm.miw.restControllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.TicketController;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.dtos.UserNotCommitedOutputDto;
import es.upm.miw.exceptions.PdfException;
import es.upm.miw.exceptions.NotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";

    public static final String ID_ID = "/{id}";

    public static final String SEARCH_DATE = "/search/date";

    public static final String SEARCH_MOBILE = "/search/mobile";

    public static final String SEARCH_MOBILE_LAST = "/search/mobile/last";

    public static final String SEARCH_ORDER_ID = "/search/order-id";

    public static final String SEARCH_FAMILY_ID = "/search/family-id";

    public static final String SEARCH_TICKETS_NOT_COMMITED = "/search/tickets-not-commited";

    public static final String SEARCH_BY_ID_AND_DATES = "/searchByIdAndDates";

    public static final String SEARCH_BY_CREATION_DATES = "/searchByCreationDates";

    @Autowired
    private TicketController ticketController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public byte[] createTicket(@Valid @RequestBody TicketCreationInputDto ticketCreationDto)
            throws MethodArgumentNotValidException, NotFoundException, PdfException {
        return this.ticketController.createTicketAndPdf(ticketCreationDto);
    }

    @GetMapping(value = ID_ID)
    public TicketDto readTicket(@PathVariable String id) throws NotFoundException {
        return this.ticketController.read(id);
    }

    @PutMapping(value = ID_ID, produces = {"application/pdf", "application/json"})
    public byte[] updateTicket(@PathVariable String id, @RequestBody TicketCreationInputDto ticketCreationInputDto)
            throws NotFoundException, PdfException {
        return this.ticketController.updateTicket(id, ticketCreationInputDto);
    }

    @GetMapping(value = SEARCH_DATE)
    public List<TicketDto> findBetweenDates(@RequestParam long start, @RequestParam long end) {
        return this.ticketController.findBetweenDates(new Date(start), new Date(end));
    }

    @GetMapping(value = SEARCH_MOBILE)
    public List<TicketDto> findByMobile(@RequestParam String mobile) {
        return this.ticketController.findByMobile(mobile);
    }

    @GetMapping(value = SEARCH_MOBILE_LAST)
    public TicketDto findLastByMobile(@RequestParam String mobile) throws NotFoundException {
        return this.ticketController.findLastByMobile(mobile);
    }

    @GetMapping(value = SEARCH_ORDER_ID)
    public List<UserNotCommitedOutputDto> findByOrderArticleNotCommited(@RequestParam String orderId) throws NotFoundException {
        return this.ticketController.findByOrderArticleNotCommited(orderId);
    }

    @GetMapping(value = SEARCH_FAMILY_ID)
    public List<UserNotCommitedOutputDto> findByFamilyId(@RequestParam String familyId) throws NotFoundException {
        return this.ticketController.findByFamilyIdNotCommited(familyId);
    }

    @GetMapping(value = SEARCH_TICKETS_NOT_COMMITED)
    public List<UserNotCommitedOutputDto> findByTicketsNotCommited() {
        return this.ticketController.findByTicketsNotCommited();
    }

}
