/**
 * @(#)RestAuthEntryPoint.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes what happens when 
 * 				 users tries to login into spring security
 */

package com.mm.wszib.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("restAuthEntryPoint")
public class RestAuthEntryPoint implements AuthenticationEntryPoint {
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(
			HttpServletRequest request,
			HttpServletResponse response, 
			AuthenticationException authException) throws IOException {
				response.setHeader(HttpHeaders.LOCATION, "Unauthorized");
				response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
		}
	
}