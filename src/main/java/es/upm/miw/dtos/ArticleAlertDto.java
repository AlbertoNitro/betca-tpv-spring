package es.upm.miw.dtos;


import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.ArticleAlert;

public class ArticleAlertDto {
	
	    private String code;
	    
	    private Integer warningStock;
	    
	    private Integer criticalStock;
	    
	    private Article article;

	    public ArticleAlertDto() {
	        this.criticalStock = 0;
	        this.warningStock = 0;
	    }

	    public ArticleAlertDto(String code, Integer warning, Integer critical, Article article) {
	        this();
	        this.code = code;
	        this.criticalStock = critical;
	        this.warningStock = warning;
	        this.setArticle(article);
	    }
	    
	    public ArticleAlertDto(ArticleAlert articleAlert) {
	        this.article = articleAlert.getArticle();
	        this.warningStock = articleAlert.getWarningStock();
	        this.criticalStock = articleAlert.getCriticalStock();
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
	        return (code.equals(((ArticleAlertDto) obj).code));
	    }

	    @Override
	    public String toString() {
	        return "Article alert [code=" + code + ", warning stock=" + warningStock + ", critical stock=" + criticalStock + "]";
	    }
}
