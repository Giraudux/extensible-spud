package fr.univ.nantes.extensiblespud.parser;

import java.io.InputStream;

/**
 *
 */
public interface Parser<T> {
    T parse(InputStream inputStream, Class<T> tClass) throws Exception;
}
