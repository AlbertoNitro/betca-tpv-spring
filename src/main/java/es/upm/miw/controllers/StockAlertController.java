package es.upm.miw.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.StockAlert;
import es.upm.miw.dtos.StockAlertDto;
import es.upm.miw.dtos.StockAlertMinimumDto;
import es.upm.miw.repositories.core.StockAlertRepository;

@Controller
public class StockAlertController {

    @Autowired
    private StockAlertRepository stockAlertRepository;

    public void createStockAlert(StockAlertDto stockAlertDto) {
    		StockAlert stockAlert = new StockAlert(stockAlertDto.getName(), stockAlertDto.getArticles());
        this.stockAlertRepository.save(stockAlert);
    }

    public boolean nameRepeated(String oldName, StockAlertDto stockAlertDto) {
    		StockAlert stockAlert = this.stockAlertRepository.findByName(stockAlertDto.getName());
        return stockAlert != null && !stockAlert.getName().equals(oldName);
    }

    public boolean existsName(String name) {
        return this.stockAlertRepository.findByName(name) != null;
    }
   
    public boolean putStockAlert(String name, StockAlertDto stockAlertDto) {
        StockAlert stockAlert = this.stockAlertRepository.findByName(name);
        assert stockAlert != null;
        	stockAlert.setName(stockAlertDto.getName());
        	stockAlert.setArticles(stockAlertDto.getArticles());
        this.stockAlertRepository.save(stockAlert);
        
        return true;
    }

    public boolean deleteStockAlert(String name) {
        StockAlert stockAlertBd = this.stockAlertRepository.findByName(name);
        if (stockAlertBd == null) {
            return true;
        } else {
            this.stockAlertRepository.delete(stockAlertBd);
            return true;
        } 
    }

    public Optional<StockAlertDto> readStockAlert(String name) {
        StockAlert stockAlertBd = this.stockAlertRepository.findByName(name);
        if (stockAlertBd == null) {
            return Optional.empty();
        } else {
            return Optional.of(new StockAlertDto(stockAlertBd));
        }
    }

    public List<StockAlertMinimumDto> readStockAlertAll() {
        return this.stockAlertRepository.findStockAlertAll();
    }

}