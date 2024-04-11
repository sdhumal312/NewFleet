<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />" >
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripDaily.in"/>">TripCollection</a> / <a
									href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a> /
								<a href="<c:url value="/closeTripDaily/1.in"/>">Close Trip</a> /
								<a
									href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Show
									TripCollection</a> / Add Time Income
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('ADD_INCOME_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_INCOME_TRIPSHEET')">
							<div class="row">
								<div class="pull-left">
									<h4>
										Trip Number : <a
											href="showTripDaily.in?ID=${TripDaily.TRIPDAILYID}">TS-
											${TripDaily.TRIPDAILYNUMBER}</a>
									</h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : ${TripDaily.CREATED}</h5>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<c:choose>
										<c:when test="${TripDaily.VEHICLEID == 0}">
											<a style="color: #000000;" href="#" data-toggle="tip"
												data-original-title="Click Vehicle Info"> <c:out
													value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:when>
										<c:otherwise>
											<a style="color: #000000;"
												href="showVehicle.in?VID=${TripDaily.VEHICLEID}"
												data-toggle="tip" data-original-title="Click Vehicle Info">
												<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:otherwise>
									</c:choose>
									<br>
									<c:out value="${TripDaily.TRIP_ROUTE_NAME}" />
									<br>
									<c:out value="${TripDaily.VEHICLE_GROUP}" />
								</h4>
							</div>

							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripDaily.TRIP_OPEN_DATE}" /></a></li>
									<li>Depot : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripDaily.VEHICLE_GROUP}" /></a></li>

									<li>Driver: <a data-toggle="tip"
										data-original-title="Driver"> <c:out
												value="${TripDaily.TRIP_DRIVER_NAME}" /></a></li>
									<li>Conductor : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripDaily.TRIP_CLEANER_NAME}" /></a></li>
									<li>Opening KM: <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripDaily.TRIP_OPEN_KM}" /></a></li>
									<li>Closing KM: <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripDaily.TRIP_CLOSE_KM}" /></a></li>

									<li>Usage KM: <a data-toggle="tip"
										data-original-title="usage KM"> <c:out
												value="${TripDaily.TRIP_USAGE_KM}" /></a></li>



								</ul>
							</div>
							<br>
							<c:if test="${!empty TripColIncome}">
								<fieldset>
									<div class="row">
										<div class="">
											<table class="table table-bordered table-striped">
												<thead>
													<tr class="breadcrumb">
														<th class="fit">No</th>
														<th class="fit ar">Collection Name</th>
														<th class="fit ar">Collected By</th>
														<th class="fit ar">Reference</th>
														<th class="fit ar">Pre Amount</th>
														<th class="fit ar">New Amount</th>
														<th class="fit ar">Actions</th>
													</tr>
												</thead>
												<tbody>
													<%
														Integer hitsCount = 1;
													%>
													<c:forEach items="${TripColIncome}" var="TripColIncome">

														<tr data-object-id="" class="ng-scope">
															<td class="fit">
																<%
																	out.println(hitsCount);
																				hitsCount += 1;
																%>
															</td>
															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeName}" /></td>

															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeCollectedBy}" /></td>

															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeRefence}" /></td>
															<td class="fit ar"><c:out
																	value="${TripColIncome.incomeAmount}" /></td>
															<td>
																<div>
																	<div class="row1">
																		<input name="TRIPDAILYID" id="TRIPDAILYID${TripColIncome.TDTIMEID}" type="hidden" required="required"	value="${TripDaily.TRIPDAILYID}">
																		<input name="TDTIMEID" id="TDTIMEID${TripColIncome.TDTIMEID}" type="hidden" required="required" value="${TripColIncome.TDTIMEID}">
																		<input style="width: 60px;" required="required" name="incomeAmount" id="incomeAmount${TripColIncome.TDTIMEID}" type="number" value="${TripColIncome.incomeAmount}">
																	</div>
																</div>
																<script type="text/javascript">
																	$(document).ready(function() {
																		$("#incomeAmount"+${TripColIncome.TDTIMEID}).blur(function() {
																			showLayer();
																			var jsonObject			= new Object();
																			jsonObject["tripdailyid"]		= $("#TRIPDAILYID"+${TripColIncome.TDTIMEID}).val();
																			jsonObject["tripincomeID"]		= $("#TDTIMEID"+${TripColIncome.TDTIMEID}).val();
																			jsonObject["incomeAmount"]		= $("#incomeAmount"+${TripColIncome.TDTIMEID}).val();
																			$.ajax({
																				url: "tripDailySheetWS/updateTimeIncomeAddAmount.do",
																				type: "POST",
																				dataType: 'json',
																				data: jsonObject,
																				success: function (data) {
																					
																				},
																				error: function (e) {
																				}
																			});
																			hideLayer();
																	    })
																	});
																</script>
															</td>
															<td class="fit ar"><a
																href="removeDailyTimeIncome.in?ID=${TripColIncome.TDTIMEID}"
																data-toggle="tip" data-original-title="Click Remove"><font
																	color="red"><i class="fa fa-times"> Remove</i></font></a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="row">
											<div class="col-md-11">
												<div class="col-md-pull-7">
												<button class="btn btn-success" onclick="updateTimeIncome();">Save</button>
												</div>
												<div class="col-md-offset-6">
													<table class="table">
														<tbody>
															<tr data-object-id="" class="ng-scope">
																<td class="key"><h4>Total Time Collection :</h4></td>
																<td class="value"><h4>
																		<i class="fa fa-inr"></i> ${TimeIncomeTotal}
																	</h4></td>

															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</c:if>
							<c:if test="${empty TripColIncome}">

								<div class="modal fade" id="addTripIncome" role="dialog">
									<div class="modal-dialog">
										<!-- Modal content-->
										<div class="modal-content">
											<form action="saveFixedTripTimeIncomeAdd.in" method="post"
												name="vehicleStatu" onsubmit="return validateParts()">
												<div class="form-horizontal ">

													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
														<h4 class="modal-title" id="TripIncome">Fixed Trip
															Time Income</h4>
													</div>
													<div class="modal-body">

														<input type="hidden" value="${TripDaily.TRIPDAILYID}"
															name="TRIPDAILYID" />
														<div class="row1" id="grptripRouteName" class="form-group">

															<label class="L-size  control-label" for="TripRouteList">Select
																Sub Route Service :<abbr title="required">*</abbr>
															</label>
															<div class="I-size" id="routeSelect">
																<input type="hidden" id="TripRouteSubList"
																	name="TRIP_ROUTE_ID" style="width: 100%;"
																	placeholder="Please Enter 3 or more Route Name, NO " />
																<span id="tripRouteNameIcon" class=""></span>
																<div id="tripRouteNameErrorMsg" class="text-danger"></div>
																<label id="errorRoute" class="error"></label>
															</div>
														</div>
														<br>
													</div>
													<div class="modal-footer">
														<button type="submit" class="btn btn-primary">
															<span><spring:message code="label.master.Save" /></span>
														</button>
														<button type="button" class="btn btn-default"
															data-dismiss="modal">
															<span id="Close"><spring:message
																	code="label.master.Close" /></span>
														</button>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>

								<button type="button" class="btn btn-success"
									data-toggle="modal" data-target="#addTripIncome"
									data-whatever="@mdo">
									<span class="fa fa-plus"> Select Fixed Trip Time Collection</span>
								</button>

							</c:if>

							<form action="updateDailyTimeIncome.in" method="post">
								<input type="hidden" name="TRIPDAILYID"
									value="${TripDaily.TRIPDAILYID}">
								<div class="form-horizontal">

									<fieldset>
										<legend>Time Collection Details</legend>
										<div class="row1">
											<div class="col-md-4">
												<select class="select2" name="incomeName" style="width: 100%;"
													id="Income" required="required">
												</select>
											</div>
											<div class="col-md-3">
												<input type="number" class="form-text" min="0" name="incomeAmount" id ="Amount"
													placeholder="Amount" required="required">
											</div>
											<div class="col-md-3">
												<input type="text" class="form-text" name="incomeRefence"
													placeholder="Reference" value="X0">
											</div>
											<div class="input_fields_income">
													<button class="add_field_button_income btn btn-success">
														<i class="fa fa-plus"></i>
													</button>
												</div>

										</div>
										<br>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success"
												onclick="return validateTimeIncome()" name="commit"
												type="submit" value="Save Trip Income"> <a
												class="btn btn-link"
												href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</form>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripDaily.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js"/>"></script>
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionIncome.js"/>"></script> -->
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripRouteFixedAdd.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
		function toggle2Tax(e,n){var t=document.getElementById(e),r=document.getElementById(n);"block"==t.style.display?(t.style.display="none",r.innerHTML='<font color="green"><i class="fa fa-plus-circle"></i></font>'):(t.style.display="block",r.innerHTML="Cancel")}
		function updateTimeIncome() {
			if (confirm("Are you sure?") == true) {
				location.reload();
		     }
		}
	</script>
	<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
		
	})
</script>


</div>