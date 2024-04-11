<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
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
									TripCollection</a> / Add Income
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
						</sec:authorize>
					</div>
				</div>
				
				<div class="row" onload="">
							<form accept-charset="UTF-8" action="UpdateTripDailyPassengerDetails.in" method="post"
																		class="form-horizontal">
							<div class="form-horizontal">	
								<input type="hidden" name="TRIPDAILYID" value="${TripDaily.TRIPDAILYID}">
							<fieldset>									
							<legend>Passenger Details</legend>		
							<div class="box">
							 <div class="boxinside">							
										<div class="row1" id="grptripliter" class="form-group">
											<label class="L-size control-label" for="tripliter">Total
												Passenger :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_TOTALPASSNGER"
													id="tripliter" placeholder="enter total passenger"
													type="text" maxlength="10" value="${TripDaily.TRIP_TOTALPASSNGER}"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripliterIcon"
													class=""></span>
												<div id="tripliterErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripPass" class="form-group">
											<label class="L-size control-label" for="tripliter">
												PASS :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_PASS_PASSNGER"
													id="tripPass" placeholder="enter total pass"
													type="text" maxlength="10" value="${TripDaily.TRIP_PASS_PASSNGER}"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripPassIcon"
													class=""></span>
												<div id="tripPassErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<div class="row1" id="grptripSingl" class="form-group">
											<label class="L-size control-label" for="tripSingl">RFID
												PASS :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_RFIDPASS" id="tripSingl"
													placeholder="enter RFID pass " type="text" maxlength="10" value="${TripDaily.TRIP_RFIDPASS}"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripSinglIcon"
													class=""></span>
												<div id="tripSinglErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										
										<div class="row1" id="grptripAmount" class="form-group">
											<label class="L-size control-label" for="tripSingl">RFID
												AMOUNT :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="TRIP_RFID_AMOUNT" id="tripAmount"
													placeholder="enter RFID Amount " type="text" maxlength="10" value="${TripDaily.TRIP_RFID_AMOUNT}"
													onkeypress="return tripOpening(event);"
													ondrop="return false;"><span id="tripAmountIcon"
													class=""></span>
												<div id="tripAmountErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										<c:if test="${!configuration.hideOverTimeColumn}">
											<div class="row1" id="grptripOverTime" class="form-group">
												<label class="L-size control-label" for="tripSingl">Over
													Time :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="form-text" name="TRIP_OVERTIME"
														id="tripOverTime" placeholder="enter over time "
														type="text" maxlength="10" value="${TripDaily.TRIP_OVERTIME}"
														onkeypress="return tripOpening(event);"
														ondrop="return false;"><span id="tripOverTimeIcon"
														class=""></span>
													<div id="tripOverTimeErrorMsg" class="text-danger"></div>
													<label class="error" id="errortripOpening"
														style="display: none"> </label> <label id="errorOpening"
														class="error"></label>
												</div>
											</div>
										</c:if>
										<div class="row1" id="grptripliter" class="form-group">
											<label class="L-size control-label" for="tripliter">No Of
												Round Trip :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="noOfRoundTrip"
													id="noOfRoundTrip" placeholder="enter total passenger"
													type="number" step="any" maxlength="10" value="${TripDaily.noOfRoundTrip}"
													ondrop="return false;"><span id="tripliterIcon"
													class=""></span>
												<div id="tripliterErrorMsg" class="text-danger"></div>
												<label class="error" id="errortripOpening"
													style="display: none"> </label> <label id="errorOpening"
													class="error"></label>
											</div>
										</div>
										</fieldset>	
									<div class="box-footer h-padded">
											<input class="btn btn-success"
												onclick="return validatePassengerDetails();" name="commit"
												type="submit" value="Save Details"> <a
												class="btn btn-link"
												href="<c:url value="/showTripDaily?ID=${TripDaily.TRIPDAILYID}"/>">Cancel</a>
									</div>
									</div>
								</form>
							
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionIncome.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
		});
		function toggle2Tax(e,n){var t=document.getElementById(e),r=document.getElementById(n);"block"==t.style.display?(t.style.display="none",r.innerHTML='<font color="green"><i class="fa fa-plus-circle"></i></font>'):(t.style.display="block",r.innerHTML="Cancel")}
	</script>
	<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
		
	})
</script>


</div>