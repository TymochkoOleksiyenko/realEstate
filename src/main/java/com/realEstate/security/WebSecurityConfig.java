package com.realEstate.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    @Bean
    public SpringSecurityDialect securityDialect() {
            return new SpringSecurityDialect();
        }

   @Bean
   public PasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/admin/**").permitAll()
               .antMatchers("/user/flats/**").permitAll()
               .antMatchers("/**").permitAll()
               .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("mail")
                    .permitAll()
               .and()
                    .csrf().disable();
    }



    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("charlie")
                .password(getPasswordEncoder().encode("charlie"))
                .roles("ADMIN")
                .and()
                .passwordEncoder(getPasswordEncoder());
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select mail, password, true from users where mail=?")
                .authoritiesByUsernameQuery("select mail, role from users where mail=?")
                .passwordEncoder(getPasswordEncoder());
    }
}























