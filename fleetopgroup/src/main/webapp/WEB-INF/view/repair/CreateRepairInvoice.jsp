<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/bootstrap/bootstrap-float-label.min.css" />">
<style>
.row {
	width: 100%;
	margin: 10px auto;
	padding:1%;
}
.label_font{
	font-weight: bold;
	font-size: larger;
}
.noBackGround{
	background: none;
}


.col{
	margin-top: 20px;
}
.custom-select{
	height: 42px;
    font-size: 15px;
 }
.select2-container {
	width: 100%;
	padding: 0;
}
.select2-container-multi .select2-choices {
    min-height: 38px;
}


.select2-container .select2-choice {
   height: 36px;
}


</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/repairViewList.in"/>">Stock Repair</a> / 
					<span>Create Repair Stock Invoice</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/repairViewList.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('CENTRALISED_REPAIR')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('CENTRALISED_REPAIR')">
			<div class="row">
				<div class="col-sm-8 col-md-12">
					<input type="hidden" id="companyId" value="${companyId}" />
					<input type="hidden" id="userId" value="${userId}" />
					<div class="tab-content">
						<div class="box" >
							<div class="box-body">
								 <label class="has-float-label ">
								  <span style="color: #2e74e6;font-size: 24px;">Repair Stock Invoice</span>
								</label>
								<br>
								<div class="col col-sm-1 col-md-3">
									<label class="has-float-label"> 
										<select name="purchaseorder_typeId" id="repairTypeId" class="browser-default custom-select" required="required"  onchange="validateVendor();"  >
											<option value="1">Part Repair</option>
											<option value="2">Tyre Repair</option>
											<option value="3">Battery Repair</option>
										</select> 
										<span style="color: #2e74e6; font-size: 18px;">Repair Stock Type  <abbr title="required">*</abbr></span>
									</label>
								</div>
								<div class="col col-sm-1 col-md-3">
									<label class="has-float-label"> 
										<select name="purchaseorder_typeId"  id="repairWorkshopId" onchange="validateVendor();" class="browser-default custom-select" required="required">
											<option value="1">Own Workshop</option>
											<option value="2">Dealer Workshop</option>
										</select> 
										<span style="color: #2e74e6; font-size: 18px;">Repair Workshop  <abbr title="required">*</abbr></span>
									</label>
								</div>
								<div class="col col-sm-12 col-md-3">
								  <label class="has-float-label">
								    <div class="input-group input-append date" id="repairOpenDate">
										<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
											id="createRepairInvoiceDate" required="required"
											data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
											<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
									</div>
								    <span style="color: #2e74e6;font-size: 18px;">Sent Date <abbr title="required">*</abbr></span>
								  </label>
								</div>
								<div class="col col-sm-12 col-md-3">
								 	<label class="has-float-label">
								   	 	<div class="input-group input-append date" id="stockRequiredDate">
											<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="invoiceDate" readonly="readonly"
												id="repairStockRequiredDate" required="required"
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
												<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
										</div>		
								    <span style="color: #2e74e6;font-size: 18px;">Required Date <abbr title="required">*</abbr></span>
								  </label>
								</div>
								<div class="col col-sm-1 col-md-3" id="locationDiv" >
									 <label class="has-float-label">
									      <input type="hidden" id="locationId" class="browser-default"  style="line-height: 30px;font-size: 15px;height: 35px;">
									    <span style="color: #2e74e6;font-size: 18px;">From Location <abbr title="required">*</abbr></span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-3" id="additionalPartLocationDiv" >
									 <label class="has-float-label">
									      <input type="hidden" id="additionalPartLocationId" class="browser-default"  style="line-height: 30px;font-size: 15px;height: 35px;">
									    <span style="color: #2e74e6;font-size: 18px;">To Location <abbr title="required">*</abbr></span>
									  </label>
								</div>
								<div class="col col-sm-1 col-md-3"  id="vendorDiv" style="display:none">
									 <label class="has-float-label">
									      <input type="hidden" id="vendorId" class="browser-default" style="line-height: 30px;font-size: 15px;height: 35px;">
									    <span style="color: #2e74e6;font-size: 18px;">Vendor <abbr title="required">*</abbr></span>
									  </label>
								</div>
								<div class="col col-sm-12 col-md-3">
								  	<label class="has-float-label">
								    	<input type="text" class="form-control browser-default custom-select noBackGround" id="referenceNumber"  >
								   	 	<span style="color: #2e74e6;font-size: 18px;">Reference Number </span>
								  	</label>
								 </div>	
								
							</div>
							 <div class="row" >
								<div class="col col-sm-12 col-md-6">
								  <label class="has-float-label">
								   <textarea  class="form-control browser-default custom-select noBackGround" id="description"></textarea>
								    <span style="color: #2e74e6;font-size: 18px;">Remark </span>
								  </label>
							  	</div>
							</div>
						</div>
					</div>
					<div class="row" >
						<button type="submit" onclick="return saveRepairStockInvoice();"  class="btn btn-success" >Next</button> &nbsp;&nbsp;
						<a class=" btn btn-info" href="dealerServiceEntries.in">
						<span id="Cancel">Cancel</span></a>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/repair/CreateRepairInvoice.js"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>		
</div>