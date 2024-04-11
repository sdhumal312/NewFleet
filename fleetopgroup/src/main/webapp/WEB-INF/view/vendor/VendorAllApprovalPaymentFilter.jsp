<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
					<a href="<c:url value="/open"/>">
						<spring:message code="label.master.home" /></a> / <a href="<c:url value="/vendorHome.in"/>">Vendors</a> / 
						<a href="ShowVendor.in?vendorId=${vendor.vendorId}"> 
						<c:out value="${vendor.vendorName}" /> </a> / <span id="NewVehi">Show Vendor Approval</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="ShowApprovalList.in?vendorId=${vendor.vendorId}">Cancel</a>
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
								<a href="ShowVendor.in?vendorId=${vendor.vendorId}" data-toggle="tip" data-original-title="Click Vendor Details">
									<c:out value="${vendor.vendorName}" />
								</a>
							</h3>
						</div>
					</div>
					<div class="secondary-header-title">
						<ul class="breadcrumb">
							<li>Vendor Type : <a data-toggle="tip" data-original-title="Vendor Type "> 
								<c:out value="${vendor.vendorType}" /></a></li>
							<li>Phone : <a data-toggle="tip" data-original-title="Phone">
								<c:out value="${vendor.vendorPhone}" /></a></li>
							<li>PAN No : <a data-toggle="tip" data-original-title="PAN No">
								<c:out value="${vendor.vendorPanNO}" /></a></li>
							<li>Service GST NO : <a data-toggle="tip" data-original-title="GST NO"> 
								<c:out value="${vendor.vendorGSTNO}" /></a></li>
							<li>GST Registered : <a data-toggle="tip" data-original-title="GST NO"> 
							<c:choose>
								<c:when test="${vendor.vendorGSTRegisteredId == 1}"> Turnover Below 25 lakhs GST
								</c:when>
								<c:otherwise> Turnover Above 25 lakhs GST
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
											<input type="hidden" name="vendorId" id="vendorId"
												value="${vendor.vendorId}">
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
														<th>Payment</th>
														<th>Payment Date</th>
														<th>Payment Type</th>
														<th>Mode Of Payment</th>
														<th class="hide paymentMode" >Payment Transaction No</th>
														<th>Cost</th>
														<th>Received Amount</th>
														<th>Balance Amount</th>
														<th>Payment Status</th>
														<!-- <th>Status</th> -->
													</tr>
												</thead>
												<tfoot>
													<tr>
														<th class="fit"></th>
														<th>Vehicle</th>
														<th>Group</th>
														<th>Invoice Date</th>
														<th>Invoice Number</th>
														<th>Payment</th>
														<th>Payment Date</th>
														<th>Payment Type</th>
														<th>Mode Of Payment</th>
														<th class="hide paymentMode"  >Payment Transaction No</th>
														<th>Cost</th>
														<th>Received Amount</th>
														<th>Balance Amount</th>
														<th>Payment Status</th>
														<!-- <th>Status</th> -->
													</tr>
												</tfoot>
												<tbody id="vendorList">
													<input type="hidden" name="VendorId" id="vendorId" value="${VendorId}" />
													<input type="hidden" name="dateRangeFrom" id="dateRangeFrom" value="${dateRangeFrom}" />
													<input type="hidden" name="dateRangeTo" id="dateRangeTo" value="${dateRangeTo}" />
													<c:if test="${!empty ServiceEntries}">
														<c:forEach items="${ServiceEntries}" var="ServiceEntries">
															<tr data-object-id="" class="ng-scope">
																<td class="fit">
																	<c:if test="${ServiceEntries.service_vendor_paymode != 'APPROVED'}">
																		<input name="SelectService_id" value="SE-${ServiceEntries.serviceEntries_id}" id="example_${ServiceEntries.serviceEntries_id}" type="checkbox" />
																	</c:if>
																</td>
																
																<td>
																	<a href="showServiceEntryDetails?serviceEntryId=${ServiceEntries.serviceEntries_id}" data-toggle="tip" data-original-title="Click Service Details">
																		<c:out value="SE-${ServiceEntries.serviceEntries_Number}" />
																	</a>
																</td>
																<td>
																	<a target="_blank"  href="showVehicle?vid=${ServiceEntries.vid}" data-toggle="tip" data-original-title="Click Service Details">
																	<c:out value="${ServiceEntries.vehicle_registration}" /> </a>
																</td>
																<td><c:out value="${ServiceEntries.invoiceDate}" /></td>
																<td><c:out value="${ServiceEntries.invoiceNumber}" /></td>
																<td><c:out value="${ServiceEntries.service_paymentType}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${ServiceEntries.serviceEntries_id}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${ServiceEntries.serviceEntries_id}_1_${ServiceEntries.totalservice_cost}_0" id="SelectServiceValues_${ServiceEntries.serviceEntries_id}" type="hidden" /> 
																	<select id="PaymentType_${ServiceEntries.serviceEntries_id}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this)">
																		<option value="0">Select Payment-Type</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${ServiceEntries.serviceEntries_id}" type="hidden" />
																	<select id="PaymentMode_${ServiceEntries.serviceEntries_id}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${ServiceEntries.serviceEntries_id}" >
																</td>
																<td id="invoiceAmount_${ServiceEntries.serviceEntries_id}" class="fir ar"><i class="fa fa-inr"></i> 
																	<fmt:formatNumber pattern="#.##" value="${ServiceEntries.totalservice_cost}" /></td>
																<td>
																	<input type="hidden" value="${ServiceEntries.paidAmount}" id="paidAmt_${ServiceEntries.serviceEntries_id}">
																	<input type="hidden" value="${ServiceEntries.balanceAmount}" id="balAmt_${ServiceEntries.serviceEntries_id}">
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${ServiceEntries.serviceEntries_id}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																	</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${ServiceEntries.balanceAmount}" id="balanceAmt_${ServiceEntries.serviceEntries_id}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<c:if test="${ServiceEntries.service_vendor_paymode == 'PARTIAL'}">
																	<td>
																		<input name="paymentStatus" class="form-text" id="paymentStatusID_${ServiceEntries.serviceEntries_id}" value="${ServiceEntries.service_vendor_paymode}" readonly="readonly" onclick="return popUp(this);" onkeypress="return isNumber(event)" />
																	</td>
																</c:if>
																
																<c:if test="${!ServiceEntries.service_vendor_paymode == 'PARTIAL'}">
																<td>
																	<input name="paymentStatus" class="form-text" id="paymentStatusID_${ServiceEntries.serviceEntries_id}" value="${ServiceEntries.service_vendor_paymode}" readonly="readonly" onkeypress="return isNumber(event)" />
																</td>
																</c:if>

															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${!empty PurchaseOrder}">
														<c:forEach items="${PurchaseOrder}" var="PurchaseOrder">
															<tr data-object-id="" class="ng-scope">
																<td class="fit">
																	<c:if test="${PurchaseOrder.purchaseorder_vendor_paymode != 'APPROVED'}">
																		<input name="SelectService_id" value="PO-${PurchaseOrder.purchaseorder_id}" id="example_${PurchaseOrder.purchaseorder_id}" type="checkbox" />
																	</c:if>
																</td>
																<td> <a target="_blank" href="PurchaseOrders_Parts.in?ID=${PurchaseOrder.purchaseorder_id}">
																		<c:out value="PO-${PurchaseOrder.purchaseorder_Number}" />
																	</a>
																</td>
																<td> 
																	 <a target="_blank" href="ShowVendor.in?vendorId=${PurchaseOrder.purchaseorder_vendor_id}">
																		<c:out value="${PurchaseOrder.purchaseorder_vendor_name}" />
																	</a>
																</td>
																<td> <c:out value="${PurchaseOrder.purchaseorder_invoice_date}" /> </td>
																<td> <c:out value="${PurchaseOrder.purchaseorder_invoiceno}" /> </td>
																<td><c:out value="${PurchaseOrder.purchaseorder_terms}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${PurchaseOrder.purchaseorder_id}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${PurchaseOrder.purchaseorder_id}_1_${PurchaseOrder.purchaseorder_totalcost}_0" id="SelectServiceValues_${PurchaseOrder.purchaseorder_id}" type="hidden" /> 
																	<select id="PaymentType_${PurchaseOrder.purchaseorder_id}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this)">
																		<option value="0">Select Payment-Type</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${PurchaseOrder.purchaseorder_id}" type="hidden" />
																	<select id="PaymentMode_${PurchaseOrder.purchaseorder_id}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${PurchaseOrder.purchaseorder_id}" >
																</td>
																<td id="invoiceAmount_${PurchaseOrder.purchaseorder_id}">
																	<fmt:formatNumber pattern="#.##"  value="${PurchaseOrder.purchaseorder_balancecost}"/>
																</td>
																<td>
																	<input type="hidden" value="${PurchaseOrder.paidAmount}" id="paidAmt_${PurchaseOrder.purchaseorder_id}">
																	<input type="hidden" value="${PurchaseOrder.balanceAmount}" id="balAmt_${PurchaseOrder.purchaseorder_id}">
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${PurchaseOrder.purchaseorder_id}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${PurchaseOrder.balanceAmount}" id="balanceAmt_${PurchaseOrder.purchaseorder_id}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td>
																	<input name="paymentStatus" class="form-text" id="paymentStatusID_${PurchaseOrder.purchaseorder_id}" value="${PurchaseOrder.purchaseorder_vendor_paymode}" readonly="readonly" onclick="return popUp(this);" onkeypress="return isNumber(event)" />
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${!empty TyreInvoice}">
														<c:forEach items="${TyreInvoice}" var="TyreInvoice">
															<tr>
																<td class="fit">
																	<c:if test="${TyreInvoice.VENDOR_PAYMODE_STATUS != 'APPROVED'}">
																		<input name="SelectService_id" value="TI-${TyreInvoice.ITYRE_ID}" id="example_${TyreInvoice.ITYRE_ID}" type="checkbox" />
																	</c:if>
																</td>
																<td>
																	<a href="<c:url value="/showTyreInventory.in?Id=${TyreInvoice.ITYRE_ID}"/>" data-toggle="tip" data-original-title="Click Inventory INFO">
																	<c:out value="TI-${TyreInvoice.ITYRE_NUMBER}" /> </a>
																</td>
																<td class="fit ar">
																	<a target="_blank" href="ShowVendor.in?vendorId=${TyreInvoice.VENDOR_ID}">
																	<c:out value="${TyreInvoice.VENDOR_NAME}" /> </a>
																</td>
																<td><c:out value="${TyreInvoice.INVOICE_DATE}" /></td>
																<td class="fit ar">
																	<c:out value="${TyreInvoice.INVOICE_NUMBER}" />
																</td>
																<td><c:out value="${TyreInvoice.PAYMENT_TYPE}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${TyreInvoice.ITYRE_ID}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${TyreInvoice.ITYRE_ID}_1_${TyreInvoice.INVOICE_AMOUNT}_0" id="SelectServiceValues_${TyreInvoice.ITYRE_ID}" type="hidden" /> 
																	<select id="PaymentType_${TyreInvoice.ITYRE_ID}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this)">
																		<option value="0">Select Payment-Type</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${TyreInvoice.ITYRE_ID}" type="hidden" />
																	<select id="PaymentMode_${TyreInvoice.ITYRE_ID}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${TyreInvoice.ITYRE_ID}" >
																</td>
																<td id="invoiceAmount_${TyreInvoice.ITYRE_ID}" class="fit ar"><i class="fa fa-inr"></i> 
																	<fmt:formatNumber pattern="#.##" value="${TyreInvoice.INVOICE_AMOUNT}"/>
																</td>
																<td>
																	<input type="hidden" value="${TyreInvoice.paidAmount}" id="paidAmt_${TyreInvoice.ITYRE_ID}"> 
																	<input type="hidden" value="${TyreInvoice.balanceAmount}" id="balAmt_${TyreInvoice.ITYRE_ID}"> 
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${TyreInvoice.ITYRE_ID}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${TyreInvoice.balanceAmount}" id="balanceAmt_${TyreInvoice.ITYRE_ID}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>

																<td>
																	<input name="paymentStatus" class="form-text" id="paymentStatusID_${TyreInvoice.ITYRE_ID}" value="${TyreInvoice.VENDOR_PAYMODE_STATUS}" readonly="readonly" onclick="return popUp(this);" onkeypress="return isNumber(event)" />
																</td>

															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${!empty TyreRetread}">
														<c:forEach items="${TyreRetread}" var="TyreRetread">
															<tr>
																<td class="fit">
																	<c:if test="${TyreRetread.TR_VENDOR_PAYMODE_STATUS != 'APPROVED'}">
																		<input name="SelectService_id" value="TR-${TyreRetread.TRID}" id="example_${TyreRetread.TRID}" type="checkbox" />
																	</c:if>
																</td>
																<td>
																	<a href="<c:url value="/ShowRetreadTyre?RID=${TyreRetread.TRID}"/>" data-toggle="tip" data-original-title="Click Tyre Retread INFO">
																	<c:out value="TR-${TyreRetread.TRNUMBER}" /></a>
																</td>
																<td class="fit ar">
																	<a target="_blank" href="ShowVendor.in?vendorId=${TyreRetread.TR_VENDOR_ID}">
																	<c:out value="TR-${TyreRetread.TR_VENDOR_NAME}" /></a>
																</td>
																<td><c:out value="${TyreRetread.TR_INVOICE_DATE}" /></td>
																<td class="fit ar"> <c:out value="${TyreRetread.TR_INVOICE_NUMBER}" /> </td>
																<td> <c:out value="${TyreRetread.TR_PAYMENT_TYPE}" /> </td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${TyreRetread.TRID}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${TyreRetread.TRID}_1_${TyreRetread.TR_AMOUNT}_0" id="SelectServiceValues_${TyreRetread.TRID}" type="hidden" /> 
																	<select id="PaymentType_${TyreRetread.TRID}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this)">
																		<option value="0">Select Payment-Type</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${TyreRetread.TRID}" type="hidden" />
																	<select id="PaymentMode_${TyreRetread.TRID}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${TyreRetread.TRID}" >
																</td>
																<td id="invoiceAmount_${TyreRetread.TRID}" class="fit ar"><i class="fa fa-inr"></i> 
																	<fmt:formatNumber pattern="#.##" value="${TyreRetread.TR_AMOUNT}" /></td>
																<td>
																	<input type="hidden" value="${TyreRetread.paidAmount}" id="paidAmt_${TyreRetread.TRID}"> 
																	<input type="hidden" value="${TyreRetread.balanceAmount}" id="balAmt_${TyreRetread.TRID}"> 
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${TyreRetread.TRID}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${TyreRetread.balanceAmount}" id="balanceAmt_${TyreRetread.TRID}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td>
																	<input name="paymentStatus" class="form-text" id="paymentStatusID_${TyreRetread.TRID}" value="${TyreRetread.TR_VENDOR_PAYMODE_STATUS}" readonly="readonly" onclick="return popUp(this);" onkeypress="return isNumber(event)" />
																</td>

															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${!empty BatteryInvoice}">
														<c:forEach items="${BatteryInvoice}" var="BatteryInvoice">
															<tr>
																<td class="fit">
																	<c:if test="${BatteryInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																		<input name="SelectService_id" value="BI-${BatteryInvoice.batteryInvoiceId}" id="example_${BatteryInvoice.batteryInvoiceId}" type="checkbox" />
																	</c:if>
																</td>
																<td><a href="<c:url value="/showBatteryInvoice.in?Id=${BatteryInvoice.batteryInvoiceId}"/>" data-toggle="tip" data-original-title="Click Inventory INFO">
																	<c:out value="BI-${BatteryInvoice.batteryInvoiceNumber}" /></a>
																</td>
																<td class="fit ar">
																	<a target="_blank" href="ShowVendor.in?vendorId=${BatteryInvoice.vendorId}">
																	<c:out value="${BatteryInvoice.vendorName}" /> </a>
																</td>
																<td><c:out value="${BatteryInvoice.invoiceDateStr}" /></td>
																<td class="fit ar"> <c:out value="${BatteryInvoice.invoiceNumber}" /></td>
																<td><c:out value="${BatteryInvoice.paymentStatus}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${BatteryInvoice.batteryInvoiceId}" name= "datepicker"/>
																</td>
																<td>
																	<input name="SelectServiceValues" value="${BatteryInvoice.batteryInvoiceId}_1_${BatteryInvoice.invoiceAmount}_0" id="SelectServiceValues_${BatteryInvoice.batteryInvoiceId}" type="hidden" /> 
																	<select id="PaymentType_${BatteryInvoice.batteryInvoiceId}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this)">
																		<option value="0">Select Payment-Type</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${BatteryInvoice.batteryInvoiceId}" type="hidden" />
																	<select id="PaymentMode_${BatteryInvoice.batteryInvoiceId}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${BatteryInvoice.batteryInvoiceId}" >
																</td>
																<td id="invoiceAmount_${BatteryInvoice.batteryInvoiceId}" class="fit ar"><i class="fa fa-inr"></i> 
																<fmt:formatNumber pattern="#.##" value="${BatteryInvoice.invoiceAmount}" />
																</td>
																
																<td>
																	<input type="hidden" value="${BatteryInvoice.paidAmount}" id="paidAmt_${BatteryInvoice.batteryInvoiceId}">
																	<input type="hidden" value="${BatteryInvoice.balanceAmount}" id="balAmt_${BatteryInvoice.batteryInvoiceId}">
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="${BatteryInvoice.balanceAmount}" id="receivedAmt_${BatteryInvoice.batteryInvoiceId}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="0" id="balanceAmt_${BatteryInvoice.batteryInvoiceId}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td>
																	<input name="paymentStatus" class="form-text" id="paymentStatusID_${BatteryInvoice.batteryInvoiceId}" value="${BatteryInvoice.vendorPaymentStatusStr}" readonly="readonly" onclick="return popUp(this);" onkeypress="return isNumber(event)" />
																</td>
															</tr>
														</c:forEach>
													</c:if>
													<c:if test="${!empty PartInvoice}">
														<c:forEach items="${PartInvoice}" var="PartInvoice">
															<tr>
																<td class="fit">
																	<c:if test="${PartInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																		<input name="SelectService_id" value="PI-${PartInvoice.partInvoiceId}" id="example_${PartInvoice.partInvoiceId}" type="checkbox" />
																	</c:if>
																</td>
																<td>
																	<a href="<c:url value="/showInvoice.in?Id=${PartInvoice.partInvoiceId}"/>" data-toggle="tip" data-original-title="Click Inventory INFO">
																	<c:out value="PI-${PartInvoice.partInvoiceNumber}" /> </a>
																</td>
																<td class="fit ar">
																	<a target="_blank" href="ShowVendor.in?vendorId=${PartInvoice.vendorId}">
																	<c:out value="${PartInvoice.vendorName}" /> </a>
																</td>
																<td><c:out value="${PartInvoice.invoiceDateOn}" /></td>
																<td class="fit ar"><c:out value="${PartInvoice.partInvoiceNumber}" /></td>
																<td><c:out value="${PartInvoice.paymentStatus}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${PartInvoice.partInvoiceId}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${PartInvoice.partInvoiceId}_1_${PartInvoice.invoiceAmount}_0" id="SelectServiceValues_${PartInvoice.partInvoiceId}" type="hidden" /> 
																	<select id="PaymentType_${PartInvoice.partInvoiceId}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this);">
																		<option value="0">Select PaymentType</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${PartInvoice.partInvoiceId}" type="hidden" />
																	<select id="PaymentMode_${PartInvoice.partInvoiceId}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${PartInvoice.partInvoiceId}" >
																</td>
																<td id="invoiceAmount_${PartInvoice.partInvoiceId}" class="fit ar">
																	<i class="fa fa-inr"></i> 
																	<fmt:formatNumber pattern="#.##" value="${PartInvoice.invoiceAmount}" />
																</td>
																<td>
																	<input type="hidden" value="${PartInvoice.paidAmount}" id="paidAmt_${PartInvoice.partInvoiceId}"> 
																	<input type="hidden" value="${PartInvoice.balanceAmount}" id="balAmt_${PartInvoice.partInvoiceId}"> 
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${PartInvoice.partInvoiceId}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${PartInvoice.balanceAmount}" id="balanceAmt_${PartInvoice.partInvoiceId}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td>
																	<input name="paymentStatus" id="paymentStatusID_${PartInvoice.partInvoiceId}" class="form-text" onclick="return popUp(this);" value="${PartInvoice.vendorPaymentStatusStr}" type="text" onkeypress="return isNumber(event)" />
																</td>
															</tr>
														</c:forEach>
													</c:if>
													
														<c:if test="${!empty ClothInvoice}">
														<c:forEach items="${ClothInvoice}" var="ClothInvoice">
															<tr>
																<td class="fit">
																	<c:if test="${ClothInvoice.vendorPaymentStatusStr != 'APPROVED'}">
																		<input name="SelectService_id" value="CI-${ClothInvoice.clothInvoiceId}" id="example_${ClothInvoice.clothInvoiceId}" type="checkbox" />
																	</c:if>
																</td>
																<td>
																	<a href="<c:url value="/showClothInvoice.in?Id=${ClothInvoice.clothInvoiceId}"/>" data-toggle="tip" data-original-title="Click Inventory INFO">
																	<c:out value="CI-${ClothInvoice.clothInvoiceNumber}" /> </a>
																</td>
																<td class="fit ar">
																	<a target="_blank" href="ShowVendor.in?vendorId=${ClothInvoice.vendorId}">
																	<c:out value="${ClothInvoice.vendorName}" /> </a>
																</td>
																<td><c:out value="${ClothInvoice.invoiceDate}" /></td>
																<td class="fit ar"><c:out value="${ClothInvoice.clothInvoiceNumber}" /></td>
																<td><c:out value="${ClothInvoice.paymentType}" /></td>
																<td>
																	<input class="form-text" type="text" id="datepicker_${ClothInvoice.clothInvoiceId}" name= "datepicker" >
																</td>
																<td>
																	<input name="SelectServiceValues" value="${ClothInvoice.clothInvoiceId}_1_${ClothInvoice.invoiceAmount}_0" id="SelectServiceValues_${ClothInvoice.clothInvoiceId}" type="hidden" /> 
																	<select id="PaymentType_${ClothInvoice.clothInvoiceId}" name="typeOfPaymentId" class="form-text" onchange="changePaymentType(this);">
																		<option value="0">Select PaymentType</option>
																		<option value="1">Clear</option>
																		<option value="2">Partial</option>
																		<option value="3">Negotiate</option>
																	</select>
																</td>
																<td>
																	<input name="SelectModeOfPayment" id="SelectModeOfPayment_${ClothInvoice.clothInvoiceId}" type="hidden" />
																	<select id="PaymentMode_${ClothInvoice.clothInvoiceId}" name="modeOfPaymentId" class="form-text" onchange="changePaymentModeType(this);">
																		<option value="0">Select PaymentMode</option>
																		<option value="1">Cash</option>
																		<option value="3">NEFT</option>
																		<option value="4">RTGF</option>
																		<option value="5">IMPS</option>
																		<option value="6">DD</option>
																		<option value="7">CHEQUE</option>
																	</select>
																</td>
																<td class="hide paymentMode" > 
																	<input class="hide form-text" type="text"  id="paymentModeNum_${ClothInvoice.clothInvoiceId}" >
																</td>
																<td id="invoiceAmount_${ClothInvoice.clothInvoiceId}" class="fit ar">
																	<i class="fa fa-inr"></i> 
																	<fmt:formatNumber pattern="#.##" value="${ClothInvoice.invoiceAmount}" />
																</td>
																<td>
																	<input type="hidden" value="${ClothInvoice.paidAmount}" id="paidAmt_${ClothInvoice.clothInvoiceId}"> 
																	<input type="hidden" value="${ClothInvoice.balanceAmount}" id="balAmt_${ClothInvoice.clothInvoiceId}"> 
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${ClothInvoice.clothInvoiceId}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumberKeyWithDecimal(event,this.id)" />
																</td>
																<td>
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${ClothInvoice.balanceAmount}" id="balanceAmt_${ClothInvoice.clothInvoiceId}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td>
																	<input name="paymentStatus" id="paymentStatusID_${ClothInvoice.clothInvoiceId}" class="form-text" onclick="return popUp(this);" value="${ClothInvoice.vendorPaymentStatusStr}" type="text" onkeypress="return isNumber(event)" />
																</td>
															</tr>
														</c:forEach>
													</c:if>
													
													<div  id="popupPaidInvoice"  class="modal fade"role="dialog">
														<div class="modal-dialog modal-md">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close" data-dismiss="modal">&times;</button>
																	<h4 class="modal-title">Paid Invoice List</h4>
																</div>

																<div class="modal-body">
																	<div id="approval" style="width: 95%;" class="table">
																	</div>
																	<div class="modal-footer">
																		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</tbody>
											</table>
											<div align="center">
												<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
													<button class="btn btn-success" onclick="return createVendorPaymentApproval();" type="button">Create</button>
												</sec:authorize>
											</div>
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
<aside class="control-sidebar control-sidebar-dark" style="padding-top: 100px;">
	<div class="row">
		<div class="box">
			<div class="box-header">
				<h4>Search</h4>
			</div>
			<div class="box-body" style="padding: 20px;">
				<c:if test="${!configuration.directPayment}">
					<form action="<c:url value="/FilterApprovalList.in "/>" method="POST">
				</c:if>
				<c:if test="${configuration.directPayment}">
					<form action="<c:url value="/FilterFuelApprovalList.in "/>" method="POST">
				</c:if>
				<div class="form-horizontal ">
					<div class="row1">
							<input type="hidden" class="form-text" name="vendorId" value="${vendor.vendorId}" readonly="readonly"> 
							<input type="hidden" class="form-text" name="vendor_name" value="${vendor.vendorName}" readonly="readonly">
					</div>
					<div class="row1">
							<label class="control-label">Vehicle Group :</label>
							<div class="">
								<input type="hidden" id="SelectVehicleGroup" name="vehicle_group" style="width: 100%;" placeholder="Please Enter 2 or more Group Name" />
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
						<label class=" control-label">Date range: <abbr title="required">*</abbr> </label>
						<div class="">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="reportrange" class="form-text" name="fuelRange_daterange" required="required" style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label"></label>
							<div class="I-size">
								<button type="submit" name="Filter" class="btn btn-success">
									<i class="fa fa-search"> Filter</i>
								</button>
								<a href="ShowVendor.in?vendorId=${vendor.vendorId}" class="btn btn-default"> Cancel</a>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</aside>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovalValidate.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/EditVendorApprovalPaymentAdd.js" />"></script>
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
<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>	
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>