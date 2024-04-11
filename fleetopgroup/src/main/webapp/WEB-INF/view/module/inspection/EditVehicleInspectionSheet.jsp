<%@page import="ch.qos.logback.classic.Logger"%>
<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
	<style>
	
	td, th{
	padding : 15px;
	}</style>
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
						<!-- <a href="AddInspectionSheet.in" class="btn btn-success" >
							<span class="fa fa-plus" id="AddJobType"> Create New Inspection Sheet</span>
						</a> -->

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
						<h3 style="text-align: center;">Edit Vehicle Inspection Sheet</h3>
					</div>
					<br />
					<div class="box">
						<div class="box-body">
							<input type="hidden" name="ISID_id" id="ISID_id"
								value="${ISID_id}">

							<div class="row">
								<label class="L-size control-label" id="Type">Inspection
									Sheet Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="inspectionSheetName"
										id="inspectionSheetName" readonly="readonly"
										placeholder="Enter Sheet Name" />
								</div>
							</div>
							<br />
							<div class="row" id="grpvehicleGroup">
								<c:if test="${!configuration.sheetToVehicleType}">
								<label class="L-size control-label">Vehicle Group :<abbr
									title="required"></abbr>
								</label>
								</c:if>
								<c:if test="${configuration.sheetToVehicleType}">
								<label class="L-size control-label">Vehicle Type :<abbr
									title="required"></abbr>
								</label>
								</c:if>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										name="vehicleGroupName"
										value="${inspectionSheetList.vehicleGroup}"
										id="vehicleGroupId" readonly="readonly"
										placeholder="Enter Sheet Name" />
								</div>
							</div>
							<div id="addParameter1">
								<fieldset id="addParameter" class=""></fieldset>
							</div>
							<div >
								<button type="submit" id="createTripRoute" onclick=" return updateInspectionSheet();"class="btn btn-success">Update Inspection Sheet</button>
								<button class="add_field_button btn btn-success" class="input_fields_wrap" id ="btn" value = "0" onclick ="addNewField();"><i class="fa fa-plus"></i>
							</div>

							<!-- <div class="input_fields_wrap">
								<button class="add_field_button btn btn-success">
									<i class="fa fa-plus"></i>
								</button>

							</div> -->
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


<!-- <script type="text/javascript">
		$(document).ready(function() {
			
			$('#vehicleGroup').select2();
			
		});
		
	</script> -->


<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VIP/VehicleInspectionParameterEdit.js"/>"></script>	
<%-- <script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/inspection/createInspectionSheet.js" />"></script> --%>
</div>