
// Package
package org.centerom.almistack.ui.editorinputs;

// Imports
import org.centerom.almistack.domain.beans.Release;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;


public class ReleaseEditorInput implements IEditorInput {

	// TODO: remove when configuration and internationalization is available
	// |
	// v 
	private static final String TOOL_TIP = "Editor for ";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	private Release release = null;


	public ReleaseEditorInput(Release release) {
		this.release = release;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return release;
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
		return release.getName();
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
        	if (release.getId()
        			   .toString()
        			   .equalsIgnoreCase(
        					   ((ReleaseEditorInput) obj).release.getId()
        					   							 .toString())) {
        		result = Boolean.TRUE;
        	}
        	else {
        		result = Boolean.FALSE;
        	}
        }
        return result;
    }
    
    public Release getRelease() {	
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}

}
// END <ReleaseEditorInput> class
// --- -------------------- -----