
// Package
package org.centerom.almistack.ui.commands;

// Imports
import org.centerom.almistack.ui.dialogs.RenameDialog;
import org.centerom.almistack.ui.editors.ProductEditor;
import org.centerom.almistack.ui.editors.ProjectEditor;
import org.centerom.almistack.ui.editors.ReleaseEditor;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;


public class RenameLinkCommand extends AbstractHandler {

	// Command Id
	public final static String COMMAND_ID   = "org.centerom.almistack.ui.commands.renameLink";
	public final static String PARAMETER_ID = "org.centerom.almistack.ui.commands.renameLink.nameParameter";
	

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Locals
		RenameDialog dialog = null;
		String paramValue   = null;
		IEditorPart editor  = null;

		try {
			//
			paramValue = event.getParameter(PARAMETER_ID);

			editor = PlatformUI.getWorkbench()
							   .getActiveWorkbenchWindow()
							   .getActivePage()
							   .getActiveEditor();

			dialog = new RenameDialog(Display.getDefault()
											 .getActiveShell(),
									  paramValue);
			// 
			if (dialog.open() == Window.OK) {
				// 
				paramValue  = dialog.getTag();
//				editorInput = editor.getEditorInput();

				if (editor instanceof ProductEditor) {
					// Update ...
					((ProductEditor) editor).setProjectName(paramValue); 
				}
				else if (editor instanceof ProjectEditor) {

				}
				else if (editor instanceof ReleaseEditor) {
					// Update ...
					((ReleaseEditor) editor).setProjectName(paramValue);
				}

				// Update repository: invoke OSGI service for doing it ... TODO
/*				
				if (editorInput instanceof ProductEditorInput) {
					// Update editor input and editor widget
					ProductEditorInput productEditorInput = (ProductEditorInput) editorInput;
					productEditorInput.getProduct().setName(paramValue);
					((ProductEditor) editor).setProjectName(paramValue); 
				}
				else if (editorInput instanceof ProjectEditorInput) {

				}
				else if (editorInput instanceof ReleaseEditorInput) {
					ReleaseEditorInput releaseEditorInput = (ReleaseEditorInput) editorInput;
					releaseEditorInput.getRelease().getOwner().setName(paramValue);
				}

				// Update repository: invoke OSGI service for doing it ... TODO
*/ 

			}
		}
		finally {
			return null;	
		}
	}

}
// END <RenameLinkCommand> class
// --- ------------------- -----