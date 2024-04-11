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
					/<span id="reportHead">Create Invoice Payment </span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						 id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm hide"
						onclick="advanceTableToExcel('tripCollectionExpenseList', 'Create Pick And Drop')" id="exportExcelBtn">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
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
							
							<div class="row1">
								<label class="L-size control-label"> Party
									Name : <abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="hidden" name="partyName"
										id="partyId"  style="width: 100%;" 
										placeholder="Please Enter Party Name" />
								</div>
							</div>	
							
							
							<br/>
						
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
		
		
		<div class="content hide" id="contentBody">
			<div class="main-body">
				<div class="box">
					<div class="box-body">
						
						<div class="panel panel-success hide"  id="settle-border-boxshadow">
							<div class="panel-heading text-center">
								<h4> Pick Or Drop Invoice Payment
								</h4>
							</div>
						</div>	

						<table class="table table-bordered hide" id="settleTable" style="width: 100%">
								<thead>
				    				<tr class="text-info text-center" style="height: 35px;">
				    					<th>Select</th>
				    					<th>Invoice No</th>
				    					<th>Party Name</th>
				    					<th>Invoice Date</th>
				    					<th>Amount</th>
				    					<th>Balance Amount</th>
				    					<th>Payment Type</th>
				    					<th>Transaction No</th>
				    					<th>Payment Mode</th>
				    					<th>Payment Date</th>
				    					<th>Paid Amount</th>
				    					<th class="hide"></th>
				    					
				    				</tr>
				    			</thead>
				    			<tbody id="settleDetails">
				    			</tbody>
				    			
						</table>
						
						</br>
						
						<button type="button" id="UpSaveButton" class="saveBtn btn btn-success text-center" data-tooltip="Save">
							<span>Make Payment</span>
						</button> 
					 
						<!-- <div class="table-responsive">
							<table id="VendorPaymentTable" class="table table-hover table-bordered">
							</table>
						</div> -->
					</div>
				</div>
				
			</div>
		</div>	
		
	</section>
	
	
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/CreatePickOrDropInvoicePayment.js" />"></script>
	
	
	
	
</div>