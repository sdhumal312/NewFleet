<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('DOWNLOAD_WORKORDER')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
</div>
<div id="tableContain" style="padding-left: 10px;padding-right: 10px;"></div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/WO/WorkOrdersPrint.js" />"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		getCompanyWiseWorkOrderDetails(<%=request.getParameter("workorders_id")%>);
		
		
	});
</script>