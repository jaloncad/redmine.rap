
// Package
package org.centerom.almistack.ui.rap.composites;

// Imports
import org.centerom.almistack.ui.providers.ImageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;


public class Header extends Composite {

	// Default Serial Version UID
	private static final long serialVersionUID = 1L;

	// Layout
	private static final Color BACK_COLOR = new Color(null, new RGB(174, 186, 255));
	private static final int HEIGHT       = 70;
	private static final int TITLE_HORIZONTAL_ALIGN  = 20;
	private static final int LOGOUT_HORIZONTAL_ALIGN = 10;
	
	
	private FormToolkit toolkit = new FormToolkit(this.getDisplay());

	public Header(Composite holder) {

		super(holder, SWT.NONE);
/*
		this.setLayout((new CustomLayout(4, Boolean.FALSE)).getLayout());
		this.setLayoutData((new CustomData(HEIGHT,
										   SWT.FILL,
										   SWT.FILL,
										   Boolean.TRUE,
										   Boolean.FALSE)).getData());
*/
		this.setBackground(BACK_COLOR);

		// Add header contents and set server push notifications
		createContents();
	}


	private void createContents() {
		// Logo
		CLabel logo = new CLabel(this, SWT.NULL);
		logo.setImage(ImageProvider.IMG_LOGO);
		logo.setBackground(BACK_COLOR);

		// Title
		Label title = toolkit.createLabel(this, "Alm-iStack Collaborative Tool");
/*		
		title.setLayoutData(new CustomData(SWT.LEFT,
										   SWT.CENTER,
										   Boolean.FALSE,
										   Boolean.FALSE,
										   TITLE_HORIZONTAL_ALIGN).getData());
*/										   
		title.setFont(new Font(this.getDisplay(), "Verdana", 28, SWT.NORMAL));
		title.setBackground(BACK_COLOR);

		// Logged user
		CLabel user = new CLabel(this, SWT.NULL);
		user.setLayoutData(new GridData(SWT.RIGHT,
										SWT.CENTER,
										Boolean.TRUE,
										Boolean.FALSE));
		user.setImage(ImageProvider.IMG_USER);
		user.setText("Welcome Gabriel Alonso Iglesias");
		user.setCursor(user.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		user.setBackground(BACK_COLOR);

		// Logout 
		CLabel logout = new CLabel(this, SWT.NULL);
/*		
		logout.setLayoutData(new CustomData(SWT.RIGHT,
											SWT.CENTER,
											Boolean.FALSE,
											Boolean.FALSE,
											LOGOUT_HORIZONTAL_ALIGN).getData());
*/											
		logout.setImage(ImageProvider.IMG_LOGOUT);
		logout.setText("Logout");
		logout.setCursor(logout.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
		logout.setBackground(BACK_COLOR);
	}


/*	TODO	
	private void setAlarm() {

		// 
		Utils.createPushSession();

		
		
		Runnable bgRunnable = new Runnable() {

			public void run() {
				// do some background work ...
				// schedule the UI update
				try {

					while (Boolean.TRUE) {

						System.out.println("Comprobando -------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

						Thread.sleep(SECONDS_TO_SLEEP);

						if (RedmineConnectorService.getInstance().hasNews(PROJECT_ID)) {

							System.out.println("Activado -------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

							alerts.setImage(ImageProvider.IMG_BELL_BUSY);
							Utils.stopPushSession();
							break;
						}
					}
				}
				catch (	  InterruptedException
						| RedmineConnectorException e) {
					e.printStackTrace();
				}

				display.asyncExec(new Runnable() {
					public void run() {
						alerts.setImage(ImageProvider.IMG_BELL_BUSY);
						Utils.stopPushSession();						
					}
				});

			}
		};

		Utils.startPushSession();
		Thread bgThread = new Thread(bgRunnable);
		bgThread.setDaemon(Boolean.TRUE);
		bgThread.start();
	}
*/

}
// END <Header> class 
// --- -------- -----