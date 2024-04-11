<%@ include file="../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a>/ Vendor Lorry Hire List
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/addVendorLorryHireDetails.in"/>"> <span
							class="fa fa-plus"></span> Add Vendor Lorry Hire
						</a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="<c:url value="/makeLorryHirePayment.in"/>">Add Lorry Hire Payment
						</a>
					</sec:authorize>
				</div>
				<!-- 
				<div>
				<button id ="pageNumber"  onclick="getPageWiseVendorPaymentDetails(1)" > ok </button>
				</div> -->
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('LORRY_HIRE_PAYMENT')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT')">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Vendor Lorry Hire</span> 
							<span id="VendorPaymentCount" class="info-box-number"></span>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		<div class="row">
			<sec:authorize access="!hasAuthority('LORRY_HIRE_PAYMENT')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('LORRY_HIRE_PAYMENT')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								
								</table>
							</div>
						</div>
					</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script>
		$(document).ready(function() {
			getPageWiseVendorLorryHireDetails(1);	
		});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/lorryhire/VendorLorryHireList.js" />"></script>
	
</div>