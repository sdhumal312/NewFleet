<%@ include file="taglib.jsp"%>
<style>
.bs-glyphicons {
	margin: 0 -10px 20px;
	overflow: hidden;
}

.bs-glyphicons-list {
	padding-left: 0;
	list-style: none;
}

.bs-glyphicons li {
	float: left;
	width: 25%;
	height: 115px;
	padding: 10px;
	font-size: 10px;
	line-height: 1.4;
	text-align: center;
	background-color: #f9f9f9;
	border: 1px solid #fff;
}

.bs-glyphicons .fa {
	margin-top: 8px;
	margin-bottom: 10px;
	font-size: 50px;
}

.bs-glyphicons .glyphicon-class {
	display: block;
	text-align: center;
	word-wrap: break-word; /* Help out IE10+ with class names */
}

.bs-glyphicons a {
	color: #000000;
	font-weight: 900;
	font-size: 13px;
}

.bs-glyphicons li:hover {
	color: #000000;
	background-color: #ffcb05;
}

@media ( min-width : 768px) {
	.bs-glyphicons {
		margin-right: 0;
		margin-left: 0;
	}
	.bs-glyphicons li {
		width: 12.5%;
		font-size: 12px;
	}
}
</style>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <span> Report </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/open"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="bs-glyphicons">
				<ul class="bs-glyphicons-list">
					<sec:authorize
						access="hasAuthority('VIEW_VEHICLE_REPORT') or hasAuthority('VIEW_VE_WI_RE_REPORT') or hasAuthority('VIEW_ALL_VE_WI_SE_REPORT')">
						<li><a href="<c:url value="/VR.in"/>"><span
								class="fa fa-bus" aria-hidden="true"></span> <span
								class="glyphicon-class">Vehicle Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_DR_DE_REPORT') or hasAuthority('VIEW_DR_CO_RE_REPORT')
					or hasAuthority('VIEW_DL_EX_DA_REPORT')
					or hasAuthority('VIEW_DR_MO_ES_PF_REPORT')
					or hasAuthority('VIEW_DR_LO_HA_REPORT')
					or hasAuthority('VIEW_TS_HA_BA_REPORT')
					or hasAuthority('VIEW_DE_PE_REPORT')
					or hasAuthority('VIEW_DR_CO_PE_REPORT')
					or hasAuthority('VIEW_DE_PE_REPORT')
					or hasAuthority('VIEW_DO_AD_REPORT')
					or hasAuthority('VIEW_DR_CO_AD_REPORT')
					or hasAuthority('VIEW_AD_PE_REPORT')">
						<li><a href="<c:url value="/DR.in"/>"><span
								class="fa fa-user" aria-hidden="true"></span> <span
								class="glyphicon-class">Driver Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_RR_CO_REPORT') 
					or hasAuthority('VIEW_TO_VE_CO_REPORT')
					or hasAuthority('VIEW_TO_GO_CO_REPORT')
					or hasAuthority('VIEW_AP_RR_REPORT')">
						<li><a href="<c:url value="/RRR.in"/>"><span
								class="fa fa-bell" aria-hidden="true"></span> <span
								class="glyphicon-class">Renewal Reminder Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_VE_FU_MI_REPORT') 
					or hasAuthority('VIEW_FU_RA_REPORT')
					or hasAuthority('VIEW_FU_CO_REPORT')">
						<li><a href="<c:url value="/FR.in"/>"><span
								class="fa fa-tint" aria-hidden="true"></span> <span
								class="glyphicon-class">Fuel Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_DR_AT_REPORT')
					or hasAuthority('VIEW_GO_DR_AT_REPORT')
					or hasAuthority('VIEW_DR_PO_REPORT')
					or hasAuthority('VIEW_GO_DR_PO_REPORT')
					or hasAuthority('VIEW_DR_LO_HA_REPORT')
					or hasAuthority('VIEW_TS_HA_BA_REPORT')
					or hasAuthority('VIEW_DO_DA_AT_REPORT')">
						<li><a href="<c:url value="/AR.in"/>"><span
								class="fa fa-file-text-o" aria-hidden="true"></span> <span
								class="glyphicon-class">Attendance Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_OV_SR_REPORT') or hasAuthority('VIEW_DU_SR_REPORT')">
						<li><a href="<c:url value="/SRR.in"/>"><span
								class="fa fa-bell-slash" aria-hidden="true"></span> <span
								class="glyphicon-class">Service Reminder Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_IS_DA_ST_REPORT') or hasAuthority('VIEW_IS_RE_REPORT')  or hasAuthority('VIEW_IS_RE_BY_REPORT')">
						<li><a href="<c:url value="/IR.in"/>"><span
								class="fa fa-exclamation-circle" aria-hidden="true"></span> <span
								class="glyphicon-class">Issues Report</span></a></li>
					</sec:authorize>

					<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">

						<sec:authorize
							access="hasAuthority('VIEW_US_TS_AD_REPORT') 
						or hasAuthority('VIEW_BR_TS_AD_REPORT')
						or hasAuthority('VIEW_DA_TS_AD_REPORT')
						or hasAuthority('VIEW_TS_DA_REPORT')
						or hasAuthority('VIEW_CL_TS_AD_REPORT')
						or hasAuthority('VIEW_TS_CO_REPORT')
						or hasAuthority('VIEW_TS_ST_REPORT')
						or hasAuthority('VIEW_TS_RO_DI_KM_REPORT')
						">
							<li><a href="<c:url value="/TSR.in"/>"><span
									class="fa fa-road" aria-hidden="true"></span> <span
									class="glyphicon-class">Trip sheet Report</span></a></li>
						</sec:authorize>
					</sec:authorize>

					<sec:authorize access="hasAuthority('FLAVOR_THREE_PRIVILEGE')">
						<sec:authorize
							access="hasAuthority('VIEW_DA_TC_REPORT')
						or hasAuthority('VIEW_VE_TC_REPORT')
						or hasAuthority('VIEW_GR_TC_REPORT')
						or hasAuthority('VIEW_DA_TC_REPORT')
						or hasAuthority('VIEW_TO_DR_BA_REPORT')
						or hasAuthority('VIEW_TO_CO_BA_REPORT')
						or hasAuthority('VIEW_DR_JA_TC_REPORT')
						or hasAuthority('VIEW_CO_JA_TC_REPORT')
						or hasAuthority('VIEW_DR_AD_JA_REPORT')
						or hasAuthority('VIEW_CO_AD_JA_REPORT')
						 ">
							<li><a href="<c:url value="/TCR.in"/>"><span
									class="fa fa-file-text-o" aria-hidden="true"></span> <span
									class="glyphicon-class">Trip collection Report</span></a></li>
						</sec:authorize>
					</sec:authorize>

					<sec:authorize access="hasAuthority('FLAVOR_TWO_PRIVILEGE')">
						<sec:authorize
							access="hasAuthority('VIEW_DE_DA_TD_CB_REPORT') 
						or hasAuthority('VIEW_DE_DA_TD_REPORT')
						or hasAuthority('VIEW_DE_VE_TI_REPORT')
						or hasAuthority('VIEW_DE_RO_TI_REPORT')
						or hasAuthority('VIEW_VE_DA_TD_REPORT')
						or hasAuthority('VIEW_VE_FU_MI_REPORT')
						or hasAuthority('VIEW_DA_FU_MI_REPORT')
						or hasAuthority('VEHICLE_WISE_FUEL_MILEAGE_REPORT')
						or hasAuthority('VIEW_CO_TD_REPORT')
						or hasAuthority('VIEW_RO_DA_TD_REPORT')
						or hasAuthority('VIEW_AL_RO_DA_TD_REPORT')						
						or hasAuthority('VIEW_DA_TD_REPORT')">
							<li><a href="<c:url value="/TDR.in"/>"><span
									class="fa fa-road" aria-hidden="true"></span> <span
									class="glyphicon-class">Trip Daily Collection Report</span></a></li>
						</sec:authorize>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_PA_ST_REPORT') 
					or hasAuthority('VIEW_DA_IN_ST_REPORT')
					or hasAuthority('VIEW_PA_PU_REPORT')
					or hasAuthority('VIEW_PA_TR_REPORT')">
						<li><a href="<c:url value="/PR.in"/>"><span
								class="fa fa-cutlery" aria-hidden="true"></span> <span
								class="glyphicon-class">Part Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_TY_PO_REPORT')
					or hasAuthority('VIEW_TY_ST_REPORT')
					or hasAuthority('VIEW_TY_RE_REPORT')">
						<li><a href="<c:url value="/TR.in"/>"><span
								class="fa fa-circle-o" aria-hidden="true"></span> <span
								class="glyphicon-class">Tyre Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_VE_PO_REPORT')
					or hasAuthority('VIEW_DA_PO_REPORT')
					or hasAuthority('VIEW_PU_TO_PA_REPORT')">
						<li><a href="<c:url value="/POR.in"/>"><span
								class="fa fa-shopping-cart" aria-hidden="true"></span> <span
								class="glyphicon-class">PurchaseOrder Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_LO_WO_REPORT')
					or hasAuthority('VIEW_VE_WO_REPORT')
					or hasAuthority('VIEW_GR_WO_REPORT')
					or hasAuthority('VIEW_WO_PA_CO_REPORT') ">
						<li><a href="<c:url value="/WOR.in"/>"><span
								class="fa fa-list-alt" aria-hidden="true"></span> <span
								class="glyphicon-class">WorkOrder Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_CB_DA_REPORT')
					or hasAuthority('VIEW_CA_DA_REPORT')
					or hasAuthority('VIEW_CB_PR_DA_REPORT')
					or hasAuthority('VIEW_CB_ST_REPORT')">
						<li><a href="<c:url value="/CBR.in"/>"><span
								class="fa fa-book" aria-hidden="true"></span> <span
								class="glyphicon-class">CashBook Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_VEN_SE_REPORT')
					or hasAuthority('VIEW_VEL_SE_REPORT')">
						<li><a href="<c:url value="/SER.in"/>"><span
								class="fa fa-cog" aria-hidden="true"></span> <span
								class="glyphicon-class">Service Entries Report</span></a></li>
					</sec:authorize>
						<sec:authorize
 						access=" hasAuthority('VIEW_DSE_REPORT')"> 
						<li><a href="<c:url value="/DSEREPORT.in"/>"><span
								class="fa fa-cog" aria-hidden="true"></span> <span
								class="glyphicon-class">Dealer Service Report</span></a></li>
					</sec:authorize> 
					<sec:authorize
						access="hasAuthority('VIEW_VEN_REPORT')
					or hasAuthority('VIEW_VEN_REPORT')">
						<li><a href="<c:url value="/VENREP.in"/>"><span
								class="fa fa-users" aria-hidden="true"></span> <span
								class="glyphicon-class">Vendor Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_BATTERY_REPORT')
					or hasAuthority('VIEW_BATTERY_REPORT')">
						<li><a href="<c:url value="/batteryReport.in"/>"><span
								class="fa fa-battery-full" aria-hidden="true"></span> <span
								class="glyphicon-class">Battery Report</span></a></li>
					</sec:authorize>
					
					
					<sec:authorize
						access="hasAuthority('VIEW_P_AND_L_REPORT') or hasAuthority('VIEW_P_AND_L_REPORT')">
						<li><a href="<c:url value="/ProfitAndLoss.in"/>"><span
								class="fa fa-rupee" aria-hidden="true"></span> <span
								class="glyphicon-class">Vehicle Profit & Loss</span></a></li>
					</sec:authorize>
					
					
					<!--Checking Report Module By Dev Y Start-->
					<sec:authorize
						access="hasAuthority('VIEW_CHECKING_ENTRY') or hasAuthority('VIEW_CHECKING_ENTRY')">
						<li><a href="<c:url value="/CR.in"/>"><span
								class="fa fa-user" aria-hidden="true"></span> <span
								class="glyphicon-class">Checking Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE') or hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
						<li><a href="<c:url value="/inspectionReport.in"/>"><span
								class="fa fa-check" aria-hidden="true"></span> <span
								class="glyphicon-class">Inspection Report</span></a></li>
					</sec:authorize>
					<!--Checking Report Module By Dev Y End-->
					
					<sec:authorize
						access="hasAuthority('VIEW_CLOTH_TYPES_INVENTORY')">
						<li><a href="<c:url value="/UR.in"/>"><span
								class="fa fa-cubes" aria-hidden="true"></span> <span
								class="glyphicon-class"> Upholstery Report</span></a></li>
					</sec:authorize>
					
					<sec:authorize
						access="hasAuthority('VIEW_WAREHOUSE_LOCATION_REPORT')">
						<li><a href="<c:url value="/WLR.in"/>"><span
								class="fa fa-cube" aria-hidden="true"></span> <span
								class="glyphicon-class">WareHouse Location Report</span></a></li>
					</sec:authorize>
					
					<sec:authorize
						access="hasAuthority('VIEW_UREA_REPORT')">
						<li><a href="<c:url value="/UreaReport.in"/>"><span
								class="fa fa-tint" aria-hidden="true"></span> <span
								class="glyphicon-class">Urea Report</span></a></li>
					</sec:authorize>
					
					<sec:authorize
						access="hasAuthority('SHOW_PICK_DROP_REPORTS')">
						<li><a href="<c:url value="/pickAndDropReport.in"/>"><span
								class="fa fa-car" aria-hidden="true"></span> <span
								class="glyphicon-class">PickUp & Drop Report</span></a></li>
					</sec:authorize>
					
					
					<sec:authorize
						access="hasAuthority('COMPARISION_REPORT')">
						<li><a href="<c:url value="/comparisionReport.in"/>"><span
								class="fa fa-balance-scale" aria-hidden="true"></span> <span
								class="glyphicon-class">Comparison Report</span></a></li>
					</sec:authorize>
					<sec:authorize
						access="hasAuthority('SHOW_USER_ACTIVITY_REPORT')">
					<li><a href="<c:url value="/UA.in"/>"><span
								class="fa fa-user" aria-hidden="true"></span> <span
								class="glyphicon-class">User Activity</span></a></li>
								</sec:authorize>

								
						<sec:authorize
						access="hasAuthority('REPAIR_STOCK_REPORT')">
					<li><a href="<c:url value="/repairStockReport.in"/>"><span
								class="fa fa-user" aria-hidden="true"></span> <span
								class="glyphicon-class">Repair Stock Report</span></a></li>
								</sec:authorize>			
					
				</ul>
			</div>
		</div>
	</section>

</div>