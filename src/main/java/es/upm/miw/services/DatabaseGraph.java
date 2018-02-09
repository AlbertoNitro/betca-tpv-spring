package es.upm.miw.services;

import java.util.List;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Token;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;

public class DatabaseGraph {

    private List<User> userList;

    private List<Token> tokenList;

    private List<Provider> providerList;

    private List<Article> articleList;

    private List<Voucher> voucherList;

    private List<Ticket> ticketList;

    private List<Shopping> shoppingList;

    private List<Invoice> invoiceList;
    
    private List<CashierClosure> cashierClosureList;

    public DatabaseGraph() {
        // Empty for framework
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

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Shopping> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<CashierClosure> getCashierClosureList() {
        return cashierClosureList;
    }

    public void setCashierClosureList(List<CashierClosure> cashierClosureList) {
        this.cashierClosureList = cashierClosureList;
    }

}
