
// Package
package org.centerom.almistack.ui.listeners;

// Imports
import org.centerom.almistack.ui.editors.AbstractEditor;
import org.centerom.almistack.ui.utils.ControlUtils;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;


public class WidgetModifyListener implements ModifyListener, Listener {

	private static final long serialVersionUID = 1L;
	
	private AbstractEditor editor = null;
	private String originalValue  = null;


	public WidgetModifyListener(String originalValue, AbstractEditor editor) {
		super();

		this.originalValue = originalValue;
		this.editor        = editor;
	}

	@Override
	public void modifyText(ModifyEvent event) {

		System.out.println("modificado !!!!");

		String sourceValue = null;
		Object source      = event.getSource();
		
		if (source instanceof Text) {
			sourceValue = ((Text) source).getText();
		}
		else if (source instanceof CCombo) {
			CCombo cbo  = (CCombo) source;
			sourceValue = cbo.getItem(cbo.getSelectionIndex());
		}
		
		// Set dirty flag
		if (!sourceValue.equals(originalValue)) {
			editor.setDirty(Boolean.TRUE);
			editor.setEnabledSaveAction(Boolean.TRUE);
		}
	}

	@Override
	public void handleEvent(Event event) {
		// Locals
		String sourceValue = null;
		Object source      = event.widget;
		
		if (source instanceof DateTime) {
			sourceValue = ControlUtils.getDateTimeStringValue((DateTime) source);		
		}
		
		// Set dirty flag
		if (!sourceValue.equals(originalValue)) {
			editor.setDirty(Boolean.TRUE);
			editor.setEnabledSaveAction(Boolean.TRUE);
		}
	}

}
// END <WidgetModifyListener> class
// --- ---------------------- -----