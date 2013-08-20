
// Package
package org.centerom.almistack.ui.editorenhancements;

// Imports
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;


public class ComboCellEditingSupport extends EditingSupport {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	// 
	private CellEditor editor = null;
	private Boolean canEdit   = null;


    public ComboCellEditingSupport(TableViewer viewer,
    							   String[]    suggestions,
    							   Boolean     canEdit) {
    	super(viewer);
    	this.editor = new ComboBoxCellEditor(viewer.getTable(),
    										 suggestions,
    										 SWT.NONE);
    	this.canEdit = canEdit;
    }

    @Override
	protected boolean canEdit(Object element) {
		return canEdit;
	}

    @Override
    protected CellEditor getCellEditor(Object element) {
    	return editor;
    }

    @Override
    protected Object getValue(Object element) {
    	// Locals
    	CCombo statesCombo = (CCombo) editor.getControl();
    	String state       = null;

		if (element instanceof Release) {
			state = ((Release) element).getState();
		}
		else if (element instanceof Iteration) {
			// TODO: ??
		}
		else if (element instanceof UserStory) {
			state = ((UserStory) element).getState(); 
		}
		else if (element instanceof Task) {
			state = ((Task) element).getState();
		}
		// Set combo new state
    	statesCombo.setText(state);

    	return new Integer(-2);
    }

    @Override
    protected void setValue(Object element, Object value) {
    	// Locals
    	CCombo statesCombo = (CCombo) editor.getControl();

		if (element instanceof Release) {
			((Release) element).setState(statesCombo.getText());
		}
		else if (element instanceof Iteration) {
			// TODO: ??
		}
		else if (element instanceof UserStory) {
			((UserStory) element).setState(statesCombo.getText());
		}
		else if (element instanceof Task) {
			((Task) element).setState(statesCombo.getText());
		}
    	// Update viewer
    	getViewer().update(element, null);
    }
	
}
// END <ComboCellEditingSupport> class
// --- ------------------------- -----