
// Package
package org.centerom.almistack.ui.editorenhancements;

// Imports
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.core.Utils;
import org.centerom.almistack.domain.beans.Release;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;


public class TextCellEditingSupport extends EditingSupport {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	// TODO: remove when configuration and internationalization is available
	// |
	// v	
	// Table header: columns index; TODO: remove when FW is available
	private static final int NAME_COL_INDEX         = 0;
	private static final int START_DATE_COL_INDEX   = 1;
	private static final int RELEASE_DATE_COL_INDEX = 2;
	private static final int STATE_COL_INDEX        = 3;
	private static final int ESTIMATION_COL_INDEX   = 4;
	private static final int DONE_COL_INDEX         = 5;
	private static final int TO_DO_COL_INDEX        = 6;
	// ^
	// |
	// TODO: remove when configuration and internationalization is available

	//
	private final CellEditor editor;
	private final int   columnIndex;
	private Boolean canEdit = null;


	public TextCellEditingSupport(TableViewer viewer,
								  int         columnIndex,
								  Boolean     canEdit) {
		super(viewer);

		this.editor      = new TextCellEditor(viewer.getTable());
		this.columnIndex = columnIndex;
		this.canEdit     = canEdit;
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
    	Release release = (Release) element;
    	String result   = null;

    	switch (columnIndex) {

    		case NAME_COL_INDEX:
    			result = release.getName();
    			break;
    		case START_DATE_COL_INDEX:
    			result = Utils.parseDateToString(release.getStartDate());
    			break;
    		case RELEASE_DATE_COL_INDEX:
    			result = Utils.parseDateToString(release.getStartDate());
    			break;
    		case STATE_COL_INDEX:
    			result = release.getState();
    			break;
    		case ESTIMATION_COL_INDEX:
    			result = Utils.parseFloatToString(release.getEstimation());
    			break;    			
    		case DONE_COL_INDEX:
    			result = Utils.parseFloatToString(release.getDone());
    			break;
    		case TO_DO_COL_INDEX:
    			result = Utils.parseFloatToString(release.getToDo());
    			break;
    		default:
    			// Never forever ...
    			result = Constants.UNDEFINED;
    	}
    	return result;
	}

	@Override
	protected void setValue(Object element, Object value) {
		// Locals
		Release release = (Release) element;

   	   	switch (columnIndex) {

       		case NAME_COL_INDEX:
       			release.setName((String) value);
       			break;
       		case START_DATE_COL_INDEX:
       			release.setStartDate(Utils.parseStringToDate((String) value));
       			break;
       		case RELEASE_DATE_COL_INDEX:
       			release.setEndDate(Utils.parseStringToDate((String) value));
       			break;
       		case STATE_COL_INDEX:
       			release.setState((String) value);
       			break;
       		case ESTIMATION_COL_INDEX:
       			release.setEstimation(Utils.parseStringToFloat((String) value));
       			break;    			
       		case DONE_COL_INDEX:
       			release.setDone(Utils.parseStringToFloat((String) value));
       			break;
       		case TO_DO_COL_INDEX:
       			release.setToDo(Utils.parseStringToFloat((String) value));
       			break;
       		default:
       			// Never forever ...
       	}
   	   	getViewer().update(element, null);
   	}

}
// END <TextCellEditingSupport> class
// --- ------------------------ -----
