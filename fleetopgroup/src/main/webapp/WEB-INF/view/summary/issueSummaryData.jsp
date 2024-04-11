<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">

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
    height: 145px;
     }
    .card.card-img-holder .card-img-absolute {
      position: absolute;
      top: 0;
      right: 0;
      height: 100%; }
      
.card.card-img-holder1 {
    position: relative;
    border-radius: 10px;
    height: 135px;
     }

.bg-gradient-secondary {
  background: -webkit-gradient(linear, left top, right top, from(#33ccff), to(#ff99cc)) !important;
  background: linear-gradient(to right, #33ccff, #ff99cc) !important;
   }

.bg-gradient-test95{
background: linear-gradient(45deg,#4099ff,#73b4ff);
}
  
.bg-gradient-test96{
background: linear-gradient(45deg,#2ed8b6,#59e0c5);
}
  
.bg-gradient-test97{
background: linear-gradient(45deg,#FFB64D,#ffcb80);
}
  
.bg-gradient-test98{
background: linear-gradient(45deg,#FF5370,#ff869a);
}

.bg-gradient-test99{
background: linear-gradient(to bottom, #cc00cc 0%, #ff99cc 100%);
}     

.bg-gradient-test100{
background: linear-gradient(to bottom, #00ffff 0%, #66ffff 100%);
}     

.text-white, .navbar.navbar-primary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-secondary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-success .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-info .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-warning .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-danger .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-light .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-dark .navbar-menu-wrapper .nav-profile-text p {
  color: #ffffff !important; }   
        	
.mb-3 {
    text-align:center;
	padding-top: 9%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 18px;
	font-weight: 550;
}

.mb-4 {
    text-align:center;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 38px;
	font-weight: 600;
}

.mb-5 {
    padding-left: 33%;
padding-top: 13%;
font-family: font-family: "ubuntu-medium", sans-serif;
font-size: 19px;
font-weight: 550;
}

.mb-51 {
text-align:center;
padding-top: 13%;
font-family: font-family: "ubuntu-medium", sans-serif;
font-size: 19px;
font-weight: 550;
}

.mb-6 {
    text-align:center;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 38px;
	font-weight: 600;
}

.mb-7 {
    text-align:center;
	padding-top: 10%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 17px;
	font-weight: 550;
}

.mb-8 {
    text-align:center;
	padding-top: 2%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 30px;
	font-weight: 600;
}

.mb-8A {
    text-align:center;
	padding-top: 2%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 23px;
	font-weight: 600;
}

.mb-9 {
    text-align:center;
	padding-top: 10%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 17px;
	font-weight: 550;
}

.mb-10 {
    text-align:center;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 33px;
	font-weight: 600;
}

.mb-11 {
    text-align:center;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 24px;
	font-weight: 600;
}

.mb-12 {
    text-align:center;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 24px;
	font-weight: 600;
}

.mb-13 {
    padding-left: 15%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 19px;
	font-weight: 600;
	color: green;
}

.mb-14 {
    padding-left: 15%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 19px;
	font-weight: 600;
	color: red;
}

.mb-15 {
    text-align:center;
	padding-top: 6%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 32px;
	font-weight: 600;
	color: green;
}

.mb-16 {
    text-align:center;
	padding-top: 6%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 32px;
	font-weight: 600;
	color: red;
}

.mb-17 {
    padding-left: 15%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 19px;
	font-weight: 600;
	color: #ff9900;
}

.mb-18 {
    text-align:center;
	padding-top: 6%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 32px;
	font-weight: 600;
	color: #ff9900;
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
    padding-left: 1% }
    
.col-md-6 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 15%;
    padding-left: 2% }
    
.col-md-7 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 15%;
    padding-left: 7% }
    
.col-md-8 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 20%;
    padding-left: 3% }        
     
.col-md-10 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 17%;
    padding-left: 1%;
    height: 150px; 
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
	margin-top: -38%;
}

.chart-container {
	width:  50%;
	height: 300px;
	margin: 0 auto;
}

 .col-12{ 
    position: relative; 
   width: 100%; 
   padding-top: 5%; 
   padding-bottom: 5%;  
   } 

.card-img-absolute1 {
    width: 24%;
	padding-left: 5%; 
}
  
.card-img-absolute2 {
    width: 90%;
	padding-left: 10%; 
	padding-top : 0%;
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

.bg-gradient-success {
  background: -webkit-gradient(linear, left top, right top, from(#84d9d2), to(#07cdae)) !important;
  background: linear-gradient(to right, #84d9d2, #07cdae) !important; } 
  
 .bg-gradient-info {
  background: -webkit-gradient(linear, left top, right top, from(#90caf9), color-stop(99%, #047edf)) !important;
  background: linear-gradient(to right, #90caf9, #047edf 99%) !important; }      

.button {
	padding-left: 1%; 
	padding-top : 0%;
	
}

.buttonStyle {
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 12px;
	font-weight: 700;
}

.scroll-box {
 overflow-x: scroll;
 overflow-y: scroll;
 padding: 1rem;
 height: 380px;
       } 
       
  .pendingLabel {
    color: #000000;
    text-decoration: underline;
    padding-left: 10%; 
    font-size: 18px;
	font-weight: 700; }	
	      
 .incomeExpense {
    color: #5B2C6F;
    text-decoration: underline;
    text-align: center; 
    font-size: 19px;
	font-weight: 700; } 
	
.tooltip {
  position: relative;
  display: inline-block;
  border-bottom: 1px dotted black;
}

.showMoreInfo {
    width:64px;
    height:64px
}	     
  
</style>

<div class="content-wrapper">

	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
					<a id="cancel" href="#">Work Summary</a> / Issues Summary
				</div>
				<div class="pull-right">
					
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Issues Summary Data Report')"
						id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" id="cancel1" href="#">Cancel</a>
				</div>
			</div>
		</div>
		<input type="hidden" id="companyId">
	</section>
	
				<div class="row">
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card bg-gradient-test99 card-img-holder text-white" onclick="showTripDetails(0);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-3">
									<i class="fa fa-exclamation-circle"></i> <span>Issue Created</span>
                    			</h4>
                    			<h2 class="mb-4" id="issueCreatedCounts" ></h2>
                  			</div>
                		</div>
              		</div>
        		</div>

	<section class="content2">
		<div class="box">
			<div class="box-body">
				<div class="row" id="proBanner">
					<div class="col-12">
						
						
						<div class="row">
							<label class="L-size control-label">Date range: <abbr
								title="required">*</abbr></label>
							<div class="I-size">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="dateRange" class="form-text"
										name="tripEndDateTimeStr" required="required"
										readonly="readonly"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%"
										onChange="getAllIssueCountData();">
									<!-- <button onClick="getAllCountData();">ok</button> -->
									<input type="hidden" id="preDateRange" value="${DateRange}">
								</div>
							</div>
						</div>
						<c:if test="${configuration.showGroupInIssueSummary}">
						<div class="row">
							<label class="L-size control-label">Group </label>
							<div class="I-size">
								<input type="hidden" id="SelectGroup" name="SelectGroup"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 105%"
									onchange="getAllIssueCountData();"
									placeholder="Select Vehicle Group" />
							</div>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</section>

	<div class="row" style="margin-top: 2%;">
					<div class="row pendingLabel" >
	        			<span id="issueDate"> </span>
	        		</div>
	        	</div>
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test96 card-img-holder1 text-white" onclick="showTripDetails(1);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-exclamation-circle"></i> <span>Open</span>
                    			</h4>
                    			<h2 class="mb-8" id="issueOpenCounts"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test95 card-img-holder1 text-white" onclick="showTripDetails(3);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-exclamation-circle"></i> <span>In Process</span>
                    			</h4>
                    			<h2 class="mb-8" id="issueProcessCount"></h2>
                  			</div>
                		</div>
              		</div>
              		<div class="col-md-6 stretch-card grid-margin">
                		<div class="card bg-gradient-test98 card-img-holder1 text-white" onclick="showTripDetails(2);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-exclamation-circle"></i> <span>Closed</span>
                    			</h4>
                    			<h2 class="mb-8" id="issueCloseCounts"></h2>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
				
				<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-7 stretch-card grid-margin">
                		<div class="card bg-gradient-test97 card-img-holder1 text-white" onclick="showTripDetails(4);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-exclamation-circle"></i> <span>Resolved</span>
                    			</h4>
                    			<h2 class="mb-8" id="issueResolveCounts"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-7 stretch-card grid-margin">
                		<div class="card bg-gradient-test100 card-img-holder1 text-white" onclick="showTripDetails(5);">
                 			 <div class="card-body">
                    			<h4 class="font-weight-normal mb-7">
							 <i class="fa fa-exclamation-circle"></i> <span>Rejected</span>
                    			</h4>
                    			<h2 class="mb-8" id="issueRejectedCounts"></h2>
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
								<table id="issueTableDataDetails" class="table table-hover hide table-bordered" width="100%">
								</table>
							</div>
							
						</div>
					</div>
				</section>
				
				<div class="row" >
	        		<div class="row incomeExpense" style="margin-top: 2%;">
	        			<span> Pending Issues As Per Today !</span>
	        		</div>
	        	</div>	
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-danger card-img-holder text-white" onclick="showTripDetails(6);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-exclamation-circle"></i>  Total OverDue Count
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="issueOverDueCounts"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<!-- <div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-primary card-img-holder text-white" onclick="showTripDetails(7);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-exclamation-circle"></i> All Open
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="issueAllOpenCounts"></h2>
                  			</div>
                		</div>
              		</div> -->
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-primary card-img-holder text-white" onclick="showTripDetails(8);">
                 			 <div class="card-body">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-exclamation-circle"></i> 1-7 Days
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="from7Days"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-success card-img-holder text-white" onclick="showTripDetails(9);">
                 			 <div class="card-body" id="oldestTrip">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-exclamation-circle"></i> 8-15 Days
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="from15Days"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-8 stretch-card grid-margin">
                		<div class="card bg-gradient-info card-img-holder text-white" onclick="showTripDetails(10);">
                 			 <div class="card-body" id="oldestTrip">
                 			 	<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
								<h4 class="font-weight-normal mb-9"> <i class="fa fa-exclamation-circle"></i> 15+ Days
									<i class="mdi mdi-chart-line mdi-24px float-right"></i>
								</h4>
                    			<h2 class="mb-10" id="from30Days"></h2>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        		
        		<div class="row" style="margin-top: 2%;">
	        		
	        	</div>	
	
	
<%-- <table width="100%">
	<tr>
		<td width="60%" valign="top">
			<div class="row ">
				<div class="col-md-5 stretch-card grid-margin">
					<div class="card bg-gradient-blue card-img-holder text-white"
						onclick="showTripDetails(0);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>Issue Created
								</span>
							</h4>
							<h2 class="count-size" id="issueCreatedCounts"></h2>
						</div>
					</div>
				</div>
				<div class="col-md-6" width="100%" valign="top">
					<div class="card bg-gradient-white card-img-holder text-white" valign="top">
						<div id="proBanner">
							<div style="padding-top:10%;">
								<div class="row1">
									<label class="L-size control-label">Date range: <abbr
										title="required"></abbr></label>
									<div class="I-size">
										<div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"></i>
											</div>
											<input type="text" id="dateRange" class="form-text"
												name="tripEndDateTimeStr" required="required" readonly=""
												style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%"
												onchange="getAllIssueCountData();"> <input
												type="hidden" id="preDateRange" value="${DateRange}">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 2%;  margin-left: -18px; margin-right: -6%; padding:15px;">
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-green card-img-holder text-white"
						onclick="showTripDetails(1);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>Open</span>
							</h4>
							<h2 class="count-size" id="issueOpenCounts"></h2>
						</div>
					</div>
				</div>
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-green card-img-holder text-white"
						onclick="showTripDetails(3);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>In Process</span>
							</h4>
							<h2 class="count-size" id="issueProcessCount"></h2>
						</div>
					</div>
				</div>
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-green card-img-holder text-white"
						onclick="showTripDetails(2);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>Closed</span>
							</h4>
							<h2 class="count-size" id="issueCloseCounts"></h2>
						</div>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 2%;  margin-left: -18px; margin-right: -6%; padding:15px;">
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-green card-img-holder text-white"
						onclick="showTripDetails(4);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>Resolved</span>
							</h4>
							<h2 class="count-size" id="issueResolveCounts"></h2>
						</div>
					</div>
				</div>
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-green card-img-holder text-white"
						onclick="showTripDetails(5);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>Rejected</span>
							</h4>
							<h2 class="count-size" id="issueRejectedCounts"></h2>
						</div>
					</div>
				</div>
				<div class="col-lg-4 stretch-card grid-margin" style="width:28.2%;">
					<div class="card bg-gradient-red card-img-holder text-white"
						onclick="showTripDetails(6);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>OverDue</span>
							</h4>
							<h2 class="count-size" id="issueOverDueCounts"></h2>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row incomeExpense" style="margin-top: 2%;">
        			<span> Total Pending Issues As Per Current Date !</span>
        	</div>
			
			<div class="row" style="margin-top: 2%;  margin-left: -10px; margin-right: -6%; padding:15px;">
				<div class="col-md-4 stretch-card grid-margin">
					<div class="card bg-gradient-red card-img-holder text-white"
						onclick="showTripDetails(10);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>All Open</span>
							</h4>
							<h2 class="count-size" id="issueAllOpenCounts"></h2>
						</div>
					</div>
				</div>
				<div class="col-md-4 stretch-card grid-margin">
					<div class="card bg-gradient-red card-img-holder text-white"
						onclick="showTripDetails(7);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>0-7 Days</span>
							</h4>
							<h2 class="count-size" id="from7Days"></h2>
						</div>
					</div>
				</div>
				<div class="col-md-4 stretch-card grid-margin">
					<div class="card bg-gradient-red card-img-holder text-white"
						onclick="showTripDetails(8);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>8-15 Days</span>
							</h4>
							<h2 class="count-size" id="from15Days"></h2>
						</div>
					</div>
				</div>
		
				<div class="col-md-4 stretch-card grid-margin">
					<div class="card bg-gradient-red card-img-holder text-white"
						onclick="showTripDetails(9);">
						<div class="card-body">
							<h4 class="font-weight-normal title">
								<i class="fa fa-exclamation-circle"></i> <span>15+ Days</span>
							</h4>
							<h2 class="count-size" id="from30Days"></h2>
						</div>
					</div>
				</div>
			</div>
			
			<td class="box" valign="top" style="margin-left: 2px;  height: 700px;" width="40%">
			<section class="table-content">
		<!-- 	<div class="box"> -->
				<div class="box-body">
					<div class="button hide" id="graphButton">
						<a class="btn bg-gradient-danger btn-sm text-white buttonStyle "
							onclick="backToGraph();"> Back To Graph </a>
					</div>
	
					<div class="chart-container" id="chartContainer">
						<div class="doughnut-chart-container">
							<canvas id="doughnut-chartcanvas-1" width="10%" height="15%"
								style="display: block; width: 20%; height: 35%;"></canvas>
						</div>
					</div>
	
					<div class="table-responsive scroll-box hide" id="tsTable">
						<table id="issueTableDataDetails"
							class="table table-hover hide " width="100%">
						</table>
					</div>
	
				</div>
			<!-- </div> -->
		</section>
		</td>
	</tr>
</table> --%>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#SelectGroup").select2({
				ajax : {
					url : "getVehicleGroup.in",
					dataType : "json",
					type : "GET",
					contentType : "application/json",
					data : function(a) {
						return {
							term : a
						}
					},
					results : function(a) {
						return {
							results : $.map(a, function(a) {
								return {
									text : a.vGroup,
									slug : a.slug,
									id : a.gid
								}
							})
						}
					}
				}
			})
		})
	</script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/issueSummary.js"/>"></script>

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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script> 	
</div>
