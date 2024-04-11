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
						<a href="open" >Home</a>/ <a href="addClothTypes.in">Assign Max Qnty To Vehicle</a>
				</div>
				<div class="pull-right">
			
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_CLOTH_TYPES"))) {
						%>
						
  						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Assign Max Qnty To Vehicle</span>
						</button>
  						<% } %>
					
				</div>
			</div>
		</div>
	</section>
	<%-- <section class="panel panel-success">

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
	</section> --%>
		
	<div class="content" >
	
	<div class="modal fade" id="addManufacturer" role="dialog">
			<div class="modal-dialog" style="width:750px;">
				<!-- Modal content-->
				<div class="modal-content">
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Assign Max Qnty To 
								Vehicle</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label">Vehicle Name <abbr
									title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" id="vehicleId"
										name="vehicleId" style="width: 100%;"
										required="required"
										placeholder="Please Enter 2 or more Vehicle Name" />
									<p class="help-block">Select One Or More Vehicle</p>
								</div>
							</div>
							<br/>
							<br/>
							<br/>
							
						<div class="panel panel-success">
        		    		<div class="panel-body"> 
								<div class="row1" id="grpmanufacturer" class="form-group">
									<label class="L-size control-label" for="manufacurer">Upholstery Types :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
									<input type="hidden" id="upholsteryType"
										name="upholsteryType" style="width: 100%;" required="required"
										placeholder="Please Enter 2 or more Cloth Types Name" />
									</div>
								</div>
								<br/>
								<br/>
							
								<div class="row">
									<label class="L-size control-label" id="Type">Assign Max Qty :
									<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="text" class="form-text" id="maxQuantity"
										maxlength="249" name="maxQuantity" required="required"
										onkeypress="return isNumber(event)" 
										placeholder="Enter Max Qnty For Assignment to a Vehicle" />
									</div>
								</div>
								<br/>
														
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
							<br> <label class="error" id="errorINEACH"
								style="display: none"></label>
						</div>	
							
							<div class="row1">
								<div class="input_fields_wrap">
									<button class="add_field_button btn btn-info"
										data-toggle="tip"
										data-original-title="Click add one more upholstery type">
										<i class="fa fa-plus"></i> Add More
									</button>
								</div>
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveMaxAllowed();">
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
		
		<div class="modal fade" id="editMaxAllowed" role="dialog">
			<div class="modal-dialog" style="width:750px;">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="maxAllowedSettingId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Max Qnty Assigned To The
								Vehicle </h4>
						</div>
						<div class="modal-body">
						
							<div class="row1">
								<label class="L-size control-label">Vehicle Name <abbr
									title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" id="editVehicleId"
										name="editVehicleId" style="width: 100%;"
										required="required" readonly="readonly"
										placeholder="Please Enter 2 or more Vehicle Name" />
									<p class="help-block">Assigned Vehicle</p>
								</div>
							</div>
							<br/>
							<br/>
							<br/>
							
						<div class="panel panel-success">
        		    		<div class="panel-body"> 
								<div class="row1" id="grpmanufacturer" class="form-group">
									<label class="L-size control-label" for="manufacurer">Upholstery Types :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
									<input type="hidden" id="editUpholsteryType"
										name="editUpholsteryType" style="width: 100%;" required="required"
										value="${data.maxAllowedSettingId.clothTypeName}
										placeholder="Please Enter 2 or more Cloth Types Name" />
									</div>
								</div>
								<br/>
								<br/>
							
								<div class="row">
									<label class="L-size control-label" id="Type">Assign Max Qty :
									<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="text" class="form-text" id="editMaxQuantity"
										maxlength="249" name="editMaxQuantity" required="required" onkeypress="return isNumber(event)" 
										placeholder="Enter Max Qnty For Assignment to a Vehicle" />
									</div>
								</div>
								<br/>
														
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
						</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateMaxAllowedSettingById();">
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
					<div class="row">
					<div class='col-sm-3'>
						<input type="hidden" id="vehId" name="vid"
							style="width: 100%;" required="required" 
							placeholder="Search Vehicle Name" /> 
					</div>
					<div>
							<button type="submit" class="btn btn-primary" onclick="searchByVehicle();">
								<span>Search</span>
							</button>
					</div>
					</div>
					<div class="table-responsive">
						<input type="hidden" id="startPage" value="${SelectPage}">
						<table id="VendorPaymentTable1" class="table table-hover table-bordered">
						</table>
					</div>
				</div>
			</div>	
		</div>	
	
		<div class="main-body" id ="mainTable">
			<div class="box">
				<div class="box-body">
				 	
					<div class="table-responsive">
						<input type="hidden" id="startPage" value="${SelectPage}">
						<table id="VendorPaymentTable" class="table table-hover table-bordered">
						</table>
					</div>
				</div>
			</div>
			<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
						</ul>
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/AddVehicleClothMaxAllowed.js"></script>
