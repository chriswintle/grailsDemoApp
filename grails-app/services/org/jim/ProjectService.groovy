package org.jim

import org.jim.Phase;

import org.jim.ProjectItem

class ProjectService {
	
   def personService = new PersonService();

   def listProjects() {
		def projects = ProjectItem.list(sort:'priority')
		return projects
   }
	
   def getProject(id){
		def project = ProjectItem.get(id)
		return project;  
   }  
   
   def getMaxPriority(){
	   def priority = ProjectItem.createCriteria().get {
		   projections {
			   max "priority"
		   }
	   } as Long
   
	   if(! priority){
		   priority = 0;
	   }
	   
	   return priority;
   }   
	
   def createProject(params){
		
		def techLead = personService.getPerson(params.techLeadId)
		def projectManager = personService.getPerson(params.projectManagerId)
		
		def priority = getMaxPriority()
		
		def phase
		
		try {
			phase = Phase.valueOf(params.phase)
		}
		catch(java.lang.IllegalArgumentException e){
			log.error "Illegal phase value passed";
			return null;
		}
	
		def newIndex = priority + 1;
		
		def project = new ProjectItem(
			name: params.name,
			code: params.code,
			deliveryDate: params.deliveryDate,	
			projectManager:projectManager,
			techLead:techLead,
			priority:newIndex,
			phase:phase
		)
		
		return project.save(flush:true)
		
	}
	
	def updateProject(params){
		if(!params){
			return null
		}
		def techLead = personService.getPerson(params.techLeadId)
		def projectManager = personService.getPerson(params.projectManagerId)
		def project = getProject(params.id);
		
		if(! project){
			return
		}
		
		project.deliveryDate = params.deliveryDate ?: project.deliveryDate ;
		project.code = params.code ?:project.code;
		project.name = params.name ?: project.name;
		project.techLead = techLead ?: project.techLead;
		project.projectManager = projectManager ?: project.projectManager ;
		
		if(params.priority && project.priority != params.priority){
			reprioritiseProjects(project.priority, params.priority)
			project.priority = params.priority;
		}
		
		return project.save(flush:true);
		
	}
	
	def deleteProject(id){
		def project = getProject(id);
		
		if(! project){
			return
		}
		
		def priority = project.priority;
		
		reprioritiseProjects(priority, getMaxPriority()+1)
			
		return project.delete(flush:true)
		
	}
	
	def reprioritiseProjects(currentPriority, newPriority){
		def projects = ProjectItem.list(sort:"priority")

		
		
		//I am certain there is some lovely GORM way of doing this, but this is simple enough.
		projects.eachWithIndex { project, idx ->
			def currentPriorityThing = currentPriority;
			def newPriorityThing = newPriority;
			if(newPriority < currentPriority){
				//then everything after and including the new index must be knocked up by 1 up until after the old index
				
				if(idx >= newPriority -1 && idx <= currentPriority -1){
					project.priority++;
					project.save(flush:true);
				}
				
			}
			else {
				//then everything higher or above the old index slots down by 1 up until the new index
				if(idx >= currentPriority -1 && idx <= newPriority -1){
					project.priority--;
					project.save(flush:true);
				}	
			}
			
		}
	}
}
