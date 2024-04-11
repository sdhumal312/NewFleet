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
   padding: 5px 0 0 8px;
}

.showError{
box-shadow: 0px 1px 5px red;
}


</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"> <spring:message code="label.master.home" /> </a> / 
					<a href="<c:url value="/requisition"/>"> Requisition</a> / <span id="NewVehicle">Create Requisition</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="requisition">
						<span id="AddVehicle"> Cancel</span>
					</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('CREATE_REQUISITION')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('CREATE_REQUISITION')">
			<div class="row">
				<div class="col-sm-8 col-md-12">
					<input type="hidden" id="companyId" value="${companyId}">
					<input type="hidden" id="userId" value="${userId}">
					<input type="hidden" id="showManufacturerAndSize" value="${configuration.showManufacturerAndSize}">
					<input type="hidden" id="showUpholstery" value="${configuration.showUpholstery}">
					<input type="hidden" id="showPartUOM" value="${configuration.showPartUOM}">
					<div class="tab-content">
							<div class="box ">
								<div class="box-body">
									<label class="has-float-label "> <span
										style="color: #2e74e6; font-size: 24px;">Create
											Requisition</span>
									</label> <br>
									<div class="row">
										<div class="col col-sm-1 col-md-3">
											<label class="has-float-label"> <select id="location"
												style="line-height: 30px; font-size: 15px; height: 35px;"
												name="REQUITED_LOCATION_ID">
													<c:forEach items="${partLocationPermission}"
														var="partLocationPermission">
														<option value="${partLocationPermission.partLocationId}">
															<c:out value="${partLocationPermission.partLocationName}" />
														</option>
													</c:forEach>
											</select> <span style="color: #2e74e6; font-size: 16px;">Requisition
													Location <abbr title="required">*</abbr>
											</span>
											</label>
										</div>
										<div class="col col-sm-1 col-md-3">
											<label class="has-float-label"> <input type="hidden"
												id="assignToId" class="browser-default"
												style="line-height: 30px; font-size: 15px; height: 35px;">
												<span style="color: #2e74e6; font-size: 18px;">Assign
													To<abbr title="required">*</abbr> </span>
											</label>
										</div>
											<div class="col col-sm-12 col-md-3">
										  <label class="has-float-label">
										    <div class="input-group input-append date" id="minCurrentDate">
													<input type="text" class="form-control  browser-default custom-select noBackGround	invoiceDate" name="requireDate" readonly="readonly"
														id="requireDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> 
														<span  class="  input-group-addon add-on btn btn-sm"><em class="fa fa-calendar"></em></span>
												</div>
										    <span style="color: #2e74e6;font-size: 18px;">Required On <abbr title="required">*</abbr></span>
										  </label>
									</div>
										<div class="col col-sm-1 col-md-3">
											<label class="has-float-label"> <input type="text"
												class="form-control browser-default custom-select noBackGround"
												id="refNumber"> <span
												style="color: #2e74e6; font-size: 18px;">Reference
													Number </span>
											</label>
										</div>
									</div>
									<div class="row">
										<div class="col col-sm-1 col-md-3">
											<label class="has-float-label"> <input type="text"
												class="form-control browser-default custom-select noBackGround"
												name="remark" id="remark"> <span
												style="color: #2e74e6; font-size: 18px;">Remark</span>
											</label>
										</div>
									</div>
								</div>
								<br>
							</div>
							<div>
							<div class="box" >
								<div class="box-body">
									 <div class="row">
									 	<div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										    <select id="requisitionType"
													style="line-height: 30px;font-size: 15px;height: 35px;" name="requisitionType" onchange="hideDiv(this.id)" >
														<option value="0">Select Type</option>
														<option value="1">Part</option>
														<option value="2">Tyre</option>
														<option value="3">Battery</option>
														<c:if test="${configuration.showUpholstery}">
														<option value="4">Upholstery</option>
														</c:if>
														<option value="5">Urea</option>
												</select>
										    
										    <span style="color: #2e74e6;font-size: 16px;">Requisition type <abbr title="required">*</abbr></span>
										  </label>
									</div>
									<div id="partDiv" class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										 <input type="hidden" name="partId" id="partId" class="browser-default partId" onchange="setUOM(this.id);validateDublicate(this)" style="line-height: 30px;font-size: 15px;height: 35px;">
										    <span style="color: #2e74e6;font-size: 18px;" >Part Name <abbr title="required">*</abbr></span>
										  </label>
										</div>
										<c:if test="${configuration.showPartUOM}">
											<div id="partUOMDiv" class="col col-sm-1 col-md-3">
												<label class="has-float-label"> <input type="text"
													class="form-control browser-default noBackGround"
													name="partUom" id="partUom" readonly="readonly"> <span
													style="color: #2e74e6; font-size: 18px;">UOM</span>
												</label>
											</div>
										</c:if>
										<div id="tyreModelDiv" class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										 <select style="line-height: 30px;font-size: 15px;height: 35px;" id="tyremodel"
																			name="TYRE_MODEL_ID" required="required"  onchange="validateSelectDublicate(this);">
																		</select>
										    <span style="color: #2e74e6;font-size: 18px;" >Tyre Model </span>
										  </label>
										</div>
