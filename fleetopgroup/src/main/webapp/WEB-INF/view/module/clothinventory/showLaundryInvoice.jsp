<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet"
	href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
	<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="content">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="ClothInventory.in">New Upholstery Inventory</a>
					/ <span>View Upholstery Details</span>
				</div>
				<div class="pull-right">
					<% 
						Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
						if(permission.contains(new SimpleGrantedAuthority("DELETE_CLOTH_INVENTORY"))) {
						%>
							<a id="deleteLaundry" onclick="deleteClothLaundryInvoice();" class="btn btn-primary btn-sm" href="#">Delete Invoice</a>
					  		
					<% } %>
					
					<a href="ClothInventory.in">Cancel</a>
				</div>
				
				<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">CI-</span></span>
								<input class="form-text" id="searchClothInv" onkeyup="searchClothInvOnEnter(event);" name="tripStutes"
									type="number" min="1" required="required"
									placeholder="CI-ID eg:7878" maxlength="20"> <span
									class="input-group-btn">
									<button type="submit" onclick="clothInvoiceSearch();" name="search" id="search-btn"
										class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
				</div>
				
			</div>
			<sec:authorize access="!hasAuthority('DELETE_CLOTH_INVENTORY')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('DELETE_CLOTH_INVENTORY')">
				<div class="row">
					<div class="col-md-7 col-sm-7 col-xs-7">
						<h4>Laundry Invoice <span id="clothInvoiceNumber"></span></h4>
						<input type="hidden" id="invoiceId" value="${invoiceId}"/>
						<h4 id="vendorinfo" align="center">
						</h4>
					</div>
					<div class="col-md-2 col-sm-2 col-xs-2">
						<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table ">
									<tbody>
										<tr class="row" style="text-align: left;">
											<th class="key">Sent Date :</th>
											<td class="value" id="invoiceDate"></td>
										</tr>
										<tr class="row" style="text-align: left;">
											<th class="key">Payment Type :</th>
											<td class="value" id="paymentType"></td>
										</tr>
										<tr class="row" style="text-align: left;"> 
											<th class="key">Total Received Qty :</th>
											<td class="value" id="totalRecevedQuantity"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Receive Date :</th>
											<td class="value" id="invoiceNumber"></td>
										</tr>
										<tr class="row">
											<th class="key">Total Amount:</th>
											<td class="value" id="invoiceAmount"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-4 col-xs-11">
						<div class="box box-success">
							<div class="box-body no-padding">
								<table class="table">
									<tbody>
										<tr class="row">
											<th class="key">Payment Number :</th>
											<td class="value" id="paymentNumber"></td>
										</tr>
										<tr class="row">
											<th class="key">Total Quantity :</th>
											<td class="value" id="allQuantity"></td>
										</tr>
										<tr class="row" id="tallyCompanyRow" style="display: none;">
											<th class="key">Tally Company :</th>
											<td class="value" id="tallyCompany"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					
				</div>
				<fieldset>
				
		<div class="modal fade" id="receivedHistoryModal" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Service Reminder Details</h4>
						</div>
						<div class="modal-body">
							
							<table id="receiveTable" style="width: 100%; display: none;" class="table-responsive table">
								<thead>
									<tr>
										<th>Sr</th>
										<th>Upholstery</th>
										<th>Receive Date</th>
										<th>Received Quantity</th>
										<th>Received By</th>
										<th>Remark</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									
								</tbody>
							</table>	
								
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
				
					<div class="modal fade" id="receiveClothFromLaundry" role="dialog">
						<div class="modal-dialog" style="width:750px;">

							<!-- Modal content-->
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Receive Upholstery</h4>
									</div>
									<div class="modal-body">
									<input type="hidden" id="laundryClothDetailsId">
									<input type="hidden" id="laundryInvoiceId">
									<input type="hidden" id="locationId">
									<input type="hidden" id="clothTypesId">
										<div class="row">
											<label class="L-size control-label" id="Type">Total Quantity
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													id="totalQuantity" required="required" name="totalQuantity"
													maxlength="10" /> 
											</div>
										</div>
										<br />
										
										<!-- <div class="row">
											<label class="L-size control-label" id="Type">Received Quantity
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													id="receivedQuantity" required="required" name="receivedQuantity"
													maxlength="50" /> 
											</div>
										</div> -->
										
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="col-md-9">
												<div class="col-md-1">
													<label class="control-label">Previous Receive Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Damage Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Lost Qty</label>
												</div>
											</div>
										</div>	
												
										<div class="row1" id="grpquantity" class="form-group">
											<label class="L-size control-label" for="quantity">
											</label>
											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text" readonly="readonly"
													id="receivedQuantity" required="required" name="receivedQuantity"
													maxlength="50" /> 
													<div id="quantityErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="receiveDmgQuantity" required="required" name="receiveDmgQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="receiveLosedQuantity" required="required" name="receiveLosedQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
										</div>	
										
										
										<br />
										<div class="row">
											<label class="L-size control-label" id="Type">Quantity 
												 :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" id="receiveQuantity" required="required" name="receiveQuantity"
													maxlength="10" onkeypress="return isNumberKeyWithDecimal(event,this.id);" /> 
											</div>
										</div>
										<br />
										<div class="row" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Receive
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="opendDate">
													<input type="text"  class="form-text" name="INVOICE_DATE"
														id="receiveDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<br/>
										<div class="row">
											<label class="L-size control-label" id="Type">Remark
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" 
													id="receiveDescription" required="required" name="receiveDescription"
													maxlength="50" /> 
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save" onclick="receiveClothFromLaundry();">Receive</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
							</div>

						</div>
					</div>

					<div class="row">
						<div class="col-md-11 col-sm-11 col-xs-11">
							<div class="table-responsive">
								<input type="hidden" id="selectedQuantity" value="0"/>
								<table class="table" id="dataTable" style="display: none;">
									<thead>
										<tr class="breadcrumb">
											<th class="fit"></th>
											<th class="fit ar">Upholstery Type</th>
											<th class="fit ar">Qty</th>
											<th class="fit ar">Received Qty</th>
											<th class="fit ar">Damaged Qty</th>
											<th class="fit ar">Lost Qty</th>
											<th class="fit ar">Remaining Qty</th>
											<th class="fit ar">Each</th>
											<th class="fit ar">Dis</th>
											<th class="fit ar">GST</th>
											<th class="fit ar">Total</th>
										</tr>
									</thead>
									<tbody id="batteryAmountBody">
										
									</tbody>

								</table>
							</div>
						</div>
						<br/><br/><br/><br/>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<dl>
										<dd>Description :</dd>
										<dt id="description"></dt>
									</dl>
								</div>
								<div class="col-md-3">
									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key">SubTotal :</th>
												<td class="value"><i class="fa fa-inr"></i>
													<span id="subTotal"></span></td>
											</tr>
											<tr class="row">
												<th class="key"><a>Total :</a></th>
												<td class="value"><a><i class="fa fa-inr"></i>
														<span id="total"></span></a></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
				<div class="modal fade" id="damageClothFromLaundry" role="dialog">
						<div class="modal-dialog" style="width:750px;">

							Modal content
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Add Upholstery Damaged Qty</h4>
									</div>
									<div class="modal-body">
									<input type="hidden" id="damagelaundryClothDetailsId">
									<input type="hidden" id="damagelaundryInvoiceId">
									<input type="hidden" id="damagelocationId">
									<input type="hidden" id="damageclothTypesId">
									<input type="hidden" id="validateDamageQuantity" value="0"/>
										<div class="row">
											<label class="L-size control-label" id="Type">Total Quantity
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													id="totalQty" required="required" name="totalQty"
													maxlength="10" /> 
											</div>
										</div>
										<br />
										
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="col-md-9">
												<div class="col-md-1">
													<label class="control-label">Previous Receive Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Damage Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Lost Qty</label>
												</div>
											</div>
										</div>	
												
										<div class="row1" id="grpquantity" class="form-group">
											<label class="L-size control-label" for="quantity">
											</label>
											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text" readonly="readonly"
													id="receivedQty" required="required" name="receivedQty"
													 /> 
													<div id="quantityErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="previousDmgQuantity" required="required" name="previousDmgQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="damageLosedQuantity" required="required" name="damageLosedQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
										</div>	
										
										
										<div class="row">
											<label class="L-size control-label" id="Type">Damage Quantity 
												 :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													id="damageQuantity" required="required" name="damageQuantity"
													maxlength="10" /> 
											</div>
										</div>
										<br />
										<div class="row" id="grpinvoiceDate1" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Receive
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="opendDate">
													<input type="text"  class="form-text" name="damageINVOICE_DATE"
														id="damageReceiveDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										
										<br/>
										<div class="row">
											<label class="L-size control-label" id="Type">Remark
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" 
													id="damageDescription" required="required" name="damageDescription"
													maxlength="50" /> 
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save" onclick="saveDamageWashingQty();">Receive Damage</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
							</div>

						</div>
					</div>
					
					
					<div class="modal fade" id="lostClothFromLaundry" role="dialog">
						<div class="modal-dialog" style="width:750px;">

							Modal content
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Add Upholstery Lost Qty</h4>
									</div>
									<div class="modal-body">
									<input type="hidden" id="lostlaundryClothDetailsId">
									<input type="hidden" id="lostlaundryInvoiceId">
									<input type="hidden" id="lostlocationId">
									<input type="hidden" id="lostclothTypesId">
									<input type="hidden" id="validateLostQuantity" value="0"/>
										<div class="row">
											<label class="L-size control-label" id="Type">Total Quantity
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" readonly="readonly"
													id="totQty" required="required" name="totQty"
													maxlength="50" /> 
											</div>
										</div>
										<br />
										
										<div class="row1">
											<label class="L-size control-label"></label>
											<div class="col-md-9">
												<div class="col-md-1">
													<label class="control-label">Previous Receive Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Damage Qty</label>
												</div>
												<div class="col-md-2">
													<label class="control-label">Previous Lost Qty</label>
												</div>
											</div>
										</div>	
												
										<div class="row1" id="grpquantity" class="form-group">
											<label class="L-size control-label" for="quantity">
											</label>
											<div class="col-md-9">
												<div class="col-md-1">
													<input type="text" class="form-text" readonly="readonly"
													id="recvQty" required="required" name="recvQty"
													 /> 
													<div id="quantityErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="prevDmgQuantity" required="required" name="prevDmgQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
												<div class="col-md-2">
													<input type="text" class="form-text" readonly="readonly"
													id="prevLosedQuantity" required="required" name="prevLosedQuantity"
													 />
													<div id="unitpriceErrorMsg" class="text-danger"></div>
												</div>
											</div>
											<br/>
											<br/>
											<br/>
											<br/>
											<br/>
										</div>	
										
										
										<div class="row">
											<label class="L-size control-label" id="Type">Lost Quantity 
												 :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													id="lostQuantity" required="required" name="lostQuantity"
													maxlength="10" min="1" /> 
											</div>
										</div>
										<br />
										<div class="row" id="grpinvoiceDate" class="form-group">
											<label class="L-size control-label" for="invoiceDate">Receive
												Date :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="opendDate">
													<input type="text"  class="form-text" name="lostINVOICE_DATE"
														id="lostReceiveDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="invoiceDateIcon" class=""></span>
												<div id="invoiceDateErrorMsg" class="text-danger"></div>
											</div>
										</div>
										<br/>
										<div class="row">
											<label class="L-size control-label" id="Type">Remark
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" 
													id="lostDescription" required="required" name="lostDescription"
													maxlength="50" /> 
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save" onclick="savelostWashingQty();">Add Lost</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
							</div>

						</div>
					</div>
				
					<div class="modal fade" id="addVehicleLaundryModel" role="dialog">
						<div class="modal-dialog" style="width:750px;">
							Modal content
							<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title" id="VehicleType">Add Vehicle Laundry Details</h4>
									</div>
									<div class="modal-body">
									<input type="hidden" id="clothTypesId">
									<input type="hidden" id="maxVehicleQty">
									<input type="hidden" id="asignVehicleQty">
									<input type="hidden" id="laundryClothDetailsId">
									
									<div>
										<table class="table table stripped">
										<thead>
											<tr>
												<th>Vehicle</th>
												<th>Upholstery Type</th>
												<th>Quantity</th>
												<th>Action</th>
											</tr>
										</thead>
											<tbody id="vehicleBody">
												
											</tbody>
										</table>
									</div>
									
									<div class="row">
											<label class="L-size control-label" for="issue_vehicle_id">Vehicle
												: </label>
											<div class="I-size" id="vehicleSelect">
												<div class="col-md-9">
													<input type="hidden" id="vid" name="vid"
														style="width: 100%;" value="0"
														placeholder="Please Enter 2 or more Vehicle Name" />
												</div>
												<label id="errorVehicle" class="error"></label>
											</div>
										</div>
										<br/>
										<div class="row">
											<label class="L-size control-label" id="Type">Total Quantity
												 :
											</label>
											<div class="I-size">
												<input type="text" class="form-text" onkeypress="return isNumberKeyWithDecimal(event,this.id)"
													id="vehicleQuantity" required="required" name="vehicleQuantity"
													maxlength="10" /> 
											</div>
										</div>
										<br />
										
										
										
									<div class="modal-footer">
										<button type="submit" class="btn btn-primary">
											<span id="Save" onclick="saveVehicleLaundryDetails();">Add Details</span>
										</button>
										<button type="button" class="btn btn-default"
											data-dismiss="modal">
											<span id="Close">Close</span>
										</button>
									</div>
							</div>

						</div>
					</div>
				
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | <small class="text-muted"><b>Created
					date: </b> <span id="createdOn"></span></small> | <small
				class="text-muted"><b>Last updated by :</b> <span id="lastupdatedBy"></span></small> | <small
				class="text-muted"><b>Last updated date:</b><span id="lastUpdated"></span></small>
		</div>
	</section>
</div>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>

<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script
	src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/showLaundryInvoice.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/clothinventory/clothUtility.js"></script>