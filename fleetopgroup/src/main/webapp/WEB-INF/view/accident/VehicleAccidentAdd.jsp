<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="viewVehicleAccidentList.in">Vehicle Accident</a> /
					<span>Add Vehicle Accident</span>
				</div>
				<div class="pull-right">
					<a href="newTripSheetEntries.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<sec:authorize access="!hasAuthority('VIEW_VEHICLE_ACCIDENT')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_VEHICLE_ACCIDENT')">
	
	<section class="content">
		<div id="copyTrip" style="text-align: center;display: none;"></div>
		<div class="box">
			   <div class="box-body">
			   <input type="hidden" name="companyId" id="companyId" value="${companyId}"/> 
			   <input type="hidden" name="userId" id="userId" value="${userId}"/> 
			   <input type="hidden" name="accessToken" id="accessToken" value="${accessToken}"/> 
	<input type="hidden" id="accidentClaimConfig" value="${configuration.accidentClaim}">
							
							 		<div class="row1" style="display: none;color: red;" class="help-block" id="last_occurred">
										<div >
											<span class="loading ng-hide" id="loading"> <img
												alt="Loading" class="loading-img"
												src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/ajax-loader.gif" />">
											</span>
										</div>
									</div> 
							
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Vehicle : <abbr title="required">*</abbr></label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon">
                              	 <i class="fa fa-bus"></i>
                               </span>
                              	 <input type="hidden" id="TripSelectVehicle" name="vid" style="width: 100%;" value="0" 
                              	 	placeholder="Please Enter 2 or more Vehicle Name" />
                               </div>
                            </div>
                         </div>
                         
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Date : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-control" name="accidentDate" readonly="readonly"
														id="accidentDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
								</div>
                            </div>
                         </div>
                         
                         <div class="form-group column" id="dispatchDateTime">
                            <label class="col-md-4 control-label">Time</label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group clockpicker">
                                <span
														class="input-group-addon"> <i
														class="glyphicon glyphicon-time" aria-hidden="true"></i>
													</span>
                            	 <input type="text" class="form-control" readonly="readonly"
														name="accidentTime" id="accidentTime" onchange="getVehicleTripDetails();">
                               
                               </div>
                            </div>
                         </div>
                         
                          <div class="form-group column">
                            <label class="col-md-4 control-label">Driver : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-user"></i>
                               </span>
                              <input type="hidden" id="driverList"
														name="tripFristDriverID" style="width: 100%;"
														placeholder="Please Enter 3 or more Driver Name, No"
														value="0" />

                               </div>
                            </div>
                         </div>
                       
                         <div class="form-group column">
                            <label class="col-md-4 control-label">TripSheet
												 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
									<select id="tripSheetId" name="tripSheetId"
														style="width: 100%;">
													</select>
                               </div>
                            </div>
                         </div>
                         
                          <div class="form-group column">
                            <label class="col-md-4 control-label">Route
												 : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
																<input type="hidden" id="FuelRouteList" name="routeID"
										style="width: 100%;" value="0" required="required"
										placeholder="Please Enter 3 or more Route Name, NO " />
                               </div>
                            </div>
                         </div>
                        
                         <div class="form-group column">
                            <label class="col-md-4 control-label">Incident
												Location : </label>
                            <div class="col-md-8 inputGroupContainer">
                               <div class="input-group">
                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
                             	 <input class="form-control" name="incidentlocation" placeholder=""
													id="incidentlocation" type="text" maxlength="50">
                               </div>
                            </div>
                         </div>
                   </div>
              </div>		
								<div class="box">
									<div class="box-body">
										<div class="row1">
											<label class="L-size control-label">Accident Description :</label>
											<div class="I-size">
												<textarea class="form-control" id="description"
													name="description" rows="3" maxlength="550">
													</textarea>
											</div>
										</div>
									</div>
								</div>
								
						<b>Other Vehicle Details (If any) <a class=" btn btn-default"
										onclick="visibility('otherVehicle', 'plusSign');"> <i id="plusSign" class="fa fa-plus"></i>
									</a></b>
							<fieldset>
							<div class="box" id="otherVehicle" style="display: none;">
									<div class="box-body">
								<div >
									<div class="box">
										<div class="box-body">
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Vehicle Number : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-bus"></i></span>
					                             	 <input class="string required form-control"
														name="otherVehicle" maxlength="50" type="text" id="otherVehicleNumber"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
					                         	<div class="form-group column ">
					                            <label class="col-md-4 control-label">Driver : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="otherDriver" maxlength="50" type="text" id="otherDriver"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">Driver Mobile : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"  
														name="otherDriverMob" maxlength="10" type="text" id="otherDriverMob"
														onblur="return isMobileNum(this);" onkeypress="return isNumberKey(event,this);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Owner : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="otherVehicleOwner" maxlength="50" type="text" id="otherVehicleOwner"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                         
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">More Details : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="otherVehicleDetails" maxlength="50" type="text" id="otherVehicleDetails"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
										</div>
									</div>
								</div>
								</div></div>
								</fieldset>
								<br/>
								
								<b>FIR Details (If any) <a class=" btn btn-default"
										onclick="visibility('firDetails', 'plusSignFIR');"> <i id="plusSignFIR" class="fa fa-plus"></i>
									</a></b>
							<fieldset>
							<div class="box" id="firDetails" style="display: none;">
									<div class="box-body">
								<div >
									<div class="box">
										<div class="box-body">
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">FIR Number : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="AdvanceAmount" maxlength="50" type="text" id="firNumber"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
					                         	<div class="form-group column ">
					                            <label class="col-md-4 control-label">Police Station : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="firPoliceStation" maxlength="50" type="text" id="firPoliceStation"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">FIR By : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="advanceRefence" maxlength="50" type="text" id="firBy"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">FIR Remark : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"
														name="advanceRefence" maxlength="50" type="text" id="firRemark"
														onkeypress="return IsAdvanceRefence(event);"
														ondrop="return false;"> 
					                               </div>
					                            </div>
					                         </div>
					                         
					                        <!--  <div class="form-group column ">
					                            <label class="col-md-4 control-label">FIR Document : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input type="file" name="input-file-preview"
													id="renewalFile" /> <span id="firDocument" class=""></span>
					                               </div>
					                            </div>
					                         </div> -->
										</div>
									</div>
								</div>
								</div></div>
								</fieldset>
								<br/>
								<c:if test="${configuration.accidentClaim}">
								<b>Claim Details (If any) 
								<a class=" btn btn-default"
									onclick="visibility('additionalDetails', 'plusSignAdditional');"> <i id="plusSignAdditional" class="fa fa-plus"></i>
								</a></b>
							<fieldset>
							<div class="box" id="additionalDetails" style="display: none;">
									<div  class="box-body">
									<div class="box">
										<div class="box-body">
											<div class="form-group column ">
					                            <label class="col-md-4 control-label">Approx Damage Amount : </label>
					                            <div class="col-md-8 inputGroupContainer">
					                               <div class="input-group">
					                               <span class="input-group-addon"><i class="fa fa-book"></i></span>
					                             	 <input class="string required form-control"    onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														name="approxDamageAmount" maxlength="50" type="number" id="approxDamageAmount"
														ondrop="return false;">
					                               </div>
					                            </div>
					                         </div>
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">Amount Received : </label>
					                            <div class="col-md-1">
					                             	 <input class="pull-left form-control" name="isAmtReceived " type="checkbox" id="isAmtReceived" onclick="amtReceivedClick();">  
					                            </div>
					                             <div class="col-md-6" id="receivedAmountDiv" style="display: none;">
					                              <input class="string required form-control"    onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														name="approxDamageAmount" maxlength="50" type="number" id="receivedDamageAmount"
														ondrop="return false;">
					                             </div>
					                         </div>
					                          <div class="form-group column ">
					                            <label class="col-md-4 control-label">Amount Paid : </label>
					                            <div class="col-md-1">
					                             	 <input class="pull-left form-control" name="isAmtPaid "type="checkbox" id="isAmtPaid" onclick="amtPaidClick();"> 
					                            </div>
					                             <div class="col-md-6" id="paidAmountDiv" style="display: none;">
					                              <input class="string required form-control"    onkeypress="return isNumberKeyWithDecimal(event,this.id);"
														name="approxDamageAmount" maxlength="50" type="number" id="paidDamageAmount"
														ondrop="return false;">
					                             </div>
					                         </div>
					                         
					                         <div class="form-group column ">
					                            <label class="col-md-4 control-label">Do You Want To Claim : </label>
					                            <div class="col-md-4">
					                             <div class='btn-group'>
						                             <label id="yes" class='btn btn-success btn-sm active ' onclick="selectClaim(1);"> Yes </label>  
						                             <label id="no" class='btn btn-default  btn-sm' onclick="selectClaim(2);"> No </label> 
					                             </div>
					                             <input type="hidden" id="isClaim">
					                            </div>
					                             
					                         </div>
					                         
					                          <div class="form-group column ">
					                            <label class="col-md-4 control-label">Remark: </label>
					                          	<div class="col-md-6" id="claimRemarkDiv" >
					                              <input class="string required form-control"
														name="approxDamageAmount" maxlength="50" type="text" id="claimRemark"
														ondrop="return false;">
					                             </div>
					                          </div>
										</div>
									</div>
								</div>
							</div>
								</fieldset>
								</c:if>
								<br/>
						<fieldset class="form-actions">
								<div class="row1">

									<div class="pull-right">
										
										<button type="submit" id="saveAccidentDetails" class="btn btn-success"
											onclick="return saveAccidentDetails();">Save</button>
										<a class="btn btn-default" href="viewVehicleAccidentList.in">Cancel</a>
									</div>
								</div>
							</fieldset>
							
              </div>
              </div>
           </sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/accident/VehicleAccidentAdd.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />" /></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
	<script>
	$(function() {
		$(document).ready(function() {
			$('.clockpicker').clockpicker({
				placement: 'bottom',
				align: 'right',
				autoclose: true
			});
			
			$('#tripSheetId').select2();
		});
		
	});
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/ipaddress.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/accident/validateVehicleAccident.js" />"></script>		
</div>