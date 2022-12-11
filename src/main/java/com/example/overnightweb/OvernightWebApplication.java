package com.example.overnightweb;


import com.example.common.entity.RoleUser;
import com.example.common.entity.StatusSeller;
import com.example.common.entity.User;
import com.example.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.overnightweb.*", "com.example.common.*"})
@EntityScan(basePackages = "com.example.common.*")
@EnableJpaRepositories(basePackages ="com.example.common.*" )
@EnableAsync
public class OvernightWebApplication implements CommandLineRunner{


        @Autowired
        private UserRepository userRepository;

        public static void main(String[] args) {
            SpringApplication.run(OvernightWebApplication.class, args);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Override
        public void run(String... args) throws Exception {
            Optional<User> byEmail = userRepository.findByEmail("admin@gmail.com");
            if (byEmail.isEmpty()) {
                userRepository.save(User.builder()
                        .name("admin")
                        .surname("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder().encode("admin"))
                        .phoneNumber("098444444")
                        .role(RoleUser.ADMIN)
                        .status(StatusSeller.ACTIVE)
                        .build());
            }

        }
    }