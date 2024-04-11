<%@ include file="../../taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
								href="<c:url value="/VehicleInspection.in"/>"> Vehicle Inspection
							</a>
				</div>
				
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE_INSPECTION_MODULE')">
			<div class="row">
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Todays Inspection Vehicle</span> <a data-toggle="tip"
								data-original-title="Click Inspection Details"
								href="<c:url value="/ViewVehicleInspectionList.in"/>"><span
								class="info-box-number">${todaysCount}</span></a>
						</div>
					</div>
				</div>
				
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Inspected Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectedList.in"/>"><span
								class="info-box-number">${inspectedCount}</span></a>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Pending Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectionPendingList.in"/>"><span
								class="info-box-number">${pendingInspectionList}</span></a>

						</div>
					</div>
				</div>
				<div class="col-md-3 col-sm-3 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-info"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Saved Inspection Vehicles</span> <a data-toggle="tip"
								data-original-title="Click Inspected Details"
								href="<c:url value="/ViewVehicleInspectionSavedList.in"/>"><span
								class="info-box-number">${inspectedSavedListCount}</span></a>

						</div>
					</div>
				</div>
				
				
			</div>
		</sec:authorize>
		

	</section>
</div>