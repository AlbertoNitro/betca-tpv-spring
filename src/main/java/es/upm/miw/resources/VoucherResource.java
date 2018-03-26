package es.upm.miw.resources;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
import es.upm.miw.resources.exceptions.FieldInvalidException;
import es.upm.miw.resources.exceptions.VoucherConsumedException;
import es.upm.miw.resources.exceptions.VoucherReferenceNotFoundException;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(VoucherResource.VOUCHERS)
public class VoucherResource {

    public static final String VOUCHERS = "/vouchers";

    public static final String REFERENCE = "/{reference}";

    public static final String VALID = "/valid";

    @Autowired
    private VoucherController voucherController;

    @PostMapping(produces = {"application/pdf", "application/json"})
    public @ResponseBody byte[] createVoucher(@Valid @RequestBody VoucherDto voucherDto) throws FieldInvalidException {
        Optional<byte[]> pdf = this.voucherController.createVoucher(voucherDto.getValue());
        if (!pdf.isPresent()) {
            throw new FieldInvalidException("Voucher exception");
        } else {
            return pdf.get();
        }
    }

    @PatchMapping(value = REFERENCE)
    public BigDecimal consumeVoucher(@PathVariable String reference) throws VoucherReferenceNotFoundException, VoucherConsumedException {
        if (!this.voucherController.existsVoucher(reference)) {
            throw new VoucherReferenceNotFoundException();
        }
        if (this.voucherController.consumedVoucher(reference)) {
            throw new VoucherConsumedException();
        }
        return this.voucherController.consumeVoucher(reference);
    }

    @GetMapping
    public List<VoucherDto> readVoucherAll() {
        return this.voucherController.readVoucherAll();
    }
    
    @GetMapping(value = VALID)
    public List<VoucherDto> readVoucherAllValid() {
        return this.voucherController.readVoucherAllValid();
    }
    
    @GetMapping(value = REFERENCE)
    public VoucherDto readVoucher(@PathVariable String reference) throws VoucherReferenceNotFoundException {
        return this.voucherController.readVoucher(reference).orElseThrow(() -> new VoucherReferenceNotFoundException(reference));
    }
   

}
