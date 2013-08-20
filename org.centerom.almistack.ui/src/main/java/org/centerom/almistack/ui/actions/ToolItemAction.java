
// Package
package org.centerom.almistack.ui.actions;

// Imports
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.ui.utils.EditorUtils;
import org.centerom.almistack.ui.utils.ViewUtils;
import org.centerom.almistack.ui.views.PlanView;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;


public class ToolItemAction extends Action implements IWorkbenchAction {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	// Constants
	// ---------
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String ALM_DIALOG_CAPTION  = "Alm-iStack";
	
	// Agile view: actions codes
	private static final int AGILE_VIEW_FOCUS_PROJECT_AC = 22;
	private static final int AGILE_VIEW_OPEN_PLAN_AC     = 23;

	// Releases view: actions codes
	private static final int RELEASES_VIEW_SHOW_ALL_AC          = 1;
	private static final int RELEASES_VIEW_FILTER_BY_PRODUCT_AC = 2;
	private static final int RELEASES_VIEW_FILTER_BY_PROJECT_AC = 3;

	// Iterations view: actions codes
	private static final int ITERATIONS_VIEW_SHOW_ALL_AC          = 4;
	private static final int ITERATIONS_VIEW_FILTER_BY_PRODUCT_AC = 5;
	private static final int ITERATIONS_VIEW_FILTER_BY_PROJECT_AC = 6;
	private static final int ITERATIONS_VIEW_FILTER_BY_RELEASE_AC = 7;

	// User stories view: actions codes
	private static final int USER_STORIES_VIEW_SHOW_ALL_AC            = 8;
	private static final int USER_STORIES_VIEW_FILTER_BY_PRODUCT_AC   = 9;
	private static final int USER_STORIES_VIEW_FILTER_BY_PROJECT_AC   = 10;
	private static final int USER_STORIES_VIEW_FILTER_BY_RELEASE_AC   = 11;
	private static final int USER_STORIES_VIEW_FILTER_BY_ITERATION_AC = 12;

	// Tasks view: actions codes
	private static final int TASKS_VIEW_SHOW_ALL_AC             = 13;
	private static final int TASKS_VIEW_FILTER_BY_PRODUCT_AC    = 14;
	private static final int TASKS_VIEW_FILTER_BY_PROJECT_AC    = 15;
	private static final int TASKS_VIEW_FILTER_BY_RELEASE_AC    = 16;
	private static final int TASKS_VIEW_FILTER_BY_ITERATION_AC  = 17;
	private static final int TASKS_VIEW_FILTER_BY_USER_STORY_AC = 18;

	// Release editor: actions codes
	private static final int PRODUCT_EDITOR_SAVE_CHANGES_AC = 19;
	private static final int PROJECT_EDITOR_SAVE_CHANGES_AC = 20;
	private static final int RELEASE_EDITOR_SAVE_CHANGES_AC = 21;
	
	// Alerts view
	private static final int ALERTS_VIEW_NEW_CONFIGURATION_AC = 24;

	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	private int actionCode = -1;
	
	public ToolItemAction(int actionCode,
			  			  String  id) {
		this.actionCode = actionCode;
		this.setId(id);
	}

	public ToolItemAction(int actionCode,
			  			  String  id,
			  			  String  text) {
		this.actionCode = actionCode;
		this.setId(id);
		this.setText(text);
	}
	
	public ToolItemAction(int actionCode,
						  String  id,
						  Boolean enabled,
						  String  toolTipText,
						  ImageDescriptor imageDescriptor) {
		this.actionCode = actionCode;
		this.setId(id);
		this.setEnabled(enabled);
		this.setToolTipText(toolTipText);
		this.setImageDescriptor(imageDescriptor);
	}

	public ToolItemAction(int actionCode,
			 			  String  id,
						  Boolean enabled,
						  String  toolTipText,
						  String  text) {
		this.actionCode = actionCode;
		this.setId(id);
		this.setEnabled(enabled);
		this.setToolTipText(toolTipText);
		this.setText(text);
	}
	
	public ToolItemAction(int actionCode,
			 			  String  id,
						  Boolean enabled,
						  String  toolTipText,
						  String text,
						  ImageDescriptor imageDescriptor) {
		this.actionCode = actionCode;
		this.setId(id);
		this.setEnabled(enabled);
		this.setToolTipText(toolTipText);
		this.setText(text);
		this.setImageDescriptor(imageDescriptor);
	}

	@Override
	public void run() {

		Shell shell = null;
		
		// Depending of the id, we performed an action 
		switch (actionCode) {
		
			case AGILE_VIEW_FOCUS_PROJECT_AC:
				// TODO: dummy
				shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, ALM_DIALOG_CAPTION, Constants.NOT_AVAILABLE_FEATURE);

				break;
			
			case AGILE_VIEW_OPEN_PLAN_AC:
				// Open plan view
				ViewUtils.openView(PlanView.ID);
				
				break;
		
			case PRODUCT_EDITOR_SAVE_CHANGES_AC:
				// Consolidate product changes in its editor




				break;

			case PROJECT_EDITOR_SAVE_CHANGES_AC:
				// Consolidate project changes in its editor

				
				

				break;
				
			case RELEASE_EDITOR_SAVE_CHANGES_AC:
				// Consolidate release changes in its editor
				EditorUtils.getEditor().doSave(null);
				break;

			case RELEASES_VIEW_SHOW_ALL_AC:
				// TODO: dummy
				shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, ALM_DIALOG_CAPTION, Constants.NOT_AVAILABLE_FEATURE);
		
				break;
			
			case RELEASES_VIEW_FILTER_BY_PRODUCT_AC:
				// TODO: dummy
				shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, ALM_DIALOG_CAPTION, Constants.NOT_AVAILABLE_FEATURE);
			
				break;

			case RELEASES_VIEW_FILTER_BY_PROJECT_AC:
				// TODO: dummy
				shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, ALM_DIALOG_CAPTION, Constants.NOT_AVAILABLE_FEATURE);		
				
				break;
				
			case ALERTS_VIEW_NEW_CONFIGURATION_AC:
				// TODO: dummy
				shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				MessageDialog.openInformation(shell, ALM_DIALOG_CAPTION, Constants.NOT_AVAILABLE_FEATURE);

				break;
		}
	}

	@Override
	public void dispose() {}

}
// END <ToolItemAction> class
// --- ---------------- -----