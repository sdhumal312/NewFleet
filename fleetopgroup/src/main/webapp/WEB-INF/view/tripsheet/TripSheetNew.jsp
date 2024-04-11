<%@ include file="taglib.jsp"%>
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
						href="<c:url value="/newTripSheetEntries.in"/>">TripSheets</a>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-2">
						<form action="<c:url value="/searchTripSheetShow.in"/>"
							method="post">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="tripStutes" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="TS-ID eg:7878" maxlength="20"> <span
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
						<form action="<c:url value="/searchVehCurTSShow.in"/>" method="post">
							<div class="input-group">
								<input type="hidden" id="TripSelectVehicle_ID" name="vid"
									style="width: 100%;" required="required" 
									placeholder="Search Vehicle Name" /> <span
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
						<a class="btn btn-warning btn-sm" data-toggle="modal"
							data-target="#CloseTripCollection" data-whatever="@mdo"
							data-toggle="tip"
							data-original-title="click this for trip Details"> <span
							class="fa fa-search"></span> Search Trip By Date
						</a>
						<a class="btn btn-success btn-sm" href="addTripSheetEntries.in"> <span
							class="fa fa-plus"></span> Create TripSheet
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/TripSheetReport"/>"> <span
							class="fa fa-search "></span> Search
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('IVCARGO_INTEGRATION_NEEDED')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/SyncVehicleLhpvData"/>"> <span
							class="fa fa-plus "></span> Sync LHPV Date From IVCARGO
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="modal fade" id="CloseTripCollection" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search TripSheet By Date</h4>
					</div>
					<form id="formCloseTrip"
						action="<c:url value="/searchTripSheetByDate.in"/>" method="post"
						enctype="multipart/form-data" name="formCloseTrip" role="form"
						class="form-horizontal">
						<div class="modal-body">
							
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group input-append date"
									id="tripCloseDate">
									<input class="form-text" id="ReportDailydate" name="searchDate"
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
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="row">
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayOne} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATETS.in?DATE=${DayOne}"/>">${DayOne_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayTwo} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATETS.in?DATE=${DayTwo}"/>">${DayTwo_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayThree} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATETS.in?DATE=${DayThree}"/>">${DayThree_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFour} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATETS.in?DATE=${DayFour}"/>">${DayFour_Count}</a></span>
						</div>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="info-box">
							<span class="info-box-text">${DayFive} TRIPSHEET</span> <span
								class="info-box-number"><a
								href="<c:url value="/DATETS.in?DATE=${DayFive}"/>">${DayFive_Count}</a></span>
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
				This TripSheet Created successfully .
			</div>
		</c:if>
		<c:if test="${param.deleteTripSheet eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Canceled successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Already Updated.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateTripSheet}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Updated successfully .
			</div>
		</c:if>
		<c:if test="${dangerTripSheet}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This TripSheet Not Deleted.
			</div>
		</c:if>


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
										href="<c:url value="/newTripSheetEntries.in"/>">Today TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close
											TripSheet</a></li>
									<c:if test="${configuration.hideClosedTripSheet}">	
										<sec:authorize access="hasAuthority('SHOW_TRIPSHEET_CLOSE_STATUS')">
											<li role="presentation"><a
												href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment
													TripSheet</a></li>
											<li role="presentation"><a
												href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">Account
													Closed TripSheet</a></li>
										</sec:authorize>
									</c:if>	
									<c:if test="${!configuration.hideClosedTripSheet}">	
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment
											TripSheet</a></li>
									<li role="presentation"><a
										href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">Account
											Closed TripSheet</a></li>
									</c:if>
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
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="TripSheetTable" class="table">
									<thead>
										<tr>
											<th class="fit">TripSheet Id</th>
											<th>Vehicle</th>
											<th class="fit ar">Group</th>
											<th>Route</th>
											<th>Trip Date</th>
											<th class="fit ar">Advance</th>
											<th class="fit ar">Booking-Ref:</th>
											<th class="fir ar">Status</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty TripSheet}">
											<c:forEach items="${TripSheet}" var="TripSheet">
												<tr data-object-id="" class="ng-scope">
													<td class="fit"><c:choose>
															<c:when test="${TripSheet.tripStutesId == 1}">
																<a
																	href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																		value="TS-${TripSheet.tripSheetNumber}" /></a>
															</c:when >
															<c:when test="${TripSheet.tripStutesId == 2}">
																<a
																	href="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																		value="TS-${TripSheet.tripSheetNumber}" /></a>
															</c:when>
															<c:otherwise > 
																<a
																	href="<c:url value="showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"/>"><c:out
																			value="TS-${TripSheet.tripSheetNumber}" /></a>
															</c:otherwise>
														</c:choose></td>
													<td><c:out value="${TripSheet.vehicle_registration}" />
													</td>
													<td class="fit ar"><c:out
															value="${TripSheet.vehicle_Group}" /></td>
													<td><c:out value="${TripSheet.routeName}" /></td>
													<td><c:out value="${TripSheet.tripOpenDate}" /></td>
													<td class="fir ar"><c:out
															value="${TripSheet.tripTotalAdvance}" /></td>
													<td class="fir ar"><c:out
															value="${TripSheet.tripBookref}" /></td>
													<td class="fir ar"><c:choose>
															<c:when test="${TripSheet.tripStutesId == 1}">
																<span class="label label-pill label-warning"><c:out
																		value="${TripSheet.tripStutes}" /></span>
															</c:when>
															<c:when test="${TripSheet.tripStutesId == 2}">
																<span class="label label-pill label-info"><c:out
																		value="${TripSheet.tripStutes}" /></span>
															</c:when >
															<c:otherwise >
															
																<span class="label label-pill label-success"><c:out
																		value="${TripSheet.tripStutes}" /></span>
															
															</c:otherwise>
														</c:choose></td>
												</tr>
											
											</c:forEach>
											<tr>
												<td colspan="5" style="text-align: right;">Total
													Advance :</td>
												<td><c:out value="${TripSheetAdvance}"></c:out></td>
												<td colspan="2"></td>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSearchVehicle.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
</div>