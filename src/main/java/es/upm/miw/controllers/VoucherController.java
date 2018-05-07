package es.upm.miw.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.VoucherDto;
import es.upm.miw.repositories.core.VoucherRepository;
import es.upm.miw.resources.exceptions.NotFoundException;
import es.upm.miw.resources.exceptions.VoucherException;
import es.upm.miw.services.PdfService;
import es.upm.miw.utils.Encrypting;

@Controller
public class VoucherController {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PdfService pdfService;

    public Optional<byte[]> createVoucher(BigDecimal value) {
        Voucher voucher = new Voucher(value);
        String id;
        do {
            id = new Encrypting().shortId64UrlSafe();
        } while (this.voucherRepository.findOne(id) != null);
        voucher.setId(id);
        this.voucherRepository.save(voucher);
        return pdfService.generateVoucher(voucher);
    }

    public VoucherDto readVoucher(String id) throws NotFoundException {
        Voucher voucher = this.voucherRepository.findOne(id);
        if (voucher == null) {
            throw new NotFoundException("Voucher id (" + id + ")");
        }
        return new VoucherDto(voucher);
    }

    public BigDecimal consumeVoucher(String id) throws NotFoundException, VoucherException {
        Voucher voucher = this.voucherRepository.findOne(id);
        if (voucher == null) {
            throw new NotFoundException("Voucher id (" + id + ")");
        }
        if (voucher.isUsed()) {
            throw new VoucherException("Voucher is already consumed (" + id + ")");
        }
        voucher.setDateOfUse(new Date());
        this.voucherRepository.save(voucher);
        return voucher.getValue();
    }

    public List<VoucherDto> readVoucherAll() {
        List<Voucher> voucherList = this.voucherRepository.findAll(new Sort(Sort.Direction.DESC, "creationDate"));
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

}
