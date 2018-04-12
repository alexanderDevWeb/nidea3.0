<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

${applicationScope.usuarios_conectados}

<h1>BackOffice</h1>


<a href="backoffice/materiales">Materiales listado backoffice</a>

<table id="tabla" class="display text-center">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>            
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usuarios_conectados}" var="usuario">>
        <tr>
             <td>${usuario.key}</td>
            <td>${usuario.getValue()}</td>
        </tr>
    </c:forEach>
    </tbody>
    
</table>



<%@include file="/templates/footer.jsp" %>