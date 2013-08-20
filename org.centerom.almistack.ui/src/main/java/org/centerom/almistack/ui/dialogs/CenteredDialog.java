
// Package
package org.centerom.almistack.ui.dialogs;

// Imports
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class CenteredDialog extends Dialog {

	// Defatult serial Id
	private static final long serialVersionUID = 1L;


	protected CenteredDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Sets dialog on the center of the monitor
	 */
	protected void setDialogLocation() {
		Rectangle monitorArea = getShell().getDisplay().getPrimaryMonitor().getBounds();
		Rectangle shellArea = getShell().getBounds();
		int x = monitorArea.x + (monitorArea.width - shellArea.width) / 2;
		int y = monitorArea.y + (monitorArea.height - shellArea.height) / 2;
		getShell().setLocation(x, y);
	}

}
// END <CenteredDialog> class
// --- ---------------- -----