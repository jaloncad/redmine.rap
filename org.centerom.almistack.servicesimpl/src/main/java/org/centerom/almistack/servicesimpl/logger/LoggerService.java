
// Package
package org.centerom.almistack.servicesimpl.logger;

// Imports
import java.util.Map;

import org.centerom.almistack.services.logger.ILoggerService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;


public class LoggerService implements ILoggerService {

	// Configuration parameters
	// ------------- ----------
	// Support logging modes 
	private Boolean consoleMode     = null;
	private Boolean fileMode        = null;
	private Boolean rollingFileMode = null;	// Not implemented neither configured: TODO
	private Level consoleLevel      = null;
	private Level fileLevel         = null;
	private Level rollingFileLevel  = null;	// Not implemented neither configured: TODO
	
	

	// Member variables
	// ------ ---------
	private Logger logger  = null;
	private LoggerContext loggerContext = null;
	private StatusManager statusManager = null;


	/* --------------------------------------------------------------------------------------------
	   DS methods to configure component
	   == ======= == ========= ========= */

	/**
	 * DS method to activate this component. If this method exists,
	 * it will be invoked when the component is activated.
	 * Note: this method should not be visible for other classes.
	 *
	 * @param cContext component context
	 * @param properties map containing service & configuration properties
	 */
	protected void activate(ComponentContext cContext, Map<String, String> properties) {
		innerInitialize(properties);
	}
	
