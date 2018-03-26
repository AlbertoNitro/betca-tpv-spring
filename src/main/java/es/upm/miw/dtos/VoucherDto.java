package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.utils.Encrypting;

public class VoucherDto {

    private String reference;

    private BigDecimal value;
    
    private Date creationDate;

    private Date dateOfUse;

    public VoucherDto() {
        // Empty for framework
    }

    public VoucherDto(BigDecimal value) {
        this.reference = new Encrypting().encryptInBase64UrlSafe();
        this.value = value;
        this.dateOfUse = null;
    }

    public VoucherDto(Voucher voucher) {
        this.reference = voucher.getReference();
        this.value = voucher.getValue();
        this.creationDate=voucher.getCreationDate();
        this.dateOfUse = voucher.getDateOfUse();
    }
    
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateOfUse() {
        return dateOfUse;
    }

    public void setDateOfUse(Date dateOfUse) {
        this.dateOfUse = dateOfUse;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        String date = "null";
        String dateUse = "null";
        if (creationDate != null) {
            date = new SimpleDateFormat("dd-MMM-yyyy").format(creationDate.getTime());
        }   
        if (dateOfUse != null) {
            date = new SimpleDateFormat("dd-MMM-yyyy").format(dateOfUse.getTime());
        }   
        return "VoucherDto [reference=" + reference + ", value=" + value + ", creationDate=" + date + ", dateOfUse=" + dateUse
                + "]";
    }

}
