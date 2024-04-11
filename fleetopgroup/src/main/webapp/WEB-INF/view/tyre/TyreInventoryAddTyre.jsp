<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>  
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
						href="<c:url value="/TyreInventory/1.in"/>">New Tyre Inventory</a>
					/ <span>Add Tyre Details</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreInventory/1.in"/>">Cancel</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_TYRE_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<h4>Tyre Invoice ${TyreInvoice.ITYRE_NUMBER}</h4>
						<h4 align="center">
							<a
								href="<c:url value="/ShowVendor.in?vendorId=${TyreInvoice.VENDOR_ID}"/>"
								data-toggle="tip" data-original-title="Click Vendor Info"> <c:out
									value="${TyreInvoice.VENDOR_NAME}" /><br> <c:out
									value="${TyreInvoice.VENDOR_LOCATION}" />
							</a>
						</h4>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<sec:authorize access="hasAuthority('DOWNLOND_TYRE_RETREAD')">
										<a style="width: 10%"
											href="PrintTyreInvoice?Id=${TyreInvoice.ITYRE_ID}"
											target="_blank" class="btn btn-default "><em
											class="fa fa-print"></em></a>
									</sec:authorize>
								</div>
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
											<th class="key">Invoice Date :</th>
											<td class="value"><c:out
													value="${TyreInvoice.INVOICE_DATE}" /></td>
										</tr>
										<tr class="row">
											<th class="key">Payment Tyre :</th>
											<td class="value">
											<span id="paymentTypeSpan"><c:out value="${TyreInvoice.PAYMENT_TYPE}" /></span></td>
										</tr>
										<tr class="row">
											<th class="key">WareHouse Location :</th>
											<td class="value"><c:out
													value="${TyreInvoice.WAREHOUSE_LOCATION}" /></td>
										</tr>
										<tr class="row">
											<th class="key">Vendor Payment Status :</th>
											<td class="value">
												<c:choose>
													<c:when test="${TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 4 || TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 5 }">
														<c:out value="${TyreInvoice.VENDOR_PAYMODE_STATUS} PAID" />
													</c:when>
													<c:otherwise>
														<c:out value="${TyreInvoice.VENDOR_PAYMODE_STATUS}" />
													</c:otherwise>
												</c:choose>
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
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Invoice Number :</th>
											<td class="value"><c:out
													value="${TyreInvoice.INVOICE_NUMBER}" /></td>
										</tr>
										<c:if test="${!configuration.roundupAmount}">
										
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value">
											<fmt:formatNumber pattern="#.##" value="${TyreInvoice.INVOICE_AMOUNT}" />
													 </td>
										</tr>
										</c:if>
										
										
										<c:if test="${configuration.roundupAmount}">
										<tr class="row">
											<th class="key">Invoice Amount:</th>
											<td class="value">
											<fmt:formatNumber pattern="#.##" value="${totalAmount}"/>
											 </td>
										</tr>
										
										
										<tr class="row">
											<th class="key">Roundup Amount:</th>
											<td class="value">
											<fmt:formatNumber pattern="#.##" value="${TyreInvoice.INVOICE_AMOUNT}" />
											</td>
										</tr>
										</c:if>
										<c:if test="${showSubLocation}">
											<tr class="row">
												<th class="key">Sub Location:</th>
												<td class="value"><c:out
														value="${TyreInvoice.subLocation}" /></td>
											</tr>
										</c:if>
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
										<tr class="row">
											<th class="key">Payment Number :</th>
											<td class="value"><c:out
													value="${TyreInvoice.PAYMENT_NUMBER}" /></td>
										</tr>
										<tr class="row">
											<th class="key">PO Number :</th>
											<td class="value">	<a href="PurchaseOrders_Parts.in?ID=${TyreInvoice.purchaseOrderId}">
												 <c:out value="${TyreInvoice.PO_NUMBER}" />
												</a></td>
										</tr>
										<c:if test="${configuration.tallyIntegrationRequired}">
											<tr class="row">
												<th class="key">Tally Company :</th>
												<td class="value"><c:out
														value="${TyreInvoice.tallyCompanyName}" /></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:if test="${TyreInvoice.VENDOR_PAYMODE_STATUS_ID != 0}">
						<div class="col-md-3 col-sm-3 col-xs-11">
							<div class="box box-success">
								<div class="box-body no-padding">
									<table class="table ">
										<tbody>
											<tr class="row">
												<th class="key">Approval Number :</th>
												<td class="value">
												<c:choose>
												<c:when test="${TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 6}">
													<a href="AddServiceApproval.in?approvalId=${TyreInvoice.approvalId}">
													 <c:out value="${TyreInvoice.approvalNumber}" />
													</a>
													</c:when>
													<c:otherwise>
													<a href="ShowApprovalPayment.in?approvalId=${TyreInvoice.approvalId}">
													 <c:out value="${TyreInvoice.approvalNumber}" />
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
					<!-- Modal -->
					<div class="modal fade" id="editTyreSerialNumber" role="dialog">
						<div class="modal-dialog">

							<!-- Modal content-->
							<div class="modal-content">
								<form action="updateTyreSerialNo.in" method="post"
									name="vehicleType" onsubmit="return validateType()">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Edit Tyre Serial
											No</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<label class="L-size control-label" id="Type">Serial
												No :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" class="form-text" value=""
													required="required" name="Id" id="tyreId" />
												<!-- invoice Id -->
												<input type="hidden" class="form-text" value=""
													required="required" name="InvoiceID" id="tyreInvoiceId" />
												<!-- Tyre Serial Num -->
												<input type="text" class="form-text" value=""
													id="TyreSerialName" required="required" name="TyreSerialNo"
													maxlength="50" placeholder="Enter Tyre Serial Number" /> <label
													id="errorvType" class="error"></label>
											</div>
										</div>
										<br />
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save"><spring:message
													code="label.master.Save" /></span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close"><spring:message
													code="label.master.Close" /></span>
										</button>
									</div>
								</form>
							</div>

						</div>
					</div>

					<div class="row">

						<c:if test="${param.UpdateSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Tyre Inventory Updated Successfully.
							</div>
						</c:if>
						<c:if test="${param.deleteSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Tyre Inventory Deleted Successfully.
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
								This <%=request.getParameter("successTyre")%> Tyre Serial number Updated Successfully.. 
								
								<% if(request.getParameter("DuplicateTyre") != null) { %>
									and Entered <%=request.getParameter("DuplicateTyre")%> Tyre Serial number duplicate 
								<% } %>

							</div>
						</c:if>
						<c:if test="${param.alreadyTyre eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Tyre Serial Number Already Updated.
							</div>
						</c:if>
						<c:if test="${param.deleteFrist eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Should be Delete First Tyre Serial Number and Amount

							</div>
						</c:if>

						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Manufacturer &amp; Model</th>
											<th>Size</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<c:if test="${TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 2}">
												<th class="fit">Actions</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty TyreAmount}">
											<%
												Integer hitsCount = 1;
											%>
											<c:forEach items="${TyreAmount}" var="TyreAmount">
												<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<%
															out.println(hitsCount);
																		hitsCount += 1;
														%>
													</td>

													<td><c:out value="${TyreAmount.TYRE_MANUFACTURER}" />
														<c:out value="${TyreAmount.TYRE_MODEL}" /></td>
													<td><c:out value="${TyreAmount.TYRE_SIZE}" /></td>
													<td class="fit ar">${TyreAmount.TYRE_QUANTITY}</td>
													<td class="fit ar">${TyreAmount.UNIT_COST}</td>
													
													<c:choose>
														<c:when test="${TyreAmount.discountTaxTypeId == 2}">
															<td class="fit ar">${TyreAmount.DISCOUNT}</td>
															<td class="fit ar">${TyreAmount.TAX}</td>
														</c:when>
														<c:otherwise>
															<td class="fit ar">${TyreAmount.DISCOUNT}%</td>
															<td class="fit ar">${TyreAmount.TAX}%</td>
														</c:otherwise>
													</c:choose>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${TyreAmount.TOTAL_AMOUNT}</td>
													<c:if test="${(TyreInvoice.VENDOR_PAYMODE_STATUS_ID == 2 || TyreInvoice.PAYMENT_TYPE_ID != 2) && TyreInvoice.VENDOR_PAYMODE_STATUS_ID != 0}">
													<td class="fit">
														<div class="btn-group">
															<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																href="#"> <span class="fa fa-cog"></span> <span
																class="caret"></span>
															</a>

															<ul class="dropdown-menu pull-right">

																<li><sec:authorize
																		access="hasAuthority('DELETE_TYRE_INVENTORY')">
																		<a
																			href="deleteTyreAmount.in?Id=${TyreAmount.ITYRE_AMD_ID}"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
																			<span class="fa fa-trash"></span> Delete
																		</a>
																	</sec:authorize></li>
															</ul>
														</div>
													</td>
													</c:if>
												</tr>
												<tr>
													<td colspan="7">
														<div class="row">
															<div class="col-md-11">
																<table class="table">
																	<c:if test="${!empty Tyre}">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="icon">No</th>
																				<th class="icon ar">Tyre Serial Number</th>
																				<th class="icon ar">Amount</th>
																				<th class="fit">Action</th>
																			</tr>
																		</thead>
																		<tbody>
																			<%
																				Integer tyreCount = 1;
																			%>
																			<c:forEach items="${Tyre}" var="Tyre">
																				<c:if
																					test="${Tyre.ITYRE_AMOUNT_ID == TyreAmount.ITYRE_AMD_ID}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="fit">
																							<%
																								out.println(tyreCount);
																														tyreCount += 1;
																							%>
																						</td>
																						<td class="icon"><c:out
																								value="${Tyre.TYRE_NUMBER}" /></td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${Tyre.TYRE_AMOUNT}</td>
																					<c:if test="${TyreInvoice.VENDOR_PAYMODE_STATUS_ID != 0}">
																						<c:choose>
																						<c:when test="${Tyre.TYRE_ASSIGN_STATUS_ID == 6 }">
																						<td>
																							<span class="label label-pill label-warning">
																							<c:out value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																						</td>
																						</c:when>
																						<c:when test="${Tyre.TYRE_ASSIGN_STATUS_ID == 3}">
																						<td>
																							<span class="label label-pill label-danger">
																							<c:out value="${Tyre.TYRE_ASSIGN_STATUS}" /></span>
																						</td>
																						</c:when>
																						<c:otherwise>
																							<td class="fit">
																							<div class="btn-group">
																								<a class="btn-sm dropdown-toggle"
																									data-toggle="dropdown" href="#"> <span
																									class="fa fa-cog"></span> <span class="caret"></span>
																								</a>
																								<ul class="dropdown-menu pull-right">
																									<li><sec:authorize
																											access="hasAuthority('EDIT_TYRE_INVENTORY')">
																												<a
																													onclick="javascript:edit_TyreSerialInput('${Tyre.TYRE_ID}', '${Tyre.ITYRE_INVOICE_ID}',  '${Tyre.TYRE_NUMBER}')"
																													id="editTyreSerialInput"> <span
																													class="fa fa-pencil"></span> Edit Tyre
																												</a>
																										</sec:authorize></li>
																									<li><sec:authorize
																											access="hasAuthority('DELETE_TYRE_INVENTORY')">
																											<a
																												href="deleteTyreInventory?Id=${Tyre.TYRE_ID}"
																												class="confirmation"
																												onclick="return confirm('Are you sure? Delete ')">
																												<span class="fa fa-trash"></span> Delete
																												Tyre
																											</a>
																										</sec:authorize></li>
																									</ul>
																							</div>
																						</td>
																							
																						</c:otherwise>	
																						</c:choose>
																						</c:if>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</c:if>
																	<c:if test="${TyreAmount.TYRE_ASSIGN_NO != 0}">
																		<tbody style="border-top: 0px;">
																			<tr>
																				<td colspan="3">
																					<div class="">
																						<div id="enterTyre${TyreAmount.ITYRE_AMD_ID}"
																							style="display: none;">
																							<form action="saveTyreInfo.in" method="post">
																								<div class="row">
																									<input type="hidden" name="subLocationId" value="${TyreInvoice.subLocationId}" >
																									<input type="hidden" name="ITYRE_INVOICE_ID"
																										value="${TyreInvoice.ITYRE_ID}"
																										required="required"> <input type="hidden" name="STATUS_OF_TYRE"
																										value="${TyreInvoice.STATUS_OF_TYRE}"
																										required="required"><input
																										type="hidden" name="ITYRE_AMOUNT_ID"
																										value="${TyreAmount.ITYRE_AMD_ID}"
																										required="required">
																									<div class="col-md-10 col-md-offset-1">
																										<div class="row">
																											<div class="col-md-1">
																												<label class="control-label">No</label>

																											</div>
																											<div class="col-md-3">
																												<label class="control-label">Tyre
																													Serial NO</label>

																											</div>
																											<div class="col-md-2">
																												<label class="control-label">Tyre
																													Size</label>
																											</div>
																											<div class="col-md-1">
																												<label class="control-label showUses">Uses
																													KM</label>
																											</div>
																											<div class="col-md-2">
																												<label class="control-label noOfRetread">No Of
																													Retread</label>
																											</div>
																										</div>
																										<div id="multilTyre${TyreAmount.ITYRE_AMD_ID}">
																											<div class="row">
																												<span class="loading ng-hide" id="loading">
																													<img alt="Loading" class="loading-img"
																													src="resources/images/ajax-loader.gif" />">
																												</span>
																											</div>
																										</div>
																										<br>
																										<div class="row" id="saveTyre">
																											<div class="col-md-10 col-md-offset-2">
																												<input class="btn btn-success" name="commit"
																													type="submit" onclick="return validateTyre();" value="Save Tyre"> <a
																													class="btn btn-link"
																													href="TyreInventory/1.in">Cancel</a>
																											</div>
																										</div>
																									</div>
																								</div>
																							</form>
																						</div>
																						<a id="addTyre${TyreAmount.ITYRE_AMD_ID}"
																							onload=""
																							onclick="javascript:getMultiTyreInput('multilTyre${TyreAmount.ITYRE_AMD_ID}','${TyreAmount.TYRE_ASSIGN_NO}', '${TyreAmount.TYRE_SIZE}', '${configuration.showTyreUsesColumn}', '${configuration.showTyreStatusAtAdd}', '${TyreInvoice.STATUS_OF_TYRE}' );"
																							href="javascript:toggle2('enterTyre${TyreAmount.ITYRE_AMD_ID}','addTyre${TyreAmount.ITYRE_AMD_ID}');">
																							Add Tyre Details </a>
																					</div>
																				</td>
																			</tr>
																		</tbody>
																	</c:if>
																</table>
															</div>
														</div>
													</td>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty TyreAmount}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Tyre Inventory is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>

								</table>
							</div>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<dl>
										<dd>Initial_Note :</dd>
										<dt>${TyreInvoice.DESCRIPTION}</dt>
									</dl>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
										<c:if test="${!configuration.roundupAmount}">
										<tr class="row">
												<th class="key">SubTotal :</th>
												<td class="value"><i class="fa fa-inr"></i>
													<fmt:formatNumber pattern="#.##" value="${TyreInvoice.INVOICE_AMOUNT}"/> 
													</td>
											</tr>
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														<fmt:formatNumber pattern="#.##" value="${TyreInvoice.INVOICE_AMOUNT}"/> 
														</a></td>
											</tr>
										</c:if>
											<c:if test="${configuration.roundupAmount}">
											<tr class="row">
												<th class="key">SubTotal :</th>
												<td class="value"><i class="fa fa-inr"></i>
													<fmt:formatNumber pattern="#.##" value=" ${totalAmount}"/></td>
											</tr>
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														<fmt:formatNumber pattern="#.##" value=" ${totalAmount}"/>
														
														</a></td>
											</tr>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>	
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${TyreInvoice.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${TyreInvoice.CREATED_DATE}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${TyreInvoice.LASTMODIFIEDBY}" /></small> | <small
				class="text-muted"><b>Last updated date:</b> <c:out
					value="${TyreInvoice.LASTUPDATED_DATE}" /></small>
		</div>
	</section>
</div>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/InventoryTyreValidate.js" />"></script>
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
	$(document).ready(function(){
		setPaymentDetailsLink(${TyreInvoice.ITYRE_ID},2,${TyreInvoice.PAYMENT_TYPE_ID});	
});
</script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>