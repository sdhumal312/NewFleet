<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">

<div class="content-wrapper" onload="getVehicleEmiDetails();">
	<section class="content-header">
	<c:if test="${param.update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Data Updated Successfully.
			</div>
		</c:if>
		<div class="box">
		<input type="hidden" id="vehicleId" value="${vehicle.vid}"/>
		<input type="hidden" id="vehicleMonthlyEMIPayment" value="${vehicleMonthlyEMIPayment}"/>
		
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/vehicle/1/1"/>">Vehicle</a> / <a
						href="<c:url value="/showVehicle.in?vid=${vehicle.vid}"/>"><c:out
							value="${vehicle.vehicle_registration}" /></a> / <span>Vehicle Emi</span>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_EMI_DETAILS')">
						<button id="addButton"  type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addTripExpense" data-whatever="@mdo">
							<span class="fa fa-plus"> Add EMI Details</span>
						</button>
					</sec:authorize>
											   
					<%-- <sec:authorize access="hasAuthority('ADD_EMI_DETAILS')">
						<a class="btn btn-info"  
							id ="emiPayment" href="#" > <span
							class="fa fa-exchange"> Emi Payment Details </span>
						</a>
					</sec:authorize> --%>
					
					<%-- <sec:authorize access="hasAuthority('EDIT_EMI_DETAILS')">
						<button id="EDITButton" type="button" class="btn btn-success" data-toggle="modal"
							data-target="#editEmiDetails" data-whatever="@mdo">
							<span class="fa fa-plus"> Edit EMI Details</span>
						</button>
					</sec:authorize> --%>
					
					<a class="btn btn-link btn-sm"
						href="showVehicle.in?vid=${vehicle.vid}">Cancel</a>
				</div>
			</div>


			<div class="modal fade" id="editEmiDetails" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form>
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">EMI Detail</h4>
							</div>
							<div class="modal-body">
							<input type="hidden" id="bankAccount" value="" name="bankAccountId" required="required"
													style="width: 100%;" disabled="disabled" />
													
							<input type="hidden" id="vehicleEmiDetailsId" value="" name="vehicleEmiDetailsId" required="required"
													style="width: 100%;" disabled="disabled" />
								<div class="row1">
									<label class="L-size control-label"> Bank Name: <abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" id="bankEditId" required="required"
											style="width: 100%;" disabled="disabled" />
									</div>
								</div>					
													
								<div class="row1">
											<label class="L-size control-label"> Banker Name/ Account : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" id="bankAccountIdEdit" name="bankAccountId" required="required"
												 onkeypress="return isNumberKey(event,this);"	style="width: 100%;" disabled="disabled" />
											</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Loan Amount :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="loanAmount"
											 maxlength="200" id="loanAmountEdit"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;" required="required" /> 
									</div>
								</div>
								</br>
								
								<div class="row">
									<label class="L-size control-label"> DownPayment Amount :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="editDownPaymentAmount"
											 maxlength="200" id="downPaymentAmountEdit"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;" required="required" /> 
									</div>
								</div>
								</br>
								
								<div class="row">
									<label class="L-size control-label"> Monthly EMI :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="monthlyEmiAmount"
											 step='0.01' maxlength="200" id="monthlyEmiAmountEdit"
											  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;" required="required" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Interest Rate : <abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" name="interestRate"
											 step='0.01' maxlength="200" id="interestRateEdit"
											 onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											/> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row1" id="grprenewalDate" class="form-group">
											<label class="L-size control-label" for="reservation">Loan 
												Start Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
														<div class="input-group input-append date"
															id="loanStartEdit">
															<input type="text" class="form-text"
																id="loanStartDateEdit" name="loanStartDate" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																required /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span> 
														</div>
											</div>
								</div>
							<div class="row1" id="grprenewalDate" class="form-group">
											<label class="L-size control-label" for="reservation">Loan 
												End Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
														<div class="input-group input-append date"
															id="loanEndEdit">
															<input type="text" class="form-text"
																id="loanEndDateEdit" name="loanEndDate" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																required /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span> 
														</div>
											</div>
							</div>
							
							<div class="row">
								<label class="L-size control-label"><span id="Type">Tenure
										Type</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<select class="form-text selectType" name="tenureType"
										style="width: 100%;" id="tenureTypeEdit">
											<option value="1">Month</option>
											<option value="2">Year</option>
											<option value="3">Days</option>
											
									</select> 
								</div>
							</div>
							
							<br/>
								
								<div class="row">
									<label class="L-size control-label"> Tenure : <abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="tenure"
											step='0.01' maxlength="15" value="0" id="tenureEdit"
											ondrop="return false;"  onkeypress="return isNumberKey(event,this);" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label">Remark :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
										 id="remarkEdit" name="remark" rows="3" maxlength="200"
											onkeypress="return ExpenseRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorExpenseRemarks" class="error"></label>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<button type="submit" id="editSave" class="btn btn-primary">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>

			
			<div class="modal fade" id="addTripExpense" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form>
						<div class="form-horizontal ">

							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="TripExpense">EMI Details</h4>
							</div>
							<div class="modal-body">
							
							<div class="row1">
											<label class="L-size control-label"> Bank Name : <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<select id="bankId"  style="width: 100%;" ></select>
											</div>
								</div>

								<div class="row1">
											<label class="L-size control-label"> Banker Account : <abbr
												title="required">*</abbr></label>
											<div class="I-size">

												<input type="hidden" id="bankAccountId" name="bankAccountId" required="required"
													style="width: 100%;" placeholder="Please Select Bank Account" />
											</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Loan Amount :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="loanAmount"
											placeholder="Enter Loan Amount" maxlength="200" value="0" id="loanAmount"
										    onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;" required="required" /> 
									</div>
								</div>
								</br>
								
								<div class="row">
									<label class="L-size control-label"> DownPayment Amount :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="downPaymentAmount"
											 maxlength="200" id="downPaymentAmount"  onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											ondrop="return false;" required="required" /> 
									</div>
								</div>
								</br>
								
								<div class="row">
									<label class="L-size control-label"> Monthly EMI :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="monthlyEmiAmount"
											placeholder="Enter Monthly EMI" step='0.01' value="0.00" maxlength="200" id="monthlyEmiAmount"
											ondrop="return false;" required="required"  onkeypress="return isNumberKeyWithDecimal(event,this.id);" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label"> Interest Rate : <abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="text" class="form-text" name="interestRate"
											placeholder="Enter Interest Rate" step='0.01' value="0" maxlength="200" id="interestRate" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
											/> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
							<div class="row1" id="grprenewalDate" class="form-group">
											<label class="L-size control-label" for="reservation">Loan 
												Start Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
														<div class="input-group input-append date"
															id="loanStart">
															<input type="text" class="form-text"
																id="loanStartDate" name="loanStartDate" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																required /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span> 
														</div>
											</div>
							</div>
							<div class="row1" id="grprenewalDate" class="form-group">
											<label class="L-size control-label" for="reservation">Loan 
												End Date <abbr title="required">*</abbr>
											</label>
											<div class="I-size">
														<div class="input-group input-append date"
															id="loanEnd">
															<input type="text" class="form-text"
																id="loanEndDate" name="loanEndDate" readonly="readonly"
																data-inputmask="'alias': 'dd-mm-yyyy'" data-mask=""
																required /> <span class="input-group-addon add-on"><span
																class="fa fa-calendar"></span></span> 
														</div>
											</div>
							</div>
							
							<div class="row">
								<label class="L-size control-label"><span id="Type">Tenure
										Type</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<select class="form-text selectType" name="tenureType"
										style="width: 100%;" id="tenureType">
											<option value="1" selected="selected">Month</option>
											<option value="2">Year</option>
											<option value="3">Days</option>
											
									</select> 
								</div>
							</div>
							
							<br/>
								
								<div class="row">
									<label class="L-size control-label"> Tenure :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="number" class="form-text" name="tenure"
											placeholder="Enter Tenure" step='0.01' maxlength="15" id="tenure"
											ondrop="return false;"  onkeypress="return isNumberKey(event,this);" /> <label
											id="errorExpenseName" class="error"></label>
									</div>
								</div>
								
								<div class="row">
									<label class="L-size control-label">Remark :</label>
									<div class="I-size">
										<textarea class="text optional form-text"
										 id="remark" name="remark" rows="3" maxlength="200"
											onkeypress="return ExpenseRemarks(event);"
											ondrop="return false;"> 
				                                </textarea>
										<label id="errorExpenseRemarks" class="error"></label>
									</div>
								</div>
								<br> <br>
							</div>
							<div class="modal-footer">
								<button type="submit" id="save" class="btn btn-primary">
									<span><spring:message code="label.master.Save" /></span>
								</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<span id="Close"><spring:message
											code="label.master.Close" /></span>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
			
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
					<div class="pull-left">
						<a href="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
								class="zoom" data-title="Vehicle Photo" data-footer="" 
								data-type="image" data-toggle="lightbox"> 
								  <span class="info-box-icon bg-green" id="iconContainer"><i class="fa fa-bus"></i></span>
							      <img src="${pageContext.request.contextPath}/getImageVehicle/${vehicle.vehicle_photoid}.in"
							      class="img-rounded" alt=" " width="100" height="100" id="vehicleImage" onerror="hideImageOnError(this)" />
						</a>
					</div>
					<div class="pull-left1">
						<h3 class="secondary-header-title">
							<a href="showVehicle.in?vid=${vehicle.vid}"> <c:out
									value="${vehicle.vehicle_registration}" />
							</a>
						</h3>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-black-tie" aria-hidden="true"
									data-toggle="tip" data-original-title="Status"><a
										href="#"><c:out value=" ${vehicle.vehicle_Status}" /></a></span></li>
								<li><input id="CurrentOdometer" type="hidden"
									value="${vehicle.vehicle_Odometer}"> <span
									class="fa fa-clock-o" aria-hidden="true" data-toggle="tip"
									data-original-title="Odometer"><a href="#"><c:out
												value=" ${vehicle.vehicle_Odometer}" /></a></span></li>
								<li><span class="fa fa-bus" aria-hidden="true"
									data-toggle="tip" data-original-title="Type"><a href="#"><c:out
												value=" ${vehicle.vehicle_Type}" /></a></span></li>
								<li><span class="fa fa-map-marker" aria-hidden="true"
									data-toggle="tip" data-original-title="Location"><a
										href="#"><c:out value=" ${vehicle.vehicle_Location}" /></a></span></li>
								<li><span class="fa fa-users" aria-hidden="true"
									data-toggle="tip" data-original-title="Group"><a
										href="#"><c:out value=" ${vehicle.vehicle_Group}" /></a></span></li>
								<li><span class="fa fa-road" aria-hidden="true"
									data-toggle="tip" data-original-title="Route"><a
										href="#"><c:out value=" ${vehicle.vehicle_RouteName}" /></a></span></li>
							</ul>
						</div>
					</div>
				</sec:authorize>
			</div>
			
		</div>
	</section>
	
	<section>
		<div id="contentbox">
		  <table>
		  	<tr>
		  	  <td width="90%" valign="top">
		  	  		<div class="panel-group">
						<div class="panel panel-info" id="top-border-boxshadow">
							<div class="panel-heading text-center">
								<h4 data-selector="header">EMI Details</h4>
							</div>
							<div class="panel-body">
								<div class="row">
									<table id="VendorPaymentTable" class="table table-hover table-bordered">
									</table>
								</div>
							</div>
						</div>
					</div>
		  	  </td>
		  	  <td width="10%">
		  	  	<div class="col-md-2 col-sm-2 col-xs-12">
						<%@include file="VehicleSideMenu.jsp"%>
				</div>
		  	  </td>
		  	</tr>
		  </table>
		</div>
	</section>
	
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js" />"></script>

		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepicker.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js" />"></script>
		<%-- <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
		 <script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script> --%>
		<script>
			$(function() {
				$('#reservation').daterangepicker();
				$("#to").select2({
					placeholder : "Please Select Type"
				});

			});
			
			 $(document).ready(function() {
		         var img = $("#vehicleImage");
		         var iconContainer = $("#iconContainer");

		         // Check if the image is loaded
		         img.on("load", function() {
		             // If loaded, hide the icon
		             iconContainer.hide();
		         });
		     });
		</script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
		<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/VehicleEmiDetails.js" />"></script>
		
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>
		<script type='text/javascript' 
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/photoView/ekko-lightbox.js"/>" ></script>
		  
</div>