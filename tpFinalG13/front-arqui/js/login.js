import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    Helper.start();
    const uri = "http://localhost:8080/";

    let btnIniciar = document.querySelector("#btn-iniciar");

    btnIniciar.addEventListener("click", iniciar);

    async function iniciar(event) {
        event.preventDefault();
        let password = document.querySelector("#password");
        let userName = document.querySelector("#username").value;

        let enviar = {
            "username": userName,
            "password": password.value
        };

        
        let response = await fetch(uri + "usuarios/login",{
            "method": "POST",
            "headers" : { "Content-Type": "application/json" },
            "body": JSON.stringify(enviar)
        });

        if (response.ok) {
            let json = await response.json();
            Helper.guardarToken(json.token);
            location.href = "../html/viajes.html";
            
        } else {
            let error = await response.text();
            if (error == '"El email ya existe"')
                errorEmail();
            else if (error == '"El usuario ya existe"')
                errorUsuario();
        }
    }

});