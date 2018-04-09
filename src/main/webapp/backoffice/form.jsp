<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1  class="text-center">FORM CRUD</h1>

<h3  class="text-center">Opción: ${op}</h3>

<p>${material}</p>

<div id="formCRUD" class="mb-3">

  <form class="form-signin" action="backoffice/materiales" method="post">     

      <div class="form-label-group">
      <label for="usuario">Id</label>
        <input type="text"
        	   class="form-control"
               name="id" 
               value="${material.id}"               
               readonly>
      </div>

      <div class="form-label-group">
      <label for="password">Nombre</label>
        <input type="text" 
               name="nombre" 
               class="form-control" 
               placeholder="Nombre"
               value="${material.nombre}">
      </div>
      
      <div class="form-label-group mb-3">
      <label for="password">Precio</label>
        <input type="number"
			   step="0.1" 
               name="precio" 
               class="form-control" 
               placeholder="Precio"
               value="${material.precio}">
      </div>
      
     
     <c:choose>        
         <c:when test="${material.id == -1}">
         	<button class="btn btn-lg btn-primary btn-block" name="op" value="4" type="submit">Crear</button>
         </c:when>
         <c:otherwise>
         	<button class="btn btn-lg btn-warning btn-block" name="op" value="4" type="submit">Modificar</button>
         	<button id="del" class="btn btn-lg btn-danger btn-block" name="op" value="3" type="submit">Eliminar</button>
         </c:otherwise>      
     </c:choose>      
    </form>
</div>

<%@include file="/templates/footer.jsp" %>
<script>
	$("#del").click(function(){
		if(!confirm("Está seguro????????")) event.preventDefault();
	})
</script>