<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	
<%@ page import="org.fleetopgroup.constant.TripSheetStatus" %>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
							href="<c:url value="/newTripSheetEntries.in?loadTypeId=2"/>">Dispatch</a> 
						/ <small>Show TripSheet</small>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-2">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">TS-</span></span> <input class="form-text"
										id="searchTripSheet" name="tripStutes" type="number" min="1" onkeyup="searchTripSheetShow(event);"
										required="required" placeholder="TS-ID eg:7878" maxlength="20">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm" onclick="searchTripSheet();">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
						<sec:authorize access="hasAuthority('ADD_TRIPSHEET')">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/addTripSheetEntries.in"/>"> <span
								class="fa fa-plus"></span> Create TripSheet
							</a>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<c:if test="${configuration.dispatchTripPrint}">
									<a  onclick="showDispatchTripPrint(${tripSheetId});" class="btn btn-default statusTwo"><em
										class="fa fa-print"></em> Print</a>
								</c:if>
								<c:if test="${!configuration.dispatchTripPrint}">
									<a href="showTripSheetPrint.in?id=${tripSheetId}"
										target="_blank" class="btn btn-default statusTwo"><em
										class="fa fa-print"></em> Print</a>
								</c:if>
									<a href="TSPrint.in?ID=${tripSheetId}"
										target="_blank" class="btn btn-default statusThree statusFour"><em
										class="fa fa-print"></em> Print</a>
							 <sec:authorize access="hasAuthority('UPDATE_CLOSING_KM')">
										<a class="btn btn-success btn-sm statusThree" id="updateClosingKM"
											href="#"
											class="confirmation"> <span
											class="fa fa-rocket"></span> Update Closing KM
										</a>
							</sec:authorize>
							<input type="hidden"  id="vExpectedOdometer" value="${vehicle.vehicle_ExpectedOdameter}" />
							<input type="hidden"  id="validateDirectOdometerUpdate" value="${configuration.validateDirectOdometerUpdate}" />
							<input class="hidden" id="tripCloseKM" /> 
							 <sec:authorize access="hasAuthority('UPDATE_OPENING_KM')">
							 <c:if test="${configuration.updateOpeningKm}">
								<a id="updateOPeningKM" class="btn btn-success btn-sm  statusThree" href="#" class="confirmation">
									<span class="fa fa-rocket"></span> Update Opening KM
								</a>
							</c:if>	
							</sec:authorize>
							 <sec:authorize access="hasAuthority('EDIT_TRIP_ROUTE_POINT')">
								<a id="editRoutePoint" class="btn btn-success btn-sm  statusThree" href="#" class="confirmation">
									<span class="fa fa-rocket"></span> Edit Route Point
								</a>
							</sec:authorize>
						</sec:authorize>
						<c:if test="${configuration.showTripsheetDueAmount}">
							<div class="pull-right">
								<a class="btn btn-danger btn-sm statusTwo statusThree" id="balanceDueAmount"
									href="#" class="confirmation">
									 <span class="fa fa-rupee" id="pendingDue"></span> 
								</a>
							</div>
						</c:if>
							<div class="pull-right">
								<a class="btn btn-danger btn-sm statusTwo statusThree hide" id="driverWalletBalance"
									href="#" class="confirmation">
									 <span class="fa fa-rupee" id="driverWalletBalanceSpan"></span> 
								</a>
							</div>
						
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content" id="tripSheetDetails" style="display: none;">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-8 col-xs-12">
				
				<input type="hidden" id="downloadTripExpenseDocument" value="${configuration.downloadTripExpenseDocument}">
				<input type="hidden" id="tripVoucherDate" value="">
				<input type="hidden" id="tripSheetId" value="${tripSheetId}">
				<input type="hidden" id="companyId" value="${companyId}">
				<input type="hidden" id="userId" value="${userId}">
				<input type="hidden" id="showTripsheetDueAmount" value="${configuration.showTripsheetDueAmount}">
				<input type="hidden" id="showDriverFullNameConfig" value="${configuration.showDriverFullName}">
				<input type="hidden" value="${configuration.allowTallyIntegration}" id="allowTallyIntegration">
				<input type="hidden" id="roundFigureAmount" value="${configuration.roundFigureAmount}">
				<input type="hidden" id="allowBitlaApiIncome" value="${configuration.allowBitlaApiIncome}">
				<input type="hidden" id="showDiffAvgCost" value="${configuration.showDiffAvgCost}">
				<input type="hidden" id=allowAccountCloseWithoutIncome value="${configuration.allowAccountCloseWithoutIncome}">
				<input type="hidden" id="allowMantisDispatchIncome" value="${configuration.allowMantisDispatchIncome}">
				<input type="hidden" id="hideAdvanceAndExpenseAddIfAccClose" value="${configuration.hideAdvanceAndExpenseAddIfAccClose}">
				<input type="hidden" value="${configuration.hideVoucherDate}" id="hideVoucherDate">
				<input type="hidden" value="${configuration.hideTallyCompany}" id="hideTallyCompany">
				<input type="hidden" value="${configuration.directAccountClose}" id="directAccountClose">
				<input type="hidden" value="" id="tripTotalexpense">
				<input type="hidden" value="${configuration.showVehicleSearchAfterACCClose}" id="showVehicleSearchAfterACCClose">
				<input type="hidden" value="0" id="tvid">
				<input type="hidden" value="${configuration.showLsSourceAndDestination}" id="showLsSourceAndDestination">
				<sec:authorize access="hasAuthority('VIEW_B_E_INCOME_TRIPBALANCE')">
					<input type="hidden" id="View_BE_TripBalance" value="true">
				</sec:authorize>
				
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
											value="${tripSheetId}" />
										<input type="hidden"  id="allowTollApiIntegration" name="allowTollApiIntegration" />	
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
				
				<div class="box">
					<div class="boxinside">
						<div id="div_printTripsheet">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET')">
								<spring:message code="message.unauth"></spring:message>
							</sec:authorize>
							<sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
								<div>
									<div class ="row">
										<h4><span id="DriverWarningMsg" style="color: red; font-weight: bold;"></span></h4>
									</div>
								</div>
								<br>
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- <span id="tripSheetNumber"></span></h4>
									</div>
									<div class="pull-right">
										<h5>Created Date : <span id="created"></span> </h5>
									</div>
								</div>
								<div class="row">
									<h4 align="center">
												<a style="color: #000000;" href="#" data-toggle="tip"
													data-original-title="Click Vehicle Info"> <span id="vehicle_registration"></span>
												</a>
									</h4>
								</div>
								<div class="row">
									<h4 align="center"> <span id="routeName"></span> </h4>
								</div>
								
								
								<div class="secondary-header-title">
									<table class="table" id="tripSheetDetailsTable">
										<tbody id="tripSheetDetailsBody">
											
										</tbody>
									</table>
								</div>
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET_ONLY')">	
								<table class="table">
									<tbody id="tripSheetSubDetailsBody">

									</tbody>
								</table>
							</sec:authorize>
								<sec:authorize access="hasAuthority('VIEW_LORRY_HIRE')">	
								<table class="table">
									<tbody id="lorryHireBody">

									</tbody>
								</table>
								</sec:authorize>
							</sec:authorize>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-1 col-sm-2 col-xs-12">
				<ul class="nav nav-list">

					<li class="active"><a class="btn btn-default btn-sm"
						href="newTripSheetEntries.in">Overview</a></li>
						<li class="divider statusOne"></li>
						<li class="statusOne"><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
								<a class="btn btn-success btn-sm"
									href="dispatchTripSheet.in?ID=${tripSheetId}"
									class="confirmation"
									onclick="return confirm('Are you sure? Dispatch sheet ')">
									<span class="fa fa-rocket"> </span> Dispatch
								</a>
							</sec:authorize></li>
						
							<li id="advanceLi" class="statusTwo statusThree"><sec:authorize
									access="hasAuthority('ADD_ADVANCE_TRIPSHEET')">
									<a class="btn btn-success btn-sm"
										href="addAdvance.in?tripSheetID=${tripSheetId}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Advance ')"> <span
										class="fa fa-plus"></span> Advance
									</a>
								</sec:authorize></li>
							<li id="expenseLi" class="statusTwo statusThree"><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<a class="btn btn-warning btn-sm"
										href="addExpense.in?tripSheetID=${tripSheetId}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Expense ')"> <span
										class="fa fa-plus"></span> Expense
									</a>
								</sec:authorize> 
							<sec:authorize access="hasAuthority('ADD_ROUTE_WISE_TRIPSHEET_WEIGHT')">
								<a class="btn btn-primary btn-sm"
									href="addTripsheetWeight.in?tripSheetID=${tripSheetId}"
									class="confirmation"
									onclick="return confirm('Are you sure? Add Tripsheet Weight ')"> <span
									class="fa fa-plus"></span> TripSheetweight
								</a>
							</sec:authorize>
								<sec:authorize access="hasAuthority('ADD_HALT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="addHaltAmount.in?ID=${tripSheetId}"> <span
										class="fa fa-plus"></span> Driver Halt
									</a>
								</sec:authorize> <sec:authorize access="hasAuthority('ADD_FUEL_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="addTSFuel.in?ID=${tripSheetId}"> <span
										class="fa fa-plus"></span> Fuel Entries
									</a>
								</sec:authorize></li>
							<li class="divider"></li>
							<li id="incomeLi"  class="statusTwo statusThree"><sec:authorize
									access="hasAuthority('ADD_INCOME_TRIPSHEET')">
									<a class="btn btn-info btn-sm"
										href="addIncome.in?tripSheetID=${tripSheetId}"
										class="confirmation"
										onclick="return confirm('Are you sure? Add Income ')"> <span
										class="fa fa-plus"></span> Income
									</a>
								</sec:authorize></li>
							<li class="statusThree"><sec:authorize
									access="hasAuthority('CLOSE_ACCOUNT_TRIPSHEET')">
									<input type="hidden" id="tripsheetTotalIncome">
									<a class="btn btn-success btn-sm"
										href="addcloseAccountTripSheet.in?tripSheetID=${tripSheetId}"
										class="confirmation"
										onclick="return closeTripSheetAcc();"> <span
										class="fa fa-times"></span> Close A/C
									</a>
								</sec:authorize></li>
							<li class="statusTwo"><sec:authorize access="hasAuthority('CLOSE_TRIPSHEET')">
									<a class="btn btn-danger btn-sm"
										href="addCloseTripsheet.in?tripSheetID=${tripSheetId}">
										<span class="fa fa-times"></span> Close Trip
									</a>
								</sec:authorize></li>
							
							<li id="editTripSheet" class="statusTwo"><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="<c:url value="/editTripSheet.in?ID=${tripSheetId}"/>">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li>
							<li id="editTripSheet" class="statusThree"><sec:authorize access="hasAuthority('TRIPSHEET_EDIT_AFTER_CLOSE')">
									<a class="btn btn-default btn-sm"
										href="<c:url value="/editTripSheet.in?ID=${tripSheetId}"/>">
										<span class="fa fa-rocket"> </span> Edit
									</a>
								</sec:authorize></li>	
							<li id="editTripSheet" class="statusOne statusTwo statusThree statusFour"><sec:authorize access="hasAuthority('SHOW_TRIPSHEET_DOCUMENT')">	
								<a class="btn btn-default btn-sm" onclick="editDocModal();" ><span class="fa fa-rocket"> </span> Trip Document
								</a>
							</sec:authorize></li>	
							<li id="deleteTripSheet" class="statusTwo statusOne"><sec:authorize access="hasAuthority('DELETE_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="#"
										class="confirmation"
										onclick="deleteTripSheetData();"> <span
										class="fa fa-trash"></span> Delete
									</a>
								</sec:authorize></li>
							
						<c:if test="${configuration.driverPenaltyDetails}">
							<li class="statusTwo statusThree driverPenalty"><sec:authorize
									access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
									<a class="btn btn-default btn-sm"
										href="driverPenalty.in?ID=${tripSheetId}"> <span
										class="fa fa-plus"></span> Penalty
									</a>
								</sec:authorize></li>
							</c:if>	
							
								 <c:if test="${configuration.showExtraReceived}"> 
									<li class="statusTwo"><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
										<a class="btn btn-warning btn-sm"
											href="addExtraOptions.in?tripSheetID=${tripSheetId}"
											class="confirmation"
											onclick="return confirm('Are you sure? Add Extra ')"> <span
											class="fa fa-plus"></span> Extra
										</a>
									</sec:authorize></li>
								 </c:if> 
								 <c:if test="${configuration.addLhpvAfterTripSheet}"> 
									<li class="statusTwo"><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
										<a class="btn btn-warning btn-sm" id="addLhpvAfterTripSheet"
											href="#"
											class="confirmation"
											onclick="return confirm('Are you sure to Add LHPV ? ')"> <span
											class="fa fa-plus"></span> Add Lhpv
										</a>
									</sec:authorize></li>
								 </c:if> 
								 <c:if test="${configuration.showPODdetails}"> 
									<li class="statusTwo"><sec:authorize access="hasAuthority('EDIT_TRIPSHEET')">
										<a class="btn btn-success btn-sm"
											href="addPOD.in?tripSheetID=${tripSheetId}"
											class="confirmation"
											onclick="return confirm('Are you sure to Add POD ? ')"> <span
											class="fa fa-plus"></span> Add POD
										</a>
									</sec:authorize></li>
								 </c:if> 
								 <c:if test="${configuration.showPODdetails}">	
									<li class="statusTwo statusThree"><sec:authorize
											access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
											<a class="btn btn-success btn-sm"
													href="receivePODAccount.in?tripSheetID=${tripSheetId}"
													class="confirmation"
													onclick="return confirm('Are you sure to Receive POD ? ')"> <span
													class="fa fa-plus"></span> Receive POD
											</a>
										</sec:authorize></li>
									</c:if>	
								  <c:if test="${configuration.allowIVLoadingSheetEntry}"> 
									<li class="ivCargoLSData">
										<a class="btn btn-success btn-sm" id="allowIVLoadingSheetEntry"
											href="#"
											class="confirmation">  Add IVCargo LS
										</a>
									</li>
								 </c:if>
								 <li class="statusTwo statusThree"><sec:authorize access="hasAuthority('SHOW_UREA_ENTRY_IN_TRIPSHEET')">
										<a class="btn btn-default btn-sm"
										href="addTripSheetUreaEntries.in?tripSheetID=${tripSheetId}"> <span
										class="fa fa-plus"></span> Urea Entries
									</a>
								</sec:authorize></li>
								 <li class="statusTwo"><sec:authorize access="hasAuthority('ADD_REFRESHMENT_INVENTORY')">
										<a class="btn btn-success btn-sm"
										href="refreshmentEntry.in?tripSheetID=${tripSheetId}"> <span
										class="fa fa-plus"></span> Refreshment
									</a>
								</sec:authorize></li>
								<c:if test="${configuration.allowTallyIntegration}">
									<c:if test="${!configuration.hideVoucherDate}">
											 <li class="statusTwo statusThree">
											<a class="btn btn-info btn-sm"
											href="#" onclick="showVoucherDateModal();"> <span
											class="fa fa-plus"></span> Voucher Date
										</a>
									</li>
									
									</c:if>
									<c:if test="${!configuration.hideTallyCompany}">
										<li class="statusTwo statusThree">
												<a class="btn btn-info btn-sm"
												href="#" onclick="showTallyCompanyModal();"> <span
												class="fa fa-plus"></span> Tally Company
											</a>
										</li>
									</c:if>
									
								</c:if>
								<c:if test="${configuration.showTripsheetDueAmount}">
								 	<li class="statusTwo statusThree">
									 	<a class="btn btn-warning btn-sm"
											href="addDueAmount.in?ID=${tripSheetId}"> <span
											class="fa fa-plus"></span> Due Amount
										</a>
									</li>
								</c:if>	
								<li class="statusThree"><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
										<a id="tollApi" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"> <span
											class="fa fa-plus"></span> Toll  Expense
										</a>
								</sec:authorize></li>
								<li class="statusTwo statusThree">
									<sec:authorize access="hasAuthority('VIEW_LORRY_HIRE')">
											<a class="btn btn-info btn-sm"
												href="addVendorLorryHireDetails?tripSheetID=${tripSheetId}"> <span
												class="fa fa-plus"></span> Add Lorry Hire
											</a>
									</sec:authorize>
								</li>
								<li class="statusThree">
									<sec:authorize access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
										<a id="gpsApi" class="btn btn-success btn-sm" href="#" class="confirmation"> 
											<span class="fa fa-plus"></span> GPS Data
										</a>
									</sec:authorize>
								</li>
								<li class="statusThree"><sec:authorize
									access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
									<c:if test="${configuration.allowTicketIncomeApiIntegeration }">
										<a id="mantisIncome" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"> <span
											class="fa fa-plus"></span> Mantis Income
										</a>
									</c:if>
									<c:if test="${configuration.allowITSGatewayBusIncome }">
										<a id="itsIncome" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"> <span
											class="fa fa-plus"></span> Bus Income
										</a>
									</c:if>
									<c:if test="${configuration.allowBitlaApiIncome }">
										<a id="bitlaIncome" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"> <span
											class="fa fa-plus"></span> Bitla Income
										</a>
									</c:if>
									<c:if test="${configuration.allowBitlaApiIncome }">
										<a id="bitlaIncome" class="btn btn-success btn-sm"
											href="#"
											class="confirmation"> <span
											class="fa fa-plus"></span> Bitla Income
										</a>
									</c:if>
						</sec:authorize></li>
							<li>
								 <sec:authorize access="hasAuthority('CHANGE_STATUS')"> 
									<a id="showChangeStatus" style="display:none" class="btn btn-info btn-sm"
										href="#" onclick="ChangeStausToClose();"> <span
										class="fa fa-plus"></span> Change Status
									</a>
								 </sec:authorize> 
							</li>
						
								<li class="statusTwo statusThree statusFour">
									<sec:authorize access="hasAuthority('TRIP_CHECK_POINT_INSPECTION')">
										<a class="btn btn-success btn-sm"
											href="<c:url value="/tripCheckPointInspection?tripSheetId=${tripSheetId}"/>"> 
											<span class="fa fa-plus"></span> CheckPoint
										</a>
									</sec:authorize>
									<sec:authorize access="hasAuthority('ADD_COMMENT_TRIPSHEET')">
										<a class="btn btn-success" data-toggle="modal"
											data-target="#tripComment"> <i class="fa fa-comment"></i>
											Comment <span data-toggle="tip" title="" class="badge bg-red" id="commentCount"
											data-original-title="comment"></span>
										</a>
									</sec:authorize>
								</li>
					<li class="divider"></li> 
				</ul>
			</div>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b><span id="createdBy"></span></small> | <small class="text-muted"><b>Created
					date: </b> <span id="createdOn"></span></small> | <small
				class="text-muted"><b>Last updated by :</b> <span id="updatedBy"></span></small> | <small class="text-muted"><b>Last
					updated date:</b> <span id="updatedOn"></span></small>
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
										<input type="hidden"  id="TRIPSHEETID"
											value="${TripSheet.tripSheetID}" />
										<input type="hidden"  id="vid" name="vid" />	
									</div>
								</div>
								<div class="row1" id="grprenewalDate" class="form-group">

										<label class="L-size control-label" for="reservation">Trip Closing KM
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="tripClosingKM"  
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
			
						<div class="modal fade" id="updateOpenKm" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">TripSheet Open Odometer</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<input type="hidden" name="TRIPSHEETID" id="TRIPID"  /> 
											<input type="hidden"  id="vehicleId" name="vid"  />
											
										</div>
									</div>
									<div class="row1" id="grprenewalDate" class="form-group">
										<label class="L-size control-label" for="reservation">Trip Opening KM 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="tripOpenKM"
												onkeypress="return isNumberKey(event);" required="required" name="tripOpeningKM" type="number" onpaste="return false" />
											<div style="color: red">Odometer Range : 
											<span id="startingKm"></span> - 
											<span id="endingKm"></span>
											</div>
										</div>
									</div>
									<br>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary"
											onclick="updateTripSheetOpeningKM();" id="js-upload-submit"
											value="Save Odometer">Update Open Odometer</button>
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
								</div>
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
			
			<div class="modal fade" id="tallyCompanyModal" role="dialog">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Tally Company</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="tallyIntegrationRequired" value="${configuration.tallyIntegrationRequired}">
											<div class="row1" id="grpmanufacturer" class="form-group">
											<label class="L-size control-label" for="manufacurer">Tally Company Name :<abbr title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="tallyCompanyId" name="tallyCompanyId" style="width: 100%;" value="0"
													  placeholder="Please Enter Tally Company Name" />
												</div>
											</div>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-info"
						>
					<span id="saveTallyCompany" onclick="return saveTallyCompany();">save</span>
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
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Trip Toll Expenses</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
					<table class="box-body" id="modelBodyExpense" border="1" width="100%">
					
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
			<div id="idMyModal"></div>
			
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
			
		<div class="modal fade" id="loadingSheetIncome" role="dialog">
			<div class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Trip Income</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
					<table class="box-body" id="modelBodyloadingSheetIncome" border="1" width="100%">
					
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
														id="refreshmentDate" required="required" value=""
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
													onkeypress="return isNumberKeyWithDecimal(event,this.id); return validateReturnQuantity();"
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
			
			<div class="modal fade" id="editRoutePointModal" role="dialog">
					<div class="modal-dialog">
						<!-- Modal content-->
						<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal">&times;</button>
									<h4 class="modal-title">Trip Route Point Edit</h4>
								</div>
								<div class="modal-body">
									<div class="row">
										<input type="hidden"  id="TRIPSHEETID"
											value="${TripSheet.tripSheetID}" />
										<input type="hidden"  id="vid" name="vid" />	
									</div>
								</div>
								<div class="row1" id="driver1PointRow" class="form-group">

										<label class="L-size control-label" for="reservation">Driver 1 : 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="driver1Point"  
											required="required" name="driver1Point" type="number"></input>
										</div>
								</div>
								<div class="row1" id="driver2PointRow" class="form-group">

										<label class="L-size control-label" for="reservation">Driver 2 : 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="driver2Point"  
											required="required" name="driver2Point" type="number"></input>
										</div>
								</div>
								<div class="row1" id="cleanerPointRow" class="form-group">

										<label class="L-size control-label" for="reservation">Cleaner : 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
										<input class="form-text" id="cleanerPoint"  
											required="required" name="cleanerPoint" type="number"></input>
										</div>
								</div>
									<br>
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary" onclick="updateTripRoutePoint();"
										id="js-upload-submit" value="Save Odometer">Update
										</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
								</div>
						</div>
					</div>
				</div>
			
						<div class="modal fade" id="updateOpenKm" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">TripSheet Open Odometer</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<input type="hidden" name="TRIPSHEETID" id="TRIPID"  /> 
											<input type="hidden"  id="vehicleId" name="vid"  />
											
										</div>
									</div>
									<div class="row1" id="grprenewalDate" class="form-group">
										<label class="L-size control-label" for="reservation">Trip Opening KM 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="tripOpenKM"
												onkeypress="return isNumberKey(event);" required="required" name="tripOpeningKM" type="number" onpaste="return false" />
											<div style="color: red">Odometer Range : 
											<span id="startingKm"></span> - 
											<span id="endingKm"></span>
											</div>
										</div>
									</div>
									<br>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary"
											onclick="updateTripSheetOpeningKM();" id="js-upload-submit"
											value="Save Odometer">Update Open Odometer</button>
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="modal fade" id="editRoutePointModal" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">TripSheet Open Odometer</h4>
									</div>
									<div class="modal-body">
										<div class="row">
											<input type="hidden" name="TRIPSHEETID" id="TRIPID"  /> 
											<input type="hidden"  id="vehicleId" name="vid"  />
											
										</div>
									</div>
									<div class="row1" id="grprenewalDate" class="form-group">
										<label class="L-size control-label" for="reservation">Trip Opening KM 
											<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="tripOpenKM"
												onkeypress="return isNumberKey(event);" required="required" name="tripOpeningKM" type="number" onpaste="return false" />
											<div style="color: red">Odometer Range : 
											<span id="startingKm"></span> - 
											<span id="endingKm"></span>
											</div>
										</div>
									</div>
									<br>
									<div class="modal-footer" style="padding-left: 20px;">
										<button type="submit" class="btn btn-primary"
											onclick="updateTripSheetOpeningKM();" id="js-upload-submit"
											value="Save Odometer">Update Open Odometer</button>
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
						
						<div class="modal fade" id="previousDuePayment" role="dialog">
							<div class="modal-dialog modal-md" style="width:1250px;">
								<div class="modal-content">
										<div class="form-horizontal ">
				
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<!-- <h4 class="modal-title" id="TripExpense">Pre EMI Settlement</h4> -->
											</div>
											
											<div class="modal-body">
												<div class="panel panel-success "  id="settle-border-boxshadow">
													<div class="panel-heading text-center">
														<h4 >Due Amount Settlement
														</h4>
													</div>
												</div>
												
												<div class="row invoice-info">
													<table id="dueAmountTable" style="width: 95%;"
													class="table-hover table-bordered">
													</table>
												</div>
												
											</div>
											
											<div class="modal-footer">
												<button type="button" class="btn btn-default" data-dismiss="modal">
													<span id="Close"><spring:message code="label.master.Close" /></span>
												</button>
											</div>
									  </div>
								</div>
							</div>
						</div>
		
	</section>
	
	<div class="modal fade" id="editTripSheetDoc" role="dialog" >
		<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
			<div class="modal-dialog" style="width:1200px;">
				<!-- Modal content-->
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">TripSheet Document</h4>
						</div>
						<input type="hidden" id="tripSheetDocumentId" >
						<div class="modal-body">
						<div class="row">
							<div class="form-group column">
								<label for="comment">TripSheet Document:</label>
								<input type="file" name="input-file-preview" id="tripDocument" data-max-size="512000" /> 
								<span id="renewalFileIcon" 	class=""></span>
								<div id="renewalFileErrorMsg" class="text-danger"></div>
								<span class="help-block">Add an optional document</span>
							</div>
							<div class="form-group">
							<label for="comment">TripSheet Document:</label>
							<a href ="#" onclick="downloadTripSheetDocument();">
							<span class="fa fa-download"> Doc</span>
							</a>
							</div>
							</div>
						</div>
						<div class="modal-footer">
						
						<input type="button" value="Update TripSheet Doc" onclick="updateTripSheetDocument();" id="btnSubmitS" class="btn btn-success" />
						<a class="btn btn-link" data-dismiss="modal">Cancel</a>
						</div>
				</div>
			</div>
			</form>
		</div>
	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSearchVehicle.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripTollExpensesDetails.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetCombineDetails.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/LinkLoadingSheetToTripSheet.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetDetails.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetShowAccount.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripUpdateClosingKM.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripUpdateOpeningKM.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/addMantisIncome.js" />"></script>	
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetChangeStatusToClose.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetAccountClose.js" />"></script>	
</div>
