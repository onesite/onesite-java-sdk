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

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public class Constants
{
	public enum AccountStatus
	{
		PENDING, GOOD_STANDING, DELINQUENT, INACTIVE, DISABLED, DELETED;

		@JsonCreator
		public static AccountStatus fromValue(String value)
		{
			for (AccountStatus status : AccountStatus.values()) {
				if (status.toString().toLowerCase().replace("_", "-").equals(value.toLowerCase())) {
					return status;
				}
			}
			throw new IllegalArgumentException(value);
		}

		@JsonValue
		@Override
		public String toString()
		{
			return super.toString().toLowerCase().replace("_", "-");
		}
	}
	
	public enum Gender
	{
		MALE, FEMALE, UNSPECIFIED;

		@JsonCreator
		public static Gender fromValue(String value)
		{
			for (Gender gender : Gender.values()) {
				if (gender.toString().toLowerCase().equals(value.toLowerCase())) {
					return gender;
				}
			}
			throw new IllegalArgumentException(value);
		}

		@JsonValue
		@Override
		public String toString()
		{
			return super.toString().toLowerCase();
		}
	}

	public enum GroupMemberStatus
	{
		PENDING, MEMBER, MODERATOR, OWNER, INVITED, BANNED;

		@JsonCreator
		public static GroupMemberStatus fromValue(String value)
		{
			for (GroupMemberStatus status : GroupMemberStatus.values()) {
				if (status.toString().toLowerCase().equals(value.toLowerCase())) {
					return status;
				}
			}
			throw new IllegalArgumentException(value);
		}

		@JsonValue
		@Override
		public String toString()
		{
			return super.toString().toLowerCase();
		}
	}

	public enum BirthdayDisplay
	{
		FULL, BIRTHDAY_ONLY, AGE_ONLY, PRIVATE, FRIENDS;

		@JsonCreator
		public static BirthdayDisplay fromValue(String value)
		{
			for (BirthdayDisplay dob : BirthdayDisplay.values()) {
				if (dob.toString().toLowerCase().equals(value.toLowerCase())) {
					return dob;
				}
			}
			throw new IllegalArgumentException(value);
		}

		@JsonValue
		@Override
		public String toString()
		{
			return super.toString().toLowerCase();
		}
	}

	public enum FriendshipApproval
	{
		MANUALLY(0), AUTO_APPROVE(1);

		int value = 1;
		boolean set = false;

		private FriendshipApproval(int value)
		{
			this.value = value;
		}

		@JsonValue
		public int getValue()
		{
			return this.value;
		}
		
		public boolean isSet()
		{
			return this.set;
		}
		
		@Override
		public String toString()
		{
			return String.valueOf(this.getValue());
		}
	}

	public enum CommentsApproval
	{
		MANUALLY(0), AUTO_APPROVE_ALL(1), AUTO_APPROVE_FRIENDS_ONLY(2);

		int value = 1;

		private CommentsApproval(int value)
		{
			this.value = value;
		}
		
		@JsonValue
		public int getValue()
		{
			return this.value;
		}
		
		@Override
		public String toString()
		{
			return String.valueOf(this.getValue());
		}
	}

	public enum MessagePrivacy
	{
		ALLOW_ALL(0), FRIENDS_ONLY(1);

		int value = 0;

		private MessagePrivacy(int value)
		{
			this.value = value;
		}

		@JsonValue
		public int getValue()
		{
			return this.value;
		}
		
		@Override
		public String toString()
		{
			return String.valueOf(this.getValue());
		}
	}

	public enum EmailNotification
	{
		NONE, DAILY, WEEKLY, ONGOING;

		@JsonCreator
		public static EmailNotification fromValue(String value)
		{
			for (EmailNotification notification : EmailNotification.values()) {
				if (notification.toString().toLowerCase().equals(value.toLowerCase())) {
					return notification;
				}
			}
			throw new IllegalArgumentException(value);
		}

		@JsonValue
		@Override
		public String toString()
		{
			return super.toString().toLowerCase();
		}
	}
}
