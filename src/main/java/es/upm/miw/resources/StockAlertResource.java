package es.upm.miw.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.StockAlertController;
import es.upm.miw.dtos.StockAlertDto;
import es.upm.miw.dtos.StockAlertMinimumDto;
import es.upm.miw.resources.exceptions.ForbiddenException;
import es.upm.miw.resources.exceptions.StockAlertFieldAlreadyExistException;
import es.upm.miw.resources.exceptions.StockAlertIdNotFoundException;

@RestController
@RequestMapping(StockAlertResource.STOCKALERT)
public class StockAlertResource {

	public static final String STOCKALERT = "/stockAlert";

    public static final String NAME_ID = "/{name}";
    
    @Autowired
    private StockAlertController stockAlertController;

    @PostMapping
    public void createStockAlert(@Valid @RequestBody StockAlertDto stockAlertDto) throws StockAlertFieldAlreadyExistException {
        if (this.stockAlertController.existsName(stockAlertDto.getName())) {
            throw new StockAlertFieldAlreadyExistException("Existing name");
        }
        this.stockAlertController.createStockAlert(stockAlertDto);
    }

    @PutMapping(value = NAME_ID)
    public void putStockAlert(@PathVariable String name, @Valid @RequestBody StockAlertDto stockAlertDto)
            throws ForbiddenException, StockAlertIdNotFoundException, StockAlertFieldAlreadyExistException {
        if (!this.stockAlertController.existsName(name)) {
            throw new StockAlertIdNotFoundException("Not existing name");
        }
        if (!this.stockAlertController.putStockAlert(name, stockAlertDto)) {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping(value = NAME_ID)
    public void deleteStockAlert(@PathVariable String name) throws ForbiddenException {
        if (!this.stockAlertController.deleteStockAlert(name)) {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = NAME_ID, method = RequestMethod.GET)
    public StockAlertDto readStockAlert(@PathVariable String name) throws StockAlertIdNotFoundException {
        return this.stockAlertController.readStockAlert(name).orElseThrow(() -> new StockAlertIdNotFoundException(name));
    }

    @GetMapping
    public List<StockAlertMinimumDto> readStockAlertAll() {
        return this.stockAlertController.readStockAlertAll();
    }
}
