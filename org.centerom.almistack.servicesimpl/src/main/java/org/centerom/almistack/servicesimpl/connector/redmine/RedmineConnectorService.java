
// Package 
package org.centerom.almistack.servicesimpl.connector.redmine;

// Imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.centerom.almistack.core.Utils;
import org.centerom.almistack.core.services.exceptions.ServiceException;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.User;
import org.centerom.almistack.domain.beans.UserStory;
import org.centerom.almistack.services.connector.IRepositoryConnectorService;
import org.centerom.almistack.servicesimpl.connector.redmine.exceptions.RedmineConnectorServiceException;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.RedmineManager;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.Issue;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssueRelation;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.beans.IssueStatus;
import org.centerom.almistack.servicesimpl.connector.redmine.internal.exceptions.RedmineException;
import org.osgi.service.component.ComponentContext;


public class RedmineConnectorService implements IRepositoryConnectorService {

	// Constants
	// ---------
	private static final String FALSE = "0";

	// Configuration parameters
	// ------------- ----------
	// Connection parameters
	private String host   = null;
	private String apiKey = null;

	// Issue types
	private Integer bug       = null;
	private Integer release   = null;
	private Integer iteration = null;
	private Integer userStory = null;
	private Integer task      = null;

	// Custom fields
	private String assigned = null;
	private String usIdName = null;

	// Redmine manager (API)
	// ------- ------- -----
	private RedmineManager redmineManager     = null;
	private RedmineManager.INCLUDE[] includes = {RedmineManager.INCLUDE.relations};

    // Cache section
	// ----- -------
    // TODO: we need to determine a cache policy to avoid overload connections to Redmine
    private List<String> definedCacheStates = new ArrayList<String>();


	/* --------------------------------------------------------------------------------------------
	   DS methods to configure component
	   == ======= == ========= ========= */

	/**
	 * DS method to activate this component. If this method exists,
	 * it will be invoked when the component is activated.
	 * Note: this method should not be visible for other classes.
	 *
	 * @param cContext component context
	 * @param properties map containing service & configuration properties
	 */
    protected void activate(ComponentContext cContext, Map<String, String> properties) {
    	innerInitialize(properties);
	}

	/**
	 * DS method to deactivate this component. If this method exists,
	 * it will be invoked when the component is deactivated.
	 * Note: this method should not be visible for other classes.
	 *
	 * @param cContext component context
	 * @param reason the reason why component is stopping
	 */
	protected void deactivate(ComponentContext cContext, int reason) {
		disposeManager();
	}

