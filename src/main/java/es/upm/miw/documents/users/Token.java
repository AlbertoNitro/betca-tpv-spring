package es.upm.miw.documents.users;

import java.util.Date;

public class Token {

    private String value;

    private Date creationDate;

    public Token() {
        this.value = new Encrypting().encryptInBase64UrlSafe("miw.tpv" + Long.toString(new Date().getTime()));
        this.creationDate = new Date();
    }

    public String getValue() {
        return value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
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
        return value.equals(((Token) obj).value);
    }

    @Override
    public String toString() {
        return "Token [value=" + value + ", expirationDate=" + creationDate.toString() + "]";
    }
}
