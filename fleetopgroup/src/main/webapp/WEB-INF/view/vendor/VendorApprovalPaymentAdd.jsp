<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">	
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="box">
					<div class="boxinside">
						<div class="row">
							<div class="pull-left">
								<a href="<c:url value="/open.html"/>"><spring:message code="label.master.home" /></a> / <a ref="<c:url value="/VendorApprovalCreated/1.in"/>">Vendor Approvals</a>
							</div>
							<div class="pull-right">
								<a class="btn btn-link" href="<c:url value="/ApprovalPaymentList/1.in"/>">Cancel</a>
							</div>
						</div>
						<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
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
								<a href="ShowVendor.in?vendorId=${approval.approvalvendorID}&page=1" data-toggle="tip" data-original-title="Click Vendor Info">
									<c:out value="${approval.approvalvendorName}" />
								</a>
							</h4>
						</div>
						<div class="row">
							<h4 align="center">${approval.approvalvendorLocation }</h4>
						</div>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>Vendor Type : <a data-toggle="tip" data-original-title="Vendor Type "> 
									<c:out value="${vendor.vendorType}" /></a>
								</li>
								<li>Phone : <a data-toggle="tip" data-original-title="Phone">
									<c:out value="${vendor.vendorPhone}" /></a>
								</li>
								<li>PAN No : <a data-toggle="tip" data-original-title="PAN No">
									<c:out value="${vendor.vendorPanNO}" /></a>
								</li>
								<li>Service GST NO : <a data-toggle="tip" data-original-title="GST NO"> 
									<c:out value="${vendor.vendorGSTNO}" /></a>
								</li>
								<li>GST Registered : <a data-toggle="tip" data-original-title="GST NO"> 
									<c:choose>
										<c:when test="${vendor.vendorGSTRegisteredId == 1}">Turnover Below 25 lakhs GST
										</c:when>
										<c:otherwise>Turnover Above 25 lakhs GST
										</c:otherwise>
									</c:choose></a>
								</li>
							</ul>
						</div>
						<div class="breadcrumb">
							<h5 align="center">${vendor.vendorAddress1}, ${vendor.vendorAddress2}, ${vendor.vendorCity}, ${vendor.vendorState}, ${vendor.vendorCountry}, Pin- ${vendor.vendorPincode}</h5>
						</div>
							<br>
						<fieldset>
							<div class="row">
								<div class="table-responsive">
									<c:if test="${!empty ServiceEntries}">
													<table class="table">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">ID</th>
																<th class="fit ar">Invoice/Job Number</th>
																<th class="fit ar">Invoice Date</th>
																<th class="fit ar">Vendor/Vehicle</th>
																<th class="fit ar">Invoice Cost</th>
																<th class="fit ar">Approved Cost</th>
																<th class="fit ar">Payment Status</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${ServiceEntries}" var="ServiceEntries">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fir ar"><a href="${ServiceEntries.transactionUrl}" target="_blank">${ServiceEntries.transactionNumber}</a></td>
																	<td class="fir ar">${ServiceEntries.invoiceNumber}</td>
																	<td class="fir ar">${ServiceEntries.invoiceDateStr}</td>
																	
																	<c:choose>
																		<c:when test="${ServiceEntries.vehicleNumber != null}">
																			<td class="fir ar">${ServiceEntries.vehicleNumber}</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${approval.approvalvendorName}" />
																			</td>
																		</c:otherwise>
																	</c:choose>	
																	<td class="fir ar">${ServiceEntries.subApprovalTotal}</td>
																	<td class="fir ar">${ServiceEntries.subApprovalPaidAmount}</td>
																	<c:choose>
																		<c:when test="${ServiceEntries.approvedPaymentStatus == 'PAID'}">
																			<td class="fir ar">CLEAR</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${ServiceEntries.approvedPaymentStatus}" />
																			</td>
																		</c:otherwise>
																	</c:choose>		
																	
																</tr>
															</c:forEach>
														</tbody>
														<tfoot>
															<tr class="breadcrumb">
															</tr>
														</tfoot>
													</table>
												</c:if>
								</div>
								<div class="row">
									<div class="col-md-11">
										<div class="col-md-offset-8">
											<table class="table">
												<tbody>
													<tr data-object-id="" class="ng-scope">
														<td class="fit ar"><h4>Approved Total :</h4></td>
														<td class="fit ar"></td>
														<td class="fit ar"><h4> <i class="fa fa-inr"></i> ${totalPaidApprovalAmount}</h4></td>
														<td class="fit"></td>
													</tr>
													<tr data-object-id="" class="ng-scope">
														<td class="fit ar"><h4>Invoice Total :</h4></td>
														<td class="fit ar"></td>
														<td class="fit ar"><h4> <i class="fa fa-inr"></i> ${approval.approvalTotal}</h4></td>
														<td class="fit"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<fieldset>
									<hr>
									<div class="row">
										<div class="row">
											<div class="pull-left">
												<span>Approved By : ${approval.approvalCreateBy}</span>
											</div>
											<div class="pull-right">
												<span>Date : ${approval.approvalCreateDateStr}</span>
											</div>
										</div>
									</div>
								</fieldset>
								<sec:authorize access="hasAuthority('PAYMENT_APPROVEL_VENDOR')">
									<fieldset>
										<input type="hidden" value="${configuration.showTDSAndPayableAmount}" id="TDSandPayableAmount">
										<legend>Payment Information</legend>
												<input type="hidden" name="approvalId" id="approvalId" value="${approval.approvalId}">
												<input type="hidden" name="paymentMode" id="paymentMode" value="${configuration.paymentMode}"> <br>
												
												<c:if test="${!configuration.paymentMode}">
												<div class="row">
													<label class="L-size control-label">Type of Payments <abbr title="required">*</abbr> </label>
													<div class="I-size">
														<div class="form-group">
															<select class="form-text" name="vendorPaymentStatus" id="payment_option" onchange="return addNegotiableAmount(this); " >
																<option value="1">CLEAR</option>
																<option value="5">Negotiation</option>
															</select>
														</div>
													</div>
												</div>
												</c:if>
												<input type="hidden" class="form-text" id="renewal_Amount" name="approvalTotalStr" value="${approvalTotal}" required readonly="readonly">
												<input type="hidden" class="form-text" id="approvalTotal" value="${approval.approvalTotal}" required readonly="readonly">
												<c:if test="${configuration.vendorPaymentFlavor3}">
												
												<div class="row">
													<label class="L-size control-label">Draft Amount <abbr title="required">*</abbr></label>
													<div class="I-size">
														<div class="form-group">
															<input type="text" class="form-text" id="totalPaidApprovalAmount" value="${totalPaidApprovalAmount}" required readonly="readonly">
															<%-- <input type="hidden" class="form-text" id="renewal_Amount1" name="renewal_Amount1" value="${approvalTotalAmt}" > --%>
														</div>
													</div>
												</div>
												</c:if>
												
											
												<c:if test="${configuration.showTDSAndPayableAmount}">
													<div class="row">
														<label class="L-size control-label">TDS Amount <abbr title="required">*</abbr></label>
														<div class="I-size">
															<div class="form-group">
																<input type="text" class="form-text" id="approvalTDSAmount" value="${TDSAmount}" required readonly="readonly">
															</div>
														</div>
													</div>
												
													<div class="row">
														<label class="L-size control-label">Payable Amount <abbr title="required">*</abbr></label>
														<div class="I-size">
															<div class="form-group">
																<input type="text" class="form-text" id="approvalPayableAmount" value="${PayableAmount}" required readonly="readonly">
															</div>
														</div>
													</div>
												</c:if>
												
												
												<c:if test="${!configuration.vendorPaymentFlavor3}">
												<div class="paidAmnt hide" id="paidAmnt">
													<label class="L-size control-label">Paid Amount</label>
													<div class="I-size">
														<input type="text" class="form-text" id="paidAmount" value="${approvalTotal}" onkeyup="calculateNegotiableAmount(this)" onkeypress="return isNumber(event)" name="paidAmount"  placeholder="Please Enter Negotiation Amount" /> 
														<label id="errorvStatus" class="error"></label>
													</div>
												</div>	
												</c:if> 
												<div class="paidAmnt hide">
													<label class="L-size control-label">Negotiable Amount</label>
													<div class="I-size">
														<input type="text" class="form-text" id="negotiableAmount" value="0" name="discountAmount" readonly=  /> 
														<label id="errorvStatus" class="error"></label>
													</div>
												</div>	 
												
												<div class="row">
													<label class="L-size control-label">Modes of Payment <abbr title="required">*</abbr> </label>
													<div class="I-size">
														<div class="form-group" id="paymentDiv">
															<select class="form-text" name="approvalPaymentTypeId" id="Approval_option">
																<option value="1">CASH</option>
																<option value="3">NEFT</option>
																<option value="4">RTGS</option>
																<option value="5">IMPS</option>
																<option value="6">DD</option>
																<option value="7">CHEQUE</option>
															</select>
														</div>
													</div>
												</div>
												<div class="row">
													<label class="L-size control-label" id="target1">Enter </label>
													<div class="I-size">
														<div class="form-group">
															<input type="text" class="form-text" id="approvalPayNumber" name="approvalPayNumber" onkeypress="return IsAlphaNumericPaynumber(event);" ondrop="return false;" maxlength="25" required>
															<label class="error" id="errorPaynumber" style="display: none"> </label>
														</div>
													</div>
												</div>
												<div class="row">
													<label class="L-size control-label">Paid Date <abbr title="required">*</abbr></label>
													<div class="I-size">
														<div class="form-group">
															<div class="input-group input-append date" id="ApprovalPaidDate">
																<input type="text" name="approvalDateofpayment" class="form-text" id ="approvalDateofpaymentOn"  data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" required /> 
																	<span class="input-group-addon add-on"><span class="fa fa-calendar"></span></span>
															</div>
														</div>
													</div>
												</div>
												<input type="hidden" id="allowTallyIntegration" value="${tripConfiguration.allowTallyIntegration}" />
												<c:if test="${tripConfiguration.allowTallyIntegration}">
													<div class="row" id="grpmanufacturer" class="form-group">
													<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
														<div class="I-size">
															<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" 
															  placeholder="Please Enter Tally Company Name" />
														</div>
												</div>
												<br/>
												</c:if>
												<div class="row">
													<label class="L-size control-label">Cashier | Paid By : <abbr title="required">*</abbr> </label>
													<div class="I-size">
														<div class="form-group">
															<input type="text" class="form-text" name="approvalpaidby" value="${userName}" readonly="readonly" onkeypress="return IsAlphaNumericPaidby(event);" ondrop="return false;" maxlength="50" required>
															<label class="error" id="errorPaidby" style="display: none"> </label>
														</div>
														<input type="hidden" id="approvalpaidbyId" name="approvalpaidbyId" value="${userId}" />
													</div>
												</div>
										
												<div class="panel-footer">
													<div class="L-size"></div>
													<div class="I-size">
														<a class="btn btn-success" href="#" onclick="makeVendorApprovalPayment();">Make Payment</a> 
														<a class="btn btn-default" href="ApprovalPaymentList/1.in">Close</a>
													</div>
												</div>
												<br> <br>
												<%@ include file="/WEB-INF/view/payment/PaymentDetails.jsp"%>
									</fieldset>
								</sec:authorize>
							</div>
						</fieldset>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/EditVendorApprovalPaymentAdd.js" />"></script>
<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript">
	function showoption() {
		var t = $("#Approval_option :selected"), e = t.text();
		"CASH" == e ? $("#target1").text(e + " Receipt NO : ") : $("#target1").text(e + " Transaction NO : ")
		}
	$(document).ready(function() {
		 $("#approvalDateofpaymentOn").datepicker({
		        autoclose: !0,
		        todayHighlight: !0,
		        format: "dd-mm-yyyy",
		        endDate: 'currentDate'
		    })
		$("#Approval_option").on("change", function() {showoption()}), $("#Approval_option").change()
		})
</script>
</div>
