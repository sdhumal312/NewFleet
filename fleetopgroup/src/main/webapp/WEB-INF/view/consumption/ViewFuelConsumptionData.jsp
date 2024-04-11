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
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">Consumption</a> / Fuel Consumption
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
				<div class="row" style="display: none;">
					<label class="L-size control-label"> Company : </label>
					<div class="I-size">
						<select id="companyId" style="width: 100%;" onchange="getAllCountData();"></select>
						<input type="hidden" id="compId" value="${CompanyId}">
						<input type="hidden" id="compName" value="${CompanyName}">
					</div>
				</div>
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
						<label class="L-size control-label">Date Type: 
							<abbr title="required">*</abbr>
						</label>
						
						<div class="I-size btn-group"  >
							<label id="creationDateId" class="btn btn-success  btn-sm " onclick="selectDateType(1);">
								Creation
							</label> 
							<label id="transactionDateId" class="btn btn-default  btn-sm" onclick="selectDateType(2);"> 
								Transaction
							</label>
						</div>
					</div>
	      		</div>
	      	</div>	
      	</div>
		<input type="hidden" id="companyId">
	</section>
	<section>
		<!-- <div class="col-md-7 "> -->
			<div class="row">
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Fuel Entry Count <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="entryCount"></h2>
						</div>
					</div>
				</div>
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Fuel Total Consumption <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="totalConsumption"></h2>
						</div>
					</div>
				</div>
				<div id="fuelConsumptionDiv" class="col-sm-3 stretch-card grid-margin">
					<div class="card bg-gradient-blueOne card-img-holder text-white  ">
						<div class="card-body">
							<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" alt="Consumpetion" class="card-img-absolute">
							<h4 class="font-weight-normal mb-3">	
								Fuel Total Amount <em class="mdi mdi-chart-line mdi-24px float-right"></em>
							</h4>
							<h2 class="mb-5" id="totalAmount"></h2>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<input type="hidden" id="defaultCompanyId" value="${companyId}">
				<input type="hidden" id="userId" value="${companyId}">
				<input type="hidden" id="startDate" value="${startDate}">
				<input type="hidden" id="endDate" value="${endDate}">
				<input type="hidden" id="dateType" value="${dateType}">
				<input type="hidden" id="consumptionType" value="${consumptionType}">
				<div class="table-responsive">
					<table class="table">
					<thead >
						<tr class="bg-gradient-blueOne card-img-holder text-white">
							<th class="fit ar">Fuel Number</th>
							<th class="fit ar">Fuel Date</th>
							<th class="fit ar">Vehicle</th>
							<th class="fit ar">Liter</th>
							<th class="fit ar">Price</th>
							<th class="fit ar">Amount</th>
							
						</tr>
					</thead>
					<tbody id="consumptionDataBody">
					</tbody>
					</table>
				</div>
				<!-- <div class="text-center">
					<ul id="navigationBar" class="pagination pagination-lg pager">
						
					</ul>
				</div> -->
			</div>
		<!-- </div> -->
		
		<!-- <div class="col-md-5 "> </div> -->
	</section>
