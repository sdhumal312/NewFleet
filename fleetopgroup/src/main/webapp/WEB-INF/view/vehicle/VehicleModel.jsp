<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open" >Home</a>/ <a href="TyreExpense.in">New Vehicle Model</a>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal" data-target="#addVehicleModel" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType">Add Vehicle Model</span>
					</button>
				</div>
			</div>
		</div>
	</section>
	<section class="panel panel-success">
		<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
					<div class="info-box-content">
						<span class="info-box-text" >Total Vehicle Model</span> 
						<span id="vehicleModelCount" class="info-box-number"></span>
					</div>
				</div>
			</div>
		</div>
	</section>
		
	<div class="content" >
	<input type="hidden" id="vehicleModelTyreLayoutConfig" value="${configuration.vehicleModelTyreLayout}">
	<div class="modal fade" id="addVehicleModel" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >New Vehicle Model</h4>
					</div>
					<div class="modal-body">
					<div class="row">
						<label class="L-size control-label" id="Type"> Vehicle Manufacturer :</label>
						<div class="I-size">
							<select id="manufacturer" class="form-text" style="width: 100%">
														<option value="-1">Select Manufacturer</option>
														<c:forEach items="${manufacturer}" var="manufacturer">
															<option value="${manufacturer.vehicleManufacturerId}"><c:out
																	value="${manufacturer.vehicleManufacturerName}" /></option>
														</c:forEach>
													</select>
						</div>
					</div>
						<div class="row">
							<label class="L-size control-label" id="name"> Vehicle Model Name :</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="150"  id="addVehicleModelName"
									placeholder="Enter  Name" />
							</div>
						</div>
						<div class="row">
							<label class="L-size control-label" id="Type">Description :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="addDescription"
									maxlength="249" name="description"
									placeholder="Enter description" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" id="saveVehicleModel" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editVehicleModel" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" >Edit Vehicle Model</h4>
				</div>
				<div class="modal-body">
				
					<div class="row">
						<label class="L-size control-label" id="Type"> Vehicle Manufacturer :</label>
						<div class="I-size">
							<select id="editManufacturer"  class="select2" style="width: 100%">
														<option value="-1"></option>
														<c:forEach items="${manufacturer}" var="manufacturer">
															<option value="${manufacturer.vehicleManufacturerId}"><c:out
																	value="${manufacturer.vehicleManufacturerName}" /></option>
														</c:forEach>
													</select>
						</div>
					</div>
				
					<div class="row">
						<label class="L-size control-label" id="Type"> Vehicle Model Name :</label>
						<div class="I-size">
							<input type="hidden" id="editVehicleModelId"> 
							<input type="text" class="form-text" required="required"
								maxlength="150"  id="editVehicleModelName"
								placeholder="Enter  Name" />
						</div>
					</div>
					<div class="row">
						<label class="L-size control-label" id="Type">Description :</label>
						<div class="I-size">
							<input type="text" class="form-text" id="editDescription"
								maxlength="249" name="description"
								placeholder="Enter description" />
						</div>
					</div>
					<br />
				</div>
				<div class="modal-footer">
					<button type="submit" id="updateVehicleModel"class="btn btn-primary" >
						<span>Update</span>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span id="Close"><spring:message code="label.master.Close" /></span>
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="main-body">
		<div class="box">
			<div class="box-body">
				<input type="hidden" id="companyId" value="${companyId}">
				<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Manufacturer</th>
							<th class="fit ar">Model</th>
							<th class="fit ar">Description</th>
							<th class="fit ar">Action</th>
						</tr>
					</thead>
					<tbody id="vehicleModelTable">
					
					</tbody>

					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleModel.js"></script>