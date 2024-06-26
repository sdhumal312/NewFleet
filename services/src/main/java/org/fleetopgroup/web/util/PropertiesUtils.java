package org.fleetopgroup.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class PropertiesUtils {

	public static final String TRACE_ID = PropertiesUtils.class.getName();

	public static String getValueByName(String propertyFileName, String propertyName, String defaultValue)
			throws Exception {

		Properties		properties		= null;
		InputStream		in				= null;

		try {

			properties = new Properties();

			if (!FileUtility.isExist(propertyFileName)) {
				return defaultValue;
			}

			PropertiesUtils.loadFile(in, properties, propertyFileName);
			return getProperty(properties, propertyName, defaultValue);
		} catch (Exception e) {
			return defaultValue;
		} finally {
			properties = null;
			in = null;
		}
	}

	public static String getValueByName(String propertyFileName, String propertyName) throws Exception {
		try {
			return getValueByName(propertyFileName, propertyName, null);
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	public static void loadFile(InputStream in, Properties properties, String file) throws Exception {
		try {

			// in = PropertiesUtils.class.getResourceAsStream(file);
			in = PropertiesUtils.class.getClassLoader().getResourceAsStream(file);
			
			if(in == null){
				in = PropertiesUtils.class.getResourceAsStream(file);
			}
			
			if (in != null) {
				properties.load(in);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	private static String getProperty(Properties properties, String propertyName, String defaultValue) {
		return properties.getProperty(propertyName, defaultValue);
	}

	public static ArrayList<String> loadListData(Properties properties, ValueObject object,
			ArrayList<String> dataList) throws Exception {

		ArrayList<String>	notFoundList;//	= null;

		try {

			notFoundList = new ArrayList<String>();

			dataList.forEach((s) -> {
				if (PropertiesUtils.getProperty(properties, s, null) == null) {
					notFoundList.add(s);
				} else {
					try {
						object.put(s, PropertiesUtils.getProperty(properties, s, null));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			return notFoundList;
		} catch (Exception e) {
			throw e;
		} finally {
			//notFoundList = null;
		}
	}
}
