<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="showTripSheet.in?tripSheetID=${tripSheetId}"> Trip Number : TS- ${TripSheet.tripSheetNumber}</a>
				</div>
				
			</div>
		</div>
	</section>
	<!-- Main content -->

	<section class="content">
	
		<div class="box">
			<c:if test="${!noTripSheet}">
						<div class="boxinside">
						<div class="row">
							<div class="row">
								<div class="pull-left">
									<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									<input type="hidden" id="tripSheetNumber" value="${TripSheet.tripSheetNumber}"/>
								</div>
								<div class="pull-right">
									<h5>Created Date : ${TripSheet.created}</h5>
								</div>
							</div>

							<div class="row">
								<h4 align="center">
									<a href="showVehicle.in?vid=${TripSheet.vid}" data-toggle="tip"
										data-original-title="Click Vehicle Info"> <c:out
											value="${TripSheet.vehicle_registration}" />
									</a>
									<input type="hidden" id="vid" value="${TripSheet.vid}" />
									<input type="hidden" id="vehicle_registration" value="${TripSheet.vehicle_registration}" />
									
												<input type="hidden" id="tripFristDriverID" value="${TripSheet.tripFristDriverID}">
												<input type="hidden" id="tripSecDriverID" value="${TripSheet.tripSecDriverID}">
												<input type="hidden" id="tripCleanerID" value="${TripSheet.tripCleanerID}">
												<input type="hidden" id="routeID" value="${TripSheet.routeID}">
												<input type="hidden" id="routeName" value="${TripSheet.routeName}">
												<input type="hidden" id="tripFristDriverName" value="${TripSheet.tripFristDriverName}">
												<input type="hidden" id="tripSecDriverName" value="${TripSheet.tripSecDriverName}">
												<input type="hidden" id="tripCleanerName" value="${TripSheet.tripCleanerName}">
								</h4>
							</div>
							<div class="col-md-3"></div>
						</div>
						<div class="row">
							<h4 align="center">${TripSheet.routeName}</h4>
						</div>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>Date of Journey : <a data-toggle="tip"
									data-original-title="Trip Open Date"> <c:out
											value="${TripSheet.tripOpenDate}  TO" /></a> <a
									data-toggle="tip" data-original-title="Trip Close Date"> <c:out
											value="  ${TripSheet.closetripDate}" /></a></li>
								<li>Group Service : <a data-toggle="tip"
									data-original-title="Group Service"><c:out
											value="${TripSheet.vehicle_Group}" /></a></li>
								<li>Booking No : <a data-toggle="tip"
									data-original-title="Booking No"> <c:out
											value="${TripSheet.tripBookref}" /></a></li>
								<li>Driver 1 : <a data-toggle="tip"
									data-original-title="Driver 1"> <c:out
											value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}   " /></a></li>
								<li>Driver 2 : <a data-toggle="tip"
									data-original-title="Driver 2"><c:out
											value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName} " /></a></li>
								<li>Cleaner : <a data-toggle="tip"
									data-original-title="Cleaner"><c:out
											value="${TripSheet.tripCleanerName}" /></a></li>
								<li>Opening KM : <a data-toggle="tip"
									data-original-title="Opening KM"><c:out
											value="${TripSheet.tripOpeningKM}" /></a></li>
								<li>Closing KM : <a data-toggle="tip"
									data-original-title="closing KM"> <c:out
											value="${TripSheet.tripClosingKM}" /></a></li>
							</ul>
						</div>
					
						<c:if test="${!empty urea}">
							<table id="FuelTable" class="table table-hover table-bordered">
								<thead>
									<tr>
										<th>ID</th>
										<th>Vehicle</th>
										<th>Urea Manufacturer</th>
										<th>Date</th>
										<th>Close(Km)</th>
										<th>Volume</th>
										<th>Amount</th>
										<th>Actions</th>
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
											
											<td><a
												onclick ="deleteTripSheetUreaEntries(${urea.ureaEntriesId})"
												<%-- href="removeTSFuel.in?TSID=${urea.tripSheetId}&FID=${urea.ureaEntriesId}" --%>
												data-toggle="tip" data-original-title="Click Remove"><font
													color="red"><i class="fa fa-times"> Remove</i></font></a></td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="5" class="text-right"
											style="font-size: 15px; font-weight: bold;">Total Volume
											:</td>
										<td colspan="3" style="font-size: 15px; font-weight: bold;"><c:out
												value="${totalUrea} " /></td>
									</tr>
								</tbody>
							</table>
						</c:if>	
					   </div>
					</c:if>
				</div>


		<sec:authorize access="!hasAuthority('ADD_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_FUEL')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

					<%-- <form id="formFuel" action="<c:url value="/saveFuel.in"/>"
						method="post" enctype="multipart/form-data" name="formFuel"
						role="form" class="form-horizontal"
						onsubmit="return validateFuel();"> --%>
				<div class="alert alert-success hide" id="showData" >
					<button class="close" data-dismiss="alert" type="button">x</button>
					 This Urea Entry UT -<a id="fuelID" href=""></a> created successfully .
				</div>		
						
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
						<input type="hidden" id="tripSheetId" value="${tripSheetId}" >	
						<input type="hidden" id="vehicleId" value="${TripSheet.vid}" >	
						<input type="hidden" id="minOdometer" value="${TripSheet.tripOpeningKM}" >
						<input type="hidden" id="maxOdometer" value="${TripSheet.tripOpeningKM+TripSheet.vehicle_ExpectedOdameter}"  >
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="isNextUreaEntry" >
						<input type="hidden" id="nextUreaEntryId" >
						<div class="form-horizontal ">
							<fieldset>
								<legend>Add Urea Entries</legend>
								<div class="box">
									<div class="box-body">
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label" for="FuelSelectVehicle">Vehicle<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" id="FuelSelectVehicle1" name="vid"
													style="width: 100%;" readonly="readonly" class="form-text"
													 /> <span
													id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vehicle Group :</label> 
											<div class="I-size">

												<input class="form-text" id="vehicle_group" 
													required="required" name="vehicle_group" type="text"
													readonly="readonly">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">TripSheet ID :</label>
											<div class="I-size">
												<div class="input-group">
													<span class="input-group-addon"> <span
														aria-hidden="true">TS-</span></span> <input type="text"
														class="form-text" name="fuel_TripsheetNumber" value="${UreaEntriesInvoice.tripSheetNumber}"
														placeholder="eg: 160" maxlength="8" id="fuel_TripsheetNumber"
														readonly="readonly"
														required="required" />

												</div>

											</div>
										</div>
										<div class="row1" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size"> 
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="fuel_date"
														id="fuelDate" required="required"  onchange="getPreNextUreaEntiresByDate();"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="fuelDateIcon" class=""></span>
												<div id="fuelDateErrorMsg" class="text-danger"></div>
											</div>
										</div>

										<div class="row1" id="grpfuelOdometer" class="form-group">
											<label class="L-size control-label" for="fuel_meter">Odometer<abbr 
												title="required">*</abbr></label>
											<div class="I-size">

											 <input class="form-text"
												id="fuel_meter" name="fuel_meter" type="number" min="0"
												max="" onkeypress="return isNumberKey(event);" onkeyup="validateMaxOdameter();"
												ondrop="return false;" onblur="validateMaxOdameter();"> <span id="fuelOdometerIcon"
												class=""></span>
												<div id="odometerRangeDiv" style="display: none; font-size: 14px;">
													<label id="odometerRange" class="error"> </label> 
												</div>
												<input type="hidden" class="form-text" id="ureaOdometerOld" 
												name="ureaOdometerOld" readonly="readonly">
												<input type="hidden" class="form-text" id="maxQuantity" name="maxQuantity" readonly="readonly">
												<input type="hidden" class="form-text" id="manufacturerId" name="manufacturerId" readonly="readonly">
												<input type="hidden" class="form-text" id="wareHouseLocation" name="wareHouseLocation" readonly="readonly">	
												<input type="hidden" class="form-text" id="vehicle_ExpectedOdameter" name="vehicle_ExpectedOdameter" readonly="readonly">	
											</div>
										</div>
										<div class="row1" id="grpfuelOdometer" class="form-group">
											<label class="L-size control-label" for="fuel_meter">Old Odometer<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input class="form-text" id="fuel_meter_old" name="fuel_meter" onkeyup="validateMaxOdameter();"
													type="number" min="0" max="" onkeypress="return isNumberKey(event);" readonly ="readonly"
													ondrop="return false;" onblur="validateMaxOdameter();"> 
												<span id="fuelOdometerIcon" class=""></span>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">From Location Stock :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="ureaLocation"
													name="driver_id" style="width: 100%;" required="required"
													placeholder="Please Enter Urea Manufacturer name" />
											</div>
										</div>
										
										<div class="row1" id="grpfuelPrice" class="form-group">
											
											<div class="col-md-2" style="padding-left: 220px;">
											<label class="L-size control-label" for="fuel_price">Price/Unit</label>
												<input class="form-text" id="ureaPrice" name="ureaPrice"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;" >
												<p class="help-block">ex: 56.78</p>
											</div>
											<div class="col-md-2">
											<label class="L-size control-label" for="fuel_price">Dis :</label>
												<input class="form-text" id="discount" name="discount"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;">
											</div>
											<div class="col-md-2">
											<label class="L-size control-label" for="fuel_price">GST :</label>
												<input class="form-text" id="gst" name="gst"
													type="text" maxlength="8" min="0" readonly="readonly"
													onkeypress="return isNumberKey(event,this);"
													ondrop="return false;">
											</div>
											
										</div>
										
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Liter
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="ureaLiters" name="ureaLiters"
													type="text" maxlength="8" min="0"  onblur="return validateUreaLiters();"
													onkeypress="return isNumberKeyWithDecimal(event,this.id);" onkeyup="sumthere('ureaLiters','ureaPrice','discount', 'gst','ureaAmount');"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>

											</div>
										</div>
										
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Urea Amount
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="ureaAmount" name="ureaAmount"
													type="text" maxlength="8" min="0" readonly="readonly"
												>
											</div>
										</div>
										
										
										<div class="row1" id="grpfuelReference" class="form-group">
											<label class="L-size control-label" for="fuel_reference">Reference
												:
											</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference" maxlength="50"
													name="fuel_reference" type="text"
													onkeypress="return IsReference(event);"
													ondrop="return false;">
												<p class="help-block">Optional (e.g. invoice number,
													transaction ID, etc.)</p>

											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Driver Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="DriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													readonly="readonly" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Driver2 Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="Driver2Fuel"
													name="secDriverID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Cleaner :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="CleanerFuel"
													name="cleanerID" style="width: 100%;" required="required"
													placeholder="Please Enter 3 or more Driver Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size  control-label">Route Service :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="FuelRouteList" name="routeID"
													style="width: 100%;" value="0" required="required"
													readonly="readonly" />
											</div>
										</div>
								</div>
							</fieldset>

							<fieldset id="grpfuelComment">
								<legend>Comment</legend>
								<div class="box">
									<div class="box-body">
										<div class="col-sm-offset-2">

											<div class="row1">
												<div class="I-size">
													<textarea class="form-text" id="fuel_comments"
														name="fuel_comments" rows="3" maxlength="240">	
												</textarea>
													<label class="error" id="errorComment"
														style="display: none"> </label> <span class="help-block">Add
														an optional comment</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</fieldset>

							<div class="panel-footer">
								<div class="L-size"></div>
								<div class="I-size">
									<div class="col-sm-offset-4 I-size">
										<input type="button" value="Add" onclick="saveTripSheetUreaEntry();" id="btnSubmit" class="btn btn-success" />	
										<a class="btn btn-default" href="showTripSheet.in?tripSheetID=${tripSheetId}">Cancel</a>
									</div>
								</div>
							</div>
						</div>
					  </form>	
					<!-- </form> -->
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaEntriesTripSheetAdd.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaEntriesValidate.js" />"></script>		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#fuel_type").select2({
				placeholder : "Please Enter Vehicle Name Search"
			}), $("#tagPicker").select2({
				closeOnSelect : !1
			});
			
		});
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>