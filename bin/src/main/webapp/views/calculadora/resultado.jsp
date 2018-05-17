<jsp:include page="/templates/head.jsp"></jsp:include>
<jsp:include page="/templates/navbar.jsp"></jsp:include>




<%
	float resul = (Float)request.getAttribute("resultado");	
%>		

<h1>Resultado de  la Operación <span class="badge badge-primary"><%=resul%></span> </h1>




<jsp:include page="/templates/footer.jsp"></jsp:include>