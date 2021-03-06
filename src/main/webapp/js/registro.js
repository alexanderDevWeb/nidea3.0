// Comprobación de usuario al introducir 5 caracteres en el nombre
document.getElementById('usuario').addEventListener('input', function(event) {	
	
	// Valor a comprobar
	var nombreBuscar = event.target.value;
	
	if (nombreBuscar.length >= 5){
		// URL de la API
		var url = "api/registro?nombre=" + nombreBuscar;	  		
			
		// Hago la llamada, FUNCION LOCAL DEL ARCHIVO
		//llamadaAjax(url, "nombre");
		
		
		let promesa = ajax("GET", url);
		
		promesa.then(
			 result => {
                    console.log(result);
                    if(result.status == "200"){ // Usuario Existente
                    	document.querySelector('#usuario').style.border = "1px solid red";
        	    		document.querySelector('#msgNombre').innerHTML = "Nombre Existente";
        	    		document.querySelector('#msgNombre').classList.replace("text-success", "text-danger");
        	    		document.querySelector('#btnRegistro').disabled = true;
                    }else if(result.status == "204"){ // Usuario Libre
                    	document.querySelector('#usuario').style.border = "1px solid green";
                    	document.querySelector('#msgNombre').classList.replace("text-danger", "text-success");
             		   document.querySelector('#msgNombre').innerHTML = "Nombre Libre!!";
             		  document.querySelector('#btnRegistro').disabled = false;
                    }
			 }
		).catch(
			 error => {
	                console.log(error)
	         }	
		);
		
		
	}else {
		document.querySelector('#msgNombre').innerHTML = "";
		document.querySelector('#usuario').style.border = "none";
	}	
});

//Comprobación de usuario al hacer onblur en el email
document.getElementById('email').addEventListener('blur', function(event) {
	
	// Valor a comprobar
	var emailBuscar = event.target.value;		
	
	if(emailBuscar){
		// URL de la API
		var url = "api/registro?email=" + emailBuscar;	  		
			
		// Hago la llamada, FUNCION LOCAL DEL ARCHIVO YA NO SE USA 
		let promesa = ajax("GET", url);
		
		promesa.then(
				 result => {
	                    console.log(result);
	                    if(result.status == "200"){ // Usuario Existente
	                    	document.querySelector('#email').style.border = "1px solid red";
	        	    		document.querySelector('#msgEmail').innerHTML = "Email Existente";
	        	    		document.querySelector('#msgEmail').classList.replace("text-success", "text-danger");
	        	    		document.querySelector('#btnRegistro').disabled = true;
	                    }else if(result.status == "204"){ // Usuario Libre
	                    	document.querySelector('#email').style.border = "1px solid green";
	                    	document.querySelector('#msgEmail').classList.replace("text-danger", "text-success");
	             		   document.querySelector('#msgEmail').innerHTML = "Email Libre!!";
	             		  document.querySelector('#btnRegistro').disabled = false;
	                    }
				 }
			).catch(
				 error => {
		                console.log(error)
		         }	
			);
		
	}else{
		document.querySelector('#msgEmail').innerHTML = "";
		document.querySelector('#email').style.border = "none";
	}		
});


// Funcion para la llamada
function llamadaAjax(url, field){
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		//llamada terminada y correcta
	    if (this.readyState == 4 && this.status == 200) {
	    	var data = this.responseText;
	    	console.log(data);
	    	
	    	// Si es true NO permito registrarse
	    	
	    	if(field == "nombre"){
	    		document.querySelector('#usuario').style.border = "1px solid red";
	    		document.querySelector('#msgNombre').innerHTML = "Nombre Existente";
	    	}else{
	    		document.querySelector('#email').style.border = "1px solid red";
	    		document.querySelector('#msgEmail').innerHTML = "Email Existente";
	    	}
	    	document.querySelector('#btnRegistro').disabled = true;
	    	   
	   } else if (this.readyState == 4 && this.status == 204){
		   var data = this.responseText;
	    	console.log(data);
	    	
	    if(field == "nombre"){
		   document.querySelector('#usuario').style.border = "1px solid green";
		   document.querySelector('#msgNombre').innerHTML = "";
	    } else {
	    	document.querySelector('#email').style.border = "1px solid green";
	    	document.querySelector('#msgEmail').innerHTML = "";
	    }
	    document.querySelector('#btnRegistro').disabled = false;
	   }
	};
	xhttp.open("GET", url , true);
	xhttp.send();
	
}


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
