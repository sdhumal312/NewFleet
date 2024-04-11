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
					/ <a href="<c:url value="/SRR.in"/>">Service Reminder Report</a> / <span>Service Reminder Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Overdue SR Report')">
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
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_OV_SR_REPORT')">
			<div class="panel box box-danger">
				<div class="box-header with-border">
					<h4 class="box-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#SROne">Overdue
							Service Reminder Report </a>
					</h4>
				</div>
				<!-- <div id="SROne" class="panel-collapse collapse"> -->
				<div class="box-body">
					<form action="OverdueServiceReport" method="post">
						<div class="form-horizontal ">

							<div class="row1">
								<label class="L-size control-label">Vehicle Name :</label>
								<div class="I-size">
									<div class="col-md-9">
										<input type="hidden" id="OverOverSRVehicle" name="vid"
											style="width: 100%;"
											placeholder="Please Enter 2 or more Vehicle Name" />
									</div>
								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label">Service Jobs :</label>
								<div class="I-size">
									<div class="col-md-9">
										<input type="hidden" id="OverOverSRJOB" name="serviceTypeId"
											style="width: 100%;"
											placeholder="Please Enter 2 or more Job Name" />
										<p class="help-block">Select an existing Service Jobs</p>
									</div>
								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label">Service Sub Jobs : </label>
								<div class="I-size">

									<div class="col-md-9">
										<select style="width: 100%;" name="serviceSubTypeId"
											id='OverOverSRSubJOB'>

										</select>
										<p class="help-block">Select an existing Service Sub Jobs</p>
									</div>
								</div>
							</div>

							<!-- Date range -->
							<div class="row1">
								<label class="L-size control-label">Date <abbr
									title="required">*</abbr>
								</label>
								<div class="I-size">
									<div class="input-group input-append date" id="SROverdueDate">
										<input type="text" class="form-text" name="SERVICE_DATE"
											placeholder="dd-mm-yyyy" required="required"
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
											<button type="submit" name="commit" class="btn btn-success">
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
		<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty Service}">
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
												<caption>Service Reminder Report</caption>
												<thead>
													<tr>
														<th>Id</th>
														<th>Vehicle</th>
														<th>Service Task &amp; Schedule</th>
														<th>Next Due</th>
														<th>Subscribers</th>
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${Service}" var="Service">

														<tr data-object-id="" class="ng-scope">
															<td class="fit"><a target="_blank"
																href="<c:url value="/ShowService.in?service_id=${Service.service_id}"/>"><c:out
																		value="S-${Service.service_Number}" /></a></td>
															<td><c:out value="${Service.vehicle_registration}" />

																<br> <c:out value="${Service.vehicle_Group}" /></td>
															<td><c:choose>
																	<c:when
																		test="${Service.diffenceThrsholdOdometer == 'Due Soon'}">
																		<span class="label label-default label-warning"
																			style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>
																	</c:when>
																	<c:when
																		test="${Service.diffenceThrsholdOdometer == 'Overdue'}">
																		<span class="label label-default label-danger"
																			style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>

																	</c:when>
																	<c:otherwise>
																		<span class="label label-default label-warning"
																			style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>

																	</c:otherwise>
																</c:choose> <b style="font-size: 15px;"><a><c:out
																			value="${Service.service_type} - " /> <c:out
																			value="${Service.service_subtype}" /></a></b> <span>every
																	<c:out value="${Service.time_interval} " /> <c:out
																		value="${Service.time_intervalperiod} " /> or <c:out
																		value="${Service.meter_interval}  Km" />
															</span></td>


															<td><i class="fa fa-calendar-check-o"></i> <span><c:out
																		value="${Service.diffrent_time_days}" /></span> <br> <i
																class="fa fa-road"></i> <span><c:out
																		value="${Service.diffrent_meter_oddmeter}" /></span>
															<td><span data-toggle="tip"
																data-original-title="${Service.service_subscribeduser}"><c:out
																		value="${Service.service_subscribeduser_name}" /></span></td>
														</tr>
													</c:forEach>
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