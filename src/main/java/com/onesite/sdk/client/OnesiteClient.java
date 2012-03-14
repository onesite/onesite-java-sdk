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
package com.onesite.sdk.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.http.entity.mime.content.ContentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onesite.commons.net.http.rest.client.RestClient;
import com.onesite.commons.util.json.JsonUtil;

public class OnesiteClient
{
	private static final Logger log = LoggerFactory.getLogger(OnesiteClient.class);

	public String servicesURL;
	public String devkey;

	protected RestClient client;

	/**
	 * Create a client using the System property onesite.services.url
	 */
	public OnesiteClient()
	{
		servicesURL = System.getProperty("onesite.services.url", "https://services.onesite.com");
		devkey = System.getProperty("onesite.devkey", "");

		URL url;
		try {
			url = new URL(servicesURL);
			client = new RestClient(url.getHost(), url.getPort(), url.getProtocol());
		} catch (MalformedURLException e) {
			log.error("Error creating OnesiteClient for url: " + servicesURL);
		}
	}

	/**
	 * Specify an option schema://host:port to use
	 * 
	 * @param host
	 * @param port
	 * @param schema
	 */
	public OnesiteClient(String host, int port, String schema)
	{
		devkey = System.getProperty("onesite.devkey", "");
		client = new RestClient(host, port, schema);
	}

	public OnesiteClient(URL url)
	{
		devkey = System.getProperty("onesite.devkey", "");
		client = new RestClient(url.getHost(), url.getPort(), url.getProtocol());
	}

	/**
	 * Call the ONESite Service with parameters
	 * 
	 * @param path
	 * @param params
	 * 
	 * @return String result
	 */
	public int get(String path, Map<String, String> params) throws Exception
	{
		// Guarantee that the devkey is included with all service call
		params.put("devkey", this.devkey);

		return client.get(path, params);
	}
	
	public int post(String path, Map<String, String> params, ContentBody body) throws Exception
	{
		// Guarantee that the devkey is included with all service call
		params.put("devkey", this.devkey);

		return client.post(path, params, body);
	}

	/**
	 * @see RestClient#getStatusCode()
	 */
	public int getHttpStatusCode()
	{
		return client.getStatusCode();
	}

	/**
	 * @see RestClient#getStatusMessage()
	 */
	public String getHttpStatusMessage()
	{
		return client.getStatusMessage();
	}

	/**
	 * @see RestClient#getResult()
	 */
	public String getHttpResult()
	{
		return client.getResult();
	}
	
	public int getResultCode() throws Exception
	{
		return JsonUtil.getIntValueFromPath("code", this.getHttpResult());
	}

	public String getResultMessage() throws Exception
	{
		return JsonUtil.getStringValueFromPath("message", this.getHttpResult());
	}

	public String getResult() throws Exception
	{
		return JsonUtil.getJsonString(JsonUtil.getObjectFromPath("content", this.getHttpResult()));
	}
}
