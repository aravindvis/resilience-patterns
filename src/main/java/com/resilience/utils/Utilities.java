package com.resilience.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Utility methods for various JSON operations using the jackson library.
 *
 */

public class Utilities {

	private Utilities() {
		
	}
	
	//Single Object mapper to be used across the application
	private static ObjectMapper mapper = new ObjectMapper();


	//Creation of a new ObjectNode. Will be called whenever we need to create a JSON Object.
	public static ObjectNode createObjectNode() {
		return mapper.createObjectNode();
	}

	//Method to convert a JSON byte array to a Jackson Objectnode object.
	public static ObjectNode byteToJson(byte[] jsonInByteArray) throws IOException {
		return (ObjectNode) mapper.readTree(jsonInByteArray);
	}

	//Method to cover a JSON to a String.
	public static String convertJsonToString(ObjectNode json) throws JsonProcessingException {
		String jsonStr = null;
		if (json != null) {
			jsonStr = mapper.writeValueAsString(json);
		}
		return jsonStr;
	}

	//Method to convert String to JSON (Jackson ObjectNode)
	public static ObjectNode convertStringToJson(String strJson) throws IOException {
		ObjectNode jsonObj = null;
		jsonObj = (ObjectNode) mapper.readTree(strJson);
		return jsonObj;
	}

	//Method to convert a Java Map to a JSON Object
	public static ObjectNode convertMapToJson(Map<String, ? extends Object> jsonMap) throws IOException {
		ObjectNode json = null;
		if (jsonMap != null && !jsonMap.isEmpty()) {
			String str = mapper.writeValueAsString(jsonMap);
			json = convertStringToJson(str);
		}
		return json;
	}

	//Method to convert a Java List to a Jackson ArrayNode.
	public static ArrayNode convertListToArrayNode(List<?> object) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ArrayNode arrayNode = null;
		mapper.writeValue(out, object);
		String strArray = out.toString("UTF-8");
		arrayNode = Utilities.convertStringToArrayNode(strArray);
		return arrayNode;

	}

	
	//Method to convert a JSON Array (in string representation) to ArrayNode (Jackson)
	public static ArrayNode convertStringToArrayNode(String strArray) throws IOException {
		ArrayNode jsonObj = null;
		jsonObj = (ArrayNode) mapper.readTree(strArray);
		return jsonObj;
	}
}
