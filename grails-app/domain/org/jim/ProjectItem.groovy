package org.jim

import org.jim.Phase;

class ProjectItem {
	Integer priority;
	Person techLead;
	Person projectManager;
	Date deliveryDate;
	String code;
	String name;
	Phase phase;
	

    static constraints = {
		priority nullable:false, range: 0..100
		techLead nullable:false
		projectManager nullable:false
		deliveryDate nullable:false
		code nullable:false, unique:true, blank:false
		name nullable:false, unique:true, blank:false
		phase nullable:false
    }
	
	static mapping = {
		table "project_item"
		techLead column: "tech_lead_id"
		projectManager column: "project_manager_id"
	}
}
