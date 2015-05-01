package org.jim



import org.jim.Person
import spock.lang.*

/**
 *
 */
class PersonServiceIntegrationSpec extends Specification {
	def personService
    def setup() {
    }

    def cleanup() {
    }

    void "test createPerson OK"() {
		when:
		def params = [firstName:"test", lastName:"person"]
		
		then:
			def result = personService.createPerson(params)
			
			result.firstName == "test"
			result.lastName == "person"
			
    }
	
	void "test createPerson blank first name"() {
		when:
			def params = [firstName:"", lastName:"person"]
		
		
		then:
			def result = personService.createPerson(params)
			
			result == null
			
	}
	
	void "test createPerson blank last name"() {
		when:
			def params = [firstName:"test", lastName:""]
		
		
		then:
			def result = personService.createPerson(params)
			
			result == null
			
	}
	
	void "test createPerson null first name"() {
		when:
			def params = [firstName:null, lastName:"person"]
		
		
		then:
			def result = personService.createPerson(params)
			
			result == null
			
	}
	
	void "test createPerson null last name"() {
		when:
			def params = [firstName:"test", lastName:null]
		
		
		then:
			def result = personService.createPerson(params)
			
			result == null
			
	}
	
	
	
	
	void "testGetPerson OK"(){
		when:
			def saved = new Person(firstName:"test", lastName:"test").save(flush:true)
		
		then:
			def result = personService.getPerson(saved.id)
			
			result.firstName == "test"
		
	}
	
	
	void "testGetPerson no entry"(){
		when:
			def saved = new Person(firstName:"test", lastName:"test").save(flush:true)
		
		then:
			def result = personService.getPerson(12345678)
			
			result == null
		
	}
	
	void "testGetPerson null id"(){
		when:
			def saved = new Person(firstName:"test", lastName:"test").save(flush:true)
		
		then:
			def result = personService.getPerson(null)
			
			result == null
		
	}
	
	
	void "tets listPeople OK"(){
		when:
			def saved1 = new Person(firstName:"test1", lastName:"test1").save(flush:true)
			def save2 = new Person(firstName:"test2", lastName:"test2").save(flush:true)
			def save3 = new Person(firstName:"test3", lastName:"test3").save(flush:true)
		
		
		then:
			def result = personService.listPeople();
			
			result.size() == 3
			result.get(0).firstName == "test1"
		
	}
	
	
	void "tets listPeople no result"(){
		when:
			def person = true;
		
		
		then:
			def result = personService.listPeople();
			
			result.size() == 0
			
			
	}
}
