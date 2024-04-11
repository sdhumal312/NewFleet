<%@ include file="../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
	<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/bootstrap-clockpicker.min.css" />">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
						<a href="<c:url value="/viewVehicleAccidentList.in"/>"> Vehicle Accident</a> /
						 <small>Show Vehicle Accident Detail</small>
					</div>
					<div class="col-md-off-5">
					
						<div class="col-md-2">
							<div class="input-group">
									<span class="input-group-addon"> <span
										aria-	1="true">AD-</span></span> <input class="form-text" onkeyup="accidentEntriesSearchOnEnter(event);"
										id="accidentSearchNumber" name="Search" type="number"
										required="required" min="1" placeholder="ID eg: 1234">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn" onclick="accidentEntriesSearch();"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>

						<sec:authorize access="hasAuthority('ADD_VEHICLE_ACCIDENT')">
							<a class="btn btn-info btn-sm"
								href="addVehicleAccident.in"> <span
								class="fa fa-plus"></span> Add Vehicle Accident
							</a>
						</sec:authorize>
						
						<div class="pull-right">
							<a></a>		
							<a class="btn btn-danger" href="<c:url value="/viewVehicleAccidentList.in"/>">Cancel</a>
						</div>
						
					</div>
					
					
						
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VEHICLE_ACCIDENT')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VEHICLE_ACCIDENT')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-5 col-sm-5 col-xs-12">
							<input type="hidden" id="accidentId" value="${accidentId}">
							<input type="hidden" id="dAccidentId" value="${dAccidentId}">
							<input type="hidden" id="vid">
							<input type="hidden" id="driverId">
							<input type="hidden" id="companyId" value="${companyId}">
							<input type="hidden" id="userId" value="${userId}">
							<input type="hidden" id="serveyorDetailsId">
							<input type="hidden" id="status">
							<input type="hidden" id="accidentDate">
							<input type="hidden" id="isClaimStatusId">
							<input type="hidden" id="accidentTypeFlag" value="false">
							<input type="hidden" id="accidentClaimConfig" value="${configuration.accidentClaim}">
							<input type="hidden" id="accidentDocValidate" value="${configuration.accidentDocValidate}">
							<input type="hidden" id="accidentAdditionalFields" value="${configuration.accidentAdditionalFields}">
							<input type="hidden" id="spotSurveyFlag" >
							<input type="hidden" id="spotSurveyCompletionFlag" >
							<input type="hidden" id="keepOpenFlag" value="false">
							<input type="hidden" id="beforeApprovalFlag" value="false">
							<input type="hidden" id="serviceStatusId">
							<input type="hidden" id="docExist">
							<input type="hidden" id="multipleQuotation" value="${dseConfig.multipleQuotation}">
						<!-- 	<input type="hidden" id="accidentPersonTypeId" name="accidentPersonTypeId"> -->
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Vehicle Accident Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Accident Number :</th>
													<td class="value" id="accidentNumber"></td>
												</tr>
												<tr class="row">
													<th class="key">Accident Status :</th>
													<td class="value" id="accidentStatus"></td>
												</tr>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td><span  class="value" id="vehicleNumber"></span></td>
												</tr>
												<tr class="row">
													<th class="key">Accident Date Time :</th>
													<td class="value" id="accidentDateTime"></td>
												</tr>
												<tr class="row">
													<th class="key">Driver :</th>
													<td class="value" id="driver"></td>
												</tr>
												<tr class="row">
													<th class="key">TripSheet :</th>
													<td class="value" id="tripSheetNumber">
													</td>
												</tr>
												<tr class="row">
													<th class="key">Route :</th>
													<td class="value" id="routeId">
													</td>
												</tr>
												<tr class="row">
													<th class="key">Description :</th>
													<td class="value" id="description">
													</td>
												</tr>
										</table>
									</div>
								</div>
								
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">FIR Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">FIR Number :</th>
													<td class="value" id="firNumber"></td>
												</tr>
												<tr class="row">
													<th class="key">Police Station :</th>
													<td class="value" id="polishStation"></td>
												</tr>
												<tr class="row">
													<th class="key">FIR By :</th>
													<td class="value" id="firBy"></td>
												</tr>
												<tr class="row">
													<th class="key">FIR Remark :</th>
													<td class="value" id="firRemark"></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Additional Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Approx Damage Amount :</th>
													<td class="value" id="approxDamageAmount"></td>
												</tr>
												<tr class="row">
													<th class="key">Damage Amount Status :</th>
													<td class="value" id="damageAmountStatus"></td>
												</tr>
												<tr class="row">
													<th class="key">Damage Amount :</th>
													<td class="value" id="damageAmount"></td>
												</tr>
												<tr class="row">
													<th class="key">Is Claim :</th>
													<td class="value" id="isClaim"></td>
												</tr>
												<tr class="row">
													<th class="key">Claim Remark :</th>
													<td class="value" id="claimRemark"></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Service/Quotation Details</h3>
									</div>
									<div class="box-body no-padding">
									<input type="hidden" id="serviceId">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Service Number :</th>
													<td class="value" id="serviceNumber"></td>
												</tr>
												
											</tbody>
										</table>
									</div>
								</div>
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Payment Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Payment Date :</th>
													<td class="value" id="pDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Payment Amount :</th>
													<td class="value" id="pAmount"></td>
												</tr>
												<tr class="row">
													<th class="key">Payment Remark :</th>
													<td class="value" id="pRemark"></td>
												</tr>
												 <c:if test="${configuration.accidentAdditionalFields}">
													<tr class="row">
													<th class="key">Query Remark :</th>
													<td class="value" id="qRemark"></td>
												</tr>
												</c:if>
												
											</tbody>
										</table>
									</div>
								</div>
								
							</div>
							
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Accident With Vehicle Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Vehicle Number :</th>
													<td class="value" id="otherVehicle"></td>
												</tr>
												<tr class="row">
													<th class="key">Driver  :</th>
													<td class="value" id="accidentWithDriver"></td>
												</tr>
												<tr class="row">
													<th class="key">Driver Mobile :</th>
													<td class="value" id="accidentWithDriverrMobile">></td>
												</tr>
												<tr class="row">
													<th class="key">Owner :</th>
													<td class="value" id="accidentWithOwner"></td>
												</tr>
												<tr class="row">
													<th class="key">Other Details :</th>
													<td class="value" id=""></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<c:if test="${configuration.accidentClaim}">
								<div class="box box-success" id="accidentTypeDetailsDiv">
									<div class="box-header">
										<h3 class="box-title">Accident Type Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Accident Type :</th>
													<td class="value" id="showAccidentType"></td>
												</tr>
												<tr class="row">
													<th class="key">Nature Of Own Damage:</th>
													<td class="value" id="showNatureOfOwnDamage"></td>
												</tr>
												<tr class="row">
													<th class="key">Approx Damage Of Own :</th>
													<td class="value" id="showApproxOwnDamgeCost"></td>
												</tr>
												<tr class="row">
													<th class="key">Serveyor Company :</th>
													<td class="value" id="showNatureOfTPDamage"></td>
												</tr>
												<tr class="row">
													<th class="key">Nature Of TP Damage :</th>
													<td class="value" id="showApproxTPDamgeCost"></td>
												</tr>
												<tr class="row">
													<th class="key">Number Of Accident Person:</th>
													<td><a  class="value" id="showNumberOfAccidentPerson" onclick="claimProcess();"></a></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								</c:if>
