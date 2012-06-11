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

namespace java com.onesite.sdk.thrift.api
namespace php  OnesiteApi

include "OnesiteDao.thrift"
include "OnesiteConstants.thrift"

struct Status {
	1: i32 code,
	2: string message
}

struct ResponseError {
	1: Status status	
}

struct ResponseString {
	1: Status status,
	2: string content	
}

struct ResponseInt {
	1: Status status,
	2: i32 content	
}

struct ResponseLong {
	1: Status status,
	2: i64 content	
}

struct ResponseBoolean {
	1: Status status,
	2: bool content	
}

struct ResponseSession {
	1: Status status,
	2: OnesiteDao.Session session	
}

struct UserCreateOptions {
	1: bool sendConfirmationEmail,
	2: string referringUrl,
	3: i64 addInitialFriend,
	4: i64 joinInitialGroup,
	5: OnesiteConstants.GroupMemberStatus groupMemberStatus,
	6: string couponCode;
}

struct ResponseUser {
	1: Status status,
	2: OnesiteDao.User user	
}

struct ResponseExternalProperty {
    1: Status status,
    2: OnesiteDao.ExternalProperty externalProperty    
}

struct ResponseExternalAccount {
    1: Status status,
    2: OnesiteDao.ExternalAccount externalAccount    
}
