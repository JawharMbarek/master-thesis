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

import de.m0ep.socc.config.IConnectorConfig;

public class ConfigUtils {
    private static final Logger LOG = LoggerFactory
	    .getLogger(ConfigUtils.class);

    public static String[] getParameterNames(
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
	    Map<String, Object> paramerers, T configDefault) {
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
