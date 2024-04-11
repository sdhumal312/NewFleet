<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripDaily.in"/>">TripCollection</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="searchTripDailyShow.in" method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="tripStutes" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="TS-ID eg:6786" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addTripDaily.in"/>"> <span
							class="fa fa-plus"></span> Create TripCollection
						</a>
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#CloseTripCollection" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="Click this Close Collection Details"> <span
							class="fa fa-plus"></span> Close Daily TripCollection
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('RE_OPEN_DAILY_TRIP_COLLECTION')">
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#ReOpenTripCollection" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="Click this Close Collection Details"> <span
							class="fa fa-plus"></span> Re-Open Daily TripCollection
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/TripDailyReport.in"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon"> <i data-toggle="modal"
							data-toggle="tip"
							data-original-title="Click this Renewal Details"
							data-target="#RenewalReminder" data-whatever="@mdo"> <span
								class="fa fa-calendar"></span>
						</i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text" data-toggle="tip"
								data-original-title="Click Calendar Icon">Today Trip
								Entries </span> <span class="info-box-number"><a
								data-toggle="tip" data-original-title="Click Calendar Icon"
								href="<c:url value="/newTripDaily.in"/>">${TripSheetToday}</a></span>
						</div>
					</div>
				</div>

				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripCollection</span>
							<form action="searchTripDaily.in" method="post">
								<div class="input-group">
									<input class="form-text" id="tripStutes" name="tripStutes"
										type="text" pattern="{2,20}" title="Two or more characterss"
										required="required" placeholder="TS-ID, Vehicle No"
										maxlength="20"> <span class="input-group-btn">
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
		<c:if test="${param.NotFound eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				ID Not Available.<br>
			</div>
		</c:if>
		<c:if test="${saveTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripCollection Created successfully .
			</div>
		</c:if>
		<c:if test="${param.delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripCollection Canceled successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripCollection Already Updated.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripCollection Already Updated.
			</div>
		</c:if>
		<c:if test="${param.nodata eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Trip Collection date No Entries Available.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${param.update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripCollection Updated successfully .
			</div>
		</c:if>
		<c:if test="${param.closeTrip eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Please Close first TripCollection Id ( ${closeID} ).
			</div>
		</c:if>
		<c:if test="${param.sequenceCounterNotFound eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Sequence Not Found Please Contact To System Administrator !
			</div>
		</c:if>
		<c:if test="${param.alreadyOpen eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Daily Trip Collection In open status !<br>
			</div>
		</c:if>
		<c:if test="${param.reOpenSuccess eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Daily Trip Collection Re-opened Successfully !<br>
			</div>
		</c:if>
		<c:if test="${param.closedCB eq true}">
			<div class="alert alert-warning">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Cash Book already Closed you can not re-open !<br>
			</div>
		</c:if>
		<div class="modal fade" id="CloseTripCollection" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Close Trip Location Date</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/closeDailyTripDaily.in"/>" method="post"
						enctype="multipart/form-data" name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
							<div class="row" id="grpVehicleGroupSelect" class="form-group">
								<input type="hidden" id="VehicleGroupSelect" name="VEHICLEGROUP_ID"
									style="width: 100%;" required="required"
									placeholder="Please select Group Name" /> <span
									id="VehicleGroupSelectIcon" class=""></span>
								<div id="VehicleGroupSelectErrorMsg" class="text-danger"></div>

							</div>
							<br>
							<!-- <div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="ReportDailydate" name="closedate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="ReportDailydateIcon" class=""></span>
								<div id="ReportDailydateErrorMsg" class="text-danger"></div>
							</div> -->
							
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group input-append date"
									id="tripCloseDate">
									<input class="form-text" id="ReportDailydate" name="closedate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="ReportDailydateIcon" class=""></span>
								<div id="ReportDailydateErrorMsg" class="text-danger"></div>
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
		
		<div class="modal fade" id="ReOpenTripCollection" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Re-Open Trip Collection Date</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/ReOpenDailyTripDaily.in"/>" method="post"
						enctype="multipart/form-data" name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
							<div class="row" id="grpVehicleGroupSelect" class="form-group">
								<input type="hidden" id="VehicleGroupID" name="VEHICLEGROUP_ID"
									style="width: 100%;" required="required"
									placeholder="Please select Group Name" /> <span
									id="VehicleGroupSelectIcon" class=""></span>
								<div id="VehicleGroupSelectErrorMsg" class="text-danger"></div>

							</div>
							<br>
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group input-append date"
									id="tripCloseDate">
									<input class="form-text" id="ReportDailydate" name="closedate"
										required="required" type="text"
										data-inputmask="'alias': 'yyyy-mm-dd'" data-mask=""> <span
										class="input-group-addon add-on"> <span
										class="fa fa-calendar"></span>
									</span>
								</div>
								<span id="ReportDailydateIcon" class=""></span>
								<div id="ReportDailydateErrorMsg" class="text-danger"></div>
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
		
		<!-- Modal -->
		<div class="modal fade" id="RenewalReminder" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Trip Date</h4>
					</div>
					<form action="<c:url value="/searchTripDailyDate.in"/>"
						method="POST">
						<div class="modal-body">
							<div class="row">
								<div class="input-group input-append date"
									id="vehicle_RegisterDate">
									<input class="form-text" id="IssuesDailydate"
										name="TripColDate" required="required" type="text"
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
					<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active"><a
										href="<c:url value="/newTripDaily.in"/>">Today Trip</a></li>
									<li role="presentation"><a
										href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a></li>
									<li role="presentation"><a
										href="<c:url value="/closeTripDaily/1.in"/>"> Close Trip</a></li>
									<%-- <li role="presentation" class="" id="RAIPUR-DEPOT"><a
										href="<c:url value="/YeTrDay.in?Loc=RAIPUR-DEPOT"/>"> Last Raipur-Depot Closed Trip</a></li>
									<li role="presentation" class="" id="KORBA-DEPOT"><a
										href="<c:url value="/YeTrDay.in?Loc=KORBA-DEPOT"/>"> Last Korba-Depot Closed Trip</a></li> --%>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<div class="box-body">
							<div class="table-responsive">
								<table id="TripSheetTable" class="table">
									<thead>
										<tr>
											<th class="fit">ID</th>
											<th>Vehicle</th>
											<th class="fit ar">Depot</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th>Passenger</th>
											<th class="fir ar">Status</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty TripDaily}">
											<c:forEach items="${TripDaily}" var="TripDaily">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><a
														href="showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"><c:out
																value="TS-${TripDaily.TRIPDAILYNUMBER}" /></a></td>
													<td><a target="_blank"
														href="showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
														data-toggle="tip" data-original-title="Click Details">
															<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />

													</a></td>
													<td class="fit ar"><c:out
															value="${TripDaily.VEHICLE_GROUP}" /></td>
													<td><c:out value="${TripDaily.TRIP_ROUTE_NAME}" /></td>

													<td><c:out value="${TripDaily.TRIP_OPEN_DATE}" /></td>

													<td class="fir ar"><c:out
															value="${TripDaily.TRIP_TOTALPASSNGER}" /></td>

													<td class="fir ar"><c:choose>

															<c:when test="${TripDaily.TRIP_CLOSE_STATUS == 'OPEN'}">
																<span class="label label-pill label-warning"><c:out
																		value="${TripDaily.TRIP_CLOSE_STATUS}" /></span>
															</c:when>
															<c:otherwise>
																<span class="label label-pill label-success"><c:out
																		value="${TripDaily.TRIP_CLOSE_STATUS}" /></span>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripDailyNewValidate.js" />"></script>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
									$("#datemask").inputmask("yyyy-mm-dd", {
										placeholder : "yyyy-mm-dd"
									}),
									$("[data-mask]").inputmask(),
									$("#VehicleGroupSelect,#VehicleGroupID")
											.select2(
													{
														ajax : {
															url : "${pageContext.request.contextPath}/getVehicleGroup.in",
															dataType : "json",
															type : "GET",
															contentType : "application/json",
															data : function(e) {
																return {
																	term : e
																}
															},
															results : function(
																	e) {
																return {
																	results : $
																			.map(
																					e,
																					function(
																							e) {
																						return {
																							text : e.vGroup,
																							slug : e.slug,
																							id : e.gid
																						}
																					})
																}
															}
														}
													})
						})
	</script>
</div>
