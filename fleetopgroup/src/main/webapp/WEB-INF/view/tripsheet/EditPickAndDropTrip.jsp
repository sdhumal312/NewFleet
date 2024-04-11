<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	

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
						
					<sec:authorize access="hasAuthority('CREATE_PICK_OR_DROP')">
						<div class="pull-right">
							<a class="btn btn-success btn-sm" href="createPickAndDropTrip.in"> <span
								class="fa fa-plus"></span> Create TripSheet
							</a>
						</div>
					</sec:authorize>
					
				</div>
			</div>
		</div>
	</section>
	
	
	<!-- Main content -->
	<section class="content">
		<div class="panel box box-primary">
		   <%-- <form id="formDriver" action="<c:url value="/dispatchPickAndDropTrip.in"/>"
			method="post" name="formDriver" role="form" class="form-horizontal"> --%>     <!-- onsubmit="return validateDriverSave()" -->
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">
				
					<input type="hidden" id="editPickAndDropId" value="${editPickAndDropId}">
					<input type="hidden" id="noOfDaysForBackDate" value="${configuration.numberOfDaysForBackDate}">
					<input type="hidden" id="backDateString" value="${minBackDate}">
				
					<div class="row1">
						<label class="L-size control-label">Vehicle Number : </label>
						<div class="I-size">
							<input type="hidden" id="vid" name="vid" readonly="readonly"
								style="width: 100%;" placeholder="All" />
						</div>
					</div>

					<div class="row1" id="grpwoEndDate" class="form-group">
						<label class="L-size control-label" for="fuelDate">
							Date <abbr title="required">*</abbr> </label>
						<div class="col-md-3">
							<div class="input-group input-append date" id="StartDate1">
								<input type="text" class="form-text" name="journeyDate" 
									id="journeyDate" data-inputmask="'alias': 'dd-mm-yyyy'" readonly="readonly"
									data-mask="" /> <span class="input-group-addon add-on">
									<span class="fa fa-calendar"></span>
								</span>
							</div>
						</div>
										
						<div class="L-size">
							<label class="L-size control-label" for="fuelDate">Time
								<abbr title="required">*</abbr>
							</label>
							<div class="input-group clockpicker">
								<input type="text" class="form-text" readonly="readonly"
									name="journeyTime" id="journeyTime" required="required"> <span
									class="input-group-addon"> <span
									class="fa fa-clock-o" aria-hidden="true"></span>
								</span>
							</div>
						</div>
					</div>
					
					<div class="row1">
						<label class="L-size control-label">Driver : </label>
						<div class="I-size">
							<input type="hidden" id="tripFristDriverID" name="tripFristDriverID"
								style="width: 100%;" placeholder="All" />
						</div>
					</div>
					
					<div class="row1 hide oldParty">
						<label class="L-size control-label">Party Name : </label>
						<div class="I-size">
							<input type="hidden" id="vendorId" name="vendorId"
								style="width: 100%;" placeholder="All" />
						</div>
					</div>
					
					<div class="row1 hide newParty" class="form-group">
						<label class="L-size control-label">New Party Name : 
						 <abbr title="required">*</abbr></label>
						<div class="I-size">
							<input type="text" id="newParty" name="newParty" class="form-text"
								style="width: 100%;" placeholder="Enter New Party Name" />
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<label class="L-size control-label" for="fuel_liters">Rate :
						<abbr title="required">*</abbr></label>
						<div class="I-size">
							<input type="text" maxlength="10" onkeypress="return isNumberKeyWithDecimal(event,this.id); " class="form-text" id="rate" name="rate" onchange="calAmnt()"> 
						</div>
					</div>
					
					<div class="row1">
						<label class="L-size control-label">PickUp Or Drop : 
						 <abbr title="required">*</abbr></label>
						<div class="btn-group I-size" id="status" data-toggle="buttons">
							<label class="btn btn-default btn-on btn-sm pk">
								<input type="radio" value="1" name="pickOrDropStatus"
								id="pickStatus" onchange="pickOrDrop(this);">PickUp
							</label> 
							<label class="btn btn-default btn-off btn-sm dp"> 
								<input type="radio" value="2" name="pickOrDropStatus"
								id="dropStatus" onchange="pickOrDrop(this);">Drop
							</label>
						</div>
					</div>
					
					<div class="row1 hide pick">
						<label class="L-size control-label">PickUp Point : </label>
						<div class="I-size">
							<input type="hidden" id="pickId" name="pickId"
								style="width: 100%;" placeholder="All" />
						</div>
					</div>
					
					<div class="row1 hide drop">
						<label class="L-size control-label">Drop Point : </label>
						<div class="I-size">
							<input type="hidden" id="dropId" name="dropId"
								style="width: 100%;" placeholder="All" />
						</div>
					</div>
					
					<div class="row1 hide newRoute" class="form-group">
						<label class="L-size control-label">New Pick/Drop Location : 
						 <abbr title="required">*</abbr></label>
						<div class="I-size">
							<input type="text" id="newRouteName" name="newRouteName" class="form-text"
								style="width: 100%;" placeholder="Enter New Pick/Drop Location" />
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<label class="L-size control-label" for="fuel_liters">Total KM
							:<abbr title="required">*</abbr>
						</label>
						<div class="I-size">
							<input type="text" maxlength="10" onkeypress="return isNumberKeyWithDecimal(event,this.id);" class="form-text" id="tripUsageKM" name="tripUsageKM" onchange="calAmnt();"> 
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<label class="L-size control-label" for="fuel_liters"> Total Amount :
						</label>
						<div class="I-size">
							<input class="form-text" type="number" id="amount" name="amount" readonly="readonly"> 
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<label class="L-size control-label" for="fuel_liters">Advance
							:<abbr title="required">*</abbr>
						</label>
						<div class="I-size">
							<input class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id);" id="tripTotalAdvance" name="tripTotalAdvance"
								type="text" maxlength="10" min="0"> 
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<label class="L-size control-label" for="fuel_liters">Comment
							:<abbr title="required">*</abbr>
						</label>
						<div class="I-size">
							<textarea class="form-text" id="remark"
								name="remark" rows="3" maxlength="240">	
							</textarea> 
						</div>
					</div>
					
					<div class="row1" id="grpfuelLiter" class="form-group">
						<div class="col-sm-offset-4 I-size">
							<button type="submit" class="btn btn-primary" onclick="dispatchPickAndDropTrip();">
								<span>Update</span>
							</button>	
							<a class="btn btn-default" href="<c:url value="/showDispatchedPickAndDropTrip?dispatchPickAndDropId=${editPickAndDropId}"/>">Cancel</a>
						</div>
					</div>
					
				</div>
			</div>
			<!-- </form> -->
		</div>
		</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/EditPickAndDropTrip.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script type="text/javascript" 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
	
	<script type="text/javascript">
		
	var previousDate = $('#backDateString').val();
	var previousDateForBackDate =   previousDate.split("-")[0] + '-' +  previousDate.split("-")[1] + '-' +  previousDate.split("-")[2];
	
		$(function() {
			$("#journeyDate").datepicker({
			       defaultDate: new Date(),
			       autoclose: !0,
			       todayHighlight: !0,
			       format: "dd-mm-yyyy",
			       setDate: "0",
			       endDate: "currentDate",
				   startDate:previousDateForBackDate
			   })
		}); 	
	
		$(document).ready(function() {
			
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
		});
	</script>		
</div>