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
					/ <a href="<c:url value="/DR.in"/>">Driver Report</a> / <span>Driver
						Penalty/ Advance Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Depot Wise Penalty Advance Report')">
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
		
				<sec:authorize access="hasAuthority('VIEW_AD_PE_REPORT')">
					<div class="panel box box-danger">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapsedriverAdvacne"> Advance / Penalty Depot Wise
									Report </a>
							</h4>
						</div>
						<!-- <div id="collapsedriverAdvacne" class="panel-collapse collapse"> -->
							<div class="box-body">
								<form action="DepotAdvancePenaltyReport" method="post">
									<div class="form-horizontal ">
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="rangeFuelMileage" class="form-text"
														name="TRIP_DATE_RANGE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Depot : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="SelectFuelGroup6"
													name="vehicleGroupId" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
												<p class="help-block">Select One Depot</p>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<select name="ADVANCE_TYPE_ID" class="form-text">
													<option value="-1">ALL</option>
													<option value="1">ADVANCE</option>
													<option value="2">PENALTY</option>
												</select>
											</div>
										</div>

										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>
												<div class="I-size">
													<div class="pull-left">
														<button type="submit" id="submit1"name="commit"
															class="btn btn-success" onclick="return validateAdvanceOrPenaltyDepotReport();">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>
									</div>
								</form>
							</div>
				<!-- 		</div> -->
					</div>
				</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty DriverAdvanvce}">
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
												<caption>Driver Advance/Penalty Report
													-${SearchDate}</caption>
												<thead>
													<tr>
														<th class="fit">No</th>
														<th>Type</th>
														<th>Advance NAME</th>
														<th>Advance Date</th>
														<th>Amount</th>
														<th>Balance Amount</th>
														<th>Status</th>
														<th>TripSheet</th>
													</tr>
												</thead>
												<tbody>
													<%
													Integer hitsCount = 1;
												%>


													<c:forEach items="${DriverAdvanvce}" var="DriverAdvanvce">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:choose>
																	<c:when
																		test="${DriverAdvanvce.ADVANCE_NAME == 'ADVANCE'}">
																		<span class="label label-default label-success">
																			<c:out value="${DriverAdvanvce.ADVANCE_NAME}" />
																		</span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-default label-warning">
																			<c:out value="${DriverAdvanvce.ADVANCE_NAME}" />
																		</span>
																	</c:otherwise>
																</c:choose></td>
															<td><c:out
																	value="${DriverAdvanvce.driver_empnumber} - " /> <c:out
																	value="${DriverAdvanvce.driver_firstname} " /> <c:out
																	value="${DriverAdvanvce.driver_Lastname}" /></td>
															<td><c:out value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
															<td><c:out value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
															<td><c:out value="${DriverAdvanvce.ADVANCE_BALANCE}" /></td>
															<td><c:out value="${DriverAdvanvce.ADVANCE_STATUS}" /></td>
															<td><c:choose>
																	<c:when test="${DriverAdvanvce.TRIPDAILYID != 0}">
																		<c:out value="TS-${DriverAdvanvce.TRIPDAILYID}  " />
																		<c:out value="${DriverAdvanvce.TRIP_ROUTE_NAME}" />
																	</c:when>
																</c:choose></td>

														</tr>
													</c:forEach>
													<tr>
														<td colspan="8"></td>
													</tr>
													<tr>
														<td colspan="4"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL
															:</td>
														<td
															style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																value="${TotalAmount}" /></td>
														<td
															style="text-align: center; font-size: 15px; font-weight: bold;"><c:out
																value="${TotalBalance}" /></td>
														<td colspan="2"></td>
													</tr>
												</tbody>
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/validateReports.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/validateReport1.js"/>"></script>	
		
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateAdvanceOrPenaltyDepotReport.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>