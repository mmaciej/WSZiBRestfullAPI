/**
 * @(#)CustomUri.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: Class which describes Custom Uri returned by controllers
 */

package com.mm.wszib.other;

public class CustomUri {
	
	private final String uri;
	private final String action;
	
	public CustomUri(String uri, String action) {
		this.uri = uri;
		this.action = action;
	}

	public String getUri() {
		return uri;
	}

	public String getAction() {
		return action;
	}
	
}
