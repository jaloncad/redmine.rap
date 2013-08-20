package org.centerom.almistack.servicesimpl.connector.redmine.internal.comm;


import org.apache.http.HttpRequest;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.comm.handlers.ContentHandler;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;

/**
 * Basic transport simplifier.
 * 
 * @author maxkar
 * 
 */
final class BasicSimplifier<K, T> implements SimpleCommunicator<K> {
	private final ContentHandler<T, K> contentHandler;
	private final Communicator<T> peer;

	public BasicSimplifier(ContentHandler<T, K> contentHandler,
			Communicator<T> peer) {
		this.contentHandler = contentHandler;
		this.peer = peer;
	}

	@Override
	public K sendRequest(HttpRequest request) throws RedmineException {
		return peer.sendRequest(request, contentHandler);
	}

}
