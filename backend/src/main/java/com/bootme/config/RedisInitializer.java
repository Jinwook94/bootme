package com.bootme.config;

//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisServerCommands;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@Component
//@Profile("dev")
//public class RedisInitializer implements CommandLineRunner {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//
//    public RedisInitializer(RedisTemplate<String, Object> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public void run(String... args) {
//        flushRedisDatabase();
//    }
//
//    private void flushRedisDatabase() {
//        Optional.ofNullable(redisTemplate.getConnectionFactory())
//                .map(RedisConnectionFactory::getConnection)
//                .ifPresent(RedisServerCommands::flushDb);
//    }
//
//}
