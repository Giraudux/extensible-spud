package fr.univ.nantes.extensiblespud.handler;

import fr.univ.nantes.extensiblespud.bean.HandlerBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public class HandlerManager implements InvocationHandler, Handler {
    private Collection<Handler> handlers__;
    private HandlerBean handlerBean__;

    /**
     *
     */
    public HandlerManager(HandlerBean handlerBean) {
        handlers__ = new ArrayList<Handler>();
        handlerBean__ = handlerBean;
    }

    /**
     * @param handlerManager
     */
    public HandlerManager(HandlerManager handlerManager) {
        handlers__ = handlerManager.handlers__;
        handlerBean__ = handlerManager.handlerBean__;
    }

    /**
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        for (Handler handler : handlers__) {
            for (Class<?> aClass : handler.getInterfaces()) {
                for (Method aMethod : aClass.getMethods()) {
                    if (method.equals(aMethod)) {
                        return handler.invoke(o, method, objects);
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param handlerBean
     */
    public void setHandlerBean(HandlerBean handlerBean) {
        handlerBean__ = handlerBean;
        for (Handler handler : handlers__) {
            handler.setHandlerBean(handlerBean);
        }
    }

    /**
     * @return
     */
    public Collection<Class<?>> getInterfaces() {
        Collection<Class<?>> interfaces = new ArrayList<Class<?>>();

        for (Handler handler : handlers__) {
            interfaces.addAll(handler.getInterfaces());
        }

        return interfaces;
    }

    /**
     * @param handler
     * @return
     */
    public boolean add(Handler handler) {
        handler.setHandlerBean(handlerBean__);
        return handlers__.add(handler);
    }

    /**
     * @param handler
     * @return
     */
    public boolean remove(Handler handler) {
        handler.setHandlerBean(null);
        return handlers__.remove(handler);
    }
}
