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
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <a
						href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>"><c:out
							value="${driver.driver_firstname} " /> <c:out
							value="${driver.driver_Lastname}" /></a> / <span>Driver Bata
						Details</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('EDIT_DRIVER')">
						<a class="btn btn-default"
							href="editDriver.in?driver_id=${driver.driver_id}"> <i
							class="fa fa-pencil"></i> Edit Driver
						</a>

					</sec:authorize>

					<sec:authorize access="hasAuthority('VIEW_DRIVER_BATA')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#DriverFamily"> <i class="fa fa-search"></i>
							Bata Details
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_DRIVER_BATA')">
						<a class="btn btn-warning btn-sm"
							href="<c:url value="/DriverHaltNew?driverId=${driver.driver_id}"/>"> <span
							class="fa fa-plus"></span> Add Halt Beta
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('PRINT_DRIVER')">
						<a href="PrintDriverFamily?id=${driver.driver_id}" target="_blank"
							class="btn btn-default btn-sm"><i class="fa fa-print"></i>
							Print</a>
					</sec:authorize>

					<a class="btn btn-link"
						href="showDriver.in?driver_id=${driver.driver_id}">Cancel</a>

				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<!-- Show the User Profile -->
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="zoom" data-title="Driver Photo" data-footer=""
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImage/${driver.driver_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showDriver.in?driver_id=${driver.driver_id}"> <c:out
									value="${driver.driver_firstname}" /> <c:out
									value="${driver.driver_Lastname}" /></a>
						</h3>

						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Job Role"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_jobtitle}" /></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group Service"> </span>
									<a href=""><c:out value="${driver.driver_group}" /></a></li>
								<li><span class="fa fa-empire" aria-hidden="true"
									data-toggle="tip" data-original-title="Emp Number"> </span> <span
									class="text-muted"><c:out
											value="${driver.driver_empnumber}" /></span></li>
							</ul>
						</div>

					</div>
				</sec:authorize>
			</div>

		</div>
	</section>
	<div class="modal fade" id="DriverFamily" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="formPart" action="<c:url value="/searchDriverBata.in"/>"
					method="post" name="formOwner" role="form" class="form-horizontal">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Search Bata Details</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal ">
							<input type="hidden" name="DRIVERID" value="${driver.driver_id}" />

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
											name="DRIVER_BATA_DATE" required="required"
											style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">

						<input class="btn btn-success"
							onclick="this.style.visibility = 'hidden'" name="commit"
							type="submit" value="Search Driver Bata">
						<button type="button" class="btn btn-link" data-dismiss="modal">Close</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<c:if test="${param.success  eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details created Successfully .
					</div>
				</c:if>

				<c:if test="${param.delete eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Driver Family Details Deleted Successfully .
					</div>
				</c:if>
				<c:if test="${param.already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details Already Created.
					</div>
				</c:if>
				<c:if test="${param.danger eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Driver Family Details Not Created.
					</div>
				</c:if>

				<div class="row">
					<sec:authorize access="!hasAuthority('EDIT_DRIVER_BATA')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('EDIT_DRIVER_BATA')">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<form id="formDriverHalt"
										action="<c:url value="/updateLocalDriverHalt.in"/>"
										method="post" name="formDriverHalt" role="form"
										class="form-horizontal">

										<div class="form-horizontal">
											<fieldset>
												<legend>Driver Halt Bata Details</legend>
												<div class="row1" id="grpdriverName" class="form-group">
													<div class="I-size">
														<input type="hidden" name="DRIVERID"
															value="${DriverHalt.DRIVERID}" /> <input type="hidden"
															name="DHID" value="${DriverHalt.DHID}" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Vehicle Name :</label>
													<div class="I-size" id="driverSelect">
														<input type="hidden" value="${DriverHalt.VID}" id="Ovid">
														<input type="hidden" value="${DriverHalt.VEHICLE_NAME}"
															id="OVehicleName"> <input type="hidden"
															id="TripSelectVehicle" name="VID" style="width: 100%;"
															required="required" value="${DriverHalt.VID}"
															placeholder="Please Enter 2 or more Vehicle Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Reference :</label>
													<div class="I-size">
														<input class="form-text" id="fuel_reference"
															value="${DriverHalt.REFERENCE_NO}" maxlength="50"
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
															<input type="text" class="form-text" name="HALT_DATE"
																readonly="readonly"
																data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
																value="${DriverHalt.HALT_DATE_FROM} to ${DriverHalt.HALT_DATE_TO}"
																data-mask="" id="haltDatesa" maxlength="26"> <span
																class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span>
														</div>
														<span id="haltDateIcon" class=""></span>
														<div id="haltDateErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1" id="grphaltAmount" class="form-group">
													<label class="L-size control-label">Halt per day
														Bata Amount <abbr title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="text" class="form-text" name="HALT_AMOUNT"
															value="${DriverHalt.HALT_AMOUNT}" maxlength="10"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															id="haltAmount"> <span id="haltAmountIcon"
															class=""></span>
														<div id="haltAmountErrorMsg" class="text-danger"></div>
													</div>
												</div>
												<div class="row1" id="grphaltReason" class="form-group">
													<label class="L-size control-label">Halt Reason <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<textarea rows="3" cols="2" class="form-text"
															id="haltReason" name="HALT_REASON" maxlength="200">${DriverHalt.HALT_REASON}</textarea>
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
															id="haltPlace" required="required"
															value="${DriverHalt.HALT_PLACE}" name="HALT_PLACE">
														<span id="haltPlaceIcon" class=""></span>
														<div id="haltPlaceErrorMsg" class="text-danger"></div>

													</div>
													<input name="HALT_PLACE_ID" id="HALT_PLACE_ID"
														type="hidden" value="${DriverHalt.HALT_PLACE_ID}" />
												</div>
												<input type="hidden" id="haltBy" name="HALT_PAIDBY" value="${DriverHalt.HALT_PAIDBY}">
											</fieldset>
											<div class="box-footer h-padded">
												<fieldset class="form-actions">
													<div class="col-md-offset-3">
														<input class="btn btn-success" name="commit" type="submit"
															value="Update
													Halt Beta"> <a
															class="btn btn-link"
															href="<c:url value="/showDriver?driver_id=${driver.driver_id}"/>">Cancel</a>
													</div>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
			<!-- side reminter -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="DriverSideMenu.jsp"%>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
	<script>
		$(function() {
			$('#haltDate').daterangepicker();
			$(".select2").select2({
				placeholder : "Please Select Driver Name"
			});
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverHaltValidate.js" />"></script>

	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/script.js" />"></script>
	<script type="text/javascript">
	$(document).ready(function(){var h=$("#Ovid").val(),i=$("#OVehicleName").val();$("#TripSelectVehicle").select2("data",{id:h,text:i});});
	</script>
	<script type="text/javascript">
	$(document).ready(function() {
	    var e = 25,
	        t = $(".input_fields_wrap"),
	        n = $(".add_field_button"),
	        a = 1;
	    $(n).click(function(n) {
	        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row"><div class="col-md-4"><input class="form-text" id="OwnerSerial" name="DF_NAME" type="text" maxlength="150" required="required"> </div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_SEX" required="required"><option value="MALE">MALE</option><option value="FEMALE">FEMALE</option></select></div><div class="col-md-1"><input class="form-text" id="DF_AGE" name="DF_AGE" type="number"  required="required"></div><div class="col-md-2"><select class="form-text " id="OwnerName" name="DF_RELATIONSHIP"	required="required"><option value="FATHER">FATHER</option><option value="MOTHER">MOTHER</option><option value="SON">SON</option><option value="DAUGHTER">DAUGHTER</option><option value="BROTHER">BROTHER</option><option value="SISTER">SISTER</option><option value="HUSBAND">HUSBAND</option><option value="WIFE">WIFE</option></select></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'))
	    }), $(t).on("click", ".remove_field", function(e) {
	        e.preventDefault(), $(this).parent("div").remove(), a--
	    })
	});
	</script>

</div>