package es.upm.miw.repositories.core;

import es.upm.miw.repositories.core.memory.ComponentArticle;

public interface IComponentArticle {
    public void add(ComponentArticle componentArticle);

    public void delete(ComponentArticle componentArticle);

    public void view(ComponentArticle componentArticle);

    public void print(int nivel);

    public int count();
}
