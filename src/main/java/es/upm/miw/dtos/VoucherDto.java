package es.upm.miw.dtos;

import java.math.BigDecimal;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.utils.Encrypting;

public class VoucherDto {

    private String reference;

    private BigDecimal value;

    private boolean used;

    public VoucherDto() {
        // Empty for framework
    }

    public VoucherDto(BigDecimal value) {
        this.reference = new Encrypting().encryptInBase64UrlSafe();
        this.value = value;
        this.used = false;
    }

    public VoucherDto(Voucher voucher) {
        this.reference = voucher.getReference();
        this.value = voucher.getValue();
        this.used = voucher.isUsed();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "VoucherDto [value=" + value + ", used=" + used + "]";
    }

}
