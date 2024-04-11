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
						href="viewVendorLorryHireDetails.in">Lorry Hire List</a>
					/ <span>View Lorry hire Details</span>
				</div>
				<div class="pull-right">
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						short chargeNotPaids = (short)request.getAttribute("chargeNotPaid");
						if((permission.contains(new SimpleGrantedAuthority("DELETE_LORRY_HIRE"))) && (chargeNotPaids == 1) ) {
						%>
					  		<a id="delete" onclick="deleteLorryHireInvoice();" class="btn btn-primary btn-sm" href="#">Delete Lorry Hire Details</a>
					  		
					<% } %>
					<a href="viewVendorLorryHireDetails.in">Cancel</a>
				</div>
				
			</div>
			<sec:authorize access="!hasAuthority('VIEW_LORRY_HIRE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_LORRY_HIRE')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Lorry Hire Number <span id="lorryHireDetailsNumber"></span></h4>
						<input type="hidden" id="lorryHireDetailsId" value="${lorryHireDetailsId}"/>
						
						
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
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Vehicle :</th>
											<td class="value" id="vehicle"></td>
										</tr>
										<tr class="row">
											<th class="key">Lorry Hire :</th>
											<td class="value" id="lorryHire"></td>
										</tr>
										<tr class="row">
											<th class="key">Income Name :</th>
											<td class="value" id="incomeName"></td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Vendor :</th>
											<td class="value" id="vendor"></td>
										</tr>
										<tr class="row">
											<th class="key">Advance Amount:</th>
											<td class="value" id="advanceAmount"></td>
										</tr>
										<tr class="row">
											<th class="key">TripSheet :</th>
											<td class="value" id="tripSheetNumber"></td>
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
											<th class="key">Hire Date :</th>
											<td class="value" id="hireDate"></td>
										</tr>
										
										<tr class="row">
											<th class="key">Paid Amount :</th>
											<td class="value" id="paidAmount"></td>
										</tr>
										<tr class="row">
											<th class="key">Driver  :</th>
											<td class="value" id="driverName"></td>
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
											<th class="key">Balance Amount :</th>
											<td class="value" id="balanceAmount"></td>
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
											<th class="key">Remark :</th>
											<td class="value" id="remark"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				<fieldset>

					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table" id="dataTable" style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th class="fit ar">Charge Name</th>
											<th class="fit ar">Amount</th>
											<th class="fit">Action</th>
										</tr>
									</thead>
									<tbody id="batteryAmountBody">
										
									</tbody>

								</table>
								
						<% 
						Collection<GrantedAuthority>  permissions = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						short chargeNotPaid = (short)request.getAttribute("chargeNotPaid");
						if( ( permissions.contains(new SimpleGrantedAuthority("ADD_MORE_LORRY_CHARGES")) ) && (chargeNotPaid == 1 )) {
						%>
								<a id ="AddMoreParts" 
									onclick ="javascript:AddCharge();"
									href="javascript:AddCharge();">
									Add More Charges
								</a>
						<% } %>		
								
						
								
							</div>
						</div>
						<br/><br/><br/><br/>
						<div class="col-md-9">
							<div class="row">
								<div class="col-md-11">
									<table class="table">
										<tbody>
											<tr class="row">
												<th style="width: 70%" class="key">SubTotal :</th>
												<td class="value"><i class="fa fa-inr"></i>
													<span id="subTotal"></span></td>
											</tr>
											<tr class="row">
												<th style="width: 70%" class="key"><a>Balance :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														<span id="balance"></span></a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				
				
				
				<!--popup start-->
				<%-- <input type="hidden" id="lorryHireDetailsId" value="${lorryHireDetailsId}"/> --%>
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog modal-lg">

						<input type="hidden" id="vendorId" value="${vendorId}"/>
						<div class="modal-content">
						
							<div class="modal-body">
								<fieldset>
									<legend>Expense Details</legend>
									<div class="row1" class="form-group" id="grprouteExpense">

										<div class="col-md-4">
											<select class="form-text select2" style="width: 100%;"
												name="expenseName" id="Expense">

											</select> <span id="routeExpenseIcon" class=""></span>
											<div id="routeExpenseErrorMsg" class="text-danger"></div>
										</div>
										<div class="col-md-3" id="grprouteAmount">
											<input type="number" class="form-text" name="Amount"
												id="routeAmount" placeholder="Amount" min="0"> <span
												id="routeAmountIcon" class=""></span>
											<div id="routeAmountErrorMsg" class="text-danger"></div>
										</div>

										<div class="input_fields_wrap">
											<button class="add_field_button btn btn-success">
												<i class="fa fa-plus"></i>
											</button>

										</div>

									</div>

								</fieldset>
							</div>
							
							<div class="modal-footer">
								<input type="submit" class="btn btn-success"  value="Save" onclick="saveExpenseDetails();"/>
								<button type="button" class="btn btn-success"
									data-dismiss="modal">Close</button>
							</div>
						</div>

					</div>
				</div>
				<!--popup stop-->
				<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
				</div>
				
				<table class="table table-bordered" id="vendorPaymentTable" style="width: 100%">
				
				</table>
				<!-- <button id="vendorPaymentIdentity">Developer</button> -->
				<!-- <input type="submit" class="btn btn-success"  value="Developer" onclick="vendorPaymentInformation()"> -->
				
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
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/lorryhire/showVendorLorryHire.js"></script>
