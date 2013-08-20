
// Package
package org.centerom.almistack.ui.views;

// Imports
import java.util.HashMap;
import java.util.Map;

import org.centerom.almistack.core.services.exceptions.ServiceException;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.services.connector.IRepositoryConnectorService;
import org.centerom.almistack.ui.actions.DropFocusAction;
import org.centerom.almistack.ui.actions.ToolItemAction;
import org.centerom.almistack.ui.consumers.ServiceConsumer;
import org.centerom.almistack.ui.editorenhancements.ActivationStrategy;
import org.centerom.almistack.ui.editorenhancements.ComboCellEditingSupport;
import org.centerom.almistack.ui.editorenhancements.TextCellEditingSupport;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.centerom.almistack.ui.providers.ReleasesContentProvider;
import org.centerom.almistack.ui.providers.ReleasesLabelProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;


public class ReleasesView extends ViewPart {

	// View Id
	public final static String ID = "org.centerom.almistack.ui.views.releasesView";

	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String PREVENT_DROP_FOCUS = "Edit operation is being performed at this moment.\n"
												   + "Pressing Ok will discard current changes.";
	private static final String TITLE_SECTION      = "Releases";
	private static final String SHOW_ALL_DESC      = "Displaying assigned releases.";
	
	private static final int SECTION_MARGIN_HEIGHT = 20;
	private static final int SECTION_TITLE_BAR_TEXT_MARGIN_WIDTH = 20;

	// Columns labels
	private static final String NAME_COL_LABEL         = "Name";
	private static final String START_DATE_COL_LABEL   = "Start Date";
	private static final String RELEASE_DATE_COL_LABEL = "Release Date";
	private static final String STATE_COL_LABEL        = "State";
	private static final String ESTIMATION_COL_LABEL   = "Estimation";
	private static final String DONE_COL_LABEL         = "Done";
	private static final String TO_DO_COL_LABEL        = "To Do";

	// Columns indexes
	private static final int NAME_COL_INDEX         = 0;
	private static final int START_DATE_COL_INDEX   = 1;
	private static final int RELEASE_DATE_COL_INDEX = 2;
	private static final int STATE_COL_INDEX        = 3;
	private static final int ESTIMATION_COL_INDEX   = 4;
	private static final int DONE_COL_INDEX         = 5;
	private static final int TO_DO_COL_INDEX        = 6;

	// Columns sizes
	private static final int NAME_COL_WIDTH         = 100;
	private static final int START_DATE_COL_WIDTH   = 100;
	private static final int RELEASE_DATE_COL_WIDTH = 100;
	private static final int STATE_COL_WIDTH        = 140;
	private static final int ESTIMATION_COL_WIDTH   = 90;
	private static final int DONE_COL_WIDTH         = 55;
	private static final int TO_DO_COL_WIDTH        = 55;

	// Tool bar section: tool items tool tip texts
	private static final String ADD_RELEASE_TOOL_TIP_TEXT    = "Add release";
	private static final String EDIT_RELEASE_TOOL_TIP_TEXT   = "Edit release";
	private static final String DELETE_RELEASE_TOOL_TIP_TEXT = "Delete release";
	private static final String SAVE_CHANGES_TOOL_TIP_TEXT   = "Save changes";
	private static final String CANCEL_CHANGES_TOOL_TIP_TEXT = "Cancel changes";

