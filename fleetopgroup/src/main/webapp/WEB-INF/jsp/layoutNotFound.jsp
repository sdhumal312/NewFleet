<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<%
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
	httpResponse.setDateHeader("Expires", 0); // Proxies.
	//chain.doFilter(request, response);
%>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="icon" href="<c:url value="/resources/favicon.ico" />" type="image/x-icon">
<link rel="shortcut icon" href="<c:url value="/resources/favicon.ico" />" type="image/x-icon">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap.css" />">
<link rel='stylesheet'
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" />
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/Fleetop.min.css" />">
<title><tiles:insertAttribute name="title" /></title>
<style>
div#load_screen {background-color: rgba(0, 0, 0, .25);opcity: 1;position: fixed;z-index: 10;top: opx;width: 100%;height: 1400px}div#load_screen>div#loading {color: #fff;width: 120px;height: 24px;margin: 200px auto}
</style>
<script type="text/javascript">
	window.addEventListener("load", function() {
		var e = document.getElementById("load_screen");
		document.body.removeChild(e)
	});
</script>
</head>
<body>
	<div id="load_screen">
		<div id="loading">
			<img src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/loading.gif"/>" class="icon" />
		</div>
	</div>
	<tiles:insertAttribute name="body" />
</body>
</html>