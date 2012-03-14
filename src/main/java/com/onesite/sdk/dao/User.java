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

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.onesite.sdk.api.args.Constants.AccountStatus;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User
{
	@JsonProperty("UserID")
	private long id;

	@JsonProperty("Username")
	private String username;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("Name")
	private String displayName;

	@JsonProperty("AccountStatus")
	private AccountStatus accountStatus;
	
	@JsonProperty("Avatar")
	private String avatarUrl;

	@JsonProperty("ExternalAccounts")
	private List<ExternalAccount> externalAccounts = new ArrayList<ExternalAccount>();

	@JsonProperty("Site")
	private Site site;
	
	@JsonProperty("Profile")
	private Profile profile;
	
	@JsonProperty("Preferences")
	private Preferences preferences; 

	public User()
	{
	}
	
	public User(long id)
	{
		this.setID(id);
	}
	
	public User(String username)
	{
		this.setUsername(username);
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

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public AccountStatus getAccountStatus()
	{
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus)
	{
		this.accountStatus = accountStatus;
	}

	public String getAvatarUrl()
	{
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl)
	{
		this.avatarUrl = avatarUrl;
	}

	public List<ExternalAccount> getExternalAccounts()
	{
		return externalAccounts;
	}

	public void setExternalAccounts(List<ExternalAccount> externalAccounts)
	{
		this.externalAccounts = externalAccounts;
	}
	
	public void addExternalAccount(ExternalAccount account)
	{
		this.externalAccounts.add(account);
	}

	public Site getSite()
	{
		return site;
	}

	public void setSite(Site site)
	{
		this.site = site;
	}

	public Profile getProfile()
	{
		return profile;
	}

	public void setProfile(Profile profile)
	{
		this.profile = profile;
	}

	public Preferences getPreferences()
	{
		return preferences;
	}

	public void setPreferences(Preferences preferences)
	{
		this.preferences = preferences;
	}

}
