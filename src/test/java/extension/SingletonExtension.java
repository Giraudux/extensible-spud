package extension;

public class SingletonExtension implements Extension<Object,Object> {
    public SingletonExtension() {
        System.out.println(System.identityHashCode(this)+": SingletonExtension: constructor");
    }

    public Object run(Object o) {
        System.out.println(System.identityHashCode(this)+": AutorunExtension: run");
        return o;
    }
}