	// Tool bar view: actions codes
	private static final int RELEASES_VIEW_SHOW_ALL_AC          = 1;
	private static final int RELEASES_VIEW_FILTER_BY_PRODUCT_AC = 2;
	private static final int RELEASES_VIEW_FILTER_BY_PROJECT_AC = 3;
	// Tool bar view: identifiers
	private static final String RELEASES_VIEW_SHOW_ALL_ID          = "releases_view_show_all_id";
	private static final String RELEASES_VIEW_FILTER_BY_PRODUCT_ID = "releases_view_filter_by_product_id";
	private static final String RELEASES_VIEW_FILTER_BY_PROJECT_ID = "releases_view_filter_by_project_id";
	// Tool bar view: names
	private static final String SHOW_ALL_LABEL          = "All";
	private static final String FILTER_BY_PRODUCT_LABEL = "Filter by product";
	private static final String FILTER_BY_PROJECT_LABEL = "Filter by project";
	// Tool bar view: tool tips texts
	private static final String SHOW_ALL_TOOL_TIP_TEXT          = "Displays all assigned releases";
	private static final String FILTER_BY_PRODUCT_TOOL_TIP_TEXT = "Displays current product releases";
	private static final String FILTER_BY_PROJECT_TOOL_TIP_TEXT = "Displays current project releases";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	// Member variables
	// ------ ---------
	// 
	private Map<Integer,
				Release> releases     = new HashMap<Integer, Release>();
	private String[] definedStates    = new String[]{};
	private String userId             = null;
	private Boolean isEnabledEditMode = Boolean.FALSE;
	//
	private TableViewerColumn nameViewer       = null;
	private TableViewerColumn startDateViewer  = null;
	private TableViewerColumn endDateViewer    = null;
	private TableViewerColumn stateViewer      = null;
	private TableViewerColumn estimationViewer = null;
	private TableViewerColumn doneViewer       = null;
	private TableViewerColumn toDoViewer       = null;
	private TableViewer tableViewer = null;
	private FormToolkit toolkit     = null;
	private Section section         = null;
	private ToolBar toolBar         = null;
	private ToolItem addRelease     = null;
	private ToolItem editRelease    = null;
	private ToolItem deleteRelease  = null;
	private ToolItem saveChanges    = null;
	private ToolItem cancelChanges  = null;
//	private Cursor handCursor	    = null;
	private Composite sectionComposite = null;
	private Composite viewerComposite  = null;

	// OSGI services 
	private IRepositoryConnectorService
						connectorSrv = ServiceConsumer.getRepositoryConnectorService();	// Repository connector
//	private ILoggerService loggerSrv = ServiceConsumer.getLoggerService();				// Logger


	public ReleasesView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		//
		toolkit    = new FormToolkit(parent.getDisplay());
//		handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);

		// Retrieve needed data and create composite contents 
		retrieveData();
		addViewToolBar();
		createContents(parent);

