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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.onesite.common.util.json.JsonUtil;
import com.onesite.sdk.client.OnesiteException;
import com.onesite.sdk.client.OnesiteResultCode;
import com.onesite.sdk.dao.ExternalAccount;
import com.onesite.sdk.dao.ExternalProperty;
import com.onesite.sdk.dao.Password;
import com.onesite.sdk.dao.User;

/**
 * API for creating and managing ONESite Users 
 */
public class UserApi extends ApiMethod
{
	private final Logger log = LoggerFactory.getLogger(UserApi.class);

	/**
	 * Specific result codes returned from the UserApi Services
	 */
	public class ResultCode implements OnesiteResultCode
	{
		// User.USERNAME_TAKEN
		public static final int USERNAME_TAKEN = 207;
		
		// User.EMAIL_TAKEN
		public static final int EMAIL_TAKEN = 208;
		
		// User.SUBDIR_TAKEN
		public static final int SUBDIR_TAKEN = 209;

		// User.ACCOUNT_TAKEN
		public static final int ACCOUNT_TAKEN = 210;
	}
	
	public UserApi()
	{
	}

	/**
	 * Creates a new User account
	 * 
	 * @param user the User to create
	 * @param pass the password to use with this User
	 * 
	 * @return long the new userID
	 */
	public User create(User user, Password pass)
	{
		// content.user_id
		
		return user;
	}

	/**
	 * Delete a given users account
	 * 
	 * @param user User to delete (requires one of : id, username, or email)
	 * 
	 * @return success
	 * @throws Exception 
	 */
	public boolean delete(User user) throws Exception
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
		
