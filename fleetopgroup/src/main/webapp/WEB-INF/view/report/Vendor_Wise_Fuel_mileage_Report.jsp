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
					/ <a href="<c:url value="/FR.in"/>">Fuel Report</a>  / <span>Vendor Wise Fuel Mileage Report </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vendor Wise Fuel Mileage Report')">
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
		<sec:authorize access="hasAuthority('VIEW_VE_FU_MI_REPORT')">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTwo"> Vendor Wise Fuel Mileage Report  </a>
								</h4>
							</div>
							<!-- <div id="collapseTwo" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="VendorWiseFuelMileageReport" method="post">
										<div class="form-horizontal ">
										
										
										<div class="row1">
											<label class="L-size control-label">Vehicle Name 
											</label>
											<div class="I-size">
												<input type="text" id="ReportSelectVehicle" name="vid" value="0"
												     placeholder="Please Select Vehicle" style="width: 100%;" />
											</div>
										</div>
										
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vendor Name <abbr
													title="required">*</abbr>
												</label>
												
													<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="Vendor_id"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
											</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="rangeFuelMileage" class="form-text"
															name="fuelmileage_daterange" required="required" readonly="readonly"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															 <button type="submit" name="commit" id="validatedata" 
																class="btn btn-success" onclick="return checkVendor();">
																<i class="fa fa-search"> Search</i>
															</button> 
															
															<!-- <input type='button' value='Search' name="commit" class="btn btn-success" > 
															<i class="fa fa-search"> </i>
															</input> -->
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
						<!-- 	</div> -->
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
											<table id="advanceTable" class="table table-hover table-bordered table-striped">
												<caption>Vendor Wise Fuel Mileage Report</caption>

												<thead>
													<tr>
														<th>Id</th>
														<c:if test="${configuration.showVehicleNumCol}">
															<th>Vehicle No</th>
														</c:if>
														<th>Date of Filling</th>
														<th>Date of Creation</th>
														<th>Open_Km</th>
														<th>Close_Km</th>
														<th>Usage</th>
														<th>GPS Usage</th>
														<c:if test="${configuration.showGPSKMPLInVFuelReport}">
															<th>GPS Km/L</th>
														</c:if>
														<th>Volume</th>
														<th>Total Amount</th>
														<c:if test="${!configuration.showAmountPerLiterCol}">
															<th>Price Per liter</th>
														</c:if>
														<c:if test="${configuration.showAmountPerLiterCol}">
														<th>Amount/liter</th>
														</c:if>
														<th>Km/L</th>
														<th>Cost</th>				
														<c:if test="${configuration.showFuelReferenceNumCol}">
															<th>Fuel Reference No</th>
														</c:if>
														
														<c:if test="${configuration.showDriverNameCol}">
															<th>Driver Name</th>
														</c:if>
														
													</tr>
												</thead>
												<tbody>

													<%-- <c:forEach items="${vehicles}" var="vehicles"> --%>
														<!-- Show  Fuel-->
														<tr>
															<td colspan="9"><a target="_blank"
																style="font-size: 20px; color: black;"
																href="ShowVendor.in?vendorId=${fuel[0].vendor_id}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info">
																 <c:out value="${fuel[0].vendor_name}" />
															</a> - Expected Mileage ${vehicles.vehicle_ExpectedMileage}
																to ${vehicles.vehicle_ExpectedMileage_to}</td>
														</tr>

														<c:if test="${!empty fuel}">
															<c:forEach items="${fuel}" var="fuel">

																<%-- <c:if test="${vehicles.vid == fuel.vid}"> --%>

																	<tr>
																		<td><a target="_blank"
																			href="showFuel.in?FID=${fuel.fuel_id}"
																			data-toggle="tip"
																			data-original-title="Click Fuel Details"><c:out
																					value="FT-0${fuel.fuel_Number}" /></a></td>
																		<c:if test="${configuration.showVehicleNumCol}">
																			<td><c:out value="${fuel.vehicle_registration}" /></td>
																		</c:if>			
																		<td style="width: 100px; word-wrap: break-word;"><c:out
																				value="${fuel.fuel_date}" /><br> </td>
																		<td style="width: 100px; word-wrap: break-word;">
 																			<c:out value="${fuel.created}" /><br>
																		</td>		
																		<td><c:out value="${fuel.fuel_meter_old}" /></td>
																		<td><c:out value="${fuel.fuel_meter}" /></td>

																		<td><c:out value="${fuel.fuel_usage} km" /></td>
																		<td><c:out value="${fuel.gpsUsageKM} km" /></td>
																		<c:if test="${configuration.showGPSKMPLInVFuelReport}">
																			<td><c:out value="${fuel.gpKMPL} km" />
																				<c:if
																				test="${fuel.fuel_kml != null}">
																				<c:choose>
																					<c:when
																						test="${vehicles.vehicle_ExpectedMileage <= fuel.gpKMPL}">
																						<c:choose>
																							<c:when
																								test="${vehicles.vehicle_ExpectedMileage_to >= fuel.gpKMPL}">
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
																			</c:if>
																			</td>
																		</c:if>

																		<td><abbr data-toggle="tip"
																			data-original-title="Liters"><c:out
																					value="${fuel.fuel_liters}" /></abbr> <c:if
																				test="${fuel.fuel_tank_partial==1}">
																				<abbr data-toggle="tip"
																					data-original-title="Partial fuel-up"> <i
																					class="fa fa-adjust"></i>
																				</abbr>
																			</c:if></td>
																		
																		<td><i class="fa fa-inr"></i> 
																			<c:out value="${fuel.fuel_amount}" /> 
																		</td>
																		
																		<c:if test="${!configuration.showAmountPerLiterCol}">
																			<td><i class="fa fa-inr"></i> 
																			<abbr data-toggle="tip" data-original-title="Price">
																				<c:out value="${fuel.fuel_price}/liters" /></abbr>
																			</td>
																		</c:if>
																		<c:if test="${configuration.showAmountPerLiterCol}">
																		<td>
																		<c:out value="${fuel.fuel_price}"/>
																		</td>
																		</c:if>
																		<td><c:out value="${fuel.fuel_kml} " /> <c:if
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
																	</c:if>
																	
																	</td>
																	<c:if test="${configuration.showFuelReferenceNumCol}">
																		<td><c:out value="${fuel.fuel_reference}" /></td>
																	</c:if>
																	
																	<c:if test="${configuration.showDriverNameCol}">
																		<td><c:out value="${fuel.driver_name}" /></td>
																	</c:if>
																	
																	</tr>
																<%-- </c:if> --%>
															</c:forEach>
														</c:if>

													<%-- </c:forEach> --%>
													<!-- close Vehicle for -->

													<!-- close Vehicle If -->
												</tbody>
												<tfoot>
													<tr class="workorder_repair_totals">
														<th class="text-right" colspan="8"><b>Total
																liters | Amount :</b></th>
														<td>${TotalLiterAmount}</td>
														<c:if test="${configuration.showGPSKMPLInVFuelReport}">
															<td></td>
														</c:if>
														<td><i class="fa fa-inr" data-toggle="tip"
															data-original-title="Total_Amount_cost"></i>
															${TotalFuelAmount}</td>
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
										//$(".invoice").addClass("hide");
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
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/exportCSV/tableToExcel.js"></script>		
			
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	
	<script type="text/javascript">
	function checkVendor() {
	    if(Number($('#selectVendor').val()) <= 0){
	    	showMessage('info','Please Select Vendor Name!');
	    	return false;
	    }
	};
	</script>
	
</div>
