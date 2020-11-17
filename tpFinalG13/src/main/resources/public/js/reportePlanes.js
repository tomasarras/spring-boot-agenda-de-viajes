import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    const uri = "http://localhost:8080/";
    let pendientes = false;
    let realizados = false;
    let zona = false;
    let fecha = false;

    let data = new Vue({
        el: "#data",
        data: {
            planesRealizados : [],
            planesPendientes : [],
            planesZona : [],
            planesFecha : [],
        },
        methods: {
            borrarPlan : function(event) {
                let btnBorrar = event.target.parentElement;
                borrarPlan(btnBorrar.getAttribute("name"));
            },
            editarPlan : async function (event) {
                let btnEditar = event.target;
                let ul = btnEditar.parentElement.previousElementSibling;

                let modificar = async function () {
                    let ul = btnEditar.parentElement.previousElementSibling;
                    let idPlan = btnEditar.getAttribute("name");
                    let inputs = ul.querySelectorAll("input");
                    let json = planToJson(inputs);
                    let type = ul.querySelector(".type").getAttribute("name");
                    json.type = type;
                    json.nombre = ul.parentElement.parentElement.previousElementSibling.querySelector(".input-editar-nombre").value;
                    let response = await fetch(uri + "usuarios/viajes/planes/"+idPlan,{
                        "method": "PUT",
                        "headers": {
                            "Content-Type": "application/json",
                            "Authorization": Helper.getToken()
                        },
                        "body" : JSON.stringify(json)
                    });

                    if (response.ok) {
                        location.reload();
                    }
                }

                Helper.comprobarInputsVacios(modificar,ul.querySelectorAll("input.campo-vacio"));
            },
            abrirLista : function (event) {
                let element = event.target.parentElement;
                if (element.nodeName == "H2") {
                    element = element.parentElement;
                }
                try {
                    element.nextElementSibling.classList.toggle("active");
                } catch {}
            },
            mostrarEditarPlan : function (event) {
                let parent = event.target.parentElement;
                let divInicial = parent.parentElement.parentElement;
                let btnEditar = divInicial.querySelector(".btn-editar-plan");
                let btnCancelar = divInicial.querySelector(".btn-cancelar");
                btnEditar.classList.remove("oculto");
                btnCancelar.classList.remove("oculto");
                let nombrePlan = divInicial.querySelector(".nombre-plan");
                let inputPlan = divInicial.querySelector(".input-plan");
                nombrePlan.classList.add("oculto");
                inputPlan.classList.remove("oculto");

                parent.parentElement.nextElementSibling.classList.add("active");
                parent.classList.add("oculto");
                let contenido = parent.parentElement.nextElementSibling.firstChild.firstChild.children;
                for (let i = 0; i < contenido.length; i++) {
                    let content = contenido[i].children[0].children;
                    if (content[0].classList.contains("type")) {
                        content[0].parentElement.parentElement.classList.add("oculto");
                    } else {
                        content[0].classList.toggle("oculto");
                        content[1].classList.toggle("oculto");
                    }
                }
            },
            cancelarEdicion : function (event) {
                let contenido = event.target.parentElement.parentElement.firstChild.children;
                for (let i = 0; i < contenido.length; i++) {
                    let content = contenido[i].children[0].children;
                    if (content[0].classList.contains("type")) {
                        content[0].parentElement.parentElement.classList.remove("oculto");
                    } else {
                        content[0].classList.toggle("oculto");
                        content[1].classList.toggle("oculto");
                    }
                }

                let divInicial = event.target.parentElement.parentElement.parentElement.parentElement;
                let nombrePlan = divInicial.querySelector(".nombre-plan");
                let inputPlan = divInicial.querySelector(".input-plan");
                nombrePlan.classList.remove("oculto");
                inputPlan.classList.add("oculto");
                let btnEditar = divInicial.querySelector(".btn-editar-plan");
                let btnCancelar = divInicial.querySelector(".btn-cancelar");
                btnEditar.classList.add("oculto");
                btnCancelar.classList.add("oculto");
                divInicial.querySelector(".btns-editar-js").classList.remove("oculto");
            },
            getType(tipo) {
                currentType = tipo;
                let tipos = document.querySelector(".select-tipo-plan").children;
                for (let i = 0; i < tipos.length; i++) {
                    if (tipos[i].value == tipo) {
                        return tipos[i].innerHTML;
                    }
                }
            },
            formatDate(date) {
                let year = date.substr(0,4);
                let month = date.substr(5,2);
                let day = date.substr(8,2);
                if (day < 10) {
                    day = day.slice(-1);
                }
                
                switch (month) {
                    case "01": month = "enero"; break;
                    case "02": month = "febrero"; break;
                    case "03": month = "marzo"; break;
                    case "04": month = "abril"; break;
                    case "05": month = "mayo"; break;
                    case "06": month = "junio"; break;
                    case "07": month = "julio"; break;
                    case "08": month = "agosto"; break;
                    case "09": month = "septiembre"; break;
                    case "10": month = "octubre"; break;
                    case "11": month = "noviembre"; break;
                    case "12": month = "diciembre"; break;
                }
                
                
                return day + " de " + month + " de " + year;
               
            },
            getPrettyKey(key) {
                let keys = getKeysFromType(currentType);
                for (let i = 0; i < keys.length; i++) {
                    if (keys[i].children[0].getAttribute("for") == key) {
                        return keys[i].children[0].innerHTML;
                    }
                }
            }
        }
    });
    let currentType;


    Helper.start();

    document.querySelector("#select-tipo-reporte")
    .addEventListener("change",seleccionarReporte);

    document.querySelector("#btn-reporte-zona")
    .addEventListener("click",getPlanesPorZona);

    document.querySelector("#btn-reporte-fecha")
    .addEventListener("click",getPlanesPorFecha);

    function getPlanesPorZona() {
        let zona = document.querySelector("#ciudad").value;
        let planesUl = document.querySelector("#planes-zona");
        planesUl.classList.remove("oculto");
        let planesUls = document.querySelectorAll("reportePlan");

        for (let i = 0; i < planesUls.length; i++) {
            planesUls[i].classList.add("oculto");
        }

        fetch(uri + "usuarios/viajes/planes?zona=" + zona,
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(planes => {
            data.planesZona = planes;
        })
        .catch(r => {
            Helper.quitarToken();
        });
    }

    function getPlanesPorFecha() {
        let fechaInicio = document.querySelector("#fechaInicio").value.replace("T", " ");
        let fechaFin = document.querySelector("#fechaFin").value.replace("T", " ");

        let planesUl = document.querySelector("#planes-fecha");
        planesUl.classList.remove("oculto");
        let planesUls = document.querySelectorAll("reportePlan");

        for (let i = 0; i < planesUls.length; i++) {
            planesUls[i].classList.add("oculto");
        }

        fetch(uri + "usuarios/viajes/planes?fechaInicio=" + fechaInicio + "&fechaFin="+fechaFin,
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(planes => {
            data.planesFecha = planes;
        })
        .catch(r => {
            console.log(r)
            //Helper.quitarToken();
        });
    }

    function seleccionarReporte() {
        let select = document.querySelector("#select-tipo-reporte");

        switch (select.value) {
            case "pendientes" : {
                if (!pendientes) {
                    getPlanesPendientes();
                }

                let planesPendientes = document.querySelector("#planesPendientes");
                show(planesPendientes);
                break;
            }
            case "realizados" : {
                if (!realizados) {
                    getPlanesRealizados();
                }
                let planesRealizados = document.querySelector("#planesRealizados");
                show(planesRealizados);
                break;
            }
            case "zona" : {
                let planesZona = document.querySelector("#planesZona");
                show(planesZona);
                break;
            }
            case "fecha" : {
                let planesFecha = document.querySelector("#planesFecha");
                show(planesFecha);
                break;
            }
        }
    }

    function getPlanesPendientes() {
        fetch(uri + "usuarios/viajes/planes?estado=pendientes",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(planes => {
            data.planesPendientes = planes;
        })
        .catch(r => {
            Helper.quitarToken();
        });
    }

    function getPlanesRealizados() {
        fetch(uri + "usuarios/viajes/planes?estado=realizados",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(planes => {
            data.planesRealizados = planes;
        })
        .catch(r => {
            Helper.quitarToken();
        });
    }

    function show(reporte) {
        let reportes = document.querySelectorAll(".reportePlan");
        for (let i = 0; i < reportes.length; i++) {
            reportes[i].classList.add("oculto");
        }
        reporte.classList.remove("oculto");
    }

    async function borrarPlan(id) {
        let response = await fetch(uri+"usuarios/viajes/planes/"+id,{
            "method": "DELETE",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        });

        if (response.ok) {
            for (let i = 0; i < data.planes.length; i++) {
                if (data.planes[i].id == id) {
                    data.planes.splice(i, 1);
                }
            }
        }
    }

    async function crearPlan(json,idViaje) {
        let response = await fetch(uri + "usuarios/viajes/"+idViaje+"/planes",{
            "method": "POST",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            },
            "body" : JSON.stringify(json)
        });
        let responseToJson = await response.json();
        return responseToJson;
    }

    function planToJson(inputs) {
        let json = {};
        for (let i = 0; i < inputs.length; i++){
            json[inputs[i].getAttribute("name")] = inputs[i].value;
        }
        return json;
    }

    function buscarPlanActivo(div,plan) {
        let planes = div.children;
        let planReturn;

        for (let i = 0; i < planes.length; i++){
            planes[i].classList.add("oculto");
            if (planes[i].getAttribute("value") == plan) {
                planReturn = planes[i];
            }
        }

        if (planReturn != undefined) {
            return planReturn;
        } else {
            return -1;
        }
    }

    function getKeysFromType(type) {
        let types = document.querySelector(".tipos-de-planes").children;
        for (let i = 0; i < types.length; i++) {
            if (type == types[i].getAttribute("value")) {
                return types[i].children;
            }
        }
    }

    if (!Helper.sesion.logeado) {
        let base = new URL('/', location.href).href;
        location.href =  base + "html/login.html";
    }

});