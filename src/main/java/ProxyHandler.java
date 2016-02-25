import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {
    private Class<?> extensionClass;
    private Object extensionInstance;

    public ProxyHandler (Class<?> extensionClass, Object extensionInstance) {
        this.extensionClass = extensionClass;
        this.extensionInstance = extensionInstance;
    }

    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        if(extensionInstance == null) {
            extensionInstance = extensionClass.newInstance();
        }

        return method.invoke(extensionInstance, objects);
    }
}
