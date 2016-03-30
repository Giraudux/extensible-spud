package extension;

public class AutorunExtension implements Extension {

    public AutorunExtension() {
        System.out.println(System.identityHashCode(this)+": AutorunExtension: constructor");
    }

    public Object run(Object o) {
        System.out.println(System.identityHashCode(this)+": AutorunExtension: run");
        return o;
    }
}