<%-- 								<c:if test="${configuration.accidentClaim}"> --%>
<!-- 								<div class="box box-success" id="accidentTypeDetailsDiv"> -->
<!-- 									<div class="box-header"> -->
<!-- 										<h3 class="box-title">Spot Survey Details</h3> -->
<!-- 									</div> -->
<!-- 									<div class="box-body no-padding"> -->
<!-- 										<table class="table table-striped"> -->
<!-- 											<tbody> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Name :</th> -->
<!-- 													<td class="value" id="showSpotSurveyorName"></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Mobile:</th> -->
<!-- 													<td class="value" id="showSpotSurveyorMobile"></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Company:</th> -->
<!-- 													<td class="value" id="showSpotSurveyorCompany"></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Date :</th> -->
<!-- 													<td ><a  class="value" id="showSpotSurveyorDate"></a> <a  class="value" id="showSpotSurveyorTime"></a></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Remark :</th> -->
<!-- 													<td class="value" id="showSpotSurveyorRemark"></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Completion Date:</th> -->
<!-- 													<td><a  class="value" id="showSpotSUrveyorCompletionDate"></a> <a  class="value" id="showSpotSUrveyorCompletionTime"></a></td> -->
<!-- 												</tr> -->
<!-- 												<tr class="row"> -->
<!-- 													<th class="key">Spot Surveyor Completion Remark:</th> -->
<!-- 													<td class="value" id="showSpotSUrveyorCompletionRemark"></td> -->
<!-- 												</tr> -->
<!-- 											</tbody> -->
<!-- 										</table> -->
<!-- 									</div> -->
<!-- 								</div> -->
<%-- 								</c:if> --%>
								<div class="box box-success" style="display: none;" id="sDetailsDiv">
									<div class="box-header">
										<h3 class="box-title">
											<c:if test="${configuration.accidentSpotservay}">
										Spot Serveyor Details
										</c:if>
										<c:if test="${!configuration.accidentSpotservay}">
										 Servey Details
										 </c:if>
										
										</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Serveyor Name :</th>
													<td class="value" id="sName"></td>
												</tr>
												<tr class="row">
													<th class="key">Serveyor Mobile :</th>
													<td class="value" id="sMobile"></td>
												</tr>
												<tr class="row">
													<th class="key">Serveyor Company :</th>
													<td class="value" id="sCompany"></td>
												</tr>
												<c:if test="${configuration.accidentAdditionalFields}">
												<tr class="row">
													<th class="key">Spot Surveyor Inform Date:</th>
													<td class="value" id="sInformDate"></td>
												</tr>
												</c:if>
												<tr class="row">
													<th class="key">Remark :</th>
													<td class="value" id="sRemark"></td>
												</tr>
												<tr class="row">
													<th class="key">Completion Date :</th>
													<td class="value" id="sCompletionDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Completion Remark :</th>
													<td class="value" id="sCompletionRemark"></td>
												</tr>
											<c:if test="${configuration.accidentClaim}">
												<tr class="row">
													<th class="key">Final Surveyor Name :</th>
													<td class="value" id="sFinalSuveyorName"></td>
												</tr>
												<tr class="row">
													<th class="key">Final Surveyor Mobile:</th>
													<td class="value" id="sFinalSuveyorMobile"></td>
												</tr>
												<tr class="row">
													<th class="key">Final Surveyor Email:</th>
													<td class="value" id="sFinalSuveyorEmail"></td>
												</tr>
												<tr class="row">
													<th class="key">Final Surveyor Claim Num:</th>
													<td class="value" id="sFinalSuveyorClaimNum"></td>
												</tr>
												
											</c:if>	
												<tr class="row">
													<th class="key">Final Damage Servey :</th>
													<td class="value" id="finalSCompletionDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Final Damage Servey Remark :</th>
													<td class="value" id="finalSCompletionRemark"></td>
												</tr>
												
												<c:if test="${configuration.accidentClaim}">
													<tr class="row">
														<th class="key">Keep Open Date :</th>
														<td class="value" id="sKeepOpenDate"></td>
													</tr>
												</c:if>
												<c:if test="${configuration.accidentClaim}">
													<tr class="row">
														<th class="key">Keep Open Remark :</th>
														<td class="value" id="sKeepOpenRemark"></td>
													</tr>
												</c:if>
												<c:if test="${configuration.accidentClaim}">
													<tr class="row">
														<th class="key">Insurance submited Date :</th>
														<td class="value" id="sInsuranceSubmitDate"></td>
													</tr>
												</c:if>
												<c:if test="${configuration.accidentClaim}">
													<tr class="row">
														<th class="key">Call Fnal Survey Date :</th>
														<td class="value" id="sCallFinalSurveyorDate"></td>
													</tr>
												</c:if>
												
												<tr class="row">
													<th class="key">Quotation Approve Date :</th>
													<td class="value" id="sApprovalDate"></td>
												</tr>
												<tr class="row">
													<th class="key">Quotation Approve Remark :</th>
													<td class="value" id="sApprovalRemark"></td>
												</tr>
												<tr class="row">
													<th class="key">Final Inspection Date :</th>
													<td class="value" id="sInspectionDate"></td>
												</tr>
												 <c:if test="${configuration.accidentAdditionalFields}">	
												<tr class="row">
													<th class="key">Salvage Amount :</th>
													<td class="value" id="salvageAmountKey"></td>
												</tr></c:if>
												<tr class="row">
													<th class="key">Final Inspection Remark :</th>
													<td class="value" id="sInspectionRemark"></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
			
			<div class="col-md-2 col-sm-2 col-xs-12" id="allLink" style="width: 240px;">
				<sec:authorize access="hasAuthority('ADD_VEHICLE_ACCIDENT')">
				<ul class="nav nav-list">
					<li>
						<a href="javascript:void(0)" onclick="getRenewalAndVehicleDocuments();">Vehicle Renewal/Document <span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="getDriverDocuments();">Driver Document <span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="openUpdateIncedentDocumentPopUp();"><span id="incidentDocCount" class="pull-right badge bg-aqua"></span> Upload Incident/Estimate Document</a>
					</li>
					
					<li>
						<a href="javascript:void(0)" onclick="addAdvancepopup();"><i class="fa fa-plus"></i> Add Advance <span id="advanceCount" class="pull-right badge bg-aqua"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" onclick="addExpensepopup();"><i class="fa fa-plus"></i> Add Other Expenses <span id="expenseCount" class="pull-right badge bg-aqua"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link1" ><i style="display: none;" id="stepCheck1" class="fa fa-check"></i><span style="color: red;" id="step1">Step 1 : </span> <i class="fa fa-plus"></i> Incident Created <span class="count muted text-muted pull-right"></span></a>
					</li>
					<li id="claimDiv" style="display: none;">
					<a href="javascript:void(0)" id="link1_1" onclick="claimProcess();"><i style="display: none;" id="stepCheck1_1" class="fa fa-check"></i><span style="color: red;" id="step1_1">Step 1.1 : </span> <i class="fa fa-plus"></i> Update Accident Type Details <span class="count muted text-muted pull-right"></span></a>
					</li>
