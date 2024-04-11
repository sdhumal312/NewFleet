<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/RenRemApp/${SelectStatus}/1.in"/>">Renewal
						Reminders Approval</a>
				</div>
				<div class="pull-right">

					<a class="btn btn-link"
						href="<c:url value="/RenRemApp/${SelectStatus}/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="col-md-11">
					<div class="main-body">
						<div class="row">
							<div class="main-body">
								<h4>Create Approval Renewal Reminder List Search Date
									${RRDate}</h4>
								<div class="box">
									<div class="box-body">
										<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
											<spring:message code="message.unauth"></spring:message>
										</sec:authorize>
										<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
											<form id="frm-example"
												action="ApprovalRenewalReminderList.in" method="POST">
												<input type="hidden" name="SearchDate" value="${RRDate}"
													required="required">
												<input type="hidden" name="Selectfuel_id" value="" id="Selectfuel_id" >
											
												<table id="VendorApplovalList"
													class="table table-bordered table-striped">
													<thead>
														<tr>
															<th class="fit"><input name="select_all" value="1"
																id="example-select-all" type="checkbox" /></th>
															<th class="fit ar">RRID</th>
															<th>Vehicle</th>
															<th>Type</th>
															<th>Sub Type</th>
															<th>Receipt</th>
															<th>From</th>
															<th>To</th>
															<th>Amount</th>
														</tr>
													</thead>
													<tfoot>
														<tr>
															<th class="fit"></th>
															<th class="fit ar">RRID</th>
															<th>Vehicle</th>
															<th>Type</th>
															<th>Sub Type</th>
															<th>Receipt</th>
															<th>From</th>
															<th>To</th>
															<th>Amount</th>
														</tr>
													</tfoot>
													<tbody id="vendorList">
														<c:if test="${!empty renewal}">
															<c:forEach items="${renewal}" var="renewal">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit"><input name="Selectfuel_id1"
																		value="${renewal.renewal_id}" id="example_${renewal.renewal_id}" onclick="countCheck(this.id,this.checked);"
																		type="checkbox" /></td>
																	<td><a
																		href="<c:url value="/showRenewalReminderDetails?renewalId=${renewal.renewal_id}" />"
																		data-toggle="tip"
																		data-original-title="Click this Renewal Details"><c:out
																				value="RR-${renewal.renewal_R_Number}" /> </a></td>
																	<td class="fit ar"><c:out
																			value="${renewal.vehicle_registration}" /></td>
																	<td class="fit ar"><c:out
																			value="${renewal.renewal_type}" /></td>
																	<td class="fit ar"><c:out
																			value="${renewal.renewal_subtype}" /></td>
																	<td class="fit ar"><c:out
																			value="${renewal.renewal_receipt}" /></td>
																	<td class="fit ar"><c:out
																			value="${renewal.renewal_from}" /></td>
																	<td><c:out value="${renewal.renewal_to}" /></td>
																	<td class="fit ar"><span class="badge"> <c:out
																				value="${renewal.renewal_Amount}" />
																	</span></td>

																</tr>
															</c:forEach>
														</c:if>
													</tbody>
												</table>
												<div align="center">
													<sec:authorize access="hasAuthority('ADD_RENEWAL')">
														<button class="btn btn-success" data-toggle="tip"
															data-original-title="Please Select Any One"
															data-toggle="modal" data-target="#processing-modal" >Create</button>
													</sec:authorize>
													<a class="btn btn-link"
														href="<c:url value="/RenRemApp/${SelectStatus}/1.in"/>">Cancel</a>
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

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.columnFilter.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderApprovalFilter.js" />"></script>	
	

