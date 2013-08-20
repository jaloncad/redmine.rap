
// Package
package org.centerom.almistack.ui.utils;

// Imports
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;


public class CommandUtils {


	public static void executeCommand(String commandId) {
		//
	    try {
	    	((IHandlerService) PlatformUI.getWorkbench()
	    								 .getService(IHandlerService.class))
	    								 .executeCommand(commandId, null);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	public static void executeCommand(String commandId, Map<String, Object> parameters) {
		// Locals
		ParameterizedCommand customizeCommand = null;
    	Command command = null;

	    try {
	    	// 
	    	command = ((ICommandService) PlatformUI.getWorkbench()
	    										   .getService(ICommandService.class))
	    										   .getCommand(commandId);
	    	// Generate customize command
	    	customizeCommand = ParameterizedCommand.generateCommand(command, parameters);

	    	// Execute the customize command
	    	((IHandlerService) PlatformUI.getWorkbench()
					 					 .getService(IHandlerService.class))
					 					 .executeCommand(customizeCommand, null);
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

}
// END <CommandUtils> class 
// --- -------------- -----