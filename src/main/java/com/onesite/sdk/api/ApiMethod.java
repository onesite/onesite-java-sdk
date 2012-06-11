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
package com.onesite.sdk.api;

import java.util.Map;

import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onesite.commons.net.http.rest.client.HttpStatus;
import com.onesite.sdk.client.OnesiteClient;
import com.onesite.sdk.client.OnesiteException;
import com.onesite.sdk.client.OnesiteResultCode;
import com.onesite.sdk.thrift.api.Status;

public class ApiMethod
{
	private static Logger log = LoggerFactory.getLogger(ApiMethod.class);

	private OnesiteClient client;

	public ApiMethod()
	{
		this.client = new OnesiteClient();
	}

	/**
	 * Call the API via the OnesiteClient and return back the results. A OnesiteException
	 * will be returned if an error occurs with a set OnesiteResultCode.
	 * 
	 * @param path
	 * @param params
	 * 
	 * @return result
	 * @throws Exception
	 */
	protected String get(String path, Map<String, String> params) throws Exception
	{
		try {
			this.client.get(path, params);
		} catch (Exception e) {
			log.error("Error occurred during client call to " + path);
			throw e;
		}

		if (this.client.getHttpStatusCode() == HttpStatus.SC_OK) {

			try {
				if (this.client.getResultCode() == OnesiteResultCode.OK) {
					return this.client.getResult();
				} else {
					throw new OnesiteException(this.client.getResultCode(), this.client.getResultMessage());
				}
			} catch (Exception e) {
				log.error("Error occurred while processing requested result code for " + path);
				throw e;
			}
		}

		throw new OnesiteException(this.client.getHttpStatusCode(), this.client.getHttpStatusMessage());
	}

	/**
	 * Call the API via the OnesiteClient and populate the TBase obj. A OnesiteException
	 * will be returned if an error occured during deserialization of the TBase obj
	 * 
	 * @param path
	 * @param params
	 * @param type
	 * 
	 * @throws Exception
	 */
	protected void get(String path, Map<String, String> params, TBase obj) throws Exception
	{
		try {
			this.client.get(path, params);
		} catch (Exception e) {
			log.error("Error occurred during client call to " + path);
			throw e;
		}

		if (this.client.getHttpStatusCode() == HttpStatus.SC_OK) {

			try {
				TDeserializer deserializer = new TDeserializer();
				deserializer.fromString(obj, this.client.getHttpResult());
				
				Status status = (Status) obj.getFieldValue(obj.fieldForId(1));

				if (status.getCode() != OnesiteResultCode.OK) {
					throw new OnesiteException(status.getCode(), status.getMessage());
				}
			} catch (Exception e) {
				throw new Exception("Error deserializing result ", e);
			}
		} else {
			throw new OnesiteException(this.client.getHttpStatusCode(), this.client.getHttpStatusMessage());
		}
	}
}
