package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Provider {

    @Id
    private String id;

    @Indexed(unique = true)
    private String company;

    private String nif;

    private String address;

    private String phone;

    private String email;

    private String note;

    private Boolean active;

    public Provider() {
        this.active = true;
    }

    public Provider(String company, String nif, String address, String phone, String email, String note) {
        this();
        this.company = company;
        this.nif = nif;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return this.company.hashCode();
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
        return (company.equals(((Provider) obj).company));
    }

    @Override
    public String toString() {
        return "Provider [id=" + id + ", company=" + company + ", nif=" + nif + ", address=" + address + ", phone=" + phone + ", email="
                + email + ", note=" + note + ", active=" + active + "]";
    }

}
