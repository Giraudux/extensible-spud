package extension;

import fr.univ.nantes.extensiblespud.Platform;

public class AutorunExtension implements Extension<Object,Object> {

    public AutorunExtension() throws Exception {
        System.out.println(System.identityHashCode(this)+": AutorunExtension: constructor");
        ((ExtensionInteger) Platform.getInstance().loadExtension("extension.AccExtension")).run(null);
    }

    public Object run(Object o) {
        System.out.println(System.identityHashCode(this)+": AutorunExtension: run");
        return o;
    }
}
