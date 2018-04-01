package es.upm.miw.repositories.core.memory;

import es.upm.miw.documents.core.Article;

public class ArticleLeaf extends ComponentArticle {
    private Article article;

    public ArticleLeaf(Article article) {
        super();
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "" + article.getReference() + "";
    }

    @Override
    public void view(ComponentArticle componentArticle) {

        System.out.println(componentArticle.toString());
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public void print(int nivel) {
        String nnivel = "";
        for (int i = 0; i < nivel; i++) {
            nnivel += "-";
        }
        System.out.println(nnivel + "" + article.getReference());
    }

    @Override
    public void add(ComponentArticle componentArticle) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public boolean isComposite() {
        return false;
    }

}
