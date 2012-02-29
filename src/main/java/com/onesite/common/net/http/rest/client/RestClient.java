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
package com.onesite.common.net.http.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Connects to a rest service, parses the data and stores/returns the result string
 */
public class RestClient
{
	private static final Logger log = LoggerFactory.getLogger(RestClient.class);

	private HttpHost target;
	
	protected int status;
	protected String statusMessage;
	protected String result;

	/**
	 * Creates a new Rest client
	 *
	 * @param host
	 * @param port
	 * @param schema
	 */
	public RestClient(String host, int port, String schema)
	{
		this.target = new HttpHost(host, port, schema);
	}

	/**
	 * Connect to a Url.
	 * URL = scheme://host:port/path?query_string
	 * 
	 * @param path
	 * @param params
	 * 
	 * @return statusCode
	 */
	public int get(String path, Map<String, String> params) throws Exception
	{
		String query_string = "";

		if (params != null) {
			for (Map.Entry<String, String> val : params.entrySet()) {
				String encodedKey = "";
				String encodedValue = "";

				// Encode the keys and values and add them to the query string
				try {
					encodedKey = URLEncoder.encode(val.getKey().toString(), "UTF-8");
					encodedValue = URLEncoder.encode(val.getValue().toString(), "UTF-8");

					query_string = query_string + "&" + encodedKey + "=" + encodedValue;
				} catch (UnsupportedEncodingException e) {
					throw new Exception("Error encoding query parameters", e);
				}
			}
		}

		// Build the url from the path and params
		String url = path + "?" + query_string;
		log.debug("Encoded URL: " + url);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(this.target, request);

		status = response.getStatusLine().getStatusCode();
		statusMessage = response.getStatusLine().getReasonPhrase();

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new Exception("Error: " + response.getStatusLine().getStatusCode());
		}

		this.result = convertStreamToString(response.getEntity().getContent());
		httpClient.getConnectionManager().shutdown();

		return status;
	}

	/**
	 * Convert an InputStream to String
	 * 
	 * @param inStream
	 * @return result
	 */
	private String convertStreamToString(InputStream inStream)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * Return the status code obtained from the get() call
	 * 
	 * @return code
	 */
	public int getStatusCode()
	{
		return this.status;
	}

	/**
	 * Return the status message obtained from the get() call
	 * 
	 * @return message
	 */
	public String getStatusMessage()
	{
		return this.statusMessage;
	}

	/**
	 * Return the result obtained from the get() call
	 * 
	 * @return result
	 */
	public String getResult()
	{
		return this.result;
	}
}
