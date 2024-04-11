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
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="icon" href="<c:url value="/resources/QhyvOb0m3EjE7A4/favicon.ico" />" type="image/x-icon">
<link rel="shortcut icon" href="<c:url value="/resources/QhyvOb0m3EjE7A4/favicon.ico" />" type="image/x-icon">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap.css" />" type="text/css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrapswitch/bootstrap-switch.min.css" />" type="text/css">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/font-awesome.min.css" />" type="text/css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/Fleetop.min.css" />" type="text/css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/skin-blue.css" />" type="text/css">
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min.js" />"></script>
<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/exportCSV/tableToExcel.js"></script>			
<title><tiles:insertAttribute name="title" /></title>
</head>
<body
	class="hold-transition skin-yellow sidebar-collapse sidebar-mini fixed">
	<div id="load_screen">
		<div id="loading">
			<img src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/loading.gif" />"
				class="icon" />
		</div>
	</div>
	<div class="wrapper">
		<div id="header">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="menu">
			<tiles:insertAttribute name="menu" />
		</div>
		
		<div id="body">
			<tiles:insertAttribute name="body" />
		</div>
		<div id="footer">
			<tiles:insertAttribute name="footer" />
		</div>
		
		<a id="back-to-top" href="#"
			class="btn btn-primary btn-lg back-to-top" role="button"
			title="Click to top page" data-toggle="tooltip" data-placement="left"><span
			class="fa fa-chevron-up"></span></a>
		
		<a id="back-to-top" href="#"
			class="btn btn-primary btn-lg back-to-top" role="button"
			title="Click to top page" data-toggle="tooltip" data-placement="left"><span
			class="fa fa-chevron-up"></span></a>
		<div class="control-sidebar-bg"></div>
	</div>
</body>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/fleetop.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrap.js" />"></script>
<!-- Slimscroll -->
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fastclick/jquery.slimscroll.min.js" />"></script>
<!-- FastClick -->
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fastclick/fastclick.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/jquery/jquery.lingua.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/exportCSV/ExportCSV.js" />"></script>
<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/sweetalert2.js" />"></script>
<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/alert.js" />"></script>	
<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/bootstrapswitch/bootstrap-switch.min.js" />"></script>			
			
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-172748265-1"></script>
<script>
   window.dataLayer = window.dataLayer || [];
   function gtag(){
	  dataLayer.push(arguments);
  }
  gtag('js', new Date());
  gtag('config', 'UA-172748265-1', {
	  'user_id': $('#customCompanyId').val()+'.'+$('#customUserId').val()+'.11118988998'
  }); 
  gtag('config', 'UA-172748265-1'); 
	
</script>										
</html>