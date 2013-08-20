
// Package
package org.centerom.almistack.ui.editorenhancements;

// Imports
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;
import org.eclipse.ui.IEditorInput;


public class EditorInputConverter extends AbstractParameterValueConverter {

	//  
	Map<String, IEditorInput> map = new HashMap<String, IEditorInput>();

	
	/**
	 * Converts a string encoded command parameter value into the parameter
	 * value object.
	 * 
	 * @param parameterValue
	 *            a command parameter value string describing an object; may be
	 *            <code>null</code>
	 * @return the object described by the command parameter value string; may
	 *         be <code>null</code>
	 * @throws ParameterValueConversionException
	 *             if an object cannot be produced from the
	 *             <code>parameterValue</code> string
	 */
	@Override
	public Object convertToObject(String parameterValue)
						   throws ParameterValueConversionException {
		return (IEditorInput) map.get(parameterValue);
	}

	/**
	 * Converts a command parameter value object into a string that encodes a
	 * reference to the object or serialization of the object.
	 * 
	 * @param parameterValue
	 *            an object to convert into an identifying string; may be
	 *            <code>null</code>
	 * @return a string describing the provided object; may be <code>null</code>
	 * @throws ParameterValueConversionException
	 *             if a string reference or serialization cannot be provided for
	 *             the <code>parameterValue</code>
	 */
	@Override
	public String convertToString(Object parameterValue)
						   throws ParameterValueConversionException {
		// Retrieve parameter as IEditorInput object
		IEditorInput editorInput = (IEditorInput) parameterValue;
		String id = Integer.toString(editorInput.hashCode());

		map.put(id, editorInput);
		
		return id;
	}

}
// END <EditorInputConverter> class
// --- ---------------------- -----