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
package com.example.springsession1256.session;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author thaarbach
 */
@SessionScope
@Service
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public class SessionScopedBean implements Serializable {

	Logger logger = LoggerFactory.getLogger(SessionScopedBean.class);

	private final Map<String, Object> map;

	public SessionScopedBean() {
		this.map = new ConcurrentHashMap<>();
	}

	@PostConstruct
	public void init() {
		logger.info("################################### SessionScopedBean - PostConstruct ###################################");
	}

	public void setSomeValue(String key, Object value) {
		logger.info("################################### SessionScopedBean - added value ###################################");
		map.put(key, value);
	}

	public Object getSomeValue(String key) {
		logger.info("################################### SessionScopedBean - get value ###################################");
		return map.get(key);
	}

	@PreDestroy
	public void after() {
		logger.info("################################### SessionScopedBean - PreDestroy ###################################");
	}
}
