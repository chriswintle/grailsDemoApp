package org.jim

import org.jim.PersonService;
import org.jim.ProjectController;
import org.jim.ProjectService;

import org.jim.ProjectItem;

import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(ProjectController)
class ProjectControllerSpec extends Specification {
	def projectServiceMock = Mock(ProjectService)
	def personPerviceMock = Mock(PersonService)

    def setup() {
		controller.personService = personPerviceMock
		controller.projectService = projectServiceMock
    }

    def cleanup() {
    }

    void "test create project OK"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.createProject(_) >> { return new ProjectItem(name:"thing", code:"thing") } 
		
		when:
			
			controller.doCreateProject();
		
		then:
			flash.message == "Project Created"
			response.redirectUrl == "/project/listProjects"
    }
	
	void "test create project service fail"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.createProject(_) >> { return null }
		
		when:
			
			controller.doCreateProject();
		
		then:
			flash.error == "Could not create project"
			response.redirectUrl == "/project/listProjects"
	}
	
	
	void "test edit project OK"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.getProject(_) >> { return new ProjectItem(name:"thing", code:"thing") }
			1 * projectServiceMock.updateProject(_) >> { return new ProjectItem(name:"thingupdated", code:"thingupdated") }
		
		when:
			
			controller.doEditProject();
		
		then:
			flash.message == "Project thingupdated updated succesfully"
			response.redirectUrl == "/project/listProjects"
	}
	
	
	void "test edit project not found"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.getProject(_) >> { return null }
			0 * projectServiceMock.updateProject(_) >> { return new ProjectItem(name:"thingupdated", code:"thingupdated") }
		
		when:
			
			controller.doEditProject();
		
		then:
			flash.error == "An error was encountered when editing your project. Please try again"
			response.redirectUrl.contains("/project/editProject")
	}
	
	void "test edit project error saving"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.getProject(_) >> { return new ProjectItem(name:"thing", code:"thing") }
			1 * projectServiceMock.updateProject(_) >> { return null }
		
		when:
			
			controller.doEditProject();
		
		then:
			flash.error == "An error was encountered when editing your project. Please try again"
			response.redirectUrl.contains("/project/editProject")
	}
	
	
	void "test delete project OK"() {
		
		given:
			def params = [name:"thing", code:"thing"]
			controller.params.name = "thing"
			
			1 * projectServiceMock.deleteProject(_) >> { return null}

		
		when:
			
			controller.deleteProject();
		
		then:
			flash.message == "Project Deleted"
			response.redirectUrl == "/project/listProjects"
	}
}
