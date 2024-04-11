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
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/RenewalReminder/${SelectStatus}/1.in"/>">Renewal
							Reminders</a></small>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
						<form action="<c:url value="/SearchRenRemShow.in"/>" method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">RR-</span></span>
								<input class="form-text" id="vehicle_registrationNumber"
									name="vehicle_registration" type="number" required="required"
									min="1" placeholder="ID eg: 1234"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<div class="col-md-2">
						<form action="<c:url value="/searchRenewalReminder.in"/>"
							method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="vehicle_registration" type="text" required="required"
									placeholder="RR-ID, Rep-Che-No"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<a class="btn btn-default btn-sm" data-toggle="tip"
							data-original-title="Download Import CSV Format. When Import Don't Remove the header"
							href="${pageContext.request.contextPath}/downloaddocument/3.in">
							<i class="fa fa-download"></i>
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IMPORT_RENEWAL')">
						<button type="button" class="btn btn-default btn-sm"
							data-toggle="modal" data-target="#addImport" data-whatever="@mdo">
							<span class="fa fa-file-excel-o"> Import</span>
						</button>

					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addRenewalReminder.in"/>"> <span
							class="fa fa-plus"></span> Add Renewal
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/RenewalReminderReport.in"/>"> <span
							class="fa fa-search"></span> Search
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
							class="fa fa-search"></span> Search Date
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="row">
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayOne} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayOne}"/>">${DayOne_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayTwo} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayTwo}"/>">${DayTwo_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayThree} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayThree}"/>">${DayThree_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFour} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayFour}"/>">${DayFour_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFive} RENEWALS</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATERR.in?DATE=${DayFive_Count}"/>">${DayFour_Count}</a></span>
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
					<form action="<c:url value="/DRR.in"/>" method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="RRDate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
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

		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" ><a
										href="<c:url value="/RenewalReminder/1/1.in"/>">NOT APPROVED</a></li>
									<li role="presentation"><a
										href="<c:url value="/RenewalReminder/3/1.in"/>">OPEN</a></li>
									<li role="presentation"><a
										href="<c:url value="/RenewalReminder/4/1.in"/>">IN
											PROGRESS</a></li>
									<li role="presentation" id="CANCELED"><a
										href="<c:url value="/RenewalReminder/5/1.in"/>">CANCELED</a></li>
									<li role="presentation" id="APPROVED" class="active"><a
										href="<c:url value="/RenewalReminder/2/1.in"/>">APPROVED</a></li>
									<li role="presentation" id="REJECTED"><a
										href="<c:url value="/RenewalReminder/6/1.in"/>">REJECTED</a></li>
									<li role="presentation" id="7"><a
										href="<c:url value="/RenewalOverDue/7/1.in"/>">OVERDUE<span
											class="info-box-number">${OverDueCount}</span></a></li>	
									<li role="presentation" id="8"><a
										href="<c:url value="/RenewalDueSoon/8/1.in"/>">DUE SOON<span
											class="info-box-number">${DueSoonCount}</span></a></li>			
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>

		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="RenewalReminderTable" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit">ID</th>
											<th class="fit ar">Vehicle Name</th>
											<th class="fit ar">Vehicle Group</th>
											<th class="fit ar">Renewal Types</th>
											<th class="fit ar">Validity From</th>
											<th>Validity To</th>

											<th class="fit ar">Amount</th>

											<th class="fit ar">Download</th>
											<th class="fit ar">Revise</th>
											<th class="actions" class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty renewal}">
											<c:forEach items="${renewal}" var="renewal">

												<tr data-object-id="" class="ng-scope">
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
															value="${renewal.vehicleGroup}" /></td>				

													<td class="fit ar"><c:choose>
															<c:when
																test="${renewal.renewal_dueRemDate == 'Due Soon'}">
																<span class="label label-default label-warning"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>
															</c:when>
															<c:when test="${renewal.renewal_dueRemDate == 'Overdue'}">
																<span class="label label-default label-danger"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>

															</c:when>
															<c:otherwise>
																<span class="label label-default label-warning"
																	style="font-size: 12px;"><c:out
																		value="${renewal.renewal_dueRemDate}" /></span>

															</c:otherwise>
														</c:choose> <c:out value="${renewal.renewal_type}" /> <c:out
															value="  ${renewal.renewal_subtype}" /></td>

													<td class="fit ar"><c:out
															value="${renewal.renewal_from}" /></td>


													<td><c:out value="${renewal.renewal_to}" /><br>
														<i class="fa fa-calendar-check-o"></i> <c:set var="days"
															value="${renewal.renewal_dueDifference}">
														</c:set> <c:choose>
															<c:when test="${fn:contains(days, 'now')}">
																<span style="color: #06b4ff;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>
															</c:when>
															<c:when test="${fn:contains(days, 'ago')}">
																<span style="color: red;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>

															</c:when>

															<c:otherwise>
																<span style="color: red;"><c:out
																		value="${renewal.renewal_dueDifference}" /></span>


															</c:otherwise>
														</c:choose></td>
													<td class="fit ar"><span class="badge"> <c:out
																value="${renewal.renewal_Amount}" />
													</span></td>

													<td class="fit ar"><c:choose>
															<c:when test="${renewal.renewal_document == true && renewal.renewal_document_id > 0}">
																<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">

																	<a
																		href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_document_id}.in">
																		<span class="fa fa-download"> Download</span>
																	</a>
																</sec:authorize>
															</c:when>
														</c:choose></td>
													<td class="fit ar"><sec:authorize
															access="hasAuthority('EDIT_RENEWAL')">
															<a
																href="<c:url value="/reviseRenewalReminder.in?renewal_id=${renewal.renewal_id}" />">
																<span class="fa fa-upload"> Revise</span>
															</a>
														</sec:authorize></td>
													<td class="fit ar"><span class="label label-success"><i
															class="fa fa-check-square-o"></i> <c:out
																value=" ${renewal.renewal_status}" /></span></td>

												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/RenewalReminder/${SelectStatus}/1" />
					<c:url var="lastUrl"
						value="/RenewalReminder/${SelectStatus}/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/RenewalReminder/${SelectStatus}/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/RenewalReminder/${SelectStatus}/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/RenewalReminder/${SelectStatus}/${i}" />
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
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>