package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.repositories.core.VoucherRepository;
import es.upm.miw.services.PdfService;

@Controller
public class VoucherController {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createVoucher(BigDecimal value) {
        Voucher voucher = new Voucher(value);
        this.voucherRepository.save(voucher);
        return pdfService.generateVoucher(voucher);
    }

    public List<VoucherDto> readVoucherAll() {
        List<Voucher> voucherList = this.voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<VoucherDto>();
        for (Voucher voucher : voucherList) {
            voucherDtoList.add(new VoucherDto().minimumDto(voucher));
        }
        return voucherDtoList;
    }

    public List<VoucherDto> readVoucherAllValid() {
        List<Voucher> voucherList = this.voucherRepository.findAll();
        List<VoucherDto> voucherDtoList = new ArrayList<VoucherDto>();
        for (Voucher voucher : voucherList) {
            if (!voucher.isUsed()) {
                voucherDtoList.add(new VoucherDto().minimumDto(voucher));
            }
        }
        return voucherDtoList;
    }

    public BigDecimal consumeVoucher(String id) {
        Voucher voucher = this.voucherRepository.findOne(id);
        assert voucher != null;
        assert !voucher.isUsed();
        voucher.setDateOfUse(new Date());
        this.voucherRepository.save(voucher);
        return voucher.getValue();
    }

    public boolean existsVoucher(String id) {
        return this.voucherRepository.findOne(id) != null;
    }

    public boolean consumedVoucher(String id) {
        Voucher voucher = this.voucherRepository.findOne(id);
        assert voucher != null;
        return voucher.isUsed();
    }

    public Optional<VoucherDto> readVoucher(String id) {
        Voucher voucher = this.voucherRepository.findOne(id);
        if (voucher != null) {
            return Optional.of(new VoucherDto(voucher));
        } else {
            return Optional.empty();
        }
    }

}
