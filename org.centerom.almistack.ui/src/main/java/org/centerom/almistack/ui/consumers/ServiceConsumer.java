
// Package
package org.centerom.almistack.ui.consumers;
// Imports
import org.centerom.almistack.services.connector.IRepositoryConnectorService;
import org.centerom.almistack.services.logger.ILoggerService;


public class ServiceConsumer {

	// ALM OSGI services
	// --- ---- --------
	private static IRepositoryConnectorService _connector;	// Connector system
	private static ILoggerService _logger;					// Logger system

	
	/**
	 * Used by DS to register the repository connector service.
	 * 
	 * @param service the service
	 */
	public synchronized void registerConnectorSrv(IRepositoryConnectorService service) {

		System.out.println("Service was set via DS !!!");

		_connector = service;
	}

	/**
	 * Used by DS to unregister the repository connector service.
	 * 
	 * @param service the service
	 */
	public synchronized void unregisterConnectorSrv(IRepositoryConnectorService service) {

		System.out.println("Service was unset via DS !!!");

		_connector = service;
	}
	
	/**
	 * Used by DS to register the logger service.
	 * 
	 * @param service the service
	 */
	public synchronized void registerLoggerSrv(ILoggerService service) {

		System.out.println("Service was set via DS !!!");

		_logger = service;
	}

	/**
	 * Used by DS to unregister the logger service.
	 * 
	 * @param service the service
	 */
	public synchronized void unregisterLoggerSrv(ILoggerService service) {

		System.out.println("Service was unset via DS !!!");

		_logger = service;
	}

	public static IRepositoryConnectorService getRepositoryConnectorService() {
        return _connector;
    }
	
	public static ILoggerService getLoggerService() {
        return _logger;
    }

}
// END <ServiceConsumer> class
// --- ----------------- -----