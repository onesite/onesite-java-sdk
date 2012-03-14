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
public class ExternalProperty
{
	@JsonProperty("Name")
	private String name;

	@JsonProperty("Type")
	private String type;

	@JsonProperty("Value")
	private String value;

	public ExternalProperty()
	{
	}

	/**
	 * Create an ExternalProperty with set values
	 * 
	 * @param name Name of the property (ie: wordpress)
	 * @param type The type of the property (ie: lastLoginTime)
	 */
	public ExternalProperty(String name, String type)
	{
		this.setName(name);
		this.setType(type);
	}

	/**
	 * Create an ExternalProperty with set values
	 * 
	 * @param name Name of the property (ie: wordpress)
	 * @param type The type of the property (ie: lastLoginTime)
	 * @param value The value of the property (ie: now().toString())
	 */
	public ExternalProperty(String name, String type, String value)
	{
		this.setName(name);
		this.setType(type);
		this.setValue(value);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
