
// Package
package org.centerom.almistack.ui.expressions;

// Imports
import java.util.ArrayList;
import java.util.Collection;

import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.ui.utils.ViewUtils;
import org.centerom.almistack.ui.views.AgileHierarchyView;


public class CNFMenuTester extends BasePropertyTester {

	//
	public static final String NODE_IS_SELECTED    = "nodeIsSelected";		//$NON-NLS-1$
	public static final String PROJECT_IS_SELECTED = "projectIsSelected";	//$NON-NLS-1$
	public static final String NOT_PROJECT_IS_SELECTED = "notProjectIsSelected";	//$NON-NLS-1$

	@Override
	public boolean test(Object   receiver,
						String   property,
						Object[] args,
						Object   expectedValue) {
		// Locals
		Boolean test       = Boolean.FALSE;
		Collection<?> node = (Collection<?>) receiver;

		// Any node selected?
		if (!node.isEmpty()) {
			// Yes; which property are we evaluating ?
			if (property.equals(NODE_IS_SELECTED)) {
				// "NODE_IS_SELECTED": show item menu as enabled when any node is selected
				test = Boolean.TRUE;
			}
			else if (property.equals(PROJECT_IS_SELECTED)) {
				// "PROJECT_IS_SELECTED": show "Open Plan" when project node is selected 
				if (new ArrayList(node).get(0) instanceof Project) {
					test = Boolean.TRUE;
				}
			}/*else if (property.equals(NOT_PROJECT_IS_SELECTED)) {
				// 
				if (!(new ArrayList(node).get(0) instanceof Project)) {
					test = Boolean.TRUE;
				}
			}*/
		}
		return test;
	}

}
// END <CNFMenuTester> class
// --- --------------- -----