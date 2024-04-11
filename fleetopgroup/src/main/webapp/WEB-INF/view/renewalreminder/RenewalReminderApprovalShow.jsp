<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
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
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/RenRemApp/${SelectStatus}/${SelectPage}.in"/>">Renewal
									Reminder Approval</a> / <a
									href="<c:url value="/${SelectStatus}/ShowRenRemApproval.in?AID=${approval.renewalApproval_id}&page=${SelectPage}"/>"
									data-toggle="tip" data-original-title="Click Approval Details">
									<c:out value="RA-${approval.renewalApproval_Number}" />
								</a> / <span id="NewVehi">Show Approval List</span>
							</div>
							<div class="col-md-off-5">
								<div class="col-md-3">
									<form action="<c:url value="/searchRenRemAppShow.in"/>"
										method="post">
										<div class="input-group">
											<span class="input-group-addon"> <span
												aria-hidden="true">RA-</span></span> <input class="form-text"
												id="vehicle_registrationNumber" name="Search" type="number"
												min="1" required="required" placeholder="ID eg: 2343">
											<span class="input-group-btn">
												<button type="submit" name="search" id="search-btn"
													class="btn btn-success btn-sm">
													<i class="fa fa-search"></i>
												</button>
											</span>
										</div>
									</form>
								</div>

								<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
									<a
										href="<c:url value="/PrintRenRemApproval?AID=${approval.renewalApproval_id}"/>"
										target="_blank" class="btn btn-default btn-sm"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize>
								<c:choose>
									<c:when test="${approval.approval_document == true}">
										<sec:authorize access="hasAuthority('DOWNLOND_PURCHASE')">
											<a class="btn btn-default"
												href="${pageContext.request.contextPath}/download/RenRemAppDocument/${approval.approval_document_id}.in">
												<span class="fa fa-download"> Download</span>
											</a>
										</sec:authorize>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-default"
											data-toggle="modal" data-target="#addPurchaseOrderDocument"
											data-whatever="@mdo">
											<i class="fa fa-upload"> Upload</i>
										</button>
									</c:otherwise>
								</c:choose>
								<a
									href="<c:url value="/RenRemApp/${SelectStatus}/${SelectPage}.in"/>"
									data-toggle="tip" data-original-title="Click Back"> Cancel</a>
							</div>
						</div>
						<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
							<div class="row">
								<div class="pull-left">
									<span>Approval Number :
										RA-${approval.renewalApproval_Number}</span>
								</div>
								<div class="pull-right">
									<span>Created Date : ${approval.approvalCreated_Date}</span>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<a
										href="<c:url value="/${SelectStatus}/ShowRenRemApproval.in?AID=${approval.renewalApproval_id}&page=${SelectPage}"/>"
										data-toggle="tip" data-original-title="Click Vehicle Info">
										<c:out value="RA-${approval.renewalApproval_Number}" />
									</a>
								</h4>
							</div>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Approved Date : <a data-toggle="tip"
										data-original-title="Created Date"> <c:out
												value="${approval.approvalCreated_Date}" /></a></li>
									<li>Approved By : <a data-toggle="tip"
										data-original-title="Created by"><c:out
												value="${approval.approvalCreated_By}" /></a></li>
									<li>Payment Type : <a data-toggle="tip"
										data-original-title="Payment Type"><c:out
												value="${approval.approvalPayment_Type}" /></a></li>
									<li>Payment Number : <a data-toggle="tip"
										data-original-title="Payment Number"><c:out
												value="${approval.approvalPay_Number}" /></a></li>
									<li>Payment By : <a data-toggle="tip"
										data-original-title="Payment by"><c:out
												value="${approval.approvalPayment_By}" /></a></li>
									<li>Payment Date : <a data-toggle="tip"
										data-original-title="Payment Date"><c:out
												value="${approval.approvalPayment_Date}" /></a></li>
								</ul>
							</div>
						</sec:authorize>
						<c:if test="${param.saveapproval eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Renewal Reminder Approval Payment Update Successfully .
							</div>
						</c:if>
						<br>
						<!-- Modal -->
						<div class="modal fade" id="addDDnumber" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<form action="<c:url value="/UpdateDDnumberRenRem.in"/>"
										method="post" name="vehicleType">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="VehicleType">Add DD Number</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<label class="L-size control-label" id="Type">Vehicle
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" class="form-text" value=""
														required="required" name="AID_CANCEL" id="TR_AMOUNT_ID" />
													<!-- Tyre Retread Id -->
													<input type="hidden" class="form-text" value=""
														required="required" name="renewal_id" id="TRID" />
													<!-- Tyre Serial Num -->
													<input type="text" class="form-text" value=""
														id="VEHCLE_NUMBER" readonly="readonly" required="required" />
													<label id="errorvType" class="error"></label>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label">Modes of Payment
													<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select class="form-text" name="paymentTypeId"
														id="renPT_option">
														<option value="6">DD</option>
														<option value="1">CASH</option>
														<option value="2">CREDIT</option>
														<option value="3">NEFT</option>
														<option value="4">RTGS</option>
														<option value="5">IMPS</option>
														<option value="7">CHEQUE</option>
													</select>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label" id="Type">DD
													number :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<!-- Amount Num -->

													<input type="text" class="form-text"
														name="renewal_PayNumber" placeholder="Enter DD Number"
														onkeypress="return IsAlphaNumericPaynumber(event);"
														ondrop="return false;" maxlength="25"> <label
														class="error" id="errorPaynumber" style="display: none">
													</label>
												</div>
											</div>
											<br />
											<div class="row" id="grppaidDate" class="form-group">

												<label class="L-size control-label" for="renewalpaidDate">DD
													Date :<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<div class="input-group input-append date" id="paidDate">
														<input type="text" class="form-text"
															name="renewal_dateofpayment" id="renewalpaidDate"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"><span
															class="fa fa-calendar"></span></span>
													</div>
													<span id="paidDateIcon" class=""></span>
													<div id="paidDateErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-success">
												<span id="Save">Update DD Number</span>
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
						<!-- Cancel Approval Reminder -->
						<div class="modal fade" id="cancelRenewalAmount" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<form action="<c:url value="/UpdateCancelRenRem.in"/>"
										method="post" name="vehicleType">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="VehicleType">Cancel Approval
												Renewal Reminder</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<label class="L-size control-label" id="Type">Vehicle
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" class="form-text" value=""
														required="required" name="CANCEL_APPROVAL_ID"
														id="CEN_APPROVAL_ID" />
													<!-- Tyre Retread Id -->
													<input type="hidden" class="form-text" value=""
														required="required" name="CANCEL_RENEWAL_ID"
														id="RENEWAL_ID" />
													<!-- Tyre Serial Num -->
													<input type="text" class="form-text" value=""
														id="REN_VEL_NUMBER" readonly="readonly"
														required="required" /> <label id="errorvType"
														class="error"></label>
												</div>
											</div>

											<div class="row">
												<label class="L-size control-label">Cancel Reason :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<textarea rows="3" cols="" class="form-text"
														name="CANCEL_COMMENT" required="required"
														onkeypress="return IsAlphaNumericNumber(event);"
														ondrop="return false;" maxlength="120"></textarea>
													<label class="error" id="errorNumber" style="display: none">
													</label>
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-success">
												<span id="Save">Cancel</span>
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
						<div class="modal fade" id="addPurchaseOrderDocument"
							role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post"
										action="<c:url value="/uploadRenRemAppDocument.in"/>"
										enctype="multipart/form-data">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">Renewal Payment Approval
												Document</h4>
										</div>
										<div class="modal-body">
											<div class="form-horizontal ">
												<input type="hidden" name="renewalApproval_id"
													value="${approval.renewalApproval_id}">
												<div class="row">
													<div class="L-size">
														<label class="L-size control-label"> Browse:<abbr
															title="required">*</abbr>
														</label>
													</div>
													<div class="I-size">
														<input type="file"
															accept="image/png, image/jpeg, image/gif"
															name="input-file-preview" required="required" />
													</div>
												</div>
												<br />
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">
												<span>Upload Document</span>
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
						<fieldset>
							<div class="row">
								<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
									<div class="">
										<table class="table">
											<thead>
												<tr class="breadcrumb">
													<th class="fit">ID</th>
													<th class="fit">RRID</th>
													<th class="fit ar">Vehicle Name</th>
													<th class="fit ar">Renewal / Validity From-To</th>
													<th class="fit ar">Pay-Type</th>
													<th class="fit ar">Amount</th>
													<th class="fit ar">Status</th>
													<th class="fit ar">DD No</th>
													<th class="fit ar">Cancel</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty renewal}">
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${renewal}" var="renewal">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit"><a
																href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_id}" />"
																data-toggle="tip"
																data-original-title="Click this Renewal Details"><c:out
																		value="RR-${renewal.renewal_R_Number}" /> </a></td>
															<td class="fit ar"><a
																href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_id}" />"
																data-toggle="tip"
																data-original-title="Click Renewal Details"><c:out
																		value="${renewal.vehicle_registration}" /> </a></td>
															<td class="fit ar"><c:out
																	value="${renewal.renewal_type}" /> <c:out
																	value="  ${renewal.renewal_subtype}" /><br> <c:out
																	value="${renewal.renewal_from} -to- " /> <c:out
																	value="${renewal.renewal_to}" /></td>
															<td><c:out value="${renewal.renewal_paymentType}" /><br>
																<c:out value="${renewal.renewal_PayNumber}" /></td>
															<td class="fit ar"><span class="badge"> <c:out
																		value="${renewal.renewal_Amount}" />
															</span></td>
															<td class="fit ar"><c:choose>
																	<c:when test="${renewal.renewal_staus_id == 2}">
																		<span class="label label-success"><i
																			class="fa fa-check-square-o"></i> <c:out
																				value=" ${renewal.renewal_status}" /></span>
																	</c:when>
																	<c:when
																		test="${renewal.renewal_staus_id == 4}">

																		<sec:authorize access="hasAuthority('ADD_RENEWAL')">
																			<a class="btn btn-success btn-sm" target="_blank"
																				href="<c:url value="/approvalRenRemUpload.in?RID=${renewal.renewal_id}"/>"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Upload File  ')">
																				<span class="fa fa-upload"> Upload</span>
																			</a>
																		</sec:authorize>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-danger"> <c:out
																				value=" ${renewal.renewal_status}" /></span>
																	</c:otherwise>
																</c:choose></td>
															<td class="fit ar"><c:choose>
																	<c:when test="${renewal.renewal_staus_id == 2}">
																		<c:if test="${renewal.renewal_document == true}">
																			<sec:authorize
																				access="hasAuthority('DOWNLOND_RENEWAL')">
																				<a
																					href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_document_id}.in">
																					<span class="fa fa-download"> Download</span>
																				</a>
																			</sec:authorize>
																		</c:if>
																	</c:when>
																	<c:when
																		test="${renewal.renewal_staus_id == 4}">
																		 <sec:authorize
																			access="hasAuthority('PAYMENT_APPROVEL_RENEWAL')"> 
																			<a class="btn btn-info btn-sm"
																				onclick="javascript:add_NewDDNumberRenRem('${renewal.renewal_approvedID}', '${renewal.renewal_id}',  '${renewal.vehicle_registration}');"
																				id="editTyreSerialInput"> <span
																				class="fa fa-plus"></span> DD Number
																			</a>
																		 </sec:authorize> 
																	</c:when>
																</c:choose></td>
															<td class="fit ar"><c:choose>
																	<c:when
																		test="${renewal.renewal_staus_id == 4}">
																		 <sec:authorize
																			access="hasAuthority('DELETE_APPROVEL_RENEWAL')"> 
																			<a class="btn btn-danger btn-sm"
																				onclick="javascript:Cancel_RenRem_Amount('${renewal.renewal_approvedID}', '${renewal.renewal_id}',  '${renewal.vehicle_registration}');"
																				id="editTyreSerialInput"> <span
																				class="fa fa-trash"></span> Cancel
																			</a>
																	 </sec:authorize> 
																	</c:when>
																	<c:when test="${renewal.renewal_staus_id == 6}">
																		<sec:authorize access="hasAuthority('EDIT_RENEWAL')">
																			<a
																				href="<c:url value="/editRenewalReminder.in?renewal_id=${renewal.renewal_id}" />">
																				<i class="fa fa-edit"></i> Edit
																			</a>
																		</sec:authorize>
																	</c:when>
																</c:choose></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
											<tfoot>
												<tr class="breadcrumb">
												</tr>
												<tr data-object-id="" class="ng-scope">
													<td class="fit ar" colspan="4"></td>
													<td class="fit ar"><h4>Total :</h4></td>
													<td class="fit ar"><h4>
															<i class="fa fa-inr"></i> ${approvalTotal}
														</h4></td>
													<td class="fit ar"></td>
													<td class="fit ar"></td>
												</tr>
											</tfoot>
										</table>
									</div>
									<fieldset>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-offset-5">
													<table class="table">
														<tbody>
															<tr style="color: blue;">
																<td><h4>Total Approval Payment :</h4></td>
																<td><h4>
																		<i class="fa fa-inr"></i> ${approvalTotal}
																	</h4></td>
															</tr>
															<tr style="color: green;">
																<td><h4>Total Received Approval :</h4></td>
																<td><h4>
																		<i class="fa fa-inr"></i> ${ApprovedAmount}
																	</h4></td>
															</tr>
															<tr style="color: red;">
																<td><h4>Pending Approval Payment :</h4></td>
																<td><h4>
																		<i class="fa fa-inr"></i> ${PendingAmount}
																	</h4></td>
															</tr>
															<tr style="color: black;">
																<td><h4>Cancel Approval Payment :</h4></td>
																<td><h4>
																		<i class="fa fa-inr"></i> ${CancelAmount}
																	</h4></td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</fieldset>
								</sec:authorize>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${approval.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${approval.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${approval.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${approval.lastupdated}" /></small>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>
	<script type="text/javascript">
		$(function() {

			$('[data-toggle="popover"]').popover()
		})
	</script>
	<script type="text/javascript">
		function add_NewDDNumberRenRem(Approval_id, AppRen_iD, Vehicle_name) {
			document.getElementById("TR_AMOUNT_ID").value = Approval_id;
			document.getElementById("TRID").value = AppRen_iD;
			document.getElementById("VEHCLE_NUMBER").value = Vehicle_name;

			$("#addDDnumber").modal();
		}
		function Cancel_RenRem_Amount(Approvaled_id, AppRenRem_iD,
				Vehicleed_name) {
			document.getElementById("CEN_APPROVAL_ID").value = Approvaled_id;
			document.getElementById("RENEWAL_ID").value = AppRenRem_iD;
			document.getElementById("REN_VEL_NUMBER").value = Vehicleed_name;

			$("#cancelRenewalAmount").modal();
		}
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>