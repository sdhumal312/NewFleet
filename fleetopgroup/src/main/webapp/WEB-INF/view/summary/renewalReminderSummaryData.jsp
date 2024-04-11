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
    height: 157px;
     }
    .card.card-img-holder .card-img-absolute {
      position: absolute;
      top: 0;
      right: 0;
      height: 154px; }

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

.text-white, .navbar.navbar-primary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-secondary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-success .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-info .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-warning .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-danger .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-light .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-dark .navbar-menu-wrapper .nav-profile-text p {
  color: #ffffff !important; }   
        	
.mb-3 {
    padding-left: 0%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 17px;
	font-weight: 600;
	color: #ff9900;
}

.mb-4 {
    text-align: center;
	padding-top: 4%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 49px;
	font-weight: 600;
	color: #ff9900;
}


.mb-5 {
   padding-left: 0%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 17px;
	font-weight: 600;
	color: #047edf;
}

.mb-5A {
   padding-left: 0%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 15px;
	font-weight: 600;
	color: #047edf;
}


.mb-6 {
    text-align: center;
	padding-top: 4%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 49px;
	font-weight: 600;
	color: #047edf;
}

.mb-9 {
   padding-left: 24%;
	padding-top: 1%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 23px;
	font-weight: 600;
	color: green;
}

.mb-17 {
    padding-left: 24%;
	padding-top: 11%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 40px;
	font-weight: 600;
	color: green;
}


.mb-11 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #6600cc;
}

.mb-18 {
    padding-left: 24%;
	padding-top: 11%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 43px;
	font-weight: 600;
	color: #6600cc;
}

.mb-7 {
   padding-left: 24%;
	padding-top: 1%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 23px;
	font-weight: 600;
	color: red;
}


.mb-8 {
    padding-left: 24%;
	padding-top: 11%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 43px;
	font-weight: 600;
	color: red;
}

.mb-11 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #6600cc;
}



.mb-10 {
    padding-left: 39%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 54px;
	font-weight: 600;
	color: green;
}

.mb-11 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #6600cc;
}


.mb-13 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #ff9900;
}


.mb-12 {
    padding-left: 39%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 54px;
	font-weight: 600;
	color: #6600cc;
}

.mb-13 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #ff9900;
}

.mb-15 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #047edf;
}

.mb-14 {
    padding-left: 39%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 54px;
	font-weight: 600;
	color: #ff9900;
}

.mb-15 {
   padding-left: 18%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 27px;
	font-weight: 600;
	color: #047edf;
}


.mb-16 {
    padding-left: 39%;
	padding-top: 0%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 54px;
	font-weight: 600;
	color: #047edf;
}

.mb-48 {
   text-align: center;
	padding-top: 1%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 16px;
	font-weight: 600;
	color: white;
}

.mb-48A {
   text-align: center;
	padding-top: 6%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 20px;
	font-weight: 600;
	color: white;
}

.mb-49 {
    text-align: center;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 28px;
	font-weight: 600;
	color: white;
}

.mb-49A {
    text-align: center;
	padding-top: 3%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 33px;
	font-weight: 600;
	color: white;
}

.mb-49B {
    text-align: center;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 28px;
	font-weight: 600;
	color: white;
}

.mb-50 {
   text-align: center;
	padding-top: 16%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 23px;
	font-weight: 600;
	color: white;
}

.mb-51 {
    text-align: center;
	padding-top: 3%;
	font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 37px;
	font-weight: 600;
	color: white;
}

.col-md-3 {
    border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 23%;
    padding-left: 3%;
}

 .col-md-4 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 17%;
    padding-left: 1%;
     }
     
  .col-md-5 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 21%;
    padding-left: 1%;
     }    
     
 .col-md-2 {
 	border-radius: 10px;
	webkit-box-flex: 0;
	ms-flex: 0 0 33.33333%;
	flex: 0 0 33.33333%;
	max-width: 13%;
	padding-left: 1%;
	padding-right: 0%;
	padding-bottom: 1%;
     }  
 
 .col-md-1 {
 	border-radius: 10px;
	webkit-box-flex: 0;
	ms-flex: 0 0 33.33333%;
	flex: 0 0 33.33333%;
	max-width: 13%;
	padding-left: 1%;
	padding-right: 0%;
	padding-bottom: 1%;
     }       
    
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

.col-md-21 {
 	border-radius: 10px;
	webkit-box-flex: 0;
	ms-flex: 0 0 33.33333%;
	flex: 0 0 33.33333%;
	max-width: 17%;
	padding-left: 1%;
	padding-right: 0%;
	padding-bottom: 1%;
     }