		getViewSite().getPage().addPartListener(new org.eclipse.ui.IPartListener2()	{

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {}
			
			@Override
			public void partBroughtToTop(IWorkbenchPartReference partRef) {}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {}

			@Override
			public void partOpened(IWorkbenchPartReference partRef) {}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {}

			@Override
			public void partVisible(IWorkbenchPartReference partRef) {}

			@Override
			public void partInputChanged(IWorkbenchPartReference partRef) {}

			@Override
			public void partDeactivated(IWorkbenchPartReference partRef) {

				// Losing focus and we are editing data?
				if     (partRef.getId().equalsIgnoreCase(ID)
					&&  isEnabledEditMode == Boolean.TRUE) {
					// Yes; ask
					DropFocusAction action = new DropFocusAction(PREVENT_DROP_FOCUS); 

					if (action.execute() == Boolean.TRUE) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								// Wait until focus is really lost
								while (Display.getDefault().readAndDispatch()) {}
								// 
								tableViewer.getControl().forceFocus();
							}
						});
	
					}
				}
			}
		});
	}

	@Override
	public void setFocus() {}

	public void refresh(Map<Integer, Release> releases,	String description) {
		//
		section.setDescription(description);

		tableViewer.setInput(releases.values());
	    tableViewer.setItemCount(releases.size());		
	}

	private void retrieveData() {
		try {
			// TODO: user Id: get it from context
			releases      = connectorSrv.getAllReleases(userId);
			definedStates = connectorSrv.getDefinedStates();
		}
		catch (ServiceException e) {
			e.printStackTrace();
		}	
	}

	private void addViewToolBar() {
		// Locals
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();

		// Set operations
		menuManager.add(new ToolItemAction(RELEASES_VIEW_SHOW_ALL_AC,
										   RELEASES_VIEW_SHOW_ALL_ID,
										   Boolean.FALSE,
										   SHOW_ALL_TOOL_TIP_TEXT,
										   SHOW_ALL_LABEL));
		menuManager.add(new Separator());
		menuManager.add(new ToolItemAction(RELEASES_VIEW_FILTER_BY_PRODUCT_AC,
										   RELEASES_VIEW_FILTER_BY_PRODUCT_ID,
										   Boolean.TRUE,
										   FILTER_BY_PRODUCT_TOOL_TIP_TEXT,
										   FILTER_BY_PRODUCT_LABEL,
										   ImageDescriptor.createFromImage(ImageProvider.IMG_PRODUCT)));
		menuManager.add(new ToolItemAction(RELEASES_VIEW_FILTER_BY_PROJECT_AC,
										   RELEASES_VIEW_FILTER_BY_PROJECT_ID,
										   Boolean.TRUE,
										   FILTER_BY_PROJECT_TOOL_TIP_TEXT,
										   FILTER_BY_PROJECT_LABEL,
										   ImageDescriptor.createFromImage(ImageProvider.IMG_PROJECT)));
	}

	private void createContents(Composite parent) {
		// Add a section as a child to composite parent
		section = toolkit.createSection(parent,
									    Section.TITLE_BAR
									  | Section.DESCRIPTION
									  | ExpandableComposite.EXPANDED);
		section.setText(TITLE_SECTION);
		section.setDescription(SHOW_ALL_DESC);
		section.marginHeight = SECTION_MARGIN_HEIGHT;
		section.titleBarTextMarginWidth = SECTION_TITLE_BAR_TEXT_MARGIN_WIDTH;

		// Add a composite to the section		
		sectionComposite = toolkit.createComposite(section, SWT.NONE);
		sectionComposite.setLayoutData(new GridData(SWT.FILL,
													SWT.FILL,
													Boolean.TRUE,
													Boolean.TRUE));
		sectionComposite.setLayout(new GridLayout());
		section.setClient(sectionComposite);

		// New table viewer composite for section 
		viewerComposite = toolkit.createComposite(sectionComposite, SWT.NONE);
		viewerComposite.setLayout(new GridLayout());
		viewerComposite.setLayoutData(new GridData(SWT.FILL,
												   SWT.NONE,
												   Boolean.TRUE,
												   Boolean.FALSE));
		tableViewer = new TableViewer(viewerComposite, SWT.H_SCROLL
													 | SWT.V_SCROLL
													 | SWT.BORDER
													 | SWT.FULL_SELECTION);
		// Customize table viewer and add tool bar to the section
		customizeViewer();
		addSectionToolBar();
	}

	private void customizeViewer() {
		// Name column
		nameViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		nameViewer.setLabelProvider(new ReleasesLabelProvider(NAME_COL_INDEX));
		nameViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																NAME_COL_INDEX,
																Boolean.FALSE));
		TableColumn nameTblCol = nameViewer.getColumn();
		nameTblCol.setWidth(NAME_COL_WIDTH);
		nameTblCol.setText(NAME_COL_LABEL);

		// Start date column
		startDateViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		startDateViewer.setLabelProvider(new ReleasesLabelProvider(START_DATE_COL_INDEX));
		startDateViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																	 START_DATE_COL_INDEX,
																	 Boolean.FALSE));
		TableColumn startDateTblCol = startDateViewer.getColumn();
		startDateTblCol.setWidth(START_DATE_COL_WIDTH);
		startDateTblCol.setText(START_DATE_COL_LABEL);
		
		// Release date column
		endDateViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		endDateViewer.setLabelProvider(new ReleasesLabelProvider(RELEASE_DATE_COL_INDEX));
		endDateViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																   RELEASE_DATE_COL_INDEX,
																   Boolean.FALSE));
		TableColumn endDateTblCol = endDateViewer.getColumn();
		endDateTblCol.setWidth(RELEASE_DATE_COL_WIDTH);
		endDateTblCol.setText(RELEASE_DATE_COL_LABEL);

		// State column
		stateViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		stateViewer.setLabelProvider(new ReleasesLabelProvider(STATE_COL_INDEX));
		stateViewer.setEditingSupport(new ComboCellEditingSupport(tableViewer,
																  definedStates,
																  Boolean.FALSE));
		TableColumn stateTblCol = stateViewer.getColumn();
		stateTblCol.setWidth(STATE_COL_WIDTH);
		stateTblCol.setText(STATE_COL_LABEL);

		// Estimation column
		estimationViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		estimationViewer.setLabelProvider(new ReleasesLabelProvider(ESTIMATION_COL_INDEX));
		estimationViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																	  ESTIMATION_COL_INDEX,
																	  Boolean.FALSE));
		TableColumn estimationTblCol = estimationViewer.getColumn();
		estimationTblCol.setWidth(ESTIMATION_COL_WIDTH);
		estimationTblCol.setText(ESTIMATION_COL_LABEL);

		// Done column
		doneViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		doneViewer.setLabelProvider(new ReleasesLabelProvider(DONE_COL_INDEX));
		doneViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
															    DONE_COL_INDEX,
															    Boolean.FALSE));
		TableColumn doneTblCol = doneViewer.getColumn();
		doneTblCol.setWidth(DONE_COL_WIDTH);
		doneTblCol.setText(DONE_COL_LABEL);
		
		// To Do column
		toDoViewer = new TableViewerColumn(tableViewer, SWT.NONE);
		toDoViewer.setLabelProvider(new ReleasesLabelProvider(TO_DO_COL_INDEX));
		toDoViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																TO_DO_COL_INDEX,
																Boolean.FALSE));
		TableColumn toDoTblCol = toDoViewer.getColumn();
		toDoTblCol.setWidth(TO_DO_COL_WIDTH);
		toDoTblCol.setText(TO_DO_COL_LABEL);

		// 
	    TableViewerEditor.create(tableViewer,
	    						 new TableViewerFocusCellManager(tableViewer,
	    								 						 new FocusCellOwnerDrawHighlighter(tableViewer)),	    								 						 
	    						 new ActivationStrategy(tableViewer),
	    						 ColumnViewerEditor.TABBING_HORIZONTAL
   			                   | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR);

	    tableViewer.setContentProvider(new ReleasesContentProvider());
	    tableViewer.setInput(releases.values());
	    tableViewer.setItemCount(releases.size());
	    tableViewer.getTable().setLayoutData(new GridData(SWT.FILL,
	    												  SWT.NONE,
	    												  Boolean.TRUE,
	    												  Boolean.FALSE));		
	    tableViewer.getTable().setHeaderVisible(Boolean.TRUE);
	}

	private void addSectionToolBar() {
		// Locals
		toolBar = (new ToolBarManager(SWT.FLAT)).createControl(section);
//		toolBar.setCursor(handCursor);

		// Tool item: add release		
		addRelease = new ToolItem(toolBar, SWT.PUSH);
		addRelease.setToolTipText(ADD_RELEASE_TOOL_TIP_TEXT);
		addRelease.setImage(ImageProvider.IMG_RELEASE_ADD);
		addRelease.setEnabled(Boolean.FALSE);
		addRelease.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateToolItems(Boolean.FALSE);
				switchEditMode(Boolean.FALSE);
				
//				Double d = new Double(Math.random());
//				new Integer(d.toString())

//				releases.put(69, new Release(69, "tmp", new Date(), new Date(), Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, "Defined",null, null));
			    tableViewer.setInput(releases.values());
			    tableViewer.setItemCount(releases.size());
			}
		});

		// Tool item: edit release
		editRelease = new ToolItem(toolBar, SWT.PUSH);
		editRelease.setToolTipText(EDIT_RELEASE_TOOL_TIP_TEXT);
		editRelease.setImage(ImageProvider.IMG_RELEASE_EDIT);
		editRelease.setEnabled(Boolean.TRUE);
		editRelease.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateToolItems(Boolean.FALSE);
				switchEditMode(Boolean.TRUE);
			}
		});

		// Tool item: delete release
		deleteRelease = new ToolItem(toolBar, SWT.PUSH);
		deleteRelease.setToolTipText(DELETE_RELEASE_TOOL_TIP_TEXT);
		deleteRelease.setImage(ImageProvider.IMG_RELEASE_DELETE);
		deleteRelease.setEnabled(Boolean.FALSE);
		deleteRelease.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateToolItems(Boolean.FALSE);
				switchEditMode(Boolean.FALSE);
			}
		});

		// Tool item: separator
		new ToolItem(toolBar, SWT.SEPARATOR);

		// Tool item: save changes 
		saveChanges = new ToolItem(toolBar, SWT.PUSH);
		saveChanges.setToolTipText(SAVE_CHANGES_TOOL_TIP_TEXT);
		saveChanges.setImage(ImageProvider.IMG_SAVE);
		saveChanges.setEnabled(Boolean.FALSE);
		saveChanges.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateToolItems(Boolean.TRUE);
				switchEditMode(Boolean.FALSE);
			}
		});

		//  Tool item: cancel changes
		cancelChanges = new ToolItem(toolBar, SWT.PUSH);
		cancelChanges.setToolTipText(CANCEL_CHANGES_TOOL_TIP_TEXT);
		cancelChanges.setImage(ImageProvider.IMG_CANCEL);
		cancelChanges.setEnabled(Boolean.FALSE);
		cancelChanges.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateToolItems(Boolean.TRUE);
				switchEditMode(Boolean.FALSE);
			}
		});
		
		// Set tool bar depending on the section
		section.setTextClient(toolBar);
	}

	private void updateToolItems(Boolean showCRUD) {

		if (showCRUD) {
			// 
			addRelease.setEnabled(Boolean.FALSE);
			editRelease.setEnabled(Boolean.TRUE);
			deleteRelease.setEnabled(Boolean.FALSE);
			saveChanges.setEnabled(Boolean.FALSE);
			cancelChanges.setEnabled(Boolean.FALSE);
		}
		else {
			//
			addRelease.setEnabled(Boolean.FALSE);
			editRelease.setEnabled(Boolean.FALSE);
			deleteRelease.setEnabled(Boolean.FALSE);
			saveChanges.setEnabled(Boolean.TRUE);
			cancelChanges.setEnabled(Boolean.TRUE);
		}
	}
	
	private void switchEditMode(Boolean newMode) {
		//
		isEnabledEditMode = newMode;

		nameViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																NAME_COL_INDEX,
																newMode));
		startDateViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																	 START_DATE_COL_INDEX,
																	 newMode));
		endDateViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																   RELEASE_DATE_COL_INDEX,
																   newMode));
		stateViewer.setEditingSupport(new ComboCellEditingSupport(tableViewer,
																  definedStates,
																  newMode));
		estimationViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																	  ESTIMATION_COL_INDEX,
																	  newMode));
		doneViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
																DONE_COL_INDEX,
																newMode));
		toDoViewer.setEditingSupport(new TextCellEditingSupport(tableViewer,
															    TO_DO_COL_INDEX,
															    newMode));
	}
}
// END <ReleasesView> class
// --- -------------- -----


