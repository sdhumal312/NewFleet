<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/simplepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/consumption/consumption.css"/>">

<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">UserActivity</a> / Work Order Activity
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'TripSheet Summary Data Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" id="cancel1" href="#">Cancel</a>
				</div>
			</div>
		</div>
		<div class="box">
			<div class="box-body">

				<div class="row" id="proBanner">
					<div class="col-sm-6">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr>
						</label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text"
									name="TRIP_DATE_RANGE" required="required" onchange="dateChange();"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
									<div class="col-sm-6">
											<label class="L-size control-label" for="subscribe" >
												User <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3" style="width: 60%">
												<input class="" placeholder="assignee users" id="subscribe"
													type="hidden" style="width: 100%" onchange="dateChange()" name="assigneeId"
													required="required" ondrop="return false;"> 
											</div>
										</div>
	      		</div>
	      	</div>
	      	<br>	
      	</div>
	</section>
	<section>



			<br>
			
			<div class="row">
				<div id="woCreatedDiv" class="col-sm-4 stretch-card grid-margin">
					<div id="backgroudColourC" class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Work Order Created <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="woCreateCount"></h2>
						</div>
					</div>
				</div>
				<div id="woModifiedDiv" class="col-sm-4 stretch-card grid-margin">
					<div id="backgroudColourM" class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Work Order Modified <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="woUpdatedCount"></h2>
						</div>
					</div>
				</div>
				<div id="woDeletedDiv" class="col-sm-4 stretch-card grid-margin">
					<div id="backgroudColourD" class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Work Order Deleted <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="woDeletedCount"></h2>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<input type="hidden" id="startDate" value="${startDate}">
				<input type="hidden" id="endDate" value="${endDate}">
				<input type="hidden" id="userId" value="${user}">
				<input type="hidden" id="userName" value="${userName}">
				<input type="hidden" id="activityType" value="${activityType}">
				<div id="userActivityListTable" style="display: none;">
				<div class="table-responsive">
				<div id="tableCaption" align="center"
					style="font-size: 20px; font-weight: bold;"></div>
				<div align="center" style="font-size: 12px; font-weight: bold;">
					 <span id="showUserName"></span>
				</div>
				<br> <br>
				<table class="table table-bordered" >
				
					<thead id="tableHead" >
						<tr class="bg-gradient-blueOne card-img-holder text-white">
							<th class="fit ar">WO Number</th>
							<th class="fit ar">Start Date</th>
							<th class="fit ar">Due Date</th>
							<th class="fit ar">Vehicle</th>
							<th class="fit ar">Created Date</th>
							<th id="deletedColoumName" class="fit ar">Last Updated Date</th>
							<th class="fit ar">Cost</th>
						</tr>
					</thead>
					<tbody id="woActivityDataBody">
					</tbody>
					</table>
				</div>
				</div>
			
			</div>
	</section>
	
	<section class="content" id="ResultContent" style="display: none;">
			<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					
					<!--exp  start-->
					<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
										</div>
										<div class="row invoice-info">
											<table id="tripCollExpenseName" style="width: 95%;"
											class="table-hover table-bordered">
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					<!--exp  stop-->
					
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
									<table class="table table-hover table-bordered table-striped" id="companyTable" style="display: none;">
										<tbody id="companyHeader">
											
										</tbody>
									</table>
										
										
										<div id="selectedData" style="text-align: left;">
													<table>
															<tr>
																<td style="display: none; font-weight: bold;" id="companyName"> </td>
															</tr>
														<tbody id="selectedReportDetails">
														</tbody>
													</table>
											</div>
											<br/>
											<br/>
										<div class="row invoice-info">
<!-- 											<table > -->
<!-- 												  <tr> -->
<!-- 												    <td valign="top" width="100%"> -->
												    
														<div id="gridContainer">
														      <div id="myGrid"></div>
														      <div id="pager" style="width:100%;height:20px;"></div>
														      
														 </div>     
<!-- 												    </td> -->
												   
<!-- 												  </tr> -->
<!-- 											</table> -->
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
						</div></div>
					</section>
					
	
	
	
</div>  
	
     	
    <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/userActivity/userActivity.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/userActivity/viewWOActivityData.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>    
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script> 	
	
	
<!-- 	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script> -->
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>

	


	
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>

