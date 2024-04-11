<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/css/vendor.bundle.base.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/vendor/mdi/css/materialdesignicons.min.css"/>">
	
<style>

.bg-gradient-test1 {
 background: -webkit-gradient(linear, left top, right top, from(#fff720), to(#3cd500)) !important;
 background: linear-gradient(to right, #fff720, #3cd500) !important; } 
/* Cards */
.card {
  border: 0;
  background: #fff; }
  .card .card-body {
    padding: 2.5rem 2.5rem; }
    .card .card-body + .card-body {
      padding-top: 1rem;
      padding-bottom: 0.5rem; }
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
    margin-bottom: 4px;
    border-radius: 10px;
    height : 160px }
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

.card-inverse-primary {
  background: rgba(182, 109, 255, 0.2);
  border: 1px solid #a764eb;
  color: #8a53c2; }

.card-inverse-secondary {
  background: rgba(195, 189, 189, 0.2);
  border: 1px solid #b3aeae;
  color: #949090; }

.card-inverse-success {
  background: rgba(27, 207, 180, 0.2);
  border: 1px solid #19bea6;
  color: #159d89; }

.card-inverse-info {
  background: rgba(25, 138, 227, 0.2);
  border: 1px solid #177fd1;
  color: #1369ad; }

.card-inverse-warning {
  background: rgba(254, 215, 19, 0.2);
  border: 1px solid #eac611;
  color: #c1a30e; }

.card-inverse-danger {
  background: rgba(254, 124, 150, 0.2);
  border: 1px solid #ea728a;
  color: #c15e72; }

.card-inverse-light {
  background: rgba(248, 249, 250, 0.2);
  border: 1px solid #e4e5e6;
  color: #bcbdbe; }

.card-inverse-dark {
  background: rgba(62, 75, 91, 0.2);
  border: 1px solid #394554;
  color: #2f3945; }
  
.bg-gradient-primary {
  background: -webkit-gradient(linear, left top, right top, from(#da8cff), to(#9a55ff)) !important;
  background: linear-gradient(to right, #da8cff, #9a55ff) !important; }

.bg-gradient-secondary {
  background: -webkit-gradient(linear, left top, right top, from(#33ccff), to(#ff99cc)) !important;
  background: linear-gradient(to right, #33ccff, #ff99cc) !important; }

.bg-gradient-success {
  background: -webkit-gradient(linear, left top, right top, from(#84d9d2), to(#07cdae)) !important;
  background: linear-gradient(to right, #84d9d2, #07cdae) !important; }

.bg-gradient-info {
  background: -webkit-gradient(linear, left top, right top, from(#90caf9), color-stop(99%, #047edf)) !important;
  background: linear-gradient(to right, #90caf9, #047edf 99%) !important; }

.bg-gradient-warning {
  background: -webkit-gradient(linear, left top, right top, from(#ff3300), to(#ff66ff)) !important;
  background: linear-gradient(to right, #ff3300, #ff66ff) !important; }

.bg-gradient-danger {
  background: -webkit-gradient(linear, left top, right top, from(#ffbf96), to(#fe7096)) !important;
  background: linear-gradient(to right, #ffbf96, #fe7096) !important; }

.bg-gradient-light {
  background: -webkit-gradient(linear, left top, left bottom, from(#f4f4f4), to(#e4e4e9)) !important;
  background: linear-gradient(to bottom, #f4f4f4, #e4e4e9) !important; }

.bg-gradient-lightDark {
  background: -webkit-gradient(linear, left top, left bottom, from(#0000ff), to(#66ffff)) !important;
  background: linear-gradient(to bottom, #0000ff, #66ffff) !important; }  

.bg-gradient-darkLight {
  background: -webkit-gradient(linear, left top, left bottom, from(#242428), to(#00fdfd)) !important;
  background: linear-gradient(to bottom, #242428, #00fdfd) !important; }  

.bg-gradient-dark {
  background: linear-gradient(89deg, #5e7188, #3e4b5b) !important; }
  
 .col-md-4 {
 	border-radius: 10px;
    -webkit-box-flex: 0;
    -ms-flex: 0 0 33.33333%;
    flex: 0 0 33.33333%;
    max-width: 25%;
    padding-left: 65px; } 
    
       
.text-white, .navbar.navbar-primary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-secondary .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-success .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-info .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-warning .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-danger .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-light .navbar-menu-wrapper .nav-profile-text p, .navbar.navbar-dark .navbar-menu-wrapper .nav-profile-text p {
  color: #ffffff !important; } 

.grid-margin, .purchase-popup {
  margin-bottom: 0.5rem; }

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
  .stretch-card > .card {
    width: 100%;
    min-width: 100%; }
     
.rounded-legend ul li {
  list-style-type: none;
  color: #9c9fa6;
  font-size: .75rem; }

.rounded-legend.legend-vertical ul li {
  margin-top: 1rem; }
  
 .rounded-legend ul li .legend-dots {
    width: 1rem;
    height: 1rem;
    border-radius: 100%;
    display: inline-block;
    vertical-align: text-bottom;
    margin-right: .5rem; }
    .rtl .rounded-legend ul li .legend-dots {
      margin-left: .5rem; }

.float-right {
    float: right !important;
}

.content20 {
    padding: 1%;
}

.content21 {
    min-height: 118px;
	margin-right: 2%;
	width: 55%;
	margin-left: 1%;
	margin-bottom: 15%;
}

.content22 {
	margin-left: 58%;
    min-height: 118px;
	margin-right: 2%;
	width: 40%;
	margin-top: -45%;
	margin-bottom: 8%;
	
}

.chart-container {
	width:  60%;
height: 400px;
margin: 0 auto;
}	

.d-flex, .page-header, .loader-demo-box, .list-wrapper ul li, .navbar .navbar-menu-wrapper .navbar-nav .nav-item.dropdown .dropdown-menu.navbar-dropdown .dropdown-item, .navbar .navbar-menu-wrapper .navbar-nav .nav-item.nav-profile .nav-link {
  display: -webkit-box !important;
  display: -ms-flexbox !important;
  display: flex !important; }
  
.align-items-center, .page-header, .loader-demo-box, .list-wrapper ul li, .navbar .navbar-menu-wrapper .navbar-nav .nav-item.dropdown .dropdown-menu.navbar-dropdown .dropdown-item {
  -webkit-box-align: center !important;
  -ms-flex-align: center !important;
  align-items: center !important; }

.grid-margin, .purchase-popup {
  margin-bottom: 2.5rem; } 
  
.content-wrapper1 {
  background: #f2edf3;
  padding: 2.75rem 2.25rem;
  width: 100%;
  -webkit-box-flex: 1;
  -ms-flex-positive: 1;
  flex-grow: 1; }     
 
@keyframes chartjs-render-animation{from{opacity:.99}to{opacity:1}}.chartjs-render-monitor{animation:chartjs-render-animation 1ms}.chartjs-size-monitor,.chartjs-size-monitor-expand,.chartjs-size-monitor-shrink{position:absolute;direction:ltr;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1}.chartjs-size-monitor-expand>div{position:absolute;width:1000000px;height:1000000px;left:0;top:0}.chartjs-size-monitor-shrink>div{position:absolute;width:200%;height:200%;left:0;top:0}                    

.mb-3{
font-family: "ubuntu-medium", sans-serif;
font-size: 18px;
font-weight: 599;
}

.mb-5{
font-family: font-family: "ubuntu-medium", sans-serif;
	font-size: 50px;
	font-weight: 600;
	margin-top: 11%;
}

.loader  
{

    background-image:url('resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif');
   
}
    
</style>

<!-- <div class="lds-ripple"></div> -->

<div class="content-wrapper">

	<section class="content20">
		<input type="hidden" id="SHOW_COMPANYLIST_DASHBOARD" value="${SHOW_COMPANYLIST_DASHBOARD}">
		<div class="box">
			<div class="box-body">
				<div class="row" id="companyRow" style="display: none;">
		
					<div class="row1">
						<label class="L-size control-label"> Company : </label>
						<div class="I-size">
							<select id="companyId" style="width: 100%;" onchange="getAllCountData();"></select>
							<input type="hidden" id="compId" value="${CompanyId}">
							<input type="hidden" id="compName" value="${CompanyName}">
						</div>
					</div>
		
				</div>
			</div>
		</div>

	</section>
		
	<section class="content20">	
		<div class="box">
			<div class="box-body">
				<div class="row" id="proBanner">
              		<div class="row1">
						<label class="L-size control-label">Date range: 
							<abbr title="required">*</abbr>
						</label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="dateRange" class="form-text" required="required" readonly=""
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%" onchange="getAllCountData();">
							</div>
						</div>
					</div>
       			 </div>
			</div>
		</div>	
	</section>	
	
	<div class="row">
		<a id="tsCount" href="#">
			<div id="tripsheet1" class="col-md-4 stretch-card grid-margin">
				<div class="card bg-gradient-danger card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate" name="tripSheetSummaryDate" val="1"> 
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Trip Sheet 
							<i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="tripsheetCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="tripsheet2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		<a id="fCount" href="#">
			<div id="fuel1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-info card-img-holder text-white ">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Fuel Entries 
						<i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="fuelCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="fuel2" class="col-md-4  ">
			<div class="  card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		
		<a id="workOrdersCount" href="#">
			<div id="workOrder1" class="col-md-4 stretch-card grid-margin " width="20%">
				<div class="card bg-gradient-success card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Work Orders 
						<i class="mdi mdi-diamond mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="workOrderCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="workOrder2" class="col-md-4 stretch-card grid-margin " width="20%">
			<div class="card bg-gradient-success card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
	</div>
	<div class="row">
		<a id="srCount" href="#">
			<div id="serviceReminder1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-primary card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Service Reminders 
						<i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="serviceEntriesCount"></h2>
					</div>
				</div>
			</div>
		</a> 
		<div id="serviceReminder2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
		<a id="rrCount" href="#">
			<div id="renwalReminder1" class="col-md-4 stretch-card grid-margin ">
				<div class="card bg-gradient-warning card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Renewal Reminders
						<i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="RRCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="renwalReminder2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
		<a id="issueCountDetails" href="#">
			<div id="issue1" class="col-md-4 stretch-card grid-margin " width="20%">
				<div class="card bg-gradient-secondary card-img-holder text-white">
					<div class="card-body">
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3"> Issues 
						<i class="mdi mdi-diamond mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="issueCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="issue2" class="col-md-4 stretch-card grid-margin ">
			<div class="card bg-gradient-primary card-img-holder text-white">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">	
			</div>
		</div>
	</div>
	
	<div class="row">
		<a href="<c:url value="/getServiceEntrySummaryData"/>">
		<a id="serviceEntryCountDetails" href="#">
			<div id="serviceEntry1" class="col-md-4 stretch-card grid-margin" >
				<div class="card bg-gradient-test1 card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate"
							name="tripSheetSummaryDate" val="1"> <img
							src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg"
							class="card-img-absolute">
						<h4 class="font-weight-normal mb-3">
							Service Entry <i class="mdi mdi-chart-line mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="serviceEntryCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="serviceEntry2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		
		<sec:authorize access="hasAuthority('FLAVOR_FOUR_PRIVILEGE')">
		<a id="pickAndDropCountDetails" href="#">
			<div id="pickDrop1" class="col-md-4 stretch-card grid-margin" >
				<div class="card bg-gradient-lightDark card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate"
							name="tripSheetSummaryDate" val="1"> <img
							src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg"
							class="card-img-absolute">
						<h4 class="font-weight-normal mb-3">
							Tripsheet Pick & Drop <i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="pickAndDropCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="pickDrop2" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('VEHICLE_TYRE_ASSIGNMENT')">
		<a id="viewTyreSummary" href="#">
			<div id="tyreDiv" class="col-md-4 stretch-card grid-margin" >
				<div class="card bg-gradient-darkLight card-img-holder text-white ">
					<div class="card-body">
						<input type="hidden" id="tripSheetSummaryDate" name="tripSheetSummaryDate" val="1"> 
						<img src="resources\QhyvOb0m3EjE7A4\images\summary\circle.svg" class="card-img-absolute">
						<h4 class="font-weight-normal mb-3">
							Tyre <i class="mdi mdi-bookmark-outline mdi-24px float-right"></i>
						</h4>
						<h2 class="mb-5" id="tyreStockCount"></h2>
					</div>
				</div>
			</div>
		</a>
		<div id="tyreDivLoading" class="col-md-4 stretch-card grid-margin">
			<div class="card bg-gradient-danger card-img-holder text-white ">
				<img src="resources\QhyvOb0m3EjE7A4\images\dashboardLoader.gif" class="stretch-card" style="width: 100%;height: 160px;">
			</div>
		</div>
		</sec:authorize>
		
	</div>
	
	<section class="content">
		<div class="box">
			<div class="box-body">
			
				<div class="chart-container" id="chartContainer">
					<canvas id="bar-chartcanvas"></canvas>
				</div>
			
			</div>
		</div>
	</section>
	
	<div id="loader"></div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/viewSummary.js"/>"></script>
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/summary/Chart.min.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>	
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
</div>