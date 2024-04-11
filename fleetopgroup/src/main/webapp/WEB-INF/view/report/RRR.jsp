<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
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
					/ <a href="<c:url value="/RRR.in"/>">Renewal Reminder Report</a>
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



				<!-- Renewal Reminder -->
				<div class="tab-pane" id="RRReport">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="renwalReminderReport"/>">Renewal Reminder Report  </a>
							</h4>
						</div>
					</div>
				
					<sec:authorize access="hasAuthority('VIEW_RR_CO_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RRComplianceReport"/>">Renewal Reminder Compliance Report  </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_TO_VE_CO_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RRVehicleComplianceReport"/>">Total Vehicle Wise Compliance Report  </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_TO_GO_CO_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RRGroupComplianceReport"/>">Total Group Wise Compliance Report  </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_AP_RR_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/RRApprovalReport"/>">Approval Renewal Reminder Report  </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('RENEWAL_EXPIRY_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/getRenewalExpiryReport"/>">Renewal SubType Wise Expiry Report  </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('VIEW_RR_OVERDUE_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/getRenewalOverDueReport"/>">Renewal Reminder Overdue/DueSoon Report</a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					
					<sec:authorize access="hasAuthority('MANDATORY_RENEWAL_PENDING_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/mandatoryRenewalPendingReport"/>">Mandatory Renewal Pending Report </a>
							</h4>
						</div>
					</div>
					</sec:authorize>
					
					
				</div>
			</div>
		</div>
	</section>

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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>