/*				
reviewBean.setStatus(ReviewStatus.ACCEPTED);

UXPrototypeEditor editor = (UXPrototypeEditor) reviewBean.getEditor();

// Comprobamos si el componente asociado a la revisión todavía existe, en caso de no hacerlo,
// se asociará la última captura de pantalla donde el componente todavía existía
if (reviewBean.getImageResolutionName().equals("") && !(Boolean) editor.getBrowser().evaluate("return $(\"*[id='" + reviewBean.getHtmlComponentId() + "']\").length > 0;")) {
	reviewBean.setImageResolutionName(editor.getLastReviewedScreenshot());
}

// Se marca el editor como "Dirty" 
editor.setDirty(true);

// Se actualiza el estado de las revisiones en el outline y en el editor
editor.updateOutline();
editor.updateReviewStatus(reviewBean);

init(reviewBean, false, false);


TODO: This for tool bar inside the view:

	private void createToolBar2() {
		
		ToolBarManager mgr = (ToolBarManager) getViewSite().getActionBars().getToolBarManager();
		CustomControlItem item1 = new CustomControlItem("someText1");
		mgr.add(item1);
		CustomControlItem item2 = new CustomControlItem("someText1");
		mgr.add(item2);	
	}
	
	
	
	private class CustomControlItem extends ControlContribution {

		protected CustomControlItem(String id) {
			super(id);
		}

		@Override
		protected Control createControl(Composite parent) {

			CLabel logo = new CLabel(parent, SWT.NULL);
			logo.setImage(ImageProvider.IMG_RELEASE_EDIT);
			return logo;
		}
		
		
	}
	
	
	
	
		private class CustomControlItem extends ControlContribution {

		// Default serial Id
		private static final long serialVersionUID = 1L;
		private Image image    = null;
		private String toolTip = null;
		private ToolBar toolbar = null;
		

		protected CustomControlItem(ToolBar toolbar, String id, Image image, String toolTip) {
			super(id);
			
			this.image   = image;
			this.toolTip = toolTip;
			this.toolbar = toolbar;
		}

		@Override
		protected Control createControl(Composite parent) {
			
			ToolItem item = new ToolItem(toolbar, SWT.PUSH);
			
			return item.getControl();
			
		}
		
	}


	private void createToolBar2() {
		

		    
		    
		ToolBarManager toolBarMgr = new ToolBarManager(SWT.FLAT | SWT.RIGHT);

		
		
//		IToolBarManager toolBarMgr = mform.getForm().getToolBarManager();
		ToolBar toolbar = toolBarMgr.createControl(section);
		
//		IServiceLocator workbench =  PlatformUI.getWorkbench();
		
		

		
//		
		
		  
/*	    
		private static final String     = "add_release";
		private static final String EDIT_RELEASE_ID   = "edit_release";
		private static final String DELETE_RELEASE_ID = "delete_release";
		private static final String SAVE_CHANGES_ID   = "save_changes";
		private static final String CANCEL_CHANGES_ID = "cancel_changes";


		CustomControlItem editRelease = new CustomControlItem(toolbar,
				ADD_RELEASE_ID,
														ImageProvider.IMG_RELEASE_EDIT,
														EDIT_RELEASE_TOOL_TIP_TEXT);
	


	    CommandContributionItemParameter saveContributionParameter = new CommandContributionItemParameter(
	    		this.getViewSite(),
		            null,
		            "it.wellnet.easysitebox.menu.commands.saveMenu",
		            CommandContributionItem.STYLE_PUSH);
    

	    
	    

//	    saveContributionParameter.icon = ImageDescriptor.createFromImage(ImageProvider.IMG_RELEASE_EDIT);

//	    CommandContributionItem editRelease = new CommandContributionItem(saveContributionParameter);


	     
		toolBarMgr.add(editRelease);
		toolBarMgr.update(true);
		
		editRelease.setVisible(Boolean.TRUE);
//		toolbar.setVisible(Boolean.TRUE);
		

		section.setTextClient(toolbar);
		

		
		
		
		
	}
	
*/