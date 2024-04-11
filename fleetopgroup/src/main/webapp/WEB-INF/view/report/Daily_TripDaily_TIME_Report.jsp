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
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TDR.in"/>">Trip Collection Report</a> / <span>Daily
						Trip Collection Time Report</span>
				</div>
				<div class="pull-right">

					<div style="display: inline-block; width: 100px"></div>
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Trip Collection Time Report')">
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
		<sec:authorize access="hasAuthority('VIEW_DE_VE_TI_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionTime">Vehicle Trip Wise Report </a>
							</h4>
						</div>
						<!-- <div id="TripCollectionTime" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="DailyTripDailyTimeReport" method="post">
									<div class="form-horizontal ">

										<!-- vehicle Group Service -->
										<div class="row1">
											<label class="L-size control-label">Vehicle Name <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="TCVehicle" name="VEHICLE_ID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Or More Vehicle</p>
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
														<button type="submit" name="commit"
															class="btn btn-success" onclick="return validateVehicleTripWiseReport();">
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
	<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
		<div id="div_print">

			<div id="div_print">
				<c:if test="${!empty TDRoute}">
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
																	value="${VEHICLE_NAME}" /> Daily Trip Time Report <c:out
																	value=" - ${SEARCHDATE}" />
														</span></td>
													</tr>
												</tbody>
											</table>
											<table id="advanceTable" style="width: 95%"
												class="table table-hover table-bordered table-striped">
												<thead>
													<tr>
														<th>No</th>
														<th>DATE</th> ${TDINCOME_NAME}
														<th>Total.K.M</th>
														<th>Diesel</th>
														<c:if test="${configuration.showDieselAmount}">
															<th>Diesel Amount</th>
														</c:if>
														<th>KMPL</th>
														<th>Collection</th>
														<th>Expense</th>
														<th>Balance</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>


													<c:forEach items="${TDRoute}" var="TDRoute">

														<tr data-object-id="" class="closeAmount">
															<td class="fit">
																<%
																		out.println(hitsCount);
																					hitsCount += 1;
																	%>
															</td>
															<td><a target="_blank"
																href="showTripDaily.in?ID=${TDRoute.TRIPDAILYID}"><c:out
																		value="${TDRoute.TRIP_OPEN_DATE}" /></a></td>
															${TDRoute.CREATEDBY}
															<td><c:out value="${TDRoute.TRIP_USAGE_KM}" /></td>
															<td><c:out value="${TDRoute.TRIP_DIESEL}" /></td>
															<c:if test="${configuration.showDieselAmount}">
																<td><c:out value="${TDRoute.TRIP_DIESEL_AMOUNT}" /></td>
															</c:if>
															<td><c:out value="${TDRoute.TRIP_DIESELKMPL}" /></td>

															<td><c:out value="${TDRoute.TOTAL_INCOME}" /></td>
															<td><c:out value="${TDRoute.TOTAL_EXPENSE}" /></td>
															<td><c:out value="${TDRoute.TOTAL_BALANCE}" /></td>

														</tr>

													</c:forEach>

													<tr data-object-id="" class="closeGroupAmount">
														<td colspan="2"><c:out value="Total :" /></td>
														${TDINCOME_COLUMN}
														<td><c:out value="${TRIP_USAGE_KM}" /></td>
														<td><c:out value="${TRIP_DIESEL}" /></td>
														<td><c:out value="${TRIP_DIESEL_AMOUNT}" /></td>
														<td><c:out value="${TRIP_DIESELKMPL}" /></td>
														<td><c:out value="${TOTAL_BALANCE}" /></td>
														<td><c:out value="${TOTAL_INCOME}" /></td>
														<td><c:out value="${TOTAL_EXPENSE}" /></td>
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
							setTimeout(function(){showMessage('info', 'No Record Found !') }, 500);
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateVehicleTripWiseReport.js"/>"></script>
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