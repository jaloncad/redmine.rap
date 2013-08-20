
// Package
package org.centerom.almistack.ui.editors;

// Imports
import java.util.HashMap;
import java.util.Map;

import org.centerom.almistack.core.Utils;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.ui.actions.ToolItemAction;
import org.centerom.almistack.ui.editorinputs.ProductEditorInput;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.centerom.almistack.ui.providers.IterationsContentProvider;
import org.centerom.almistack.ui.providers.TasksContentProvider;
import org.centerom.almistack.ui.providers.UserStoriesContentProvider;
import org.centerom.almistack.ui.utils.ViewUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;


public class ProductEditor extends AbstractEditor {

	
	// Editor Id
	public final static String ID = "org.centerom.almistack.ui.editors.productEditor";
	
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String ALM_DIALOG_CAPTION  = "Alm-iStack";
	// Labels
	// ------
	// Fields
	private static final String NAME_LABEL         = "Name:";
	private static final String ESTIMATION_LABEL   = "Estimation:";
	private static final String OWNER_LABEL        = "Owner:";
	private static final String PROJECT_LABEL      = "Project:";
	private static final String START_DATE_LABEL   = "State Date:";
	private static final String RELEASE_DATE_LABEL = "Release Date:";
	private static final String STATE_LABEL        = "State:";
	private static final String DONE_LABEL         = "Done:";
	private static final String TO_DO_LABEL        = "To Do:";
	private static final String DESCRIPTION_LABEL  = "Description:";
	private static final String COMMENTS_LABEL     = "Comments:";

	// Sections
	private static final String GENERAL_SECTION_LABEL      = "General";
	private static final String SCHEDULE_SECTION_LABEL     = "Schedule";
	private static final String MEMO_SECTION_LABEL         = "Memo";
	private static final String ITERATIONS_SECTION_LABEL   = "Iterations";
	private static final String USER_STORIES_SECTION_LABEL = "User Stories";
	private static final String TASKS_SECTION_LABEL        = "Tasks";
	
	private static final int CTRLS_SEPARATION = 30;
	

	// Product editor: actions codes
	private static final int PRODUCT_EDITOR_SAVE_CHANGES_AC = 21;
	
	// Product editor: identifiers
	private static final String PRODUCT_EDITOR_SAVE_CHANGES_ID = "product_editor_save_changes_id";

	// Tool bar view: tool tips texts
	private static final String PRODUCT_EDITOR_SAVE_TOOL_TIP_TEXT = "Save changes";
	
	private static final String RENAME_MENU_ITEM = "Rename";
	private static final String owner = "Gabriel Alonso";	// TODO
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

//	private FormToolkit toolkit     = null;
	/*
	// Sections
	private Section generalSection     = null;
	private Section scheduleSection    = null;
	private Section memoSection        = null;
	private Section iterationsSection  = null;
	private Section userStoriesSection = null;
	private Section tasksSection       = null;
*/
	// Label controls
	private CLabel lblName        = null;
	private CLabel lblEstimation  = null;
	private CLabel lblOwner       = null;
	private CLabel lblProject     = null;
	private CLabel lblStartDate   = null;
	private CLabel lblReleaseDate = null;
	private CLabel lblState       = null;
	private CLabel lblDone        = null;
	private CLabel lblToDo        = null;
	private CLabel lblDescription = null;
	private CLabel lblComments    = null;

	// Input controls
	private Text txtName            = null;
	private Text txtEstimation      = null;
	private Link lnkOwner           = null;
	private Link lnkProject         = null;
	private DateTime dtStartDate    = null;
	private DateTime dtReleaseDate  = null;
	private CCombo cboStates        = null;
	private Text txtDone            = null;
	private Text txtToDo            = null;	
	private Text txtDescription     = null;
	private Text txtComments        = null;
	
	// Main composites
	private Composite topComposite = null;
	private Composite btmComposite = null;
	
	// Main composites bounds
	private Rectangle topCompositeBounds    = null;
	private Rectangle bottomCompositeBounds = null;
	
	
	private String headerTitle = null;
	private Boolean isDirty    = Boolean.FALSE;

