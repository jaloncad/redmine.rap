
// Package
package org.centerom.almistack.core.services;

//Imports
import java.util.Map;

import org.centerom.almistack.core.services.exceptions.ServiceException;


public interface IService {

	/**
	 * DS method to modify the configuration properties.
	 * This may be called by multiple threads:
	 * configuration administration updates may be processed asynchronously.
	 * This is called by the activate method, and otherwise when
	 * the configuration properties are modified while the component is
	 * activated.
	 * 
	 * @param properties the new configuration properties 
	 */
	abstract void innerInitialize(Map<String, String> properties) throws ServiceException;

}
// End <IOSGIServive> class
// --- -------------- -----