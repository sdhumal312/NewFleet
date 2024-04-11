<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
					/<a href="<c:url value="/AR.in"/>">Driver Report</a> / <span>Driver
						Local Halt Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Driver Local Halt Report')">
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
		<sec:authorize access="hasAuthority('VIEW_DR_LO_HA_REPORT')">
			<div class="panel box box-danger">
				<div class="box-header with-border">
					<h4 class="box-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseHalt"> Driver Local Halt Report </a>
					</h4>
				</div>
				<!-- <div id="collapseHalt" class="panel-collapse collapse"> -->
				<div class="box-body">
					<form action="<c:url value="/DriverlocalHaltReport"/>"
						method="post">
						<div class="form-horizontal ">
							<div class="row1">
								<label class="L-size control-label">Driver Name :<abbr
									title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" id="DriverAdList2" name="DRIVER_ID"
										style="width: 100%;" value="0"
										placeholder="Please Enter 3 or more Driver Name, No" />
									<label id="errorDriver1" class="error"></label>

								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label"> Group :<abbr
									title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" id="TCGroupWise" name="HALT_GROUP_ID"
										style="width: 100%;" value="0"
										placeholder="Please Enter 2 or more Group Name" />
								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label"> Job Title :</label>
								<div class="I-size">
									<input type="hidden" id="HaltDriverJob_ID" name="HALT_TITLE"
										value="0" style="width: 100%;"
										placeholder="Please Enter 2 or more Job Type" />
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
											<button type="submit" name="commit" class="btn btn-success"
												onclick="return validateDriverLocalHaltReport();">
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
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty DriverHalt}">
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
												<caption>Driver Local Halt Report -${SearchDate}</caption>
												<thead>
													<tr>
														<th class="fit">NO</th>
														<th>Driver</th>
														<th>Date</th>
														<th>JobType</th>
														<th>Group</th>
														<th>Veh / Spare</th>
														<th>Amount</th>
														<th>Ref</th>
														<!-- <th>Halt BY / Place</th> -->
														<th>Route</th>
													</tr>
												</thead>
												<tbody>
													<%
													Integer hitsCount = 1;
												%>


													<c:forEach items="${DriverHalt}" var="DriverHalt">
														<tr data-object-id="">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td><c:out value="${DriverHalt.DRIVER_NAME}" />
															<c:out value="${DriverHalt.driverLastName}" />
															<c:out value="${DriverHalt.driverFatherName}" />
															</td>
															<td><c:out value="${DriverHalt.HALT_DATE_FROM} " />
																<c:out value=" ${DriverHalt.HALT_DATE_TO}" /></td>
															<td><c:out value="${DriverHalt.CREATEDBY}" /></td>
															<td><c:out value="${DriverHalt.TRIP_ROUTE_NAME}" /></td>
															<td><c:out value="${DriverHalt.VEHICLE_NAME}" /></td>
															<td><fmt:formatNumber maxFractionDigits="2" value="${DriverHalt.HALT_AMOUNT}" /></td>
															<td><c:out value="${DriverHalt.REFERENCE_NO}" /></td>
															<%-- <td><c:out value="${DriverHalt.HALT_PAIDBY}" /> <c:out
																	value="${DriverHalt.HALT_PLACE}" /></td> --%>
															<td><c:out value="${DriverHalt.HALT_REASON}" /></td>
														</tr>
													</c:forEach>
													<tr>
														<td colspan="10"></td>
													</tr>
													<tr>
														<td colspan="6"
															style="text-align: right; font-size: 15px; font-weight: bold;">TOTAL
															Amount :</td>
														<td colspan="4"
															style="text-align: left; font-size: 15px; font-weight: bold;"><c:out
																value="${HALT_AMOUNT}" /></td>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateDriverLocalHaltReport.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>