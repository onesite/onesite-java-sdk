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
package com.onesite.sdk.api.args;

import java.net.URL;

import com.onesite.sdk.api.args.Constants.GroupMemberStatus;

public class UserCreateOptions
{
	private boolean sendConfirmationEmail = false;
	private URL referringUrl = null;
	private Long addFriend;
	private Long joinGroup;
	private GroupMemberStatus groupMemberStatus = GroupMemberStatus.MEMBER;
	private String couponCode;
	
	public UserCreateOptions()
	{
	}

	public boolean isSendConfirmationEmail()
	{
		return sendConfirmationEmail;
	}

	public void setSendConfirmationEmail(boolean sendConfirmationEmail)
	{
		this.sendConfirmationEmail = sendConfirmationEmail;
	}

	public URL getReferringUrl()
	{
		return referringUrl;
	}

	public void setReferringUrl(URL referringUrl)
	{
		this.referringUrl = referringUrl;
	}

	public Long getInitialFriend()
	{
		return addFriend;
	}

	public void addInitialFriend(long friendID)
	{
		this.addFriend = new Long(friendID);
	}

	public Long getInitialGroup()
	{
		return joinGroup;
	}

	public void setInitialGroup(long joinGroup)
	{
		this.joinGroup = new Long(joinGroup);
	}

	public GroupMemberStatus getGroupMemberStatus()
	{
		return groupMemberStatus;
	}

	public void setGroupMemberStatus(GroupMemberStatus groupMemberStatus)
	{
		this.groupMemberStatus = groupMemberStatus;
	}

	public String getCouponCode()
	{
		return couponCode;
	}

	public void setCouponCode(String couponCode)
	{
		this.couponCode = couponCode;
	}
}
