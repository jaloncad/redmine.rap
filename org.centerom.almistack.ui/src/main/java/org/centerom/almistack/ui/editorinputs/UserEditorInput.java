
// Package
package org.centerom.almistack.ui.editorinputs;

// Imports
import org.centerom.almistack.domain.beans.User;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;


public class UserEditorInput implements IEditorInput {


	// TODO: remove when configuration and internationalization is available
	// |
	// v 
	private static final String TOOL_TIP = "Editor for ";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	private User user = null;


	public UserEditorInput(User user) {
		this.user = user;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		return user;
	}

	@Override
	public boolean exists() {
		return Boolean.TRUE;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return user.getFullName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return TOOL_TIP + this.getName();
	}

    @Override
    public boolean equals(Object obj) {
    	// Locals
    	Boolean result = Boolean.TRUE;

        if (this == obj) {
        	result = Boolean.TRUE;
        }
        else if (  obj == null
        		|| getClass() != obj.getClass()) {
        	result = Boolean.FALSE;
        }
        else {
        	// Match identifiers: it is enough
        	if (user.getId()
        			.toString()
        			.equalsIgnoreCase(
        					   ((UserEditorInput) obj)
        					   					.user
        					   					.getId()
        					   					.toString())) {
        		result = Boolean.TRUE;
        	}
        	else {
        		result = Boolean.FALSE;
        	}
        }
        return result;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
// END <UserEditorInput> class
// --- ----------------- -----