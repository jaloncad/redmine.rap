
// Package
package org.centerom.almistack.ui.editors;

// Imports
import org.centerom.almistack.domain.beans.User;
import org.centerom.almistack.ui.editorinputs.UserEditorInput;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public class UserEditor extends AbstractEditor {

	// Editor Id
	public final static String ID = "org.centerom.almistack.ui.editors.userEditor";


	// TODO: remove when configuration and internationalization is available
	// |
	// v
	// Labels
	private static final String ALM_DIALOG_CAPTION  = "Alm-iStack";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available


	private String headerTitle = null;
	private Boolean isDirty    = Boolean.FALSE;

	private User user = null;


	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// Initializing operations
		setSite(site);
		setInput(input);

		// Bean associated with current editor
		setUser(((UserEditorInput) input).getUser());
		setPartName(input.getName());

//		retrieveNestedData();

		headerTitle = "User " + getUser().getFullName() + " overview";
	}

	@Override
	public void createPartControl(Composite parent) {
		// 
		super.createPartControl(parent, headerTitle, ImageProvider.IMG_EDITOR);

		// TODO: ...
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
// END <UserEditor> class
// --- ------------ -----