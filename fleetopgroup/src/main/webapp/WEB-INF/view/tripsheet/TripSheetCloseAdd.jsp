<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	
<style>
.blink_me {
  animation: blinker 1s linear infinite;
}

@keyframes blinker {  
  50% { opacity: 0; }
}

    .form-horizontal1 {
        border: 1px solid #ccc;
        padding: 20px;
        border-radius: 5px;
       /*  background-color: #f8f8f8; */
        max-width: 500px; 
        margin: auto;
    }

    .form-horizontal1 p {
        margin: 0;
        padding: 8px;
        border-bottom: 1px solid #ddd;
        font-size: 14px;
    }

    .form-horizontal1 p span {
        float: right;
        font-weight: bold;
    }

</style>

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
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									TripSheet</a> / <small>Close TripSheet</small>
							</div>
							<div class="pull-right"></div>
						</div>
						<sec:authorize access="!hasAuthority('CLOSE_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripSheet.created}</h5>
									</div>
								</div>

								<div class="row">
									<h4 align="center">
										<a href="showVehicle.in?vid=${TripSheet.vid}"
											data-toggle="tip" data-original-title="Click Vehicle Info">
											<c:out value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>
								<div class="col-md-3"></div>
							</div>
							<div class="row">
								<h4 align="center">${TripSheet.routeName}</h4>
								<input type="hidden" id="showExtraReceived" value="${configuration.showExtraReceived}">
								<input type="hidden" id="dispatchedByTimeOn" value="${TripSheet.dispatchedByTimeOn}">
								<input type="hidden" id="driver1Id" value="${TripSheet.tripFristDriverID}">
								<input type="hidden" id="driver2Id" value="${TripSheet.tripSecDriverID}">
								<input type="hidden" id="cleanerId" value="${TripSheet.tripCleanerID}">
								<input type="hidden" id="dispatchedByTime" value="${TripSheet.tripOpenDate}">
								<input type="hidden" id="reverseDriverBalance" value="${configuration.reverseDriverBalance}">
								<input type="hidden" id="removeAdvanceFromDriverBalance" value="${configuration.removeAdvanceFromDriverBalance}">
								
								<input type="hidden" id="meterNotWorkingDontValidate" value="${configuration.meterNotWorkingDontValidate}">
								
								<input type="hidden" id ="advanceForReverseBalance" value="${TripSheet.tripTotalAdvance}">
								<input type="hidden" id ="expenseForReverseBalance" value="${TripSheet.tripTotalexpense}">
								<input type="hidden" id ="incomeForReverseBalance" value="${TripSheet.tripTotalincome}">
								<input type="hidden" id ="roundFigureAmount" value="${configuration.roundFigureAmount}">
								<input type="hidden" id ="validateOdometerInTripSheet" value="${configuration.validateOdometerInTripSheet}">
								<input type="hidden" id ="validateTripCloseTimeAsCurrentTime" value="${configuration.validateTripCloseTimeAsCurrentTime}">
								<input type="hidden" id ="showGPSodoInOpeningKm" value="${configuration.showGPSodoInOpeningKm}">
							</div>

							<div class="secondary-header-title">
								<table class="table">
										<tbody>
											<tr>
												<td>Date of Journey : <a data-toggle="tip"
													data-original-title="Trip Open Date"> <c:out
															value="${TripSheet.tripOpenDate}  TO" /></a> <a
													data-toggle="tip" data-original-title="Trip Close Date">
														<c:out value="  ${TripSheet.closetripDate}" />
												</a></td>
												<td>Group Service : <a data-toggle="tip"
													data-original-title="Group Service"><c:out
															value="${TripSheet.vehicle_Group}" /></a></td>
												<td>Booking No : <a data-toggle="tip"
													data-original-title="Booking No"> <c:out
															value="${TripSheet.tripBookref}" /></a></td>
											</tr>
											<tr>
												<td>Trip Route Point : <a data-toggle="tip"
													data-original-title="Fixed Point"> <c:out
															value="${TripSheet.routeAttendancePoint}" /></a></td>
												<td>Trip Route Volume : <a data-toggle="tip"
													data-original-title="Volume Point"> <c:out
															value="${TripSheet.routeTotalLiter}" /></a></td>
												<td>Driver 1 : <a data-toggle="tip"
													data-original-title="Driver 1"> 
													<c:if test="${configuration.showDriverFullName}">
														<c:out value="${TripSheet.tripFristDriverName}  ${TripSheet.tripFristDriverLastName}" />
													<c:out value=" -${TripSheet.tripFristDriverFatherName} "/>
													</c:if>
													<c:if test="${!configuration.showDriverFullName}">
														<c:out value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName}" />
														<c:out value=" -${TripSheet.tripFristDriverFatherName}"/>
													</c:if>
													</a> /  <c:out value="${TripSheet.tripFristDriverMobile }" /></td>
											</tr>
											
											<tr>
											<c:if test="${configuration.driver2}">
												<td>Driver 2 : <a data-toggle="tip"
													data-original-title="Driver 2">
													<c:if test="${configuration.showDriverFullName}">
														<c:out value="${TripSheet.tripSecDriverName}  ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" />
													</c:if>
													<c:if test="${!configuration.showDriverFullName}">
														<c:out value="${TripSheet.tripSecDriverName}" />
													</c:if>	
														</a> / <c:out value="${TripSheet.tripSecDriverMobile}" /></td>
											</c:if>
												<c:if test="${configuration.cleaner}">		
												<td>Cleaner : <a data-toggle="tip"
													data-original-title="Cleaner">
													<c:if test="${!configuration.showDriverFullName}">
													<c:out value="${TripSheet.tripCleanerName}" /></a> </c:if>
													
													<c:if test="${configuration.showDriverFullName}">
													<c:out value=" ${TripSheet.tripCleanerName} ${TripSheet.tripCleanerMidleName} ${TripSheet.tripCleanerLastName}"></c:out>
													</c:if> / <c:out
														value="${TripSheet.tripCleanerMobile}" /></td>
														
														</c:if>
												<td>Opening KM: <a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripSheet.tripOpeningKM}" /></a></td>
															<td></td><td></td>
											</tr>
											<tr>
												<td>Driver 1 Route Point : <a data-toggle="tip"
													data-original-title="Driver 1"><c:out
															value="${TripSheet.tripFristDriverRoutePoint}" /></a></td>
												<td>Driver 2 Route Point : <a data-toggle="tip"
													data-original-title="Driver 2"><c:out
															value="${TripSheet.tripSecDriverRoutePoint}" /></a></td>
												<td>Cleaner Route Point : <a data-toggle="tip"
													data-original-title="Cleaner"><c:out
															value="${TripSheet.tripCleanerRoutePoint}" /></a></td>
											</tr>
											<tr>
												<td>Closing KM: <a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripSheet.tripClosingKM}" /></a></td>

												<c:choose>
													<c:when
														test="${TripSheet.tripClosingKMStatusId == 1}">
														<td>Meter : <a data-toggle="tip"
															data-original-title="Meter Not Working"> <c:out
																	value="Meter Not Working " /></a></td>

													</c:when>
													<c:otherwise>
														<td>Usage KM: <a data-toggle="tip"
															data-original-title="usage KM"> <c:out
																	value="${TripSheet.tripUsageKM}" /></a></td>

													</c:otherwise>
												</c:choose>

												<td>Dispatch By: <a data-toggle="tip"
													data-original-title="dispatched By"><c:out
															value="${TripSheet.dispatchedBy}" /></a></td>
											</tr>
											<tr>
												<td>Dispatch Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.dispatchedLocation}" /></a></td>
												<td>Closed By: <a data-toggle="tip"
													data-original-title="closed By"><c:out
															value="${TripSheet.closedBy}" /></a></td>
												<td>Closed Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.cloesdLocation}" /></a></td>
											</tr>
											
											<c:if test="${configuration.showSubroute}">
											<tr>
												<td colspan="3">Sub Route: <a data-toggle="tip"
													data-original-title="Sub Route"><c:out
															value="${TripSheet.subRouteName}" /></a>
												</td>
												<td></td>
												<td></td>
											</tr>
											</c:if>
											
											<c:if test="${configuration.showLoadType}">
												<tr>
													<td colspan="3">Load Type: <a data-toggle="tip"
														data-original-title="Sub Route"><c:out
																value="${TripSheet.loadTypeName}" /></a>
													</td>													
												</tr>
											</c:if>
											
											
											<tr>
												<td >Route Remark: <a data-toggle="tip"
													data-original-title="Route Remark"><c:out
															value="${TripSheet.routeRemark}" /></a></td>
												<c:if test="${configuration.showPODdetails}">			
												<td >Number of POD: <a data-toggle="tip"
													data-original-title="Number of POD"><c:out
															value="${TripSheet.noOfPOD}" /></a></td>
												</c:if>
												<c:if test="${!configuration.showPODdetails}">
													<td></td>
												</c:if>
												<td></td>			
											</tr>
												
											
										</tbody>
									</table>
							</div>
							<br>
							<c:if test="${true}">
								<div class="alert alert-info">
									<button class="close" data-dismiss="alert" type="button">x</button>
									This TripSheet Closed driver Account To driver Attendance
									Create Automatically.. Please Enter Right data.
								</div>
							</c:if>
								<table class="table table-bordered table-striped">
									<thead>
										<tr class="breadcrumb">
											<th class="fit ar">No</th>
											<th class="fit ar">Name</th>
											<th class="fit ar">TripRoute Point</th>
											<th class="fit ar">Halt Point</th>
											<th class="fit ar">Total</th>
										</tr>
									</thead>
									<tbody>
										<%
											Integer hitsCountDis = 1;
										%>
										<c:if test="${!empty  DisplayPoint}">
										<c:forEach items="${DisplayPoint}" var="DisplayPoint">

											<tr data-object-id="" class="ng-scope">
												<td class="fit">
													<%
														out.println(hitsCountDis);
																hitsCountDis += 1;
													%>
												</td>

												<td><c:out value="${DisplayPoint.DRIVER_NAME}" /></td>
												 <sec:authorize access="!hasAuthority('CLOSE_TRIPSHEET')"> 
													<td><input type="hidden" required="required"
														value="${DisplayPoint.DRIVERID}" name="ROUTE_DRIVERID" />
														<input type="hidden"
														id="RoutePoint${DisplayPoint.DRIVERID}"
														required="required" 
														value="${DisplayPoint.TRIP_ROUTE_POINT}"
														name="ROUTE_POINT" />
														 <c:out
															value="${DisplayPoint.TRIP_ROUTE_POINT}" />
															</td>
															
													<td><c:out value="${DisplayPoint.HALT_POINT}" /></td>
													<td><c:out value="${DisplayPoint.POINT_TOTAL}" /></td>
												</sec:authorize>
												<sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">

													<td>
													<input type="hidden" id="validateTripRoutePoint" value ="${configuration.validateTripRoutePoint}"/>
													<input type="hidden" class="string required"
														required="required" value="${DisplayPoint.DRIVERID}"
														name="ROUTE_DRIVERID" /> <input type="text"
														onkeypress="return isNumberKeyWithDecimal(event,this.id)"
														id="RoutePoint${DisplayPoint.DRIVERID}"
														class="col-xs-3 form-text"
														required="required"
														onkeyup="javascript:TotalPoint('RoutePoint${DisplayPoint.DRIVERID}', 'HaltPoint${DisplayPoint.DRIVERID}', 'TotalPoint${DisplayPoint.DRIVERID}');"
														value="${DisplayPoint.TRIP_ROUTE_POINT}"
														name="ROUTE_POINT" /></td>
													<td><input type="text"
														class="col-xs-3 form-text"
														readonly="readonly" id="HaltPoint${DisplayPoint.DRIVERID}"
														value="${DisplayPoint.HALT_POINT}"></td>
													<td><input type="text"
														class="col-xs-3 form-text"
														readonly="readonly"
														id="TotalPoint${DisplayPoint.DRIVERID}"
														value="${DisplayPoint.POINT_TOTAL}"></td>
												</sec:authorize>

											</tr>
										</c:forEach>
										</c:if>
										<c:if test="${empty  DisplayPoint}">
										<input type="hidden" required="required"
														value="0" name="ROUTE_DRIVERID" />
										<input type="hidden" required="required"
														value="0" name="ROUTE_POINT" />				
										</c:if>
										<tr>
											<td colspan="5" style="text-align: center;"><h4>
													<span id="balanceFormula">(Advance - Expense ) = Balance</span>
											</h4></td>
										</tr>
										<tr>
											<td colspan="5" style="text-align: center;"><h4>
												<span id="balanceFormulaVal">
													(
													<fmt:formatNumber pattern="#.##" value="${TripSheet.tripTotalAdvance}" />- 
													<fmt:formatNumber pattern="#.##" value="${TripSheet.tripTotalexpense}" />
													) = <i class="fa fa-inr"></i>
													<fmt:formatNumber pattern="#.##"
														value="${TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense}" />
														</span>
												</h4></td>
										</tr>
									</tbody>
								</table>
								
							
								<table class="table">
									<tbody>
									 
									<tr>
										 
											<td><c:if test="${!empty TripSheetExtraOptions}"> 
                                           <script> console.log("Hi")</script>
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Extra Name</th>
																<th class="fit ar">Quantity</th>
																<th class="fit ar">Description</th>
																<th class="fit ar">Extra Date</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
											z				  <c:forEach items="${TripSheetExtraOptions}"
																var="TripSheetExtraOptions">

																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar">
																	
																		<input name="" type="text" readonly="readonly"
																		class="col-xs-3 form-text"
																	    id="tripsheetextranamey${TripSheetExtraOptions.tripsheetextraname}"
																		value="${TripSheetExtraOptions.tripsheetextraname}">
																		<input name="tripsheetextraname" type="hidden" 
																		value="${TripSheetExtraOptions.tripsheetoptionsId}">	
																	</td>
																	<td>
																	<input name="TripSheetExtraQuantityReceived" type="text"
																		class="col-xs-3 form-text"
																	    id="tripSheetExtraQuantity${TripSheetExtraOptions.tripsheetoptionsId}"
																		<%-- value="${TripSheetExtraOptions.tripSheetExtraQuantity}" --%>
																		onkeyup="return tripExtra(${TripSheetExtraOptions.tripsheetoptionsId},${TripSheetExtraOptions.tripSheetExtraQuantity});">
																	</td>
																	<td class="fit ar">
																	
																		<input name="TripSheetExtraDescription" type="text" 
																		class="col-xs-3 form-text"
																	    id="tripsheetextradescription${TripSheetExtraOptions.tripsheetoptionsId}"
																		value="${TripSheetExtraOptions.tripsheetextradescription}">	
																	</td>
																			
																	<td class="fit ar"><c:out
																			value="${TripSheetExtraOptions.created}" /></td>
																</tr>
 

															</c:forEach>  
															
														</tbody>
													</table>
												 </c:if> 
												</td> 
										</tr>
									
									</tbody>
									</table>

								<input type="hidden" name="tripSheetID" id="tripSheetId"
									value="${TripSheet.tripSheetID}">
								<input type="hidden" id="allowedKMForExtendedTrip" value="${allowedKMForExtendedTrip}">
								<input type="hidden" id="minAllowedKM" value="${minAllowedKM}">
								<input type="hidden" id="maxAllowedKM" value="${maxAllowedKM}">
								<input type="hidden" id="oldminAllowedKM" value="${minAllowedKM}">
								<input type="hidden" id="oldmaxAllowedKM" value="${maxAllowedKM}">
								<input type="hidden" id="totalIncome" value="${totalIncome}">
								<input type="hidden" id="totalExpense" value="${totalExpense}">
								<input type="hidden" id="totalAdvance" value="${totalAdvance}">
								<input type="hidden" id="gpsFlavor" value="${gpsFlavor}">
								<input type="hidden" id="allowTallyPushButtonOnClose" value="${configuration.allowTallyPushButtonOnClose}">
								<input type="hidden" id="newLogicForTripSheetDriverBalance" value="${configuration.newLogicForTripSheetDriverBalance}" >
								<input type="hidden" id="totalExpenseAmount" value="${TripSheet.tripTotalAdvance}">
								<input type="hidden" id="totalAdvanceAmount" value="${TripSheet.tripTotalexpense}">
								<input type="hidden" id="fuelVolume" value ="${fuel}" > 
								<input type="hidden" id="fuelMileage" value ="${fuelMileage}" > 
								<input type="hidden" id="fuelCostPerLiter" value ="${fuelCostPerLiter}" > 
								
								 <c:if test ="${configuration.newLogicForTripSheetDriverBalance}">
									<div class="form-horizontal" id="showDriverData" style ="display : none;">
										<legend>
											Driver Balance
											<div class="form-horizontal1">
												
												<p>Advance Amount <span id ="advanceID"></span></p>
												<p>Expenses Amount <span id ="expenseID"></span></p>
												<p>PerLiter Rate <span id ="perLitreRate"></span></p>
												<p>Expected Litre <span id ="expectedLitre"></span> </p>
												<p>Actual Litre <span id ="actualLitre"></span> </p>
												<p>Diesel Expenses give/take by driver <span id ="dieselExpense"></span></p>
												<p id="amountText"><span id ="actualDriver"></span> </p>	
									
											</div>
										</legend>
									</div>
									<br>
								</c:if>
								
								
								<div class="form-horizontal">					
									<fieldset>												
									<c:if test="${!configuration.addInDriverbalanceAfterTripClose}">									
										<legend>
									
										office Use 
										<a class="btn btn btn-light statusTwo statusThree hide"	id="driverWalletBalance" href="#" class="confirmation">
											Driver Balance is : <span class="fa fa-rupee"
											id="driverWalletBalanceSpan"> ${driverWalleteBalance}</span></a>
									</legend>
									</c:if>
										<c:if test="${configuration.allowTallyPushButtonOnClose}">
											<div class="row1" id="extendedTrip">
												<label class="L-size control-label">Push To Tally  :</label>
												<div class="I-size">
														<div class="">
															<div class="btn-group" id="tally" data-toggle="buttons">
																<label class="btn btn-default btn-on btn-lg">
																	<input type="radio" value="0" name="tallyPush"
																	id="isTallyPushNo">No
																</label> <label class="btn btn-default btn-off btn-lg active"> <input
																	type="radio" value="1" name="tallyPush" id="isTallyPushYes" checked="checked">Yes
																</label>
															</div>
														</div>
													</div>
											</div>
										</c:if>

										<div class="row1">
										
											<label class="L-size control-label">Amount :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3">
												<input class="string required form-text" id="closeTripAmount"
													name="closeTripAmount" maxlength="50" type="text"
													required="required"
													value='0'
													onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													ondrop="return false;"> <label class="error"
													id="errorAdvanceAmount" style="display: none"> </label>
											</div>
											
											<input type="hidden" id="lossInTrip" value="${lossInTrip}">
											<input type="hidden" id="driverBalanceKey" value="${driverBalanceKey}">
											<input type="hidden" id="balanceAmount" value="${balanceAmount}">
											
											<label class="L-size control-label">Pay to :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3">
												<select class="string required form-text"
													name="closeTripStatusId" id="closeTripStatusId" required="required" onchange="setPaidByFeild();">
													<option value="1">OFFICE</option>
													<option value="2">DRIVER</option>
												</select>
											</div>
											
										</div>
										
										
										<div class="row1" id="paidByDriver">
										
											<label class="L-size control-label"> Paid By:<abbr
												title="required">*</abbr></label>
											<div class="col-md-3">
												<select class="string required form-text" id="closeTripNameByIdselect"
													name="closeTripNameById" required="required">
													<option value="${TripSheet.tripFristDriverID}">${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}</option>
													<option value="${TripSheet.tripSecDriverID}">${TripSheet.tripSecDriverName}  ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}</option>
												</select> <label class="error" id="errorAdvancePaidby"
													style="display: none"> </label>

											</div>
											
											<label class="L-size control-label">Ref-NO:</label>
											<div class="col-md-3">
												<input class="string required form-text"
													name="closeTripReference" maxlength="10" type="text"
													required="required" value="0" id="closeTripReference"
													onkeypress="return IsReference(event);"
													ondrop="return false;"> <label class="error"
													id="errorReference" style="display: none"> </label>
											</div>
											
										</div>
										<div class="row1" id="paidByOffice" style="display: none;">
											<label class="L-size control-label"> Paid By :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3">
												<select class="string required form-text" id="paidByOfficeSelect"
													name="paidByOffice" required="required">
													<option value="1">OFFICE</option>
												</select> <label class="error" id="errorAdvancePaidby"
													style="display: none"> </label>
											</div>
											
											<label class="L-size control-label">Ref-NO:<abbr
												title="required">*</abbr></label>
											<div class="col-md-3">
												<input class="string required form-text"
													name="closeTripReference" maxlength="10" type="text"
													required="required" value="0" id="closeTripReference"
													onkeypress="return IsReference(event);"
													ondrop="return false;"> <label class="error"
													id="errorReference" style="display: none"> </label>
											</div>
										</div>
										
										<input type="hidden" name="allowGPSIntegration" value="${allowGPSIntegration }" id="allowGPSIntegration">
										<input type="hidden" name="showTime" value="${ShowTime}" id="showTime">
										<input type="hidden" name="vehicleId" value="${TripSheet.vid}" id="vehicleId">
										<input type="hidden" name="companyId" value="${companyId}" id="companyId">
										<input type="hidden" name="userId" value="${userId}" id="userId">
										<input type="hidden" name="validateClosingOdometerOnRoute" value="${configuration.validateClosingOdometerOnRoute}" id="validateClosingOdometerOnRoute">
										<input type="hidden" value="${configuration.tripOpenCloseFuelRequired}" id="tripOpenCloseFuelRequired">
										<input type="hidden" value="${configuration.showDefaultTimeAndDate}" id="showDefaultTimeAndDate"> 
										<input type="hidden" value="${DefaultDate}" id="defaultDate">
										<input type="hidden" value="${DefaultTime}" id="defaultTime">
										
										<c:if test="${configuration.tripOpenCloseFuelRequired}">
											<div class="row1" id="tripEndDieselRow">
												<label class="L-size control-label">Balance Fuel: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input id="tripEndDiesel" type="number" step="any" max="1000"
													  value="0"	class="string required form-text" name="tripEndDiesel">
												</div>
											</div>
										</c:if>
										<div class="row1" id="manualClosingKm">
											<label class="L-size control-label">Closing KM:<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" name="vid" value="${TripSheet.vid}">
												<input type="hidden" name="vehicle_registration"
													value="${TripSheet.vehicle_registration}">
													
												 <input type="hidden" name="tripOpeningKM" id="tripOpeningKM"
													value="${TripSheet.tripOpeningKM}" > 
													
													<!-- closed km -->
												<input value="${TripSheetKm}"
													class="string  form-text" name="tripClosingKM"
													min="${TripSheet.tripOpeningKM}"
													type="number" 
													placeholder="OPEN-KM =${TripSheetKm}"
													onkeypress="return IsClosingKM(event);"
													ondrop="return false;" id="tripCloseKM"> 
								
												<label class="error"
													id="errorClosingKM" style="display: none; text-align: left;"> </label>
												<div class="I-size">
													<label class="error" class="col-md-offset-1" id="odometerRange">
														Odometer Range : ${minAllowedKM} - ${maxAllowedKM}</label>
												</div>	
												<div class="I-size" id="gpsNotWorking" style="display: none;">
													<label class="error" class="col-md-offset-1">
														GPS Not Working/Attached !</label>
												</div>
													
												<div class="I-size">
													<label class="control-label" class="col-md-offset-1">
														Meter Not Working</label> <input type="checkbox"
														name="tripClosingKMStatusId" id="tripClosingKMStatusId" value="1">
												</div>
											</div>
										</div>
									<c:if test="${configuration.showLastTripMileageOnCloseTrip && lastTripId > 0 && lastTripFuel.fuel_TripsheetID > 0}">
										<div class="row1 ">
											<div class="I-size L-size control-label">
												<samp id="lastTripDetailsSamp">
													Last Trip : <a href="<c:url value="/showTripSheet?tripSheetID=${lastTripFuel.fuel_TripsheetID}"/>">${lastTripFuel.tripSheetNumber} </a>
													<c:choose>
													<c:when test="${lastTripFuel.fuel_cost < lastTripFuel.fuel_kml }">
														<samp class="blink_me" style="color: red">
													, Fuel
													mileage :${lastTripFuel.fuel_kml}</samp>
													</c:when> 
													<c:otherwise>
													, Fuel
													mileage :${lastTripFuel.fuel_kml} ${lastTripFuel.fuel_cost}
													</c:otherwise>
													</c:choose>
												</samp>
											</div>
										</div>
									</c:if>
									<div class="row1" id="extendedTrip">
											<label class="L-size control-label">IS Trip Extended :</label>
											<div class="I-size">
													<div class="">
														<div class="btn-group" id="status" data-toggle="buttons" onchange="setCloseOdometerRange();">
															<label class="btn btn-default btn-on btn-lg">
																<input type="radio" value="0" name="isExtended"
																id="isExtendedNo">Yes
															</label> <label class="btn btn-default btn-off btn-lg active"> <input
																type="radio" value="1" name="isExtended" id="isExtendedYes" checked="checked"">No
															</label>
														</div>
													</div>
												</div>
										</div>
										
										<div class="row1" id="gpsClosingKMRow" style="display: none;">
											<label class="L-size control-label">GPS Closing KM:</label>
											<div class="I-size">
												<input readonly="readonly" id="tripGpsClosingKM" type="number"
												  value="0"	class="string required form-text" name="tripGpsClosingKM">
											</div>
										</div>
										<div class="row1" id="gpsClosingLocationRow" style="display: none;">
											<label class="L-size control-label">GPS Closing Location:</label>
											<div class="I-size">
												<input readonly="readonly" id="gpsCloseLocation" type="text"
												 class="string required form-text" name="gpsCloseLocation">
											</div>
										</div>
										
										<c:if test="${configuration.showTimeDuringCloseTripsheet}">
										<div class="row1">
											<label class="L-size control-label">Trip Closed Date :<abbr
												title="required">*</abbr></label>
											<div class="L-size" style="width: 30%">
												<div class="input-group input-append date" 
													id="TripStartEndDate">
													<input type="text" class="form-text" name="closetripDate" id="closetripDate"
														required="required" value="${TripSheet.closetripDate}" readonly="readonly"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" onchange="getVehicleGPSDataAtTime();" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
											<div class="L-size">	
												<div class="input-group clockpicker">
													<input type="text" class="form-text" readonly="readonly" onchange="getVehicleGPSDataAtTime();"
														name="tripEndDateTimeStr" id="tripEndTime" required="required"> <span
														class="input-group-addon"> <i
														class="fa fa-clock-o" aria-hidden="true"></i>
													</span>
												</div>
											</div>
										</div>
										</c:if>
										<c:if test="${!configuration.showTimeDuringCloseTripsheet}">
										 <div class="row1">
											<label class="L-size control-label">Trip Closed Date :<abbr
											title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group input-append date"
													id="TripStartEndDate">
														<input type="text" class="form-text" name="closetripDate" id="closetripDate"
														required="required" value="${TripSheet.closetripDate}" readonly="readonly"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
														</span>
												</div>
											</div>
										</div>
										</c:if>
										
										<div class="box-footer h-padded">
											<fieldset class="col-md-offset-3">
												<input class="btn btn-success" data-disable-with="Saving..." id="saveClose"
												 onclick="saveCloseTripSheet(${TripSheet.tripOpeningKM},${ExpectedOdameterKm});"	name="commit" type="submit" 
												 value="Close Trip Sheet">
												<a class="btn btn-link"
													href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
											</fieldset>
										</div>
									</fieldset>
								</div>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/gps/getGPSDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetClose.js" />"></script>	
	
	<script type="text/javascript">
	
	$(document).ready(function() {
		if(${driverBalanceWithNarration} || ${configuration.reverseDriverBalance} ){
			setPaidByPayToFeilds();
		}
		
		if(${allowGPSIntegration })
			setTripClosingDataforGps('${gpsConfig}', '${gpsObject}');
	});
	
	$(document).ready(function() {
		$('.clockpicker').clockpicker({
			placement: 'top',
			align: 'right',
			autoclose: true
		});
		
		var today = new Date();
		var time = today.getHours();
		var minute = today.getMinutes();
		
		if(today.getMinutes()<10){
			minute = '0'+minute;
		} 
		
		if(today.getHours()<10){
			time = '0'+time;
		}
		
		
	});
	function disableF5(e) { 
		if ((e.which || e.keyCode) == 116) e.preventDefault(); };

	
	$(document).ready(function(){
		  if($("#showDefaultTimeAndDate").val() == "true" || $("#showDefaultTimeAndDate").val() == true) {
			
			  $("#closetripDate").val($("#defaultDate").val());
			  $("#tripEndTime").val($("#defaultTime").val());
		  }
		  
		var start_date = new Date ('${StartDate}');
		$("#TripStartEndDate").datepicker({autoclose:!0,todayHighlight:!0,format:"dd-mm-yyyy", startDate: start_date})
		
		if(${configuration.payToOfficeOnlyWhileClosingTrip}){
			$("#closeTripStatusId").prop('disabled',true);
		}
		
		if(${configuration.saveDriverLedgerDetails}){
			$('#driverWalletBalance').removeClass('hide');
		}
		setPayToField((${TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense}).toFixed(2));
		
	});
	
	function setPayToField(amount){
		console.log('allowTallyPushButtonOnClose : '+$('#allowTallyPushButtonOnClose').val());
		if( amount >= 0){
			$('#closeTripStatusId').val(1); 
			$('#closeTripStatusId').trigger("change");
			if($('#allowTallyPushButtonOnClose').val() == 'false')
				$('#closeTripAmount').val(amount);
		}else{
			$('#closeTripStatusId').val(2); 
			$('#closeTripStatusId').trigger("change");
			if($('#allowTallyPushButtonOnClose').val() == 'false')
				$('#closeTripAmount').val(amount * -1);
		}
	}
	
	</script>
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	
</div>
