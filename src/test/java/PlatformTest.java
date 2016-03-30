import extension.Extension;
import fr.univ.nantes.extensiblespud.Platform;
import fr.univ.nantes.extensiblespud.bean.ConfigurationBean;
import fr.univ.nantes.extensiblespud.bean.DescriptionBean;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PlatformTest {
    @Test
    public void test0() throws Exception {
        ConfigurationBean configuration = new ConfigurationBean();
        configuration.setClassPath("file://target/test-classes");
        configuration.setDescPath("src/test/resources");
        Platform.setConfiguration(configuration);

        for(Map.Entry<String,DescriptionBean> entry: Platform.getInstance().getDescriptions().entrySet()) {
            if(entry.getValue().getSingleton()) {
                Extension extension0 = (Extension) Platform.getInstance().loadExtension(entry.getKey());
                Extension extension1 = (Extension) Platform.getInstance().loadExtension(entry.getKey());
                assertEquals(System.identityHashCode(extension0), System.identityHashCode(extension1));
                extension0.run(null);
                extension1.run(null);
            }
        }
    }

    @Test
    public void test1() throws Exception {
        ;
    }
}
