package es.upm.miw.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.ArticlesTrackingController;

import es.upm.miw.dtos.TicketDto;

import es.upm.miw.resources.exceptions.TicketIdNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ArticlesTrackingResource.TRACKING)

public class ArticlesTrackingResource {
public static final String TRACKING = "/tracking";
    

    public static final String ID_ID = "/{id}";
  
    @Autowired
    private ArticlesTrackingController articlesTrackingController;

    @GetMapping(value = ID_ID)
    public TicketDto readTicket(@PathVariable String id) throws TicketIdNotFoundException {
    	return this.articlesTrackingController.findTicketNotCommitted(id).orElseThrow(() -> new TicketIdNotFoundException(id));
    }

}

