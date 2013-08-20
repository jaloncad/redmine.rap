
// Package
package org.centerom.almistack.ui.contributions;

// Imports
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.StatusLineLayoutData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public class FooterContribution extends ContributionItem {

	private static final long serialVersionUID = 1L;

	private static final String ID = "footer_id";
	private static final int WIDTH = 200;
	
/*	
	private String text = null;
	private Image image = null;
	private String url  = null;
*/

/*	
	public FooterContribution(String id,
								  String text,
								  Image  image,
								  String url) {
		super(id);

		this.text  = text;
		this.image = image;
		this.url   = url;
	}
*/	
	public FooterContribution() {
		super(ID);
	}
			
	@Override
	public void fill(Composite parent) {
/*		
		// 
		Label separator = new Label(parent, SWT.SEPARATOR);
		CLabel label    = new CLabel(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout(1, Boolean.FALSE);
		layout.horizontalSpacing = 20;
		parent.setLayout(layout);
		

//		StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
//		label.setLayoutData(statusLineLayoutData);
		
		label.setLayout(new GridLayout(1, Boolean.FALSE));
		label.setData(new GridData(30, 30));
		label.setText(text);
		label.setImage(image);
		 
		
		
		
//		Composite composite = toolkit.createComposite(parent);
		
//		parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));

*/
		

		StatusLineLayoutData statusLineLayoutData = new StatusLineLayoutData();
		parent.setLayoutData(statusLineLayoutData);
		
		new Label(parent, SWT.SEPARATOR);
		CLabel title = new CLabel(parent, SWT.NONE);
		title.setText("2013 Alm-iStack Collaborative Tool Software");
		title.setImage(ImageProvider.IMG_COPYRIGHT);
		
		new Label(parent, SWT.SEPARATOR);
		CLabel about = new CLabel(parent, SWT.NONE);
		about.setText("About");

		new Label(parent, SWT.SEPARATOR);
		CLabel recycleBin = new CLabel(parent, SWT.NONE);
		recycleBin.setText("Recycle Bin");
		recycleBin.setImage(ImageProvider.IMG_BIN_EMTY);
	}

}
// END <FooterContribution> class
// --- -------------------- -----