<style>

#snackbar {
 	visibility: hidden;  
	min-width: 350px;
	margin-left: -125px;
	background-color: #f39c12;
	color: #000000;
	text-align: center;
	border-radius: 2px;
	padding: 16px;
	position: fixed;
	z-index: 1;
	left: 50%;
	bottom: 160px
}

 #rrSnackbar {
 	visibility: hidden;   
	min-width: 350px;
	margin-left: -125px;
	background-color: #f39c12;
	color: #000000;
	text-align: center;
	border-radius: 2px;
	padding: 16px;
	position: fixed;
	z-index: 1;
	left: 50%;
	bottom: 100px
}

#snackbar.show, #rrSnackbar.show {
	visibility: visible;
	-webkit-animation: fadein .5s, fadeout .5s 2.5s;
	animation: fadein .5s, fadeout .5s 2.5s
}

@
-webkit-keyframes fadein {
	from {bottom: 0;
	opacity: 0
}

to {
	bottom: 30px;
	opacity: 1
}

}
@
keyframes fadein {
	from {bottom: 0;
	opacity: 0
}

to {
	bottom: 30px;
	opacity: 1
}

}
@
-webkit-keyframes fadeout {
	from {bottom: 30px;
	opacity: 1
}

to {
	bottom: 0;
	opacity: 0
}

}
@
keyframes fadeout {
	from {bottom: 30px;
	opacity: 1
}

to {
	bottom: 0;
	opacity: 0
}
}
.dropdown-menu {
   max-height:500px;
   overflow:scroll; 
}

#companyInfo {
    display: inline-flex;
    align-items: center; 
    width: auto; 
    height:35px;
    text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2); 
    border-top-left-radius: 5px; 
    border-top-right-radius: 55px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 55px;
    background-color: #F7E7CE;
    margin:8px 2px 10px 95px;
    padding: 0px 4px 0px 10px;
    width: 425px;
}

#companyName {
    font-family: "IM Fell Double Pica SC", serif;
    font-weight: 590;
    font-style: normal;
    font-size: 20px;
    margin-right: 20px;
    text-align: center;
    letter-spacing: -1.2px;
    word-spacing: 5px;
    font-size: 17.2px;
    width: 360px;
    line-height: 1.1;
    color: #002366;
}

#logoContainer {
    height:105%;;
    margin: 0px -55px 0px 0px;
    width:170px;
    border-radius: 10px;
}

#logoContainer img {
    max-width: 200%;
    max-height: 105%;
}

