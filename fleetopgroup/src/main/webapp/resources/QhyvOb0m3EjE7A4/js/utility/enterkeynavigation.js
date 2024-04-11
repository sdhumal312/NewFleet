$('body').on('keydown', 'input, select, textarea, file, checkbox, a, .select2-input', function(e) {
    var self = $(this)
      , form = self.parents('form:eq(0)')
      , focusable
      , next
      ;
    if (e.keyCode == 13) {
        focusable = form.find('input,a,select,button,textarea,file,checkbox, .select2-input').filter(':visible');
        next = focusable.eq(focusable.index(this)+1);
        if (next.length) {
            next.focus();
        } else {
            form.submit();
        }
        return false;
    }
});

jQuery(function($){
	// Suscribe to enter keydown select
	    var current_select2 = null;
	    $(document).on('keydown', '.select2-input', function (ev) {
	        var me = $(this);
	        if (me.data('listening') != 1)
	        {
	            me
	                .data('listening', 1)
	                .keydown(function(ev) {
	                    if ((ev.keyCode ? ev.keyCode : ev.which) == 13)
	                        onKeyEnterSelect2(me, ev);
	                })
	            ;
	        }
	    });

	    function onKeyEnterSelect2(el, ev)
	    {
	        var keycode = (ev.keyCode ? ev.keyCode : ev.which);
	      //  $('#vehicle_group').focus();
	        var focusable = $(this).parents('form:eq(0)').find('input,a,select,button,textarea,file,checkbox, .select2-input').filter(':visible');
	    }
	});