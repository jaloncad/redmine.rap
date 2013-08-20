
// Package
package org.centerom.almistack.ui.helpers;

// Imports
import org.centerom.almistack.ui.editorinputs.ProductEditorInput;
import org.centerom.almistack.ui.editorinputs.ProjectEditorInput;
import org.centerom.almistack.ui.editorinputs.ReleaseEditorInput;
import org.centerom.almistack.ui.editorinputs.UserEditorInput;
import org.centerom.almistack.ui.utils.EditorUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;


public class LinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		// Locals
		IStructuredSelection selection = null;
		Object selectedNode            = null;

		if (anInput instanceof ProductEditorInput) {
			selectedNode = ((ProductEditorInput) anInput).getProduct();
		}
		else if (anInput instanceof ProjectEditorInput) {
			selectedNode = ((ProjectEditorInput) anInput).getProject();
		}
		else if (anInput instanceof ReleaseEditorInput) {
			selectedNode = ((ReleaseEditorInput) anInput).getRelease();
		}
		else if (anInput instanceof UserEditorInput) {
			selectedNode = ((UserEditorInput) anInput).getUser();
		}
/*			
		else if (anInput instanceof IterationEditorInput) {
//			editorId    = IterationEditor.ID;
//			editorInput = new IterationEditorInput((Iteration) treeNodeType);
		}
		else if (anInput instanceof UserStoryEditorInput) {
//			editorId    = UserStoryEditor.ID;
//			editorInput = new UserStoryEditorInput((UserStory) treeNodeType);
		}
		else if (anInput instanceof TaskEditorInput) {
//			editorId    = TaskEditor.ID;
//			editorInput = new TaskEditorInput((Task) treeNodeType);
		}
*/

		if (selectedNode != null) {
			selection = new StructuredSelection(selectedNode);
		}
		else {
			selection = StructuredSelection.EMPTY;
		}
		return selection;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		if (!aSelection.isEmpty()) {
			// Activate accurate editor
			EditorUtils.activateEditor(aSelection.getFirstElement());
		}
	}

}
// END <LinkHelper> class
// --- ------------ -----