package com.polovni_automobili.auta.config;

import com.polovni_automobili.auta.service.implementation.MyUserDetailsService;
import com.polovni_automobili.auta.service.implementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true, jsr250Enabled = true, prePostEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPointy unauthorizedHandler;






    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean( BeanIds.AUTHENTICATION_MANAGER )
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder( 15 );
    }





    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers(  "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js" ).permitAll()
                .antMatchers( "/admin/**" ).hasRole("ADMIN")                    //.permitAll()
                .antMatchers("/users/login", "/users/register").permitAll()
                .antMatchers("/public/**").permitAll()

                .anyRequest().authenticated();
        http.addFilterBefore( jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class );



        //.httpBasic()
               // .and()
               // .authorizeRequests()
               // .antMatchers("/users/**").hasRole("ADMIN");

                //.antMatchers("/users/**").hasAnyAuthority("ADMIN", "USER")
                //.antMatchers("/admin/**").hasAnyAuthority("ADMIN");
                //.anyRequest().authenticated()
                //.and()
               // .exceptionHandling().accessDeniedPage("/403");

    }
}




















// @Bean
// public UserDetailsService userDetailsService(){
// return new UserServiceImplementation();
// }

//@Bean
//public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//}

//@Bean
//public DaoAuthenticationProvider authenticationProvider() {
//  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//authProvider.setUserDetailsService(userDetailsService());
//authProvider.setPasswordEncoder(passwordEncoder());

//return authProvider;
//}
