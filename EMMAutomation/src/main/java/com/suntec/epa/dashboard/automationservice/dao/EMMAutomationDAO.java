package com.suntec.epa.dashboard.automationservice.dao;

/**
 * Sample DAO interface.
 */
public interface EMMAutomationDAO {
	String getHierarchy() throws PersistenceException;
	String getServerList(String lob, String project, String application) throws PersistenceException;
}
