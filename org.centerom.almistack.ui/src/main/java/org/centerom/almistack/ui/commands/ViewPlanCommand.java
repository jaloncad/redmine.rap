package org.centerom.almistack.ui.commands;

import java.lang.reflect.InvocationTargetException;

import org.centerom.almistack.domain.beans.Release;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class ViewPlanCommand extends AbstractHandler {

	private Release release = null;

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		

	
//		event.
		System.out.println("execute invoqued !!!");
		
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (   selection != null
			&& selection instanceof TreeSelection) {
			
			
//			((TreeSelection) selection)
			
			System.out.println("in");
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
			
		}

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
			
		
		return null;
	}

}
