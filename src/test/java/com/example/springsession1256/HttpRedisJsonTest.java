/*
 * Copyright 2014-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.springsession1256;

import com.example.springsession1256.pages.HomePage;
import com.example.springsession1256.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Eddú Meléndez
 * @author Vedran Pavic
 */

@ActiveProfiles(profiles = {"test", "json-serialization"})
class HttpRedisJsonTest extends BaseTest {

	@Test
	void goLoginRedirectToLogin() {
		LoginPage login = HomePage.go(this.driver, LoginPage.class);
		login.assertAt();
	}

	@Test
	void goHomeRedirectLoginPage() {
		LoginPage login = HomePage.go(this.driver, LoginPage.class);
		login.assertAt();
	}

	@Test
	void login() {
		LoginPage login = HomePage.go(this.driver, LoginPage.class);
		HomePage home = login.form().login(HomePage.class);
		home.containCookie("SESSION");
		home.doesNotContainCookie("JSESSIONID");
	}

	@Test
	void createAttribute(CapturedOutput capturedOutput) {
		//Fail because of:
		// Could not read JSON:Cannot construct instance of `org.springframework.beans.factory.support.DisposableBeanAdapter` (no Creators, like default constructor, exist):
		// cannot deserialize from Object value (no delegate- or property-based Creator)
		// When DestructionCallbackBindingListenerMixIn is removed from ObjectMapper configuration, then test fails also because of (no Creators, like default constructor, exist)
		testCreateAttribute(capturedOutput);
	}

	@Test
	void logout(CapturedOutput capturedOutput) {
		testLogout(capturedOutput);
	}

}
