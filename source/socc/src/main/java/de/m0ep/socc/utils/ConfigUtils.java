package de.m0ep.socc.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.LocaleBeanUtils;

import de.m0ep.socc.connectors.AbstractConnectorConfig;
import de.m0ep.socc.connectors.ConnectorConfig;

public class ConfigUtils {
    public static String[] getPropertyNames(
	    Class<? extends ConnectorConfig> clazz) {
	List<String> result = new ArrayList<String>();

	PropertyDescriptor[] properties = PropertyUtils
		.getPropertyDescriptors(clazz);

	for (PropertyDescriptor pd : properties) {
	    // irgnore getClass()
	    if (pd.getName().equalsIgnoreCase("class"))
		continue;

	    result.add(pd.getName());
	}

	return result.toArray(new String[result.size()]);
    }

    /**
     * Maps a configuration {@link Map} to a {@link ConnectorConfig} bean
     * 
     * @param bean
     *            The bean, where the configuration {@link Map} schould be
     *            mapped.
     * @param config
     *            The configuration {@link Map}
     */
    public static void setProperties(AbstractConnectorConfig bean,
	    Map<String, Object> parameters) {
	if (null == bean || null == parameters)
	    return;

	try {
	    LocaleBeanUtils.populate(bean, parameters);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
