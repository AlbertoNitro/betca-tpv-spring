package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.Provider;

@JsonInclude(Include.NON_NULL)
public class ProviderDto extends ProviderMinimumDto {

    private String address;

    private String mobile;

    private String phone;

    private String note;
    
    private Boolean active;

    public ProviderDto() {
        // Empty for framework
    }

    public ProviderDto(String id, String company, String address, String mobile, String phone, String note, Boolean active) {
        super(id, company);
        this.address = address;
        this.mobile = mobile;
        this.phone = phone;
        this.note = note;
        this.active = active;
    }

    public ProviderDto(Provider provider) {
        super(provider.getId(), provider.getCompany());
        this.address = provider.getAddress();
        this.mobile = provider.getMobile();
        this.phone = provider.getPhone();
        this.note = provider.getNote();
        this.active = provider.isActive();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "[" + super.toString() + "ProviderDto [address=" + address + ", mobile=" + mobile + ", phone=" + phone + ", address=" + address
                + ", note=" + note + ", active=" + active + "] ]";
    }

}
