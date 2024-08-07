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
package com.example.springsession1256.mixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
/**
 *  @author thaarbach
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) //needed to serialize private field in DestructionCallbackBindingListener
public abstract class DestructionCallbackBindingListenerMixIn {
	/**
	 * Create a new DestructionCallbackBindingListener for the given callback.
	 *
	 * @param destructionCallback the Runnable to execute when this listener
	 *                            object gets unbound from the session
	 */
	@JsonCreator
	public DestructionCallbackBindingListenerMixIn(@JsonProperty("destructionCallback") Runnable destructionCallback) {
	}


}
