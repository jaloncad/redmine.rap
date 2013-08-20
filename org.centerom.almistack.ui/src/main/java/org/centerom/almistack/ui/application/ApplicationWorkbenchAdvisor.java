
// Package
package org.centerom.almistack.ui.application;

// Imports
import java.lang.reflect.Constructor;

import org.centerom.almistack.ui.perspectives.AgilePerspective;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;


public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor {

	// TODO: Make it configurable for Single Sourcing
	private static final String CLASS = "org.centerom.almistack.ui.rap.application.ApplicationWorkbenchWindowAdvisor";
//	private static final String CLASS = "org.centerom.almistack.ui.rcp.application.ApplicationWorkbenchWindowAdvisor";
	
	@Override
	public void initialize(IWorkbenchConfigurer configurer) {
		super.initialize(configurer);
	}

	@Override
	public String getInitialWindowPerspectiveId() {
		return AgilePerspective.ID;
	}

//	@Override
//	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer windowConfigurer) {
//
//		try {
//
//			// Retrieves constructor instance
//			//Constructor<?> constructor = Class.forName(CLASS).getDeclaredConstructor(IWorkbenchWindowConfigurer.class);
//
//			return (WorkbenchWindowAdvisor) constructor.newInstance(windowConfigurer);
//
//		}
//		catch (Exception e) {
//			throw new RuntimeException("Error", e);
//		}
//	}
	
	@Override
	public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		return new ApplicationWorkbenchWindowAdvisor(configurer);
	}

}
// End <ApplicationWorkbenchAdvisor> class
// --- ----------------------------- -----