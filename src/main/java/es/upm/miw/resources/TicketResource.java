package es.upm.miw.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import es.upm.miw.dtos.TicketCreationInputDto;
import es.upm.miw.dtos.TicketDto;
import es.upm.miw.dtos.TicketSearchOutputDto;
import es.upm.miw.dtos.TicketUpdationInputDto;
import es.upm.miw.resources.exceptions.FieldInvalidException;
import es.upm.miw.resources.exceptions.TicketIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TicketResource.TICKETS)
public class TicketResource {
    public static final String TICKETS = "/tickets";

    public static final String ID_ID = "/{id}";

    public static final String SEARCH_DATE = "/search/date";

    public static final String SEARCH_BY_ID_AND_DATES = "/searchByIdAndDates";

    public static final String SEARCH_BY_CREATION_DATES = "/searchByCreationDates";

    @Autowired
    private TicketController ticketController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public byte[] createTicket(@Valid @RequestBody TicketCreationInputDto ticketCreationDto) throws FieldInvalidException {
        return this.ticketController.createTicket(ticketCreationDto).orElseThrow(() -> new FieldInvalidException("Article exception"));
    }

    @GetMapping(value = ID_ID)
    public TicketDto readTicket(@PathVariable String id) throws TicketIdNotFoundException {
        return this.ticketController.read(id).orElseThrow(() -> new TicketIdNotFoundException(id));
    }

    @GetMapping(value = SEARCH_DATE)
    public List<TicketDto> findBetweenDates(@RequestParam long start, @RequestParam long end) {
        return this.ticketController.findBetweenDates(new Date(start), new Date(end));
    }

    @PutMapping(value = ID_ID, produces = {"application/pdf", "application/json"})
    public byte[] updateTicket(@PathVariable String id, @RequestBody TicketDto ticketDto)
            throws TicketIdNotFoundException, FieldInvalidException {
        if (!this.ticketController.existTicket(id)) {
            throw new TicketIdNotFoundException(id);
        }
        return this.ticketController.updateTicket(id, ticketDto).orElseThrow(() -> new FieldInvalidException("Ticket Update Exception"));
    }

    @PatchMapping(value = ID_ID)
    public void updateAmountAndStateTicket(@PathVariable(value = "id") String id,
            @Valid @RequestBody TicketUpdationInputDto ticketUpdationInputDto) throws TicketIdNotFoundException {
        if (this.ticketController.existTicket(id)) {
            this.ticketController.updateAmountAndStateTicket(id, ticketUpdationInputDto);
        } else {
            throw new TicketIdNotFoundException(id);
        }
    }

    @RequestMapping(value = SEARCH_BY_CREATION_DATES, method = RequestMethod.GET)
    public List<TicketDto> findTicketsBetweenCreationDates(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam("initialDate") Date initialDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam("finalDate") Date finalDate) {
        return this.ticketController.getBetweenDates(initialDate, finalDate);
    }

    @RequestMapping(value = SEARCH_BY_ID_AND_DATES, method = RequestMethod.GET)
    public List<TicketSearchOutputDto> findIdArticleDatesBetween(@RequestParam("id") String id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("dateStart") Date dateStart,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(value = "dateFinish") Date dateFinish) {
        return this.ticketController.findByIdArticleDatesBetween(id, dateStart, dateFinish);
    }

    @RequestMapping(value = "historicalProducts", method = RequestMethod.GET)
    public List<HistoricalProductOutPutDto> getHistoricalProductsData(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("initDate") Date startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("endDate") Date endDate) {

        List<HistoricalProductOutPutDto> result = new ArrayList<HistoricalProductOutPutDto>();

        Random generator = new Random();
        int numProducts = generator.nextInt(10);
        int numMonths = generator.nextInt(10);
        for (int i = 0; i < numProducts; i++) {
            List<Integer> valuesPerMonth = new ArrayList<Integer>();
            String productName = "product" + i;
            for (int j = 0; j < numMonths; j++) {
                valuesPerMonth.add(generator.nextInt(100));
            }
            result.add(new HistoricalProductOutPutDto(valuesPerMonth, productName));
        }

        return result;
    }

}
