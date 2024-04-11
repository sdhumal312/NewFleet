<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<style>
.closeAmount th, td {
	text-align: center;
}

.closeRouteAmount td {
	text-align: center;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: center;
	font-weight: bold;
}

.actualkm {
	float: center;
}

.columnDaily {
	text-align: center;
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
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a> / <span><c:out
							value=" BUS OPERATORS - ${SEARCHDATE}" /> Collection Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vehicle TripDaily Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VE_DA_TD_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionTwo">Vehicle Wise Date Range Weekly,
									Monthly, Yearly Trip Collection Report </a>
							</h4>
						</div>
						<!-- <div id="TripCollectionTwo" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="VehicleWiseTripDailyReport" method="post">
									<div class="form-horizontal ">
										<!-- vehicle Select -->
										<div class="row1">
											<label class="L-size control-label">Depot : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="vehicleGroupId6"
													name="vehicleGroupId6" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
												
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vehicle Name <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<!-- <input type="hidden" id="TCVehicle" name="VEHICLE_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Or More Vehicle</p> -->
												
												<select style="width: 100%;" id="Vehicleofdepot6"
												name="VEHICLE_ID">
												</select> <span id="tyreModelIcon" class="">
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
													<input type="text" id="VTC_daterange" class="form-text"
														name="VEHICLE_TC_DATERAGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit" onclick="return validationVehicleWiseDateRangeWeeklyMonthlyYearlyTripCollectionReport();"

															class="btn btn-success">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</form>
								<!-- end Repair Report -->
							</div>
					<!-- 	</div> -->
					</div>
				</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty TripDaily}">
						<section class="invoice">
							<div class="row invoice-info">
								<div class="col-xs-12">
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
										<div id="sorttable-div" align="center" style="font-size: 10px"
											class="table-responsive ">
											<div class="row invoice-info">
												<table style="width: 95%">
													<tbody>
														<tr>
															<td align="center"><span class="text-bold"> <c:out
																		value=" BUS OPERATORS - ${SEARCHDATE}" />
															</span></td>
														</tr>
													</tbody>
												</table>
												<table id="advanceTable" style="width: 95%"
													class="table table-hover table-bordered table-striped">
													<thead>
														<tr class="closeAmount">
															<th>No</th>
															<th>Date</th>
															<th>Bus NO/ROUTE</th>												
															<th>Day</th>															
															<th>DRIVER/CONDUCTOR</th>
															<th>END.KM</th>
															<th>STA.KM</th>
															<th>Total.KM</th>
															<th>DIESEL</th>
															<c:if test="${configuration.dieselAmount}">
															<th>DIESEL Amount</th>
															</c:if>
															<th>KMPL</th>
															<th>PSNGR</th>
															<th>RFID Pass</th>
															<th>RFID AMOUNT</th>
															<th>Collection</th>
															<c:if test="${configuration.hideWT}">
															<th>WT</th>
															</c:if>
															<th>NET Collection</th>
															<c:if test="${configuration.hideEPK}">
															<th>EPK</th>
															</c:if>
															<th>EXPENSE</th>
															<c:if test="${!configuration.hideOT}">
															<th>OT</th>
															</c:if>
															<th>BALANCE</th>
														</tr>	
													</thead>
													<tbody>

														<%
														Integer hitsCount = 1;
													%>
														<c:forEach items="${TripDaily}" var="TripDaily">
															<tr data-object-id="" class="ng-scope closeAmount">
																<td class="fit">
																	<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
																</td>
																<td><a target="_blank"
																	href="<c:url value="/showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"/>"><c:out
																			value="${TripDaily.VEHICLE_REGISTRATION}" /></a><br>
																	<c:out value="${TripDaily.TRIP_ROUTE_NAME}" /></td>
																<td><c:out value="${TripDaily.TRIP_OPEN_DATE}" /></td>																
																<td><c:out value="${TripDaily.DAY_OF_MONTH}" /></td>													
																<td><c:out value="${TripDaily.TRIP_DRIVER_NAME}" /><br>
																	<c:out value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></td>
																<td><c:out value="${TripDaily.TRIP_OPEN_KM}" /></td>
																<td><c:out value="${TripDaily.TRIP_CLOSE_KM}" /></td>
																<td><c:out value="${TripDaily.TRIP_USAGE_KM}" /></td>
																<td><c:out value="${TripDaily.TRIP_DIESEL}" /></td>
																<c:if test="${configuration.dieselAmount}">
																<td><c:forEach items="${FuelAmt}" var="FuelAmt">
																	<c:if test="${TripDaily.TRIPDAILYID == FuelAmt.TRIPDAILYID}">
																	<c:if test="${!empty FuelAmt.fuel_amount}">
																		<c:out value="${FuelAmt.fuel_amount}" />
																		</c:if>
																	<c:if test="${empty FuelAmt.fuel_amount}">
																	<c:set var = "fuel" scope = "session" value = "${0}"/>
      																<c:out value = "${fuel}"/>
																	</c:if>	
																	</c:if>
																</c:forEach>
																</td>
																</c:if>
																<td><c:out value="${TripDaily.TRIP_DIESELKMPL}" /></td>
																<td><c:out value="${TripDaily.TRIP_TOTALPASSNGER}" /></td>
																<td><c:out value="${TripDaily.TRIP_RFIDPASS}" /></td>
																<td><c:out value="${TripDaily.TRIP_RFID_AMOUNT}" /></td>
																<td><c:out value="${TripDaily.TOTAL_INCOME}" /></td>
																<c:if test="${configuration.hideWT}">
																<td><c:out value="${TripDaily.TOTAL_WT}" /></td>
																</c:if>
																<c:if test="${configuration.netCollection}">
															<td><c:out value="${TripDaily.TRIP_RFID_AMOUNT + TripDaily.TOTAL_INCOME}" /></td>
															</c:if>
															<c:if test="${!configuration.netCollection}">
															<td><c:out value="${TripDaily.TOTAL_NET_INCOME }" /></td>
															</c:if>
																
																<c:if test="${configuration.hideEPK}">
																<td><c:out value="${TripDaily.TOTAL_EPK}" /></td>
																</c:if>
																<td><c:out value="${TripDaily.TOTAL_EXPENSE}" /></td>
																<c:if test="${!configuration.hideOT}">
																<td><c:out value="${TripDaily.TRIP_OVERTIME}" /></td>
																</c:if>
																<td><c:out value="${TripDaily.TOTAL_BALANCE}" /></td>
															
															</tr>
														</c:forEach>
														<tr class="closeGroupAmount">
															<td colspan="7"><c:out value="Total :" /></td>
															<td><c:out value="${TotalUsageKM}" /></td>
															<td><c:out value="${TotalDiesel}" /></td>
															<c:if test="${configuration.dieselAmount}">
																<td><c:out value="${DieselTotalAmount}" /></td>
															</c:if>
															<td><c:out value="${TotalKMPL}" /></td>
															<td><c:out value="${TotalPassenger}" /></td>
															<td><c:out value="${TotalRFID}" /></td>
															<td><c:out value="${TotalRFIDAmount}" /></td>
															<td><c:out value="${TotalCollection}" /></td>
															<c:if test="${configuration.hideWT}">
																<td><c:out value="${TotalWT}" /></td>
															</c:if>
															<c:if test="${configuration.netCollection}">
																<td><c:out value="${NetTotalCollection}" /></td>
															</c:if>
															<c:if test="${!configuration.netCollection}">
																<td><c:out value="${TotalNetCollection}" /></td>
															</c:if>
															<c:if test="${configuration.hideEPK}">
																<td><c:out value="${TotalEPK}" /></td>
															</c:if>
																<td><c:out value="${TotalExpense}" /></td>
															<c:if test="${!configuration.hideOT}">
															<td><c:out value="${TotalOT}" /></td>
															</c:if>
															
															<c:if test="${configuration.totalBalance}">
																<td><c:out value="${BalanceTotal}" /></td>
															</c:if>
															<c:if test="${!configuration.totalBalance}">
																<td><c:out value="${TotalBalance}" /></td>
															</c:if>
															
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</section>
					</c:if>
					
					<c:if test="${NotFound}">
					<script>
							$(".invoice").addClass("hide");	
							setTimeout(function(){validateReport(); }, 500);
					</script>											
					</c:if>	
					
				</div>
			</div>
		</sec:authorize>

	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js"/>"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/validateReport1.js"/>"></script>	
		
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteFixedAdd.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask();
			$('#monthRangeSelector').Monthpicker();
			$('#monthRangeSelector2').Monthpicker();
		})
	</script>
</div>