</div>  
	<%-- 
				<div class="row">
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-test99 card-img-holder text-white" onclick="showTripDetails(1);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-3">
									<i class="fa fa-file-text-o"></i> <span>Trip Sheet Created</span>
                    			</h4>
                    			<h2 class="mb-4" id="createdCount" ></h2>
                  			</div>
                		</div>
              		</div>
        		</div>
        		
        		<section class="content2">	
					<div class="box">
						<div class="box-body">
							<div class="row" id="proBanner">
             					<div class="col-12">
	               					<div>
						              	<center><h3 id="designDate" class="simplepicker-btn" onclick="openDatePicker();"><%= (new java.util.Date()).toLocaleString()%></h3></center>
						              	<input type="hidden" id="dbDate" value="<%= (new java.util.Date()).toInstant()%>"></input>
					              	</div>
					              	
					              	<div class="row1">
										<label class="L-size control-label">Date range: <abbr
											title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="dateRange" class="form-text" 
														name="tripEndDateTimeStr" required="required" readonly=""
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" onChange="getTripSheetData();">
														<!-- <button onClick="getAllCountData();">ok</button> -->
														<input type ="hidden" id="preDateRange" value="${DateRange}">
											</div>
										</div>
									</div>
					              	
             					</div>
      			 			</div>
						</div>
					</div>	
				</section>
				
				<div class="row" style="margin-top: 2%;">
					<div class="row pendingLabel" >
	        			<span id="tripDate"> </span>
	        		</div>
	        	</div>
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test96 card-img-holder1 text-white" onclick="showTripDetails(2);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-file-text-o"></i> <span>Open</span>
                    			</h4>
                    			<h2 class="mb-8" id="openCount"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test95 card-img-holder1 text-white" onclick="showTripDetails(3);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-file-text-o"></i> <span>Closed</span>
                    			</h4>
                    			<h2 class="mb-8" id="closedCount"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder1 text-white" onclick="showTripDetails(4);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-file-text-o"></i> <span>Account Closed</span>
                    			</h4>
                    			<h2 class="mb-8" id="accountClosedCount"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test97 card-img-holder1 text-white" onclick="showTripDetails(5);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 		<i class="fa fa-file-text-o"></i> <span>Usage KM</span>
                    			</h4>
                    			<h2 class="mb-8A" id="totalRunCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        		
        		<div class="row" style="margin-top: 2%;">
			
              		<div class="col-md-10 stretch-card grid-margin">
                		<div class="card  card-img-holder1 text-white">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\advance.svg" alt="" 
                 			 				style="width:55px; height:45px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-17">ClosedTrip Advance</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			 	<div class="row">
	                    			<h6 class="mb-18" id="advance"></h6>
	                 			</div>
	                 			<!-- <img src="resources\QhyvOb0m3EjE7A4\images\summary\greenGraph.png" class="card-img-absolute2" > -->
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-10 stretch-card grid-margin">
                		<div class="card  card-img-holder1 text-white">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\money.svg" alt="" 
                 			 				style="width:55px; height:50px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-13">ClosedTrip Income</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			 	<div class="row">
	                    			<h6 class="mb-15" id="income"></h6>
	                 			</div>
	                 			<!-- <img src="resources\QhyvOb0m3EjE7A4\images\summary\greenGraph.png" class="card-img-absolute2" > -->
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-10 stretch-card grid-margin">
                		<div class="card  card-img-holder1 text-white">
                 			 <div class="card-body">
                 			 	<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\Expense.jpg" alt="" 
                 			 				style="width:55px; height:50px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-14">ClosedTrip Expense</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			 	<div class="row">
	                    			<h6 class="mb-16" id="expense"></h6>
	                 			</div>
                    			<!-- <img src="resources\QhyvOb0m3EjE7A4\images\summary\redGraph.png" class="card-img-absolute2" > -->
                  			</div>
                		</div>
              		</div>
              		
              	</div>
              	
              	
              	<section class="content3">
					<div class="box">
						<div class="box-body">
							
							<div class="button hide" id="graphButton">
								<a class="btn bg-gradient-danger btn-sm text-white buttonStyle " onclick="backToGraph();">
			   							 Back To Graph
			  					</a>
			  					<button id="printBtn" class="btn btn-default hide" onclick="printDiv('tsTable')">
									<span class="fa fa-print"> Print</span>
								</button>
							</div>
								
							 <div class="chart-container" id="chartContainer">
			        			<div class="doughnut-chart-container" style="padding-top: 10%;">
			            			<canvas id="doughnut-chartcanvas-1" width="30%" height="40%" style="display: block; width: 30%; height: 40%;"></canvas>
			        			</div>
			   				 </div>
			   				 
			   				 <div class="table-responsive scroll-box hide" id="tsTable">
								<table id="tripSheetTableDataDetails" class="table table-hover hide " width="100%">
								</table>
							</div>
							
							<!-- <div class="pull-right ">
								<img class="showMoreInfo" src="resources\QhyvOb0m3EjE7A4\images\summary\moreinfo.svg"
								title="Please Scroll To End Of Page To View Complete Details" 
								alt="" style="width:55px; height:50px;" >
							</div> -->
							
						</div>
					</div>
				</section>
        		
        		<div class="row" >
	        		<div class="row incomeExpense" style="margin-top: 2%;">
	        			<span> Total Pending Work As Per Today !</span>
	        		</div>
	        	</div>	
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-danger card-img-holder text-white" onclick="showTripDetails(6);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-file-text-o"></i> Today's Tripsheet Still Left To Be Closed
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="todaysTripOpenStatusCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-primary card-img-holder text-white" onclick="showTripDetails(7);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-file-text-o"></i> Total Tripsheet In Dispatched Status
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="tripSheetDispatchedCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-success card-img-holder text-white" onclick="showTripDetails(8);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-file-text-o"></i> Total Tripsheet In Saved Status
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="tripSheetSavedCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-info card-img-holder text-white">
                			<div class="card-body">
	                			<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-file-text-o"></i> Oldest Open TripSheet
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
								<a  target="_blank" href="#"  id="oldestTrip"><h4 class="mb-11" id="oldestTripNumber"></h4></a>
	                   			<h4 class="mb-12" id="oldestDays"></h4>
                 			</div>
                		</div>
              		</div>
              		
        		</div>
        		 --%>
     	
    
    
    <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/consumption/viewConsumptionData.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/consumption/viewFuelConsumptionData.js"/>"></script>
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
   

