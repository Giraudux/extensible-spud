package extension;

public class AccExtension implements ExtensionInteger {
    Integer acc;

    public AccExtension() {
        acc = 0;
    }

    public Integer run(Integer integer) {
        acc++;
        return acc;
    }
}
