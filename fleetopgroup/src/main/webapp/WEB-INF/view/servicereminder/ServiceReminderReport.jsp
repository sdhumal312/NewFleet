<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
						href="ViewServiceReminderList.in"> Service
						Reminder</a> / <small>Search Service Reminder</small>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
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
					<a href="ViewServiceReminderList.in"> Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_REMINDER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<div class="row">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="ServiceTable"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="fit">Id</th>
											<th>Vehicle</th>
											<th>Service Task &amp; Schedule</th>
											<th>Next Due</th>
											<th class="fit ar">Subscribers</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty Service}">
											<c:forEach items="${Service}" var="Service">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a target="_blank" 
														href="ShowService.in?service_id=${Service.service_id}"><c:out
																value="S-${Service.service_Number}" /></a></td>
													<td><a href="showVehicle.in?vid=${Service.vid}"
														data-toggle="tip" target="_blank" 
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
															var="days" value="${Service.diffrent_time_days}"></c:set>
														<c:choose>
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
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/Servicelanguage.js" />"></script>
</div>