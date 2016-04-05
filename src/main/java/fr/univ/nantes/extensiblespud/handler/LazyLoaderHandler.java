package fr.univ.nantes.extensiblespud.handler;

import fr.univ.nantes.extensiblespud.bean.HandlerBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 */
public class LazyLoaderHandler implements Handler {
    private HandlerBean handlerBean__;
    private Class<?> aCLass__;

    /**
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (handlerBean__.getInstance() == null) {
            handlerBean__.setInstance(aCLass__.newInstance());
        }

        return method.invoke(handlerBean__.getInstance(), objects);
    }

    /**
     * @param handlerBean
     */
    public void setHandlerBean(HandlerBean handlerBean) {
        handlerBean__ = handlerBean;
        try {
            aCLass__ = Class.forName(handlerBean.getDescription().getName(), true, handlerBean.getClassLoader());
        } catch (Exception e) {
            handlerBean__.getStatus().setLoadingFailed(true);
            handlerBean__.getStatus().setLastException(e);
        }
    }

    /**
     * @return
     */
    public Collection<Class<?>> getInterfaces() {
        Collection<Class<?>> interfaces = new ArrayList<Class<?>>();

        try {
            interfaces.addAll(Arrays.asList(aCLass__.getInterfaces()));
        } catch (Exception e) {
            handlerBean__.getStatus().setLoadingFailed(true);
            handlerBean__.getStatus().setLastException(e);
        }

        return interfaces;
    }
}
