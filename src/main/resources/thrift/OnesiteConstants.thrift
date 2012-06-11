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

namespace java com.onesite.sdk.thrift.dao.constants
namespace php  OnesiteConstants

enum AccountStatus {
	PENDING, 
	GOOD_STANDING, 
	DELINQUENT, 
	INACTIVE, 
	DISABLED, 
	DELETED
}

enum Gender {
	MALE, 
	FEMALE, 
	UNSPECIFIED
}

enum BirthdayDisplay {
	FULL,
	BIRTHDAY_ONLY,
	AGE_ONLY,
	OWNER,
	FRIENDS
}

enum FriendshipApproval {
	MANUALLY,
	AUTO_APPROVE
} 

enum CommentsApproval { 
	MANUALLY,
	AUTO_APPROVE_ALL,
	AUTO_APPROVE_FRIENDS_ONLY
}

enum MessagePrivacy {
	ALLOW_ALL,
	FRIENDS_ONLY
}

enum EmailNotification {
	NONE,
	DAILY,
	WEEKLY,
	ONGOING
}

enum GroupMemberStatus {
	PENDING, 
	MEMBER, 
	MODERATOR, 
	OWNER, 
	INVITED, 
	BANNED
}
