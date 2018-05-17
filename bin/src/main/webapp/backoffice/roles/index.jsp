
<%@page import="com.ipartek.nidea.ejemplos.Operable"%>
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.RolesController"%>

<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>


<h1 class="text-center">Roles LISTADO <a href="backoffice/roles?op=<%=Operable.OP_MOSTRAR_FORMULARIO%>"><i class="fa fa-plus-circle text-success" aria-hidden="true"></i></a></h1>

<hr>
<div class="text-center">
<strong>Buscador</strong>
<form action="backoffice/roles" method="get">
<input type="hidden" name="op" value="<%=Operable.OP_BUSQUEDA%>">
<input type="search" name="search" value="${search}"required placeholder="Nombre del rol">
<input type="submit" value="Buscar">
</form>
</div>

<table id="tabla" class="display text-center">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${roles}" var="rol">
        <tr>
            <td>${rol.id}</td>
            <td>
            <a href="backoffice/roles?id=${rol.id}&op=<%=Operable.OP_MOSTRAR_FORMULARIO%>">
            ${rol.nombre}
            </a>
            </td>            
        </tr>
    </c:forEach>
    </tbody>
    
</table>


<%@include file="/templates/footer.jsp" %>