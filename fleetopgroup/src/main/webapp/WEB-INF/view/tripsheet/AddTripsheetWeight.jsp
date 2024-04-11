<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message
										code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Show
									TripSheet</a> / <small>Add TripSheet Weight</small>
							</div>
						</div>
		
					    <input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="vid" value="${TripSheet.vid}">
						<input type="hidden" name="TripSheetID" id="tripSheetId" value="${TripSheet.tripSheetID}">
						
							<div class="row">
								<div class="row">
									<div class="pull-left">
										<h4>Trip Number : TS- ${TripSheet.tripSheetNumber}</h4>
									</div>
									
									<div class="pull-right">
										<h5>Created Date : ${TripSheet.created}</h5>
									</div>
									
								</div>
								<div class="row">
									<h4 align="center">
										<a href="showVehicle.in?vid=${TripSheet.vid}"
											data-toggle="tip" data-original-title="Click Vehicle Info">
										 	 <c:out value="${TripSheet.vehicle_registration}" />
										</a>
									</h4>
								</div>	
								<div class="col-md-3"></div>
							</div>
							
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li>Date of Journey : <a data-toggle="tip"
										data-original-title="Trip Open Date"> <c:out
												value="${TripSheet.tripOpenDate}  TO" /></a> <a
										data-toggle="tip" data-original-title="Trip Close Date"> <c:out
												value="  ${TripSheet.closetripDate}" /></a></li>
									<li>Group Service : <a data-toggle="tip"
										data-original-title="Group Service"><c:out
												value="${TripSheet.vehicle_Group}" /></a></li>
									<li>Booking No : <a data-toggle="tip"
										data-original-title="Booking No"> <c:out
												value="${TripSheet.tripBookref}" /></a></li>
									<li>Driver 1 : <a data-toggle="tip"
										data-original-title="Driver 1"> <c:out
												value="${TripSheet.tripFristDriverName} ${TripSheet.tripFristDriverLastName} - ${TripSheet.tripFristDriverFatherName}" /></a></li>
									<li>Driver 2 : <a data-toggle="tip"
										data-original-title="Driver 2"><c:out
												value="${TripSheet.tripSecDriverName} ${TripSheet.tripSecDriverLastName} - ${TripSheet.tripSecDriverFatherName}" /></a></li>
									<li>Cleaner : <a data-toggle="tip"
										data-original-title="Cleaner"><c:out
												value="${TripSheet.tripCleanerName}" /></a></li>
									<li>Opening KM : <a data-toggle="tip"
										data-original-title="Opening KM"><c:out
												value="${TripSheet.tripOpeningKM}" /></a></li>
									<li>Closing KM : <a data-toggle="tip"
										data-original-title="closing KM"> <c:out
												value="${TripSheet.tripClosingKM}" /></a></li>
								</ul>
							</div>
							<br><br><br><br>
							
							<table class="table table-bordered table-striped" id="tripsheetWeight" style="display:none">
								  <thead class="thead-dark">
								    <tr>
								      <th scope="col">No</th>
								      <th scope="col">RouteName</th>
								      <th scope="col">Actual Weight</th>
								      <th scope="col">Scale Weight</th>
								      <th scope="col">Weight Difference</th>
								      <th class="col">Actions</th>
								    </tr>
								  </thead>
								  <tbody id="tripweightbody">
								    	
								  </tbody>
							</table>
					
					
									
								<div class="form-horizontal">
									<fieldset>
										<div class="row1">
											<div class="col-md-4">
												<label class="control-label">Route Name</label>
											</div>
											<div class="col-md-2">
												<label class="control-label">Scale Weight</label>
											</div>
											<div class="col-md-2">
												<label class="control-label">Actual Weight</label>
											</div>
										</div>
										<div class="row1">
											<div class="col-md-4">
												<input type="text" class="form-text" name="routename" id="routename"
													placeholder="Route Name" >
											</div>
											<div class="col-md-2">
												<input type="text" class="form-text" name="scaleWeight" id="scaleWeight"
												     value="0" >
											</div>
											<div class="col-md-2">
												<input type="text" class="form-text" name="actualWeight" id="actualWeight"
												    value="0" >
											</div> 
										</div>
									</fieldset>
									<div class="box-footer h-padded">
										<fieldset class="form-actions">
											<input class="btn btn-success" data-disable-with="Saving..." 
												name="commit" type="button" value="Save Weight" id="SaveWeight"> <a
												class="btn btn-link"
												href="<c:url value="/showTripSheet?tripSheetID=${TripSheet.tripSheetID}"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
						</div>
					</div>
				</div>
			</div>
	</section>
	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripSheetWeightAdd.js" />"></script>
</div>
