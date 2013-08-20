package org.centerom.almistack.ui.application;

import java.net.URL;

import org.centerom.almistack.core.Constants;
import org.centerom.almistack.ui.contributions.FooterContribution;
import org.centerom.almistack.ui.contributions.SearchContribution;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	// Constants
	// --------
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String MENU_SEPARATOR  = "menu_separator";
	
	// For Tool bars 
	private static final String ICONS_TOOLBAR_ID  = "icons_toolbar_id";
	private static final String SEARCH_TOOLBAR_ID = "search_toolbar_id";
	
	// For Actions
	private static final String DUMMY1_ACTION_ID   = "dummy1_action_id";
	private static final String DUMMY2_ACTION_ID   = "dummy2_action_id";
	private static final String DUMMY3_ACTION_ID   = "dummy3_action_id";
	private static final String ALM_SITE_ACTION_ID = "alm_site_action_id";
	
	private static final String ALM_DIALOG_CAPTION  = "Alm-iStack";
	private static final String ALM_HOME_PAGE_LABEL = "Home Page";
	private static final String ALM_HOME_PAGE_URL   = "https://sites.google.com/a/centeropenmiddleware.com/alm-istack/";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	// Member variables
	// ------ ---------
	// Workbench Actions
	private IWorkbenchAction importAction  = null;
	private IWorkbenchAction exportAction  = null;
	private IWorkbenchAction toogleCoolBarAction = null;
	private IWorkbenchAction lockToolBartAction  = null;
	private IWorkbenchAction closePerspectiveAction     = null;
	private IWorkbenchAction closeAllPerspectivesAction = null;

	// Actions
	private Action dummy1Action  = null;
	private Action dummy2Action  = null;
	private Action dummy3Action  = null;
	private Action almSiteAction = null;

	// Browser object 
	private IWebBrowser browser = null;


	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	@Override
	protected void makeActions(final IWorkbenchWindow window) {
 
		// Workbench actions
		// --------- -------
		// File menu
		importAction = ActionFactory.IMPORT.create(window);
		exportAction = ActionFactory.EXPORT.create(window);

		// View menu
		toogleCoolBarAction        = ActionFactory.TOGGLE_COOLBAR.create(window);
		closePerspectiveAction     = ActionFactory.CLOSE_PERSPECTIVE.create(window);
		closePerspectiveAction     = ActionFactory.CLOSE_PERSPECTIVE.create(window);
		closeAllPerspectivesAction = ActionFactory.CLOSE_ALL_PERSPECTIVES.create(window);
		lockToolBartAction         = ActionFactory.LOCK_TOOL_BAR.create(window);

		// Custom actions
		// ------ -------
		// New action Dummy 1 for cool bar
		dummy1Action = new Action() {
			private static final long serialVersionUID = 1L;
			public void run() {
		        MessageDialog.openInformation(window.getShell(),
		        							  ALM_DIALOG_CAPTION,
		                                      "Executing dummy 1 ...");
			}
		};		
		dummy1Action.setId(DUMMY1_ACTION_ID);		
		dummy1Action.setImageDescriptor(ImageDescriptor.createFromImage(ImageProvider.IMG_DUMMY_1));

		// New action Dummy 2 for cool bar
		dummy2Action = new Action() {
			private static final long serialVersionUID = 1L;
			public void run() {
		        MessageDialog.openInformation(window.getShell(),
		        							  ALM_DIALOG_CAPTION,
		                                      "Executing dummy 2 ...");
			}
		};		
		dummy2Action.setId(DUMMY2_ACTION_ID);		
		dummy2Action.setImageDescriptor(ImageDescriptor.createFromImage(ImageProvider.IMG_DUMMY_2));
		
		// New action Dummy 3 for cool bar
		dummy3Action = new Action() {
			private static final long serialVersionUID = 1L;
			public void run() {
		        MessageDialog.openInformation(window.getShell(),
		        							  ALM_DIALOG_CAPTION,
		                                      "Executing dummy 3 ...");
			}
		};		
		dummy3Action.setId(DUMMY3_ACTION_ID);		
		dummy3Action.setImageDescriptor(ImageDescriptor.createFromImage(ImageProvider.IMG_DUMMY_3));

		// New action for menu bar
		almSiteAction = new Action() {

			private static final long serialVersionUID = 1L;

			public void run() {
				IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
		        try {
		        	browser = browserSupport.createBrowser(IWorkbenchBrowserSupport.AS_EXTERNAL,
		        										   almSiteAction.getId(),
		        										   Constants.EMPTY_CHAR,
		        										   Constants.EMPTY_CHAR);
		        	browser.openURL(new URL(ALM_HOME_PAGE_URL));
		        }
		        catch( Exception e ) {
		        	e.printStackTrace();
		        }
			}
		};
		almSiteAction.setText(ALM_HOME_PAGE_LABEL);
		almSiteAction.setId(ALM_SITE_ACTION_ID);
		
		// Register actions
		register(importAction);
		register(exportAction);
		register(toogleCoolBarAction);
		register(lockToolBartAction);
		register(closePerspectiveAction);
		register(closeAllPerspectivesAction);
		register(dummy1Action);
		register(dummy2Action);
		register(dummy3Action);
		register(almSiteAction);
	}
	
	@Override
	protected void fillMenuBar(final IMenuManager menuBar) {
		// Locals
		MenuManager fileMenu   = new MenuManager("&File", IWorkbenchActionConstants.M_FILE );
		MenuManager viewMenu   = new MenuManager("View", IWorkbenchActionConstants.VIEW_EXT);
		MenuManager windowMenu = new MenuManager("Window", IWorkbenchActionConstants.M_WINDOW);
		MenuManager helpMenu   = new MenuManager("Help", IWorkbenchActionConstants.M_HELP);

		// File menu items
		fileMenu.add(importAction);
		fileMenu.add(exportAction);
		
		// View menu items
		viewMenu.add(toogleCoolBarAction);
		viewMenu.add(lockToolBartAction);
		viewMenu.add(new Separator(MENU_SEPARATOR));
		viewMenu.add(closeAllPerspectivesAction);
		viewMenu.add(closeAllPerspectivesAction);

		// Help menu items
		helpMenu.add(almSiteAction );

		// Add menus to menu bar
		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);
	}

	@Override
	protected void fillCoolBar(final ICoolBarManager coolBar) {
		// 
		IToolBarManager iconsToolBar  = new ToolBarManager(SWT.FLAT | SWT.RIGHT);		
		IToolBarManager searchToolBar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		
		coolBar.add(new ToolBarContributionItem(iconsToolBar, ICONS_TOOLBAR_ID));
//		coolBar.add(new ToolBarContributionItem(searchToolBar, SEARCH_TOOLBAR_ID));

		// Add actions
		iconsToolBar.add(dummy1Action);
		iconsToolBar.add(dummy2Action);
		iconsToolBar.add(dummy3Action);
/*		
		Action dummy4Action = new Action() {


		};		
		dummy4Action.setId("org.eclipse.ui.file.save");		

		iconsToolBar.add(dummy4Action);
	*/			
		// Add search feature
		searchToolBar.add(new SearchContribution());
		
		coolBar.add(searchToolBar);
		
		
		
		
	}

	@Override
	protected void fillStatusLine(final IStatusLineManager statusLine) {
		statusLine.add(new FooterContribution());
//		statusLine.add(new FooterLinkContribution("about_id", "About", ImageProvider.IMG_COPYRIGHT, "http://"));
//		statusLine.add(new FooterLinkContribution("contact_id", "Contact Support", ImageProvider.IMG_COPYRIGHT, "http://"));
//		statusLine.add(new FooterLinkContribution("legal", "Legal", ImageProvider.IMG_COPYRIGHT, "http://"));
	} 


	
	

}
