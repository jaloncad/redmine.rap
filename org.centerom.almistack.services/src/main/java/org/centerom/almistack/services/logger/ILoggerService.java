
// Package

// Package
package org.centerom.almistack.services.logger;

//Imports
import org.centerom.almistack.core.services.IService;




public interface ILoggerService extends IService {

	/*	--------------------------------------------------------------------------------------------
		DEBUG level
		===== ===== */

	public void debug(final String msg);

	public void debug(final String msg, Throwable e);

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void debug(String msg, String...params);

	/*	--------------------------------------------------------------------------------------------
		INFO debug level
		==== ===== ===== */

	public void info(final String msg);

	public void info(final String msg, Throwable e);

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void info(final String msg, String...params);

	/*	--------------------------------------------------------------------------------------------
		ERROR debug level
		==== ===== ===== */

	public void error(final String msg);

	public void error(final String msg, Throwable e);

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void error(final String msg, String... params);

	/*	--------------------------------------------------------------------------------------------
		WARN level
		===== ===== */

	public void warn(final String msg);

	public void warn(final String msg, Throwable e);

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void warn(final String msg, String... params);

	/*	--------------------------------------------------------------------------------------------
		TRACE level
		====== ===== */

	public void trace(final String msg);

	public void trace(final String msg, Throwable e);

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void trace(final String msg, String... params);	
	
}
// END <ILoggerService> class
// --- ---------------- -----