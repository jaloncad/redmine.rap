package org.centerom.almistack.ui.commands;

import org.centerom.almistack.domain.beans.Release;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

public class NewItemCommand extends AbstractHandler {

	private Release release = null;

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		
//		IEditorPart org.eclipse.ui.IWorkbenchPage.openEditor(IEditorInput input, String editorId) throws PartInitException
	
		System.out.println("execute invoqued !!!");
		
		return null;
	}

}
