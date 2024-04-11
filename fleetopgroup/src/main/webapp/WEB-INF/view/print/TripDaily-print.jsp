<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
		<section class="invoice">
			<div class="row">
				<table class="table table-hover table-bordered table-striped">
					<tbody>
						<tr>
							<td><c:choose>
									<c:when test="${company.company_id != null}">
										<img
											src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
											class="img-rounded" alt="Company Logo" width="280"
											height="40" />

									</c:when>
									<c:otherwise>
										<i class="fa fa-globe"></i>
										<c:out value="${company.company_name}" />
									</c:otherwise>
								</c:choose></td>
							<td>Print By: ${company.firstName}_${company.lastName}</td>
						</tr>
						<tr>
							<td colspan="2">Branch :<c:out
									value=" ${company.branch_name}  , " /> Department :<c:out
									value=" ${company.department_name}" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="row invoice-info">
				<h3>TripSheet ${TripDaily.TRIPDAILYNUMBER}</h3>
			</div>

			<div class="row ">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr>
								<td colspan="4">
									<h4 align="center">
										<c:out value="${TripDaily.TRIP_ROUTE_NAME}" />
										<br>
										<c:out value="${TripDaily.VEHICLE_GROUP}" />
									</h4>
								</td>
							</tr>
							<tr>
								<td>Vehicle :</td>
								<td><c:choose>
										<c:when test="${TripDaily.VEHICLEID == 0}">
											<a
												href="<c:url value="/showVehicle?vid=${TripDaily.VEHICLEID}"/>"
												target="_blank" style="color: #000000;" href="#"
												data-toggle="tip" data-original-title="Click Vehicle Info">
												<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:when>
										<c:otherwise>
											<a style="color: #000000;"
												href="showVehicle.in?vid=${TripDaily.VEHICLEID}"
												data-toggle="tip" data-original-title="Click Vehicle Info">
												<c:out value="${TripDaily.VEHICLE_REGISTRATION}" />
											</a>
										</c:otherwise>
									</c:choose></td>
								<td>Depot :</td>
								<td><c:out value="${TripDaily.VEHICLE_GROUP}" /></td>
							</tr>
							<tr>
								<td>Date of Journey :</td>
								<td><a data-toggle="tip"
									data-original-title="Trip Open Date"> <c:out
											value="${TripDaily.TRIP_OPEN_DATE}" /></a></td>
								<td>Total Passenger:</td>
								<td><a data-toggle="tip"
									data-original-title="total passenger"> <c:out
											value="${TripDaily.TRIP_TOTALPASSNGER}" />
								</a></td>
							</tr>
							<tr>
								<td>Driver:</td>
								<td><a
									href="showDriver.in?driver_id=${TripDaily.TRIP_DRIVER_ID}"
									target="_blank" data-toggle="tip" data-original-title="Driver">
										<c:out value="${TripDaily.TRIP_DRIVER_NAME}" />
								</a></td>
								<td>RFID Pass :</td>
								<td><a data-toggle="tip" data-original-title="RFID pass"><c:out
											value="${TripDaily.TRIP_RFIDPASS}" /></a></td>

							</tr>
							<tr>
								<td>Conductor :</td>
								<td><a
									href="showDriver.in?driver_id=${TripDaily.TRIP_CONDUCTOR_ID}"
									target="_blank" data-toggle="tip"
									data-original-title="Driver 2"><c:out
											value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></a></td>

								<td>Cleaner :</td>
								<td><a data-toggle="tip" data-original-title="Cleaner"><c:out
											value="${TripDaily.TRIP_CLEANER_NAME}" /></a></td>
							</tr>
							<tr>
								<td>Pass :</td>
								<td><a data-toggle="tip" data-original-title="Total Pass"><c:out
											value="${TripDaily.TRIP_PASS_PASSNGER}" /></a></td>

								<td>RFID Amount :</td>
								<td><a data-toggle="tip" data-original-title="RFID Amount"><c:out
											value="${TripDaily.TRIP_RFID_AMOUNT}" /></a></td>
							</tr>
							<tr>
								<td>Diesel :</td>
								<td><a data-toggle="tip" data-original-title="Diesel"><c:out
											value="${TripDaily.TRIP_DIESEL}" /></a></td>

								<td>Opening KM:</td>
								<td><a data-toggle="tip" data-original-title="Opening KM"><c:out
											value="${TripDaily.TRIP_OPEN_KM}" /></a></td>
							</tr>
							<tr>
								<td>Diesel KMPL:</td>
								<td><a data-toggle="tip" data-original-title="Diesel kmpl"><c:out
											value="${TripDaily.TRIP_DIESELKMPL}" /></a></td>
								<td>Closing KM:</td>
								<td><a data-toggle="tip" data-original-title="closing KM">
										<c:out value="${TripDaily.TRIP_CLOSE_KM}" />
								</a></td>
							</tr>
							<tr>
								<td>OverTime :</td>
								<td><a data-toggle="tip" data-original-title="over time"><c:out
											value="${TripDaily.TRIP_OVERTIME}" /></a></td>
								<td>Total KM:</td>
								<td><a data-toggle="tip" data-original-title="usage KM">
										<c:out value="${TripDaily.TRIP_USAGE_KM}" />
								</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<br>
			
			<!-- /.row -->
			<!-- Table Trip Fuel Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>TRIP FUEL DATA</caption>
						<thead>
							<tr>
								<th>NO</th>
								<th>DOF</th>
								<th>PLACE</th>
								<th>N O L</th>
								<th>PRICE/LITER</th>
								<th>TOTAL</th>
								<th>ODA</th>
								<th>BILL NO</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="13px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>

			<!-- Table ONWARD JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>ONWARD JOURNEY LINE CHECKING REPORT</caption>
						<thead>
							<tr>
								<th>DATE &amp; TIME</th>
								<th>PLACE</th>
								<th>TOTAL SEATS</th>
								<th>EXTRA SEAT</th>
								<th>EXTRA LUGGAGE</th>
								<th>CHECKED BY NAME AND SIGN</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>


				</div>
				<!-- /.col -->
			</div>

			<!-- Table LAST DESTINATION REACHING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>LAST DESTINATION REACHING REPORT</caption>
						<thead>
							<tr>
								<th>DESTINATION</th>
								<th>REACHING TIME</th>
								<th>DRIVER 1</th>
								<th>DRIVER 2</th>
								<th>CLEANER</th>
								<th>SIGNATURE</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- Table RETURN JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>RETURN JOURNEY LINE CHECKING REPORT</caption>
						<thead>
							<tr>
								<th>DATE &amp; TIME</th>
								<th>PLACE</th>
								<th>TOTAL SEATS</th>
								<th>EXTRA SEAT</th>
								<th>EXTRA LUGGAGE</th>
								<th>CHECKED BY NAME AND SIGN</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td height="15px;"></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- Table RETURN JOURNEY LINE CHECKING REPORT Data -->
			<div class="row">
				<div class="col-xs-11 table-responsive">
					<table class="table table-bordered table-striped">
						<caption>NOTE:</caption>
						<tbody>
							<tr>
								<td height="10px;"></td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<p class="lead">HAPPY JOURNEY</p>
				</div>
				<!-- /.col -->
			</div>
		</section>
	</sec:authorize>
</div>