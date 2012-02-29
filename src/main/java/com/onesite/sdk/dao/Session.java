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

import java.util.Date;
import java.util.Map;

public class Session
{
	private String coreU;
	private String coreX;
	private String accessToken;

	private Map<String, String> sessionData;

	private String sessionStatus;
	
	private String ip;	
	private String agent;
	private Date expiresTime;
	
	private User user;

	public Session()
	{

	}

	public Session(String coreU, String coreX)
	{
		this.setCoreU(coreU);
		this.setCoreX(coreX);
	}

	public Session(String coreU, String coreX, User user)
	{
		this.setCoreU(coreU);
		this.setCoreX(coreX);
		this.setUser(user);
	}
	
	public Session(String accessToken)
	{
		this.setAccessToken(accessToken);
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

	public Map<String, String> getSessionData()
	{
		return sessionData;
	}

	public void setSessionData(Map<String, String> sessionData)
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
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus)
	{
		this.sessionStatus = sessionStatus;
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

	public Date getExpiresTime()
	{
		return expiresTime;
	}
	
	public void setExpiresTime(Date expireTime)
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