</style>
<%@ include file="taglib.jsp"%>
<header class="main-header ">
	
	<input type="hidden" id="companyLogo" value="">
	<a href="/open.html" class="logo logominhide">
	    <span class="logo-mini">
	        <font color="#0F0F0F">F</font><font color="#F89219">T</font>
	    </span>
	    <span class="logo-lg">
	        <img id="customLogo" alt="fleetop.com" align="middle" width="150" height="40">
	    </span>
	</a>
	<input type="hidden" id="checkk" value="">
	
	<script>

	function setLogo(){
	    var customLogoSrc = "/resources/QhyvOb0m3EjE7A4/css/FleetopLogo.png";
	    var defaultLogoSrc = "${pageContext.request.contextPath}/downloadlogo/"+document.getElementById("checkk").value+".in";
	    var manualLogoImage     = "/resources/QhyvOb0m3EjE7A4/images/trucklogo2.png";
	    var img = new Image();
	    img.src = defaultLogoSrc;
	    img.onload = function() {
	        document.getElementById("customLogo").src = defaultLogoSrc;
	        document.getElementById("manualLogo").src = manualLogoImage;
	    };
	    img.onerror = function() {
	        document.getElementById("customLogo").src = customLogoSrc;
	        document.getElementById("manualLogo").src = manualLogoImage;
	    }; 
	}
	</script>
									
	<nav class="navbar navbar-static-top" role="navigation">
		<a href="<c:url value="/#"/>" class="sidebar-toggle"
			data-toggle="offcanvas" role="button"> <span class="sr-only"></span>
		</a>
		<div class="collapse navbar-collapse pull-left" id="navbar-collapse">
			<form class="navbar-form navbar-left"
				action="<c:url value="/searchVehicle.in"/>" method="post"
				role="search">
				<div class="form-group">
					<input type="text" class="form-text" id="navbar-search-input"
						name="searchvehicle" required="required"
						placeholder="Search vehicles..">
				</div>
			</form>
		</div>
		
		<!-- <div id="companyInfo">
		    <div id="companyName"></div>
		    <div id="logoContainer">

		        <img alt="Company Logo" id="manualLogo">
		    </div>
		</div>	 -->

		<div class="collapse navbar-collapse pull-left">
			<a class="navbar-form navbar-left form-group hide" id="ivCargoLogoA" href="#" onclick="getIvUrl();">
				<img src="resources\QhyvOb0m3EjE7A4\images\IVCargo_gif.gif" alt="ivCargo" style="height: 26px;" >
			</a>
		</div>
	
		<div class="navbar-custom-menu">
			<sec:authorize access="hasAuthority('RR_NOTIFICATION')">
				<div id="rrSnackbar" class="hide" >Some text some message..</div>
			</sec:authorize>	
			<div id="snackbar">Some text some message..</div>
			<ul class="nav navbar-nav navbar-right">
			
			<sec:authorize access="hasAuthority('RR_NOTIFICATION')">
			<li id="rrNotificationIcon" class="dropdown messages-menu hide">
				<a href="javascript:void(0)" id="unReadRRNotifications" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"> 
					<i class="fa fa-bell"></i> 
					<span class="label label-success" id="renewalNotificationCount" onclick="getRRNotificationList();"></span>
				</a>
			</li>
			</sec:authorize>
				
			<li class="dropdown messages-menu"><a href="javascript:void(0)"
				id="unReadNotifications" class="dropdown-toggle" data-toggle="dropdown"
				aria-expanded="false"> <i class="fa fa-info"></i> <span
					class="label label-success" id="notificationCount" onclick="getUserNotificationList();"></span>
				</a>
			</li>
			<li class="dropdown messages-menu"><a href="javascript:void(0)"
				id="unReadMeassage" class="dropdown-toggle" data-toggle="dropdown"
				aria-expanded="false"> <i class="fa fa-envelope"></i> <span
					class="label label-success" id="Unread">${unread}</span>
			</a>
				<ul class="dropdown-menu">
					<li class="header">You have ${unread} messages</li>
					<li>
						<!-- inner menu: contains the actual data -->
						<div class="slimScrollDiv"
							style="position: relative; overflow: hidden; width: auto; height: 200px;">
							<ul class="menu"
								style="overflow: hidden; width: 100%; height: 200px;"
								id="UNMESSAGE">
								<li class="loading ng-hide" id="loading"><img
									alt="Loading" class="loading-img"
									src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif"/>"></li>

							</ul>
							<div class="slimScrollBar"
								style="background: rgb(0, 0, 0); width: 3px; position: absolute; top: 0px; opacity: 0.4; display: none; border-radius: 7px; z-index: 99; right: 1px; height: 131.148px;"></div>
							<div class="slimScrollRail"
								style="width: 3px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;"></div>
						</div>
					</li>
					<li class="footer"><a href="<c:url value="/mailbox/1.in"/>">See
							All Messages</a></li>
				</ul></li>
				<li><a href="#" id='ct'></a></li>
				<li class="dropdown notifications-menu"><a
					class="dropdown-toggle" data-toggle="dropdown"
					href="<c:url value="/#"/>"><i class="fa fa-cogs"></i> <!-- Settings -->
						<i class="fa fa-caret-down"></i></a>
					<ul class="dropdown-menu">
						<li class="header"><i class="fa fa-cogs"></i> <spring:message
								code="label.pages.mastersettings" /></li>
						<li><sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
								<ul class="menu">
								<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET_ONLY')">
									<li class="dropdown-header">Company Details</li>
									<li><a href="<c:url value="/newCompany.in"/>"><i
											class="fa fa-star"></i> Company Info</a></li></sec:authorize>
									
									<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
											<a href="<c:url value="/newBranch.in"/>"><span
												class="fa fa-users"></span> Branch Info</a>
										</sec:authorize></li>
									<li><sec:authorize
											access="hasAuthority('VIEW_DEPARTMENT')">
											<a href="<c:url value="/addDepartment.in"/>"><span
												class="fa fa-cube"></span> Department Info</a>
										</sec:authorize></li>
									<li class="divider"></li>
									<li class="dropdown-header"><spring:message
											code="label.pages.roles" /></li>
									<li><sec:authorize access="hasAuthority('VIEW_ROLE')">
											<a href="<c:url value="/newRole" />"><span
												class="fa fa-file-image-o"></span> <spring:message
													code="label.pages.rolepermissions" /></a>
										</sec:authorize></li>
									<li><sec:authorize access="hasAuthority('VIEW_USER')">
											<a href="<c:url value="/newUserList/1.in" />"><span
												class="fa fa-users"></span> User List Info</a>
										</sec:authorize></li>
									<li class="dropdown-header">Vehicle Settings</li>
								<%-- 	<li><a href="<c:url value="/addVehicleStatus.in"/>"><i
											class="fa fa-star"></i> Vehicle Statues</a></li> --%>
									
									<li><a href="<c:url value="/addVehicleStatusPermission.in"/>"><i
											class="fa fa-star"></i> Vehicle Statues</a></li>
									
									<li><a href="<c:url value="/addVehicleTypes/1.in"/>"><span
											class="fa fa-keyboard-o"></span> Vehicle Types</a></li>
									<li><a href="<c:url value="/addVehicleGroup.in"/>"><span
											class="fa fa-users"></span> Vehicle Group</a></li>
									<li class="dropdown-header">Vehicle Document Settings</li>
									<li><a href="<c:url value="/addVehicleDocType.in"/>"><span
											class="fa fa-file-text"></span> Vehicle Document Types</a></li>
									<li><a href="<c:url value="/addVehiclePhoType.in"/>"><span
											class="fa fa-file-image-o"></span> Vehicle Photo Types</a></li>
									<li><a
										href="<c:url value="/addVehiclePurchaseInfoType.in"/>"><span
											class="fa fa-shopping-cart"></span> Vehicle Purchase Info
											Types</a></li>
									<li class="dropdown-header">Fuel Settings</li>
									<%-- <li><a href="<c:url value="/addVehicleFuel.in"/>"><span
											class="fa fa-tint"></span> Vehicle Fuel Types</a></li> --%>
									<li><a href="<c:url value="/addVehicleFuelPermission.in"/>"><span
											class="fa fa-tint"></span> Vehicle Fuel Types</a></li>
									<li class="dropdown-header">Driver Settings</li>
									<li><a href="<c:url value="/addDriverDocType.in"/>"><span
											class="fa fa-file-text"></span> Driver Renewal Types</a></li>
									<li><a href="<c:url value="/addDriverTrainingType.in"/>"><span
										class="fa fa-file-text"></span> Driver Training Types</a></li>
									<li><a href="<c:url value="/addDriverJobType.in"/>"><span
											class="fa fa-file-text"></span> Driver Job Types</a></li>
									<li class="dropdown-header">Reminder Settings</li>
									<li><a href="<c:url value="/addRenewalType.in"/>"><span
											class="fa fa-bell-slash-o"></span> Renewal Types</a></li>
									<li><a href="<c:url value="/addRenewalSubType.in"/>"><span
											class="fa fa-bell-slash-o"></span> Renewal Sub Types</a></li>
									<li class="dropdown-header">Vendor Settings</li>
									<li><a href="<c:url value="/addVendorType.in"/>"><span
											class="fa fa-users fa-lg"></span> Vendor Types</a></li>
									<li class="dropdown-header">Parts Settings</li>
									<li><a href="<c:url value="/addPartCategories.in"/>"><span
											class="fa fa-cube"></span> Part Categories</a></li>
									<li><a href="<c:url value="/addPartLocations.in"/>"><span
											class="fa fa-cube"></span> Part Locations</a></li>
									<li><a href="<c:url value="/addPartManufacturer.in"/>"><span
											class="fa fa-cube"></span> Part Manufacturers</a></li>
									<li><a href="<c:url value="/addPartMeasurementUnit.in"/>"><span
											class="fa fa-cube"></span> Part Measurement Unit</a></li>
									<li class="dropdown-header">WorkOrders Settings</li>
									<li><a href="<c:url value="/addJobType.in"/>"><span
											class="fa fa-file"></span> Job Types</a></li>
									<li><a href="<c:url value="/addJobSubType/1.in"/>"><span
											class="fa fa-file"></span> Job ROT Types</a></li>
									<li class="dropdown-header">Trip Settings</li>
									<li><a href="<c:url value="/newTripRoute/1.in"/>"><span
											class="fa fa-file"></span>Trip Route</a></li>
									<li><a href="<c:url value="/addTripExpense/1.in"/>"><span
											class="fa fa-file"></span>Trip Expense</a></li>
										
									<li><a href="<c:url value="/addTripIncome/1/1.in"/>"><span
											class="fa fa-money"></span>Trip Income</a></li>
											
									<li class="dropdown-header">Trip Sheet Settings</li>
									<li><a href="<c:url value="/newTripRoute/1.in"/>"><span
											class="fa fa-file"></span>Trip Sheet Options</a></li>	
									
									<li class="dropdown-header">Vehicle Inspection Settings</li>
										<sec:authorize access="hasAuthority('ADD_VEHICLE_INSPECTION_PARAMETER')">
											<li><a href="<c:url value="/addInspectionParameter.in"/>">Inspection Parameters</a></li>
										</sec:authorize>		
									<sec:authorize access="hasAuthority('ADD_VEHICLE_INSPECTION_SHEET')">		
										<li><a href="<c:url value="/ViewInspectionSheet.in"/>">Inspection Sheet</a></li>	</sec:authorize>
								</ul>
							</sec:authorize></li>
						<li class="footer"><a href="<c:url value="/#"/>"></a></li>
					</ul></li>
					<li class="dropdown notifications-menu"><sec:authorize access="hasAuthority('MASTER_COM_VIEW_PRIVILEGE')"><a
					class="dropdown-toggle" data-toggle="dropdown"
					href="<c:url value="/#"/>"><i class="fa fa-cogs"></i> Master
						<i class="fa fa-caret-down"></i></a>
					<ul class="dropdown-menu">
						<li class="header"><i class="fa fa-cogs"></i> <spring:message
								code="label.pages.mastersettings" /></li>
						<li><sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
								<ul class="menu">
									<li class="dropdown-header">Setup Master Company Details</li>
									<li><a href="<c:url value="/masterCompany/1.in"/>"><i
											class="fa fa-star"></i>View Company Master</a></li>
									<li><a href="<c:url value="/masterCompanyCreate.in"/>"><i
											class="fa fa-star"></i>Create Company Master</a></li>
									<li><a href="<c:url value="/masterEnableConfiguration"/>"><i
											class="fa fa-star"></i>Save Configuration</a></li>
									<li><a href="<c:url value="/masterEnableCompanyConfiguration"/>"><i
											class="fa fa-star"></i>Set Company Configuration</a></li>		
								</ul>
							</sec:authorize></li>
						<li class="footer"><a href="<c:url value="/#"/>"></a></li>
					</ul></sec:authorize></li>
					
				<li class="dropdown mega-dd hidden-xs"><a
					class="dropdown-toggle" data-toggle="dropdown"
					href="javascript:void(0)" aria-expanded="false"> <i
						class="fa fa-sliders" aria-hidden="true"> </i> <spring:message
							code="label.pages.mastersettings" /><i
						class="material-icons md-18 md-keyboard_arrow_down"></i>
				</a>
					<div class="dropdown-menu has-caret">
						<div class="container-fluid">
							<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET_ONLY')">
								<li class="dropdown-header">Company Details</li>
								<li><a href="<c:url value="/newCompany.in"/>">Company
										Info</a></li>
										</sec:authorize>
								<li><sec:authorize access="hasAuthority('VIEW_BRANCH')">
										<a href="<c:url value="/newBranch.in"/>">Branch Info</a>
									</sec:authorize></li>
								<li><sec:authorize access="hasAuthority('VIEW_DEPARTMENT')">
										<a href="<c:url value="/addDepartment.in"/>">Department
											Info</a>
									</sec:authorize></li>
								<li class="divider"></li>
								<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
									<li class="dropdown-header"><spring:message
											code="label.pages.roles" /></li>

									<li><sec:authorize access="hasAuthority('VIEW_ROLE')">
											<a href="<c:url value="/newRole" />"><spring:message
													code="label.pages.rolepermissions" /></a>
										</sec:authorize></li>
									<li><sec:authorize access="hasAuthority('VIEW_USER')">
											<a href="<c:url value="/newUserList/1.in" />">User List
												Info</a>
										</sec:authorize></li>
									<%-- 	<li><sec:authorize access="hasAuthority('VIEW_USER')">
											<a href="<c:url value="/editCompanyWisePrivileges.in" />">Company Wise
												Privileges</a>
										</sec:authorize></li> --%>	
								</sec:authorize>
								<li class="divider"></li>
								<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
									<li class="dropdown-header">Vehicle Settings</li>

									<%-- <li><a href="<c:url value="/addVehicleStatus" />">Vehicle
											Statuses</a></li> --%>
											
									<li><a href="<c:url value="/addVehicleStatusPermission" />">Vehicle
											Statuses</a></li>
									<li><a href="<c:url value="/addVehicleTypes/1" />">Vehicle
											Types</a></li>
									<li><a href="<c:url value="/addVehicleGroup" />">Vehicle
											Group</a></li>
								<sec:authorize access="hasAuthority('SHOW_VEHICLE_TOLL_MASTER')">
									<li><a href="<c:url value="/addVehicleToll" />">Vehicle
											Toll </a></li>	
								</sec:authorize>
								<sec:authorize access="hasAuthority('SHOW_VEHICLE_GPS_MASTER')">			
									<li><a href="<c:url value="/addVehicleGps" />">Vehicle
											GPS </a></li>	
								</sec:authorize>
								
									<li><a href="<c:url value="/vehicleManufacturer" />">Vehicle Manufacturer </a></li>	
									<li><a href="<c:url value="/vehicleModel" />">Vehicle Model </a></li>
									<sec:authorize access="hasAuthority('VIEW_VEHICLE_BODY_MAKER')">
									<li><a href="<c:url value="/showVehicleBodyMakerList" />">Body Maker </a></li>	
									</sec:authorize>
								<sec:authorize access="hasAuthority('VEHICLE_ACCIDENT_TYPE')">	
								<li><a href="<c:url value="/vehicleAccidentTypeMaster" />">Vehicle Accident Type </a></li>		
								</sec:authorize>			
								</sec:authorize>
							</ul>
							<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Vehicle Document</li>
									<li><a href="<c:url value="/addVehicleDocType" />">Vehicle
											Document Types</a></li>
									<li><a href="<c:url value="/addVehiclePhoType" />">Vehicle
											Photo Types</a></li>
									<li><a
										href="<c:url value="/addVehiclePurchaseInfoType" />">Vehicle
											Purchase Info Types</a></li>
									<li class="divider"></li>
									<li class="dropdown-header">Fuel Settings</li>
									<%-- <li><a href="<c:url value="/addVehicleFuel" />">Fuel
											Types</a></li> --%>
									<li><a href="<c:url value="/addVehicleFuelPermission" />">Fuel
											Types</a></li>
									<li class="divider"></li>
									<li class="dropdown-header">Driver Settings</li>
									<li><a href="<c:url value="/addDriverDocType" />">Driver
											Renewal Types</a></li>
									<sec:authorize access="hasAuthority('DRIVER_TRAINING_TYPE')">		
									<li><a href="<c:url value="/addDriverTrainingType" />">Driver
											Training Types</a></li>
									</sec:authorize>		
									<li><a href="<c:url value="/addDriverJobType" />">Driver
											Job Types</a></li>
									<sec:authorize access="hasAuthority('DRIVER_BASIC_DETAILS_TYPE')">
									<li><a href="<c:url value="/driverBasicDetailsType.in"/>">Driver Basic Details Type</a></li>
									</sec:authorize>		
								</ul>
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Tyre Settings</li>
									<li><a href="<c:url value="/addVehicleTyresize" />">Tyre
											Size Types</a></li>
									<li><a href="<c:url value="/addTyreModelType" />">Tyre
											Manufacturers</a></li>
									<li><a href="<c:url value="/addModelSubType" />">Tyre
											Model Types</a></li>
									<li><a href="<c:url value="/TyreExpense" />">
											Tyre Expenses</a></li>
									<li>
										<sec:authorize access="hasAuthority('VEHICLE_TYRE_ASSIGNMENT')">
											<a href="<c:url value="/vehicleTyreAssignment.in?data=" />"> Tyre Assign</a>
										</sec:authorize>		
									</li>
									<li class="divider"></li>
									<li class="dropdown-header">Reminder Settings</li>
									<li><a href="<c:url value="/renewalTypeAjax.in"/>">Renewal
											Types</a></li>
									<li><a href="<c:url value="/renewalSubTypeAjax.in"/>">Renewal
											Sub Types</a></li>
									<li class="divider"></li>
									<li class="dropdown-header">Parts Settings</li>
									<li><a href="<c:url value="/addPartCategories.in"/>">Part
											Categories</a></li>
									<li><a href="<c:url value="/addPartLocations.in"/>">Part
											Locations</a></li>
									<li><a href="<c:url value="/addPartManufacturer.in"/>">Part
											Manufacturers</a></li>
									<li><a href="<c:url value="/addPartMeasurementUnit.in"/>">Measurement
											Units</a></li>
									<sec:authorize access="hasAuthority('SHOW_PART_SUB_CATEGORY')">		
									<li><a href="<c:url value="addPartSubCategories" />">Part
											Sub Category</a></li>
									</sec:authorize>
									<sec:authorize access="hasAuthority('LABOUR_MASTER')">		
									<li><a href="<c:url value="labour" />">Labour Master</a></li>
									</sec:authorize>
								</ul>
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">

									<li class="dropdown-header">Work Order Task</li>
									<li><a href="<c:url value="/addJobType.in"/>">Job Task
											Types</a></li>
									<li><a href="<c:url value="/addJobSubType/1.in"/>">Job
											ROT Types</a></li>
									<sec:authorize access="hasAuthority('ADD_REASON_REPAIR_TYPES')">
										<li><a href="<c:url value="addReasonForRepairType"/>">
											Reason Repair Type</a></li>
									</sec:authorize>
											
									<li class="divider"></li>
									<li class="dropdown-header">Vendor Settings</li>
									<li><a href="<c:url value="/addVendorType.in"/>">Vendor
											Types</a></li>
									<li><a href="<c:url value="/partyMaster.in"/>">Party Master
											</a></li>		
									<li class="divider"></li>
									<li class="dropdown-header">Trip Settings</li>
									<li><a href="<c:url value="/newTripRoute/1.in"/>">Trip
											Route</a></li>
									<li><a href="<c:url value="/addTripExpense/1.in"/>">Trip
											/ Cash Expense</a></li>
									<li><a href="<c:url value="/addTripIncome/1/1.in"/>">Trip
											/ Cash Income</a></li>											
									<!--newy-->		
									
									<sec:authorize access="hasAuthority('ADD_LOAD_TYPES')">
									<li><a href="<c:url value="/addLoadTypes.in"/>">Load Type Master</a></li>		
									</sec:authorize>											
									
									<li class="dropdown-header">CashBook Settings</li>
									<li><a href="<c:url value="/addCashBookName.in"/>">CashBook
											Name</a></li>
										
								</ul>
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Bank Settings</li>
									<sec:authorize access="hasAuthority('ADD_BANK')">
										<li><a href="<c:url value="/addBank.in"/>">Bank
												Name</a></li>
									</sec:authorize>		
									<sec:authorize access="hasAuthority('ADD_BANK_ACCOUNT')">		
									<li><a href="<c:url value="/addBankAccount/1.in"/>">Bank
											Account</a></li>	</sec:authorize>			
								</ul>

								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Battery Settings</li>
									<sec:authorize access="hasAuthority('ADD_BATTERY_MANFATURER')">
										<li><a href="<c:url value="/addBatteryManfaturer.in"/>">Battery
												Manufacturer</a></li>
									</sec:authorize>		
									<sec:authorize access="hasAuthority('ADD_BATTERY_TYPE')">		
									<li><a href="<c:url value="/addBatteryType.in"/>">Battery
											Type</a></li>	</sec:authorize>
									<sec:authorize access="hasAuthority('ADD_BATTERY_CAPACITY')">		
										<li><a href="<c:url value="/addBatteryCapacity.in"/>">Battery
											C20 Capacity</a></li>	</sec:authorize>				
								</ul>
								<sec:authorize access="hasAuthority('TRIP_SHEET_OPTIONS_EXTRA')">
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Trip Sheet Settings</li>
									<sec:authorize access="hasAuthority('TRIP_SHEET_OPTIONS_EXTRA')">
										<li><a href="<c:url value="/addTripSheetOptions"/>">Trip Sheet
												Options</a></li>
									</sec:authorize>		
													
								</ul>
								</sec:authorize>
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Inspection Settings</li>
									<sec:authorize access="hasAuthority('ADD_VEHICLE_INSPECTION_PARAMETER')">
										<li><a href="<c:url value="/addInspectionParameter.in"/>">Inspection Parameters</a></li>
									</sec:authorize>		
									<sec:authorize access="hasAuthority('ADD_VEHICLE_INSPECTION_SHEET')">		
									<li><a href="<c:url value="/ViewInspectionSheet.in"/>">Inspection Sheet</a></li>	</sec:authorize>
								</ul>
							</sec:authorize>
							
							<sec:authorize access="!hasAuthority('VIEW_TRIPSHEET_ONLY')">
								<sec:authorize access="hasAuthority('TALLY_COMPANY_MASTER')">
									<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
											<li class="dropdown-header">Tally Settings</li>
											<li><a href="<c:url value="/addTallyCompany.in"/>">Tally Company Master</a></li>
									</ul>
								</sec:authorize>	
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
										<li class="dropdown-header">Tool Box Settings</li>
										<li><a href="<c:url value="/addToolBox.in"/>">Vehicle Tool Box </a></li>
								</ul>
							</sec:authorize>
							<sec:authorize access="hasAuthority('CLOTH_INVENTORY_SETTING')">
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
										<li class="dropdown-header">Upholstery Settings</li>
										<sec:authorize access="hasAuthority('ADD_CLOTH_TYPES')">
											<li><a href="<c:url value="/addClothTypes.in"/>">Upholstery Types</a></li>
										</sec:authorize>
										<sec:authorize access="hasAuthority('ADD_CLOTH_TYPES')">
											<li><a href="<c:url value="/addVehicleClothMaxAllowed.in"/>">Assign Max Qnty To Vehicle</a></li>
										</sec:authorize>		
								</ul>
							</sec:authorize>
							
							<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
										<sec:authorize access="hasAuthority('ADD_UREA_TYPES')">
										<li class="dropdown-header">Urea Settings</li>
										<li><a href="<c:url value="/addUreaTypes.in"/>">Urea Manufacturer Master</a></li>
										</sec:authorize>		
							</ul>
							
							<sec:authorize access="hasAuthority('MARQUEE_MASTER')">
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">Marquee Settings</li>
									<li><a href="<c:url value="/MarqueeMasterRestController/addMarqueeMaster.in"/>">Marquee Master</a></li>
								</ul>
							</sec:authorize>
							
							<sec:authorize access="hasAuthority('PICK_AND_DROP_MASTER')">
								<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
									<li class="dropdown-header">PickAndDrop Settings</li>
									<li><a href="<c:url value="/PickAndDropLocation.in"/>">PickAndDrop Master</a></li>
								</ul>
							</sec:authorize>
							
							<ul class="col-md-3 col-sm-3 col-xs-3 mega-dd-col">
								<li class="dropdown-header">Comment Master</li>
								<li><a href="<c:url value="viewCommentType.in"/>">Comment Type</a></li>
							</ul>
						</div>
					</div></li>
				<li class="dropdown user user-menu"><a
					href="javascript:void(0)" id="userid" class="dropdown-toggle"
					data-toggle="dropdown" aria-expanded="false"> <sec:authentication
							var="principal" property="principal" /> <%-- <img
						src="${pageContext.request.contextPath}/ProfilePicture/${principal.username}.in"
						class="user-image" alt="User Image"> --%> <img
						src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/user.png"/>"
						class="user-image" alt="User Image"> <span class="hidden-xs"
						id="loginName"> <!-- <security:authorize access="isAuthenticated()">
								<security:authentication property="principal.username" />
							</security:authorize> -->
					</span>
					<sec:authentication var="principal" property="principal" />
				</a><input type="hidden" id="customUserId" value="${principal.id}">
					<input type="hidden" id="customCompanyId" value="${principal.company_id}">
					<ul class="dropdown-menu">
					
						<!-- User image -->
						<li class="user-header" id="subtaskto">
							<!-- <img src="dist/img/user2-160x160.jpg"
							class="img-circle" alt="User Image">
							<p>
								Fleetop - Developer <small>Member since
									Nov. 2016</small>
							</p> --> <!-- <p id="subtaskto" ></p> -->
						</li>
						<li class="user-body">
							<div class="text-center">
								<a href="<c:url value="/UserChangePassword.in"/>"><spring:message
										code="label.pages.updatepassword"></spring:message></a>
							</div>
						</li>
						<li class="user-footer">
							<div class="pull-left">
								<a href="<c:url value="/UserProfile"/>"
									class="btn btn-default btn-flat"><spring:message
										code="label.pages.profile" /></a>
							</div>
							<div class="pull-right">
								<a class="btn btn-default btn-flat"
									href="<c:url value="/fleetop_logout" />"><spring:message
										code="label.pages.logout"></spring:message></a>
							</div>
						</li>
					</ul></li>
				<li><a href="<c:url value="/fleetop_logout" />"><i
						class="fa fa-sign-out" aria-hidden="true"> Sign out</i></a></li>
			</ul>
		</div>
	</nav>
	<marquee SCROLLDELAY=100  style="heigth: 4px; hspace:10px" id="message"> </marquee>
<!-- <script type="text/html" src="resources/QhyvOb0m3EjE7A4/js/fleetop/header.js"></script> -->
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/header.js" />"></script>
</header>
