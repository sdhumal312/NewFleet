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
						<a href="open" >Home</a>/ <a href="UreaEntriesShowList.in">New Urea Entry</a>
				</div>
				<div class="pull-right">
						<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_ENTRY"))) {
						%>
						
  						<!-- <a class="btn btn-danger btn-sm" onclick = "showAllVehicleUreaReminderList();">Missing Urea Entries  <span
							class="fa fa-info"></span>
						</a> -->
  						<a class="btn btn-success btn-sm"
							href="<c:url value="/addUreaEntries.in"/>"> <span
							class="fa fa-plus"></span>Add Urea Entry
						</a>
  						<% } %>
				</div>
				<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">UE-</span></span> <input class="form-text" onkeyup="ureaEntriesSearchOnEnter(event);"
										id="ureaInvoiceNumber" name="Search" type="number"
										required="required" min="1" placeholder="ID eg: 1234">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" onclick="ureaEntriesSearch();"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
						<div class="col-md-off-5">
							<div class="col-md-3">
							 <a class="btn btn-info btn-sm" href="<c:url value="/ureaEntriesSearch"/>">
								<span class="fa fa-search"></span> Search
							</a>	
							</div>
						</div>
						
			</div>
		</div>
	</section>
	<section class="panel panel-success">
			<div class="modal fade" id="addImport" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<form method="post" action="<c:url value="/importInventoryBattery.in"/>"
							enctype="multipart/form-data">
							<div class="panel panel-default">
								<div class="panel-heading clearfix">
									<h3 class="panel-title">Import File</h3>
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<br>
										<div class="row1">
											<label class="L-size control-label">Warehouse
												location From : </label>
											<div class="I-size">
												<input required="required" type="hidden" name="location"
													id="warehouselocation2" value="0" style="width: 100%;" placeholder="All" />
											</div>
										</div>
										<br/>
										<div class="row1">
											<div class="L-size">
												<label> Import Only xlsx File: </label>
											</div>
											<div class="I-size">
												<input type="file" accept=".xlsx" name="import"
													required="required" />
											</div>
										</div>
									</div>
									<div class="row1 progress-container">
										<div class="progress progress-striped active">
											<div class="progress-bar progress-bar-success"
												style="width: 0%">Upload Your Inventory Battery Entries Please
												wait..</div>
										</div>
									</div>
									<div class="modal-footer">
										<input class="btn btn-success"
											onclick="return validateSelection();" name="commit"
											type="submit" value="Import Inventory Battery files"
											class="btn btn-primary" id="myButton"
											data-loading-text="Loading..." class="btn btn-primary"
											autocomplete="off" id="js-upload-submit" value="Add Document"
											data-toggle="modal">
										<button type="button" class="btn btn-link"
											data-dismiss="modal">Close</button>
									</div>
									<script>
										$('#myButton')
												.on(
														'click',
														function() {
															//alert("hi da")
															$(".progress-bar")
																	.animate(
																			{
																				width : "100%"
																			},
																			2500);
															var $btn = $(this)
																	.button(
																			'loading')
															// business logic...

															$btn
																	.button('reset')
														})
									</script>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_CLOTH_TYPES_INVENTORY"))) {%>
			<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Upholstery Invoice</span> 
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
			if(!permission.contains(new SimpleGrantedAuthority("ADD_UREA_ENTRY"))) {
		%>
			Unauthorized Access !!
		<% } %>
		<% 
			if(permission.contains(new SimpleGrantedAuthority("ADD_UREA_ENTRY"))) {
		%>
		<br/>
			
			
			
		<% } %>
	</div>
</div>

	<div class="modal fade" id="ureaAlertModal" role="dialog" >
		<div class="modal-dialog" style="width:1250px;">
			<!-- Modal content-->
			<div class="modal-content" >
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >Missing Urea Entries</h4>
					</div>
					<div class="modal-body" >
						<div class="box">
							<div class="box-body">
								<div class="table-responsive">
									<input type="hidden" id="startPage" value="${SelectPage}"> 
									<table class="table table-hover table-bordered">
										<thead>
											<th> SR NO </th>
											<th> Vehicle Registration </th>
											<th> Last Urea Entry Number </th>
											<th> Last Urea Entry Date </th>
											<th> Last Urea Entry Odometer </th>
											<th> Current Odometer</th>
											<th> Max Odometer </th>
										</thead>
										<tbody id="ureaAlertTable"  ></tbody>
									</table>
								</div>
							</div>
						</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureaentries/ViewUreaEntriesList.js"></script>
<script src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/ureaentries/UreaEntriesUtility.js"></script>