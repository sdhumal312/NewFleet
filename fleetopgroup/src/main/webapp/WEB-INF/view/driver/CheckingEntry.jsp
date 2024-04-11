<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> /<span> <a href="CheckingEntry/1.in">Checking Entry</a> </span> / <span>Add
						Checking Entry</span>
				</div>
				<div class="pull-right">

					<a class="btn btn-link btn-sm"
						href="<c:url value="/getDriversList"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
				<c:if test="${param.Haltsuccess eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Halt Beta Updated successfully .
					</div>
				</c:if>
				<!-- <div class="box"> -->
			<section class="content">
					<div class="form-horizontal">
							<div class="box">
									<div class="box-body ">
										<div class="row1">
											<label class="L-size control-label">Depot : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="vehicleGroupId"
													name="vehicleGroupId" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Checking Inspector <abbr
												title="required">*</abbr></label>
											<!-- <div class="I-size" id="checkingIns">
														<select style="width: 100%;" id="checkingInspectorId"
														 name="checkingInspectorId">
														</select> <span id="tyreModelIcon" class=""></span>
														<div id="tyreModelErrorMsg" class="text-danger"></div>
											</div> -->
											<div class="I-size" id="checkingIns">
												<input type="hidden" id="checkingInspectorId"
													name="checkingInspectorId" style="width: 100%;"
													required="required" placeholder="Please Checking Inspector !"
													value="0" />
											</div>
										</div>
										
										<div class="row1">
												<label class="L-size control-label">Date
													<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<div class="input-group input-append date" id="TripStartDate">
														<input id="checkingDateTime" type="text" class="form-text" name="checkingDateTime"
															placeholder="dd-mm-yyyy" required="required"
															data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
															class="input-group-addon add-on"> <span
															class="fa fa-calendar"></span>
														</span>
													</div>
												</div>
											</div>
											
										<div class="row1" id="grpvehicleName" class="form-group">
											<label class="L-size control-label" for="TripSelectVehicle">Vehicle
												: <abbr title="required">*</abbr>
											</label>
											<div class="I-size" id="vehicleSelect">
												<input type="hidden" id="vid" name="vid"
													style="width: 100%;"
													placeholder="Please Enter 2 or more Vehicle Name" />
											</div>
										</div>
										<div class="row1" id="grpconductorName" class="form-group">
											<label class="L-size control-label" for="ConductorList">Conductor:
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="conductorId"
													name="conductorId" style="width: 100%;"
													placeholder="Please Enter 3 or more Conductor Name, NO" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> In Place :<abbr
												title="required">*</abbr></label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input id="place" type="text" class="form-text" 
													name="place" required="required">
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">In
												Time:<abbr title="required">*</abbr>
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12 clockpicker">
												<input readonly="readonly" id="checkingTime" type="text" class="form-text" name="checkingTime"
													 value="00:00">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label"> Out Place :</label>
											<div class="col-md-2 col-sm-2 col-xs-12">
												<input id="outplace" type="text" class="form-text" 
													name="outplace" required="required">
											</div>
											<label class="col-md-1 col-sm-1 col-xs-12 control-label">Out
												Time:
											</label>
											<div class="col-md-3 col-sm-3 col-xs-12 clockpicker">
												<input readonly="readonly" type="text" id="outTime" class="form-text" name="outTime"
													 value="00:00">
											</div>
										</div>
										<div class="row1" id="grptripOpeningKm" class="form-group">
											<label class="L-size control-label">No. of Seat : </label>
											<div class="I-size">
												<input class="form-text" name="noOfSeat"
													id="noOfSeat" value="0" type="text"
													maxlength="50" >
											</div>
										</div>
										<div class="row1" id="grptripOpeningKm" class="form-group">
											<label class="L-size control-label">Remark : </label>
											<div class="I-size">
												<input class="form-text" name="remark"
													id="remark" placeholder="Enter Remark" type="text"
													maxlength="50" >
											</div>
										</div>
										<c:if test="${configuration.showCheckingEntryDetails}">	
										<div class="row1" id="grpRoute" class="form-group">
											<label class="L-size control-label">Route : </label>
											<div class="I-size">
												<input class="form-text" name="route"
													id="route" placeholder="Enter Route" type="text"
													maxlength="50" >
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCheckingEntryDetails}">	
										<div class="row1" id="grpDescription" class="form-group">
											<label class="L-size control-label">Description : </label>
											<div class="I-size">
												<input class="form-text" name="description"
													id="description" placeholder="Enter Description" type="text"
													maxlength="50" >
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCheckingEntryDetails}">	
										<div class="row1" id="grpPunishment" class="form-group">
											<label class="L-size control-label">Punishment : </label>
											<div class="I-size">
												<input class="form-text" name="punishment"
													id="punishment" placeholder="Enter Punishment" type="text"
													maxlength="50" >
											</div>
										</div>
										</c:if>
										<c:if test="${configuration.showCheckingEntryDetails}">	
										<div class="row1" id="grpOrderNoAndDate" class="form-group">
											<label class="L-size control-label">Order No And Date : </label>
											<div class="I-size">
												<input class="form-text" name="orderNoAndDate"
													id="orderNoAndDate" placeholder="Enter Order No And Date" type="text"
													maxlength="50" >
											</div>
										</div>
										</c:if>
							<fieldset class="form-actions">			
								<div class="row1">
									<div class="pull-right">
										<button type="submit" id="Save" class="btn btn-success">Save</button>
										<a class="btn btn-default" href="CheckingEntry/1.in">Cancel</a>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script>
		$(function() {
			$('.clockpicker').clockpicker({
				  twelvehour: true
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/driver/checkingentry.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
		
</div>