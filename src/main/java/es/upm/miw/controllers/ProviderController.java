package es.upm.miw.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Provider;
import es.upm.miw.dtos.ProviderDto;
import es.upm.miw.dtos.ProviderMinimumDto;
import es.upm.miw.repositories.core.ProviderRepository;

@Controller
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;
    
    public Optional<ProviderDto> readProvider(String id) {
        Provider providerBd = this.providerRepository.findById(id);
        if (providerBd == null) {
            return Optional.empty();
        } else {
            return Optional.of(new ProviderDto(providerBd));
        }
    }

    public List<ProviderMinimumDto> readProviderAll() {
        return this.providerRepository.findProviderAll();
    }

}
