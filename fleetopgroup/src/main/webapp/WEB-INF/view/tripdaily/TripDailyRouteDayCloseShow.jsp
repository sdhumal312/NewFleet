<%@ include file="taglib.jsp"%>
<style>
.closeAmount td {
	text-align: right;
}

.breadcrumb th {
	text-align: right;
}

.closeRouteAmount td {
	text-align: right;
	font-size: 15px;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: right;
	font-size: 18px;
	font-weight: bold;
}

.actualkm {
	width: 0.8%;
	float: left;
}
</style>
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-12 col-sm-11 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripDaily.in"/>">Trip Collection</a> /
								<a href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a>
								/ <a href="<c:url value="/closeTripDaily/1.in"/>">Close Trip</a>
								/ Close Daily TripCollection
							</div>
							<div class="pull-right">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="<c:url value="/addTripDaily.in"/>"> <span
										class="fa fa-plus"></span> Create TripCollection
									</a>
								</sec:authorize>
								<%-- <sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
									<a href="showTripSheetCol.in?id=${TripCol.TRIPCOLLID}"
										target="_blank" class="btn btn-default"><i
										class="fa fa-print"></i> Print</a>
								</sec:authorize> --%>
							</div>
						</div>
						<c:if test="${param.alreadyClosed eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Collection already Closed Successfully.
							</div>
						</c:if>
						<c:if test="${param.CloseSuccess eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								This Trip Daily Collection Closed successfully .
							</div>
						</c:if>
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div class="row">
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="${VEHICLEGROUP}" />

													</h4>
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td colspan="4">
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Bus Name/Route</th>
																<th class="fit ar">Total.K.M</th>
																<th class="fit ar">Diesel</th>
															<c:if test="${configuration.showDieselAmount}">
																<th class="fit ar">Diesel Amt</th>
															</c:if>
																<th class="fit ar">KMPL</th>
																<th class="fit ar">PSNGR</th>
																<th class="fit ar">Pass</th>
																<th class="fit ar">RFID</th>
																<th class="fit ar">RFID Amount</th>
																<c:if test="${!configuration.hideActualCollection}">
																	<th class="fit ar">Actual Collection</th>
																</c:if>
																<c:if test="${!configuration.hideWtPenalty}">
																	<th class="fit ar">WT/Penalty</th>
																</c:if>
																
																
																<th class="fit ar">Net_Collection</th>
																<th class="fit ar">Expense</th>
																<c:if test="${!configuration.hideOT}">
																	<th class="fit ar">OT</th>
																</c:if>
																<th class="fit ar">Balance</th>
																<c:if test="${configuration.showChaloDetails}">
																<th class="fit ar">Chalo km</th>
																</c:if>
																<c:if test="${configuration.showChaloDetails}">
																<th class="fit ar">Chalo Amount</th>
																</c:if>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:if test="${!empty TDRoute}">

																<c:forEach items="${TDRoute}" var="TDRoute">
																	<c:choose>
																		<c:when
																			test="${TDRoute.TRIP_CLOSE_STATUS == 'TOTAL:'}">
																			<c:if test="${!configuration.routeInDiffColumn}">
																				<tr data-object-id="" class="closeRouteAmount">
	
																					<td colspan="2" class="fit ar"><c:out
																							value="${TDRoute.TRIP_CLOSE_STATUS}" /><br>
																					<c:out value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_USAGE_KM}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_DIESEL}" /></td>
																							
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_DIESELKML}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_TOTALPASSNGER}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_PASS_PASSNGER}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_RFIDPASS}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_RFID_AMOUNT}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_COLLECTION}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_WT}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_NET_COLLECTION}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_EXPENSE}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_OVERTIME}" /></td>
																					<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_BALANCE}" /></td>
																					<c:if test="${configuration.showChaloDetails}">		
																					<td class="fit ar"><c:out
																						value="${TDRoute.CHALO_KM}" /></td>	
																					</c:if>
																					<c:if test="${configuration.showChaloDetails}">																				
																					<td class="fit ar"><c:out
																						value="${TDRoute.CHALO_AMOUNT}" /></td>
																					</c:if>	
																				</tr>
																			</c:if>
																			
																		</c:when>
																		<c:otherwise>
																			<tr data-object-id="" class="closeAmount">
																				<td class="fit">
																					<%
																						out.println(hitsCount);
																											hitsCount += 1;
																					%>
																				</td>
																				<td class="fit ar"><a target="_blank"
																					href="showTripDaily.in?ID=${TDRoute.TRIPROUTEID}"><c:out
																							value="${TDRoute.TRIP_CLOSE_STATUS}" /></a><br>
																				<c:out value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_USAGE_KM}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_DIESEL}" /></td>
																				<c:if test="${configuration.showDieselAmount}">
																						<td class="fit ar"><c:out
																							value="${TDRoute.TOTAL_DIESEL_AMOUNT}" /></td>
																					</c:if>	
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_DIESELKML}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_TOTALPASSNGER}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_PASS_PASSNGER}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_RFIDPASS}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_RFID_AMOUNT}" /></td>
																			
																			<c:if test="${!configuration.hideActualCollection}">
																					<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_COLLECTION}" /></td>
																			</c:if>
																			<c:if test="${!configuration.hideWtPenalty}">
																					<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_WT}" /></td>
																			</c:if>
																				
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_NET_COLLECTION}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_EXPENSE}" /></td>
																			<c:if test="${!configuration.hideOT}">
																					<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_OVERTIME}" /></td>
																			</c:if>
																				
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_BALANCE}" /></td>
																				<c:if test="${configuration.showChaloDetails}">		
																				<td class="fit ar"><c:out
																						value="${TDRoute.CHALO_KM}" /></td>
																				</c:if>
																				<c:if test="${configuration.showChaloDetails}">
																				<td class="fit ar"><c:out
																						value="${TDRoute.CHALO_AMOUNT}" /></td>
																				</c:if>		

																			</tr>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>

																<tr data-object-id="" class="closeGroupAmount">
																	<td colspan="2" class="fit ar"><c:out
																			value="Total ${TDGroupCol.VEHICLE_GROUP} :" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_USAGE_KM}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_DIESEL}" /></td>
																	<c:if test="${configuration.showDieselAmount}">
																					<td class="fit ar"><c:out
																			value="${totalDieselAmount}" /></td>
																	</c:if>		

																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_DIESEL_MILAGE}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_TOTALPASSNGER}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_PASS_PASSNGER}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_RFIDPASS}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_RFID_AMOUNT}" /></td>
																	<c:if test="${!configuration.hideActualCollection}">
																					<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_COLLECTION}" /></td>
																	</c:if>
																<c:if test="${!configuration.hideWtPenalty}">
																					<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_WT}" /></td>
																</c:if>
																	
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_NET_COLLECTION}" /></td>
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_EXPENSE}" /></td>
																<c:if test="${!configuration.hideOT}">
																					<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_OVERTIME}" /></td>
																</c:if>			
																	
																	<td class="fit ar"><c:out
																			value="${TDGroupCol.TOTAL_BALANCE}" /></td>
																	<c:if test="${configuration.showChaloDetails}">		
																	<td class="fit ar"><c:out value="${TDGroupCol.totalCHALO_KM}" /></td>
																	</c:if>
																	<c:if test="${configuration.showChaloDetails}">
																	<td class="fit ar"><c:out value="${TDGroupCol.totalCHALO_AMOUNT}" /></td>
																	</c:if>
																</tr>
																
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr class="">
												<td>Closed By:</td>
												<td><c:out value="${TDGroupCol.CREATEDBY}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="">
												<td>Closed Date:</td>
												<td><c:out value="${TDGroupCol.CREATED}" /></td>
												<td colspan="2"></td>
											</tr>
											<tr class="">
												<td>Closed Remark :</td>
												<td><c:out value="${TDGroupCol.TRIP_CLOSE_REMARKS}" /></td>
												<td colspan="2"></td>
											</tr>
										</tbody>
									</table>
								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

</div>