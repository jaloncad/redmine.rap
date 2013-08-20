
// Package
package org.centerom.almistack.ui.views;

// Imports
import java.util.HashMap;
import java.util.Map;

import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.ui.actions.ToolItemAction;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.centerom.almistack.ui.utils.EditorUtils;
import org.centerom.almistack.ui.utils.ViewUtils;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;


public class AgileHierarchyView extends CommonNavigator {

	// View Id
	public final static String ID = "org.centerom.almistack.ui.views.agileHierarchyView";

	// TODO: remove when configuration and internationalization is available
	// |
	// v 
	private static final String PRODUCT_DESCRIPTION = "Displaying product releases.";
	private static final String PROJECT_DESCRIPTION = "Displaying project releases.";

	// Tool bar view: actions codes
	private static final int AGILE_VIEW_FOCUS_PROJECT_AC = 22;
	private static final int AGILE_VIEW_OPEN_PLAN_AC     = 23;
	// Tool bar view: identifiers
	private static final String AGILE_VIEW_FOCUS_PROJECT_ID = "agile_view_focus_prokect_id";
	private static final String AGILE_VIEW_OPEN_PLAN_ID     = "agile_view_open_plan_id";
	
	// Tool bar view: labels
	private static final String FOCUS_PROJECT_LABEL = "Focus on selected project";
	// Tool bar view: tool tips texts
	private static final String FOCUS_PROJECT_TOOL_TIP_TEXT = "Focus the selected project";
	private static final String OPEN_PLAN_TOOL_TIP_TEXT     = "Open plan of selected project";

	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	// 
	private CommonViewer treeViewer        = null;
	private IMenuManager menuManager       = null;
	private IToolBarManager toolBarManager = null;
	
//	private Boolean isProjectFocused = Boolean.FALSE;


	/**
	 * Constructs and returns an instance of {@link CommonViewer}.
	 * The ID of the Eclipse view part will be used to create the viewer.
	 *
	 * This method has been overridden to disable multi-selection node feature.
	 *
	 * @param aParent
	 *			A composite parent to contain the CommonViewer
	 * @return An instance of CommonViewer
	 */
	@Override
	protected CommonViewer createCommonViewerObject(Composite parent) {
		// Build CNF
		treeViewer = new CommonViewer(getViewSite().getId(),
									  parent,
								 	  SWT.H_SCROLL
									| SWT.V_SCROLL);
		addViewToolBarActions();
		addEvents();

		// Return CNF
		return treeViewer;
	}
	
	@Override
	protected IAdaptable getInitialInput() {
		return new RootNode();
	}
	
	private void addViewToolBarActions() {
		// Locals
		menuManager    = getViewSite().getActionBars().getMenuManager();
		toolBarManager = getViewSite().getActionBars().getToolBarManager();

		// Set operations
		toolBarManager.add(new ToolItemAction(AGILE_VIEW_OPEN_PLAN_AC,
											  AGILE_VIEW_OPEN_PLAN_ID,
											  Boolean.FALSE,
											  OPEN_PLAN_TOOL_TIP_TEXT,
										 	  ImageDescriptor.createFromImage(ImageProvider.IMG_PLAN)));
		menuManager.add(new ToolItemAction(AGILE_VIEW_FOCUS_PROJECT_AC,
										   AGILE_VIEW_FOCUS_PROJECT_ID,
										   Boolean.FALSE,
										   FOCUS_PROJECT_TOOL_TIP_TEXT,
										   FOCUS_PROJECT_LABEL,
										   ImageDescriptor.createFromImage(ImageProvider.IMG_FOCUS_PROJECT)));
	}
	
	private void addEvents() {
		
		// Subscribe selection events for refreshing all views
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// Selected item
				Object element = ((IStructuredSelection) event.getSelection()).getFirstElement();
				// New releases to be displayed and all agile views
				Map<Integer, Release> newReleases = new HashMap<Integer, Release>();
				ReleasesView releasesView         = (ReleasesView) ViewUtils.getView(ReleasesView.ID);
//				IterationsView iterationsView   = (IterationsView) ViewUtils.getView(IterationsView.ID);
//				UserStoriesView userStoriesView = (UserStoriesView) ViewUtils.getView(UserStoriesView.ID);
//				TasksView tasksView             = (TasksView) ViewUtils.getView(TasksView.ID);

				if (element instanceof Product) {

					// Update tool items
					updateManagers(Boolean.FALSE, Boolean.FALSE);

					// Retrieve product projects
					Map<Integer, Project> projects = ((Product) element).getProjects();

					// For each project, retrieve its releases
					for (Map.Entry<Integer, Project> project : projects.entrySet()) {
						// Releases project
						Map<Integer, Release> releases = project.getValue().getReleases();
						
						// For each project release, add to product releases
						for (Map.Entry<Integer, Release> release : releases.entrySet()) {
							newReleases.put(release.getKey(), release.getValue());
						}
					}
					// Refresh releases view displaying product releases
					releasesView.refresh(newReleases, PRODUCT_DESCRIPTION);
				}
				else if (element instanceof Project) {
					
					// Update tool items
					updateManagers(Boolean.TRUE, Boolean.TRUE);
					
					// Refresh releases view displaying project releases
					newReleases = ((Project) element).getReleases();
					releasesView.refresh(newReleases, PROJECT_DESCRIPTION);
				}

//				((CommonViewer) event.getSource()).getCommonNavigator().setFocus();	// Does not work
			}
		});

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				// Open editor associated to double clicked node
				EditorUtils.openEditor(((TreeSelection) event
						  							   .getSelection())
						  							   .getFirstElement());
			}
		});
	}


	private void updateManagers(Boolean planValue, Boolean focusProjectValue) {
/*
		if (isProjectFocused) {
			((ActionContributionItem) toolBarManager.find(OPEN_PLAN_ID)).getAction().setEnabled(Boolean.TRUE);
			((ActionContributionItem) toolBarManager.find(FOCUS_PROJECT_ID)).getAction().setEnabled(Boolean.TRUE);	
		}
		else {
			
		}
*/		
		System.out.println("");
		

		((ActionContributionItem) toolBarManager.find(AGILE_VIEW_OPEN_PLAN_ID)).getAction().setEnabled(planValue);
		((ActionContributionItem) menuManager.find(AGILE_VIEW_FOCUS_PROJECT_ID)).getAction().setEnabled(focusProjectValue);
		
	}
	
	public class RootNode extends PlatformObject {}

}
// End <AgileHierarchyView> view
// --- -------------------- ----