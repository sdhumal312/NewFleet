<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/sweetalert2.css"/>">
		<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/sweetalertNew2.js" />"></script>
		<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/sweetalert2.min.css"/>">
		<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/sweetalert/sweetalert2.min.js" />"></script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="<c:url value="/ViewInspectionSheet.in"/>"> View Inspection Sheet</a> / 
					<span id="NewVehicle">Assign Inspection Sheet</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="panel box box-primary">
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADD_VEHICLE_ASSIGNMENT_TO_SHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_VEHICLE_ASSIGNMENT_TO_SHEET')">
											<div class="box-header">
													<div class="row">
															<h3 style="text-align: center;">Assign Inspection Sheet</h3>
											</div> 
											</div>
											<br/>
										<div class="box">
											
											<div class="box-body">
									<input type="hidden" id="sheetId" value= "${sheetId}">
									<input type="hidden" id="sheetName" value="${sheetName}">
									<input type="hidden" id="info" value="${status}">
									<form action="asignSheetToVehicleType.in" method="post">
								
										<div class="row">
														<label class="L-size control-label">Select Sheet : <abbr
															title="required">*</abbr></label>
														<div class="I-size">
			
															<input type="hidden" id="inspectionSheetId"
																name="inspectionSheetId" readonly="readonly" style="width: 100%;"
																placeholder="Please Enter Sheet Name" />
														</div>
													</div> <br/>
													
													
											<div class="row">
										<label class="L-size control-label">Vehicle Type : 
													<abbr title="required">*</abbr> 
										</label>
										<div class="I-size">
												<input type="hidden" id="VehicleTypeSelect" name="VehicleTypeSelect" style="width: 100%;"
												placeholder="Please Enter 2 or more Type Name" /> 
												</div>
										</div><br/>
										
											<div class=row>
												<div class="L-size control-label" >Branch :
												</div>
												<div class="I-size" >
												<input type="hidden" id="VehicleLocation" name="VehicleLocation" style="width: 100%;" value="0" placeholder="Please Enter Branch Name" />
												</div>
											</div>
											<br/>		
													
											<div class="row">
												<label class="L-size control-label">Start Date :
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="renewal_to">
														<input type="text" id="inspectionStartDateStr" class="form-text" name="inspectionStartDateStr" readonly="readonly"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div><br/>
											
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															<button type="submit" onclick="return validateVehicleTypeAssignment();" name="commit"
																class="btn btn-success">
																Assign
															</button>
														</div>
													</div>
												</div>
											</fieldset>
								
										</form>		
											</div>
										</div>
							
						</sec:authorize>
					</div>
				</div>
			</div>

		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js" />"></script>
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
		

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
		
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/AsignVehicleInspectionSheet.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/AsignVehicleInspectionSheetToVehicleGroup.js" />"></script>

	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	
	
</div>