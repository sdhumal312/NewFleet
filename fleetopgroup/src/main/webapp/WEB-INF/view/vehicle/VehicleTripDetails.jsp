<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle TripSheet</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
						<a class="btn btn-success btn-sm" href="/addTripSheetEntries.in"> <i
							class="fa fa-plus"></i> Create TripSheet
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm"
						href="/showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								  <span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							      <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							      class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="/showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<section class="content-body">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<c:if test="${param.saveService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Created successfully .
						</div>
					</c:if>
					<c:if test="${param.updateService eq true}">
						<div class="alert alert-success">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Service Updated successfully .
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
					<c:if test="${param.duplicateFuelEntries eq true}">
						<div class="alert alert-danger">
							<button class="close" data-dismiss="alert" type="button">x</button>
							This Fuel Entries was Already created..(or) This Duplicate
							Entries Reference Number.
						</div>
					</c:if>

					<div class="row">
						<div class="main-body">
							<c:if test="${!empty TripSheet}">
								<div class="box">
									<div class="box-body">
										<table id="FuelTable" class="table table-hover table-striped">
											<thead>
												<tr>
													<th class="col-sm-1">TS-Id</th>
													<th class="col-sm-2">Trip Date</th>
													<th class="col-sm-2">Route</th>
													<th>O-C(km)</th>
													<th>Trip-Km</th>
													<th>Income</th>
													<th>Expense</th>
													<th>Toll</th>
													<th>Fuel</th>
													<th class="fit ar">Balance</th>
													<th class="fit ar">B-Ref:</th>
													<th class="fir ar">Status</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${TripSheet}" var="TripSheet">
													<tr data-object-id="" class="ng-scope">
														<td class="col-sm-1"><a
															href="/showTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"><c:out
																	value="TS-${TripSheet.tripSheetNumber}" /></a></td>
														<td class="col-sm-2"><c:out
																value="${TripSheet.tripOpenDate} - " /><br> <c:out
																value="${TripSheet.closetripDate}" /></td>
														<td class="col-sm-2"><c:out
																value="${TripSheet.routeName}" /></td>
														<td><span data-toggle="tip"
															data-original-title="Open KM"><c:out
																	value="${TripSheet.tripOpeningKM}" /></span><br> <span
															data-toggle="tip" data-original-title="Close KM"><c:out
																	value=" ${TripSheet.tripClosingKM}" /></span></td>
														<td><c:out value=" ${TripSheet.tripUsageKM}" /></td>
														<td><i class="fa fa-inr"></i> <c:out
																value="${TripSheet.tripTotalincome}" /></td>
														<td><i class="fa fa-inr"></i> <c:out
																value="${TripSheet.tripTotalexpense}" /></td>
														<td><i class="fa fa-inr"></i> <c:out
																value="${TripSheet.tollAmount}" /></td>			
														<td><i class="fa fa-inr"></i> <c:out
																value="${TripSheet.fuelAmount}" /></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:choose>
																<c:when
																	test="${TripSheet.tripTotalexpense > TripSheet.tripTotalincome}">
																	<abbr style="color: red;" data-toggle="tip"
																		data-original-title="Loss ${TripSheet.closeACCTripAmount}"><c:out
																			value="${TripSheet.closeACCTripAmount}" /> </abbr>
																</c:when>
																<c:when test="${TripSheet.tripTotalincome == null}">
																	<abbr style="color: red;" data-toggle="tip"
																		data-original-title="Loss ${TripSheet.closeACCTripAmount}"><c:out
																			value="${TripSheet.closeACCTripAmount}" /> </abbr>
																</c:when>
																<c:otherwise>
																	<abbr data-toggle="tip"
																		data-original-title="Gain ${TripSheet.closeACCTripAmount}"><c:out
																			value="${TripSheet.closeACCTripAmount}" /> </abbr>
																</c:otherwise>
															</c:choose></td>
														<td class="fir ar"><c:out
																value="${TripSheet.tripBookref}" /></td>
														<td class="fir ar"><c:choose>
																<c:when test="${TripSheet.tripStutesId ==1}">
																	<span class="label label-pill label-warning"><c:out
																			value="${TripSheet.tripStutes}" /></span>
																</c:when>
																<c:when test="${TripSheet.tripStutesId == 2}">
																	<span class="label label-pill label-info"><c:out
																			value="${TripSheet.tripStutes}" /></span>
																</c:when>
																<c:otherwise>
																	<span class="label label-pill label-success"><c:out
																			value="${TripSheet.tripStutes}" /></span>
																</c:otherwise>
															</c:choose></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									
									    <!-- Pagination -->
									            <c:url var="firstUrl" value="/VehicleTripDetails/${vehicle.vid}/1" />
									            <c:url var="lastUrl" value="/VehicleTripDetails/${vehicle.vid}/${endIndex}" />
									            <c:url var="prevUrl" value="/VehicleTripDetails/${vehicle.vid}/${currentIndex - 1}" />
									            <c:url var="nextUrl" value="/VehicleTripDetails/${vehicle.vid}/${currentIndex + 1}" />
									            
									      <div class="text-center">
									        <ul class="pagination pagination-lg pager">
									            <!-- First and Previous page links -->
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
									            
									            <!-- Page links -->
									            <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
									                <c:url var="pageUrl" value="/VehicleTripDetails/${vehicle.vid}/${i}" />
									                <c:choose>
									                    <c:when test="${i == currentIndex}">
									                        <li class="active"><a href="${pageUrl}"><c:out value="${i}" /></a></li>
									                    </c:when>
									                    <c:otherwise>
									                        <li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
									                    </c:otherwise>
									                </c:choose>
									            </c:forEach>
									            
									            <!-- Next and Last page links -->
									            <c:choose>
									                <c:when test="${currentIndex == endIndex}">
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
							</c:if>
							<c:if test="${empty TripSheet}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</sec:authorize>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
</div>
</section>
	<script type="text/javascript">
	 $(document).ready(function() {
         var img = $("#vehicleImage");
         var iconContainer = $("#iconContainer");

         // Check if the image is loaded
         img.on("load", function() {
             // If loaded, hide the icon
             iconContainer.hide();
         });
     });
</script>
<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
