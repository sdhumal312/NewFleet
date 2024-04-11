function showAlert(type, msg, yourClass) {
	if(yourClass == undefined || yourClass == null || yourClass.trim() == ''){
		yourClass = 'swal-wide';
	}
	Swal.fire({
		  position: 'center',
		  icon: type,
		  title: msg,
		  showConfirmButton: false,
		  timer: 5000,
		  customClass:yourClass,
		  showCloseButton:true
		})
}


