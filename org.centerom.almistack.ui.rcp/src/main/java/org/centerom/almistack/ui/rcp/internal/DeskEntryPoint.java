
//
package org.centerom.almistack.ui.rcp.internal;

//
import org.centerom.almistack.ui.application.ApplicationWorkbenchAdvisor;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;


public class DeskEntryPoint implements IApplication {

	public Object start(IApplicationContext context) {

		Display display = PlatformUI.createDisplay();

		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		}
		finally {
			display.dispose();
		}

/*		
		new ApplicationWorkbench().createUI();
		return new Integer(0);
*/
	}

	public void stop() {

		if (PlatformUI.isWorkbenchRunning()) {
			
			final IWorkbench workbench = PlatformUI.getWorkbench();
			final Display display      = workbench.getDisplay();

			display.syncExec(new Runnable() {

				public void run() {
					if (!display.isDisposed()) {
						workbench.close();
					}
				}
			});
		}
	}
	
	
	
	
}
