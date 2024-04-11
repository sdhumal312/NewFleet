<%@ include file="taglib.jsp"%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/FuelInventory.in"/>">Fuel Inventory</a> / 
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="FuelInventory.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
					<fieldset>
						<legend>Fuel Transfer Details</legend>
						<div class="box">
							<div class="box-body">
							   <input type="hidden" id="companyId" ">
								<div class="form-horizontal ">
			                       <div class="form-group column">
			                            <label class="col-md-4 control-label">To Petrol pump <abbr title="required">*</abbr></label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
				                               <span class="input-group-addon">
				                              	 <i class="glyphicon glyphicon-cog"></i>
				                               </span>
			                          		<input type="hidden" id="toPetrolPumpId" style="width: 100%;" />
			                               </div>
			                            </div>
			                         </div>
			                   		 <div class="form-group column">
			                            <label class="col-md-4 control-label">From Petrol pump <abbr title="required">*</abbr></label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
				                               <span class="input-group-addon">
				                              	 <i class="glyphicon glyphicon-cog"></i>
				                               </span>
			                          		<input type="hidden" id="fromPetrolPumpId" style="width: 100%;" onchange="getInvoice()"/>
			                               </div>
			                            </div>
			                         </div>
			                         
			                          <div id="selectInvoiceDiv">
			                          <div class="form-group column">
			                            <label class="col-md-4 control-label">Select Invoice <abbr title="required">*</abbr></label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
				                               <span class="input-group-addon">
				                              	 <i class="glyphicon glyphicon-cog"></i>
				                               </span>
			                          		<input type="hidden" id="invoiceList" style="width: 100%;" onchange="setQuantity()" />
			                               </div>
			                            </div>
			                            </div>
			                         </div>
			                          <div class="form-group column">
			                            <label class="col-md-4 control-label">Required Liters :</label>
			                            <div class="col-md-8 inputGroupContainer">
			                               <div class="input-group">
			                               <span class="input-group-addon">
			                              	 <i class="fa fa-bus"></i>
			                               </span>
			                              	<input type="number" class="form-control" id="quantity" 
			                              	onkeypress="return isNumberKeyWithDecimal(event,id);" style="width: 100%;"  placeholder="Please Enter Required Quantity" />
			                               </div>
			                            </div>
			                         </div>
								</div>
							</div>
							<br><br>
							<div class="row">
								<label class="L-size control-label">Transfer Remark :</label>
								<div class="I-size">
									<textarea class="form-text" id="remark" style="width: 100%;" rows="3" maxlength="250" ondrop="return false;"> </textarea>
								</div>
							</div>
							<br><br>
						</div>
					</fieldset>
					<div class="panel-footer">
						<div class="L-size"></div>
						<div class="I-size">
							<button type="submit" id="transferFuel" class="btn btn-success">Transfer</button>
							<a class="btn btn-link" href="FuelInventory.in">Cancel</a>
						</div>
					</div>
		</section>	
	
    <script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/FU/createFuelTransfer.js"/>"></script>		
	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>	
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>	
	
</div>