
// Package
package org.centerom.almistack.ui.utils;

//Imports
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class ViewUtils {

	// 
	private static final String POINTS = "Points";
	

	public static IViewPart getView(String ViewId) {
		// Locals
		IViewPart view = null;

		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ViewId);
		}
		return view;
	}
	
	public static IViewPart openView(String ViewId) {
		// Locals
		IViewPart view = null;

		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			try {
				view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ViewId);
			}
			catch (PartInitException e) {
				e.printStackTrace();
			}
		}
		return view;
	}

	/**
	 * Force the repainting of the control
	 * (the only way found to accomplish this, is to reset its bounds,
	 *  and set again its original ones)
	 * 
	 * @param control the control to be repainted
	 */
	public static void forceRepaint(Control control) {
		// Locals
		Rectangle bounds = control.getBounds(); 
		
		control.setBounds(0, 0, 0, 0);
		control.setBounds(bounds);
	}
	
	
	public static void addDecoration(Text text) {

		Image imageDecoration = FieldDecorationRegistry.getDefault()
													   .getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
													   .getImage();
		ControlDecoration decoration = new ControlDecoration(text, SWT.RIGHT	
					   										     | SWT.TOP);
		decoration.setImage(imageDecoration);
		decoration.setDescriptionText(POINTS);
		decoration.setMarginWidth(2);
	}

}
// END <ViewUtils> class
// --- ----------- -----