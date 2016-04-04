package fr.univ.nantes.extensiblespud.parser;

import java.io.InputStream;

/**
 *
 */
public interface Parser<T> {

    /**
     * @param inputStream
     * @return
     * @throws Exception
     */
    T parse(InputStream inputStream) throws Exception;

    /**
     * @return
     */
    String fileExtension();
}
