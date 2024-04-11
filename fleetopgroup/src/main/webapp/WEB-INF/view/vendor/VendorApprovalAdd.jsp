<%@ include file="taglib.jsp"%>
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
								<a href="showVehicle.in?vid=${approval.approvalId}" data-toggle="tip" data-original-title="Click Vehicle Info">
									<c:out value="${approval.approvalvendorName}" />
								</a> / <span id="NewVehi">Show Vendor Approval List</span>
							</div>
							<div class="pull-right">
								<a href="showVehicle.in?vid=${approval.approvalId}" data-toggle="tip" data-original-title="Click Vehicle Info"> Cancel</a>
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
									<span>Created Date : ${approval.createdOn}</span>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<a href="showVehicle.in?vid=${approval.approvalId}"
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
										<c:when test="${vendor.vendorGSTRegisteredId == 1}">

																Turnover Below 25 lakhs GST
										</c:when>
										<c:otherwise>
																Turnover Above 25 lakhs GST

										</c:otherwise>
									</c:choose></a></li>
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
								<form id="frm-example" action="UpdateApprovalList.in" method="POST">
								<sec:authorize access="!hasAuthority('ADD_APPROVEL_VENDOR')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_APPROVEL_VENDOR')">
									<div class="">
										<table class="table">
											<thead>
												<tr class="breadcrumb">
													<th class="fit">No</th>
													<th class="fit ar">Fuel ID</th>
													<th class="fit ar">Vehicle</th>
													<th class="fit ar">Group</th>
													<th class="fit ar">Date</th>
													<th class="fit ar">odometer</th>
													<th class="fit ar">Volume</th>
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
												<c:if test="${!empty fuel}">
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${fuel}" var="fuel">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar">
															<input type="hidden" id="invoiceId_${fuel.fuel_id}"  name="multipleApproval" value="${fuel.fuel_id}">
																<a href="showFuel.in?FID=${fuel.fuel_id}" data-toggle="tip" data-original-title="Click Fuel Details">
																<c:out value="FT-${fuel.fuel_Number}" /></a>
															</td>
															<td class="fit ar">
																<a href="showFuel.in?FID=${fuel.fuel_id}" data-toggle="tip" data-original-title="Click Fuel Details">
																<c:out value="${fuel.vehicle_registration}" /> </a>
															</td>
															<td class="fit ar">
																<c:out value="${fuel.vehicle_Ownership}" /> <br> 
																<c:out value="${fuel.vehicle_group}" />
															</td>

															<td class="fit ar"><c:out value="${fuel.fuel_date}" /></td>
															<td class="fit ar"><c:out value="${fuel.fuel_meter}" /></td>
															<td class="fit ar"><abbr data-toggle="tip" data-original-title="Liters">
																<c:out value="${fuel.fuel_liters}" /></abbr> 
																<c:if test="${fuel.fuel_tank_partial==1}">
																	<abbr data-toggle="tip" data-original-title="Partial fuel-up"> 
																	<i class="fa fa-adjust"></i>
																	</abbr>
																</c:if> <br> 
																<c:out value="${fuel.fuel_type}" />
															</td>
															<td class="fit ar"><i class="fa fa-inr"></i> 
																<c:out value="${fuel.fuel_amount}" /> <br> 
																<abbr data-toggle="tip" data-original-title="Price"> 
																<c:out value="${fuel.fuel_price}/liters" />
																</abbr>
															</td>
															<c:if test="${configuration.vendorPaymentFlavor3}">
																<td >
																	<select style="width:100px;" id="PaymentType_${fuel.fuel_id}" name="approvedPaymentStatusId" class="form-text" onchange="changePaymentType(this)" >
																		<option value ="1"> Clear </option>
																		<option value ="5" > Negotiate </option>
																	</select>
																</td>
																<td style="width: 80px;">
																	<input type="hidden" value="${fuel.fuel_amount}" id="balAmt_${fuel.fuel_id}">
																	<input name="paidAmount" class="form-text" placeholder="Received Amount" readonly="readonly" value="0" id="receivedAmt_${fuel.fuel_id}" type="text" onkeyup="calculateBalance(this)" onkeypress="return isNumber(event)" />
																</td>
																<td style="width: 80px;">
																	<input name="balanceAmount" class="form-text" placeholder="Balance Amount" value="${fuel.balanceAmount}" id="balanceAmt_${fuel.fuel_id}" readonly="readonly" type="text" onkeypress="return isNumber(event)" />
																</td>
																<td id="date" style="width:150px;"  class="input-group input-append date" >
																	<input type="text" id="expectedPaymentDate" class="form-text" name="expectedPaymentDate" required="required" data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" />
																	 <div class="input-group-addon add-on"> 
																	 <span class="fa fa-calendar"></span>
																	 </div> 
																</td>
															</c:if>
															<c:if test="${!configuration.vendorPaymentFlavor3}">
																<input type="hidden" name="approvalStatusId">
																<input type="hidden" name="expectedPaymentDate">
															</c:if>
															
															<td class="fit ar">
																<a href="RemoveApprovalList.in?fuel_id=${fuel.fuel_id}" data-toggle="tip" data-original-title="Click Remove">
																<font color="red"><i class="fa fa-times"> Remove</i></font></a>
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
															<td class="fit ar"><h4>
																<i class="fa fa-inr"></i> ${approvalTotal}
																</h4>
															</td>
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
													<label class="L-size control-label" for="issue_vehicle_id">
														Approved By :<abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text" readonly="readonly" name="approvalCreateBy" required="required"
															value="${approval.approvalCreateBy}" maxlength="75" onkeypress="return IsVendorName(event);"
															ondrop="return false;"> 
														<label class="error" id="errorVendorName" style="display: none"> </label>
													</div>
													<input type="hidden" name="approvalCreateById"  value="${approval.approvalCreateById }" id="approvalCreateById" />
												</div>
												<br> <br>
												<div class="row1">
													<div class="col-md-offset-5 I-size">
														<input class="btn btn-success" name="commit" type="submit" value="Approve" data-toggle="modal"
															data-target="#processing-modal"> 
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
								</form>
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