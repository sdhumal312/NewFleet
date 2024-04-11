<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<ul class="nav nav-list">
	<%-- <li class="active"><sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>">Overview</a>
		</sec:authorize></li> --%>

	<li id="ownerInfo"><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
			<a href="<c:url value="/ShowVehicleOwner.in?vehid=${vehicle.vid}"/>">
				Owner Information</a>
		</sec:authorize></li>
	<li id="mandatoryCompliance"><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
			<a
				href="<c:url value="/ShowVehicleMandatory.in?vehid=${vehicle.vid}"/>">
				Mandatory Compliance</a>
		</sec:authorize></li>
		<c:if test="${!inspConfig.sheetToVehicleType }">
			<li><sec:authorize access="hasAuthority('VIEW_VEHICLE_ASSIGNMENT_TO_SHEET')">
				<a href="<c:url value="/ShowVehicleInspectionSheet.in?vehid=${vehicle.vid}"/>">
					Inspection Sheet</a>
			</sec:authorize></li>
		</c:if>
	<li id="breakDownCalendar"><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
			<a
				href="<c:url value="/ShowVehicleBreakDownCalender.in?vehid=${vehicle.vid}"/>">
				Break Down Calendar <span class="pull-right badge bg-aqua">${breakDownCount}</span></a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_DOCUMENT')">
			<!-- old flow module -->
			<%-- <a
				href="<c:url value="/ShowVehicleDocument.in?vehid=${vehicle.vid}"/>">
				Documents <span class="pull-right badge bg-aqua">${document_Count}</span>
			</a> --%>
			<a
				href="<c:url value="/ViewVehicleDocument.in?vehid=${vehicle.vid}"/>">
				Vehicle/Renewal Documents <span class="pull-right badge bg-aqua">${document_Count}</span>
			</a>
		</sec:authorize></li>
		
		<li><sec:authorize access="hasAuthority('FLAVOR_FOUR_PRIVILEGE')">
			<a href="<c:url value="/vehicleWiseTripSheetPickAndDrop.in?vid=${vehicle.vid}"/>">
				Tripsheet PickUp & Drop <span class="pull-right badge bg-aqua">${pickUpDropCount}</span>
			</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PHOTO')">
			<a href="<c:url value="/ShowVehiclePhoto.in?vehid=${vehicle.vid}"/>">
				Photos <span class="pull-right badge bg-aqua">${photo_Count}</span>
			</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_PURCHASE')">
			<a
				href="<c:url value="/ShowVehiclePurchase.in?vehid=${vehicle.vid}"/>">
				Purchase Info <span class="pull-right badge bg-aqua">${purchase_Count}</span>
			</a>
		</sec:authorize></li>

	<!-- old flow -->
	<%-- <li><sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<a
				href="<c:url value="/VehicleRenewalReminder.in?vid=${vehicle.vid}"/>">
				Renewal Reminders <span class="pull-right badge bg-aqua">${reminder_Count}</span>
			</a>
		</sec:authorize></li> --%>
	<li><sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<a
				href="<c:url value="/ViewVehicleWiseRenewalReminder.in?vid=${vehicle.vid}"/>">
				Renewal Reminders <span class="pull-right badge bg-aqua">${reminder_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_FUEL')">
			<a
				href="<c:url value="/VehicleFuelDetails/1.in?vid=${vehicle.vid}"/>">
				Fuel Entries <span class="pull-right badge bg-aqua">${fuelEntrie_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
			<a
				href="<c:url value="/VehicleServiceEntriesDetails.in?vid=${vehicle.vid}"/>">
				Service Entries <span class="pull-right badge bg-aqua">${serviceEntrie_Count}</span>
			</a>
		</sec:authorize></li>
		
			<li><sec:authorize access="hasAuthority('VIEW_DEALER_SERVICE_ENTRIES')">
			<a
				href="<c:url value="/getVehicleDealerServiceDetails.in?id=${vehicle.vid}"/>">
				Dealer Service Entries <span class="pull-right badge bg-aqua">${dseCount}</span>
			</a>
		</sec:authorize></li>
		
		
		
		
	<li><sec:authorize access="hasAuthority('VIEW_SERVICE_REMINDER')">
			<a
				href="<c:url value="/VehicleServiceDetails.in?vid=${vehicle.vid}"/>">
				Service Reminder <span class="pull-right badge bg-aqua">${serviceReminder_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
			<a href="<c:url value="/VehicleTripDetails/${vehicle.vid}/1"/>">
				Trip Sheet <span class="pull-right badge bg-aqua">${tripsheet_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_WORKORDER')">
			<a
				href="<c:url value="/VehicleWorkOrderDetails/${vehicle.vid}/1.in"/>">
				Work Orders <span class="pull-right badge bg-aqua">${workOrder_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_ISSUES')">
			<a
				href="<c:url value="/VehicleIssuesDetails.in?vid=${vehicle.vid}"/>">
				Issues <span class="pull-right badge bg-aqua">${issues_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_ODOMETER')">
			<a
				href="<c:url value="/VehicleOdometerDetails.in?vid=${vehicle.vid}"/>">
				Odometer History <span class="pull-right badge bg-aqua">${odometerhis_Count}</span>
			</a>
		</sec:authorize></li>

	<li><sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_COMMENT')">
			<a href="<c:url value="/VehicleCommentDetails?Id=${vehicle.vid}"/>">
				Comments <span class="pull-right badge bg-aqua">${comment_Count}</span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<a href="<c:url value="/ViewVehicleInspection/1.in?vid=${vehicle.vid}"/>">
				Vehicle Inspection <span class="pull-right badge bg-aqua">${inspectionCount}</span>
			</a>
		</sec:authorize></li>	
	<li>
	<c:if test="${configDriverSalary.vehicleModelTyreLayout}">
		<a href="<c:url value="/showVehicleTyreAssignedDetails?id=${vehicle.vid}"/>">
			Tyre <span class="pull-right badge bg-aqua"></span>
		</a>
	</c:if>
	<c:if test="${!configDriverSalary.vehicleModelTyreLayout}">
		<sec:authorize access="hasAuthority('ADDEDIT_VEHICLE_TYRE')">
			<a href="<c:url value="/VehicleTyreDetails?Id=${vehicle.vid}"/>">
				Tyre <span class="pull-right badge bg-aqua"></span>
			</a>
		</sec:authorize>
	</c:if>
	</li>
	<li><sec:authorize access="hasAuthority('VIEW_EMI_DETAILS')">
			<a href="<c:url value="/VehicleEmiDetails?Id=${vehicle.vid}"/>">
				EMI Details  <span class="pull-right badge bg-aqua"></span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('VIEW_BATTERY_INVENTORY')">
			<a href="<c:url value="/VehicleBatteryDetails?Id=${vehicle.vid}"/>">
				Battery  <span class="pull-right badge bg-aqua"></span>
			</a>
		</sec:authorize></li>
	<li><sec:authorize access="hasAuthority('ADD_VEHICLE_CLOTH_INVENTORY_DETAILS')">
			<a href="<c:url value="/VehiclePartInventoryDetails?Id=${vehicle.vid}"/>">
				Upholstery Inventory  <span class="pull-right badge bg-aqua"></span>
			</a>
		</sec:authorize></li>	
	<li>
	
	<sec:authorize access="hasAuthority('ADD_VEHICLE_TOOL_BOX_DETAILS')">
			<a href="<c:url value="/ToolBoxDetails?Id=${vehicle.vid}"/>">
				ToolBox  <span class="pull-right badge bg-aqua"></span>
			</a>
	</sec:authorize>
	
	</li>
		
		
	<li class="divider"></li>


</ul>
