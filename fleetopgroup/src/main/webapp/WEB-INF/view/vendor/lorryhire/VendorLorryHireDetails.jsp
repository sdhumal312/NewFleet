<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a>/ <a
						href="<c:url value="/viewVendorLorryHireDetails.in"/>">Vendor Lorry Hire List</a> / <span
						id="NewVehi">Vendor Lorry Hire</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
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
							<input type="hidden" id="hideExpensesTab" value="${configuration.hideExpensesTab}">
							<input type="hidden" id="tripCompanyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
							<c:if test="${!empty tripSheet}">
								<input type="hidden" id="tripId" value="${tripSheet.tripSheetID}">
								<input type="hidden" id="tripNumber" value="${tripSheet.tripSheetNumber}">
								<input type="hidden" id="vid" value="${tripSheet.vid}">
								<input type="hidden" id="vehicleName" value="${tripSheet.vehicle_registration}">
								<input type="hidden" id="dId" value="${tripSheet.tripFristDriverID}">
								<input type="hidden" id="dName" value="${tripSheet.tripFristDriverName}">
							</c:if>
										<div class="row1" id="grpwoJobType" class="form-group">
													<label class="L-size control-label" for="from">TS Number <abbr title="required">*</abbr> </label>
													<div class="I-size">
															<div class="row">
																<input type="hidden" name="selectTripSheetNumber" id="selectTripSheetNumber"
																	required="required" style="width: 100%;"
																	required="required"
																	placeholder="Please Select TripSheet" /> 
															</div>
													</div>
										</div>	
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label"
												for="SelectVehicle">Vehicle Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="SelectVehicle" name="vid"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" /> <span
													id="vehicleNumberIcon" class=""></span>
												<div id="vehicleNumberErrorMsg" class="text-danger"></div>
											</div>
										</div>	
										<div class="row1" id="grpvehicleNumber" class="form-group">
											<label class="L-size control-label"
												for="SelectVehicle">Driver Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="driverId" class="select2" name="driverId" readonly="readonly"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Driver Name" />
											</div>
										</div>
									
										<div class="row1">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="Vendor_id"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Income Name :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="incomeSelect">
												<select class="select2" name="incomeName" style="width: 100%;"
													id="Income" required="required">
												</select>
											</div>
										</div>
										<!-- <div class="row1">
											<label class="L-size control-label">Total Balance :</label>
											<div class="I-size">
												<input type="number"  name="openingBalance"
													id="openingBalance" class="form-text" disabled="disabled" />
											</div>
										</div> -->
									
										<div class="row1">
											<label class="L-size control-label">Lorry Hire :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="number"  name="lorryHire"
													id="lorryHire" value="0" class="form-text" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Adv. Amount :</label>
											<div class="I-size">
												<input type="number"  name="advAmount"
													id="advAmount" value="0" class="form-text" />
											</div>
										</div>
										
											
											<div class="row1">
													<label class="L-size control-label"> Date :</label>
													<div class="I-size">
													<div class="input-group input-append date" id="datePicker">
															<input type="text" class="form-text" name="cheque_date"
																id="chequeDate" data-inputmask="'alias': 'dd-mm-yyyy'"
																data-mask="" /> <span class="input-group-addon add-on">
																<span class="fa fa-calendar"></span>
															</span>
														</div>
														</div>
												</div>		
												<div class="row">
													<label class="L-size control-label" style="text-align-last: center;" id="Type">Remark :</label>
													<div class="I-size">
														<input type="text" class="form-text" id="remark" maxlength="249" name="remark" value="" placeholder="Enter description" />
													</div>
												</div>							
									</div>
								</div>
							</fieldset>
										<c:if test="${!configuration.hideExpensesTab}">
										<fieldset>
											<legend>Expense Details</legend>
											<div class="row1" class="form-group" id="grprouteExpense">

												<div class="col-md-4">
													<select class="form-text select2" style="width: 100%;"
														name="expenseName" id="Expense" >

													</select> <span id="routeExpenseIcon" class=""></span>
													<div id="routeExpenseErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-3" id="grprouteAmount" >
													<input type="number" class="form-text" name="Amount"
														id="routeAmount" placeholder="Amount" min="0"> <span
														id="routeAmountIcon" class=""></span>
													<div id="routeAmountErrorMsg" class="text-danger"></div>
												</div>

												<div class="input_fields_wrap">
													<button class="add_field_button btn btn-success">
														<i class="fa fa-plus"></i>
													</button>

												</div>

											</div>
										</fieldset>
										<br>
										</c:if>
								 
							<fieldset class="form-actions">
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="button" class="btn btn-success" onclick="addVendorLorryHire();">Add Vendor Lorry Hire</button>
										<a class="btn btn-link"
											href="<c:url value="/vendorHome.in"/>">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/lorryhire/VendorLorryHireAdd.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>