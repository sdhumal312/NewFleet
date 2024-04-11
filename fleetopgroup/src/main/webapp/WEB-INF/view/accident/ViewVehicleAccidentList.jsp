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
						<a href="open" >Home</a>/ <a href="viewVehicleAccidentList.in">Vehicle Accident</a>
				</div>
				<div class="pull-right">
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("VIEW_VEHICLE_ACCIDENT"))) {
						%>
  						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVehicleAccident.in"/>"> <span
							class="fa fa-plus"></span>Add Accident Details
						</a>
  						<% } %>
				</div>
				<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">AD-</span></span> <input class="form-text" onkeyup="accidentEntriesSearchOnEnter(event);"
										id="accidentSearchNumber" name="Search" type="number"
										required="required" min="1" placeholder="ID eg: 1234">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" onclick="accidentEntriesSearch();"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
						<%-- <div class="col-md-off-5">
							<div class="col-md-3">
							 <a class="btn btn-info btn-sm" href="<c:url value="/ureaEntriesSearch"/>">
								<span class="fa fa-search"></span> Search
							</a>	
							</div>
						</div> --%>
						
			</div>
		</div>
	</section>
	<section class="panel panel-success">
			
		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_CLOTH_TYPES_INVENTORY"))) {%>
			<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Vehicle Accident</span> 
							<span id="totalClothInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
				
			</div>
		<% } %>
	</section>
		
	<div class="content" >
		<div class="main-body">
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
		<% 
			if(!permission.contains(new SimpleGrantedAuthority("VIEW_VEHICLE_ACCIDENT"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("VIEW_VEHICLE_ACCIDENT"))) {
		%>
		<br/>
			
		<% } %>
	</div>
</div>


<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/accident/ViewAccidentEntriesList.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/accident/VehicleAccidentUtility.js"></script>