
// Package
package org.centerom.almistack.ui.expressions;

// Imports
import org.eclipse.core.expressions.PropertyTester;

public abstract class BasePropertyTester extends PropertyTester {

	/**
	 * Converts the given expected value to a boolean.
	 * 
	 * @param expectedValue the expected value (may be <code>null</code>).
	 * @return <code>false</code> if the expected value equals Boolean.FALSE,
	 *         <code>true</code> otherwise
	 */
	protected boolean toBoolean(Object expectedValue) {
		// Locals 
		Boolean result = Boolean.TRUE;

		if (expectedValue instanceof Boolean) {
			result = ((Boolean) expectedValue).booleanValue();
		}
		return result;
	}

}
// END <BasePropertyTester> class
// --- -------------------- -----