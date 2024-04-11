function visibility(e,n){var t=document.getElementById(e),l=document.getElementById(n);"block"==t.style.display?(t.style.display="none",l.style.display="block"):(t.style.display="block",l.style.display="none")}
$(document).ready(function() {
	$("#Conductorofdepot, #Vehicleofdepot7 , #driverofdepot , #driverofdepot18").select2();
	
	$("#Conductorofdepot,#Vehicleofdepot6,#Routeofdepot4").select2();
	
	$("#vehicleId, #ReportSelectVehicle, #ReportSelectVehicle1, #WorkOrderVehicle, #OverOverSRVehicle, #DuesoonSRVehicle, #SEVehicle, #TCVehicle").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }
    ),
    //Below Code is For Vehicle Creation Report Ajax
    
	$("#companyIdentification").select2( {
		minimumInputLength:2,minimumResultsForSearch:10, 
		ajax: {
            url:"getCompanyInformationDetails.in", 
            dataType:"json", 
            type:"POST", 
            contentType:"application/json", 
            quietMillis:50, 
            data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.company_id +"-"+ a.company_name, 
                            slug: a.slug,
                            id	: a.company_id,
                        }
                    }
                    )
                }
            }
        }
    }
    ),
    
    
    $("#TripSelectVehicle").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleListTEST.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#driverAllList,#DriverAdList2, #driverAllListPenalty, #driverAllListPenalty2, #driverAttendance, #JAMAdriverList2").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverALLListOfCompany.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname+" - "+a.driver_fathername, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#JAMAdriverList, #DriverAdList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriver1List.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#JAMAConductorList, #ConductorAdList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getConductorList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname +" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#JAMACleaner").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getDriverCleanerList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driver_empnumber+" "+a.driver_firstname+" "+a.driver_Lastname, slug: a.slug, id: a.driver_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#TripRouteList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.routeNo+" "+a.routeName, slug: a.slug, id: a.routeID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#TripRouteNameList").select2( {
        ajax: {
            url:"getTripRouteList.in", dataType:"json", type:"POST", contentType:"application/json", data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.routeNo+" "+e.routeName, slug: e.slug, id: e.routeID
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#TripRouteNameID").select2({ajax:{url:"getTripRouteList.in",dataType:"json",type:"POST",contentType:"application/json",data:function(e){return{term:e}},results:function(e){return{results:$.map(e,function(e){return{text:e.routeNo+" "+e.routeName,slug:e.slug,id:e.routeID}})}}}}), $("#ReportSelectVehicleMileage").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, multiple:!0, ajax: {
            url:"getVehicleListALLStatus.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#ReportVehicleFuelRange, #ReportRRCRange").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, multiple:!0, ajax: {
            url:"getVehicleListALLStatus.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#searchpart, #searchpartPO").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchMasterPart.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partnumber+" - "+a.partname+" - "+a.category+" - "+a.make, slug: a.slug, id: a.partid
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#searchpartPartstockReport").select2( {
        minimumInputLength:2, minimumResultsForSearch:10,multiple:!0, ajax: {
            url:"getSearchMasterPart.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partnumber+" - "+a.partname+" - "+a.category+" - "+a.make, slug: a.slug, id: a.partid
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#PartVendorList, #ServiceVendorList").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getVendorSearchListInventory.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#PurchaseVendor, #PurchaseVendorPart").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getVendorSearchListPurchase.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#OverOverSRJOB, #DuesoonSRJOB").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getJobTypeService.in?Action=FuncionarioSelect2", dataType:"json", type:"GET", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.Job_Type, slug: a.slug, id: a.Job_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#selectVendor").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getVendorSearchListFuel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#SelectVehicleGroup, #VehicleGroupRRG, #driverGroup, #TCGroupWise, #TCGroupWise2, #VehicleGrpId1, #vehicleGrpId10 ,#vehicleGrpId9 , #vehicleGrpId18 ,#vehicleGrpId7 , #vehicleGrpId11, #vehicleGroupId8,#vehicleGroupId6,#vehicleGroupId4").select2( {
        ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    }
    
    ),$("#vehicleGrpId10").change(function() {
        $.getJSON("getDriverAndConductorByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#Conductorofdepot").html(b)
        })
        
    }), 
    
    $("#vehicleGrpId9").change(function() {
        $.getJSON("getConductorByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#Conductorofdepot").html(b)
        })
        
    }),
    
    $("#vehicleGrpId7").change(function() {
        $.getJSON("getVehicleByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].vid + '">' + a[d].vehicle_registration  + "</option>";
            b += "</option>", $("#Vehicleofdepot7").html(b)
        })
        
    }),
    
    $("#vehicleGrpId18").change(function() {
        $.getJSON("getDriverByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#driverofdepot18").html(b)
        })
        
    }),
    
     
    $("#vehicleGrpId11").change(function() {
        $.getJSON("getVehicleByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].vid + '">' + a[d].vehicle_registration +  "</option>";
            b += "</option>", $("#driverofdepot").html(b)
        })
        
    }),$("#vehicleGroupId8").change(function() {
        $.getJSON("getDriverAndConductorByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].driver_id + '">' + a[d].driver_empnumber + "-" + a[d].driver_firstname + " " + a[d].driver_Lastname + "</option>";
            b += "</option>", $("#Conductorofdepot").html(b)
        })
    }), 
    $("#vehicleGroupId6").change(function() {
        $.getJSON("getVehicleByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].vid + '">' + a[d].vehicle_registration + "</option>";
            b += "</option>", $("#Vehicleofdepot6").html(b)
        })
    }),$("#vehicleGroupId4").change(function() {
        $.getJSON("getRouteByVehicleGroupId.in", {
        	vehicleGroupId: $(this).val(),
            ajax: "true"
        }, function(a) {
            for (var b = '<option value="0">Please Select</option>', c = a.length, d = 0; c > d; d++) b += '<option value="' + a[d].routeID + '">' + a[d].routeName +  "</option>";
            b += "</option>", $("#Routeofdepot4").html(b)
        })
    }),$("#SelectFuelGroup ,#workOrderGroup,#workOrderGroup1, #SelectFuelGroup2, #SelectFuelGroup3, #SelectFuelGroup4, #SelectFuelGroup6").select2( {
        ajax: {
            url:"getVehicleGroup.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vGroup, slug: a.slug, id: a.gid
                        }
                    }
                    )
                }
            }
        }
    }
    
    
    ), 
    
    $("#loadListId").select2( {
		 minimumInputLength: 2,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getLoadListInfo.in?Action=FuncionarioSelect2",
	            dataType: "json",
	            type: "POST",
	            contentType: "application/json",
	            quietMillis: 50,
	            data: function(a) {
	                return {
	                    term: a
	                }
	            },
	            results: function(a) {
	                return {
	                    results: $.map(a, function(a) {
	                        return {
	                            text: a.loadTypeName,
	                            slug: a.slug,
	                            id: a.loadTypesId
	                        }
	                    })
	                }
	            }
	        }
   }),
     
     $("#SelectDriverJob").select2( {
        ajax: {
            url:"getDriverJobType.in", dataType:"json", type:"POST", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driJobType, slug: a.slug, id: a.driJobType
                        }
                    }
                    )
                }
            }
        }
    }
    ),$("#HaltDriverJob_ID, #GroupDriverJob_ID, #AttGroupDriverJob_ID, #DriJob_ID").select2( {
        ajax: {
            url:"getDriverJobType.in", dataType:"json", type:"POST", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.driJobType, slug: a.slug, id: a.driJobId
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#SelectDriverTraining").select2( {
        ajax: {
            url:"getDriverTrainingType.in", dataType:"json", type:"POST", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.dri_TrainingType, slug: a.slug, id: a.dri_TrainingType
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#Reporttyremodel").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSubModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_MODEL_SUBTYPE, slug: a.slug, id: a.TYRE_MST_ID
                        }
                    }
                    )
                }
            }
        }
    }
    ),
    $("#tyremodelstock").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSubModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_MODEL_SUBTYPE, slug: a.slug, id: a.TYRE_MST_ID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#ReporttyreSize").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSize.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_SIZE, slug: a.slug, id: a.TS_ID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#tyreSizeStock").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreSize.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_SIZE, slug: a.slug, id: a.TS_ID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#CashBookNameDate, #CashBookNameDate2").select2( {
        ajax: {
            url:"getCashBookNameList.in", dataType:"json", type:"GET", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.CASHBOOK_NAME, slug: a.slug, id: a.NAMEID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#CashBookNameList, #CashBookNameList2").select2( {
        ajax: {
            url:"getCashBookNameList.in", dataType:"json", type:"GET", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.CASHBOOK_NAME, slug: a.slug, id: a.NAMEID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#branchnamelist, #branchnameLocation").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getBranchListsearch", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.branch_name, slug: e.slug, id: e.branch_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#subscribe, #subscribeDaily").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getUserEmailId_Subscrible", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(e) {
                return {
                    term: e
                }
            }
            , results:function(e) {
                return {
                    results:$.map(e, function(e) {
                        return {
                            text: e.firstName+" "+e.lastName, slug: e.slug, id: e.user_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#CashNameList").select2( {
        ajax: {
            url:"getCashBookNameList.in", dataType:"json", type:"GET", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.CASHBOOK_NAME, slug: a.slug, id: a.NAMEID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#manufacurer").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchTyreModel.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.TYRE_MODEL, slug: a.slug, id: a.TYRE_MT_ID
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#warehouselocation, #Partwarehouselocation").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#Partwarehouselocation2, #Partwarehouselocation3, #PartlocationPO, #PartlocationPO2, #PartlocationPO3, #workOrderLocation, #workOrderLocation2").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getSearchPartLocations.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.partlocation_name, slug: a.slug, id: a.partlocation_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), 
   $("#TyreRetreadVendor, #TyrePurchaseVendor").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getTyreVendorSearchList.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vendorName+" - "+a.vendorLocation, slug: a.slug, id: a.vendorId
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#TyreRetreadStatus, #TyreRetread, #TyreStatus, #TyreType").select2(), $("#OverOverSRSubJOB, #DuesoonSRSubJOB").select2(), $("#IssuesType").select2(), $("#DriverLanguage").select2(), $("#tagPicker").select2( {
        closeOnSelect: !1
    }
    ), $("#subscribeAssign").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getUserEmailId_Assignto", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.firstName+" "+a.lastName, slug: a.slug, id: a.user_email
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#subscribeReport").select2( {
        minimumInputLength:3, minimumResultsForSearch:10, ajax: {
            url:"getUserEmailId_Assignto", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.firstName+" "+a.lastName, slug: a.slug, id: a.user_id
                        }
                    }
                    )
                }
            }
        }
    }
    ), $("#from, #fromRRV, #fromRRG , #from2, #from3").select2( {
        ajax: {
            url:"getRenewalType.in", dataType:"json", type:"GET", contentType:"application/json", data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.renewal_Type, slug: a.slug, id: a.renewal_id
                        }
                    }
                    )
                }
            }
        }
    }
    )
}),
$(function() {
    $("#to, #toRRV, #toRRG, #to2, #to3, #RouteSubList, #RouteList").select2()
}

),
$("#reservation").daterangepicker({format: "YYYY/MM/DD", startDate: "2016/01/01", endDate: "2016/01/02", ranges: "10"}),
$(document).ready(function() {
    $("#OverOverSRJOB").change(function() {
        $.getJSON("getJobSubTypeByTypeId.in", {
            JobType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].Job_Subid+'">'+a[d].Job_ROT_number+" - "+a[d].Job_ROT+"</option>";
            b+="</option>", $("#OverOverSRSubJOB").html(b)
        }
        )
    }
    ), $("#DuesoonSRJOB").change(function() {
        $.getJSON("getJobSubTypeByTypeId.in", {
            JobType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].Job_Subid+'">'+a[d].Job_ROT_number+" - "+a[d].Job_ROT+"</option>";
            b+="</option>", $("#DuesoonSRSubJOB").html(b)
        }
        )
    }
    )
}

),
$(document).ready(function() {
    $("#from").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#to").html(b)
        }
        )
    }
    ), $("#from2").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#to2").html(b)
        }
        )
    }
    ), $("#from3").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#to3").html(b)
        }
        )
    }
    ), $("#fromRRV").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#toRRV").html(b)
        }
        )
    }
    ), $("#fromRRG").change(function() {
        $.getJSON("getRenewalSubTypeChange.in", {
            RenewalType: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='<option value="-1">ALL</option>', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].renewal_Subid+'">'+a[d].renewal_SubType+"</option>";
            b+="</option>", $("#toRRG").html(b)
        }
        )
    },$("#RouteList").change(function() {
        $.getJSON("getTripRouteSubListById.in", {
            vehicleGroup: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
            b+="</option>", $("#RouteSubList").html(b)
        }
        )
    }
    ),$("#TCGroupWise2").change(function() {
        $.getJSON("getTripRouteListById.in", {
            vehicleGroup: $(this).val(), ajax: "true"
        }
        , function(a) {
            for(var b='', c=a.length, d=0;
            c>d;
            d++)b+='<option value="'+a[d].routeID+'">'+a[d].routeNo + " " + a[d].routeName+"</option>";
            b+="</option>", $("#RouteList").html(b)
        }
        )
    }
    )
    )
}

),
$(function() {
    function a(a, b) {
        $("#TyreRetreadRange, #DriAdvDateRange, #ConAdvDateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#TyreRetreadRange , #DriAdvDateRange, #ConAdvDateRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#IssuesReportedRange, #LocWorkOrder, #CJDateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#IssuesReportedRange, #LocWorkOrder, #CJDateRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#IssuesAssignRange, #VehWorkOrder").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#IssuesAssignRange, #VehWorkOrder").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#RenewalComRange, #RenewalComRangeRRG, #RenewalComRangeRRV").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#RenewalComRange, #RenewalComRangeRRG, #RenewalComRangeRRV").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(document).ready(function() {
    $('input[name="SearchRadio"]').click(function() {
        "VEHICLE"==$(this).attr("value")&&($(".HideRadioBox").not(".VEHICLE").hide(), $(".VEHICLE").show()), "GROUP"==$(this).attr("value")&&($(".HideRadioBox").not(".GROUP").hide(), $(".GROUP").show())
    }
    ), $(".HideRadioBox").hide(), $("#fuel_vendor_paymode, #vehicle_Ownership, #Fuel_Group, #Fuel_Ownership").select2(), $("#tagPicker").select2( {
        closeOnSelect: !1
    }
    )
}

),
$(function() {
    function a(a, b) {
        $("#reportrange, #GroupWorkOrder, #GTC_daterange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#reportrange, #GroupWorkOrder, #GTC_daterange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#rangeFuelMileage, #PartInventryRange,#DRTC_daterange, #rangeFuelMileage2 , #rangeFuelMileage3, #rangeFuelMileage4, #rangeFuelMileage5, #rangeFuelMileage6").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#rangeFuelMileage, #PartInventryRange , #DRTC_daterange, #rangeFuelMileage2, #rangeFuelMileage3, #rangeFuelMileage4, #rangeFuelMileage5, #rangeFuelMileage6").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#VehicleFuelRange, #PurchaseDateRange, #DJDateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#VehicleFuelRange, #PurchaseDateRange, #DJDateRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#GroupFuelRange, #VendorPurchaseDate, #VTC_daterange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#GroupFuelRange, #VendorPurchaseDate, #VTC_daterange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#rangeTyrePurchase, #SEdateRange, #SEVehdateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#rangeTyrePurchase, #SEdateRange, #SEVehdateRange").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

),
$(function() {
    function a(a, b) {
        $("#dateRangeReport").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRangeReport").daterangepicker( {
    	format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
}

);

function printJSReport(printedArea) {
    jsreport.download('report.pdf', {
      template: {
          content: document.getElementById('div_print').innerHTML,
          engine: 'none',
          recipe: 'phantom-pdf'
    }});
}