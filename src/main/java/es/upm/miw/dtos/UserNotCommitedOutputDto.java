package es.upm.miw.dtos;

public class UserNotCommitedOutputDto extends UserMinimumDto {

    private String TicketId;
    
    private String articles;

    private Boolean allEntry;

    public UserNotCommitedOutputDto() {
        this.articles = "";
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public Boolean getAllEntry() {
        return allEntry;
    }

    public void setAllEntry(Boolean allEntry) {
        this.allEntry = allEntry;
    }

    public String getTicketId() {
        return TicketId;
    }

    public void setTicketId(String ticketId) {
        TicketId = ticketId;
    }

    public void addArticle(String description) {
        this.articles += description + ". ";

    }

    @Override
    public String toString() {
        return "UserNotCommited [TicketId=" + TicketId + ", articles=" + articles + ", allEntry=" + allEntry + "]";
    }

}
