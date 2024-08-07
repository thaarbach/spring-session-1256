# Demonstrator for #1256
This demonstrator is based on spring-session-samples/spring-session-sample-boot-redis-json
 
The test classes will demonstrate serialization issues when `GenericJackson2JsonRedisSerializer` is used in combination with common Spring Beans (e.g. DestructionCallbackBindingListener). 
These Beans which will be added to the session when using `@SessionScoped` Beans and having a `@PreDestroy` annotated method defined. 

It also demonstrates that the `@PreDestory` annotated method will not be invoked after logout when the default `JdkSerializationRedisSerializer` is used. 
Both case can be reproduced by running the Test or the Application.

## Precondition
The `SessionScopedBean` is initialized by adding an attribute to the session.

## Using `GenericJackson2JsonRedisSerializer`

Run HttpRedisJsonTest, an exception occurs due to missing default c-tors. 
```
java.io.IOException: org.springframework.data.redis.serializer.SerializationException: Could not read JSON:Cannot construct instance of `org.springframework.beans.factory.support.DisposableBeanAdapter` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 185] (through reference chain: org.springframework.web.context.request.DestructionCallbackBindingListener["destructionCallback"]) 
java.lang.RuntimeException: java.io.IOException: org.springframework.data.redis.serializer.SerializationException: Could not read JSON:Cannot construct instance of `org.springframework.beans.factory.support.DisposableBeanAdapter` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); line: 1, column: 185] (through reference chain: org.springframework.web.context.request.DestructionCallbackBindingListener["destructionCallback"]) 

```
Removing the `DestructionCallbackBindingListenerMixIn` from the `ObjectMapper` configuration in `SessionConfig` the error occurs when deserializing the DestructionCallbackBindingListener.

## Using `JdkSerializationRedisSerializer`

Run HomeJdkTest
Here, serialization and deserialization seem to work but the `@PreDestroy` annotated method in the `SessionScopedBean` is not executed.


