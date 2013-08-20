
// Package
package org.centerom.almistack.ui.actions;

// Imports
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public class DropFocusAction extends Action implements IWorkbenchAction {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	// Constants
	// ---------
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String ALM_DIALOG_CAPTION = "Alm-iStack";

	// ^
	// |
	// TODO: remove when configuration and internationalization is available


	public DropFocusAction(String  text) {
		setText(text);
	}

	public Boolean execute() {
		// Locals
		Boolean result = Boolean.FALSE;
		Shell shell    = PlatformUI.getWorkbench()
								   .getActiveWorkbenchWindow()
								   .getShell();

		result = MessageDialog.openConfirm(shell,
										   ALM_DIALOG_CAPTION,
										   getText());
		return result;
	}
	
	@Override
	public void dispose() {}

}
// END <DropFocusAction> class
// --- ----------------- -----