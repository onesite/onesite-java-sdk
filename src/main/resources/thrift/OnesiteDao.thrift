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

namespace java com.onesite.sdk.thrift.dao
namespace php  OnesiteDao

include "OnesiteConstants.thrift"

struct Password {
	1: string password,
	2: optional bool encoded = false
}

struct Site {
	1: i64 id,
	2: string subdir,
	3: string url
}

struct ExternalAccount {
	1: string providerName,
	2: string userIdentifier,
	3: string accessToken
}

struct ExternalProperty {
	1: string name,
	2: string type,
	3: optional string value
}

struct Profile {
	1: i64 birthday,
	2: string firstName,
	3: string lastName,
	4: OnesiteConstants.Gender gender,
	5: string address,
	6: string address2,
	7: string city,
	8: string state,
	9: string zip,
	10: string country,
	11: string location,
	12: i64 timezone,
	13: string phone,
	14: string quote,
	15: string locale

}

struct Preferences {
	1: OnesiteConstants.BirthdayDisplay birthdayDisplay,
	2: OnesiteConstants.FriendshipApproval friendshipApproval,
	3: OnesiteConstants.CommentsApproval commentsApproval,
	4: OnesiteConstants.MessagePrivacy messagePrivacy,
	5: OnesiteConstants.EmailNotification emailNotification,
	6: bool searchable,
	7: bool showOnlineStatus 
}

struct User {
	1: i64 id,
	2: string email,
	3: string username,
	4: string displayName,
	5: string avatar,
	6: OnesiteConstants.AccountStatus accountStatus,
	7: Site site,
	8: list<ExternalAccount> externalAccounts,
	9: Profile profile,
	10: Preferences preferences, 
}

struct Session {
	1: string coreU,
	2: string coreX,
	3: string accessToken,
	4: map<string,string> sessionData,
	5: string status,
	6: string ip,
	7: string agent,
	8: i64 expiresTime,
	9: User user
}
