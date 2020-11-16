import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    Helper.start();
    const uri = "http://localhost:8080/";

    let btnIniciar = document.querySelector("#btn-iniciar");

    btnIniciar.addEventListener("click", (e)=>{
        e.preventDefault();
        Helper.comprobarInputsVacios(iniciar,btnIniciar);
    });

    async function iniciar() {
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
            let error = document.querySelector("#incorrectos");
            error.classList.remove("oculto");
            let inputs = document.querySelectorAll("input");
            inputs.forEach(input =>{
                input.addEventListener("focus",()=>{
                    error.classList.add("oculto");
                });
            });
        }
    }

    if (Helper.sesion.logeado) {
        location.href = "html/viajes.html";
    }

});