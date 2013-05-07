/*
 * The MIT License (MIT) Copyright © 2013 Florian Müller
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the “Software”), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.m0ep.socc.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import de.m0ep.socc.config.IConnectorConfig;

/**
 * Some utility methods to handle JavaBean configuration classes.
 * 
 * @author Florian Müller
 */
public class ConfigUtils {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConfigUtils.class);

    /*
     * Private constructor to avoid creating objects from this class.
     */
    private ConfigUtils() {
    }

    /**
     * Return all names of properties handled by this bean.
     * 
     * @param clazz
     *            {@link Class} of the bean.
     * @return All propertynames from <i>clazz</i>
     * 
     * @throws NullPointerException
     *             Thrown if <i>clazz</i> is null.
     */
    public static String[] getPropertyNames(
	    Class<? extends IConnectorConfig> clazz) {
	Preconditions.checkNotNull(clazz, "Calzz can not be null.");
	List<String> result = new ArrayList<String>();

	PropertyDescriptor[] properties = PropertyUtils
		.getPropertyDescriptors(clazz);

	for (PropertyDescriptor pd : properties) {
	    // irgnore getClass()
	    if (pd.getName().equalsIgnoreCase("class")) {
		continue;
	    }

	    result.add(pd.getName());
	}

	return result.toArray(new String[result.size()]);
    }

    /**
     * Convert a {@link Map} to a {@link IConnectorConfig} using
     * <i>configDefault</i> with provided default values.
     * 
     * @param paramerers
     *            {@link Map} with values for some properties
     * @param configDefault
     *            {@link IConnectorConfig} with default values to fill in the
     *            <i>parameters</i> {@link Map}, should not be null.
     * @return <i>configDefault</i> with inserted propertyvalues from
     *         <i>parameters</i>
     * 
     * @throws NullPointerException
     *             Thrown if <i>parameters</i> and/or <i>configDefault</i> are
     *             null.
     */
    public static <T extends IConnectorConfig> T fromMap(
	    Map<String, Object> paramerers, T configDefault) {
	Preconditions.checkNotNull(paramerers, "Parameters can not be null.");
	Preconditions.checkNotNull(
		configDefault,
		"ConfigDefault can not be null.");

	try {
	    BeanUtils.populate(configDefault, paramerers);
	    return configDefault;
	} catch (Exception e) {
	    String error = "failed to convert Map to "
		    + configDefault.getClass().getName();
	    LOG.error(error, e);
	    throw new RuntimeException(error, e);
	}
    }

    /**
     * Convert a {@link IConnectorConfig} JavaBean to a {@link Map}
     * 
     * @param config
     *            {@link IConnectorConfig} to convert
     * @return Map of <i>config</i> bean
     * 
     * @throws NullPointerException
     *             Thrown if config is null.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(IConnectorConfig config) {
	Preconditions.checkNotNull(config, "Config can not be null.");
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
