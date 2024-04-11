<%@ include file="taglib.jsp"%>
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
									href="<c:url value="/RenRemApp/1/1.in"/>">Renewal
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
										href="PrintRenRemApproval?AID=${approval.renewalApproval_id}"
										target="_blank" class="btn btn-default btn-sm"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize>
								<a href="<c:url value="/RenRemApp/1/1.in"/>"
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
									<li>Created Date : <a data-toggle="tip"
										data-original-title="Created Date"> <c:out
												value="${approval.approvalCreated_Date}" /></a></li>
									<li>Created By : <a data-toggle="tip"
										data-original-title="Created by"><c:out
												value="${approval.approvalCreated_By}" /></a></li>
								</ul>
							</div>
						</sec:authorize>
						<c:if test="${param.Already eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Already Available Renewal reminder document is are <br>
								${VMandatory}.
							</div>
						</c:if>
						<c:if test="${param.Success eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Renewal Reminder Created Approval Successfully .
							</div>
						</c:if>
						<c:if test="${param.deleteRenewalReminder eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Renewal Reminder Deleted successfully .
							</div>
						</c:if>
						<c:if test="${param.closeStatus eq true}">
							<div class="alert alert-danger">
								<button class="close" data-dismiss="alert" type="button">x</button>
								${VMandatory}<br> You should be close first TripSheet or
								change status or close workOrder .
							</div>
						</c:if>
						<br>
						<!-- Modal -->
						<div class="modal fade" id="editTyreRetreadNumber" role="dialog">
							<div class="modal-dialog">
								<!-- Modal content-->
								<div class="modal-content">
									<form action="<c:url value="/UpdateApprovalRenRemAmount.in"/>"
										method="post" name="vehicleType">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title" id="VehicleType">Edit Renewal
												Approval Amount</h4>
										</div>
										<div class="modal-body">
											<div class="row">
												<label class="L-size control-label" id="Type">Vehicle
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" class="form-text" value=""
														required="required" name="APPROVAL_ID" id="TR_AMOUNT_ID" />
													<!-- Tyre Retread Id -->
													<input type="hidden" class="form-text" value=""
														required="required" name="RENEWAL_ID" id="TRID" />
													<!-- Tyre Serial Num -->
													<input type="text" class="form-text" value=""
														id="TYRE_NUMBER" readonly="readonly" required="required" />
													<label id="errorvType" class="error"></label>
												</div>
											</div>
											<div class="row">
												<label class="L-size control-label" id="Type">Amount
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<!-- Amount Num -->

													<input type="text" class="form-text" data-toggle="tip"
														data-original-title="Renewal Cost" value=""
														name="RENEWAL_AMOUNT" placeholder="Renewal Cost"
														required="required"
														onkeypress="return isLabertimeKeyQut(event);"
														id="laberhourscost" min="0.0">
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-success">
												<span id="Save">Change Amount</span>
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
													<th class="fit ar">Edit</th>
													<th class="fit ar">Remove</th>
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
																href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_R_Number}" />"
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
															<td class="fit ar"> <sec:authorize
																	access="hasAuthority('DELETE_APPROVEL_RENEWAL')">
																	<a
																		onclick="javascript:edit_RenewaLAmount('${approval.renewalApproval_id}', '${renewal.renewal_id}',  '${renewal.vehicle_registration}',  '${renewal.renewal_Amount}');"
																		id="editTyreSerialInput"> <span
																		class="fa fa-pencil"></span> Edit
																	</a>
																 </sec:authorize></td>
															<td class="fit ar"> <sec:authorize
																	access="hasAuthority('DELETE_APPROVEL_RENEWAL')">
																	<a
																		href="<c:url value="/RemoveApprovalRenRem.in?AID=${approval.renewalApproval_id}&RRID=${renewal.renewal_id}" />"
																		data-toggle="tip" data-original-title="Click Remove"><font
																		color="red"><i class="fa fa-times"> Remove</i></font></a>
																 </sec:authorize> </td>
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
													<td class="fit"></td>
												</tr>
											</tfoot>
										</table>
									</div>

									<div class="row">
										<div class="col-md-11">
											<div>
												<sec:authorize access="hasAuthority('ADD_APPROVEL_RENEWAL')">
													<a class="btn btn-success btn-sm"
														href="<c:url value="/addApprovalRenRem.in?AID=${approval.renewalApproval_id}"/>">
														<span class="fa fa-plus"></span> Add Approval Renewal
													</a>
												</sec:authorize>
											</div>
										</div>
									</div>
								</sec:authorize>
								<sec:authorize access="!hasAuthority('ADD_APPROVEL_RENEWAL')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_APPROVEL_RENEWAL')">
									<fieldset>
										<legend>Approval Info</legend>
										<div class="col-md-11">
											<div class="col-md-offset-4">
												<form id="frm-example" action="UpdateApprovalRenRem.in"
													method="POST">
													<input type="hidden" name="renewalApproval_id"
														value="${approval.renewalApproval_id}">
													<div class="row1">
														<label class="L-size control-label" for="issue_vehicle_id">
															Approved By :<abbr title="required">*</abbr>
														</label>
														<div class="I-size">
															<input type="text" class="form-text" readonly="readonly"
																name="approvalCreated_By" required="required"
																value="${approval.approvalCreated_By}" maxlength="75"
																onkeypress="return IsVendorName(event);"
																ondrop="return false;"> <label class="error"
																id="errorVendorName" style="display: none"> </label>
														</div>
														<input type="hidden" name="approvalCreated_ById" id="approvalCreated_ById" value="${approval.approvalCreated_ById}"/>
													</div>
													<br> <br>
													<div class="row1">
														<div class="col-md-offset-5 I-size">
															<input class="btn btn-success" name="commit"
																type="submit" value="Approve" data-toggle="modal"
																data-target="#processing-modal">
															<sec:authorize
																access="hasAuthority('DELETE_APPROVEL_RENEWAL')">
																<a class="btn btn-danger" class="confirmation"
																	onclick="return confirm('Are you sure? Delete ')"
																	href="<c:url value="/${SelectStatus}/CancelRenRemApproval.in?AID=${approval.renewalApproval_id}&page=${SelectPage}"/>">
																	Cancel </a>
															</sec:authorize>
														</div>
													</div>
												</form>
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
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${approval.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${approval.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${approval.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${approval.lastupdated}" /></small>
		</div>
	</section>
	<script type="text/javascript">
		$(function() {

			$('[data-toggle="popover"]').popover()
		})
	</script>
	<script type="text/javascript">
		function edit_RenewaLAmount(Approval_id, AppRen_iD, Vehicle_name,
				Amount) {
			document.getElementById("TR_AMOUNT_ID").value = Approval_id;
			document.getElementById("TRID").value = AppRen_iD;
			document.getElementById("TYRE_NUMBER").value = Vehicle_name;
			document.getElementById("laberhourscost").value = Amount;

			$("#editTyreRetreadNumber").modal();
		}
	</script>
</div>
