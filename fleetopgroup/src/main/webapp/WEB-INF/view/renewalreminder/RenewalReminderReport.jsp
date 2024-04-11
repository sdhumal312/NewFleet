<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal
							Reminders</a> / <a href="<c:url value="/RenewalReminderReport"/>">Renewal
							Reminder Report</a> / Search Renewal Reminders
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchRenewalReminder.in"/>"
								method="post">
								<div class="input-group">
								         	<input class="form-text" id="vehicle_registrationNumber"
										name="vehicle_registration" type="text" required="required"
										placeholder="RR-ID, Rep-Che-No, Pay-Date"> <span
						 				class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<sec:authorize access="hasAuthority('DOWNLOAD_RENEWAL')">
							<a href="#" id="exportRenewalReport" class="btn btn-info"> <span
								class="fa fa-file-excel-o"></span>Export
							</a>
						</sec:authorize>
						<a class="btn btn-link"
							href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>

					</div>
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
				<div class="main-body">
					<div class="box">
						<div class="box-header">
							<div class="pull-right">
								<div id="langSelect"></div>
							</div>
						</div>
						<div class="box-body">
							<table id="RenewalReportTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="fit">ID</th>
										<th class="fit ar">Vehicle Name</th>
										<th class="fit ar">Renewal Name</th>
										<th class="fit ar">Validity From-To</th>
										<th class="fit ar">Receipt NO</th>
										<th class="fit ar">Amount</th>
										<th class="fit ar">Paid Date</th>
										<th class="fit ar">Paid By</th>
										<th class="fit ar">Status</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty renewal}">
										<c:forEach items="${renewal}" var="renewal">

											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
													target="_blank" data-toggle="tip"
													data-original-title="Click Renewal Details"><c:out
															value="RR-${renewal.renewal_R_Number}" /> </a></td>
												<td class="fit ar"><a
													href="showRenewalReminder.in?renewal_id=${renewal.renewal_R_Number}"
													target="_blank" data-toggle="tip"
													data-original-title="Click Renewal Details"> <span
														class="badge"> <c:out
																value="${renewal.vehicle_registration}" />
													</span>
												</a></td>

												<td class="fit ar"><c:out
														value="${renewal.renewal_type}" />-<c:out
														value="${renewal.renewal_subtype}" /></td>

												<td class="fit ar"><c:out
														value="${renewal.renewal_from}  -to- " />
													<c:out value="${renewal.renewal_to}" /></td>
												<td class="fit ar"><a target="_blank"
													href="showRenewalReminder.in?renewal_id=${renewal.renewal_R_Number}"
													data-toggle="tip"
													data-original-title="Click Renewal Details"><c:out
															value="${renewal.renewal_receipt}" /> </a></td>
												<td class="fit ar"><span class="badge"> <c:out
															value="${renewal.renewal_Amount}" />
												</span></td>
												<td class="fit ar"><c:out
														value="${renewal.renewal_dateofpayment}" /></td>
												<td class="fit ar"><c:out
														value="${renewal.renewal_paidby}" /></td>
												<td class="fit ar"><c:choose>
														<c:when test="${renewal.renewal_status == 'APPROVED'}">
															<span class="label label-success"
																style="font-size: 10px;"> <c:out
																	value="${renewal.renewal_status}" /><br> by <c:out
																	value="${renewal.renewal_approvedby}" />
															</span>
														</c:when>
														<c:otherwise>
															<span class="label label-danger"> <c:out
																	value="${renewal.renewal_status}" />
															</span>
														</c:otherwise>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/Renewallanguage.js" />"></script>
</div>