	// Data
	private Map<Integer, Project>
				projects = new HashMap<Integer, Project>();
	private Map<Integer, Release>
				releases = new HashMap<Integer, Release>();
	private Map<Integer, Iteration>
				iterations  = new HashMap<Integer, Iteration>();
	private Map<Integer, UserStory>
				userStories = new HashMap<Integer, UserStory>();
	private Map<Integer, Task>
				tasks       = new HashMap<Integer, Task>();

	private Product product = null;


	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// Initializing operations
		setSite(site);
		setInput(input);

		// Bean associated with current editor
		setProduct(((ProductEditorInput) input).getProduct());
		setPartName(input.getName());

		// Retrieve iterations, user stories and tasks of current release
		retrieveNestedData();

		headerTitle = "Product " + getProduct().getName() + " overview";
	}

	@Override
	public void createPartControl(Composite parent) {
		// 
		super.createPartControl(parent, headerTitle, ImageProvider.IMG_EDITOR);
		
		// Get editor tool bar manager
		IToolBarManager toolBarManager = form.getToolBarManager();
		ToolItemAction action = new ToolItemAction(PRODUCT_EDITOR_SAVE_CHANGES_AC,
												   PRODUCT_EDITOR_SAVE_CHANGES_ID,
												   Boolean.FALSE,
												   PRODUCT_EDITOR_SAVE_TOOL_TIP_TEXT,
												   ImageDescriptor.createFromImage(ImageProvider.IMG_SAVE));
		toolBarManager.removeAll();
		toolBarManager.add(action);
		toolBarManager.update(Boolean.TRUE);

		// Create contents
		createContents();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	
		setDirty(Boolean.FALSE);
	}

	@Override
	public void doSaveAs() {}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return Boolean.FALSE;
	}

	@Override
	public void setFocus() {
		
		
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setOwnerName(String ownerName) {
		this.lnkOwner.setText(ownerName);
	}
	
	public void setProjectName(String projectName) {
		this.lnkProject.setText(Utils.createLinkableExpression(projectName));
		
/*		
		ProductEditorInput editorInput = (ProductEditorInput) this.getEditorInput();
		product.s
		editorInput.setProduct(product)
		setInput(input);
*/
	}

	private void createContents() {
		// Create top and bottom composites
		topComposite = toolkit.createComposite(formComposite, SWT.NONE);
		btmComposite = toolkit.createComposite(formComposite, SWT.NONE);
		
		// Layouts
		GridLayout layout        = new GridLayout(3, Boolean.TRUE);
		layout.marginHeight      = 5;
		layout.marginWidth       = 5;
		layout.verticalSpacing   = 5;
		layout.horizontalSpacing = 5;
		GridData layoutData      = new GridData(SWT.FILL,
										   		SWT.FILL,
										   		Boolean.TRUE,
										   		Boolean.TRUE);		
		// Set composites layouts
		topComposite.setLayout(layout);
		topComposite.setLayoutData(layoutData);
		btmComposite.setLayout(layout);
		btmComposite.setLayoutData(layoutData);

//		topComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));	// TODO		
//		btmComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));	// TODO

		// 
		formComposite.addControlListener(new ControlListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				// Retrieve parent bounds
				Rectangle formBounds = ((Composite) event.getSource()).getBounds();
				int topCpteHeight    = (int) (formBounds.height * 0.65);
				int topCpteWidth     = (int) (formBounds.width);
				int btmCpteHeight    = (int) (formBounds.height * 0.35);
				int btmCpteWidth     = (int) (formBounds.width);
				
				topComposite.setSize(topCpteWidth, topCpteHeight);
				btmComposite.setBounds(btmComposite.getLocation().x,
									   topCpteHeight,
									   btmCpteWidth,
									   btmCpteHeight);
				// 
				topCompositeBounds    = topComposite.getBounds();
				bottomCompositeBounds = btmComposite.getBounds();
			}

			@Override
			public void controlMoved(ControlEvent e) {}
		});
	
		// Create contents of both main composites
		createTopContents(topComposite);
		createBottomContents(btmComposite);
	}

	private void createTopContents(Composite composite) {	
		// Layouts 
		GridData layoutData = new GridData(SWT.FILL,
										   SWT.FILL,
										   Boolean.TRUE,
										   Boolean.TRUE);
		GridLayout layout   = new GridLayout(2, Boolean.FALSE);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing   = 0;
		
		// General section
		// ------- -------
		Section generalSection = toolkit.createSection(composite,
													   Section.TITLE_BAR
													 | ExpandableComposite.EXPANDED);
		generalSection.setText(GENERAL_SECTION_LABEL);
		generalSection.setLayout(layout);
		generalSection.setLayoutData(layoutData);

		// Set composite into the section		
//		Composite generalSC = toolkit.createComposite(generalSection, SWT.NONE);
		Composite generalSC = toolkit.createComposite(generalSection, SWT.NONE);
		generalSC.setLayout(layout);
		generalSC.setLayoutData(layoutData);
		generalSection.setClient(generalSC);

//		generalSC.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));	// TODO

		// Schedule section
		// -------- -------
		Section scheduleSection = toolkit.createSection(composite,
														Section.TITLE_BAR
													  | ExpandableComposite.EXPANDED);
		scheduleSection.setText(SCHEDULE_SECTION_LABEL);
		scheduleSection.setLayout(layout);
		scheduleSection.setLayoutData(layoutData);

		// Set composite into the section		
		Composite scheduleSC = toolkit.createComposite(scheduleSection, SWT.NONE);
		scheduleSC.setLayout(layout);
		scheduleSC.setLayoutData(layoutData);
		scheduleSection.setClient(scheduleSC);

//		scheduleSectionComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW));	// TODO

		// Memo section
		// ---- -------
		Section memoSection = toolkit.createSection(composite,
										    		Section.TITLE_BAR
										    	  | ExpandableComposite.EXPANDED);
		memoSection.setText(MEMO_SECTION_LABEL);
		memoSection.setLayout(new GridLayout(2, Boolean.FALSE));
		memoSection.setLayoutData(layoutData);

		// Set composite into the section		
		Composite memoSC = toolkit.createComposite(memoSection, SWT.NONE);
		memoSC.setLayout(layout);
		memoSC.setLayoutData(layoutData);
		memoSection.setClient(memoSC);

//		memoSectionComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW));	// TODO

		// Fill sections
		fillGeneralSection(generalSC);
		fillScheduleSection(scheduleSC);
		fillMemoSection(memoSC);
	}

	private void createBottomContents(Composite composite) {
		// Layout
		GridLayout layout = new GridLayout();
		GridData data     = new GridData(SWT.FILL,
										 SWT.FILL,
										 Boolean.TRUE,
										 Boolean.TRUE);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing   = 0;
		layout.marginBottom      = 7;
		
		// Iterations section
		// ---------- -------
		Section iterationsSection = toolkit.createSection(composite,
											   			  Section.TITLE_BAR
											   			| ExpandableComposite.TWISTIE
											   			| ExpandableComposite.EXPANDED);
		iterationsSection.setText(ITERATIONS_SECTION_LABEL);
		iterationsSection.setLayout(layout);
		iterationsSection.setLayoutData(data);

		// Set composite into the section		
		Composite iterationsSC = toolkit.createComposite(iterationsSection, SWT.NONE);
		iterationsSC.setLayout(layout);
		iterationsSC.setLayoutData(data);
		iterationsSection.setClient(iterationsSC);
		iterationsSection.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent event) {}

			@Override
			public void expansionStateChanged(ExpansionEvent event) {
				// Restore bounds
				topComposite.setBounds(topCompositeBounds);
				btmComposite.setBounds(bottomCompositeBounds);
				
				
/*
				// TODO: puede valir para que mole
				Rectangle formBounds = ProductEditor.this.formComposite.getBounds();
				int topCpteWidth     = (int) (formBounds.width);
				int btmCpteWidth     = (int) (formBounds.width);
				int topCpteHeight    = -1;
				int btmCpteHeight    = -1;

				// Widget is now expanded?
				if (event.getState() == Boolean.TRUE) {
					// Yes;
					topCpteHeight = (int) (formBounds.height * 0.65);
					btmCpteHeight = (int) (formBounds.height * 0.35);
				}
				else {
					// No; is collapsed		
					topCpteHeight = (int) (formBounds.height * 0.85);
					btmCpteHeight = (int) (formBounds.height * 0.15);					
				}
				topComposite.setSize(topCpteWidth, topCpteHeight);
				btmComposite.setBounds(btmComposite.getLocation().x,
									   topCpteHeight,
									   btmCpteWidth,
									   btmCpteHeight);
*/									   
			}
		});