<!-- 									<div id="tyreManuDiv" class="col col-sm-1 col-md-3"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 										 <input type="hidden" id="manufacurer" style="line-height: 30px;font-size: 15px;height: 35px;" name="manufacurer" style="width: 100%;" -->
<!-- 																			required="required" -->
<!-- 																			placeholder="Please Enter 2 or more Tyre Manufacturer Name" /> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Tyre Manufacturer </span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
<!-- 									<div id="tyreSizeDiv" class="col col-sm-1 col-md-3"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 										 <input type="hidden" id="tyreSize" name="tyreSize" -->
<!-- 																			style="line-height: 30px;font-size: 15px;height: 35px;" required="required" -->
<!-- 																			placeholder="Please select Tyre Size" /> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Tyre Size </span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
<!-- 									<div id="batteryManuDiv" class="col col-sm-1 col-md-3"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 										 	<input type="hidden" id="batteryManufacturer" -->
<!-- 																name="batteryManufacturer" style="line-height: 30px;font-size: 15px;height: 35px;" -->
<!-- 																placeholder="Please Enter 2 or more Battery Manufacturer Name" /> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Battery Manufacturer </span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
<!-- 									<div id="batteryTypeDiv" class="col col-sm-1 col-md-3"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 										<select style="line-height: 30px;font-size: 15px;height: 35px;" id="batterryTypeId" -->
<!-- 															 name="batteryTypeId"></select> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Battery Model </span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
									<div id="batteryCapaDiv" class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										<input type="hidden" id="batteryCapacityId" name="batteryCapacityId"
																style="line-height: 30px;font-size: 15px;height: 35px;"
																placeholder="Please select Battery Capacity" onchange="validateDublicate(this);"  />
										    <span style="color: #2e74e6;font-size: 18px;" >Battery Capacity</span>
										  </label>
										</div>
									<div id="upholsteryDiv"class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										<input type="hidden" id="clothTypes"
															name="clothTypes" style="line-height: 30px;font-size: 15px;height: 35px;"
															placeholder="Please Enter 2 or more Cloth Types Name" onchange="validateDublicate(this);" />
										    <span style="color: #2e74e6;font-size: 18px;" >Upholstery : </span>
										  </label>
										</div>
<!-- 									<div id="ureaDiv" class="col col-sm-1 col-md-3"> -->
<!-- 										 <label class="has-float-label"> -->
<!-- 											<input type="hidden" id="ureaManufacturer" -->
<!-- 															name="ureaManufacturer" style="line-height: 30px;font-size: 15px;height: 35px;" -->
<!-- 															placeholder="Please Enter 2 or more Manufacturer Name" /> -->
<!-- 										    <span style="color: #2e74e6;font-size: 18px;" >Urea Manufacturer : </span> -->
<!-- 										  </label> -->
<!-- 										</div> -->
									 <div class="col col-sm-1 col-md-3">
										 <label class="has-float-label">
										    <input type="text" class="form-control browser-default noBackGround" name="Qty" id="Qty"
										     onkeypress="return isNumberKeyWithDecimal(event,this.id);"
										     >
										    <span style="color: #2e74e6;font-size: 18px;" >Qty<abbr title="required">*</abbr></span>
										  </label>
										</div>
									 
									 </div>
								</div>
									 </div>

								<div class="partDiv">
									<div class="addMorePartDiv"></div>
								</div>

							</div>
							<button type="button"  class="btn btn-info addMorePartButton" style="background:lightseagreen;"><span class="fa fa-plus"></span></button>
							<br>
							<br>
						<div class="row" >
							<button type="submit" id="submit"  class="btn btn-success" >Save </button> &nbsp;&nbsp;
							<a class=" btn btn-info" href="requisition">
							<span id="Cancel">Cancel</span></a>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>  
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/requisitionCommon.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/requisition/createRequisition.js"></script>
</div>