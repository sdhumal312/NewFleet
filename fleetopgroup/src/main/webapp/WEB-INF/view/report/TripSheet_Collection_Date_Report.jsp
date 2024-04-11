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
						Sheet Date Report</span>
				</div>
				<div class="pull-right">
					<%-- <div style="display: inline-block;">
						<form action="TripSheetCollectionDateReport" method="post">
							<input type="hidden" name="TRIP_DATE_RANGE"
								placeholder="yyyy-mm-dd" required="required"
								value="${YESTERDAY}" />
							<button class="btn btn-default" type="submit">
								<span class="fa fa-backward" aria-hidden="true"></span>
							</button>
						</form>
					</div>
					<div style="display: inline-block;">${TRIP_DATE}</div>
					<div style="display: inline-block;">
						<form action="TripSheetCollectionDateReport" method="post">
							<input type="hidden" name="TRIP_DATE_RANGE"
								placeholder="yyyy-mm-dd" required="required" value="${TOMORROW}" />
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
						onclick="advanceTableToExcel('advanceTable', 'TripSheet Date Report')">
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
		
		<!--SRS Travels Trip Sheet Collection Report Start-->
			<sec:authorize access="hasAuthority('VIEW_TS_CO_REPORT')">
					<!-- Trip sheet Collection Date Report -->
					<div class="tab-pane" id="TSReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripsheetCollectionDATE"> Trip Sheet Collection
										Report </a>
								</h4>
							</div>
							<!-- <div id="TripsheetCollectionDATE" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="TripSheetCollectionDateReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Trip Closed User
													Name : </label>
												<div class="I-size">
													<input class="" placeholder="Advance users"
														id="subscribeDaily" type="hidden" style="width: 100%"
														name="closedBy"
														onkeypress="return Isservice_subscribeduser(event);"
														required="required" ondrop="return false;">
													<p class="help-block">Select One Or More User name</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Trip Closed
													Location: </label>
												<div class="I-size">
													<input class="" placeholder="Branch name"
														id="branchnameLocation" type="hidden" style="width: 100%"
														name="cloesdLocation"
														onkeypress="return Isservice_subscribeduser(event);"
														required="required" ondrop="return false;">
													<p class="help-block">Select One Or More User name</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Vehicle Group:<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="workOrderGroup"
														name="vehicleGroupId" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Vehicle Group</p>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Trip Closed Date
													range: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="reportrange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
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
																class="btn btn-success" onclick="return validateTripSheetCollectionReport();">
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
		<!--SRS Travels Trip Sheet Collection Report -->
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
												<caption>TripSheet Date Report -${TRIP_DATE}
													${TRIP_GROUP} ${TRIP_CLOSEDBY} ${TRIP_LOCATION}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>Vehicle</th>
														<th>TS_ID</th>
														<th>Toll Charges</th>
														<th>Driver 1 Bata</th>
														<th>Driver 2 Bata</th>
														<th>Cleaner Bata</th>
														<th>Welfare</th>
														<th>Luggage</th>
														<th>Wash &amp; Park</th>
														<th>Halting</th>
														<th>OtherExpense</th>
														<th>T.Expense</th>
														<th>T.Advance</th>
														<th>Net pay</th>
														<th>closedBy</th>
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
																<c:out value="${TripSheet.routeName}" /><br> <c:out
																	value="${TripSheet.tripOpenDate} to " /> <c:out
																	value="${TripSheet.closetripDate}" /></td>
															<td class="fit"><a
																href="<c:url value="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"><c:out
																		value="TS-${TripSheet.tripSheetNumber}" /></a></td>
															<td><c:out value="${TripSheet.collectionTollCharge}" /></td>

															<td><c:out
																	value="${TripSheet.collectionFristDriverBata} " /></td>
															<td><c:out
																	value="${TripSheet.collectionSecondDriverBata}  " /></td>
															<td><c:out
																	value="${TripSheet.collectionCleanerBata}  " /></td>

															<td><c:out
																	value="${TripSheet.collectionWelfareExpense} " /></td>
															<td><c:out value="${TripSheet.collectionLuggage}  " /></td>
															<td><c:out value="${TripSheet.collectionWashpark}  " /></td>
															<td><c:out
																	value="${TripSheet.collectionHaltingBata} " /></td>
															<td><c:out
																	value="${TripSheet.collectionOtherExpense}  " /></td>
															<td><c:out value="${TripSheet.tripTotalexpense}  " /></td>
															<td><c:out value="${TripSheet.tripTotalAdvance}  " /></td>
															<td><c:out value="${TripSheet.closeTripAmount}  " /></td>
															<td><c:out value="${TripSheet.closedBy}  " /></td>

														</tr>

													</c:forEach>

													<tr data-object-id="">
														<td colspan="3">Total:</td>
														<td><c:out value="${TotalTollCharge}" /></td>
														<td><c:out value="${TotalDriverOne}" /></td>

														<td><c:out value="${TotalDrvierTwo} " /></td>
														<td><c:out value="${TotalCleaner}  " /></td>
														<td><c:out value="${TotalWelfare}  " /></td>

														<td><c:out value="${TotalLuggage} " /></td>
														<td><c:out value="${TotalWash}  " /></td>
														<td><c:out value="${TotalHalting} " /></td>
														<td><c:out value="${TotalOtherExpense}  " /></td>
														<td><c:out value="${TotalExpense}  " /></td>
														<td><c:out value="${TotalAdvance}  " /></td>
														<td><c:out value="${Totalnetpay}  " /></td>
														<td></td>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateTripSheetCollectionReport.js"/>"></script>
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