package es.upm.miw.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import es.upm.miw.services.PdfService;

@Controller
public class BudgetController {

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createBudget() {
        return pdfService.generateBudget();
    }
}
