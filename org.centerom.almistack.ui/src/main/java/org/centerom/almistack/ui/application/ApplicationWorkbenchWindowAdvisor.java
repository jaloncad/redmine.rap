
// Package
package org.centerom.almistack.ui.application;

// Imports
import org.centerom.almistack.ui.application.ApplicationActionBarAdvisor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {


	public ApplicationWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer windowConfigurer) {
		super(windowConfigurer);
	}

	@Override
	public void preWindowOpen() {
		//
		IWorkbenchWindowConfigurer configurer = this.getWindowConfigurer();

		configurer.setShowStatusLine(Boolean.TRUE);
		configurer.setShowCoolBar(Boolean.TRUE);
		configurer.setShowPerspectiveBar(Boolean.TRUE);
		configurer.setShowProgressIndicator(Boolean.FALSE);	// Bottom Right section in the status line 
		configurer.setShowFastViewBars(Boolean.TRUE);
		configurer.setShowMenuBar(Boolean.FALSE);
		
		configurer.setShellStyle(SWT.NONE);	// For removing resize top options
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(final IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);	
	}

	@Override
	public void postWindowOpen() {
		getWindowConfigurer().getWindow().getShell().setMaximized(Boolean.TRUE);
	}

}
// END <ApplicationWorkbenchWindowAdvisor> class
// --- ----------------------------------- -----