<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">  
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <a
						href="ShowVendor.in?vendorId=${vendor.vendorId}"> <c:out
							value="${vendor.vendorName}" />
					</a> / <span id="NewVehi">Show Vendor Approval</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="ShowApprovalList.in?vendorId=${vendor.vendorId}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<div class="row">
						<div class="col-md-4">
							<h3>
								<a href="ShowVendor.in?vendorId=${vendor.vendorId}"
									data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip"
								data-original-title="Vendor Type "> <c:out
										value="${vendor.vendorType}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone"><c:out
										value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip"
								data-original-title="PAN No"><c:out
										value="${vendor.vendorPanNO}" /></a></li>
							<li>Service GST NO : <a data-toggle="tip"
								data-original-title="GST NO"> <c:out
										value="${vendor.vendorGSTNO}" /></a></li>
							<li>GST Registered : <a data-toggle="tip"
								data-original-title="GST NO"> <c:choose>
										<c:when test="${vendor.vendorGSTRegisteredId == 1}">

																Turnover Below 25 lakhs GST
															</c:when>
										<c:otherwise>
																Turnover Above 25 lakhs GST

															</c:otherwise>
									</c:choose></a></li>
						</ul>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<c:if test="${param.saveapproval eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${saveapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${deleteapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Canceled successfully .
			</div>
		</c:if>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
				<div class="col-md-11">
					<div class="main-body">
						<div class="row">
							<a class="btn btn-default"
								href="ShowVendorFuelCredit.in?vendorId=${vendor.vendorId}"><i
								class="fa fa-credit-card"></i> Credit History</a> <a
								class="btn btn-default"
								href="ShowVendorFuelCash.in?vendorId=${vendor.vendorId}"> <i
								class="fa fa-money"></i> Paid History
							</a>
							<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
								<a class="btn btn-success"
									href="ShowApprovalList.in?vendorId=${vendor.vendorId}"><i
									class="fa fa-thumbs-o-up"></i> Create Approval List </a>
							</sec:authorize>
							<a class="col-md-offset-3 btn btn-info"
								data-toggle="control-sidebar"><i class="fa fa-search">
									Filter</i></a>
						</div>
						<br>
						<div class="row">
							<div class="main-body">
								<h4>Create Approval List</h4>
								<div class="box">
									<div class="box-body">
										<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
											<spring:message code="message.unauth"></spring:message>
										</sec:authorize>
										<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
											<form id="frm-example" action="ApprovalServiceEntriesList.in"
												method="POST">
												<input type="hidden" name="vendorId"
													value="${vendor.vendorId}">
												<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
												<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
												<table id="VendorApplovalList"
													class="table table-bordered table-striped">
													<thead>
														<tr>
															<th class="fit"><input name="select_all" value="1"
																id="example-select-all" type="checkbox" /></th>
															<th>ID</th>
															<th>Vehicle/Vendor</th>
															<th>Invoice Date</th>
															<th>Invoice Number</th>
															<th>Cost</th>
															<th>Doc</th>
															<th>Status</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th class="fit"></th>
															<th>Vehicle</th>
															<th>Group</th>
															<th>Invoice Date</th>
															<th>Invoice Number</th>
															<th>Cost</th>
															<th>Doc</th>
															<th>Status</th>
														</tr>
													</tfoot>
													<tbody id="vendorList">

														<c:if test="${!empty ServiceEntries}">
															<c:forEach items="${ServiceEntries}" var="ServiceEntries">

																<tr data-object-id="" class="ng-scope">
																	<td class="fit"><c:if
																			test="${ServiceEntries.service_vendor_paymode != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="SE-${ServiceEntries.serviceEntries_id}"
																				id="example" type="checkbox" />
																		</c:if></td>


																	<td><a target="_blank" 
																		href="showServiceEntryDetails?serviceEntryId=${ServiceEntries.serviceEntries_id}"
																		data-toggle="tip"
																		data-original-title="Click Service Details"><c:out
																				value="SE-${ServiceEntries.serviceEntries_Number}" /> </a></td>
																	<td class="fit ar" ><a target="_blank" 
																		href="showVehicle?vid=${ServiceEntries.vid}"
																		data-toggle="tip"
																		data-original-title="Click Service Details"><c:out
																				value="${ServiceEntries.vehicle_registration}" /> </a></td>

																	<td><c:out value="${ServiceEntries.invoiceDate}" /></td>

																	<td><c:out value="${ServiceEntries.invoiceNumber}" /></td>
																	<td class="fir ar"><i class="fa fa-inr"></i> <c:out
																			value="${ServiceEntries.totalservice_cost}" /></td>

																	<td>
																		<c:choose>
																			<c:when test="${ServiceEntries.service_document_id != null && ServiceEntries.service_document_id > 0}">
																				<a target='_blank' href="${pageContext.request.contextPath}/download/serviceDocument/${ServiceEntries.service_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</c:when>
																		</c:choose>
																	</td>
																	<td><c:choose>
																			<c:when
																				test="${ServiceEntries.service_vendor_paymode == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${ServiceEntries.service_vendor_paymode}" /></span>
																			</c:when>
																			<c:when
																				test="${ServiceEntries.service_vendor_paymode == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${ServiceEntries.service_vendor_paymode}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${ServiceEntries.service_vendor_paymode}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${!empty PurchaseOrder}">
															<c:forEach items="${PurchaseOrder}" var="PurchaseOrder">
																<tr data-object-id="" class="ng-scope">
																<td class="fit"><c:if
																			test="${PurchaseOrder.purchaseorder_vendor_paymode != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="PO-${PurchaseOrder.purchaseorder_id}"
																				id="example" type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank"
																		href="PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}">
																			<c:out value="PO-${PurchaseOrder.purchaseorder_Number}" />
																	</a></td>
																	<td class="fit ar"><a target="_blank"
																		href="<c:url value="/ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}"/>"
																		data-toggle="tip" data-original-title="Click Inventory INFO"><c:out
																				value="${PurchaseOrder.purchaseorder_vendor_name}" /> </a>
																	</td>
																	
																	<td><c:out
																			value="${PurchaseOrder.purchaseorder_invoice_date}" /></td>
																	
																	
																	<td><c:out
																			value="${PurchaseOrder.purchaseorder_invoiceno}" /></td>

																	<td><c:out
																			value="${PurchaseOrder.purchaseorder_balancecost}" /></td>
																	<td>
																		<c:choose>
																			<c:when test="${PurchaseOrder.purchaseorder_document_id != null && PurchaseOrder.purchaseorder_document_id > 0}">
																				<a target='_blank' href="${pageContext.request.contextPath}/download/PurchaseorderDocument/${PurchaseOrder.purchaseorder_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</c:when>
																		</c:choose>
																	</td>
																	<td><c:choose>
																			<c:when
																				test="${PurchaseOrder.purchaseorder_vendor_paymode == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${PurchaseOrder.purchaseorder_vendor_paymode}" /></span>
																			</c:when>
																			<c:when
																				test="${PurchaseOrder.purchaseorder_vendor_paymode == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${PurchaseOrder.purchaseorder_vendor_paymode}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${PurchaseOrder.purchaseorder_vendor_paymode}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${!empty TyreInvoice}">
															<c:forEach items="${TyreInvoice}" var="TyreInvoice">
																<tr>
																	<td class="fit"><c:if
																			test="${TyreInvoice.VENDOR_PAYMODE_STATUS != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="TI-${TyreInvoice.ITYRE_ID}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a
																		href="<c:url value="/showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="TI-${TyreInvoice.ITYRE_NUMBER}" /> </a></td>
																	<td class="fit ar"><a target="_blank" 
																		href="<c:url value="/ShowVendor.in?vendorId=${TyreInvoice.VENDOR_ID}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${TyreInvoice.VENDOR_NAME}" /> </a></td>
																	<td><c:out value="${TyreInvoice.INVOICE_DATE}" /></td>

																	<td class="fit ar"><c:out
																			value="${TyreInvoice.INVOICE_NUMBER}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${TyreInvoice.INVOICE_AMOUNT}" /></td>
																		<td>
																			<c:choose>
																				<c:when test="${TyreInvoice.tyre_document_id != null && TyreInvoice.tyre_document_id > 0}">
																					<a target='_blank' href="${pageContext.request.contextPath}/download/TyreInventoryDocument/${TyreInvoice.tyre_document_id}.in">
																						<span class="fa fa-download"> Download</span>
																					</a>
																				</c:when>
																			</c:choose>
																		</td>
																	<td><c:choose>
																			<c:when
																				test="${TyreInvoice.VENDOR_PAYMODE_STATUS == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${TyreInvoice.VENDOR_PAYMODE_STATUS}" /></span>
																			</c:when>
																			<c:when
																				test="${TyreInvoice.VENDOR_PAYMODE_STATUS == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${TyreInvoice.VENDOR_PAYMODE_STATUS}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${TyreInvoice.VENDOR_PAYMODE_STATUS}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${!empty TyreRetread}">
															<c:forEach items="${TyreRetread}" var="TyreRetread">
																<tr>
																	<td class="fit"><c:if
																			test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="TR-${TyreRetread.TRID}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a
																		href="<c:url value="/ShowRetreadTyre?RID=${TyreRetread.TRID}"/>"
																		data-toggle="tip"
																		data-original-title="Click Tyre Retread INFO"><c:out
																				value="TR-${TyreRetread.TRNUMBER}" /></a></td>
																	<td class="fit ar"><a target="_blank" 
																	href="<c:url value="/ShowVendor.in?vendorId=${TyreRetread.TR_VENDOR_ID}"/>"
																		data-toggle="tip"
																		data-original-title="Click Tyre Retread INFO"><c:out
																				value="TR-${TyreRetread.TR_VENDOR_NAME}" /></a></td>

																	<td><c:out value="${TyreRetread.TR_INVOICE_DATE}" /></td>

																	<td class="fit ar"><c:out
																			value="${TyreRetread.TR_INVOICE_NUMBER}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${TyreRetread.TR_AMOUNT}" /></td>

																	<td></td>
																	<td><c:choose>
																			<c:when
																				test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS}" /></span>
																			</c:when>
																			<c:when
																				test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${!empty BatteryInvoice}">
															<c:forEach items="${BatteryInvoice}" var="BatteryInvoice">
																<tr>
																	<td class="fit"><c:if
																			test="${BatteryInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="BI-${BatteryInvoice.batteryInvoiceId}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank" 
																		href="<c:url value="/showBatteryInvoice.in?Id=${BatteryInvoice.batteryInvoiceId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="BI-${BatteryInvoice.batteryInvoiceNumber}" /> </a></td>
																	<td class="fit ar"><a  target="_blank" 
																		href="<c:url value="/ShowVendor.in?vendorId=${BatteryInvoice.vendorId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${BatteryInvoice.vendorName}" /> </a></td>
																	<td><c:out value="${BatteryInvoice.invoiceDateStr}" /></td>

																	<td class="fit ar"><c:out
																			value="${BatteryInvoice.invoiceNumber}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${BatteryInvoice.invoiceAmount}" /></td>

																	<td></td>
																	<td><c:choose>
																			<c:when
																				test="${BatteryInvoice.vendorPaymentStatusStr == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${BatteryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:when
																				test="${BatteryInvoice.vendorPaymentStatusStr == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${BatteryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${BatteryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
															</c:forEach>
														</c:if>
														
														<c:if test="${!empty PartInvoice}">
															<c:forEach items="${PartInvoice}" var="PartInvoice">
																<tr>
																	<td class="fit"><c:if
																			test="${PartInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="PI-${PartInvoice.partInvoiceId}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank" 
																		href="<c:url value="/showInvoice.in?Id=${PartInvoice.partInvoiceId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="PI-${PartInvoice.partInvoiceNumber}" /> </a></td>
																	<td class="fit ar"><a target="_blank" 
																		href="<c:url value="/ShowVendor.in?vendorId=${PartInvoice.vendorId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${PartInvoice.vendorName}" /> </a></td>
																	<td><c:out value="${PartInvoice.invoiceDateOn}" /></td>

																	<td class="fit ar"><c:out
																			value="${PartInvoice.invoiceNumber}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${PartInvoice.invoiceAmount}" /></td>

																	<td>
																		<c:choose>
																			<c:when test="${PartInvoice.part_document_id != null && PartInvoice.part_document_id > 0}">
																				<a target='_blank' href="${pageContext.request.contextPath}/download/PartDocument/${PartInvoice.part_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</c:when>
																		</c:choose>
																	</td>
																	<td><c:choose>
																			<c:when
																				test="${PartInvoice.vendorPaymentStatusStr == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${PartInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:when
																				test="${PartInvoice.vendorPaymentStatusStr == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${PartInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${PartInvoice.vendorPaymentStatusStr}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																		
																</tr>
															</c:forEach>
														</c:if>
														
														<c:if test="${!empty ClothInvoice}">
															<c:forEach items="${ClothInvoice}" var="ClothInvoice">
																<tr>
																	<td class="fit"><c:if
																			test="${ClothInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="CI-${ClothInvoice.clothInvoiceId}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank" 
																		href="<c:url value="/showClothInvoice.in?Id=${ClothInvoice.clothInvoiceId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="CI-${ClothInvoice.clothInvoiceNumber}" /> </a></td>
																	<td class="fit ar"><a target="_blank" 
																		href="<c:url value="/ShowVendor.in?vendorId=${ClothInvoice.vendorId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${ClothInvoice.vendorName}" /> </a></td>
																	<td><c:out value="${ClothInvoice.invoiceDateStr}" /></td>

																	<td class="fit ar"><c:out
																			value="${ClothInvoice.invoiceNumber}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${ClothInvoice.invoiceAmount}" /></td>

																	<td> 
																		<c:choose>
																			<c:when test="${ClothInvoice.cloth_document_id != null && ClothInvoice.cloth_document_id > 0}">
																				<a target='_blank' href="${pageContext.request.contextPath}/download/ClothInvoiceDocument/${ClothInvoice.cloth_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</c:when>
																		</c:choose>
																	</td>

																	<td><c:choose>
																			<c:when
																				test="${ClothInvoice.vendorPaymentStatusStr == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${ClothInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:when
																				test="${ClothInvoice.vendorPaymentStatusStr == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${ClothInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${ClothInvoice.vendorPaymentStatusStr}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																		<!-- <td>
																		</td> -->
																		
																</tr>
															</c:forEach>
														</c:if>
														<c:if test="${!empty laundryInvoice}">
															<c:forEach items="${laundryInvoice}" var="laundryInvoice">
																<tr>
																	<td class="fit"><c:if
																			test="${laundryInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																			<input name="SelectService_id"
																				value="LI-${laundryInvoice.laundryInvoiceId}" id="example"
																				type="checkbox" />
																		</c:if></td>
																	<td><a target="_blank" 
																		href="<c:url value="/showLaundryInvoice?Id=${laundryInvoice.laundryInvoiceId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO">LI-${laundryInvoice.laundryInvoiceNumber} </a></td>
																	<td class="fit ar"><a target="_blank" 
																		href="<c:url value="/ShowVendor.in?vendorId=${laundryInvoice.vendorId}"/>"
																		data-toggle="tip"
																		data-original-title="Click Inventory INFO"><c:out
																				value="${laundryInvoice.vendorName}" /> </a></td>
																	<td><c:out value="${laundryInvoice.invoiceDateStr}" /></td>

																	<td class="fit ar"><c:out
																			value="${laundryInvoice.paymentNumber}" /></td>

																	<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																			value="${laundryInvoice.totalCost}" /></td>

																	<td></td>
																	<td><c:choose>
																			<c:when
																				test="${laundryInvoice.vendorPaymentStatusStr == 'PAID'}">
																				<span class="label label-pill label-success"><c:out
																						value="${laundryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:when
																				test="${laundryInvoice.vendorPaymentStatusStr == 'APPROVED'}">
																				<span class="label label-pill label-warning"><c:out
																						value="${laundryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-danger"><c:out
																						value="${laundryInvoice.vendorPaymentStatusStr}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																		<!-- <td>
																		</td> -->
																		
																</tr>
															</c:forEach>
														</c:if>
														
													</tbody>
												</table>
												<div align="center">
													<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
														<button class="btn btn-success" data-toggle="tip"
															data-original-title="Please Select Any One"
															data-toggle="modal" data-target="#processing-modal">Create</button>
													</sec:authorize>
												</div>
											</form>
										</sec:authorize>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
</div>
<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark"
	style="padding-top: 100px;">
	<div class="row">
		<div class="box">
			<div class="box-header">
				<h4>Search</h4>
			</div>
			<div class="box-body" style="padding: 20px;">
				<form action="FilterApprovalList.in" method="POST">
					<div class="form-horizontal ">
						<div class="row1">
							<input type="hidden" class="form-text" name="vendorId"
								value="${vendor.vendorId}" readonly="readonly"> <input
								type="hidden" class="form-text" name="vendor_name"
								value="${vendor.vendorName}" readonly="readonly">
						</div>
						<div class="row1">
							<label class="control-label">Vehicle Group :</label>
							<div class="">
								<input type="hidden" id="SelectVehicleGroup"
									name="vehicle_group" style="width: 100%;"
									placeholder="Please Enter 2 or more Group Name" />
							</div>
						</div>
						<div class="row1">
							<label class="control-label">Vehicle Ownership :</label>
							<div class="">
								<select class=" select2" name="vehicle_OwnershipId"
									style="width: 100%;">
									<option value="0"></option>
									<option value="1">Owned</option>
									<option value="2">Leased</option>
									<option value="3">Rented</option>
									<option value="4">Attached</option>
									<option value="5">Customer</option>
								</select>
							</div>
						</div>
						<!-- Date range -->
						<div class="row1">
							<label class=" control-label">Date range: <abbr
								title="required">*</abbr>
							</label>
							<div class="">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="reportrange" class="form-text"
										name="fuelRange_daterange" required="required"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
							</div>
						</div>
						<div class="row1">
							<label class="L-size control-label"></label>
							<div class="I-size">
								<button type="submit" name="Filter" class="btn btn-success">
									<i class="fa fa-search"> Filter</i>
								</button>
								<a href="ShowVendor.in?vendorId=${vendor.vendorId}"
									class="btn btn-default"> Cancel</a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</aside>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovalValidate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>


<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleOldlanguage.js" />"></script>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>