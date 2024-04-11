
<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/monthpicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<div class="content-wrapper">

	<!-- Header Part  Start-->
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Vehicle
						Creation Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vehicle Creation Report')"
						id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<!-- Header Part  End-->


	<section class="content">

	<%-- 	<sec:authorize
			access="hasAuthority('VEHICLE_CREATION_REPORT')"> --%>
		<div class="tab-pane active" id="vehicleReport">
			<div class="panel box box-primary">

				<div class="box-header with-border">
					<h4 class="box-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseOneTwo"> Vehicle Creation Report </a>
					</h4>
				</div>

				<div class="box-body">
					<div class="form-horizontal ">

						<div class="row1">
							<label class="L-size control-label">Month: <abbr
								title="required">*</abbr></label>
							<div class="I-size">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input type="text" id="monthRangeSelector" class="form-text"
										name="FROM_DATERANGE" required="required"
										style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
								</div>
							</div>

						</div>
						<!-- Devy Date Field Script Start Which is Mandatory -->


						<!--Search Button Start Hint:Fuel Reference-->
						<fieldset class="form-actions">
							<div class="row1">
								<label class="L-size control-label"></label>

								<div class="I-size">
									<div class="pull-left">
										<button type="submit" name="commit" class="btn btn-success"
											id="btn-search">
											<i class="fa fa-search"> Search </i>
										</button>
									</div>
								</div>
							</div>
						</fieldset>
						<!--Search Button End -->
						<div id="div_print">
						<table id="advanceTable" border=1 width="100%">
						
						</table>
						</div>
				
						<!--Pop Up Logic Start-->
						<div class="modal fade" id="myModal" role="dialog">
							<div class="modal-dialog modal-md">
								<input type="hidden" id="  " name="  " value=" " />
								<div class="modal-content">
									<div class="modal-body">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<div class="row invoice-info" id="reportHeader"
											style="font-size: 15px; font-weight: bold;"></div>
									</div>
									<div class="row invoice-info">
										<table id="tableinfo" style="width: 100%;"
											class="table-hover table-bordered">

										</table>
									</div>
								</div>


							</div>
						</div>
						<!--Pop Up Logic End-->


					</div>

				</div>

			</div>
		</div>
		<%-- </sec:authorize> --%>
	</section>


	<script type="text/javascript">
		$(document).ready(function() {
			$('#monthRangeSelector').Monthpicker({
				monthLabels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]
			});
		});
	</script>


	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/monthpicker.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/VehicleCreationReport.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
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

</div>