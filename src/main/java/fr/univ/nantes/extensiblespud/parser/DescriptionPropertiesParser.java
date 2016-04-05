package fr.univ.nantes.extensiblespud.parser;

import fr.univ.nantes.extensiblespud.bean.DescriptionBean;

/**
 * @author Nina Exposito
 * @author Alexis Giraudet
 * @author Jean-Christophe Guérin
 * @author Jasone Lenormand
 */
public class DescriptionPropertiesParser extends PropertiesParser<DescriptionBean> implements DescriptionParser {

    /**
     *
     */
    public DescriptionPropertiesParser() {
        super(DescriptionBean.class);
    }
}
