package de.m0ep.socc.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.m0ep.socc.connectors.IConnectorConfig;

public class ConfigUtils {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConfigUtils.class);

    public static String[] getPropertyNames(
	    Class<? extends IConnectorConfig> clazz) {
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

    public static <T extends IConnectorConfig> T fromMap(
	    Map<String, Object> paramerers, Class<T> beanClass) {
	try {
	    T beanConfig = beanClass.newInstance();
	    LocaleBeanUtils.populate(beanConfig, paramerers);
	    return beanConfig;
	} catch (Exception e) {
	    String error = "failed to convert Map to " + beanClass.getName();
	    LOG.error(error, e);
	    throw new RuntimeException(error, e);
	}
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(IConnectorConfig config) {
	try {
	    return LocaleBeanUtils.describe(config);
	} catch (Exception e) {
	    String error = "failed to convert " + config.getClass().getName()
		    + " to Map";
	    LOG.error(error);
	    throw new RuntimeException(error, e);
	}
    }
}
