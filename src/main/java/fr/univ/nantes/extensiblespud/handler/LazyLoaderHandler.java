package fr.univ.nantes.extensiblespud.handler;

import java.lang.reflect.Method;

/**
 *
 */
public class LazyLoaderHandler implements Handler {
    private Class<?> extensionClass;
    private Object extensionInstance;

    /**
     * @param extensionClass
     * @param extensionInstance
     */
    public LazyLoaderHandler(Class<?> extensionClass, Object extensionInstance) {
        this.extensionClass = extensionClass;
        this.extensionInstance = extensionInstance;
    }

    /**
     * @param o
     * @param method
     * @param objects
     * @return
     * @throws Throwable
     */
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if (extensionInstance == null) {
            extensionInstance = extensionClass.newInstance();
        }

        return method.invoke(extensionInstance, objects);
    }
}
