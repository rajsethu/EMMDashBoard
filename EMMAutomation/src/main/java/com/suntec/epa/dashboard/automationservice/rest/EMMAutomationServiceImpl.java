package com.suntec.epa.dashboard.automationservice.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.suntec.epa.dashboard.automationservice.dao.EMMAutomationDAOImpl;
import com.suntec.epa.dashboard.automationservice.model.EMMAutomationCmdResponse;


/**
 * Implementation of sample service.
 */
@Component
public class EMMAutomationServiceImpl implements EMMAutomationService {
	private static final Logger logger = LoggerFactory.getLogger(EMMAutomationServiceImpl.class);
	
	@Autowired
	private EMMAutomationDAOImpl dao;
	
	@Value("${masterScript.command}")
	private String masterScript ;
	
	@Value("${ldapservername}")
	private String ldapservername;
	
	@Override
	public String getHierarchy() {
		String hierarchy = null;
		try {
			hierarchy = dao.getHierarchy();
		} catch (Exception e) {
			logger.error("Failed to retrieve hierarchy.",e);
		}
		return hierarchy;
	}
	
	@Override
	public String getServerList(String lob, String project, String application) {
		// TODO Auto-generated method stub
		String serverList = null;
		try {
			// Sync the status of the applications running on the servers - Running/Stopped
			syncServerApplnStatus(lob, project, application);
			serverList = dao.getServerList(lob, project, application);
		} catch (Exception e) {
			logger.error("Failed to retrieve Server List.",e);
		}
		return serverList;
	}
		
	private void syncServerApplnStatus(String lob, String project, String application) {
		// TODO Auto-generated method stub
		String finalCommand="";
		BufferedReader bufReader 	= null;
		InputStream inStream 		= null;
		Process process = null;
		String line = null;
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			File file = new File(".");
			System.out.println(file.getAbsoluteFile());
			finalCommand = masterScript.replace("<lob>", lob).replace("<project>", project)
					.replace("<app>", application).replace("<hostname>", "ALL").replace("<startstop>", "SYNCSTATUS");
			
			System.out.println("final Command before execution :"+finalCommand);
			process = Runtime.getRuntime().exec(finalCommand);
			process.waitFor();
			inStream = process.getInputStream();
			bufReader = new BufferedReader(new InputStreamReader(inStream));
			while( ( line = bufReader.readLine()) != null) {
				System.out.println(line);
				stringBuilder.append(line).append("\n");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}		
	}

	/* 
	 * (non-Javadoc)
	 * expected start stop status structure
 	DATA_WIFI_Radius_servername:START:success
	DATA_WIFI_Radius_servername:START:failure

	DATA_WIFI_Radius_servername:STOP:success
	DATA_WIFI_Radius_servername:STOP:failure
	
	* @see com.suntec.epa.dashboard.automationservice.rest.EMMAutomationService#executeCommand(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String executeCommand(String lob, String project, String application, String hostName, String execCmd) {
		// TODO Auto-generated method stub
		String finalCommand="";
		BufferedReader bufReader 	= null;
		InputStream inStream 		= null;
		Process process = null;
		String line = null;
		StringBuilder stringBuilder = new StringBuilder("");
		ArrayList responseList = new ArrayList<>() ;
		String jsonInString = null;
		try {
			File file = new File(".");
			System.out.println(file.getAbsoluteFile());
			finalCommand = masterScript.replace("<lob>", lob).replace("<project>", project)
					.replace("<app>", application).replace("<hostname>", hostName).replace("<startstop>", execCmd);
			
			System.out.println("final Command before execution :"+finalCommand);
			process = Runtime.getRuntime().exec(finalCommand);
			process.waitFor();
			inStream = process.getInputStream();
			bufReader = new BufferedReader(new InputStreamReader(inStream));
			while( ( line = bufReader.readLine()) != null) {
				System.out.println(line);
				stringBuilder.append(line).append("\n");
			}
			
			EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
						
			if (stringBuilder.toString().contains("SUCCESS")) {
				response.setStatus("Success");
			}else {
				response.setStatus("Failure");
			}
			responseList.add(response);
			jsonInString = new Gson().toJson(responseList);

		} catch (IOException | InterruptedException e) {
			EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
			response.setStatus("Failure");
			responseList.add(response);
			jsonInString = new Gson().toJson(responseList);
			e.printStackTrace();
		}
		return jsonInString;
	}

	@Override
	public String getServerApplnLog(String lob, String project, String application, String hostName) {
		// TODO Auto-generated method stub
		String finalCommand="";
		BufferedReader bufReader 	= null;
		InputStream inStream 		= null;
		Process process = null;
		String line = null;
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			File file = new File(".");
			System.out.println(file.getAbsoluteFile());
			finalCommand = masterScript.replace("<lob>", lob).replace("<project>", project)
					.replace("<app>", application).replace("<hostname>", hostName).replace("<startstop>", null);
			
			System.out.println("final Command before execution :"+finalCommand);
			process = Runtime.getRuntime().exec(finalCommand);
			process.waitFor();
			inStream = process.getInputStream();
			bufReader = new BufferedReader(new InputStreamReader(inStream));
			while( ( line = bufReader.readLine()) != null) {
				System.out.println(line);
				stringBuilder.append(line).append("\n");
			}
			return null;
		}catch (IOException | InterruptedException e) {
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String authenticate(String username, String password) {
		// TODO Auto-generated method stub		
		ArrayList responseList = new ArrayList<>() ;
		String jsonInString = null;
		
		Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(LdapContext.CONTROL_FACTORIES, "com.sun.jndi.ldap.ControlFactory ");
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); 
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, username); 
        env.put(Context.SECURITY_CREDENTIALS, password); 
        env.put(Context.PROVIDER_URL, ldapservername); 
         
        try { 
              DirContext ctx; 
              ctx = new InitialDirContext(env); 
              ctx.close();     
              EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
  			  response.setStatus("Success");
  			  responseList.add(response);
  			  jsonInString = new Gson().toJson(responseList);
        }catch (CommunicationException e) {
        	e.printStackTrace();
        	EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
			response.setStatus("Failure");
			responseList.add(response);
			jsonInString = new Gson().toJson(responseList);

        }catch (AuthenticationException e) {
        	e.printStackTrace();
        	EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
			response.setStatus("Failure");
			responseList.add(response);
			jsonInString = new Gson().toJson(responseList);
              
        }catch (Exception e) {
        	e.printStackTrace();
        	EMMAutomationCmdResponse response = new EMMAutomationCmdResponse();
			response.setStatus("Failure");
			responseList.add(response);
			jsonInString = new Gson().toJson(responseList);
              
        }
		return jsonInString;
	}	
}
