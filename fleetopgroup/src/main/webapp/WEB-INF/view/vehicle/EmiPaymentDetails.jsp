<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">

<div class="content-wrapper" onload="getVehicleEmiDetails();">
	<section class="content-header">
		<div class="box">
		
			<input type="hidden" id="vehicleId" value="${vehicle.vid}"/>
			<input type="hidden" id="vehEmiDetailsId" value="${vehicleEmiDetailsId}"/>
			
				<div class="box-header">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> 
							/ <a href="<c:url value="/vehicle/1/1"/>">Vehicle</a> 
							/ <a href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							       value="${vehicle.vehicle_registration}" /></a>
							/ <a href="<c:url value="/VehicleEmiDetails.in?Id=${vehicle.vid}"/>">Vehicle EMI Details</a>
						    / <span>Vehicle EMI Payment Details</span>
					</div>
					<div class="pull-right">
						<a class="btn btn-link btn-sm"
							href="showVehicle.in?vid=${vehicle.vid}">Cancel
						</a>
					</div>
				</div>
		</div>
	</section>
	
	<section class="content">
				<div>
					<div class="form-horizontal ">
						
							<div class="box" id="top-border-boxshadow">
								<div class="panel-heading text-center"><h4>Vehicle EMI Paid Details</h4></div>
								
									<div class="panel-body">
										
										<div class="table-responsive">
											<table id="VendorPaymentTable2" class="table table-hover table-bordered">
											</table>
										</div>
										
									</div>
									
							</div>
							
							
					</div> 
					
						<br>
					
					<div class="panel panel-success "  id="middle-border-boxshadow">
						<div class="panel-heading text-center">
							<h4 >Pending Vehicle EMI Payment Details
							
							<a class="btn btn-info pull-right"  
							id ="emiPayment" onclick="preEmiSettle();" > <span
							class="fa fa-rupee"> Loan Settlement </span>
							</a>
							
							</h4>
							
						</div>
  								<div class="panel-body">
  									
									<table class="table table-bordered" id="reportTable" style="width: 100%">
										<thead>
						    				<tr class="text-info text-center" style="height: 35px;">
						    					<th>Sr No</th>
						    					<th>Account / Bank Name</th>
						    					<th>Loan Amount</th>
						    					<th>EMI Date</th>
						    					<th>Monthly EMI Amount</th>
						    					<th>Payment Mode</th>
						    					<th>Remark</th>
						    					<th>Paid Amount</th>
						    					<th class="hide"></th>
						    					<th class="hide"></th>
						    					
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
				
				</div>
	</section>
	
	<section>
	<div class="modal fade" id="preEmiSettlement" role="dialog">
			<div class="modal-dialog modal-md" style="width:1250px;">
				<div class="modal-content">
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<!-- <h4 class="modal-title" id="TripExpense">Pre EMI Settlement</h4> -->
							</div>
							<div class="modal-body">
								
								<div class="panel panel-success "  id="settle-border-boxshadow">
									<div class="panel-heading text-center">
										<h4 >Pre EMI Settlement
										</h4>
									</div>
								</div>	

								<table class="table table-bordered" id="settleTable" style="width: 100%">
										<thead>
						    				<tr class="text-info text-center" style="height: 35px;">
						    					<th>Sr No</th>
						    					<th>Account / Bank Name</th>
						    					<th>Loan Amount</th>
						    					<th>DownPayment Amount</th>
						    					<th>Monthly EMI Amount</th>
						    					<th>Payment Mode</th>
						    					<th>Remark</th>
						    					<th>Paid Amount</th>
						    					<th class="hide"></th>
						    					<th class="hide"></th>
						    					
						    				</tr>
						    			</thead>
						    			<tbody id="settleDetails">
						    			</tbody>
						    			
									</table>
								
							</div>
							
							<div class="modal-footer">
								<button type="submit" id="saveSettlement" class="btn btn-primary">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
					  </div>
				</div>
			</div>
		</div>
	</section>	
	
	
	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
		<%-- <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
		 <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script> --%>
		<script>
			$(function() {
				$('#reservation').daterangepicker();
				$("#to").select2({
					placeholder : "Please Select Type"
				});

			});
		</script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/EmiPaymentDetails.js" />"></script>
		<script type="text/javascript" 
			src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>		
		
</div>