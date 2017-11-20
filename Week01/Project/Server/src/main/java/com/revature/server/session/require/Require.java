package com.revature.server.session.require;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

/**
 * Used to verify request has certain fields/values before request is processed 
 * @author Antony Lulciuc
 */
public final class Require {
	/**
	 * Determines if query and transaction both have user session id 
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireSelf(Request request) throws RequestException {
		String id = Long.toString(request.getUserId());

		// query ID must apply to self
		requireQuery(new String[] { Info.USERID, User.ID }, new String[] { id, id }, request);
		
		// transaction ID must apply to self
		requireQuery(new String[] { Info.USERID, User.ID }, new String[] { id, id }, request);
	}
	
	/**
	 * Determines if query has user session id
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireSelfQuery(Request request) throws RequestException {
		String id = Long.toString(request.getUserId());
		
		// query ID must apply to self
		requireQuery(new String[] { Info.USERID, User.ID }, new String[] { id, id }, request);
	}
	
	/**
	 * Determines if transaction has user session id
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireSelfTransaction(Request request) throws RequestException {
		String id = Long.toString(request.getUserId());
		
		// transaction ID must apply to self
		requireQuery(new String[] { Info.USERID, User.ID }, new String[] { id, id }, request);
	}
	
	/**
	 * Requires query to have all fields specified 
	 * @param params - what request must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAllQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	/**
	 * Requires query to have all values specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAllQuery(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	/**
	 * Requires transaction to have all fields specified 
	 * @param params - what request must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAllTransaction(String[] params, Request request) throws RequestException {
		validateAll(params, request.getTransaction(), request);
	}
	
	/**
	 * Requires transaction to have all values specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAllTransaction(String[] params, String[] values, Request request) throws RequestException {
		validateAll(params, values, request.getQuery(), request);
	}
	
	/**
	 * Requires query to have at least one value specified for fields defined 
	 * @param params - what request must have
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireQuery(String[] params, Request request) throws RequestException {
		validateAll(params, request.getQuery(), request);
	}
	
	/**
	 * Requires query to have at least one value specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireQuery(String[] params, String[] values, Request request) throws RequestException {
		validate(params, values, request.getQuery(), request);
	}
	
	/**
	 * Requires transaction to have at least one value specified for fields defined 
	 * @param params - what request must have
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireTransaction(String[] params, Request request) throws RequestException {
		validate(params, request.getTransaction(), request);
	}
	
	/**
	 * Requires transaction to have at least one value specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireTransaction(String[] params, String[] values, Request request) throws RequestException {
		validate(params, values, request.getTransaction(), request);
	}
	
	/**
	 * Requires data param to have at least one value specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param data - what to check
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	public static void require(String[] params, String[] values, FieldParams data, Request request) throws RequestException {
		validate(params, values, data, request);
	}
	
	/**
	 * Requires query and transaction to have at least one field defined 
	 * @param params - fields to check
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void require(String[] params, Request request) throws RequestException {
		requireQuery(params, request);
		requireTransaction(params, request);
	}
	
	/**
	 * Requires query and transaction to have at least one value specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void require(String[] params, String[] values, Request request) throws RequestException {
		requireQuery(params, values, request);
		requireTransaction(params, values, request);
	}
	
	/**
	 * Requires query and transaction to have all fields
	 * @param params - fields to check
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAll(String[] params, Request request) throws RequestException {
		requireAllQuery(params, request);
		requireAllTransaction(params, request);
	}
	
	/**
	 * Requires query and transaction to have all values specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireAll(String[] params, String[] values, FieldParams data, Request request) throws RequestException {
		validateAll(params, values, data, request);
	}
	
	/**
	 * Requires session to have at least one checkpoint defined 
	 * @param checkpoints - checkpoints to check
	 * @param request - what to check
	 * @throws RequestException
	 */
	public static void requireCheckpoint(String[] checkpoints, Request request) throws RequestException {
		int index = Collections.indexOfSubList(Arrays.asList(checkpoints), Arrays.asList(new String[] {request.getCheckpoint()}));
		
		// If we did not find checkpoint
		if (index < 0) 
			throw new RequestException(request, "You do not have to required permissions to perform this action");
	}
	
