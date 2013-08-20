package org.centerom.almistack.servicesimpl.connector.redmine.internal.comm.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.centerom.almistack.servicesimpl.connector.redmine.internal.comm.BasicHttpResponse;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineCommunicationException;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;

/**
 * "Copy bytes" handler.
 * 
 * @author maxkar
 * 
 */
public final class CopyBytesHandler implements ContentHandler<BasicHttpResponse, Void> {

	private final OutputStream outStream;

	public CopyBytesHandler(OutputStream outStream) {
		this.outStream = outStream;
	}

	@Override
	public Void processContent(BasicHttpResponse content)
			throws RedmineException {
		final byte[] buffer = new byte[4096 * 4];
		int readed;
		try {
			final InputStream input = content.getStream();
			try {
				while ((readed = input.read(buffer)) > 0)
					outStream.write(buffer, 0, readed);
			} finally {
				input.close();
			}
		} catch (IOException e) {
			throw new RedmineCommunicationException(e);
		}
		return null;
	}
}
