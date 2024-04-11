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
					<a href="open" >Home</a>/ <a href="TyreExpense.in">Vehicle Accident Type</a>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal" data-target="#addVehicleAccidentTypeModal" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType">Add Vehicle Accident Type</span>
					</button>
				</div>
			</div>
		</div>
	</section>
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
	<input type="hidden" id="editVehicleAccidentTypeMasterId" >
	<section class="panel panel-success">
		<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
					<div class="info-box-content">
						<span class="info-box-text" >Total Vehicle Accident Type</span> 
						<span id="vehicleAccidentTypeCount" class="info-box-number"></span>
					</div>
				</div>
			</div>
		</div>
	</section>
		
	<div class="content" >
	<div class="modal fade" id="addVehicleAccidentTypeModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Vehicle Accident Type</h4>
					</div>
					<div class="modal-body">
					<div class="row">
						<label class="L-size control-label" id="Type"> Vehicle Accident Type :</label>
						<div class="I-size">
							<input type="text" class="form-text" required="required" id="addVehicleAccidentTypeMasterName" placeholder="Enter Vehicle Accident Type" />
						</div>
					</div>
					<div class="row">
						<label class="L-size control-label" id="Type">Description :</label>
						<div class="I-size">
							<input type="text" class="form-text" id="addDescription" maxlength="249" name="description" placeholder="Enter description" />
						</div>
					</div>
					<br />
					</div>
					<div class="modal-footer">
						<button type="submit" id="saveVehicleAccidentType" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editVehicleAccidentTypeModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Vehicle Accident Type</h4>
					</div>
					<div class="modal-body">
					<div class="row">
						<label class="L-size control-label" id="Type"> Vehicle Accident Type :</label>
						<div class="I-size">
							<input type="text" class="form-text" required="required" id="editVehicleAccidentTypeMasterName" placeholder="Enter Vehicle Accident Type" />
						</div>
					</div>
					<div class="row">
						<label class="L-size control-label" id="Type">Description :</label>
						<div class="I-size">
							<input type="text" class="form-text" id="editDescription" maxlength="249" name="description" placeholder="Enter description" />
						</div>
					</div>
					<br />
					</div>
					<div class="modal-footer">
						<button type="submit" id="updateVehicleAccidentTypeMaster" class="btn btn-primary">
							<span><spring:message code="label.master.Save" /></span>
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

				<div class="table-responsive">
					<table class="table">
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">Accident Type</th>
							<th class="fit ar">Description</th>
							<th class="fit ar">Action</th>
						</tr>
					</thead>
					<tbody id="vehicleAccidentTypeTable">
					
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleAccidentTypeMaster.js"></script>