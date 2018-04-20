
<%@page import="com.ipartek.formacion.nidea.controller.MaterialesUserController"%>
<%@page import="com.ipartek.formacion.nidea.controller.backoffice.MaterialesController"%>
<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>


<h1 class="text-center">MATERIALES ${usuario.nombre.toUpperCase()} <a href="frontoffice/materiales?op=<%=MaterialesController.OP_MOSTRAR_FORMULARIO%>"><i class="fa fa-plus-circle text-success" aria-hidden="true"></i></a></h1>
<%-- ${usuario} --%>
<hr>
<div class="text-center">
<strong>Buscador</strong>
<form action="frontoffice/materiales" method="get">
<input type="hidden" name="op" value="<%=MaterialesController.OP_BUSQUEDA%>">
<input type="search" name="search" value="${search}"required placeholder="Nombre del material">
<input type="submit" value="Buscar">
</form>
</div>

<table id="tabla" class="display text-center">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>            
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${materiales}" var="material">
        <tr>
            <td>${material.id}</td>
            <td>
            <a href="frontoffice/materiales?id=${material.id}&op=<%=MaterialesUserController.OP_MOSTRAR_FORMULARIO%>">
            ${material.nombre}
            </a>
            </td>
            <td>${material.precio}</td>            
        </tr>
    </c:forEach>
    </tbody>
    
</table>


<%@include file="/templates/footer.jsp" %>