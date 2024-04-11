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
			</div>
		</div>
	</section>

	<section class="content-body">
	<input type="hidden" id="vehicleModelId" value="${vehicleModelId}">
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
										<select class="form-control select2"
											onchange="createLayout();" style="width: 100%;" id="axle">
											<option value="0">Axle 0</option>
											<option value="1">Axle 1</option>
											<option value="2">Axle 2</option>
											<option value="3">Axle 3</option>
											<option value="4">Axle 4</option>
											<option value="5">Axle 5</option>
										</select>
									</div>
								</div>
								<br>
								<fieldset>
									<legend id="Classification">Front Tyres</legend>
									<div class="row1">
										<div class="col-md-6 col-sm-4 col-xs-6">
											<label class=" control-label"><span>Front Tyre Model:</span><abbr title="required">*</abbr></label>
										</div>
										<div class="col-md-5 col-sm-4 col-xs-6">
											<div>
												<input type="hidden" id="frontTryeModelId"  class="select" onchange="setAllModel();" style="width: 100%;"/>
											</div>
											<!-- <div>
												<input type="checkbox" id="allPosition" onclick="setAllModel();"><span>For All Position</span>
											</div> -->
										</div>

									</div>
								</fieldset>
								<fieldset>
									<legend id="Classification">Rear Tyres</legend>
									<div class="row1">
										<div class="col-md-6 col-sm-4 col-xs-6">
											<label class=" control-label"><span>Rear Tyre Model:</span><abbr title="required">*</abbr></label> 
										</div>
										<div class="col-md-5 col-sm-4 col-xs-6">
											<input type="hidden" id="rearTyreModelId" class="select" style="width: 100%;"/>
										</div>
									</div>
								</fieldset>
								<fieldset id="spareTyreModel" style="display:none;">
									<legend id="Classification">Spare Tyres</legend>
									<div class="row1">
										<div class="col-md-6 col-sm-4 col-xs-6">
											<label class=" control-label"><span>Spare Tyre Model:</span><abbr title="required">*</abbr></label> 
										</div>
										<div class="col-md-5 col-sm-4 col-xs-6">
											<input type="hidden" id="spareTyreModelId" class="select" style="width: 100%;"/>
										</div>
									</div>
								</fieldset>
								<fieldset class="form-actions">
									<div class="pull-left">
										<button type="submit" name="commit" class="btn btn-success" onclick="saveVehicleModelTyreLayout();"
											data-loading-text="<i class='fa fa-spinner fa-spin'></i> Processing Vehicle..">
											<span id="submit">Create Layout</span>
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

	<%-- <script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/TyreLayoutCode.js" />"></script> --%>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/VehicleModelTyreLayout.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
</div>