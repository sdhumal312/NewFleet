<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/VendorApprovalCreated/1.in"/>">Vendor Approvals</a> /
					<a href="<c:url value="/ApprovalPaymentList/1.in"/>">Vendor
						Approved Payment</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<table id="approvalTableReport"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">Approval Id</th>
										<th>Vendor Name</th>
										<th class="fit ar">Vendor Type</th>
										<th class="fit ar">Create Date</th>
										<th class="fit ar">Approved By</th>
										<th class="fit ar">Amount</th>
										<th class="fit ar">Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty approval}">
										<c:forEach items="${approval}" var="approval">
											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="ShowApproval.in?approvalId=${approval.approvalId}"><c:out
															value="A-${approval.approvalNumber}" /></a></td>
												<td><a
													href="ShowApproval.in?approvalId=${approval.approvalId}"
													data-toggle="tip"
													data-original-title="Click this Vendor Details"> <c:out
															value="${approval.approvalvendorName} - ${approval.approvalvendorLocation}" />
												</a></td>
												<td class="fit ar"><c:out
														value="${approval.approvalvendorType}" /></td>
												<td class="fit ar"><c:out value="${approval.created}" /></td>
												<td class="fir ar"><c:out
														value="${approval.approvalCreateBy}" /></td>
												<td class="fir ar"><c:out
														value="${approval.approvalTotal}" /></td>
												<td class="fir ar"><c:choose>
														<c:when test="${approval.approvalStatusId == 3}">
															<span class="label label-pill label-warning"><c:out
																	value="${approval.approvalStatus}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${approval.approvalStatus}" /></span>
														</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${approval.approvalStatusId == 3}">
															<div class="row1">
																<sec:authorize
																	access="hasAuthority('DELETE_APPROVEL_VENDOR')">
																	<a class="btn btn-danger btn-sm"
																		href="CancelApprovalVender.in?VENID=0&AID=${approval.approvalId}"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Delete ')">
																		<span class="fa fa-trash"></span> Delete
																	</a>
																</sec:authorize>
																<sec:authorize
																	access="hasAuthority('PAYMENT_APPROVEL_VENDOR')">
																	<a class="btn btn-success btn-sm"
																		href="approvedPayment.in?approvalId=${approval.approvalId}"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Payment  ')">
																		<span class="fa fa-cog"> Make Payment</span>
																	</a>
																</sec:authorize>
															</div>
														</c:when>
														<c:when test="${approval.approvalStatus == null}">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>

																<ul class="dropdown-menu pull-right">
																	<li><a
																		href="approvedList.in?approvalId=${approval.approvalId}"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Approve ')">
																			<span class="fa fa-trash"></span> Approval List
																	</a></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_APPROVEL_VENDOR')">
																			<a
																				href="CancelApprovalVender.in?VENID=0&AID=${approval.approvalId}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>

																</ul>
															</div>
														</c:when>
													</c:choose></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VA/NewApprovallanguage.js" />"></script>
</div>