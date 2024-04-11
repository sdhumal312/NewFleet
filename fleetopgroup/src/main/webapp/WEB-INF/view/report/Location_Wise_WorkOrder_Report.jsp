<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
					/ <a href="<c:url value="/WOR.in"/>">WorkOrder Report</a> <span>WorkOrder Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Location Wise Workorder Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>					
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<sec:authorize access="hasAuthority('VIEW_LO_WO_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#workOrderOne"> Location Wise WorkOrder Report </a>
							</h4>
						</div>
						
						
							<div class="box-body">
								<form action="LocationWorkOrderReport" method="post">
									<div class="form-horizontal ">
										
										<div class="row1">
											<label class="L-size control-label">Warehouse
												Location: <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="workOrderLocation"
													name="WORKORDER_LOCATION" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more location name" />
												<p class="help-block">Select Warehouse location</p>
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
													<input type="text" id="LocWorkOrder" class="form-text"
														name="WORKORDER_DATERANGE" required="required"
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
															class="btn btn-success" onclick="return validateLocationWiseWorkOrderReport();">
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
					
					</div>
				</sec:authorize>

	
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty WorkOrder}">
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
													<c:if test="${configuration.multipleApproval}">
														<td colspan="2"><button class="btn btn-success" id="approveWorkorders" >Approve WorkOrders</button>
														</td>
													</c:if>
												</tr>
											</tbody>
										</table>
										<div class="row invoice-info">
											<table id="advanceTable" class="table table-hover table-bordered table-striped">
												<caption>WorkOrder Report</caption>
												<thead>
													<tr>
													<c:if test="${configuration.multipleApproval}">
														<th class="fit"><input name="selectAll" value="" id="selectAll" type="checkbox" /></th>
													</c:if>	
														<th>Id</th>
														<th>Vehicle</th>
														<th>Start</th>
														<th>Due</th>
														<th>Assigned to</th>
														<th>Amount</th>
														<th>Location</th>
													</tr>
												</thead>
												<tbody>

													<c:forEach items="${WorkOrder}" var="WorkOrder">
														<tr data-object-id="" class="ng-scope">
														<c:if test="${configuration.multipleApproval}">
																<c:choose>
																	<c:when test="${WorkOrder.approvalStatusId == 0}">
																		<td class="fit">
																			<input name="selectWorkOrder" value="${WorkOrder.workorders_id}" id="example" type="checkbox" />
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td class="fit">
																			<label>APPROVED</label>
																		</td>
																	</c:otherwise>
																</c:choose>
															</c:if>	
															<td><a target="_blank"
																href="showWorkOrder?woId=${WorkOrder.workorders_id}"
																data-toggle="tip"
																data-original-title="Click work Order Info"> <c:out
																		value="WO-${WorkOrder.workorders_Number}" />
															</a></td>

															<td><c:out value="${WorkOrder.vehicle_registration}" />
															</td>
															<td><c:out value="${WorkOrder.start_date}" /></td>
															<td><c:out value="${WorkOrder.due_date}" /></td>

															<td><c:out value="${WorkOrder.assignee}" /></td>
															<td><fmt:formatNumber maxFractionDigits="2" value="${WorkOrder.totalworkorder_cost}" /></td>
															<td><c:out value="${WorkOrder.workorders_location}" /></td>
														</tr>
													</c:forEach>
													<c:if test="${configuration.multipleApproval}">
														<tr class="vehicle_repair_total">
															<th class="text-right" colspan="6"><b> Total Amount :</b></th>
															<td colspan="1"><fmt:formatNumber maxFractionDigits="2" value="${TotalAmount}"/> </td>
														</tr>
													</c:if>	
													<c:if test="${!configuration.multipleApproval}">
														<tr class="vehicle_repair_total">
															<th class="text-right" colspan="5"><b> Total Amount :</b></th>
															<td colspan="2"> <fmt:formatNumber maxFractionDigits="2" value="${TotalAmount}"/></td>
														</tr>
													</c:if>
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
</div>
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
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>	
	<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateLocationWiseWorkOrderReport.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>