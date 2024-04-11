<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/batteryReport.in"/>">Battery Reports</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/Report"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-2 col-md-8 col-sm-8 col-xs-12">
				<sec:authorize access="hasAuthority('VIEW_BATTERY_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/batteryTransferReport"/>">Battery Transfer Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_BATTERY_SCRAP_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/batteryScrapReport"/>">Battery Scrap Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_BATTERY_STOCK_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/batteryStockReport"/>">Battery Stock Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<sec:authorize access="hasAuthority('BATTERY_PURCHASE_INVOICE_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/batteryPurchaseInvoiceReport"/>">Battery Purchase Invoice Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
					<sec:authorize access="hasAuthority('VEHICLE_WISE_BATTERY_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/VehicleWiseBatteryReport"/>">All Vehicle Battery Asignment Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<sec:authorize access="hasAuthority('BATTERY_WISE_HISTORY_REPORT')"> 
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a href="<c:url value="/BatteryWiseHistoryReport"/>">Battery History Report</a>
								</h4>
							</div>
						</div>
 				</sec:authorize> 
			</div>
		</div>
	</section>
</div>
