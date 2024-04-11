<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <span> Report </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/open"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">

		<div class="row">
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-bus"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/VR"/>">Vehicle Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-user"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/DR"/>">Driver Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-bell"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/RRR"/>">Renewal Reminder Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="row">

			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-bell-slash"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/SRR"/>">Service Reminder Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon"><i class="fa fa-tint"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/FR"/>">Fuel Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon"><i class="fa fa-bell-slash"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/SER"/>">Service Entries Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i
						class="fa fa-exclamation-circle"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/IR"/>">Issues Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-file-text-o"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/TSR"/>">Trip sheet Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon"><i class="fa fa-file-text-o"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/TCR"/>">Trip collection Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon"><i class="fa fa-shopping-cart"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/POR"/>">PurchaseOrder Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-cubes"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/PR"/>">Part Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-circle-o"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/TR"/>">Tyre Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>

		</div>
		<br>
		<div class="row">
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-file-text-o"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/WR"/>">WorkOrder Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-5 col-xs-12">
				<div class="info-box ">
					<span class="info-box-icon"><i class="fa fa-book"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/CBR"/>">CashBook Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 col-sm-5 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon"><i class="fa fa-file-text-o"></i></span>
					<div class="info-box-content">
						<span class="info-box-number"><a href="<c:url value="/AR"/>">Attendance Report</a></span>
						<div class="progress">
							<div class="progress-bar" style="width: 0%"></div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-12">
				<div class="box">
					<div class="box-body">
						<ul class="nav nav-pills nav-stacked" id="myTabs" data-spy="affix"
							data-offset-top="55">
							<li class="active"><a href="#vehicleReport"
								data-toggle="pill">Vehicle Report</a></li>
							<li><a href="#driverReport" data-toggle="pill">DriverReport
							</a></li>
							<li><a href="#FuelReport" data-toggle="pill">Fuel Report</a></li>
							<li><a href="#RRReport" data-toggle="pill">Renewal
									Reminder Report </a></li>
							<li><a href="#SRReport" data-toggle="pill">Service
									Reminder Report </a></li>
							<li><a href="#SEReport" data-toggle="pill">Service
									Entries Report</a></li>
							<li><a href="#IssuesReport" data-toggle="pill">Issues
									Report</a></li>
							<li><a href="#TSReport" data-toggle="pill">Trip sheet
									Report</a></li>
							<li><a href="#TCReport" data-toggle="pill">Trip
									collection Report</a></li>
							<li><a href="#InventoryReport" data-toggle="pill">Inventory
									Report</a></li>
							<li><a href="#POReport" data-toggle="pill">PurchaseOrder
									Report</a></li>
							<li><a href="#WOReport" data-toggle="pill">WorkOrder
									Report</a></li>
							<li><a href="#CBReport" data-toggle="pill">CashBook
									Report</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-8 col-sm-8 col-xs-12">

				<div class="tab-content">
					<!--  vehicle Report -->
					<div class="tab-pane active" id="vehicleReport">

						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne"> Vehicle Wise Repair Report </a>
								</h4>
							</div>
							<div id="collapseOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="RepairReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="ReportSelectVehicle"
														name="repair_vid" style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>
												</div>
											</div>
											<!-- service Type -->
											<div class="row1">
												<label class="L-size control-label">Service Type <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text select2" name="repair_servicetype"
														id="renPT_option" required="required">
														<option value="WORK_ORDER">Work Orders</option>
														<option value="SERVICE_ENTRIES">Service Entries</option>
														<option value="ALL">All</option>
													</select>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="reportrange" class="form-text"
															name="repair_daterange" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
									<!-- end Repair Report -->
								</div>
							</div>
						</div>
					</div>
					<!-- Driver Report -->
					<div class="tab-pane" id="driverReport">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#driverOne"> Driver details Report </a>
								</h4>
							</div>
							<div id="driverOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="DriverDetailsReport" method="post">
										<div class="form-horizontal ">
											<!-- Driver Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group :</label>
												<div class="I-size">
													<input type="hidden" id="driverGroup" name="driver_group"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>
											<!-- Driver Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Job Title :</label>
												<div class="I-size">
													<input type="hidden" id="SelectDriverJob"
														name="driver_jobtitle" style="width: 100%;"
														placeholder="Please Enter 2 or more Job Type" />
												</div>
											</div>
											<div class="row1">
												<label class="string required L-size control-label">Languages
													: </label>
												<div class="I-size">
													<select id="DriverLanguage" name="driver_languages"
														multiple="multiple" data-placeholder="Select a languages"
														style="width: 100%;">
														<option>English</option>
														<option>Hindi</option>
														<option>Tamil</option>
														<option>Kannada</option>
														<option>Telugu</option>
														<option>Marathi</option>
														<option>Malayalam</option>
														<option>Odia</option>
														<option>Gujarati</option>
														<option>Punjabi</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Training /
													Specialization:</label>
												<div class="I-size">
													<input type="hidden" id="SelectDriverTraining"
														name="driver_trainings" style="width: 100%;"
														placeholder="Please Enter 2 or more Training Name" />

												</div>
											</div>



											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Fuel Report -->
					<div class="tab-pane" id="FuelReport">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTwo"> Vehicle Wise Fuel Mileage Report </a>
								</h4>
							</div>
							<div id="collapseTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="FuelMileageReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="ReportSelectVehicleMileage"
														name="fuelmileage_vid" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>

												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="rangeFuelMileage" class="form-text"
															name="fuelmileage_daterange" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseThree"> Fuel Range Report </a>
								</h4>
							</div>
							<div id="collapseThree" class="panel-collapse collapse">
								<div class="box-body">

									<div class="form-horizontal ">

										<!--  Select Type Search -->
										<div class="row1">
											<label class="L-size control-label"> Search By</label>
											<div class="I-size">
												<label><input type="radio" name="SearchRadio"
													value="VEHICLE"> Vehicle</label> <label><input
													type="radio" name="SearchRadio" value="GROUP">
													Group </label>

											</div>
										</div>

										<!-- Show Vehicle Search Fuel Range -->
										<div class="VEHICLE HideRadioBox">
											<form action="VehicleFuelRange" method="post">
												<!-- vehicle Select -->
												<div class="row1">
													<label class="L-size control-label">Vehicle Name <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="ReportVehicleFuelRange"
															name="fuelRange_vid" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Or More Vehicle</p>

													</div>
												</div>
												<!-- Fuel range Select -->
												<div class="row1">
													<label class="L-size control-label">Fuel range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text" class="selectpicker"
															name="fuelRange">
															<option value="BELOW"
																style="color: red; font-size: 19px;">Below the
																Range</option>
															<option value="WITH_IN"
																style="color: #1FB725; font-size: 19px;">With
																in the Range</option>
															<option value="OUT_OF"
																style="color: blue; font-size: 19px;">Out of
																Range</option>

														</select>
													</div>
												</div>

												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="VehicleFuelRange"
																class="form-text" name="fuelRange_daterange"
																required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>

												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit"
																	class="btn btn-success">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
												</fieldset>
											</form>
										</div>
										<!-- end Show Vehicle Search Fuel Range -->
										<!-- Show Group Search Fuel Range -->
										<div class="GROUP HideRadioBox">
											<form action="GroupFuelRange" method="post">
												<!-- vehicle Group Service -->
												<div class="row1">
													<label class="L-size control-label"> Group :</label>
													<div class="I-size">
														<input type="hidden" id="SelectVehicleGroup"
															name="fuelRange_group" style="width: 100%;"
															placeholder="Please Enter 2 or more Group Name" />
													</div>
												</div>
												<!-- Fuel range Select -->
												<div class="row1">
													<label class="L-size control-label">Fuel range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text" name="fuelRange"
															id="renPT_option">
															<option value="BELOW"
																style="color: red; font-size: 19px;">Below the
																Range</option>
															<option value="WITH_IN"
																style="color: #1FB725; font-size: 19px;">With
																in the Range</option>
															<option value="OUT_OF"
																style="color: blue; font-size: 19px;">Out of
																Range</option>

														</select>
													</div>
												</div>

												<!-- Date range -->
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="GroupFuelRange" class="form-text"
																name="fuelRange_daterange" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>

												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit"
																	class="btn btn-success">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
												</fieldset>

											</form>
										</div>
										<!-- end Show Group Search Fuel Range -->

									</div>
								</div>
							</div>
						</div>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseFour"> Daily Fuel Consumption Report </a>
								</h4>
							</div>
							<div id="collapseFour" class="panel-collapse collapse">
								<div class="box-body">
									<form action="DailyFuelConsumption" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="SelectFuelGroup"
														name="vehicle_group" style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>
											<!-- vehicle Ownership -->
											<div class="row1">
												<label class="L-size control-label" id="VehicleGroup">
													Ownership :</label>
												<div class="I-size">
													<select class=" select2" id="vehicle_Ownership"
														name="vehicle_Ownership" style="width: 100%;">
														<option value=""></option>
														<option value="Owned">Owned</option>
														<option value="Leased">Leased</option>
														<option value="Rented">Rented</option>
														<option value="Attached">Attached</option>
														<option value="Customer">Customer</option>
														<option value="All">All</option>
													</select>
												</div>
											</div>
											<!-- Search Vendor Name -->
											<div class="row1">
												<label class="L-size control-label">Vendor Name :</label>
												<div class="I-size">

													<input type="hidden" id="selectVendor" name="vendor_name"
														style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Vendor Name" />
												</div>
											</div>
											<!-- Payment Type -->
											<div class="row1">
												<label class="L-size control-label">Payment Type :</label>
												<div class="I-size">
													<select name="fuel_vendor_paymode" id="fuel_vendor_paymode"
														style="width: 100%;">
														<option value=""><!-- please select --></option>
														<option value="PAID">Cash</option>
														<option value="NOTPAID">Credit</option>
														<option value="ALL">ALL</option>
													</select>
												</div>
											</div>

											<!-- Date range -->

											<div class="row1">
												<label class="L-size control-label">Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="ReportDailydate">
														<input type="text" class="form-text" name="fuel_to"
															required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>

											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Renewal Reminder -->
					<div class="tab-pane" id="RRReport">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#RROne">Renewal Reminder Compliance Report </a>
								</h4>
							</div>
							<div id="RROne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="RRComplianceReport" method="post">
										<div class="form-horizontal ">
											<!-- Renewal Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Type <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="from" name="renewal_type"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Renewal Name" />
												</div>
											</div>
											<!-- Renewal Sub Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Sub Type
													<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select style="width: 100%;" name="renewal_subtype" id='to'
														required>

													</select>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="RenewalComRange" class="form-text"
															name="RR_COMPLIANCE_DATE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#RRCompTwo">Total Vehicle Wise Compliance Report </a>
								</h4>
							</div>
							<div id="RRCompTwo" class="panel-collapse collapse">
								<div class="box-body">

									<div class="form-horizontal ">
										<!-- Show Vehicle Search Fuel Range -->
										<form action="RRVehicleComplianceReport" method="post">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="ReportRRCRange" name="RR_VID"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>
												</div>
											</div>
											<!-- Renewal Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Type <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="fromRRV" name="renewal_type"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Renewal Name" />
												</div>
											</div>
											<!-- Renewal Sub Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Sub Type
													<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select style="width: 100%;" name="renewal_subtype"
														id='toRRV' required>

													</select>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="RenewalComRange" class="form-text"
															name="RR_COMPLIANCE_DATE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
										<!-- end Show Vehicle Search RR Range -->


									</div>
								</div>
							</div>
						</div>
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#RRCompThree">Total Group Wise Compliance Report </a>
								</h4>
							</div>
							<div id="RRCompThree" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="RRGroupComplianceReport" method="post">
											<!-- vehicle Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="VehicleGroupRRG" name="RR_GROUP"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>
											<!-- Renewal Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Type <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="fromRRG" name="renewal_type"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Renewal Name" />
												</div>
											</div>
											<!-- Renewal Sub Type Select -->
											<div class="row1">
												<label class="L-size control-label">Renewal Sub Type
													<abbr title="required">*</abbr>
												</label>

												<div class="I-size">
													<select style="width: 100%;" name="renewal_subtype"
														id='toRRG' required>

													</select>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="RenewalComRangeRRG"
															class="form-text" name="RR_COMPLIANCE_DATE"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Service Reminder Report -->
					<div class="tab-pane" id="SRReport">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#SROne">Overdue Service Reminder Report </a>
								</h4>
							</div>
							<div id="SROne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="OverdueServiceReport" method="post">
										<div class="form-horizontal ">

											<div class="row1">
												<label class="L-size control-label">Vehicle Name :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="OverOverSRVehicle" name="vid"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Service Jobs :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="OverOverSRJOB"
															name="service_type" style="width: 100%;"
															placeholder="Please Enter 2 or more Job Name" />
														<p class="help-block">Select an existing Service Jobs</p>
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Service Sub Jobs
													: </label>
												<div class="I-size">

													<div class="col-md-9">
														<select style="width: 100%;" name="service_subtype"
															id='OverOverSRSubJOB'>

														</select>
														<p class="help-block">Select an existing Service Sub
															Jobs</p>
													</div>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="SROverdueDate">
														<input type="text" class="form-text" name="SERVICE_DATE"
															placeholder="yyyy-mm-dd" required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>

						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#SRTwo"> DueSoon Service Reminder Report </a>
								</h4>
							</div>
							<div id="SRTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="DuesoonServiceReport" method="post">
										<div class="form-horizontal ">
											<!--  Issues Type -->

											<div class="row1">
												<label class="L-size control-label">Vehicle Name :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="DuesoonSRVehicle" name="vid"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Service Jobs :</label>
												<div class="I-size">
													<div class="col-md-9">
														<input type="hidden" id="DuesoonSRJOB" name="service_type"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Job Name" />
														<p class="help-block">Select an existing Service Jobs</p>
													</div>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Service Sub Jobs
													: </label>
												<div class="I-size">

													<div class="col-md-9">
														<select style="width: 100%;" name="service_subtype"
															id='DuesoonSRSubJOB'>

														</select>
														<p class="help-block">Select an existing Service Sub
															Jobs</p>
													</div>
												</div>
											</div>

											<!-- Date range -->

											<div class="row1">
												<label class="L-size control-label">Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="SRDuesoondate">
														<input type="text" class="form-text" name="SERVICE_DATE"
															required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>

											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Service Entries Reminder Report -->
					<div class="tab-pane" id="SEReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#SEOne"> Vendor Wise Service Entries Report </a>
								</h4>
							</div>
							<div id="SEOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="VendorServiceEntriesReport" method="post">
										<div class="form-horizontal ">
											<!--  Vendor Type -->

											<div class="row1">
												<label class="L-size control-label">Vendor :<abbr
													title="required">*</abbr></label>
												<div class="I-size" id="vendorSelect">
													<input type="hidden" id="ServiceVendorList"
														name="VENDOR_ID" required="required" style="width: 100%;"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>

												</div>

											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="SEdateRange" class="form-text"
															name="SERVICE_DATERANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#SETwo"> Vehicle Wise Service Entries Report </a>
								</h4>
							</div>
							<div id="SETwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="VehicleServiceEntriesReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="SEVehicle" name="VEHICLE_ID"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="SEVehdateRange" class="form-text"
															name="SERVICE_DATERANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Issues Report -->
					<div class="tab-pane" id="IssuesReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#IssuesOne"> Issues Daily Status Report </a>
								</h4>
							</div>
							<div id="IssuesOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="IssuesDailyStatusReport" method="post">
										<div class="form-horizontal ">
											<!--  Issues Type -->
											<div class="row1">
												<label class="L-size control-label">Issues Type :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<select name="ISSUES_TYPE" id="IssuesType"
														style="width: 100%;">
														<option value="VEHICLE_ISSUE">Vehicle Issue</option>
														<option value="DRIVER_ISSUE">Driver Issue</option>
														<option value="OTHER_ISSUE">Other Issue</option>
														<option value="ALL">ALL</option>
													</select>
												</div>
											</div>
											<!-- Issues Status -->
											<div class="row1">
												<label class="L-size control-label">Issues Status :</label>
												<div class="I-size">
													<select name="ISSUES_STATUS" id="fuel_vendor_paymode"
														style="width: 100%;">
														<option value="ALL">ALL</option>
														<option value="OPEN">OPEN</option>
														<option value="RESOLVED">RESOLVED</option>
														<option value="REJECT">REJECT</option>
														<option value="CLOSED">CLOSED</option>
													</select>
												</div>
											</div>

											<!-- Date range -->

											<div class="row1">
												<label class="L-size control-label">Reported Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="IssuesDailydate">
														<input type="text" class="form-text"
															name="ISSUES_REPORTED_DATE" required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>

											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>

						<!-- Start Reported Issues Report -->
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#IssuesTwo"> Issues Reported Report </a>
								</h4>
							</div>
							<div id="IssuesTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="IssuesReportedReport" method="post">
										<div class="form-horizontal ">

											<div class="row1">
												<label class="L-size control-label"> Reported By : </label>
												<div class="I-size">
													<input class="" placeholder="Plese Select."
														id="subscribeReport" type="hidden" style="width: 100%"
														name="ISSUES_REPORTED_BY">
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label"> Assigned To : </label>
												<div class="I-size">
													<input class="" placeholder="Plese Select."
														id="subscribeAssign" type="hidden" style="width: 100%"
														name="ISSUES_ASSIGN_TO">
												</div>
											</div>
											<!--  Issues Type -->
											<div class="row1">
												<label class="L-size control-label">Issues Type :<abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<select name="ISSUES_TYPE" id="fuel_vendor_paymode"
														style="width: 100%;">
														<option value="ALL">ALL</option>
														<option value="VEHICLE_ISSUE">Vehicle Issue</option>
														<option value="DRIVER_ISSUE">Driver Issue</option>
														<option value="OTHER_ISSUE">Other Issue</option>
													</select>
												</div>
											</div>
											<!-- Issues Status -->
											<div class="row1">
												<label class="L-size control-label">Issues Status :</label>
												<div class="I-size">
													<select name="ISSUES_STATUS" id="fuel_vendor_paymode"
														style="width: 100%;">
														<option value="ALL">ALL</option>
														<option value="OPEN">OPEN</option>
														<option value="RESOLVED">RESOLVED</option>
														<option value="REJECT">REJECT</option>
														<option value="CLOSED">CLOSED</option>
													</select>
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="IssuesReportedRange"
															class="form-text" name="ISSUES_REPORTED_DATE"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>



					</div>
					<!-- Trip sheet Report -->
					<div class="tab-pane" id="TSReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripsheetOne"> Trip sheet Advances Report </a>
								</h4>
							</div>
							<div id="TripsheetOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="TripSheetAdvanceReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Advance User
													Name <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input class="" placeholder="Advance users" id="subscribe"
														type="hidden" style="width: 100%" name="TRIP_USER"
														onkeypress="return Isservice_subscribeduser(event);"
														required="required" ondrop="return false;">
													<p class="help-block">Select One Or More User name</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="TSAVANCEDailydate">
														<input type="text" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
									<!-- end Repair Report -->
								</div>
							</div>
						</div>
					</div>
					<!-- Trip sheet Report -->
					<div class="tab-pane" id="TCReport">

						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionOne"> Daily Trip Collection Report </a>
								</h4>
							</div>
							<div id="TripCollectionOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="DailyTripCollectionReport" method="post">
										<div class="form-horizontal ">
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="TCDailydate">
														<input type="text" class="form-text" name="TRIP_DATE"
															required="required"
															data-inputmask="'alias': 'yyyy-mm-dd'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>

											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionTwo"> Vehicle Wise Trip Collection
										Report </a>
								</h4>
							</div>
							<div id="TripCollectionTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="VehicleWiseTripColReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="TCVehicle" name="VEHICLE_ID"
														style="width: 100%;" required="required" value="0"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="VTC_daterange" class="form-text"
															name="VEHICLE_TC_DATERAGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
									<!-- end Repair Report -->
								</div>
							</div>
						</div>
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionThree">Group Wise Trip Collection
										Report </a>
								</h4>
							</div>
							<div id="TripCollectionThree" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search GroupWiseTripColReport -->
										<form action="GroupWiseTripColReport" method="post">
											<!-- vehicle Group Service -->
											<div class="row1">
												<label class="L-size control-label"> Group : <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<input type="hidden" id="TCGroupWise" name="TRIP_GROUP"
														style="width: 100%;" value="ALL"
														placeholder="Please Enter 2 or more Group Name" />
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="GTC_daterange" class="form-text"
															name="VEHICLE_TC_DATERAGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
								</div>
							</div>
						</div>
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionFour">Date Range Wise Trip Collection
										Report </a>
								</h4>
							</div>
							<div id="TripCollectionFour" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="DateWiseTripColReport" method="post">

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="DRTC_daterange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionFive">Driver JAMA Collection Report </a>
								</h4>
							</div>
							<div id="TripCollectionFive" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="DriverJamaTripColReport" method="post">

											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Driver
													: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="JAMAdriverList"
														name="TRIP_DRIVER_ID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" /> <label id="errorDriver1" class="error"></label>
												</div>
											</div>
											<!-- <div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cleaner
													:</label>
												<div class="I-size" >
													<input type="hidden" id="JAMACleaner" name="TRIP_CLEANER_ID"
														style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Cleaner Name, No" />
													<label id="errorCleaner" class="error"></label>
												</div>
											</div> -->
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="DJDateRange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionSIX">Conductor JAMA Collection Report
									</a>
								</h4>
							</div>
							<div id="TripCollectionSIX" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="ConductorJamaTripColReport" method="post">

											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Conductor:

												</label>
												<div class="I-size">
													<input type="hidden" id="JAMAConductorList"
														name="TRIP_CONDUCTOR_ID" style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Conductor Name, NO" />
													<label id="errorDriver2" class="error"></label>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="CJDateRange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>


											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</form>
										<!-- end Show Group Search RR Range -->
									</div>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionSEVEN">Driver Advance JAMA Report </a>
								</h4>
							</div>
							<div id="TripCollectionSEVEN" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="AdvanceJamaReport" method="post">

											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Driver:
												</label>
												<div class="I-size">
													<input type="hidden" id="DriverAdList"
														name="TRIP_DRIVER_ID" style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Driver Name, NO" /> <label
														id="errorDriver2" class="error"></label>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="DriAdvDateRange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#TripCollectionEIGHT">Conductor Advance JAMA Report
									</a>
								</h4>
							</div>
							<div id="TripCollectionEIGHT" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Group Search Fuel Range -->
										<form action="AdvanceJamaReport" method="post">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Conductor:
												</label>
												<div class="I-size">
													<input type="hidden" id="ConductorAdList"
														name="TRIP_DRIVER_ID" style="width: 100%;" value="0"
														placeholder="Please Enter 3 or more Conductor Name, NO" />
													<label id="errorDriver2" class="error"></label>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="ConAdvDateRange" class="form-text"
															name="TRIP_DATE_RANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- Inventory Report -->
					<div class="tab-pane" id="InventoryReport">
						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#PartOne">Part Stock Report </a>
								</h4>
							</div>
							<div id="PartOne" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Vehicle Search Fuel Range -->
										<form action="PartStockReport" method="post">
											<!--  Search Parts Number -->
											<div class="row1">
												<label class="L-size control-label">Search Parts
													Number : </label>
												<div class="I-size">
													<input type="hidden" id="searchpart" name="partid"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Part Name or Part Number" />
												</div>
											</div>
											<!-- location -->
											<div class="row1">
												<label class="L-size control-label">Warehouse
													location : </label>
												<div class="I-size">
													<input type="hidden" name="location"
														id="Partwarehouselocation" style="width: 100%;"
														placeholder="Please Enter 2 or more location Name" />
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
										<!-- end Show Vehicle Search RR Range -->


									</div>
								</div>
							</div>
						</div>

						<div class="panel box box-success">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#PartTwo">Part Purchase Report </a>
								</h4>
							</div>
							<div id="PartTwo" class="panel-collapse collapse">
								<div class="box-body">
									<div class="form-horizontal ">
										<!-- Show Vehicle Search Fuel Range -->
										<form action="PartPurchaseReport" method="post">
											<!--  Search Parts Number -->
											<div class="row1">
												<label class="L-size control-label">Search Parts
													Number : </label>
												<div class="I-size">
													<input type="hidden" id="searchpartPO" name="partid"
														style="width: 100%;"
														placeholder="Please Enter 2 or more Part Name or Part Number" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Part Vendor :<abbr
													title="required">*</abbr></label>
												<div class="I-size" id="vendorSelect">
													<input type="hidden" id="PartVendorList" name="vendor_id"
														required="required" style="width: 100%;"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>

												</div>

											</div>
											<!-- location -->
											<div class="row1">
												<label class="L-size control-label">Warehouse
													location : </label>
												<div class="I-size">
													<input type="hidden" name="location" id="PartlocationPO"
														style="width: 100%;"
														placeholder="Please Enter 2 or more location Name" />
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="PartInventryRange"
															class="form-text" name="PART_RANGE_DATE"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
						<!-- Start Show Group Search Tyre Range -->
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTyrePurchase"> Tyre Purchase Report </a>
								</h4>
							</div>
							<div id="collapseTyrePurchase" class="panel-collapse collapse">
								<div class="box-body">
									<form action="TyrePurchaseReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Tyre Model : </label>
												<div class="I-size">
													<input type="text" id="Reporttyremodel" name="TYRE_MODEL"
														style="width: 100%;"
														placeholder="Please select Tyre Model" />

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Size : </label>
												<div class="I-size">
													<input type="text" id="ReporttyreSize" name="TYRE_SIZE"
														style="width: 100%;" placeholder="Please select Tyre Size" />
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Tyre Date range:
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="rangeTyrePurchase"
															class="form-text" name="TyrePurchase_daterange"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- end Show Group Search Tyre Range -->
						<!-- Start Type Stock Tyre Range -->
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTyreStock"> Tyre Stock Report </a>
								</h4>
							</div>
							<div id="collapseTyreStock" class="panel-collapse collapse">
								<div class="box-body">
									<form action="TyreStockReport.in" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Tyre
													Manufacturer : </label>
												<div class="I-size">
													<input type="hidden" id="manufacurer"
														name="TYRE_MANUFACTURER" style="width: 100%;"
														placeholder="Please Enter 2 or more Tyre Manufacturer Name" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Model : </label>
												<div class="I-size">
													<input type="text" id="tyremodelstock" name="TYRE_MODEL"
														style="width: 100%;"
														placeholder="Please select Tyre Model" />

												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Size : </label>
												<div class="I-size">
													<input type="text" id="tyreSizeStock" name="TYRE_SIZE"
														style="width: 100%;" placeholder="Please select Tyre Size" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Warehouse
													location : </label>
												<div class="I-size">
													<input type="hidden" name="WAREHOUSE_LOCATION"
														id="warehouselocation" style="width: 100%;"
														placeholder="Please Enter 2 or more location Name" />
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Status :</label>
												<div class="I-size">
													<select class="select2" name="TYRE_ASSIGN_STATUS"
														id="TyreStatus" style="width: 100%;">
														<option value="">-- Please select --></option>
														<option value="AVAILABLE">AVAILABLE</option>
														<option value="IN SERVICE">IN SERVICE</option>
														<option value="SENT-RETREAD">SENT-RETREAD</option>
														<option value="SCRAPED">SCRAPED</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Type type : </label>
												<div class="I-size">
													<select class="select2" name="TYRE_RETREAD_COUNT"
														id="TyreType" style="width: 100%;">
														<option value="">-- Please select --></option>
														<option value="0">NEW TYRE (NT)</option>
														<option value="1">1 RETRED TYRE (1RT)</option>
														<option value="2">2 RETRED TYRE (2RT)</option>
														<option value="3">3 RETRED TYRE (3RT)</option>
													</select>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- end Show Group Search Tyre Range -->
						<!-- Start Type Stock Tyre Range -->
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTyreStatus"> Tyre Retread Report </a>
								</h4>
							</div>
							<div id="collapseTyreStatus" class="panel-collapse collapse">
								<div class="box-body">
									<form action="TyreRetreadStockReport.in" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label">Type Retread
													Status : </label>
												<div class="I-size">
													<select class="select2" name="TYRE_STATUS"
														id="TyreRetreadStatus" style="width: 100%;">
														<option value="">-- Please select --></option>
														<option value="AVAILABLE">AVAILABLE</option>
														<option value="SENT-RETREAD">SENT-RETREAD</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Retread : </label>
												<div class="I-size">
													<select class="select2" name="RETREAD_STATUS"
														id="TyreRetread" style="width: 100%;">
														<option value="">-- Please select --></option>
														<option value="RECEIVED">RECEIVED TYRE</option>
														<option value="REJECT">REJECT TYRE</option>
													</select>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Tyre Vendor : </label>
												<div class="I-size">
													<input type="hidden" id="TyreRetreadVendor"
														name="TR_VENDOR_ID" style="width: 100%;"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Tyre Retread
													Date range: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="TyreRetreadRange" class="form-text"
															name="TyreRetread_daterange" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- end Show Tyre Retread Range -->
					</div>
					<!-- 	Purchase Order Report -->
					<div class="tab-pane" id="POReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#PurchaseOne"> Vendor Wise Purchase Order Report </a>
								</h4>
							</div>
							<div id="PurchaseOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="VendorPurchaseReport" method="post">
										<div class="form-horizontal ">
											<!-- Purchase Order Type -->
											<div class="row1">
												<label class="L-size control-label">Purchase Order
													Type :<abbr title="required">*</abbr>
												</label>
												<div class="I-size ">
													<select name="PURCHASE_TYPE" class="form-text"
														required="required">
														<option value="ALL">ALL - Purchase Order</option>
														<option value="PART-PO">Parts - Purchase Order</option>
														<option value="TYRE-PO">Tyres - Purchase Order</option>
													</select>
												</div>
											</div>
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Part/Tyre Vendor
													:<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="PurchaseVendor"
														name="PURCHASE_VENDOR" value="" style="width: 100%;"
														required="required"
														placeholder="Please Select Vendor Name" /> <label
														class="error" id="errorVendorSelect"> </label>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="VendorPurchaseDate"
															class="form-text" name="PURCHASE_DATERANGE"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>

						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#PurchaseTwo"> Date Range Wise Purchase Order Report
									</a>
								</h4>
							</div>
							<div id="PurchaseTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="DatePurchaseReport" method="post">
										<div class="form-horizontal ">
											<!-- Purchase Order Type -->
											<div class="row1">
												<label class="L-size control-label">Purchase Order
													Type :<abbr title="required">*</abbr>
												</label>
												<div class="I-size ">
													<select name="PURCHASE_TYPE" class="form-text"
														required="required">
														<option value="ALL">ALL - Purchase Order</option>
														<option value="PART-PO">Parts - Purchase Order</option>
														<option value="TYRE-PO">Tyres - Purchase Order</option>
													</select>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="PurchaseDateRange"
															class="form-text" name="PURCHASE_DATERANGE"
															required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>

					</div>
					<!-- WorkOrder Report -->
					<div class="tab-pane" id="WOReport">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#workOrderOne"> Location Wise WorkOrder Report </a>
								</h4>
							</div>
							<div id="workOrderOne" class="panel-collapse collapse">
								<div class="box-body">
									<form action="LocationWorkOrderReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Warehouse
													Location: <abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="workOrderLocation"
														name="WORKORDER_LOCATION" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more location name" />
													<p class="help-block">Select Warehouse location</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="LocWorkOrder" class="form-text"
															name="WORKORDER_DATERANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
									<!-- end Repair Report -->
								</div>
							</div>
						</div>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#WorkOrderTwo"> Vehicle Wise WorkOrder Report </a>
								</h4>
							</div>
							<div id="WorkOrderTwo" class="panel-collapse collapse">
								<div class="box-body">
									<form action="VehicleWorkOrderReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Vehicle Name: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="WorkOrderVehicle"
														name="VEHICLE_ID" style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Or More Vehicle</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="VehWorkOrder" class="form-text"
															name="WORKORDER_DATERANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
									<!-- end Repair Report -->
								</div>
							</div>
						</div>
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#WorkOrderThree"> Group Wise WorkOrder Report </a>
								</h4>
							</div>
							<div id="WorkOrderThree" class="panel-collapse collapse">
								<div class="box-body">
									<form action="GroupWorkOrderReport" method="post">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											<div class="row1">
												<label class="L-size control-label">Group: <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="workOrderGroup"
														name="WORKORDER_GROUP" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Vehicle Name" />
													<p class="help-block">Select One Vehicle Group</p>
												</div>
											</div>
											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="GroupWorkOrder" class="form-text"
															name="WORKORDER_DATERANGE" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- Cash Book Report -->
					<div class="tab-pane" id="CBReport">
						<!-- Start Show Cash Book Range -->
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseCashBook"> Cash Book Date Range Report </a>
								</h4>
							</div>
							<div id="collapseCashBook" class="panel-collapse collapse">
								<div class="box-body">
									<form action="CashBookdateRangeReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cash
													Book No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="CashBookNameList"
														name="CASH_BOOK_NO" style="width: 100%;"
														required="required"
														placeholder="Please Enter 2 or more Cash Book Name" />
													<p class="help-block">Select One Or More Vehicle</p>
													<label class="error" id="errorVendorName"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Date Of Payment
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="renewal_to">
														<input type="text" class="form-text" name="CASH_DATE"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- end Show Group Search Tyre Range -->
						<!-- Start Show Cash Book Range -->
						<div class="panel box box-warning">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseCash"> Cash Date Range Report </a>
								</h4>
							</div>
							<div id="collapseCash" class="panel-collapse collapse">
								<div class="box-body">
									<form action="CashdateRangeReport" method="post">
										<div class="form-horizontal ">
											<div class="row1">
												<label class="L-size control-label" for="issue_vehicle_id">Cash
													Book No :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="hidden" id="CashNameList" name="CASH_BOOK_NO"
														style="width: 100%;" required="required"
														placeholder="Please Enter 2 or more Cash Book Name" />
													<p class="help-block">Select One Or More Vehicle</p>
													<label class="error" id="errorVendorName"
														style="display: none"> </label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Date Of Payment
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date"
														id="renewal_from">
														<input type="text" class="form-text" name="CASH_DATE"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" name="commit"
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button>
														</div>
													</div>
												</div>
											</fieldset>

										</div>
									</form>
								</div>
							</div>
						</div>
						<!-- end Show Group Search Tyre Range -->
					</div>
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>