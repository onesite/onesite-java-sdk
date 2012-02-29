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

import java.util.List;

public class User
{
	private long id;
	private String username;
	private String email;

	private boolean anonymous = false;

	private long nodeID;
	private String domain;
	private String accountStatus;

	private String subdir;
	private String displayName;
		
	private String avatarUrl;
		
	private List<ExternalAccount> linkedAccounts;
	
	private Profile profile;
	
	public User()
	{
	}
		
	public long getID()
	{
		return id;
	}

	public void setID(long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public long getNodeID()
	{
		return nodeID;
	}

	public void setNodeID(long nodeID)
	{
		this.nodeID = nodeID;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

	public String getAccountStatus()
	{
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus)
	{
		this.accountStatus = accountStatus;
	}

	public String getSubdir()
	{
		return subdir;
	}

	public void setSubdir(String subdir)
	{
		this.subdir = subdir;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public String getAvatarUrl()
	{
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl)
	{
		this.avatarUrl = avatarUrl;
	}

	public List<ExternalAccount> getLinkedAccounts()
	{
		return linkedAccounts;
	}

	public void setLinkedAccounts(List<ExternalAccount> linkedAccounts)
	{
		this.linkedAccounts = linkedAccounts;
	}

	public boolean isAnonymous()
	{
		return anonymous;
	}

	public void setAnonymous(boolean anonymous)
	{
		this.anonymous = anonymous;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}	
}