	/**
	 * DS method to deactivate this component. If this method exists,
	 * it will be invoked when the component is deactivated.
	 * Note: this method should not be visible for other classes.
	 * 
	 * @param cContext component context
	 * @param reason the reason why component is stopping
	 */
	protected void deactivate(ComponentContext cContext, int reason) {
		disposeLogger();
	}
	
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
	public synchronized void innerInitialize(Map<String, String> properties) {
	
		try {

			// Read properties configuration
			consoleMode      = Boolean.valueOf(properties.get("mode.console"));
			fileMode         = Boolean.valueOf(properties.get("mode.file"));
			rollingFileMode  = Boolean.valueOf(properties.get("mode.rollingFile"));
			consoleLevel     = Level.valueOf(properties.get("console.level"));
			fileLevel        = Level.valueOf(properties.get("file.level"));
			rollingFileLevel = Level.valueOf(properties.get("rollingFile.level"));
			
			
			/*
Level. ();
					file.level        = Level Level.WARN
					rollingFile.level = Level.WARN		
//			private Level fileLevel         = null;
//			private Level rollingFileLevel  = null;	
			*/
			configureAppenders();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configureAppenders() {
		// Locals
		ConsoleAppender<ILoggingEvent> consoleAppender     = null;
		FileAppender<ILoggingEvent> fileAppender           = null;
		ConsoleAppender<ILoggingEvent> rollingFileAppender = null;
		LayoutWrappingEncoder<ILoggingEvent> encoder       = new LayoutWrappingEncoder<ILoggingEvent>();
		ConsolePatternLayout consolePatternLayout          = new ConsolePatternLayout();

		loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		logger        = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
		statusManager = loggerContext.getStatusManager();

		// We are not interested in auto-configuration
		loggerContext.reset();
		encoder.setContext(loggerContext);

		// Console mode?
		if (consoleMode.toString().equalsIgnoreCase(Boolean.TRUE.toString())) {
			// Yes; create a console appender
			consoleAppender      = new ConsoleAppender<ILoggingEvent>(); 
			consoleAppender.setContext(loggerContext);

//		    encoder.setPattern("%d{HH:mm:ss:SSS} [%thread] %-4level %logger{0} - %message%n");
			encoder.setLayout(consolePatternLayout);
		    encoder.start();

/*
			PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		    encoder.setContext(loggerContext);
		    encoder.setPattern("%d{HH:mm:ss:SSS} [%thread] %-4level %logger{0} - %message%n");
		    encoder.start();
			PatternLayout pl = new PatternLayout();
			pl.setContext(loggerContext);
			pl.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n");
			pl.start();
*/
			consoleAppender.setEncoder(encoder);
			consoleAppender.start();

			logger.addAppender(consoleAppender);
			logger.setLevel(consoleLevel);
		}

		// File mode?
		if (fileMode == Boolean.TRUE) {
/*
			FileAppender<LoggingEvent> fileAppender = logger.g .getAppender("file");
			FileAppender fileAppender = (FileAppender) logger.getAppender("file");
			
			
	       fileAppender.stop();
	       fileAppender.setFile("D:\\Alm-iStack\\logs");
	       PatternLayout pl = new PatternLayout();
	       pl.setPattern("%d %5p %t [%c:%L] %m%n)");
	       pl.setContext(loggerContext);
	       pl.start();
	       fileAppender.setLayout(pl);
	       fileAppender.setContext(loggerContext);
	       fileAppender.start();
	       
	       		
		<property name="LOG_PATH" value="D:\\Alm-iStack\\logs"/>
		   
		<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
			<!--
				 encoders are assigned the type
	        	 ch.qos.logback.classic.encoder.PatternLayoutEncoder by default
			-->
			<encoder>
				<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{5} - %msg%n</pattern>
			</encoder>
		</appender>

	<!-- 
		File appender
	-->
		<appender name="FILE" class="ch.qos.logback.core.FileAppender">
			<file>${LOG_PATH}/Alm-iStack.log</file>
			<layout class="ch.qos.logback.classic.PatternLayout">
				<Pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</Pattern>
			</layout>
		</appender>
*/
		}
		// Rolling file mode?
		if (rollingFileMode == Boolean.TRUE) {
			// TODO
		}
		// Print logger context status
		StatusPrinter.print(loggerContext);
	}

	/**
	 * Free logger.
	 */
    private void disposeLogger() {
    	logger        = null;
    	loggerContext = null;
    	statusManager = null;
    }

	/**
	 * Print into the standard out the status of the logger.
	 */
	public void printStatus() {
		StatusPrinter.print(loggerContext);
	}

	public StatusManager getStatusManager() {
		return statusManager;
	}

	public LoggerContext getLoggerContext() {
		return loggerContext;
	}

	/*	--------------------------------------------------------------------------------------------
		DEBUG level
		===== ===== */

	public void debug(final String msg) {
		if(logger.isDebugEnabled()) {
			logger.debug(msg);
		}
	}

	public void debug(final String msg, Throwable e) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg, e);
		}
	}

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void debug(String msg, String...params) {
		if (logger.isDebugEnabled()) {
			logger.debug(msg, params);
		}
	}

	/*	--------------------------------------------------------------------------------------------
		INFO debug level
		==== ===== ===== */

	public void info(final String msg) {
		if (logger.isInfoEnabled()) {
			logger.info(msg);
		}
	}

	public void info(final String msg, Throwable e) {
		if (logger.isInfoEnabled()) {
			logger.info(msg, e);
		}
	}

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void info(final String msg, String...params) {
		if (logger.isInfoEnabled()) {
			logger.info(msg, params);
		}
	}

	/*	--------------------------------------------------------------------------------------------
		ERROR debug level
		==== ===== ===== */

	public void error(final String msg) {
		if (logger.isErrorEnabled()) {
			logger.error(msg);
		}
	}

	public void error(final String msg, Throwable e) {
		if (logger.isErrorEnabled()) {
			logger.error(msg, e);
		}
	}

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void error(final String msg, String... params) {
		if (logger.isErrorEnabled()) {
			logger.error(msg, params);
		}
	}

	/*	--------------------------------------------------------------------------------------------
		WARN level
		===== ===== */

	public void warn(final String msg) {
		if (logger.isWarnEnabled()) {
			logger.warn(msg);
		}
	}

	public void warn(final String msg, Throwable e) {
		if (logger.isWarnEnabled()) {
			logger.warn(msg, e);
		}
	}

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void warn(final String msg, String... params) {
		if(logger.isWarnEnabled()) {
			logger.warn(msg, params);
		}
	}

	/*	--------------------------------------------------------------------------------------------
		TRACE level
		====== ===== */

	public void trace(final String msg) {
		if (logger.isTraceEnabled()) {
			logger.trace(msg);
		}
	}

	public void trace(final String msg, Throwable e) {
		if (logger.isTraceEnabled()) {
			logger.trace(msg, e);
		}
	}

	/**
	* example: x.debug("Entry number: {} is {}", i, entry[i]);
	* @param msg
	* @param params
	*/
	public void trace(final String msg, String... params) {
		if (logger.isTraceEnabled()) {
			logger.trace(msg, params);
		}
	}

}
// END <LoggerService> class
// --- --------------- -----