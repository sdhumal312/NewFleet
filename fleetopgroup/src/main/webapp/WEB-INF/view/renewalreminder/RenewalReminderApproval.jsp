<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/RenRemApp/${SelectStatusId}/1.in"/>">Renewal
						Reminders Approval</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_APPROVEL_RENEWAL')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
							class="fa fa-search"></span> Filter Approval Renewal
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-clock-o"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total ${SelectStatus} Renewal</span>
							<input type="hidden" value="${SelectStatus}" id="statues"><span
								class="info-box-number">${RenewalReminderCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Approval</span>
							<form action="<c:url value="/searchRenRemAppShow.in"/>" method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">RA-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="ID eg: 3223">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="RenewalReminder" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Renewal Date</h4>
					</div>
					<form action="<c:url value="/RenRemAppSearch.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ApprovalDailydate" name="RRDate"
										required="required" type="text"
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-success">Search</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<c:if test="${param.deleteRenewalReminder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Approval Renewal Reminder Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id="OPEN"><a
										href="<c:url value="/RenRemApp/1/1.in"/>">OPEN</a></li>
									<li role="presentation" id="APPROVED"><a
										href="<c:url value="/RenRemApp/2/1.in"/>">APPROVED</a></li>
									<li role="presentation" id="PAID"><a
										href="<c:url value="/RenRemApp/3/1.in"/>">PAID</a></li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="RenewalReminderTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">ID</th>
											<th class="fit ar">Created Date</th>
											<th class="fit ar">Created By</th>
											<th class="fit ar">Amount</th>
											<th class="fit ar">Download</th>
											<th class="fit ar">Status</th>
											<th class="actions" class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty renewal}">
											<c:forEach items="${renewal}" var="renewal">

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="<c:url value="/${SelectStatusId}/ShowRenRemApproval.in?AID=${renewal.renewalApproval_id}&page=${SelectPage}" />"
														data-toggle="tip"
														data-original-title="Click this Renewal Details"><c:out
																value="RA-${renewal.renewalApproval_Number}" /> </a></td>
													<td class="fit ar"><a
														href="<c:url value="/${SelectStatusId}/ShowRenRemApproval.in?AID=${renewal.renewalApproval_id}&page=${SelectPage}" />"
														data-toggle="tip"
														data-original-title="Click Renewal Details"><c:out
																value="${renewal.approvalCreated_Date}" /> </a></td>

													<td class="fit ar"><c:out
															value="  ${renewal.approvalCreated_By}" /></td>

													<td class="fit ar"><span class="badge"> <c:out
																value="${renewal.approvalPayment_Amount}" />
													</span></td>
													<td class="fit ar"><c:choose>
															<c:when test="${renewal.approval_document == true}">
																<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">
																	<a
																		href="${pageContext.request.contextPath}/download/RenRemAppDocument/${renewal.approval_document_id}.in">
																		<span class="fa fa-download"> Download</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td>
													<td class="fir ar"><c:choose>
															<c:when test="${renewal.approvalStatusId == 1}">
																<span class="label label-pill label-warning"><c:out
																		value="${renewal.approval_Status}" /></span>
															</c:when>
															<c:otherwise>
																<span class="label label-pill label-success"><c:out
																		value="${renewal.approval_Status}" /></span>
															</c:otherwise>
														</c:choose></td>
													<td><c:choose>
															<c:when test="${renewal.approvalStatusId == 1}">
																<sec:authorize
																	access="hasAuthority('DELETE_APPROVEL_RENEWAL')">
																	<a class="btn btn-success btn-sm"
																		href="<c:url value="/${SelectStatusId}/CancelRenRemApproval.in?AID=${renewal.renewalApproval_id}&page=${SelectPage}"/>"
																		class="confirmation"
																		onclick="return confirm('Are you sure? Delete ')">
																		<span class="fa fa-trash"></span> Delete
																	</a>
																 </sec:authorize> 
															</c:when>

															<c:otherwise>

															</c:otherwise>
														</c:choose></td>
												</tr>
											</c:forEach>
											<c:if test="${!empty RRAmount}">
												<tr>
													<td colspan="5" class="key"><h4>Total:</h4></td>
													<td colspan="4" class="value"><h4>${RRAmount}</h4></td>
												</tr>
											</c:if>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/RenRemApp/${SelectStatusId}/1" />
					<c:url var="lastUrl"
						value="/RenRemApp/${SelectStatusId}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl"
						value="/RenRemApp/${SelectStatusId}/${currentIndex - 1}" />
					<c:url var="nextUrl"
						value="/RenRemApp/${SelectStatusId}/${currentIndex + 1}" />
					<div class="text-center">
						<ul class="pagination pagination-lg pager">
							<c:choose>
								<c:when test="${currentIndex == 1}">
									<li class="disabled"><a href="#">&lt;&lt;</a></li>
									<li class="disabled"><a href="#">&lt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${firstUrl}">&lt;&lt;</a></li>
									<li><a href="${prevUrl}">&lt;</a></li>
								</c:otherwise>
							</c:choose>
							<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
								<c:url var="pageUrl" value="/RenRemApp/${SelectStatusId}/${i}" />
								<c:choose>
									<c:when test="${i == currentIndex}">
										<li class="active"><a href="${pageUrl}"><c:out
													value="${i}" /></a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${currentIndex == deploymentLog.totalPages}">
									<li class="disabled"><a href="#">&gt;</a></li>
									<li class="disabled"><a href="#">&gt;&gt;</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${nextUrl}">&gt;</a></li>
									<li><a href="${lastUrl}">&gt;&gt;</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			setTimeout(function(){
				$("#ApprovalDailydate").datepicker({
					defaultDate : new Date(),
					autoclose : !0,
					todayHighlight : !0,
					format : "dd-mm-yyyy",
					startDate : '-0m'
				})
				$("#datemask").inputmask("dd-mm-yyyy", {
					placeholder : "dd-mm-yyyy"
				}), $("[data-mask]").inputmask()
			});	
			}, 1000);
			
	</script>
	<script type="text/javascript">
		$(document).ready(function() {
			console.log(document.getElementById("statues").value);
			var e = document.getElementById("statues").value;
			switch (e) {
			case "ALL":
				document.getElementById("All").className = "active";
				break;
			case e:
				document.getElementById(e).className = "active"
			}
		});
	</script>
</div>