//		iterationsSC.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));	// TODO

		// User stories section
		// ---- ------- -------
		Section userStoriesSection = toolkit.createSection(composite,
														   Section.TITLE_BAR
														 | ExpandableComposite.TWISTIE
														 | ExpandableComposite.COMPACT);
		userStoriesSection.setText(USER_STORIES_SECTION_LABEL);
		userStoriesSection.setLayout(layout);
		userStoriesSection.setLayoutData(data);
		userStoriesSection.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent event) {}

			@Override
			public void expansionStateChanged(ExpansionEvent event) {
				// Restore bounds
				topComposite.setBounds(topCompositeBounds);
				btmComposite.setBounds(bottomCompositeBounds);				
			}
		});

		// Set composite into the section		
		Composite userStoriesSC = toolkit.createComposite(userStoriesSection, SWT.NONE);
		userStoriesSC.setLayout(layout);
		userStoriesSC.setLayoutData(data);
		userStoriesSection.setClient(userStoriesSC);

//		userStoriesSC.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW));	// TODO

		// Tasks section
		// ----- -------
		Section tasksSection = toolkit.createSection(composite,
										    Section.TITLE_BAR
										  | ExpandableComposite.TWISTIE
										  | ExpandableComposite.EXPANDED);
		tasksSection.setText(TASKS_SECTION_LABEL);
		tasksSection.setLayout(layout);
		tasksSection.setLayoutData(data);
		tasksSection.addExpansionListener(new IExpansionListener() {

			@Override
			public void expansionStateChanging(ExpansionEvent event) {}

			@Override
			public void expansionStateChanged(ExpansionEvent event) {
				// Restore bounds
				topComposite.setBounds(topCompositeBounds);
				btmComposite.setBounds(bottomCompositeBounds);	
			}
		});

		// Set composite into the section		
		Composite tasksSC = toolkit.createComposite(tasksSection, SWT.NONE);
		tasksSC.setLayout(layout);
		tasksSC.setLayoutData(data);
		tasksSection.setClient(tasksSC);

