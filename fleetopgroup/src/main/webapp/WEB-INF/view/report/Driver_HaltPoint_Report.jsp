<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<style>
.closeAmount td {
	text-align: right;
}

.actualkm {
	width: 0.8%;
	float: left;
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
					/ <a href="<c:url value="/AR.in"/>">Driver Attendance Report</a> / <span>Driver
						Group Attendance Point Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Driver Group Attendance Point Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		
		<!--SRS Travels Driver Attendance Point Report By Dev Yogi Start -->
						<sec:authorize access="hasAuthority('VIEW_DR_PO_REPORT')">
					<!-- SRS TRAVELS SHOW DETAILS -->

					<div class="panel box box-danger">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseTwo"> Driver Attendance Point Report </a>
							</h4>
						</div>
						<!-- <div id="collapseTwo" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="DriverHaltPointReport" method="post">
									<div class="form-horizontal ">
										<div class="row1">
											<label class="L-size control-label">Driver Name <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="JAMAdriverList2" name="DRIVER_ID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Driver Name, No"
													value="0" /> <label id="errorDriver1" class="error"></label>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="rangeFuelMileage" class="form-text"
														name="HALT_DATE_RANGE" required="required"
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
															class="btn btn-success" onclick="return validateDriverAttendancePointReport();"
															>
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
		<!--SRS Travels Driver Attendance Point Report By Dev Yogi End -->
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty DA}">
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
												<caption>Driver Attendance Point Report
													-${SearchDate}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>Driver/Group</th>
														<th>Att-Date</th>
														<th>TripSheet</th>
														<th>Route</th>
														<th>TS-date</th>
														<th>Vehicle</th>
														<th>Point</th>
														<th>Type</th>
													</tr>
												</thead>
												<tbody>
													<%
													Integer hitsCount = 1;
												%>


													<c:forEach items="${DA}" var="DA">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${DA.DRIVER_NAME}" /><br>
															<c:out value="${DA.DRIVER_GROUP}" /></td>
															<td><c:out value="${DA.ATTENDANCE_DATE}" /></td>
															<td><c:if test="${DA.TRIPSHEETID != 0}">
																	<a
																		href="showTripSheet.in?tripSheetID=${DA.TRIPSHEETID}"
																		target="_blank"><c:out
																			value="TS-${DA.TRIPSHEETNUMBER}" /></a>
																</c:if></td>
															<td><c:out value="${DA.TRIP_ROUTE_NAME}" /></td>
															<td><c:out value="${DA.TRIP_OPEN_DATE} -" />
																<c:out value=" ${DA.TRIP_CLOSE_DATE}" /></td>
															<td><c:out value="${DA.TRIP_VEHICLE_NAME}" /></td>
															<td><c:out value="${DA.DRIVER_POINT}" /></td>
															<td><c:out value="${DA.POINT_TYPE}" /></td>

														</tr>
													</c:forEach>
													<tr>
														<td colspan="9"></td>
													</tr>
													<tr>
														<td colspan="7"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL
															POINT :</td>
														<td colspan="2"
															style="text-align: left; font-size: 15px; font-weight: bold;"><c:out
																value="${TotalPoint}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</c:if>
									<c:if test="${NotFound}">
										<script>
											$(".invoice").addClass("hide");
											setTimeout(function() {
												validateReport();
											}, 500);
										</script>
									</c:if>
								</div>
							</div>
						</div>
					</section>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
		
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
			<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDriverAttendancePointReport.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>		
</div>