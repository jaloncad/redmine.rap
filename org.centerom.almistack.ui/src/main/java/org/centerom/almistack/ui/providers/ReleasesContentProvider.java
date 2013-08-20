
// Package
package org.centerom.almistack.ui.providers;

// Imports
import java.util.Collection;

import org.centerom.almistack.domain.beans.Release;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class ReleasesContentProvider implements IStructuredContentProvider {

	private static final long serialVersionUID = 1L;
	
	private Object[] elements = null;
	
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object inputElement) {
		Collection<Release> releases = (Collection<Release>) inputElement;
		return releases.toArray();
	}

	@SuppressWarnings("unchecked")
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	
		if (newInput == null) {
			setElements(new Object[0]);
		}
		else {
			Collection<Release> releases = (Collection<Release>) newInput;
			setElements(releases.toArray());
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
// END <ReleasesContentProvider> class
// --- ------------------------- -----