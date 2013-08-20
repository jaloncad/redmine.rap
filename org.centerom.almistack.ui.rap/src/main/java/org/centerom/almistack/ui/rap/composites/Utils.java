
// 
package org.centerom.almistack.ui.rap.composites;

// 
import org.eclipse.rap.rwt.service.ServerPushSession;


/**
 * @version 0.1 - Utils
 */
public class Utils {

	private static ServerPushSession _pushSession = null;
	
	public static void createPushSession() {
		if (_pushSession == null) {
			_pushSession = new ServerPushSession();	
		}
	}
	
	public static void startPushSession() {
		_pushSession.start();
	}
	
	public static void stopPushSession() {
		_pushSession.stop();
	}
}
// END <Utils> class
// --- ------- -----