//		tasksSC.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_YELLOW));	// TODO

		addTable(iterationsSC, iterations, new IterationsContentProvider());
		addTable(userStoriesSC, userStories, new UserStoriesContentProvider());
		addTable(tasksSC, tasks, new TasksContentProvider());
	}

	private void fillGeneralSection(Composite composite) {
		// Local composites	
		final Composite lblsComposite  = toolkit.createComposite(composite);
		final Composite ctrlsComposite = toolkit.createComposite(composite);
		
//		lblsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));	// TODO
//		ctrlsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));	// TODO

		// Local composites layouts
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		RowData data     = new RowData();
		layout.justify   = Boolean.TRUE;

		lblsComposite.setLayout(layout);
		lblsComposite.setData(data);
		ctrlsComposite.setLayout(layout);
		ctrlsComposite.setData(data);

		// Labels
		lblName       = new CLabel(lblsComposite, SWT.LEFT);
		lblEstimation = new CLabel(lblsComposite, SWT.LEFT);
		lblOwner      = new CLabel(lblsComposite, SWT.LEFT);
		lblProject    = new CLabel(lblsComposite, SWT.LEFT | SWT.BORDER);

		// Controls
		txtName       = new Text(ctrlsComposite, SWT.LEFT | SWT.BORDER);		
		txtEstimation = new Text(ctrlsComposite, SWT.LEFT | SWT.BORDER);
		lnkOwner      = new Link(ctrlsComposite, SWT.LEFT);
		lnkProject    = new Link(ctrlsComposite, SWT.LEFT);

		// Labels configuration
		lblName.setText(NAME_LABEL);
		lblEstimation.setText(ESTIMATION_LABEL);
		lblOwner.setText(OWNER_LABEL);
		lblProject.setText(PROJECT_LABEL);
		
		// Controls configuration
		lnkOwner.setText(Utils.createLinkableExpression(owner));
		lnkOwner.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected( final SelectionEvent event ) {
				// Dummy: TODO
				MessageDialog.openInformation(PlatformUI.getWorkbench()
														.getActiveWorkbenchWindow()
														.getShell(), 
											  ALM_DIALOG_CAPTION,
											  "Should open 'Owner' wizard");
			}
		});		
		lnkProject.setText("<a>Alm-iStack</a>");
		lnkProject.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected( final SelectionEvent event ) {
				// Dummy: TODO
				MessageDialog.openInformation(PlatformUI.getWorkbench()
														.getActiveWorkbenchWindow()
														.getShell(), 
											  ALM_DIALOG_CAPTION,
											  "Should open 'Alm-iStack' wizard");
			}
		});
		// 
		Menu lnkMenu = new Menu(lnkProject);
		MenuItem lnkMenuItem = new MenuItem(lnkMenu, SWT.PUSH);
		lnkMenuItem.setText(RENAME_MENU_ITEM);
		lnkMenuItem.addSelectionListener( new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected( final SelectionEvent event ) {
/*
				// Launch command that opens pop up for rename resource
				CommandUtils.executeCommand(RenameLinkCommand.COMMAND_ID,
											RenameLinkCommand.PARAMETER_ID,
											ALM_DIALOG_CAPTION);
*/											
			}
		});
		lnkProject.setMenu(lnkMenu);

		// Add decorations
		ViewUtils.addDecoration(txtEstimation);
		
		// Set widgets sizes
		composite.addControlListener(new ControlListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				// Retrieves "scheduleSC" bounds
				Rectangle bounds = ((Composite) event.getSource()).getBounds();

				// Manage left and right bounds
				lblsComposite.setBounds(0,
										0,
										(int) (bounds.width * 0.3),
										bounds.height);
				ctrlsComposite.setBounds((int) (bounds.width * 0.3),
										 0,
										 (int) (bounds.width * 0.7),
										 bounds.height);			
			}

			@Override
			public void controlMoved(ControlEvent e) {}
		});
	}

	private void fillScheduleSection(Composite composite) {
		// Local composites	
		final Composite lblsComposite  = toolkit.createComposite(composite);
		final Composite ctrlsComposite = toolkit.createComposite(composite);
		
//		lblsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));	// TODO
//		ctrlsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));	// TODO
		
		// Layout
		RowData data     = new RowData();
		RowLayout layout = new RowLayout(SWT.VERTICAL);
		layout.justify   = Boolean.TRUE;

		lblsComposite.setLayout(layout);
		lblsComposite.setData(data);
		ctrlsComposite.setLayout(layout);
		ctrlsComposite.setData(data);

		// Labels
		lblStartDate   = new CLabel(lblsComposite, SWT.LEFT);
		lblReleaseDate = new CLabel(lblsComposite, SWT.LEFT);
		lblState       = new CLabel(lblsComposite, SWT.LEFT);
		lblDone        = new CLabel(lblsComposite, SWT.LEFT);
		lblToDo        = new CLabel(lblsComposite, SWT.LEFT);
		
		// Controls
		dtStartDate = new DateTime(ctrlsComposite, SWT.DATE
				 								 | SWT.DROP_DOWN
				 								 | SWT.BORDER);
		dtReleaseDate = new DateTime(ctrlsComposite, SWT.DATE
												   | SWT.DROP_DOWN
												   | SWT.BORDER);
		cboStates = new CCombo(ctrlsComposite, SWT.BORDER);
		txtDone   = new Text(ctrlsComposite, SWT.BORDER);
		txtToDo   = new Text(ctrlsComposite, SWT.BORDER);
		
		// Labels configuration
		lblStartDate.setText(START_DATE_LABEL);		
		lblReleaseDate.setText(RELEASE_DATE_LABEL);
		lblState.setText(STATE_LABEL);
		lblDone.setText(DONE_LABEL);
		lblToDo.setText(TO_DO_LABEL);

		// Controls configuration
		cboStates.setEditable(Boolean.FALSE);

		// Add decorations
		ViewUtils.addDecoration(txtDone);
		ViewUtils.addDecoration(txtToDo);

		// Set widgets sizes
		composite.addControlListener(new ControlListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				// Retrieves "scheduleSC" bounds
				Rectangle bounds = ((Composite) event.getSource()).getBounds();

				// Manage left and right bounds
				lblsComposite.setBounds(0, 0,
										(int) (bounds.width * 0.4),
										bounds.height);
				ctrlsComposite.setBounds((int) (bounds.width * 0.4), 0,
										 (int) (bounds.width * 0.6),
										 bounds.height);			
			}

			@Override
			public void controlMoved(ControlEvent e) {}
		});
	}

	private void fillMemoSection(Composite composite) {
		// Local composites	
		final Composite lblsComposite  = toolkit.createComposite(composite);
		final Composite ctrlsComposite = toolkit.createComposite(composite);
		
//		lblsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));	// TODO
//		ctrlsComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));	// TODO
		
		// Layout
		RowData data     = new RowData();
		RowLayout layout = new RowLayout(SWT.VERTICAL);

		lblsComposite.setLayout(layout);
		lblsComposite.setData(data);
		ctrlsComposite.setLayout(layout);
		ctrlsComposite.setData(data);

		// Labels
		lblDescription = new CLabel(lblsComposite, SWT.LEFT);
		lblComments    = new CLabel(lblsComposite, SWT.LEFT);

		// Controls
		txtDescription = new Text(ctrlsComposite, SWT.BORDER | SWT.MULTI);
		txtComments    = new Text(ctrlsComposite, SWT.BORDER | SWT.MULTI);
		
		// Labels configuration
		lblDescription.setText(DESCRIPTION_LABEL);		
		lblComments.setText(COMMENTS_LABEL);

		// Set widgets sizes
		composite.addControlListener(new ControlListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void controlResized(ControlEvent event) {
				
				// Retrieves "memoSCBounds" bounds
				Rectangle memoSCBounds   = ((Composite) event.getSource()).getBounds();
				int lblsCompositeWidth   = (int) (memoSCBounds.width * 0.4);
				int lblsCompositeHeight  = (int) (memoSCBounds.height);
				int ctrlsCompositeWidth  = (int) (memoSCBounds.width * 0.6);
				int ctrlsCompositeHeight = (int) (memoSCBounds.height);

				// Manage left and right bounds
				lblsComposite.setBounds(0, 0, lblsCompositeWidth, lblsCompositeHeight);
				ctrlsComposite.setBounds(lblsCompositeWidth, 0,
						 				 ctrlsCompositeWidth,
						 				 ctrlsCompositeHeight);
				
				int txtControlWidth  = (int) (ctrlsCompositeWidth * 0.8);
				int txtControlHeight = (int) (ctrlsCompositeHeight * 0.4);
				
				txtDescription.setSize(txtControlWidth, txtControlHeight);
				txtComments.setSize(txtControlWidth, txtControlHeight);
				
				lblComments.setLocation(lblComments.getLocation().x,
										txtControlHeight + CTRLS_SEPARATION);
				txtComments.setLocation(txtComments.getLocation().x,
										txtControlHeight + CTRLS_SEPARATION);
			}

			@Override
			public void controlMoved(ControlEvent e) {}
		});
	}
	
	@SuppressWarnings("unchecked")
	private void addTable(Composite composite,
						  Object issues,
						  IStructuredContentProvider provider) {
		// Locals	
		Map<Integer,
			Object> tmpIssues   = (Map<Integer, Object>) issues;
		TableViewer tableViewer = new TableViewer(composite, SWT.V_SCROLL
														   | SWT.H_SCROLL
														   | SWT.BORDER
														   | SWT.FULL_SELECTION);
		// Customize table viewer
	    tableViewer.getTable().setLayoutData(new GridData(SWT.FILL,
	    												  SWT.FILL,
	    												  Boolean.TRUE,
	    												  Boolean.TRUE));
		tableViewer.setContentProvider(provider);
	    tableViewer.setInput(tmpIssues.values());
	    tableViewer.setItemCount(tmpIssues.size());
	}

	private void retrieveNestedData() {

		
		Map<Integer, Project> projects     = product.getProjects();
		Project project = projects.get(11);
		Map<Integer, Release> releases     = project.getReleases();
		Release release = releases.get(4);
		

		//
		iterations.putAll(release.getIterations());

		for (Map.Entry<Integer, Iteration> iteration : iterations.entrySet()) {
			// User stories of current iteration
			Map<Integer, UserStory> tmpUserStories = iteration.getValue().getUserStories();
			userStories.putAll(tmpUserStories);
			
			for (Map.Entry<Integer, UserStory> userStory : tmpUserStories.entrySet()) {
				// Tasks of current user story
				tasks.putAll(userStory.getValue().getTasks());	
			}
		}
	}

}
// END <ProductEditor> class
// --- --------------- -----