<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/TyreInventory/1.in"/>">Tyre Inventory</a> / 
				</div>
				<div class="pull-right">
					<a href="<c:url value="/TyreInventory/1.in"/>">Cancel..</a>
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
							<h4> Tyre Sold Invoice-${invoiceId}
								<span id="tyreState" class="label label-pill label-warning"> </span>
							</h4>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><i class="fa fa-bitcoin"> Sold date:</i> <a
										data-toggle="tip" data-original-title="Retread date"><span id="soldDate"></span></a></li>
									<li><span class="fa fa-user"> Invoice Number:</span> <a
										data-toggle="tip" data-original-title="Required date"><span id="soldInvoiceNo"></span></a></li>
									<li>
									<%-- <sec:authorize access="hasAuthority('DOWNLOND_TYRE_RETREAD')">
										<a style="width: 10%"
											href="PrintTyreInvoice?Id=${TyreInvoice.ITYRE_ID}"
											target="_blank" class="btn btn-default "><i
											class="fa fa-print"></i></a>
									</sec:authorize> --%>
									</li>			
								</ul>
							</div>
						</div>
						<div class="col-md-4">
							<div class="row">
								<div id="work-order-statuses">
									<div id="work-order-statuses">
										<input type="hidden" id="statues" name="statues" >
										<a data-disable-with="..." data-method="post"
											data-remote="true" rel="nofollow"> 
											<span id="status-open" class="status-led"> 
											<i class="fa fa-circle"></i>
												<div class="status-text">In Process</div>
										</span>
										</a> <a data-method="post" data-remote="true" rel="nofollow">
											<span id="status-in-progress" class="status-led"> <i
												class="fa fa-circle"></i>
												<div class="status-text">Sold</div>
										</span>
										</a>
									</div>
									<!-- <button type="button" class="btn btn-default"
										data-toggle="modal" data-target="#addTyreSoldDocument"
										data-whatever="@mdo">
										<i class="fa fa-upload"></i>
									</button> -->
								</div>
							</div>
						</div>
					</div>
					<br>
					<fieldset>
						<div class="modal fade" id="addTyreSoldDocument" role="dialog">
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
								<input type="hidden" id="invoiceId" value="${invoiceId}">
									<table class="table">
										<thead id="inProcesstHead" class="hide">
											<tr class="breadcrumb">
												<th class="fit">NO</th>
												<th>Tyre Number</th>
												<th>Sold Cost</th>
												<th>Discount</th>
												<th>Gst</th>
												<th>Total</th>
												<!-- <th>Action</th> -->
											</tr>
										</thead>
										<thead id="completeThead" class="hide">
											<tr class="breadcrumb">
												<th class="fit">NO</th>
												<th>Tyre Number</th>
												<th>Tyre Size</th>
											</tr>
										</thead>
										<tbody id="showTyreTable">
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="col-md-11">
								<div class="row">
									<div class="col-md-7">
										
									</div>
									<div class="col-md-4">
										<table class="table">
											<tbody>
												<tr class="row">
													<th class="key"><h4>Total Sold Amount :</h4></th>
													<td class="value">
														<h4>
															<i class="fa fa-inr"></i> <span id="totalTyreSoldAmount"></span>
														</h4>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
						</div>
								
							
					</fieldset>
				</sec:authorize>
			</div>
		</div>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/IT/TyreSoldCompleteDetails.js" />"></script>	
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>		
<script
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js" />"></script>	
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
