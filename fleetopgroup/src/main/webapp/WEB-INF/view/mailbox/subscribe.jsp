<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- iCheck -->
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/icheck/blue.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/mailbox/1"/>">MailBox</a>
				</div>
				<div class="pull-right"></div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">

			<div class="col-md-2 col-sm-2 col-xs-12">
				<a href="compose.html"
					class="btn btn-primary btn-block margin-bottom">Compose</a>
			</div>
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="box box-primary">
					<div class="box-header with-border">
						<h3 class="box-title">${displayname}</h3>
						<div class="box-tools pull-right">
							<div class="has-feedback" style="width: 200px;">
								<form action="searchmailbox" method="post">
									<div class="input-group">
										<input class="form-text" name="searchmail" type="text"
											required="required" placeholder="Search mail"> <span
											class="input-group-btn">
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
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-12">
				<div class="box box-solid">
					<div class="box-header with-border">
						<h3 class="box-title">Mailbox</h3>
						<div class="box-tools">
							<button class="btn btn-box-tool" data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<div class="box-body no-padding">
						<ul class="nav nav-pills nav-stacked">
							<li class="active"><a
								href="<c:url value="/mailbox/1"></c:url>"><i
									class="fa fa-inbox"></i> Inbox <span
									class="label label-primary pull-right">${unread}</span></a></li>
							<li><a href="<c:url value="/sentmailbox/1" />"><i
									class="fa fa-envelope-o"></i> Sent</a></li>
							<li><a href="<c:url value="/trashmailbox/1" />"><i
									class="fa fa-trash-o"></i> Trash</a></li>
						</ul>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /. box -->
				<div class="box box-solid">
					<div class="box-header with-border">
						<h3 class="box-title">Info</h3>
						<div class="box-tools">
							<button class="btn btn-box-tool" data-widget="collapse">
								<i class="fa fa-minus"></i>
							</button>
						</div>
					</div>
					<div class="box-body no-padding">
						<ul class="nav nav-pills nav-stacked">
							<li><a href="<c:url value="/importantmailbox" />"><i
									class="fa fa-star text-red"></i> Important</a></li>

							<li><a href="<c:url value="/subscribebox" />"><i
									class="fa fa-calendar-plus-o text-yellow"></i> Subscribe</a></li>

						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-9 col-sm-8 col-xs-12">
				<div class="box box-primary">
					<div class="box-body no-padding">
						<div class="table-responsive">
							<table class="table">
								<thead>
									<tr>
										<th>ID</th>
										<th>VehicleName</th>
										<th>Type</th>
										<th>Date</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty Subscribe}">
										<c:forEach items="${Subscribe}" var="Subscribe">

											<tr>
												<td><c:choose>
														<c:when
															test="${Subscribe.SUBSCRIBE_LOCATION == 'SERVICE_REMINDER'}">
															<a
																href="ShowService.in?service_id=${Subscribe.SUBSCRIBE_LOCATION_ID}"><c:out
																	value="SR-${Subscribe.SUBSCRIBE_LOCATION_ID}" /></a>
														</c:when>
														<c:otherwise>
															<a
																href="showRenewalReminder.in?renewal_id=${Subscribe.SUBSCRIBE_LOCATION_ID}"
																data-toggle="tip"
																data-original-title="Click this Renewal Details"><c:out
																	value="RR-${Subscribe.SUBSCRIBE_LOCATION_ID}" /> </a>
														</c:otherwise>
													</c:choose></td>
												<td><a
													href="<c:url value="/showVehicle?vid=${Subscribe.SUBSCRIBE_VEHICLE_ID}"/>"
													data-toggle="tip"
													data-original-title="Click vehicle Details"><c:out
															value="${Subscribe.SUBSCRIBE_VEHICLE_NAME}" /></a></td>
												<td><b style="font-size: 15px;"><a><c:out
																value="${Subscribe.SUBSCRIBE_TYPE} - " /> <c:out
																value="${Subscribe.SUBSCRIBE_SUBTYPE}" /></a></b></td>

												<td><c:out value="${Subscribe.SUBSCRIBE_DATE}" /><br>
													<i class="fa fa-calendar-check-o"></i> <c:set var="days"
														value="${Subscribe.SUBSCRIBE_THRESHOLD_DATE}">
													</c:set> <c:choose>
														<c:when test="${fn:contains(days, 'now')}">
															<span style="color: #06b4ff;"><c:out
																	value="${Subscribe.SUBSCRIBE_THRESHOLD_DATE}" /></span>
														</c:when>
														<c:when test="${fn:contains(days, 'ago')}">
															<span style="color: red;"><c:out
																	value="${Subscribe.SUBSCRIBE_THRESHOLD_DATE}" /></span>

														</c:when>

														<c:otherwise>
															<span style="color: red;"><c:out
																	value="${Subscribe.SUBSCRIBE_THRESHOLD_DATE}" /></span>


														</c:otherwise>
													</c:choose></td>
												<td class="fir ar">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<c:choose>
																<c:when
																	test="${Subscribe.SUBSCRIBE_LOCATION == 'SERVICE_REMINDER'}">
																	<li><sec:authorize
																			access="hasAuthority('WORKORDER_SERVICE_REMINDER')">
																			<a
																				href="addServiceWorkOrder.in?service_id=${Subscribe.SUBSCRIBE_LOCATION_ID}">
																				<i class="fa fa-edit"></i> Create WorkOrder
																			</a>
																		</sec:authorize></li>

																</c:when>
																<c:otherwise>
																	<li><sec:authorize
																			access="hasAuthority('EDIT_RENEWAL')">
																			<a
																				href="reviseRenewalReminder.in?renewal_id=${Subscribe.SUBSCRIBE_LOCATION_ID}">
																				<span class="fa fa-upload"> Revise</span>
																			</a>
																		</sec:authorize></li>
																</c:otherwise>
															</c:choose>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<!--  Service Reminder Subscribe entries Show -->
									<c:if test="${!empty Service}">
										<c:forEach items="${Service}" var="Service">

											<tr data-object-id="" class="ng-scope">
												<td><a
													href="ShowService.in?service_id=${Service.service_id}"><c:out
															value="SR-${Service.service_Number}" /></a></td>
												<td><a
													href="ShowService.in?service_id=${Service.service_id}"
													data-toggle="tip" data-original-title="Click this Details">
														<c:out value="${Service.vehicle_registration}" />

												</a></td>
												<td><c:choose>
															<c:when
																test="${Service.diffenceThrsholdOdometer == 'Due Soon'}">
																<span class="label label-default label-warning"
																	style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>
															</c:when>
															<c:when
																test="${Service.diffenceThrsholdOdometer == 'Overdue'}">
																<span class="label label-default label-danger"
																	style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>

															</c:when>
															<c:otherwise>
																<span class="label label-default label-warning"
																	style="font-size: 12px;">${Service.diffenceThrsholdOdometer}</span>

															</c:otherwise>
														</c:choose> <b style="font-size: 15px;"><a><c:out
																value="${Service.service_type} - " /> <c:out
																value="${Service.service_subtype}" /></a></b></td>


												<td><i class="fa fa-calendar-check-o"></i> <c:set
														var="days" value="${Service.diffrent_time_days}">
													</c:set> <c:choose>
														<c:when test="${fn:contains(days, 'now')}">
															<span style="color: #06b4ff;"><c:out
																	value="${Service.diffrent_time_days}" /></span>
														</c:when>
														<c:when test="${fn:contains(days, 'ago')}">
															<span style="color: red;"><c:out
																	value="${Service.diffrent_time_days}" /></span>

														</c:when>

														<c:otherwise>
															<span style="color: red;"><c:out
																	value="${Service.diffrent_time_days}" /></span>


														</c:otherwise>
													</c:choose> <br> <i class="fa fa-road"></i> <c:set var="miles"
														value="${Service.diffrent_meter_oddmeter}"></c:set> <c:choose>
														<c:when test="${fn:contains(miles, 'now')}">
															<span style="color: #06b4ff;"><c:out
																	value="${Service.diffrent_meter_oddmeter}" /></span>
														</c:when>
														<c:when test="${fn:contains(miles, 'ago')}">
															<span style="color: red;"><c:out
																	value="${Service.diffrent_meter_oddmeter}" /></span>

														</c:when>

														<c:otherwise>
															<span style="color: red;"><c:out
																	value="${Service.diffrent_meter_oddmeter}" /></span>


														</c:otherwise>
													</c:choose>
												<td class="fir ar">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>

														<ul class="dropdown-menu pull-right">
															<c:choose>
																<c:when test="${Service.servicestatus != 'INACTIVE'}">
																	<li><sec:authorize
																			access="hasAuthority('WORKORDER_SERVICE_REMINDER')">
																			<a
																				href="addServiceWorkOrder.in?service_id=${Service.service_id}">
																				<i class="fa fa-edit"></i> Create WorkOrder
																			</a>
																		</sec:authorize></li>

																</c:when>
																<c:otherwise>
																	<li><span class="label label-warning"><i
																			class="fa fa-dot-circle-o"></i> <c:out
																				value=" WorkOrder Created" /></span></li>
																</c:otherwise>
															</c:choose>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<!--  complete Service Reminder Subscribe entries Show -->
									<!--  Start Renewal Reminder Subscribe entries Show -->
									<c:if test="${!empty renewal}">
										<c:forEach items="${renewal}" var="renewal">

											<tr data-object-id="" class="ng-scope">
												<td><a
													href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
													data-toggle="tip"
													data-original-title="Click this Renewal Details"><c:out
															value="RR-${renewal.renewal_R_Number}" /> </a></td>
												<td><a
													href="showRenewalReminder.in?renewal_id=${renewal.renewal_id}"
													data-toggle="tip"
													data-original-title="Click Renewal Details"><c:out
															value="${renewal.vehicle_registration}" /> </a></td>

												<td><b style="font-size: 15px;"><a><c:out
																value="${renewal.renewal_type} -" /> <c:out
																value="  ${renewal.renewal_subtype}" /></a></b></td>

												<td><c:out value="${renewal.renewal_to}" /><br> <i
													class="fa fa-calendar-check-o"></i> <c:set var="days"
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
												<c:choose>
													<c:when test="${renewal.renewal_staus_id == 2}">
														<td class="fit ar"><span class="label label-success"><i
																class="fa fa-check-square-o"></i> <c:out
																	value=" ${renewal.renewal_status}" /></span></td>
													</c:when>
													<c:when test="${renewal.renewal_status == null}">

														<td class="fit ar">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <i
																	class="fa fa-cog material-icons md-settings"></i> <span
																	class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><span class="label label-warning"><i
																			class="fa fa-dot-circle-o"></i> <c:out
																				value=" NOT APPROVED" /></span></li>
																	<li><sec:authorize
																			access="hasAuthority('DOWNLOND_RENEWAL')">
																			<a
																				href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_id}.in">
																				<span class="fa fa-download"> Download</span>
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('EDIT_RENEWAL')">
																			<a
																				href="reviseRenewalReminder.in?renewal_id=${renewal.renewal_id}">
																				<span class="fa fa-upload"> Revise</span>
																			</a>
																		</sec:authorize></li>

																</ul>
															</div>
														</td>
													</c:when>
													<c:otherwise>
														<td class="fit ar">
															<div class="btn-group">
																<a class="btn btn-default btn-sm dropdown-toggle"
																	data-toggle="dropdown" href="#"> <i
																	class="fa fa-cog material-icons md-settings"></i> <span
																	class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><span class="label label-danger"><i
																			class="fa fa-ban"></i> <c:out
																				value="${renewal.renewal_status}" /></span></li>
																	<li><sec:authorize
																			access="hasAuthority('DOWNLOND_RENEWAL')">
																			<a
																				href="${pageContext.request.contextPath}/download/RenewalReminder/${renewal.renewal_id}.in">
																				<span class="fa fa-download"> Download</span>
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('EDIT_RENEWAL')">
																			<a
																				href="reviseRenewalReminder.in?renewal_id=${renewal.renewal_id}">
																				<span class="fa fa-upload"> Revise</span>
																			</a>
																		</sec:authorize></li>

																</ul>
															</div>
														</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</c:if>
									<!--  complete Renewal Reminder Subscribe entries Show -->

									<!--  Start Driver Reminder Subscribe entries Show -->

									<c:if test="${!empty driverReminder}">
										<c:forEach items="${driverReminder}" var="driverReminder">

											<tr data-object-id="" class="ng-scope">
												<td><a
													href="showDriver.in?driver_id=${driverReminder.driver_id}"
													data-toggle="tip" data-original-title="Click driver"><c:out
															value="D-${driverReminder.driver_id}" /> </a></td>
												<td><a
													href="ShowDriverReminder.in?driver_id=${driverReminder.driver_id}"
													data-toggle="tip"
													data-original-title="Click driver Reminder"><c:out
															value="${driverReminder.driver_dlnumber}" /></a></td>
												<td><b style="font-size: 15px;"><a><c:out
																value="${driverReminder.driver_remindertype}" /></a></b></td>

												<td><c:out value="${driverReminder.driver_dlto}" /><br>
													<i class="fa fa-calendar-check-o"></i><font color="#FF6666">
														<c:out value="${driverReminder.driver_dueDifference}" />

												</font></td>
												<td class="fit ar">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <i
															class="fa fa-cog material-icons md-settings"></i> <span
															class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize
																	access="hasAuthority('DOWNLOND_DRIVER')">
																	<a
																		href="${pageContext.request.contextPath}/download/driverReminder/${driverReminder.driver_remid}.in">
																		<span class="fa fa-download"> Download</span>
																	</a>
																</sec:authorize></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<!--  complete Driver Reminder Subscribe entries Show -->
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<!-- /. box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->
</div>
