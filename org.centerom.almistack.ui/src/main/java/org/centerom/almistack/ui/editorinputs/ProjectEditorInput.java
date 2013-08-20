
// Package
package org.centerom.almistack.ui.editorinputs;

// Imports
import org.centerom.almistack.domain.beans.Project;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;


public class ProjectEditorInput implements IEditorInput {

	
	// TODO: remove when configuration and internationalization is available
	// |
	// v 
	private static final String TOOL_TIP = "Editor for ";
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	private Project project = null;


	public ProjectEditorInput(Project project) {
		this.project = project;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return project;
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
		return project.getName();
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
        	if (project.getId()
        			   .toString()
        			   .equalsIgnoreCase(
        					   ((ProjectEditorInput) obj).project.getId()
        					   							 .toString())) {
        		result = Boolean.TRUE;
        	}
        	else {
        		result = Boolean.FALSE;
        	}
        }
        return result;
    }

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
// END <ProjectEditorInput> class
// --- -------------------- -----