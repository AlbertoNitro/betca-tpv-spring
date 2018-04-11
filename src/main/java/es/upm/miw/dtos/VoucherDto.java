package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Voucher;
import es.upm.miw.dtos.validations.BigDecimalPositive;

@JsonInclude(Include.NON_NULL)
public class VoucherDto {

    private String id;

    @BigDecimalPositive
    private BigDecimal value;

    private Date creationDate = null;

    private Date dateOfUse = null;

    public VoucherDto() {
        // Empty for framework
    }

    public VoucherDto(BigDecimal value) {
        this.value = value;
    }

    public VoucherDto(Voucher voucher) {
        this.id = voucher.getId();
        this.value = voucher.getValue();
        this.creationDate = voucher.getCreationDate();
        this.dateOfUse = voucher.getDateOfUse();
    }

    public VoucherDto minimumDto(Voucher voucher) {
        this.id = voucher.getId();
        this.value = voucher.getValue();
        return this;
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

    public String getId() {
        return id;
    }

    public void setId(String reference) {
        this.id = reference;
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
        return "VoucherDto [reference=" + id + ", value=" + value + ", creationDate=" + date + ", dateOfUse=" + dateUse + "]";
    }

}
