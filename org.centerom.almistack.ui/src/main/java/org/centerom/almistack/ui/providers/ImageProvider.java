

// Package
package org.centerom.almistack.ui.providers;

// Imports
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;


public class ImageProvider {

	// Images folder location
	public static final String IMAGES_FOLDER = "src/main/resources/images/";
	public static final String PLUGIN_ID     = "org.centerom.almistack.ui";

	// Login form
	public final static Image IMG_KEY_GO    = registerImage("key_go.png");
	public final static Image IMG_KEY       = registerImage("lock.png");
	public final static Image IMG_LOCK_OPEN = registerImage("unlock.png");
	
	// Header form
	public final static Image IMG_LOGO_HEADER = registerImage("logo_header.png");
	public final static Image IMG_USER        = registerImage("user.png");
	public final static Image IMG_LOGOUT      = registerImage("logout.png");
//	public final static Image IMG_USER       = registerImage("users/user_gomer.png");

	// Workbench
	// ---------
	// Tool bar
	public final static Image IMG_DUMMY_1  = registerImage("database_add.png");
	public final static Image IMG_DUMMY_2  = registerImage("database_connect.png");
	public final static Image IMG_DUMMY_3  = registerImage("database_edit.png");
	
	// CNF
	public final static Image IMG_PRODUCT       = registerImage("product.png");
	public final static Image IMG_PROJECT       = registerImage("project.png");
	public final static Image IMG_RELEASE       = registerImage("release.png");
	public final static Image IMG_ITERATION     = registerImage("iteration.png");
	public final static Image IMG_USER_STORY    = registerImage("user_story.png");
	public final static Image IMG_TASK          = registerImage("task.png");
	public final static Image IMG_PLAN          = registerImage("plan.png");
	public final static Image IMG_LOGO          = registerImage("logo.png");
	public final static Image IMG_FOCUS_PROJECT = registerImage("focus_project.png");
	
	// Views
	// -----
	// Common
	public final static Image IMG_SAVE   = registerImage("save.png");
	public final static Image IMG_CANCEL = registerImage("cancel.png");
	// Releases
	public final static Image IMG_RELEASE_ADD    = registerImage("release_add.png");
	public final static Image IMG_RELEASE_EDIT   = registerImage("release_edit.png");
	public final static Image IMG_RELEASE_DELETE = registerImage("release_delete.png");

	// Editors
	// -------
	// Release
	public final static Image IMG_EDITOR = registerImage("editor.png");

	
	public final static Image IMG_CALENDAR = registerImage("calendar.png");
	
	
	
	// Footer
	public final static Image IMG_COPYRIGHT = registerImage("copyright.png");
	public final static Image IMG_BIN_EMTY  = registerImage("bin_empty.png");
	public final static Image IMG_BIN_FULL  = registerImage("bin_full.png");

/*	
	// Not classified yet
	public final static Image IMG_INFO    = registerImage("info.png");
	public final static Image IMG_BROWSER = registerImage("browser.png");
	public final static Image IMG_BELL_BUSY  = registerImage("bell_busy.png");
	public final static Image IMG_BELL_EMPTY = registerImage("bell_empty.png");
	public final static Image IMG_CHAT       = registerImage("chat.png");
	public final static Image IMG_HELP       = registerImage("help.png");


	
	public final static Image IMG_SETUP      = registerImage("setup.png");
	public final static Image IMG_HAND_LENS  = registerImage("hand_lens.png");
	

	// Work area form
*/

	// Deprecated
//	public final static Image IMG_BOOKMARK   = registerImage("bookmark.png");
	public final static Image IMG_HELP_LARGE = registerImage("help_large.png");


	private ImageProvider() {}

	private static Image registerImage(String imageName) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, IMAGES_FOLDER + imageName).createImage();
	}

}
// END <ImageProvider> class
// --- --------------- -----