package es.upm.miw.dtos;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.CashierClosure;

public class CashierLastOutputDto {

    private Boolean closed;

    @JsonInclude(Include.NON_NULL)
    private Date closureDate;

    private BigDecimal finalCash;

    public CashierLastOutputDto() {
        // Empty for framework
    }

    public CashierLastOutputDto(Boolean closed, Date closureDate, BigDecimal finalCash) {
        super();
        this.closed = closed;
        this.closureDate = closureDate;
        this.finalCash = finalCash;
    }

    public CashierLastOutputDto(CashierClosure cashierClosure) {
        this(cashierClosure.isClosed(), cashierClosure.getClosureDate(),cashierClosure.getFinalCash());
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Date getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Date closureDate) {
        this.closureDate = closureDate;
    }

    public BigDecimal getFinalCash() {
        return finalCash;
    }

    public void setFinalCash(BigDecimal finalCash) {
        this.finalCash = finalCash;
    }

    @Override
    public String toString() {
        return "CashierClosureLastOutputDto [closed=" + closed + ", closureDate=" + closureDate + ", finalCash=" + finalCash + "]";
    }

}
