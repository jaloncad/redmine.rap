package org.centerom.almistack.servicesimpl.connector.redmine.internal.comm;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpRequest;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.comm.handlers.ContentHandler;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineInternalError;

public class RedmineAuthenticator<K> implements Communicator<K> {
	/**
	 * Header value.
	 */
	private String authKey;

	/**
	 * Used charset.
	 */
	private final String charset;

	/**
	 * Peer communicator.
	 */
	private final Communicator<K> peer;

	public RedmineAuthenticator(Communicator<K> peer, String charset) {
		this.peer = peer;
		this.charset = charset;
	}

	public void setCredentials(String login, String password) {
		// TODO: login siempre es NULL
		if (login == null) {
			authKey = null;
			return;
		}
		try {
			// TODO: cambiado a raiz de incorporar el plugin commons codec 1.3 (Redmine usaba el 1.7)
			/*
			authKey = "Basic "
					+ Base64.encodeBase64String(
							(login + ':' + password).getBytes(charset)).trim();
							*/
			authKey = "Basic "
					+ Base64.encodeBase64(
							(login + ':' + password).getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			throw new RedmineInternalError(e);
		}
	}

	@Override
	public <R> R sendRequest(HttpRequest request, ContentHandler<K, R> handler)
			throws RedmineException {
		if (authKey != null)
			request.addHeader("Authorization", authKey);
		return peer.sendRequest(request, handler);
	}

}
