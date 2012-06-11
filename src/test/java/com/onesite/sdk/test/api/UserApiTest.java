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
package com.onesite.sdk.test.api;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.onesite.sdk.api.UserApi;
import com.onesite.sdk.test.AbstractTestCase;
import com.onesite.sdk.thrift.dao.ExternalAccount;
import com.onesite.sdk.thrift.dao.ExternalProperty;
import com.onesite.sdk.thrift.dao.Password;
import com.onesite.sdk.thrift.dao.Profile;
import com.onesite.sdk.thrift.dao.User;
import com.onesite.sdk.thrift.dao.constants.Gender;

public class UserApiTest extends AbstractTestCase
{
	public static final long userID = Long.valueOf(System.getProperty("test.userID", "0"));
	public static final String email = System.getProperty("test.email", "test@onesite.com");
	public static final String username = System.getProperty("test.username", String.format("java-sdk-test-%d", (int)(Math.random()*100)));
	public static final String password = System.getProperty("test.pass", "");
	public static final String agent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.26) Gecko/20120128 Firefox/3.6.26";
	public static final String ip = "127.0.0.1";
	public static final int expiresFromNow = 60 * 60 * 24 * 7;
	public static final String callbackUrl = "http://example.com/";

	@Test
	public void testGetUserDetails()
	{
		UserApi api = new UserApi();

		try {
			User testUser = new User();
			testUser.setId(userID);
			
			User user = api.getDetails(testUser);

			System.out.println("User");
			System.out.println("id: " + user.getId());
			System.out.println("username: " + user.getUsername());
			System.out.println("email: " + user.getEmail());
			System.out.println("account status: " + user.getAccountStatus());
			System.out.println("Birthday: " + user.getProfile().getBirthday());
			System.out.println("Timezone: " + user.getProfile().getTimezone());

			Assert.assertFalse(StringUtils.isEmpty(user.getUsername()));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testExternalProperty()
	{
		UserApi api = new UserApi();

		try {
			User testUser = new User();
			testUser.setId(userID);
			
			// add a value
			boolean result = api.setExternalProperty(testUser, new ExternalProperty("foo", "awesome").setValue("true"));
			Assert.assertTrue(result);

			// get the value
			ExternalProperty prop = api.getExternalProperty(testUser, new ExternalProperty("foo", "awesome"));
			Assert.assertTrue(prop.getValue().equals("true"));

			// delete the value
			result = api.deleteExternalProperty(testUser, new ExternalProperty("foo", "awesome"));
			Assert.assertTrue(result);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testCreate()
	{
		UserApi api = new UserApi();

		try {
			User user = new User();
			user.setEmail("test@onesite.com");
			user.addToExternalAccounts(new ExternalAccount("facebook", String.format("abc%d", (int)(Math.random()*1000)), "0987654321abcdefghijklmnopqrstuvwxyz"));

			User result = api.create(user, new Password("123456"), null);

			Assert.assertNotNull(result);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testCreate2()
	{
		UserApi api = new UserApi();

		try {
			User user = new User();
			user.setEmail(email);
			user.setUsername(username);
			
			Profile profile = new Profile();
			profile.setFirstName("test");
			profile.setGender(Gender.MALE);
			
			user.setProfile(profile);
			
			User result = api.create(user, new Password("123456"), null);

			Assert.assertNotNull(result);

			Assert.assertNotSame(result.getId(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
