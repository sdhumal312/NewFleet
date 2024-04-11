<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=3"/>">Manage</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=4"/>">Advance Close</a> /
						<a href="<c:url value="/newTripSheetEntries.in?loadTypeId=5"/>">Payment</a> /
						<a href="<c:url value="/newTripSheetEntries.in?loadTypeId=6"/>">A/C Closed</a>
						/ <small>Show TripSheet</small>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchTripSheetShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">TS-</span></span> <input class="form-text"
										id="tripStutes" name="tripStutes" type="number" min="1"
										required="required" placeholder="TS-ID eg:7878" maxlength="20">
									<span class="input-group-btn">
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
								href="<c:url value="/addTripSheetEntries.in"/>"> <span
								class="fa fa-plus"></span> Create TripSheet
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
							<c:choose>
								<c:when test="${TripSheet.tripStutesId == 2}">
									<a href="showTripSheetPrint.in?id=${TripSheet.tripSheetID}"
										target="_blank" class="btn btn-default"><i
										class="fa fa-print"></i> Print</a>
								</c:when>
								<c:otherwise>
									<a href="TSPrint.in?ID=${TripSheet.tripSheetID}"
										target="_blank" class="btn btn-default"><i
										class="fa fa-print"></i> Print</a>
								</c:otherwise>
							</c:choose>
						</sec:authorize>
						<c:if test="${TripSheet.tripStutesId == 3}">
							 <sec:authorize access="hasAuthority('UPDATE_CLOSING_KM')">
										<a class="btn btn-success btn-sm"
											href="#"
											class="confirmation"
											onclick="updateTripClosingKM(${TripSheet.vid}, ${TripSheet.tripSheetID}, ${TripSheet.tripClosingKM});"> <span
											class="fa fa-rocket"></span> Update Closing KM
										</a>
							</sec:authorize>
						</c:if>
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="tripVoucherDate" value="${TripSheet.voucherDateStr}">
						<input type="hidden" value="${configuration.allowTallyIntegration}" id="allowTallyIntegration">
					</div>
				</div>
			</div>
		</div>
	</section>
						<c:if test="${param.ticketIncomeAdded eq true}">
							<div class="alert alert-success">
								<button class="close" data-dismiss="alert" type="button">x</button>
								Income Data Added Successfully.
							</div>
						</c:if>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-9">
				<div class="box">
					<div class="boxinside">

						<!-- Modal  and create the javaScript call modal -->
			<div class="modal fade" id="tripComment" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">TripSheet Comment</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<input type="hidden" name="TRIPSHEETID"
											value="${TripSheet.tripSheetID}" />
										<input type="hidden"  id="allowTollApiIntegration" name="allowTollApiIntegration"  value="${allowTollApiIntegration }" />	
									</div>
									<br>
									<div class="form-group">
										<label for="comment">Comment:</label>
										<textarea class="form-text" rows="5" id="comment"
											required="required" name="TRIP_COMMENT" maxlength="900"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary"
										id="saveComment" value="Save Comment" onclick="saveTripSheetComment();">Save
										Comment</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
						</div>
					</div>
				</div>
						
				<div class="modal fade" id="updateCloseKm" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">TripSheet Odometer</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<input type="hidden" name="TRIPSHEETID" id="TRIPSHEETID"
											value="${TripSheet.tripSheetID}" />
										<input type="hidden"  id="vid" name="vid" value="${TripSheet.vid}"/>	
									</div>
								</div>
								<div class="row1" id="grprenewalDate" class="form-group">

										<label class="L-size control-label" for="reservation">Trip Closing KM
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="tripClosingKM"  value="${TripSheet.tripClosingKM}"
											required="required" name="tripClosingKM" type="number"></input>
										</div>
								</div>
									<br>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" onclick="updateTripSheetClosingKM();"
										id="js-upload-submit" value="Save Odometer">Update
										Odometer</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
						</div>
					</div>
				</div>
						
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_ACCOUNT_TRIPSHEET')">
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
										<a style="color: #000000;"
											href="showVehicle.in?vid=${TripSheet.vid}" data-toggle="tip"
											data-original-title="Click Vehicle Info"> <c:out
												value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>

								<div class="row">
									<h4 align="center">${TripSheet.routeName}</h4>
								</div>
								<%-- <div class="row">
									<h4 align="center">${TripSheet.subRouteName}</h4>
								</div> --%>


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
													data-original-title="Driver 1"> <c:out
															value="${TripSheet.tripFristDriverName}" /></a> / <c:out
														value="${TripSheet.tripFristDriverMobile }" /></td>
											</tr>
											<tr>
												<td>Driver 2 : <a data-toggle="tip"
													data-original-title="Driver 2"><c:out
															value="${TripSheet.tripSecDriverName}" /></a> / <c:out
														value="${TripSheet.tripSecDriverMobile}" /></td>
												<td>Cleaner : <a data-toggle="tip"
													data-original-title="Cleaner"><c:out
															value="${TripSheet.tripCleanerName}" /></a> / <c:out
														value="${TripSheet.tripCleanerMobile}" /></td>
														<td></td>
												
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
													<td>Opening KM: <a data-toggle="tip" id="tripOpeningKM"
														data-original-title="Opening KM">${TripSheet.tripOpeningKM}</a></td>
													<td>Closing KM: <a data-toggle="tip"
														data-original-title="closing KM" id="closingKM">
																${TripSheet.tripClosingKM}</a></td>
	
													<c:choose>
														<c:when
															test="${TripSheet.tripClosingKMStatusId == 1}">
															<td>Meter : <a data-toggle="tip"
																data-original-title="Meter Not Working"> <c:out
																		value="Meter Not Working " /></a></td>
	
														</c:when>
														<c:otherwise>
															<td>Usage KM: <a data-toggle="tip" id="tripUsaseKm"
																data-original-title="usage KM">
																		${TripSheet.tripUsageKM}</a></td>
	
														</c:otherwise>
													</c:choose>
												</tr>
											<c:if test="${TripSheet.tripGpsUsageKM != null && TripSheet.tripGpsUsageKM > 0}">
												<tr>
													<td>GPS Opening KM: <a data-toggle="tip"
														data-original-title="Opening KM"><c:out
																value="${TripSheet.tripGpsOpeningKM}" /></a></td>
													<td>GPS Closing KM: <a data-toggle="tip"
														data-original-title="closing KM"> <c:out
																value="${TripSheet.tripGpsClosingKM}" /></a></td>
	
													<td>GPS Usage KM: <a data-toggle="tip"
																data-original-title="usage KM"> <c:out
																		value="${TripSheet.tripGpsUsageKM}" /></a></td>
	
												</tr>
											</c:if>	
											<tr>
												<td>Dispatch By: <a data-toggle="tip"
													data-original-title="dispatched By"><c:out
															value="${TripSheet.dispatchedBy}" /></a></td>
												<td>Dispatch Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.dispatchedLocation}" /></a></td>
												<td>Dispatch time: <a data-toggle="tip"
													data-original-title="Time"><c:out
															value="${TripSheet.dispatchedByTime}" /></a></td>
											</tr>
											<tr>
												<td>Closed By: <a data-toggle="tip"
													data-original-title="closed By"><c:out
															value="${TripSheet.closedBy}" /></a></td>
												<td>Closed Location: <a data-toggle="tip"
													data-original-title="location"> <c:out
															value="${TripSheet.cloesdLocation}" /></a></td>
												<td>Closed Time: <a data-toggle="tip"
													data-original-title="closed By"><c:out
															value="${TripSheet.closedByTime}" /></a></td>
											</tr>
											<c:if test="${configuration.showSubroute}">
											<tr>
												<td>Sub-Route : <a data-toggle="tip"
													data-original-title="SubRoute"><c:out
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
											<c:if test="${TripSheet.gpsOpeningLocation != null}">
												<tr>
													<td colspan="3">GPS Opening Location : <a data-toggle="tip"
														data-original-title="Sub Route"><c:out
																value="${TripSheet.gpsOpeningLocation}" /></a></td>
												
												</tr>
											</c:if>
											<c:if test="${TripSheet.gpsCloseLocation != null && TripSheet.gpsCloseLocation != ''}">
												<tr>
													<td colspan="3">GPS Close Location : <a data-toggle="tip"
														data-original-title="Sub Route"><c:out
																value="${TripSheet.gpsCloseLocation}" /></a></td>
															
												</tr>
											</c:if>
											<tr>
												<td colspan="3" >Route Remark:  <a data-toggle="tip"
													data-original-title="Route Remark"><c:out
															value="${TripSheet.routeRemark}" /></a></td>
																
											</tr>
											<c:if test="${configuration.showPODdetails}">	
											<tr>
													<td >Number of POD: <a data-toggle="tip"
														data-original-title="Number of POD"><c:out
																value="${TripSheet.noOfPOD}" /></a></td>
													<td >Received POD: <a data-toggle="tip"
														data-original-title="Receive Number of POD"><c:out
																value="${TripSheet.receivedPOD}" /></a></td>
													<td></td>			
											</tr>
											</c:if>
											<c:if test="${configuration.tripOpenCloseFuelRequired}">
												<tr>
													<td >Last Fuel: <a data-toggle="tip"
														data-original-title="Purchase Fuel"><c:out
																value="${TripSheet.tripStartDiesel}" /></a></td>
														<td> Purchased Diesel: <a data-toggle="tip"
														data-original-title="Last Fuel"><c:out
																value="${fTDiesel}" /></a> </td>
														<td> Total :<c:if test="${fTDiesel != null}"> 
														<a data-toggle="tip"
															data-original-title="Total Fuel"><c:out
																value="${TripSheet.tripStartDiesel + fTDiesel}" /></a>
														</c:if> </td>	
												</tr>
											</c:if>
											<c:if test="${configuration.tripOpenCloseFuelRequired && TripSheet.tripEndDiesel != null}">
												<tr>
													<td >Balance Fuel: <a data-toggle="tip"
														data-original-title="Balance Fuel"><c:out
																value="${TripSheet.tripEndDiesel}" /></a></td>
														
														<td> Used Fuel :<c:if test="${fTDiesel != null}"> 
														<a data-toggle="tip"
															data-original-title="Used Fuel"><c:out
																value="${TripSheet.tripStartDiesel + fTDiesel - TripSheet.tripEndDiesel}" /></a>
														</c:if> </td>	
														<td> </td>
												</tr>
											</c:if>
											
											<c:if test="${configuration.allowTallyIntegration}">
												<tr>
													<td ><b>Voucher Date :</b> <a data-toggle="tip"
														data-original-title="Voucher Date"><c:out
																value="${TripSheet.voucherDateStr}" /></a></td>
														
														<td> </td>	
														<td> </td>
												</tr>
											</c:if>
											
										</tbody>
									</table>
								</div>
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET_ONLY')">
								<table class="table">

									<tbody>
										<tr>
											<td><c:if test="${!empty TripSheetAdvance}">

													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Advance Place</th>
																<th class="fit ar">Advance PaidBy</th>
																<th class="fit ar">Reference</th>
																<th class="fit ar">Amount</th>
																<th class="fit ar">Advance Date</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${TripSheetAdvance}"
																var="TripSheetAdvance">

																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetAdvance.advancePlace}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetAdvance.advancePaidby}" /></td>

																	<td class="fit ar"><c:out
																			value="${TripSheetAdvance.advanceRefence}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetAdvance.advanceAmount}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetAdvance.created}" /></td>
																</tr>


															</c:forEach>
															<tr data-object-id="" class="ng-scope">
																<td colspan="4" class="key"><h4>Total Advance
																		:</h4></td>
																<td colspan="2" class="value"><h4>
																		<i class="fa fa-inr"></i> ${advanceTotal}
																	</h4></td>
															</tr>

														</tbody>
													</table>
												</c:if></td>
										</tr>
										<c:if test="${!configuration.showCombineTripDetails}"> 
										<tr>
											<td><c:if test="${!empty TripSheetExpense}">

													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Expense Name</th>
																<c:if test="${configuration.showCreditAndVendorAtExpense}">
																	<th class="fit ar">Tally Company Name</th>
																</c:if>
																<c:if test="${!configuration.showCreditAndVendorAtExpense}">
																	<th class="fit ar">Type</th>
																	<th class="fit ar">Place</th>
																	<th class="fit ar">Ref</th>
																</c:if>
																<th class="fit ar">Expense Date</th>
																<th class="fit ar">Amount</th>
																<c:if test="${configuration.showCreditAndVendorAtExpense}">
																	<th class="fit ar">Vendor</th>
																</c:if>
																
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${TripSheetExpense}"
																var="TripSheetExpense">
																<c:choose>
																<c:when test="${TripSheetExpense.expenseFixed == 'FIXED'}">
																<tr style="background: #FFFF00; data-object-id="" class="ng-scope ">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td "><c:out
																			value="${TripSheetExpense.expenseName}" /></td>
																	<td ><c:choose>
																			<c:when
																				test="${TripSheetExpense.expenseFixed == 'FIXED'}">
																				<small class="label label-success"><c:out
																						value="${TripSheetExpense.expenseFixed}" /></small>
																			</c:when>
																			<c:otherwise>
																				<small class="label label-warning"><c:out
																						value="${TripSheetExpense.expenseFixed}" /></small>
																			</c:otherwise>
																		</c:choose></td>
																	<td><c:out
																			value="${TripSheetExpense.expensePlace}" /></td>

																	<td><c:out
																			value="${TripSheetExpense.expenseRefence}" /></td>
																	<td width="10%" ><c:out
																			value="${TripSheetExpense.created}" /></td>		
																	<td width="10%"><c:out
																			value="${TripSheetExpense.expenseAmount}" /></td>
																</tr>
																<c:if test="${configuration.showCreditAndVendorAtExpense}">
																	<td ><c:out
																			value="${TripSheetExpense.vendorName}" /></td>
																			</c:if>	
															</c:when>
															<c:otherwise>
																<tr  data-object-id="" class="ng-scope ">
																<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetExpense.expenseName}" /></td>
																			
																	<c:if test="${configuration.showCreditAndVendorAtExpense}">
																		<td class="fit ar"><c:out
																			value="${TripSheetExpense.tallyCompanyName}" /></td>
																	</c:if>	
																	
																	<c:if test="${!configuration.showCreditAndVendorAtExpense}">		
																	<td class="fit ar"><c:choose>
																			<c:when
																				test="${TripSheetExpense.expenseFixed == 'FIXED'}">
																				<small class="label label-success"><c:out
																						value="${TripSheetExpense.expenseFixed}" /></small>
																			</c:when>
																			<c:otherwise>
																				<small class="label label-warning"><c:out
																						value="${TripSheetExpense.expenseFixed}" /></small>
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetExpense.expensePlace}" /></td>
																
																	<td class="fit ar"><c:out
																			value="${TripSheetExpense.expenseRefence}" /></td>
																	</c:if>
																	
																	<td width="10%"><c:out
																			value="${TripSheetExpense.created}" /></td>
																	
																	<td width="10%"><c:out
																			value="${TripSheetExpense.expenseAmount}" /></td>
																		
																	
																	<c:if test="${configuration.showCreditAndVendorAtExpense}">
																	<td ><c:out
																			value="${TripSheetExpense.vendorName}" /></td>
																			</c:if>	
															</tr>
															</c:otherwise>
															</c:choose>

															</c:forEach>
															<c:if test="${configuration.showCreditAndVendorAtExpense}">
																<tr>
																	<td  colspan="4" class="key"><h4>Total Expense
																			:</h4></td>
																	<td colspan="1"  class="value"><h4>
																			<i class="fa fa-inr"></i> ${expenseTotal}
																		</h4></td>
																</tr>
															</c:if>
															
															<c:if test="${!configuration.showCreditAndVendorAtExpense}">
																<tr>
																	<td  colspan="5" class="key"><h4>Total Expense
																			:</h4></td>
																	<td colspan="2"  class="value"><h4>
																			<i class="fa fa-inr"></i> ${expenseTotal}
																		</h4></td>
																</tr>
															</c:if>

														</tbody>
													</table>
												</c:if></td>
										</tr>
										</c:if>
										
										<c:if test="${configuration.driverPenaltyDetails}">
											<tr>
												<td>
													<c:if test="${!empty DriverAdvanvce}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">
																	<th class="fit">No</th>
																	<th class="fit ar">Driver Name</th>
																	<th class="fit ar">Penalty Date</th>
																	<th class="fit ar">Penatly Amount</th>
																	<th class="fit ar">Paid By</th>
																</tr>
															</thead>
															<tbody>
																<%
																	Integer hitsCount = 1;
																%>
																<c:forEach items="${DriverAdvanvce}" var="DriverAdvanvce">
																	<tr class="ng-scope ">
																		<td class="fit">
																			<% out.println(hitsCount); hitsCount += 1; %>
																		</td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.driver_empnumber}-${DriverAdvanvce.driver_firstname}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
																		<td class="fit ar"><c:out value="${DriverAdvanvce.ADVANCE_PAID_BY}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																<td colspan="3" class="key"><h4>Total Penalty :</h4></td>
																<td colspan="2" class="value"><h4> <i class="fa fa-inr"></i> ${penaltyTotal} </h4></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>
										</c:if>
										
										<c:if test="${configuration.showCombineTripDetails}"> 	
										<tr>
											<td><c:if test="${!empty ExpenseCombineList}">

													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>
															</tr>
															</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${ExpenseCombineList}"
																var="ExpenseCombineList">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	
																	<td class="fit ar">
																		<c:out value="${ExpenseCombineList.expenseName}" />
																	</td>
																	
																	<td class="fit ar">
																		<a 
																		href="#"
																		onclick=" expensePopUp(${TripSheet.tripSheetID},${ExpenseCombineList.expenseId});"> 
																		<c:out value="${ExpenseCombineList.expenseAmount}" />
																		</a>	
																	</td>
																	
																	</tr>
																	</c:forEach>
																	<tr >
																		<td colspan="2" class="key"><h4>Total Expense
																				:</h4>
																		</td>
																		<td  colspan="1" class="value"><h4>
																				<i class="fa fa-inr"></i> ${expenseTotal}
																			</h4>
																		</td>
																	</tr>
														</tbody>
													</table>
												</c:if></td>
										</tr>
										</c:if>	
										
										<tr>
											<td><c:if test="${!empty TripSheetTollExpense}">

													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>
															</tr>
															</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>

																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar">
																	<a 
																	href="#"
																	onclick=" PopUp(${TripSheet.tripSheetID});"> 
																	<c:out value="${TripSheetTollExpenseName}" />
																	</a>
																	</td>
																	
																	<td class="fit ar">
																	<a 
																	href="#"
																	onclick=" PopUp(${TripSheet.tripSheetID});"> 
																	<c:out value="${TripSheetTollExpenseTotalAmount}" />
																	</a>	
																			
																	</td>
																	
																	</tr>
														
														</tbody>
													</table>
												</c:if></td>
										</tr>
										<tr>
											<td><c:if test="${!empty busBooking}"> 
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">Booking No</th>
																<th class="fit ar">Booking Ref</th>
																<th class="fit ar">PartyName</th>
																<th class="fit ar">Rate</th>
																<th class="fit ar">Amount</th>
															</tr>
														</thead>
														<tbody>
															<tr>
															 	 <td class="fit ar">${busBooking.busBookingNumber}</td>
															 	 <td class="fit ar">${busBooking.bookingRefNumber}</td>
															 	 <td class="fit ar">${busBooking.corporateName}</td>
															 	 <td class="fit ar">${busBooking.rate}</td>
															 	 <td class="fit ar">${busBookingAmount}</td>
															</tr>
														</tbody>
													</table>
												 </c:if> 
												</td>
										</tr>
										<tr>
											<td><c:if test="${!empty TripSheetIncome}">
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">
																<th class="fit">No</th>
																<th class="fit ar">Income Name</th>
																<th class="fit ar">Income Place</th>
																<th class="fit ar">Collected By</th>
																<th class="fit ar">Reference</th>
																<th class="fit ar">Amount</th>
																<th class="fit ar">Income Date</th>
															</tr>
														</thead>
														<tbody>
															<%
																Integer hitsCount = 1;
															%>
															<c:forEach items="${TripSheetIncome}"
																var="TripSheetIncome">

															<c:choose>
																<c:when test="${TripSheetIncome.incomeFixed == 'FIXED'}">
																<tr style="background: #FFFF00; data-object-id="" class="ng-scope ">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeName}" /></td>
																	<td class="fit ar"><c:choose>
																			<c:when
																				test="${TripSheetIncome.incomeFixed == 'FIXED'}">
																				<small class="label label-success"><c:out
																						value="${TripSheetIncome.incomeFixed}" /></small>
																			</c:when>
																			<c:otherwise>
																				<small class="label label-warning"><c:out
																						value="${TripSheetIncome.incomeFixed}" /></small>
																			</c:otherwise>
																		</c:choose></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomePlace}" /></td>

																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeRefence}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeAmount}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.created}" /></td>
																</tr>
																</c:when>
																
																<c:otherwise>
																		<tr data-object-id="" class="ng-scope ">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeName}" /></td>
																	<td class="fit ar"><c:choose>
																			<c:when
																				test="${TripSheetIncome.incomeFixed == 'FIXED'}">
																				<small class="label label-success"><c:out
																						value="${TripSheetIncome.incomeFixed}" /></small>
																			</c:when>
																			<c:otherwise>
																				<small class="label label-warning"><c:out
																						value="${TripSheetIncome.incomeFixed}" /></small>
																			</c:otherwise>
																		</c:choose></td>
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomePlace}" /></td>

																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeRefence}" /></td>
																			
																	<c:if test="${TripSheetIncome.ticketIncomeApiId != 0}">		
																		<td class="fit ar">
																			<a href="#" 
																			onclick=" TcktIncmApi(${TripSheetIncome.ticketIncomeApiId});"> 
																			<c:out	value="${TripSheetIncome.incomeAmount}" />
																			</a>
																		</td>
																	</c:if>
																	
																	<c:if test="${TripSheetIncome.ticketIncomeApiId == 0}">
																		<td class="fit ar"><c:out
																			value="${TripSheetIncome.incomeAmount}" /></td>		
																	</c:if>
																	
																	<td class="fit ar"><c:out
																			value="${TripSheetIncome.created}" /></td>
																</tr>
																
																</c:otherwise>
																</c:choose>
															</c:forEach>
															<tr>
																<td colspan="5" class="key"><h4>Total Income :</h4></td>
																<td colspan="2" class="value"><h4>
																		<i class="fa fa-inr"></i> ${incomeTotal}
																	</h4></td>
															</tr>
														</tbody>
													</table>
												</c:if>
											</td>
										</tr>
										
										<c:if test="${configuration.showTripsheetDueAmount}">
											<tr>
												<td>
													<c:if test="${!empty TripsheetDueAmount}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">
																	<th class="fit">No</th>
																	<th class="fit ar">Name</th>
																	<th class="fit ar">Job Type</th>
																	<th class="fit ar">Approx Date</th>
																	<th class="fit ar">Due Date</th>
																	<th class="fit ar">Due Amount</th>
																	<th class="fit ar">Balance Amount</th>
																</tr>
															</thead>
															<tbody>
																<%
																	Integer hitsCount = 1;
																%>
																<c:forEach items="${TripsheetDueAmount}" var="TripsheetDueAmount">
																	<tr class="ng-scope ">
																		<td class="fit">
																			<% out.println(hitsCount); hitsCount += 1; %>
																		</td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driver_firstname}-${DriverAdvanvce.driver_Lastname}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.driJobType}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.approximateDateStr}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.dueDateStr}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.dueAmount}" /></td>
																		<td class="fit ar"><c:out value="${TripsheetDueAmount.balanceAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																<td colspan="5" class="key"><h4>Total Due Amount :</h4></td>
																<td colspan="2" class="value"><h4> <i class="fa fa-inr"></i> ${TotalDueAmount} </h4></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>
										</c:if>
										
										
										<tr>
											<td>
												<!--  Trip Sheet closed  fuel entries --> <c:if
													test="${!empty fuel}">
													<table id="FuelTable" class="table table-hover">
														<thead>
															<tr class="breadcrumb">
																<th>ID</th>
																<th>Vehicle</th>
																<th>Date</th>
																<th>Close(Km)</th>
																<th>Usage</th>
																<th>Volume</th>
																<th>Amount</th>
																<th>FE</th>
																<th>Cost</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${fuel}" var="fuel">
																<tr data-object-id="" class="ng-scope">
																	<td><a
																		href="<c:url value="/showFuel.in?FID=${fuel.fuel_id}"/>"
																		data-toggle="tip"
																		data-original-title="Click Fuel Details"><c:out
																				value="FT-${fuel.fuel_Number}" /></a></td>
																	<td><a
																		href="<c:url value="/VehicleFuelDetails/1.in?vid=${fuel.vid}"/>"
																		data-toggle="tip"
																		data-original-title="Click Vehicle Details"><c:out
																				value="${fuel.vehicle_registration}" /> </a></td>
																	<td><c:out value="${fuel.fuel_date}" /><br>
																		<h6>
																			<a data-toggle="tip"
																				data-original-title="Vendor Name"> <c:out
																					value="${fuel.vendor_name}" />-( <c:out
																					value="${fuel.vendor_location}" /> )
																			</a>
																		</h6></td>
																	<td><c:out value="${fuel.fuel_meter}" /></td>

																	<td><c:out value="${fuel.fuel_usage} km" /></td>

																	<td><abbr data-toggle="tip"
																		data-original-title="Liters"><c:out
																				value="${fuel.fuel_liters}" /></abbr> <c:if
																			test="${fuel.fuel_tank_partial==1}">
																			<abbr data-toggle="tip"
																				data-original-title="Partial fuel-up"> <i
																				class="fa fa-adjust"></i>
																			</abbr>
																		</c:if> <br> <c:out value="${fuel.fuel_type}" /></td>
																	<td><i class="fa fa-inr"></i> <c:out
																			value="${fuel.fuel_amount}" /> <br> <abbr
																		data-toggle="tip" data-original-title="Price">
																			<c:out value="${fuel.fuel_price}/liters" />
																	</abbr></td>
																	<td class="fit"><c:out value="${fuel.fuel_kml} " />
																		<c:if test="${fuel.fuel_kml != null}">
																			<c:choose>
																				<c:when
																					test="${vehicle.vehicle_ExpectedMileage <= fuel.fuel_kml}">
																					<c:choose>
																						<c:when
																							test="${vehicle.vehicle_ExpectedMileage_to >= fuel.fuel_kml}">
																							<abbr data-toggle="tip"
																								data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																								<i class="fa fa-stop-circle"
																								style="color: #1FB725; font-size: 19px;"></i>
																						   </abbr>
																						</c:when>
																						<c:otherwise>
																							<abbr data-toggle="tip"
																								data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage} to ${vehicle.vehicle_ExpectedMileage_to}">
																								<i class="fa fa-chevron-circle-up"
																								style="color: blue; font-size: 19px;"></i>
																							</abbr>
																						</c:otherwise>
																					</c:choose>
																				</c:when>
																				<c:otherwise>
																					<abbr data-toggle="tip"
																						data-original-title="Expected Mileage ${vehicle.vehicle_ExpectedMileage}  to ${vehicle.vehicle_ExpectedMileage_to}">

																						<i class="fa fa-chevron-circle-down"
																						style="color: red; font-size: 19px;"></i>
																					</abbr>
																				</c:otherwise>

																			</c:choose>
																		</c:if></td>
																	<td><c:out value="${fuel.fuel_cost} " /> <c:if
																			test="${fuel.fuel_cost != null}">
													/Km
													</c:if></td>
																</tr>
															</c:forEach>
															  <c:if test="${configuration.tripOpenCloseFuelRequired && TripSheet.tripEndDiesel != null}">
																<tr>
																	<td colspan="1" class="text-right"
																		style="font-size: 15px; font-weight: bold;">Trip Km/L
																		:</td>
																	<td style="font-size: 15px; font-weight: bold;">
																		<fmt:formatNumber type="number" pattern="#.##" value="${TripSheet.tripUsageKM /(TripSheet.tripStartDiesel + fTDiesel - TripSheet.tripEndDiesel)}"/>
																	</td>	
																	<td colspan="2" class="text-right"
																		style="font-size: 15px; font-weight: bold;">Total
																		:</td>
																	<td style="font-size: 15px; font-weight: bold;"><c:out
																			value="${fTUsage}" /></td>
																	<td style="font-size: 15px; font-weight: bold;"><c:out
																			value="${fTDiesel} " /></td>
																	<td colspan="3"
																		style="font-size: 15px; font-weight: bold;"><c:out
																			value="${fTAmount} " /></td>
																</tr>
															</c:if>	
															
															<c:if test="${!configuration.tripOpenCloseFuelRequired}">
															  <tr>
																<td colspan="4" class="text-right"
																	style="font-size: 15px; font-weight: bold;">Total
																	:</td>
																<td style="font-size: 15px; font-weight: bold;"><c:out
																		value="${fTUsage}" /></td>
																<td style="font-size: 15px; font-weight: bold;"><c:out
																		value="${fTDiesel} " /></td>
																<c:if test="${tripConfig.showTotalKmpl}">
																	<td style="font-size: 15px; font-weight: bold;">
																		<c:out value="${fTAmount} " />
																	</td>
																	<td colspan="2" style="font-size: 15px; font-weight: bold;">
																		<fmt:formatNumber type="number" pattern="#.##" value="${totalKmpl}" />
																	</td>
																</c:if>	
																<c:if test="${!tripConfig.showTotalKmpl}">
																	<td colspan="3" style="font-size: 15px; font-weight: bold;">
																		<c:out value="${fTAmount} " />
																	</td>
																</c:if>	
															  </tr>
															 </c:if> 
															
														</tbody>
													</table>
												</c:if>
											</td>
										</tr>
										
										<tr>
											<td>
												<c:if test="${!empty urea}">
													<table id="FuelTable" class="table table-hover">
														<thead>
															<tr>
																<th>ID</th>
																<th>Vehicle</th>
																<th>Urea Manufacturer</th>
																<th>Date</th>
																<th>Close(Km)</th>
																<th>Volume</th>
																<th>Amount</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${urea}" var="urea">
																<tr data-object-id="" class="ng-scope">
																	<td><a
																		href="<c:url value="/showUreaDetails.in?Id=${urea.ureaEntriesId}"/>"
																		data-toggle="tip" data-original-title="Click Urea Details"><c:out
																				value="UE-${urea.ureaEntriesNumber}" /></a>
																	</td>
																	<td><a
																		href="<c:url value="/showVehicle.in?vid=${urea.vid}"/>"
																		data-toggle="tip"
																		data-original-title="Click Vehicle Details"><c:out
																				value="${urea.vehicle_registration}" /> </a>
																	</td>
																	<td>
																		<c:out value="${urea.manufacturerName}" /><br>
																	</td>
																	<td>
																		<c:out value="${urea.ureaDateStr}" /><br>
																	</td>
																	<td>
																		<c:out value="${urea.ureaOdometer}" />
																	</td>
																	<td>
																		<c:out value="${urea.ureaLiters}" />
																	</td>
																	<td><i class="fa fa-inr"></i> <c:out
																			value="${urea.ureaAmount}" /> <br> <abbr
																		data-toggle="tip" data-original-title="Price"> <c:out
																				value="${urea.ureaRate}/liters" />
																		</abbr>
																	</td>
																</tr>
															</c:forEach>
															<tr>
																<td colspan="5" class="text-right"
																	style="font-size: 15px; font-weight: bold;">Total
																	:</td>
																<td style="font-size: 15px; font-weight: bold;"><c:out
																		value="${totalUrea} " /></td>
																<td style="font-size: 15px; font-weight: bold;"><c:out
																		value="${totalUreaAmnt} " /></td>
															</tr>
														</tbody>
													</table>
												</c:if>	
											</td>
										</tr>
										
										<tr>
											<td>
												<table class="table table-bordered table-striped">
													<thead>
														<tr class="breadcrumb">
															<th class="fit ar">Paid To</th>
															<th class="fit ar">Paid By</th>
															<th class="fit ar">(Advance - Expense)</th>
															<th class="fit ar">Reference</th>
															<th class="fit ar">TS-Close Date</th>
															<th class="fit ar">Status</th>
														</tr>
													</thead>
													<tbody>

														<tr data-object-id="" class="ng-scope">

															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripStatus}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripNameBy}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripReference}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closetripDate}" /></td>
															<td class="fit ar"><c:choose>

																	<c:when test="${TripSheet.tripStutesId == 4}">
																		<span class="label label-pill label-success"><c:out
																				value="CLOSED" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																				value="${TripSheet.tripStutes}" /></span>
																	</c:otherwise>
																</c:choose></td>
														</tr>

													</tbody>
												</table>
											</td>
										</tr>
										
							
		
										<tr>
											<td>
												<table class="table table-bordered table-striped">
													<tr>
														<td colspan="0" class="key"><h4>Trip Balance:</h4></td>
														<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${tripTotalincome}</h4></td>
													</tr>
													<c:if test="${configuration.driverBalanceWithNarration}">
														<tr>
															<td colspan="0" class="key"><h4><b>${driverBalanceKey}:</b></h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i><b> ${balanceAmount}</b></h4></td>
														</tr>	
													</c:if>
													<c:if test="${configuration.showDriverBalance}">
														<tr>
															<td colspan="0" class="key"><h4>Driver Balance:</h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${driverBalance}</h4></td>
														</tr>	
													</c:if>
													<c:if test="${configuration.showTripsheetDueAmount}">
														<tr>
															<td colspan="0" class="key"><h4>Due Amount:</h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${TotalDueAmount}</h4></td>
														</tr>	
													</c:if>
												</table>
											</td>
										</tr>
										
										<tr>
											<td class="fit ar">
												<c:if test="${TripSheet.tripStutesId == 4}">
												<c:if test="${!empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + urea expense + fasttag toll expenses)
												</c:if>
												<c:if test="${empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + fasttag toll expenses)
												</c:if>
												</c:if>
											</td>
										</tr>
										<tr>
											<td><c:if test="${TripSheet.tripStutesId == 4}">
														<tr data-object-id="" class="ng-scope">

															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripStatus}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripNameBy}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripReference}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closetripDate}" /></td>
															<td class="fit ar"><c:choose>

																	<c:when test="${TripSheet.tripStutesId == 4}">
																		<span class="label label-pill label-success"><c:out
																				value="CLOSED" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																				value="${TripSheet.tripStutes}" /></span>
																	</c:otherwise>
																</c:choose></td>
														</tr>

													</tbody>
												</table>
											</td>
										</tr>
										
							
		
										<tr>
											<td>
												<table class="table table-bordered table-striped">
													<tr>
														<td colspan="0" class="key"><h4>Trip Balance:</h4></td>
														<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${tripTotalincome}</h4></td>
													</tr>
													<c:if test="${configuration.showDriverBalance}">
														<tr>
															<td colspan="0" class="key"><h4>Driver Balance:</h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${driverBalance}</h4></td>
														</tr>	
													</c:if>
												</table>
											</td>
										</tr>
										
										<tr>
											<td class="fit ar">
												<c:if test="${TripSheet.tripStutesId == 4}">
												<c:if test="${!empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + urea expense + fasttag toll expenses)
												</c:if>
												<c:if test="${empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + fasttag toll expenses)
												</c:if>
												</c:if>
											</td>
										</tr>
										<tr>
											<td><c:if test="${TripSheet.tripStutesId == 4}">
											<tr>
												<td>
														<tr data-object-id="" class="ng-scope">

															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripStatus}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripNameBy}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.tripTotalAdvance - TripSheet.tripTotalexpense}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closeTripReference}" /></td>
															<td class="fit ar"><c:out
																	value="${TripSheet.closetripDate}" /></td>
															<td class="fit ar"><c:choose>

																	<c:when test="${TripSheet.tripStutesId == 4}">
																		<span class="label label-pill label-success"><c:out
																				value="CLOSED" /></span>
																	</c:when>
																	<c:otherwise>
																		<span class="label label-pill label-warning"><c:out
																				value="${TripSheet.tripStutes}" /></span>
																	</c:otherwise>
																</c:choose></td>
														</tr>
												</c:if>
											</td>
										</tr>
										
							
		
										<tr>
											<td>
												<table class="table table-bordered table-striped">
													<tr>
														<td colspan="0" class="key"><h4>Trip Balance:</h4></td>
														<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${tripTotalincome}</h4></td>
													</tr>
													<c:if test="${configuration.showDriverBalance}">
														<tr>
															<td colspan="0" class="key"><h4>Driver Balance:</h4></td>
															<td colspan="0" class="value"><h4><i class="fa fa-inr"></i> ${driverBalance}</h4></td>
														</tr>	
													</c:if>
												</table>
											</td>
										</tr>
										
										<tr>
											<td class="fit ar">
												<c:if test="${TripSheet.tripStutesId == 4}">
												<c:if test="${!empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + urea expense + fasttag toll expenses)
												</c:if>
												<c:if test="${empty urea}">
												Balance : Total Income - (Driver expenses + fuel expenses paid in credit or card + fasttag toll expenses)
												</c:if>
												</c:if>
											</td>
										</tr>
										<tr>
										<%-- 	<td><c:if test="${TripSheet.tripStutesId == 4}"> --%>
											<tr>
												<td>
													<table class="table table-bordered table-striped">
														<thead>
															<tr class="breadcrumb">

																<th class="fit ar">A/C Closed By</th>
																<th class="fit ar"> Balance </th>
																<th class="fit ar">A/C Reference</th>
																<th class="fit ar">A/C Closed Date</th>
																<th class="fit ar">Status</th>
															</tr>
														</thead>
														<tbody>

															<tr data-object-id="" class="ng-scope">

																<td class="fit ar"><c:out
																		value="${TripSheet.closeACCTripNameBy}" /></td>

																<td class="fit ar"><c:out
																		value="${tripTotalincome}" /></td>
																<td class="fit ar"><c:out
																		value="${TripSheet.closeACCTripReference}" /></td>
																<td class="fit ar"><c:out
																		value="${TripSheet.closeACCtripDate}" /></td>
																<td class="fit ar"><c:choose>

																		<c:when test="${TripSheet.tripStutesId == 4}">
																			<span class="label label-pill label-success"><c:out
																					value="${TripSheet.tripStutes}" /></span>
																		</c:when>
																		<c:otherwise>
																			<span class="label label-pill label-warning"><c:out
																					value="${TripSheet.tripStutes}" /></span>
																		</c:otherwise>
																	</c:choose></td>
															</tr>

														</tbody>
													</table>
												</c:if></td>
										</tr>
										<tr>
											<td>
												<!-- Trip sheet driver halt details --> <c:if
													test="${!empty TripSheetHalt}">
													<div class="row">
														<div class="">
															<table class="table table-bordered table-striped">
																<thead>
																	<tr class="breadcrumb">
																		<th class="fit">No</th>
																		<th class="fit ar">Name</th>
																		<th class="fit ar" colspan="2">Halt date</th>
																		<th class="fit ar">Amount</th>
																		<th class="fit ar">Place</th>
																		<th class="fit ar">Paid By</th>
																	</tr>
																</thead>
																<tbody>
																	<%
																		Integer hitsCount = 1;
																	%>
																	<c:forEach items="${TripSheetHalt}" var="TripSheetHalt">
																		<tr data-object-id="" class="ng-scope">
																			<td class="fit">
																				<%
																					out.println(hitsCount);
																								hitsCount += 1;
																				%>
																			</td>
																			<td class="fit ar"><c:out
																					value="${TripSheetHalt.DRIVER_NAME}" /></td>
																			<td class="fit ar" colspan="2"><c:out
																					value="${TripSheetHalt.HALT_DATE_FROM} to " /> <c:out
																					value="${TripSheetHalt.HALT_DATE_TO}" /></td>
																			<td class="fit ar"><c:out
																					value="${TripSheetHalt.HALT_AMOUNT}" /></td>
																			<td class="fit ar"><c:out
																					value="${TripSheetHalt.HALT_PLACE}" /></td>
																			<td class="fit ar"><c:out
																					value="${TripSheetHalt.HALT_PAIDBY}" /></td>
																		</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</c:if>
											</td>
										</tr>
										
										<tr>
												<td>
													<c:if test="${!empty refreshment}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">
																	<th class="fit ar">R.NO</th>
																	<th class="fit ar">Date</th>
																	<th class="fit ar">Quantity</th>
																	<th class="fit ar">Returned Qty</th>
																	<th class="fit ar">Consumption</th>
																	<th class="fit ar">Amount</th>
																	<th class="fit ar">Action</th>
																</tr>
															</thead>
															<tbody>
																
																<c:forEach items="${refreshment}" var="refreshment">
																	<tr class="ng-scope ">
																		<td class="fit ar"><c:out value="${refreshment.refreshmentEntryNumber}" /></td>
																		<td class="fit ar"><c:out value="${refreshment.asignmentDateStr}" /></td>
																		<td class="fit ar"><c:out value="${refreshment.quantity}" /></td>
																		<td class="fit ar"><c:out value="${refreshment.returnQuantity}" /></td>
																		<td class="fit ar"><c:out value="${refreshment.quantity -refreshment.returnQuantity}" /></td>
																		<td class="fit ar"><c:out value="${refreshment.totalAmount}" /></td>
																		<td class="fit ar"><a href="#" onclick="returnRefreshmentToTripSheet('${refreshment.refreshmentEntryId}', '${refreshment.quantity -refreshment.returnQuantity}', '${refreshment.returnQuantity}');" class="btn btn-info btn-sm fa fa-undo"> Return</a></td>
																	</tr>
																</c:forEach>
															<tr>
																<td colspan="2" class="fit ar"><h4>Total :</h4></td>
																<td class="fit ar"><h4>${totalQty}</h4></td>
																<td class="fit ar"><h4>${totalRQty}</h4></td>
																<td class="fit ar"><h4>${totalConsumption}</h4></td>
																<td class="fit ar"><h4> ${grandTotal} </h4></td>
																<td class="fit ar"></td>
															</tr>
															</tbody>
														</table>
													</c:if>
												</td>
											</tr>

										<tr>
											<td><c:if
													test="${TripSheet.tripStutesId ==3 || TripSheet.tripStutesId == 4}">
													<table class="table table-bordered table-striped">
														<tbody>
														<thead>
															<tr class="breadcrumb">
																<th></th>
																<th>Actual</th>
																<th>Fixed</th>
																<th>Differences</th>
															</tr>
														</thead>
														<tbody>
															<tr class="breadcrumb">
																<td class="text-right">Differences of Trip Volume :</td>
																<td><c:out value=" ${fTDiesel}" /></td>
																<td><c:out value="${TripSheet.routeTotalLiter}" /></td>
																<td><c:out
																		value=" =  ${fTDiesel-TripSheet.routeTotalLiter}" /></td>
															</tr>
															<tr class="breadcrumb">
																<td class="text-right">Differences of Trip KM Usage
																	:</td>
																<td><c:out value="${TripSheet.tripUsageKM}" /></td>
																<td><c:out value="${TripSheet.routeApproximateKM}" /></td>
																<td><c:choose>
																		<c:when
																			test="${TripSheet.tripClosingKMStatusId == 1}">
															Meter : <a data-toggle="tip"
																				data-original-title="Meter Not Working"> <c:out
																					value="Meter Not Working " /></a>
																		</c:when>
																		<c:otherwise>
																			<c:out
																				value=" = ${TripSheet.tripUsageKM - TripSheet.routeApproximateKM}" />

																		</c:otherwise>
																	</c:choose></td>
															</tr>
														</tbody>
													</table>
												</c:if></td>
										</tr>
										<tr>
											<td><c:if test="${!empty TripComment}">
													<table class="table table-bordered table-striped">
														<tbody>
															<c:forEach items="${TripComment}" var="TripComment">
																<tr>
																	<td><div class="timeline-item">
																			<strong><a data-toggle="tip"
																				data-original-title="${TripComment.CREATED_EMAIL}"><i
																					class="fa fa-user"></i> <c:out
																						value="${TripComment.CREATEDBY}" /></a> commented
																				from <b><c:out
																						value="${TripComment.CREATED_PLACE}" /></b> <c:out
																					value="${TripComment.CREATED_DATE}" /></strong>
																			<div class="pull-right">
																				<span class="time"><i class="fa fa-clock-o"></i>
																					${TripComment.CREATED_DATE_DIFFERENT}</span>
																			</div>
																			<div class="timeline-body">
																				<c:out value="${TripComment.TRIP_COMMENT}" />
																			</div>
																		</div></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
												</c:if></td>
										</tr>
									</tbody>
								</table>
								</sec:authorize>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1">
				<ul class="nav nav-list">

					<li class="active"><a href="newTripSheetEntries.in">Overview</a></li>

					<c:choose>
						<c:when test="${TripSheet.tripStutesId ==3}">
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_ADVANCE_AFTER_TRIP_CLOSE')">
									<a class="btn btn-success btn-sm"
										href="addAdvance.in?tripSheetID=${TripSheet.tripSheetID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Advance ')"> <span
										class="fa fa-plus"></span> Advance
									</a>
								</sec:authorize></li>
							<li><sec:authorize
									access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addExpense.in?tripSheetID=${TripSheet.tripSheetID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Expense ')"> <span
										class="fa fa-plus"></span> Expense
									</a>
								</sec:authorize> <sec:authorize access="hasAuthority('ADD_HALT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="addHaltAmount.in?ID=${TripSheet.tripSheetID}"> <span
										class="fa fa-plus"></span> Driver Halt
									</a>
								</sec:authorize> <sec:authorize access="hasAuthority('ADD_FUEL_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="addTSFuel.in?ID=${TripSheet.tripSheetID}"> <span
										class="fa fa-plus"></span> Fuel Entries
									</a>
								</sec:authorize></li>
							<li><sec:authorize
									access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="addIncome.in?tripSheetID=${TripSheet.tripSheetID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Income ')"> <span
										class="fa fa-plus"></span> Income
									</a>
								</sec:authorize></li>
							<li><sec:authorize
									access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="addcloseAccountTripSheet.in?tripSheetID=${TripSheet.tripSheetID}"
										class="confirmation"
										onclick="return closeTripSheetAccount();"> <span
										class="fa fa-times"></span> Close A/C
									</a>
								</sec:authorize></li>
							
							<%-- <li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="<c:url value="/editTripSheet.in?ID=${TripSheet.tripSheetID}"/>">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li> --%>
							<li><sec:authorize
									access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
									<a class="btn btn-info" data-toggle="modal"
										data-target="#tripComment"> <i class="fa fa-comment"></i>
										Comment <span data-toggle="tip" title="" class="badge bg-red"
										data-original-title="${TripSheet.tripCommentTotal} comment">${TripSheet.tripCommentTotal}</span>
									</a>
								</sec:authorize></li>
							<c:if test="${configuration.driverPenaltyDetails}">
							<li><sec:authorize
									access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="driverPenalty.in?ID=${TripSheet.tripSheetID}"> <span
										class="fa fa-plus"></span> Penalty
									</a>
								</sec:authorize></li>
							</c:if>		
								
							<c:if test="${configuration.showPODdetails}">	
							<li><sec:authorize
									access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
											href="receivePODAccount.in?tripSheetID=${TripSheet.tripSheetID}"
											class="confirmation"
											onclick="return confirm('Are you sure to Add POD ? ')"> <span
											class="fa fa-plus"></span> Receive POD
									</a>
								</sec:authorize></li>
							</c:if>	
							<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<c:if test="${allowTollApiIntegration }">
										<a id="tollApi" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"
											onclick="return addTollExpensesDetails(${TripSheet.tripSheetID}, ${TripSheet.vid}, '${TripSheet.vehicle_registration}', '${TripSheet.dispatchedByTimeOn}', '${TripSheet.closedByTimeOn}');"> <span
											class="fa fa-plus"></span> Toll  Expense
										</a>
									</c:if>
								</sec:authorize></li>
							<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<c:if test="${allowTicketIncomeApiIntegeration }">
										<a id="tollApi" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"
											onclick="return addMantisIncome(${TripSheet.tripSheetID}, ${TripSheet.vid}, '${TripSheet.vehicle_registration}', '${TripSheet.dispatchedByTimeOn}', '${TripSheet.closedByTimeOn}');"> <span
											class="fa fa-plus"></span> Mantis Income
										</a>
									</c:if>
									<c:if test="${allowITSGatewayBusIncome }">
										<a id="tollApi" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"
											onclick="return addMantisIncome(${TripSheet.tripSheetID}, ${TripSheet.vid}, '${TripSheet.vehicle_registration}', '${TripSheet.dispatchedByTimeOn}', '${TripSheet.closedByTimeOn}');"> <span
											class="fa fa-plus"></span> Bus Income
										</a>
									</c:if>
						</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('SHOW_UREA_ENTRY_IN_TRIPSHEET')">
										<a class="btn btn-default btn-sm"
										href="addTripSheetUreaEntries.in?tripSheetID=${TripSheet.tripSheetID}"> <span
										class="fa fa-plus"></span> Urea Entries
									</a>
								</sec:authorize></li> 
								<li><sec:authorize access="hasAuthority('ADD_REFRESHMENT_INVENTORY')">
										<a class="btn btn-success btn-sm"
										href="refreshmentEntry.in?tripSheetID=${TripSheet.tripSheetID}"> <span
										class="fa fa-plus"></span> Refreshment
									</a>
								</sec:authorize></li>
									
								<c:if test="${configuration.allowTallyIntegration}">
									 <li>
											<a class="btn btn-default btn-sm"
											href="#" onclick="showVoucherDateModal();"> <span
											class="fa fa-plus"></span> Voucher Date
										</a>
									</li>
								</c:if>
								<c:if test="${configuration.allowTallyIntegration}">
									 <li>
											<a class="btn btn-default btn-sm"
											href="#" onclick="showVoucherDateModal();"> <span
											class="fa fa-plus"></span> Voucher Date
										</a>
									</li>
								</c:if>
							<c:if test="${configuration.allowTallyIntegration}">
								<li><a class="btn btn-default btn-sm" href="#"
									onclick="showVoucherDateModal();"> <span class="fa fa-plus"></span>
										Voucher Date
								</a></li>
							</c:if>
								<c:if test="${configuration.allowTallyIntegration}">
									 <li>
											<a class="btn btn-default btn-sm"
											href="#" onclick="showVoucherDateModal();"> <span
											class="fa fa-plus"></span> Voucher Date
										</a>
									</li>
								</c:if>
							<c:if test="${configuration.allowTallyIntegration}">
								<li><a class="btn btn-default btn-sm" href="#"
									onclick="showVoucherDateModal();"> <span class="fa fa-plus"></span>
										Voucher Date
								</a></li>
							</c:if>
								<c:if test="${configuration.showTripsheetDueAmount}">
											 	<li>
												 	<a class="btn btn-warning btn-sm"
														href="addDueAmount.in?ID=${TripSheet.tripSheetID}"> <span
														class="fa fa-plus"></span> Due Amount
													</a>
												</li>
											</c:if>	
						</c:when>
						<c:otherwise>
							<li><sec:authorize
									access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
									<a class="btn btn-info" data-toggle="modal"
										data-target="#tripComment"> <i class="fa fa-comment"></i>
										Comment <span data-toggle="tip" title="" class="badge bg-red"
										data-original-title="${TripSheet.tripCommentTotal} comment">${TripSheet.tripCommentTotal}</span>
									</a>
								</sec:authorize></li>
						</c:otherwise>

					</c:choose>
						
				</ul>
			</div>
		</div>
				<div class="modal fade" id="voucherDateModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Voucher Date</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="tripSheetId" value="${TripSheet.tripSheetID}">
						<div class="row1" id="grppaidDate" class="form-group">

											<label class="L-size control-label" for="renewalpaidDate">Voucher
												Date :<abbr title="required">*</abbr>
											</label>

											<div class="I-size">
												<div class="input-group input-append date" id="paidDate">
													<input type="text" class="form-text" value="${TripSheet.voucherDateStr}"
														name="renewal_dateofpayment" id="voucherDate"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="paidDateIcon" class=""></span>
												<div id="paidDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-info"
						>
					<span id="saveVoucherDate" onclick="return saveVoucherDate();">save</span>
					</button>
					<button type="button" class="btn btn-default"
					data-dismiss="modal">
					<span id="Close">Close</span>
					</button>
					</div>
					</div>
					</div>
			</div>
		<div class="modal fade" id="getExpensesModel" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn btn-danger"
							data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Trip Toll Expenses</h4>
					</div>
					<div class="modal-body">

						<fieldset>
							<div class="box">
								<table class="box-body" id="modelBodyExpense" border="1"
									width="100%">

								</table>
							</div>
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="ExpenseCombineDetails" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Trip Expenses</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
					<table class="box-body" id="modelBodyExpenseDetails" border="1" width="100%">
					
					</table>
					</div>
					</fieldset>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default"
					data-dismiss="modal">
					<span id="Close">Close</span>
					</button>
					</div>
					</div>
					</div>
			</div>
			
			<div class="modal fade" id="ticketIncomeApi" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1250px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close btn btn-danger"
							data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Bus Api Income Details</h4>
					</div>
					<div class="modal-body">

						<fieldset>
							<div class="box">
								<table class="box-body" id="modelBodyApiIncome" border="1"
									width="100%">

								</table>
							</div>
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close">Close</span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
					<div class="modal fade" id="returnRefreshMent" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Return Refreshment</h4>
					</div>
					<div class="modal-body">
							<input type="hidden" id="refreshmentEntryId" value="0">
							<input type="hidden" id="consumedQuantity" value="0">
						<div class="row1" id="grpfuelDate">
											<label class="L-size control-label" for="fuelDate">Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size"> 
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="refreshmentDate"  readonly="readonly"
														id="refreshmentDate" required="required" value="${TripSheet.closetripDate}"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>
						</div>
						<br/>
						<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Return Qty
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="returnQuantity" name="quantity"
													type="text" maxlength="8" min="0" onkeyup=" return validateReturnQuantity();"
													onkeypress="return isDecimalNumberKey(event); return validateReturnQuantity();"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>

											</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info"
							>
						<span id="returnRefreshment" onclick="return saveReturnRefreshment();">Return</span>
						</button>
						<button type="button" class="btn btn-default"
						data-dismiss="modal">
						<span id="Close">Close</span>
						</button>
					</div>
					</div>
					</div>
			</div>
		
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${TripSheet.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${TripSheet.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${TripSheet.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${TripSheet.lastupdated}" /></small>
		</div>
		
	</section>
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<%-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js" />"></script> --%>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripUpdateClosingKM.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripTollExpensesDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetShowAccount.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addMantisIncome.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetCombineDetails.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/LinkLoadingSheetToTripSheet.js" />"></script>
</div>