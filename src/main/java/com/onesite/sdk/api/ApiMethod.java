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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onesite.common.util.json.JsonUtil;
import com.onesite.sdk.client.HttpStatus;
import com.onesite.sdk.client.OnesiteClient;
import com.onesite.sdk.client.OnesiteException;
import com.onesite.sdk.client.OnesiteResultCode;

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
		
		String result = null;

		if (this.client.getStatusCode() == HttpStatus.SC_OK) {
			result = this.client.getResult();

			try {
				int code = JsonUtil.getIntValueFromPath("code", result);
				if (code == OnesiteResultCode.OK) {
					return result;
				} else {
					throw new OnesiteException(code, JsonUtil.getStringValueFromPath("message", result));
				}
			} catch (Exception e) {
				log.error("Error occurred while processing requested result code for " + path);
				throw e;
			}
		}
		
		throw new OnesiteException(this.client.getStatusCode(), this.client.getStatusMessage());
	}
}
