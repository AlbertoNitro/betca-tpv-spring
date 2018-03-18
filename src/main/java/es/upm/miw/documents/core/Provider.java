package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Provider {

    @Id
    private String id;

    private String company;

    private String address;

    private String mobile;

    private String phone;

    private String note;
    
    private Boolean active;

    public Provider() {
        // Empty for framework
    }
    
    public Provider(String id, String company, String address, String mobile, String phone, String note, Boolean active) {
        super();
        this.id = id;
        this.company = company;
        this.address = address;
        this.mobile = mobile;
        this.phone = phone;
        this.note = note;
        this.active = active;
    }
    
    public Provider(String company, String address, String mobile, String phone, String note, Boolean active) {
        super();
        this.company = company;
        this.address = address;
        this.mobile = mobile;
        this.phone = phone;
        this.note = note;
        this.active = active;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
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
        return (id.equals(((Provider) obj).id));
    }

    @Override
    public String toString() {
        return "Provider[" + id + ": company=" + company + ", address=" + address + ", mobile=" + mobile + ", phone=" + phone + ", note="
                + note + ", active=" + active + "]";
    }

}
