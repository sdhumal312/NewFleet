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
					/ <a href="<c:url value="/SER.in"/>">Service Entries Report</a> / <span>Service Entries Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vehicle Wise Service Entries Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>					
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	
	
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		<!--SRS Travels Vehicle Wise Service Entries Report By Dev Yogi Start-->
							<sec:authorize access="hasAuthority('VIEW_VEL_SE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#SETwo"> Vehicle Wise Service Entries Report </a>
								</h4>
							</div>
							<!-- <div id="SETwo" class="panel-collapse collapse"> -->
								<div class="box-body">
									<form action="VehicleServiceEntriesReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="SEVehicle" name="VEHICLE_ID"
														style="width: 100%;" required="required"
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
														<input type="text" id="SEVehdateRange" class="form-text"
															name="SERVICE_DATERANGE" required="required"
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
																class="btn btn-success" onclick="return validateVehicleWiseServiceEntriesReport();">
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
		
		<!--SRS Travels Vehicle Wise Service Entries Report By Dev Yogi End-->

		<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty ServiceEntries}">
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
												<caption>Service Entries Report</caption>
												<thead>
													<tr>
														<th>Id</th>
														<th>Vehicle</th>
														<th>Vendor</th>
														<th>Invoice Number</th>
														<th>Job Number</th>
														<th>Cost</th>
														<th>RoundCost</th>
														<th>Payment</th>
														<th>Paid By</th>
														<th>Last Modified By</th>
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${ServiceEntries}" var="ServiceEntries">
														<tr data-object-id="" class="ng-scope">
															<td class="fit"><a target="_blank"
																href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"><c:out
																		value="S-${ServiceEntries.serviceEntries_Number}" /></a></td>
															<td><c:out
																	value="${ServiceEntries.vehicle_registration}" /> - <c:out
																	value="${ServiceEntries.vehicle_Group}" /></td>
															<td><c:out value="${ServiceEntries.vendor_name}" /><br>
																<c:out value="${ServiceEntries.vendor_location}" /></td>
															<td><c:out value="${ServiceEntries.invoiceNumber}" /></td>
															<td><c:out value="${ServiceEntries.jobNumber}" /></td>
															<td><c:out
																	value="${ServiceEntries.totalservice_cost}" /></td>
															<td><c:out
																	value="${ServiceEntries.totalserviceROUND_cost}" /></td>
															<td><c:out
																	value="${ServiceEntries.service_paymentType}" /></td>
															<td><c:out value="${ServiceEntries.service_paidby}" /></td>
														<td ><c:out
															value="${ServiceEntries.lastModifiedBy}" /></td>	

														</tr>
													</c:forEach>
													<tr class="vehicle_repair_total">
														<th class="text-right" colspan="6"><b> Total
																Amount :</b></th>
														<td colspan="3">${TotalAmount}</td>
													</tr>
												</tbody>
											</table>
										</div>
									</c:if>
									<c:if test="${NotFound}">
									<script>								
										$(".invoice").addClass("hide");
										setTimeout(function() {validateReport();}, 500);
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateVehicleWiseServiceEntriesReport.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>