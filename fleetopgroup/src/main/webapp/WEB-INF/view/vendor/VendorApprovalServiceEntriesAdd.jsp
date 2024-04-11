<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
					<div class="boxinside">
						<div class="row">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
								<a href="<c:url value="/vendor/1.in"/>">Vendors</a> / 
								<a href="<c:url value="/ShowVendor.in?vendorId=${approval.approvalvendorID}"/>" 
								 	data-toggle="tip" data-original-title="Click Vehicle Info"> 
								 	<c:out value="${approval.approvalvendorName}" /> </a> / 
								<span id="NewVehi">Show Vendor Approval List</span>
							</div>
							<div class="pull-right">
								<a href="<c:url value="/VendorApprovalCreated/1.in"/>" data-toggle="tip" data-original-title="Click Vehicle Info"> Cancel</a>
							</div>
						</div>
						<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_VENDOR')">
							<div class="row">
								<div class="pull-left">
									<span>Approval Number : A-${approval.approvalNumber}</span>
								</div>
								<div class="pull-right">
									<span>Created Date : ${approval.created}</span>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<a href="<c:url value="/ShowVendor.in?vendorId=${approval.approvalvendorID}"/>"
										data-toggle="tip" data-original-title="Click Vehicle Info">
										<c:out value="${approval.approvalvendorName}" />
									</a>
								</h4>
							</div>
							<div class="row">
								<h4 align="center">${approval.approvalvendorLocation }</h4>
							</div>

							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Vendor Type : <a data-toggle="tip"
										data-original-title="Vendor Type "> <c:out
												value="${vendor.vendorType}" /></a></li>
									<li>Phone : <a data-toggle="tip"
										data-original-title="Phone"><c:out
												value="${vendor.vendorPhone}" /></a></li>
									<li>PAN No : <a data-toggle="tip"
										data-original-title="PAN No"><c:out
												value="${vendor.vendorPanNO}" /></a></li>
									<li>Service GST NO : <a data-toggle="tip"
										data-original-title="GST NO"> <c:out
												value="${vendor.vendorGSTNO}" /></a></li>
									<%-- <li>VAT No : <a data-toggle="tip"
										data-original-title="VAT NO"> <c:out
												value="${vendor.vendorVatNo}" /></a></li> --%>

								</ul>
							</div>
							<div class="breadcrumb">
								<h5 align="center">${vendor.vendorAddress1},
									${vendor.vendorAddress2}, ${vendor.vendorCity},
									${vendor.vendorState}, ${vendor.vendorCountry}, Pin-
									${vendor.vendorPincode}</h5>
							</div>
						</sec:authorize>
						<br>
						<fieldset>
							<div class="row">
									<sec:authorize access="!hasAuthority('ADD_APPROVEL_VENDOR')">
										<spring:message code="message.unauth"></spring:message>
									</sec:authorize>
									<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
										<div class="">
											<table class="table">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Service ID</th>
														<th class="fit ar">Vehicle/Vendor</th>
														<th class="fit ar">Invoice Date</th>
														<th class="fit ar">Invoice No</th>
														<th class="fit ar">Amount</th>
														<c:if test="${configuration.vendorPaymentFlavor3}">
														<th class="fit">Type Of Payment</th>
														<th class="fit">Received Amount</th>
														<th class="fit">Balance Amount</th>
														<th class="fit">Expected Payment Date</th>
														</c:if>
														<th class="fit">Actions</th>
														
													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty showSubApproval}">
													
														<%
															Integer hitsCount = 1;
														%>
														<c:forEach items="${showSubApproval}" var="subApproval">
															<tr data-object-id="" class="ng-scope">
																<td class="fit" >
																	<%
																		out.println(hitsCount);
																					hitsCount += 1;
																	%>
																</td>
																<td class="fir ar">
																<input type="hidden" id="invoiceId_${subApproval.invoiceId}" name="invoiceId" value="${subApproval.invoiceId}">
																<input type="hidden" id="txnType_${subApproval.approvalPlaceId}" name="txnTypeId" value="${subApproval.approvalPlaceId}">
																	<a target="_blank" href="${subApproval.transactionUrl}"
																				data-toggle="tip" data-original-title="Click To Get Details">${subApproval.transactionNumber} </a>
																</td>
																<c:choose>
																	<c:when test="${subApproval.vid != null && subApproval.vid > 0}">
																		<td>
																		<a target="_blank" href="showVehicle?vid=${subApproval.vid}"
																			data-toggle="tip"
																			data-original-title="Click Fuel Details"><c:out
																				value="${subApproval.vehicleNumber}" /> </a>
																		</td>
																	</c:when>
																	<c:otherwise>
																		<td>${approval.approvalvendorName}</td>
																	</c:otherwise>
																</c:choose>
																
	
																<td><c:out value="${subApproval.invoiceDateStr}" /></td>
																<td><c:out value="${subApproval.invoiceNumber}" /></td>
																<td class="fir ar"><i class="fa fa-inr"></i> 
																	<fmt:formatNumber type="number" pattern="#.##" value="${subApproval.subApprovalTotal}" />
																</td>
																<c:if test="${configuration.vendorPaymentFlavor3}">
																<td >
																	<select style="width:100px;" id="PaymentType_${subApproval.invoiceId}" name="approvedPaymentStatusId" class="form-text" onchange="changePaymentType(this)" >
																		<option value ="1"> Clear </option>
																		<c:if test="${configuration.showPartialPaymentOption}">
																			<option value ="4"> Partial </option>
																		</c:if>
																		<option value ="5" > Negotiate </option>
																	</select>
																</td>
																<td style="width: 80px;">
																	<input type="hidden" value="${subApproval.subApprovalTotal}" id="balAmt_${subApproval.invoiceId}">
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="${subApproval.subApprovalTotal}" id="receivedAmt_${subApproval.invoiceId}" type="text" 
																	   onkeyup="calculateBalance(this)" onkeypress="return isNumber(event)" />
																</td>
																<td style="width: 80px;">
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="0" id="balanceAmt_${subApproval.invoiceId}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td id="date" style="width:150px;"  class="input-group input-append date" >
																	<input type="text" id="expectedPaymentDate_${subApproval.invoiceId}" class="form-text" name="expectedPaymentDate" required="required" data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" readonly="readonly"/>
																	 <div class="input-group-addon add-on"> 
																	 <span class="fa fa-calendar"></span>
																	 </div> 
																</td>
																</c:if>
																<c:if test="${!configuration.vendorPaymentFlavor3}">
																<input type="hidden" name="approvalStatusId">
																<input type="hidden" name="expectedPaymentDate">
																
																</c:if>
	
																<td class="fir ar">
																<a href="#" onclick="removeInvoiceFromApproval('${subApproval.invoiceId}', '${approval.approvalId}', '${subApproval.approvalPlaceId}', '${subApproval.subApprovalTotal}','${subApproval.subApprovalId}')"><font
																		color="red"><i class="fa fa-times"> Remove</i></font></a>
																</td>
																
															</tr>
														</c:forEach>
													</c:if>
												</tbody>
												<tfoot>
													<tr class="breadcrumb">
	
													</tr>
	
												</tfoot>
											</table>
										</div>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-offset-8">
													<table class="table">
														<tbody>
															<tr data-object-id="" class="ng-scope">
																<td class="fit ar"><h4>Total :</h4></td>
																<td class="fit ar"></td>
																<td class="fit ar"><h4> <i class="fa fa-inr"></i> ${approvalTotal} </h4></td>
																<td class="fit"></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
										<fieldset>
											<legend>Approval Info</legend>
											<div class="col-md-11">
												<div class="col-md-offset-4">
													
														<input type="hidden" name="approvalId" value="${approval.approvalId}"> 
														<input type="hidden" name="approvalvendorID" value="${approval.approvalvendorID}">
														<div class="row1">
															<label class="L-size control-label" for="issue_vehicle_id"> Approved By :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="text" class="form-text" readonly="readonly" name="approvalCreateBy" required="required"
																	value="${approval.approvalCreateBy}" maxlength="75" onkeypress="return IsVendorName(event);" ondrop="return false;"> 
																	<label class="error" id="errorVendorName" style="display: none"> </label>
															</div>
														</div>
														<br> <br>
														<div class="row1">
															<div class="col-md-offset-5 I-size">
																<a class="btn btn-success" class="confirmation" onclick="approveVendorApprovalEntry('${approval.approvalId}');"
																	href="#">
																	Approve </a>
																	<a class="btn btn-danger" class="confirmation" onclick="return confirm('Are you sure? Delete ')"
																	href="CancelApprovalVender.in?AID=${approval.approvalId}&VENID=${approval.approvalvendorID}">
																	Cancel </a>
															</div>
														</div>
													
												</div>
	
												<hr>
											</div>
	
										</fieldset>
									</sec:authorize>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<script type="text/javascript" 
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
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
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>	
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/VendorApprovalAdd.js" />"></script>	