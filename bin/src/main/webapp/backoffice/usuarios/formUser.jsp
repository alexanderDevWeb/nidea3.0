<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1  class="text-center">FORM CRUD</h1>

<%-- <h3  class="text-center">Opción: ${op}</h3> --%>

<%-- <p>${roles}</p> --%>
<%-- <p>${usuario}</p> --%>

<div id="formCRUD" class="mb-3">

  <form class="form-signin" action="backoffice/usuarios" method="post">     

      <div class="form-label-group">
      <label for="usuario">Id</label>
        <input type="text"
        	   class="form-control"
               name="id" 
               value="${usuario.id}"               
               readonly>
      </div>
      
      <br>

      <div class="form-label-group">
      <label for="password">Nombre</label>
        <input type="text" 
               name="nombre" 
               class="form-control" 
               placeholder="Nombre"
               value="${usuario.nombre}">
      </div>
      
      <br>      
      
      
    <label for="password">Rol</label>
	<select name="idRol"  class="form-control">	
	<c:forEach items="${roles}" var="rol">      
		<option value="${rol.id}" ${rol.id == usuario.rol.id? "selected" :"" }>${rol.nombre}</option>	
	</c:forEach>
	</select>
	      
      <br>
     
     <c:choose>        
         <c:when test="${usuario.id == -1}">
         	<button class="btn btn-lg btn-primary btn-block" name="op" value="4" type="submit">Crear</button>
         </c:when>
         <c:otherwise>
         	<button class="btn btn-lg btn-warning btn-block" name="op" value="4" type="submit">Modificar</button>
         	<button id="del" class="btn btn-lg btn-danger btn-block" name="op" value="3" type="submit" data-toggle="modal" data-target="#exampleModal">Eliminar</button>
         </c:otherwise>      
     </c:choose>     
        
    </form>
    
    <!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Eliminar</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
         ¿ Estas seguro que deseas Eliminar el Usuario?
      </div>
      <div class="modal-footer">
        <button id="modalNo" type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
        <button id="modalOk" type="button" class="btn btn-primary">Aceptar</button>
      </div>
    </div>
  </div>
</div>
</div>
</div>

<%@include file="/templates/footer.jsp" %>
<script>
var enviar = false;

$("#del").click(function(){
	if (!enviar){
		event.preventDefault();	
	}				
})

$("#modalOk").click(function(){
	enviar = true;
	$("#del").click();
})

$("#modalNo").click(function(){
	event.preventDefault();		
})
</script>