<%@ include file="taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
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
					<a href="<c:url value="/vehicleModel.in"/>">Vehicle Model</a>
				</div>
			
			</div>
			<div class="box-body">
				<div class="pull-left">
					<img src="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/chassis.png"
					class="img-rounded" alt="Tyre Photo"  width="50%" />
				
				</div>
				<div class="pull-left1">
					<h3 class="secondary-header-title">
						Vehicle Model: <span id="vehicleModel"></span>
					</h3>
			</div>
		</div>
	</section>

	<section class="content-body">
	<input type="hidden" id="vehicleModelId" value="${vehicleModelId}">
	<input type="hidden" id="vehicleModelTyreLayoutId" >
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
		<div class="row">
			<div class="col-md-7 col-sm-6 col-xs-12">
				<div class="box">
					<div class="box-body" style="padding-bottom: 90px;">
						<table id="layoutTable" style="border-spacing: 0; border-width: 0; padding: 0; border-width: 0;">
							
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
												<tr class="row">
													<th class="key">Tyre Modal :</th>
													<td class="value" >
														<span id="tyreModelName"></span>
													</td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Model Type  :</th>
													<td class="value"><span id="tyreModelType"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Model Size  :</th>
													<td class="value"><span id="tyreModelSize"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Gauge Measurement Line :</th>
													<td class="value"><span id="gauageMeasurementLine"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Gauge :</th>
													<td class="value"><span id="tyreGuage"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre Tube Type :</th>
													<td class="value"><span id="tyreTubeTypeName"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Ply :</th>
													<td class="value"><span id="ply"></span></td>
												</tr>
												<tr class="row">
													<th class="key">PSI :</th>
													<td class="value"><span id="psi"></span></td>
												</tr>
										</table>
									</div>
								</fieldset>
								
							
								<fieldset class="form-actions">
									<div class="pull-left">
										<button type="submit" name="commit" class="btn btn-success" onclick="deleteVehicleModelTyreLayout();"
											data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
											<span id="submit">Delete Layout</span>
										</button>
										<a class=" btn btn-info"
											href="vehicleModel"><span
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

	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/ShowVehicleModelTyreLayout.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>