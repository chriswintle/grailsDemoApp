import org.jim.Phase;

import grails.util.GrailsUtil;

class BootStrap {
	def projectService
	def personService

    def init = { servletContext ->
		
		switch (GrailsUtil.environment) {
		
			case "development":
				def people = personService.listPeople();
				
				if(!people){
					people = generateMockPeople();
				}
				
				
				def projects = projectService.listProjects();
				
				if(! projects) {
					projects = generateMockProjects();
				}
			
			break
			
			case "test":
				
			
			break
			
		}
		
		
		
    }
    def destroy = {
   
		
    }
	
	
	def generateMockPeople(){
		def person1= personService.createPerson(firstName:"Test", lastName:"Person")
		def person2= personService.createPerson(firstName:"Test", lastName:"Person2")
		def person3= personService.createPerson(firstName:"Test", lastName:"Person3")
		
		return personService.listPeople();
	}
	
	def generateMockProjects(){
		def people = personService.listPeople();
		def project1 = projectService.createProject(code:"CODE1", name:"Project 1", priority:1, deliveryDate: new Date(), phase:"Briefing", techLeadId:people.get(0).id, projectManagerId:people.get(1).id);
		def project2 = projectService.createProject(code:"CODE2", name:"Project 2", priority:2, deliveryDate: new Date(), phase:"Scoping", techLeadId:people.get(1).id, projectManagerId:people.get(2).id);
		def project3 = projectService.createProject(code:"CODE3", name:"Project 3", priority:3, deliveryDate: new Date(), phase:"Interaction", techLeadId:people.get(2).id, projectManagerId:people.get(0).id);
		def project4 = projectService.createProject(code:"CODE4", name:"Project 4", priority:4, deliveryDate: new Date(), phase:"Briefing", techLeadId:people.get(1).id, projectManagerId:people.get(1).id);
		def project5 = projectService.createProject(code:"CODE5", name:"Project 5", priority:5, deliveryDate: new Date(), phase:"QA", techLeadId:people.get(2).id, projectManagerId:people.get(1).id);
		
		return projectService.listProjects();
	}
}
