package org.jim

import org.jim.Person

/**
 * PersonService
 * A service class encapsulates the core business logic of a Grails application
 */
//@Transactional
class PersonService {

    def listPeople() {
		def people = Person.findAll();
		return people;
    }
	
	
	def getPerson(id){
		def person = Person.get(id)
		return person;
	}
	
	
	
	
	def createPerson(params){
		def person = new Person(
			firstName:params.firstName,
			lastName:params.lastName
		)
		
		return person.save(flush:true)	
	}
	
}
