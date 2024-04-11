<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a href="<c:url value="/vehicle/1/1"/>">Vehicle</a> /
					<a href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>"><c:out value="${vehicle.vehicle_registration}" /></a> / 
					<span> Vehicle Document</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
						<a class="btn btn-success btn-sm" data-toggle="modal"
							data-target="#vehicleDocuemnt"> <i class="fa fa-plus"></i>
							Add Vehicle Document
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('ADD_RENEWAL')">
						<a class="btn btn-info btn-sm"
							href="<c:url value="/renewalReminderAjaxAdd.in?vid=${vehicle.vid}&renewalSubTypeId=0"/>"> <span
							class="fa fa-plus"></span> Add Renewal
						</a>
					</sec:authorize>
						<button type="button" class="btn btn-info" onclick="renewalHistoryModal();"> Renewal History</span>
					</button>
					<a class="btn btn-warning btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
				<div class="box-body">
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
				</div>
			</sec:authorize>
		</div>
	</section>
	
	<section class="content-body">
		<input type="hidden" name="vid" id="vid" value="${vehicle.vid}" />
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<h4>
									<span class="label label-success">Vehicle Document</span>
								</h4>
								<div class="table-responsive">
									<table id="VendorPaymentTable" class="table table-hover table-bordered">
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<h4>
									<span class="label label-info">Pending Mandatory Renewals</span>
								</h4>
								<div class="table-responsive">
									<table id="VendorPaymentTable1" class="table table-hover table-bordered">
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="main-body">
						<div class="box">
							<div class="box-body">
								<h4>
									<span class="label label-warning">Renewal Reminders</span>
								</h4>
								<div class="table-responsive">
									<table id="VendorPaymentTable2" class="table table-hover table-bordered">
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
			
			<div class="col-md-2 col-sm-2 col-xs-12">
				<%@include file="VehicleSideMenu.jsp"%>
			</div>
			
		</div>
	</section>
	
	
	<!-- Modal  and create the javaScript call modal -->
	<div class="modal fade" id="vehicleDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Upload Vehicle Document</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" id="unique-one-time-token" name="unique-one-time-token" value="${accessToken}">
						<input type="hidden" id="validateDoublePost" name="validateDoublePost" value="true">
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">DocumentName <abbr title="required">*</abbr>
								</label>
								<div class="col-md-6">
									<select name="docTypeId" id="docTypeId" class="form-text">
										<c:forEach items="${vehicledoctype}" var="vehicledoctype">
											<option value="${vehicledoctype.dtid}"><c:out value="${vehicledoctype.vDocType}" /></option>
										</c:forEach>
									</select> <span class="help-block">Categorize this vehicle.</span>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">Document <abbr title="required">*</abbr>
								</label>
								<div class="col-md-6">
									<input type="file" name="input-file-preview" id="file" multiple="multiple"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" value="Submit" id="btnSubmit" class="btn btn-success" />
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close"><spring:message
									code="label.master.Close" /></span>
						</button>
					</div>
				</form>	
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editVehicleDocuemnt" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<form method="POST" enctype="multipart/form-data" id="editFileUploadForm">	
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit Vehicle Document</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id" id="id" />
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">Document Name <abbr title="required">*</abbr></label>
								<div class="col-md-6">
									<input type="text" class="form-text" name="editDocTypeName"  id="editDocTypeName" readonly="readonly">
								</div>
								<input type="hidden" name="editDocTypeId" id="editDocTypeId"  />
							</div>
						</div>
						<br>
						<div class="row">
							<div class="form-group">
								<label class="col-md-3">Document <abbr title="required">*</abbr>
								</label>
								<div class="col-md-6">
									<input type="file" name="input-file-preview" id="editFile" multiple="multiple"></input>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<input type="button" value="Revise Document" id="editBtnSubmit" class="btn btn-success" />
						<button type="button" class="btn btn-default"
							data-dismiss="modal">
							<span id="Close"><spring:message
									code="label.master.Close" /></span>
						</button>
					</div>
				</form>	
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="renewalHistoryModel" role="dialog">
		<div class="modal-dialog" style="width:1250px;">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Renewal History</h4>
					</div>
					<div class="modal-body">
					<div class="table-responsive">
						<table id="renewalHistoryTable" class="table table-hover table-bordered">
						</table>
					</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
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
	<script type='text/javascript' src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
	
	
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/ViewVehicleDocument.js" />"></script>
	
</div>
