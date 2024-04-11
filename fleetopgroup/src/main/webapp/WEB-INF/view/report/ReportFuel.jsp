<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/Fuel/1.in"/>">Fuel Entry</a> / <span>Search
						Fuel Entry</span>
				</div>
				<div class="col-md-off-5">
					<div class="col-md-3">
						<form action="<c:url value="/searchFuel.in"/>" method="post">
							<div class="input-group">
								<input class="form-text" id="vehicle_registrationNumber"
									name="Search" type="text" required="required"
									placeholder="FT_ID, VID, REF-No, Date"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</form>
					</div>
					<a href="<c:url value="/Fuel/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_FUEL')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Fuel Search</legend>

						<div class="row">

							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="FuelReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Vehicle Name :</label>
													<div class="I-size">

														<input type="hidden" id="FuelSelectVehicle" name="vid"
															style="width: 100%;" required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleOwnership">Vehicle
														Ownership</label>
													<div class="I-size">
														<select style="width: 100%;" id="vehicle_Ownership"
															name="vehicle_OwnershipId" style="width: 100%;">
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
													<label class="L-size control-label" id="F"> Fuel
														Type :</label>
													<div class="I-size">
														<select style="width: 100%;" id="fuel_type"
															name="fuelTypeId" style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${vehiclefuel}" var="vehiclefuel">
																<option value="${vehiclefuel.fid}">
																	<c:out value="${vehiclefuel.vFuel}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Group :</label>
													<div class="I-size">
														<select name="vehicleGroupId" id="vehicle_group"
															style="width: 100%;">
															<option value=""></option>
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																	<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>

														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size">
														<input type="hidden" id="VehicleTODriverFuel"
															name="driver_id" style="width: 100%;" required="required"
															placeholder="Please Enter 3 or more Driver Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vendor Name :</label>
													<div class="I-size">
														<input type="hidden" id="selectVendor2" name="vendor_name"
															style="width: 100%;" required="required"
															placeholder="Please Enter 3 or more Vendor Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vendor Payment
														:</label>
													<div class="I-size">
														<select name="fuel_vendor_paymodeId"
															id="fuel_vendor_paymode" style="width: 100%;">
															<option value=""><!-- please select --></option>
															<option value="1">Cash</option>
															<option value="2">Credit</option>
															<option value="-1">Cash &amp; Credit</option>
														</select>
													</div>
												</div>
												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label"> Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="Fuel_daterange" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>

											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																onclick="this.style.visibility = 'hidden'" name="commit"
																type="submit" value="Search All"> <a
																href="<c:url value="/Fuel/1.in"/>" class="btn btn-info">
																<span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js" />"></script>
	<script type="text/javascript">
	$(document).ready(function(){$("#fuel_vendor_paymode, #vehicle_group, #vehicle_Ownership, #fuel_type").select2({placeholder:"Please Enter"}),$("#tagPicker").select2({closeOnSelect:!1})});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script>
		$(function() {
			function e(e, t) {
				$("#reportrange").val(
						e.format("DD-MM-YYYY") + " to "
								+ t.format("DD-MM-YYYY"))
			}
			e(moment().subtract(1, "days"), moment()), 
			$("#reportrange")
					.daterangepicker(
							{
								maxDate: new Date(),
								format : 'DD-MM-YYYY',
								ranges : {
									Today : [ moment(), moment() ],
									Yesterday : [ moment().subtract(1, "days"),moment().subtract(1, "days") ],
									"Last 7 Days" : [moment().subtract(6, "days"),moment()],
									"This Month": [moment().startOf("month"), moment().endOf("month")],	
									"Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
								}
							}, e)
		})
	</script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>