package es.upm.miw.documents.core;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import es.upm.miw.utils.Encrypting;

@Document
public class Voucher {

    @Id
    private String reference;

    private BigDecimal value;

    private Date creationDate;

    private Date dateOfUse;

    public Voucher() {
        this(new BigDecimal(0));
    }

    public Voucher(BigDecimal value) {
        this.reference = new Encrypting().encryptInBase64UrlSafe();
        this.creationDate = new Date();
        this.dateOfUse = null;
        this.setValue(value);
    }

    public String getReference() {
        return reference;
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

    public Date getDateOfUse() {
        return dateOfUse;
    }

    public void setDateOfUse(Date dateOfUse) {
        this.dateOfUse = dateOfUse;
    }

    public void use() {
        assert !this.isUsed();
        dateOfUse = new Date();
    }

    public boolean isUsed() {
        return dateOfUse != null;
    }

    @Override
    public int hashCode() {
        return reference.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return reference == ((Voucher) obj).reference;
    }

    @Override
    public String toString() {
        String creationTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(creationDate.getTime());
        String useTime;
        if (this.isUsed()) {
            useTime = new SimpleDateFormat("HH:00 dd-MMM-yyyy ").format(dateOfUse.getTime());
        } else {
            useTime = "---";
        }
        return "Voucher [reference=" + reference + ", value=" + value.doubleValue() + ", creationDate=" + creationTime + ", dateOfUse=" + useTime + "]";
    }

}
