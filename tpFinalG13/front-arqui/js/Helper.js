export default class Helper {

    static start() {
        let sesion = new Vue({
            el: "#header-nav",
            data: {
                logeado : false
            }
        });
    
        let token = this.getToken();

        if (token) {
            sesion.logeado = true;
        }

        let btnCerrarSesion = document.querySelector("#logout");
        btnCerrarSesion.addEventListener("click",this.quitarToken);
    }

    comprobarInputsVacios(event, callback) {
        let error = false;

        let inputs = document.querySelectorAll(".campo-vacio");
        inputs.forEach(input => {
            input.classList.remove("is-invalid");
            if (input.value == '') {
                input.classList.add("is-invalid");
                error = true;
                event.preventDefault();
            }

            input.addEventListener("click", () => input.classList.remove("is-invalid"));

        });

        if (!error)
            callback();
    }

    static guardarToken(token) {
        localStorage.setItem("token",token);
        //document.cookie = "token=" + token;
    }

    static getToken() {
        /*let cookies = document.cookie.split(";");
        let i = 0; 
        let token;
        while ( i < cookies.length) {
            if (cookies[i].search("token") == 0) {
                token = cookies[i].split("=")[1];
                i = cookies.length;
            }
            i++;
        }*/
        return localStorage.getItem("token");
    }


    static quitarToken() {
        /*document.cookie = "token=;";
        let lista = document.cookie.split(";");
        for (let i = 0; i < lista.length; i++) {
            let igual = lista[i].indexOf("=");
            let nombre = lista[i].substring(0,igual);
            lista[i] = nombre+"=;";
            document.cookie = lista[i]
        }*/
        localStorage.removeItem("token");
        location.href = "../html/login.html";
    }
}