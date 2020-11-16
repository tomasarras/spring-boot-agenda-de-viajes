import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    Helper.start();
    const uri = "http://localhost:8080/";

    let btnRegistrarse = document.querySelector("#btn-registrarse");

    btnRegistrarse.addEventListener("click", (e)=>{
        e.preventDefault();
        Helper.comprobarInputsVacios(registrarse,btnRegistrarse)
    });

    async function registrarse() {
        let password1 = document.querySelector("#password-1");
        let password2 = document.querySelector("#password-2");
        let email = document.querySelector("#email");

        

        if (password1.value === password2.value && password1.value != '') {
            let userName = document.querySelector("#username").value;

            let enviar = {
                "username": userName,
                "email": email.value,
                "password": password1.value,
            };

            
            let response = await fetch(uri + "usuarios/registrar",{
                "method": "POST",
                "headers" : { "Content-Type": "application/json" },
                "body": JSON.stringify(enviar)
            });

            if (response.ok) {
                let json = await response.json();
                console.log(json.token);
                Helper.guardarToken(json.token);
                location.href = "html/viajes.html";
            } else {
                let error = await response.text();
                if (error == 'El email ya esta registrado')
                    errorEmail();
                else if (error == 'El username ya esta registrado')
                    errorUsuario();
            }


        } else
            password2.classList.add("is-invalid");
    }

    function errorEmail() {
        let divMensaje = document.querySelector("#email-existente");
        let errorEmailVacio = divMensaje.previousElementSibling;
        errorEmailVacio.classList.add("oculto");
        divMensaje.classList.remove("oculto");
        let input = document.querySelector("#email");
        input.classList.add("is-invalid")
        input.addEventListener("focus",()=>{
            divMensaje.classList.add("oculto");
            input.classList.remove("is-invalid");
            errorEmailVacio.classList.remove("oculto");
        });
    }

    function errorUsuario() {
        let divMensaje = document.querySelector("#usuario-existente");
        let errorEmailVacio = divMensaje.previousElementSibling;
        errorEmailVacio.classList.add("oculto");
        divMensaje.classList.remove("oculto");
        let input = document.querySelector("#username");
        input.classList.add("is-invalid")
        input.addEventListener("focus",()=>{
            divMensaje.classList.add("oculto");
            input.classList.remove("is-invalid");
            errorEmailVacio.classList.remove("oculto");
        });
    }

    if (Helper.sesion.logeado) {
        location.href = "html/viajes.html";
    }

});