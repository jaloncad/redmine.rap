
// Package
package org.centerom.almistack.ui.providers;

// Imports
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.core.Utils;
import org.centerom.almistack.domain.beans.Release;
import org.eclipse.jface.viewers.ColumnLabelProvider;


public class ReleasesLabelProvider extends ColumnLabelProvider {

	private static final long serialVersionUID = 1L;
	
	// TODO: remove when configuration and internationalization is available
	// |
	// v
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
	private final int columnIndex;

    public ReleasesLabelProvider(int columnIndex) {
    	this.columnIndex = columnIndex;
    }

    @Override
    public String getText(Object element) {

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
    			result = Utils.parseDateToString(release.getEndDate());
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
    			result = Constants.UNDEFINED;
    	}
    	return result;
    }

}
// END <ReleasesLabelProvider> class
// --- ----------------------- -----