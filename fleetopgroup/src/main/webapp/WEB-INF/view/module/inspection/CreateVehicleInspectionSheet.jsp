<%@page import="ch.qos.logback.classic.Logger"%>
<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	
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
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/ViewInspectionSheet"/>">Inspection Sheet
					</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<a href="AddInspectionSheet.in" class="btn btn-success"> <span
							class="fa fa-plus" id="AddJobType"> Create New Inspection
								Sheet</span>
						</a>

						<a href="ViewInspectionSheet.in" class="btn btn-info"> Cancel
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saved eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Manufacturers Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.already eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Battery Manufacturers Type Already Exists.
			</div>
		</c:if>

		<div class="row">
			<div class="col-xs-12">
				<div class="main-body">
					<div class="box">
						<h3 style="text-align: center;">Vehicle Inspection Sheet</h3>
					</div>
					<br />
					<div class="box">
						<div class="box-body">
							<form id="formTripRoute"
								action="<c:url value="/saveVehicleInspectionSheet.in"/>"
								method="post" enctype="multipart/form-data" name="formTripRoute"
								role="form" class="form-horizontal">
								<input type="hidden" id="sheetToVehicleTypeConfig" value="${configuration.sheetToVehicleType}"  />
								<div class="row">
									<label class="L-size control-label" id="Type">Inspection
										Sheet Name :</label>
									<div class="I-size">
										<input type="text" class="form-text" required="required"
											maxlength="150" name="inspectionSheetName"
											id="inspectionSheetName" placeholder="Enter Sheet Name" />
									</div>
								</div>
								<br />
								<c:if test="${!configuration.sheetToVehicleType}">
								<input type="hidden" name="VehicleLocation" value="0"  />
									<div class="row" id="grpvehicleGroup">
										<label class="L-size control-label">Vehicle Group :<abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<select id="vehicleGroup" class="required"
												style="width: 100%;" name="vehicleGroupId"
												multiple="multiple">
												<c:forEach items="${vehicleGroup}" var="vehicleGroup">
													<option value="${vehicleGroup.gid}">
														<c:out value="${vehicleGroup.vGroup}" />
													</option>
												</c:forEach>
											</select>

										</div>
									</div>
								</c:if>
								<c:if test="${configuration.sheetToVehicleType}">
								<input type="hidden" id="AlreadyAssignrd" value="${AlreadyAssigned}" >
									<div class="row">
										<label class="L-size control-label">Vehicle Type : <abbr
											title="required">*</abbr>
										</label>
										<div class="I-size">
											<select id="vehicleGroup" class="required"
												style="width: 100%;" name="vehicleTypeId"
												multiple="multiple">
												<c:forEach items="${group}" var="vehicleTypeId">
													<option value="${vehicleTypeId.tid}">
														<c:out value="${vehicleTypeId.vtype}" />
													</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<br />
									
									<div class=row>
												<div class="L-size control-label" >Branch :<abbr
											title="required">*</abbr>
												</div>
												<div class="I-size" >
												<input type="hidden" id="VehicleLocation" name="VehicleLocation" style="width: 100%;" value="0" placeholder="Please Enter Branch Name" />
												</div>
											</div>
								</c:if>


								<fieldset>
									<legend>Add Parameters</legend>
									<div class="row" class="form-group" id="grprouteExpense">

										<div class="col-md-2">
											<select class="form-text select2" style="width: 100%;"
												name="inspectionParameter" id="Expense" onchange="validateInspectionParameter(this)">
												<option>Please Select</option>
											</select>
										</div>
										<div class="col-md-2" id="grprouteAmount">
											<input type="number" class="form-text" name="frequency"
											
												id="frequency" placeholder="Frequency in Days" min="1"
												onkeypress="return isNumberKeyWithDecimal(event,id);">
										</div>
										<div class="col-md-1">
											<label>is Mandatory :</label>
										</div>
										<div class="col-md-1">
											<select class="form-text select2" name="requiredType"
												id="requiredType">
												<option value="false">NO</option>
												<option value="true">YES</option>
											</select>
										</div>
										<div class="col-md-4" id="grprouteReference">
											<label class="control-label">Photo Needed : </label> <label
												class="radio-inline"> <input name="photoGroup"
												id="inputPhotoGroupYes" value="true" type="radio" />Yes
											</label> <label class="radio-inline"> <input
												name="photoGroup" id="inputPhotoGroupYesNo" value="false"
												type="radio" checked />No
											</label> <label style="padding-left: 5px;" class="control-label">Text
												Needed : </label> <label class="radio-inline"> <input
												name="textGroup" id="textGroupYes" value="true" type="radio" />Yes
											</label> <label class="radio-inline"> <input name="textGroup"
												id="textGroupNo" value="false" type="radio" checked />No
											</label>
										</div>

										<div class="input_fields_wrap">
											<button class="add_field_button btn btn-success">
												<i class="fa fa-plus"></i>
											</button>

										</div>
								</fieldset>
								<input type="hidden" name="photoRequired" id="photoRequired">
								<input type="hidden" name="textRequired" id="textRequired">

								<div class="form-group">
									<label class="L-size control-label" for="vehicle_theft"></label>
									<div class="col-sm-5">
										<fieldset class="form-actions">
											<button type="submit" id="createTripRoute"
												onclick="return validateInspectionForm();"
												class="btn btn-success">Create Inspection Sheet</button>
											<a class="btn btn-link"
												href="<c:url value="/ViewInspectionSheet.in"/>">Cancel</a>
										</fieldset>
									</div>
								</div>

							</form>
						</div>
					</div>



				</div>

			</div>


		</div>
</div>

</div>
</section>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>


<script type="text/javascript">
		$(document).ready(function() {
			
			$('#vehicleGroup').select2();
			$('#vehicleTypeId').select2();
			
			var info = $('#AlreadyAssignrd').val();
		    if(info){
		    	swal({
		    		title: "Already Assigned!",
		    		text:"Selected Vehicle type is Already Assigned with another sheet !",
		    		type: "warning",
		    		width: 300,
		    		height: 200,
		    		showCancelButton: !0,
		    		showConfirmButton: 0,
		    		cancelButtonClass: "btn btn-danger",
		    		preConfirm: function () {
		    		}
		    	})
		    }
			
			
		});
		
	</script>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  


<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/createInspectionSheet.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/AsignVehicleInspectionSheetToVehicleGroup.js" />"></script>
</div>