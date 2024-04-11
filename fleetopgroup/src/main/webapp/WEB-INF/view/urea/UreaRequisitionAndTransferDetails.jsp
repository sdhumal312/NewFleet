]<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / 
					<a href="<c:url value="/UreaInventory.in"/>">Urea Inventory</a> / 
					<a href="<c:url value="/ureaRequisitionAndTransferDetails.in"/>">Urea Requisition And Transfer Details</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('YOUR_UREA_REQUISITION_AND_TRANFER_HISTORY')">
						<a href="#" onclick="getYourRequisitionAndTransferList(1,7,4,1);" class="btn btn-success btn-sm">
							<span class="fa fa-info-circle "> Your Requisition And Transfer History</span></a>
					</sec:authorize>
					<sec:authorize access="hasAuthority('CREATE_UREA_REQUISITION')">
						<a href="<c:url value="/createUreaRequisition.in"/>" class="btn btn-success btn-sm">
							<span class="fa fa-plus"> Create Urea Requisition</span></a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
			<div class="row">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green">
							<i class="fa fa-flag-o"></i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text">Total Requisition</span>
							<span class="info-box-number" id="totalRequisitionCount"></span>
						</div>
					</div>
				</div>
				<!-- <div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green">
							<i class="fa fa-flag-o"></i>
						</span>
						<div class="info-box-content">
							<span class="info-box-text">Total Transfer</span>
							<span class="info-box-number" id="totalTransferCount"></span>
						</div>
					</div>
				</div> -->
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Requisition</span>
								<div class="input-group">
									<span class="input-group-addon"> 
										<span aria-hidden="true">UR-</span>
									</span>
									<input class="form-control" id=searchUR onkeypress="return isNumberKey(event);" type="number" min="1" required="required" placeholder="UR-ID">
									<span class="input-group-btn">
										<button type="submit" onclick="searchUR();" name="search" id="search-btn" class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" id="all">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" id=sentRequisition ><a
										href="#" onclick="getRequisitionAndTransferList(1,1);">SENT REQUISTION</a></li>
									<li role="presentation" id="acceptedRequisition"><a
										href="#" onclick="getRequisitionAndTransferList(1,2);">ACCEPTED REQUISTION</a></li>
									<li role="presentation" id="rejectedRequisition"><a
										href="#" onclick="getRequisitionAndTransferList(1,3);">REJECTED REQUISTION</a></li>
									<li role="presentation" id="receivedList"><a
										href="#" onclick="getRequisitionAndTransferList(1,4);">RECEIVED</a></li>
									<li role="presentation" id="rejectedList"><a
										href="#" onclick="getRequisitionAndTransferList(1,5);">REJECTED</a></li>
									<li role="presentation" id="transferdList"><a
										href="#" onclick="getRequisitionAndTransferList(1,7);">TRANSFERED</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" id="your">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<!-- <li role="presentation" id=yourSendRequisitionList><a
										href="#" onclick="getYourRequisitionAndTransferList(1,1,1,1);">SENT REQUISTION</a></li>
									<li role="presentation" id=yourReceivedRequisitionList><a
										href="#" onclick="getYourRequisitionAndTransferList(1,1,2,2);">RECEIVED REQUISTION</a></li>
									<li role="presentation" id=acceptOrRejectUrea><a
										href="#" onclick="getYourRequisitionAndTransferList(1,2,3,3);">ACCEPT / REJECT REQUISTION</a></li>
									<li role="presentation" id=receivedUrea><a
										href="#" onclick="getYourRequisitionAndTransferList(1,3,0,4);">RECEIVED UREA</a></li>
									<li role="presentation" id="rejectedUrea"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,4,0,5);">REJECTED UREA</a></li>
									<li role="presentation" id="transferedUrea"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,2,4,6);">TRANSFERED UREA</a></li> -->
										
									<li role="presentation" id="transferedUrea"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,7,4,1);">TRANSFERED UREA</a></li>	
									<li role="presentation" id="yourSendRequisitionList"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,1,1,2);">YOUR SENT REQUISTION</a></li>	
									<li role="presentation" id="yourReceivedRequisitionList"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,4,2,3);">YOUR RECEIVED UREA</a></li>	
									<li role="presentation" id="yourRejetedRequisitionList"><a
										href="#" onclick="getYourRequisitionAndTransferList(1,5,3,4);">YOUR REJECTED UREA</a></li>	
									
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row" >
				<div class="col-xs-11">
					<div id="AllInventory" class="tab-content2 current">
						<div class="main-body">
							<div class="box">
								<div class="box-body">
									<div class="table-responsive">
									<input type="hidden" id="requisitionStatus" value="3">
									<table class="table" id="reqTable" >
									<tbody id="ureaRequisitionAndTransferTable"> </tbody>
				
									</table>
									</div>
								</div>
							</div>
							<div class="text-center">
								<ul id="navigationBar" class="pagination pagination-lg pager">
									
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
	</section>
</div>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaRequisitionAndTranferDetails.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>