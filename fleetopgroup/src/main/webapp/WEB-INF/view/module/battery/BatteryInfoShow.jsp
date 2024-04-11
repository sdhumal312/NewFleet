<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<script>

function getBatteryPrint(batteryId) {

	childwin = window.open('PrintBatteryInventory?batteryId='+batteryId,'newwindow', config='height=300,width=425, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, directories=no, status=no');
}
</script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open">Home</a> / <a
						href="BatteryInventory.in">New Battery
						Inventory</a> 
					<span id="batteryNumber"></span>
				</div>
				
				<div class="pull-right">
				
						<% 
							Collection<GrantedAuthority>  permission = (Collection<GrantedAuthority>)request.getAttribute("permissions");
							if(permission.contains(new SimpleGrantedAuthority("EDIT_BATTERY"))) {
						%>
						
						
						<button type="button" id="editBatteryNumber" class="btn btn-success" data-toggle="modal"
							data-target="#editBatterySerialNumber" data-whatever="@mdo">
							<span class="fa fa-plus"> Edit Battery No</span>
						</button>
						
						<%} %>
						
				</div>
				
				<div class="pull-right" id="status-close">
									
					<sec:authorize access="hasAuthority('BATTERY_PRINT')">
										
						<%-- <c: test="${configuration.showBatteryInventoryPrint}"> --%>
							<input type="button"  class="btn btn-default fa fa-print" 
								onclick="getBatteryPrint(${batteryId});" 
								value="Print" />
							</c>    
										    
										
					</sec:authorize>
				</div>
			</div>
			
		</div>
	</section>
	<section class="content-body">
		<input type="hidden" id="batteryId" value="${batteryId}">
		<input type="hidden" id="showSubLocation" value="${showSubLocation}">
		<div class="modal fade" id="editBatterySerialNumber" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Edit Battery Serial No</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Serial No
									:<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" class="form-text"
										required="required" id="batteryInvoiceId" />
									<input type="text" class="form-text"
										required="required" id="batterySerial"
										name="batterySerialNo" maxlength="50"
										placeholder="Enter Battery Serial Number" /> 
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" id="updateSerialNumber" class="btn btn-primary">
								Save
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close">Close</span>
							</button>
						</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="editrenewalPeriod" role="dialog">
			<div class="modal-dialog" style="width:1250px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Battery History Details</h4>
						</div>
						<div class="modal-body">
							
							<table id="dataTable" style="width: 100%; display: none;" class="table-responsive table">
								<thead>
									<tr>
										<th>Asign Date</th>
										<th>Battery Number</th>
										<th>Vehicle</th>
										<th>Position</th>
										<th>Status</th>
										<th>Odometer</th>
										<th>Usage</th>
										<th>Usage(in Days)</th>
										<th>Comment</th>
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
		<!-- Scrop Top available -->
		<div class="modal fade" id="scroptoavailable" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveTyreAvailable.in" method="post">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Scrap To Available</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Scrap To
									Available Note :<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="hidden" class="form-text" value="${Tyre.TYRE_ID}"
										required="required" name="TyreID" />
									<!-- Tyre Serial Num -->
									<textarea class="text optional form-text" maxlength="200"
										required="required" id="initial_note" name="Description"
										rows="3">
				                                 </textarea>
									<label id="errorvType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save">Change To Available</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>

			</div>
		</div>
		<% if(permission.contains(new SimpleGrantedAuthority("VIEW_BATTERY_INVENTORY"))) { %>
			<div class="row">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-6 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Battery Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Battery Number :</th>
													<td id="batteryNo" class="value" style="width: 2432452px;"></td>
												</tr>
												<tr class="row">
													<th class="key">Manufacturer Name :</th>
													<td class="value" id="manufacturer"></td>
												</tr>
												<tr class="row">
													<th class="key">Battery Model :</th>
													<td class="value" id="batteryType"></td>
												</tr>
												<tr class="row">
													<th class="key">Battery Capacity :</th>
													<td class="value" id="capacity"></td>
												</tr>
												<tr class="row">
													<th class="key">Location :</th>
													<td class="value" id="location"></td>
												</tr>
												<c:if test="${showSubLocation}">
												<tr class="row">
													<th class="key"> Sub Location :</th>
													<td class="value" id="Sublocation"></td>
												</tr>
												</c:if>
												<tr class="row">
													<th class="key">Status :</th>
													<td class="value"> <span id="status"></span> <span id="dismountStatus"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Warranty Status :</th>
													<td class="value" style="font-weight: bold;font-size: 16px;" id="warrantyStatus"></td>
												</tr>
												<tr class="row">
													<th class="key">Warranty Period :</th>
													<td class="value" id="warrantyPeriod"></td>
												</tr>
												<tr class="row">
													<th class="key">Warranty counter :</th>
													<td class="value" id="warrantyCounter"></td>
												</tr>
												<tr class="row">
													<th class="key">Cost Per Day :</th>
													<td class="value" id="costPerDay"></td>
												</tr>
												<tr class="row">
													<th class="key">Cost Per Odometer :</th>
													<td class="value" id="costPerOdometer"></td>
												</tr>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Scrap Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key">Scraped By :</th>
													<td class="value" id="scrapedBy"></td>
												</tr>
												<tr class="row">
													<th class="key">Scraped Date :</th>
													<td class="value" id="scrapedDate"></td>
												</tr>
										</table>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Vehicle Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table">
											<tbody>

												<tr class="row">
													<th class="key">Battery Amount :</th>
													<td class="value" id="batteryAmount"></td>
												</tr>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value" id="vehicleRegistration"></td>
												</tr>
												<tr class="row">
													<th class="key">Open Odometer :</th>
													<td class="value" id="openOdodmeter"></td>
												</tr>
												<tr class="row">
													<th class="key">Close Odometer :</th>
													<td class="value" id="closedOdometer"></td>
												</tr>
												<tr class="row">
													<th class="key">Battery Purchase Date :</th>
													<td class="value" id="purchaseDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Battery Assign Date :</th>
													<td class="value" id="batteryAsignDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Total Battery Running Km :</th>
													<td class="value" id="runningKM"></td>
												</tr>
												<tr class="row">
													<th class="key" >No Of Days Used : </th>
													<td class="value" style="font-weight: bold;"><h3 id="usesNoOfTime">
															
														</h3></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<ul class="nav nav-list">
						<li class="active" id="history"><!-- <a
							href="TyreInventoryHistory?ID=">View
								History</a> --></li>
						<li class="active"><a
							href="BatteryInventory.in">New Battery
								Inventory</a></li>
					</ul>
				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span></small> | <small class="text-muted"><b>Created
						date: </b> <span id="createdOn"></span></small> | <small
					class="text-muted"><b>Last updated by :</b> <span id="lastUpdatedBy"></span></small> | <small class="text-muted"><b>Last
						updated date:</b> <span id="lastUpdatedOn"></span></small>
			</div>
		<% } %>
	</section>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/tabsShowVehicle.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/viewBatteryInfo.js"></script>
</div>