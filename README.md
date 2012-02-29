# ONEsite Java Sdk
---
This open source Java library provides a simple interface for you to integrate ONEsite services into any existing Java application. More
in depth guides and API documentation can be found [http://developer.onesite.com](http://developer.onesite.com).


### Requirements
---
A development key is required to interact with any of the ONEsite services. You can register for a development key at [http://www.onesite.com/node/ssoSignup)](http://www.onesite.com/node/ssoSignup).

### Getting Started
---
1. Checkout or untar the onesite-java-sdk
2. Build the project using Maven

	<pre lang="java"><code>
	mvn clean package
	</code></pre>

3. The release tar.gz package is located in the **target/releases** directory


### Configuration
---
The sdk uses a java system properties for global configuration. An example config file containing the necessary properties can be found at **doc/conf/onesite.properties**.


### License
---
Except as otherwise noted, the ONEsite Java SDK is licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
