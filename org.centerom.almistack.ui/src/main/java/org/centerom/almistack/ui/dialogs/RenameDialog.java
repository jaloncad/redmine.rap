
// Package
package org.centerom.almistack.ui.dialogs;

// Imports
import org.centerom.almistack.core.Constants;
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class RenameDialog extends CenteredDialog {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	//
	private static final int NODE_TAG_MAX_LENGTH = 20;

	//
	private Text tagTxt     = null;
	private String tagValue = null;
	private Button okBtn    = null;


	public RenameDialog(Shell parentShell) {
		super(parentShell);

		this.tagValue = Constants.EMPTY_CHAR;
	}

	public RenameDialog(Shell parentShell, String nodeTag) {
		super(parentShell);

		this.tagValue = nodeTag;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control control = super.createContents(parent);
		okBtn = this.getButton(IDialogConstants.OK_ID);
		okBtn.setEnabled(Boolean.FALSE);

		return control;
	}

	/**
	 * Create dialog contents.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		// Locals
		Composite composite   = (Composite) super.createDialogArea(parent);
		GridLayout layout     = new GridLayout(3, Boolean.FALSE);
		Label commentLabel    = new Label(composite, SWT.NONE);
		Image imageDecoration = FieldDecorationRegistry.getDefault()
													   .getFieldDecoration(FieldDecorationRegistry.DEC_INFORMATION)
													   .getImage();
		composite.setLayout(layout);	
		commentLabel.setText("New name:");

		GridData data = new GridData(IDialogConstants.ENTRY_FIELD_WIDTH,
									 SWT.DEFAULT);
		data.horizontalIndent    = imageDecoration.getBounds().width;
		data.horizontalAlignment = SWT.FILL;
		data.verticalAlignment   = SWT.CENTER;
		data.grabExcessHorizontalSpace = Boolean.TRUE;

		tagTxt = new Text(composite, SWT.BORDER);
		tagTxt.setLayoutData(data);
		tagTxt.setText(this.tagValue);

		ControlDecoration decoration = new ControlDecoration(tagTxt,
															 SWT.LEFT
														   | SWT.TOP);
		decoration.setImage(imageDecoration);
		decoration.setDescriptionText("Enter resource text");
		decoration.setMarginWidth(1);
		decoration.setShowOnlyOnFocus(Boolean.TRUE);

		tagTxt.addModifyListener(new ModifyListener() {

			private static final long serialVersionUID = 1L;

			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});

		tagTxt.addKeyListener(new KeyListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void keyReleased(KeyEvent e) {
				validatePage();
			}

			@Override
			public void keyPressed(KeyEvent e) {}

		});

		setDialogLocation();

		return composite;
	}

	/**
	 * Configures the shell.
	 * 
	 * @param shell the shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);

		// Set the title bar text and the size
		shell.setText("Rename Resource");
		shell.setImage(ImageProvider.IMG_LOGO);
		shell.setSize(400, 140);
	}

	@Override
	protected void okPressed() {
		// Locals
		String newNodeTagText = this.tagTxt.getText();
		String messageError   = "Number of characters cannot exceed " + NODE_TAG_MAX_LENGTH + " length.";
		String title          = "Edit tag";

		if (newNodeTagText.length() > NODE_TAG_MAX_LENGTH) {
			MessageDialog.openError(getShell(), title, messageError);
		}
		else {
			// Set new node tag value
			this.tagValue = this.tagTxt.getText();
			super.okPressed();	
		}
	}

	public String getTag() {
		return this.tagValue;
	}
	
	private void validatePage() {
		if (tagTxt.getText().equalsIgnoreCase(Constants.EMPTY_CHAR)) {
			okBtn.setEnabled(Boolean.FALSE);
		}
		else {
			okBtn.setEnabled(Boolean.TRUE);
		}
	}

}
// END <RenameDialog> class
// --- -------------- -----