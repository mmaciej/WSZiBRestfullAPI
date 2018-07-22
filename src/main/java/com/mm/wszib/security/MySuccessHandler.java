/**
 * @(#)MySuccessHandler.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes what happens after successfull login
 * 				 managed by spring security
 */

package com.mm.wszib.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.mm.wszib.other.UserRestConstants;

public class MySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private RequestCache requestCache = new HttpSessionRequestCache();
	 
    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
    @Override
    public void onAuthenticationSuccess(
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		Authentication authentication) throws ServletException, IOException {
    			
    			response.setHeader(HttpHeaders.LOCATION, UserRestConstants.GET_API);
    			
		        SavedRequest savedRequest
		          = requestCache.getRequest(request, response);
		        
		        if (savedRequest == null) {
		            clearAuthenticationAttributes(request);
		            return;
		        }
		        String targetUrlParam = getTargetUrlParameter();
		        if (isAlwaysUseDefaultTargetUrl()
		          || (targetUrlParam != null
		          && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
		            requestCache.removeRequest(request, response);
		            clearAuthenticationAttributes(request);
		            return;
		        }
		 
		        clearAuthenticationAttributes(request);
    }
 
    /**
     * @param requestCache
     */
    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
	
}
