<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripDaily.in"/>">TripCollection</a>
				</div>
				<div class="pull-right">
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
							href="<c:url value="/TripDailyReport"/>"> <span
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
						<span class="info-box-icon bg-green"><i
							class="fa fa-tripadvisor"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Trip Close</span> <span
								class="info-box-number">${TripClose}</span>
						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripCollection</span>
							<form action="<c:url value="/searchTripDailyShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">TS-</span></span> <input class="form-text"
										id="tripStutes" name="tripStutes" type="number" min="1"
										required="required" placeholder="TS-ID eg:6786" maxlength="20">
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
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search TripCollection</span>
							<form action="<c:url value="/searchTripDailyClosed.in"/>" method="post">
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
							<div class="row" id="grpReportDailydate" class="form-group">
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
									<li role="presentation"><a
										href="<c:url value="/newTripDaily.in"/>">Today Trip</a></li>
									<li role="presentation"><a
										href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip
									</a></li>
									<li role="presentation" class="" id="CLOSED"><a
										href="<c:url value="/closeTripDaily/1.in"/>"> Close Trip <span
											data-toggle="tip" title="" class="badge bg-red"
											data-original-title="${TripClose} Close Trip">${TripClose}</span></a></li>
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
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="main-body">
					<c:if test="${!empty TripDaily}">
						<div class="box">
							<div class="box-body">
								<table id="TripSheetManage" class="table table-hover table-bordered">
									<thead>
										<tr>
											<th class="fit"></th>
											<th class="fit">ID</th>
											<th>Vehicle</th>
											<th class="fit ar">Depot</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th>Passenger</th>
											<th class="fit ar">Income</th>
											<th class="fit ar">Expense</th>
											<th class="fit ar">Balance</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${TripDaily}" var="TripDaily">
											<tr data-object-id="" class="ng-scope">
												<td><a
													href="<c:url value="/copyTripDaily.in?ID=${TripDaily.TRIPDAILYID}"/>"
													class="btn-sm btn btn-info" data-toggle="tip" target="_blank"
													data-original-title="Copy Old to Create New"><span
														class="fa fa-copy"></span></a></td>

												<td class="fit"><a target="_blank"
													href="<c:url value="/showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"/>"><c:out
															value="TS-${TripDaily.TRIPDAILYNUMBER}" /></a></td>
												<td><a target="_blank"
													href="<c:url value="/showTripDaily.in?ID=${TripDaily.TRIPDAILYID}"/>"
													data-toggle="tip" data-original-title="Click Details">
														<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />

												</a></td>
												<td class="fit ar"><c:out
														value="${TripDaily.VEHICLE_GROUP}" /></td>
												<td><c:out value="${TripDaily.TRIP_ROUTE_NAME}" /></td>

												<td><c:out value="${TripDaily.TRIP_OPEN_DATE}" /></td>

												<td class="fir ar"><c:out
														value="${TripDaily.TRIP_TOTALPASSNGER}" /></td>

												<td class="fir ar"><c:out
														value="${TripDaily.TOTAL_INCOME}" /></td>

												<td class="fir ar"><c:out
														value="${TripDaily.TOTAL_EXPENSE}" /></td>

												<td class="fir ar"><c:out
														value="${TripDaily.TOTAL_BALANCE}" /></td>


											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</c:if>
					<c:url var="firstUrl" value="/closeTripDaily/1" />
					<c:url var="lastUrl"
						value="/closeTripDaily/${deploymentLog.totalPages}" />
					<c:url var="prevUrl" value="/closeTripDaily/${currentIndex - 1}" />
					<c:url var="nextUrl" value="/closeTripDaily/${currentIndex + 1}" />
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
								<c:url var="pageUrl" value="/closeTripDaily/${i}" />
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
					<c:if test="${empty TripDaily}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">No results
								found</p>
						</div>
					</c:if>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripDailyNewValidate.js"/>"></script>
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
	<input type="hidden" value="${SelectStatus}" id="statues">
	<script type="text/javascript">
		$(document).ready(function() {
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