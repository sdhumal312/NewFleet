<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
  <section class="content-header">
	<div class="box">
		<div class="box-body">
			<div class="row">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newPurchaseOrders/1.in"/>">REQUISITION</a> /
					<c:if test="${poConfiguration.requisitionApproved}">
						<a href="<c:url value="/PurchaseOrders_APPROVED/1.in"/>">REQUISITION APPROVED</a>/
					</c:if>
					<a href="<c:url value="/PurchaseOrders_ORDERED/1.in"/>">ORDERED</a>
					/ <a href="<c:url value="/PurchaseOrders_RECEIVED/1.in"/>">RECEIVED</a>
					/ <a href="<c:url value="/PurchaseOrders_COMPLETED/1.in"/>">COMPLETED</a>
					/ <span>Add Purchase Order</span>
				</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchPurchaseOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">PO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="Po-NO eg:74654">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
					<div class="pull-right">
					<c:if test="${poConfiguration.makeApproval}">
							<button class="btn btn-info btn-sm" onclick="makeApproval();">
								Make Approval
							</button>
						</c:if>
						<c:if test="${configuration.VendorInputFieldInPOAddPart}">
							<button class="btn btn-danger btn-sm" onclick="addVendorInPO();">
								Add Vendor
							</button>
						</c:if>
						<c:if test="${poConfiguration.tallyIntegrationRequired}">
							<button class="btn btn-warning btn-sm" onclick="addTallyCompanyInPO();">
								Add Tally Company
							</button>
						</c:if>
						<a class="btn btn-success" href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="box">
			<sec:authorize access="!hasAuthority('VIEW_PURCHASE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_PURCHASE')">
				<div class="row">
					<div class="col-md-5">
						<!-- <div class="pull-left"> -->
						<h4>Purchase Order ${PurchaseOrder.purchaseorder_Number }</h4>
						<h4 align="center">
							<a
								href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}"
								data-toggle="tip" data-original-title="Click Vendor Info"> <c:out
									value="${PurchaseOrder.purchaseorder_vendor_name}" /><br>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_location}" />
							</a>
						</h4>

					</div>
					<div class="col-md-6">
						<div class="row">
							<input type="hidden" id="statues" name="statues"
								value="${PurchaseOrder.purchaseorder_status}">
								<input type="hidden" id="previousDate" name="previousDate" value="${poConfiguration.previousPOpartRate}">
									<input type="hidden" id="shipLocationId" value="${PurchaseOrder.purchaseorder_shiplocation_id}">
								<input type="hidden" id="shipLocation" value="${PurchaseOrder.purchaseorder_shiplocation}">
								<input type="hidden" id="locationWisePartCount" name="previousPOpartRate" value="${poConfiguration.locationWisePartCount}">
								
								<input type="hidden" id="fromTransfer"  value="${fromTransfer}">
								<input type="hidden" id="partFound"  value="${partFound}">
								<input type="hidden" id="transactionId" value="${rTransactionId}">
								<input type="hidden" id="transactionName"  value="${rTransactionName}">
								<input type="hidden" id="rQuantity" value="${rQuantity}">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<a data-disable-with="..." data-method="post"
										data-remote="true" rel="nofollow"> <span id="status-open"
										class="status-led"> <i class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">REQUISITION</div>
									</span>
									</a> <a data-method="post" data-remote="true" rel="nofollow"> <span
										id="status-in-progress" class="status-led"> <i
											class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">ORDERED</div>
									</span>
									</a> <a data-disable-with="..." data-method="post"
										data-remote="true" rel="nofollow"><span
										id="status-on-hold" class="status-led"> <i
											class="fa fa-circle"></i>
											<div class="status-text" style="color: black;">RECEIVED</div>
									</span> </a>

									<button type="button" class="btn btn-default"
										data-toggle="modal" data-target="#addPurchaseOrderDocument"
										data-whatever="@mdo">
										<i class="fa fa-upload"></i> Upload
									</button>

									<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
										<a style="width: 10%"
											href="PrintPurchaseOrder?id=${PurchaseOrder.purchaseorder_id}"
											target="_blank" class="btn btn-default "><i
											class="fa fa-print"></i> Print</a>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<dl class="dl-horizontal">
							<dt>PO-Type :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_type}" />
							</dd>
							<dt>Date Opened :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_created_on}" />
							</dd>
							<dt>Date Required :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_requied_on}" />
							</dd>
							<c:if test="${poConfiguration.showQuoteNumber}">
							<dt>Quote No :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_quotenumber}" />
							</dd>
							</c:if>
							<c:if test="${poConfiguration.WorkOrderNo}">
							<dt>WO / Indent No :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_workordernumber}" />
								/
								<c:out value="${PurchaseOrder.purchaseorder_indentno}" />
							</dd>
							</c:if>
							<dt>Terms :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_terms}" />
							</dd>
						</dl>
					</div>
					<div class="col-md-4">
						<dl class="dl-horizontal">
							<dt>Vendor :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_vendor_name}" />
							</dd>
							<dt>Vendor GST :</dt>
							<dd>
								<c:out value="${PurchaseOrder.vendorGstNumber}" />
							</dd>
							<dt>Buyer Name:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_buyer}" />
							</dd>
							<dt>Buyer Address :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_buyeraddress}" />
							</dd>
							<c:if test="${poConfiguration.showBuyerGstNumber}">
								<dt>Buyer GST :</dt>
								<dd>
									<c:out value="${PurchaseOrder.buyerGstNumber}" />
								</dd>
							</c:if>
							<dt>Ship via :</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shipvia}" />
							</dd>
							<c:if test="${poConfiguration.tallyIntegrationRequired}">
								<dt>Tally Company :</dt>
								<dd>
									<c:out value="${PurchaseOrder.tallyCompanyName}" />
								</dd>
							</c:if>	
						</dl>
					</div>
					<div class="col-md-3">

						<dl class="dl-horizontal">
							<dt>Ship To:</dt>
							<dd>
								<c:out value="${PurchaseOrder.purchaseorder_shiplocation}" />
							</dd>
							<dt>Ship Address:</dt>
							<dd>
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_address}" />
							</dd>
							<dt>Contact:</dt>
							<dd>
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_contact} - " />
								<c:out
									value="${PurchaseOrder.purchaseorder_shiplocation_mobile}" />
							</dd>
							<dt>Notes :</dt>
							<dd style="word-wrap: break-word">
								<c:out value="${PurchaseOrder.purchaseorder_notes}" />
							</dd>
						</dl>
					</div>
				</div>
				<fieldset>
					<div class="modal fade" id="addPurchaseOrderDocument" role="dialog">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post" action="uploadPurchaseOrderDocument.in"
									enctype="multipart/form-data">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">PurchaseOrder Document</h4>
									</div>
									<div class="modal-body">
										<div class="form-horizontal ">
											<input type="hidden" name="purchaseorder_id"
												value="${PurchaseOrder.purchaseorder_id}">
											<div class="row">
												<div class="L-size">
													<label class="L-size control-label"> Document Name:<abbr
														title="required">*</abbr>
													</label>
												</div>
												<div class="I-size">
													<select name="purchaseorder_document" class="form-text">
														<option value="quotation">Quotation</option>
														<option value="invoice">Invoice</option>
													</select>
												</div>
											</div>
											<br>
											<div class="row">
												<div class="L-size">
													<label class="L-size control-label"> Browse:<abbr
														title="required">*</abbr>
													</label>
												</div>
												<div class="I-size">
													<input type="file"
														accept="image/png, image/jpeg, image/gif, application/pdf "
														name="input-file-preview" required="required" />
												</div>
											</div>
											<br />
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span>Upload</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span>Cancel</span>
										</button>
									</div>

								</form>
							</div>
						</div>
					</div>
					<div class="row">
						<c:if test="${deleteFristParts}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Should be Delete First Task Parts and Technician

							</div>
						</c:if>
						<div class="col-md-11">
						<input type="hidden" id="purchaseOrderId" value="${PurchaseOrder.purchaseorder_id}">
							<input type="hidden" id="VendorInputFieldInPOAddPart"value="${configuration.VendorInputFieldInPOAddPart}">
							<input type="hidden" id="updatedVendorId" value="${PurchaseOrder.purchaseorder_vendor_id}">
							<input type="hidden" id="updatedVendorName" value="${PurchaseOrder.purchaseorder_vendor_name}">
							<input type="hidden" id="showTallyCompany" value="${poConfiguration.tallyIntegrationRequired}"/>
							<input type="hidden" id="updatedTallyCompanyId" value="${PurchaseOrder.tallyCompanyId}">
							<input type="hidden" id="purchaseorderToPartId" >
							<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
							<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
							<input type="hidden" id="purchaseOrderTypeId" value="${PurchaseOrder.purchaseorder_typeId}"/>
							<input type="hidden" id="purchaseOrderTotal">
							<input type="hidden" id="oldQuantity" >
							<input type="hidden" id="oldUnitPrice" >
							<input type="hidden" id="oldGst" >
							<input type="hidden" id="makeApprovalApproval" value="${poConfiguration.makeApproval}">
							<input type="hidden" id="companyId" value="${companyId}">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Upholstery</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
											<c:if test="${poConfiguration.makeApproval}">
												<th class="fit">Status</th>
											</c:if>
											<th class="fit">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PurchaseOrderPart}">
											<c:forEach items="${PurchaseOrderPart}"
												var="PurchaseOrderPart">
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 1 }">
													<input type="hidden" id="pendingApproval" value="true">
												</c:if>
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 2 }">
													<input type="hidden" id="approvedApproval" value="true">
												</c:if>
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 1 || PurchaseOrderPart.approvalPartStatusId == 2}">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:if
															test="${PurchaseOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><c:out
															value="${PurchaseOrderPart.clothTypeName}" /> <br>
													</td>

													<td class="fit ar">${PurchaseOrderPart.quantity}</td>
													<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
													<td class="fit ar">${PurchaseOrderPart.discount}%</td>
													<td class="fit ar">${PurchaseOrderPart.tax}%</td>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PurchaseOrderPart.totalcost}</td>
													<c:if test="${poConfiguration.makeApproval}">
														<td class="fit ar"> ${PurchaseOrderPart.approvalPartStatus}</td>
													</c:if>			
													<td class="fit">
														<div class="btn-group">
															<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																href="#"> <span class="fa fa-cog"></span> <span
																class="caret"></span>
															</a>

															<ul class="dropdown-menu pull-right">
																<li><sec:authorize
																		access="hasAuthority('DELETE_PURCHASE')">
																		<a
																			href="deletePurchaseOrderToPart.in?purchaseorderto_partid=${PurchaseOrderPart.purchaseorderto_partid}"
																			class="confirmation"
																			onclick="return confirm('Are you sure? Delete ')">
																			<span class="fa fa-trash"></span> Delete
																		</a>
																	</sec:authorize></li>
																	<c:if test="${configuration.addVehicleDetailsInPOAddPart}">
																		<li>
																			<a href="#" onclick="showAddVehicle('${PurchaseOrderPart.quantity}','${PurchaseOrderPart.purchaseorderto_partid}')">Add Vehicle</a> 
																		</li>
																	</c:if>
																	<c:if test="${poConfiguration.editPartDetails}">
																	<c:choose>
																			<c:when test="${PurchaseOrderPart.approvalPartStatusId == 1}">
																					<li>
																			<a href="#" onclick="editPurchaseOrderPartDetails('${PurchaseOrderPart.clothTypesId}', '${PurchaseOrderPart.clothTypeName}','${PurchaseOrderPart.TYRE_MODEL_ID}', '${PurchaseOrderPart.TYRE_MODEL}','${PurchaseOrderPart.TYRE_SIZE_ID}', '${PurchaseOrderPart.TYRE_SIZE}','${PurchaseOrderPart.purchaseorderto_partid}','${PurchaseOrderPart.quantity}','${PurchaseOrderPart.parteachcost}','${PurchaseOrderPart.discount}','${PurchaseOrderPart.tax}','${PurchaseOrderPart.totalcost}')">Edit Cloth</a> 
																		</li>
																			</c:when>
																			<c:otherwise>
																			<li>
																				<a>${PurchaseOrderPart.approvalPartStatus}</a>
																			</li>
																			</c:otherwise>
																		</c:choose>
																	
																	
																	</c:if>
															</ul>
														</div>
													</td>
												</tr>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty PurchaseOrderPart}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Purchase Order is Empty</h5>
												</td>
											</tr>
										</c:if>

										<tr data-object-id="" class="ng-scope">
											<td colspan="8">
												<div class="">
													<div id="changePart" style="display: none">
														<form id="formPOAddParts"
															action="<c:url value="/savePurchaseOrderCloth.in"/>"
															method="post" enctype="multipart/form-data"
															name="formPOAddParts" role="form" class="form-horizontal">
															<div class="row" id="grppoParts" class="form-group">
																<div class="col-md-4">
																	<!-- vendorId -->
																	<input type="hidden"
																		value="${PurchaseOrder.purchaseorder_vendor_id}"
																		id="VENDOR_ID"> <input type="hidden"
																		name="purchaseorder_id"
																		value="${PurchaseOrder.purchaseorder_id}"
																		id="PurchaseOrders_id">
																	<!--  -->
																	<input type="hidden" name="clothTypesId" id="clothTypesId"
																		required="required" style="width: 100%;" onchange="getLastPartCostDetails(this.id)"
																		placeholder="Please Enter 2 or more Cloth Name" /> <span
																		id="PurchaseOrders_idIcon" class=""></span>
																	<div id="PurchaseOrders_idErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-1">
																	<!-- onkeypress="return isNumberKeyQut(event);" --> <!--Original-->
																	<input type="text" class="form-text" placeholder="Qty"
																		name="quantity" required="required" id="quantity" onpaste="return false"
																		maxlength="10" data-toggle="tip"
																		data-original-title="enter Part Quantity"
																		onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																		onkeyup="javascript:sumthere('quantity', 'parteachcost', 'discount', 'tax', 'tatalcost');"
																		min="0.00"> <span id="quantityIcon" class=""></span>
																	<div id="quantityErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-1"><!-- onkeypress="return isNumberKeyEach(event);" --> <!--Original-->
																	<input type="text" name="parteachcost"
																		class="form-text" placeholder="each cost" onpaste="return false"
																		required="required" id="parteachcost" maxlength="12"
																		data-toggle="tip"
																		data-original-title="enter Each Cost"
																		onkeypress="return isNumberKeyWithDecimal(event,this.id)"
																		ondrop="return false;"
																		onkeyup="javascript:sumthere('quantity', 'parteachcost','discount', 'tax', 'tatalcost');"
																		min="0.00"> <span id="parteachcostIcon"
																		class=""></span>
																	<div id="parteachcostErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-1">
																	<div class="input-group"><!--onkeypress="return isNumberKeyDis(event);"--><!--Original-->
																		<input type="text" class="form-text" placeholder="Dis"
																			name="discount" required="required" id="discount" onpaste="return false"
																			maxlength="5" data-toggle="tip"
																			data-original-title="enter discount"
																			onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																			ondrop="return false;"
																			onkeyup="javascript:sumthere('quantity', 'parteachcost', 'discount', 'tax', 'tatalcost');"
																			min="0.00"> <span class="input-group-addon">%</span>
																	</div>
																	<span id="discountIcon" class=""></span>
																	<div id="discountErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-1">
																	<div class="input-group"><!--onkeypress="return isNumberKeyTax(event);" --><!--Original-->
																		<input type="text" class="form-text" placeholder="GST" onpaste="return false"
																			name="tax" required="required" id="tax" maxlength="5"
																			onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																			data-toggle="tip" data-original-title="enter GST"
																			onkeyup="javascript:sumthere('quantity', 'parteachcost', 'discount', 'tax', 'tatalcost');"
																			min="0.0"> <span class="input-group-addon">%</span>
																	</div>
																	<span id="taxIcon" class=""></span>
																	<div id="taxErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-1">
																	<input type="text" name="totalcost" data-toggle="tip"
																		data-original-title="Total cost" class="form-text"
																		required="required" id="tatalcost" readonly="readonly">
																</div>
																<!-- <div class="fit"></div> -->
															</div>
															<br> <label class="error" id="errorINEACH"
																style="display: none"> </label>
																	<div class="help-block" id="lastPOpartDetails">

															</div>

															<div class="help-block" id="last_occurred">
																<span class="loading ng-hide" id="loading"> <img
																	alt="Loading" class="loading-img"
																	src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
																</span>

															</div>
															<div class="row">
																<div class="col-md-5 col-md-offset-2">
																	<button type="submit" class="btn btn-success">Create
																		Upholstery</button>
																</div>
															</div>
														</form>
													</div>
												</div>
											</td>
										</tr>
									</tbody>
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="8"><a id="addParts"
												onclick="javascript:getClothInventoryList('clothTypesId');"
												href="javascript:toggle3('changePart','addParts');"> Add
													Upholstery </a></th>
										</tr>
									</tfoot>
								</table>
							</div>
							<c:if test="${poConfiguration.makeApproval}">
							<div id="rejectedPart"  class="table-responsive">
							<h4>
								<span class="label label-danger">Rejected Upholstery</span>
							</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Upholstery</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PurchaseOrderPart}">
										<input type="hidden" id="PurchaseOrderPart" value="${PurchaseOrderPart}">
											<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 3}">
												<input type="hidden" id="retectedPurchaseOrderPart" value="true">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:if
															test="${PurchaseOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><c:out
															value="${PurchaseOrderPart.clothTypeName}" /> <br>
													</td>

													<td class="fit ar">${PurchaseOrderPart.quantity}</td>
													<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
													<td class="fit ar">${PurchaseOrderPart.discount}%</td>
													<td class="fit ar">${PurchaseOrderPart.tax}%</td>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PurchaseOrderPart.totalcost}</td>

												</tr>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty PurchaseOrderPart}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Purchase Order is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div> 
							<div  id="transferPart" class="table-responsive">
							<h4>
								<span class="label label-primary">Branch Transfer</span>
							</h4>
								<table class="table">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th>Upholstery</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
										
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty PurchaseOrderPart}">
										<input type="hidden" id="PurchaseOrderPart" value="${PurchaseOrderPart}">
											<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
												<c:if test="${PurchaseOrderPart.approvalPartStatusId == 4}">
													<input type="hidden" id="transferPurchaseOrderPart" value="true">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:if
															test="${PurchaseOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${PurchaseOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><c:out
															value="${PurchaseOrderPart.clothTypeName}" /> <br>
													</td>

													<td class="fit ar">${PurchaseOrderPart.quantity}</td>
													<td class="fit ar">${PurchaseOrderPart.parteachcost}</td>
													<td class="fit ar">${PurchaseOrderPart.discount}%</td>
													<td class="fit ar">${PurchaseOrderPart.tax}%</td>
													<td class="fit ar"><i class="fa fa-inr"></i>
														${PurchaseOrderPart.totalcost}</td>
													
												</tr>
												</c:if>
											</c:forEach>
										</c:if>
										<c:if test="${empty PurchaseOrderPart}">
											<tr data-object-id="" class="ng-scope">
												<td colspan="8">
													<h5 align="center">Purchase Order is Empty</h5>
												</td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
							</c:if>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-7">
									<sec:authorize access="hasAuthority('ORDERED_PURCHASE')">
										<c:if test="${!empty PurchaseOrderPart}">
											<form action="OrderedPurchaseOrder.in" method="post"
												onsubmit="return PurchaseOrdersValidate();">
												<div class="form-horizontal ">

													<input type="hidden" name="purchaseorder_id"
														value="${PurchaseOrder.purchaseorder_id}"
														required="required">

													<div class="row1">
														<label class="L-size control-label"
															for="issue_description"> Remarks :</label>
														<div class="I-size">
															<textarea class="text optional form-text"
																id="initial_note" name="purchaseorder_orderd_remark"
																rows="3" maxlength="500">
				                                 			</textarea>
				                                 			<c:if test="${poConfiguration.showSaveRemarkButton}">
				                                			 <input style="margin-top:5px;" class="btn btn-success" 
				                                			 type="button" value="SaveRemark" id="saveRemark" onclick="SavePoRemark()">
				                                			 </c:if>
														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Advance Amount
															: <abbr title="required">*</abbr>
														</label>
														<div class="col-md-3">
															<input type="text" class="form-text" onpaste="return false"
																name="purchaseorder_advancecost" required="required"  id="advanceAmount"
																onkeypress="return isNumberKeyWithDecimal(event,this.id)" value="0"
																ondrop="return false;"> <label class="error"
																id="errorAmount" style="display: none"></label>

														</div>
													</div>
													<div class="row1">
														<label class="L-size control-label">Order By: <abbr
															title="required">*</abbr>
														</label>
														<div class="I-size">
															<input type="text" class="form-text" readonly="readonly"
																name="purchaseorder_orderdby" required="required"
																onkeypress="return IsOrderdby(event);"
																value="${CreatedBy}" ondrop="return false;"> <label
																class="error" id="errorOrderdby" style="display: none">
															</label>
														 <input type="hidden" name="purchaseorder_orderdbyId" value="${CreatedById}" />
														</div>
													</div>
													<fieldset class="form-actions">
														<div class="row">
															<div class="col-md-10 col-md-offset-2">

																<input class="btn btn-success" type="submit"
																	class="confirmation"
																	onclick="return validateSentPart();"
																	value="Sent PurchaseOrder"> <a
																	class="btn btn-link"
																	href="<c:url value="/newPurchaseOrders/1.in"/>">Cancel</a>
															</div>
														</div>
													</fieldset>
												</div>
											</form>
										</c:if>
									</sec:authorize>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<c:if test="${poConfiguration.eachTotalCost}">
												<tr class="row">
													<th class="key">SubTotal :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${totalEachCost}</td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.eachTotalCost}">
												<tr class="row">
													<th class="key">SubTotal :</th>
													<td class="value"><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_subtotal_cost}</td>
												</tr>
											</c:if>
											
											<tr class="row">
												<th class="key">Freight :</th>
												<td class="value"><a id="freight" data-remote="true"
													href="javascript:toggle2Freight('Purchase_order-freight','freight');">
														<i class="fa fa-inr"></i>
												</a> ${PurchaseOrder.purchaseorder_freight}


													<div class="popup-edit hide-on-escape"
														id="Purchase_order-freight">
														<form accept-charset="UTF-8"
															action="PurchaseOrdersUpdate_freight.in" method="post"
															novalidate="novalidate">

															<div class="row1">
																<label class="control-label">Freight Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input name="purchaseorder_id" type="hidden"
																		required="required"
																		value="${PurchaseOrder.purchaseorder_id}"> <input
																		class="form-text" name="purchaseorder_freight"
																		type="number" required="required"
																		value="${PurchaseOrder.purchaseorder_freight}">
																</div>
															</div>
															<div class="row1">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>

													</div></td>
											</tr>
											
											<c:if test="${poConfiguration.totalGstCost}">
												<tr class="row">
													<th class="key">GST Cost :</th>
													<td class="value"><i class="fa fa-inr"></i>
													<fmt:formatNumber type="number" pattern="#.##" value="${PurchaseOrder.purchaseorder_totaltax_cost}" />
													</td>
												</tr>
											</c:if>
											<c:if test="${!poConfiguration.totalGstCost}">
											<tr class="row">
												<th class="key">GST cost :</th>
												<td class="value"><a id="tax_cost" data-remote="true"
													href="javascript:toggle2Tax('work_order-tax_cost','tax_cost');">
														<i class="fa fa-inr"></i>
												</a> ${PurchaseOrder.purchaseorder_totaltax_cost}


													<div class="popup-edit hide-on-escape"
														id="work_order-tax_cost">
														<form accept-charset="UTF-8"
															action="PurchaseOrdersUpdate_Taxcost.in" method="post"
															novalidate="novalidate">
															
															<div class="row1">
																<label class="control-label">GST Cost</label>
																<div class="input float optional work_order_tax_cost">
																	<input name="purchaseorder_id" type="hidden"
																		required="required"
																		value="${PurchaseOrder.purchaseorder_id}"> <input
																		class="form-text" required="required"
																		name="purchaseorder_totaltax_cost" type="number"
																		value="${PurchaseOrder.purchaseorder_totaltax_cost}">
																</div>
															</div>
															<div class="row1">
																<input class="btn btn-success" name="commit"
																	type="submit" value="Apply">
															</div>
														</form>

													</div></td>
											</tr>
											</c:if>
											<c:if test="${poConfiguration.totalDiscountCost}">
												<tr class="row">
													<th class="key">Discount cost :</th>
													<td id="totalDiscountCost" class="value"><i class="fa fa-inr"></i>
														${TotalDiscountCost}</td>
												</tr>
											</c:if>
											<c:if test="${poConfiguration.finalTotalCost}">
												<input type="hidden" id="finalTotalCost" value="${finalCost}">
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														${finalCost}</a></td>
											</tr>
											</c:if>
											<c:if test="${!poConfiguration.finalTotalCost}">
											<input type="hidden" id="finalTotalCost" value="${PurchaseOrder.purchaseorder_totalcost}">
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														${PurchaseOrder.purchaseorder_totalcost}</a></td>
											</tr>
											</c:if>
											
											
											<tr class="row">
												<th class="key">Advance Paid :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${PurchaseOrder.purchaseorder_advancecost}</td>
											</tr>
											<c:if test="${poConfiguration.finalBalanceCost}">
											<tr class="row">
												<th class="key">Balance :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${balanceCost}</td>
											</tr>
											</c:if>
											<c:if test="${!poConfiguration.finalBalanceCost}">
											<tr class="row">
												<th class="key">Balance :</th>
												<td class="value"><i class="fa fa-inr"></i>
													${PurchaseOrder.purchaseorder_balancecost}</td>
											</tr>
											</c:if>
											
										</tbody>
									</table>
								</div>
								<table class="table">
									<tfoot>
										<tr class="breadcrumb">
											<th colspan="6"><a href=""><i class="fa fa-plus"></i>
											</a></th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${PurchaseOrder.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${PurchaseOrder.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${PurchaseOrder.lastModifiedBy}" /></small> | <small
				class="text-muted"><b>Last updated date:</b> <c:out
					value="${PurchaseOrder.lastupdated}" /></small>
		</div>
	</section>
</div>
<div class="modal fade" id="vendorDetails" role="dialog">
	<div class="modal-dialog" style="width: 650px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Vendor Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<label class="L-size control-label" for="selectVendor">Vendor
								<abbr title="required">*</abbr>
							</label>
							<div class="I-size" >
								<input type="hidden" id="allTypeOfVendorId" style="width: 100%;"
									required="required" placeholder="Please Select Vendor Name" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVendor" class="btn btn-primary"> Update Vendor
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="tallyCompanyDetails" role="dialog">
	<div class="modal-dialog" style="width: 650px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Tally Company Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpmanufacturer" class="form-group">
						<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
								  placeholder="Please Enter Tally Company Name" />
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveTally" class="btn btn-primary"> Update Tally Company
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="vehicleDetails" role="dialog">
	<div class="modal-dialog" style="width: 850px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">vehicle Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-5">
								<label class="L-size control-label" for="selectVendor">Total Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text" style="width: 100%;" id="totalQty"  readonly="readonly"/>
								</div>
							</div>	
							<div class="col-md-4">
							<label class="L-size control-label" for="selectVendor">Remaining Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text" style="width: 80%;" id="remainingQty"  readonly="readonly"/>
								</div>
							</div>
						</div>
						<br>
						<br>
						<div class="row1"  class="form-group">
							<div class="col-md-5">
								<label class="L-size control-label" for="selectVendor">Vehicle
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="hidden" class="select"  style="width: 100%;" name="vehicle" id=vid />
								</div>
							</div>
							<div class="col-md-4">
								<label class="L-size control-label" for="selectVendor">Part Qty
									<abbr title="required">*</abbr>
								</label>
								<div class="I-size" >
									<input type="text" class="form-text"  style="width: 80%;" name="partQuantity"  id=partQty 
									onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="validatePartQuantity();"/>
								</div>
							</div>
							<div class="input_fields_wrap1">
								<button class="add_field_button1 btn btn-success" id="add_field_button1">
									<i class="fa fa-plus"></i>
								</button>
							</div>
						</div>
							<div class="row1">
								<table class="table">
									<thead>
										<tr>
											<th class="fit ar">Sr No</th>
											<th class="fit ar">Vehicle</th>
											<th class="fit ar">Part Quantity</th>
											<th class="fit ar">Action</th>
										</tr>
									</thead>
									<tbody id="vehicleTable"> </tbody>
			
								</table>
							</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVehiclePart" onclick="saveVehiclePart();" class="btn btn-primary"> Add Vehicle
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="editPartDetailsModal" role="dialog">
	<div class="modal-dialog" style="width: 850px;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Part Details</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="row1" id="grpselectVendor" class="form-group">
							<label class="L-size control-label" for="selectVendor">Upholstery Name
								<abbr title="required">*</abbr>
							</label>
							<div class="I-size" >
								<!-- <input type="hidden"  style="width: 100%;" id="partName"  placeholder="Please Enter 2 or more Part Name" /> -->
								<input type="hidden" name="partid" id="editClothTypesId"
									required="required" style="width: 100%;"
									required="required"
									placeholder="Please Enter 2 or more Part Name" /> <span
									id="PurchaseOrders_idIcon" class=""></span>
								<div id="PurchaseOrders_idErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br>
						<div class="row1 " id="partStock">
							<span class="loading ng-hide" id="loading"> <img
								alt="Loading" class="loading-img"
								src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
							</span>

						</div>
						<br>
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-1">
								<label class=" control-label" for="selectVendor">Quantity
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Unit Cost
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Discount
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">GST
							</label>
							</div>
							<div class="col-md-2">
								<label class=" control-label" for="selectVendor">Total
							</label>
							</div>
						</div>
						<br>
						<br>
						<div class="row1" id="grpselectVendor" class="form-group">
							<div class="col-md-1">
								<input type="text" class="form-text" placeholder="Qty"  id="editQuantity"
									maxlength="10" onpaste="return false" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
									onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
									min="0.00"> 
							</div>
							<div class="col-md-2">
								<input type="text" class="form-text" placeholder="Unit Price"  id="editUnitPrice"
									maxlength="10" onpaste="return false" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
									onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
									min="0.00"> 
							</div>
							<div class="col-md-2">
								<div class="input-group">
									<input type="text" class="form-text" placeholder="Discount" onpaste="return false"
										id="editDiscount" maxlength="5" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
										min="0.0"> <span class="input-group-addon">%</span>
								</div>
							</div>
							<div class="col-md-2">
								<div class="input-group">
									<input type="text" class="form-text" placeholder="GST" onpaste="return false"
										id="editGST" maxlength="5" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										onkeyup="javascript:sumthere('editQuantity', 'editUnitPrice', 'editDiscount', 'editGST', 'editTotalCost');"
										min="0.0"> <span class="input-group-addon">%</span>
								</div>
							</div>
							<div class="col-md-2">
								<input type="text" id="editTotalCost"  data-toggle="tip"
									data-original-title="Total cost" class="form-text"
									required="required" id="tatalcost" readonly="readonly">
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" id="saveVehiclePart" onclick="updatePurchaseOrderPartDetails();" class="btn btn-primary"> Update Part
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="makeApprovalMoadal" role="dialog">
	<div class="modal-dialog" style="width: 95%;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="JobType">Make Approval</h4>
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="table-bordered table-responsive text-center">
					<table class="table table-bordered" style="border: 1px solid #ddd !important;">
					<thead>
						<tr>
							<th class="fit ar">Tyre</th>
							<th class="fit ar">Request Quantity</th>
							<th class="fit ar" style="width: 10%;">Ordered Quantity</th>
							<th class="fit ar" style="width: 10%;">Total Cost</th>
							<th class="fit ar">Vendor</th>
							<th class="fit ar" style="text-align: center;">Action</th>
							<th class="fit ar">Remark</th>
						</tr>
					</thead>
					<tbody id="partDetailsTable">
						<c:if test="${!empty PurchaseOrderPart}">
							<c:forEach items="${PurchaseOrderPart}" var="PurchaseOrderPart">
								<c:choose>
									<c:when test="${PurchaseOrderPart.approvalPartStatusId == 1}">
										<tr data-object-id="" class="ng-scope">
													<td class="fit">
														<input type="hidden" id="approvalToPartId${PurchaseOrderPart.purchaseorderto_partid}" value="${PurchaseOrderPart.purchaseorderto_partid}" name="approvalPurchaseOrderToPartId"> 
														<input type="hidden" id="approvalPurchaseOrderClothTypeId${PurchaseOrderPart.purchaseorderto_partid}" value="${PurchaseOrderPart.clothTypesId}" name="approvalPurchaseOrderClothTypeId"> 
														<a href="#" onclick="getLocationWiseUpholsteryQuantity(${PurchaseOrderPart.clothTypesId});">
															<c:out value="${PurchaseOrderPart.clothTypeName}" />
														</a>
													</td>
													<td class="fit" style="font-size: 15px;">
														<a href="#" data-toggle="tooltip" title="Each: ${PurchaseOrderPart.parteachcost} Tax: ${PurchaseOrderPart.tax} Discount: ${PurchaseOrderPart.discount}"><c:out value="${PurchaseOrderPart.quantity}" /></a>
													</td>
													<td class="fit"><input type="text"
														id="approvalPartQuantity${PurchaseOrderPart.purchaseorderto_partid}"
														class="form-text" style="width: 90%;"
														name="approvalQuantity"
														value="${PurchaseOrderPart.quantity}"
														onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														onkeyup="javascript:sumthere('approvalPartQuantity${PurchaseOrderPart.purchaseorderto_partid}', 'approvalPartEachCost${PurchaseOrderPart.purchaseorderto_partid}', 'approvalPartDiscount${PurchaseOrderPart.purchaseorderto_partid}', 'approvalPartTax${PurchaseOrderPart.purchaseorderto_partid}', 'approvalPartCost${PurchaseOrderPart.purchaseorderto_partid}');">
													</td>
													<td class="fit"><input type="text"
														id="approvalPartCost${PurchaseOrderPart.purchaseorderto_partid}"
														class="form-text" style="width: 90%;"
														name="approvalTotalCost"
														value="${PurchaseOrderPart.totalcost}" readonly="readonly">
													</td>
													<td class="fit" style="width: 12%;">
														<input type="hidden" name="approvalEachCost" id="approvalPartEachCost${PurchaseOrderPart.purchaseorderto_partid}" value="${PurchaseOrderPart.parteachcost}"> 
														<input type="hidden" name="approvalDiscount" id="approvalPartDiscount${PurchaseOrderPart.purchaseorderto_partid}" value="${PurchaseOrderPart.discount}"> 
														<input type="hidden" name="approvalTax" id="approvalPartTax${PurchaseOrderPart.purchaseorderto_partid}" value="${PurchaseOrderPart.tax}"> 
														<input type="hidden" id="partVendorId${PurchaseOrderPart.purchaseorderto_partid}" name="vendorId" class="partVendorId"  onchange="createNewPurchaseOrder(${PurchaseOrderPart.purchaseorderto_partid});" style="width: 100%;" required="required" placeholder="Vendor Name" />
													</td>
													
													
													<td class="fit">

														<div class="btn-group" id="btnGroup${PurchaseOrderPart.purchaseorderto_partid}" data-toggle="buttons">
															<label class="btn btn-default btn-on btn-mg active" id="approveStatusId${PurchaseOrderPart.purchaseorderto_partid}" style="width: 20%;"> 
																<input type="radio" value="2"   name="status${PurchaseOrderPart.purchaseorderto_partid}"  checked="checked">Approve
															</label> 
															<label class="btn btn-default btn-off btn-mg" id="rejectStatusId${PurchaseOrderPart.purchaseorderto_partid}" style="width: 15%;" >
																<input type="radio" value="3"  name="status${PurchaseOrderPart.purchaseorderto_partid}"  > Reject
															</label>
															<label class="btn btn-default btn-off btn-mg" id="transferStatusId${PurchaseOrderPart.purchaseorderto_partid}" style="width: 35%;" >
																<input type="radio" value="4"  name="status${PurchaseOrderPart.purchaseorderto_partid}"  > Branch Transfer
															</label>
														</div>
													</td>
													<td class="fit">
														<input type="text" class="form-text" id="remark" name="remark">
													</td>
												</tr>
									</c:when>
									
								</c:choose>
								</c:forEach>
						</c:if>
					</tbody>

					</table>
				</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success" onclick="createPurchaseOrderPartApproval();" > Complete Approval
				</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<span id="Close"><spring:message code="label.master.Close" /></span>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="locationPartDetailsModal" role="dialog">
	<div class="modal-dialog" style="width: 90%;">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<div class="pull-left">
					<h4>Location Quantity</h4>
				</div>
				<input type="hidden" id="purchaseOrderToPartId" >
			</div>
			<div class="modal-body">
				<div class="box">
					<div class="box-body">
						<div class="table-responsive">
					<table class="table">
					<thead id="locationPartDetailsHeader">
						
					</thead>
					<tbody id="locationPartDetailsTable">
						
					</tbody>

					</table>
				</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success" id="continuePO" data-dismiss="modal" > Close
				</button>
				
			</div>
		</div>
	</div>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/PO/PurchaseOrdersValidate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var input_Statues = document.getElementById('statues').value;
						var wrapperStatues = $("#work-order-statuses"); //Fields wrapper
						switch (input_Statues) {
						case "REQUISITION":
							document.getElementById('status-open').className = 'status-led-open';

							break;
						case "ORDERED":
							document.getElementById('status-in-progress').className = 'status-led-in-progress';

							break;

						case "ONHOLD":
							document.getElementById('status-on-hold').className = 'status-led-on-hold';

							break;

						case "COMPLETED":
							document.getElementById('status-ReOpen').style.display = 'block';

							break;
						}

					});

	$(document)
			.ready(
					function() {
						$("#inventory_name")
								.change(
										function() {
											
											if($("#locationWisePartCount").val() == false || $("#locationWisePartCount").val() === 'false'){
											$
													.getJSON(
															"getPurchaseOrderStock.in",
															{
																PARTID : $(this)
																		.val(),
																ajax : "true"
															},
															function(e) {
																
																$('#parteachcost').val(e.unitCost);
																$('#discount').val(e.discount);
																$('#tax').val(e.tax);
																var t = "", d = "", n = "", i = "", r = "";
																		d = e.inventory_all_id,
																		n = e.partnumber,
																		i = e.partname,
																		r = e.all_quantity;
																if (r != 0
																		&& null != r)
																	t = '<p style="color: red;"> You have '
																			+ r
																			+ ' Quantity in stock . <a href="showInventory.in?inventory_all_id='
																			+ d
																			+ '" target="_blank">View <i class="fa fa-external-link"></i></a></p>'
																else
																	t = '<p style="color: blue;">You don\'t have in stock.</P>';
																$(
																		"#last_occurred")
																		.html(t)
															})
															}
										})

						$("#inventory_name")
								.change(
										function() {
											$
													.getJSON(
															"getVendorFixedPricePO.in",
															{
																PARTID : $(this).val(),
																VENDOTID : document.getElementById("VENDOR_ID").value,
																ajax : "true"
															},
															function(e) {
																var d = "", n = "", i = "", r = "";
																		d = e.VPPID,
																		n = e.PARTEACHCOST,
																		i = e.PARTDISCOUNT,
																		r = e.PARTGST;
																if (d != 0
																		&& null != d) {
																	document
																			.getElementById('parteachcost').value = n;
																	document
																			.getElementById('discount').value = i;
																	document
																			.getElementById('tax').value = r;
																	document
																			.getElementById('parteachcost').readOnly = true;
																	document
																			.getElementById('discount').readOnly = true;
																	document
																			.getElementById('tax').readOnly = true;
																}else {
																	document
																			.getElementById('parteachcost').value = "";
																	document
																			.getElementById('discount').value = "";
																	document
																			.getElementById('tax').value = "";
																	document
																			.getElementById('parteachcost').readOnly = false;
																	document
																			.getElementById('discount').readOnly = false;
																	document
																			.getElementById('tax').readOnly = false;
																}

																
															})
										})

					});
	$(document)
	.ready(
			function() {
				$("#advanceAmount")
						.keyup(
								function() {
									var advAmt = Number($(
											"#advanceAmount").val());
									var subAmt = Number($(
											"#finalTotalCost").val());

									if (advAmt > subAmt) {
										showMessage('info',
										'Advance Amount Can Not Be Greater Than Total Cost ');
										$("#advanceAmount").val(0);
										return false;
									}

								})

			});
</script>

<!-- get the Inventory Drop Down down -->
<c:url var="findInventoryURL" value="getInventoryList.in" />

<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>

<!-- get the Inventory Quantity and Unit price -->
<c:url var="findInventoryURL" value="getInventoryQuantityList.in" />
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>