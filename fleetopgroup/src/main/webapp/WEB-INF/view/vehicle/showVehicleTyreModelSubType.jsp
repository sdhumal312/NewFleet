<%@ include file="taglib.jsp"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>">
						<spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/addModelSubType.in"/>">Vehicle Tyre Model SubType</a> /
				</div>
				
			</div>
			<div class="box-body">
				<div class="pull-left">
					<a
						href="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/TyreWheel.png"
						class="zoom" data-title="Tyre Photo" data-footer=""
						data-type="image" data-toggle="lightbox"> <img
						src="${pageContext.request.contextPath}/resources/QhyvOb0m3EjE7A4/images/TyreWheel.png"
						class="img-rounded" alt="Tyre Photo" width="100" height="100" />
					</a>
				</div>
				<div class="pull-left1">
					<h3 class="secondary-header-title">
							<span id="tyreModelName1" class="label label-pill label-success"></span>
					</h3>
				
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content-body">
			<div class="row">
			<input type="hidden" id="TYRE_MST_ID" value="${TYRE_MST_ID}">
			<input type="hidden" id="companyId" value="${companyId}">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-6 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Tyre Model Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Tyre Modal :</th>
													<td class="value" >
														<span id="tyreModelName"></span>
													</td>
												</tr>
												<tr class="row">
													<th class="key">Manufacturer Name :</th>
													<td class="value"> <span id="tyreManufacturerName"></span>
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
												<tr class="row">
													<th class="key">Tyre Warranty  :</th>
													<td class="value"><span id="tyreWarranty"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Tyre warranty terms  :</th>
													<td class="value"><span id="tyreWarrantyTerms"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Remarks  :</th>
													<td class="value"><span id="remark"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Cost Per KM (Approx) :</th>
													<td class="value"><span id="costPerKm"></span></td>
												</tr>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<%@include file="masterSideMenu.jsp"%>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | 
				<small class="text-muted"><b>Created date: </b> <span id="createdDate"></span></small> | 
				<small class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy"></span></small> | 
				<small class="text-muted"><b>Last updated date:</b> <span id="lastUpdatedDate"></span></small>
			</div>
	</section>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/showVehicleTyreModelSubType.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
</div>