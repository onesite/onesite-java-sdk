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
public class Site
{
	@JsonProperty("SiteID")
	private long id;

	@JsonProperty("Subdir")
	private String subdir;
	
	@JsonProperty("URL")
	private String homeURL;

	public Site()
	{

	}

	public Site(long id)
	{
		this.setId(id);
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getSubdir()
	{
		return subdir;
	}

	public void setSubdir(String subdir)
	{
		this.subdir = subdir;
	}

	public String getHomeURL()
	{
		return homeURL;
	}

	public void setHomeURL(String homeURL)
	{
		this.homeURL = homeURL;
	}

}
