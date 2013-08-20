
// Package
package org.centerom.almistack.ui.contributions;

// Imports
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;


public class SearchContribution extends ContributionItem {

	private static final long serialVersionUID = 1L;

	private static final String ID = "SEARCH_ID";
	private static final int WIDTH = 200;


	public SearchContribution() {
		super(ID);
	}

	@Override
	public final void fill(ToolBar parent, int index) {
		// 
		Text text = new Text(parent, SWT.SEARCH
								   | SWT.ICON_CANCEL
								   | SWT.ICON_SEARCH
								   | SWT.BORDER);
		text.setMessage("Search ...");
		text.setToolTipText("Enter Search Criteria");
		ToolItem toolItem = new ToolItem(parent,
										 SWT.SEPARATOR | SWT.RIGHT,
										 index);
		toolItem.setControl(text);
		toolItem.setWidth(WIDTH);
	}

}
// END <QuickSearch> class
// --- ------------- -----