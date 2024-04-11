<%@ include file="taglib.jsp"%>
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
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/CBR.in"/>">Cash Book Report</a>
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
			

				<sec:authorize access="hasAuthority('FLAVOR_ONE_PRIVILEGE')">
				
				
				<!--Cash Book Date Range Report For Srs Travels By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_CB_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CashBookdateRangeReport"/>">Cash Book Date Range Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--Cash Book Date Range Report For Srs Travels By Dev Yogi End-->
				
				
				<!--Cash Date Range Report For Srs Travels By Dev Yogi Start -->
				<sec:authorize access="hasAuthority('VIEW_CA_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CashdateRangeReport"/>">Cash Date Range Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<!--Cash Date Range Report For Srs Travels By Dev Yogi End-->
				
				
				<!--CashBook Payment/Receipt Date Range Report For Srs Travels By Dev Yogi Start-->
				<sec:authorize access="hasAuthority('VIEW_CB_PR_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CashPaymentdateRangeReport"/>">CashBook Payment/Receipt Date Range Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				<!--CashBook Payment/Receipt Date Range Report For Srs Travels By Dev Yogi End-->
				
				
				
				
				</sec:authorize>

				
				<!--Separation between srs and durgamba may be -->
				
				
				
				
				
				<sec:authorize access="hasAuthority('FLAVOR_TWO_PRIVILEGE')">
				<!--Cash Book Date Range Report  By Dev Yogi Starting -->
				<sec:authorize access="hasAuthority('VIEW_CB_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/SRICashBookdateRangeReport"/>">Cash Book Date Range Report </a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--Cash Book Date Range Report  By Dev Yogi Ending  -->
				
				
				<!--Cash  Date Range Report  By Dev Yogi Starting -->
				<sec:authorize access="hasAuthority('VIEW_CA_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/SRICashdateRangeReport"/>">Cash Date Range Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<!--Cash Date Range Report  By Dev Yogi Ending  -->
				
				
				<!--CashBook Payment/Receipt Date Range Report  By Dev Yogi Starting  -->

				<sec:authorize access="hasAuthority('VIEW_CB_PR_DA_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/SRICashPaymentdateRangeReport"/>">CashBook Payment/Receipt Date Range Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>
				
				<!--CashBook Payment/Receipt Date Range Report  By Dev Yogi Ending  -->
				
				<!--Cash Book Status Downward code temporarily disabled  -->
				<%-- <sec:authorize access="hasAuthority('VIEW_WO_PA_CO_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CashBookStatusReport"/>">CashBook Status Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>		 --%>	
				

				</sec:authorize>

				<!-- /*********************************************************/ /*******
				BOTH CASH BOOK FORMAT *******************/
				/********************************************************/ -->



				<!--CashBook Status Report Code By Dev Yogi Start -->
				<sec:authorize access="hasAuthority('VIEW_CB_ST_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/CashBookStatusReport"/>">CashBook Status Report</a>
								</h4>
							</div>
						</div>
				</sec:authorize>				
				<!--CashBook Status Report Code By Dev Yogi end-->


				<!--CashBook Status Report Original Code Start -->	
				<%-- <sec:authorize access="hasAuthority('VIEW_CB_ST_REPORT')">
					<!-- end Show Group Search Tyre Range -->
					<!-- Start Show Cash Book Range -->
					<div class="panel box box-warning">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseCashFour"> CashBook Status Report </a>
							</h4>
						</div>
						<div id="collapseCashFour" class="panel-collapse collapse">
							<div class="box-body">
								<form action="CashBookStatusReport" method="post">
									<div class="form-horizontal ">
										<div class="row1">
											<label class="L-size control-label" for="issue_vehicle_id">Cash
												Book No :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="CashBookNameDate2"
													name="CASH_BOOK_ID" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more Cash Book Name" />
												<p class="help-block">Select One Or More Vehicle</p>
												<label class="error" id="errorVendorName"
													style="display: none"> </label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Status : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="CASH_STATUS_ID">
													<option value="1">NOT APPROVED</option>
													<option value="2">APPROVED</option>
												</select>

											</div>
										</div>
										<!-- Date range -->
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="reportrange" class="form-text"
														name="CASH_BOOK_DATE" required="required"
														style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit"
															class="btn btn-success">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
										</fieldset>

									</div>
								</form>
							</div>
						</div>
					</div>
				</sec:authorize> --%>
				<!--CashBook Status Report Original Code end-->
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/CB/CashBook.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script type="text/javascript">
		$(function() {

			function cb(start, end) {
				$('#reportrange').val(
						start.format('YYYY-MM-DD') + ' to '
								+ end.format('YYYY-MM-DD'));
			}
			cb(moment().subtract(1, 'days'), moment());

			$('#reportrange').daterangepicker(
					{
						ranges : {
							'Today' : [ moment(), moment() ],
							'Yesterday' : [ moment().subtract(1, 'days'),
									moment().subtract(1, 'days') ],
							'Last 7 Days' : [ moment().subtract(6, 'days'),
									moment() ]
						}
					}, cb);

		});
	</script>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
			
		});
	</script>
</div>