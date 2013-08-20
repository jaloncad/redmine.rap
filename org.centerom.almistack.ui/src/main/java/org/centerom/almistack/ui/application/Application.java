
// Package
package org.centerom.almistack.ui.application;

// Imports
import org.centerom.almistack.services.logger.ILoggerService;
import org.centerom.almistack.ui.consumers.ServiceConsumer;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;


public class Application implements IApplication {

	// Logger instance
	private ILoggerService logger = ServiceConsumer.getLoggerService();

	@Override
	public Object start(IApplicationContext context) {

		Display display = PlatformUI.createDisplay();

		try {

			int returnCode = PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}			
			return IApplication.EXIT_OK;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			display.dispose();
		}
		return display;
	}

	@Override
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
// END <Application> class
// --- ------------- -----