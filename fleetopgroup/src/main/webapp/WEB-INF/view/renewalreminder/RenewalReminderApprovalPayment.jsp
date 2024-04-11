<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
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
									href="<c:url value="/RenRemApp/${SelectStatus}/${SelectPage}.in"/>">Renewal Reminder
									Approval</a> / <a
									href="<c:url value="/${SelectStatus}/ShowRenRemApproval.in?AID=${approval.renewalApproval_id}&page=${SelectPage}"/>"
									data-toggle="tip" data-original-title="Click Approval Details">
									<c:out value="RA-${approval.renewalApproval_Number}" />
								</a> / <span id="NewVehi">Show Approval List</span>
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
									<a
										href="<c:url value="/PrintRenRemApproval?AID=${approval.renewalApproval_id}"/>"
										target="_blank" class="btn btn-default btn-sm"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize>
								<button type="button" class="btn btn-default"
									data-toggle="modal" data-target="#addPurchaseOrderDocument"
									data-whatever="@mdo">
									<i class="fa fa-upload">Upload</i>
								</button>

								<a href="<c:url value="/RenRemApp/${SelectStatus}/${SelectPage}.in"/>" data-toggle="tip"
									data-original-title="Click Back"> Cancel</a>
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
									<li>Created Date : <a data-toggle="tip"
										data-original-title="Created Date"> <c:out
												value="${approval.approvalCreated_Date}" /></a></li>
									<li>Created By : <a data-toggle="tip"
										data-original-title="Created by"><c:out
												value="${approval.approvalCreated_By}" /></a></li>
								</ul>
							</div>
						</sec:authorize>
						<br>
						<div class="modal fade" id="addPurchaseOrderDocument"
							role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post" action="uploadRenRemAppDocument.in"
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
													<th class="fit ar">Renewal Types</th>
													<th class="fit ar">Validity From</th>
													<th class="fit ar">Validity To</th>
													<th class="fit ar">Amount</th>
													<th class="fit ar"></th>
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
																	value="  ${renewal.renewal_subtype}" /></td>

															<td class="fit ar"><c:out
																	value="${renewal.renewal_from}" /></td>
															<td><c:out value="${renewal.renewal_to}" /></td>
															<td class="fit ar"><span class="badge"> <c:out
																		value="${renewal.renewal_Amount}" />
															</span></td>
															<td class="fit ar"></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
											<tfoot>
												<tr class="breadcrumb">
												</tr>
												<tr data-object-id="" class="ng-scope">
													<td class="fit ar" colspan="5"></td>
													<td class="fit ar"><h4>Total :</h4></td>
													<td class="fit ar"><h4>
															<i class="fa fa-inr"></i> ${approvalTotal}
														</h4></td>
												</tr>
											</tfoot>
										</table>
									</div>
									<fieldset>
										<hr>
										<div class="row">
											<div class="row">
												<div class="pull-left">
													<span>Approved By : ${approval.approvalCreated_By}</span>
												</div>

												<div class="pull-right">
													<span>Date : ${approval.approvalCreated_Date}</span>
												</div>
											</div>
										</div>
									</fieldset>
								</sec:authorize>
								<sec:authorize access="!hasAuthority('PAYMENT_APPROVEL_RENEWAL')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('PAYMENT_APPROVEL_RENEWAL')">
									<fieldset>
										<legend>Payment Information</legend>
										<form id="frm-example" action="<c:url value="/UpdateApprovalRenRemPayment.in"/>"
											method="POST">
											<input type="hidden" name="renewalApproval_id"
												value="${approval.renewalApproval_id}"> <br>
											<div class="row">
												<label class="L-size control-label">Draft Amount <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="form-group">
														<input type="text" class="form-text"
															name="approvalPending_Amount"
															value="${approval.approvalPayment_Amount}" required
															readonly="readonly">
													</div>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label">Modes of Payment
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="form-group">
														<select class="form-text" name="paymentTypeId"
															id="Approval_option">
															<option value="1">CASH</option>
															<option value="6">DD</option>
															<option value="7">CHEQUE</option>
															<option value="8">BANK DRAFT</option>
															<option value="3">NEFT</option>
															<option value="4">RTGS</option>
															<option value="5">IMPS</option>
														</select>
													</div>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label" id="target1">Enter
												</label>
												<div class="I-size">
													<div class="form-group">
														<input type="text" class="form-text"
															name="approvalPay_Number"
															onkeypress="return IsAlphaNumericPaynumber(event);"
															ondrop="return false;" maxlength="25" required> <label
															class="error" id="errorPaynumber" style="display: none">
														</label>
													</div>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label">Paid Date <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="form-group">
														<div class="input-group input-append date"
															id="ApprovalPaidDate">
															<input type="text" class="form-text"
																name="approvalPayment_Date"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																required /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span>
														</div>
													</div>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label">Cashier | Paid
													By : <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="form-group">
														<input type="text" class="form-text"
															name="approvalPayment_By" value="${userName}"
															readonly="readonly"
															onkeypress="return IsAlphaNumericPaidby(event);"
															ondrop="return false;" maxlength="50" required> <label
															class="error" id="errorPaidby" style="display: none">
														</label>

													</div>
													<input type="hidden" name="approvalPayment_ById" value="${userId}"/>
												</div>
											</div>
											<div class="panel-footer">
												<div class="L-size"></div>
												<div class="I-size">
													<input class="btn btn-success" type="submit"
														value="Make Payment"> <a class="btn btn-default"
														href="<c:url value="/RenRemApp/${SelectStatus}/${SelectPage}.in"/>">Close</a>
												</div>
											</div>
											<br> <br>
										</form>
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
	<script type="text/javascript">
		function showoption() {
			var t = $("#Approval_option :selected"), e = t.text();
			"CASH" == e ? $("#target1").text(e + " Receipt NO : ") : $(
					"#target1").text(e + " Transaction NO : ")
		}
		$(document).ready(function() {
			$("#Approval_option").on("change", function() {
				showoption()
			}), $("#Approval_option").change()
		})
	</script>
</div>