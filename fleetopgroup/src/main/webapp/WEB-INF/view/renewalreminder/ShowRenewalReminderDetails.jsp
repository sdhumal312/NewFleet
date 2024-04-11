<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> /
						<a href="<c:url value="/viewRenewalReminder.in"/>"> RenewalReminder</a> /
						 <small>Show Renewal Reminder Detail</small>
					</div>
					<div class="col-md-off-5">
					
						<div class="col-md-2">
							<div class="input-group">
								<span class="input-group-addon"> <span aria-hidden="true">RR-</span></span>
								<input class="form-text" id="searchByNumber"
									name="Search" type="number" min="1" required="required"
									placeholder="ID eg: 2323"> <span class="input-group-btn">
									<button type="submit" name="search" id="search-btn" onclick="return serachRenewalReminderByNumber();" class="btn btn-success btn-sm">
										<i class="fa fa-search"></i>
									</button>
								</span>
							</div>
						</div>

						<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<c:if test="${!renewalReminder.ignored}">
							<a class="btn btn-info btn-sm"
								href="<c:url value="/renewalReminderAjaxAdd.in?vid=${renewalReminder.vid}&renewalSubTypeId=0"/>"> <span
								class="fa fa-plus"></span> Add Renewal
							</a>
							</c:if>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('EDIT_RENEWAL_PERIOD')">
							<c:if test="${!renewalReminder.ignored}">
									<button type="button" class="btn btn-warning btn-sm"
										data-toggle="modal" data-target="#editrenewalPeriod"
										data-whatever="@mdo">Edit Renewal Period</button>
										</c:if>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('EDIT_RENEWAL')">
						<c:if test="${!renewalReminder.ignored}">
							<a class="btn btn-success btn-sm"
								href="<c:url value="/renewalReminderAjaxRevise.in?renewalId=${renewalReminder.renewal_id}"/>">
								<i class="fa fa-plus"></i> Revise Renewal
							</a>
							</c:if>
						</sec:authorize>
						
						<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<button type="button" class="btn btn-warning btn-sm"
							data-toggle="modal" data-target="#uploadEditFile"
							data-whatever="@mdo">Upload/Edit File</button>
						</sec:authorize>
						<c:if test="${renewalReminder.renewal_staus_id == 1 }">
							<sec:authorize access="hasAuthority('CREATE_RR_APPROVAL')">
								<button type="button" class="btn btn-info btn-sm"
								data-toggle="modal" data-target="#createApprovalModal"
								data-whatever="@mdo">Create Approval</button>
							</sec:authorize>
						</c:if>
						<div class="pull-right">
							<a class="btn btn-danger" href="<c:url value="/viewRenewalReminder.in"/>">Cancel</a>
						</div>
						
					</div>
					
					
						
				</div>
			</div>
		</div>
		
		<sec:authorize access="hasAuthority('!VIEW_RENEWAL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		
		<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left1">
							<h3 class="secondary-header-title">
								<a
									href="<c:url value="/showVehicle.in?vid=${renewalReminder.vid}"/>"
									data-toggle="tip" data-original-title="Click Vehicle Details">
									<c:out value="${renewalReminder.vehicle_registration}" />
								</a>
							</h3>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><span class="fa fa-bell"> Type :</span> <a href=""
										data-toggle="tip" data-original-title="Renewal Type"> <c:out
												value="${renewalReminder.renewal_type}" />
									</a></li>
									<li><span class="fa fa-usb"> Sub Type :</span> <a href=""
										data-toggle="tip" data-original-title="Renewal Sub Type">
											<c:out value="${renewalReminder.renewal_subtype}" />
									</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>

	</section>
	
	<section class="content">
	
		<div class="modal fade" id="editrenewalPeriod" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Edit Renewal Reminder Period</h4>
					</div>
					<div class="modal-body">
					  <fieldset>
						<legend>Renewal Period</legend>
						<div class="box">
							<div class="box-body">
							<input type="hidden" class="form-text" name="renewal_id" id="renewal_id" value="${renewalReminder.renewal_id}" />
							<input type="hidden" class="form-text" name="renewal_Amount" value="${renewalReminder.renewal_Amount}" />
							<div class="row1" id="grprenewalDate" class="form-group">
								<label class="L-size control-label" for="reservation">Validity
									From &amp; To <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<div class="input-group input-append date">
										<input type="text" class="form-text" name="renewal_from"
											required id="reservation" maxlength="26" readonly="readonly" 
											data-inputmask="'mask': ['99-99-9999  to  99-99-9999']"
											data-mask=""
											value="${renewalReminder.renewal_from}  to  ${renewalReminder.renewal_to}">
										<span class="input-group-addon add-on"><span
											class="fa fa-calendar"></span></span>
									</div>
									<span id="renewalDateIcon" class=""></span>
									<div id="renewalDateErrorMsg" class="text-danger"></div>
								</div>
							</div>
							<br>
							<div class="row" id="grprenewalTime" class="form-group">
								<label class="L-size control-label"
									for="renewal_timethreshold">Due Threshold <abbr
									title="required">*</abbr> :
								</label>
								<div class="I-size">
									<div class="col-md-4">
										<input type="text" class="form-text"
											name="renewal_timethreshold" id="renewal_timethreshold"
											value="${renewalReminder.renewal_timethreshold}" min="1"
											max="6" maxlength="2" value="1" required="required"
											onkeypress="return IsNumericTimeThru(event);"
											ondrop="return false;"> <span id="renewalTimeIcon"
											class=""></span>
										<div id="renewalTimeErrorMsg" class="text-danger"></div>
										<label class="error" id="errorTimeThru"
											style="display: none"> </label> <label class="error"
											id="errorTime"> </label>
									</div>
									<div class="col-md-4">
										<select class="form-text" name="renewal_periedthreshold"
											id="renewal_periedthreshold"
											onchange="OnChangeDueThreshold(this)" required="required">
											<option value="0"<c:if test="${renewalReminder.renewal_periedthreshold == 0}">selected="selected"</c:if> >day(s)</option>
											<option value="7" <c:if test="${renewalReminder.renewal_periedthreshold == 7}">selected="selected"</c:if>>Week(s)</option>
											<option value="28" <c:if test="${renewalReminder.renewal_periedthreshold == 28}">selected="selected"</c:if> >Month(s)</option>
										</select>
									</div>
								</div>
							</div>
						   </div>
						</div>
					 </fieldset>
					 <br/>
					</div>
					<div class="modal-footer">
						<button type="submit" onclick="return updateRenewalPeriod();" class="btn btn-success">Update Renewal Period</button>
						<button type="button" class="btn btn-default"
							data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="uploadEditFile" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form method="POST" enctype="multipart/form-data" id="fileUploadForm">	
						<input type="hidden" class="form-text" name="renewal_id" id="renewal_id" value="${renewalReminder.renewal_id}" />
						<input type="hidden" class="form-text" name="renewal_Number" id="renewal_Number" value="${renewalReminder.renewal_R_Number}" />
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Upload/Edit Renewal Document</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Document <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<input type="file" name="input-file-preview" id="renewalFile" multiple="multiple"></input>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Upload" id="btnSubmit" class="btn btn-success" />
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
		
		<div class="modal fade" id="createApprovalModal" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Create Approval</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<div class="form-group">
									<label class="col-md-3">Approval Remark <abbr title="required">*</abbr>
									</label>
									<div class="col-md-6">
										<textarea class="form-text" id="approvalRemark" name="approvalRemark" rows="3" maxlength="240">	
												</textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<input type="button" value="Create Approval" id="createApproval" class="btn btn-success" />
							<button type="button" class="btn btn-default" data-dismiss="modal">
								<span id="Close"><spring:message code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="col-md-9 col-sm-9 col-xs-12">
					<div class="main-body">
						<div class="row">
							<div class="col-md-5 col-sm-5 col-xs-12">
							
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Renewal Profile Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Renewal Number :</th>
													<td class="value"><c:out
															value="RR-${renewalReminder.renewal_R_Number}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Vehicle Name :</th>
													<td class="value"><c:out
															value="${renewalReminder.vehicle_registration}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Type :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_type}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Sub Type :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_subtype}" /></td>
												</tr>
												<input type="hidden" id="renewalStatusId" value="${renewalReminder.renewal_staus_id}">
												<tr class="row">
													<th class="key">Renewal Approval Status:</th>
													<td class="value">
														<span id="renewalStatusLabel" class="label label-default">
															<c:out value="${renewalReminder.renewal_status}" />
														</span>
													</td>
												</tr>
										</table>
									</div>
								</div>
								<br>
								
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Amount Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<c:if test="${configuration.receiptnumbershow}">
												<tr class="row">
													<th class="key">Receipt No | Challan No | Policy No :</th>
													<td class="value"><span class="label label-success"><c:out
																value="${renewalReminder.renewal_receipt}" /></span></td>
												</tr>
												</c:if>
												<tr class="row">
													<th class="key">Draft Amount :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_Amount}" /></td>
												</tr>
												<c:if test="${configuration.showVendorCol}">
													<tr class="row">
														<th class="key">Vendor :</th>
														<td class="value"><c:out
																value="${renewalReminder.vendorName}" /></td>
													</tr>
												</c:if>
												<c:if test="${configuration.modeofaaymentshow}">
												<tr class="row">
													<th class="key">Modes of Payment :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_paymentType}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.cashtransactionshow}">
												<tr class="row">
													<th class="key"><c:out
															value="${renewalReminder.renewal_paymentType}" /> NO :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_PayNumber}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.paidDateshow}">
												<tr class="row">
													<th class="key">Paid Date :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_dateofpayment}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.tallyIntegrationRequired}">
												<tr class="row">
													<th class="key">Tally Company :</th>
													<td class="value"><c:out
															value="${renewalReminder.tallyCompanyName}" /></td>
												</tr>
												</c:if>
												<c:if test="${configuration.paidbyshow}">
												<tr class="row">
													<th class="key">Cashier | Paid By : :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_paidby}" /></td>
												</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
								<br>
								<c:if test="${configuration.createApproval}">
								<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Approved Information</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Renewal AID :</th>
													<td class="value"><c:if
															test="${renewalReminder.renewal_approvedID != 0}">
															<a
																href="<c:url value="/1/ShowRenRemApproval.in?AID=${renewalReminder.renewal_approvedID}&page=1" />"
																data-toggle="tip"
																data-original-title="Click this Details"><c:out
																	value="RA-${RenewalReminderApproval.renewalApproval_Number}" /> </a>
														</c:if></td>
												</tr>
												<tr class="row">
													<th class="key">Renewal Status :</th>
													<c:choose>
														<c:when
															test="${renewalReminder.renewal_status == 'APPROVED'}">
															<td class="value"><span class="label label-success"><c:out
																		value="${renewalReminder.renewal_status}" /></span></td>
														</c:when>
														<c:otherwise>
															<td class="value"><span class="label label-danger"><c:out
																		value="${renewalReminder.renewal_status}" /></span></td>
														</c:otherwise>
													</c:choose>

												</tr>
												<tr class="row">
													<th class="key">Approved By :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_approvedby}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Approved Date :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewalApproveddate}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Approved Comment :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_approvedComment}" /></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
								</c:if>
								
								<c:if test="${configuration.optionalInformation}">
									<div class="box box-success">
										<div class="box-header">
											<h3 class="box-title">+ Optional Information</h3>
										</div>
										<div class="box-body no-padding">
											<table class="table table-striped">
												<tbody>
													<tr class="row">
														<th class="key">Authorization States :</th>
														<td class="value"><c:out
																value="${renewalReminder.renewal_authorization}" /></td>
													</tr>
													<tr class="row">
														<th class="key">Remarks :</th>
														<td class="value"><c:out
																value="${renewalReminder.renewal_number}" /></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</c:if>
							</div>
							
							<div class="col-md-5 col-sm-5 col-xs-12">
								<div class="box box-success">
								<input type="hidden" id="thresholdPeriod" value="${renewalReminder.renewal_periedthreshold}">
								<input type="hidden" id="timethreshold" value="${renewalReminder.renewal_timethreshold}">
									<div class="box-header">
										<h3 class="box-title">Renewal Period Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Validity From :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_from}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Validity To :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_to}" /></td>
												</tr>
												<tr class="row">
													<th class="key">Due Threshold :</th>
													<td id="thresholdValue"></td>
												</tr>
												<tr class="row">
													<th class="key">Due Start Date Of Renewal :</th>
													<td class="value"><c:out
															value="${renewalReminder.renewal_dateofRenewal}" /></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<br>
								<c:if test="${renewalReminder.ignored}">
									<div class="box box-success">
									<div class="box-header">
										<h3 class="box-title">Renewal Ignore Details</h3>
									</div>
									<div class="box-body no-padding">
										<table class="table table-striped">
											<tbody>
												<tr class="row">
													<th class="key">Ignore Status :</th>
													<td class="value">
													<label class="label label-default label-info">
													Ignored
													 </label>
													</td>
												</tr>
												<tr class="row">
													<th class="key">Ignore Remark :</th>
													<td class="value"><c:out
															value="${renewalReminder.ignoredRemark}" /></td>
												</tr>
												
											</tbody>
										</table>
									</div>
								</div>
								</c:if>
								
								<article class="col-md-5 col-sm-5 col-xs-12">
									<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">
									<br>
										<div class="panel panel-default">
											<div class="panel-body">
												<c:if test="${!empty documents}">
												   <input type="hidden" id="docSize" value="${documents.size() }">
													<c:forEach items="${documents}" var="documents">
														<c:choose>
															<c:when test="${documents._id > 0}">
																<a class="btn btn-app" data-toggle="tip"
																	data-original-title="Click Download"
																	href="${pageContext.request.contextPath}/download/RenewalReminder/${documents._id}">
																	<i class="fa fa-download"></i>Download
																</a>
																<sec:authorize access="hasAuthority('DELETE_RENEWAL_DOCUMENT')">
																	<a href="#" style="color: red;" onclick="removeRenewalDocument('${documents._id}');"><i class="fa fa-trash"></i> Remove</a>
																</sec:authorize>
																
															</c:when>
															<c:otherwise>
																<div align="center"></div>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>	
											</div>
											<div class="panel-footer">
												<h4>
													<a
														href="<c:url value="/showVehicle.in?vid=${renewalReminder.vid}"/>"
														data-toggle="tip"
														data-original-title="Click Vehicle Details"> <c:out
															value="${renewalReminder.vehicle_registration}" />
													</a> <a href=""> <c:out
															value="${renewalReminder.renewal_subtype}" /></a>
												</h4>
											</div>
										</div>
									</sec:authorize>
								</article>
							</div>
						</div>
					</div>
				</div>
			</sec:authorize>
			
			<div class="col-md-2 col-sm-2 col-xs-12">
				<ul class="nav nav-list">
					
					<li><sec:authorize access="hasAuthority('VIEW_RENEWAL')">
							<a
								href="<c:url value="/showRenewalReminderDetailsHistory.in?vid=${renewalReminder.vid}"/>">Renewal
								Reminder History <span class="count muted text-muted pull-right"></span>
							</a>
					</sec:authorize></li>
				</ul>
			</div>
		</div>
		
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out value="${renewalReminder.createdBy}" /></small> |
			<small class="text-muted"><b>Created date: </b> <c:out value="${renewalReminder.created}" /></small> | 
			<small class="text-muted"><b>Last updated by :</b> <c:out value="${renewalReminder.lastModifiedBy}" /></small> |
			<small class="text-muted"><b>Last updated date:</b> <c:out value="${renewalReminder.lastupdated}" /></small>
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
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminderlanguage.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/RenewalReminder.validate.js" />"></script>
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/RR/ShowRenewalReminderDetails.js" />"></script>		
		<script>
			$(function() {
				$('#reservation').daterangepicker();
			});
			function thresholdperiod(){
				if($('#thresholdPeriod').val()=='28'){
					period="Month";
				}else if($('#thresholdPeriod').val()=='7'){
					period="Week";
				}else if ($('#thresholdPeriod').val() == '0') {
					period = "Day's";
				}
				
				$('#thresholdValue').text($('#timethreshold').val()+' '+period);

			}
		</script>
		
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>
		<script
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>