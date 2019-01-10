package com.suntec.epa.dashboard.automationservice.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.suntec.epa.dashboard.automationservice.model.EMMAutomation;
import com.suntec.epa.dashboard.automationservice.model.EMMAutomationFilterList;

@Component
public class EMMAutomationDAOImpl implements EMMAutomationDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EMMAutomationDAOImpl.class);
	
	@Value("${sqllite.driver.class}")
	private String driverClass;
	
	@Value("${sqllite.datasource.url}")
	private String dbURL;

	@SuppressWarnings("unchecked")
	public String getHierarchy() throws PersistenceException {
		// TODO Auto-generated method stub
		String lob = "";
		String project = "";
		List<EMMAutomation> list = new ArrayList<>();
		String jsonInString = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			Class.forName(driverClass);
			con = DriverManager.getConnection(dbURL, null, null);
			con.setAutoCommit(true);// / Giving DB-Auto Commit --	
			String sql="SELECT lc_name,pc_name,ac_name FROM LOB_CONFIG,PROJECT_CONFIG,APPLICATION_CONFIG where lc_id = pc_lc_id and pc_id = ac_pc_id order by lc_name,pc_name";
			stmt  = con.createStatement();
			rs    = stmt.executeQuery(sql);
			
			@SuppressWarnings("rawtypes")
			ArrayList projectList = new ArrayList<>() ;
			@SuppressWarnings("rawtypes")
			ArrayList applntList = new ArrayList<>() ;
			
			
			while(rs.next()) {	
				
				if( ! lob.equals(rs.getString("lc_name"))) {
					EMMAutomation lobData = new EMMAutomation();
					lob = rs.getString("lc_name");
					lobData.setId(lob);
					lobData.setIdDesc(lob);		
					lobData.setType("lob");
					
					list.add(lobData);
					
					if( null != projectList && projectList.size() > 0) {
						 projectList = new ArrayList<>() ;
					}
					
					if( null != applntList && applntList.size() > 0) {
						 applntList = new ArrayList<>() ;
					}			
					lobData.setList(projectList);
				}				
				
				if( ! project.equals(rs.getString("pc_name"))) {
					project = rs.getString("pc_name");	
					EMMAutomation projectData = new EMMAutomation();
					projectData.setId(project);
					projectData.setIdDesc(project);		
					projectData.setType("PJ");
					
				
					if( null != applntList && applntList.size() > 0) {
						 applntList = new ArrayList<>() ;
					}
					projectData.setList(applntList);
					projectList.add(projectData);
				}
				EMMAutomation applData = new EMMAutomation();
				applData.setId( rs.getString("ac_name"));
				applData.setIdDesc( rs.getString("ac_name"));		
				applData.setType("APP");
				applntList.add(applData);
				
				project = rs.getString("pc_name");
				lob = rs.getString("lc_name");
			}
			//jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emmData);
			System.out.println(list);
			jsonInString = new Gson().toJson(list);
			
        } catch (Exception e) {
        	LOGGER.error(" Excpetion while calling DB procedure {}", e);
	    }
		finally {
	    	try {
	    		if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
		    	if(con!=null)con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        return jsonInString;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getServerList(String lob, String project, String application)
			throws PersistenceException {
		// TODO Auto-generated method stub
		String jsonInString = null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;

		try {
			Class.forName(driverClass);
			con = DriverManager.getConnection(dbURL, null, null);
			con.setAutoCommit(true);// / Giving DB-Auto Commit --	
			String sql="SELECT sc_hostname,scd_status FROM APPLICATION_CONFIG,SERVER_CONFIG,SERVER_CONFIG_DET where ac_name = '"+application+"' and ac_id = sc_ac_id and sc_id = scd_sc_id";
			stmt  = con.createStatement();
			rs    = stmt.executeQuery(sql);
			
			@SuppressWarnings("rawtypes")
			ArrayList serverList = new ArrayList<>() ;
						
			while(rs.next()) {	
				EMMAutomationFilterList filter = new EMMAutomationFilterList();
				filter.setLob(lob);
				filter.setProject(project);
				filter.setApplication(application);
				filter.setServer(rs.getString("sc_hostname"));
				filter.setStatus(rs.getString("scd_status"));
				serverList.add(filter);
			}
			jsonInString = new Gson().toJson(serverList);
			
        } catch (Exception e) {
        	LOGGER.error(" Excpetion while calling DB procedure {}", e);
	    }finally {
	    	try {
				if(stmt!=null)stmt.close();
				if(rs!=null)rs.close();
		    	if(con!=null)con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }		
        return jsonInString;
	}
}
