package es.upm.miw.dtos;

public class UserNotCommitedOutputDto extends UserMinimumDto {

    private String TicketId;

    private String articlesEntry;

    private String articlesNotEntry;

    public UserNotCommitedOutputDto() {
        this.articlesEntry = "";
        this.articlesNotEntry = "";
    }

    public String getArticlesEntry() {
        return articlesEntry;
    }

    public void setArticlesEntry(String articlesEntry) {
        this.articlesEntry = articlesEntry;
    }

    public String getArticlesNotEntry() {
        return articlesNotEntry;
    }

    public void setArticlesNotEntry(String articlesNotEntry) {
        this.articlesNotEntry = articlesNotEntry;
    }

    public String getTicketId() {
        return TicketId;
    }

    public void setTicketId(String ticketId) {
        TicketId = ticketId;
    }

    public void addArticleEntry(String description) {
        this.articlesEntry += description + ".  ";

    }

    public void addArticleNotEntry(String description) {
        this.articlesNotEntry += description + ".  ";
    }

    @Override
    public String toString() {
        return "UserNotCommitedOutputDto [TicketId=" + TicketId + ", articlesEntry=" + articlesEntry + ", articlesNotEntry="
                + articlesNotEntry + "]";
    }

}