<!-- 					<li id="spotSurveyDiv" style="display: none;"> -->
<!-- 					<a href="#" id="link1_2" onclick="openSpotSurveyorPopup();"><i style="display: none;" id="stepCheck1_2" class="fa fa-check"></i><span style="color: red;" id="step1_2">Step 1.2 : </span> <i class="fa fa-plus"></i> Update Spot Surveyor Details <span class="count muted text-muted pull-right"></span></a> -->
<!-- 					</li> -->
<!-- 					<li id="spotSurveyComDiv" style="display: none;"> -->
<!-- 					<a href="#" id="link1_3" onclick="openSpotSurveyorCompletionPopup();"><i style="display: none;" id="stepCheck1_3" class="fa fa-check"></i><span style="color: red;" id="step1_3">Step 1.3 : </span> <i class="fa fa-plus"></i> Spot Surveyor Completion Details <span class="count muted text-muted pull-right"></span></a> -->
<!-- 					</li> -->
					<li>
						<a href="javascript:void(0)" id="link2" onclick="openUpdateServeryorPopup();"><i style="display: none;" id="stepCheck2" class="fa fa-check"></i><span style="color: red;" id="step2">Step 2 : </span> <i class="fa fa-plus"></i> 
						<c:if test="${configuration.accidentSpotservay}">
						Update Spot Serveyor Details 
						</c:if>
						<c:if test="${!configuration.accidentSpotservay}">
						Update Serveyor Details 
						</c:if>
						<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link3" onclick="openServeyCompletePopup();"><i style="display: none;" id="stepCheck3" class="fa fa-check"></i><span style="color: red;" id="step3">Step 3 : </span> <i class="fa fa-plus"></i> 
						<c:if test="${configuration.accidentSpotservay}">
						Spot Serveyor Completion Details 
						</c:if>
						<c:if test="${!configuration.accidentSpotservay}">
						Serveyor Completion Details
						</c:if>
						<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link4" onclick="openCreateQuotation();"><i style="display: none;" id="stepCheck4" class="fa fa-check"></i><span style="color: red;" id="step4">Step 4 : </span> <i class="fa fa-plus"></i> Create Quotation <span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link5" onclick="openFinalDamageServeyPopup();"><i style="display: none;" id="stepCheck5" class="fa fa-check"></i><span style="color: red;" id="step5">Step 5 : </span><i class="fa fa-plus"></i> Final Servey for Damage <span class="count muted text-muted pull-right"></span></a>
					</li>
					<li id="keepOpenDiv" style="display: none;">
						<a href="javascript:void(0)" id="link10" onclick="keepOpenPopup();"><i style="display: none;" id="stepCheck10" class="fa fa-check"></i><span style="color: red;" id="step10">Step 5.1 : </span><i class="fa fa-plus"></i> Keep Open<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li id="beforeEsimateDiv" style="display: none;">
						<a href="javascript:void(0)" id="link11" onclick="beforeEstimatePopup();"><i style="display: none;" id="stepCheck11" class="fa fa-check"></i><span style="color: red;" id="step11">Step 5.2 : </span><i class="fa fa-plus"></i> Before Approve<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link6" onclick="approveQuotationPopup();"><i style="display: none;" id="stepCheck6" class="fa fa-check"></i><span style="color: red;" id="step6">Step 6 : </span><i class="fa fa-plus"></i> Approve Quotation<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link7"><i style="display: none;" id="stepCheck7" class="fa fa-check"></i><span style="color: red;" id="step7">Step 7 : </span><i class="fa fa-plus"></i>Service Completed<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link8" onclick="addfinalServeyDetails();"><i style="display: none;" id="stepCheck8" class="fa fa-check"></i><span style="color: red;" id="step8">Step 8 : </span><i class="fa fa-plus"></i>Update Final Inspection<span class="count muted text-muted pull-right"></span></a>
					</li>
					<li>
						<a href="javascript:void(0)" id="link9" onclick="openPaymentDetailsPopup();"><i style="display: none;" id="stepCheck9" class="fa fa-check"></i><span style="color: red;" id="step9">Step 9 : </span><i class="fa fa-plus"></i>Update Payment Details<span class="count muted text-muted pull-right"></span></a>
					</li>
					
				</ul>
				</sec:authorize>
			</div>
		</div>
		
		<div class="modal fade" id="vehicledocumentmodal" role="dialog">
			<div class="modal-dialog" style="width:900px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Vehicle Document</h4>
						</div>
						<div class="modal-body">
						<h4 id="dataTableHeader" class="modal-title">Vehicle Document</h4>
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Document Type</th>
										<th>Download</th>
									</tr>
								</thead>
								<tbody id="tableBody">
									
								</tbody>
							</table>	
								
						</div>
							<div class="modal-body">
							<h4 id="dataTableHeader1" class="modal-title">Renewal Document</h4>
							<table id="dataTable1" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Document Type</th>
										<th>RR number</th>
										<th>Due Date</th>
										<th>Download</th>
									</tr>
								</thead>
								<tbody id="tableBody1">
									
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
		
		<div class="modal fade" id="driverdocumentmodal" role="dialog">
			<div class="modal-dialog" style="width:900px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Driver Document</h4>
						</div>
						<div class="modal-body">
							
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Document Type</th>
										<th>Download</th>
									</tr>
								</thead>
								<tbody id="driverTableBody">
									
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
		
		<div class="modal fade" id="driverdocumentmodal" role="dialog">
			<div class="modal-dialog" style="width:900px;">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Driver Document</h4>
						</div>
						<div class="modal-body">
							
							<table id="dataTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Document Type</th>
										<th>Download</th>
									</tr>
								</thead>
								<tbody id="driverTableBody">
									
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
		
		<div class="modal fade" id="uploadIncidentFile" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Upload Incident Document</h4>
						</div>
						<div class="modal-body">
						
						<table id="docTable"  style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<c:if test="${configuration.accidentClaim}">
										<th>Step</th>
										</c:if>
										<th>Document Type</th>
										<th>File Name</th>
										<th>Upload Time</th>
										<c:if test="${configuration.accidentClaim}">
										</c:if>
										<th>Download</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="docTableBody">
									
								</tbody>
							</table>
									<c:if test="${configuration.accidentClaim}">
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Document Step <abbr title="required">*</abbr>
									</label>
										<div class="col-md-6">
											<select class="select form-text" id="documentStatusId" onchange="changeAccidentType();resetAccidentTypeDetails();" >
		                             			<option value="0">Please Select Step</option>
		                             			<option value="1">Accident Photos</option>
