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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.onesite.sdk.api.SessionApi;
import com.onesite.sdk.test.AbstractTestCase;
import com.onesite.sdk.thrift.dao.Session;
import com.onesite.sdk.thrift.dao.User;

public class SessionApiTest extends AbstractTestCase
{
	public static final long userID = Long.valueOf(System.getProperty("test.userID", "0"));
	public static final String password = System.getProperty("test.pass", "");
	public static final String agent = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.26) Gecko/20120128 Firefox/3.6.26";
	public static final String ip = "127.0.0.1";
	public static final int expiresFromNow = 60 * 60 * 24 * 7;
	public static final String callbackUrl = "http://example.com/";

	@Test
	public void testCreateAnonymous()
	{
		SessionApi api = new SessionApi();

		try {
			Session testSession = new Session();
			testSession.setIp(ip);
			testSession.setAgent(agent);
			
			Session session = api.create(testSession);

			System.out.println("Anonymous Session");
			System.out.println("coreU: " + session.getCoreU());
			System.out.println("coreX: " + session.getCoreU());

			Assert.assertFalse(StringUtils.isEmpty(session.getCoreU()));
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testCreateActive()
	{
		SessionApi api = new SessionApi();

		try {
			User testUser = new User();
			testUser.setId(userID);
			
			Session testSession = new Session();
			testSession.setUser(testUser);
			testSession.setIp(ip);
			testSession.setAgent(agent);
			
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("alpha", "abc");
			data.put("numeric", "123");
			data.put("other", "#$%.-");
			testSession.setSessionData(data);

			Session session = api.create(testSession);

			System.out.println("Active Session");
			System.out.println("coreU: " + session.getCoreU());
			System.out.println("coreX: " + session.getCoreU());
			System.out.println("Data:  " + session.getSessionDataSize());
			
			Assert.assertFalse(StringUtils.isEmpty(session.getCoreU()));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testJoinCrossDomain()
	{
		String callbackUrl = "http://example.com";
		String domain = "twilight-fans.net";

		SessionApi api = new SessionApi();

		try {
			URL redirectUrl = api.joinCrossDomain(callbackUrl, domain, ip, agent);

			System.out.println("Join Cross Domain Redirect Url");
			System.out.println(redirectUrl.toString());

			Assert.assertFalse(StringUtils.isEmpty(redirectUrl.toString()));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testLogin()
	{
		SessionApi api = new SessionApi();
		try {
			User testUser = new User();
			testUser.setId(userID);			
			
			Session session = api.login(testUser, password, agent, ip, expiresFromNow);
			
			System.out.println("Active Session");
			System.out.println("coreU: " + session.getCoreU());
			System.out.println("coreX: " + session.getCoreU());
			System.out.println("Data:  " + session.getSessionDataSize());
			
			System.out.println("Login Successful");
			Assert.assertFalse(StringUtils.isEmpty(session.getCoreU()));
			
			System.out.println("Running session check against logged in coreU " + session.getCoreU());
			session = api.check(session);
			System.out.println("coreU: " + session.getCoreU());
			System.out.println("coreX: " + session.getCoreU());
			System.out.println("Data:  " + session.getSessionDataSize());
			if (session.isSetSessionData()) {
				System.out.println("User Status: " + session.getSessionData().get("STATUS"));
			}
			
			Assert.assertFalse(StringUtils.isEmpty(session.getCoreU()));
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void testLoginCrossDomain()
	{
		SessionApi api = new SessionApi();
		try {
			User testUser = new User();
			testUser.setId(userID);	
			
			URL redirectUrl = api.loginCrossDomain(testUser, password, callbackUrl, ip, expiresFromNow);
			
			System.out.println("Login Cross Domain Redirect Url");
			System.out.println(redirectUrl.toString());

			Assert.assertFalse(StringUtils.isEmpty(redirectUrl.toString()));
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
