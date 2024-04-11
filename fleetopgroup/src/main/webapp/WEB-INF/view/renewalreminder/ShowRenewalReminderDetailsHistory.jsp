<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
						<a href="<c:url value="/viewRenewalReminder.in"/>"> RenewalReminder</a> /
						 <small>Renewal Reminder History</small>
					</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/renewalReminderAjaxAdd.in?vid=${vid}&renewalSubTypeId=0"/>"> <span
							class="fa fa-plus"></span> Add Renewal
						</a>
					</sec:authorize>
					<a class="btn btn-warning btn-sm" href="<c:url value="/viewRenewalReminder.in"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content-body">
		<input type="hidden" name="vid" id="vid" value="${vid}" />
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<h4>
									<span class="label label-info">Renewal Reminder History</span>
								</h4>
								<div class="table-responsive">
									<table id="VendorPaymentTable" class="table table-hover table-bordered">
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</section>
	
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/ShowRenewalReminderDetailsHistory.js" />"></script>
	
</div>