package org.fleetopgroup.web.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConfigurationUtilityBll {
	
	public static final String 	FILE_PATH					= "filePath";
	private static final String COLUMN_CONFIGURATION		= "columnConfiguration";
	private static final String TABLE_PROPERTIES			= "tableProperties";
	private static final String INITIAL_FILE_PATH			= "configuration/";
	private static final String DEFAULT_FILE_PATH			= "configuration/default";

	public static ValueObject getConfiguration(ValueObject valueObject) throws Exception {
		String					filePath						= null;
		String					companyId						= null;
		String					jsonFile						= null;
		HashMap<Object, Object>	defaultConfigurationObject		= null;
		HashMap<Object, Object>	groupConfigurationObject		= null;

		try {
			filePath	= getFullFilePathToJson(companyId, valueObject.getString(FILE_PATH));
			jsonFile	= FileUtility.readFile(filePath);

			defaultConfigurationObject		= JsonConvertor.toValueObjectFromTreeJsonString(jsonFile).getHtData();

			companyId	= valueObject.getString("companyId",null);
			
			filePath		= getFullFilePathToJson(companyId, valueObject.getString(FILE_PATH));

			
			jsonFile	= FileUtility.readFile(filePath);

			if (jsonFile == null) {
				return new ValueObject(defaultConfigurationObject);
			}

			groupConfigurationObject		= JsonConvertor.toValueObjectFromTreeJsonString(jsonFile).getHtData();

			return new ValueObject(getConfigurationFromDefaultAndGroupObject(defaultConfigurationObject, groupConfigurationObject));
		} catch (Exception e) {
			throw e;
		} finally {
			filePath					= null;
			companyId					= null;
			jsonFile					= null;
			defaultConfigurationObject	= null;
			groupConfigurationObject	= null;
		}
	}

private static String getFullFilePathToJson(String companyId, String filePath) throws Exception {
	String		fullFilePathToJson		= null;
	try {
		if (companyId != null) {
			fullFilePathToJson	= INITIAL_FILE_PATH + "_" + companyId + filePath;
		} else {
			fullFilePathToJson	= DEFAULT_FILE_PATH + filePath;
		}
		return fullFilePathToJson;
	} catch (Exception e) {
		throw e;
	}
}

@SuppressWarnings("unchecked")
private static HashMap<Object, Object> getConfigurationFromDefaultAndGroupObject(final HashMap<Object, Object> defaultObject, final HashMap<Object, Object> groupObject) throws Exception {
	Set<Object>		defaultKeys			= null;

	try {
		defaultKeys	= defaultObject.keySet();
		defaultKeys.forEach(key -> {
			if (groupObject.containsKey(key)) {
				ObjectMapper 			mapper 				= new ObjectMapper();
				HashMap<String, Object> defaultInnerObject	= mapper.convertValue(defaultObject.get(key), HashMap.class);
				HashMap<String, Object>	groupInnerObject	= mapper.convertValue(groupObject.get(key), HashMap.class);

				try {
					HashMap<String, Object> finalObject		= replaceValuesFromGroupInnerObject(defaultInnerObject, groupInnerObject);
					defaultObject.put(key, finalObject);
				} catch (Exception e) {
					
				} finally {
					defaultInnerObject	= null;
					groupInnerObject	= null;
				}
			}
		});

		return defaultObject;
	} catch (Exception e) {
		throw e;
	} finally {
		defaultKeys	= null;
	}
}

@SuppressWarnings("unchecked")
private static HashMap<String, Object> replaceValuesFromGroupInnerObject(final HashMap<String, Object> defaultInnerObject, final HashMap<String, Object> groupInnerObject) throws Exception {
	Set<String>		defaultKeys			= null;

	try {
		defaultKeys	= defaultInnerObject.keySet();
		defaultKeys.forEach(key -> {
			if (groupInnerObject.containsKey(key)) {
				try {
					ObjectMapper 			mapper 			= new ObjectMapper();
					HashMap<String, Object> defaultInnerObj	= mapper.convertValue(defaultInnerObject.get(key), HashMap.class);
					HashMap<String, Object>	groupInnerObj	= mapper.convertValue(groupInnerObject.get(key), HashMap.class);
					HashMap<String, Object> finalObject		= replaceValuesFromGroupInnerObject(defaultInnerObj, groupInnerObj);

					defaultInnerObject.put(key, finalObject);
				} catch (Exception e) {						
					defaultInnerObject.put(key, groupInnerObject.get(key));
				}
			}
		});

		return defaultInnerObject;
	} catch (Exception e) {
		throw e;
	} finally {
		defaultKeys	= null;
	}
}

@SuppressWarnings("unchecked")
public static ValueObject getSequencedConfiguration(ValueObject valueObject) throws Exception {
	HashMap<String, Object>			preSequenceObject	= null;
	TreeMap<String, Object>			sortingObject		= null;
	ObjectMapper					mapper 				= null;
	LinkedHashMap<Object, Object>	sequenceObject		= null;
	ValueObject						valueOutObject		= null;

	try {
		mapper				= new ObjectMapper();
		preSequenceObject	= mapper.convertValue(valueObject.get(COLUMN_CONFIGURATION), HashMap.class);
		sortingObject		= new TreeMap<String, Object>((Comparator.comparingLong(Long::parseLong)));
		sortingObject.putAll(preSequenceObject);
		sequenceObject		= new LinkedHashMap<Object, Object>();

		for(Map.Entry<String,Object> entry : sortingObject.entrySet()) {
			Object value = entry.getValue();
			sequenceObject.put(value, valueObject.get(value));
		}

		valueOutObject	= new ValueObject();
		valueOutObject.put(TABLE_PROPERTIES, valueObject.get(TABLE_PROPERTIES));
		valueOutObject.put(COLUMN_CONFIGURATION, sequenceObject);

		return valueOutObject;
	} catch (Exception e) {
		throw e;
	}
}


}
