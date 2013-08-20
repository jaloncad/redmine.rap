
// Package
package org.centerom.almistack.ui.editors;

// Imports
import org.centerom.almistack.ui.actions.ToolItemAction;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;


public abstract class AbstractEditor extends EditorPart {

	public final static String ID = "org.centerom.almistack.ui.editors.abstractEditor";

	// Constants
	// ---------
	// TODO: remove when configuration and internationalization is available
	// |
	// v
	// Release editor: actions codes
	private static final int RELEASE_EDITOR_SAVE_CHANGES_AC = 21;
	
	// Release editor: identifiers
	private static final String RELEASE_EDITOR_SAVE_CHANGES_ID = "release_editor_save_changes_id";

	// Tool bar view: tool tips texts
	private static final String RELEASE_EDITOR_SAVE_TOOL_TIP_TEXT = "Save changes";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	// Editor form
	protected FormToolkit toolkit     = null;
	protected ScrolledForm form       = null;
	protected Composite formComposite = null;
	
	// Dirty flag
	private Boolean isDirty = Boolean.FALSE;

	// Tool bar
	private ToolItemAction saveAction = null;
	
	public void createPartControl(Composite parent, String title, Image image) {
		// Form layout
		IToolBarManager toolBarManager = null;
		GridLayout layout = new GridLayout();
		GridData data     = new GridData(SWT.FILL,
										 SWT.FILL,
										 Boolean.TRUE,
										 Boolean.TRUE);
		layout.marginHeight      = 0;
		layout.marginWidth       = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing   = 0;

		// Create form
		toolkit = new FormToolkit(parent.getDisplay());
		form    = toolkit.createScrolledForm(parent);

		// Set header color based on SO decorating configuration
		toolkit.decorateFormHeading(form.getForm());

		// Form icon and title
		form.setImage(image);
		form.setText(title);

		// Retrieve form composite and set its layout
		formComposite = form.getBody();
		formComposite.setLayout(layout);
		formComposite.setLayoutData(data);
		
		// Get editor tool bar manager
		toolBarManager = form.getToolBarManager();
		saveAction = new ToolItemAction(RELEASE_EDITOR_SAVE_CHANGES_AC,
										RELEASE_EDITOR_SAVE_CHANGES_ID,
										Boolean.FALSE,
										RELEASE_EDITOR_SAVE_TOOL_TIP_TEXT,
										ImageDescriptor.createFromImage(ImageProvider.IMG_SAVE));
		toolBarManager.removeAll();
		toolBarManager.add(saveAction);
		toolBarManager.update(Boolean.TRUE);

//		formComposite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_RED));	// TODO
	}

	@Override
	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(Boolean isDirty) {
		this.isDirty = isDirty;
		firePropertyChange(PROP_DIRTY);
	}

	public void setEnabledSaveAction(Boolean enabled) {
		saveAction.setEnabled(enabled);
	}
	
}
// END <AbstractEditor> class
// --- ---------------- -----