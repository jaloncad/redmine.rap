
// Package
package org.centerom.almistack.ui.providers;

// Imports
import java.util.Collection;

import org.centerom.almistack.domain.beans.Iteration;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class IterationsContentProvider implements IStructuredContentProvider {

	private static final long serialVersionUID = 1L;
	
	private Object[] elements = null;
	
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection<Iteration> iterations = (Collection<Iteration>) inputElement;
		return iterations.toArray();
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	
		if (newInput == null) {
			setElements(new Object[0]);
		}
		else {
			Collection<Iteration> iterations = (Collection<Iteration>) newInput;
			setElements(iterations.toArray());
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
// END <IterationsContentProvider> class
// --- --------------------------- -----