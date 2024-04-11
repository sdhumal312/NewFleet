<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <a
						href="ViewServiceReminderList.in"> Service Reminder</a>
					/ <small>OverDue Service Reminder</small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_SERVICE_REMINDER')">
						<a class="btn btn-success btn-sm" href="addServiceReminder.in"> <span
							class="fa fa-plus"></span> Add Multiple Service Reminders
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
						<a class="btn btn-info btn-sm" href="ServiceReminderReport.in"> <span
							class="fa fa-search"></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-3 col-sm-3 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i
						class="fa fa-bell-slash"></i></span>
					<div class="info-box-content">
						<span class="info-box-text">Overdue</span> <a data-toggle="tip"
							data-original-title="Click OverDue Details"
							href="/OverDueService/1.in"><span class="info-box-number">${TodayOverDueServiceRemindercount}</span></a>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i
						class="fa fa-volume-up"></i></span>
					<div class="info-box-content">
						<span class="info-box-text">Due Soon</span> <a data-toggle="tip"
							data-original-title="Click DueSoon Details"
							href="/DueSoonService.in"><span class="info-box-number">${TodayDueServiceRemindercount}</span></a>

					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-4 col-xs-12">
				<div class="info-box">
					<div class="info-box-center">
						<span class="info-box-text">Search Service</span>
						<form action="<c:url value="/searchServiceReminder.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="Search" type="text" min="1" required="required"
										placeholder="S-ID, Ven-No, Tash"> <span
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
		<c:if test="${param.saveService eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Service Created successfully .
			</div>
		</c:if>

		<c:if test="${param.deleteService eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Service Deleted successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Service Not Create successfully .
			</div>
		</c:if>
		<c:if test="${param.alreadyCreatedWorkOrder eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Service Reminder was already created. Please Wait for Complete
				Work Order.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<div class="row">
				<div class="main-body">
					<br>
					<h4>OverDue Service Reminder</h4>
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>Vehicle</th>
											<th>Service Task &amp; Schedule</th>
											<th>Next Due</th>
											<th class="fit ar">Subscribers</th>
											<th class="fit ar">Actions</th>
										</tr>
									</thead>
									<tbody>

										<c:if test="${!empty Service}">
											<c:forEach items="${Service}" var="Service">

												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="/ShowService.in?service_id=${Service.service_id}"><c:out
																value="S-${Service.service_Number}" /></a></td>
													<td><a href="showVehicle.in?vid=${Service.vid}"
														data-toggle="tip"
														data-original-title="Click this vehicle Details"> <c:out
																value="${Service.vehicle_registration}" />

													</a> <br> <c:out value="${Service.vehicle_Group}" /></td>
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
																	value="${Service.service_subtype}" /></a></b> <span>every
															<c:out value="${Service.time_interval} " /> <c:out
																value="${Service.time_intervalperiod} " /> or <c:out
																value="${Service.meter_interval}  Km" />
													</span></td>


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
													<td><span data-toggle="tip"
														data-original-title="${Service.service_subscribeduser}"><c:out
																value="${Service.service_subscribeduser_name}" /></span></td>

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

																		<li><sec:authorize
																				access="hasAuthority('EDIT_SERVICE_REMINDER')">
																				<a
																					href="VehicleServiceEdit.in?service_id=${Service.service_id}">
																					<i class="fa fa-edit"></i> Edit
																				</a>
																			</sec:authorize></li>
																		<li><sec:authorize
																				access="hasAuthority('DELETE_SERVICE_REMINDER')">
																				<a
																					href="deleteServiceReminder.in?service_id=${Service.service_id}"
																					class="confirmation"
																					onclick="return confirm('Are you sure? Delete ')">
																					<span class="fa fa-trash"></span> Delete
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
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<c:url var="firstUrl" value="/OverDueService/1.in" />
					<c:url var="lastUrl"
						value="/OverDueService/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/OverDueService/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/OverDueService/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/OverDueService/${i}" />
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
			</div>
		</sec:authorize>
	</section>
</div>