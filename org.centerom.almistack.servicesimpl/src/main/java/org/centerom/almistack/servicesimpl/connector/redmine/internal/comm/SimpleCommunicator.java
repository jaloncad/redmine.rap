package org.centerom.almistack.servicesimpl.connector.redmine.internal.comm;

import org.apache.http.HttpRequest;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;

/**
 * Simple communicator interface.
 * 
 * @author maxkar
 * 
 */
public interface SimpleCommunicator<T> {
	/**
	 * Performs a request.
	 * 
	 * @return the response body.
	 */
	public abstract T sendRequest(HttpRequest request) throws RedmineException;

}
