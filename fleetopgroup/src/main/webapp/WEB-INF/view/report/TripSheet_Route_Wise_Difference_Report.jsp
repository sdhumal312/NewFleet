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
						Sheet Difference Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'TripSheet Difference Report')">
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
		
		
		
		<!--SRS Travels Trip Sheet Route Wise Difference KM and Volume Report By Dev Yogi Start-->
			<sec:authorize access="hasAuthority('VIEW_TS_RO_DI_KM_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#TripCollectionThree">Trip Sheet Route Wise
									Difference KM &amp; Volume Report </a>
							</h4>
						</div>
						<!-- <div id="TripCollectionThree" class="panel-collapse collapse"> -->
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Group Search GroupWiseTripColReport -->
									<form action="TSDiffKMVOLUMEReport" method="post">
										<!-- vehicle Group Service -->
										<div class="row1">
											<label class="L-size control-label"> Route : 
												</label>
											<div class="I-size">

												<input type="hidden" id="TripRouteList" name="TRIP_ROUTE"
													style="width: 100%;" placeholder="ALL" />
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
													<input type="text" id="GTC_daterange" class="form-text"
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
															class="btn btn-success" >
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>

									</form>
									<!-- end Show Group Search RR Range -->
								</div>
							</div>
						<!-- </div> -->
					</div>
			</sec:authorize>		
		<!--SRS Travels Trip Sheet Route Wise Difference KM and Volume Report By Dev Yogi End-->
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
												<caption>TripSheet Difference Report
													-${SEARCHDATE}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>TripSheet</th>
														<th>Drivers</th>
														<th>TripDate</th>
														<th>Open KM</th>
														<th>Closed KM</th>
														<th>Usage KM</th>
														<th>Fixed KM</th>
														<th>Diff-KM</th>
														<th>Usage Liter</th>
														<th>Fixed Liter</th>
														<th>Diff-Liter</th>
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
															<td><c:out value="${TripSheet.vehicle_registration}" /><br>
																<a target="_blank" data-toggle="tip"
																data-original-title="Click Trip Details"
																href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>">
																	<c:out value="TS-${TripSheet.tripSheetNumber}" />
															</a></td>
															<td><c:out value="${TripSheet.tripFristDriverName} " /><br>
																<c:out value="${TripSheet.tripSecDriverName}" /></td>
															<td><c:out value="${TripSheet.tripOpenDate}  to " /><br>
																<c:out value="${TripSheet.closetripDate}" /></td>

															<td><c:out value="${TripSheet.tripOpeningKM}" /></td>
															<td><c:out value="${TripSheet.tripClosingKM}" /></td>

															<td><c:out value="${TripSheet.tripUsageKM}" /></td>
															<td><c:out value="${TripSheet.routeApproximateKM}" /></td>
															<td><c:choose>
																	<c:when test="${TripSheet.tripDiffernceKM <= 0}">
																		<abbr data-toggle="tip"
																			data-original-title="${TripSheet.routeName}  Fixed KM to ${TripSheet.tripDiffernceKM} Low">
																			<i class="fa fa-arrow-circle-down"
																			style="color: red; font-size: 19px;"> <c:out
																					value="${TripSheet.tripDiffernceKM}" /></i>
																		</abbr>
																	</c:when>
																	<c:otherwise>
																		<abbr data-toggle="tip"
																			data-original-title="${TripSheet.routeName}  Fixed KM to ${TripSheet.tripDiffernceKM} High">
																			<i class="fa fa-arrow-circle-up"
																			style="color: #1FB725; font-size: 19px;"> <c:out
																					value="${TripSheet.tripDiffernceKM}" /></i>
																		</abbr>

																	</c:otherwise>
																</c:choose></td>

															<td><c:out value="${TripSheet.tripUsageLiter}" /></td>
															<td><c:out value="${TripSheet.routeTotalLiter}" /></td>
															<td><c:choose>
																	<c:when test="${TripSheet.tripDiffernceLiter <= 0.0}">
																		<abbr data-toggle="tip"
																			data-original-title="${TripSheet.routeName}  Fixed Liter to ${TripSheet.tripDiffernceLiter} Low">
																			<i class="fa fa-arrow-circle-down"
																			style="color: red; font-size: 19px;"> <c:out
																					value="${TripSheet.tripDiffernceLiter}" /></i>
																		</abbr>
																	</c:when>
																	<c:otherwise>
																		<abbr data-toggle="tip"
																			data-original-title="${TripSheet.routeName}  Fixed Liter to ${TripSheet.tripDiffernceLiter} High">
																			<i class="fa fa-arrow-circle-up"
																			style="color: #1FB725; font-size: 19px;"> <c:out
																					value="${TripSheet.tripDiffernceLiter}" /></i>
																		</abbr>

																	</c:otherwise>
																</c:choose></td>

														</tr>
													</c:forEach>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateTripSheetRouteWiseDifferenceKmAndVolumeReport.js"/>"></script>
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