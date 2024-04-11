<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/IR.in"/>">Issues Report</a> / <span>
						Issues Report</span>
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
		<sec:authorize access="!hasAuthority('VIEW_ISSUES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>

		<sec:authorize access="hasAuthority('VIEW_IS_RE_BY_REPORT')">
			<!-- Your Only REport YourIssuesStatusReport-->
			<div class="panel box box-primary">
				<div class="box-header with-border">
					<h4 class="box-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#IssuesThere">Issues Reported By Report </a>
					</h4>
				</div>
				<!-- <div id="IssuesThere" class="panel-collapse collapse"> -->
				<div class="box-body">
					<form>
						<div class="form-horizontal ">
							<!-- Issues Status -->
							<div class="row1">
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label">Issues Type :<abbr
										title="required">*</abbr></label>
									<div class="I-size">
										<select name="ISSUES_TYPE_ID" id="IssuesType"
											style="width: 100%;">
													<option value="1">Vehicle Issue</option>
														<option value="2">Driver Issue</option>
														<option value="3">Refund Issue</option>
														<c:if test="${configuration.customerIssue}">
															<option value="4">Customer Issue</option>
														</c:if>
														<option value="5">Other Issue</option>
														<option value="6">Vehicle BreakDown</option>
														<option value="-1">ALL</option>
										</select>
									</div>
								</div>
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label">Labels</label>
									<div class="I-size">
										<select class="form-text" name="ISSUES_LABELS_ID" id="labelId"
											required="required">
													<option value="-1">ALL</option>
													<option value="1">NORMAL</option>
													<c:if test="${!configuration.hideHighStatus}">
														<option value="2">HIGH</option>
													</c:if>
													<c:if test="${!configuration.hideLowStatus}">
														<option value="3">LOW</option>
													</c:if>
													<option value="4">URGENT</option>
													<option value="5">VERY URGENT</option>
													<c:if test="${showBreakdownSelection}">
														<option value="6">BREAKDOWN</option>													
													</c:if>	
										</select>
									</div>
								</div>
							</div>

							<div class="row1">
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label">Issues Status :</label>
									<div class="I-size">
										<select name="ISSUES_STATUS_ID" id="issueStatusId"
											style="width: 100%;">
											<option value="-1">ALL</option>
											<option value="1">OPEN</option>
											<option value="4">RESOLVED</option>
											<option value="5">REJECT</option>
											<option value="2">CLOSED</option>
										</select>
									</div>
								</div>
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label"> Reported By : </label>
									<div class="I-size">
										<input class="" placeholder="Plese Select."
											id="subscribeReport" type="hidden" style="width: 100%"
											name="ISSUES_REPORTED_BY_ID">
									</div>
								</div>
							</div>


							<div class="row1">
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label"> Assigned To : </label>
									<div class="I-size">
										<input class="" placeholder="Plese Select."
											id="subscribeAssign" type="hidden" style="width: 100%"
											name="ISSUES_ASSIGN_TO">
									</div>
								</div>
								<div class="col col-sm-1 col-md-5">
									<label class="L-size control-label">Date range: <abbr
										title="required">*</abbr>
									</label>
									<div class="I-size">
										<div class="input-group">
											<div class="input-group-addon">
												<i class="fa fa-calendar"></i>
											</div>
											<input type="text" id="VehWorkOrder" class="form-text"
												name="ISSUES_REPORTED_DATE" required="required"
												style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
										</div>
									</div>
								</div>
							</div>
							<div class="row1">

								<label class="L-size control-label"></label>

								<div class="I-size">
									<div class="">
										<button type="submit" name="commit" class="btn btn-success">
											<i class="fa fa-search"> Search</i>
										</button>
									</div>
								</div>

							</div>
						</div>
					</form>
				</div>
				<!-- </div> -->
			</div>
		</sec:authorize>

		<section class="content" id="ResultContent" style="display: none;">
			<div class="box-body">
				<div id="div_print">
					<div id="div_print">
						<section class="invoice">

							<div class="row invoice-info">
								<div class="col-xs-12">
									<div class="table-responsive">
										<div id="sorttable-div" align="center" style="font-size: 10px"
											class="table-responsive ">
											<div class="row invoice-info" id="reportHeader"
												style="font-size: 15px; font-weight: bold;"></div>

											<div id="invoicePurchase" style="display: none;">
												<table class="" style="width: 95%">
													<tr>
														<td style="font-size: 12px;">Date Range : <span
															id="dateRangeIdentity"></span></td>
													</tr>
												</table>
											</div>

											<div class="row invoice-info">
												<table id="tripCollExpenseName" style="width: 95%;"
													class="table-hover table-bordered">
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>

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

															</div>
														</td>

													</tr>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
			</div>
		</section>

	</section>

	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
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

	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
	<script type="text/javascript"
		src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/NewIssueReport.js"/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>


</div>