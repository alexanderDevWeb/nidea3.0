// Librería pra realizar llamadas AJAX XMLHttpRequest
// en Vanilla JS

// Retorna un Promise

console.log("Librería cargada");



function ajax(method, url, data = null) {

    // devuelve una promise
    return new Promise(function (resolve, reject) {

        let request;       
        
        // Soporte para navegadores
        if (window.XMLHttpRequest) { // Chrome, safari..
            request = new XMLHttpRequest();
        } else if (window.ActiveXObject) { // IE
            request = new ActiveXObject("Msxml2.XMLHTTP");
        } else {
            reject("Tu navegador no es compatible con llamdas AJAX")
        }

        // Comprobar cambios de estado
        request.onreadystatechange = function(){
            if (request.readyState == 4){
                if( request.status >= 200 && request.status <= 208){
                    resolve(request);
                } else {
                    reject(Error(request.statusText));       
                }
            }        
        };

        request.open(method, url, true);
        request.setRequestHeader("Content-type", "application/json");
        request.send(data);
    })

    /*
    return new Promise(
                resolve => {
                    setTimeout(function() {
                        console.log('retorno Ajax');
                        resolve(frutas);
                    }, 1000)
                } // resolve
            ) // Promise
    */
    
    
    
}
