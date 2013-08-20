
// Package
package org.centerom.almistack.ui.commands;

// Imports
import org.centerom.almistack.ui.dialogs.RenameDialog;
import org.centerom.almistack.ui.views.AgileHierarchyView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;


public class RenameNodeCommand extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Locals
		ISelection selection    = null;
		AgileHierarchyView view = null;
		RenameDialog dialog     = null;
		Tree tree      = null;
		String nodeTag = null;

		try {
			// 
			selection = HandlerUtil.getCurrentSelection(event);
			
			if (   selection != null
				&& selection instanceof TreeSelection) {

				view = (AgileHierarchyView) PlatformUI.getWorkbench()
										   .getActiveWorkbenchWindow()
										   .getActivePage()
										   .showView(AgileHierarchyView.ID);
				// Selected node tag
				tree    = view.getCommonViewer().getTree();
				nodeTag = tree.getSelection()[0].getText();
				dialog  = new RenameDialog(Display.getDefault()
												  .getActiveShell(),
										   nodeTag);
				// 
				if (dialog.open() == Window.OK) {
					// Set new value: on the tree
					nodeTag = dialog.getTag();

					tree.getSelection()[0].setText(nodeTag);
					
					// Update repository: invoke OSGI service for doing it ... TODO
				}
			}
		}
		catch (PartInitException e) {
			e.printStackTrace();
		}
		finally {
			return null;	
		}
	}

}
// END <RenameNodeCommand> class
// --- ------------------- -----