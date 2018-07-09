package com.resilience.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
public class Utilities {

	private static ObjectMapper mapper = new ObjectMapper();

	public static ObjectNode createObjectNode() {
		return mapper.createObjectNode();
	}

	public static ObjectNode byteToJson(byte[] jsonInByteArray) throws JsonProcessingException, IOException {
		return (ObjectNode) mapper.readTree(jsonInByteArray);
	}

	public static String convertJsonToString(ObjectNode json) throws JsonProcessingException {
		String jsonStr = null;
		if (json != null) {
			jsonStr = mapper.writeValueAsString(json);
		}
		return jsonStr;
	}

	public static ObjectNode convertStringToJson(String strJson) throws IOException {
		ObjectNode jsonObj = null;
		jsonObj = (ObjectNode) mapper.readTree(strJson);
		return jsonObj;
	}

	public static ObjectNode convertMapToJson(Map<String, ? extends Object> jsonMap) throws IOException {
		ObjectNode json = null;
		if (jsonMap != null && !jsonMap.isEmpty()) {
			String str = mapper.writeValueAsString(jsonMap);
			json = convertStringToJson(str);
		}
		return json;
	}

	public static ArrayNode convertListToArrayNode(List<?> object) throws IOException {

		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		ArrayNode arrayNode = null;
		mapper.writeValue(out, object);
		String strArray = out.toString("UTF-8");
		arrayNode = Utilities.convertStringToArrayNode(strArray);
		return arrayNode;

	}

	public static ArrayNode convertStringToArrayNode(String strArray) throws IOException {
		ArrayNode jsonObj = null;
		jsonObj = (ArrayNode) mapper.readTree(strArray);
		return jsonObj;
	}
}
