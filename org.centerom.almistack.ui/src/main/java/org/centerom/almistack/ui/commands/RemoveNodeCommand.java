
// Package
package org.centerom.almistack.ui.commands;

// Imports
import org.centerom.almistack.ui.views.AgileHierarchyView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class RemoveNodeCommand extends AbstractHandler {



	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
//		IEditorPart org.eclipse.ui.IWorkbenchPage.openEditor(IEditorInput input, String editorId) throws PartInitException	// TODO: for opening editor

		// Locals
		ISelection selection    = null;
		AgileHierarchyView view = null;
		Tree tree = null;

		try {
			// 
			selection = HandlerUtil.getCurrentSelection(event);
			
			if (   selection != null
				&& selection instanceof TreeSelection) {

				view = (AgileHierarchyView) PlatformUI.getWorkbench()
										   .getActiveWorkbenchWindow()
										   .getActivePage()
										   .showView(AgileHierarchyView.ID);
				tree = view.getCommonViewer().getTree();

				// Remove selected node
				tree.getSelection()[0].dispose();

//				System.out.println(" -----------------------> " + tree.getItemCount());

				if (tree.getItemCount() > 0) {
					tree.setSelection(tree.getTopItem());
					
					// Perform additional operations	// TODO
					// updateEditor
					// updatedReleasesView();
					// updatedIterationsView();
					// updatedUserStoriesView();
					// updatedTasksView();
				}
				else {
					view.getCommonViewer().setInput(tree);
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


/*			
			selectedProject = (IProject) ((TreeSelection) selection).getFirstElement();

			if (selectedProject != null) {
				String projectMethodologyURL = PrototypePreferences.getMethodologyURL(selectedProject.getName());
				MethodologyUrlDialog dialog = new MethodologyUrlDialog(Display.getDefault().getActiveShell(), projectMethodologyURL == null ? "" : projectMethodologyURL);
				if (dialog.open() == Window.OK) {
					try {
						PrototypePreferences.setMethodologyURL(selectedProject.getName(), dialog.getURL());
					} catch (InvocationTargetException e) {

					}
				}
			}
*/			
			


/*		
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection != null && selection instanceof TreeSelection) {
			
			selectedProject = (IProject) ((TreeSelection) selection).getFirstElement();

			if (selectedProject != null) {
				String projectMethodologyURL = PrototypePreferences.getMethodologyURL(selectedProject.getName());
				MethodologyUrlDialog dialog = new MethodologyUrlDialog(Display.getDefault().getActiveShell(), projectMethodologyURL == null ? "" : projectMethodologyURL);
				if (dialog.open() == Window.OK) {
					try {
						PrototypePreferences.setMethodologyURL(selectedProject.getName(), dialog.getURL());
					} catch (InvocationTargetException e) {

					}
				}
			}
			
		}
*/


}
// END <RemoveNodeCommand> class
// --- ------------------- -----
