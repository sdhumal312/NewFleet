package org.fleetopgroup.web.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class JsonConvertor {

	/**
	 * convert json data into valueobject key values pairs
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static ValueObject toValueObjectFormSimpleJsonString(String jsonStr) throws Exception {
		ObjectMapper	mapper	= null;
		try {
			if (jsonStr == null) {
				return new ValueObject();
			}

			mapper	= new ObjectMapper();

			return new ValueObject(mapper.readValue(jsonStr, new TypeReference<HashMap<?, ?>>() {}));
		} catch (Exception e) {
			throw e;
		} finally {
			mapper	= null;
		}
	}

	/**
	 * convert tree json string to tree valueobject
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static ValueObject toValueObjectFromTreeJsonString(String jsonStr) throws Exception {

		ValueObject		object		= null;
		JsonFactory		factory		= null;
		ObjectMapper	mapper		= null;
		JsonNode		rootNode	= null;

		try {

			if (jsonStr == null) {
				return new ValueObject();
			}

			object	= new ValueObject();

			factory = new JsonFactory();

			mapper = new ObjectMapper(factory);
			rootNode = mapper.readTree(jsonStr);

			Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();

			while (fieldsIterator.hasNext()) {
				Map.Entry<String,JsonNode> field = fieldsIterator.next();
				object.put(field.getKey(), field.getValue());
				//LogWriter.writeLog(TRACE_ID, LogWriter.LOG_LEVEL_DEBUG, "===================== Key: " + field.getKey() + "\tValue:" + field.getValue());
			}
			return object;
		} catch (Exception e) {
			throw e;
		} finally {
			object		= null;
			factory		= null;
			mapper		= null;
			rootNode	= null;
		}
	}

	/**
	 * return list of valueobject of given json string
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<ValueObject> toValueObjectFromJsonString(String jsonStr) throws Exception {

		JsonNode 				individualElement				= null;
		ValueObject				objectFromSingleString			= null;
		ArrayList<ValueObject> 	collectionOfObjectsInArrlist 	= null;
		ObjectMapper 			jsonMapper 						= null;
		JsonNode 				jsonNode 						= null;

		try {

			if (jsonStr == null) {
				return new ArrayList<ValueObject>();
			}

			collectionOfObjectsInArrlist = new ArrayList<>();
			jsonMapper = new ObjectMapper();
			jsonNode = jsonMapper.readTree(jsonStr);

			if (jsonNode.isArray()) {
				ArrayNode arrayNode = (ArrayNode) jsonNode;
				for (int i = 0; i < arrayNode.size(); i++) {
					individualElement = arrayNode.get(i);
					objectFromSingleString = toValueObjectFormSimpleJsonString(individualElement.toString());
					collectionOfObjectsInArrlist.add(objectFromSingleString);
				}
			}

			return collectionOfObjectsInArrlist;
		} catch (Exception e) {
			throw e;
		} finally {
			individualElement				= null;
			objectFromSingleString			= null;
			collectionOfObjectsInArrlist 	= null;
			jsonMapper 						= null;
			jsonNode 						= null;
		}
	}

	public static String toJsonStringFromValueObject(ValueObject object) throws Exception {
		ObjectMapper	mapper	= null;
		try {
			mapper	= new ObjectMapper();
			return mapper.writeValueAsString(object.getHtData());
		} catch (Exception e) {
			throw e;
		} finally {
			mapper	= null;
		}
	}
	
	public static ValueObject toValueObjectFormTreeJsonString(String jsonStr) throws Exception {

		ValueObject		object		= null;
		JsonFactory		factory		= null;
		ObjectMapper	mapper		= null;
		JsonNode		rootNode	= null;

		try {

			if (jsonStr == null) {
				return new ValueObject();
			}

			object	= new ValueObject();

			factory = new JsonFactory();

			mapper = new ObjectMapper(factory);
			rootNode = mapper.readTree(jsonStr);  

			Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();

			while (fieldsIterator.hasNext()) {
				Map.Entry<String,JsonNode> field = fieldsIterator.next();
				object.put(field.getKey(), field.getValue());
			}
			return object;
		} catch (Exception e) {
			throw e;
		} finally {
			object		= null;
			factory		= null;
			mapper		= null;
			rootNode	= null;
		}
	}
}
