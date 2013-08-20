
// Package
package org.centerom.almistack.ui.views;

// Imports
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.centerom.almistack.core.Constants;
import org.centerom.almistack.core.Utils;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TableViewerFocusCellManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;


public class PlanView extends ViewPart {

	// View Id
	public final static String ID = "org.centerom.almistack.ui.views.planView";
	
	
	// TODO: remove (x1)
	// private static final String  PROJECT_KEY = "agile-project";

	private static final int BACKLOG_HELP = 0;
	private static final int PLAN_HELP    = 1;
	
	
	// Table headers columns indexes
	private static final int ID_NAME_COL_US_INDEX    = 0;
	private static final int NAME_COL_US_INDEX       = 1;
	private static final int ESTIMATION_COL_US_INDEX = 2;
	private static final int STATE_COL_US_INDEX      = 3;

	// Table header columns labels
	private static final String ID_NAME_COL_US_LABEL    = "ID";
	private static final String NAME_COL_US_LABEL       = "Name";
	private static final String ESTIMATION_COL_US_LABEL = "Plan Est";
	private static final String STATE_COL_US_LABEL      = "State";

	private static final Integer PROJECT_ID = 3;	// TODO

	private FormToolkit toolkit = null;
	private CCombo cboReleases  = null;
	private Label releaseTitle  = null;
	private Composite iterationsComposite = null;
	private Composite planComposite = null;
	
	// Viewers
	private TableViewer unassignedUserStoriesViewer = null;

	// Following both maps purpose: identify which release we are working in each moment:
	//		Map<Integer, Release> releases     => releaseId    -> Release
	//		Map<Integer, Release> idsRelations => cboItemIndex -> releaseId
	// so knowing the itemIndex we can determine which Release object is selected
	private Map<Integer,
				Release> releases      = new HashMap<Integer,Release>();
	private Map<Integer,
				Integer> idsRelations  = new HashMap<Integer,Integer>();
	private List<UserStory>
				unassignedUserStories  = new ArrayList<UserStory>();
	private String[] userStoriesStates = new String[]{};

	// DND
	private Transfer[] dragTypes  = {DataTransfer.getInstance()};
	private Transfer[] dropTypes  = {DataTransfer.getInstance()};
	private DragSource dragSource = null;
	private UserStory dragData    = null;
	private Control dragControl   = null;
	private int dragOperation     = DND.DROP_MOVE;
	private int dropOperation     = DND.DROP_MOVE;
	private List<DropTarget> dropTargets = new ArrayList<DropTarget>();
	private List<Control> dropControls   = new ArrayList<Control>();;


	public PlanView() {
		super();
	}


	@Override
	public void createPartControl(Composite parent) {

/*

//		this.setLayout((new CustomLayout(MARGIN_TOP, MARGIN_RIGHT, MARGIN_BOTTOM, MARGIN_LEFT)).getLayout());

		this.setLayout((new CustomLayout(2, Boolean.TRUE)).getLayout());
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));

//		this.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_CYAN));	// TODO

		// 

*/		
		//
		toolkit    = new FormToolkit(parent.getDisplay());
//		handCursor = new Cursor(Display.getCurrent(), SWT.CURSOR_HAND);

		// Retrieve needed data and create composite contents 
//		retrieveData();
//		createContents(parent);
	}

	@Override
	public void setFocus() {}

	private void retrieveData() {
/*
		try {
			// Under current project, retrieve:
			//	 	- its Releases
			//	 	- those User Stories that are NOT assigned to its Iterations
			this.releases = RedmineConnectorService.getInstance().getProjectReleases(PROJECT_ID);
			this.userStoriesStates     = RedmineConnectorService.getInstance().getUserStoriesStates();
			this.unassignedUserStories = RedmineConnectorService.getInstance().getUnassignedUserStories(PROJECT_ID);
		}
		catch (RedmineConnectorException e) {
			e.printStackTrace();
		}
*/		
	}


