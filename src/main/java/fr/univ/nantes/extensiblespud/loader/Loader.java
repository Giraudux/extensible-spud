package fr.univ.nantes.extensiblespud.loader;

/**
 *
 */
public interface Loader<T> {
    public T load(String path);
}
