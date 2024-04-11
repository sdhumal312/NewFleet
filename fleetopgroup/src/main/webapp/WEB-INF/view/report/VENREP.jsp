<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VENREP.in"/>">Vendor Reports</a>
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
				<sec:authorize access="hasAuthority('VIEW_VEN_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/VendorPaymentReport"/>">Vendor Payment Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				
				<sec:authorize access="hasAuthority('VIEW_VEN_GST_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/VendorGstReport"/>">Vendor GST Report </a>
							</h4>
						</div>
					</div>
				</sec:authorize>
				
				<!--Vendor Payment History Report Start-->
			 <sec:authorize access="hasAuthority('Vendor_Payment_History_Report')">
				<div class="panel box box-primary">
					<div class="box-header with-border">
						<h4 class="box-title">
							<a href="<c:url value="/VendorPaymentHistoryReport"/>">Vendor Payment History Report</a>
						</h4>
					</div>
				</div>
				</sec:authorize> 
				<!--Vendor Payment History Report Stop-->
				
				<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/LorryHirePaymenReport.in"/>">Vendor Lorry Hire Payment Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize> 
				
				<!-- Vendor Lorry Hire Details Report Start-->
				<sec:authorize access="hasAuthority('VENDOR_LORRY_HIRE_DETAILS_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/VendorLorryHireDetailsReport"/>">Vendor Lorry Hire Details Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize> 
				<sec:authorize access="hasAuthority('VENDOR_LORRY_HIRE_DETAILS_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a href="<c:url value="/VendorLorryHireOutstandingReport"/>">Vendor Lorry Hire Outstanding Report</a>
							</h4>
						</div>
					</div>
				</sec:authorize> 
				<!-- Vendor Lorry Hire Details Report Stop-->
				
				<sec:authorize access="hasAuthority('VIEW_VEN_SE_REPORT')">
						<div class="panel box box-primary">
							<div class="box-header with-border">
								<h4 class="box-title">
										<a 
											href="<c:url value="/VendorServiceEntriesReport"/>"> Vendor Wise Service Entries Report </a>
								</h4>
							</div>
						</div>
				   </sec:authorize>
				   
				 <sec:authorize access="hasAuthority('VENDOR_WISE_PAYMENT_STATUS_REPORT')">
						<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
									<a 
										href="<c:url value="/VendorWisePaymentStatusReport"/>">Vendor Wise Payment Status Report</a>
							</h4>
						</div>
					</div>
				 </sec:authorize>  
				
			</div>
		</div>
	</section>
</div>