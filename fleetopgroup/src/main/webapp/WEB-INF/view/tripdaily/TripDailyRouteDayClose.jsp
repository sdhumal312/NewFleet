<%@ include file="taglib.jsp"%>
<style>
.closeAmount td {
	text-align: right;
}
.breadcrumb th{text-align: right;}

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
															<c:if test="${configuration.routeInDiffColumn}">
																<th class="fit ar">Bus Name</th>
																<th class="fit ar">Route</th>
															</c:if>
															<c:if test="${!configuration.routeInDiffColumn}">
																<th class="fit ar">Bus Name/Route</th>
															</c:if>
																
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
																Double  totalDieSalAmount	= 0.0;
															%>
															<c:if test="${!empty TDRoute}">

																<c:forEach items="${TDRoute}" var="TDRoute">
																	<c:choose>
																		<c:when
																			test="${TDRoute.TRIP_CLOSE_STATUS == 'TOTAL:'}">
																		<c:if test="${!configuration.routeInDiffColumn}">
																			<tr data-object-id="" class="closeRouteAmount">
																				<c:if test="${configuration.routeInDiffColumn}">
																					<th class="fit ar">Bus Name</th>
																					<th class="fit ar">Route</th>
																				</c:if>
																				<td colspan="2" class="fit ar"><a target="_blank"
																					href="showTripDaily.in?ID=${TDRoute.TRIPROUTEID}"><c:out
																						value="${TDRoute.TRIP_CLOSE_STATUS}" /><br></a><c:out
																						value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_USAGE_KM}" /></td>
																				<td class="fit ar"><c:out
																						value="${TDRoute.TOTAL_DIESEL}" /></td>
																						
																				<c:if test="${configuration.showDieselAmount}">
																					<td class="fit ar"></td>
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
																				<c:if test="${configuration.routeInDiffColumn}">
																					<td class="fit ar"><a target="_blank"
																					 	href="showTripDaily.in?ID=${TDRoute.TRIPROUTEID}"><c:out
																						value="${TDRoute.TRIP_CLOSE_STATUS}" /></a></td>
																					<td class="fit ar"><c:out
																						value="${TDRoute.TRIP_ROUTE_NAME}" /></td>	
																				</c:if>
																				<c:if test="${!configuration.routeInDiffColumn}">
																					<td class="fit ar"><a target="_blank"
																					 	href="showTripDaily.in?ID=${TDRoute.TRIPROUTEID}"><c:out
																							value="${TDRoute.TRIP_CLOSE_STATUS}" /></a><br><c:out
																						value="${TDRoute.TRIP_ROUTE_NAME}" /></td>
																				</c:if>
																				
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
																	<c:if test="${configuration.routeInDiffColumn}">
																		<td colspan="3" class="fit ar"><c:out
																			value="Total ${VEHICLEGROUP} :" /></td>
																	</c:if>
																	<c:if test="${!configuration.routeInDiffColumn}">
																		<td colspan="2" class="fit ar"><c:out
																			value="Total ${VEHICLEGROUP} :" /></td>
																	</c:if>
																	
																	<td class="fit ar"><c:out value="${TotalUsageKM}" /></td>
																	<td class="fit ar"><c:out value="${TotalDiesel}" /></td>
																	<c:if test="${configuration.showDieselAmount}">
																		<td class="fit ar"><c:out value="${totalDieselAmount}" /></td>
																	</c:if>
																	<td class="fit ar"><c:out value="${TotalKMPL}" /></td>
																	<td class="fit ar"><c:out
																			value="${TotalPassenger}" /></td>
																	<td class="fit ar"><c:out value="${TotalPass}" /></td>
																	<td class="fit ar"><c:out value="${TotalRFID}" /></td>
																    <td class="fit ar"><c:out value="${TotalRFIDAmount}" /></td>
																	<c:if test="${!configuration.hideActualCollection}">
																		<td class="fit ar"><c:out
																			value="${TotalCollection}" /></td>
																	</c:if>
																	<c:if test="${!configuration.hideWtPenalty}">
																		<td class="fit ar"><c:out value="${TotalWT}" /></td>
																	</c:if>
																	
																	<td class="fit ar"><c:out value="${TotalNetCollection}" /></td>
																	<td class="fit ar"><c:out value="${TotalExpense}" /></td>
																	<c:if test="${!configuration.hideOT}">
																		<td class="fit ar"><c:out value="${TotalOT}" /></td>
																	</c:if>
																	
																	<td class="fit ar"><c:out value="${TotalBalance}" /></td>
																	<c:if test="${configuration.showChaloDetails}">
																	<td class="fit ar"><c:out value="${TotalChaloKm}" /></td>
																	</c:if>
																	<c:if test="${configuration.showChaloDetails}">
																	<td class="fit ar"><c:out value="${TotalChaloAmount}" /></td>
																	</c:if>

																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="4">
													<div class="row">
														<c:if test="${true}">
															<div class="alert alert-info">
																<h5>
																	Please Check before closing the day Trip Collection
																	Amount Total and Balance.<br> This Amount transfer
																	to Location wise CashBook in the day
																</h5>
															</div>
														</c:if>
														<form id="formCloseTripClose"
															action="<c:url value="/closeDayTripDailyGroup.in"/>"
															method="post" enctype="multipart/form-data"
															name="formCloseTripClose" role="form"
															class="form-horizontal">
															<br>
															<div class="form-horizontal ">
																<div class="row1" id="grpcloseDate" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label" for="closedate">Trip
																			Collection Close Date : <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="CLOSED_DATE"
																			type="text" value="${TRIP_OPEN_DATE}"
																			readonly="readonly" id="closeDate"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="closeDateIcon" class=""></span>
																		<div id="closeDateErrorMsg" class="text-danger"></div>
																	</div>
																</div>

																<div class="row1" id="grpperSingl" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Close Depot : <abbr
																			title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<input class="form-text" name="VEHICLEGROUP"
																			type="text" value="${VEHICLEGROUP}"
																			required="required" readonly="readonly" id="perSingl"
																			onkeypress="return isNumberKey(event,this);"
																			ondrop="return false;"> <span
																			id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																	<input type="hidden" value="${VEHICLEGROUPID}" name = "VEHICLEGROUPID" id="VEHICLEGROUPID"/>
																</div>
																<div class="row1" id="grpbonusCost" class="form-group">
																	<div class="col-md-3">
																		<label class="control-label">Close
																			Remarks : <abbr title="required">*</abbr>
																		</label>
																	</div>
																	<div class="col-md-7">
																		<textarea rows="3" cols="5" class="form-text"
																			name="CLOSED_REMARKS" required="required"></textarea>
																		<span id="perSinglIcon" class=""></span>
																		<div id="perSinglErrorMsg" class="text-danger"></div>
																	</div>
																</div>
															</div>
															<fieldset class="form-actions">

																<div class="text-left">
																	<input class="btn btn-success"
																		onclick="this.style.visibility = 'hidden'"
																		name="commit" type="submit"
																		value="Close Day Trip Collection"> <a
																		class="btn btn-info"
																		href="<c:url value="/newTripDaily.in"/>"><span
																		id="Cancel">Cancel</span></a>
																</div>

															</fieldset>
														</form>
													</div>
												</td>
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
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCollectionClose.js"/>"></script>
 -->
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js"/>"></script> -->
</div>