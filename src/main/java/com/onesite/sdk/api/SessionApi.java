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
package com.onesite.sdk.api;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onesite.commons.util.json.JsonUtil;
import com.onesite.sdk.client.OnesiteResultCode;
import com.onesite.sdk.dao.Session;
import com.onesite.sdk.dao.User;

/**
 * ONESite API for the Session management designed to handle
 * cross domain Single Sign On, session authentication and validation.
 * 
 */
public class SessionApi extends ApiMethod
{
	private final Logger log = LoggerFactory.getLogger(SessionApi.class);

	public SessionApi()
	{
	}

	/**
	 * Creates a new session. If the User object is null or not populated with a id, username
	 * or email then an Anonymous session will be created.
	 * 
	 * @param session A session possibly containing a User
	 * 
	 * @return Session containing coreU and coreX values
	 * @throws Exception
	 */
	public Session create(Session session) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("ip", session.getIp());
		params.put("agent", session.getAgent());
		params.put("expires", Long.toString(session.getExpiresTime()));
		
		User user = session.getUser();
		
		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			params.put("anonymous", "1");
		}

		if (!session.getSessionData().isEmpty()) {
			params.put("session_data", session.getSessionData().toString());
		}

		try {
			String result = this.get("/1/session/create.json", params);
			session = (Session) JsonUtil.getMappedClassFromJson(result, "session", Session.class);
			
			return session;
		} catch (Exception e) {
			log.error("Error creating session", e);
			throw e;
		}
	}

	/**
	 * Creates a redirect url to forward the User to in order to establish a Cross Domain session
	 * 
	 * @param user User to create the session for (requires id, username, or email)
	 * @param ip The users ip address
	 * @param callbackUrl Url to send the user to after the redirect flow
	 * @param expiresFromNow Session Expiration time
	 * 
	 * @return redirect url
	 * @throws Exception
	 */
	public URL createCrossDomain(User user, String callbackUrl, String ip, long expiresFromNow) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		params.put("callback_url", callbackUrl);
		params.put("ip", ip);
		params.put("expires", Long.toString(expiresFromNow));

		try {
			String result = this.get("/1/session/createCrossDomain.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("redirect_url", result))) {
				return new URL(JsonUtil.getStringValueFromPath("redirect_url", result));
			}
		} catch (Exception e) {
			log.error("Error creating cross domain session url", e);
			throw e;
		}

		throw new Exception("Error creating cross domain redirect url");
	}

	/**
	 * Authenticate a User and generate an active Session.
	 * 
	 * @param user User to login (User id, username or email must be set)
	 * @param password The users password
	 * @param expiresFromNow Expiration time in seconds
	 * 
	 * @return Session created
	 * @throws Exception
	 */
	public Session login(User user, String password, long expiresFromNow) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("expires", Long.toString(expiresFromNow));
		
		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		if (!StringUtils.isEmpty(password)) {
			params.put("password", password);
		} else {
			throw new Exception("No password provided to login");
		}

		try {
			String result = this.get("/1/session/login.json", params);
			Session session = (Session) JsonUtil.getMappedClassFromJson(result, "session", Session.class);
			return session;
		} catch (Exception e) {
			log.error("Error logging User in and creating new session", e);
			throw e;
		}
	}

	/**
	 * Authenticate a User and returns a Url to redirect them to so an active Session can be generated
	 * 
	 * @param user User to create the session for (requires id, username, or email)
	 * @param password The users password
	 * @param ip The users ip address
	 * @param callbackUrl Url to send the user to after the redirect flow
	 * @param expiresFromNow Session Expiration time
	 * 
	 * @return redirect url
	 * @throws Exception
	 */
	public URL loginCrossDomain(User user, String password, String callbackUrl, String ip, long expiresFromNow) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("callback_url", callbackUrl);
		params.put("ip", ip);
		params.put("expires", Long.toString(expiresFromNow));
		
		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		if (!StringUtils.isEmpty(password)) {
			params.put("password", password);
		} else {
			throw new Exception("No password provided");
		}

		try {
			String result = this.get("/1/session/loginCrossDomain.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("redirect_url", result))) {
				return new URL(JsonUtil.getStringValueFromPath("redirect_url", result));
			}
		} catch (Exception e) {
			log.error("Error createing redirect url for loginCrossDomain", e);
			throw e;
		}

		throw new Exception("Error creating redirect url for login cross domain");
	}

	/**
	 * Logout a given User
	 * 
	 * @param session active Session
	 * 
	 * @return success
	 * @throws Exception
	 */
	public boolean logout(Session session) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		if (!StringUtils.isEmpty(session.getCoreU())) {
			params.put("core_u", session.getCoreU());
		}else if (!StringUtils.isEmpty(session.getAccessToken())){
			params.put("access_token", session.getCoreU());
		} else if (session.getUser().getID() != 0) {
			params.put("user_id", Long.toString(session.getUser().getID()));
		} else if (!StringUtils.isEmpty(session.getUser().getUsername())) {
			params.put("username", session.getUser().getUsername());
		} else if (!StringUtils.isEmpty(session.getUser().getEmail())) {
			params.put("email", session.getUser().getEmail());
		} else {
			throw new Exception("Missing Session.logout valid User identifier");
		}

		try {
			String result = this.get("/1/session/logout.json", params);

			if (JsonUtil.getIntValueFromPath("code", result) == OnesiteResultCode.OK) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error logging out session", e);
			throw e;
		}

		return false;
	}

	/**
	 * Validate a Session, if valid a populates User object is returned otherwise an anonymous User object.
	 * 
	 * @param session current Session
	 * 
	 * @return session
	 * @throws Exception
	 */
	public Session check(Session session) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		if (!StringUtils.isEmpty(session.getAccessToken())) {
			params.put("access_token", session.getAccessToken());
		} else {
			params.put("core_u", session.getCoreU());
			params.put("core_x", session.getCoreX());
		}

		params.put("ip", session.getIp());
		params.put("agent", session.getAgent());

		try {
			String result = this.get("/1/session/check.json", params);
			session = (Session) JsonUtil.getMappedClassFromJson(result, "session", Session.class);
			return session;
		} catch (Exception e) {
			log.error("Error checking session status", e);
			throw e;
		}
	}

	/**
	 * Verifies and joins a User to a multi-domain session
	 * 
	 * @param callbackUrl Callback url to send the user to after successful join
	 * @param domain Top level domain
	 * @param ip The users ip address
	 * @param agent The users browser agent
	 * 
	 * @return URL redirect url
	 * @throws Exception
	 */
	public URL joinCrossDomain(String callbackUrl, String domain, String ip, String agent) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("callback_url", callbackUrl);
		params.put("domain", domain);
		params.put("ip", ip);
		params.put("agent", agent);
		
		try {
			String result = this.get("/1/session/joinCrossDomain.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("redirect_url", result))) {
				return new URL(JsonUtil.getStringValueFromPath("redirect_url", result));
			}
		} catch (Exception e) {
			log.error("Error joining cross domain session", e);
			throw e;
		}

		throw new Exception("Error creating join cross domain redirect url");
	}
}
