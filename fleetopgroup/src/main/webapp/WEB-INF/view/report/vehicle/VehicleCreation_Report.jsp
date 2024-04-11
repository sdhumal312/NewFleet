<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					<sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
						/ <a href="<c:url value="/ImportantReport"/>">Important Report</a>
					</sec:authorize>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Vehicle
						Creation Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
				
			</div>
		</div>
	</section>
		<section class="invoice" id="div_print">
					<div class="row invoice-info">
						<div class="col-sm-12 col-md-12 col-xs-12"
							style="padding-right: 80px;">
							<div class="table-responsive">
								<c:if test="${!empty CountDetails}">
									<table id="advanceTable" style="width: 100%"
										class="table table-hover table-bordered table-striped">
										
										<caption>Vehicle Creation Report</caption>
										<thead>
											<tr>
												<th>ID</th>
												<th>Vehicle Registration Number</th>
												<th>Status</th>
												<th>Created Date</th>
												<th>Last Updated Date</th>
											</tr>
										</thead>
										<tbody>
				
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${CountDetails}" var="CountDetails">
												<!--  display workOrder details Below  in one vehicle-->
												<tr>
													<td>
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>
													<td>
														<a href="showVehicle?vid=${CountDetails.vid}"  target="_blank"><c:out value="${CountDetails.vehicle_registration}" /></a>
													</td>
													<td>
														<c:out value="${CountDetails.vehicle_Status}" />
													</td>
													<td>
														<c:out value="${CountDetails.created}" />
													</td>
													<td>
														<c:out value="${CountDetails.lastupdated}" />
													</td>
													
												</tr>
											</c:forEach>
										</tbody>
									</table>
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

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/vehicleCommentReport.js" />"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/VehicleCreationReport.js" />"></script>	
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
	
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
</div>