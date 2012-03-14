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
package com.onesite.sdk.dao;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExternalAccount
{	
	@JsonProperty("ProviderName")
	private String providerName;
	
	@JsonProperty("UserIdentifier")
	private String userIdentifier;
	
	@JsonProperty("AccessToken")
	private String accessToken;

	public ExternalAccount()
	{

	}
	
	public ExternalAccount(String providerName)
	{
		this.setProviderName(providerName);
	}

	public ExternalAccount(String providerName, String userIdentifier)
	{
		this.setProviderName(providerName);
		this.setUserIdentifier(userIdentifier);
	}

	public ExternalAccount(String providerName, String userIdentifier, String accessToken)
	{
		this.setProviderName(providerName);
		this.setUserIdentifier(userIdentifier);
		this.setAccessToken(accessToken);
	}

	public String getProviderName()
	{
		return providerName;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public String getUserIdentifier()
	{
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier)
	{
		this.userIdentifier = userIdentifier;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}
}
