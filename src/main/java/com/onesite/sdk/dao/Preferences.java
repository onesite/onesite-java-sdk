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

import com.onesite.sdk.api.args.Constants.BirthdayDisplay;
import com.onesite.sdk.api.args.Constants.CommentsApproval;
import com.onesite.sdk.api.args.Constants.EmailNotification;
import com.onesite.sdk.api.args.Constants.FriendshipApproval;
import com.onesite.sdk.api.args.Constants.MessagePrivacy;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Preferences
{
	@JsonProperty("BirthdayDisplay")
	private BirthdayDisplay birthdayDisplay = BirthdayDisplay.PRIVATE;

	@JsonProperty("FriendshipApproval")
	private FriendshipApproval friendshipApproval = FriendshipApproval.MANUALLY;

	@JsonProperty("CommentsApproval")
	private CommentsApproval commentsApproval = CommentsApproval.AUTO_APPROVE_ALL;

	@JsonProperty("MessagePrivacy")
	private MessagePrivacy messagePrivacy = MessagePrivacy.ALLOW_ALL;

	@JsonProperty("EmailNotification")
	private EmailNotification emailNotification = EmailNotification.ONGOING;

	@JsonProperty("IsSearchable")
	private boolean searchable = true;

	@JsonProperty("ShowOnlineStatus")
	private boolean showOnlineStatus = true;

	public Preferences()
	{
	}

	public BirthdayDisplay getBirthdayDisplay()
	{
		return birthdayDisplay;
	}

	public void setBirthdayDisplay(BirthdayDisplay birthdayDisplay)
	{
		this.birthdayDisplay = birthdayDisplay;
	}

	public FriendshipApproval getFriendshipApproval()
	{
		return friendshipApproval;
	}

	public void setFriendshipApproval(FriendshipApproval friendshipApproval)
	{
		this.friendshipApproval = friendshipApproval;
	}

	public CommentsApproval getCommentsApproval()
	{
		return commentsApproval;
	}

	public void setCommentsApproval(CommentsApproval commentsApproval)
	{
		this.commentsApproval = commentsApproval;
	}

	public MessagePrivacy getMessagePrivacy()
	{
		return messagePrivacy;
	}

	public void setMessagePrivacy(MessagePrivacy messagePrivacy)
	{
		this.messagePrivacy = messagePrivacy;
	}

	public EmailNotification getEmailNotification()
	{
		return emailNotification;
	}

	public void setEmailNotification(EmailNotification emailNotification)
	{
		this.emailNotification = emailNotification;
	}

	public boolean isSearchable()
	{
		return searchable;
	}

	public void setSearchable(boolean searchable)
	{
		this.searchable = searchable;
	}

	public boolean isShowOnlineStatus()
	{
		return showOnlineStatus;
	}

	public void setShowOnlineStatus(boolean showOnlineStatus)
	{
		this.showOnlineStatus = showOnlineStatus;
	}

}
