
// Package
package org.centerom.almistack.ui.commands;

// Imports
import java.util.ArrayList;
import java.util.Collection;

import org.centerom.almistack.ui.utils.EditorUtils;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;


public class OpenEditorCommand extends AbstractHandler {

	// Command Id
	public final static String COMMAND_CNF_ID = "org.centerom.almistack.ui.commands.openEditor";


	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Locals
		Collection<?> collection  = null;
		EvaluationContext evalCtx = null;

		try {
			evalCtx      = (EvaluationContext) event.getApplicationContext();
			collection   = (Collection<?>) evalCtx.getDefaultVariable();

			// Open element editor
			EditorUtils.openEditor(new ArrayList(collection).get(0));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			return null;
		}
	}

}
// END <OpenEditorCommand> class
// --- ------------------- -----