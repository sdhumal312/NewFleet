function toggleSingle(a) {
    var b = document.getElementById(a);
    "block" == b.style.display ? b.style.display = "none" : b.style.display = "block"
}

function HideValue(a, b, c, d) {
    document.getElementById(a), document.getElementById(b), document.getElementById(c), document.getElementById(d)
}

function toggle2(a, b, c, d) {
    var e = document.getElementById(a),
        f = document.getElementById(b),
        g = document.getElementById(c),
        h = document.getElementById(d);
    "block" == e.style.display ? e.style.display = "none" : e.style.display = "block", "block" == f.style.display ? f.style.display = "none" : f.style.display = "block", "block" == g.style.display ? g.style.display = "none" : g.style.display = "block", "block" == h.style.display ? h.style.display = "none" : h.style.display = "block"
}
$("#AxleDual1").click(function() {
    $(this).is(":checked") ? ($("#Dual1_LI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="LI-1">'), $("#Dual1_RI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="RI-1">'), $("#Single1_LI").hide(), $("#Single1_RI").hide()) : ($("#Dual1_LI").html(""), $("#Dual1_RI").html(""), $("#Single1_LI").show(), $("#Single1_RI").show())
}), $("#Axle1Stepney").click(function() {
    $(this).is(":checked") ? $("#Stepney").html('<div class="stepney" ></div> <input type="hidden" name="POSITION" value="ST-1">') : $("#Stepney").html("")
}), $("#AxleDual2").click(function() {
    $(this).is(":checked") ? ($("#Dual2_LI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="LI-2">'), $("#Dual2_RI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="RI-2">'), $("#Single2_LI").hide(), $("#Single2_RI").hide()) : ($("#Dual2_LI").html(""), $("#Dual2_RI").html(""), $("#Single2_LI").show(), $("#Single2_RI").show())
}), $("#AxleDual3").click(function() {
    $(this).is(":checked") ? ($("#Dual3_LI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="LI-3">'), $("#Dual3_RI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="RI-3">'), $("#Single3_LI").hide(), $("#Single3_RI").hide()) : ($("#Dual3_LI").html(""), $("#Dual3_RI").html(""), $("#Single3_LI").show(), $("#Single3_RI").show())
}), $("#AxleDual4").click(function() {
    $(this).is(":checked") ? ($("#Dual4_LI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="LI-4">'), $("#Dual4_RI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="RI-4">'), $("#Single4_LI").hide(), $("#Single4_RI").hide()) : ($("#Dual4_LI").html(""), $("#Dual4_RI").html(""), $("#Single4_LI").show(), $("#Single4_RI").show())
}), $("#AxleDual5").click(function() {
    $(this).is(":checked") ? ($("#Dual5_LI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="LI-5">'), $("#Dual5_RI").html('<div class="tyre"></div><input type="hidden" name="POSITION" value="RI-5">'), $("#Single5_LI").hide(), $("#Single5_RI").hide()) : ($("#Dual5_LI").html(""), $("#Dual5_RI").html(""), $("#Single5_LI").show(), $("#Single5_RI").show())
}), $(document).ready(function() {
    $("#AxleSelect").change(function() {
        
          var  selected = $(this).val();
        
        if(selected == 1){
        	$('#Axle1').show();
        	$('#Axle2').hide();
        	$('#Axle3').hide();
        	$('#Axle4').hide();
        	$('#Axle5').hide();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }else if(selected == 2){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').hide();
        	$('#Axle4').hide();
        	$('#Axle5').hide();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }else if(selected == 3){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').hide();
        	$('#Axle5').hide();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }else if(selected == 4){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').show();
        	$('#Axle5').hide();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }else if(selected == 5){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').show();
        	$('#Axle5').show();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        	
        }else if(selected == 6){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').show();
        	$('#Axle5').show();
        	$('#Axle6').show();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }else if(selected == 7){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').show();
        	$('#Axle5').show();
        	$('#Axle6').show();
        	$('#Axle7').show();
        	$('#Axle8').hide();
        }else if(selected == 8){
        	$('#Axle1').show();
        	$('#Axle2').show();
        	$('#Axle3').show();
        	$('#Axle4').show();
        	$('#Axle5').show();
        	$('#Axle6').show();
        	$('#Axle7').show();
        	$('#Axle8').show();
        }else{
        	$('#Axle1').hide();
        	$('#Axle2').hide();
        	$('#Axle3').hide();
        	$('#Axle4').hide();
        	$('#Axle5').hide();
        	$('#Axle6').hide();
        	$('#Axle7').hide();
        	$('#Axle8').hide();
        }
        
       /* switch (i) {
            case "1":
                "block" == a.style.display ? a.style.display = "block" : a.style.display = "block", "block" == c.style.display ? c.style.display = "none" : c.style.display = "none", "block" == d.style.display ? d.style.display = "none" : d.style.display = "none", "block" == e.style.display ? e.style.display = "none" : e.style.display = "none", "block" == f.style.display ? f.style.display = "none" : f.style.display = "none";
                break;
            case "2":
                "block" == a.style.display ? a.style.display = "block" : a.style.display = "block", "block" == c.style.display ? c.style.display = "block" : c.style.display = "block", "block" == d.style.display ? d.style.display = "none" : d.style.display = "none", "block" == e.style.display ? e.style.display = "none" : e.style.display = "none", "block" == f.style.display ? f.style.display = "none" : f.style.display = "none";
                break;
            case "3":
                "block" == a.style.display ? a.style.display = "block" : a.style.display = "block","block" == c.style.display ? c.style.display = "block" : c.style.display = "block", "block" == d.style.display ? d.style.display = "block" : d.style.display = "block", "block" == e.style.display ? e.style.display = "none" : e.style.display = "none", "block" == f.style.display ? f.style.display = "none" : f.style.display = "none";
                break;
            case "4":
                "block" == a.style.display ? a.style.display = "block" : a.style.display = "block", "block" == c.style.display ? c.style.display = "block" : c.style.display = "block", "block" == d.style.display ? d.style.display = "block" : d.style.display = "block", "block" == e.style.display ? e.style.display = "block" : e.style.display = "block", "block" == f.style.display ? f.style.display = "none" : f.style.display = "none";
                break;
            case "5":
                "block" == a.style.display ? a.style.display = "block" : a.style.display = "block", "block" == c.style.display ? c.style.display = "block" : c.style.display = "block", "block" == d.style.display ? d.style.display = "block" : d.style.display = "block", "block" == e.style.display ? e.style.display = "block" : e.style.display = "block", "block" == f.style.display ? f.style.display = "block" : f.style.display = "block";
                break;
            default:
                "block" == a.style.display ? a.style.display = "none" : a.style.display = "none", "block" == c.style.display ? c.style.display = "none" : c.style.display = "none", "block" == d.style.display ? d.style.display = "none" : d.style.display = "none", "block" == e.style.display ? e.style.display = "none" : e.style.display = "none", "block" == f.style.display ? f.style.display = "none" : f.style.display = "none"
        }*/
    })
});
$(document).ready(function() {
    $("#tyreSize").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
                            text: a.TYRE_SIZE,
                            slug: a.slug,
                            id: a.TS_ID
                        }
                    })
                }
            }
        }
    }), $("#tyreSizeTwo").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchTyreSize.in?Action=FuncionarioSelect2",
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
                            text: a.TYRE_SIZE,
                            slug: a.slug,
                            id: a.TS_ID
                        }
                    })
                }
            }
        }
    }),$("#batteryCapacityId").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getSearchBatteryCapacity.in?Action=FuncionarioSelect2",
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
                            text: a.batteryCapacity,
                            slug: a.slug,
                            id: a.batteryCapacityId
                        }
                    })
                }
            }
        }
    })

});