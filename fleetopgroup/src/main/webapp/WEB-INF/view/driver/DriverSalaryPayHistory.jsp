<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <a
						href="<c:url value="/DriverSalaryAdvance.in?ID=${driver.driver_id}"/>">Driver
						Advance</a> / Add Driver Advance
				</div>
				<div class="pull-right">
				<c:if test="${driver.driverStatusId != 6}">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
						<a class="btn btn-success"
							href="<c:url value="/addDriSalAdv.in?ID=${driver.driver_id}"/>">
							<i class="fa fa-plus"></i> Add Driver Advance
						</a>
					</sec:authorize>
				</c:if>	
					<a class="btn btn-link"
						href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-user" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">

				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<sec:authorize access="!hasAuthority('ADDEDIT_DRIVER_REMINDER')">
									<spring:message code="message.unauth"></spring:message>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
									<table id="DriverReminderTable"
										class="table table-hover table-striped">
										<thead>
											<tr>
												<th>Advance ID</th>
												<th>Advance Date</th>
												<th>Amount</th>
												<th>Balance Amount</th>
												<th>Status</th>
												<th>Advance Remarks</th>
											</tr>
										</thead>
										<tbody>
											<tr data-object-id="" class="ng-scope">
												<td class="icon"><c:out
														value="DA-${DriverAdvanvce.DSANUMBER}" /></td>
												<td class="icon"><i></i> <c:out
														value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
												<td class="icon"><c:out
														value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
												<td class="icon"><c:out
														value="${DriverAdvanvce.ADVANCE_BALANCE}" /></td>
												<td class="icon"><c:out
														value="${DriverAdvanvce.ADVANCE_STATUS}" /></td>
												<td class="icon"><c:out
														value="${DriverAdvanvce.ADVANCE_REMARK}" /></td>

											</tr>
											<tr>
												<td></td>
												<td colspan="5">
													<table class="table table-hover table-striped">
														<thead>
															<tr>
																<th>No</th>
																<th>Paid Date</th>
																<th>Paid Type</th>
																<th>Paid No</th>
																<th>Paid Amount</th>
																<th>Received By</th>
																<th>Remarks</th>
															</tr>
														</thead>
														<tbody>

															<%
																Integer hitsCount = 1;
															%>
															<c:if test="${!empty DriverPayAdvance}">
																<c:forEach items="${DriverPayAdvance}"
																	var="DriverPayAdvance">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit">
																			<%
																				out.println(hitsCount);
																							hitsCount += 1;
																			%>
																		</td>
																		<td class="icon"><i></i> <c:out
																				value="${DriverPayAdvance.PAID_DATE_ON}" /></td>
																		<td class="icon"><c:out
																				value="${DriverPayAdvance.ADVANCE_PAID_TYPE}" /></td>
																		<td class="icon"><c:out
																				value="${DriverPayAdvance.ADVANCE_PAID_NUMBER}" /></td>
																		<td class="icon"><c:out
																				value="${DriverPayAdvance.ADVANCE_PAID_AMOUNT}" /></td>
																		<td class="icon"><c:out
																				value="${DriverPayAdvance.ADVANCE_RECEIVED_BY}" /></td>
																		<td class="icon"><c:out
																				value="${DriverPayAdvance.PAID_REMARK}" /></td>
																	</tr>
																</c:forEach>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</sec:authorize>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a
						href="showDriver?driver_id=${driver.driver_id}">Overview</a></li>

					<li class="active"><a
						href="ShowReminderHis.in?driver_id=${driver.driver_id}">Reminder
							History</a></li>

					<li><sec:authorize
							access="hasAuthority('ADDEDIT_DRIVER_REMINDER')">
							<a href="ShowDriverReminder.in?driver_id=${driver.driver_id}">Reminders
								<span class="count muted text-muted pull-right">${ReminderCount}</span>
							</a>
						</sec:authorize></li>
					<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
							<a href="ShowDriverAd.in?Id=${driver.driver_id}">Attendance </a>
						</sec:authorize></li>

					<li><sec:authorize
							access="hasAuthority('ADDEDIT_DRIVER_DOCUMENT')">
							<a href="ShowDriverDocument.in?driver_id=${driver.driver_id}">Documents
								<span class="count muted text-muted pull-right">${DocumentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize
							access="hasAuthority('ADDEDIT_DRIVER_COMMENT')">
							<a href="ShowDriverComment.in?driver_id=${driver.driver_id}">Comments
								<span class="count muted text-muted pull-right">${CommentCount}</span>
							</a>
						</sec:authorize></li>

					<li><sec:authorize
							access="hasAuthority('ADDEDIT_DRIVER_PHOTO')">
							<a href="ShowDriverPhoto.in?driver_id=${driver.driver_id}">Photos
								<span class="count muted text-muted pull-right">${PhotoCount}</span>
							</a>
						</sec:authorize></li>
					<!-- SRS TRAVELS DRIVER ADVANCE -->
					<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
							<a
								href="<c:url value="/DriverSalaryAdvance.in?ID=${driver.driver_id}"/>">Driver
								Advance </a>
						</sec:authorize></li>

					<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
							<a
								href="<c:url value="/DriverSalary.in?ID=${driver.driver_id}"/>">Driver
								Salary </a>
						</sec:authorize></li>
				</ul>
			</div>
		</div>
	</section>
</div>
