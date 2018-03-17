package es.upm.miw.repositories.core.memory;

import es.upm.miw.repositories.core.IComponentArticle;

public abstract class ComponentArticle implements IComponentArticle {

    private String name;

    public ComponentArticle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void add(ComponentArticle componentArticle) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public void delete(ComponentArticle componentArticle) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    public abstract void view(ComponentArticle componentArticle);

    public abstract void print(int nivel);

}
