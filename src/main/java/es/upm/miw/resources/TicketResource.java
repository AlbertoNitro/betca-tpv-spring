package es.upm.miw.resources;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.TicketController;
import es.upm.miw.dtos.HistoricalProductOutPutDto;
import es.upm.miw.dtos.IncomeComparision;
import es.upm.miw.dtos.NumProductsSoldDto;
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.dtos.TicketSearchOutputDto;
import es.upm.miw.dtos.UserNotCommitedOutputDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;
import es.upm.miw.resources.exceptions.TicketIdNotFoundException;

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

    public static final String HISTORICAL_PRODUCTS = "/historicalProducts";

    public static final String NUM_PRODUCTS_SOLD = "/numProductsSold";

    public static final String COMPARISION_INCOME = "/comparisionIncome";

    @Autowired
    private TicketController ticketController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public byte[] createTicket(@Valid @RequestBody TicketCreationInputDto ticketCreationDto) throws FieldInvalidException {
        return this.ticketController.createTicketAndPdf(ticketCreationDto)
                .orElseThrow(() -> new FieldInvalidException("Article exception"));
    }

    @GetMapping(value = ID_ID)
    public TicketDto readTicket(@PathVariable String id) throws TicketIdNotFoundException {
        return this.ticketController.read(id).orElseThrow(() -> new TicketIdNotFoundException(id));
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
    public TicketDto findLastByMobile(@RequestParam String mobile) throws TicketIdNotFoundException {
        return this.ticketController.findLastByMobile(mobile).orElseThrow(() -> new TicketIdNotFoundException("mobile: " + mobile));
    }

    @GetMapping(value = SEARCH_ORDER_ID)
    public List<UserNotCommitedOutputDto> findByOrderArticleNotCommited(@RequestParam String orderId) throws TicketIdNotFoundException {
        return this.ticketController.findByOrderArticleNotCommited(orderId)
                .orElseThrow(() -> new TicketIdNotFoundException("orderId: " + orderId));
    }

    @GetMapping(value = SEARCH_FAMILY_ID)
    public List<UserNotCommitedOutputDto> findByFamilyId(@RequestParam String familyId) throws TicketIdNotFoundException {
        return this.ticketController.findByFamilyIdNotCommited(familyId)
                .orElseThrow(() -> new TicketIdNotFoundException("familyId: " + familyId));
    }

    @GetMapping(value = SEARCH_TICKETS_NOT_COMMITED)
    public List<UserNotCommitedOutputDto> findByTicketsNotCommited() {
        return this.ticketController.findByTicketsNotCommited();
    }

    @PutMapping(value = ID_ID, produces = {"application/pdf", "application/json"})
    public byte[] updateTicket(@PathVariable String id, @RequestBody TicketCreationInputDto ticketCreationInputDto)
            throws TicketIdNotFoundException, FieldInvalidException {
        if (!this.ticketController.existTicket(id)) {
            throw new TicketIdNotFoundException(id);
        }
        return this.ticketController.updateTicket(id, ticketCreationInputDto)
                .orElseThrow(() -> new FieldInvalidException("Ticket Update Exception"));
    }

    @RequestMapping(value = SEARCH_BY_ID_AND_DATES, method = RequestMethod.GET)
    public List<TicketSearchOutputDto> findIdArticleDatesBetween(@RequestParam("id") String id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("dateStart") Date dateStart,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "dateFinish") Date dateFinish) {
        return this.ticketController.findByIdArticleDatesBetween(id, dateStart, dateFinish);
    }

    @RequestMapping(value = HISTORICAL_PRODUCTS, method = RequestMethod.GET)
    public List<HistoricalProductOutPutDto> getHistoricalProductsData(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("initDate") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") Date endDate) {

        return this.ticketController.getHistoricalProductsDataBetweenDates(startDate, endDate);
    }

    @RequestMapping(value = NUM_PRODUCTS_SOLD, method = RequestMethod.GET)
    public List<NumProductsSoldDto> getNumProductsSold(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("initDate") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") Date endDate) {

        return this.ticketController.getNumProductsSold(startDate, endDate);
    }

    @RequestMapping(value = COMPARISION_INCOME, method = RequestMethod.GET)
    public List<IncomeComparision> getComparisionIncome(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("initDate") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") Date endDate) {

        return this.ticketController.getIncomeComparisionData(startDate, endDate);
    }

}
