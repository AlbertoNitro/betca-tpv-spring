package es.upm.miw.services;

import java.util.List;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Voucher;
import es.upm.miw.documents.users.Token;
import es.upm.miw.documents.users.User;

public class TpvGraph {

    private List<User> userList;
    private List<Token> tokenList;
    
    private List<Provider> providerList;
    private List<Article> articleList;
    private List<Voucher> voucherList;
    private List<Ticket> ticketList;
    private List<Invoice> InvoiceList;
    
    public TpvGraph() {
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Invoice> getInvoiceList() {
        return InvoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        InvoiceList = invoiceList;
    }

}
