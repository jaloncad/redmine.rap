
// Package
package org.centerom.almistack.ui.editors;

// Imports
import java.util.HashMap;
import java.util.Map;

import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.ui.editorinputs.ProjectEditorInput;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;


public class ProjectEditor extends AbstractEditor {

	
	
	public final static String ID = "org.centerom.almistack.ui.editors.projectEditor";
	
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
	// Data
	private Map<Integer, Release>
				releases = new HashMap<Integer, Release>();
	private Map<Integer, Iteration>
				iterations  = new HashMap<Integer, Iteration>();
	private Map<Integer, UserStory>
				userStories = new HashMap<Integer, UserStory>();
	private Map<Integer, Task>
				tasks       = new HashMap<Integer, Task>();
	private Project project = null;


	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// Initializing operations
		setSite(site);
		setInput(input);

		// Bean associated with current editor
		setProject(((ProjectEditorInput) input).getProject());
		setPartName(input.getName());

		// Retrieve iterations, user stories and tasks of current release
//		retrieveNestedData();

		headerTitle = "Project " + getProject().getName() + " overview";
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
// END <ProjectEditor> class
// --- --------------- -----