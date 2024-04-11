<%@ include file="taglib.jsp"%>
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
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a>  /  <span>Group Wise Fuel Range Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Group Wise Fuel Range Report')" >
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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
		
		
		<!--Experimental Code Section Start  By Y -->
		<sec:authorize access="hasAuthority('VIEW_FU_RA_REPORT')">

						<div class="panel box box-success"> 
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseThree"> Fuel Range Report </a>
								</h4>
							</div>
							<!-- <div id="collapseThree" class="panel-collapse collapse"> -->
								<div class="box-body">

									<div class="form-horizontal "> 

										<!--  Select Type Search -->
										<!-- <div class="row1">
											<label class="L-size control-label"> Search By</label>
											<div class="I-size">
												<label><input type="radio" name="SearchRadio"
													value="VEHICLE"> Vehicle</label> <label><input
													type="radio" name="SearchRadio" value="GROUP">
													Group </label>

											</div>
										</div> -->

										<!-- Show Vehicle Search Fuel Range -->
										<!-- <div class="VEHICLE HideRadioBox"> 
											<form action="VehicleFuelRange" method="post"> 
												vehicle Select
												<div class="row1">
													<label class="L-size control-label">Vehicle Name <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="ReportVehicleFuelRange"
															name="fuelRange_vid" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Or More Vehicle</p>

													</div>
												</div>
												Fuel range Select
												<div class="row1">
													<label class="L-size control-label">Fuel range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text" class="selectpicker"
															name="fuelRange">
															<option value="1" style="color: red; font-size: 19px;">Below
																the Range</option>
															<option value="2"
																style="color: #1FB725; font-size: 19px;">With
																in the Range</option>
															<option value="3" style="color: blue; font-size: 19px;">Out
																of Range</option>

														</select>
													</div>
												</div>

												Date range
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="VehicleFuelRange"
																class="form-text" name="fuelRange_daterange"
																required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>

												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit"
																	class="btn btn-success" onclick="return validateFuelRangeReport1();">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
												</fieldset>
											</form>
										</div>
 -->										<!-- end Show Vehicle Search Fuel Range -->
										<!-- Show Group Search Fuel Range -->
										<div class="GROUP">
											<form action="GroupFuelRange" method="post">
												<!-- vehicle Group Service -->
												<div class="row1">
													<label class="L-size control-label"> Group :</label>
													<div class="I-size">
														<input type="hidden" id="SelectVehicleGroup"
															name="fuelRange_group" style="width: 100%;"
															placeholder="Please Enter 2 or more Group Name" />
													</div>
												</div>
												<!-- Fuel range Select -->
												<div class="row1">
													<label class="L-size control-label">Fuel range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text" name="fuelRange"
															id="renPT_option">
															<option value="1" style="color: red; font-size: 19px;">Below
																the Range</option>
															<option value="2"
																style="color: #1FB725; font-size: 19px;">With
																in the Range</option>
															<option value="3" style="color: blue; font-size: 19px;">Out
																of Range</option>

														</select>
													</div>
												</div>

												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="GroupFuelRange" class="form-text"
																name="fuelRange_daterange" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>

												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit"
																	class="btn btn-success" onclick="return validateFuelRangeReport();">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
												</fieldset>

											</form>
										</div>
										<!-- end Show Group Search Fuel Range -->

									  <!-- </div> -->
								</div>
							</div>
						</div>
					</sec:authorize>
		<!--Experimental Code Section End By Y  -->
		
		
		<sec:authorize access="hasAuthority('VIEW_FUEL')">
		
			<div id="div_print">
				<c:if test="${!empty vehicles}">
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
											<caption>Group Wise Fuel Range Report</caption>
											<thead>
												<tr>
													<th>Id</th>
													<th>Driver</th>
													<th>Date</th>
													<th>Open_Km</th>
													<th>Close_Km</th>
													<th>Usage</th>
													<th>Volume</th>
													<th>Amount</th>
													<th>Km/L</th>
													<th>Cost</th>
												</tr>
											</thead>
											<tbody>

												<c:forEach items="${vehicles}" var="vehicles">
													<!-- Show  Fuel-->
													<tr>
														<td colspan="9"><a target="_blank"
															style="font-size: 20px; color: black;"
															href="showVehicle.in?vid=${vehicles.vid}"
															data-toggle="tip"
															data-original-title="Click Vehicle Info"> <c:out
																	value="${vehicles.vehicle_registration}" />
														</a> - Expected Mileage ${vehicles.vehicle_ExpectedMileage} to
															${vehicles.vehicle_ExpectedMileage_to}</td>
													</tr>
													<c:if test="${!empty fuel}">
														<c:forEach items="${fuel}" var="fuel">
															<c:if test="${vehicles.vid == fuel.vid}">
																<tr>
																	<td><a target="_blank"
																		href="showFuel.in?FID=${fuel.fuel_id}"
																		data-toggle="tip"
																		data-original-title="Click Fuel Details"><c:out
																				value="FT-0${fuel.fuel_Number}" /></a></td>
																	<td><c:out value="${fuel.driver_name}" />
																		<c:out value="${fuel.firstDriverLastName}" />
																		<c:out value="${fuel.firstDriverFatherName}" />
																	</td>				
																	<td style="width: 100px; word-wrap: break-word;"><c:out
																			value="${fuel.fuel_date}" /><br> -<a
																		style="font-size: 9px; font-weight: 400;"
																		data-toggle="tip" data-original-title="Vendor Name"><c:out
																				value="${fuel.vendor_name}" />-( <c:out
																				value="${fuel.vendor_location}" /> ) </a></td>
																	<td><c:out value="${fuel.fuel_meter_old}" /></td>
																	<td><c:out value="${fuel.fuel_meter}" /></td>
																	<td><c:out value="${fuel.fuel_usage} km" /></td>
																	<td><abbr data-toggle="tip"
																		data-original-title="Liters"><c:out
																				value="${fuel.fuel_liters}" /></abbr> <c:if
																			test="${fuel.fuel_tank_partial==1}">
																			<abbr data-toggle="tip"
																				data-original-title="Partial fuel-up"> <i
																				class="fa fa-adjust"></i>
																			</abbr>
																		</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
																	<td><i class="fa fa-inr"></i> <c:out
																			value="${fuel.fuel_amount}" /> <br> <abbr
																		data-toggle="tip" data-original-title="Price">
																			<c:out value="${fuel.fuel_price}/liters" />
																	</abbr></td>
																	<td style="width: 50px; word-wrap: break-word;"><c:out
																			value="${fuel.fuel_kml} " /> <c:if
																			test="${fuel.fuel_kml != null}">
																			<c:choose>
																				<c:when
																					test="${vehicles.vehicle_ExpectedMileage <= fuel.fuel_kml}">
																					<c:choose>
																						<c:when
																							test="${vehicles.vehicle_ExpectedMileage_to >= fuel.fuel_kml}">
																							<abbr data-toggle="tip"
																								data-original-title="Expected Mileage ${vehicles.vehicle_ExpectedMileage} to ${vehicles.vehicle_ExpectedMileage_to}">
																								<i class="fa fa-stop-circle"
																								style="color: #1FB725; font-size: 19px;"></i>
																							</abbr>
																						</c:when>
																						<c:otherwise>
																							<abbr data-toggle="tip"
																								data-original-title="Expected Mileage ${vehicles.vehicle_ExpectedMileage} to ${vehicles.vehicle_ExpectedMileage_to}">
																								<i class="fa fa-chevron-circle-up"
																								style="color: blue; font-size: 19px;"></i>
																							</abbr>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise>
																					<abbr data-toggle="tip"
																						data-original-title="Expected Mileage ${vehicles.vehicle_ExpectedMileage}  to ${vehicles.vehicle_ExpectedMileage_to}">
																						<i class="fa fa-chevron-circle-down"
																						style="color: red; font-size: 19px;"></i>
																					</abbr>
																				</c:otherwise>

																			</c:choose>
																		</c:if></td>
																	<td><c:out value="${fuel.fuel_cost} " /> <c:if
																			test="${fuel.fuel_cost != null}">
																					   /Km
																					</c:if></td>
																</tr>
															</c:if>
														</c:forEach>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</section>
				</c:if>
			</div>
			
		</sec:authorize>

	</section>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
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
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
	
</div>