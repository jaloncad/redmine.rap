
// Package
package org.centerom.almistack.ui.providers;

// Imports
import java.util.Map;

import org.centerom.almistack.core.services.exceptions.ServiceException;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.services.logger.ILoggerService;
import org.centerom.almistack.ui.consumers.ServiceConsumer;
import org.centerom.almistack.ui.views.AgileHierarchyView;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class AgileCNFContentProvider implements ITreeContentProvider {

	// Default serial Id
	private static final long serialVersionUID = 1L;

	// Logger instance
	private ILoggerService logger = ServiceConsumer.getLoggerService();

	// 
	private Map<Integer, Product> agileHierarchy = null;


	@Override
	public Object[] getElements(Object inputElement) {
		return this.getChildren(inputElement);
	}

	@Override
	public Object getParent(Object element) {
		// Locals
		Object parent = null;

		// Project?
		if (element instanceof Project) {
			parent = ((Project) element).getOwner();	// Return product
		}
		// Release?
		else if (element instanceof Release) {
			parent = ((Release) element).getOwner();	// Return project		
		}
		// Iteration?
		else if (element instanceof Iteration) {
			parent = ((Iteration) element).getOwner();	// Return iteration
		}
		// User Story?
		else if (element instanceof UserStory) {
			parent = ((UserStory) element).getOwner();	// Return user story
		}
		// Task?
		else if (element instanceof Task) {
			parent = ((Task) element).getOwner();		// Return task
		}
		return parent;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// Locals
		Object[] children = null;
		
		// Root element?
        if (parentElement instanceof AgileHierarchyView.RootNode) {
        	if (this.agileHierarchy == null) {
        		children = this.getAgileHierarchy().values().toArray();
        	}
        }
		// Product?
        else if (parentElement instanceof Product) {
			Product productNode = ((Product) parentElement);
			children = productNode.getProjects().values().toArray();
		}
		// Project?
		else if (parentElement instanceof Project) {
			
			Project projectNode = ((Project) parentElement);
			
//			List<AbstractBean> childs = new ArrayList<AbstractBean>();
//			childs.add(new Plan(PLAN_NONE_NAME));
//			childs.addAll(projectNode.getReleases().values());
//			children = childs.toArray();
			
			children = projectNode.getReleases().values().toArray();
		}
		// Release?
		else if (parentElement instanceof Release) {
			Release releaseNode = ((Release) parentElement);
			children = releaseNode.getIterations().values().toArray();
		}
		// Iteration?
		else if (parentElement instanceof Iteration) {
			Iteration iterationNode = ((Iteration) parentElement);
			children = iterationNode.getUserStories().values().toArray();
		}
		// User Story?
		else if (parentElement instanceof UserStory) {
			UserStory userStorytNode = ((UserStory) parentElement);
			children = userStorytNode.getTasks().values().toArray();
		}
		return children;
	}

	@Override
	public boolean hasChildren(Object element) {
		// Locals
		Boolean result = Boolean.FALSE;

		// Product?
		if (element instanceof Product) {
			Product productNode = ((Product) element);
			result = !productNode.getProjects().isEmpty();
		}
		// Project?
		else if (element instanceof Project) {
			Project projecttNode = ((Project) element);
			result = !projecttNode.getReleases().isEmpty();
		}
		// Release?
		else if (element instanceof Release) {
			Release releaseNode = ((Release) element);
			result = !releaseNode.getIterations().isEmpty();
		}
		// Iteration?
		else if (element instanceof Iteration) {
			Iteration iterationNode = ((Iteration) element);
			result = !iterationNode.getUserStories().isEmpty();
		}
		// User Story?
		else if (element instanceof UserStory) {
			UserStory userStorytNode = ((UserStory) element);
			result = !userStorytNode.getTasks().isEmpty();
		}
		return result;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
		
//		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		
		
	}

	@Override
	public void dispose() {
		this.agileHierarchy = null;
	}

	/**
	 * Retrieve agile hierarchy to populate the tree.
	 * 
	 * @return the agile hierarchy
	 */
	private  Map<Integer, Product> getAgileHierarchy() {
		
		try {
			logger.debug("adiosssssssssssssssssss");
			this.agileHierarchy = ServiceConsumer.getRepositoryConnectorService().getProductsHierarchy();
		}
		catch (ServiceException e) {
			e.printStackTrace();
		}
		finally {
			return this.agileHierarchy;	
		}
	 }

}
// END <AgileCNFContentProvider> class
// --- ------------------------- -----