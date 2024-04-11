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
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/viewServiceEntries.in"/>"> Service Entries</a>
				</div>
				
				<div class="col-md-off-5">
					<div class="col-md-3">
						<div class="input-group">
							<span class="input-group-addon"> <span aria-hidden="true">SE-</span></span>
							<input class="form-text" id="searchByNumber"
								name="Search" type="number" min="1" required="required"
								placeholder="ID eg: 2323"> <span class="input-group-btn">
								<button type="submit" name="search" id="search-btn" onclick="return serachServiceEntryByNumber();" class="btn btn-success btn-sm">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
				
				<div class="pull-right">
					<a class="btn btn-danger" href="<c:url value="/viewServiceEntries.in"/>">Cancel</a>
				</div>
				
			</div>
		</div>
	</section>

	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">
				
							<div class="row1">
								<label class="L-size control-label" for="issue_vehicle_id">Vehicle
								</label>
								<div class="I-size">
									<input type="hidden" id="vehicle_vid" name="vid" value=""
										style="width: 100%;" required="required"
										placeholder="Please Enter 2 or more Vehicle Name" />
								</div>
							</div>
							
							<div class="row1">
								<label class="L-size control-label">Driver Name :</label>
								<div class="I-size" id="driverSelect">
									<input type="hidden" id="SelectDriverName"
										name="driver_id" style="width: 100%;" value=""
										required="required"
										placeholder="Please Enter 2 or more Driver Name" />

								</div>
							</div>
							
							<div class="row1">
								<label class="L-size control-label">Vendor :</label>
								<div class="I-size" id="vendorSelect">
									<input type="hidden" id="selectVendor" name="Vendor_id"
										style="width: 100%;" required="required" value=""
										placeholder="Please Select Vendor Name" /> <label
										class="error" id="errorVendorSelect"> </label>

								</div>
							</div>
							
							<div class="row1">
								<label class="L-size control-label">Invoice Date: </label>
								<div class="I-size">
									<div class="input-group input-append date" id="StartDate">
										<input type="text" class="form-text" name="invoiceDate" id="invoiceDate"
											data-inputmask="'alias': 'dd-mm-yyyy'" readonly="readonly" data-mask="" />
										<span class="input-group-addon add-on"> <span
											class="fa fa-calendar"></span>
										</span>
									</div>
								</div>
							</div>
							
							<div class="row1">
								<label class="L-size control-label">Modes of
									Payment </label>
								<div class="I-size">
									<select class="form-text" name="service_paymentTypeId"
										id="renPT_option">
										<option value="0"></option>
										<option value="1">Cash</option>
										<option value="2">CREDIT</option>
										<option value="3">NEFT</option>
										<option value="4">RTGS</option>
										<option value="5">IMPS</option>

									</select>
								</div>
							</div>
							
							<!-- Date range -->
							<div class="row1">
								<label class="L-size control-label">Paid Date range: <abbr
									title="required">*</abbr></label>
								<div class="I-size">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-calendar"></i>
										</div>
										<input type="text" id="dateRange" class="form-text"
											name="PART_RANGE_DATE" required="required" readonly="readonly"
											style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
									</div>
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
		
		<div class="row">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/SE/SearchServiceEntries.js" />"></script>	
	
</div>