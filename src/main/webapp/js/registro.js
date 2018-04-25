// Comprobación de usuario al hacer onblur en el nombre
document.querySelector('#usuario').addEventListener('blur', function(event) {
	
	// Valor a comprobar
	var nombreBuscar = event.target.value;
	
	// URL de la API
	var url = "api/registro?nombre=" + nombreBuscar;	  		
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		//llamada terminada y correcta
	    if (this.readyState == 4 && this.status == 200) {
	    	var data = this.responseText;
	    	
	    	// Si es true NO permito registrarse	    	
    		document.querySelector('#usuario').style.border = "1px solid red";
    		document.querySelector('#btnRegistro').disabled = true;
	    	   
	   } else if (this.readyState == 4 && this.status == 204){
		   document.querySelector('#usuario').style.border = "1px solid green";
   			document.querySelector('#btnRegistro').disabled = false;
	   }
	};
	xhttp.open("GET", url , true);
	xhttp.send();
});




// Al submitar, comprobar que el nombre sea correcto y lso passwords iguales
document.querySelector('#formRegistro').addEventListener('submit', function(event){
	
	// Limpio el mensaje cada vez que submito
	document.querySelector('#msgPass').innerHTML = "";
		
		console.log("submitando");
		
		var pass= document.querySelector('#password');
		var cPass = document.querySelector('#cPassword');			
		
		if ( pass.value != cPass.value){
			cPass.focus();
			document.querySelector('#msgPass').innerHTML = "Las contraseñas no coinciden";
			event.preventDefault();
		}	
		
})
