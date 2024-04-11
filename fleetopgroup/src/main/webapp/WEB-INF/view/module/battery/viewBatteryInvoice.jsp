<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open.in">Home</a> / <a
						href="/fleetopgroup/BatteryInventory.in">Battery Inventory</a>/ Battery Invoice List
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_VENDOR_PAYMENT')">
						<a class="btn btn-success btn-sm"
							href="BatteryInventory.in"> <span
							class="fa fa-plus"></span> Battery Inventory
						</a>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VENDOR')">
				<div class="col-md-4 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
						<div class="info-box-content">
						 <input
								type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Battery Invoice</span> 
							<span id="totalBattryInvoice" class="info-box-number"></span>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		<div class="row">
		<div class="main-tabs">
						<ul class="nav nav-pills" id="list">
							<li role="presentation" id="All"><a
								href="/fleetopgroup/viewBatteryInvoice/1.in">Battery Invoice</a></li>
							<c:if test="${!empty PartLocations}">
								<c:forEach items="${PartLocations}" var="PartLocations">
									<li class="tab-link" id="${PartLocations.partlocation_name}"><a
										class="btn btn-link"
										href="#" onclick="locationBatteryDetails(${PartLocations.partlocation_id}, 1);" >
											${PartLocations.partlocation_name}</a></li>
								</c:forEach>
							</c:if>
						</ul>
	  </div>
			<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_VENDOR')">
				<div class="main-body">
					<div class="box">
						<div class="box-body">
							<div class="table-responsive">
								<input type="hidden" id="startPage" value="${SelectPage}"> 
								<table id="VendorPaymentTable" class="table table-hover table-bordered">
								
								</table>
							</div>
						</div>
					</div>
					<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
	<script type="text/javascript" src="/fleetopgroup/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/ViewBatteryList.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			getPageWiseBatteryInvoiceDetails($("#startPage").val());	
		});
	</script>
	<script type="text/javascript" src="/fleetopgroup/resources/QhyvOb0m3EjE7A4/js/fleetop/module/battery/batteryUtility.js"></script>
</div>