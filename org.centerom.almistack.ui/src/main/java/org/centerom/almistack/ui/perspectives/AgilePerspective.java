
// Package
package org.centerom.almistack.ui.perspectives;

// Imports
import org.centerom.almistack.ui.views.AgileHierarchyView;
import org.centerom.almistack.ui.views.AlertsView;
import org.centerom.almistack.ui.views.IterationsView;
import org.centerom.almistack.ui.views.PlanView;
import org.centerom.almistack.ui.views.ReleasesView;
import org.centerom.almistack.ui.views.TasksView;
import org.centerom.almistack.ui.views.UserStoriesView;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;


public class AgilePerspective implements IPerspectiveFactory {

	// View Id
	public static final String ID = "org.centerom.almistack.ui.perspectives.agilePerspective";
	
	// Folders Identifiers
	private static final String PRODUCTS_REF_ID     = "products_ref_id";
	private static final String AGILE_ITEMS_REF_ID  = "releases_ref_id";
	private static final String ALERTS_REF_ID       = "alerts_ref_id";
	
	// Views ratios
	private static final float PRODUCTS_VIEW_RATIO    = 0.25f;
	private static final float AGILE_ITEMS_VIEW_RATIO = 0.45f;
	private static final float ALERTS_VIEW_RATIO      = 0.70f;
	
	@Override
	public void createInitialLayout(final IPageLayout layout) {

//		layout.setEditorAreaVisible(Boolean.FALSE);	// TODO
		layout.setEditorAreaVisible(Boolean.TRUE);

		// 
		String editorRefId = layout.getEditorArea();

		IFolderLayout productsFolder = layout.createFolder(PRODUCTS_REF_ID,
														   IPageLayout.LEFT,
														   PRODUCTS_VIEW_RATIO,
														   editorRefId);
		IFolderLayout agileItemsFolder = layout.createFolder(AGILE_ITEMS_REF_ID,
															 IPageLayout.BOTTOM,
															 AGILE_ITEMS_VIEW_RATIO,
															 editorRefId);
		IFolderLayout alertsFolder = layout.createFolder(ALERTS_REF_ID,
														 IPageLayout.RIGHT,
														 ALERTS_VIEW_RATIO,
														 AGILE_ITEMS_REF_ID);
														 
		// 
		productsFolder.addView(AgileHierarchyView.ID);		
		agileItemsFolder.addView(ReleasesView.ID);
		agileItemsFolder.addView(IterationsView.ID);
		agileItemsFolder.addView(UserStoriesView.ID);
		agileItemsFolder.addView(TasksView.ID);
		agileItemsFolder.addPlaceholder(PlanView.ID);
			
		alertsFolder.addView(AlertsView.ID);

		// Add shortcut for all perspectives
		layout.addPerspectiveShortcut(ID);
		layout.addPerspectiveShortcut(HomePerspective.ID);
		layout.addPerspectiveShortcut(DevelopmentPerspective.ID);
		layout.addPerspectiveShortcut(QualityPerspective.ID);
	}

}
// END <AgilePerspective> class
// --- ------------------ -----