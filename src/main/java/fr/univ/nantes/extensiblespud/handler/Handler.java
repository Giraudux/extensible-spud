package fr.univ.nantes.extensiblespud.handler;

import fr.univ.nantes.extensiblespud.bean.HandlerBean;

import java.lang.reflect.InvocationHandler;
import java.util.Collection;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public interface Handler extends InvocationHandler {

    /**
     * @param handlerBean
     */
    void setHandlerBean(HandlerBean handlerBean);

    /**
     * @return
     */
    Collection<Class<?>> getInterfaces();
}
