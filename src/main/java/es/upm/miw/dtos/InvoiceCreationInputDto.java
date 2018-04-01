package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;

public class InvoiceCreationInputDto {
    
    @NotNull
    private String mobile;
    
    @NotNull
    private String ticketId;

    public InvoiceCreationInputDto() {
    }

    public InvoiceCreationInputDto(String mobile, String ticketId) {
        this.mobile = mobile;
        this.ticketId = ticketId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "InvoiceCreationInputDto [mobile=" + mobile + ", ticketId=" + ticketId + "]";
    }

}
