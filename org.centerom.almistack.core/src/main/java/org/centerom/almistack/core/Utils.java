
// Package 
package org.centerom.almistack.core;

// Imports
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


/**
 * @version 0.1 - Utils
 */
public class Utils {

	//
	private static Boolean flag = Boolean.FALSE;
	
	
	public static Boolean getFlag() {
		return flag;
	}
	
	public static void setFlag(Boolean newFlag) {
		flag = newFlag;
	}

	/**
	 * Builds a link expression as "<a>value</a>".
	 * 
	 * @param value the value
	 * @return the link expression.
	 */
	public static String createLinkableExpression(String value) {
		// 
		StringBuilder result = new StringBuilder(Constants.LINK_NODE_START);
		result.append(getValue(value));
		result.append(getValue(Constants.LINK_NODE_END));
		
		return result.toString();
	}
	
	public static String getLinkableExpressionValue(String value) {
		// 
		return value.substring(value.indexOf(Constants.LINK_NODE_START)
										   + Constants.LINK_NODE_START.length(),
							   value.indexOf(Constants.LINK_NODE_END));
	}

	/**
	 * Retrieves the index of <itemToFind> in <items> list. 
	 * 
	 * @param items list of items
	 * @param itemToFind item to find in the list
	 * @return the index
	 */
	public static int findIndex(String[] items, String itemToFind) {
		return Arrays.asList(items).indexOf(itemToFind);
	}

	/**
	 * Checks whether data is informed or not.
	 *
	 * @param data the data to check
	 * @return <True> if data is informed, <False> otherwise
	 */
	public static Boolean isEmpty(String data) {
		return (   data == null
				|| data.equalsIgnoreCase(Constants.EMPTY_CHAR)
				|| data.length() <= 0);
	}
	
	public static Boolean isEmpty(Integer data) {
		Boolean result = Boolean.FALSE;
		try {
			result = isEmpty(data.toString());
		}
		catch (NullPointerException e) {
			result = Boolean.TRUE;
		}
		finally {
			return result;
		}
	}

	/**
	 * Build a URL.
	 * 
	 * @param theUrl the URL as string 
	 * @return the URL
	 */
	public static URL buildURL(String theUrl) {
    	// Locals
    	URL url = null;

    	if (!Utils.isEmpty(theUrl)) {
    		try {
				url = new URL(theUrl);
			}
    		catch (MalformedURLException e) {}
    	}
    	return url;
    }

	/**
	 * Clean value (trim white spaces and return empty string in case null value).
	 *
	 * @param the data to clean
	 * @return the cleaned value
	 */
	public static String getValue(String data) {
		String value = null;
		try {
			value = data.trim(); 
		}
		catch (NullPointerException e) {
			value = Constants.EMPTY_CHAR;
		}
		finally {
			return value;
		}
	}
	
	/**
	 * Clean float value (trim white spaces and return empty string in case null value). 
	 *
	 * @param the float data to clean
	 * @return the cleaned value as string
	 */
	public static String getValue(Float data) {
		String value = Constants.EMPTY_CHAR;

		if (data != null) {
			value = String.valueOf(data);
		}
		return value;
	}

	/**
	 * Parse string value to float one.
	 * 
	 * @param data the data as string value
	 * @return the string value as Float
	 */
	public static Float parseStringToFloat(String data) {
		Float value = null;
		try {
			value = Float.parseFloat(Utils.getValue(new String(data)));
		}
		catch (NumberFormatException e) {}
		finally {
			return value;
		}
	}
	
	/**
	 * Parse float value to string one.
	 * 
	 * @param data the data as float value
	 * @return the float value as string
	 */
	public static String parseFloatToString(Float data) {
    	// 
    	String value = Utils.getValue(data);
    	
    	if (Utils.isEmpty(value)) {
    		value = Constants.DASH;
    	}
    	return value;
    }

	// TODO: missing English language scenario
	public static String parseDateToString(Date date) {
		return new SimpleDateFormat(Constants.DATE_PATTERN_ES).format(date);
	}

	public static Date parseStringToDate(String strDate) {
		// Locals
		SimpleDateFormat
			dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN_ES);
		Date date      = null;

		try {
			date = dateFormat.parse(strDate);
		}
		catch (ParseException e) {
			// TODO
			e.printStackTrace();
		}
		finally {
			return date;
		}
	}

	// ...
	
	
	

	

}
// END <Utils> class
// --- ------- -----