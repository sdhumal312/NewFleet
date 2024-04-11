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
							value="${vehicle.vehicle_registration}" /></a> / <span>New
						Vehicle Owner</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#vehiclePurchase"> <i class="fa fa-plus"></i>
							Add Vehicle Owner
						</a>
					</sec:authorize>
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								<span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							    <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							    class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
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
	<div class="modal fade" id="vehiclePurchase" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="formOwner" action="<c:url value="/saveVehicleOwner.in"/>"
					method="post" enctype="multipart/form-data" name="formOwner"
					role="form" class="form-horizontal">

					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add Vehicle Owner Information</h4>
					</div>
					<div class="modal-body">
						<div class="form-horizontal ">
							<input type="hidden" name="VEHID" value="${vehicle.vid}" />
							<input type="hidden" id="showVehicleOwnerSrNo" value="${configuration.showVehicleOwnerSrNo}" />
							<c:if test="${configuration.showVehicleOwnerSrNo}">
							<div class="row" id="grpOwnerSerial" class="form-group">
								<label class="L-size control-label" for="ownerSerial">Vehicle
									Owner Serial NO: <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input class="form-text" id="OwnerSerial"
										name="VEH_OWNER_SERIAL" type="text" maxlength="25"
										required="required"> <span id="OwnerSerialIcon"
										class=""></span>
									<div id="OwnerSerialErrorMsg" class="text-danger"></div>
								</div>
							</div>
							
							</c:if>
							<div class="row" id="grpOwnerName" class="form-group">
								<label class="L-size control-label" for="OwnerName">Vehicle
									Owner Name: <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input class="form-text" id="OwnerName" name="VEH_OWNER_NAME"
										type="text" maxlength="150" required="required"> <span
										id="OwnerNameIcon" class=""></span>
									<div id="OwnerNameErrorMsg" class="text-danger"></div>
								</div>
							</div>
							<div class="row" id="grpOwnerPhone" class="form-group">
								<label class="L-size control-label" for="OwnerPhone">Vehicle
									Owner Phone NO: <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input class="form-text" id="OwnerPhone" name="VEH_OWNER_PHONE" onkeypress="return isNumberKey(event,this);"
										type="text" maxlength="10" onblur="return isMobileNum(this);"> <span id="OwnerPhoneIcon" 
										class=""></span>
									<div id="OwnerPhoneErrorMsg" class="text-danger"></div>
								</div>
							</div>
							<div class="row" id="grpOwnerAddress" class="form-group">
								<label class="L-size control-label" for="OwnerAddress">Vehicle
									Owner RC Address:<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input class="form-text" id="OwnerAddress"
										name="VEH_OWNER_ADDRESS" type="text" maxlength="190"> <span
										id="OwnerAddressIcon" class=""></span>
									<div id="OwnerAddressErrorMsg" class="text-danger"></div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-5">
									<div class="row" id="grplocationCountry" class="form-group">
										<label class="string required control-label">Country :<abbr
											title="required">*</abbr></label> <select name="VEH_OWNER_COUNTRY"
											class="select2 countries form-text" style="width: 100%"
											id="countryId">
											<option value="">Select Country</option>
										</select> <span id="locationCountryIcon" class=""></span>
										<div id="locationCountryErrorMsg" class="text-danger"></div>
									</div>
								</div>

								<div class="col-md-5">
									<div class="row" id="grplocationState" class="form-group">
										<label class="string required control-label">State ::<abbr
											title="required">*</abbr></label> <select name="VEH_OWNER_STATE"
											class="select2 states form-text" style="width: 100%"
											id="stateId">
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
											title="required">*</abbr></label> <select name="VEH_OWNER_CITY"
											class="select2 cities form-text" style="width: 100%"
											id="cityId">
											<option value="">Select City</option>
										</select> <span id="locationCityIcon" class=""></span>
										<div id="locationCityErrorMsg" class="text-danger"></div>
									</div>
								</div>

								<div class="col-md-5">
									<div class="row" id="grplocationCode" class="form-group">
										<label class="string required control-label">Postal
											Code :<abbr title="required">*</abbr>
										</label> <input type="text" class="form-text" name="VEH_OWNER_PINCODE"
											id="locationCode" onkeypress="return isNumberKey(event,this);"
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
											Owner Aaadhar NO: </label> <input class="form-text" id="OwnerAadhar"
											name="VEH_OWNER_AADHARNO" type="text" maxlength="12" onkeypress="return isNumberKey(event,this);">
										<span id="OwnerAadharIcon" class=""></span>
										<div id="OwnerAadharErrorMsg" class="text-danger"></div>
									</div>
								</div>
								<div class="col-md-5">
									<div class="row" id="grpOwnerPan" class="form-group">
										<label class=" control-label" for="OwnerPan">Vehicle
											Owner PAN NO:</label> <input class="form-text" id="OwnerPan"
											name="VEH_OWNER_PANNO" type="text" maxlength="25"> <span
											id="OwnerPanIcon" class=""></span>
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
												name="VEH_DRIVER_NAME" type="text" maxlength="25"> <span
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
												name="VEH_DRIVER_PHONE" type="text" maxlength="25"> <span
												id="DriverPhoneIcon" class=""></span>
											<div id="DriverPhoneErrorMsg" class="text-danger"></div>
										</div>
									</div>
								</c:if>	
							</div>
						</div>
					</div>
					<div class="modal-footer">

						<button type="submit" class="btn btn-success" onclick="return validateFields();">Create
							Owner Info </button>
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
				<c:if test="${param.save eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Owner Info Created Successfully.
					</div>
				</c:if>
				<c:if test="${param.update eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Owner Info Updated successfully .
					</div>
				</c:if>
				<c:if test="${param.delete eq true}">
					<div class="alert alert-success">
						<button class="close" data-dismiss="alert" type="button">x</button>
						The Vehicle Owner Info Removed successfully .
					</div>
				</c:if>

				<c:if test="${param.already eq true}">
					<div class="alert alert-danger">
						<button class="close" data-dismiss="alert" type="button">x</button>
						This Vehicle Owner Info Not created.
					</div>
				</c:if>
				<div class="row">
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
							<c:if test="${!empty vehOwner}">
								<c:forEach items="${vehOwner}" var="vehOwner">
									<div class="col-md-4 col-sm-5 col-xs-12">
										<div class="box">
											<div class="box-body">
												<div class="row">
													<div class="btn-group">
														<a class="btn btn-default btn-sm dropdown-toggle"
															data-toggle="dropdown" href="#"> <span
															class="fa fa-cog"></span> <span class="caret"></span>
														</a>
														<ul class="dropdown-menu pull-right">
															<li><sec:authorize
																	access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
																	<a
																		href="editVehicleOwner.in?VEHID=${vehOwner.VEHID}&VO=${vehOwner.VOID}"
																		class="confirmation"
																		onclick="return confirm('Are you sure you Want to Edit Owner?')">
																		<span class="fa fa-pencil"></span> Edit
																	</a>
																</sec:authorize></li>
															<li><sec:authorize
																	access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
																	<a
																		href="deleteVehicleOwner.in?VEHID=${vehOwner.VEHID}&VO=${vehOwner.VOID}"
																		class="confirmation"
																		onclick="return confirm('Are you sure you Want to delete Owner?')">
																		<span class="fa fa-trash"></span> Delete
																	</a>
																</sec:authorize></li>
														</ul>
													</div>
												</div>
												<div class="row">
													<table class="table table-striped">
														<tbody>
														
															<tr class="row">
																<th class="key">Owner Name:</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_NAME}" /></td>
															</tr>
														
														<c:if test="${configuration.showVehicleOwnerSrNo}">
															<tr class="row">
																<th class="key">Owner Serial No:</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_SERIAL}" /></td>
															</tr>
															</c:if>	
															<tr class="row">
																<th class="key">Owner Aadhar No :</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_AADHARNO}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Owner PAN No :</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_PANNO}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Owner RC Address :</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_ADDRESS} ," /><br> <c:out
																		value="${vehOwner.VEH_OWNER_CITY} ," /><br> <c:out
																		value="${vehOwner.VEH_OWNER_STATE} ," /> <c:out
																		value="${vehOwner.VEH_OWNER_COUNTRY} ." /><br> <c:out
																		value="pin: ${vehOwner.VEH_OWNER_PINCODE}" /></td>
															</tr>
															<tr class="row">
																<th class="key">Owner Phone No:</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_OWNER_PHONE}" /></td>
															</tr>
															<c:if test="${configuration.showDriverNameInAddOwnerShip}">
															<tr class="row">
																<th class="key">Driver Name :</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_DRIVER_NAME}" /></td>
															</tr>
															</c:if>
															<c:if test="${configuration.showDriverPhoneNoInAddOwnerShip}">
															<tr class="row">
																<th class="key">Driver Phone :</th>
																<td class="value"><c:out
																		value="${vehOwner.VEH_DRIVER_PHONE}" /></td>
															</tr>
															</c:if>
														</tbody>
													</table>
												</div>
												<div class="row">
													<small class="text-muted"><b>Created by :</b> <c:out
															value="${vehOwner.CREATEDBY}" /></small> | <small
														class="text-muted"><b>Created date: </b> <c:out
															value="${vehOwner.CREATED_DATE}" /></small> | <small
														class="text-muted"><b>Last updated by :</b> <c:out
															value="${vehOwner.LASTMODIFIEDBY}" /></small> | <small
														class="text-muted"><b>Last updated date:</b> <c:out
															value="${vehOwner.LASTUPDATED_DATE}" /></small>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:if>
							<c:if test="${empty vehOwner}">
								<div class="main-body">
									<p class="lead text-muted text-center t-padded">
										<spring:message code="label.master.noresilts" />
									</p>

								</div>
							</c:if>
						</sec:authorize>
					</div>
				</div>
			</div>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript">
	function validateFields(){
	if($("#showVehicleOwnerSrNo").val() == true || $("#showVehicleOwnerSrNo").val() == 'true'){
		if($("#OwnerName").val() == "" || $("#OwnerName").val() == null){
			showMessage('info','Please Enter Vehicle Owner Name')
			return false;
		}
	} 
		
	if($("#OwnerName").val() == "" || $("#OwnerName").val() == null){
		showMessage('info','Please Enter Vehicle Owner Name')
		return false;
	}
	if($("#OwnerPhone").val() == "" || $("#OwnerPhone").val() == null){
		showMessage('info','Please Enter Owner Phone Number')
		return false;
	}
	if($("#OwnerAddress").val() == "" || $("#OwnerAddress").val() == null){
		showMessage('info','Please Enter Vehicle Owner Address')
		return false;
	}
	if($("#countryId").val() == "" || $("#countryId").val() == null){
		showMessage('info','Please Select Country')
		return false;
	}
	if($("#stateId").val() == "" || $("#stateId").val() == null){
		showMessage('info','Please Select State')
		return false;
	}
	if($("#cityId").val() == "" || $("#cityId").val() == null){
		showMessage('info','Please Select City')
		return false;
	}
	if($("#locationCode").val() == "" || $("#locationCode").val() == null){
		showMessage('info','Please Enter Postal Code')
		return false;
	}
		
	}	
	
	 $(document).ready(function() {
         var img = $("#vehicleImage");
         var iconContainer = $("#iconContainer");

         // Check if the image is loaded
         img.on("load", function() {
             // If loaded, hide the icon
             iconContainer.hide();
         });
     });
		
	</script>

	<!-- <script type="text/javascript">
	$(document).ready(function(){$(".select2").select2(),$("#tagPicker").select2({closeOnSelect:!1})}),$(document).ready(function(){function e(e){for(var E=document.getElementsByTagName("textarea"),g=0;g<E.length;g++)com_satheesh.EVENTS.addEventHandler(E[g],"focus",t,!1),com_satheesh.EVENTS.addEventHandler(E[g],"blur",n,!1);E=document.getElementsByTagName("input");for(g=0;g<E.length;g++)e.indexOf(-1!=E[g].getAttribute("type"))&&(com_satheesh.EVENTS.addEventHandler(E[g],"focus",t,!1),com_satheesh.EVENTS.addEventHandler(E[g],"blur",n,!1));com_satheesh.EVENTS.addEventHandler(document.getElementById("formOwner"),"submit",u,!1),com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditOwner"),"submit",f,!1),document.getElementsByTagName("input")[0].focus(),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerSerial,"blur",a,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerName,"blur",r,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].countryId,"blur",o,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].stateId,"blur",c,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].cityId,"blur",s,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].locationCode,"blur",m,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerPhone,"blur",d,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerAddress,"blur",l,!1)}function t(e){var t=com_satheesh.EVENTS.getEventTarget(e);null!=t&&(t.style.backgroundColor=E)}function n(e){var t=com_satheesh.EVENTS.getEventTarget(e);null!=t&&(t.style.backgroundColor="")}function a(){var e=document.getElementById("OwnerSerial"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerSerial");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerSerialIcon").className="fa fa-check form-text-feedback",document.getElementById("OwnerSerialErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerSerialIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerSerialErrorMsg").innerHTML="Please enter Owner Serial"),t}function r(){var e=document.getElementById("OwnerName"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerName");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerNameIcon").className="fa fa-check form-text-feedback",document.getElementById("OwnerNameErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerNameIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerNameErrorMsg").innerHTML="Please enter Owner Name"),t}function o(){var e=document.getElementById("countryId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCountry");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("locationCountryIcon").className="fa fa-check form-text-feedback",document.getElementById("locationCountryErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCountryIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCountryErrorMsg").innerHTML="Please select warehouse country"),t}function c(){var e=document.getElementById("stateId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationState");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationStateIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationStateErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationStateIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationStateErrorMsg").innerHTML="Please select warehouse state")),t}function s(){var e=document.getElementById("cityId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCity");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationCityIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationCityErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCityIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCityErrorMsg").innerHTML="Please select warehouse city")),t}function m(){var e=document.getElementById("locationCode"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCode");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationCodeIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationCodeErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCodeIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCodeErrorMsg").innerHTML="Please select warehouse pin code")),t}function d(){var e=document.getElementById("OwnerPhone"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerPhone");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerPhoneIcon").className="fa fa-check  form-text-feedback",document.getElementById("OwnerPhoneErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerPhoneIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerPhoneErrorMsg").innerHTML="Please enter Owner Phone")),t}function l(){var e=document.getElementById("OwnerAddress"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerAddress");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerAddressIcon").className="fa fa-check  form-text-feedback",document.getElementById("OwnerAddressErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerAddressIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerAddressErrorMsg").innerHTML="Please enter Owner Address")),t}function u(e){var t=a();t&=r(),t&=o(),t&=c(),t&=s(),t&=m(),t&=d(),(t&=l())||com_satheesh.EVENTS.preventDefault(e)}function f(e){var t=a();t&=r(),t&=o(),t&=c(),t&=s(),t&=m(),t&=d(),(t&=l())||com_satheesh.EVENTS.preventDefault(e)}var E="#FFC";com_satheesh.EVENTS.addEventHandler(window,"load",function(){e("text")},!1)});
	</script> -->
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
	
</div>