<%@include file="/templates/head.jsp" %>
<%@include file="/templates/navbar.jsp" %>
<%@include file="/templates/alert.jsp" %>

<h1>BackOffice</h1>

<ol>
	<c:forEach items="${materiales}" var="material">
	
	<c:choose>
		<c:when test="${material.precio > 6.0  && material.precio <= 25.0}">
			<li style="color:blue">
		</c:when>
		<c:when test="${material.precio > 25.0}">
			<li style="color:red">
		</c:when>
		<c:otherwise>
			<li>
		</c:otherwise>
	</c:choose>	
		
		${material.nombre} - ${material.precio} &euro;</li>
		
		
	</c:forEach>
</ol>




<%@include file="/templates/footer.jsp" %>