	/**
	 * Requires session to have at least one checkpoint defined 
	 * @param checkpoints - checkpoints to check
	 * @param checkpoint - value assigned to session
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	public static void requireCheckpoint(String[] checkpoints, String checkpoint, Request request) throws RequestException {
		int index = Collections.indexOfSubList(Arrays.asList(checkpoints), Arrays.asList(new String[] { checkpoint.toLowerCase() }));
		
		// If we did not find checkpoint
		if (index < 0) 
			throw new RequestException(request, "You do not have to required permissions to perform this action");
	}
	
	/**
	 * Determines if record with query params already exists 
	 * @param table - what to perform query on
	 * @param query - used to determine if instance already exists 
	 * @param request - used to tie to session
	 * @throws RequestException - If records exist
	 */
	public static void requireUnique(String table, FieldParams query, Request request) throws RequestException {
		if (Server.database.select(table, query).size() > 0)
			throw new RequestException(request, "Data with values specified already exist!");
	}
	
	/**
	 * Determines if record with query params already exists 
	 * @param table - what to perform query on
	 * @param query - used to determine if instance already exists 
	 * @param request - used to tie to session
	 * @throws RequestException - If no records exist
	 */
	public static void requireExists(String table, FieldParams query, Request request) throws RequestException {
		if (Server.database.select(table, query).size() == 0)
			throw new RequestException(request, "Data with values specified does not exist!");
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Requires data to have all fields specified
	 * @param params - fields to check
	 * @param requestParams - what to check
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	private static void validateAll(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request missing params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	/**
	 * Requires data to have all values specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param requestParams - what to check
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	private static void validateAll(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);

		// Do we have any errors?
		if (errors.size() > 0) {
			String message = "Request has invalid values for params=[" + String.join(",", errors) + "] ";
			throw new RequestException(request, message);
		}
	}
	
	/**
	 * Requires data to have at least one field specified 
	 * @param params - fields to check
	 * @param requestParams - what to check
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	private static void validate(String[] params, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParams(params, requestParams);
		
		// Do we have any errors?
		if (errors.size() == params.length) {
			String message = "Request requires at least one of the following params=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	/**
	 * Requires data to have at least one value specified for fields defined 
	 * @param params - fields to check
	 * @param values - values they must have
	 * @param requestParams - what to check
	 * @param request - used to tie to session
	 * @throws RequestException
	 */
	private static void validate(String[] params, String[] values, FieldParams requestParams, Request request) throws RequestException {
		List<String> errors = validateRequestParamValues(params, values, requestParams);
		
		// Do we have any errors?
		if (errors.size() == values.length) {
			String message = "Request requires at least one of the following params with currect value=[" + String.join(",", errors) + "]";
			throw new RequestException(request, message);
		}
	}
	
	/**
	 * Performs validation against fields
	 * @param params - what fields data must have defined 
	 * @param requestParams - what to check
	 * @return list of params that did not match 
	 */
	private static List<String> validateRequestParams(String[] params, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		String value;
		
		// Request parameters are REQUIRED
		if (requestParams == null) 
			errors.addAll(Arrays.asList(params));
		else {
			// Check if request has required parameters 
			for (String param : params) {
				if (!requestParams.containsKey(param)) {
					errors.add(param);
				} else {
					if ((value = requestParams.get(param)) == null || value.length() == 0)
						errors.add(param + " <cannot be null or empty!>");
				}
			}
		}
		return errors;
	}
	
	/**
	 * Performs validation against field values 
	 * @param params - what fields data must have defined 
	 * @param values - what fields must equal to pass
	 * @param requestParams - what to check
	 * @return list of params that did not match 
	 */
	private static List<String> validateRequestParamValues(String[] params, String[]values, FieldParams requestParams) {
		List<String> errors = new ArrayList<>();
		Iterator<String> itParams;
		Iterator<String> itValues;
		String param;
		String value;
		
		// Request parameters are REQUIRED
		if (requestParams == null) 
			errors.addAll(Arrays.asList(params));
		else {
			// Server should not run if required parameters not properly entered.
			assert params.length == values.length;
			
			itParams = Arrays.asList(params).iterator();
			itValues = Arrays.asList(values).iterator();
			
			// Check if request has required parameters 
			for (;itParams.hasNext();) {
				param = itParams.next();
				value = itValues.next();
				
				// Check if parameter not missing
				if (requestParams.containsKey(param)) {
					// Check if value valid 
					if (!value.equals(requestParams.get(param))) 
						errors.add(param);
				} else {
					// failed if not found
					errors.add(param);
				}
			}
		}
		
		return errors;
	}
	
}
