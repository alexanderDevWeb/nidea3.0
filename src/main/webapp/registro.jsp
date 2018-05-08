<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="templates/head.jsp" %>
<%@include file="templates/navbar.jsp" %>
<%@include file="templates/alert.jsp" %>

<div id="login">

	<h1 class="text-center">REGISTRO</h1>

  <form id="formRegistro" class="form-signin" action="registro" method="post">     

      <div class="form-label-group mb-2">
<!--       	<label for="usuario">Nombre Usuario</label> -->
        <input type="text" class="form-control"
        	   id="usuario"
               name="usuario"
               value="${user.nombre}"
               placeholder="Introduzca nombre de Usuario"
               autofocus 
               required> 
               <p id="msgNombre" class="text-danger"></p>               
        
      </div>
      
      <div class="form-label-group mb-2">
<!--       	<label for="usuario">Nombre Usuario</label> -->
        <input type="text" class="form-control"
        	   id="email"
               name="email"
               value="${user.email}"
               placeholder="Introduzca email de Usuario"
               autofocus 
               required>
               <p id="msgEmail" class="text-danger"></p>     
      </div>

      <div class="form-label-group mb-2">
        <input type="password" 
       		   id="password"	
               name="password"
               value="${user.password}" 
               class="form-control" 
               placeholder="Introduzca Contraseña" required>              
       
      </div>
      
      <div class="form-label-group mb-2">
        <input type="password" 
               id="cPassword"
               name="cPassword"
               value="" 
               class="form-control" 
               placeholder="Repita Contraseña" required>  
               <p id="msgPass" class="text-danger"></p>           
       
      </div>
     
      <button id="btnRegistro" class="btn btn-lg btn-primary btn-block" type="submit" disabled>Entrar</button>
      
    </form>

</div>
<%@include file="templates/footer.jsp" %>
<script src="js/registro.js"></script>