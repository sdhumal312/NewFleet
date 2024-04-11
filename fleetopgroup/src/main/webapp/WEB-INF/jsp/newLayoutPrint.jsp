<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/printBootstrap.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/font-awesome.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/FleetopPrint.min.css" />">

<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min.js" />"></script>
<title><tiles:insertAttribute name="title" /></title>
</head>
<body>
	<div>
		<tiles:insertAttribute name="body" />
	</div>
</body>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap.js" />"></script>
</html>
