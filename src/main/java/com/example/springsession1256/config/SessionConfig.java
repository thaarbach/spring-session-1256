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
package com.example.springsession1256.config;

import com.example.springsession1256.mixin.DestructionCallbackBindingListenerMixIn;
import com.example.springsession1256.session.SessionScopedBean;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.web.context.request.DestructionCallbackBindingListener;

/**
 * @author jitendra on 3/3/16.
 * @author thaarbach
 */
@Configuration
public class SessionConfig implements BeanClassLoaderAware {

	private ClassLoader loader;

	/**
	 * Using the JdkSerializationRedisSerializer will work without serialization errors but the @PreDestroy
	 * annotated method in {@link SessionScopedBean} is not invoked
	 */
	@Bean
	@Profile("json-serialization")
	public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
		return new GenericJackson2JsonRedisSerializer(objectMapper());
	}

	/*
	 * @see
	 * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang
	 * .ClassLoader)
	 */
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.loader = classLoader;
	}

	/**
	 * Customized {@link ObjectMapper} to add mix-in for class that doesn't have default
	 * constructors
	 *
	 * @return the {@link ObjectMapper} to use
	 */
	private ObjectMapper objectMapper() {
		//Don't know why that is needed, should be added by org.springframework.security.jackson2.SecurityJackson2Modules.java:202
		final BasicPolymorphicTypeValidator polymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
				.allowIfSubType(Object.class)
				.build();

		final JsonMapper build = JsonMapper.builder()
				.activateDefaultTyping(polymorphicTypeValidator, ObjectMapper.DefaultTyping.NON_FINAL_AND_ENUMS, JsonTypeInfo.As.PROPERTY)
				// Enable DestructionCallbackBindingListenerMixIn will fix DestructionCallbackBindingListener is not in the allowlist.
				// https://github.com/spring-projects/spring-security/issues/4370
				.addMixIn(DestructionCallbackBindingListener.class, DestructionCallbackBindingListenerMixIn.class)
				.addModules(SecurityJackson2Modules.getModules(this.loader))
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.build();

		return build;
	}

}
