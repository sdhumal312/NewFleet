var showReferenceCol = true;
function visibility(e, n) {
    var t = document.getElementById(e),
        l = document.getElementById(n);
    "block" == t.style.display ? (t.style.display = "none", l.style.display = "block") : (t.style.display = "block", l.style.display = "none")
}
$(document).ready(function() {
    $("#selectVIDTOFuel").on("change", function() {
        var e = "",
            n = $(this).val();
        $("#vehicle_MeterINVehicle option").each(function() {
            n == $(this).val() && (e = $(this).text())
        }), document.getElementById("fuel_meter").placeholder = e, document.getElementById("fuel_meter_old").value = e, $("#to1").val(e), $("#hidden").hide()
    }), $("#selectVIDTOFuel").on("change", function() {
        var e = "",
            n = "",
            t = $(this).val(),
            name = $('#fuelTypeName').val();
        	
        $("#vehicle_FuelINVehicle option").each(function() {
            t == $(this).val() && (e = $(this).text())
            
        });
        var l = []; var abc = [];
        l = e.split(",");
        abc = name.split(",");
        for (var a = l.length, i = 0; a > i; i++) n += '<option value="' + l[i] + '" >' + abc[i] + "</option>";
        n += "</option>", $("#fuel_type").html(n), $("#to1").val(e), $("#hidden").hide()
    }), $("#selectVIDTOFuel").on("change", function() {
        var e = "",
            n = $(this).val();
        $("#vehicle_GroupINVehicle option").each(function() {
            n == $(this).val() && (e = $(this).text())
        }), document.getElementById("vehicle_group").value = e, $("#hidden").hide()
    })
}), $(document).ready(function() {
    $("#selectVendor").on("change", function() {
        var e = $("#selectVendor").val();
        var selectAutoCredit = $('#selectAutoCredit').val();
        if(e > 0){
        	if(selectAutoCredit == 'true' || selectAutoCredit == true){
        		document.getElementById('paymentTypeCreditId').checked = true;
        		document.getElementById('paymentTypeId').checked = false;
        		$("#creditlebel").addClass('active');
        		
        	}else{
        		$("#debitlebel").addClass('active');
        		if(document.getElementById('paymentTypeId') != null)
        			document.getElementById('paymentTypeId').checked = true;
        	}
        }else{
        	if(document.getElementById('paymentTypeId') != null)
        		document.getElementById('paymentTypeId').checked = true;
        }
        e > 0 ? ($("#editOption").show(), $("#selectVen").hide(), $("#ShowOtherPayOption").show(), $("#PayModeOption").hide()) : ($("#editOption").hide(), $("#PayModeOption").show(), $("#ShowOtherPayOption").hide())
    }), $("#editOption").hide(), $("#ShowOtherPayOption").hide()
})/*, $(document).ready(function() {
    $("#FuelSelectVehicle, #TRIPFUELADD").change(function() {
        $.getJSON("getFuelVehicleOdoMerete.in", {
            FuelvehicleID: $(this).val(),
            ajax: "true"
        }, function(e) {
        	
            var n = "",
                t = "",
                l = "";
            n = e.vehicle_Odometer, l = e.vehicle_Group, document.getElementById("fuel_meter").placeholder = n, document.getElementById("fuel_meter_old").value = n, document.getElementById("vehicle_group").value = l;
            $('#vehicleGroupId').val(e.vehicleGroupId);
            $('#vehicle_Odometer').val(e.vehicle_Odometer);
            $('#vehicle_ExpectedOdameter').val(e.vehicle_ExpectedOdameter);
            $('#fuel_meter').val(e.vehicle_Odometer);
            if(!showReferenceCol) {
            	$('#fuel_reference').val(e.vehicle_Odometer);
            }
            var a = [];
            var fuelTypeId = [];
            a = e.vehicle_Fuel.split(",");
            fuelTypeId = e.vehicleFuelId.split(",");
            
            for (var i = a.length, o = 0; i > o; o++) t += '<option value="' + fuelTypeId[o] + '" >' + a[o] + "</option>";
            t += "</option>", $("#fuel_type").html(t)
            
            $('#gpsOdometer').val(e.gpsOdameter);
            
        })
    })
})*/, $(document).ready(function() {
    $("#payMethod").on("change", function() {
        alert("slid")
    }), $("#payMethod").click(function() {
        alert($("#payMethod").val())
    }), $("#payMethod").change(function() {
        alert("Toggle: " + $(this).prop("checked"))
    }), $("input[type=checkbox][name=payMethod]").change(function() {
        alert("help")
    })
}), $(document).ready(function() {
    $("#vendorEnter").hide(), $("#driverEnter").hide()
});