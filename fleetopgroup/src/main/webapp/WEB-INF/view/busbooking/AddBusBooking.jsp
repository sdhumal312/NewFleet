<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/viewBusBookingDetails.in"/>">Bus Booking</a> / <span
						id="NewVehi">Add Bus Booking</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/viewBusBookingDetails.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Already Exists
		</div>
	</c:if>
	<section class="content">
		<div class="row">
				<div class="col-md-offset-1 col-md-9">
						<div class="form-horizontal ">
							<fieldset>
								<div class="box">
									<div class="box-body">
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
							<input type="hidden" id="validateRate" value="${configuration.validateRate}">
							<input type="hidden" id="validateBookingRef" value="${configuration.validateBookingRef}">
							
								<div class="row1" id="busBookingRefRow" style="display: none">
									<label class="L-size control-label">Booking Ref :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" name="bookingRefNumber"
											id="bookingRefNumber" class="form-text" />
									</div>
								</div>

								<div class="row1" id="busBookingDateRow" style="display: none;">
											<label class="L-size control-label">Booking Date :</label>
											 <div class="I-size">
												<div class="input-group input-append date" id="datePicker">
													<input type="text" class="form-text" name="busBookingDate"
														id="busBookingDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
													<span class="fa fa-calendar"></span>
												  </span>
												</div>
											</div>
										</div>		
											
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label"
												for="SelectVehicle">Party Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="partyId" name="partyId"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Party Name" />
											</div>
										</div>	
										
										<div class="row1" id="partyGstRow" style="display: none;">
											<label class="L-size control-label">GST No :</label>
											<div class="I-size">
												<input type="text"  name="partyGSTNo"
													id="partyGSTNo" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="partyMobRow" style="display: none;">
											<label class="L-size control-label">Mobile No :</label>
											<div class="I-size">
												<input type="number"  name="partyMobileNumber" maxlength="12"
													id="partyMobileNumber" class="form-text" onkeypress="return isNumberKey(event,this);"/>
											</div>
										</div>	
										<div class="row1" id="partyAddRow" style="display: none;">
											<label class="L-size control-label">Party Address :</label>
											<div class="I-size">
												<input type="text"  name="partyAddress"
													id="partyAddress" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="reportToRow" style="display: none;">
											<label class="L-size control-label">Report To :</label>
											<div class="I-size">
												<input type="text"  name="reportToName"
													id="reportToName" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="reportToMobRow" style="display: none;">
											<label class="L-size control-label">Report To Number :</label>
											<div class="I-size">
												<input type="number"  name="reportToMobileNumber" maxlength="12"
													id="reportToMobileNumber" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="reportToAddRow" style="display: none;">
											<label class="L-size control-label">Report To Address :</label>
											<div class="I-size">
												<input type="text"  name="billingAddress"
													id="billingAddress" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="vehicleTypeRow" style="display: none;">
											<label class="L-size control-label">Vehicle Type :</label>
											<div class="I-size">
												<input type="text"  name="vehicleTypeId"
													id="vehicleTypeId" class="form-text" />
											</div>
										</div>	
										
										<div class="row1" id="grpwoEndDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">
												Start Date <abbr title="required">*</abbr> </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" readonly="readonly" name="tripStartDate"
														id="tripStartDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										
												<div class="L-size">
													<label class="L-size control-label" for="fuelDate">Time
														<abbr title="required">*</abbr>
													</label>
														<div class="input-group clockpicker">
															<input type="text" class="form-text"
																name="startTime" id="startTime" required="required" readonly="readonly"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
												</div>
										</div>
										
										<div class="row1" id="grpwoEndDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">
												Trip End Date <abbr title="required">*</abbr> </label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="renewal_from">
													<input type="text" class="form-text" readonly="readonly" name="tripEndDate"
														id="tripEndDate" data-inputmask="'alias': 'dd-mm-yyyy'"
														data-mask="" /> <span class="input-group-addon add-on">
														<span class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
										
												<div class="L-size">
													<label class="L-size control-label" for="fuelDate">Time
														<abbr title="required">*</abbr>
													</label>
														<div class="input-group clockpicker">
															<input type="text" class="form-text"
																name="endTime" id="endTime" required="required" readonly="readonly"> <span
																class="input-group-addon"> <span
																class="fa fa-clock-o" aria-hidden="true"></span>
															</span>
														</div>
												</div>
										</div>
									
									<div class="row1" id="placeOfVisitRow" style="display: none;">
											<label class="L-size control-label">Place Of Visit :</label>
											<div class="I-size">
												<input type="text"  name="placeOfVisit"
													id="placeOfVisit" class="form-text" />
											</div>
										</div>	
										<div class="row1">
											<label class="L-size control-label">Pick Up Address :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text"  name="pickUpAddress"
													id="pickUpAddress" class="form-text" />
											</div>
										</div>	
										<div class="row1">
											<label class="L-size control-label">Drop Address :</label>
											<div class="I-size">
												<input type="text"  name="dropAddress"
													id="dropAddress" class="form-text" />
											</div>
										</div>	
										<div class="row1" id="rateRow" style="display: none;">
											<label class="L-size control-label">Per KM Rate :</label>
											<div class="I-size">
												<input type="number"  name="rate"
													id="rate" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,id);" />
											</div>
										</div>
										<div class="row1" id="hireAmountRow" style="display: none;">
											<label class="L-size control-label">Hire Amount :</label>
											<div class="I-size">
												<input type="number"  name="hireAmount"
													id="hireAmount" class="form-text" />
											</div>
										</div>
										
										
										<c:if test="${!configuration.busBookedBy}">
										<div class="row1">
											<label class="L-size control-label">Remark :</label>
											<div class="I-size">
												<input type="text"  name="remark"
													id="remark" class="form-text" />
											</div>
										</div>
										</c:if>	
										<c:if test="${configuration.busBookedBy}">
										<div class="row1">
										<label class="L-size control-label">Booked by :</label>
										<div class="I-size">
												<input type="text"  name="bookedBy"
													id="bookedBy" class="form-text" />
											</div>
										</div>
										</c:if>
									</div>
								</div>
							</fieldset>

							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="button" class="btn btn-success" onclick="saveBusBookingDetails();">Save</button>
										<a class="btn btn-link"
											href="<c:url value="/viewBusBookingDetails.in"/>">Cancel</a>
									</div>
								</div>
							</fieldset>
						</div>
				</div>
			</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/busbooking/AddBusBookingDetails.js" />"></script>	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />">
		
		</script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
		
		
		<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask();
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
			
			
			if(${configuration.showBookingDate}){
				$('#busBookingDateRow').show();
			}
			if(${configuration.showBookingRef}){
				$('#busBookingRefRow').show();
			}
			if(${configuration.showGstNumber}){
				$('#partyGstRow').show();
			}
			if(${configuration.showMobileNumber}){
				$('#partyMobRow').show();
			}
			if(${configuration.showPartyAddress}){
				$('#partyAddRow').show();
			}
			if(${configuration.showReportTo}){
				$('#reportToRow').show();
			}
			if(${configuration.showReportToAddress}){
				$('#reportToAddRow').show();
			}
			if(${configuration.showReportToMob}){
				$('#reportToMobRow').show();
			}
			if(${configuration.showVehicleType}){
				$('#vehicleTypeRow').show();
			}
			if(${configuration.showPlaceOfVisit}){
				$('#placeOfVisitRow').show();
			}
			if(${configuration.showPerKMRate}){
				$('#rateRow').show();
			}
			if(${configuration.showHireAmount}){
				$('#hireAmountRow').show();
			}
		});
	</script>
</div>
