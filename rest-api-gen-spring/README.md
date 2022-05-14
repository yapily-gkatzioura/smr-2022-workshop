# Rest API using Yapily #

## Setup the maven project ##

### Generate using Archetype ###

```shell
mvn archetype:generate -DgroupId=com.yapily -DartifactId=yapily-smr-2022-rest -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

### Adding Spring Reactor ###

```shell

    <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.6.7</version>
      <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
      <java.version>11</java.version>
    </properties>
  
    <dependencies>
        ...
    	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webflux</artifactId>
			<version>2.6.4</version>
		</dependency>
		<dependency>
			<groupId>com.yapily.api</groupId>
			<artifactId>yapily-sdk</artifactId>
			<version>1.0.0</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis-reactive</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
		</dependency>
		...
    </dependencies>

  	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

```

## Adding The Java code ##

### Main Method ###

[App](com.yapily.App.java)
```java
@SpringBootApplication
public class App 
{

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```

### Adding Yapily Configuration ###

[YapilyConfiguration](com.yapily.config.YapilyConfiguration.java)
```java
@Configuration
public class YapilyConfiguration {

    @Bean
    public ApiClient apiClient(@Value("${yapily.api.key}") String key, @Value("${yapily.api.secret}") String secret) {
        ApiClient defaultClient = new ApiClient();
        defaultClient.setUsername(key);
        defaultClient.setPassword(secret);
        defaultClient.setBasePath("https://api.yapily.com");
        return defaultClient;
    }

    @Bean
    public AuthorisationsApi authorisationsApi(ApiClient apiClient) {
        return new AuthorisationsApi(apiClient);
    }

    @Bean
    public PaymentsApi paymentsApi(ApiClient apiClient) {
        return new PaymentsApi(apiClient);
    }

}
```

### Adding Redis Configuration ###

[YapilyConfiguration](com.yapily.config.RedisConfiguration.java)
```java
@Configuration
public class RedisConfiguration {

    public RedisConfiguration(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Bean
    public ReactiveRedisTemplate<String, PaymentRequest> pendingPaymentTemplate(ObjectMapper objectMapper, ReactiveRedisConnectionFactory connectionFactory) {
        Jackson2JsonRedisSerializer<PaymentRequest> serializer = new Jackson2JsonRedisSerializer<>(PaymentRequest.class);
        serializer.setObjectMapper(objectMapper);
        RedisSerializationContext.RedisSerializationContextBuilder<String, PaymentRequest> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, PaymentRequest> context = builder.hashValue(serializer).build();
        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

}
```

### Adding Repository ###

[PaymentRepository](com.yapily.repository.PaymentRepository.java)
```java

@Repository
public class PaymentRepository {

    public static final String PENDING_KEY = "pending";
    @Autowired
    private ReactiveRedisTemplate<String, PaymentRequest> pendingPaymentTemplate;

    public Mono<Boolean> storePending(String id, PaymentRequest paymentRequest) {
        return pendingPaymentTemplate.<String, PaymentRequest>opsForHash().put(PENDING_KEY, id, paymentRequest);
    }

    public Mono<PaymentRequest> getPending(String consent) {
        return pendingPaymentTemplate.<String, PaymentRequest>opsForHash().get(PENDING_KEY, consent);
    }
    
}
```

### Adding Repository ###
