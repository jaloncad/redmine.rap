
// Package
package org.centerom.almistack.ui.utils;

// Imports
import java.util.AbstractMap;
import java.util.Map;

import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.User;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.ui.editorinputs.ProductEditorInput;
import org.centerom.almistack.ui.editorinputs.ProjectEditorInput;
import org.centerom.almistack.ui.editorinputs.ReleaseEditorInput;
import org.centerom.almistack.ui.editorinputs.UserEditorInput;
import org.centerom.almistack.ui.editors.ProductEditor;
import org.centerom.almistack.ui.editors.ProjectEditor;
import org.centerom.almistack.ui.editors.ReleaseEditor;
import org.centerom.almistack.ui.editors.UserEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;


public class EditorUtils {


	public static IEditorPart getEditor() {
		// Locals
		IEditorPart editor = null;

		if (PlatformUI.getWorkbench()
					  .getActiveWorkbenchWindow()
					  .getActivePage() != null) {
			editor = PlatformUI.getWorkbench()
							   .getActiveWorkbenchWindow()
							   .getActivePage()
							   .getActiveEditor();
		}
		return editor;
	}

	public static void openEditor(Object object) {
		// TODO: Activating editor causes CNF problems with focus !!!
		// Locals
		Map.Entry<String,
				  IEditorInput> relation = getIdInputRelation(object);
		try {
			PlatformUI.getWorkbench()
					  .getActiveWorkbenchWindow()
					  .getActivePage()
					  .openEditor(relation.getValue(),
							  	  relation.getKey(),
							  	  Boolean.FALSE);
			//		  .openEditor(editorInput, editorId, Boolean.TRUE);
		}
		catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	public static void activateEditor(Object selectedNode) {
		// Locals
		Map.Entry<String, IEditorInput>
				   relation = getIdInputRelation(selectedNode);
		IWorkbenchPage page = PlatformUI.getWorkbench()
										.getActiveWorkbenchWindow()
										.getActivePage();
		IEditorPart editor  = page.findEditor(relation.getValue());

		// Activate editor
		page.activate(editor);
	}

	private static Map.Entry<String, IEditorInput> getIdInputRelation(Object object) {
		// Locals
		String editorId = null;
		IEditorInput editorInput = null;

		if (object instanceof Product) {
			editorId    = ProductEditor.ID;
			editorInput = new ProductEditorInput((Product) object);
		}
		else if (object instanceof Project) {
			editorId    = ProjectEditor.ID;
			editorInput = new ProjectEditorInput((Project) object);
		}
		else if (object instanceof Release) {
			editorId    = ReleaseEditor.ID;
			editorInput = new ReleaseEditorInput((Release) object);
		}
		else if (object instanceof Iteration) {
//				editorId    = IterationEditor.ID;
//				editorInput = new IterationEditorInput((Iteration) object);
		}
		else if (object instanceof UserStory) {
//				editorId    = UserStoryEditor.ID;
//				editorInput = new UserStoryEditorInput((UserStory) object);
		}
		else if (object instanceof Task) {
//				editorId    = TaskEditor.ID;
//				editorInput = new TaskEditorInput((Task) object);
		}
		else if (object instanceof Task) {
			editorId    = UserEditor.ID;
			editorInput = new UserEditorInput((User) object);
		} 
		// Return relation
		return new AbstractMap.SimpleEntry<String, IEditorInput>(editorId, editorInput);
	}

}
// END <EditorUtils> class
// --- ------------- -----