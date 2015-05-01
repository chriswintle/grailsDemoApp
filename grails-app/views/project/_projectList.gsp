<table class="table">
      <thead>
        <tr>
          <th>Priority</th>
          <th>Code</th>
          <th>Name</th>
          <th>Phase</th>
          <th>Tech Lead</th>
          <th>Project Manager</th>
          <th>Delivery Date</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
       <g:each in="${projects}" var="project">
         <tr>
           <td>${project.priority}</td>
           <td>${project.code}</td>
           <td>${project.name}</td>
           <td>${project.phase}</td>
           <td>${project.techLead.firstName} ${project.techLead.lastName}</td>
           <td>${project.projectManager.firstName} ${project.projectManager.lastName}</td>
           <td><g:formatDate format="yyyy-MM-dd" date="${project.deliveryDate}"/></td>
           <td>
	           <button type="button" class="btn btn-info" onClick="location.href='${g.createLink(controller:'project', action:'editProject', params:[id:project.id])}'; return false;">Edit</button>
	           <button type="button" class="btn btn-danger" onClick="location.href='${g.createLink(controller:'project', action:'deleteProject', params:[id:project.id])}'; return false;">Delete</button> 
           </td>
         </tr>
       </g:each>
      </tbody>
</table>