<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Edit Project </title>
		
	</head>
	<body> <div class="jumbotron">
     <h1>Edit Project: ${project.code}</h1>
   </div>
   
   
  <div class="createProject">
   	<g:form name="editProject" action="doEditProject" id="${project.id}">
	 <div class="form-group">
	   <label for="name">Project name:</label>
	   <g:textField name="name" class="form-control" value="${project.name}" required="true"/>
	 </div>
	 <div class="form-group">
	 	<label for="code">Project code:</label>
	   <g:textField name="code" class="form-control" value="${project.code}" required="true"/>
	 </div>
	 
	 <div class="form-group">
	 	<label for="phase">Phase</label>
	   <g:select name="phase" from="${phases}" value="${project.phase}"/>
	 </div>
	 
	  <div class="form-group">
	 	<label for="phase">Priority:</label>
	   <g:select name="priority" from="${1..maxPriority}" value="${project.priority}"/>
	 </div>
	 
	 <div class="form-group">
	 	<label for="projectManager">Project Manager</label>
	   <g:select name="projectManagerId" from="${people}" optionKey="id" optionValue="${{it.firstName +' '+it.lastName}}" value="${project.projectManager.id}"/>
	 </div>
	 
	 <div class="form-group">
	 	<label for="techLead">Tech Lead</label>
	   <g:select name="techLeadId" from="${people}" optionKey="id" optionValue="${{it.firstName +' '+it.lastName}}" value="${project.techLead.id}"/>
	 </div>
	 
	 <div class="form-group">
	   <label for="techLead">Delivery Date</label>
	   <g:datePicker name="deliveryDate" value="${project.deliveryDate}" precision="day" />
	 </div>
	 
	 <button type="submit" class="btn btn-default">Submit</button>
	 <button type="submit" class="btn btn-danger" onclick="location.href='${g.createLink(controller:'project', action:'listProjects')}'; return false;">Cancel</button>
	</g:form>
     
</div>
</body>