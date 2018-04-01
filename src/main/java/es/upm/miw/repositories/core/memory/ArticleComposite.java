package es.upm.miw.repositories.core.memory;

import java.util.ArrayList;
import java.util.List;

public class ArticleComposite extends ComponentArticle {

    private String reference;

    List<ComponentArticle> listComponentArticle = new ArrayList<ComponentArticle>();

    public ArticleComposite() {
        super();
    }

    public ArticleComposite(String reference) {
        super();
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<ComponentArticle> getListComponentArticle() {
        return listComponentArticle;
    }

    public void setListComponentArticle(List<ComponentArticle> listComponentArticle) {
        this.listComponentArticle = listComponentArticle;
    }

    public int count() {
        return listComponentArticle.size();
    }

    @Override
    public void add(ComponentArticle componentArticle) {
        listComponentArticle.add(componentArticle);

    }

    @Override
    public void delete(ComponentArticle componentArticle) {
        listComponentArticle.remove(componentArticle);
    }

    @Override
    public List<ComponentArticle> getAllComponents() {
        return getListComponentArticle();
    }

    @Override
    public void view(ComponentArticle _componentArticle) {
        for (ComponentArticle componentArticle : listComponentArticle) {
            System.out.println(componentArticle.toString());
        }
    }

    @Override
    public void print(int nivel) {
        String nnivel = "";
        for (int i = 0; i < nivel; i++) {
            nnivel += "-";
        }
        System.out.println(nnivel + this.reference);
        for (ComponentArticle componentArticle : listComponentArticle) {
            componentArticle.print(nivel + 1);
        }
    }

    @Override
    public boolean isComposite() {
        return true;
    }

}
