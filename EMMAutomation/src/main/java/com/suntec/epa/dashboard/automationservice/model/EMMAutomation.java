package com.suntec.epa.dashboard.automationservice.model;

import java.util.List;

/**
 * A sample object representing the actual name and preferred name of a person.
 */
public class EMMAutomation {
	
	private String id;
	private String idDesc;
	private String type;
	
	private List<EMMAutomation> list = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdDesc() {
		return idDesc;
	}

	public void setIdDesc(String idDesc) {
		this.idDesc = idDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<EMMAutomation> getList() {
		return list;
	}

	public void setList(List<EMMAutomation> list) {
		this.list = list;
	}
		
}
