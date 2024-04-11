<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/NewInventory/1.in"/>">New Part Inventory</a>
					/ <span>Add Part Details</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/NewInventory/1.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_PART_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PART_INVENTORY')">
			
				<input type="hidden" id="invoiceDate" value="${PartInvoice.invoiceDateOn}">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<!-- <div class="pull-left"> -->
						<h4>Part Invoice ${PartInvoice.partInvoiceNumber}</h4>
						<h4 align="center">
							<a
								href="<c:url value="/ShowVendor.in?vendorId=${PartInvoice.vendorId}"/>"
								data-toggle="tip" data-original-title="Click Vendor Info"> <c:out
									value="${PartInvoice.vendorName}" /><br> <c:out
									value="${PartInvoice.vendorLocation}" />
							</a>
						</h4>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Invoice Date :</th>
											<td class="value"><c:out
													value="${PartInvoice.invoiceDateOn}" /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Invoice Number :</th>
											<td class="value"><c:out
													value="${PartInvoice.invoiceNumber}" /></td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
									<c:if test="${!configuration.roundupAmount}">
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value">
											<fmt:formatNumber value="${PartInvoice.invoiceAmount}" maxFractionDigits="2"/>
													 </td>
										</tr>
										</c:if>
										
										<c:if test="${configuration.roundupAmount}">
										
										<tr class="row">
											<th class="key">Roundup Amount:</th>
											<td class="value"><fmt:formatNumber value="${PartInvoice.invoiceAmount}" maxFractionDigits="2"/></td>
										</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Payment Type :</th>
											<td class="value">
											<span id="paymentTypeSpan"><c:out value="${PartInvoice.paymentType}" /></span>
											 </td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Labour Charge :</th>
											<td class="value"><c:out
													value="${PartInvoice.labourCharge}" /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
						<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										
										<c:if test="${configuration.roundupAmount}">
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value">
												<fmt:formatNumber value="${TotalPartCost}" maxFractionDigits="2"/>
											</td>
										</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${configuration.tallyIntegrationRequired}">
						<div class="col-md-3 col-sm-3 col-xs-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<table class="table ">
										<tbody>
											
											<tr class="row">
												<th class="key">Tally Company :</th>
												<td class="value"><c:out
														value="${PartInvoice.tallyCompanyName}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
					
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">WareHouse Location :</th>
											<td class="value"><c:out value="${PartInvoice.partLocation}" /></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${showSubLocation}">
						<div class="col-md-3 col-sm-3 col-xs-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<table class="table ">
										<tbody>
											<tr class="row">
												<th class="key">Sub Location :</th>
												<td class="value"><c:out
														value="${PartInvoice.subLocation}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row">
											<th class="key">Vendor Payment Status :</th>
											<td class="value">
											<c:choose>
												<c:when test="${PartInvoice.vendorPaymentStatus == 4 || PartInvoice.vendorPaymentStatus == 5 }">
													<c:out value="${PartInvoice.vendorPaymentStatusStr} PAID" />
												</c:when>
												<c:otherwise>
													<c:out value="${PartInvoice.vendorPaymentStatusStr}" />
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${PartInvoice.vendorPaymentStatus == 0}">
						<div class="col-md-3 col-sm-3 col-xs-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<table class="table ">
										<tbody>
											<tr class="row">
												<th class="key">PO Number :</th>
												<td class="value">
												<a href="PurchaseOrders_Parts.in?ID=${PartInvoice.purchaseorder_id}">
												 <c:out value="${PartInvoice.poNumber}" />
												</a>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${PartInvoice.vendorPaymentStatus != 0}">
						<div class="col-md-3 col-sm-3 col-xs-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<table class="table ">
										<tbody>
											<tr class="row">
												<th class="key">Approval Number :</th>
												<td class="value">
												<c:choose>
												<c:when test="${PartInvoice.vendorPaymentStatus == 6}">
													<a href="AddServiceApproval.in?approvalId=${PartInvoice.partApprovalId}">
													 <c:out value="${PartInvoice.approvalNumber}" />
													</a>
													</c:when>
													<c:otherwise>
													<a href="ShowApprovalPayment.in?approvalId=${PartInvoice.partApprovalId}">
													 <c:out value="${PartInvoice.approvalNumber}" />
													</a>
													</c:otherwise>
												</c:choose>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</c:if>
				</div>
				<fieldset>
				
					<div class="row">
						<c:if test="${param.UpdateSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Part Inventory Updated Successfully.
							</div>
						</c:if>
						<c:if test="${param.deleteSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Part Inventory Deleted Successfully.
							</div>
						</c:if>
						<c:if test="${param.AssignVehicle eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Tyre Assign
								<%=request.getParameter("vehicleName")%>
								Vehicle .Should be dismount First .

							</div>
						</c:if>
						<c:if test="${param.saveTyre eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This
								<%=request.getParameter("successTyre")%>
								Tyre Serial number Updated Successfully.. and Entered
								<%=request.getParameter("DuplicateTyre")%>
								Tyre Serial number duplicate .

							</div>
						</c:if>
						<c:if test="${param.PartAssigned eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Part Is Already Assigned.
							</div>
						</c:if>
						<c:if test="${param.deleteFrist eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Should be Delete First Tyre Serial Number and Amount

							</div>
						</c:if>
						<c:if test="${param.paymentDone eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Cannot Delete Payment Initiated For Invoice.
							</div>
						</c:if>
					</div>	

					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Manufacturer &amp; Model</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<c:if test="${PartInvoice.vendorPaymentStatus == 2}">
												<th class="fit">Actions</th>
											</c:if>
											
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PartAmount}">
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${PartAmount}" var="PartAmount">
												<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>

													<td><c:out value="${PartAmount.partnumber}" />
														<c:out value="${PartAmount.partname}" /></td>
													<td class="fit ar">${PartAmount.history_quantity}${PartAmount.convertToStr}</td>
													<td class="fit ar">${PartAmount.unitprice}</td>
													<c:choose>
														<c:when test="${PartAmount.discountTaxTypeId == 2}">
															<td class="fit ar">${PartAmount.discount}</td>
															<td class="fit ar">${PartAmount.tax}</td>
														</c:when>
														<c:otherwise>
															<td class="fit ar">${PartAmount.discount}%</td>
															<td class="fit ar">${PartAmount.tax}%</td>
														</c:otherwise>
													</c:choose>
													
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PartAmount.total}
													</td>
													
													<sec:authorize access="hasAuthority('DELETE_TYRE_INVENTORY')">
														<td class="fit ar">
														
														<c:if test="${(PartInvoice.paymentTypeId != 2 || PartInvoice.vendorPaymentStatus == 2) && PartInvoice.vendorPaymentStatus != 0}">
															<a href="deletePartInvoiceInventory?Id=${PartAmount.inventory_id}"
															data-toggle="tip" data-original-title="Click Remove"><font
															color="red"><i class="fa fa-times"> Remove</i></font>
															</a>
														<c:if test="${PartAmount.warranty}">
															<td>
																<a href="#" onclick="addPartWarrantyDetails('${PartAmount.inventory_id}', '${PartAmount.serialNoAddedForParts}', '${PartAmount.history_quantity}', '${PartAmount.partid}', '${PartInvoice.wareHouseLocation}');">Add Warranty/Asset Details</a>
															</td>
														</c:if>
														</c:if>	
														</td>
													</sec:authorize>
												</tr>

											</c:forEach>
										</c:if>
										<c:if test="${empty PartAmount}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Part Inventory is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>
								</table>
								<c:if test="${(PartInvoice.paymentTypeId != 2 || PartInvoice.vendorPaymentStatus == 2) && PartInvoice.vendorPaymentStatus != 0}">
									<a id ="AddMoreParts" onload="" onclick ="javascript:AddPart();" href="javascript:AddPart();">
										Add More Part
									</a>
								</c:if>
							</div>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<dl>
										<dd>Initial_Note :</dd>
										<dt>${PartInvoice.description}</dt>
									</dl>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value">
												<%-- <c:if test="${!empty TotalPartCost}"> --%>
												 <%-- <c:forEach items="${PartAmount}" var="PartAmount"> --%>
														<a><i class="fa fa-inr"></i>
														${TotalPartCost}
														</a>
												<%-- </c:forEach> --%>	
												<%-- </c:if> --%>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				
				
				<div class="modal fade" id="configureMorePart" role="dialog">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Add Part</h4>
							</div>
									
								<form id="formEditPartInventory"
								action="<c:url value="/updateAddMorePartInventoryInvoice.in"/>" method="post"
								enctype="multipart/form-data" name="formEditPartInventory"
								role="form" class="form-horizontal" novalidate="novalidate">
								
								  <div class="form-horizontal">
									<fieldset>
										<div class="box">
											<input type="hidden" id="NoOfPartsAllowedToAdd" value="50">
											<input type="hidden" id="addMorePartsAtBottom" value="true">
											<div class="box-body">
												<div class="panel panel-success">
													<div class="panel-body">
														<div class="row1" id="grpinventoryPart" class="form-group">
															<label class="L-size control-label" for="searchpart">Search
																Parts Number :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="hidden" id="searchpart" name="partid_many"
																	style="width: 100%;"
																	placeholder="Please Enter 2 or more Part Name or Part Number" />
																<span id="inventoryPartIcon" class=""></span>
																<div id="inventoryPartErrorMsg" class="text-danger"></div>
															</div>
														</div>
														<div class="row1" id="grpmanufacturer" class="form-group">
															<label class="L-size control-label" for="manufacturer">Manufacturer
																:<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="text" class="form-text" name="make_many"
																	id="manufacturer" maxlength="50"
																	onkeypress="return IsManufacturer(event);"
																	ondrop="return false;"> <span
																	id="manufacturerIcon" class=""></span>
																<div id="manufacturerErrorMsg" class="text-danger"></div>
																<label class="error" id="errorManufacturer"
																	style="display: none"> </label>
															</div>
														</div>
														<div class="row1" id="grpquantity" class="form-group">
															<label class="L-size control-label" for="quantity">Quantity
																:</label>
			
															<div class="col-md-9">
																<div class="col-md-1">
																	<input type="text" class="form-text" name="quantity_many"
																		min="0.0" id="quantity" maxlength="4"
																		placeholder="ex: 23.78" required="required"
																		data-toggle="tip"
																		data-original-title="enter Part Quantity"
																		onkeypress="return isNumberKey(event,this);"
																		onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="quantityIcon"
																		class=""></span>
																	<div id="quantityErrorMsg" class="text-danger"></div>
			
																</div>
																<div class="col-md-2">
																	<input type="text" class="form-text"
																		name="unitprice_many" id="unitprice" maxlength="7"
																		min="0.0" placeholder="Unit Cost" required="required"
																		data-toggle="tip" data-original-title="enter Unit Price"
																		onkeypress="return isNumberKey(event,this);"
																		onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="unitpriceIcon"
																		class=""></span>
																	<div id="unitpriceErrorMsg" class="text-danger"></div>
																</div>
			
																<div class="col-md-1">
																	<input type="text" class="form-text" name="discount_many"
																		min="0.0" id="discount" maxlength="5"
																		placeholder="Discount" required="required"
																		data-toggle="tip" data-original-title="enter Discount"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="discountIcon"
																		class=""></span>
																	<div id="discountErrorMsg" class="text-danger"></div>
			
																</div>
																<div class="col-md-1">
																	<input type="text" class="form-text" name="tax_many"
																		id="tax" maxlength="5" min="0.0" placeholder="GST"
																		required="required" data-toggle="tip"
																		data-original-title="enter GST"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:sumthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="taxIcon"
																		class=""></span>
																	<div id="taxErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-2">
																	<input type="text" class="form-text" maxlength="8"
																		value="0.0" min="0.0" id="tatalcost" readonly="readonly" name="tatalcost"
																		data-toggle="tip" data-original-title="Total Cost"
																		onkeypress="return isNumberKey(event,this);"
																		ondrop="return false;">
																</div>
															</div>
			
															<br> <label class="error" id="errorINEACH"
																style="display: none"></label>
			
														</div>
														
														<div id="moreParts">
														</div>
														
														<div class="row1">
															<div class="input_fields_wrap">
																<button class="add_field_button btn btn-info"
																	data-toggle="tip"
																	data-original-title="Click add one more part">
																	<i class="fa fa-plus"></i> Add More Parts
																</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
								</fieldset>
										
											<input type="hidden" name="partInvoiceId" value="${PartInvoice.partInvoiceId}">
											<input type="hidden" name="partInvoiceNumber" value="${PartInvoice.partInvoiceNumber}"> 
											<input type="hidden" name ="locationId" value="${PartInvoice.wareHouseLocation}">
											<input type="hidden" name="invoice_number" value="${PartInvoice.invoiceNumber}">
											<input type="hidden" name="invoice_date" value="${PartInvoice.invoiceDateOn}">
											<input type="hidden" name="invoice_amount" value="${PartInvoice.invoiceAmount}">	
											<input type="hidden" name="vendor_id" value="${PartInvoice.vendorId}">
											<input type="hidden" name="description" value="${PartInvoice.description}">
											<input type="hidden" name ="subLocationId" value="${PartInvoice.subLocationId}">
										
										<!-- <fieldset class="form-actions"> -->
										<div class="row1">
											<div class="col-md-10 col-md-offset-2">
												<button type="submit" class="btn btn-success" onclick = "return validateAddPart();" >Add
													Part</button>
												<a class="btn btn-link"
													href="<c:url value="/showInvoice?Id=${PartInvoice.partInvoiceId}"/>">Cancel</a>
												</div>
											</div>
										<!-- </fieldset> -->
								
									</div>
								</form>
						</div>
					</div>
				</div>
				
		<div class="modal fade" id="addPartSerialModal" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Part Serial Number Details</h4>
					</div>
						<div class="modal-body">
							<input type="hidden" id="inventoryId">
							<input type="hidden" id="partId">
							<input type="hidden" id="locationId">
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Sr.</th>
										<th>Serial Number</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									
								</tbody>
							</table>	
						</div>
						<div class="modal-footer">
							<button id="serialSave" type="submit" onclick=" return saveWarrantySerialNumber();" class="btn btn-success">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
			<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>	
			</sec:authorize>
		</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${PartInvoice.firstName}" /></small> | <small class="text-muted"><b>Created
						date: </b> <c:out value="${PartInvoice.createdOn}" /></small> | <small
					class="text-muted"><b>Last updated by :</b> <c:out
						value="${PartInvoice.firstName}" /></small> | <small
					class="text-muted"><b>Last updated date:</b> <c:out
						value="${PartInvoice.lastUpdated_Date}" /></small>
			</div>
	</section>
</div>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript">
$(document).ready(function(){
	setPaymentDetailsLink(${PartInvoice.partInvoiceId},1,${PartInvoice.paymentTypeId});	
});
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IP/EditShowPartInvoice.js" />"></script>
