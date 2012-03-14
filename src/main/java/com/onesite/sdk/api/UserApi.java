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

import com.onesite.commons.util.json.JsonUtil;
import com.onesite.sdk.api.args.UserCreateOptions;
import com.onesite.sdk.client.OnesiteException;
import com.onesite.sdk.client.OnesiteResultCode;
import com.onesite.sdk.dao.ExternalAccount;
import com.onesite.sdk.dao.ExternalProperty;
import com.onesite.sdk.dao.Password;
import com.onesite.sdk.dao.Preferences;
import com.onesite.sdk.dao.Profile;
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
		public static final int MISSING_PARAMETERS = 190;
		public static final int INVALID_USER = 200;
		public static final int MISSING_USERNAME = 203;
		public static final int USERNAME_TAKEN = 207;
		public static final int EMAIL_TAKEN = 208;
		public static final int SUBDIR_TAKEN = 209;
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
	 * @param options user create optional args for new user
	 * 
	 * @return long the new userID
	 * @throws Exception
	 */
	public User create(User user, Password password, UserCreateOptions options) throws Exception
	{
		Map<String, String> params = new HashMap<String, String>();

		// Required
		if (!StringUtils.isEmpty(user.getEmail())) {
			params.put("email", user.getEmail());
		} else {
			throw new Exception("Missing User email address");
		}

		// Create User MAP<String, String> and save all values in the params
		params.putAll(this.generateMapFromUser(user));

		// From #Site
		if (user.getSite() != null) {
			if (!StringUtils.isEmpty(user.getSite().getSubdir())) {
				params.put("subdir", user.getSite().getSubdir());
			}
		}

		// From #ExternalAccount
		if (user.getExternalAccounts().size() > 0) {
			ExternalAccount account = user.getExternalAccounts().get(0);

			params.put("external_provider_name", account.getProviderName());
			params.put("external_user_identifier", account.getUserIdentifier());
			params.put("external_access_token", account.getAccessToken());
		}

		// From #Password
		params.put("password", password.getPassword());

		// From #UserCreateOptions
		if (options != null) {
			if (!StringUtils.isEmpty(options.getCouponCode())) {
				params.put("coupon_code", options.getCouponCode());
			}

			if (!StringUtils.isEmpty(options.getGroupMemberStatus().toString())) {
				params.put("group_member_status", options.getGroupMemberStatus().toString());
			}

			if (options.getInitialGroup() != null) {
				params.put("join_group", String.valueOf(options.getInitialGroup().longValue()));
			}

			if (options.getInitialFriend() != null) {
				params.put("add_friend", String.valueOf(options.getInitialFriend().longValue()));
			}

			if (options.getReferringUrl() != null) {
				params.put("referring_url", options.getReferringUrl().toString());
			}

			params.put("send_confirmation_email", (options.isSendConfirmationEmail()) ? "1" : "0");
		}

		User newUser = null;

		try {
			String result = this.get("/1/user/create.json", params);
			newUser = (User) JsonUtil.getMappedClassFromJson(result, "user", User.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s creating User: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error creating User", e);
			throw new Exception(e);
		}

		// Add any additional external accounts to the user
		if (newUser != null) {
			if (user.getExternalAccounts().size() > 1) {
				for (int i = 1; i > user.getExternalAccounts().size(); i++) {
					ExternalAccount account = user.getExternalAccounts().get(i);
					this.addExternalAccount(newUser, account);
				}
			}
		}

		return newUser;
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

		try {
			this.get("/1/user/delete.json", params);
			return true;
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s deleting User: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error deleting User", e);
			throw e;
		}
	}

	/**
	 * Update a given users details. This call updates attributes in the main User object and the User.Profile.
	 * To update any ExternalAccounts for the User call the {@link #updateExternalAccount(User, ExternalAccount)}.
	 * To update any ExternalProperties for the User call the {@link #setExternalProperty(User, ExternalProperty)}.
	 * 
	 * @param user User to update (requires one of : id, username, or email)
	 * 
	 * @return success
	 * @throws Exception
	 */
	public User update(User user) throws Exception
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
			// Create User MAP<String, String> and save all values in the params
			params.putAll(this.generateMapFromUser(user));

			String result = this.get("/1/user/update.json", params);
			return (User) JsonUtil.getMappedClassFromJson(result, "user", User.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s updating User: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error updating User", e);
			throw new Exception(e);
		}
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
			return (User) JsonUtil.getMappedClassFromJson(result, "user", User.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s getting User details: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error getting User details", e);
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

		try {
			params.put("username", username);

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

		try {
			params.put("email", email);

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

		try {
			params.put("subdir", subdir);

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

		try {
			params.put("exernal_provider_name", account.getProviderName());
			params.put("external_user_identifier", account.getUserIdentifier());

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
			params.put("exernal_provider_name", account.getProviderName());
			params.put("external_user_identifier", account.getUserIdentifier());
			params.put("external_access_token", account.getAccessToken());

			String result = this.get("/1/user/addExternalAccount.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("uniqueId", result))) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s adding external account: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error adding external account", e);
			throw e;
		}

		return false;
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
			params.put("exernal_provider_name", account.getProviderName());
			params.put("external_user_identifier", account.getUserIdentifier());
			params.put("external_access_token", account.getAccessToken());

			String result = this.get("/1/user/updateExternalAccount.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("uniqueId", result))) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s updating external account: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error updating external account", e);
			throw e;
		}

		return false;
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
			params.put("exernal_provider_name", account.getProviderName());

			String result = this.get("/1/user/deleteExternalAccount.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("uniqueId", result))) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s deleting external account: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error deleting external account", e);
			throw e;
		}

		return false;
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

		try {
			params.put("exernal_provider_name", account.getProviderName());

			String result = this.get("/1/user/getExternalAccount.json", params);
			return (ExternalAccount) JsonUtil.getMappedClassFromJson(result, "external_account", ExternalAccount.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s setting external account: %s", e.getErrorCode(), e.getMessage()));
			throw e;
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
	public boolean setExternalProperty(User user, ExternalProperty prop) throws Exception
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
			params.put("name", prop.getName());
			params.put("type", prop.getType());
			params.put("value", prop.getValue());

			String result = this.get("/1/user/addExternalProperty.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("uniqueId", result))) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s adding external property: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error adding external property", e);
			throw e;
		}

		return false;
	}

	/**
	 * Get an external property for a given user
	 * 
	 * @param user User to set the property for (requires one of : id, username, or email)
	 * @param prop ExternalProperty to get (requires name and type)
	 * 
	 * @return value The ExternalProperty found
	 * @throws Exception
	 */
	public ExternalProperty getExternalProperty(User user, ExternalProperty prop) throws Exception
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
			params.put("name", prop.getName());
			params.put("type", prop.getType());

			String result = this.get("/1/user/getExternalProperty.json", params);
			return (ExternalProperty) JsonUtil.getMappedClassFromJson(result, "external_property", ExternalProperty.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s getting external property: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error getting external property", e);
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
			params.put("name", prop.getName());
			params.put("type", prop.getType());

			String result = this.get("/1/user/deleteExternalProperty.json", params);

			if (!StringUtils.isEmpty(JsonUtil.getStringValueFromPath("uniqueId", result))) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s deleting external property: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error deleting external property", e);
			throw e;
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

		try {
			params.put("name", prop.getName());
			params.put("type", prop.getType());
			params.put("value", prop.getValue());

			String result = this.get("/1/user/getUserByExternalProperty.json", params);
			return (User) JsonUtil.getMappedClassFromJson(result, "user", User.class);
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s getting User by external property: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error getting User by external property", e);
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
			String result = this.get("/1/user/validateCredentials.json", params);

			long userID = JsonUtil.getLongValueFromPath("content.user_id", result);

			if (userID > -1) {
				return true;
			}
		} catch (OnesiteException e) {
			log.debug(String.format("Error %s validating User credentials: %s", e.getErrorCode(), e.getMessage()));
			throw e;
		} catch (Exception e) {
			log.error("Error validating User credentials", e);
			throw e;
		}

		return false;
	}

	/**
	 * Creates a Map<String, String> from the User object to send to the Create and Update APIs.
	 * This function is private due to the work being currently done to the service to take direct json
	 * representation of the User object.
	 * 
	 * @param user User to create a map from
	 * 
	 * @return map<String, String>
	 */
	private Map<String, String> generateMapFromUser(User user)
	{
		Map<String, String> params = new HashMap<String, String>();

		// From #User
		if (!StringUtils.isEmpty(user.getUsername())) {
			params.put("username", user.getUsername());
		}

		if (!StringUtils.isEmpty(user.getDisplayName())) {
			params.put("display_name", user.getDisplayName());
		}

		if (user.getAccountStatus() != null) {
			params.put("account_status", user.getAccountStatus().toString());
		}

		if (!StringUtils.isEmpty(user.getAvatarUrl())) {
			params.put("avatar", user.getAvatarUrl());
		}

		// From #Profile
		Profile profile = user.getProfile();

		if (profile != null) {
			if (profile.getBirthday() != null) {
				params.put("birthday", String.valueOf(profile.getBirthday().getTime() / 1000));
			}

			if (!StringUtils.isEmpty(profile.getFirstName())) {
				params.put("first_name", profile.getFirstName());
			}

			if (!StringUtils.isEmpty(profile.getLastName())) {
				params.put("last_name", profile.getLastName());
			}

			if (!StringUtils.isEmpty(profile.getGender().toString())) {
				params.put("gender", profile.getGender().toString());
			}

			if (!StringUtils.isEmpty(profile.getAddress())) {
				params.put("address", profile.getAddress());
			}

			if (!StringUtils.isEmpty(profile.getAddress2())) {
				params.put("address2", profile.getAddress2());
			}

			if (!StringUtils.isEmpty(profile.getCity())) {
				params.put("city", profile.getCity());
			}

			if (!StringUtils.isEmpty(profile.getState())) {
				params.put("state", profile.getState());
			}

			if (!StringUtils.isEmpty(profile.getZip())) {
				params.put("zip", profile.getZip());
			}

			if (!StringUtils.isEmpty(profile.getLocation())) {
				params.put("location", profile.getLocation());
			}

			if (!StringUtils.isEmpty(profile.getPhone())) {
				params.put("phone", profile.getPhone());
			}

			if (!StringUtils.isEmpty(profile.getQuote())) {
				params.put("quote", profile.getQuote());
			}

			if (!StringUtils.isEmpty(profile.getCountry())) {
				params.put("country", profile.getCountry());
			}

			if (profile.getTimezone() != null) {
				params.put("timezone", String.valueOf(profile.getTimezone().longValue()));
			}

			params.put("locale", profile.getLocale().getDisplayVariant());
		}
		// From #Preferences
		Preferences preferences = user.getPreferences();

		if (preferences != null) {
			if (!StringUtils.isEmpty(preferences.getBirthdayDisplay().toString())) {
				params.put("dob_display", preferences.getBirthdayDisplay().toString());
			}

			if (!StringUtils.isEmpty(preferences.getFriendshipApproval().toString())) {
				params.put("friends_approval", preferences.getFriendshipApproval().toString());
			}

			if (!StringUtils.isEmpty(preferences.getCommentsApproval().toString())) {
				params.put("comments_approval", preferences.getCommentsApproval().toString());
			}

			if (!StringUtils.isEmpty(preferences.getMessagePrivacy().toString())) {
				params.put("message_privacy", preferences.getMessagePrivacy().toString());
			}

			if (!StringUtils.isEmpty(preferences.getEmailNotification().toString())) {
				params.put("email_notification", preferences.getEmailNotification().toString());
			}

			params.put("is_searchable", preferences.isSearchable() ? "1" : "0");
			params.put("show_online_status", preferences.isShowOnlineStatus() ? "1" : "0");
		}

		return params;
	}
}
