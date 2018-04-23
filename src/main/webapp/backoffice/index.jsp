<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<%-- ${applicationScope.usuarios_conectados} --%>

<a class="text-center" style="display:block" href="backoffice/materiales">Materiales listado backoffice</a>

<h1 class="text-center">LISTADO DE CONEXIONES</h1>

<table id="tabla" class="display text-center">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>            
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usuarios_conectados}" var="usuario">
        <tr>
             <td>${usuario.key}</td>
<%--             <td>${usuario.getValue().getNombre()}</td> --%>
            <td>${usuario.getValue().getNombre()}</td>
        </tr>
    </c:forEach>
    </tbody>
    
</table>



<%@include file="/templates/footer.jsp" %>