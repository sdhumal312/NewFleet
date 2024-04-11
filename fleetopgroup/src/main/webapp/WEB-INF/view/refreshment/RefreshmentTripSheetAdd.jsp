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
							code="label.master.home" /></a> / <a id="tripNumberCol" style="display: none;"
						href="showTripSheet.in?tripSheetID=${tripSheetId}"> Trip Number : TS- ${TripSheet.tripSheetNumber}</a>
						<a id="entry" href="#" style="display: none;">Refreshment Entry</a>
				</div>
				
			</div>
		</div>
	</section>
	<!-- Main content -->

	<section class="content">
	
		<div class="box" id="tripSheetDetails" style="display: none;">
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
												<input type="hidden" id="tripFristDriverLastName" value="${TripSheet.tripFristDriverLastName}">
												<input type="hidden" id="tripFristDriverFatherName" value="${TripSheet.tripFristDriverFatherName}">
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
											value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} -  ${TripSheet.tripFristDriverFatherName}" /></a></li>
								<li>Driver 2 : <a data-toggle="tip"
									data-original-title="Driver 2"><c:out
											value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} ${TripSheet.tripSecDriverFatherName}" /></a></li>
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
					
							<table id="refreshmentTable" class="table table-hover table-bordered" style="display: none;">
								<thead>
									<tr>
										<th class="fit ar">R.NO</th>
										<th class="fit ar">Date</th>
										<th class="fit ar">Quantity</th>
										<th class="fit ar">Returned Qty</th>
										<th class="fit ar">Consumption</th>
										<th class="fit ar">Amount</th>
										<th class="fit ar">Action</th>
									</tr>
								</thead>
								<tbody id="refreshmentBody">
									
								</tbody>
							</table>
					   </div>
					</c:if>
				</div>
				
		<sec:authorize access="!hasAuthority('ADD_REFRESHMENT_INVENTORY')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('ADD_REFRESHMENT_INVENTORY')">
			<div class="row" onload="fuelvehicle()">
				<div class="col-md-offset-1 col-md-9">

				<div class="alert alert-success hide" id="showData" >
					<button class="close" data-dismiss="alert" type="button">x</button>
					 This Urea Entry UT -<a id="fuelID" href=""></a> created successfully .
				</div>		
						
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
						<input type="hidden" id="tripSheetId" value="${tripSheetId}" >	
						<input type="hidden" id="vehicleId" value="${TripSheet.vid}" >
						<input type="hidden" id="locationQuantity" value="0">	
						<div class="form-horizontal ">
							<fieldset>
								<legend>Add Refreshment Entries</legend>
								<div class="box">
									<div class="box-body">
									<div class="row1">
											<label class="L-size control-label"> Vehicle :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3 col-sm-4 col-xs-12">
												<input type="hidden" id="FuelSelectVehicle" name="vid"
													style="width: 100%;" required="required"
													placeholder="Please Enter 2 or more Vehicle Name" /> 
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">
												Date:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="refreshmentDate"  readonly="readonly"
														id="refreshmentDate" required="required" value="${TripSheet.tripOpenDate}"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" 
														  onchange="getTripSheetForDate();" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										</div>
										<div class="row1" id="TripSheetRow" style="display: none;">
											<label class="L-size control-label">TripSheet Number :</label>
											<div class="I-size">
												<div class="input-group">
													<span class="input-group-addon"> <span
														aria-hidden="true">TS-</span></span> 
													<select style="width: 100%;" name="tripSheetNumber" id='tripSheetNumberDrop' class="form-text select2"
													     onchange="setSelectedTripSheetData();" 	required>
													</select>

												</div>
											<div style="display: none;">
												<input type="hidden" id="tripData" name="tripData"
													style="width: 100%;" required="required" /> 
											</div>
											</div>
										</div>	
										<div class="row1">
											<label class="L-size control-label"> Driver :<abbr
												title="required">*</abbr></label>
											<div class="col-md-3 col-sm-4 col-xs-12">
												<input type="hidden" id="DriverFuel"
													name="driver_id" style="width: 100%;" required="required"
													readonly="readonly" />
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">
												Route:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12">
												<input type="hidden" id="FuelRouteList" name="routeID"
													style="width: 100%;" value="0" required="required"
													readonly="readonly" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">From Location :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="locationId"
													name="locationId" style="width: 100%;" required="required"
													placeholder="Please Enter Urea Manufacturer name" />
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Part Name :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="partselect">
												<input type="hidden" id="partid"
													name="partid" style="width: 100%;" required="required"
													placeholder="Please Enter Part name" />
											</div>
										</div>
										
										<div class="row1" id="grpfuelLiter" class="form-group">
											<label class="L-size control-label" for="fuel_liters">Quantity
												:<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input class="form-text" id="quantity" name="quantity"
													type="text" maxlength="8" min="0" onkeyup=" return valadateQuantity();"
													onkeypress="return isNumberKeyWithDecimal(event,this.id); return valadateQuantity();"
													ondrop="return false;">
												<p class="help-block">ex: 23.78</p>

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
													<textarea class="form-text" id="comment"
														name="comment" rows="3" maxlength="240">	
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
										<input type="button" value="Add" onclick="saveRefreshmentEntry();" id="btnSubmit" class="btn btn-success" />	
										<a class="btn btn-default" href="showTripSheet.in?tripSheetID=${tripSheetId}">Cancel</a>
									</div>
								</div>
							</div>
						</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/refreshment/RefreshmentAdd.js" />"></script>	
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