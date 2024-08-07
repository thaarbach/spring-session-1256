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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;
/**
 *  @author thaarbach
 */
@Component
public class SimpleSessionListener {
	Logger logger = LoggerFactory.getLogger(SimpleSessionListener.class);

	public SimpleSessionListener() {
		logger.info("####################### SimpleSessionListener created #######################");
	}

	@EventListener
	public void sessionCreated(SessionCreatedEvent event) {
		logger.info("####################### Session created ####################### ");
	}

	@EventListener
	public void sessionDestroyed(SessionDestroyedEvent event) {
		logger.info("#######################  Session destroyed ####################### ");
	}

	@EventListener
	public void sessionExpired(SessionExpiredEvent event) {
		logger.info("#######################  Session expired ####################### ");
	}

	public void sessionDeleted(SessionDeletedEvent event) {
		logger.info("#######################  Session deleted ####################### ");
	}
}
