package com.suntec.epa.dashboard.automationservice.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * EPA upgrade service REST interface.
 */
@Path("/v1")
public interface EMMAutomationService {
	/**
	 * Retrieve the preferred name for someone.
	 * @param name The person's actual name
	 * @return A Response containing a single String, or an appropriate status that represented the outcome of retrieving the data.
	 */
	@Path("/getHierarchy")
	@GET
	@Produces("application/json")
	String getHierarchy();
	/**
	 * Set the preferred name for someone.
	 * @param name The person's actual name
	 * @return A Response containing a single String, or an appropriate status that represented the outcome of retrieving the data.
	 */
	@Path("/getServerList/lob/{lob}/project/{project}/application/{application}")
	@GET
	@Produces("application/json")
	String getServerList(@PathParam("lob") String lob, 
						 @PathParam("project") String project,
						 @PathParam("application") String application);
	
	/**
	 * Set the preferred name for someone.
	 * @param name The person's actual name
	 * @return A Response containing a single String, or an appropriate status that represented the outcome of retrieving the data.
	 */
	@Path("/executeCommand/lob/{lob}/project/{project}/application/{application}/hostName/{hostName}/execCmd/{execCmd}")
	@GET
	@Produces("text/plain")
	String executeCommand(@PathParam("lob") String lob, 
						 @PathParam("project") String project,
						 @PathParam("application") String application,
						 @PathParam("hostName") String hostName,
						 @PathParam("execCmd") String execCmd);
	
	/**
	 * Set the preferred name for someone.
	 * @param name The person's actual name
	 * @return A Response containing a single String, or an appropriate status that represented the outcome of retrieving the data.
	 */
	@Path("/getServerApplnLog/lob/{lob}/project/{project}/application/{application}/hostName/{hostName}")
	@GET
	@Produces("application/json")
	String getServerApplnLog(@PathParam("lob") String lob, 
							@PathParam("project") String project,
							@PathParam("application") String application,
							@PathParam("hostName") String hostName);
	
	/**
	 * authenticate User 
	 *  @params Username and password
	 *  return sucess or failure
	 */
	
	@Path("/authenticate/username/{username}/password/{password}")
	@GET
	@Produces("application/json")
	String authenticate(@PathParam("username") String username, 
						@PathParam("password") String password);


}