<!-- 		                             			<option value="2">Accident Type</option> -->
		                             			<option value="3">Spot Survey Completion</option>
		                             			<option value="5">Preliminary Estimate</option>
		                             			<option value="4">Final Survey</option>
		                             			<option value="6">Kept Open Photos</option>
		                             			<option value="7">Supplementary Estimate</option>
		                             			<option value="8">Update Final Survey Photo</option>
		                             	</select> 
										</div>
									
								</div>
							</div>	<br/>
									</c:if>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Document Type <abbr title="required">*</abbr>
									</label>
									
									<c:if test="${configuration.accidentClaim}">
										<div class="col-md-6">
											<select class="select" id="documentTypeId" style="width:100%"></select>
										</div>
									</c:if>
									<c:if test="${!configuration.accidentClaim}">
										<div class="col-md-6">
											<input required="required" type="text" class="form-text" name="documentType" id="documentType" multiple="multiple"></input>
										</div>
									</c:if>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Document <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input required="required" type="file" name="input-file-preview" id="renewalFile" multiple="multiple"></input>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Upload" id="btnSubmit" onclick="saveIncidentDocument();" class="btn btn-success" />
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
		
		<div class="modal fade" id="serveryourDetails" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Update Serveyour Details</h4>
						</div>
						<div class="modal-body">
						<div class="row" id="previousServey" style="display: none;">
								<div class="form-group">
									<label class="col-md-3">Previous Servey :  <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6" id="previousCount">
										
									</div>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Serveyor Name :  <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="serveyorName" />
									</div>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Serveyor Mobile : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text"  id="serveyorMobile" onblur="checkForOldServeyDetails(this);" />
									</div>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Serveyor Company :<abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="serveyorCompany"/>
									</div>
								</div>
							</div>	<br/>
							<c:if test="${configuration.accidentAdditionalFields}">
							<div class="row">
								<div class="form-group">
											<label class="col-md-3" for="woStartDate">Spot Surveyor Inform Date 
												 <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="LeaveDate">
													<input type="text" class="form-text" name="start_date" readonly="readonly"
														
														id="surveyorDate" required="required" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woStartDateIcon" class=""></span>
												<div id="woStartDateErrorMsg" class="text-danger"></div>
											</div>
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" 
																name="start_time" id="surveyorTime" required="required" readonly="readonly"> <span
																class="input-group-addon" > <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
												</div>	
										</div><br/>		
									</c:if>						
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Remark : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="remark"/>
									</div>
								</div>
							</div>	<br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveServeyorDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="serveyCompletePopUp" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Servey Completion Details</h4>
						</div>
						<div class="modal-body">
						
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Completion Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate">
													<input type="text" class="form-text" name="completionDate"
														id="completionDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
								<div class="row">
											<label class="L-size control-label">Completion Remark :</label>
											<div class="I-size">
												<input class="form-text" id="completionRemark"
													required="required" name="completionRemark" type="text">
											</div>
										</div>
							<br/>
							
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveServeyCompletionDetails();" class="btn btn-success" />
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
		<div class="modal fade" id="createQuotationModal" role="dialog">
			<div class="modal-dialog" style="width:450px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Select Type For Quotation</h4>
						</div>
						<div class="modal-body">
							<sec:authorize access="hasAuthority('ADD_VEHICLE_ACCIDENT')">
								<c:if test="${dealerServicePermission}">
									<a class="btn btn-primary "
										href="#" id="createDSE">
										<span class="fa fa-plus"> </span> Create DSE
									</a>
								</c:if>
								<c:if test="${!dealerServicePermission}">
									<a class="btn btn-primary "
										href="#" id="createSE">
										<span class="fa fa-plus"> </span> Create Service Entry
									</a>
								</c:if>
									</sec:authorize>
									<sec:authorize access="hasAuthority('ADD_VEHICLE_ACCIDENT')">
										<a style="margin-left: 50px;" class="btn btn-success "
											href="#" id="createWO">
											<span class="fa fa-plus"> </span> Create WorkOrder
										</a>
									</sec:authorize>
							
						</div>
						
					</form>	
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="finalServeyDamage" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Final Servey For Damage</h4>
						</div>
						<div class="modal-body">
						<div class="row">
							<label class="L-size control-label">Final Surveyor Name :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input class="form-text" id="finalServeyorName"
									required="required" name="finalServeyorName" type="text">
							</div>
						</div><br>
						<div class="row">
							<label class="L-size control-label">Final Surveyor Mobile  :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input class="form-text" id="finalServeyorMobile"
									required="required" name="finalServeyorMobile" onblur="return isMobileNum(this);" type="text">
							</div>
						</div><br>
						<div class="row">
							<label class="L-size control-label">Final Surveyor Email:</label>
							<div class="I-size">
								<input class="form-text" id="finalServeyorEmail"
									required="required" name="finalServeyorEmail" type="text">
							</div>
						</div><br>
						<!-- <div class="row">
							<label class="L-size control-label">Final Surveyor Dept :</label>
							<div class="I-size">
								<input class="form-text" id="finalServeyorDept"
									required="required" name="finalServeyorDept" type="text">
							</div>
						</div> -->
						<div class="row">
							<label class="L-size control-label">Final Surveyor Claim Num  :</label>
							<div class="I-size">
								<input class="form-text" id="finalServeyorClaimNum"
									required="required" name="finalServeyorClaimNum" type="text">
							</div>
						</div><br>
						
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Completion Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="StartDate1">
													<input type="text" class="form-text" name="finalSDate"
														id="finalSDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
					
								<div class="row">
											<label class="L-size control-label">Completion Remark :</label>
											<div class="I-size">
												<input class="form-text" id="finalSRemark"
													required="required" name="finalSRemark" type="text">
											</div>
										</div>
							<br/>
							
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveFinalServeyForDamage();" class="btn btn-success" />
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
		
		<div class="modal fade" id="approveQuotation" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Approve Quotation</h4>
						</div>
						<div class="modal-body">
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Approval Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="paymentDate">
													<input type="text" class="form-text" name="approvalDate"
														id="approvalDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
								<div class="row">
											<label class="L-size control-label">Approval Remark :</label>
											<div class="I-size">
												<input class="form-text" id="approvalRemark"
													required="required" name="approvalRemark" type="text">
											</div>
										</div>
							<br/>
							
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveQuotationApprovalDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="advancePopup" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="advanceFile">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Add Advance</h4>
						</div>
						<div class="modal-body">
						<table id="advanceTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Advance Date</th>
										<th>Advance Amount</th>
										<th>Remark</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="advanceTableBody">
									
								</tbody>
							</table>
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Advance Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="TripStartDate">
													<input type="text" class="form-text" name="advanceDate"
														id="advanceDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
								<div class="row">
											<label class="L-size control-label">Advance Amount :</label>
											<div class="I-size">
												<input class="form-text" id="advanceAmount" onkeypress="return isNumberKey(event,this);"
													required="required" name="advanceAmount" type="number">
											</div>
								</div><br/>
							<div class="row">
											<label class="L-size control-label">Advance Remark :</label>
											<div class="I-size">
												<input class="form-text" id="advanceRemark"
													required="required" name="advanceRemark" type="text">
											</div>
								</div><br/>
							
						</div>
						<div class="modal-footer">
							<input type="button" value="Save" id="btnSubmit" onclick="saveAdvanceDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="expensePopup" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="expenseFileForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Add Expense</h4>
						</div>
						<div class="modal-body">
						<table id="expenseTable" style="width: 100%;" class="table-responsive table">
								<thead>
									<tr>
										<th>Expense Type</th>
										<th>Expense Date</th>
										<th>Expense Amount</th>
										<th>Remark</th>
										<th>Download</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody id="expenseTableBody">
									
								</tbody>
							</table>
							<div class="row">
											<label class="L-size control-label">Expense Type :</label>
											<div class="I-size">
												<input class="form-text" id="expenseType"
													required="required" name="expenseType" type="text">
											</div>
								</div><br/>
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="expenseDate">Expense Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="renewal_to">
													<input type="text" class="form-text" name="expenseDate"
														id="expenseDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
								<div class="row">
											<label class="L-size control-label">Expense Amount :</label>
											<div class="I-size">
												<input class="form-text" id="expenseAmount" onkeypress="return isNumberKey(event,this);"
													required="required" name="expenseAmount" type="text">
											</div>
								</div><br/>
							<div class="row">
											<label class="L-size control-label">Expense Remark :</label>
											<div class="I-size">
												<input class="form-text" id="expenseRemark"
													required="required" name="expenseRemark" type="text">
											</div>
								</div><br/>
								<div class="row">
											<label class="L-size control-label">Document :</label>
											<div class="I-size">
												<input required="required" type="file" name="input-file-preview" id="expenseFile" multiple="multiple"></input>
											</div>
								</div><br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Save" id="btnSubmit" onclick="saveExpenseDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="finalServeyDetails" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="finalInspection">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Final Inspection Details</h4>
						</div>
						<div class="modal-body">
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Final Inspection Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="renewal_from">
													<input type="text" class="form-text" name="inspectionDate"
														id="inspectionDate" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
								<c:if test="${configuration.accidentAdditionalFields}">
								<div class="row">
											<label class="L-size control-label">Salvage Amount :</label>
											<div class="I-size">
												<input class="form-text" id="salvageAmount" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
													required="required" name="salvageAmount" type="text"/>
											</div>
								</div><br>
								</c:if>
								<div class="row">
											<label class="L-size control-label">Final Inspection Remark :</label>
											<div class="I-size">
												
													<textarea class="form-text" id="inspectionRemark"
																	name="inspectionRemark" rows="4" maxlength="1000">	
																</textarea>
											</div>
										</div>
							<br/>
							
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveFinalInspectionDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="paymentDetails" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="paymentDetailsForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Payment Details</h4>
						</div>
						<div class="modal-body">
							<div class="row" id="grpfuelDate" class="form-group">
											<label class="L-size control-label" for="fuelDate">Payment Date
												<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<div class="input-group input-append date" id="dateofbirth">
													<input type="text" class="form-text" name="paymentDate"
														id="paymentDateStr" required="required"
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
											</div>
							</div>	<br/>
							<div class="row">
											<label class="L-size control-label">Payment Amount :</label>
											<div class="I-size">
												<input class="form-text" id="paymentAmount"  onkeypress="return isNumberKey(event,this);"
													required="required" name="paymentAmount" type="number">
											</div>
								</div><br/>
								<div class="row">
											<label class="L-size control-label">Payment Remark :</label>
											<div class="I-size">
												
													<textarea class="form-text" id="paymentRemark"
																	name="paymentRemark" rows="4" maxlength="1000">	
																</textarea>
											</div>
										</div>
							<br/>
							 <c:if test="${configuration.accidentAdditionalFields}">
							<div class="row">
							<div class="I-size">
								<label class="L-size control-label" for="queryCheck">Query : </label>
								<input class="form-check-input" type="checkbox" id="queryCheck"></div>
							</div>
							<div class="row">
								<label class="L-size control-label">Query Remark :</label>
								<div class="I-size">
									<input class="form-text" id="queryRemark" name="queryRemark"
										 maxlength="1000"/>
								</div>
							</div>
							</c:if>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="savePaymentDetails();" class="btn btn-success" />
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
		<div class="modal fade" id="accidentTypeDetailsModal" role="dialog">
			<div class="modal-dialog" style="width:1180px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Accident Type Details </h4>
						</div>
						<div class="modal-body">
						<div class="row">
						<div class="col-md-5">
							 <!-- <div class="form-group column" id="accidentTypeDiv" > -->
		                            <label  class="L-size control-label">Accident Type: </label>
		                            <div  class="I-size">
		                             	<select class="select form-text" id="accidentTypeId" onchange="changeAccidentType();resetAccidentTypeDetails();" >
		                             		<option value="1">Own Damage</option>
		                             		<option value="2">Third Party Injury And Death</option>
		                             		<option value="3">Third Party Property Damage</option>
		                             		<option value="4">OD+TPIAD</option>
		                             		<option value="5">OD+TPPD</option>
		                             		<option value="6">OD+TPIAD+TPPD</option>
		                             	</select> 
		                            </div>
		                         <!-- </div> -->
		                    </div>
							</div><br/>
							<input type="hidden" id="vehicleAccidentTypeDetailsId">
	                         <div class="row" id="ODRow"  style="display: none;">
	                         	<div class="col-md-5">
		                          	<label class="L-size control-label">Nature Of Own Damage:</label>
									<div class="I-size">
	                             	 	<input class="form-text"  type="text" id="natureOfOwnDamage">
	                               	</div>
	                        	</div>
	                        	<div class="col-md-5">
		                          	<label class="L-size control-label">Approx Own Damage Cost:</label>
									<div class="I-size">
	                             	 	<input class="form-text"  type="number" id="approxOwnDamgeCost" onkeypress="return isNumberKeyWithDecimal(event,this.id);">
	                               	</div>
	                        	</div>
	                         	
	                         </div>
	                         <br>
	                         <div class="row" id="TPDRow"  style="display: none;">
	                         	<div class="col-md-5">
		                          	<label class="L-size control-label">Nature Of TP Damage:</label>
									<div class="I-size">
	                             	 	<input class="form-text"  type="text" id="natureOfTPDamage">
	                               	</div>
	                        	</div>
	                        	<div class="col-md-5">
		                          	<label class="L-size control-label">Approx TP Damage Cost:</label>
									<div class="I-size">
	                             	 	<input class="form-text"  type="number" id="approxTPDamgeCost" onkeypress="return isNumberKeyWithDecimal(event,this.id);">
	                               	</div>
	                        	</div>
	                         	
	                         </div>
	                         <br>
							<div class="row" id="TPIRow" style="display: none;">
								<div>
									<table class="table">
									<div>
									<h3 class="col-md-9" style="text-align: center;" >Accident Person Details</h3>
									<button type="button"  class="btn btn-info addMoreLabourButton col-md-1" ><span class="fa fa-plus"></span></button>
									</div>
										<thead>
											<tr>
												<th class="fit ar">Type</th>
												<th class="fit ar">Name</th>
												<th class="fit ar">Age</th>
												<th class="fit ar">Status</th>
												<th class="fit ar">Description</th>
												<th class="fit ar">Action</th>
											</tr>
										</thead>
										<tbody id="vehicleAccidentPersonTable">

										</tbody>

									</table>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveUpdateAccidentTypeDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="spotSurveryourDetails" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Update Spot Surveyor Details</h4>
						</div>
						<input type="hidden" id="spotSurveyorDetailsId">
						<input type="hidden" id="spotSurveyorCompletionDateOnSave">
						<input type="hidden" id="spotSurveyorCompletionTimeOnSave">
						<input type="hidden" id="spotSurveyorCompletionRemarkOnSave">
						<div class="modal-body">
						<div class="row" id="previousServey" style="display: none;">
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Spot Surveyor Name :  <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="spotSurveyorName" />
									</div>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Spot Surveyor Mobile : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text"  id="spotSurveyorMobile" onblur="checkForOldServeyDetails(this);" />
									</div>
								</div>
							</div>	<br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Spot Surveyor Company :<abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="spotSurveyorCompany"/>
									</div>
								</div>
							</div>	<br/>
							
							<div class="row">
								<div class="form-group">
											<label class="col-md-3" for="woStartDate">Spot Surveyor Inform Date 
												 <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="LeaveDate">
													<input type="text" class="form-text" name="start_date" readonly="readonly"
														
														id="spotSurveyorDate" required="required" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woStartDateIcon" class=""></span>
												<div id="woStartDateErrorMsg" class="text-danger"></div>
											</div>
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" 
																name="start_time" id="spotSurveyorTime" required="required" readonly="readonly"> <span
																class="input-group-addon" onchange="getVehicleGPSDataAtTime();"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
												</div>	
										</div><br/>
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Remark : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="spotSurveyorRemark"/>
									</div>
								</div>
							</div>	<br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveSpotSurveyorDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="spotSurveryourCompletionDetails" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Update Spot Surveyor Details</h4>
						</div>
						<input type="hidden" id="spotSurveyorDetailsId">
						<input type="hidden" id="spotSurveyorNameOnUpdate">
						<input type="hidden" id="spotSurveyorMobileOnUpdate">
						<input type="hidden" id="spotSurveyorCompanyOnUpdate">
						<input type="hidden" id="spotSurveyorDateOnUpdate">
						<input type="hidden" id="spotSurveyorTimeOnUpdate">
						<input type="hidden" id="spotSurveyorRemarkOnUpdate">
						<div class="modal-body">
						<div class="row" id="previousServey" style="display: none;">
							</div>	<br/>
							
							<div class="row">
							<div class="form-group">
											<label class="col-md-3" for="woStartDate">Spot Surveyor Completion Date 
												 <abbr title="required">*</abbr>
											</label>
											<div class="col-md-3">
												<div class="input-group input-append date" id="damageReceiveDate">
													<input type="text" class="form-text" name="start_date" readonly="readonly"
														
														id="spotSurveyorCompletionDate" required="required" 
														data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
														class="input-group-addon add-on"> <span
														class="fa fa-calendar"></span>
													</span>
												</div>
												<span id="woStartDateIcon" class=""></span>
												<div id="woStartDateErrorMsg" class="text-danger"></div>
											</div>
												<div class="L-size">
														<div class="input-group clockpicker">
															<input type="text" class="form-text" 
																name="start_time" id="spotSurveyorCompletionTime" required="required" readonly="readonly"> <span
																class="input-group-addon" onchange="getVehicleGPSDataAtTime();"> <i
																class="fa fa-clock-o" aria-hidden="true"></i>
															</span>
														</div>
												</div>
												</div>	
										</div><br/>
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Remark : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="spotSurveyorCompletionRemark"/>
									</div>
								</div>
							</div>	<br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="updateSpotSurveyorCompletionDetails();" class="btn btn-success" />
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
		
		<div class="modal fade" id="keepOpenModal" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Keep Open Details</h4>
						</div>
						<div class="modal-body">
						<div class="row" id="previousServey" style="display: none;">
							</div>	<br/>
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3" for="woStartDate">Keep Open Date 
										 <abbr title="required">*</abbr>
									</label>
									<div class="col-md-3">
										<div class="input-group input-append date" id="keepOpenDate1">
											<input type="text" class="form-text" name="start_date" readonly="readonly"
												
												id="keepOpenDate" required="required" 
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
												class="input-group-addon add-on"> <span
												class="fa fa-calendar"></span>
											</span>
										</div>
										<span id="woStartDateIcon" class=""></span>
										<div id="woStartDateErrorMsg" class="text-danger"></div>
									</div>
								</div>	
							</div><br/>
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Remark : <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="text" class="form-text" id="keepOpenRemark"/>
									</div>
								</div>
							</div>	<br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveKeepOpenDetails();" class="btn btn-success" />
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
		<div class="modal fade" id="beforeEstimateModal" role="dialog">
			<div class="modal-dialog" style="width:980px;">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Before Approval Details</h4>
						</div>
						<div class="modal-body">
						<div class="row" id="previousServey" style="display: none;">
							</div>	<br/>
							
							<div class="row">
								<div class="form-group">
									<label class="col-md-3" for="woStartDate">Insurance Submit Date 
										 <abbr title="required">*</abbr>
									</label>
									<div class="col-md-3">
										<div class="input-group input-append date" id="insuranceSubmitDate1">
											<input type="text" class="form-text" name="start_date" readonly="readonly"
												
												id="insuranceSubmitDate" required="required" 
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
												class="input-group-addon add-on"> <span
												class="fa fa-calendar"></span>
											</span>
										</div>
										<span id="woStartDateIcon" class=""></span>
										<div id="woStartDateErrorMsg" class="text-danger"></div>
									</div>
								</div>	
							</div><br/>
							<div class="row">
								<div class="form-group">
									<label class="col-md-3" for="woStartDate">Call Final Surveyor Date 
										 <abbr title="required">*</abbr>
									</label>
									<div class="col-md-3">
										<div class="input-group input-append date" id="callFinalSurveyorDate1">
											<input type="text" class="form-text" name="start_date" readonly="readonly"
												
												id="callFinalSurveyorDate" required="required" 
												data-inputmask="'alias': 'dd-mm-yyyy'" data-mask="" /> <span
												class="input-group-addon add-on"> <span
												class="fa fa-calendar"></span>
											</span>
										</div>
										<span id="woStartDateIcon" class=""></span>
										<div id="woStartDateErrorMsg" class="text-danger"></div>
									</div>
								</div>	
							</div><br/>
						</div>
						<div class="modal-footer">
							<input type="button" value="Update" id="btnSubmit" onclick="saveBeforeEstimate();" class="btn btn-success" />
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
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <span id="createdBy"></span> </small> |
			<small class="text-muted"><b>Created date: </b><span id="createdOnStr"></span> </small> | 
			<small class="text-muted"><b>Last updated by :</b><span id="lastUpdatedBy"></span>  </small> |
			<small class="text-muted"><b>Last updated date:</b><span id="lastUpdatedOnStr"></span>  </small>
		</div>
	</section>
</div>
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
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/accident/ShowVehicleAccidentDetails.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>			
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/accident/validateVehicleAccident.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/bootstrap-clockpicker.min.js" />"></script>	
		<script>
		$(function() {
			$(document).ready(function() {
				$('.clockpicker').clockpicker({
					placement: 'bottom',
					align: 'right',
					autoclose: true
				});
				
			});
			
		});
		</script>]
		<script>
			$(function() {
				$('#reservation').daterangepicker();
			});
		</script>
		
		
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/accident/VehicleAccidentUtility.js"></script>
