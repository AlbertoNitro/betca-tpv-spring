package es.upm.miw.repositories.core.memory;

import java.util.List;

public abstract class ComponentArticle implements IComponentArticle {

    @Override
    public void add(ComponentArticle componentArticle) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public void delete(ComponentArticle componentArticle) {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public List<ComponentArticle> getAllComponents() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Operación no soportada");
    }

    public abstract boolean isComposite();

    public abstract void view(ComponentArticle componentArticle);

    public abstract void print(int nivel);;

}
