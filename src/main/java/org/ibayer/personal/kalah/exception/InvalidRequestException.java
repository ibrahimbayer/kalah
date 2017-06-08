package org.ibayer.personal.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Is used to return http status bad_request "400" for invalid input
 * @author ibrahim.bayer
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

	public InvalidRequestException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7157889451948584556L;

}
