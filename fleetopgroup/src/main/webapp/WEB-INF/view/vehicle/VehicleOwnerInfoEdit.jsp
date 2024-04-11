<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <a
						href="<c:url value="ShowVehicleOwner.in?vehid=${vehicle.vid}"/>">Vehicle
						Owner</a> / <span>Edit Vehicle Owner</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link"
						href="ShowVehicleOwner.in?vehid=${vehicle.vid}">Cancel</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Vehicle" data-footer="The beauty "
							data-type="image" data-toggle="lightbox"> <img
							src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="img-rounded" alt="Cinque Terre" width="100" height="100" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><span class="fa fa-clock-o" aria-hidden="true"
									data-toggle="tip" data-original-title="Odometer"><a
										href="#"><c:out value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>

							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9">
				<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
					<div class="box box-success">
						<div class="box-header">
							<h3 class="box-title">Edit Vehicle Owner Information</h3>
						</div>

						<div class="box-menu">
							<form id="formPart"
								action="<c:url value="/updateVehicleOwner.in"/>" method="post"
								enctype="multipart/form-data" name="formEditOwner" role="form"
								class="form-horizontal">

								<div class="form-horizontal">
									<input type="hidden" name="VEHID" value="${vehOwner.VEHID}"
										required="required" /> <input type="hidden" name="VOID"
										value="${vehOwner.VOID}" required="required" />
									<c:if test="${configuration.showVehicleOwnerSrNo}">
									<div class="row1" id="grpOwnerSerial" class="form-group">
										<label class="L-size control-label" for="ownerSerial">Vehicle
											Owner Serial NO: <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="OwnerSerial" readonly="readonly"
												value="${vehOwner.VEH_OWNER_SERIAL}" name="VEH_OWNER_SERIAL"
												type="text" maxlength="25" required="required"> <span
												id="OwnerSerialIcon" class=""></span>
											<div id="OwnerSerialErrorMsg" class="text-danger"></div>
										</div>
									</div>
									</c:if>
									<c:if test="${!configuration.showVehicleOwnerSrNo}">
									<input type="hidden" name="VEH_OWNER_SERIAL" value="0" >
									</c:if>
									<div class="row1" id="grpOwnerName" class="form-group">
										<label class="L-size control-label" for="OwnerName">Vehicle
											Owner Name: <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="OwnerName" readonly="readonly"
												value="${vehOwner.VEH_OWNER_NAME}" name="VEH_OWNER_NAME"
												type="text" maxlength="150" required="required"> <span
												id="OwnerNameIcon" class=""></span>
											<div id="OwnerNameErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<div class="row1" id="grpOwnerPhone" class="form-group">
									<input type="hidden" id="editPhone" value="${vehOwner.VEH_OWNER_PHONE}">
										<label class="L-size control-label" for="OwnerPhone">Vehicle
											Owner Phone NO: <abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="OwnerPhone"
												value="${vehOwner.VEH_OWNER_PHONE}" name="VEH_OWNER_PHONE" onkeypress="return isNumberKey(event,this);"
												type="text" maxlength="10" onblur="return isMobileNum(this);" > <span
												id="OwnerPhoneIcon" class=""></span>
											<div id="OwnerPhoneErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<div class="row1" id="grpOwnerAddress" class="form-group">
									<input type="hidden" id="editAdd" value="${vehOwner.VEH_OWNER_ADDRESS}">
										<label class="L-size control-label" for="OwnerAddress">Vehicle
											Owner RC Address:<abbr title="required">*</abbr>
										</label>
										<div class="I-size">
											<input class="form-text" id="OwnerAddress"
												value="${vehOwner.VEH_OWNER_ADDRESS}"
												name="VEH_OWNER_ADDRESS" type="text" maxlength="190">
											<span id="OwnerAddressIcon" class=""></span>
											<div id="OwnerAddressErrorMsg" class="text-danger"></div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-5">
											<div class="row" id="grplocationCountry" class="form-group">
												<label class="string required control-label">Country
													:<abbr title="required">*</abbr>
												</label> <select name="VEH_OWNER_COUNTRY"
													class="select2 countries form-text" style="width: 100%"
													id="countryId">
													<option value="${vehOwner.VEH_OWNER_COUNTRY}"
														selected="selected">${vehOwner.VEH_OWNER_COUNTRY}</option>
													<option value="">Select Country</option>
												</select> <span id="locationCountryIcon" class=""></span>
												<div id="locationCountryErrorMsg" class="text-danger"></div>
											</div>
										</div>

										<div class="col-md-5">
											<div class="row" id="grplocationState" class="form-group">
												<label class="string required control-label">State
													::<abbr title="required">*</abbr>
												</label> <select name="VEH_OWNER_STATE"
													class="select2 states form-text" style="width: 100%"
													id="stateId">
													<option value="${vehOwner.VEH_OWNER_STATE}"
														selected="selected">${vehOwner.VEH_OWNER_STATE}</option>
													<option value="">Select State</option>
												</select> <span id="locationStateIcon" class=""></span>
												<div id="locationStateErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</div>
									<div class="row">

										<div class="col-md-5">
											<div class="row" id="grplocationCity" class="form-group">
												<label class="string required control-label">City :<abbr
													title="required">*</abbr>
												</label> <select name="VEH_OWNER_CITY"
													class="select2 cities form-text" style="width: 100%"
													id="cityId">
													<option value="${vehOwner.VEH_OWNER_CITY}"
														selected="selected">${vehOwner.VEH_OWNER_CITY}</option>
													<option value="">Select City</option>
												</select> <span id="locationCityIcon" class=""></span>
												<div id="locationCityErrorMsg" class="text-danger"></div>
											</div>
										</div>

										<div class="col-md-5">
											<div class="row" id="grplocationCode" class="form-group">
												<label class="string required control-label">Postal
													Code :<abbr title="required">*</abbr>
												</label> <input type="text" class="form-text"
													name="VEH_OWNER_PINCODE" id="locationCode" onkeypress="return isNumberKey(event,this);"
													value="${vehOwner.VEH_OWNER_PINCODE}"
													onkeypress="return IsNumericPin(event);"
													ondrop="return false;" maxlength="6"> <span
													id="locationCodeIcon" class=""></span>
												<div id="locationCodeErrorMsg" class="text-danger"></div>
												<label class="error" id="errorPin" style="display: none">
												</label>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-md-5">
											<div class="row" id="grpOwnerAadhar" class="form-group">
												<label class=" control-label" for="OwnerAadhar">Vehicle
													Owner Aaadhar NO: </label> <input class="form-text"
													value="${vehOwner.VEH_OWNER_AADHARNO}" id="OwnerAadhar" onkeypress="return isNumberKey(event,this);"
													name="VEH_OWNER_AADHARNO" type="text" maxlength="12">
												<span id="OwnerAadharIcon" class=""></span>
												<div id="OwnerAadharErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<div class="col-md-5">
											<div class="row" id="grpOwnerPan" class="form-group">
												<label class=" control-label" for="OwnerPan">Vehicle
													Owner PAN NO:</label> <input class="form-text" id="OwnerPan"
													value="${vehOwner.VEH_OWNER_PANNO}" name="VEH_OWNER_PANNO"
													type="text" maxlength="25"> <span id="OwnerPanIcon"
													class=""></span>
												<div id="OwnerPanErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</div>

									<div class="row">
									<c:if test="${configuration.showDriverNameInAddOwnerShip}">
										<div class="col-md-5">
											<div class="row" id="grpDriverName" class="form-group">
												<label class=" control-label" for="DriverName">Driver
													Name: </label> <input class="form-text" id="DriverName"
													value="${vehOwner.VEH_DRIVER_NAME}" name="VEH_DRIVER_NAME"
													type="text" maxlength="25"> <span
													id="DriverNameIcon" class=""></span>
												<div id="DriverNameErrorMsg" class="text-danger"></div>

											</div>
										</div>
									</c:if>
									<c:if test="${configuration.showDriverPhoneNoInAddOwnerShip}">	
										<div class="col-md-5">
											<div class="row" id="grpDriverPhone" class="form-group">
												<label class=" control-label" for="DriverPhone">Driver
													Phone No: </label> <input class="form-text" id="DriverPhone"
													value="${vehOwner.VEH_DRIVER_PHONE}"
													name="VEH_DRIVER_PHONE" type="text" maxlength="25">
												<span id="DriverPhoneIcon" class=""></span>
												<div id="DriverPhoneErrorMsg" class="text-danger"></div>
											</div>
										</div>
									</c:if>	
									</div>
									<div class="modal-footer">

										<button type="submit" class="btn btn-success" onclick="return validateFields();">Update
											Owner Info</button>
										<a type="button" class="btn btn-info"
											href="ShowVehicleOwner.in?vehid=${vehicle.vid}">Cancel</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</sec:authorize>
			</div>
			<div class="col-md-2">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript">
	function validateFields(){
		
		if($("#OwnerPhone").val() == "" || $("#OwnerPhone").val() == null){
			showMessage('info','Phone Number Can Not Be Empty')
			$("#OwnerPhone").val($("#editPhone").val());
			return false;
		}
		if($("#OwnerAddress").val() == "" || $("#OwnerAddress").val() == null){
			showMessage('info','Owner Address Can Not Be empty')
			$("#OwnerAddress").val($("#editAdd").val());
			
			return false;
		}
		if($('#countryId option:selected').html() == "" || $('#countryId option:selected').html() == null){
			showMessage('info','Please Select Country')
			return false;
		}
		if($('#stateId option:selected').html() == "" || $('#stateId option:selected').html() == null){
			showMessage('info','Please Select State')
			return false;
		}
		if($('#cityId option:selected').html() == "" || $('#cityId option:selected').html() == null){
			showMessage('info','Please Select City')
			return false;
		}
		if($("#locationCode").val() == "" || $("#locationCode").val() == null){
			showMessage('info','Please Enter Postal Code')
			return false;
		}
			
		}	
	
	function isMobileNum(evt)
	{
		var inputMobileNum = document.getElementById(evt.id);
		var reg = /^[6789]\d{9}$/ig;

		if(inputMobileNum.value.length == 10  && inputMobileNum.value.match(reg))
		{
			return true;
		}
		else
		{
			inputMobileNum.value = $("#editPhone").val();
			document.getElementById(evt.id).focus();
			showMessage('info','Please Enter Valid Phone Number');
			return false;
		}

	}
		
	
	
		$(document).ready(function() {
			$(".select2").select2();
			$("#tagPicker").select2({
				closeOnSelect : !1
			})
		});
		
		$(document).ready(function() {
		    function a(a) {
		        for (var p = document.getElementsByTagName("textarea"), u = 0; u < p.length; u++) 
		        	com_satheesh.EVENTS.addEventHandler(p[u], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(p[u], "blur", c, !1);
		        p = document.getElementsByTagName("input");
		        for (var u = 0; u < p.length; u++) a.indexOf(-1 != p[u].getAttribute("type")) && (com_satheesh.EVENTS.addEventHandler(p[u], "focus", b, !1), com_satheesh.EVENTS.addEventHandler(p[u], "blur", c, !1));
		        com_satheesh.EVENTS.addEventHandler(document.getElementById("formOwner"), "submit", s, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditOwner"), "submit", t, !1), 
		        document.getElementsByTagName("input")[0].focus(), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerSerial, "blur", d, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerName, "blur", e, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].countryId, "blur", f, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].stateId, "blur", g, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].cityId, "blur", h, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].locationCode, "blur", i, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerPhone, "blur", j, !1), 
		        com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerAddress, "blur", k, !1)
		       
		    }

		    function b(a) {
		        var b = com_satheesh.EVENTS.getEventTarget(a);
		        null != b && (b.style.backgroundColor = u)
		    }

		    function c(a) {
		        var b = com_satheesh.EVENTS.getEventTarget(a);
		        null != b && (b.style.backgroundColor = "")
		    }

		    function d() {
		        var a = document.getElementById("OwnerSerial"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grpOwnerSerial");
		        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("OwnerSerialIcon").className = "fa fa-check form-text-feedback", document.getElementById("OwnerSerialErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("OwnerSerialIcon").className = "fa fa-remove form-text-feedback", document.getElementById("OwnerSerialErrorMsg").innerHTML = "Please enter Owner Serial"), b
		    }

		    function e() {
		        var a = document.getElementById("OwnerName"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grpOwnerName");
		        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("OwnerNameIcon").className = "fa fa-check form-text-feedback", document.getElementById("OwnerNameErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("OwnerNameIcon").className = "fa fa-remove form-text-feedback", document.getElementById("OwnerNameErrorMsg").innerHTML = "Please enter Owner Name"), b
		    }

		    function f() {
		        var a = document.getElementById("countryId"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grplocationCountry");
		        if (null != c) return b ? (c.className = "form-group has-success has-feedback", document.getElementById("locationCountryIcon").className = "fa fa-check form-text-feedback", document.getElementById("locationCountryErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("locationCountryIcon").className = "fa fa-remove form-text-feedback", document.getElementById("locationCountryErrorMsg").innerHTML = "Please select warehouse country"), b
		    }

		    function g() {
		        var a = document.getElementById("stateId"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grplocationState");
		        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("locationStateIcon").className = "fa fa-check  form-text-feedback", document.getElementById("locationStateErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("locationStateIcon").className = "fa fa-remove form-text-feedback", document.getElementById("locationStateErrorMsg").innerHTML = "Please select warehouse state")), b
		    }

		    function h() {
		        var a = document.getElementById("cityId"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grplocationCity");
		        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("locationCityIcon").className = "fa fa-check  form-text-feedback", document.getElementById("locationCityErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("locationCityIcon").className = "fa fa-remove form-text-feedback", document.getElementById("locationCityErrorMsg").innerHTML = "Please select warehouse city")), b
		    }

		    function i() {
		        var a = document.getElementById("locationCode"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grplocationCode");
		        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("locationCodeIcon").className = "fa fa-check  form-text-feedback", document.getElementById("locationCodeErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("locationCodeIcon").className = "fa fa-remove form-text-feedback", document.getElementById("locationCodeErrorMsg").innerHTML = "Please select warehouse pin code")), b
		    }

		    function j() {
		        var a = document.getElementById("OwnerPhone"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grpOwnerPhone");
		        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("OwnerPhoneIcon").className = "fa fa-check  form-text-feedback", document.getElementById("OwnerPhoneErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("OwnerPhoneIcon").className = "fa fa-remove form-text-feedback", document.getElementById("OwnerPhoneErrorMsg").innerHTML = "Please enter Owner Phone")), b
		    }

		    function k() {
		        var a = document.getElementById("OwnerAddress"),
		            b = null != a.value && 0 != a.value.length,
		            c = document.getElementById("grpOwnerAddress");
		        return null != c && (b ? (c.className = "form-group has-success has-feedback", document.getElementById("OwnerAddressIcon").className = "fa fa-check  form-text-feedback", document.getElementById("OwnerAddressErrorMsg").innerHTML = "") : (c.className = "form-group has-error has-feedback", document.getElementById("OwnerAddressIcon").className = "fa fa-remove form-text-feedback", document.getElementById("OwnerAddressErrorMsg").innerHTML = "Please enter Owner Address")), b
		    }

		    

		    function s(a) {
		        var b = d();
		        b &= e(), b &= f(), b &= g(), b &= h(), b &= i(), b &= j(), (b &= k()) || com_satheesh.EVENTS.preventDefault(a)
		    }

		    function t(a) {
		        var b = d();
		        b &= e(), b &= f(), b &= g(), b &= h(), b &= i(), b &= j(), (b &= k()) || com_satheesh.EVENTS.preventDefault(a)
		    }
		    var u = "#FFC";
		    com_satheesh.EVENTS.addEventHandler(window, "load", function() {
		        a("text")
		    }, !1)
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	<%-- <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> --%>
</div>