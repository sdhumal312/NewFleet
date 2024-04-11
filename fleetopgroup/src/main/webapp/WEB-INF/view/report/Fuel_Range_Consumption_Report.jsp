<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a> / <span>Daily
						Fuel Consumption Report</span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block;">
						<form action="DailyFuelConsumption" method="post">
							 <input type="hidden"
								name="fuel_to" placeholder="dd-mm-yyyy" required="required"
								value="${YESTERDAY}" />
								<input type="hidden"
								name="vehicleGroupId" required="required"
								value="${VehicleGroupId}" /> <input type="hidden"
								name="vehicle_OwnershipId" required="required"
								value="${Vehicle_OwnershipId}" /> <input type="hidden"
								name="vendor_id" required="required" value="${Vendor_id}" />
							<input type="hidden" name="fuel_vendor_paymodeId"
								required="required" value="${Fuel_vendor_paymodeId}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${FUEL_DATE_CURRENT}</div>
					<div style="display: inline-block;">
						<form action="DailyFuelConsumption" method="post">
							<input type="hidden" name="fuel_to" placeholder="dd-mm-yyyy"
								required="required" value="${TOMORROW}" /> 
							<input type="hidden"
								name="vehicleGroupId" required="required"
								value="${VehicleGroupId}" /> <input type="hidden"
								name="vehicle_OwnershipId" required="required"
								value="${Vehicle_OwnershipId}" /> <input type="hidden"
								name="vendor_id" required="required" value="${Vendor_id}" />
							<input type="hidden" name="fuel_vendor_paymodeId"
								required="required" value="${Fuel_vendor_paymodeId}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-forward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block; width: 100px"></div>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Daily Fuel Consumption Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm "
						onclick="saveImageToPdf('advanceTable')" id="printPdf">
						<span class="fa fa-file-excel-o"> Export to PDF</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_FU_CO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseFour"> Daily Fuel Consumption Report </a>
								</h4>
							</div>
							<!-- <div id="collapseFour" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="DailyFuelConsumption" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="SelectFuelGroup"
														name="vehicleGroupId" style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>
											<!-- vehicle Ownership -->
											<div class="row1">
												<label class="L-size control-label" id="VehicleGroup">
													Ownership :</label>
												<div class="I-size">
													<select class=" select2" id="vehicle_Ownership"
														name="vehicle_OwnershipId" style="width: 100%;">
														<option value=""></option>
														<option value="1">Owned</option>
														<option value="2">Leased</option>
														<option value="3">Rented</option>
														<option value="4">Attached</option>
														<option value="5">Customer</option>
														<option value="-1">All</option>
													</select>
												</div>
											</div>
											<!-- Search Vendor Name -->
											<div class="row1">
												<label class="L-size control-label">Vendor Name :</label>
												<div class="I-size">
													<input type="hidden" id="selectVendor" name="vendor_id"
														style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Vendor Name" />
												</div>
											</div>
											<!-- Payment Type -->
											<div class="row1">
												<label class="L-size control-label">Payment Type :</label>
												<div class="I-size">
													<select name="fuel_vendor_paymodeId"
														id="fuel_vendor_paymode" style="width: 100%;">
														<option value=""><!-- please select --></option>
														<option value="1">Cash</option>
														<option value="2">Credit</option>
														<option value="-1">ALL</option>
													</select>
												</div>
											</div>

											<!-- Date range -->

											<div class="row1">
												<label class="L-size control-label">Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="ApprovalPaidDate">
														<input type="text" class="form-text" name="fuel_to"
															required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>

											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success" onclick="return validateDailyFuelConsumptionReport();">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							<!-- </div> -->
						</div>
					</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_FUEL')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty fuel}">
						<section class="invoice">
							<div class="row invoice-info">
								<div class="col-sm-12 col-md-12 col-xs-12"
									style="padding-right: 80px;">
									<div class="table-responsive">
										<table class="table table-hover table-bordered table-striped">
											<tbody>
												<tr>
													<td><c:choose>
															<c:when test="${company.company_id != null}">
																<img
																	src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
																	class="img-rounded " alt="Company Logo" width="280"
																	height="40" />

															</c:when>
															<c:otherwise>
																<i class="fa fa-globe"></i>
																<c:out value="${company.company_name}" />
															</c:otherwise>
														</c:choose></td>
													<td>Print By: ${company.firstName}_${company.lastName}</td>
												</tr>
												<tr>
													<td colspan="2">Branch :<c:out
															value=" ${company.branch_name}  , " /> Department :<c:out
															value=" ${company.department_name}" />
													</td>
												</tr>
											</tbody>
										</table>
										<div class="row invoice-info">
											<table id="advanceTable"
												class="table table-hover table-bordered table-striped">
												<caption>Daily Fuel Consumption Report</caption>

												<thead>
													<tr>
														<th>ID</th>
														<th>Vehicle</th>
														<th>Details</th>
														<th>Date</th>
														<th>O_Km</th>
														<th>C_Km</th>
														<th>Usage</th>
														<th>Volume</th>
														<th>Amount</th>
														<th>Km/L</th>
														<th>Status</th>
														<c:if test="${configuration.showDocColumnInDailyConsReport}">
																	<th>Doc</th>
														</c:if>
														
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${fuel}" var="fuel">
														<tr>
															<td style="width: 80px; word-wrap: break-word;"><a
																target="_blank" href="showFuel.in?FID=${fuel.fuel_id}"
																data-toggle="tip"
																data-original-title="Click Fuel Details"><c:out
																		value="FT-0${fuel.fuel_Number}" /></a></td>
															<td style="width: 80px; word-wrap: break-word;"><a
																target="_blank" href="showFuel.in?FID=${fuel.fuel_id}"
																data-toggle="tip"
																data-original-title="Click Fuel Details"><c:out
																		value="${fuel.vehicle_registration}" /> </a></td>
															<td style="width: 80px; word-wrap: break-word;"><c:out
																	value="${fuel.vehicle_Ownership}" /> <br> <c:out
																	value="${fuel.vehicle_group}" /> <br> <c:out
																	value="${fuel.driver_name}" /></td>
															<td style="width: 100px; word-wrap: break-word;"><c:out
																	value="${fuel.fuel_date}" /><br> -<a
																style="font-size: 9px; font-weight: 400;"
																data-toggle="tip" data-original-title="Vendor Name"><c:out
																		value="${fuel.vendor_name}" />-( <c:out
																		value="${fuel.vendor_location}" /> ) </a></td>
															<td><c:out value="${fuel.fuel_meter_old}" /></td>
															<td><c:out value="${fuel.fuel_meter}" /></td>

															<td><c:out value="${fuel.fuel_usage} km" /></td>

															<td style="width: 80px; word-wrap: break-word;"><abbr
																data-toggle="tip" data-original-title="Liters"><c:out
																		value="${fuel.fuel_liters}" /></abbr> <c:if
																	test="${fuel.fuel_tank_partial==1}">
																	<abbr data-toggle="tip"
																		data-original-title="Partial fuel-up"> <i
																		class="fa fa-adjust"></i>
																	</abbr>
																</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
															<td style="width: 60px; word-wrap: break-word;"><i
																class="fa fa-inr"></i> <fmt:formatNumber maxFractionDigits="2"
																	value="${fuel.fuel_amount}" /> <br> <abbr
																data-toggle="tip" data-original-title="Price"> <c:out
																		value="${fuel.fuel_price}/liters" />
															</abbr></td>
															<td style="width: 60px; word-wrap: break-word;"><c:out
																	value="${fuel.fuel_kml} " /> <br> <c:out
																	value="${fuel.fuel_cost} " /> <c:if
																	test="${fuel.fuel_cost != null}">
																					   /Km
																					</c:if></td>

															<td style="width: 60px; word-wrap: break-word;"><c:out
																	value="${fuel.fuel_reference}" /><br> <c:choose>
																	<c:when test="${fuel.fuel_vendor_paymode == 'PAID'}">
																		<span class="label label-pill label-success"><c:out
																				value="${fuel.fuel_vendor_paymode}" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-danger"><c:out
																				value="${fuel.fuel_vendor_paymode}" /></span>
																	</c:otherwise>
																</c:choose></td>
																<c:if test="${configuration.showDocColumnInDailyConsReport}">
																	<td><a target="_blank" href="download/FuelDocument/${fuel.fuel_document_id}.in"><i
																	class="fa fa-download"></i></a></td>
																</c:if>
														</tr>
													</c:forEach>

												</tbody>
												<tfoot>
													<tr class="workorder_repair_totals">
														<th class="text-right" colspan="7"><b>Total |
																Amount :</b></th>
														<td>${fuelTotalVolume}</td>
														<td><i class="fa fa-inr" data-toggle="tip"
															data-original-title="Total_Amount_cost"></i>
															${fuelTotalAmount}</td>
														<td></td>
														<td></td>
													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</div>
							</div>
						</section>
					</c:if>				
					<c:if test="${NotFound}">
								<script>								
										$(".invoice").addClass("hide");
										setTimeout(function() {validateReport();}, 500);
								</script>
					</c:if>					
					
				</div>
			</div>
		</sec:authorize>

	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDailyFuelConsumptionReport.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>