package fr.univ.nantes.extensiblespud.loader;

import java.io.InputStream;

/**
 *
 */
public interface Loader<T> {
    T load(InputStream inputStream, Class<T> tClass) throws Exception;
}
