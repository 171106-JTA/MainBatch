package com.revature.server.session.require;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.exception.RequestException;

public final class Require {
	public static void requireSelf(Request request) throws RequestException {
		requireQuery(new String[] { UserInfo.USERID }, new String[] { Long.toString(request.getUserId()) }, request);
	}
	
	public static void requireAllQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	public static void requireAllQuery(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireAllTransaction(String[] params, Request request) throws RequestException {
		validateAll(params, request.getTransaction(), request);
	}
	
	public static void requireAllTransaction(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	public static void requireQuery(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	public static void requireTransaction(String[] params, Request request) throws RequestException {
		validate(params, request.getTransaction(), request);
	}
	
	public static void requireTransaction(String[] params, String[] values, Request request) throws RequestException {
		validate(params, values, request.getQuery(), request);
	}
	
	public static void require(Checkpoint[] checkpoints, Request request) throws RequestException {
		int index = Collections.indexOfSubList(Arrays.asList(checkpoints), Arrays.asList(new Checkpoint[] {request.getCheckpoint()}));
		
		// If we did not find checkpoint
		if (index < 0) {
			String message = "Request requires at least one of the following checkpoints=" + Arrays.asList(checkpoints);
			throw new RequestException(request, message);
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private static void validateAll(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request missing params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static void validateAll(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);

		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request has invalid values for params=[" + String.join(",", errors) + "] ";
			throw new RequestException(request, message);
		}
	}
	
	private static void validate(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() == params.length) {
			String message = "Request requires at least one of the following params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static void validate(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);
		
		// Do we have any errors?
		if (errors.size() == values.length) {
			String message = "Request requires at least one of the following params with corret value=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	private static List<String> validateRequestParams(String[] params, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		
		// Check if request has required parameters 
		for (String param : params) {
			if (!requestParams.containsKey(param)) {
				errors.add(param);
			}
		}
		
		return errors;
	}
	
	private static List<String> validateRequestParamValues(String[] params, String[]values, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		Iterator<String> itParams;
		Iterator<String> itValues;
		String param;
		String value;
		
		// Server should not run if required parameters not properly entered.
		assert params.length == values.length;
		
		itParams = Arrays.asList(params).iterator();
		itValues = Arrays.asList(params).iterator();
		
		// Check if request has required parameters 
		for (;itParams.hasNext();) {
			param = itParams.next();
			value = itValues.next();
			
			// Check if parameter missing
			if (requestParams.containsKey(param)) {
				// Check if value valid 
				if (!value.equals(requestParams.get(param))) 
					errors.add(param);
			}
		}
		
		return errors;
	}
	
}
