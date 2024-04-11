<%@ include file="../../taglib.jsp"%>
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
						<c:if test="${!empty asingmentDto && !configuration.sheetToVehicleType}">
								<a class="btn btn-success btn-sm" href="deleteSheetAssignment.in?ID=${asingmentDto.vehicleInspctionSheetAsingmentId}&vid=${vehicle.vid}"> <i class="fa fa-plus"></i>
									Delete Assignment
								</a>
						</c:if>		
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
						<a
							href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							class="zoom" data-title="Amazing "
							data-footer="The beauty of nature" data-type="image"
							data-toggle="lightbox"> <img
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
						<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
							<c:if test="${!empty asingmentDto}">
									<div class="col-xs-12">
										<div class="box">
											<div class="box-body">
												<br/><br/>
												<div class="row">
													<div class="col-xs-12">
														<h4 class="secondary-header-title">
																
																Assigned Sheet Name   : <a href="showInspectionSheetDetails.in?vehicleInspectionSheetId=${asingmentDto.inspectionSheetId}" target="_blank"> <span style="font-size: 16px;"> ${asingmentDto.inspectionSheetName} </span> </a>
														</h4>
														
														<h4 class="secondary-header-title">
																
																Inspection Start Date  : <a href="#"> <span style="font-size: 16px;"> ${asingmentDto.inspectionStartDateStr} </span> </a>
														</h4>
														
														
														<h4 class="secondary-header-title">
																
																<a data-toggle="modal" href="#" onclick ="showVehicles(${asingmentDto.vid}, ${asingmentDto.inspectionSheetId})" >
																	Count&nbsp
																</a>					
																<c:forEach items="${inspectionSheetList}"
																var="inspectionSheetList">
																<span class="badge  bg-aqua">${inspectionSheetList.noOfVehicleAsigned}</span>
																</c:forEach>
														</h4>
													</div>
												</div>
							
											</div>
											
										</div>
									</div>
							</c:if>
						</sec:authorize>
						
					</div>
				</div>
			</div>
			<!--Pop Up Logic Start--><!--devy-->
			<div class="modal fade" id="myModal" role="dialog">
				<div class="modal-dialog modal-md">
					<input type="hidden" id="  " name="  " value=" " />
					<div class="modal-content">
						<div class="modal-body">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<div class="row invoice-info" id="reportHeader"
								style="font-size: 15px; font-weight: bold;"></div>
						</div>
						<div class="row invoice-info">
							<table id="inspectNoOfVehicleToSheet" style="width: 100%;"
								class="table-hover table-bordered">

							</table>
						</div>
					</div>
				</div>
			</div>
			<!--Pop Up Logic End-->

			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="../../vehicle/VehicleSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>

	<script type="text/javascript">
	$(document).ready(function(){$(".select2").select2(),$("#tagPicker").select2({closeOnSelect:!1})}),$(document).ready(function(){function e(e){for(var E=document.getElementsByTagName("textarea"),g=0;g<E.length;g++)com_satheesh.EVENTS.addEventHandler(E[g],"focus",t,!1),com_satheesh.EVENTS.addEventHandler(E[g],"blur",n,!1);E=document.getElementsByTagName("input");for(g=0;g<E.length;g++)e.indexOf(-1!=E[g].getAttribute("type"))&&(com_satheesh.EVENTS.addEventHandler(E[g],"focus",t,!1),com_satheesh.EVENTS.addEventHandler(E[g],"blur",n,!1));com_satheesh.EVENTS.addEventHandler(document.getElementById("formOwner"),"submit",u,!1),com_satheesh.EVENTS.addEventHandler(document.getElementById("formEditOwner"),"submit",f,!1),document.getElementsByTagName("input")[0].focus(),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerSerial,"blur",a,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerName,"blur",r,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].countryId,"blur",o,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].stateId,"blur",c,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].cityId,"blur",s,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].locationCode,"blur",m,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerPhone,"blur",d,!1),com_satheesh.EVENTS.addEventHandler(document.forms[0].OwnerAddress,"blur",l,!1)}function t(e){var t=com_satheesh.EVENTS.getEventTarget(e);null!=t&&(t.style.backgroundColor=E)}function n(e){var t=com_satheesh.EVENTS.getEventTarget(e);null!=t&&(t.style.backgroundColor="")}function a(){var e=document.getElementById("OwnerSerial"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerSerial");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerSerialIcon").className="fa fa-check form-text-feedback",document.getElementById("OwnerSerialErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerSerialIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerSerialErrorMsg").innerHTML="Please enter Owner Serial"),t}function r(){var e=document.getElementById("OwnerName"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerName");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerNameIcon").className="fa fa-check form-text-feedback",document.getElementById("OwnerNameErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerNameIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerNameErrorMsg").innerHTML="Please enter Owner Name"),t}function o(){var e=document.getElementById("countryId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCountry");if(null!=n)return t?(n.className="form-group has-success has-feedback",document.getElementById("locationCountryIcon").className="fa fa-check form-text-feedback",document.getElementById("locationCountryErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCountryIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCountryErrorMsg").innerHTML="Please select warehouse country"),t}function c(){var e=document.getElementById("stateId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationState");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationStateIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationStateErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationStateIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationStateErrorMsg").innerHTML="Please select warehouse state")),t}function s(){var e=document.getElementById("cityId"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCity");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationCityIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationCityErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCityIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCityErrorMsg").innerHTML="Please select warehouse city")),t}function m(){var e=document.getElementById("locationCode"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grplocationCode");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("locationCodeIcon").className="fa fa-check  form-text-feedback",document.getElementById("locationCodeErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("locationCodeIcon").className="fa fa-remove form-text-feedback",document.getElementById("locationCodeErrorMsg").innerHTML="Please select warehouse pin code")),t}function d(){var e=document.getElementById("OwnerPhone"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerPhone");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerPhoneIcon").className="fa fa-check  form-text-feedback",document.getElementById("OwnerPhoneErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerPhoneIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerPhoneErrorMsg").innerHTML="Please enter Owner Phone")),t}function l(){var e=document.getElementById("OwnerAddress"),t=null!=e.value&&0!=e.value.length,n=document.getElementById("grpOwnerAddress");return null!=n&&(t?(n.className="form-group has-success has-feedback",document.getElementById("OwnerAddressIcon").className="fa fa-check  form-text-feedback",document.getElementById("OwnerAddressErrorMsg").innerHTML=""):(n.className="form-group has-error has-feedback",document.getElementById("OwnerAddressIcon").className="fa fa-remove form-text-feedback",document.getElementById("OwnerAddressErrorMsg").innerHTML="Please enter Owner Address")),t}function u(e){var t=a();t&=r(),t&=o(),t&=c(),t&=s(),t&=m(),t&=d(),(t&=l())||com_satheesh.EVENTS.preventDefault(e)}function f(e){var t=a();t&=r(),t&=o(),t&=c(),t&=s(),t&=m(),t&=d(),(t&=l())||com_satheesh.EVENTS.preventDefault(e)}var E="#FFC";com_satheesh.EVENTS.addEventHandler(window,"load",function(){e("text")},!1)});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VIP/VehicleInspectionParameterAdd.js"/>"></script>	<!--devy-->
</div>