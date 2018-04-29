package es.upm.miw.services;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.documents.core.Article;
import es.upm.miw.documents.core.CashMovement;
import es.upm.miw.documents.core.CashierClosure;
import es.upm.miw.documents.core.FamilyArticle;
import es.upm.miw.documents.core.FamilyComposite;
import es.upm.miw.documents.core.Invoice;
import es.upm.miw.documents.core.Provider;
import es.upm.miw.documents.core.Shopping;
import es.upm.miw.documents.core.Ticket;
import es.upm.miw.documents.core.Token;
import es.upm.miw.documents.core.User;
import es.upm.miw.documents.core.Voucher;

public class DatabaseGraph {

    private List<Article> articleList;

    private List<CashMovement> cashMovementList;

    private List<CashierClosure> cashierClosureList;

    private List<FamilyArticle> familyArticleList;

    private List<FamilyComposite> familyCompositeList;

    private List<Invoice> invoiceList;

    private List<Provider> providerList;

    private List<Ticket> ticketList;

    private List<Token> tokenList;

    private List<Shopping> shoppingList;

    private List<User> userList;

    private List<Voucher> voucherList;

    public DatabaseGraph() {
        this.articleList = new ArrayList<>();
        this.cashierClosureList = new ArrayList<>();
        this.cashMovementList = new ArrayList<>();
        this.familyArticleList = new ArrayList<>();
        this.familyCompositeList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
        this.providerList = new ArrayList<>();
        this.shoppingList = new ArrayList<>();
        this.ticketList = new ArrayList<>();
        this.tokenList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.voucherList = new ArrayList<>();
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    public List<CashMovement> getCashMovementList() {
        return cashMovementList;
    }

    public void setCashMovementList(List<CashMovement> cashMovementList) {
        this.cashMovementList = cashMovementList;
    }

    public List<CashierClosure> getCashierClosureList() {
        return cashierClosureList;
    }

    public void setCashierClosureList(List<CashierClosure> cashierClosureList) {
        this.cashierClosureList = cashierClosureList;
    }

    public List<FamilyArticle> getFamilyArticleList() {
        return familyArticleList;
    }

    public void setFamilyArticleList(List<FamilyArticle> familyArticleList) {
        this.familyArticleList = familyArticleList;
    }

    public List<FamilyComposite> getFamilyCompositeList() {
        return familyCompositeList;
    }

    public void setFamilyCompositeList(List<FamilyComposite> familyCompositeList) {
        this.familyCompositeList = familyCompositeList;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<Token> getTokenList() {
        return tokenList;
    }

    public void setTokenList(List<Token> tokenList) {
        this.tokenList = tokenList;
    }

    public List<Shopping> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<Shopping> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }

}
