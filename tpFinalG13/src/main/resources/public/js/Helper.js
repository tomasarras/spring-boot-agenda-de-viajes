export default class Helper {

    static sesion = new Vue({
        el: "#header-nav",
        data: {
            logeado : false,
            admin : false
        }
    });

    static start() {
        let token = this.getToken();

        if (token) {
            Helper.sesion.logeado = true;
            let decode = this.parseJwt(token);
            let roles = decode.authorities;
            for (let i = 0; i < roles.length; i++) {
                if (roles[i] == "ROLE_ADMIN") {
                    Helper.sesion.admin = true;
                }
            }
        }

        let btnCerrarSesion = document.querySelector("#logout");
        btnCerrarSesion.addEventListener("click",this.quitarToken);
    }

    static parseJwt (token) {
        var base64Url = token.split('.')[1];
        var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
    
        return JSON.parse(jsonPayload);
    };

    static comprobarInputsVacios(callback,btn) {
        
        let sonInputs = NodeList.prototype.isPrototypeOf(btn);
        let inputs;

        if (!sonInputs) {
            let elementoEncontrado = false;
            let elemento = btn;
            while (!elementoEncontrado) {
                elemento = elemento.parentElement;
                if (elemento.classList.contains("completar-campos")) {
                    elementoEncontrado = true;
                }
            }
            
            inputs = elemento.querySelectorAll(".campo-vacio");
        } else {
            inputs = btn;
        }
        
        let error = false;

        for (let i = 0; i < inputs.length; i++) {
            if (inputs[i].value == '') {
                error = true;
                inputs[i].classList.add("is-invalid");
                inputs[i].addEventListener("focus",()=>{
                    inputs[i].classList.remove("is-invalid");
                });
            }
        }

        if (!error)
            callback();
    }

    static guardarToken(token) {
        localStorage.setItem("token",token);
    }

    static getToken() {
        return localStorage.getItem("token");
    }


    static quitarToken() {
        localStorage.removeItem("token");
        location.href = "../html/viajes.html";
    }
}