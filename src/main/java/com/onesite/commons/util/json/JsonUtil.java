/**
 * Copyright 2012 ONESite, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.onesite.commons.util.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayway.jsonpath.JsonPath;

public class JsonUtil
{
	private static Logger log = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * Converts a json string into a map of objects
	 * 
	 * @param json
	 * 
	 * @return Map<Object, Object>
	 * @throws Exception
	 */
	public static Map<Object, Object> getJsonMap(String json) throws Exception
	{
		JsonFactory factory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(factory);
		
		TypeReference<HashMap<Object, Object>> typeRef = new TypeReference<HashMap<Object, Object>>()
		{
		};

		return mapper.readValue(json, typeRef);
	}

	/**
	 * Convert an object to a json string
	 * 
	 * @param data
	 * 
	 * @return string
	 * @throws Exception
	 */
	public static String getJsonString(Object data) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(data);
	}
	
	/**
	 * Maps a json string to a pojo based on the annotated class
	 * 
	 * @param result json result
	 * @param roodNode string to locate
	 * @param clazz Class to map to
	 * 
	 * @return Object
	 * @throws Exception
	 */
	public static Object getMappedClassFromJson(String result, String rootNode, Class clazz) throws Exception
	{
		// Map the content object into a tree
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootTreeNode = mapper.readTree(result);
		JsonNode obj = rootTreeNode.get(rootNode);

		// Parse it into a Java object.
		return mapper.treeToValue(obj, clazz);
	}

	/**
	 * Look up an Object based on a path (dot notation)
	 * 
	 * @param path
	 * @param json
	 * 
	 * @return Object
	 * @throws Exception
	 */
	public static Object getObjectFromPath(String path, String json) throws Exception
	{
		return JsonPath.read(json, path);
	}

	/**
	 * Look up a string value based on a path (dot notation)
	 * 
	 * @param path
	 * @param json
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getStringValueFromPath(String path, String json) throws Exception
	{
		return (String) getObjectFromPath(path, json);
	}

	/**
	 * Look up a int value based on a path (dot notation)
	 * 
	 * @param path
	 * @param json
	 * 
	 * @return int
	 * @throws Exception
	 */
	public static int getIntValueFromPath(String path, String json) throws Exception
	{
		Object obj = getObjectFromPath(path, json);

		if (obj.getClass().equals(String.class)) {
			return Integer.valueOf((String) obj);
		}

		return (Integer) obj;
	}

	/**
	 * Look up a long value based on a path (dot notation)
	 * 
	 * @param path
	 * @param json
	 * 
	 * @return long
	 * @throws Exception
	 */
	public static long getLongValueFromPath(String path, String json) throws Exception
	{
		Object obj = getObjectFromPath(path, json);

		if (obj.getClass().equals(String.class)) {
			return Long.valueOf((String) obj);
		}

		return (Long) obj;
	}

	/**
	 * Look up a list based on a path (dot notation)
	 * 
	 * @param path
	 * @param json
	 * 
	 * @return List<Object>
	 * @throws Exception
	 */
	public static List<Object> getListValuesFromPath(String path, String json) throws Exception
	{
		return (List<Object>) getObjectFromPath(path, json);
	}
}
