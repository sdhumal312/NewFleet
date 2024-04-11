<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newTripDaily.in"/>">Trip Collection</a> / <a
							href="<c:url value="/manageTripDaily/1.in"/>">Manage Trip</a> / <a
							href="<c:url value="/closeTripDaily/1.in"/>">Close Trip</a> /
						Show TripCollection
					</div>

					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="searchTripDailyShow.in" method="post">
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
						<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addTripDaily.in"/>"> <span
								class="fa fa-plus"></span> Create TripCollection
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
							<a href="showTripDailyPrint.in?ID=${TripDaily.TRIPDAILYID}"
								target="_blank" class="btn btn-default btn-sm"><i
								class="fa fa-print"></i> Print</a>
						</sec:authorize>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
	
			
		<div class="modal fade" id="editrenewalPeriod" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="<c:url value="/updateNoOfRoundTrip.in"/>"
						method="post" name="vehicleStatu"
						onsubmit="return validateStatus()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Update No Of Round Trip</h4>
						</div>
						<div class="modal-body">

									<fieldset>
							<div class="box">
								<div class="box-body">
								<input type="hidden" class="form-text" name="TRIPDAILYID"
									value="${TripDaily.TRIPDAILYID}" />
								<!-- renewal_Amount -->
								
									<div class="row1" id="grptripliter" class="form-group">
											<label class="L-size control-label" for="tripliter">No Of
												Round Trip :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" name="noOfRoundTrip"
													id="noOfRoundTrip" step="any" placeholder="enter total passenger"
													type="number" maxlength="10" value="${TripDaily.noOfRoundTrip}"
													ondrop="return false;">
											</div>
										</div>
									<br>

								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="fixedExpensesModel" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Add Trip Fixed Expenses</h4>
						</div>
						<div class="modal-body">

									<fieldset>
							<div class="box">
								<div class="box-body" id="modelBody">
								<input type="hidden" class="form-text" name="TRIPDAILYID"
									value="${TripDaily.TRIPDAILYID}" />

								</div>
							</div>
						</fieldset>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="return updateFixedExpenses();"  class="btn btn-primary">
								<span id="Save">Save</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<!-- mypopup start -->
		<div class="modal fade" id="configureChalo" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Chalo Details</h4>
						</div>
						<div class="modal-body">
						
									<div class="row">
											<!-- <label class="L-size control-label">VehicleId</label> -->
											<div class="I-size">
												<input type="hidden" class="form-text" id="tripdailyId"
													name="tripdailyId" 
													readonly="true" value="${TripDaily.TRIPDAILYID}"
													placeholder="" /> <label
													id="errorvStatus" class="error"></label>
											</div>
									</div>
										
									<div class="row">
											
											<label class="L-size control-label">Chalo KM</label>
											<div class="I-size">
											
												<input type="text" class="form-text" id="chalokm"
													name="chalokm" 
													placeholder="Enter Chalo-Km" /> <label
													id="errorvStatus" class="error"></label>
												
											</div>
									</div>
									
									<div class="row">
											
											<label class="L-size control-label">Chalo Amount</label>
											<div class="I-size">
											
												<input type="text" class="form-text" id="chaloAmount"
													name="chaloAmount" 
													placeholder="Enter Chalo-Amount" /> <label
													id="errorvStatus" class="error"></label>
												
											</div>
										</div>
							
							
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="addEmail()" class="btn btn-success">Save</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
		<!-- mypopup end -->
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				<c:if test="${param.updateAdvance eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Advance Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.tollExpensesAdded eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Toll Expenses Added Successfully.
					</div>
				</c:if>
				<c:if test="${param.tollExpensesAlreadyAdded eq true}">
					<div class="alert alert-info">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Toll Expenses Already Added.
					</div>
				</c:if>
				<c:if test="${param.RO eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Collection Entries ReOpen Successfully.
					</div>
				</c:if>
				<c:if test="${param.updatePassenger eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Collection Passenger Details Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.error eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Collection Passenger Details Updation Failed.
					</div>
				</c:if>
				<c:if test="${param.notUpdateAdvance eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Advance Entries was Already created.
					</div>
				</c:if>
				<c:if test="${param.updateExpense eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Expense Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.alreadyExpense eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Expense Entries was Already created.
					</div>
				</c:if>
				<c:if test="${param.updateIncome eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Income Entries Updated Successfully.
					</div>
				</c:if>
				<c:if test="${param.alreadyIncome eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Trip Income Entries was Already created.
					</div>
				</c:if>
				<div class="box">
					<div class="boxinside">
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripDaily.TRIPDAILYNUMBER}</h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : ${TripDaily.CREATED}</h5>
									</div>
									<input type="hidden" id="noOfRoundTrip" value="${TripDaily.noOfRoundTrip}">
								</div>
								<div class="row">
									<table class="table  table-striped">
										<thead>
											<tr>
												<th colspan="4">
													<h4 align="center">
														<c:out value="${TripDaily.TRIP_ROUTE_NAME}" />
														<br>
														<c:out value="${TripDaily.VEHICLE_GROUP}" />
													</h4>
												</th>
											</tr>
											<tr>
												<th>Vehicle :</th>
												<th><c:choose>
														<c:when test="${TripDaily.VEHICLEID == 0}">
															<a
														href="<c:url value="/showVehicle?vid=${TripDaily.VEHICLEID}"/>"
														target="_blank"  style="color: #000000;" href="#" data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripDaily.VEHICLE_REGISTRATION}" />
															</a>
														</c:when>
														<c:otherwise>
															<a style="color: #000000;"
																href="showVehicle.in?vid=${TripDaily.VEHICLEID}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																	value="${TripDaily.VEHICLE_REGISTRATION}" />
															</a>
														</c:otherwise>
													</c:choose></th>
												<th>Depot :</th>
												<th><c:out value="${TripDaily.VEHICLE_GROUP}" /></th>
											</tr>
											<tr>
												<th>Date of Journey :</th>
												<th><a data-toggle="tip"
													data-original-title="Trip Open Date"> <c:out
															value="${TripDaily.TRIP_OPEN_DATE}" /></a></th>
												<th>Total Passenger:</th>
												<th><a data-toggle="tip"
													data-original-title="total passenger"> <c:out
															value="${TripDaily.TRIP_TOTALPASSNGER}" />
												</a></th>
											</tr>
											<tr>
												<th>Driver:</th>
												<th><a
													href="showDriver.in?driver_id=${TripDaily.TRIP_DRIVER_ID}"
													target="_blank" data-toggle="tip" data-original-title="Driver">
														<c:out value="${TripDaily.TRIP_DRIVER_NAME}" />
												</a></th>
												<th>RFID Pass :</th>
												<th><a data-toggle="tip"
													data-original-title="RFID pass"><c:out
															value="${TripDaily.TRIP_RFIDPASS}" /></a></th>

											</tr>
											<tr>
												<th>Conductor :</th>
												<th><a
													href="showDriver.in?driver_id=${TripDaily.TRIP_CONDUCTOR_ID}"
													target="_blank" data-toggle="tip" data-original-title="Driver 2"><c:out
															value="${TripDaily.TRIP_CONDUCTOR_NAME}" /></a></th>

												<th>Cleaner :</th>
												<th><a data-toggle="tip" data-original-title="Cleaner"><c:out
															value="${TripDaily.TRIP_CLEANER_NAME}" /></a></th>
											</tr>
											<tr>
												<th>Pass :</th>
												<th><a data-toggle="tip"
													data-original-title="Total Pass"><c:out
															value="${TripDaily.TRIP_PASS_PASSNGER}" /></a></th>

												<th>RFID Amount :</th>
												<th><a data-toggle="tip"
													data-original-title="RFID Amount"><c:out
															value="${TripDaily.TRIP_RFID_AMOUNT}" /></a></th>
											</tr>
											<tr>
												<th>Diesel :</th>
												<th><a data-toggle="tip" data-original-title="Diesel"><c:out
															value="${TripDaily.TRIP_DIESEL}" /></a></th>

												<th>Opening KM:</th>
												<th><a data-toggle="tip"
													data-original-title="Opening KM"><c:out
															value="${TripDaily.TRIP_OPEN_KM}" /></a></th>
											</tr>
											<tr>
												<th>Diesel KMPL:</th>
												<th><a data-toggle="tip"
													data-original-title="Diesel kmpl"><c:out
															value="${TripDaily.TRIP_DIESELKMPL}" /></a></th>
												<th>Closing KM:</th>
												<th><a data-toggle="tip"
													data-original-title="closing KM"> <c:out
															value="${TripDaily.TRIP_CLOSE_KM}" /></a></th>
											</tr>
											<tr>
											<c:if test="${!companyconfig.hideOverTimeColumn}">
												<th>OverTime :</th>
												<th><a data-toggle="tip"
													data-original-title="over time"><c:out
															value="${TripDaily.TRIP_OVERTIME}" /></a></th>
											</c:if>				
												<th>Total KM:</th>
												<th><a data-toggle="tip" data-original-title="usage KM">
														<c:out value="${TripDaily.TRIP_USAGE_KM}" />
												</a></th>
											</tr>
											<tr>
												<th>No of Round Trip :</th>
												<th colspan="3" id="noOfRoundTrip"><a data-toggle="tip"
													data-original-title="noOfRoundTrip"><c:out
															value="${TripDaily.noOfRoundTrip}" /></a></th>
											</tr>
											<c:if test="${companyconfig.showChaloDetails}">
											<tr>
												<th>Chalo KM :</th>
												<th><a data-toggle="tip"
													data-original-title="over time"><c:out
															value="${TripDaily.CHALO_KM}" /></a></th>
												<th>Chalo Amount:</th>
												<th><a data-toggle="tip" data-original-title="usage KM">
														<c:out value="${TripDaily.CHALO_AMOUNT}" />
												</a></th>
											</tr>
											</c:if>				
										</thead>
										<tbody>
											<tr>
												<td colspan="2">
													<table class="table table-bordered">
														<thead>
															<tr class="breadcrumb">
																<th class="fit ar">Income Name</th>
																<th class="fit ar">Amount</th>
															</tr>
														</thead>
														<tbody>
															<c:if test="${!empty TripColTimeIncome}">

																<c:forEach items="${TripColTimeIncome}" var="TripColTimeIncome">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit ar"><c:out
																				value="${TripColTimeIncome.incomeName}" /></td>
																		<td class="fit ar"><c:out
																				value="${TripColTimeIncome.incomeAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																	<td>
																		<h4 style="text-align: right;">Total Time Collection :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_INCOME_COLLECTION}
																		</h4>
																	</td>
																</tr>
															</c:if>
															<c:if test="${!empty TripColIncome}">

																<c:forEach items="${TripColIncome}" var="TripColIncome">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit ar"><c:out
																				value="${TripColIncome.incomeName}" /></td>
																		<td class="fit ar"><c:out
																				value="${TripColIncome.incomeAmount}" /></td>
																	</tr>
																</c:forEach>
																<tr>
																	<td>
																		<h4 style="text-align: right;">Total Income :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_INCOME}
																		</h4>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
												<td colspan="2">
													<table class="table table-bordered">
														<thead>
															<tr class="breadcrumb">
																<th class="fit ar">Expense Name</th>
																<th class="fit ar">Amount</th>

															</tr>
														</thead>
														<tbody>
															<c:if test="${!empty TripColExpense}">

																<c:forEach items="${TripColExpense}"
																	var="TripColExpense">

																	<tr data-object-id="" class="ng-scope">
																		<td class="fit ar"><c:out
																				value="${TripColExpense.expenseName}" /></td>

																		<td class="fit ar"><c:out
																				value="${TripColExpense.expenseAmount}" /></td>
																				

																	</tr>
																</c:forEach>
																<tr>
																	<td>
																		<h4 style="text-align: right;">Total Expense :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_EXPENSE}
																		</h4>
																	</td>
																</tr>
															</c:if>
														</tbody>
													</table>
												</td>
												
											
											</tr>
											
												
											<tr>
												<td colspan="4"><c:if test="${!empty DriverAdvanvce}">

														<fieldset>
															<div class="row">
																<div class="">
																	<table class="table table-bordered table-striped">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="fit">No</th>
																				<th class="fit ar">Name</th>
																				<th class="fit ar">Penalty date</th>
																				<th class="fit ar">Amount</th>
																				<th class="fit ar">Paid By</th>
																			</tr>
																		</thead>
																		<tbody>
																			<%
																				Integer hitsCount = 1;
																			%>
																			<c:forEach items="${DriverAdvanvce}"
																				var="DriverAdvanvce">
																				<tr data-object-id="" class="ng-scope">
																					<td class="fit">
																						<%
																							out.println(hitsCount);
																										hitsCount += 1;
																						%>
																					</td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.driver_empnumber} - " />
																						<c:out value="${DriverAdvanvce.driver_firstname} " />
																						<c:out value="${DriverAdvanvce.driver_Lastname}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_DATE}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_AMOUNT}" /></td>
																					<td class="fit ar"><c:out
																							value="${DriverAdvanvce.ADVANCE_PAID_BY}" /></td>

																				</tr>
																			</c:forEach>
																		</tbody>
																	</table>
																</div>
																<div class="row">
																	<div class="col-md-11">
																		<div class="col-md-offset-6">
																			<table class="table">
																				<tbody>
																					<tr data-object-id="" class="ng-scope">
																						<td class="key"><h4>Total Penalty (or)
																								WT:</h4></td>
																						<td class="value"><h4>
																								<i class="fa fa-inr"></i> ${TripDaily.TOTAL_WT}
																							</h4></td>

																					</tr>
																				</tbody>
																			</table>
																		</div>
																	</div>
																</div>
															</div>
														</fieldset>
													</c:if></td>
											</tr>

											<tr>
												<td colspan="4"><c:if
														test="${TripDaily.TRIP_CLOSE_STATUS == 'CLOSED'}">
														<table class="table table-bordered table-striped">
															<thead>
																<tr class="breadcrumb">

																	<th class="fit ar">Closed By</th>
																	<th class="fit ar">(Income - WT) - (Expense + OverTime)
																		</th>
																	<th class="fit ar">Closed Date</th>
																	<th class="fit ar">Status</th>
																</tr>
															</thead>
															<tbody>
																<tr data-object-id="" class="ng-scope">

																	<td class="fit ar"><c:out
																			value="${TripDaily.LASTMODIFIEDBY}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripDaily.TOTAL_BALANCE}" /></td>
																	<td class="fit ar"><c:out
																			value="${TripDaily.LASTUPDATED}" /></td>
																	<td class="fit ar"><c:choose>

																			<c:when
																				test="${TripDaily.TRIP_CLOSE_STATUS == 'CLOSED'}">
																				<span class="label label-pill label-success"><c:out
																						value="${TripDaily.TRIP_CLOSE_STATUS}" /></span>
																			</c:when>
																			<c:otherwise>
																				<span class="label label-pill label-warning"><c:out
																						value="${TripDaily.TRIP_CLOSE_STATUS}" /></span>
																			</c:otherwise>
																		</c:choose></td>
																</tr>
																<tr data-object-id="" class="ng-scope">
																	<td colspan="2">
																		<h4 style="text-align: right;">Total Payment :</h4>
																	</td>
																	<td>
																		<h4>
																			<i class="fa fa-inr"></i> ${TripDaily.TOTAL_BALANCE}
																		</h4>
																	</td>
																</tr>
															</tbody>
														</table>
													</c:if></td>
											</tr>
										</tbody>
									</table>
								</div>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a class="btn btn-default btn-sm"
						href="newTripDaily.in">Overview</a></li>

					<c:choose>
						<c:when test="${TripDaily.TRIP_CLOSE_STATUS == 'OPEN'}">

							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addDailyExpense.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Expense
									</a>
									</sec:authorize></li>
							<li><sec:authorize
									access="hasAuthority('ADD_FUEL_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="addTripDailyFuel.in?ID=${TripDaily.TRIPDAILYID}" onclick="return validateNoOfRoundTrip();" > <span
										class="fa fa-plus"></span> Fuel Entries
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="addDailyIncome.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Income
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addDailyTimeIncome.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Time Income
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<c:if test="${!companyconfig.hideAddPenalty}">
										<a class="btn btn-default btn-sm"
											href="addDailyPenalty.in?ID=${TripDaily.TRIPDAILYID}"> <span
											class="fa fa-plus"></span> Penalty
										</a>
									</c:if>
								</sec:authorize></li>
							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">
									<a class="btn btn-danger btn-sm"
										href="addCloseTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return validatePassengerEntry(${TripDaily.TRIP_TOTALPASSNGER})"> <span
										class="fa fa-times"></span> Close Trip
									</a>
								</sec:authorize></li>

							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="editTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Edit sheet ')">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li>
							<li><sec:authorize access="hasAuthority('DELETE_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="deleteTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Delete ')"> <span
										class="fa fa-trash"></span> Delete
									</a>
								</sec:authorize></li>
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addDailyPassengerDetails.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure?  ')"> <span
										class="fa fa-plus"></span> Passenger Details
									</a>
								</sec:authorize></li>
								<!--newystart-->
								<%-- <li><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="chaloDetails.in?ID=${TripDaily.TRIPDAILYID}"> <span
										class="fa fa-plus"></span> Chalo
									</a>
								</sec:authorize></li> --%>
								<!--newystop-->	
								
								<!-- latest start-->
								
								<!-- latest end-->							
								<c:if test="${companyconfig.showFixedExpenseButton}">
									<li><sec:authorize
										access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
										<a id="addFixedExpenses" onclick="addFixedExpenses(${TripDaily.TRIP_ROUTE_ID}, ${TripDaily.TRIPDAILYID});" class="btn btn-success btn-sm"
											href="#"> <span
											class="fa fa-plus"></span> Fixed Expense
										</a>
										</sec:authorize></li>
								</c:if>		
								<li><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<input type="hidden" id="allowTollApiIntegration" value="${allowTollApiIntegration}" />
									<a id="tollApi" style="display: none;" class="btn btn-info btn-sm"
										href="#"
										 onclick="return addTollExpensesDetails(${TripDaily.TRIPDAILYID}, ${TripDaily.VEHICLEID}, '${TripDaily.VEHICLE_REGISTRATION}', '${TripDaily.tripDate}', '${TripDaily.tripDate}');"> <span
										class="fa fa-plus"></span> Toll Expenses
									</a>
								</sec:authorize></li>
						</c:when>
						<c:when test="${TripDaily.TRIP_CLOSE_STATUS == 'CLOSED'}">
							<li class="divider"></li>
							<li><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="reopenTripDaily.in?ID=${TripDaily.TRIPDAILYID}"
										class="confirmation"
										onclick="return confirm('Are you sure? Re-open sheet ')">
										<span class="fa fa-rocket"> </span> Re-Open
									</a>
								</sec:authorize></li>
						</c:when>
					</c:choose>
					<c:if test="${companyconfig.showChaloDetails}">
								<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
									<a class="btn btn-warning btn-sm" 
										class="confirmation" 
										onclick="email();" > <span
										class="fa fa-plus"></span> Add Chalo
									</a>
								</sec:authorize>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${TripDaily.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${TripDaily.CREATED}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${TripDaily.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${TripDaily.LASTUPDATED}" /></small>
		</div>
	</section>
	<script type="text/javascript">
			function validatePassengerEntry(totalPassenger){
				console.log(typeof(totalPassenger));
					if(totalPassenger == undefined || totalPassenger == null){
						showMessage('info', 'Please Enter Passenger Details Before Closing Trip Sheet!');
						return false;
					}
					return confirm('Are you sure? Close ');
			}
			function validateNoOfRoundTrip(){
				if(Number($('#noOfRoundTrip').val()) <= 0){
					showMessage('info','Please Enter No Of Round Trip !');
					$('#editrenewalPeriod').modal();
					return false;
				}o
				return false;
			}	
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripTollExpensesDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripAddFixedExpenses.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripChaloDetails.js"/>"></script>	
	<!-- <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/printTripsheet.js"/>"></script> -->
</div>