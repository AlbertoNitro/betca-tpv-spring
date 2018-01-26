package es.upm.miw.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AdminResource.ADMINS)
public class AdminResource {

    public static final String ADMINS = "/admins";

    public static final String STATE = "/state";

    // Se puede comprobar con un navegador
    @RequestMapping(value = STATE, method = RequestMethod.GET)
    public String state() {
        return "{\"state\":\"ok\"}";
    }


}
