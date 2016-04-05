package fr.univ.nantes.extensiblespud.parser;

import java.io.InputStream;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
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
