
// Package
package org.centerom.almistack.ui.providers;

// Imports
import java.util.Collection;

import org.centerom.almistack.domain.beans.Task;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class TasksContentProvider implements IStructuredContentProvider {

	private static final long serialVersionUID = 1L;
	
	private Object[] elements = null;
	
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection<Task> tasks = (Collection<Task>) inputElement;
		return tasks.toArray();
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	
		if (newInput == null) {
			setElements(new Object[0]);
		}
		else {
			Collection<Task> tasks = (Collection<Task>) newInput;
			setElements(tasks.toArray());
		}
	}

	public void dispose() {}

	public Object[] getElements() {
		return elements;
	}

	public void setElements(Object[] elements) {
		this.elements = elements;
	}

}
// END <TasksContentProvider> class
// --- ---------------------- -----