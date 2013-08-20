
// Package
package org.centerom.almistack.services.connector;

// Imports
import java.util.Map;

import org.centerom.almistack.core.services.IService;
import org.centerom.almistack.core.services.exceptions.ServiceException;
import org.centerom.almistack.domain.beans.Iteration;
import org.centerom.almistack.domain.beans.Product;
import org.centerom.almistack.domain.beans.Project;
import org.centerom.almistack.domain.beans.Release;
import org.centerom.almistack.domain.beans.Task;
import org.centerom.almistack.domain.beans.UserStory;


public interface IRepositoryConnectorService extends IService {


	/*	--------------------------------------------------------------------------------------------
		Products related info view
		======== ======= ==== ==== */

   	public Map<Integer, Product> getProductsHierarchy() throws ServiceException;

   	/*	--------------------------------------------------------------------------------------------
		Project related info
		======= ======= ==== */

   	public Project getProjectById(Integer projectId) throws ServiceException;
   	
   	public Map<Integer, Release> getProjectReleases(Integer projectId) throws ServiceException;
   	
   	public Map<Integer, Iteration> getProjectIterations(Integer projectId) throws ServiceException;
   	
   	public Map<Integer, UserStory> getProjectUserStories(Integer projectId) throws ServiceException;
   	
   	public Map<Integer, Task> getProjectTasks(Integer projectId) throws ServiceException;

   	public Map<Integer, UserStory> getUnassignedUserStories(Integer projectId) throws ServiceException;

   	public Boolean hasNews(Integer projectId) throws ServiceException;

   	/*	--------------------------------------------------------------------------------------------
		Release related info
		======= ======= ==== */

   	public Release getReleaseById(Integer releaseId) throws ServiceException;
   	
   	public Map<Integer, Iteration> getReleaseIterations(Integer releaseId) throws ServiceException;
   	
   	public Map<Integer, UserStory> getReleaseUserStories(Integer releaseId) throws ServiceException;
   	
   	public Map<Integer, Task> getReleaseTasks(Integer releaseId) throws ServiceException;

   	/*	--------------------------------------------------------------------------------------------
		Iteration related info
		========= ======= ==== */

   	public Iteration getIterationById(Integer iterationId) throws ServiceException;
   	
   	public Map<Integer, UserStory> getIterationUserStories(Integer iterationId) throws ServiceException;
   	
   	public Map<Integer, Task> getIterationTasks(Integer iterationId) throws ServiceException;
   	
   	/*	--------------------------------------------------------------------------------------------
		User Stories related info
		==== ======= ======= ==== */

   	// TODO ...

   	/*	--------------------------------------------------------------------------------------------
		Tasks related info
		==== ======= ==== */

   	public Task getTaskById(Integer taskId) throws ServiceException;

   	/*	--------------------------------------------------------------------------------------------
		User related info
		==== ======= ==== */

   	public Map<Integer, Release> getAllReleases(String userId) throws ServiceException;
   	
   	public Map<Integer, Iteration> getAllItearations(String userId) throws ServiceException;
   	
   	public Map<Integer, UserStory> getAllUserStories(String userId) throws ServiceException;
   	
   	public Map<Integer, Task> getAllTasks(String userId) throws ServiceException;

   	/*	--------------------------------------------------------------------------------------------
		Not classified
		=== ========== */

   	public String[] getDefinedStates() throws ServiceException;

	// TODO ...

}
// END <IRepositoryConnectorService> class
// --- ----------------------------- -----