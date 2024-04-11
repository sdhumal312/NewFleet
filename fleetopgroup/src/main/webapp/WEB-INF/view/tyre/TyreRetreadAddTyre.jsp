<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/TyreInventoryNew/1.in"/>">Tyre Inventory</a>
					/ <a href="<c:url value="/TyreRetreadNew/1.in"/>">Tyre Retread
						bills</a> / <span> Retread Tyre </span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreRetreadNew/1.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="box">
			<div class="box-body">
				<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
					<div class="row">

						<div class="col-md-7">
							<h4>
								Tyre Retread ${Retread.TRNUMBER } <span
									class="label label-pill label-warning"><c:out
										value="${Retread.TR_STATUS}" /></span>
							</h4>
							<h4>
								<a href="showVehicle.in?vid=${Retread.TR_VENDOR_ID}"
									data-toggle="tip" data-original-title="Click Vehicle Info">
									<c:out value="${Retread.TR_VENDOR_NAME} - " /> <c:out
										value="${Retread.TR_VENDOR_LOCATION}" />
								</a>
							</h4>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><span class="fa fa-user"> Payment Type:</span> <a
										data-toggle="tip" data-original-title="Terms "><c:out
												value="${Retread.TR_PAYMENT_TYPE}" /></a></li>
									<li><i class="fa fa-bitcoin"> Retread date:</i> <a
										data-toggle="tip" data-original-title="Retread date"><c:out
												value="${Retread.TR_OPEN_DATE}" /></a></li>
									<li><span class="fa fa-user"> Required date:</span> <a
										data-toggle="tip" data-original-title="Required date"><c:out
												value="${Retread.TR_REQUIRED_DATE}" /></a></li>
									<li><span class="fa fa-user"> Quote No:</span> <a
										data-toggle="tip" data-original-title="Quote No"><c:out
												value="${Retread.TR_QUOTE_NO}" /></a></li>
									<li><span class="fa fa-user"> Manual No:</span> <a
										data-toggle="tip" data-original-title=" Manual No"><c:out
												value="${Retread.TR_MANUAL_NO}" /></a></li>
									<li><span class="fa fa-user"> Payment No:</span> <a
										data-toggle="tip" data-original-title=" Payment No"><c:out
												value="${Retread.TR_PAYMENT_NUMBER}" /></a></li>
								</ul>
							</div>
							<div class="row">
							<div id="work-order-statuses">
								<div id="work-order-statuses">
									<sec:authorize access="hasAuthority('DOWNLOND_TYRE_RETREAD')">
										<a style="width: 10%"
											href="PrintTyreInvoice?Id=${TyreInvoice.ITYRE_ID}"
											target="_blank" class="btn btn-default "><i
											class="fa fa-print"></i></a>
									</sec:authorize>
								</div>
							</div>
						</div>
						</div>
						<div class="col-md-4">
							<div class="row">
								<input type="hidden" id="statues" name="statues"
									value="${Retread.TR_STATUS}">
								<div id="work-order-statuses">
									<div id="work-order-statuses">
										<a data-disable-with="..." data-method="post"
											data-remote="true" rel="nofollow"> <span id="status-open"
											class="status-led"> <i class="fa fa-circle"></i>
												<div class="status-text">Open</div>
										</span>
										</a> <a data-method="post" data-remote="true" rel="nofollow">
											<span id="status-in-progress" class="status-led"> <i
												class="fa fa-circle"></i>
												<div class="status-text">Send to Retread</div>
										</span>
										</a>
									</div>


									<button type="button" class="btn btn-default"
										data-toggle="modal" data-target="#addworkorderDocument"
										data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button>
								</div>
							</div>
						</div>
					</div>
					<br>
					<fieldset>
						<div class="modal fade" id="addworkorderDocument" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<form method="post" action="uploadTyreDocument.in"
										enctype="multipart/form-data">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
											<h4 class="modal-title">Tyre Retread Document</h4>
										</div>
										<div class="modal-body">
											<input type="hidden" name="TRID" value="${Retread.TRID}">
											<div class="row1">
												<div class="L-size">
													<label class="L-size control-label"> Browse: </label>
												</div>
												<div class="I-size">
													<input type="file"
														accept="image/png, image/jpeg, image/gif"
														name="input-file-preview" required="required" />
												</div>
											</div>
											<br />
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-primary">
												<span>Upload</span>
											</button>
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<span>Cancel</span>
											</button>
										</div>
									</form>
								</div>
							</div>
						</div>
						<div class="row">
							<c:if test="${deleteFristParts}">
								<div class="alert alert-danger">
									<button class="close" data-dismiss="alert" type="button">x</button>
									Should be Delete First Task Parts and Technician
								</div>
							</c:if>
							<c:if test="${param.UploadSuccess eq true}">
								<div class="alert alert-success">
									<button class="close" data-dismiss="alert" type="button">x</button>
									This Tyre Document Upload Successfully.
								</div>
							</c:if>
							<div class="col-md-11">
								<div class="table-responsive">
									<table class="table">
										<thead>
											<tr class="breadcrumb">
												<th class="fit">NO</th>
												<th>Retread Tyre</th>
												<th class="fit ar">Tyre Cost</th>
												<th class="fit ar">Discount</th>
												<th class="fit ar">GST</th>
												<th class="fit ar">Total</th>
												<th class="fit">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty RetreadAmount}">
												<%
													Integer hitsCount = 1;
												%>
												<c:forEach items="${RetreadAmount}" var="RetreadAmount">
													<tr data-object-id="" class="ng-scope">
														<td class="fit">
															<%
																out.println(hitsCount);
																			hitsCount += 1;
															%>
														</td>
														<!-- Tast table to assing part value table -->
														<td><h4>
																<c:out value="${RetreadAmount.TYRE_NUMBER} - "></c:out>
																<c:out value="${RetreadAmount.TYRE_SIZE}"></c:out>
															</h4>
															<div class="row">
																<a id="addLabor${RetreadAmount.TR_AMOUNT_ID}"
																	href="javascript:toggle2Labor('changeLabor${RetreadAmount.TR_AMOUNT_ID}','addLabor${RetreadAmount.TR_AMOUNT_ID}');">
																	Enter Retread Tyre</a>
															</div>
															<div class="row">
																<div class="col-md-11">

																	<table class="table">
																		<tbody style="border-top: 0px;">
																			<tr data-object-id="" class="ng-scope">
																				<td colspan="6">
																					<div class="">
																						<div id="changeLabor${RetreadAmount.TR_AMOUNT_ID}"
																							style="display: none">
																							<form action="UpdateRetreadReceived.in"
																								method="post">
																								<div class="row">
																									<div class="">
																										<input type="hidden" name="RID"
																											value="${Retread.TRID}"> <input
																											type="hidden" name="TR_AMOUNT_ID"
																											value="${RetreadAmount.TR_AMOUNT_ID}"
																											id="workordertaskid">
																									</div>
																									<div class="col-md-2">
																										<div class="form-group">
																											<label for="exampleInputEmail1">Retread
																												Cost</label> <input type="text" class="form-text"
																												data-toggle="tip"
																												data-original-title="Tyre Retread Cost"
																												name="RETREAD_COST" placeholder="Cost"
																												required="required"
																												onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="laberhourscost${RetreadAmount.TR_AMOUNT_ID}"
																												onkeyup="javascript:sumthere('laberhourscost${RetreadAmount.TR_AMOUNT_ID}', 'laberdiscount${RetreadAmount.TR_AMOUNT_ID}', 'labertax${RetreadAmount.TR_AMOUNT_ID}', 'totalLaborcost${RetreadAmount.TR_AMOUNT_ID}');"
																												min="0.0">
																										</div>
																									</div>

																									<div class="col-md-2">
																										<div class="form-group">
																											<label for="exampleInputEmail1">Retread
																												Discount</label> <input type="text"
																												name="RETREAD_DISCOUNT" class="form-text"
																												placeholder="dis" required="required"
																												data-toggle="tip"
																												data-original-title="Tyre discount"
																												onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="laberdiscount${RetreadAmount.TR_AMOUNT_ID}"
																												onkeyup="javascript:sumthere( 'laberhourscost${RetreadAmount.TR_AMOUNT_ID}', 'laberdiscount${RetreadAmount.TR_AMOUNT_ID}', 'labertax${RetreadAmount.TR_AMOUNT_ID}', 'totalLaborcost${RetreadAmount.TR_AMOUNT_ID}');">
																										</div>
																									</div>
																									<div class="col-md-2">
																										<div class="form-group">
																											<label for="exampleInputEmail1">Retread
																												GST</label> <input type="text" name="RETREAD_TAX"
																												class="form-text" placeholder="GST"
																												required="required" data-toggle="tip"
																												data-original-title="Tyre GST"
																												onkeypress="return isNumberKeyWithDecimal(event,this.id);"
																												id="labertax${RetreadAmount.TR_AMOUNT_ID}"
																												onkeyup="javascript:sumthere('laberhourscost${RetreadAmount.TR_AMOUNT_ID}', 'laberdiscount${RetreadAmount.TR_AMOUNT_ID}', 'labertax${RetreadAmount.TR_AMOUNT_ID}', 'totalLaborcost${RetreadAmount.TR_AMOUNT_ID}');">
																										</div>
																									</div>
																									<div class="col-md-2">
																										<div class="form-group">
																											<label for="exampleInputEmail1">Retread
																												Total</label> <input type="text" name="totalcost"
																												class="form-text" required="required"
																												data-toggle="tip"
																												data-original-title="Tyre Total Cost"
																												id="totalLaborcost${RetreadAmount.TR_AMOUNT_ID}"
																												readonly="readonly">
																										</div>
																									</div>
																									<div class="fit"></div>
																									<label class="error" id="errorTYRE"
																										style="display: none"> </label>
																								</div>
																								<br>
																								<div class="row">
																									<div class="col-md-5 col-md-offset-2">

																										<input class="btn btn-success" type="submit"
																											value="Save Retread Cost">
																									</div>
																								</div>
																							</form>
																						</div>
																					</div>
																				</td>

																			</tr>
																		</tbody>

																	</table>
																</div>
															</div></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${RetreadAmount.RETREAD_COST}"></c:out></td>
														<td class="fit ar"><c:out
																value="${RetreadAmount.RETREAD_DISCOUNT} %"></c:out></td>
														<td class="fit ar"><c:out
																value="${RetreadAmount.RETREAD_TAX} %"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${RetreadAmount.RETREAD_AMOUNT}"></c:out></td>
														<td class="fit">
															<div class="btn-group">
																<a class="btn-sm dropdown-toggle" data-toggle="dropdown"
																	href="#"> <span class="fa fa-cog"></span> <span
																	class="caret"></span>
																</a>

																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('DELETE_WORKORDER')">
																			<a
																				href="DeleteRetreadTyre?RID=${Retread.TRID}&RAID=${RetreadAmount.TR_AMOUNT_ID}"
																				class="confirmation"
																				onclick="return confirm('Are you sure? Delete ')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="col-md-11">
								<div class="row">
									<div class="col-md-7">
										<form action="SentRetread.in" method="post">
											<div class="row1">
												<div class="L-size">
													<label class="L-size control-label"> Initial_Note :
													</label>
												</div>
												<div class="I-size">
													<input type="hidden" id="inventoryRetreadTyreId" name="RID" value="${Retread.TRID}">
													<textarea rows="2" cols="2" class="form-text"
														name="DESCRIPTION" maxlength="250">${Retread.TR_DESCRIPTION}
									             </textarea>
												</div>
											</div>
											<br>
											<div class="row1">
												<div class="L-size"></div>
												<div class="I-size">
													<button type="submit"
														class="col-md-offset-3 btn btn-primary">
														<span>Send to Retread</span>
													</button>
												</div>
											</div>

										</form>
										<br>
									</div>
									<div class="col-md-4">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key"><h4>SubTotal :</h4></th>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${Retread.TR_AMOUNT}" />
														</h4></td>
												</tr>
												<tr class="row">
													<th class="key"><h4>Retread Total :</h4></th>
													<td class="value"><h4>
															<i class="fa fa-inr"></i> <fmt:formatNumber type="number" pattern="#.##" value="${Retread.TR_ROUNT_AMOUNT}" />
														</h4></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
							<div class="row1">
							<a id ="AddMoreParts" onload=""
								onclick ="javascript:AddPart();"
								href="javascript:AddPart();">
								Add More Tyre
								</a>
								</div>
					</fieldset>
				</sec:authorize>
			</div>
		</div>
		<!-- sfkhu -->
		<div class="modal fade" id="configureMorePart" role="dialog">
					<div class="modal-dialog modal-md">
					<input type="hidden" id="NoOfPartsAllowedToAdd" value="50">
					<input type="hidden" id="addMorePartsAtBottom" value="true">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Add Retread Tyre</h4>
							</div>
								<div class="form-horizontal">
									<fieldset>
										<div class="box">
											<input type="hidden" id="NoOfPartsAllowedToAdd" value="50">
											<input type="hidden" id="addMorePartsAtBottom" value="true">
											<div class="box-body">
												<div class="panel panel-success">
													<div class="panel-body">
														<div class="row1" id="retreadTyreNum12" class="form-group">
															<label class="L-size control-label" for="searchpart">Search
															Retread	Tyre Number :<abbr title="required">*</abbr>
															</label>
															<div class="I-size">
																<input type="hidden" id="retreadTyreNum" name="tyreNumber_many" onchange="validateTyreNumber(this);"
																	style="width: 100%;"
																	placeholder="Please Enter 2 or more Part Name or Part Number" />
																<span id="inventoryPartIcon" class=""></span>
																<div id="inventoryPartErrorMsg" class="text-danger"></div>
															</div>
														</div>
														
														
														<div class="row1" id="grpquantity" class="form-group">
															<label class="L-size control-label" for="quantity">Quantity
																:</label>
			
															<div class="col-md-9">
																<div class="col-md-1">
																	<input type="text" class="form-text" name="quantity_many"
																		min="0.0" id="quantity" maxlength="4" value="1" readonly="readonly"
																		placeholder="ex: 23.78" required="required"
																		data-toggle="tip"
																		data-original-title="enter Part Quantity"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:calthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="quantityIcon"
																		class=""></span>
																	<div id="quantityErrorMsg" class="text-danger"></div>
			
																</div>
																<div class="col-md-2">
																	<input type="text" class="form-text"
																		name="unitprice_many" id="unitprice" maxlength="7"
																		min="0.0" placeholder="Unit Cost" required="required"
																		data-toggle="tip" data-original-title="enter Unit Price"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:calthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="unitpriceIcon"
																		class=""></span>
																	<div id="unitpriceErrorMsg" class="text-danger"></div>
																</div>
			
																<div class="col-md-1">
																	<input type="text" class="form-text" name="discount_many"
																		min="0.0" id="discount" maxlength="5"
																		placeholder="Discount" required="required"
																		data-toggle="tip" data-original-title="enter Discount"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:calthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="discountIcon"
																		class=""></span>
																	<div id="discountErrorMsg" class="text-danger"></div>
			
																</div>
																<div class="col-md-1">
																	<input type="text" class="form-text" name="tax_many"
																		id="tax" maxlength="5" min="0.0" placeholder="GST"
																		required="required" data-toggle="tip"
																		data-original-title="enter GST"
																		onkeypress="return isNumberKeyQut(event,this);"
																		onkeyup="javascript:calthere('quantity', 'unitprice', 'discount', 'tax', 'tatalcost');"
																		ondrop="return false;"> <span id="taxIcon"
																		class=""></span>
																	<div id="taxErrorMsg" class="text-danger"></div>
																</div>
																<div class="col-md-2">
																	<input type="text" class="form-text" maxlength="8"
																		value="0.0" min="0.0" id="tatalcost" readonly="readonly" name="tatalcost"
																		data-toggle="tip" data-original-title="Total Cost"
																		onkeypress="return isNumberKeyQut(event,this);"
																		ondrop="return false;">
																</div>
															</div>
			
															<br> <label class="error" id="errorINEACH"
																style="display: none"></label>
			
														</div>
													
														<div id="moreParts">
														</div>
														
														
														<div class="row1">
															<div class="input_fields_wrap">
																<button class="add_field_button btn btn-info"
																	data-toggle="tip"
																	data-original-title="Click add one more part">
																	<i class="fa fa-plus"></i> Add More Parts
																</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
								</fieldset>
										<div class="row1">
											<div class="col-md-10 col-md-offset-2">
												<button type="submit" id=btn-save class="btn btn-success"  >Add
													Retread Tyre</button>
												<a class="btn btn-link"
													href="<c:url value="/ShowRetreadTyre?RID=${Retread.TRID}"/>">Cancel</a>
												</div>
											</div>
										<!-- </fieldset> -->
								
									</div>
						</div>
					</div>
				</div>
		<!-- dfkjgudf -->
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${Retread.CREATEDBY}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${Retread.CREATED_DATE}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${Retread.LASTMODIFIEDBY}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${Retread.LASTUPDATED_DATE}" /></small>
		</div>
	</section>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/VI/TyreRetreadValidate.js" />"></script>
	
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/addMultipleRetreadTyre.js" />"></script>		
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>		
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>	
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
		
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						var input_Statues = document.getElementById('statues').value;
						console.log("input_Statues",input_Statues)
						var wrapperStatues = $("#work-order-statuses"); //Fields wrapper
						switch (input_Statues) {
						case "OPEN":
							document.getElementById('status-open').className = 'status-led-open';

							break;
						case "SENT-RETREAD":
							document.getElementById('status-in-progress').className = 'status-led-in-progress';

							break;
						}
					});
</script>