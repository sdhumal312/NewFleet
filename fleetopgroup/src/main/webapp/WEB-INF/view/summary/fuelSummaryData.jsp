<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/simplepicker.css"/>">


<style>

.card {
  border: 0;
  background: #fff; }
  .card .card-body {
    padding: 0.5rem 0.5rem; }
    .card .card-body + .card-body {
      padding-top: 1rem;
       }
  .card .card-title {
    color: #343a40;
    margin-bottom: 0.75rem;
    text-transform: capitalize;
    font-family: "ubuntu-medium", sans-serif;
    font-size: 1.125rem; }
  .card .card-subtitle {
    font-family: "ubuntu-regular", sans-serif;
    margin-top: 0.625rem;
    margin-bottom: 0.625rem; }
  .card .card-description {
    color: #76838f;
    margin-bottom: 1.5rem;
    font-family: "ubuntu-regular", sans-serif; }
  .card.card-outline-success {
    border: 1px solid #1bcfb4; }
  .card.card-outline-primary {
    border: 1px solid #b66dff; }
  .card.card-outline-warning {
    border: 1px solid #fed713; }
  .card.card-outline-danger {
    border: 1px solid #fe7c96; }
  .card.card-rounded {
    border-radius: 5px; }
  .card.card-faded {
    background: #b5b0b2;
    border-color: #b5b0b2; }
  .card.card-circle-progress {
    color: #ffffff;
    text-align: center; }
  .card.card-img-holder {
    position: relative;
     }
    .card.card-img-holder .card-img-absolute {
      position: absolute;
      top: 0;
      right: 0;
      height: 100%; }
  .card.bg-blue-gradient {
    background: -webkit-gradient(linear, left top, right top, from(#065efd), color-stop(#3169fd), to(#6f79fc));
    background: linear-gradient(to right, #065efd, #3169fd, #6f79fc);
    color: #fff; }
  .card.bg-orange-gradient {
    background: -webkit-gradient(linear, left top, right top, from(#ff7f2e), to(#fe7452));
    background: linear-gradient(to right, #ff7f2e, #fe7452);
    color: #fff; }
  .card.bg-green-gradient {
    background: -webkit-gradient(linear, left top, right top, from(#24e8a6), to(#09cdd1));
    background: linear-gradient(to right, #24e8a6, #09cdd1);
    color: #fff; }
  .card.card-no-shadow {
    -webkit-box-shadow: none;
    box-shadow: none; }
    

	
.stretch-card > .card {
    width: 100%;
    min-width: 100%; }

.stretch-card {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: stretch;
  -ms-flex-align: stretch;
  align-items: stretch;
  -webkit-box-pack: stretch;
  -ms-flex-pack: stretch;
  justify-content: stretch; }

/* .grid-margin, .purchase-popup {
  margin-bottom: 2.5rem; } */
  
.card.card-img-holder {
    position: relative;
    border-radius: 10px;
    height: 143px;
     }
    .card.card-img-holder .card-img-absolute {
      position: absolute;
      top: 0;
      right: 0;
      height: 100%; }
      
.card.card-img-holder1 {
    position: relative;
    border-radius: 10px;
    height: 137px;
     }        

.bg-gradient-secondary {
  background: -webkit-gradient(linear, left top, right top, from(#33ccff), to(#ff99cc)) !important;
  background: linear-gradient(to right, #33ccff, #ff99cc) !important;
   }

.bg-gradient-test95{
background: linear-gradient(45deg, #4CAF50, #00A65A);
}
  
.bg-gradient-test96{
background: linear-gradient(45deg, red, darkred);
}
  
.bg-gradient-test97{
background: linear-gradient(45deg,#FFB64D,#ffcb80);
}
  
.bg-gradient-test98{
background: linear-gradient(45deg, #357DED, #72A8FF);
}    

.text-white, .navbar.navbar-primary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-secondary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-success .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-info .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-warning .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-danger .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-light .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-dark .navbar-menu-wrapper .nav-profile-text p {
  color: #ffffff !important; }   
        	
.mb-3 {
    padding-left: 6%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: green;
}

.mb-4 {
    text-align: center;
	padding-top: 2%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 54px;
	font-weight: 600;
	color: green;
}

.mb-5 {
   padding-left: 13%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 22px;
	font-weight: 600;
	color: #047edf;
}


.mb-6 {
    text-align: center;
	padding-top: 12%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 28px;
	font-weight: 600;
	color: #047edf;
}

.mb-7 {
   padding-left: 13%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 22px;
	font-weight: 600;
	color: red;
}


.mb-8 {
    text-align: center;
	padding-top: 12%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 28px;
	font-weight: 600;
	color: red;
}

.mb-9 {
   padding-left: 13%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 22px;
	font-weight: 600;
	color: green;
}


.mb-10 {
    text-align: center;
	padding-top: 12%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 28px;
	font-weight: 600;
	color: green;
}

.mb-11 {
   text-align : center;
	padding-top: 13%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 19px;
	font-weight: 600;
	color: white;
}

.mb-11A {
   text-align : center;
	padding-top: 7%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 18px;
	font-weight: 600;
	color: white;
}

.mb-12 {
   text-align : center;
	padding-top: 5%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 34px;
	font-weight: 600;
	color: white;
}

.mb-12A {
   text-align : center;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 34px;
	font-weight: 600;
	color: white;
}


.col-md-3 {
    width: 12%;
}

 .col-md-4 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 21%;
    padding-left: 2%;
    padding-bottom: 1%; }
 
 .col-md-6 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 12%;
    padding-left: 1% }   
    
 .col-md-11 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 24%;
    padding-left: 5%;
    padding-right : 1%;
    padding-bottom: 1%;
     } 
     
.col-md-10 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 25%;
    padding-left: 16%;
    height: 350px; 
    } 
    
.col-md-20 {
	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 98%;
    padding-left: 1%;
    padding-right : 1%;
    padding-bottom: 1%;
}

.card.card-img-holder2 {
    position: relative;
    border-radius: 10px;
    height: 145px;
     } 

.content2 {
    min-height: 10px;
	margin-right: 2%;
	width: 30%;
	margin-left: 25%;
	margin-top: -9%;
}

.content3 {
   min-height: 100px;
	margin-right: 2%;
	width: 41%;
	margin-left: 58%;
	margin-top: -32%;
	
}

.chart-container {
	width:  50%;
	height: 300px;
	margin: 0 auto;
}

.col-12{
  position: relative;
  width: 100%;
  padding-top: 6%; 
  }

.card-img-absolute1 {
    width: 4%;
	padding-left: 0.5%; 
}
  
.card-img-absolute2 {
    width: 90%;
	padding-left: 6%; 
	padding-top : 3%;
}   

.bg-gradient-danger {
  background: -webkit-gradient(linear, left top, right top, from(#ffbf96), to(#fe7096)) !important;
  background: linear-gradient(to right, #ffbf96, #fe7096) !important; }
  
.bg-gradient-primary {
  background: -webkit-gradient(linear, left top, right top, from(#da8cff), to(#9a55ff)) !important;
  background: linear-gradient(to right, #da8cff, #9a55ff) !important; }
  
.bg-gradient-warning {
  background: -webkit-gradient(linear, left top, right top, from(#ff3300), to(#ff66ff)) !important;
  background: linear-gradient(to right, #ff3300, #ff66ff) !important; }
  
.bg-gradient-test1 {
 background: -webkit-gradient(linear, left top, right top, from(#fff720), to(#3cd500)) !important;
 background: linear-gradient(to right, #fff720, #3cd500) !important; }
 
 .bg-gradient-success {
  background: -webkit-gradient(linear, left top, right top, from(#84d9d2), to(#07cdae)) !important;
  background: linear-gradient(to right, #84d9d2, #07cdae) !important; }
  
  .bg-gradient-info {
  background: -webkit-gradient(linear, left top, right top, from(#90caf9), color-stop(99%, #047edf)) !important;
  background: linear-gradient(to right, #90caf9, #047edf 99%) !important; }       
  
.scroll-box {
 overflow-x: scroll;
 overflow-y: scroll;
 padding: 1rem;
 height: 340px;
       }  
       
.content20 {
    padding: 1%;
    width: 55%;
} 

.info-box1 {
    min-height: 70px;
    background: #fff;
    width: 324%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
    border-radius: 2px;
    margin-bottom: 10px;
}

 .incomeExpense {
    color: #5B2C6F;
    text-decoration: underline;
    text-align: center; 
    font-size: 19px;
	font-weight: 700; }
	
 .pendingLabel {
    color: #000000;
    text-decoration: underline;
    padding-left: 8%; 
    font-size: 18px;
	font-weight: 700; }	
	
.inr-sign::before{
content:"\20B9";
}		     

</style>


<div class="content-wrapper">

	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">Work Summary</a> / Fuel Summary		
				</div>
				<!-- <div>
					Fuel Summary 		
				</div> -->
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Fuel Summary')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" id="cancel1" href="#">Cancel</a>
				</div>
			</div>
		</div>
		<input type="hidden" id="companyId">
	</section>
        		
        		<section class="content20">	
					<div class="box">
						<div class="box-body">
							<div class="row" id="proBanner">
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
												style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" onchange="getFuelData();">
												<input type ="hidden" id="preDateRange" value="${DateRange}">
										</div>
									</div>
								</div>
			       			 </div>
						</div>
					</div>	
				</section>
				
				<div class="row">
					<div class="row pendingLabel" >
	        			<span id="fuelDate"> </span>
	        		</div>
	        	</div>	
        		
        		<div class="row" style="margin-top: 2%;">
        			
        			<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test97 card-img-holder1 text-white" onclick="showFuelDetails(4);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span> Created</span>
                    			</h4>
                    			<h2 class="mb-12" id="fuelCreatedCount"></h2>
                  			</div>
                		</div>
              		</div>
				
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test96 card-img-holder1 text-white" onclick="showFuelDetails(5);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span>Below KM/L </span>
                    			</h4>
                    			<h2 class="mb-12" id="belowRange"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test95 card-img-holder1 text-white" onclick="showFuelDetails(6);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span>Between KM/L </span>
                    			</h4>
                    			<h2 class="mb-12" id="betweenRange"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder1 text-white" onclick="showFuelDetails(7);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span>Above KM/L </span>
                    			</h4>
                    			<h2 class="mb-12" id="aboveRange"></h2>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  card-img-holder2 text-white" onclick="showFuelDetails(4);">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\Expense.jpg" alt="" 
                 			 				style="width:45px; height:45px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-5">Total Amount</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-6" id="todaysTotalFuelCost"></h6>
	                 			 </div>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  card-img-holder2 text-white" onclick="showFuelDetails(4);">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\fuelLiter.svg" alt="" 
                 			 				style="width:45px; height:45px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-7">Total Liters</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-8" id="todaysTotalFuelLiter"></h6>
	                 			 </div>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  card-img-holder2 text-white" onclick="showFuelDetails(4);">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\avgPrice.svg" alt="" 
                 			 				style="width:45px; height:45px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-9">Avg Price</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-10" id="todaysAverageFuelPrice"></h6>
	                 			 </div>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        		
        		<!-- <div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder text-white" onclick="showFuelDetails(2);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-bus"></i> <span>Active Vehicles</span>
                    			</h4>
                    			<h2 class="mb-12" id="activeVehicleCount"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder text-white" onclick="showFuelDetails(3);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span>TripSheet Fuel Entries</span>
                    			</h4>
                    			<h2 class="mb-12" id="tripSheetFuelEntries"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder text-white" onclick="showFuelDetails(1);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-11">
							 		<i class="fa fa-tint"></i> <span>Fuel Entries</span>
                    			</h4>
                    			<h4 class="mb-12" id="fuelEntries"></h4>
                  			</div>
                		</div>
              		</div>
              		
        		</div> -->
        		
			     <section class="content3">
					<div class="box">
						<div class="box-body">
							
							<div class="button hide" id="graphButton">
								<a class="btn bg-gradient-danger btn-sm text-white buttonStyle " onclick="backToGraph();">
			   							 Back To Graph
			  					</a>
			  					<button id="printBtn" class="btn btn-default hide" onclick="printDiv('fTable')">
									<span class="fa fa-print"> Print</span>
								</button>
							</div>
								
							 <div class="chart-container" id="chartContainer">
			        			<div class="doughnut-chart-container" style="padding-top: 10%;">
			            			<canvas id="doughnut-chartcanvas-1" width="30%" height="40%" style="display: block; width: 30%; height: 40%;"></canvas>
			        			</div>
			   				 </div>
			   				 
			   				 <div class="table-responsive scroll-box hide" id="fTable">
								<table id="fuelTableDataDetails" class="table table-hover hide table-bordered" width="100%">
								</table>
							</div>
						
						</div>
					</div>
				</section>
        		
        		<div class="row" style="padding-top: 12px;">
					<div class="row incomeExpense" >
	        			<span> Fuel Entry Details As Per Today !</span>
	        		</div>
	        	</div>
        		
        		<!-- <div class="row">
					<div class="col-md-4 col-sm-5 col-xs-12">
						<div class="info-box1">
							<span class="info-box-icon bg-green"><i class="fa fa-bus"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Total Vehicles</span>
								<span id="totalVehicleCount" class="info-box-number"></span>
							</div>
						</div>
					</div>
				</div> -->
	
				<div class="row" style="padding-top: 12px;">
				
					<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-danger card-img-holder text-white">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-11A"> <i class="fa fa-bus"></i> Total Vehicles 
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-12" id="totalVehicleCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-primary card-img-holder text-white" onclick="showFuelDetails(1);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-11A"> <i class="fa fa-bus"></i> No. Of Vehicles with Fuel Entries 
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-12A" id="feCreatedOnVehicles"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-success card-img-holder text-white" onclick="showFuelDetails(2);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-11A"> <i class="fa fa-bus"></i> No. Of Vehicles without Fuel Entries 
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-12A" id="feNotCreatedOnVehicles"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-info card-img-holder text-white" onclick="showFuelDetails(3);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-11A"> <i class="fa fa-bus"></i> No. Of Vehicles without Expected Mileage (Km/L) 
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-12A" id="countOfvehiclesWithoutKMPL"></h2>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        					 
        		
			
    
    
    <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/fuelSummaryData.js"/>"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>    
     
     <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>		
</div>     
