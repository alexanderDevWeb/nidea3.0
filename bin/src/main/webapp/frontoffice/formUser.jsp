<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1  class="text-center">FORM USER CRUD</h1>

<%-- <h3  class="text-center">Opción: ${op}</h3> --%>

<%-- <p>${usuarios}</p> --%>
<%-- <p>${material}</p> --%>

<div id="formCRUD" class="mb-3">

  <form class="form-signin" action="frontoffice/materiales" method="post">     

      <div class="form-label-group">
      <label for="usuario">Id</label>
        <input type="text"
        	   class="form-control"
               name="id" 
               value="${material.id}"               
               readonly>
      </div>
      
      <br>

      <div class="form-label-group">
      <label for="password">Nombre</label>
        <input type="text" 
               name="nombre" 
               class="form-control" 
               placeholder="Nombre"
               value="${material.nombre}">
      </div>
      
      <br>
      
      <div class="form-label-group mb-3">
      <label for="password">Precio</label>
        <input type="number"
			   step="0.1" 
               name="precio" 
               class="form-control" 
               placeholder="Precio"
               value="${material.precio}">
      </div>
      
    	      
      <br>
     
     <c:choose>        
         <c:when test="${material.id == -1}">
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
         ¿ Estas seguro que deseas Eliminar el Material?
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