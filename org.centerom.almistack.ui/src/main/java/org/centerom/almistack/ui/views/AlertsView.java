
// Package
package org.centerom.almistack.ui.views;

// Imports
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.core.Utils;
import org.centerom.almistack.ui.actions.ToolItemAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;


public class AlertsView extends ViewPart {


	public final static String ID = "org.centerom.almistack.ui.views.alertsView";
	
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	private static final String TITLE_SECTION  = "Notifications";
	private static final String CONFIGURE      = "Configure";
	private static final String NO_ALERTS_DESC = "System notifications is disabled.";

	private static final int SECTION_MARGIN_HEIGHT = 20;
	private static final int SECTION_TITLE_BAR_TEXT_MARGIN_WIDTH = 20;

	// Action configuration: action code and id
	private static final int ALERTS_VIEW_NEW_CONFIGURATION_AC    = 24;
	private static final String ALERTS_VIEW_NEW_CONFIGURATION_ID = "release_editor_save_changes_id";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	// Member variables
	// ------ ---------
	//
	private FormToolkit toolkit        = null;
	private Section section            = null;
	private Composite sectionComposite = null;

	// Controls
	private Link lnkConfigure = null;
	private ToolItemAction configureAction = null;

	// OSGI services 
//	private IRepositoryConnectorService
//						connectorSrv = ServiceConsumer.getRepositoryConnectorService();	// Repository connector
//	private ILoggerService loggerSrv = ServiceConsumer.getLoggerService();				// Logger

	
	public AlertsView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		// Get toolkit to start creating components
		toolkit = new FormToolkit(parent.getDisplay());
		createContents(parent);
	}

	private void createContents(Composite composite) {
		// Add a section as a child to composite parent
		section = toolkit.createSection(composite,
									    Section.TITLE_BAR
									  | Section.DESCRIPTION
									  | ExpandableComposite.EXPANDED);
		section.setText(TITLE_SECTION);
		section.setDescription(NO_ALERTS_DESC);									   
		section.marginHeight = SECTION_MARGIN_HEIGHT;
		section.titleBarTextMarginWidth = SECTION_TITLE_BAR_TEXT_MARGIN_WIDTH;

		// Add a composite to the section
		sectionComposite = toolkit.createComposite(section, SWT.NONE);
		sectionComposite.setLayoutData(new GridData(SWT.FILL,
													SWT.FILL,
													Boolean.TRUE,
													Boolean.TRUE));
		sectionComposite.setLayout(new GridLayout(1, Boolean.TRUE));
		section.setClient(sectionComposite);
		
		// Link to configuration
		lnkConfigure = new Link(sectionComposite, SWT.LEFT);
		lnkConfigure.setText(Utils.createLinkableExpression(CONFIGURE));

		// New configuration action
		configureAction = new ToolItemAction(ALERTS_VIEW_NEW_CONFIGURATION_AC,
				 ALERTS_VIEW_NEW_CONFIGURATION_ID,
				 Constants.NOT_AVAILABLE_FEATURE);
		
		// Controls configuration
		lnkConfigure.addSelectionListener(new SelectionAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected( final SelectionEvent event ) {
				// Open wizard to configure new alert
				configureAction.run();
			}
		});
	}

	@Override
	public void setFocus() {}
	
}
// END <AlertsView> class
// --- ------------ -----