.card.card-img-holder2 {
    position: relative;
    border-radius: 10px;
    height: 150px;
    
     } 

.content2 {
    min-height: 10px;
	margin-right: 2%;
	width: 62%;
	margin-left: 36%;
	margin-top: -10%;
}


.content3 {
    min-height: 118px;
	margin-right: 2%;
	width: 41%;
	margin-left: 58%;
	margin-top: -48%;
}

.chart-container {
	width:  50%;
	height: 500px;
	margin: 0 auto;
}

.col-12{
  position: relative;
  width: 100%;
  padding-top: 6%; 
  }

.content20 {
    padding: 1%;
}

.scroll-box {
 overflow-x: scroll;
 overflow-y: scroll;
 padding: 1rem;
 height: 350px;
       }

.scroll-box1 {
 overflow-x: scroll;
 overflow-y: scroll;
 padding: 1rem;
 height: 150px;
       }       
       

.bg-gradient-warning {
  background: -webkit-gradient(linear, left top, right top, from(#ff3300), to(#ff66ff)) !important;
  background: linear-gradient(to right, #ff3300, #ff66ff) !important; }

.bg-gradient-success {
  background: -webkit-gradient(linear, left top, right top, from(#84d9d2), to(#07cdae)) !important;
  background: linear-gradient(to right, #84d9d2, #07cdae) !important; }

.bg-gradient-secondary {
  background: -webkit-gradient(linear, left top, right top, from(#33ccff), to(#ff99cc)) !important;
  background: linear-gradient(to right, #33ccff, #ff99cc) !important;
   }   

.bg-gradient-info {
  background: -webkit-gradient(linear, left top, right top, from(#90caf9), color-stop(99%, #047edf)) !important;
  background: linear-gradient(to right, #90caf9, #047edf 99%) !important; } 

.bg-gradient-danger {
  background: -webkit-gradient(linear, left top, right top, from(#ffbf96), to(#fe7096)) !important;
  background: linear-gradient(to right, #ffbf96, #fe7096) !important; } 
  
.bg-gradient-primary {
  background: -webkit-gradient(linear, left top, right top, from(#da8cff), to(#9a55ff)) !important;
  background: linear-gradient(to right, #da8cff, #9a55ff) !important; }     

.info-box1 {
    min-height: 70px;
    background: #fff;
    width: 264%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, .1);
    border-radius: 2px;
    margin-bottom: 10px;
}

.mb-52 {
    color: brown;
}

.pendingLabel {
    color: #000000;
    text-decoration: underline;
    padding-left: 14%; 
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
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/viewSummary"/>">Work Summary</a> 
							/ Renewal Reminder Summary
					</a>		
				</div>
				<div class="pull-right">
					
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'RR Summary Data Report')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a class="btn btn-danger" href="<c:url value="/viewSummary"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<%-- <section class="content20">	
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
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" onchange="getRenewalReminderData();">
									<input type ="hidden" id="preDateRange" value="${DateRange}">
							</div>
						</div>
					</div>
       			 </div>
			</div>
		</div>	
	</section> --%>
	
	<input type="hidden" id="dateRange" class="form-text" 
	name="tripEndDateTimeStr" required="required" readonly=""
	style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" onchange="getRenewalReminderData();">
	<input type ="hidden" id="preDateRange" value="${DateRange}">
	
	<div class="row">
		<div class="col-md-5 col-sm-5 col-xs-12">
			<div class="info-box1">
				<span class="info-box-icon bg-green"><i class="fa fa-bus"></i></span>
				<div class="info-box-content ">
					<span class="info-box-text mb-52">Total Vehicles</span>
					<span id="totalVehicleCount" class="info-box-number mb-52"></span>
				</div>
			</div>
		</div>
	</div>
				<div class="row" >
					<div class="row pendingLabel" >
	        			<span> Renewal Reminder Details As Per Today ! </span>
	        		</div>
	        	</div>
				
				
				<div class="row" style="margin-top: 1%;">
				
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  card-img-holder text-white " onclick="showRRDetails(1);">
                 			 <div class="card-body">
                 			 	<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\renCreated.svg" alt="" 
                 			 				style="width:60px; height:60px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-3">Vehicles With Renewal Reminder</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-4" id="renewalCreatedCount"></h6>
	                 			 </div>
	                 			 
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  card-img-holder text-white" onclick="showRRDetails(2);">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\todReneval.svg" alt="" 
                 			 				style="width:55px; height:55px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-5"> Vehicles Without Renewal Reminder</h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-6" id="todaysRenewalCount"></h6>
	                 			 </div>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  card-img-holder text-white" onclick="showRRDetails(11);">
                 			 <div class="card-body">
                    			<table>
                 			 		<tr>
                 			 			<td>
                 			 				<img src="resources\QhyvOb0m3EjE7A4\images\summary\todReneval.svg" alt="" 
                 			 				style="width:55px; height:55px;" >
                 			 			</td>
                 			 			<td>
                 			 				<h6 class="mb-5A"> Vehicle Wise Mandatory & Non-Mandatory Renewals  </h6>
                 			 			</td>
                 			 		</tr>
                 			 	</table>
                 			
	                 			 <div class="row">
	                    			<h6 class="mb-6" id="mandatoryCount"></h6>
	                 			 </div>
                  			</div>
                		</div>
              		</div>
              		
        		</div>
        		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  bg-gradient-test96 card-img-holder2 text-white" onclick="showRRDetails(3);">
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-48A">
							 		<i class="fa fa-bell-slash"></i> <span>Due Soon</span>
                    			</h4>
                    			<h2 class="mb-49A" id="totalDueSoonCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  bg-gradient-test95 card-img-holder2 text-white" onclick="showRRDetails(5);" >
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-48">
							 		<span id="thisMonth"></span>
                    			</h4>
                    			<h2 class="mb-49" id="thisMonthCount"></h2>
                    			<h2 class="mb-49B" id="thisMonthAmount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-4 stretch-card grid-margin">
                		<div class="card  bg-gradient-test97 card-img-holder2 text-white" onclick="showRRDetails(6);" >
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-48">
							 		 <span id="nextMonth"></span>
                    			</h4>
                    			<h2 class="mb-49" id="nextMonthCount"></h2>
                    			<h2 class="mb-49B" id="nextMonthAmount"></h2>
                  			</div>
                		</div>
              		</div>
              	
              	</div>	
              
              		
        		<div class="row" style="margin-top: 2%;">
        		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  bg-gradient-test98 card-img-holder2 text-white" onclick="showRRDetails(7);">
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-50">
							 		<i class="fa fa-bell-slash"></i> <span>Over Due</span>
                    			</h4>
                    			<h2 class="mb-51" id="totalOverDueCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  bg-gradient-test98 card-img-holder2 text-white" onclick="showRRDetails(8);">
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-50">
							 		<i class="fa fa-bell-slash"></i> <span>0-7 Days</span>
                    			</h4>
                    			<h2 class="mb-51" id="totalSevenDaysCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  bg-gradient-test98 card-img-holder2 text-white" onclick="showRRDetails(9);">
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-50">
							 		<i class="fa fa-bell-slash"></i> <span>8-15 Days</span>
                    			</h4>
                    			<h2 class="mb-51" id="totalFifteenDaysCount"></h2>
                  			</div>
                		</div>
              		</div>
              		
              		<div class="col-md-2 stretch-card grid-margin">
                		<div class="card  bg-gradient-test98 card-img-holder2 text-white" onclick="showRRDetails(10);">
                 			 <div class="card-body">
                 			 	<h4 class="font-weight-normal mb-50">
							 		<i class="fa fa-bell-slash"></i> <span>15+ Days</span>
                    			</h4>
                    			<h2 class="mb-51" id="totalFifteenPlusDaysCount"></h2>
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
		  					<button id="printBtn" class="btn btn-default hide" onclick="printDiv('rrTable')">
									<span class="fa fa-print"> Print</span>
							</button>
						</div>
							
						 <div class="chart-container" id="chartContainer">
		        			<div class="doughnut-chart-container" style="padding-top: 18%;">
		            			<canvas id="doughnut-chartcanvas-1" width="30%" height="50%" style="display: block; width: 30%; height: 50%;"></canvas>
		        			</div>
		   				 </div>
		   				 
		   				 <div class="table-responsive scroll-box1 hide" id="rrTable1">
							<table id="rrTableDataDetails1" class="table table-hover hide table-bordered" width="100%">
							</table>
						</div>
		   				 
		   				 <div class="table-responsive scroll-box hide" id="rrTable">
							<table id="rrTableDataDetails" class="table table-hover hide table-bordered" width="100%">
							</table>
						</div>
						
						<!-- <div class="table-responsive scroll-box hide" id="rrTable1">
							<table id="rrTableDataDetails1" class="table table-hover hide " width="100%">
							</table>
						</div>
						
						<div class="table-responsive scroll-box hide" id="rrTable2">
							<table id="rrTableDataDetails2" class="table table-hover hide " width="100%">
							</table>
						</div> -->
					
					</div>
				</div>
			</section>
			
    
    
    <script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>
		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/renewalReminderSummaryData.js"/>"></script>
		
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