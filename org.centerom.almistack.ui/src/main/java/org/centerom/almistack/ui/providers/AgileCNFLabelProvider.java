
// Package
package org.centerom.almistack.ui.providers;

// Imports
import org.centerom.almistack.domain.beans.AbstractBean;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Plan;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;


public class AgileCNFLabelProvider implements ICommonLabelProvider {

	// Default Serial Id
	private static final long serialVersionUID = 1L;


	@Override
	public void addListener(ILabelProviderListener listener) {}

	@Override
	public void dispose() {}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getImage(Object element) {
		// Locals
		Image nodeImage = null;

		if (element instanceof Product) {
			nodeImage = ImageProvider.IMG_PRODUCT;
		}
		else if (element instanceof Plan) {
			nodeImage = ImageProvider.IMG_PLAN;
		}
		else if (element instanceof Project) {
			nodeImage = ImageProvider.IMG_PROJECT;
		}
		else if (element instanceof Release) {
			nodeImage = ImageProvider.IMG_RELEASE;
		}
		else if (element instanceof Iteration) {
			nodeImage = ImageProvider.IMG_ITERATION;
		}
		else if (element instanceof UserStory) {
			nodeImage = ImageProvider.IMG_USER_STORY;
		}
		else if (element instanceof Task) {
			nodeImage = ImageProvider.IMG_TASK;
		}
		return nodeImage;
	}

	@Override
	public String getText(Object element) {
		// Locals
		String nodeTag = null;

		if (element instanceof AbstractBean) {
			nodeTag = ((AbstractBean) element).getName();
		}

		return nodeTag;
	}

	@Override
	public void restoreState(IMemento aMemento) {}

	@Override
	public void saveState(IMemento aMemento) {}

	@Override
	public String getDescription(Object anElement) {
		return null;
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {}

}
//END <AgileCNFLabelProvider> class
//--- ----------------------- -----