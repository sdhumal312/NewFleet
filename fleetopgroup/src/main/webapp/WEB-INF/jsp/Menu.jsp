<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/error/error.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/progressbars/progressBar.css" />">
<%@ include file="../view/error/error.html" %>
<%@ include file="../view/progressbars/progressBar.html" %>
<aside class="main-sidebar" style="margin-top:10px;">
	<section class="sidebar" >
		<ul class="sidebar-menu">
			<li><a href="<c:url value="/open.html"/>"><i
					class="fa fa-home"></i> <span>Home</span></a></li>

			<li><sec:authorize access="hasAuthority('VIEW_DASHBOARD')">
					<a href="<c:url value="/Dashboard"/>"><i class="fa fa-qrcode"></i>
						<span>DashBoard</span></a>
				</sec:authorize></li>
			
			<li><sec:authorize access="hasAuthority('SHOW_WORK_SUMMARY')">
				<li><a href="<c:url value="/viewSummary"/>"><i class="fa fa-qrcode"></i>
					<span>Work Summary</span></a></li>
			</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('SHOW_CONSUMPTION_SUMMARY')">
				<li><a href="<c:url value="/viewConsumption"/>"><i class="fa fa-qrcode"></i>
					<span>Consumption Summary</span></a></li>
			</sec:authorize></li>
				
				<li><a href="<c:url value="/Report"/>"><i
						class="fa fa-area-chart"></i> <span>Report</span></a></li>
					
			<sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
				<li><a href="<c:url value="/ImportantReport"/>"><i
						class="fa fa-area-chart"></i> <span>Important Report</span></a></li>
			</sec:authorize>
			<li><a href="<c:url value="/mailbox/1.in"/>"> <i
					class="fa fa-envelope"></i> <span>Mailbox</span>
			</a></li>
			<li><sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<a href="<c:url value="/vehicle/1/1.in"/>"><i class="fa fa-bus"></i>
						<span>Vehicles</span> </a>
				</sec:authorize></li>
				
				
			<li><sec:authorize access="hasAuthority('VIEW_DRIVER')">
					<a href="<c:url value="/getDriversList"/>"> <i
						class="fa fa-user"></i> <span>Driver</span>
					</a>
				</sec:authorize></li>
			
			<li><sec:authorize access="hasAuthority('VIEW_RENEWAL')">
					<a href="<c:url value="/viewRenewalReminder.in"/>">
						<i class="fa fa-bell"></i> <span>Renewal Reminder</span>
					</a>
				</sec:authorize>
			</li>	
			<li><sec:authorize
					access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
					<a href="<c:url value="/RenRemApp/1/1.in"/>"> <i
						class="fa fa-bell"></i> <span>Approved Renewal Reminder</span>
					</a>
				</sec:authorize></li>
				<li class="treeview"><sec:authorize
					access="hasAuthority('VIEW_SERVICE_REMINDER')">
					<a href="#"> <i class="fa fa-bell-slash"></i> <span>Service Reminder</span>
						<i class="fa fa-angle-left pull-right"></i>
					</a>
					<ul class="treeview-menu">
						<li><sec:authorize
							access="hasAuthority('VIEW_SERVICE_REMINDER')">
							<a href="<c:url value="/ViewServiceReminderList.in"/>"> <i
								class="fa fa-bell-slash"></i> <span>Service Reminder</span>
							</a>
						</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_SERVICE_PROGRAM')">
								<a href="<c:url value="/serviceProgram.in"/>"> <i
									class="fa fa-cubes"></i> <span>Service Program</span>
								</a>
						</sec:authorize></li>
					</ul>
				</sec:authorize></li>
				<li><sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
							<a href="<c:url value="/viewServiceEntries.in"/>"> <i
								class="fa fa-bell-slash"></i> <span>Service Entries </span>
							</a>
						</sec:authorize></li>
				<li><sec:authorize access="hasAuthority('DEALER_SERVICE_ENTRIES')">
								<a href="<c:url value="/dealerServiceEntries.in"/>"> <i
									class="fa fa-cubes"></i> <span>Dealer Service</span>
								</a>
						</sec:authorize></li>		
						
		<%-- 	<li class="treeview"><sec:authorize
					access="hasAuthority('VIEW_SERVICE_ENTRIES')">
					<a href="#"> <i class="fa fa-bell-slash"></i> <span>Outside Maintenance  </span>
						<i class="fa fa-angle-left pull-right"></i>
					</a>
					<ul class="treeview-menu">
						<li><sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
							<a href="<c:url value="/viewServiceEntries.in"/>"> <i
								class="fa fa-bell-slash"></i> <span>Service Entries </span>
							</a>
						</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('DEALER_SERVICE_ENTRIES')">
								<a href="<c:url value="/dealerServiceEntries.in"/>"> <i
									class="fa fa-cubes"></i> <span>Dealer Service Entries</span>
								</a>
						</sec:authorize></li>
					</ul>
				</sec:authorize></li> --%>
			
			<li><sec:authorize access="hasAuthority('VIEW_FUEL')">
					<a href="<c:url value="/Fuel/1.in"/>"> <i class="fa fa-tint"></i>
						<span>Fuel Entries</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_UREA_ENTRY')">
					<a href="<c:url value="/UreaEntriesShowList.in"/>"> <i class="fa fa-tint"></i>
						<span>Urea Entries</span>
					</a>
				</sec:authorize></li>
				<li><sec:authorize access="hasAuthority('ADD_REFRESHMENT_INVENTORY')">
					<a href="<c:url value="/RefreshmentEntriesList.in"/>"> <i class="fa fa-flask"></i>
						<span>Refreshment Entries</span>
					</a>
				</sec:authorize></li>	
			<li><sec:authorize access="hasAuthority('VIEW_FUELVENDOR')">
					<a href="<c:url value="/FuelVendor/1.in"/>"> <i
						class="fa fa-tint"></i> <span>Fuel Vendor Entries</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_ISSUES')">
					<a href="<c:url value="/issuesOpen/1.in"/>"> <i
						class="fa fa-exclamation-circle"></i> <span>Issues</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_VENDOR')">
					<a href="<c:url value="/vendorHome.in"/>"> <i
						class="fa fa-building-o"></i> <span>Vendors</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
					<a href="<c:url value="/VendorApprovalCreated/1.in"/>"><i
						class="fa fa-circle-o"></i> <span>Approved Entries</span></a>
				</sec:authorize></li>
			<li class="treeview"><sec:authorize
					access="hasAuthority('VIEW_INVENTORY')">
					<a href="#"> <i class="fa fa-cubes"></i> <span>Inventory</span>
						<i class="fa fa-angle-left pull-right"></i>
					</a>
					<ul class="treeview-menu">
					<li><sec:authorize access="hasAuthority('VIEW_REQUISITION')">
								<a href="<c:url value="/requisition"/>"><i
									class="fa fa-exchange"></i> Centralised Requisition</a>
							</sec:authorize></li>
					
						<li><sec:authorize access="hasAuthority('VIEW_REQUISITION_INVENTORY')">
								<a href="<c:url value="/PartRequisition/1/1"/>"><i
									class="fa fa-circle-o"></i> Part Requisition</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_PART_INVENTORY') or hasAuthority('VIEW_INVENTORY')">
								<a href="<c:url value="/NewInventory/1.in"/>"> <i
									class="fa fa-cubes"></i> <span>Part Inventory</span>
								</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_TYRE_INVENTORY') or hasAuthority('VIEW_INVENTORY')">
								<a href="<c:url value="/TyreInventory/1"/>"><i
									class="fa fa-circle-o"></i> Tyre Inventory</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_BATTERY_INVENTORY')">
								<a href="<c:url value="/BatteryInventory.in"/>"><i
									class="fa fa-battery-0"></i> Battery Inventory</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_CLOTH_TYPES_INVENTORY')">
								<a href="<c:url value="/ClothInventory.in"/>"> <i
									class="fa fa-cubes"></i> <span>Upholstery Inventory</span>
								</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('VIEW_UREA_INVENTORY')">
								<a href="<c:url value="/UreaInventory.in"/>"> <i
									class="fa fa-tint"></i> <span>Urea Inventory</span>
								</a>
							</sec:authorize></li>
						<li><sec:authorize access="hasAuthority('FUEL_INVENTORY')">
								<a href="<c:url value="/FuelInventory.in"/>"> <i
									class="fa fa-tint"></i> <span>Fuel Inventory</span>
								</a>
							</sec:authorize></li>
					</ul>
				</sec:authorize></li>
			<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
				<li><sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a href="<c:url value="/newTripSheetEntries.in"/>"> <i
							class="fa fa-file-text-o"></i> <span>Trip Sheet</span>
						</a>
					</sec:authorize></li>
			</sec:authorize>
			
			<sec:authorize access="hasAuthority('FLAVOR_TWO_PRIVILEGE')"> 
				<li><sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">

						<a href="<c:url value="/newTripDaily.in"/>"> <i
							class="fa fa-file-text-o"></i> <span>Trip Daily Collection</span>
						</a>
						<a href="<c:url value="/TDAllGroup/1.in"/>"> <i
							class="fa fa-file-text-o"></i> <span>All LocationDay
								Collection</span>
						</a>
					</sec:authorize></li>
			</sec:authorize> 
			<sec:authorize access="hasAuthority('FLAVOR_THREE_PRIVILEGE')">
				<li><sec:authorize access="hasAuthority('VIEW_TRIPSHEET')">
						<a href="<c:url value="/newTripCol.in"/>"> <i
							class="fa fa-file-text-o"></i> <span>Trip Collection</span>
						</a>
					</sec:authorize></li>
			</sec:authorize>
			
			<sec:authorize access="hasAuthority('FLAVOR_FOUR_PRIVILEGE')">
				<li>
					<a href="<c:url value="/newTripSheetPickAndDrop.in"/>"> <i
						class="fa fa-car"></i> <span>TripSheet PickUp & Drop</span>
					</a>
				</li>
			</sec:authorize>	
			<li><sec:authorize access="hasAuthority('VIEW_LORRY_HIRE')">
					<a href="<c:url value="/viewVendorLorryHireDetails.in"/>"> <i
						class="fa fa-exclamation-circle"></i> <span>Lorry Hire</span>
					</a>
				</sec:authorize></li>
			
			<li><sec:authorize access="hasAuthority('VIEW_PARTS')">
					<a href="<c:url value="/newMasterParts/1.in"/>"> <i
						class="fa fa-cube"></i> <span>Parts</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('CENTRALISED_REPAIR')">
					<a href="<c:url value="/repairViewList.in"/>"> <i
						class="fa fa-cube"></i> <span>Repairing</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_PURCHASE')">
					<a href="<c:url value="/newPurchaseOrders/1.in"/>"> <i
						class="fa fa-shopping-cart"></i> <span>Purchase Orders</span>
					</a>
				</sec:authorize></li>
			<%-- <li><sec:authorize access="hasAuthority('VIEW_WORKORDER')">
					<a href="<c:url value="/viewWorkOrder.in"/>"> <i
						class="fa fa-file-text-o"></i> <span>Work Order / Job Card</span>
					</a>
				</sec:authorize></li> --%>
				
			<li><sec:authorize access="hasAuthority('VIEW_WORKORDER')">
					<a href="<c:url value="/viewWorkOrder.in"/>"> <i
						class="fa fa-file-text-o"></i> <span>Work Order / Job Card</span>
					</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_CASHBOOK')">
						<a href="<c:url value="/CashBookEntry/1.in"/>"> <em
							class="fa fa-book"></em> <span>Cash Book</span>
						</a>
				</sec:authorize></li>
			<li><sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
						<a href="<c:url value="/ViewVehicleInspectionList.in"/>"> <em
							class="fa fa-check"></em> <span>Vehicle Inspection</span>
						</a>
					</sec:authorize></li>
					<li><sec:authorize access="hasAuthority('BUS_BOOKING_DETAILS')">
						<a href="<c:url value="/viewBusBookingDetails.in"/>"> <em
							class="fa fa-book"></em> <span>Bus Booking</span>
						</a>
					</sec:authorize></li>
				<li><sec:authorize access="hasAuthority('VIEW_VEHICLE_ACCIDENT')">
						<a href="<c:url value="/viewVehicleAccidentList.in"/>"> <i class="fa fa-bus"></i> <span>Vehicle Accident</span>
						</a>
					</sec:authorize></li>		
		</ul>
	</section>
</aside>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/error/error.js" />"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/progressbars/progressBar.js" />"></script>