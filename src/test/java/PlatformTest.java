import extension.Extension;
import extension.ExtensionInteger;
import fr.univ.nantes.extensiblespud.Platform;
import fr.univ.nantes.extensiblespud.bean.ConfigurationBean;
import fr.univ.nantes.extensiblespud.bean.DescriptionBean;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PlatformTest {

    @BeforeClass
    public static void init() throws Exception {
        ConfigurationBean configuration = new ConfigurationBean();
        configuration.setClassPath("file://target/test-classes");
        configuration.setDescriptionPath("src/test/resources");
        Platform.setConfiguration(configuration);
    }

    @Test
    public void testAutoRun() throws Exception {
        Platform.getInstance().autorun();
        ExtensionInteger extension = (ExtensionInteger) Platform.getInstance().loadExtension("extension.AccExtension");
        assertEquals((int) extension.run(null), 2);
    }

    @Test
    public void testSingleton() throws Exception {
        for(Map.Entry<String,DescriptionBean> entry: Platform.getInstance().getContributors(Extension.class.getName()).entrySet()) {
            if(entry.getValue().getSingleton()) {
                Extension extension0 = (Extension) Platform.getInstance().loadExtension(entry.getKey());
                Extension extension1 = (Extension) Platform.getInstance().loadExtension(entry.getKey());
                assertEquals(System.identityHashCode(extension0), System.identityHashCode(extension1));
                extension0.run(null);
                extension1.run(null);
            }
        }
    }
}
