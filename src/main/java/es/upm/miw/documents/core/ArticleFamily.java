package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ArticleFamily {
    @Id
    private String code;

    private String idhijo;

    private String idpadre;

    private String reference;

    public ArticleFamily() {
        super();
    }

    public ArticleFamily(String idhijo, String idpadre, String reference) {
        super();
        this.idhijo = idhijo;
        this.idpadre = idpadre;
        this.reference = reference;
    }

    public String getCode() {
        return code;
    }

    public String getIdhijo() {
        return idhijo;
    }

    public void setIdhijo(String idhijo) {
        this.idhijo = idhijo;
    }

    public String getIdpadre() {
        return idpadre;
    }

    public void setIdpadre(String idpadre) {
        this.idpadre = idpadre;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
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
        return (code.equals(((ArticleFamily) obj).code));
    }

    public ArticleFamily build() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("ArticleFamily [idhijo=%s, idpadre=%s, reference=%s]", idhijo, idpadre, reference);
    }
    
   

}
