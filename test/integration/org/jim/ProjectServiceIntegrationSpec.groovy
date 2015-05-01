package org.jim



import org.jim.Phase;

import org.jim.Person;
import org.jim.ProjectItem;

import spock.lang.*

/**
 *
 */
class ProjectServiceIntegrationSpec extends Specification {
	def projectService
	Person person1
	Person person2
    
	def setup() {
		person1 = new Person(firstName:"Test1", lastName: "Test").save(flush:true);
		person2 = new Person(firstName:"Test2", lastName: "Test").save(flush:true);
    }

    def cleanup() {
		ProjectItem.deleteAll()
		Person.deleteAll()
    }

    void "testCreateProject OK"() {
	
		when:
			def params = [name:"projectName", code:"projectCode", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def project = projectService.createProject(params)
		
		then:
		
			project.priority == 1
			project.name == "projectName";
			project.code == "projectCode";
			project.phase == Phase.valueOf("Briefing");
		
    }
	
	
	
	
	void "testCreateProject With Missing Data"() {
		
		when:
		
		//given

			def params = [name:null, code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def project = projectService.createProject(params)
			
		then:
		//then
			project == null
		
	}
	
	void "testCreateProject With Duplicate Name"() {
		
		when:
		
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def params2 = [name:"test", code:"test1", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def project = projectService.createProject(params)
			def project2 = projectService.createProject(params2)
		
		then:
			project2 == null
			
	}

	
	void "testCreateProject With Duplicate Code"() {
		setup:
		//ProjectItem.deleteAll()
		
		when:
	
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def params2 = [name:"test1", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def project = projectService.createProject(params)
			def project2 = projectService.createProject(params2)
		
		then:
			project2 == null
		
	}
	
	
	void "testCreateProject With Null tech lead"() {
		
		when:
	
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:9090900, projectManagerId:person2.id]
	
			def project = projectService.createProject(params)
			
		then:
			project == null
		
	}
	void "testCreateProject With Null project manager"() {
		
		when:
	
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:90909090]
	
			def project = projectService.createProject(params)
		
		then:
			project == null
		
	}
	
	
	void "testCreateProject With Null tech lead id"() {
		
		when:
	
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:null, projectManagerId:person2.id]
			def project = projectService.createProject(params)
			
		then:
			project == null
		
	}
	void "testCreateProject With Null project manager id"() {
		
		when:
	
			def params = [name:"test", code:"test", phase:"Briefing", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:null]	
			def project = projectService.createProject(params)
		
		then:
			project == null
		
	}
	
	void "testCreateProject With Invalid Phase"() {
		
		when:
	
			def params = [name:"test", code:"test", phase:"INVALID", deliveryDate:new Date(), techLeadId:person1.id, projectManagerId:person2.id]
			def project = projectService.createProject(params)

		
		then:
			project == null
		
	}
	
	
	
	void "testGetProject OK"(){
		when:
			def project = new ProjectItem(
				name: "Name",
				code: "Code",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			
			def result = projectService.getProject(project.id)
		
		then:
		
			result.priority == 1
			result.code == "Code"
			result.techLead.id == person2.id
		
	}
	
	
	void "testGetProject No result"(){
		when:
			def result = projectService.getProject(565656)
		
		then:
			result == null
		
	}
	
	
	void "testGetProject Null ID"(){
		when:
			def result = projectService.getProject(null)
		
		then:
			result == null
		
	}
	
	
	void "test listProjects OK"(){
		when:
			def project = new ProjectItem(
				name: "Name",
				code: "Code",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project2 = new ProjectItem(
				name: "Name2",
				code: "Code2",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			
			def result = projectService.listProjects()
		
		then:
		
			result.size() == 2
			result.get(0).code == "Code"
			result.get(1).code == "Code2"
		
	}
	
	
	void "test listProjects No results"(){
		when:
			def result = projectService.listProjects()
		
		then:
		
			result.size() == 0
		
	}
	
	
	void "test getMaxPriority OK"(){
		when:
			def project = new ProjectItem(
				name: "Name",
				code: "Code",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
		
			def result = projectService.getMaxPriority()
		
		then:
			result == 1
		
	}
	
	void "test getMaxPriority No result"(){
		when:
			def result = projectService.getMaxPriority()
		then:
			result == 0
		
	}
	
	
	
	void "test updateProject OK"(){
		when:
			def project = new ProjectItem(
				name: "Name",
				code: "Code",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			
			def params = [name:"Updated Name", id:project.id]
		
			def result = projectService.updateProject(params)
		
		then:
			result.name == "Updated Name"
			result.code == "Code"
		
	}
	
	
	void "test updateProject No Params"(){
		when:
			def project = new ProjectItem(
				name: "Name",
				code: "Code",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
		
			def result = projectService.updateProject(null)
		
		then:
			result == null
		
	}
	
	
	void "test updateProject No Project"(){
		when:
		    def params = [name:"Updated Name", id:12345678]
			def result = projectService.updateProject(null)
		
		then:
			result == null
		
	}
	
	
	
	void "test reprioritiseProjects move down OK"(){
		when:
		def project1 = new ProjectItem(
			name: "Name1",
			code: "Code1",
			deliveryDate: new Date(),
			projectManager:person1,
			techLead:person2,
			priority:1,
			phase:Phase.valueOf("Briefing")
		).save(flush:true)
		
		def project2 = new ProjectItem(
			name: "Name2",
			code: "Code2",
			deliveryDate: new Date(),
			projectManager:person1,
			techLead:person2,
			priority:2, 
			phase:Phase.valueOf("Briefing")
		).save(flush:true)
		
		def project3 = new ProjectItem(
			name: "Name3",
			code: "Code3",
			deliveryDate: new Date(),
			projectManager:person1,
			techLead:person2,
			priority:3,
			phase:Phase.valueOf("Briefing")
		).save(flush:true)
		
		def project4 = new ProjectItem(
			name: "Name4",
			code: "Code4",
			deliveryDate: new Date(),
			projectManager:person1,
			techLead:person2,
			priority:4,
			phase:Phase.valueOf("Briefing")
		).save(flush:true)
		
		
		//let's move project 3 to to be priority 1
		
		projectService.reprioritiseProjects(3, 1)
		
		
		then:
		
			def project1Priority = ProjectItem.get(project1.id).priority
			def project2Priority = ProjectItem.get(project2.id).priority
			def project4Priority = ProjectItem.get(project4.id).priority
		
			//other projects should have been shuffled
			project1Priority == 2
			project2Priority == 3
			project4Priority == 4
			
	}
	
	
	void "test reprioritiseProjects move up OK"(){
		when:
			def project1 = new ProjectItem(
				name: "Name1",
				code: "Code1",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project2 = new ProjectItem(
				name: "Name2",
				code: "Code2",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:2,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project3 = new ProjectItem(
				name: "Name3",
				code: "Code3",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:3,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project4 = new ProjectItem(
				name: "Name4",
				code: "Code4",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:4,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
		
		
		//let's move project 1 to to be priority 3
		
		projectService.reprioritiseProjects(1, 3)
		
		
		then:
		
			def project2Priority = ProjectItem.get(project2.id).priority
			def project3Priority = ProjectItem.get(project3.id).priority
			def project4Priority = ProjectItem.get(project4.id).priority
		
			//other projects should have been shuffled
			project2Priority == 1
			project3Priority == 2
			project4Priority == 4
			
	}
	
	
	void "test reprioritiseProjects invalid indeces"(){
		when:
			def project1 = new ProjectItem(
				name: "Name1",
				code: "Code1",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project2 = new ProjectItem(
				name: "Name2",
				code: "Code2",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:2,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project3 = new ProjectItem(
				name: "Name3",
				code: "Code3",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:3,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project4 = new ProjectItem(
				name: "Name4",
				code: "Code4",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:4,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
		
		
		//let's move project 1 to to be priority 3
		
		projectService.reprioritiseProjects(100, 92)
		
		
		then:
		
			def project2Priority = ProjectItem.get(project2.id).priority
			def project3Priority = ProjectItem.get(project3.id).priority
			def project4Priority = ProjectItem.get(project4.id).priority
		
			//other projects should have been shuffled
			project2Priority == 2
			project3Priority == 3
			project4Priority == 4
			
	}
	
	
	
	void "test deleteProject OK"(){
		when:
			def project1 = new ProjectItem(
				name: "Name1",
				code: "Code1",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project2 = new ProjectItem(
				name: "Name2",
				code: "Code2",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:2,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project3 = new ProjectItem(
				name: "Name3",
				code: "Code3",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:3,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project4 = new ProjectItem(
				name: "Name4",
				code: "Code4",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:4,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
		
		
		//let's delete project 3
		
		projectService.deleteProject(project3.id)
		
		
		then:
		
			def project1Priority = ProjectItem.get(project1.id).priority
			def project2Priority = ProjectItem.get(project2.id).priority
			def project4Priority = ProjectItem.get(project4.id).priority
		
			//other projects should have been shuffled
			project1Priority == 1
			project2Priority == 2
			project4Priority == 3
			
			def deleted = ProjectItem.get(project3.id);
			deleted == null
			
	}
	
	
	void "test deleteProject no match"(){
		when:
			def project1 = new ProjectItem(
				name: "Name1",
				code: "Code1",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:1,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project2 = new ProjectItem(
				name: "Name2",
				code: "Code2",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:2,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project3 = new ProjectItem(
				name: "Name3",
				code: "Code3",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:3,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
			
			def project4 = new ProjectItem(
				name: "Name4",
				code: "Code4",
				deliveryDate: new Date(),
				projectManager:person1,
				techLead:person2,
				priority:4,
				phase:Phase.valueOf("Briefing")
			).save(flush:true)
		
		
		//let's delete project 3
		
		def result = projectService.deleteProject(12345)
		
		
		then:
			result == null
		
			def project1Priority = ProjectItem.get(project1.id).priority
			def project2Priority = ProjectItem.get(project2.id).priority
			def project3Priority = ProjectItem.get(project3.id).priority
			def project4Priority = ProjectItem.get(project4.id).priority
		
			//other projects should have been shuffled
			project1Priority == 1
			project2Priority == 2
			project3Priority == 3
			project4Priority == 4	
	}
	

	
	
}
