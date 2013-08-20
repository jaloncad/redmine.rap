package org.centerom.almistack.servicesimpl.connector.redmine.internal.comm;

import org.apache.http.HttpRequest;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.comm.handlers.ContentHandler;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;

public interface Communicator<K> {

	/**
	 * Performs a request.
	 * 
	 * @return the response body.
	 */
	public abstract <R> R sendRequest(HttpRequest request,
			ContentHandler<K, R> contentHandler) throws RedmineException;

}