
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.UsuariosController"%>
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>


<h1 class="text-center">USUARIOS LISTADO <a href="backoffice/usuarios?op=<%=UsuariosController.OP_MOSTRAR_FORMULARIO%>"><i class="fa fa-plus-circle text-success" aria-hidden="true"></i></a></h1>

<hr>
<div class="text-center">
<strong>Buscador</strong>
<form action="backoffice/materiales" method="get">
<input type="hidden" name="op" value="<%=UsuariosController.OP_BUSQUEDA%>">
<input type="search" name="search" value="${search}"required placeholder="Nombre del usuario">
<input type="submit" value="Buscar">
</form>
</div>

<table id="tabla" class="display text-center">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Rol</th>            
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usuarios}" var="usuario">
        <tr>
            <td>${usuario.id}</td>
            <td>
            <a href="backoffice/usuarios?id=${usuario.id}&op=<%=UsuariosController.OP_MOSTRAR_FORMULARIO%>">
            ${usuario.nombre}
            </a>
            </td>            
            <td>${usuario.rol.nombre}</td>
        </tr>
    </c:forEach>
    </tbody>
    
</table>


<%@include file="/templates/footer.jsp" %>