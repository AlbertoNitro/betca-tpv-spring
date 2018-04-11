package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.controllers.VoucherController;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.resources.exceptions.FileException;
import es.upm.miw.resources.exceptions.VoucherConsumedException;
import es.upm.miw.resources.exceptions.VoucherReferenceNotFoundException;
import es.upm.miw.utils.Encrypting;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(VoucherResource.VOUCHERS)
public class VoucherResource {

    public static final String VOUCHERS = "/vouchers";

    public static final String ID_ID = "/{id}";

    public static final String VALID = "/valid";

    @Autowired
    private VoucherController voucherController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public @ResponseBody byte[] createVoucher(@Valid @RequestBody VoucherDto voucherDto) throws FileException {
        return this.voucherController.createVoucher(voucherDto.getValue()).orElseThrow(() -> new FileException("Voucher PDF exception"));
    }

    @PatchMapping(value = ID_ID)
    public BigDecimal consumeVoucher(@PathVariable String id) throws VoucherReferenceNotFoundException, VoucherConsumedException {
        if (!this.voucherController.existsVoucher(id)) {
            throw new VoucherReferenceNotFoundException();
        }
        if (this.voucherController.consumedVoucher(id)) {
            throw new VoucherConsumedException();
        }
        return this.voucherController.consumeVoucher(id);
    }

    @GetMapping
    public List<VoucherDto> readVoucherAll() {
        return this.voucherController.readVoucherAll();
    }
    
    @GetMapping(value = VALID)
    public List<VoucherDto> readVoucherAllValid() {
        return this.voucherController.readVoucherAllValid();
    }
    
    @GetMapping(value = ID_ID)
    public VoucherDto readVoucher(@PathVariable String id) throws VoucherReferenceNotFoundException {
        return this.voucherController.readVoucher(id).orElseThrow(() -> new VoucherReferenceNotFoundException(id));
    }

}
