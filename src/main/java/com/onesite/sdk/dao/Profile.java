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
import java.util.Locale;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.onesite.commons.util.json.JsonDateDeserializerFromSeconds;
import com.onesite.sdk.api.args.Constants.Gender;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile
{
	@JsonProperty("Birthday")
	@JsonDeserialize(using=JsonDateDeserializerFromSeconds.class)
	private Date birthday;

	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("Gender")
	private Gender gender;

	@JsonProperty("Address")
	private String address;

	@JsonProperty("Address2")
	private String address2;

	@JsonProperty("City")
	private String city;

	@JsonProperty("State")
	private String state;

	@JsonProperty("Zip")
	private String zip;

	@JsonProperty("Country")
	private String country;

	@JsonProperty("Location")
	private String location;

	@JsonProperty("Timezone")
	private Long timezone;

	@JsonProperty("Phone")
	private String phone;

	@JsonProperty("Quote")
	private String quote;

	@JsonProperty("Language")
	private Locale locale = new Locale("en", "US");

	public Profile()
	{
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getZip()
	{
		return zip;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public Long getTimezone()
	{
		return timezone;
	}

	public void setTimezone(long timezone)
	{
		this.timezone = new Long(timezone);
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getQuote()
	{
		return quote;
	}

	public void setQuote(String quote)
	{
		this.quote = quote;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale loc)
	{
		this.locale = loc;
	}

}
