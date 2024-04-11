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
					/ <a href="<c:url value="/PR.in"/>">Part Report</a> / <span>Part
						Stock Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Inventory Stock Report')">
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
		
						<sec:authorize access="hasAuthority('VIEW_DA_IN_ST_REPORT')">
					<div class="panel box box-success">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#PartDateRange">Date Wise Inventory Stock Report</a>
							</h4>
						</div>
						<!-- <div id="PartDateRange" class="panel-collapse collapse"> -->
							<div class="box-body">
								<div class="form-horizontal ">
									<!-- Show Vehicle Search Fuel Range -->
									<form action="DateWiseInventoryStockReport" method="post">

										<div class="row1">
											<label class="L-size control-label">Warehouse
												location : </label>
											<div class="I-size">
												<input type="hidden" name="location"
													id="Partwarehouselocation3" style="width: 100%;"
													placeholder="All" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Part/Tyre Vendor
												: </label>
											<div class="I-size">
												<input type="hidden" id="PurchaseVendor" name="vendor_id"
													value="-1" style="width: 100%;" required="required"
													placeholder="All" /> <label class="error"
													id="errorVendorSelect"> </label>
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
													<input type="text" id="reportrange" class="form-text"
														name="PART_DATERANGE" required="required"
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
															class="btn btn-success">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>
									</form>
								</div>
							</div>
						<!-- </div> -->
					</div>
				</sec:authorize>



		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div id="div_print">

				<div id="div_print">

					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
									<c:if test="${!empty Inventory}">
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
												<caption>Part Stock Report ${SEARCHDATE}</caption>
												<thead>
													<tr>
														<th>ID</th>
														<th>Part NO_Name</th>
														<th>Category</th>
														<th>Vendor</th>
														<th>Invoice Date</th>
														<th>History Qty</th>
														<th>Current Qty</th>
														<th>Location</th>
													</tr>
												</thead>
												<tbody>

													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${Inventory}" var="Inventory">
														<tr>
															<td>
																<%
																	out.println(hitsCount);
																hitsCount += 1;
																%>
															</td>
															<td><a target="_blank"
																href="<c:url value="/showLocationInventory.in?inventory_location_id=${Inventory.inventory_location_id}"/>">
																	<c:out value="${Inventory.partnumber}  " /> <c:out
																		value="${Inventory.partname}" />
															</a></td>
															<td><c:out value="${Inventory.category}" /></td>
															<td><c:out value="${Inventory.vendor_name}" /></td>
															<td><c:out value="${Inventory.invoice_date}" /></td>
															<td><fmt:formatNumber maxFractionDigits="2" value="${Inventory.history_quantity}" /></td>
															<td><fmt:formatNumber maxFractionDigits="2" value="${Inventory.quantity}" /></td>
															<td><c:out value="${Inventory.location}" /></td>

														</tr>
													</c:forEach>

													<tr class="vehicle_repair_total">
														<th class="text-right" colspan="5"><b> Total
																History Quantity ${SEARCHDATE} :</b></th>
														<td><fmt:formatNumber maxFractionDigits="2" value="${TotalHistoryQuantity}" /> </td>
														<td><fmt:formatNumber maxFractionDigits="2" value="${TotalQuantity}" /> </td>
														<td></td>
													</tr>
													<tr class="vehicle_repair_total">
														<th class="text-right" colspan="5"><b> Total
																History Quantity Amount :</b></th>
														<td>${TotalValue}</td>
														<td></td>
														<td></td>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>