package es.upm.miw.repositories.core.memory;

import java.util.ArrayList;
import java.util.List;

public class ArticleComposite extends ComponentArticle {

    List<ComponentArticle> listComponentArticle = new ArrayList<ComponentArticle>();

    public ArticleComposite(String name) {
        super(name);
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
    public void view(ComponentArticle _componentArticle) {
        for (ComponentArticle componentArticle : listComponentArticle) {
            System.out.println(componentArticle.toString());
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public void print(int nivel) {
        String nnivel = "";
        for (int i = 0; i < nivel; i++) {
            nnivel += "-";
        }

        System.out.println(nnivel + super.getName());
        for (ComponentArticle componentArticle : listComponentArticle) {
            componentArticle.print(nivel + 1);
        }

    }

}
