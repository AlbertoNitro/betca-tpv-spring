package es.upm.miw.restControllers;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.upm.miw.businessControllers.VoucherController;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.exceptions.FileException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.exceptions.VoucherException;

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
    public byte[] createVoucher(@Valid @RequestBody VoucherDto voucherDto) throws MethodArgumentNotValidException, FileException {
        return this.voucherController.createVoucher(voucherDto.getValue()).orElseThrow(() -> new FileException("Voucher PDF exception"));
    }

    @GetMapping(value = ID_ID)
    public VoucherDto readVoucher(@PathVariable String id) throws NotFoundException {
        return this.voucherController.readVoucher(id);
    }

    @PatchMapping(value = ID_ID)
    public BigDecimal consumeVoucher(@PathVariable String id) throws NotFoundException, VoucherException {
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

}
