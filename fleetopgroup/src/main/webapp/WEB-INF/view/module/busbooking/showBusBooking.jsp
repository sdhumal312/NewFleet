<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="viewBusBookingDetails.in">Bus Booking List</a>
					/ <span>View Bus Booking Details</span>
				</div>
				<div class="pull-right">
					
					<a href="viewBusBookingDetails.in">Cancel</a>
				</div>
				
			</div>
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="row" style="height: 50px;">
				</div>
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Bus Booking Number :  <span id="busBookingNumber"></span></h4>
						<input type="hidden" id="busBookingDetailsId" value="${busBookingDetailsId}"/>
						<h4 id="vendorinfo" align="center">
						</h4>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
				
					
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
							<fieldset>
								<div style="font-size: 16px; padding-left: 20px;"><b>Booking Party Details</b></div>
							</fieldset>
								<table class="table">
									<tbody>
									<tr class="row">
											<th class="key">Party Name :</th>
											<td class="value" id="corporateName"></td>
										</tr>
										<tr class="row" id="partyMobileNumberRow">
											<th class="key">Party Mobile:</th>
											<td class="value" id="partyMobileNumber"></td>
										</tr>
										<tr class="row" id="partyGSTNoRow">
											<th class="key">Party GST :</th>
											<td class="value" id="partyGSTNo"></td>
										</tr>
										<tr class="row" id="partyAddressRow">
											<th class="key">Party Address :</th>
											<td class="value" id="partyAddress"></td>
										</tr>
										<tr class="row" id="reportToNameRow">
											<th class="key">Report To :</th>
											<td class="value" id="reportToName"></td>
										</tr>
										<tr class="row" id="reportToMobileNumberRow">
											<th class="key">Report To Number :</th>
											<td class="value" id="reportToMobileNumber"></td>
										</tr>
										<tr class="row" id="billingAddressRow">
											<th class="key">Report To address :</th>
											<td class="value" id="billingAddress"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
							<fieldset>
								<div style="font-size: 16px; padding-left: 20px;"><b>Booking Details/Rate</b></div>
							</fieldset>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row" id="busBookingRow">
											<th class="key">Booking Date :</th>
											<td class="value" id="bookingDate"></td>
										</tr>
										<tr class="row">
											<th class="key">Booking Ref :</th>
											<td class="value" id="bookingRefNumber"></td>
										</tr>
										<tr class="row" id="vehicleTypeRow">
											<th class="key">Vehicle Type :</th>
											<td class="value" id="vehicleType"></td>
										</tr>
										<tr class="row" id="rateRow">
											<th class="key">Rate :</th>
											<td class="value" id="rate"></td>
										</tr>
										<tr class="row" id="hireAmountRow">
											<th class="key">Hire Amount :</th>
											<td class="value" id="hireAmount"></td>
										</tr>
										<tr class="row">
											<th class="key">Vehicle :</th>
											<td class="value" id="vehicleNumber"></td>
										</tr>
										<tr class="row">
											<th class="key">Remark :</th>
											<td class="value" id="remark"></td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Trip Start Date :</th>
											<td class="value" id="tripStartDateTime"></td>
										</tr>
										<tr class="row">
											<th class="key">TripEnd  Date :</th>
											<td class="value" id="tripEndDateTime"></td>
										</tr>
										<tr class="row" id="placeOfVisitRow">
											<th class="key">Place Of Visit :</th>
											<td class="value" id="placeOfVisit"></td>
										</tr>
										<tr class="row">
											<th class="key">PickUp Address :</th>
											<td class="value" id="pickUpAddress"></td>
										</tr>
										<tr class="row">
											<th class="key">Drop Address :</th>
											<td class="value" id="dropAddress"></td>
										</tr>
										<tr class="row">
											<th class="key">TripSheet Number :</th>
											<td class="value" id="tripSheetNumber"></td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | <small class="text-muted"><b>Created
					date: </b> <span id="createdOn"></span></small> | <small
				class="text-muted"><b>Last updated by :</b> <span id="lastupdatedBy"></span></small> | <small
				class="text-muted"><b>Last updated date:</b><span id="lastUpdated"></span></small>
		</div>
	</section>
</div>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/busbooking/showBusBookingDetails.js"></script>
