import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    Helper.start();
    const uri = "http://localhost:8080/";

    let data = new Vue({
        el: "#data",
        data: {
            usuarios :[],
            viajes :[],
        }
    });

    let btnUsuarios = document.querySelector("#btn-ver-usuarios");
    let btnCiudades = document.querySelector("#btn-ver-ciudades");

    btnUsuarios.addEventListener("click", getUsuarios);
    btnCiudades.addEventListener("click", getCiudades);

    function getCiudades() {
        fetch(uri + "usuarios/viajes/ciudades",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(viajes => {
            data.viajes = viajes;
            document.querySelector("#tabla-ciudades")
            .classList.remove("oculto");
        })
        .catch(r => {
            console.log(r)
            //Helper.quitarToken();
        });
    }

    function getUsuarios() {
        fetch(uri + "usuarios",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(usuarios => {
            data.usuarios = usuarios;
            document.querySelector("#tabla-usuarios")
            .classList.remove("oculto");
        })
        .catch(r => {
            console.log(r)
            //Helper.quitarToken();
        });
    }

    if (!Helper.sesion.admin) {
        let base = new URL('/', location.href).href;
        location.href =  base + "index.html";
    }

});