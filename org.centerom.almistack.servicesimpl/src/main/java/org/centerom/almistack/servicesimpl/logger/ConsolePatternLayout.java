
// Package
package org.centerom.almistack.servicesimpl.logger;

// Imports
import java.text.SimpleDateFormat;
import java.util.Date;

import org.centerom.almistack.core.Constants;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;


public class ConsolePatternLayout extends LayoutBase<ILoggingEvent> {

	@Override
	public String doLayout(ILoggingEvent event) {
		// Locals
		StackTraceElement[] elements = event.getCallerData();
		StringBuffer sbOutput = new StringBuffer(128);
		Date date             = new Date(event.getTimeStamp());
		SimpleDateFormat sdf  = new SimpleDateFormat(Constants.TIME_PATTERN_MS);

		// Starts buffering
		sbOutput.append(sdf.format(date));
		sbOutput.append(Constants.BLANK);
		sbOutput.append(event.getLevel());
		sbOutput.append(Constants.BLANK);
		sbOutput.append(Constants.LEFT_BRACKET);
		sbOutput.append(event.getThreadName());
		sbOutput.append(Constants.RIGHT_BRACKET);
		sbOutput.append(Constants.BLANK);
		sbOutput.append(elements[1].getClassName());		
		sbOutput.append(Constants.LEFT_BRACKET);
		sbOutput.append(elements[1].getMethodName());
		sbOutput.append(Constants.RIGHT_BRACKET);
		sbOutput.append(Constants.BLANK);
		sbOutput.append(Constants.DASH);
		sbOutput.append(Constants.BLANK);
		sbOutput.append(event.getFormattedMessage());
		sbOutput.append(Constants.BREAK);

		return sbOutput.toString();
	}

}
// END <ConsolePatternLayout> class
// --- ---------------------- -----