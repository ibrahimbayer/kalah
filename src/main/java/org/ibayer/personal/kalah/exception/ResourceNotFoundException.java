package org.ibayer.personal.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Is used to return http status not_found "404" for resource not found
 * @author ibrahim.bayer
 *
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8798553625865802676L;

}
