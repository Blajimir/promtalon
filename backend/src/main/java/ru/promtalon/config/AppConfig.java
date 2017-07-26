package ru.promtalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.promtalon.util.NumberCodeGenerator;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Md5PasswordEncoder md5PasswordEncoder(){
        return new Md5PasswordEncoder();
    }
    @Bean
    public NumberCodeGenerator numberCodeGenerator(){
        return new NumberCodeGenerator();
    }
}
