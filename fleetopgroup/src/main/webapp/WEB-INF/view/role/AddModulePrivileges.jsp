<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="content-wrapper">
<div id="contentbox">
<div class="row">
<div class="panel-group">
		<section class="content-header">
		<br>
			<div class="pull-left">
				<a href="<c:url value="/open"/>"><spring:message
						code="label.master.home" /></a> / <a
					href="<c:url value="/masterCompany/1.in"/>">Company</a>
			</div>
			<div class="pull-right" style="padding-right: 20px;">
				<sec:authorize access="hasAuthority('MASTER_COM_EDIT_PRIVILEGE')">
					<a style="font-size: 16px;" id="cancel"
						href="#">
						Cancel
					</a>
				</sec:authorize>
			</div>
		</section>	<br><br>		
		<div class="panel panel-info" id="top-border-boxshadow">
			<div class="panel-heading text-center" ><h4><b>Company Module Permission</b></h4></div>
				<input type="hidden" id="companyId" value="${companyId}">
			   <div class="panel-body" id="content"  style="padding-left: 300px;">
			   		<div style="padding-left: 350px;" class="col-md-off-5">
				   		<a  class="btn btn-success btn-sm"
								href="#" onclick="saveCompanyModulePrivilges();"> <span 
								class="fa fa-save"></span> Save
						</a>
					</div><br>
			   		<table style="width: 80%;border-bottom: 1px;">
			   			<thead>
			   				<tr style="border-bottom: 1px;">
			   						<th>Module Name</th>
			   						<th>Status</th>
			   				</tr>
			   			</thead>
			   			<tbody id="tableBody">
			   				
			   			</tbody>
			   		</table><br>
			   		<div style="padding-left: 350px;" class="col-md-off-5">
				   		<a  class="btn btn-success btn-sm"
								href="#" onclick="saveCompanyModulePrivilges();"> <span 
								class="fa fa-save"></span> Save
						</a>
					</div>
			   </div>
		</div>
	</div>
</div>
</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/commonUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/moduleAddToCompany.js"></script>