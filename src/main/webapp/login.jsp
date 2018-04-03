<%@include file="templates/head.jsp" %>
<%@include file="templates/navbar.jsp" %>
<%@include file="templates/alert.jsp" %>

<div id="login">

  <form class="form-signin" action="login" method="post">     

      <div class="form-label-group">
        <input type="text" class="form-control"
               name="usuario" 
               placeholder="Nombre Usuario" 
               required autofocus>
               
        <label for="usuario">Nombre Usuario</label>
      </div>

      <div class="form-label-group">
        <input type="password" 
               name="password" 
               class="form-control" 
               placeholder="Contraseña" required>
               
        <label for="password">Contraseña</label>
      </div>
     
      <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
      
    </form>

</div>
<%@include file="templates/footer.jsp" %>