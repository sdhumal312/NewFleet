<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9 col-sm-9 col-xs-12">
				<div class="box">
					<div class="boxinside">
						<div class="box-header">
							<div class="pull-left">
								<a href="<c:url value="/open"/>"><spring:message 	code="label.master.home" /></a> / <a
									href="<c:url value="/newTripSheetEntries.in"/>">TripSheet</a> / <a
									href="<c:url value="/showTripSheet?tripSheetID=${tripSheetId}"/>">Show
									TripSheet</a> /Trip CheckPoint Inspection
							</div>
							<div class="pull-right"></div>
						</div>
						<input type="hidden" id="companyId" value="${companyId}">
						<input type="hidden" id="userId" value="${userId}">
						<input type="hidden" id="vid">
						<input type="hidden" id="routeId">
						<input type="hidden" id="tripsheetId" value="${tripSheetId}">
						<input type="hidden" id="checkPointParameterDocumnetConfig" value="${configuration.checkPointParameterDocumnet}">
						
						<div class="row">
							<div class="row">
								<div class="pull-left">
									<h4>Trip Number : TS-<span id="tripsheetNumber"></span></h4>
								</div>
								<div class="pull-right">
									<h5>Created Date : <span id="createDate"></span></h5>
								</div>
							</div>
							<div class="row">
								<h4 align="center">
									<a href="#" onclick="showVehicle()">
										<span id="vehicle">
									</a>
								</h4>
							</div>
							<div class="col-md-3"></div>
						</div>
						<div class="row">
							<h4 align="center"><span id="routeName"></span></h4>
						</div>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li>Date of Journey : 
									<a data-toggle="tip" data-original-title="Trip Open Date"> 
									<span id="tripOpenDate" ></span> TO <span id="tripCloseDate" ></span></a>	
								</li>
								<li>Group Service : 
									<a data-toggle="tip" data-original-title="Group Service">
									<span id="vehicleGroup" ></span></a>
								</li>
								<li>First Driver : 
									<a data-toggle="tip" data-original-title="Driver 1">
								 	<span id="firstDriver" ></span></a>
								 </li>
								<li>Second Driver : 
									<a data-toggle="tip" data-original-title="Driver 1">
								 	<span id="secondDriver" ></span></a>
								 </li>
								<li>Cleaner : 
									<a data-toggle="tip" data-original-title="Cleaner">
									<span id="cleaner" ></span></a>
								</li>
								<li>Opening KM : 
									<a data-toggle="tip" data-original-title="Opening KM">
									<span id="openingKM" ></span></a>
								</li>
								<li>Closing KM : 
									<a data-toggle="tip" data-original-title="Opening KM">
									<span id="closingKM" ></span></a>
								</li>
							</ul>
						</div>
						<br>
						<br/>
						<fieldset>
							<div class="row">
								<div class="">
									<table class="table table-bordered table-striped" >
										<thead>
											<tr class="breadcrumb">
												<th class="fit">No</th>
												<th class="fit ar">CheckPoint Name</th>
												<th class="fit ar">Actions</th>
											</tr>
										</thead>
										<tbody id="tripCheckPointTable">
													
										</tbody>
									</table>
								</div>
							</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="inspectCheckPointModal" role="dialog">
			<div class="modal-dialog" style="width:80%" >
				<form method="POST" enctype="multipart/form-data" id="fileUploadForm">
				<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<div class="pull-left">
								<h4>Inspect CheckPoint Parameter</h4>
							</div>
							<div style="text-align: center;">
								<h4>CheckPoint : <span id="tripCheckPointName"></span></h4>
							</div>
							
						</div>
						<div class="modal-body">
						<input type="hidden" id="tripCheckPointId">
							<div class="row">
								<div class="col-sm-8 col-md-1" style="width:9.33%"> <label>Parameter :</label> </div>
								<div class=" col-sm-8 col-md-3">
								<select style="width: 100%;" id="parameter" name="parameterName" placeholder="Please Select Parameter"></select>
								<!-- <input type="hidden" id="parameter" name="parameterName" style="width: 100%;" placeholder="Please Enter 2 or more Type Name" /> --> 
								
								</div>
								<div class="col-sm-8 col-md-3">
								<label>Pass </label>
								<input type="radio" value="true" class="testResult" name="result" id="pass" checked>
								&emsp;&emsp;
								<label>Fail </label>
								<input type="radio" value="false" name="result" id="fail">
								</div>
								<c:if test="${configuration.checkPointParameterDocumnet}">
									<div class="showDoc">
										<div class="col-sm-8 col-md-1" style="width:4.33%"> <label>Doc </label></div>
										<div class="col-sm-8 col-md-2"> <input type="file" name="input-file-preview" id="parameterDoc" /> </div>
									</div>
								</c:if>
								<c:if test="${!configuration.checkPointParameterDocumnet}">
									<input type="hidden" name="input-file-preview"  /> 
								</c:if>
							</div>
							<div class="input_fields_wrap">
								<button class="add_field_button btn btn-success" id="add_field_button" style="margin-top: 25px;">
									<i class="fa fa-plus"></i>
								</button>
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" id="inspectTripCheckPointParameter"
								class="btn btn-primary">Inspect
							</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="modal fade" id="InspectedTripCheckPointParameterModal" role="dialog">
		<div class="modal-dialog" style="width:80%" >
			<!-- Modal content-->
			<div class="modal-content">
			<input type="hidden" id="companyId" value="${companyId}">
				<input type="hidden" id="userId" value="${userId}">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<div class="pull-left">
								<h4>Inspect CheckPoint Parameter</h4>
							</div>
							<div style="text-align: center;">
								<h4>CheckPoint : <span id="tripCheckPointName1"></span></h4>
							</div>
					</div>
					<div class="modal-body">
						<fieldset>
						<div class="row box-body">
						<input type="hidden" id="tripCheckPointId1">
							<div class="table-responsive">
								<table  class="table table-hover table-bordered">
									<thead>
										<tr>
											<th><h5>Sr No</h5></th>
											<th><h5>CheckPoint Parameter</h5></th>
											<th><h5>Status</h5></th>
											<th><h5>Action</h5></th>
											<!-- <th><h5>Doc</h5></th> -->
										</tr>
									</thead>
									<tbody id="InspectedTripCheckPointParameterTable">
									</tbody>
								
								</table>
							</div>
						</div>
						<br>
						</fieldset>
						<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
		
	</section>
</div>	
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
<script type="text/javascript" src="<c:url value="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripCheckPointInspection.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
<script type="text/javascript">
	$(document).ready(function() {
		$(".select2").select2();
	});
</script>
