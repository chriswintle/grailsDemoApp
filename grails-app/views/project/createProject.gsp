<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Create Project </title>
		
	</head>
	<body>
 <div class="jumbotron">
     <h1>Create a new Project</h1>
     <p>You can create a new project here. It will automatically be put to the bottom of the project priority list.</p>
   </div>
   
   
  <div class="createProject">
   	<g:form name="createProject" action="doCreateProject">
	 <div class="form-group">
	   <label for="name">Project name:</label>
	   <g:textField name="name" class="form-control" required="true"/>
	 </div>
	 <div class="form-group">
	 	<label for="code">Project code:</label>
	   <g:textField name="code" class="form-control" required="true"/>
	 </div>
	 
	 <div class="form-group">
	   <label for="phase">Phase</label>
	   <g:select name="phase" from="${phases}"/>
	 </div>
	 
	 <div class="form-group">
	   <label for="projectManager">Project Manager</label>
	   <g:select name="projectManagerId" from="${people}" optionKey="id" optionValue="${{it.firstName +' '+it.lastName}}"/>
	 </div>
	 
	 <div class="form-group">
	   <label for="techLead">Tech Lead</label>
	   <g:select name="techLeadId" from="${people}" optionKey="id" optionValue="${{it.firstName +' '+it.lastName}}"/>
	 </div>
	 
	 <div class="form-group">
	   <label for="techLead">Delivery Date</label>
	   <g:datePicker name="deliveryDate" value="${new Date()}" precision="day"/>
	 </div>
	 
	 <button type="submit" class="btn btn-default">Submit</button>
	 <button type="submit" class="btn btn-danger" onclick="location.href='${g.createLink(controller:'project', action:'listProjects')}'; return false;">Cancel</button>
	</g:form>
     
</div>
</body>
