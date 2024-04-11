<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">

<style>
    .cell-effort-driven {
      text-align: center;
    }
    .slick-group-title[level='0'] {
      font-weight: bold;
    }
    .slick-group-title[level='1'] {
      text-decoration: underline;
    }
    .slick-group-title[level='2'] {
      font-style: italic;
    }
    .slick-headerrow-column {
      background: #87ceeb;
      text-overflow: clip;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
    .slick-headerrow-column input {
      margin: 0;
      padding: 0;
      width: 100%;
      height: 100%;
      -moz-box-sizing: border-box;
      box-sizing: border-box;
    }
 </style>	


<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/<a href="<c:url value="/TSR.in"/>">Trip sheet Report</a> / <span>Trip
						Sheet Date Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide" id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">
					<br>
					<div class="row1">
						<label class="L-size control-label">Group: </label>
						<div class="I-size">
							<input type="hidden" id="workOrderGroup1" name="WORKORDER_GROUP"
								style="width: 100%;" placeholder="ALL" />
						</div>
					</div>

					<div class="row1">
						<label class="L-size control-label">Date : <abbr
							title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group input-append date" id="IssuesDailydate">
								<input type="text" class="form-text" name="TRIP_DATE_RANGE"
									id="dateRange" required="required"
									data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
									class="input-group-addon add-on"> <span
									class="fa fa-calendar"></span>
								</span>
							</div>
						</div>
					</div>
					<br />
					<div class="row1">
						<label class="L-size control-label"></label>

						<div class="I-size">
							<div class="pull-left">
								<button type="submit" name="commit" class="btn btn-success"
									id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</section>
	<section class="content" id="ResultContent" style="display: none;">
		<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					<section class="invoice">

						<!--exp  start-->
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<input type="hidden" id="reportHeader" >
										<div class="row invoice-info" id="reportHeader1"
											style="font-size: 15px; font-weight: bold;"></div>

										<br>
										<div id="invoicePurchase" style="display: none;">
											<table class="" style="width: 95%">
												<tr>
													<td style="font-size: 12px;">Group : <a
														id="groupIdentity" href="#"></a></td>
												</tr>
												<tr>
													<td style="font-size: 12px;">Date : <a
														id="dateRangeIdentity" href="#"></a></td>
												</tr>
											</table>
										</div>


									</div>
								</div>
							</div>
						</div>
						<!--exp  stop-->

						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
										<table class="table table-hover table-bordered table-striped"
											id="companyTable" style="display: none;">
											<tbody id="companyHeader">

											</tbody>
										</table>


										<div id="selectedData" style="text-align: left;">
											<table>
												<tr>
													<td style="display: none; font-weight: bold;"
														id="companyName"></td>
												</tr>
												<tbody id="selectedReportDetails">
												</tbody>
											</table>
										</div>
										<br /> <br />
										<div class="row invoice-info">
											<table width="100%">
												<tr>
													<td valign="top" width="100%">

														<div id="gridContainer">
															<div id="myGrid"></div>
															<div id="pager" style="width: 100%; height: 20px;"></div>

														</div>
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
				</div>
	</section>
</div>


	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/Print/print.js"></script>
	

	
<!-- 	<script type="text/javascript"  -->
<%-- 	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/DriverWiseFuelEntryReport.js"/>"></script> --%>
	
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
	<!-- <script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper.js"></script> -->
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>

	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/tripsheet/AllGroupTripsheetDateReport.js"/>"></script>

