
// Package
package org.centerom.almistack.ui.utils;

//Imports
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.centerom.almistack.core.Utils;
import org.eclipse.swt.widgets.DateTime;


public class ControlUtils {

	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String DATE_PATTERN_ES = "dd/MM/yyyy";
	private static final String DATE_PATTERN_EN = "MM/dd/yyyy";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	
	public static String getDateTimeStringValue(DateTime dateTime) {
		// Locals
		StringBuilder sbDateTime = new StringBuilder();

		sbDateTime.append(dateTime.getYear());
		sbDateTime.append(dateTime.getMonth());
		sbDateTime.append(dateTime.getDay());

		return sbDateTime.toString();		
	}
	
	public static Date getDateTimeDateValue(DateTime dateTime) {
		// Locals
		Calendar instance = Calendar.getInstance();
		String strDate    = null;

		instance.set(Calendar.DAY_OF_MONTH, dateTime.getDay());
		instance.set(Calendar.MONTH, dateTime.getMonth());
		instance.set(Calendar.YEAR, dateTime.getYear());

		strDate = new SimpleDateFormat(DATE_PATTERN_ES).format(instance.getTime());
		
		return Utils.parseStringToDate(strDate);
	}

}
// END <ControlUtils> class
// --- -------------- -----