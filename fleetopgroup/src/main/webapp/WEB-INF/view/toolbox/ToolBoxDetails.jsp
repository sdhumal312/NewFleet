<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>">
						<spring:message code="label.master.home" />
					</a> / 
					<a href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / 
					<a href="<c:url value="showVehicle.in?vid=${vehicle.vid}"/>">
					<c:out value="${vehicle.vehicle_registration}" /></a> / 
					<span> Vehicle ToolBox Assign</span>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal"
						data-target="#addToolBoxDetails" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType"> Add ToolBox Details </span>
					</button>
				</div>
				<div class="pull-right">
				<input type="hidden" id="vid" value="${vid}">
					<a class="btn btn-link btn-sm" href="<c:url value="/vehicle/1/1"/>">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
			
		</div>
	</section>
	<div class="content">
		<div class="modal fade" id="addToolBoxDetails" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Add ToolBox Details</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type"> Tool Box
								Name : <abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="150" name="toolBox" id="toolBox"
									placeholder="Enter ToolBox Name" />
							</div>
						</div>
						<br />

						<div class="row">
							<label class="L-size control-label" id="Type"> No of
								Tools :<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									name="noOfTools" id="noOfTools" min="0.0" maxlength="6"
									placeholder="Please Enter no of Tools"
									onkeypress="return isNumberKey(event,this);" />
							</div>
						</div>
						<br />

						<div class="row">
							<label class="L-size control-label" id="Type">Description
								:</label>
							<div class="I-size">
								<input type="text" class="form-text" id="description"
									maxlength="249" name="description"
									placeholder="Enter description" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary"
								onclick="saveToolBoxDetails(${vid});">
								<span><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="main-body">
			<div class="box">
				<div class="box-body">

					<div class="table-responsive">
						<fieldset>
								<div class="">
									<table class="table">
										<thead>
											<tr class="breadcrumb">
												<th class="fit ar">No</th>
												<th class="fit ar">Tool Box</th>
												<th class="fit ar">Quantity</th>
												<th class="fit ar">Description</th>
												<th class="fit ar">Actions</th>
											</tr>
										</thead>

										<tbody id="toolBoxDetailsTable">
										</tbody>
									</table>
								</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>

	</div>

	<%-- 
	
	
	
	
	
	
	
	<!-- Main content -->
	<section class="content-body">
		<div class="row">
			<div class="col-md-9 col-sm-9 col-xs-12">
				<div class="row">
					<div class="main-body">
						<div class="box-header">
							<div class="row">
								<h3 style="text-align: center;">Assign Tools To Vehicle</h3>
							</div>
						</div>
						<br />
						<div class="box">

							<div class="box-body">
								<input type="hidden" id="vid" value="${vid}">
								<div class="row">
									<label class="L-size control-label" id="Type"> ToolBox
										:<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="hidden" class="form-text" required="required"
											maxlength="150" name="toolBox" id="toolBox"
											placeholder="Please Enter ToolBox Name" />
									</div>
								</div>

								<br />

								<div class="row">
									<label class="L-size control-label" id="Type"> No of
										Tools :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="text" class="form-text" required="required"
											name="noOfTools" id="noOfTools" min="0.0" maxlength="6"
											placeholder="Please Enter no of Tools"
											onkeypress="return isNumberKey(event,this);" />
									</div>
								</div>

								<br />

								<div class="row">
									<label class="L-size control-label" id="Type">Description
										:</label>
									<div class="I-size">
										<input type="text" class="form-text" id="description"
											maxlength="249" name="description"
											placeholder="Enter description" />
									</div>
								</div>

							</div>
							<div class="modal-footer">
								<div class="pull-right">
									<button type="button" class="btn btn-primary"
										onclick="saveToolBoxDetails();">
										<span><spring:message code="Assign" /></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-12">
			<%@include file="../../vehicle/VehicleSideMenu.jsp"%> 
		</div>
	</section> --%>
	
	<%-- <%@include file="../../vehicle/VehicleSideMenu.jsp"%>  --%>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/toolbox/toolBoxDetails.js" />"></script>
	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
</div>