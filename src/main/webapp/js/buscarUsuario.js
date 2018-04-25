function buscarUsuario( event ){
	//console.log('buscarUsuario: click %o', event);
	var nombreBuscar = event.target.value;
	console.log('nombre %s', nombreBuscar);
	
	var select = document.getElementById('sUsuarios');
	
	// Si he vaciado la busqueda, pongo el select a 0
	if ("" == nombreBuscar){
		select.innerHTML = '<option value="">No hay Datos</option>';
	}else{	
	
	var url = "api/usuario?nombre=" + nombreBuscar;	  			
	var options = "";
	
	//eliminar options antiguas
	select.innerHTML = "";
	
	//llamada Ajax
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		//llamada terminada y correcta
	    if (this.readyState == 4 && this.status == 200) {
	    	var data = JSON.parse(this.responseText);
	        console.log('retorna datos %o', data);
	        data.forEach( el => {
	        	options += '<option value="'+ el.id + '">'+el.nombre+'</option>';
	        });
	        select.innerHTML = options;
	   } else if(this.readyState == 4 && this.status == 204){
		 select.innerHTML = '<option value="">No hay Datos</option>';
	   }
	};
	xhttp.open("GET", url , true);
	    xhttp.send();  			
		}
}