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
					/ <a href="<c:url value="/TR.in"/>">Tyre Report</a> / <span>Tyre Stock Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default" onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Tyre Retread Stock Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
						<sec:authorize access="hasAuthority('VIEW_TY_RE_REPORT')">
				<!-- Start Type Stock Tyre Range -->
				<div class="panel box box-warning">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseTyreStatus"> Tyre Retread Report </a>
						</h4>
					</div>
					<!-- <div id="collapseTyreStatus" class="panel-collapse collapse"> -->
						<div class="box-body">
							<form action="TyreRetreadStockReport.in" method="post">
								<div class="form-horizontal ">
									<div class="row1">
										<label class="L-size control-label">Type Retread
											Status : </label>
										<div class="I-size">
											<select class="select2" name="TYRE_STATUS"
												id="TyreRetreadStatus" style="width: 100%;">
												<option value="0">-- Please select --></option>
												<option value="1">AVAILABLE</option>
												<option value="4">SENT-RETREAD</option>
											</select>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Retread : </label>
										<div class="I-size">
											<select class="select2" name="RETREAD_STATUS"
												id="TyreRetread" style="width: 100%;">
												<option value="0">-- Please select --></option>
												<option value="2">RECEIVED TYRE</option>
												<option value="3">REJECT TYRE</option>
											</select>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Tyre Vendor : </label>
										<div class="I-size">
											<input type="hidden" id="TyreRetreadVendor"
												name="TR_VENDOR_ID" style="width: 100%;"
												placeholder="Please Select Vendor Name" /> <label
												class="error" id="errorVendorSelect"> </label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Warehouse location
											: </label>
										<div class="I-size">
											<input type="hidden" name="WAREHOUSE_LOCATION_ID"
												id="warehouselocation" style="width: 100%;"
												placeholder="Please Enter 2 or more location Name" />
										</div>
									</div>
									<!-- Date range -->
									<div class="row1">
										<label class="L-size control-label">Tyre Retread Date
											range: <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="TyreRetreadRange" class="form-text"
													name="TyreRetread_daterange" required="required"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
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



		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div id="div_print">

				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-sm-12 col-md-12 col-xs-12"
							style="padding-right: 80px;">
							<div class="table-responsive">
								<c:if test="${!empty Tyre}">
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
										<table id="advanceTable" style="width: 95%"
										class="table table-hover table-bordered table-striped">
											<caption>Tyre Retread Stock Report ${SearchFrom}</caption>
											<thead>
												<tr>
													<th>ID</th>
													<th>Tyre NO</th>
													<th>Manufacturer</th>
													<th>Model</th>
													<th>Tyre Size</th>
													<th>Location</th>
													<th>Status</th>
													<th>usage</th>

												</tr>
											</thead>
											<tbody>

												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${Tyre}" var="Tyre">
													<!--  display workOrder details Below  in one vehicle-->
													<tr>
														<td>
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<td><a target="_blank"
															href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>">
																<c:choose>
																	<c:when test="${Tyre.TYRE_RETREAD_COUNT == 0}">
																		<span class="label label-pill label-success"><c:out
																				value="NT" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																				value="${Tyre.TYRE_RETREAD_COUNT}-RT" /></span>
																	</c:otherwise>
																</c:choose> <c:out value="${Tyre.TYRE_NUMBER}" />
														</a></td>
														<td class="fit ar"><a target="_blank"
															href="<c:url value="/showTyreInfo.in?Id=${Tyre.TYRE_ID}"/>"
															data-toggle="tip"
															data-original-title="Click Inventory INFO"><c:out
																	value="${Tyre.TYRE_MANUFACTURER}" /> </a></td>
														<td class="fit ar"><c:out value="${Tyre.TYRE_MODEL}" /></td>
														<td class="fit ar"><c:out value="${Tyre.TYRE_SIZE}" /></td>
														<td class="fit ar"><c:out
																value="${Tyre.WAREHOUSE_LOCATION}" /></td>
														<td class="fit ar"><c:choose>
																<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'AVAILABLE'}">
																	<span class="label label-pill label-success"><c:out
																			value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																</c:when>
																<c:when test="${Tyre.TYRE_ASSIGN_STATUS == 'SCRAPED'}">
																	<span class="label label-pill label-danger"><c:out
																			value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-warning"><c:out
																			value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																</c:otherwise>
															</c:choose></td>
														<td class="fit ar"><c:out value="${Tyre.TYRE_USEAGE}" /></td>
													</tr>
												</c:forEach>
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
		</sec:authorize>

	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>

</div>