		return false;
	}

	/**
	 * Update a given users details
	 * 
	 * @param user User to update (requires one of : id, username, or email)
	 * 
	 * @return success
	 * @throws Exception 
	 */
	public boolean update(User user) throws Exception
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
		
		return false;
	}

	/**
	 * Lookup details about a given User
	 * 
	 * @param user User to get details for (requires one of : id, username, or email)
	 * 
	 * @return User
	 * @throws Exception
	 */
	public User getDetails(User user) throws Exception
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

		try {
			String result = this.get("/1/user/getDetails.json", params);

			// TODO: fill account user
			return user;
		} catch (Exception e) {
			log.error("Error setting external property", e);
			throw e;
		}
	}

	/**
	 * Check to see if a given username is taken
	 * 
	 * @param username
	 * 
	 * @return taken
	 * 
	 * @throws Exception
	 */
	public boolean isUsernameTaken(String username) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("username", username);

		try {
			this.get("/1/user/isUsernameTaken.json", params);
			return true;
		} catch (OnesiteException e) {
			if (e.getErrorCode() == ResultCode.USERNAME_TAKEN) {
				return false;
			}
		} catch (Exception e) {
			log.error("Error checking if username is taken", e);
			throw e;
		}

		return false;
	}

	/**
	 * Check to see if a given email address is taken
	 * 
	 * @param email
	 * 
	 * @return taken
	 * 
	 * @throws Exception
	 */
	public boolean isEmailTaken(String email) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("email", email);

		try {
			this.get("/1/user/isEmailTaken.json", params);
			return true;
		} catch (OnesiteException e) {
			if (e.getErrorCode() == ResultCode.EMAIL_TAKEN) {
				return false;
			}
		} catch (Exception e) {
			log.error("Error checking if email is taken", e);
			throw e;
		}

		return false;
	}

	/**
	 * Check to see if a given subdir is taken (subdir immutable vanity identifier and can only be set on UserApi.create())
	 * 
	 * @param subdir
	 * 
	 * @return taken
	 * 
	 * @throws Exception
	 */
	public boolean isSubdirTaken(String subdir) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("subdir", subdir);

		try {
			this.get("/1/user/isSubdirTaken.json", params);
			return true;
		} catch (OnesiteException e) {
			if (e.getErrorCode() == ResultCode.SUBDIR_TAKEN) {
				return false;
			}
		} catch (Exception e) {
			log.error("Error checking if subdiris is taken", e);
			throw e;
		}

		return false;
	}

	/**
	 * Check to see if a given ExternalAccount is taken
	 * 
	 * @param account ExternalAccount to look for
	 * 
	 * @return taken
	 * @throws Exception
	 */
	public boolean isExternalAccountTaken(ExternalAccount account) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("exernal_provider_name", account.getProviderName());
		params.put("external_user_identifier", account.getUserIdentifier());

		// TODO: test
		try {
			this.get("/1/user/isExternalAccountTaken.json", params);
			return true;
		} catch (OnesiteException e) {
			if (e.getErrorCode() == ResultCode.ACCOUNT_TAKEN) {
				return false;
			}
		} catch (Exception e) {
			log.error("Error checking if external account is taken", e);
			throw e;
		}

		return false;
	}

	/**
	 * Add an ExternalAccount to an existing User
	 * 
	 * @param user User to add the ExternalAccount to (requires one of : id, username, or email)
	 * @param account ExternalAccount to set
	 * 
	 * @return success
	 * @throws Exception
	 */
	public boolean addExternalAccount(User user, ExternalAccount account) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("exernal_provider_name", account.getProviderName());
		params.put("external_user_identifier", account.getUserIdentifier());
		params.put("external_access_token", account.getAccessToken());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			this.get("/1/user/addExternalAccount.json", params);
			return true;
		} catch (Exception e) {
			log.error("Error adding external account", e);
			throw e;
		}
	}

	/**
	 * Update an ExternalAccount for an existing User
	 * 
	 * @param user User to update the ExternalAccount for (requires one of : id, username, or email)
	 * @param account ExternalAccount to update
	 * 
	 * @return success
	 * @throws Exception
	 */
	public boolean updateExternalAccount(User user, ExternalAccount account) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("exernal_provider_name", account.getProviderName());
		params.put("external_user_identifier", account.getUserIdentifier());
		params.put("external_access_token", account.getAccessToken());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			this.get("/1/user/updateExternalAccount.json", params);
			return true;
		} catch (Exception e) {
			log.error("Error adding external account", e);
			throw e;
		}
	}

	/**
	 * Delete an ExternalAccount for an existing User
	 * 
	 * @param user User to delete the ExternalAccount for (requires one of : id, username, or email)
	 * @param account ExternalAccount to delete
	 * 
	 * @return success
	 * @throws Exception
	 */
	public boolean deleteExternalAccount(User user, ExternalAccount account) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("exernal_provider_name", account.getProviderName());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			this.get("/1/user/deleteExternalAccount.json", params);
			return true;
		} catch (Exception e) {
			log.error("Error adding external account", e);
			throw e;
		}
	}

	/**
	 * Get all ExternalAccount details for a User
	 * 
	 * @param user User to get the ExternalAccounts for (requires one of : id, username, or email)
	 * @param account ExternalAccount to get details on (name required)
	 * 
	 * @return ExternalAccount
	 * @throws Exception
	 */
	public ExternalAccount getExternalAccount(User user, ExternalAccount account) throws Exception
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
		
		params.put("exernal_provider_name", account.getProviderName());

		try {
			String result = this.get("/1/user/getExternalAccount.json", params);

			// TODO: fill account out
			return null;
		} catch (Exception e) {
			log.error("Error setting external property", e);
			throw e;
		}
	}

	/**
	 * Set an ExternalProperty for the given user
	 * 
	 * @param user User to set the property for (requires one of : id, username, or email)
	 * @param prop ExternalProperty to set
	 * 
	 * @return Success
	 * @throws Exception
	 */
	public boolean addExternalProperty(User user, ExternalProperty prop) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("name", prop.getName());
		params.put("type", prop.getType());
		params.put("value", prop.getValue());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			this.get("/1/user/addExternalProperty.json", params);
			return true;
		} catch (Exception e) {
			log.error("Error setting external property", e);
			throw e;
		}
	}

	/**
	 * Get an external property for a given user
	 * 
	 * @param user User to set the property for (requires one of : id, username, or email)
	 * @param prop ExternalProperty to get (requires name and type)
	 * 
	 * @return value The ExternalProperty found or null
	 * @throws Exception
	 */
	public ExternalProperty getExternalProperty(User user, ExternalProperty prop) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("name", prop.getName());
		params.put("type", prop.getType());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			String result = this.get("/1/user/getExternalProperty.json", params);

			// TODO: fill prop out
			return prop;
		} catch (Exception e) {
			log.error("Error setting external property", e);
			throw e;
		}
	}

	/**
	 * Delete an ExternalProperty for the given user
	 * 
	 * @param user User to set the property for (requires one of : id, username, or email)
	 * @param prop ExternalProperty to delete (requires name and type)
	 * 
	 * @return Success
	 * @throws Exception
	 */
	public boolean deleteExternalProperty(User user, ExternalProperty prop) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("name", prop.getName());
		params.put("type", prop.getType());

		if (user.getID() != 0) {
			params.put("user_id", Long.toString(user.getID()));
		} else if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		} else if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing valid User identifier");
		}

		try {
			this.get("/1/user/deleteExternalProperty.json", params);
			return true;
		} catch (Exception e) {
			log.error("Error deleting external property", e);
		}

		return false;
	}

	/**
	 * Finds and returns a User based on a given ExternalProperty
	 * 
	 * @param prop The ExternalProperty
	 * 
	 * @return User
	 * @throws Exception
	 */
	public User getUserByExternalProperty(ExternalProperty prop) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("name", prop.getName());
		params.put("type", prop.getType());
		params.put("value", prop.getValue());

		try {
			this.get("/1/user/getUserByExternalProperty.json", params);

			User user = new User();
			// TODO: map user
			return user;
		} catch (Exception e) {
			log.error("Error deleting external property", e);
			throw new Exception(e);
		}
	}

	/**
	 * Validate a users credentials based on username
	 * 
	 * @param nodeID
	 * @param username
	 * @param password
	 * 
	 * @return boolean valid
	 * @throws Exception
	 */
	public boolean validateCredentials(long nodeID, String username, String password) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("nodeID", Long.toString(nodeID));
		params.put("username", username);
		params.put("password", password);
		params.put("no_redir", "1");

		try {
			String result = this.get("/1/session/validateCredentials.json", params);

			long userID = JsonUtil.getLongValueFromPath("content.user_id", result);

			if (userID > -1) {
				return true;
			}
		} catch (Exception e) {
			log.error("Error validating user credentials", e);
			throw e;
		}

		return false;
	}
}
