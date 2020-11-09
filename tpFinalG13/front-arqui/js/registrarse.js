import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    Helper.start();
    const uri = "http://localhost:8080/";

    let btnRegistrarse = document.querySelector("#btn-registrarse");

    btnRegistrarse.addEventListener("click", registrarse);

    async function registrarse(event) {
        event.preventDefault();
        let password1 = document.querySelector("#password-1");
        let password2 = document.querySelector("#password-2");

        let email = document.querySelector("#email");
        if (email.value.search("@") != -1) {
            event.preventDefault();

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
                    if (error == '"El email ya existe"')
                        errorEmail();
                    else if (error == '"El usuario ya existe"')
                        errorUsuario();
                }


            } else
                password2.classList.add("is-invalid");
        }
    }

});