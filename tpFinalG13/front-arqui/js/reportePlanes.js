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
            borrarViaje : async function (event) {
                let idViaje = event.target.parentElement.getAttribute("name");
                let response = await fetch(uri+"usuarios/viajes/"+idViaje,{
                    "method": "DELETE",
                    "headers": {
                        "Content-Type": "application/json",
                        "Authorization": Helper.getToken()
                    }
                });

                if (response.ok) {
                    for (let i = 0; i < data.viajes.length; i++) {
                        if (data.viajes[i].id == idViaje) {
                            data.viajes.splice(i, 1);
                        }
                    }
                }
            },

            editarPlan : async function (event) {
                let btnEditar = event.target;
                let ul = btnEditar.parentElement.previousElementSibling;
                let idPlan = btnEditar.getAttribute("name");
                let idViaje = ul.getAttribute("name");
                let inputs = ul.querySelectorAll("input");
                let json = planToJson(inputs);
                let type = ul.querySelector(".type").getAttribute("name");
                json.type = type;
                json.nombre = ul.querySelector(".nombre").getAttribute("name");
                let response = await fetch(uri + "usuarios/viajes/"+idViaje+"/planes/"+idPlan,{
                    "method": "PUT",
                    "headers": {
                        "Content-Type": "application/json",
                        "Authorization": Helper.getToken()
                    },
                    "body" : JSON.stringify(json)
                });

                if (response.ok) {
                    location.reload();

                    /*btnEditar.classList.add("oculto");
                    btnEditar.nextElementSibling.classList.add("oculto");
                    ul.parentElement.parentElement.previousElementSibling.querySelector
                    (".btns-editar-js").classList.remove("oculto");
                    let contenido = ul.children;
                    for (let i = 0; i < contenido.length; i++) {
                        let content = contenido[i].children[0].children;
                        if (content[0].classList.contains("type")) {
                            content[0].parentElement.parentElement.classList.remove("oculto");
                        } else {
                            content[0].classList.toggle("oculto");
                            content[1].classList.toggle("oculto");
                        }
                    }


                    for (let i = 0; i < this.planes.length; i++) {
                        if (data.planes[i].id == idPlan){
                            Vue.set(data.planes, i, json);
                            i = data.planes.length;
                        }
                    }

                    */
                }

            },
            abrirLista : function (event) {
                let element = event.target.parentElement;
                if (element.nodeName == "H2") {
                    element = element.parentElement;
                }
                element.nextElementSibling.classList.toggle("active");
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

    function btnsCrearPlan() {
        let btns = document.querySelectorAll(".btn-crear-plan");
        for (let i = 0; i < btns.length; i++){
            btns[i].addEventListener("click",async function () {
                let divPlanes = btns[i].previousElementSibling;
                let planActivo = buscarPlanActivo(divPlanes,btns[i].getAttribute("value"));
                let inputsPlan = planActivo.querySelectorAll("input");
                let json = planToJson(inputsPlan);
                json.type = planActivo.getAttribute("value");
                let idViaje = btns[i].getAttribute("name");
                let responsePlan = await crearPlan(json,idViaje);
                responsePlan.idViaje = idViaje;
                data.planes.push(responsePlan);
            });
        }
    }

    function getIndiceViaje(idViaje) {
        let i = 0;
        while (i < data.viajes.length) {
            if (data.viajes[i].id == idViaje) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
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

    function mostrarTipoPlanesSelect() {
        let selects = document.querySelectorAll(".select-tipo-plan");
        for (let i = 0; i < selects.length; i++) {
            selects[i].addEventListener("change",()=> {
                let btnCrear = selects[i].nextElementSibling.nextElementSibling;
                let divPlanes = selects[i].nextElementSibling;
                let planActivo = buscarPlanActivo(divPlanes,selects[i].value);

                if (planActivo != -1) {
                    planActivo.classList.add("tipo-plan-activo");
                    planActivo.classList.remove("oculto");
                    btnCrear.setAttribute("value",selects[i].value);
                }

                if (selects[i].value == "0") {
                    btnCrear.classList.add("oculto");
                } else {
                    btnCrear.classList.remove("oculto");
                }
            });
            
        }
    }


    function ocultarTipoPlanes() {
        let planes = document.querySelectorAll(".tipo-plan");
        for (let i = 0; i < planes.length; i++) {
            planes[i].classList.add("oculto");
        }

    }

    function btnsBorrarPlan() {
        let btns = document.querySelectorAll(".btns-borrar-js");
        btns.forEach(btn => {
            btn.addEventListener("click", ()=>{
                let id = btn.getAttribute("name");
                borrarPlan(id);
            });
        });
    }

    function cargarBtnsVerPlanes() {
        let btns = document.querySelectorAll(".btn-verplanes");
        for (let i = 0; i < btns.length; i++) {
            btns[i].addEventListener("click",async function(){
                let callback = ()=> {
                    cargarListaAnimada();
                    btnsBorrarPlan();
                }

                getPlanes(btns[i].getAttribute("name"),callback);
                btns[i].parentElement.nextElementSibling.nextElementSibling.classList.remove("oculto");
                btns[i].classList.add("oculto");
                btns[i].parentElement.classList.add("centrar-contenido");
            });
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

    function getPlanes(idViaje,callback) {
        fetch(uri + "usuarios/viajes/"+idViaje+"/planes",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        })
        .then(response => response.json())
        .then(planes => {
            for (let i = 0; i < planes.length; i++) {
                planes[i].idViaje = idViaje;
                data.planes.push(planes[i]);
            }
        })
        .then(c => callback())
        .catch(r => {
            Helper.quitarToken();
        });
    }

    function cargarListaAnimada() {
        /*let divsTitle = document.querySelectorAll("ul.acordeon li div.title");
        divsTitle.forEach(divTitle => {
            divTitle.addEventListener("click",()=>{
                divTitle.nextElementSibling.classList.toggle("active");
            });
        });*/
    }


    async function crearViaje() {
        let nombreViaje = document.querySelector("#nombre-viaje").value;
        let descripcion = document.querySelector("#descripcion").value;
        let fechaInicio = document.querySelector("#fecha-inicio").value;
        let fechaFin = document.querySelector("#fecha-fin").value;
        let ciudadDestino = document.querySelector("#ciudad-destino").value;

        let json = {
            "nombre" : nombreViaje,
            "descripcion" : descripcion,
            "fechaInicio" : fechaInicio,
            "fechaFin" : fechaFin,
            "ciudadDestino" : ciudadDestino
        }

        let response = await fetch(uri + "usuarios/viajes",
        {
            "method": "POST",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            },
            "body" : JSON.stringify(json)
        });

    }

    function getViajes(callback) {
        fetch(uri + "usuarios/viajes?criterio=pasados",
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
        })
        .then(() => callback())
        .catch(err => {
            //token expired
            Helper.quitarToken();
        });
    }
});