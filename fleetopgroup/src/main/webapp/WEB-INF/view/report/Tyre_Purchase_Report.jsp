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
					/  <a href="<c:url value="/TR.in"/>">Tyre Report</a> / <span>Tyre Purchase Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default" onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Tyre Purchase Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		
					<sec:authorize access="hasAuthority('VIEW_TY_PO_REPORT')">
				<!-- Start Show Group Search Tyre Range -->
				<div class="panel box box-warning">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseTyrePurchase"> Tyre Purchase Report </a>
						</h4>
					</div>
					<!-- <div id="collapseTyrePurchase" class="panel-collapse collapse"> -->
						<div class="box-body">
							<form action="TyrePurchaseReport" method="post">
								<div class="form-horizontal ">
									
									<div class="row1">
											<label class="L-size control-label">Status of Tyre												
											</label>
											<div class="I-size">
												<select class="form-text" name="STATUS_OF_TYRE"
													id="STATUS_OF_TYRE" required="required">
													<option value="0">New</option>
													<option value="1">Retread</option>
												</select>
											</div>
										</div>
								
								
								
									<div class="row1">
										<label class="L-size control-label">Tyre Model : </label>
										<div class="I-size">
											<input type="text" id="Reporttyremodel" name="TYRE_MODEL_ID"
												style="width: 100%;" placeholder="Please select Tyre Model" />

										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Tyre Size : </label>
										<div class="I-size">
											<input type="text" id="ReporttyreSize" name="TYRE_SIZE_ID"
												style="width: 100%;" placeholder="Please select Tyre Size" />
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
										<label class="L-size control-label">Tyre Date range: <abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<div class="input-group">
												<div class="input-group-addon">
													<i class="fa fa-calendar"></i>
												</div>
												<input type="text" id="rangeTyrePurchase" class="form-text"
													name="TyrePurchase_daterange" required="required"
													style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
											</div>
										</div>
									</div>									
									<div class="row1">
											<label class="L-size control-label">Vendor :</label>
											<div class="I-size">
												<input type="text" id="TyrePurchaseVendor" name="Vendor_id"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
													<label
												class="error" id="errorVendorSelect"> </label>
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
				<!-- end Show Group Search Tyre Range -->
				</sec:authorize>





		<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
			<div id="div_print">

				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-sm-12 col-md-12 col-xs-12"
							style="padding-right: 80px;">
							<div class="table-responsive">
								<c:if test="${!empty InventoryTyreInvoice}">
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
											<caption>Tyre Purchase Report</caption>
											<thead>
												<tr>
													<th>ID</th>									
													<th>PayType</th>
													<th>PayNO</th>
													<th>Invoice No</th>
													<th>Invoice Date</th>
													<th>Details</th>
													<th>Tyre Size</th>
													<th>Quantity</th>
													<th>Total</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach items="${InventoryTyreInvoice}"
													var="InventoryTyreInvoice">
													<!--  display workOrder details Below  in one vehicle-->
													<tr>
														<td><a target="_blank"
															href="showTyreInventory.in?Id=${InventoryTyreInvoice.ITYRE_ID}"
															data-toggle="tip"
															data-original-title="Click Tyre Invoce Info">TI-<c:out
																	value="${InventoryTyreInvoice.ITYRE_NUMBER}" /></a></td>
																													
														<td><c:choose>
																<c:when
																	test="${InventoryTyreInvoice.PAYMENT_TYPE =='CREDIT' }">
																	<span class="no-wrap"> <span class="text-red"><c:out
																				value="${InventoryTyreInvoice.PAYMENT_TYPE}" /></span>
																	</span>
																</c:when>
																<c:otherwise>
																	<span class="no-wrap"> <span class="text-green"><c:out
																				value="${InventoryTyreInvoice.PAYMENT_TYPE}" /></span>
																		Completed
																	</span>
																</c:otherwise>
															</c:choose></td>
														<td><c:out
																value="${InventoryTyreInvoice.PAYMENT_NUMBER}" /></td>

														<td><c:out
																value="${InventoryTyreInvoice.INVOICE_NUMBER}" /></td>
														<td><c:out
																value="${InventoryTyreInvoice.INVOICE_DATE}" /></td>
														<td colspan="4">
															<div class="media">
																<div class="media-body">
																	<p style="font-size: 12px; color: black;">
																		Vendor :
																		<c:out value="${InventoryTyreInvoice.VENDOR_NAME}  " />
																		<c:out value="${InventoryTyreInvoice.VENDOR_LOCATION}" />
																	</p>
																</div>
															</div>
															<table>
																<%
																	Integer hitsCount = 1;
																%>
																<tbody style="border-top: 0px;">
																	<!--  display TyreAmount Task  details Below  in match with work Order_id-->
																	<c:if test="${!empty InventoryTyreAmount}">
																		<c:forEach items="${InventoryTyreAmount}"
																			var="InventoryTyreAmount">
																			<c:if
																				test="${InventoryTyreAmount.ITYRE_ID == InventoryTyreInvoice.ITYRE_ID}">

																				<tr>

																					<td class="work-orders-by-task-column" colspan="3">
																						<%
																							out.println(hitsCount);
																													hitsCount += 1;
																						%>. <c:out
																							value="${InventoryTyreAmount.TYRE_MANUFACTURER}" />
																						<c:out value="${InventoryTyreAmount.TYRE_MODEL}" /><br>
																						<table>
																							<tbody style="border-top: 0px;">
																								<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																								<c:if test="${!empty Tyre}">
																									<%
																										Integer tyreCount = 1;
																									%>
																									<c:forEach items="${Tyre}" var="Tyre">
																										<c:if
																											test="${Tyre.ITYRE_AMOUNT_ID == InventoryTyreAmount.ITYRE_AMD_ID}">
																											<tr data-object-id="" class="ng-scope">
																												<td>
																													<%
																														out.println(tyreCount);
																																							tyreCount += 1;
																													%> <c:out value="  ${Tyre.TYRE_NUMBER}" />
																												</td>
																												<td class="work-orderPart-by-repir-Cost"><i
																													class="fa fa-inr"></i> ${Tyre.TYRE_AMOUNT}</td>
																											</tr>
																										</c:if>
																									</c:forEach>
																								</c:if>
																							</tbody>
																						</table>
																					</td>

																					<td><c:out
																							value="${InventoryTyreAmount.TYRE_MANUFACTURER} " /><br>
																						<c:out value=" ${InventoryTyreAmount.TYRE_MODEL} " /><br>
																						<c:out value=" ${InventoryTyreAmount.TYRE_SIZE}"></c:out></td>
																					<td><c:out
																							value="${InventoryTyreAmount.TYRE_QUANTITY}   "></c:out></td>
																					<td><i class="fa fa-inr" data-toggle="tip"
																						data-original-title="Total_cost"></i> <c:out
																							value="${InventoryTyreAmount.TOTAL_AMOUNT}"></c:out></td>
																				</tr>
																			</c:if>
																		</c:forEach>
																	</c:if>

																</tbody>
															</table>
														</td>

													</tr>
													<tr class="workorder_repair_totals">
														<th class="text-right" colspan="8"><b>Total
																Invoice Amount :</b></th>

														<td><i class="fa fa-inr" data-toggle="tip"
															data-original-title="Total_Invoice_cost"></i>
															${InventoryTyreInvoice.INVOICE_AMOUNT}</td>
													</tr>
												</c:forEach>
												<!-- close work orders for -->
												<%-- </c:if> --%>
												<!-- close work orders if -->
											</tbody>
											<tfoot>
												<tr class="workorder_repair_search_totals">
													<th class="text-right" colspan="8"><b> Total
															Amount :</b></th>

													<td><i class="fa fa-inr"></i> ${TotalTyreAmount}</td>
												</tr>
											</tfoot>
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