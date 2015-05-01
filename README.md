## Simple Grails Project Management App


#What it does
This app lets you create, view, edit and delete Projects. each projects has

1. A Tech Lead
2. A Project Manager
3. A Name
4. A Code
5. A Delivery Date
6. A priority, ranging from 1 (highest) through to the number of projects you have (new projects will be pushed to the bottom of the priority list)


###Reprioritising Projects

When you edit a project, you can change its priority. When you do so, this will reshuffle the priorities of your other projects. 

###Deleting Projects
When you delete a project, your other projects' priorities will be reshuffled accordingly. 


#What it doesn't do
At the moment, new 'people' (so people who can act as Tech lead or Project Manager) are populated in the app's bootstrap (if none are found). There is no interface for adding new people (yet) - but there are (tested) Service methods in place for creating users.


#Things to note
At present, the app is using GORM/Hibernate to generate your DB tables. Dev and Test environments are using the in-memory database - you'll want to set this up yourself for prod (because you'll definitely be shipping this app to prod).