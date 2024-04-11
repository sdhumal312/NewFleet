<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <span
						id="NewVehi">Search Report</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vehicle/1/1"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Vehicle Search</legend>

						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="vehicleReport" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label"> Status :</label>
													<div class="I-size">
														<input type="hidden" id="VehicleStatusSelect"
															name="vStatusId" style="width: 100%;"
															placeholder="Please Enter 2 or more Status Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label"> Group :</label>
													<div class="I-size">

														<input type="hidden" id="VehicleGroupSelect"
															name="vehicleGroupId" style="width: 100%;"
															placeholder="Please Enter 2 or more Group Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="Type">Type</label>
													<div class="I-size">

														<input type="hidden" id="VehicleTypeSelect"
															name="vehicleTypeId" style="width: 100%;"
															placeholder="Please Enter 2 or more Type Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="Vehicle_Location">
														Location :</label>
													<div class="I-size">
														<input type="hidden" id="VehiclePartlocationSelect"
															name="Vehicle_Location" style="width: 100%;"
															placeholder="Please Enter 2 or more Location Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Ownership :</label>
													<div class="I-size">
														<select class="form-control select2"
															name="vehicleOwnerShipId" style="width: 100%;">
															<option value=""></option>
															<option value="1">Owned</option>
															<option value="2">Leased</option>
															<option value="3">Rented</option>
															<option value="4">Attached</option>
															<option value="5">Customer</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="FuelType">Fuel
														Type</label>
													<div class="I-size">

														<input type="hidden" id="VehicleFuelSelect"
															name="vehicleFuelId" style="width: 100%;"
															placeholder="Please Enter 2 or more Fuel Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="ManufactureYear">Manufacture
														Year</label>
													<div class="I-size">
														<div class="input-group input-append date"
															id="vehicle_year">
															<input class="form-text" id="vehicle_year"
																name="vehicle_year" type="text"
																data-inputmask="'mask': ['9999']" data-mask="">
															<span class="input-group-addon add-on"> <span
																class="fa fa-calendar"></span>
															</span>
														</div>
													</div>
												</div>

												<div class="row1">
													<label class="L-size control-label" id="VehicleMaker">Vehicle
														Maker</label>
													<div class="I-size">
														<input class="form-text" id="vehicle_maker"
															name="vehicle_maker" type="text">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleModel">Vehicle
														Model</label>
													<div class="I-size">
														<input class="form-text" id="vehicle_Model"
															name="vehicle_Model" type="text">

													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="SeatCapacity">Seat
														Capacity</label>
													<div class="I-size">
														<input class="form-text" id="vehicle_seatCapacity"
															name="vehicle_SeatCapacity" type="text" maxlength="10">
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleActype">Vehicle
														A/C Type</label>
													<div class="I-size">
														<label for="vehicle_meter_unit_mi"> <input
															class="radio_buttons required" id="vehicle_ac"
															name="acTypeId" type="radio" value="1"> <span>A/C
														</span>
														</label> <label for="vehicle_meter_unit_mi"> <input
															class="radio_buttons required" id="vehicle_nonac"
															name="acTypeId" type="radio" value="2">
															<span>Non A/C </span>
														</label> <label for="vehicle_meter_unit_mi"> <input
															class="radio_buttons required" id="vehicle_fullac"
															name="acTypeId" type="radio" value="3">
															<span>A/C &amp; Non A/c</span>
														</label>
													</div>
												</div>
												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>
														<div class="I-size">
															<div class="pull-left">
																<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">


																<a href="<c:url value="/vehicle/1/1"/>"
																	class="btn btn-info"> <span id="Can">Cancel</span>
																</a>
															</div>
														</div>
													</div>
												</fieldset>

											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Reportlanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.validate.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAjaxDropDown.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>