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
package com.onesite.sdk.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTestCase
{
	protected final Logger log = LoggerFactory.getLogger(AbstractTestCase.class);

	@Rule
	public MethodRule watchman = new TestWatchman()
	{
		public void starting(FrameworkMethod method)
		{
			log.info("Test: {}", method.getName());
		}
	};

	@BeforeClass
	public static void setUpServer()
	{
		System.out.println("Log4j Config: " + System.getProperty("log4j.configuration"));
	}

	/**
	 * Helper function to load a resource file from the class path
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public byte[] readResourceFile(String file) throws IOException
	{
		log.debug("Loading " + file);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		InputStream fin = getClass().getClassLoader().getResourceAsStream(file);

		int b = 0;
		while ((b = fin.read()) >= 0) {
			bout.write(b);
		}
		fin.close();
		bout.close();

		return bout.toByteArray();
	}
}
