<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<ul class="nav nav-list">
	<li class="dropdown-header">Vehicle Settings</li>
	<%-- <li><a href="<c:url value="/addVehicleStatus.in"/>"><i
			class="fa fa-star"></i> Vehicle Statues</a></li> --%>
			
				<li><a href="<c:url value="/addVehicleStatusPermission.in"/>"><i
											class="fa fa-star"></i> Vehicle Statues</a></li>
			
	<li><a href="<c:url value="/addVehicleTypes.in"/>"><span
			class="fa fa-keyboard-o"></span> Vehicle Types</a></li>
	<li><a href="<c:url value="/addVehicleGroup.in"/>"><span
			class="fa fa-users"></span> Vehicle Group</a></li>
	<li class="dropdown-header">Vehicle Document Settings</li>
	<li><a href="<c:url value="/addVehicleDocType.in"/>"><span
			class="fa fa-file-text"></span> Vehicle Document Types</a></li>
	<li><a href="<c:url value="/addVehiclePhoType.in"/>"><span
			class="fa fa-file-image-o"></span> Vehicle Photo Types</a></li>
	<li><a href="<c:url value="/addVehiclePurchaseInfoType.in"/>"><span
			class="fa fa-shopping-cart"></span> Vehicle Purchase Info Types</a></li>
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
			class="fa fa-map"></span> Part Locations</a></li>
	<li><a href="<c:url value="/addPartManufacturer.in"/>"><span
			class="fa fa-cube"></span> Part Manufacturers</a></li>
	<li><a href="<c:url value="/addPartMeasurementUnit.in"/>"><span
			class="fa fa-cube"></span> Part Measurement Unit</a></li>
	<li class="dropdown-header">WorkOrders Settings</li>
	<li><a href="<c:url value="/addJobType.in"/>"><span
			class="fa fa-file"></span> Job Types</a></li>
	<li><a href="<c:url value="/addJobSubType/1.in"/>"><span
			class="fa fa-file"></span> Job ROT Types</a></li>
	<li class="dropdown-header"> Trip Settings</li>
	<li><a href="<c:url value="/newTripRoute/1.in"/>"><span
			class="fa fa-file"></span> Trip Route</a></li>
	<li><a href="<c:url value="/addTripExpense/1.in"/>"><span
			class="fa fa-file"></span> Trip Expense</a></li>
	<li><a href="<c:url value="//addTripIncome/1/1.in"/>"><span
			class="fa fa-money"></span> Trip Income</a></li>
	<li class="dropdown-header">CashBook Settings</li>
	<li><a href="<c:url value="/addCashBookName.in"/>"><span
			class="fa fa-book"></span> CashBook Name</a></li>
</ul>