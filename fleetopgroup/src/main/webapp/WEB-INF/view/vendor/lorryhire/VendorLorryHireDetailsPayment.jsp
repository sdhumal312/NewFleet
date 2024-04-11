<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/divBorderStyle.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vendorHome.in"/>">Vendors</a> / <span
						id="NewVehi">Vendor Lorry Hire</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/vendorHome.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<c:if test="${param.success eq true}">
		<div class="alert alert-success">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Created Successfully.
		</div>
	</c:if>
	<c:if test="${param.danger eq true}">
		<div class="alert alert-danger">
			<button class="close" data-dismiss="alert" type="button">x</button>
			This Inventory Already Exists
		</div>
	</c:if>
	<section class="content">
			<input type="hidden" id="loryHireId" value="${detailsDto.lorryHireDetailsId }">
			<input type="hidden" id="vendorId" value="${detailsDto.vendorId }">
				<div >
						<div class="form-horizontal ">
							<fieldset>
								<div class="box" id="top-border-boxshadow">
									<div class="panel-heading text-center"><h4 >Vendor Lorry Hire Pending Payment Details</h4></div>
									<div class="panel-body">

										<div class="col-md-offset-1 col-md-8">
											<label class="L-size control-label">Vendor :<abbr
												title="required">*</abbr></label>
											<div class="I-size" id="vendorSelect">
												<input type="hidden" id="selectVendor" name="Vendor_id"
													style="width: 100%;" value="0"
													placeholder="Please Select Vendor Name" />
											</div>
										</div>
									</div>
									<br>
								<div class="row1">
									<div class="col-md-10 col-md-offset-2">
										<button type="button" class="btn btn-primary" onclick="setRequestData();">Find</button>
										<a class="btn btn-link"
											href="<c:url value="/vendorHome.in"/>">Cancel</a>
									</div>
								</div>
								<br>
								</div><br>
								<div class="panel panel-success hide"  id="middle-border-boxshadow">
									<div class="panel-heading text-center"><h4 >Search Details</h4></div>
	    								<div class="panel-body">
	    								
	    									<form action="#" role="form-inline">
								    			<button type="button" id="UpSaveButton" class="saveBtn btn btn-success text-center" data-tooltip="Save">
													<span >Save</span>
												</button> 
												
												<div class="panel-heading text-center"><h4 ><b>Total Pending Amount : </b> <a href="#" id="pendingAmount"></a></h4></div>
								    		</form>
	    									<br/>
	    								
											<table class="table table-bordered" id="reportTable" style="width: 100%">
												<thead>
								    				<tr class="text-info text-center" style="height: 35px;">
								    					<th>Sr No</th>
								    					<th class="hide"></th>
								    					<th>Number</th>
								    					<th>Hire Date</th>
								    					<th>Payment Mode</th>
								    					<th>Remark</th>
								    					<th>Received As</th>
								    					<th>Lorry Hire</th>
								    					<th>Advance</th>
								    					<th>OtherCharges</th>
								    					<th>Paid Amt</th>
								    					<th>Balance</th>
								    					<th>Receive Amt</th>
								    					
								    				</tr>
								    			</thead>
								    			<tbody id="billDetails">
								    				
								    			</tbody>
								    			<tfoot id="grandTotalRow">
								    			
								    			</tfoot>
											</table>
											
											<form action="#" role="form-inline">
								    			<button type="button" id="UpSaveButton" class="saveBtn btn btn-success text-center" data-tooltip="Save">
													<span >Save</span>
												</button> 
								    		</form>
									</div>
								</div>	
							</fieldset>
						</div>
				</div>
	</section>
	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/lorryhire/VendorLorryHirePayment.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/commonUtility.js" />"></script>
	
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("dd-mm-yyyy", {
				placeholder : "dd-mm-yyyy"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>