	private void createContents(Composite composite) {
		// Locals
		Composite leftComposite  = toolkit.createComposite(composite);
		Composite rightComposite = toolkit.createComposite(composite);

//		leftComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));		// TODO
//		rightComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN));	// TODO

		// Set left composite layout
		leftComposite.setLayout(new GridLayout());
		leftComposite.setLayoutData(new GridData(SWT.FILL,
												 SWT.FILL,
												 Boolean.TRUE,
												 Boolean.TRUE));
		// Set right composite layout
		rightComposite.setLayout(new GridLayout());
		rightComposite.setLayoutData(new GridData(SWT.FILL,
												  SWT.FILL,
												  Boolean.TRUE,
												  Boolean.TRUE));
		// Backlog and Plan sections
		createBacklogSection(leftComposite);
		createPlanSection(rightComposite);
	}

	
	private void createBacklogSection(Composite composite) {
		// 
		CLabel backlogHelp     = null;
		Section backlogSection = toolkit.createSection(composite,
													   Section.TITLE_BAR
													 | ExpandableComposite.EXPANDED);
		Composite backlogComposite = toolkit.createComposite(backlogSection, SWT.NONE);

		backlogSection.setText("Backlog");
		backlogSection.setLayout(new GridLayout(1, Boolean.TRUE));
		backlogSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
		backlogSection.marginHeight = 20;
		backlogSection.titleBarTextMarginWidth = 20;

		backlogComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
		backlogComposite.setLayout(new GridLayout(1, Boolean.TRUE));
		backlogSection.setClient(backlogComposite);

		backlogHelp  = new CLabel(backlogComposite, SWT.NULL);
		backlogHelp.setImage(ImageProvider.IMG_HELP_LARGE);
		backlogHelp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, Boolean.TRUE, Boolean.FALSE));
		backlogHelp.setCursor(backlogHelp.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		backlogHelp.addListener(SWT.MouseDown, new HelpActionListener(BACKLOG_HELP));		 

		// Composite for table viewer
		Composite unassignedUSComposite = toolkit.createComposite(backlogComposite, SWT.NONE);
//		unassignedUSComposite.setLayout(new CustomLayout(1, Boolean.TRUE, 50, SWT.NONE, SWT.NONE, SWT.NONE).getLayout());
		unassignedUSComposite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, Boolean.TRUE, Boolean.FALSE));

		unassignedUserStoriesViewer = new TableViewer(unassignedUSComposite, SWT.H_SCROLL
																		   | SWT.V_SCROLL
																		   | SWT.BORDER
																		   | SWT.FULL_SELECTION);
		customizeViewer(unassignedUserStoriesViewer);

		unassignedUserStoriesViewer.setContentProvider(new USContentProvider());
		unassignedUserStoriesViewer.setInput(unassignedUserStories);
		unassignedUserStoriesViewer.setItemCount(unassignedUserStories.size());
		unassignedUserStoriesViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.NONE, Boolean.TRUE, Boolean.FALSE));		
		unassignedUserStoriesViewer.getTable().setHeaderVisible(Boolean.TRUE);
	}


	private void createPlanSection(Composite composite) {
		// 		
		Section planSection     = toolkit.createSection(composite,
														Section.TITLE_BAR
													  | ExpandableComposite.EXPANDED);
//		Composite planComposite = toolkit.createComposite(planSection, SWT.NULL);
		planComposite = toolkit.createComposite(planSection, SWT.NULL);
		planSection.setText("Plan");
		planSection.setLayout(new GridLayout(1, Boolean.TRUE));
		planSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
		planSection.marginHeight = 20;
		planSection.titleBarTextMarginWidth = 20;

		planComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
		planComposite.setLayout(new GridLayout(1, Boolean.TRUE));		
		planSection.setClient(planComposite);
		
		createReleasesSection(planComposite);
		createTitleSection(planComposite);
		createIterationsSection(planComposite);
	}


	private void createReleasesSection(Composite composite) {
		//
		Composite releasesComposite = toolkit.createComposite(composite, SWT.NULL);
		Integer itemIndex = 0;
		Label label       = toolkit.createLabel(releasesComposite, "Release", SWT.NULL);
		cboReleases       = new CCombo(releasesComposite, SWT.BORDER);
		CLabel helpIcon   = new CLabel(releasesComposite, SWT.NONE);

		releasesComposite.setLayout(new GridLayout(3, Boolean.FALSE));
		releasesComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.FALSE));

		helpIcon.setImage(ImageProvider.IMG_HELP_LARGE);
		helpIcon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, Boolean.TRUE, Boolean.FALSE));
		helpIcon.setCursor(helpIcon.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		helpIcon.addListener(SWT.MouseDown, new HelpActionListener(PLAN_HELP));

		// Populate combo
		for (Release release : releases.values()) {
			cboReleases.add(release.getName());
			idsRelations.put(itemIndex++, release.getId());
		}

		cboReleases.setEditable(Boolean.FALSE);
		cboReleases.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				// 
				Release release = releases.get(idsRelations.get(cboReleases.getSelectionIndex()));

				// Set new Release title, and its Springs with their User Stories
//				setReleaseTitle(release.getStartDate(), release.getDueDate());
				setReleaseIterations(release.getIterations());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		// Set selected first item	// TODO: set 0
		cboReleases.select(1);
	}


	private void createTitleSection(Composite composite) {
		// 
		Composite titleComposite = toolkit.createComposite(composite, SWT.NULL);
		releaseTitle    = toolkit.createLabel(titleComposite, null, SWT.NULL);
		Release release = releases.get(idsRelations.get(cboReleases.getSelectionIndex()));

//		setReleaseTitle(release.getStartDate(), release.getDueDate());
		
		titleComposite.setLayout(new GridLayout(1, Boolean.TRUE));
		titleComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.FALSE));
	}


	private void createIterationsSection(Composite composite) {
		//
		Release release = releases.get(idsRelations.get(cboReleases.getSelectionIndex()));
		//
		iterationsComposite = toolkit.createComposite(composite, SWT.NULL);
		iterationsComposite.setLayout(new GridLayout(1, Boolean.TRUE));
		iterationsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));

