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
					/ <a href="<c:url value="/DR.in"/>">Driver Report</a> / <span>Driver Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Driver Details Report')">
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
		<sec:authorize access="hasAuthority('VIEW_DR_DE_REPORT')">
					<div class="tab-pane" id="driverReport">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#driverOne"> Driver details Report </a>
								</h4>
							</div>
							
								<div class="box-body">
									<form action="DriverDetailsReport" method="post">
										<div class="form-horizontal ">
											<!-- Driver Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="driverGroup" name="vehicleGroupId"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" value="0" />
												</div>
											</div>
											<!-- Driver Job Title -->
											<div class="row1">
												<label class="L-size control-label"> Job Title :</label>
												<div class="I-size">
													<input type="hidden" id="DriJob_ID" name="driJobId"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Job Type" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Driver Status :</label>
												<div class="I-size">
													<select class=" select2" name="driverStatusId"
														style="width: 100%" id="driverStatusId">
														<option value="0"></option>
														<option value="1">ACTIVE</option>
														<option value="2">INACTIVE</option>
														<option value="3">TRIPROUTE</option>
														<option value="6">SUSPEND</option>
														<option value="7">HOLD</option>
														<option value="8">RESIGN</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="string required L-size control-label">Languages
													: </label>
												<div class="I-size">
													<select id="DriverLanguage" name="driver_languages"
														multiple="multiple" data-placeholder="Select a languages"
														style="width: 100%;">
														<option>English</option>
														<option>Hindi</option>
														<option>Tamil</option>
														<option>Kannada</option>
														<option>Telugu</option>
														<option>Marathi</option>
														<option>Malayalam</option>
														<option>Odia</option>
														<option>Gujarati</option>
														<option>Punjabi</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Training /
													Specialization:</label>
												<div class="I-size">
													<input type="hidden" id="SelectDriverTraining"
														name="driver_trainings" style="width: 100%;"
														placeholder="Please Enter 2 or more Training Name" />

												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success" onclick="return validateDriverDetailsReport();">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
						
						</div>
					</div>
				</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty drivers}">
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
												<caption>Driver details Report</caption>
												<thead>
													<tr>
														<th>Id</th>
														<th>EMP-NO</th>
														<th>Name</th>
														<th>DL-No</th>
														<th>Badge-No</th>
														<th>Phone</th>
														<th>Group</th>
														<th>Job</th>
														<th>Status</th>
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${drivers}" var="driver">
														<tr data-object-id="" class="ng-scope">
															<td><a target="_blank"
																href="showDriver.in?driver_id=${driver.driver_id}"
																data-toggle="tip" data-original-title="Click driver"><c:out
																		value="D-${driver.driver_id}" /> </a></td>
															<td><c:out value="${driver.driver_empnumber}" /></td>
															<td><c:out value="${driver.driver_firstname}   " />
																<c:out value="${driver.driver_Lastname} " />
															<c:out value="${driver.driver_fathername} " />  
															
																</td>
															<td><c:out value="${driver.driver_dlnumber}" /></td>
															<td><c:out value="${driver.driver_badgenumber}" /></td>
															<td><span class="fa fa-phone" aria-hidden="true"
																data-toggle="tipDown" data-original-title="Phone Number">
															</span> <c:out value="${driver.driver_mobnumber}" />
															<td><c:out value="${driver.driver_group}" /></td>
															<td><c:out value="${driver.driver_jobtitle}" /></td>
															<td><c:out value="${driver.driver_active}" /></td>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDriverDetailsReport.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
			
			$('#driverStatusId').select2();
		});
	</script>
</div>