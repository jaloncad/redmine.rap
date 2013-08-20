
// Package
package org.centerom.almistack.ui.editorenhancements;

// Imports
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;


public class ActivationStrategy extends ColumnViewerEditorActivationStrategy {

	private static final long serialVersionUID = 1L;

	public ActivationStrategy(ColumnViewer viewer) {
		super(viewer);
		setEnableEditorActivationWithKeyboard(Boolean.TRUE);
	}

	@Override
	protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
		// Locals
		Boolean result = null;

		if (event.character == '\r') {
			result = true;
		}
		else {
			result = super.isEditorActivationEvent(event);
		}
		return result;
	}

}
// END <ActivationStrategy> class
// --- -------------------- -----