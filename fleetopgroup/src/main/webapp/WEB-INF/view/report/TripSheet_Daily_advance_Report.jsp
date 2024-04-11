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
					/<a href="<c:url value="/TSR.in"/>">Trip sheet Report</a> / <span>Trip
						Sheet Daily Report</span>
				</div>
				<div class="pull-right">
					<%-- <div style="display: inline-block;">
						<form action="DailyTSAllAdvanceReport" method="post">
							<input type="hidden" name="TRIP_GROUP" required="required"
								value="" /> <input type="hidden" name="TRIP_DATE_RANGE"
								placeholder="dd-mm-yyyy" required="required"
								value="${YESTERDAY}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${TRIP_DATE}</div>
					<div style="display: inline-block;">
						<form action="DailyTSAllAdvanceReport" method="post">
							<input type="hidden" name="TRIP_GROUP" required="required"
								value="" /> <input type="hidden" name="TRIP_DATE_RANGE"
								placeholder="dd-mm-yyyy" required="required" value="${TOMORROW}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-forward" aria-hidden="true"></span>
							</button>
						</form>
					</div> --%>
					<div style="display: inline-block; width: 100px"></div>
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'TripSheet Daily Report')">
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
		<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		
		
		<!--SRS Travels Daily Trip Sheet Advances Report By Dev Yogi Start -->
			<sec:authorize access="hasAuthority('VIEW_CL_TS_AD_REPORT')">
					<!-- TripSHeet date wise advacne Report  -->
					<div class="tab-pane" id="TSReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripsheetDailytrip">Daily Trip sheet Advances
										Report </a>
								</h4>
							</div>
							<!-- <div id="TripsheetDailytrip" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="DailyTSAllAdvanceReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Group: <abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="workOrderGroup" name="TRIP_GROUP"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Vehicle Group</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group input-append date" id="TCDailydate">
														<input type="text" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
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
																class="btn btn-success" onclick="return validateDailyTripSheetAdvancesReport();">
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
							<!-- </div> -->
						</div>
					</div>
				</sec:authorize>		
		<!--SRS Travels Daily Trip Sheet Advances Report By Dev Yogi End -->
		<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty TripSheet}">
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
												<caption>Daily TripSheet Advance Report
													-${SearchDate}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>TS-ID</th>
														<th>Vehicle</th>
														<th>Open Date</th>
														<th>Route</th>
														<th>Status</th>
														<th style="text-align: right;">Advance</th>
														<th>Closed Date</th>
													</tr>
												</thead>
												<tbody>
													<%
													Integer hitsCount = 1;
												%>


													<c:forEach items="${TripSheet}" var="TripSheet">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit"><c:choose>
																	<c:when test="${TripSheet.tripStutesId ==1}">
																		<a
																			href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																				value="TS-${TripSheet.tripSheetNumber}" /></a>
																	</c:when>
																	<c:when test="${TripSheet.tripStutesId == 2}">
																		<a
																			href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																				value="TS-${TripSheet.tripSheetNumber}" /></a>
																	</c:when>
																	<c:otherwise>
																		<a
																			href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"><c:out
																				value="TS-${TripSheet.tripSheetNumber}" /></a>
																	</c:otherwise>
																</c:choose></td>
															<td><c:out value="${TripSheet.vehicle_registration}" /></td>
															<td><c:out value="${TripSheet.tripOpenDate}" /></td>
															<td><c:out value="${TripSheet.routeName}" /></td>
															<td><c:out value="${TripSheet.tripStutes} " /></td>
															<td style="text-align: right;"><c:out
																	value="${TripSheet.tripTotalAdvance}" /></td>

															<td><c:choose>
																	<c:when test="${TripSheet.tripStutesId ==3}">
																		<c:out value="${TripSheet.closetripDate}  " />
																	</c:when>
																</c:choose></td>
														</tr>

													</c:forEach>
													<tr>
														<td colspan="7"></td>
													</tr>
													<tr>
														<td colspan="6"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL:</td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"><c:out
																value="${AdvanceTotal}" /></td>
														<td
															style="text-align: right; font-size: 15px; font-weight: bold;"></td>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/DailyTripSheetAdvancesReport.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>