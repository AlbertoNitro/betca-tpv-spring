package es.upm.miw.repositories.core.memory;

import java.util.List;

public interface IComponentArticle {
    public void add(ComponentArticle componentArticle);

    public void delete(ComponentArticle componentArticle);

    public void view(ComponentArticle componentArticle);

    public List<ComponentArticle> getAllComponents();

    public void print(int nivel);

    public boolean isComposite();

    public int count();
}
