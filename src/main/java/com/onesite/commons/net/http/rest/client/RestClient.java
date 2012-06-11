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
package com.onesite.commons.net.http.rest.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
		String url = this.generateEncodeURLString(path, params);

		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpGet request = new HttpGet(url);
		request.addHeader("accept", "application/json");

		try {
			HttpResponse response = httpClient.execute(this.target, request);

			try {
				this.processHttpResponse(response);
			} catch (Exception e) {
				log.error("Error processing HttpResponse from " + url);
				throw e;
			}
		} catch (Exception e) {
			log.error("Error occurred during calling to " + url);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return status;
	}

	/**
	 * Create a POST request to the given url posting the ContentBody to the content tag
	 * URL = scheme://host:port/path?query_string
	 * 
	 * @param path
	 * @param params
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public int post(String path, Map<String, String> params, ContentBody body) throws Exception
	{
		String url = this.generateEncodeURLString(path, params);

		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpPost request = new HttpPost(url);
		request.addHeader("accept", "application/json");

		MultipartEntity entity = new MultipartEntity();
		entity.addPart("content", body);
		request.setEntity(entity);

		try {
			HttpResponse response = httpClient.execute(this.target, request);

			try {
				this.processHttpResponse(response);
			} catch (Exception e) {
				log.error("Error processing HttpResponse from " + url);
				throw e;
			}
		} catch (Exception e) {
			log.error("Error occurred during calling to " + url);
			throw e;
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return status;
	}

	/**
	 * Process the http response and set the status, message and result for the HttpResponse
	 * 
	 * @param response
	 * @throws Exception
	 */
	private void processHttpResponse(HttpResponse response) throws Exception
	{
		status = response.getStatusLine().getStatusCode();
		statusMessage = response.getStatusLine().getReasonPhrase();

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new Exception("Error: " + response.getStatusLine().getStatusCode());
		}

		this.result = new String(EntityUtils.toByteArray(response.getEntity()));
	}

	/**
	 * Generate an encoded url string for the given path and query parameters
	 * 
	 * @param path
	 * @param params
	 * 
	 * @return
	 * @throws Exception
	 */
	private String generateEncodeURLString(String path, Map<String, String> params) throws Exception
	{
		StringBuilder queryString = new StringBuilder();

		if (params != null) {
			for (Map.Entry<String, String> val : params.entrySet()) {
				String encodedKey = "";
				String encodedValue = "";

				// Encode the keys and values and add them to the query string
				try {
					encodedKey = URLEncoder.encode(val.getKey(), "UTF-8");
					
					if (!StringUtils.isEmpty(val.getValue())) {
						encodedValue = URLEncoder.encode(val.getValue(), "UTF-8");
						queryString.append(String.format("&%s=%s", encodedKey, encodedValue));
					}
				} catch (UnsupportedEncodingException e) {
					throw new Exception("Error encoding query parameters", e);
				}
			}
		}

		String url = String.format("%s?%s", path, queryString.toString());
		log.debug("Encoded url string: " + url);

		return url;
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
