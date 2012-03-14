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

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session
{
	// Default session expiration time of 2 weeks
	public static final int DefaultSessionExpiresTime = 60 * 60 * 24 * 7 * 2;

	@JsonProperty("CoreU")
	private String coreU = new String();

	@JsonProperty("CoreX")
	private String coreX = new String();

	@JsonProperty("AccessToken")
	private String accessToken = new String();

	@JsonProperty("Data")
	private SessionData sessionData = new SessionData();

	@JsonProperty("State")
	private String status = new String();

	@JsonProperty("RemoteIP")
	private String ip = new String();

	@JsonProperty("Agent")
	private String agent = new String();

	@JsonProperty("Expires")
	private long expiresTime = Session.DefaultSessionExpiresTime;

	@JsonProperty("User")
	private User user = new User();

	public Session()
	{

	}

	public Session(String ip, String agent)
	{
		this.setIp(ip);
		this.setAgent(agent);
	}

	public Session(User user, String ip, String agent)
	{
		this.setUser(user);
		this.setIp(ip);
		this.setAgent(agent);
	}

	public Session(String coreU, String coreX, String ip, String agent)
	{
		this.setCoreU(coreU);
		this.setCoreX(coreX);
		this.setIp(ip);
		this.setAgent(agent);
	}

	public Session(User user, String coreU, String coreX, String ip, String agent)
	{
		this.setUser(user);
		this.setCoreU(coreU);
		this.setCoreX(coreX);
		this.setIp(ip);
		this.setAgent(agent);
	}

	public Session(User user, String accessToken, String ip, String agent)
	{
		this.setUser(user);
		this.setAccessToken(accessToken);
		this.setIp(ip);
		this.setAgent(agent);
	}

	public String getCoreU()
	{
		return coreU;
	}

	public void setCoreU(String coreU)
	{
		this.coreU = coreU;
	}

	public String getCoreX()
	{
		return coreX;
	}

	public void setCoreX(String coreX)
	{
		this.coreX = coreX;
	}

	public SessionData getSessionData()
	{
		return sessionData;
	}

	public void setSessionData(SessionData sessionData)
	{
		this.sessionData = sessionData;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessTokn)
	{
		this.accessToken = accessTokn;
	}

	public String getAccessTokn()
	{
		return accessToken;
	}

	public void setAccessTokn(String accessTokn)
	{
		this.accessToken = accessTokn;
	}

	public String getSessionStatus()
	{
		return status;
	}

	public void setSessionStatus(String sessionStatus)
	{
		this.status = sessionStatus;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public String getAgent()
	{
		return agent;
	}

	public void setAgent(String agent)
	{
		this.agent = agent;
	}

	public long getExpiresTime()
	{
		return expiresTime;
	}

	public void setExpiresTime(long expireTime)
	{
		this.expiresTime = expireTime;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
