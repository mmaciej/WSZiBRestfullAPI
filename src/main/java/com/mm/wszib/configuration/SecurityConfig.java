/**
 * @(#)SecurityConfig.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class with security descriptions
 */

package com.mm.wszib.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.mm.wszib.other.UserRestConstants;
import com.mm.wszib.security.MySuccessHandler;
import com.mm.wszib.security.RestAuthEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private RestAuthEntryPoint raep;
 
    @Autowired
    private MySuccessHandler msh;
	
    @Autowired
	private BCryptPasswordEncoder bCryptEncoder;

	@Autowired
	private DataSource ds;
	
	@Value("${spring.queries.users-query}")
	private String usersQuery;
	
	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
    
    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) 
    		throws Exception {
    	
    	auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(ds)
				.passwordEncoder(bCryptEncoder);
    	
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception { 
    	http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(raep)
        .and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.GET,UserRestConstants.GET_API)
        		.hasAnyAuthority(UserRestConstants.USER_ROLE,UserRestConstants.ADMIN_ROLE)
	        .antMatchers(HttpMethod.DELETE,UserRestConstants.DELETE_ALL_USERS)
	        	.hasAuthority(UserRestConstants.ADMIN_ROLE)
	        .antMatchers(HttpMethod.GET,UserRestConstants.GET_USER)
	        	.access("@userServiceImpl.checkUserName(authentication,#username)")
	        .antMatchers(HttpMethod.PATCH,UserRestConstants.EDIT_USER)
	        	.access("@userServiceImpl.checkUserName(authentication,#username)")
	        .antMatchers(HttpMethod.DELETE,UserRestConstants.DELETE_USER)
	        	.hasAuthority(UserRestConstants.ADMIN_ROLE)
        .and()
	        .formLogin().loginProcessingUrl(UserRestConstants.LOGIN)
	        .successHandler(msh)
	        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        .and()
        	.logout().logoutUrl(UserRestConstants.LOGOUT);
    }
    
    @Bean
    public MySuccessHandler mySuccessHandler(){
        return new MySuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler(){
    	return new SimpleUrlAuthenticationFailureHandler();
    }
      
    
}
