
// 
package org.centerom.almistack.servicesimpl.connector.redmine.exceptions;

// 
import org.centerom.almistack.core.services.exceptions.ServiceException;


public class RedmineConnectorServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;


	public RedmineConnectorServiceException(String message) {
		super(message);
	}


	public RedmineConnectorServiceException(Throwable throwable) {
		super(throwable);
	}


	public RedmineConnectorServiceException(String message, Throwable throwable) {
	    super(message, throwable);
	}

}
// END <RedmineConnectorServiceException> class
// --- ---------------------------------- -----