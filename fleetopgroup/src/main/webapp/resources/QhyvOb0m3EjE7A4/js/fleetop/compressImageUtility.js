
const MAX_WIDTH = 780;
const MAX_HEIGHT = 438;
const MIME_TYPE = "image/jpeg";
const QUALITY = .9;
const MAX_SIZE	= 512;

let multiCompressedImage =document.getElementById("multiCompressedImageDiv");
setTimeout(function(){ 
	$('input[type=file]').bind('change', function(ev) {
		if($('#multipleDocumentCompress').val() == 'true'){
			showLayer();
		jQuery('.multiCompressed_'+ev.target.id.split('_')[1]+'').each(function(){ // if image replaced then deleting previous stored base64
			$(this).remove();
		});
		}
		for(var i = 0; i < ev.target.files.length; i++){
			const file = ev.target.files[i]; // get the file
			if(file != undefined){
			const blobURL = URL.createObjectURL(file);
			const img = new Image();
			img.src = blobURL;
			img.onerror = function () {
				URL.revokeObjectURL(this.src);
				// Handle the failure properly
				console.log("Cannot load image");
			};
			img.onload = function () {
				if(file.size > (MAX_SIZE * 1024)) {
					var resized    = resizeMe(img);
					if($('#multipleDocumentCompress').val() == 'true'){
						createInputForImageStore(resized,ev.target.id.split('_')[1]);
					}else{
						$('#compressed_'+ev.target.id.split('_')[1]+'').val(resized);
						downloadImg();
					}
					showMessage('info', 'Image size is greter than allowed we have compressed the image , Please see the preview !')
				}else{
					getBase64(file,ev.target.id.split('_')[1]);
				}
			};
			}
		}
		if($('#multipleDocumentCompress').val() == 'true'){
		setTimeout(() => {
			hideLayer();
		}, 600);
		}
	}
	);
}, 500);

function createInputForImageStore(file,id){
	var newInput = document.createElement("input");
	newInput.type="hidden";
	newInput.name="multiCompressed";
	newInput.setAttribute("class","multiCompressed_"+id+"");
	newInput.value=id+"-"+file;
	multiCompressedImage.appendChild(newInput);
}

function getBase64(file, id) {
	   var reader = new FileReader();
	   reader.readAsDataURL(file);
	   reader.onload = function () {
		   if($('#multipleDocumentCompress').val() == 'true'){
			   createInputForImageStore(reader.result.split(',')[1],id);
		   }else{
			   $('#compressed_'+id+'').val(reader.result.split(',')[1]);
		   }
	   };
	   reader.onerror = function (error) {
	     console.log('Error: ', error);
	   };
}

function dataURLtoFile(dataurl, filename) {
	 
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), 
        n = bstr.length, 
        u8arr = new Uint8Array(n);
        
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    
    return new File([u8arr], filename, {type:mime});
}

function calculateSize(img, maxWidth, maxHeight) {
  let width = img.width;
  let height = img.height;

  // calculate the width and height, constraining the proportions
  if (width > height) {
    if (width > maxWidth) {
      height = Math.round((height * maxWidth) / width);
      width = maxWidth;
    }
  } else {
    if (height > maxHeight) {
      width = Math.round((width * maxHeight) / height);
      height = maxHeight;
    }
  }
  return [width, height];
}

// Utility functions for demo purpose

function displayInfo(label, file) {
  const p = document.createElement('p');
  p.innerText = `${label} - ${readableBytes(file.size)}`;
  document.getElementById('root').append(p);
}

function readableBytes(bytes) {
  const i = Math.floor(Math.log(bytes) / Math.log(1024)),
    sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];

  return (bytes / Math.pow(1024, i)).toFixed(2) + ' ' + sizes[i];
}

function downloadImg() {
	var download = document.getElementById("download");
	var canvas = $('canvas')[0];
	var image = canvas.toDataURL("image/png")
	    .replace("image/png", "image/octet-stream");
	download.setAttribute("href", image);
	$('#downloadButton').show();
}


function resizeMe(img) {
  
  var canvas = document.createElement('canvas');

  var width = img.width;
  var height = img.height;

  // calculate the width and height, constraining the proportions
  if (width > height) {
    if (width > MAX_WIDTH) {
      //height *= MAX_WIDTH / width;
      height = Math.round(height *= MAX_WIDTH / width);
      width = MAX_WIDTH;
    }
  } else {
    if (height > MAX_HEIGHT) {
      //width *= MAX_HEIGHT / height;
      width = Math.round(width *= MAX_HEIGHT / height);
      height = MAX_HEIGHT;
    }
  }
  
  // resize the canvas and draw the image data into it
  canvas.width = width;
  canvas.height = height;
  var ctx = canvas.getContext("2d");
  ctx.drawImage(img, 0, 0, width, height);
  $('#root').append('');
  $('#root').append(canvas); // do the actual resized preview
  
  var result = canvas.toDataURL("image/jpeg",0.9);
  
  var base64 = result.split(',')[1];
  
  return base64; // get the data from canvas as 70% JPG (can be also PNG, etc.)

}