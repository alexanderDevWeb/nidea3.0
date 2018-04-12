<%@include file="templates/head.jsp" %>
<%@include file="templates/navbar.jsp" %>
<%@include file="templates/alert.jsp" %>

<div id="loginUser">

  <form class="form-signin" action="login-user" method="post">     

      <div class="form-label-group">
        <input type="number" class="form-control"
               name="id" 
               placeholder="Id Usuario" 
               required autofocus>        
      </div>

      <div class="form-label-group">
        <input type="text" 
               name="nombre" 
               class="form-control" 
               placeholder="Nombre" required>       
      </div>
     
      <button class="btn btn-lg btn-primary btn-block" type="submit">Entrar</button>
      
    </form>

</div>
<%@include file="templates/footer.jsp" %>