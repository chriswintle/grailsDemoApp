package org.jim

class Person {
	String firstName;
	String lastName;

    static constraints = {
		firstName nullable:false
		lastName nullable:false
    }
	
	static mapping = {
		table "person"	
	}
}
