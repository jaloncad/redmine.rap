
// Package
package org.centerom.almistack.core;

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import org.centerom.almistack.core.Constants;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;


/**
 * @version 0.1 - ConfigurationUtil
 */
public class ConfigurationUtil {
	
	// Constants
	
	
	// Singleton Pattern; instance reference
	private static ConfigurationUtil instance = null;
		
	// Member variables
	private MessageSource msgSource     = null;	// Message source
	private Locale currentLocale        = null;	// Locale
	

	/**
	 *  Empty constructor; object creation is not allowed.
	 */
	private ConfigurationUtil() {}
		
	/**
	 *  Parameterized constructor; object creation is not allowed.
	 */
	private ConfigurationUtil(Locale locale, MessageSource theMsgSource) {
		currentLocale = (locale!=null)?locale:Locale.getDefault();
		msgSource     = theMsgSource;
	}
	
	/**
	 * 
	 * 
	 * @param msgSource
	 * @param locale
	 */
	public static void initialize (Locale locale, MessageSource msgSource) {
		instance = new ConfigurationUtil(locale, msgSource);
	}

	/**
	 * Factory method. Returns the instance for this class.
	 *
	 * @return ConfigurationUtil - the instance of ConfigurationUtil
	 */
	public static ConfigurationUtil getInstance() {
		return instance;
	}
	
	
	public static void configureLog() {
		// 
		URL url = null;
		JoranConfigurator jConfig = new JoranConfigurator();
		LoggerContext logContext  = (LoggerContext) LoggerFactory.getILoggerFactory();

		try {

			jConfig.setContext(logContext);
			logContext.reset();
			// override default configuration
			// inject the name of the current application as "application-name" property of the LoggerContext
		
			// context.putProperty("application-name", NAME_OF_CURRENT_APPLICATION);
		
			url = new URL("platform:/plugin/org.centerom.almistack.common/logging/logback.xml");

			jConfig.doConfigure(url.openConnection().getInputStream());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (JoranException e) {
			e.printStackTrace();
		}
	}
	
	
	 
	
	
	
	public String getMainEntryPoint() {
		return ConfigurationProps.getInstance().getMainEntryPoint();
	}

	
	public String getRedmineHost() {
		return ConfigurationProps.getInstance().getRedmineHost();
	}
	
	public String getRedmineApiAccessKey() {
		return ConfigurationProps.getInstance().getRedmineApiAccessKey();
	}
	
					
					
	
	/**
	 * Returns the message corresponding to the given key. Default locale EN.
	 * @param key key for the message.
	 * @return the message, 'undefined' if the key is not present.
	 */
	public String getMessage(String key){
		String result = null;
		try {
			result =getMessage(key, currentLocale);
		} catch (Exception e) {
			result = Constants.UNDEFINED;
		}
		return result;
	}
	
	/**
	 * Returns the message corresponding to the given key.
	 * @param key key for the message.
	 * @param loc Locale for the message.
	 * @return the message, 'undefined' if the key is not present.
	 */
	public String getMessage(String key, Locale loc){
		String result = null;
		try {
			result = getMessage(key, null, loc);
		} catch (Exception e) {
			result = Constants.UNDEFINED;
		}
		return result;
	}
	
	/**
	 * Returns the message corresponding to the given key. Default locale EN.
	 * @param key key for the message.
	 * @param args arguments for substitution in the string.
	 * @return the message, 'undefined' if the key is not present.
	 */
	public String getMessage(String key, Object[] args){
		String result = null;
		try {
			result = getMessage(key, args, currentLocale);
		} catch (Exception e) {
			result = Constants.UNDEFINED;
		}
		return result;
	}
	
	/**
	 * Returns the message corresponding to the given key.
	 *
	 * @param key - key for the message.
	 * @param loc - Locale for the message.
	 * @param args - arguments for substitution in the string.
	 * @return String - the message, 'undefined' if the key is not present.
	 */
	public String getMessage(String key, Object[] args,  Locale loc) {
		String result = null;
		try {
			result = msgSource.getMessage(key, args, loc);

		}
		catch (Exception e) {
			result = Constants.UNDEFINED;
		}
		return result;
	}

	/**
	 * @return the currentLocale
	 */
	public Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * @param currentLocale the currentLocale to set
	 * @throws Exception 
	 */
	public void setCurrentLocale(Locale currentLocale) throws Exception {

		if (currentLocale != null) {
			this.currentLocale = currentLocale;
		}
		else {
			throw new Exception("Trying to set an invalid Locale");
		}
	}
	
	
	private static class ConfigurationProps {
		
		// Class instance; singleton pattern
		private static ConfigurationProps instance = new ConfigurationProps();
		    
		// Member variables
		private String mainEntryPoint = null;
		private String redmineHost    = null;
		private String redmineApiAccessKey = null;
			

		public static ConfigurationProps getInstance() {
			return instance;
		}

		public String getMainEntryPoint() {
			return instance.mainEntryPoint;
		}

		@SuppressWarnings("unused")
		public void setMainEntryPoint(String value) {
	    	instance.mainEntryPoint = value;
	    }

		public String getRedmineHost() {
			return instance.redmineHost;
		}

		@SuppressWarnings("unused")
		public void setRedmineHost(String value) {
	    	instance.redmineHost = value;
	    }

		public String getRedmineApiAccessKey() {
			return instance.redmineApiAccessKey;
		}

		@SuppressWarnings("unused")
	    public void setRedmineApiAccessKey(String value) {
	    	instance.redmineApiAccessKey = value;
	    }

	}

}
//
// 
