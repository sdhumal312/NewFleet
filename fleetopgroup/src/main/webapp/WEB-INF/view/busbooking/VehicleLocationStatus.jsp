<%@ include file="../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-12">
						<div class="form-group">
						<label class="control-label" >
							<div class="I-size">
								<div class="input-group input-append date" id="BusLocationDate">
									<input type="text" class="form-text" name="fuel_date" readonly="readonly"
											id="locationDate" required="required" style="width:auto" placeholder="Date"
											data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
									<span class="input-group-addon add-on">
										<span class="fa fa-calendar"></span>
									</span>
								</div>
							</div>
						</label>
						</div>
						<div class="form-group"><input id="vehicleId" style="width: 70%"  placeholder="Vehicle No"/></div>
	       	 			<div class="form-group"><input id="driverAllList" style="width: 70%"  placeholder="DriverName"/></div>
						<div class="form-group"><input id="warehouselocation" style="width: 70%"  placeholder="From"/></div>
						<div class="form-group"><input id="Partwarehouselocation" style="width: 70%"  placeholder="To"/></div>
				 		<button type="button" class="btn btn-primary" onclick="saveLocation()">+Create Bus Book</button>
			 		</div>
			 		<div class="pull-right">
			 		
					<button class="btn btn-primary"  
						onclick="printDiv('multipleTable')">
						<span class="fa fa-print"> Print</span>
					</button>
					</div>
			 		<div>
			 		<div class="col-md-8 col-sm-3 col-xs-12">
			 			<div class="row">
							<label class="control-label">Date range:</label>
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="dateRangeReport" class="form-text"
										name="repair_daterange" required="required"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 30%">
										
										<div class="pull-left">
									<button type="button" name="commit" class="btn btn-success" id="btn-save" onclick="searchData()">
										<i class="fa fa-search"> Search</i>
									</button>
								</div>
								</div>
						</div>

						<div class="row">
							<div class="table-responsive" id="multipleTable" style="margin-top: 20px;"></div>
						</div>
			 		</div>
                 </div>
			</div>
		</div>
	
	</section>
	<section>
		<div class="modal fade" id="outBusLocationPanel" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Out Bus Location</h4>
					</div>
						<div class="modal-body">
							<div class="row" id="grpReportDailydate" class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="dealerServiceSearchDate" class="form-text"
										name="searchDate" required="required" readonly="readonly"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
								
							</div>
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="updateOutDateTime();" class="btn btn-success">Out</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
						</div>
				</div>
			</div>
		</div>
	</section>
	</div>
	
	
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/busbooking/VehicleLocationStatus.js"/>"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
