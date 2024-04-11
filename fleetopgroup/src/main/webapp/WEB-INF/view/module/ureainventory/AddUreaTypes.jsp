<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
		
		<sec:authorize access="hasAuthority('UREA_EDIT_MASTER')">
			<input id="ureaEditId" access="hasAuthority('UREA_EDIT_MASTER')" type="text" class="hide" value="true"/>
		</sec:authorize>		
		
		<sec:authorize access="!hasAuthority('UREA_EDIT_MASTER')">
			<input id="ureaEditId" access="!hasAuthority('UREA_EDIT_MASTER')" type="text" class="hide" value="false"/>
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('UREA_DELETE_MASTER')">
			<input id="ureaDeleteId" access="hasAuthority('UREA_DELETE_MASTER')" type="text" class="hide" value="true"/>
		</sec:authorize>		
		
		<sec:authorize access="!hasAuthority('UREA_DELETE_MASTER')">
			<input id="ureaDeleteId" access="!hasAuthority('UREA_DELETE_MASTER')" type="text" class="hide" value="false"/>
		</sec:authorize>
	
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="addUreaTypes.in">New Urea Manufacturer</a>
				</div>
				<div class="pull-right">
			
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_TYPES"))) {
						%>
						
  						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType">Add Urea Manufacturer</span>		<!--Check-->
						</button>
  						<% } %>
					
				</div>
			</div>
		</div>
	</section>
	<section class="panel panel-success">

		<% if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_TYPES"))) {%>
			<div class="row" id="searchData">
				<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Urea Manufacturer</span> 
							<span id="totalUreaType" class="info-box-number"></span>
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
							<h4 class="modal-title" id="JobType">New Urea Manufacturer</h4>								
						</div>
						
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Manufacturer Name <abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="ureaTypeName" id="ureaTypeName"
										placeholder="Enter Manufacturer Name" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Description </label>
								<div class="I-size">
									<input type="text" class="form-text" id="description"
										maxlength="249" name="description"
										placeholder="Enter Description" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveUreaTypes();">
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
		
		<div class="modal fade" id="editUreaType" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="ureaManufacturerId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Urea Manufacturer</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Manufacturer Name <abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="UreaTypeName" id="editUreaTypeName"
										placeholder="Enter  Name" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Description </label>
								<div class="I-size">
									<input type="text" class="form-text" id="editdescription"
										maxlength="249" name="description"
										placeholder="Enter description" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateUreaTypes();">
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
						<table id="UreaTypeTable" class="table table-hover table-bordered">

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
<!-- <script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripAddLoadTypes.js"></script> -->

<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureainventory/AddUreaTypes.js"></script>


		


