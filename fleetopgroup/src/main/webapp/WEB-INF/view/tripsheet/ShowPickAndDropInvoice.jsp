<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">

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
							code="label.master.home" /></a> / <a href="<c:url value="/newTripSheetPickAndDrop"/>">TripSheet Pick & Drop</a>
					/ <a href="<c:url value="/createPickOrDropInvoice"/>">Create Invoice</a>
					/<span id="reportHead">Invoice Details </span>
				</div>
				<div class="pull-right">
					<div style="display: inline-block; width: 100px"></div>
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					
					<sec:authorize access="hasAuthority('CANCEL_PICK_OR_DROP_INVOICE')">
						<button class="btn btn-danger btn-sm"
							onclick="deleteInvoice(${invoiceSummaryId})">
							<span class="fa fa-print"> Cancel Invoice</span>
						</button>
					</sec:authorize>
						
					<!-- <button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Fleetop')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button> -->
					<a href="<c:url value="/createPickOrDropInvoice"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>

	<input type ="hidden" id="invoiceSummaryId" value="${invoiceSummaryId}">
	
	<div class="content" id="contentBody" style="padding-left: 12%;">
			<div id="div_print" class="main-body" style="width:85%;">
				<div class="box">
					<div class="box-body">
						
						<div class="panel panel-success"  id="settle-border-boxshadow">
							<div class="panel-heading text-center">
								<h4> Invoice
								</h4>
							</div>
						</div>
						
						<div class="row">
							<div class="pull-left">
								<h5>
									Party Name : 
										<span id="partyName" > </span>
								</h5>
							</div>
							
							<div class="pull-right">
								<h5>Invoice No : <span id="invoiceNo"></span></h5>
							</div>
						</div>
						
						
						<div class="row">
							<div class="pull-left">
								<h5>
									Invoice Date : 
										<span id="invoiceDate" > </span>
								</h5>
							</div>
							
							<div class="pull-right">
								<h5>Mobile No : <span id="mobileNo"></span></h5>
							</div>
						</div>		
					 	
					 	
					 	<div class="row">
							<div class="pull-left">
								<h5>
									Address: 
										<span id="address" > </span>
								</h5>
							</div>
							
							<div class="pull-right">
								<h5>
									GST No: 
										<span id="gstNo" > </span>
								</h5>
							</div>
						</div>
						
						</br>
						
					 	
					 	<div class="row">
							<div class="table-responsive">
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								</table>
							</div>
						</div>
						
						</br>	
						
						<div class="row">
							<div class="pull-left">
								<h5>
									Amount In Words: 
										<span id="amount" > </span>
								</h5>
							</div>
						</div>
						
					</div>
				</div>
				
			</div>
		</div>
	
	
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	<script type="text/javascript" 
			src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/ShowPickAndDropInvoice.js" />"></script>
	
	
	
	
</div>