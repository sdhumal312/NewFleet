function showoption() {
    var e = $("#renPT_option :selected"),
        t = e.text();
    "Cash" == t ? $("#target1").text(t + " Receipt NO") : "Cheque" == t ? $("#target1").text(t + " NO") : "CREDIT" == t ? $("#target1").text(t + " Transaction NO") : $("#target1").text(t + " Transaction NO")
}
$(document).ready(function() {
    $("#selectVendor").on("change", function() {
        var e = document.getElementById("selectVendor").value;
        if (0 != e) {
            var t = '<option value="CASH"> CASH</option><option value="CREDIT">CREDIT</option>';
            $("#renPT_option").html(t)
        } else {
            var t = '<option value="CASH">CASH</option>';
            $("#renPT_option").html(t)
        }
    }), $("#selectVendor").change(), $("#renPT_option").on("change", function() {
        showoption()
    }), $("#renPT_option").change()
}),
$(document).ready(function() {
    $("#approvalTable").DataTable({
        sScrollX: "100%",
        bScrollcollapse: !0,
        dom: "Bfrtip",
        buttons: ["excel", "print"],
        order: [
            [0, "desc"]
        ]
    })
}), $(document).ready(function() {
    $("#approvalTablePayment").DataTable({
        sScrollX: "100%",
        bScrollcollapse: !0,
        dom: "Bfrtip",
        buttons: ["excel", "print"],
        order: [
            [0, "desc"]
        ]
    })
}), $(document).ready(function() {
    $("#approvalTableAJAX").dataTable({
        bPaginate: !0,
        sAjaxSource: "getApprovalList.in",
        type: "POST",
        cache: !0,
        sAjaxDataProp: "",
        oLanguage: {
            sEmptyTable: "Message On Empty Table"
        },
        sScrollX: "100%",
        bScrollcollapse: !0,
        dom: "Bflrtip",
        buttons: ["excel", "print"],
        order: [
            [3, "desc"]
        ],
        iDisplayLength: 10,
        bSort: !0,
        aoColumnDefs: [{
            aTargets: [0],
            mRender: function(a, e, t) {
                return '<a href="ShowApproval.in?approvalId=' + a + '">A-' + a + "</a>"
            }
        }, {
            aTargets: [4],
            mRender: function(a, e, t) {
                return null == a ? "-" : a
            }
        }, {
            aTargets: [5],
            mRender: function(a, e, t) {
                a = a.toString();
                var n = "";
                a.indexOf(".") > 0 && (n = a.substring(a.indexOf("."), a.length)), a = Math.floor(a), a = a.toString();
                var r = a.substring(a.length - 3),
                    l = a.substring(0, a.length - 3);
                "" != l && (r = "," + r);
                var s = l.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + r;
                return '<i class="fa fa-inr"> ' + s + "</i>"
            }
        }, {
            aTargets: [6],
            mRender: function(a, e, t) {
                return status = a, "APPROVED" == a ? '<span class="label label-pill label-warning">' + a + "</span>" : '<span class="label label-pill label-success">' + a + "</span>"
            }
        }, {
            aTargets: [7],
            mRender: function(a, e, t) {
                return "APPROVED" == status ? '<a class="btn btn-success btn-sm" href="CancelApprovalList.in?approvalId=' + a + '" class="confirmation"onclick="return confirm(\'Are you sure? Payment \')"><span class="fa fa-trash"></span> Delete</a>' : "undefined" == status ? '<div class="btn-group"><a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-cog"></span> <span class="caret"></span></a><ul class="dropdown-menu pull-right"><li><a href="approvedList.in?approvalId=' + a + '" class="confirmation" onclick="return confirm("Are you sure? Approve ")"><span class="fa fa-trash"></span> Approval List </a></li><li><a href="CancelApprovalList.in?approvalId=' + a + '" class="confirmation" onclick="return confirm("Are you sure? Delete ")"><span class="fa fa-trash"></span> Delete</a></li></ul></div>' : "."
            }
        }],
        aoColumns: [{
            mData: "approvalId"
        }, {
            mData: "approvalvendorName"
        }, {
            mData: "approvalvendorType"
        }, {
            mData: "created"
        }, {
            mData: "approvalCreateBy"
        }, {
            mData: "approvalTotal"
        }, {
            mData: "approvalStatus"
        }, {
            mData: "approvalId"
        }],
        fnInitComplete: function(a, e) {}
    }), $("#approvalTablePaymentAJAX").dataTable({
        bPaginate: !0,
        sAjaxSource: "getApprovalList.in",
        sAjaxDataProp: "",
        oLanguage: {
            sEmptyTable: "Message On Empty Table"
        },
        sScrollX: "100%",
        bScrollcollapse: !0,
        dom: "Bflrtip",
        buttons: ["excel", "print"],
        order: [
            [3, "desc"]
        ],
        iDisplayLength: 10,
        bSort: !0,
        aoColumnDefs: [{
            aTargets: [0],
            mRender: function(a, e, t) {
                return '<a href="ShowApprovalPayment.in?approvalId=' + a + '">A-' + a + "</a>"
            }
        }, {
            aTargets: [4],
            mRender: function(a, e, t) {
                return null == a ? "-" : a
            }
        }, {
            aTargets: [5],
            mRender: function(a, e, t) {
                a = a.toString();
                var n = "";
                a.indexOf(".") > 0 && (n = a.substring(a.indexOf("."), a.length)), a = Math.floor(a), a = a.toString();
                var r = a.substring(a.length - 3),
                    l = a.substring(0, a.length - 3);
                "" != l && (r = "," + r);
                var s = l.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + r;
                return '<i class="fa fa-inr"> ' + s + "</i>"
            }
        }, {
            aTargets: [6],
            mRender: function(a, e, t) {
                return status = a, "APPROVED" == a ? '<span class="label label-pill label-warning">' + a + "</span>" : '<span class="label label-pill label-success">' + a + "</span>"
            }
        }, {
            aTargets: [7],
            mRender: function(a, e, t) {
                return "APPROVED" == status ? '<a class="btn btn-success btn-sm" href="approvedPayment.in?approvalId=' + a + '" class="confirmation"onclick="return confirm(\'Are you sure? Payment \')"><span class="fa fa-cog"> Make Payment</span></a>' : "."
            }
        }],
        aoColumns: [{
            mData: "approvalId"
        }, {
            mData: "approvalvendorName"
        }, {
            mData: "approvalvendorType"
        }, {
            mData: "created"
        }, {
            mData: "approvalCreateBy"
        }, {
            mData: "approvalTotal"
        }, {
            mData: "approvalStatus"
        }, {
            mData: "approvalId"
        }],
        fnInitComplete: function(a, e) {}
    }), $("#approvalTableReport").dataTable({
        bPaginate: !0,
        oLanguage: {
            sEmptyTable: "Message On Empty Table"
        },
        sScrollX: "100%",
        bScrollcollapse: !0,
        dom: "Bflrtip",
        buttons: ["excel", "print"],
        order: [
            [3, "desc"]
        ],
        iDisplayLength: 10,
        bSort: !0,
        aoColumnDefs: [{
            aTargets: [5],
            mRender: function(a, e, t) {
                a = a.toString();
                var n = "";
                a.indexOf(".") > 0 && (n = a.substring(a.indexOf("."), a.length)), a = Math.floor(a), a = a.toString();
                var r = a.substring(a.length - 3),
                    l = a.substring(0, a.length - 3);
                "" != l && (r = "," + r);
                var s = l.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + r;
                return '<i class="fa fa-inr"> ' + s + "</i>"
            }
        }],
        fnInitComplete: function(a, e) {}
    })
}), $(document).ready(function() {
    $(document).ready(function() {
        $("#VendorApplovalList tfoot th").each(function() {
            var a = $(this).text();
            switch (a) {
                case "":
                    break;
                case "Closing":
                    break;
                case "Usage":
                    break;
                case "Volume":
                    break;
                case "Amount":
                    break;
                default:
                    $(this).html('<input type="text" class="form-text" placeholder="Search ' + a + '" />')
            }
        });
        var a = $("#VendorApplovalList").DataTable({
            sScrollX: "100%",
            bScrollcollapse: !0,
            dom: "Blfrtip",
            buttons: ["excel", "print"],
            order: [
                [0, !1]
            ],
            lengthMenu: [
                [10, 50, 500, -1],
                [10, 50, 500, "All"]
            ]
        });
        a.columns().every(function() {
            var a = this;
            $("input", this.footer()).on("keyup change", function() {
                a.search() !== this.value && a.search(this.value).draw()
            })
        })
    }), $("#example-select-all").on("click", function() {
        $('input[type="checkbox"]').prop("checked", this.checked)
    }), $("#example tbody").on("change", 'input[type="checkbox"]', function() {
        if (!this.checked) {
            var a = $("#example-select-all").get(0);
            a && a.checked && "indeterminate" in a && (a.indeterminate = !0)
        }
    })
});