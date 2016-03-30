package extension;

public class DefaultExtension implements Extension {

    public DefaultExtension() {
        System.out.println(System.identityHashCode(this)+": DefaultExtension: run");
    }

    public Object run(Object o) {
        System.out.println(System.identityHashCode(this)+": DefaultExtension: run");
        return o;
    }
}
