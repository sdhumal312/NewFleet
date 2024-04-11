<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<style>
.steel{padding-left: 30px;}
.noDual{padding-left: 4%;}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					 <a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> /
					<a>Show Vehicle Layout</a>
				</div>
				<div class="pull-right">
			<%-- 	<button id="createLayout" class="hide" type="button" href="<c:url value="/vehicleModelTyreLayout.in?id=${vehicleModelId}"/>"> Create Layout</button> --%>
				<a  id="createLayout" class="btn btn-warning btn-md hide" href="vehicleModelTyreLayout.in?id=${vehicleModelId}" target="_blank" >Create Layout</a>
				</div>
			
			</div>
			<div class="box-body">
				<div class="pull-left">
					<img src="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/chassis.png"
					class="img-rounded" alt="Tyre Photo"  width="50%" />
				
				</div>
				<div class="pull-left1">
					<h3 class="secondary-header-title">
						Vehicle: <c:out value="${vehicleRegistration}"></c:out>
					</h3>
			</div>
		</div>
	</section>

	<section class="content-body">
	<input type="hidden" id="vehicleModelId" value="${vehicleModelId}">
	<input type="hidden" id="vid" value="${vid}">
	<input type="hidden" id="vehicleModelTyreLayoutId" >
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
	<input type="hidden" id="LP_ID" >
	<input type="hidden" id="tyreId" >
	<input type="hidden" id="tyrePositionId" >
	<input type="hidden" id="tyreNumber1" >
	<input type="hidden" id="showDismountOdometer" >
	<input type="hidden" id="vehicleRegistration" value="${vehicleRegistration}" >
		<div class="row">
			<div class="col-md-7 col-sm-6 col-xs-12">
				<div class="box">
					<div class="box-body" style="padding-bottom: 90px;">
						<table id="layoutTable">
							
						</table>
					</div>
				</div>
			</div>
			
			<div class="col-md-5 col-sm-4 col-xs-12">
				<div class="row">
					<div class="panel panel-default" style="width:100%">
						<div class="panel-body">
							<div class="form-horizontal ">
								<div class="row" style="padding-bottom: 6px;">
									<label class="L-size control-label"
										style="font-size: 15px; padding-top: 2px;">Axles</label>
									<div class="I-size">
										<select class="form-control select2" style="width: 100%;" readonly="readonly">
											<option id="numberOfAxle"></option>
										</select>
									</div>
								</div>
								<br>
								<fieldset id="tyreLayoutDetails">
									<legend id="layoutClassification">Tyre Layout Details :</legend>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Front Tyre Model :</th>
													<td class="value" > <span id="frontTyreModel"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">Front Tyre Size :</th>
													<td class="value" ><span id="frontTyreSize"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">Rear Tyre Model :</th>
													<td class="value" > <span id="rearTyreModel"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">Rear Tyre Size :</th>
													<td class="value" > <span id="rearTyreSize"></span> </td>
												</tr>
												<tr class="row">
													<th class="key">Spare Tyre Model :</th>
													<td class="value"><span id="spareTyreModel"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Spare Tyre Size :</th>
													<td class="value"><span id="spareTyreSize"></span></td>
												</tr>
												
										</table>
									</div>
								</fieldset>
								<fieldset style="display:none" id="tyrePositionDetails">
									<legend id="tyreClassification">Tyre Position Details</legend>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Tyre Position :</th>
													<td class="value" >
														<span id="tyrePosition"></span>
													</td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Number :</th>
													<td class="value" >
														<a  id="tyreNumber" target="_blank"></a>
													</td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Assign Date :</th>
													<td class="value" >
														<span id="assignDate"></span>
													</td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Modal :</th>
													<td class="value" >
														<span id="tyreModelName"></span>
													</td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Model Type  :</th>
													<td class="value"><span id="tyreModelType"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Model Size  :</th>
													<td class="value"><span id="tyreModelSize"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Gauge Measurement Line :</th>
													<td class="value"><span id="gauageMeasurementLine"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Gauge :</th>
													<td class="value"><span id="tyreGuage"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Tyre Tube Type :</th>
													<td class="value"><span id="tyreTubeTypeName"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">Ply :</th>
													<td class="value"><span id="ply"></span></td>
												</tr>
												<tr class="row mountInfo">
													<th class="key">PSI :</th>
													<td class="value"><span id="psi"></span></td>
												</tr>
											</tbody>
										</table>
									</div>
								</fieldset>
								
							
								<fieldset class="form-actions">
									<div class="pull-left">
										<button type="submit" id="dismountId" name="commit" class="btn btn-success" onclick="dismountTyre();" style="display:none"
											data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
											<span id="submit">Dismount</span>
										</button>
										<button type="submit"  id="mountId" name="commit" class="btn btn-success"  style="display:none"
											onclick="mountTyre();" data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
											<span id="submit">Mount</span>
										</button>
										<a class=" btn btn-info" href="showVehicle.in?vid=${vid}" ><span
											id="Cancel">Cancel</span></a>
									</div>
								</fieldset>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<div class="modal" id="dismountModal" tabindex="-2">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Dismount tyre</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <div class="input-group input-append date" id="startDate">
									<input type="text" class="form-control  browser-default custom-select noBackGround	" name="invoiceDate" readonly="readonly"
										id="dismountDate" required="required"  onchange="getDateWiseOdometer();"
										data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
										<button type="submit"  class=" input-group-addon add-on btn btn-sm"><i class="fa fa-calendar"></i></button>
								</div>
						    <span style="color: #2e74e6;font-size: 18px;">Dismount Date  </span>
						  </label>
					</div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="dismountOdometer"   onkeypress="return isNumberKeyWithDecimal(event,this.id);" >
						    <span style="color: #2e74e6;font-size: 18px;">Dismount Odometer </span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <select id="oldTyreMoveId" name="oldTyreMoveName"class="browser-default custom-select">
								<option value="0">Please Select Old Tyre Move To</option>
								<option value="1">Remould</option>
								<option value="2">Repair</option>
								<option value="3">Blast</option>
								<option value="4">Scrap</option>
								<option value="5">WorkShop</option>
							</select> 
						    <span style="color: #2e74e6;font-size: 18px;">Old Tyre Move To </span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="tyreGuageVal" onkeypress="return isNumberKeyWithDecimal(event,this.id);"  >
						    <span style="color: #2e74e6;font-size: 18px;">Guage </span>
						  </label>
					  </div>
					<div class="col col-sm-12 col-md-5">
						  <label class="has-float-label">
						    <input type="text" class="form-control browser-default custom-select noBackGround" id="remark"  >
						    <span style="color: #2e74e6;font-size: 18px;">Remark </span>
						  </label>
					  </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="removeTyre();">Dismount</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/ShowVehicleTyreAssignedDetails.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>