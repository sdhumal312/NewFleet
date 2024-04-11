<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<h1>
			<a class="btn btn-default" onclick="history.go(-1); return true;">
				<span class="fa fa-arrow-left"> Back</span>
			</a> <small><a href="Vehicle.in"><span id="Vehic">Home</span></a>
				/ <span id="NewVehi">All Report</span></small>

		</h1>
		<div class="pull-right">

			<div id="langSelect"></div>
		</div>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<!-- Small boxes (Stat box) -->
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${VehicleCount}</h3>
								<p>Vehicle</p>
							</div>
							<div class="icon">
								<a href="VehicleReport.in"><i class="fa fa-bus"></i></a>
							</div>
							<a href="VehicleReport.in" class="small-box-footer"> Search
								More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${DriverCount}</h3>
								<p>Driver</p>
							</div>
							<div class="icon">
								<a href="DriverReport.in"><i class="fa fa-user"></i></a>
							</div>
							<a href="DriverReport.in" class="small-box-footer"> Search
								More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${TripSheetCount}</h3>
								<p>Trip Sheet</p>
							</div>
							<div class="icon">
								<a href="TripSheetReport.in"><i class="fa fa-file-text-o"></i></a>
							</div>
							<a href="TripSheetReport.in" class="small-box-footer"> Search
								More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>

				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${FuelCount}</h3>
								<p>Fuel</p>
							</div>
							<div class="icon">
								<a href="FuelReport.in"><i class="fa fa-tint"></i></a>
							</div>
							<a href="FuelReport.in" class="small-box-footer"> Search More
								<i class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<!-- ./col -->

					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${RenewalReminderCount}</h3>
								<p>Renewal Reminder</p>
							</div>
							<div class="icon">
								<a href="RenewalReminderReport.in"><i class="fa fa-bell"></i></a>
							</div>
							<a href="RenewalReminderReport.in" class="small-box-footer">
								Search More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>

					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${ServiceReminderCount}</h3>
								<p>Service Reminder</p>
							</div>
							<div class="icon">
								<a href="ServiceReminderReport.in"><i class="fa fa-bell-slash"></i></a>
							</div>
							<a href="ServiceReminderReport.in" class="small-box-footer">
								Search More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>



					<!-- ./col -->
				</div>
				<!-- /.row -->
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${VendorCount}</h3>
								<p>Vendor</p>
							</div>
							<div class="icon">
								<a href="VendorReport.in"><i class="fa fa-building-o"></i></a>
							</div>
							<a href="VendorReport.in" class="small-box-footer"> Search
								More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>

					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>0</h3>
								<p>Issues</p>
							</div>
							<div class="icon">
								<i class="fa fa-info-circle"></i>
							</div>
							<a href="TripSheetReport.in" class="small-box-footer"> Search
								More <i class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${MasterPartCount}</h3>
								<p>Parts</p>
							</div>
							<div class="icon">
								<a href="PartReport.in"><i class="fa fa-cube"></i></a>
							</div>
							<a href="PartReport.in" class="small-box-footer"> Search More
								<i class="fa fa-search"></i>
							</a>
						</div>
					</div>

				</div>
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${PurchaseOrderCount}</h3>
								<p>Purchase Order</p>
							</div>
							<div class="icon">
								<a href="PurchaseOrderReport.in"><i class="fa fa-shopping-cart"></i></a>
							</div>
							<a href="PurchaseOrderReport.in" class="small-box-footer"> Search More
								<i class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-xs-6">
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${InventoryCount}</h3>
								<p>Inventory</p>
							</div>
							<div class="icon">
								<a href="InventoryReport.in"><i class="fa fa-cubes"></i></a>
							</div>
							<a href="InventoryReport.in" class="small-box-footer"> Search More <i
								class="fa fa-search"></i>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-xs-6">
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${WorkOrderCount}</h3>
								<p>Work Order</p>
							</div>
							<div class="icon">
								<a href="WorkOrdersReport.in"><i class="fa fa-file-text-o"></i></a>
							</div>
							<a href="WorkOrdersReport.in" class="small-box-footer"> Search More <i
								class="fa fa-search"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>