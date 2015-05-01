package org.jim

import org.jim.Phase;

import org.jim.Person
import org.jim.ProjectItem;

class ProjectController {
	
	def personService
	def projectService

    def index() { 
		redirect(action:'listProjects')		
	}
	
	
	def listProjects() {		
		def projects = projectService.listProjects(); 
		render(view:"/project/listProjects", model:[projects:projects, phases:Phase.values()]);	
	}
	
	def createProject() {	
		def people = personService.listPeople()
		render(view:"/project/createProject", model:[people:people, phases:Phase.values()])
	}
	
	
	
	def doCreateProject() {
		params.techLeadId = params.long('techLeadId');
		params.projectManagerId = params.long('projectManagerId')
		def projectParams = params;
		
		def project = projectService.createProject(projectParams)
		
		if(!project){
			flash.error = "Could not create project";
			return redirect(action:'listProjects')
		}
		flash.message = "Project Created";
		redirect(action:'listProjects')
		
	}
	
	def editProject() {
		def project = projectService.getProject(params.id)
		def people = personService.listPeople()
		def maxPriority = projectService.getMaxPriority();
		
		if(!project){
			flash.error = "We couldn't find a project with that ID. please try again";
			redirect(action:'listProjects')
		}
		
		render(view:"/project/editProject", model:[project:project, people:people, phases:Phase.values(), maxPriority:maxPriority])
	
	}	
	
	
	def doEditProject() {
		def things = params;
		params.techLeadId = params.long('techLeadId');
		params.projectManagerId = params.long('projectManagerId')
		params.priority = params.int('priority')
		
		def project = projectService.getProject(params.id);
		 
		 if(!project){
			 flash.error = "An error was encountered when editing your project. Please try again"
			 return redirect(action:'editProject', params:[project:params])
		 }
		 
		 def saved = projectService.updateProject(params)
		 
		 if(! saved) {
			 flash.error = "An error was encountered when editing your project. Please try again"
			 return redirect(action:'editProject', params:[project:params])
		 }
		 flash.message = "Project ${saved.code} updated succesfully"
		  return redirect(action:'listProjects')
	}
	

	
	def deleteProject() {
		def deleted = projectService.deleteProject(params.id);
		
		flash.message = "Project Deleted"
		return redirect(action:'listProjects')
	}
}