//		iterationsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));		// TODO

		// Paint Release Springs and their User Stories
		setReleaseIterations(release.getIterations());
	}


	private void setReleaseIterations(Map<Integer, Iteration> iterations) {
		// 
		List<TableViewer>
			iterationsViewers = new ArrayList<TableViewer>();
		Rectangle bounds	  = iterationsComposite.getBounds();
/*
		for (Control control : iterationsComposite.getChildren()) {
	          control.dispose();
		}
*/
		if (iterationsComposite.getChildren().length > 0) {

			iterationsComposite.dispose();		
			iterationsComposite = toolkit.createComposite(planComposite, SWT.NULL);
			iterationsComposite.setLayout(new GridLayout(1, Boolean.TRUE));
			iterationsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
		}
		
		ExpandBar expandBar = new ExpandBar(iterationsComposite, SWT.NONE);
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));

		Composite expandBarComposite = toolkit.createComposite(expandBar, SWT.NULL);
//		expandBarComposite.setLayout((new CustomLayout(1, Boolean.TRUE)).getLayout());
		expandBarComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));

//		iterationsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));		// TODO

		// For each iteration, paint User Stories
		for (Iteration iteration : iterations.values()) {

			ExpandItem expandItem = new ExpandItem (expandBar, SWT.NONE);
			expandItem.setText(iteration.getName());
			expandItem.setHeight(200);		
			expandItem.setExpanded(Boolean.TRUE);
			
			Composite iterationComposite = new Composite(expandBar, SWT.NONE);
//			iterationComposite.setLayout(new CustomLayout(1, Boolean.TRUE).getLayout());
			iterationComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
			expandItem.setControl(iterationComposite);

			iterationComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));		// TODO
			
			TableViewer iterationUserStoriesViewer = new TableViewer(iterationComposite, SWT.BORDER
																					| SWT.FULL_SELECTION);
			customizeViewer(iterationUserStoriesViewer);

			iterationUserStoriesViewer.setContentProvider(new USContentProvider());
			iterationUserStoriesViewer.setInput(iteration.getUserStories());
			iterationUserStoriesViewer.setItemCount(iteration.getUserStories().size());

			iterationUserStoriesViewer.getTable().setData(iteration.getId());
			iterationUserStoriesViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, Boolean.TRUE, Boolean.TRUE));
			iterationUserStoriesViewer.getTable().setHeaderVisible(Boolean.TRUE);
			iterationUserStoriesViewer.getTable().setSize(iterationComposite.getSize().x,
														  iterationComposite.getSize().y);
			iterationUserStoriesViewer.getTable().setLinesVisible(Boolean.TRUE);
			iterationUserStoriesViewer.refresh(Boolean.TRUE);

			iterationsViewers.add(iterationUserStoriesViewer);

			iterationsComposite.setBounds(bounds);
		}

		// DND controls
		setDragControls();
		setDropControls(iterationsViewers);

		// DND Source/Target
		setDragSource();
		setDropTarget();
	}


	private void setDragControls() {
		dragControl = unassignedUserStoriesViewer.getTable();
	}


	private void setDropControls(List<TableViewer> iterationsViewers) {
		
		if (!dropControls.isEmpty()) {
			dropControls.clear();
		}
		for (TableViewer tableViewer : iterationsViewers) {
			dropControls.add(tableViewer.getTable());
		}
	}


	private void setDragSource() {
		
		if (dragSource != null) {
			dragSource.dispose();
		}

		dragSource = new DragSource(dragControl, dragOperation);
		dragSource.setTransfer(dragTypes);
		dragSource.addDragListener(new DragSourceListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void dragStart(final DragSourceEvent event) {

				TableItem[] items = ((Table) dragControl).getSelection();

				if (items.length == 0) {
					event.doit = false;
				}
				else {
					// 
					dragData = (UserStory) items[0].getData();
				}
			}

			@Override
			public void dragSetData(final DragSourceEvent event) {

				if (DataTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = dragData;
				}
			}

			@Override
			public void dragFinished(final DragSourceEvent event) {

		        if (event.detail == DND.DROP_MOVE) {

		        	Table table = (Table) dragControl;
		        	TableItem[] items = table.getSelection();
		        	for( int i = 0; i < items.length; i++ ) {
		        		items[i].dispose();
		        		
		        	}
		        	// Remove User Story from unassigned list
		        	unassignedUserStories.remove(dragData);
		        }
		        dragData = null;
			}

		});
	}


	private void setDropTarget() {

		for (DropTarget dropTarget : dropTargets) {
			dropTarget.dispose();
		}
		
		for (Control dropControl : dropControls) {

			DropTarget dropTarget = new DropTarget(dropControl, dropOperation); 

		    dropTarget.setTransfer(dropTypes);
		    dropTarget.addDropListener(new DropTargetListener() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
		    	public void dragEnter(final DropTargetEvent event) {}
	
		    	@Override
		    	public void dragLeave(final DropTargetEvent event) {}

		    	@Override
		    	public void dragOperationChanged(final DropTargetEvent event) {}

		    	@Override
		    	public void dragOver(final DropTargetEvent event) {}
	
		    	@Override
		    	public void drop(final DropTargetEvent event) {
		    		//
		    		UserStory userStory = null;

		    		if (DataTransfer.getInstance().isSupportedType(event.currentDataType)) {

		    			userStory= (UserStory) event.data;

		    			// Calculate index table where to drop item
			    		Table dropTable    = (Table) ((DropTarget) event.widget).getControl();
			    		

			    		
			    		Point point        = event.display.map(null, dropTable, event.x, event.y);
			    		TableItem dropItem = dropTable.getItem(point);
			    		int index          = dropItem == null ? dropTable.getItemCount() : dropTable.indexOf(dropItem);
		            
		    			TableItem newDropItem = new TableItem(dropTable, SWT.NONE, index);    			
		    			newDropItem.setText(ID_NAME_COL_US_INDEX, userStory.getIdName());
		    			newDropItem.setText(NAME_COL_US_INDEX, userStory.getName());
//		    			newDropItem.setText(ESTIMATION_COL_US_INDEX, Utils.getViewEstimation(userStory.getEstimation()));
		    			newDropItem.setText(STATE_COL_US_INDEX, userStory.getState());
		
		    			newDropItem.setData(userStory);
		    			
		    			// Target Iteration Id 
		    			Integer targetIterationId = (Integer) dropTable.getData();
		    			// Current Release
		    			Release release = releases.get(idsRelations.get(cboReleases.getSelectionIndex()));
		    			// Current Release Iterations
		    			Map<Integer, Iteration> iterations = release.getIterations();
		    			
		    			// Add drop User Story to target drop Iteration
		    			Iteration iteration = iterations.get(targetIterationId);
//		    			iteration.getUserStories().add(userStory);
		    		}
		    		else {
		    			// Throw exception ... // TODO
		    		}
		    	}

		    	public void dropAccept(final DropTargetEvent event) {}

		    });
		    dropTargets.add(dropTarget);
		}
	}


	private void setReleaseTitle(Date startDate, Date dueDate) {
		// 
		StringBuilder title = new StringBuilder("Displaying Iterations for this Release´s Start Date: ");

//		title.append(Utils.getFormatDate(startDate));
		title.append(" and End Date: ");
//		title.append(Utils.getFormatDate(dueDate));
		
		releaseTitle.setText(title.toString());	
	}


	private void customizeViewer(TableViewer viewer) {
		// Name Id column
		TableViewerColumn idNameColViewer = new TableViewerColumn(viewer, SWT.NONE);
		idNameColViewer.setLabelProvider(new USLabelProvider(ID_NAME_COL_US_INDEX));
		idNameColViewer.setEditingSupport(new TextCellEditingSupport(viewer, ID_NAME_COL_US_INDEX));
		TableColumn idNameTblCol = idNameColViewer.getColumn();
		idNameTblCol.setWidth(80);
		idNameTblCol.setText(ID_NAME_COL_US_LABEL);

		// Name column
		TableViewerColumn nameCol = new TableViewerColumn(viewer, SWT.NONE);
		nameCol.setLabelProvider(new USLabelProvider(NAME_COL_US_INDEX));
		nameCol.setEditingSupport(new TextCellEditingSupport(viewer, NAME_COL_US_INDEX));
		TableColumn nameTblCol = nameCol.getColumn();
		nameTblCol.setWidth(280);
		nameTblCol.setText(NAME_COL_US_LABEL);

		// Plan Estimation column
		TableViewerColumn estimationColViewer = new TableViewerColumn(viewer, SWT.NONE);
		estimationColViewer.setLabelProvider(new USLabelProvider(ESTIMATION_COL_US_INDEX));
		estimationColViewer.setEditingSupport(new TextCellEditingSupport(viewer, ESTIMATION_COL_US_INDEX));
		TableColumn estimationTblCol = estimationColViewer.getColumn();
		estimationTblCol.setWidth(80);
		estimationTblCol.setText(ESTIMATION_COL_US_LABEL);

		// State column
		TableViewerColumn stateColViewer = new TableViewerColumn(viewer, SWT.NONE);
		stateColViewer.setLabelProvider(new USLabelProvider(STATE_COL_US_INDEX));

		stateColViewer.setEditingSupport(new ComboCellEditingSupport(viewer, userStoriesStates));

		TableColumn stateTblCol = stateColViewer.getColumn();
		stateTblCol.setWidth(140);
		stateTblCol.setText(STATE_COL_US_LABEL);

		// 
	    TableViewerEditor.create(viewer,
	    						 new TableViewerFocusCellManager(viewer,
	    								 						 new FocusCellOwnerDrawHighlighter(viewer)),
	    						 new EditorActivationStrategy(viewer),
	    						 ColumnViewerEditor.TABBING_HORIZONTAL
   			                   | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR);
   	
	}


	private void showMessageBoxDialog(String message) {
/*		
		// 
	    MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
	    messageBox.setText("Help");
	    messageBox.setMessage(message);

	    messageBox.open();
*/	    
	}


	private class HelpActionListener implements Listener {

		private static final long serialVersionUID = 1L;

		private final int type; 

		public HelpActionListener(int type) {
			this.type = type;
		}

		@Override
		public void handleEvent(Event event) {
			if (type == BACKLOG_HELP) {
				showMessageBoxDialog("Backlog Help");
			}
			else if (type == PLAN_HELP) {
				showMessageBoxDialog("Plan Help");
			}
		}
		
	}
	// END <HelpActionListener> class
	// --- -------------------- -----
	
	private class USContentProvider implements IStructuredContentProvider {

		private static final long serialVersionUID = 1L;
		
		private Object[] elements;
		
		public Object[] getElements(Object inputElement) {
			List<UserStory> userStories = (List<UserStory>) inputElement;
			return userStories.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
			if (newInput == null) {
				elements = new Object[0];
			}
			else {
				List<UserStory> springUS = (List<UserStory>) newInput;
				elements = springUS.toArray();
			}
		}

		public void dispose() {}
	}
	// END <USContentProvider> class
	// --- ------------------- -----
	
	private class USLabelProvider extends ColumnLabelProvider {

		private static final long serialVersionUID = 1L;

		// 
		private final int   columnIndex;

	    public USLabelProvider(int columnIndex) {
	    	this.columnIndex    = columnIndex;
	    }

	    @Override
	    public String getText(Object element) {
	    	UserStory userStory = (UserStory) element;
	    	String result       = null;

	    	switch (columnIndex) {

	    		case ID_NAME_COL_US_INDEX:
	    			result = userStory.getIdName();
	    			break;
	    		case NAME_COL_US_INDEX:
	    			result = userStory.getName();
	    			break;
	    		case ESTIMATION_COL_US_INDEX:
	    			result = getEstimation(userStory.getEstimation());
	    			break;
	    		case STATE_COL_US_INDEX:
	    			result = userStory.getState();
	    			break;
	    		default:
	    			result = Constants.UNDEFINED;
	    	}
	    	return result;
	    }
	    
	    private String getEstimation(Float estimation) {
	    	// 
	    	String value = Utils.getValue(estimation);
	    	
	    	if (Utils.isEmpty(value)) {
	    		value = Constants.DASH;
	    	}
	    	return value;
	    }
	}
	// END <USLabelProvider> class
	// --- ----------------- -----

	private static final class TextCellEditingSupport extends EditingSupport {

		private static final long serialVersionUID = 1L;

		private final CellEditor editor;
		private final int   columnIndex;	    	

		public TextCellEditingSupport(TableViewer viewer, int columnIndex) {
			super(viewer);

			this.editor      = new TextCellEditor(viewer.getTable());
			this.columnIndex = columnIndex;
		}

		@Override
		protected boolean canEdit(Object element) {
			return Boolean.TRUE;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			
	    	UserStory userStory = (UserStory) element;
	    	String result       = null;

	    	switch (columnIndex) {

	    		case ID_NAME_COL_US_INDEX:
	    			result = userStory.getIdName();
	    			break;
	    		case NAME_COL_US_INDEX:
	    			result = userStory.getName();
	    			break;
	    		case ESTIMATION_COL_US_INDEX:
//	    			result = Utils.getViewEstimation(userStory.getEstimation());
	    			break;
	    		default:
	    			// Never forever
	    			result = Constants.UNDEFINED;
	    	}
	    	return result;
		}

		@Override
		protected void setValue(Object element, Object value) {
			
			UserStory userStory = ((UserStory) element);
			
	    	switch (columnIndex) {

	    		case ID_NAME_COL_US_INDEX:
//	    			userStory.setIdName((String) value);
	    			break;
	    		case NAME_COL_US_INDEX:
	    			userStory.setName((String) value);
	    			break;
	    		case ESTIMATION_COL_US_INDEX:
	    			try {
	    				userStory.setEstimation(Float.parseFloat((String)value));
	    			}
	    			catch (   NumberFormatException
	    					| ClassCastException e) {
	    				userStory.setEstimation(null);
	    			}
	    			
	    			break;
	    		default:
	    			// Never forever ...

	    	}
			getViewer().update(element, null);
	    }
	}
	// END <TextCellEditingSupport> class
	// --- ------------------------ -----

	private static final class ComboCellEditingSupport extends EditingSupport {

		private static final long serialVersionUID = 1L;

		private final CellEditor editor;

	    public ComboCellEditingSupport(TableViewer viewer, String[] suggestions) {
	    	super(viewer);
	    	editor = new ComboBoxCellEditor(viewer.getTable(), suggestions, SWT.NONE);
	    }

	    @Override
	    protected boolean canEdit(Object element) {
	    	return Boolean.TRUE;
	    }

	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	return editor;
	    }

	    @Override
	    protected Object getValue(Object element) {

	    	UserStory userStory = (UserStory) element;
	    	CCombo statesCombo  = (CCombo) editor.getControl();

	    	statesCombo.setText(userStory.getState());
	    	return new Integer(-2);
	    }

	    @Override
	    protected void setValue(Object element, Object value) {

	    	UserStory userStory = (UserStory) element;
	    	CCombo statesCombo  = (CCombo) editor.getControl();

	    	userStory.setState(statesCombo.getText());
	    	getViewer().update(element, null);
	    }
	}
	// END <ComboCellEditingSupport> class
	// --- ------------------------- -----

	private static final class EditorActivationStrategy extends ColumnViewerEditorActivationStrategy {

		private static final long serialVersionUID = 1L;

		private EditorActivationStrategy(ColumnViewer viewer) {
			super( viewer );
			setEnableEditorActivationWithKeyboard(Boolean.TRUE);
		}

		@Override
		protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
			boolean result;
			if (event.character == '\r') {
				result = true;
			}
			else {
				result = super.isEditorActivationEvent(event);
			}
			return result;
		}
	}
	// END <EditorActivationStrategy> class
	// --- -------------------------- -----
	
	static class DataTransfer extends ByteArrayTransfer {

		private static final long serialVersionUID = 1L;

		private static final String USER_STORY_TYPE_NAME = "userStoryTypeName";
		private static final int USER_STORY_TYPE_ID      = registerType(USER_STORY_TYPE_NAME);

		private static DataTransfer _instance = new DataTransfer();

		public static DataTransfer getInstance() {
			return _instance;
		}

		@Override
	    public void javaToNative(Object object, TransferData transferData) {
			// 
			byte[] buffer = {};
			
	    	if (   validate(object)
	    		&& isSupportedType(transferData)) {

		    	UserStory userStory = (UserStory) object;

		    	try {
		    		// Write data to a byte array and then ask super to convert to pMedium
		    		ByteArrayOutputStream out = new ByteArrayOutputStream();
		    		DataOutputStream writeOut = new DataOutputStream(out);

		    		// Id
		    		writeOut.writeInt(userStory.getId());
		    		
		    		// Id Name
		    		buffer = userStory.getIdName().getBytes();
	    			writeOut.writeInt(buffer.length);
	    			writeOut.write(buffer);
		    			
	    			// Name
	    			buffer = userStory.getName().getBytes();
	    			writeOut.writeInt(buffer.length);
	    			writeOut.write(buffer);
		    			
	    			// State
	    			buffer = userStory.getState().getBytes();
	    			writeOut.writeInt(buffer.length);
	    			writeOut.write(buffer);

	    			// Estimation
	    			buffer = Utils.getValue(userStory.getEstimation()).getBytes();
	    			writeOut.writeInt(buffer.length);
	    			writeOut.write(buffer);

	    			// Close OutputStream
		    		writeOut.close();

		    		super.javaToNative(out.toByteArray(), transferData);
		    	}
		    	catch (IOException e) {
		    		e.printStackTrace();
		    	}
	    	} // validate(object) && isSupportedType(transferData))
	    	else {
	    		DND.error(DND.ERROR_INVALID_DATA);
	    	}
	    }

	    @Override
	    public Object nativeToJava(TransferData transferData) {
	    	// 
	    	UserStory userStory = null;
	    	byte[] content      = {};
    		byte[] buffer       = {};

	    	if (isSupportedType(transferData)) {

	    		buffer = (byte[]) super.nativeToJava(transferData);

	    		if (buffer != null) {

		    		try {
		    			
		    			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
		    			DataInputStream readIn  = new DataInputStream(in);

		    			while (readIn.available() > 20) {

		    				// Id
		    				Integer id = new Integer(readIn.readInt());

		    				// Id Name
		    				content = new byte[readIn.readInt()];
		    				readIn.read(content);
		    				String idName = new String(content);

		    				// Name
		    				content = new byte[readIn.readInt()];
		    				readIn.read(content);
		    				String name = new String(content);
	
		    				// State
		    				content = new byte[readIn.readInt()];
		    				readIn.read(content);
		    				String state = new String(content);
		    				
		    				// Estimation
		    				content = new byte[readIn.readInt()];
		    				readIn.read(content);
//		    				Float estimation = Utils.getValueAsFloat(new String(content));

		    				// Our Bean !
//		    				userStory = new UserStory(id, idName, name, state, estimation, null);
		    			}
		    			readIn.close();
		    		}
		    		catch (IOException e) {
		    			e.printStackTrace();
		    		}
		    		
	    		} // (buffer != null)

	    	} // (isSupportedType(transferData))

	    	return userStory;
	    }

	    @Override
	    protected String[] getTypeNames() {
	    	return new String[] {USER_STORY_TYPE_NAME};
	    }

	    @Override
	    protected int[] getTypeIds() {
	    	return new int[] {USER_STORY_TYPE_ID};
	    }

	    @Override
	    protected boolean validate(Object object) {

	    	if (object == null || !(object instanceof UserStory)) {
	    		return false;
	    	}
/*
	    	UserStory[] myTypes = (UserStory[]) object;

			for (int i = 0; i < myTypes.length; i++) {
				if (myTypes[i] == null || myTypes[i].fileName == null || myTypes[i].fileName.length() == 0) {
	    			return false;
	    		}
	    	}
*/
	    	return true;
	    }

	}
	// END <DataTransfer> class
	// --- -------------- -----

}
// END <PlanView> class 
// --- ---------- -----