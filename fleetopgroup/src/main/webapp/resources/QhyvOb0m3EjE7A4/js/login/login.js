function validate() {
    return "" == document.f.j_username.value && "" == document.f.j_password.value && "" == document.f.companyCode.value ? (alert("${noUser} & ${noPass} & ${noCompany}"), 
    		document.f.j_username.focus(), !1) : "" == document.f.j_username.value ? (alert("${noUser}"), 
    		document.f.j_username.focus(), !1) : "" == document.f.j_password.value ? (alert("${noPass}"),
    		document.f.j_username.focus(), !1) : "" == document.f.companyCode.value ? (alert("${noCompany}"),
    		document.f.j_password.focus(), !1) : void 0
}
var EmailDomainSuggester = {
    domains: ["srstravels.xyz", "fleetop.in"],
    bindTo: $("#email"),
    init: function() {
        this.addElements(), this.bindEvents()
    },
    addElements: function() {
        this.datalist = $("<datalist />", {
            id: "email-options"
        }).insertAfter(this.bindTo), this.bindTo.attr("list", "email-options")
    },
    bindEvents: function() {
        this.bindTo.on("keyup", this.testValue)
    },
    testValue: function(t) {
        var i = $(this).val(); - 1 != i.indexOf("@") ? (i = i.split("@")[0], EmailDomainSuggester.addDatalist(i)) : EmailDomainSuggester.datalist.empty()
    },
    addDatalist: function(t) {
        var i, a = "";
        for (i = 0; i < this.domains.length; i++) a += "<option value='" + t + "@" + this.domains[i] + "'>";
        this.datalist.html(a)
    }
};
EmailDomainSuggester.init();

$(document).ready(function() {
	if(window.location.href == 'https://fleetop.com/login.html' || window.location.href == 'https://fleetop.com' || window.location.href == 'https://fleetop.com/invalidSession.html#!' || window.location.href == 'https://fleetop.com/invalidSession.html' || window.location.href == 'https://www.fleetop.com/login.html' || window.location.href == 'www.https://fleetop.com' || window.location.href == 'https://www.fleetop.com/invalidSession.html#!' || window.location.href == 'https://www.fleetop.com/invalidSession.html'){
		window.location.replace("https://fleetop.com/contactus.in");
	}
});