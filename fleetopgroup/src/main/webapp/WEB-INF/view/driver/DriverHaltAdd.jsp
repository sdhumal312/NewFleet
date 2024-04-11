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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <span>Add
						Driver Halt</span>
				</div>
				<div class="pull-right">

					<a class="btn btn-link btn-sm"
						href="<c:url value="/getDriversList"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">

		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.Haltsuccess eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Halt Beta Updated successfully .
					</div>
				</c:if>
				<c:if test="${param.AlreadyHalt eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						Already Updated Driver Halt Beta In date. .
					</div>
				</c:if>
				<div class="box">
					<div class="box-body">

						<sec:authorize access="!hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_EXPENSE_TRIPSHEET')">
							<form id="formDriverHalt"
								action="<c:url value="/saveDriverHalt.in"/>" method="post"
								enctype="multipart/form-data" name="formDriverHalt" role="form"
								class="form-horizontal">

								<div class="form-horizontal">
								<input type="hidden" id="driverId" value="${driverId}">
								<input type="hidden" id="driverName" value="${driverName}">
									<fieldset>
										<legend>Driver Halt Bata Details</legend>
										<div class="row1" id="grpdriverName" class="form-group">
											<label class="L-size control-label">Driver Name <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="hidden" id="driverHaltALL" name="DRIVERID"
													style="width: 100%;"
													placeholder="Please Enter 3 or more Driver Name, No" /> <span
													id="driverNameIcon" class=""></span>
												<div id="driverNameErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Vehicle Name :</label>
											<div class="I-size" id="driverSelect">
												<input type="hidden" id="TripSelectVehicle" name="VID"
													style="width: 100%;" required="required" value="0"
													placeholder="Please Enter 2 or more Vehicle Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Reference :</label>
											<div class="I-size">
												<input class="form-text" id="fuel_reference" maxlength="50"
													name="REFERENCE_NO" type="text"
													onkeypress="return IsReference(event);"
													ondrop="return false;"> <label class="error"
													id="errorReference" style="display: none"> </label>
											</div>
										</div>
										<div class="row1" id="grphaltDate" class="form-group">
											<label class="L-size control-label">Halt Date From
												&amp; To <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date">
													<input type="text" class="form-text" name="HALT_DATE" readonly="readonly"
														data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
														data-mask="" id="haltDate" maxlength="26"> <span
														class="input-group-addon add-on"><span
														class="fa fa-calendar"></span></span>
												</div>
												<span id="haltDateIcon" class=""></span>
												<div id="haltDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grphaltAmount" class="form-group">
											<label class="L-size control-label">Halt per day Bata
												Amount <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="HALT_AMOUNT"
													maxlength="10" id="haltAmount" onkeypress="return isNumberKeyWithDecimal(event,this.id);"> <span
													id="haltAmountIcon" class=""></span>
												<div id="haltAmountErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="row1" id="grphaltReason" class="form-group">
											<label class="L-size control-label">Halt Reason <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<textarea rows="3" cols="2" class="form-text"
													id="haltReason" name="HALT_REASON" maxlength="200"></textarea>
												<span id="haltReasonIcon" class=""></span>
												<div id="haltReasonErrorMsg" class="text-danger"></div>
											</div>
										</div>

										<div class="row1" id="grphaltPlace" class="form-group">
											<label class="L-size control-label">Halt Place <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													id="haltPlace" required="required" value="${place}"
													name="HALT_PLACE"> <span id="haltPlaceIcon"
													class=""></span>
												<div id="haltPlaceErrorMsg" class="text-danger"></div>

											</div>
											<input name="HALT_PLACE_ID" id="HALT_PLACE_ID" type="hidden"
												value="${placeId}" />
										</div>
										<input type="hidden" id="haltBy" name="HALT_PAIDBY" value="${haltBy}">
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<div class="col-md-offset-3">
												<input class="btn btn-success" name="commit" type="submit"
													value="Create
													Halt Beta"> <a
													class="btn btn-link"
													href="<c:url value="/getDriversList"/>">Cancel</a>
											</div>
										</fieldset>
									</div>
								</div>
							</form>
						</sec:authorize>
					</div>
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
	<script>
		$(function() {
			$('#haltDate').daterangepicker();
			$(".select2").select2({
				placeholder : "Please Select Driver Name"
			});
		});
	</script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverHaltValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
</div>