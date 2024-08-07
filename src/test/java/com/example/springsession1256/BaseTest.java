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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.webdriver.MockMvcHtmlUnitDriverBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 *  @author thaarbach
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
public abstract class BaseTest {
	protected static final Logger log = LoggerFactory.getLogger(HttpRedisJsonTest.class);
	protected WebDriver driver;
	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		this.driver = MockMvcHtmlUnitDriverBuilder.mockMvcSetup(this.mockMvc).build();
	}

	@AfterEach
	void tearDown() {
		this.driver.quit();
	}

	protected void testLogout(CapturedOutput capturedOutput) {
		LoginPage login = HomePage.go(this.driver, LoginPage.class);
		HomePage home = login.form().login(HomePage.class);
		home.containCookie("SESSION");
		home.form().submit(HomePage.class); //click submit to invoke SecurityControllerAdvice
		log.info("####################### Click logout #######################");
		home.logout();

		assertThat(capturedOutput.getOut()).contains("####################### Session created #######################");
		assertThat(capturedOutput.getOut()).contains("####################### Click logout #######################");
		assertThat(capturedOutput.getOut()).contains("#######################  Session destroyed #######################");
		assertThat(capturedOutput.getOut()).contains("####################### Session created #######################");
	}

	protected void testCreateAttribute(CapturedOutput capturedOutput) {
		LoginPage login = HomePage.go(this.driver, LoginPage.class);
		HomePage home = login.form().login(HomePage.class);


		// @formatter:off
		home = home.form()
				.attributeName("Demo Key")
				.attributeValue("Demo Value")
				.submit(HomePage.class);
		// @formatter:on

		List<HomePage.Attribute> attributes = home.attributes();
		assertThat(attributes).extracting("attributeName").contains("Demo Key");
		assertThat(attributes).extracting("attributeValue").contains("Demo Value");
		assertThat(capturedOutput.getOut()).contains("####################### Session created #######################");
		assertThat(capturedOutput.getOut()).contains("################################### SessionScopedBean - added value ###################################");
		assertThat(capturedOutput.getOut()).contains("################################### SessionScopedBean - added value ###################################");
		assertThat(capturedOutput.getOut()).contains("################################### SessionScopedBean - PreDestroy ###################################");
		home.logout();

	}
}
