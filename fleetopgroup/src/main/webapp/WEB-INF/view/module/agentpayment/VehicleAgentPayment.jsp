<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>
						Vehicle Agent Payment</span>
				</div>
				<div class="pull-right">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#searchServiceEntriesByDate" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-search"></span> Search Entries By Date
						</a>
					<button id="print" class="btn btn-default btn-sm" style="display: none;"
						onclick="printDiv('advanceTable')">
						<span class="fa fa-print"> Print</span>
					</button>
					<a class="btn btn-link" href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a href="showVehicle.in?vid=${vehicle.vid}"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vehicle.vehicle_Ownership}" />
								</a>
							</h3>
						</div>
						<div class="col-md-4">
							<h4>
									You Have to Pay : <i class="fa fa-inr"></i>
									<span id="vehicleOwnerTotal">${vehicleOwnerTotal}</span>
							</h4>
						</div>
					</div>
				
						<input type="hidden" id="vid" value="${vehicle.vid}">
						<div class="secondary-header-title">
							<ul class="breadcrumb">
							<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Vehicle"><a
										href="#"><c:out value=" ${vehicle.vehicle_registration}" /></a></span></li>
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
								<li>
									<button style="display: none;" class="btn btn-success" id="createApproval" onclick="creteVehicleAgentApproval();">Make Payment</button>
								</li>		
							</ul>
						</div>
						
						<div style="text-align: right;">
							
						</div>
						
				</sec:authorize>
			</div>
		</div>
	</section>

	<section class="content-body">
		<input type="hidden" id="companyId" value="${userDetails.company_id}">
		<input type="hidden" id="userId" value="${userDetails.id}">
		<input type="hidden" id="toDate">
		<div class="modal fade" id="searchServiceEntriesByDate" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Entries By Date</h4>
					</div>
						<div class="modal-body">
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="ReportDailydate" class="form-text" name="searchDate" required="required"
											style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success" onclick="getVehicleAgentPaymentDetails();">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
				<div class="modal fade" id="makePaymentModal" role="dialog">
						<div class="modal-dialog" style="width:750px;">

							<!-- Modal content-->
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Vehicle Agent Payment</h4>
									</div>
									<div class="modal-body">
									<input type="hidden" id="vehicleId">
									<input type="hidden" id="toDate">
									<div class="row">
											<label class="L-size control-label" id="Type">Total Amount 
												 :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="number" class="form-text" 
													id="totalAmount" required="required" name="totalAmount" readonly="readonly"
													maxlength="50" min="1" /> 
											</div>
										</div>
										<br />
										<div class="row1">
													<label class="L-size control-label">Payment Mode:<abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text select2"
															name="paymentMode" id="paymentMode" onclick="onPaymentModeSelect();"
															onchange="onPaymentModeSelect();" required="required">
															<option value="1">Clear</option>
															<option value="2">Negotiated</option>
															<option value="3">Partial</option>
														</select>
													</div>
												</div>
										<br />
										<div class="row">
													<label class="L-size control-label">Payment Type <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text select2"
															name="paymentType" id="paymentType"
															required="required">
															<option value="1">Cash</option>
															<option value="2">Credit</option>
															<option value="3">NEFT</option>
															<option value="4">RTGS</option>
															<option value="5">IMPS</option>
															<option value="6">DD</option>
															<option value="7">Cheque</option>
															<option value="8">Bank Draft</option>
														</select>
													</div>
												</div>
										<br />
										<div class="row">
											<label class="L-size control-label" id="Type">Paid Amount 
												 :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="number" class="form-text"  onkeyup="validatePaidAmount();"
													id="paidAmount" required="required" name="paidAmount" readonly="readonly"
													maxlength="50" min="1" /> 
											</div>
										</div>
										<br />
										<div class="row" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Payment
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="opendDate">
													<input type="text"  class="form-text" name="paymentDate"
														id="paymentDate" required="required" readonly="readonly"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<br/>
										<div class="row">
											<label class="L-size control-label" id="Type">Remark
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" 
													id="remark" required="required" name="remark"
													maxlength="50" /> 
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save" onclick="makeVehicleAgentPayment();">Save</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
							</div>

						</div>
					</div>
		
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="col-md-10 col-sm-12 col-xs-12">
					<c:if test="${param.deleteSuccess eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Vehicle Battery Layout Deleted Successfully.
						</div>
					</c:if>
								<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info">
											
											<table id="advanceTable" style="width: 95%; display: none;"
												class="table table-hover table-bordered table-striped">
												<thead>
													<tr>
														<th colspan="2" style="font-size: 12px;" align="center">
														    Vehicle Agent Entries for  : <span id="dateRange"></span>
										</th>
													</tr>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="1"><b> Opening
																Balance :</b></th>
														<td><i class="fa fa-inr"></i><span id="openingBalance"></span></td>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>
															<table style="width: 100%"
																class="table table-hover table-striped">
																<thead>
																	<tr>
																		<th colspan="4">Credit</th>

																	</tr>
																</thead>
																<tbody id="creditTable">
																	
																</tbody>
																<tfoot>
																	<tr>
																		<th class="text-right" colspan="3"><b> Credit
																				Total : </b></th>
																		<td><i class="fa fa-inr"></i><span id="creditTotal"></span></td>
																	</tr>
																</tfoot>
															</table>
														</td>
														<td>
																<table style="width: 100%"
																	class="table table-hover table-striped">
																	<thead>
																		<tr>
																			<th colspan="4">Debit</th>

																		</tr>
																	</thead>
																	<tbody id="deditTable">

																	</tbody>
																	<tfoot>
																		<tr>
																			<th class="text-right" colspan="3"><b> Debit
																					Total : </b></th>

																			<td><i class="fa fa-inr"></i><span id="debitTotal"></span></td>
																		</tr>
																	</tfoot>
																</table>
																
																</td>
													</tr>
													<tr style="font-size: 12px;" align="center">
														<th class="text-right" colspan="1"><b> Balance :</b></th>
														<td style="font-size: 12px;" align="center"><i
															class="fa fa-inr"></i> <span id="balance"></span></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/VehicleAgentPayment.js" />"></script>
</div>