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

public class OnesiteException extends Exception
{
	private static final long serialVersionUID = 1L;

	public int errorCode;

	public OnesiteException(int code, String msg)
	{
		super(msg);
		this.setErrorCode(code);
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int code)
	{
		this.errorCode = code;
	}

}
