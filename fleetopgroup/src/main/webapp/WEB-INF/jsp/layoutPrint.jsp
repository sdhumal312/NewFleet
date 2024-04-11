<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><tiles:insertAttribute name="title" /></title>
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="stylesheet" type="text/css" href="/resources/QhyvOb0m3EjE7A4/css/printBootstrap.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />
<link rel="stylesheet"
	href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" />
<link rel="stylesheet" type="text/css" href="/resources/QhyvOb0m3EjE7A4/css/FleetopPrint.min.css">
</head>
<body onload="window.print();">
	<div>
		<tiles:insertAttribute name="body" />
	</div>
	<script type="text/javascript" src="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="/resources/QhyvOb0m3EjE7A4/js/bootstrap.js"></script>
	<script type="text/javascript" src="/resources/QhyvOb0m3EjE7A4/js/app.js"></script>
</body>
</html>
