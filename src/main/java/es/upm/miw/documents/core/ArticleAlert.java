package es.upm.miw.documents.core;

import org.springframework.data.annotation.Id;

public class ArticleAlert {

    @Id
    private String code;
    
    private Integer warningStock;
    
    private Integer criticalStock;
    
    private Article article;

    public ArticleAlert() {
        this.criticalStock = 0;
        this.warningStock = 0;
    }

    public ArticleAlert(String code, Integer warning, Integer critical, Article article) {
        this();
        this.code = code;
        this.criticalStock = critical;
        this.warningStock = warning;
        this.setArticle(article);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public Integer getWarningStock() {
		return warningStock;
	}

	public void setWarningStock(Integer warningStock) {
		this.warningStock = warningStock;
	}

	public Integer getCriticalStock() {
		return criticalStock;
	}

	public void setCriticalStock(Integer criticalStock) {
		this.criticalStock = criticalStock;
	}
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
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
        return (code.equals(((ArticleAlert) obj).code));
    }

    @Override
    public String toString() {
        return "Article alert [code=" + code + ", warning stock=" + warningStock + ", critical stock=" + criticalStock + "]";
    }
}