	/**
	 * DS method to modify the configuration properties.
	 * This may be called by multiple threads:
	 * configuration administration updates may be processed asynchronously.
	 * This is called by the activate method, and otherwise when
	 * the configuration properties are modified while the component is
	 * activated.
	 * 
	 * @param properties the new configuration properties 
	 */
	public synchronized void innerInitialize(Map<String, String> properties) {

		try {
			// Connection parameters
			host   = properties.get("connection.host");
			apiKey = properties.get("connection.apiKey");

			// Issue types
			bug       = Integer.parseInt(properties.get("issue.type.bug"));
			release   = Integer.parseInt(properties.get("issue.type.release"));
			iteration = Integer.parseInt(properties.get("issue.type.iteration"));
			userStory = Integer.parseInt(properties.get("issue.type.userStory"));
			task      = Integer.parseInt(properties.get("issue.type.task"));

			// Custom fields
			assigned = properties.get("customField.assigned");
			usIdName = properties.get("customField.usIdName");
			
			// New manager instance with the new configuration
			setManager();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*	--------------------------------------------------------------------------------------------
		Constructor
		=========== */

    /**
     * Default Constructor
     */
    public RedmineConnectorService() {}

	/*	--------------------------------------------------------------------------------------------
		Products related info view
		======== ======= ==== ==== */

	/**
	 * Retrieves ALM products hierarchy.
	 * 
	 * @return the hierarchy represented as a map  
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException 
	 */
    @Override
	public Map<Integer, Product> getProductsHierarchy() throws RedmineConnectorServiceException {
		// Locals
		Map<Integer,
			Product> products = new HashMap<Integer, Product>();
		List<org.centerom
		    	.almistack.servicesimpl
		    	.connector.redmine
		    	.internal.beans
		    	.Project> redmineProjects = null;
		try {
			// Retrieve all Redmine projects 
			redmineProjects = redmineManager.getProjects();

			// For each Redmine project, find out whether is a product or not
			for (org.centerom
				.almistack.servicesimpl
				.connector.redmine
				.internal.beans
				.Project redmineProject : redmineProjects) {

				// Product?
				if (isProduct(redmineProject.getParentId())) {
					// Yes; retrieve all projects associated with current product
					Integer productId = redmineProject.getId(); 
					Map<Integer, Project>
						     projects = this.getProductProjects(productId,
								   							    redmineProjects);
					Product product   = new Product(redmineProject.getId(),
													redmineProject.getName(),
													redmineProject.getDescription(),
													null,
													redmineProject.getCreatedOn(),
													projects);
					// Add product
					products.put(productId, product);

					// For each project of current product, set its owner (the product)
					for (Map.Entry<Integer, Project> project : products.get(productId)
															  .getProjects().entrySet()) {
						((Project) project.getValue()).setOwner(product);
					}
				}
			}
			// Return products map
			return products;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}
    
    
    
    
    
    /*

	public Map<Integer, Release> getProductReleases(Integer productId) throws RedmineConnectorServiceException {
		// Locals
		Map<Integer,
		 	Release> releases = new HashMap<Integer, Release>();
		List<Issue> issues    = null;
		try {
			//
			issues = redmineManager.getProjectIssuesByTrack(projectId,
														    RELEASE_TRACK_ID,
															includes);
			// For each release
			for (Issue issue : issues) {
				// Retrieve release iterations
				Integer releaseId = issue.getId();
				Map<Integer, Iteration>
					  iterations  = this.getReleaseIterations(releaseId, issue.getRelations());
				Release release   = new Release(releaseId,
												issue.getSubject(),
												issue.getStartDate(),
												issue.getDueDate(),
												issue.getEstimatedHours(),
												issue.getDoneRatio().floatValue(),
												null,
												issue.getStatusName(),
												null,
												iterations);
				// Add iterations to current release
				releases.put(releaseId, release);

				// For each iteration of current release, set its owner (the release)
				for (Map.Entry<Integer, Iteration> iteration : releases.get(releaseId).getIterations().entrySet()) {
					((Iteration) iteration.getValue()).setOwner(release);
				}
			}
			// Return Releases list
			return releases;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}
    
    */
    

   	/*	--------------------------------------------------------------------------------------------
		Project related info
		======= ======= ==== */

	/**
	 * Retrieves ALM Project info which id is <projectId>.
	 * 
	 * @param projectId the Project Id
	 * @return the Project info  
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException 
	 */
	@Override
	public Project getProjectById(Integer projectId) throws RedmineConnectorServiceException {
		// Locals
		org.centerom.almistack
	   .servicesimpl.connector
	   .redmine.internal.beans.Project redmineProject = null;
 
		try {
			redmineProject = redmineManager.getProjectById(projectId);

			// Retrieve all releases associated with current project
			Map<Integer, Release> releases = this.getProjectReleases(projectId);
			
			return new Project(redmineProject.getId(),
							   redmineProject.getName(),
							   Utils.buildURL(redmineProject.getHomepage()),
							   redmineProject.getDescription(),
							   null,
							   redmineProject.getCreatedOn(),
							   null,
							   releases);
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	/**
	 * Retrieves all Releases associated to project which id is <projectId>.
	 * 
	 * @param projectId the Project Id
	 * @return the map of Releases  
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException 
	 */
	@Override
	public Map<Integer, Release> getProjectReleases(Integer projectId)
											 throws RedmineConnectorServiceException {
		// Locals
		Map<Integer,
		 	Release> releases = new HashMap<Integer, Release>();
		List<Issue> issues    = null;
		try {
			//
			issues = redmineManager.getProjectIssuesByTrack(projectId,
														    release,
															includes);
			// For each release
			for (Issue issue : issues) {
				// Retrieve Redmine user
				org.centerom.
					almistack.servicesimpl.
						connector.redmine.
							internal.beans.User author = issue.getAuthor();
				Integer releaseId = issue.getId();
				Map<Integer,
					Iteration>
				  	  iterations  = this.getReleaseIterations(releaseId,
				  			  								  issue.getRelations());
				User user         = new User(author.getId(),
											 author.getFirstName(),
											 author.getLastName());				
				Release release   = new Release(releaseId,
												issue.getSubject(),
												issue.getStartDate(),
												issue.getDueDate(),
												issue.getEstimatedHours(),
												issue.getDoneRatio().floatValue(),
												null,
												issue.getStatusName(),
												issue.getDescription(),
												issue.getNotes(),
												user,
												null,
												iterations);
				// Add release
				releases.put(releaseId, release);

				// For each iteration of current release, set its owner (the release)
				for (Map.Entry<Integer, Iteration> iteration : releases.get(releaseId)
															  .getIterations().entrySet()) {
					((Iteration) iteration.getValue()).setOwner(release);
				}
			}
			// Return Releases list
			return releases;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	@Override
	public Map<Integer, Iteration> getProjectIterations(Integer projectId) throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	@Override
	public Map<Integer, UserStory> getProjectUserStories(Integer projectId) throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	@Override
	public Map<Integer, Task> getProjectTasks(Integer projectId) throws RedmineConnectorServiceException {
		// 
		Map<Integer,
			  Task> tasks  = new HashMap<Integer, Task>();
		List<Issue> issues = null;

		// 
		try {
			issues = redmineManager.getProjectIssuesByTrack(projectId, task);
			
			// For each issue, builds Task bean
	    	for (Issue issue : issues) {
	    		// TODO

	    	}
	    	return tasks;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	/**
	 * Retrieves all unassigned User Stories associated to project which id is <projectId>.
	 * 
	 * @param projectId the project identifier
	 * @return the list of unassigned User Stories associated to project which id is <projectId>.
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException
	 */
	@Override
	public Map<Integer, UserStory> getUnassignedUserStories(Integer projectId)
													 throws RedmineConnectorServiceException {
		// Locals
		Map<Integer, UserStory>
			   userStories = new HashMap<Integer, UserStory>();
		List<Issue> issues = null;

		try {
			issues = redmineManager.getProjectIssuesByTrack(projectId,
															userStory);
			// For each User Story (assigned or not)
			for (Issue issue : issues) {
				// User Story is assigned to an Iteration?
				if (issue.getCustomField(assigned).equals(FALSE)) {
					// No; retrieve its tasks and build it
					Integer userStoryId = issue.getId();
					Map<Integer, Task>
								  tasks = this.getUserStoryTasks(issue.getRelations());
					UserStory userStory = new UserStory(userStoryId,
														issue.getCustomField(usIdName),
														issue.getSubject(),
														issue.getStatusName(),
														issue.getEstimatedHours(),
														issue.getDescription(),
														issue.getNotes(),
														null,
														tasks);
					userStories.put(userStoryId, userStory);

					// For each task of current user story, set its owner (the user story)
					for (Map.Entry<Integer, Task> task : userStories.get(userStoryId)
														.getTasks().entrySet()) {
						((Task) task.getValue()).setOwner(userStory);
					}
				}
			}
			// Return User Stories list
			return userStories;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	/**
	 * Retrieves whether input <projectId> has news or not.
	 *
	 * @param projectId the project identifier
	 * @return <True> if <projectId> has news attached, <False> otherwise
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException
	 */		
	@Override
	public Boolean hasNews(Integer projectId) throws RedmineConnectorServiceException {

		try {
			return (redmineManager.getNews(projectId).size() > 0);
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	/*	--------------------------------------------------------------------------------------------
		Release related info
		======= ======= ==== */

	@Override
	public Release getReleaseById(Integer releaseId) throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	/**
	 * Retrieves all Iterations associated to Release which id is <releaseId>.
	 * 
	 * @param releaseId the Release Id
	 * @param issuesRelations the list of issues that are related with <releaseId>
	 * @return the map of Iterations
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException
	 */
	@Override
	public Map<Integer, Iteration> getReleaseIterations(Integer releaseId) 
												 throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	@Override
	public Map<Integer, UserStory> getReleaseUserStories(Integer releaseId)
												  throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	@Override
	public Map<Integer, Task> getReleaseTasks(Integer releaseId)
									   throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

   	/*	--------------------------------------------------------------------------------------------
		Iteration related info
		========= ======= ==== */

	@Override
	public Iteration getIterationById(Integer iterationId) throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	/**
	 * Retrieves all User Stories associated to Iteration which id is <iterationId>.
	 *
	 * @param iterationId the Iteration Id
	 * @param issuesRelations the list of issues that are related with <iterationId>
	 * @return the list of User Stories
	 * @throws RedmineConnectorServiceException the RedmineConnectorServiceException
	 */
	@Override
	public Map<Integer, UserStory> getIterationUserStories(Integer iterationId)
												    throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

	@Override
	public Map<Integer, Task> getIterationTasks(Integer iterationId)
										 throws RedmineConnectorServiceException {
		// TODO
		return null;
	}

   	/*	--------------------------------------------------------------------------------------------
		User Stories related info
		==== ======= ======= ==== */
	
	// TODO ...
	
	/*	--------------------------------------------------------------------------------------------
		Tasks related info
		===== ======= ==== */
	
	@Override
	public Task getTaskById(Integer taskId) throws RedmineConnectorServiceException {
		// Locals
		Task task   = null;
		Issue issue = null;

		// 
		try {
			issue = redmineManager.getIssueById(taskId, includes);

			// Build Task bean given the issue object
			// TODO ...

	    	return task;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

   	/*	--------------------------------------------------------------------------------------------
		User related info
	 	TODO: for now we are not taking in account the userId: it will be done when 
	 	 	  application security will be implemented.
		===== ====================================================================================== */
	
	@Override
	public Map<Integer, Release> getAllReleases(String userId)
										 throws ServiceException {
		// Locals
		Map<Integer,
		 	Release> releases = new HashMap<Integer, Release>();
		List<Issue> issues    = null;

		try {
			//
			issues = redmineManager.getIssuesByTrack(release, includes);

			// For each release
			for (Issue issue : issues) {
				// Retrieve Redmine user
				org.centerom.
					almistack.servicesimpl.
						connector.redmine.
							internal.beans.User author = issue.getAuthor();
				Integer releaseId = issue.getId();
				Map<Integer,
					Iteration>
					  iterations  = this.getReleaseIterations(releaseId,
							  								  issue.getRelations());
				User user         = new User(author.getId(),
											 author.getFirstName(),
											 author.getLastName());
				Release release   = new Release(releaseId,
												issue.getSubject(),
												issue.getStartDate(),
												issue.getDueDate(),
												issue.getEstimatedHours(),
												issue.getDoneRatio().floatValue(),
												null,
												issue.getStatusName(),
												issue.getDescription(),
												issue.getNotes(),
												user,
												null,
												iterations);
				// Add iterations to current release
				releases.put(releaseId, release);
							 
				// For each iteration of current release, set its owner (the release)
				for (Map.Entry<Integer,
							   Iteration> iteration : releases.get(releaseId)
							   								  .getIterations()
							   								  .entrySet()) {
					((Iteration) iteration.getValue()).setOwner(release);
				}
			}
			// Return releases list
			return releases;
		}
		catch (RedmineException e) {
			throw new RedmineConnectorServiceException(e);
		}
	}

	@Override
	public Map<Integer, Iteration> getAllItearations(String userId)
											 throws ServiceException {

		return null;
	}

	@Override
	public Map<Integer, UserStory> getAllUserStories(String userId)
											  throws ServiceException {

		return null;
	}

	@Override
	public Map<Integer, Task> getAllTasks(String userId)
								   throws ServiceException {

		return null;
	}

   	/*	--------------------------------------------------------------------------------------------
		Not classified
		=== ========== */

	/**
	 * Retrieves all available statuses.
	 *
	 * @return the String array with status names
	 * @throws RedmineException the RedmineException
	 */
	@Override
	public String[] getDefinedStates() throws ServiceException {
		// First of all, look up into cache
		if (definedCacheStates.isEmpty()) {
			try {
				List<IssueStatus> states = redmineManager.getStatuses();

				for (IssueStatus state : states) {
					definedCacheStates.add(state.getName());
				}
			}
			catch (RedmineException e) {
				throw new RedmineConnectorServiceException(e);
			}
		}
		// Return all available states
		return (String[]) definedCacheStates.toArray(new String[0]);		
	}

   	/*	--------------------------------------------------------------------------------------------
		Internal
		======== */

	/**
	 * Sets new Redmine manager according with configuration parameters.
	 */
	private void setManager() {
    	redmineManager = new RedmineManager(host, apiKey);
    }

	/**
	 * Free manager.
	 */
    private void disposeManager() {
    	redmineManager = null;
    }

    /**
     * Retrieves all projects associated to product which id is <productId>.
     * 
     * @param productId the product identifier
     * @param redmineProjects the Redmine Projects
     * @return the map of projects associated to <productId>
     * @throws RedmineConnectorServiceException the RedmineConnectorServiceException
     */
	private Map<Integer, Project> getProductProjects(Integer productId,
													 List<org
													 	 .centerom
													 	 .almistack.servicesimpl
													 	 .connector.redmine
													 	 .internal.beans
													 	 .Project> redmineProjects)
											 throws RedmineConnectorServiceException {
		// Locals
		Map<Integer, Project> projects = new HashMap<Integer, Project>();

		try {
			// Iterate all projects retrieved from Redmine 
			for (org.centerom
				.almistack.servicesimpl
				.connector.redmine
				.internal.beans
				.Project redmineProject : redmineProjects) {

				Integer parentId = redmineProject.getParentId();

				// Found project? 
				if (	!Utils.isEmpty(parentId)
					 && parentId.toString().equalsIgnoreCase(productId.toString())) {
					// Yes; add ALM project
					Integer projectId = redmineProject.getId();
					Project project   = this.getProjectById(projectId);
					projects.put(projectId, project);

					// For each release of current project, set its owner (the project)
					for (Map.Entry<Integer, Release> release : projects.get(projectId)
																	   .getReleases()
																	   .entrySet()) {
						((Release) release.getValue()).setOwner(project);
					}
				}
			}
			// Return projects map
			return projects;
		}
		catch (RedmineConnectorServiceException e) {
			throw e;
		}
	}

	/**
	 * Retrieves all iterations associated to release which id is <releaseId>.
	 * 
	 * @param releaseId the release Id
	 * @param issuesRelations the list of issues that are related with <releaseId>
	 * @return the map of iterations
	 * @throws RedmineException the RedmineException
	 */
	private Map<Integer, Iteration> getReleaseIterations(Integer releaseId,
		     											 List<IssueRelation> issuesRelations)
		     									  throws RedmineException {
		// Locals
		Map<Integer, Iteration> iterations = new HashMap<Integer, Iteration>();
		
		for (IssueRelation issueRelation : issuesRelations) {
			// 
			Integer issueToId = issueRelation.getIssueToId();
			Issue issue       = redmineManager.getIssueById(issueToId, includes);
				
			// Are we dealing with an iteration? 
			if (issue.getTracker().getId() == iteration) {
				// Yes; retrieve user stories of current iteration
				Integer iterationId = issue.getId();
				Map<Integer, UserStory>
						userStories = this.getIterationUserStories(issue.getRelations());
				Iteration iteration = new Iteration(iterationId,
													issue.getSubject(),
													issue.getStartDate(),
													issue.getDueDate(),
													issue.getDescription(),
													issue.getNotes(),
													null,
													userStories);
				iterations.put(iterationId, iteration);

				// For each user story of current iteration, set its owner (the iteration)
				for (Map.Entry<Integer, UserStory> userStory : iterations.get(iterationId)
																		 .getUserStories()
																		 .entrySet()) {
					((UserStory) userStory.getValue()).setOwner(iteration);
				} 
			}
		}
		// Return iterations list
		return iterations;
	}

	/**
	 * Retrieves all user stories from <issuesRelations>.
	 *
	 * @param issuesRelations the list of issues that are related with an iteration
	 * @return the list of User Stories
	 * @throws RedmineException the RedmineException
	 */
	private Map<Integer, UserStory> getIterationUserStories(List<IssueRelation> issuesRelations)
													 throws RedmineException {
		// Locals
		Map<Integer, UserStory> userStories =  new HashMap<Integer, UserStory>();

		for (IssueRelation issueRelation : issuesRelations) {
			// 
			Integer issueToId = issueRelation.getIssueToId();
			Issue issue       = redmineManager.getIssueById(issueToId, includes);
			
			// Are we dealing with a user story? 
			if (issue.getTracker().getId() == userStory) {
				// Yes; retrieve its tasks
				Integer userStoryId = issue.getId();
				Map<Integer, Task>
							  tasks = this.getUserStoryTasks(issue.getRelations());
				UserStory userStory = new UserStory(userStoryId,
						  							issue.getCustomField(usIdName),
						  							issue.getSubject(),
						  							issue.getStatusName(),
						  							issue.getEstimatedHours(),
						  							issue.getDescription(),
						  							issue.getNotes(),
						  							null,
						  							tasks);
				userStories.put(userStoryId, userStory);
				
				// For each task of current user story, set its owner (the user story)
				for (Map.Entry<Integer, Task> task : userStories.get(userStoryId)
																.getTasks()
																.entrySet()) {
					((Task) task.getValue()).setOwner(userStory);
				} 
			}
		}
		// Return user stories map
		return userStories;
	}

	/**
	 * Retrieves all tasks from <issuesRelations>.
	 *
	 * @param issuesRelations the list of issues that are related with a user story
	 * @return the list of tasks
	 * @throws RedmineException the RedmineException
	 */
	private Map<Integer, Task> getUserStoryTasks(List<IssueRelation> issuesRelations)
								  		  throws RedmineException {
		// Locals
		Map<Integer, Task> tasks =  new HashMap<Integer, Task>();
		
		for (IssueRelation issueRelation : issuesRelations) {
			// 
			Integer issueToId = issueRelation.getIssueToId();
			Issue issue       = redmineManager.getIssueById(issueToId, includes);
			
			// Are we dealing with a task? 
			if (issue.getTracker().getId() == task) {
				// Yes; add it
				Integer taskId = issue.getId();
				Task task      = new Task(taskId,
						   				  issue.getSubject(),
						   				  null,
						   				  issue.getStatusName(),
						   				  issue.getEstimatedHours(),
						   				  null,
						   				  Boolean.FALSE,
						   				  issue.getDescription(),
						   				  issue.getNotes(),
						   				  null,
						   				  null);
				tasks.put(taskId, task);
				// Task is not the owner of nobody :(
			}
		}
		// Return tasks map
		return tasks;
	}

	/**
	 * Determines whether <id> is a product or not. 
	 * 
	 * @param id the product identifier.
	 * @return <True> if <id> is empty, <False> otherwise.
	 */
	private Boolean isProduct(Integer id) {
		return Utils.isEmpty(id);
	}

}
// END <RedmineConnectorService> class
// --- ------------------------- -----


/*

 	OLD
 	---
 	|
 	v
 
	public List<Issue> getTasksByQueryId(Integer queryId) {


	    	// tasks = mgr.getIssuesGabi(projectId, 13, includes);
	    	// tasks = mgr.getIssues("a1", 13, includes);
	    	// List<SavedQuery> list1 = mgr.getSavedQueries();
	    	// List<SavedQuery> list2 = mgr.getSavedQueries("a1");
	    	// tasks = mgr.getIssuesGabi(7, 5, includes);
 
	    	for (SavedQuery tmp : list1) {
	    		System.out.println("Id Query: " + tmp.getId() + "; Nombre Query: " + tmp.getName() + "; Project Id: " + tmp.getProjectId());
	    		// tasks = mgr.getIssuesGabi(7, 5, includes);
	    	}

		return null;
	}

	public Issue getTaskDetails(Integer taskId) throws ServiceException {
		try {
			return connector.getTaskDetails(taskId);
		}
		catch (RedmineException e) {
			throw new ServiceException(e);
		}
	}

 */
