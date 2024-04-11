<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
			
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newTripSheetPickAndDrop.in"/>">TripSheet PickAndDrop</a>
				</div>
				
				<div class="col-md-off-5">
					<div class="col-md-2">
						<%-- <form action="<c:url value="/searchTripSheetShow.in"/>"
							method="post"> --%>
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">TS-</span></span>
								<input class="form-text" id="tripStutes" name="tripStutes"
									type="number" min="1" required="required" 
									placeholder="TS-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="searchTripsheet();"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						<!-- </form> -->
					</div>
						
						<div class="pull-right">
						<sec:authorize access="hasAuthority('CREATE_PICK_OR_DROP')">
								<a class="btn btn-success btn-sm" href="createPickAndDropTrip.in"> <span
									class="fa fa-plus"></span> Create TripSheet
								</a>
						</sec:authorize>
							
						<sec:authorize access="hasAuthority('CREATE_PICK_OR_DROP_INVOICE')">		
								<a class="btn btn-warning btn-sm" target="_blank" href="createPickOrDropInvoice.in"> <span
									class="fa fa-plus"></span> Create Invoice
								</a>
						</sec:authorize>
						
								
						<sec:authorize access="hasAuthority('CREATE_PICK_OR_DROP_INVOICE_PAYMENT')">
								<a class="btn btn-danger btn-sm" target="_blank" href="createPickOrDropInvoicePayment.in"> <span
									class="fa fa-plus"></span> Invoice Payment
								</a>
						</sec:authorize>
						</div>
					
					<sec:authorize access="hasAuthority('EDIT_PICK_OR_DROP')">
						<input type="hidden" id="editPermision" value=true>
					</sec:authorize>
					
					<sec:authorize access="hasAuthority('DELETE_PICK_OR_DROP')">
						<input type ="hidden" id="deletePermision" value=true>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>
	
	
		<!-- Main content -->
	
		<div class="content" >
			<div class="main-body">
				<div class="box">
					<div class="box-body">
					 
						<div class="table-responsive">
							<input type="hidden" id="startPage" value="${SelectPage}"> 
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
		</div>	
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/NewTripSheetPickAndDrop.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
</div>