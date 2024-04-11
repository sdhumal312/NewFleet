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
						<a href="open" >Home</a>/ <a href="addClothTypes.in">New Upholstery Types</a>
				</div>
				<div class="pull-right">
			
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES"))) {
						%>
						
  						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Add Upholstery Type</span>
						</button>
  						<% } %>
					
				</div>
			</div>
		</div>
	</section>
	<section class="panel panel-success">

		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_BATTERY_INVENTORY"))) {%>
			<div class="row" id="searchData">
				<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Upholstery Type</span> 
							<span id="totalClothType" class="info-box-number"></span>
						</div>
					</div>
				</div>
				
			</div>
		<% } %>
	</section>
		
	<div class="content" >
	
	<div class="modal fade" id="addManufacturer" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">New Upholstery
								Type</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="clothTypeName" id="clothTypeName"
										placeholder="Enter  Name" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Description :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="description"
										maxlength="249" name="description"
										placeholder="Enter description" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveClothTypes();">
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
		
		<div class="modal fade" id="editClothType" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="clothTypesId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Upholstery
								Type</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Name :</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="clothTypeName" id="editclothTypeName"
										placeholder="Enter  Name" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Description :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="editdescription"
										maxlength="249" name="description"
										placeholder="Enter description" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateClothTypes();">
								<span>Update</span>
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


		<div class="main-body">
			<div class="box">
				<div class="box-body">

					<div class="table-responsive">
						<table id="VendorPaymentTable" class="table table-hover table-bordered">

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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/batteryUtility.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/addBatteryInventory.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/ViewClothTypes.js"></script>