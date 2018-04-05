package es.upm.miw.documents.core;

import java.util.Date;

import es.upm.miw.utils.Encrypting;

public class Token {

    private String value;

    private Date creationDate;
    
    private long lifetime = 86400000; //By default, token lifetime is set to 24H

    public Token() {
        this.setValue(new Encrypting().encryptInBase64UrlSafe());
    }

    public String getValue() {
        return value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setValue(String value) {
        this.value = value;
        this.creationDate = new Date();
    }
    
    public void setLifetime(long lifetime) {
    		this.lifetime = lifetime;
    }
    
    public long getLifetime() {
		return lifetime;
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
        return "Token [value=" + value + ", creationDate=" + creationDate.toString() + "]";
    }
}
