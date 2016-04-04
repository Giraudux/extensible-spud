package fr.univ.nantes.extensiblespud.parser;

import fr.univ.nantes.extensiblespud.bean.DescriptionBean;

/**
 *
 */
public class DescriptionPropertiesParser extends PropertiesParser<DescriptionBean> implements DescriptionParser {

    /**
     *
     */
    public DescriptionPropertiesParser() {
        super(DescriptionBean.class);
    }
}
