package es.upm.miw.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.upm.miw.documents.core.ArticleAlert;
import es.upm.miw.documents.core.StockAlert;

@JsonInclude(Include.NON_NULL)
public class StockAlertDto extends StockAlertMinimumDto {
    
    private List<ArticleAlert> articles;
    
    public StockAlertDto() {
        // Empty for framework
    }

    public StockAlertDto(String name, List<ArticleAlert> articles) {
        super(name);
        this.articles = articles;
    }

    public StockAlertDto(StockAlert stockAlert) {
        super(stockAlert.getName());
        this.articles = stockAlert.getArticles();
    }

    public List<ArticleAlert> getArticles() {
        return this.articles;
    }

    public void setArticles(List<ArticleAlert> articles) {
        this.articles = articles;
    }

	public String toString() {
        return "[" + super.toString() + "UserDto [articles=" + articles.toString() + "] ]";
    }
	
}