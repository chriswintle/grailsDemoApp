<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>List Projects</title>
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
	</head>
	<body>

<div class="jumbotron">
    <h1>List Projects</h1>
    <p>Available projects will be listed below - to add a new Project <button type="button" class="btn btn-lg btn-primary" onclick="location.href='${g.createLink(controller:'project', action:'createProject')}';">Click here</button></p>
 </div>
 
  <g:if test="${flash.message}">
	  <div class="alert alert-success" role="alert">
	  	${flash.message}
	  </div>
  </g:if>
  <g:if test="${flash.error}">
	  <div class="alert alert-danger" role="alert">
	  <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
	  <span class="sr-only">Error:</span>
	 	${flash.error}
	  </div>
   </g:if>
 
 
<div class="projectsList"> 
  <g:render template="projectList" model="[projects:projects]" />
</div>
</body>
