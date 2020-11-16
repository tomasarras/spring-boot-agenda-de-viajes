import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    const uri = "http://localhost:8080/";
    let data = new Vue({
        el: "#data",
        data: {
            planes : [],
            viajes : [],
            planesActivos: []
        },
        methods: {
            borrarPlan : function(event) {
                let btnBorrar = event.target.parentElement;
                console.log(btnBorrar)
                borrarPlan(btnBorrar.getAttribute("name"));
            },
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

                Helper.comprobarInputsVacios(modificar,btnEditar);
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

    document.querySelector("#btn-importar-viaje")
    .addEventListener("click",showPopUpImportarViaje);

    document.querySelector("#importar-vuelo")
    .addEventListener("change",importarViaje);

    let btnCrearViaje = document.querySelector("#btn-crear-viaje");
    btnCrearViaje.addEventListener("click",()=>{
        Helper.comprobarInputsVacios(crearViaje,btnCrearViaje);
    });

    

    getViajes(cargarAnimaciones);

    Helper.start();

    function cargarAnimaciones() {
        cargarBtnsVerPlanes();
        ocultarTipoPlanes();
        mostrarTipoPlanesSelect();
        btnsExpandirSelect();
        btnsCrearPlan();
    }

    function btnsExpandirSelect() {
        let btns = document.querySelectorAll(".btn-agregar-plan");
        for (let i = 0; i < btns.length; i++) {
            btns[i].addEventListener("click",()=>{
                let selectPlan = btns[i].parentElement.nextElementSibling;
                btns[i].classList.add("oculto")
                selectPlan.classList.remove("oculto");
                btns[i].parentElement.classList.add("centrar-contenido");
            });
        }
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
                let inputsObligatorios = planActivo.querySelectorAll(".campo-vacio");
                let enviarPlan = async function () {
                    let json = planToJson(inputsPlan);
                    json.type = planActivo.getAttribute("value");
                    let idViaje = btns[i].getAttribute("name");
                    let responsePlan = await crearPlan(json,idViaje);
                    responsePlan.idViaje = idViaje;
                    data.planes.push(responsePlan);
                    planActivo.classList.add("oculto");
                    inputsPlan.forEach(input => {
                        input.value = '';
                    });
                    document.querySelector(".btn-crear-plan").classList.add("oculto");
                }

                Helper.comprobarInputsVacios(enviarPlan,inputsObligatorios);
            });
        }
    }

    async function importarViaje(event) {
        let file = event.target.files[0];
        let reader = new FileReader();

        reader.readAsText(file);
        reader.onload = async function(readerEvent) {
            let content = readerEvent.target.result;
            try {
                let viajeJson = JSON.parse(content);
                let planesValidos = comprobarPlanes(viajeJson.planes);
                let viajeSinPlanesJson = comprobarViaje(viajeJson);
                let viajeValido = planesValidos && viajeSinPlanesJson != false;

                if (viajeValido) {
                    document.querySelector("#btns-importar")
                    .classList.remove("oculto");
                    let mensajeImportar = document.querySelector("#nombre-vuelo-importar");
                    mensajeImportar.innerHTML = viajeJson.nombre;
                    mensajeImportar.parentElement.classList.remove("oculto");

                    let enviarViajeImportado = async function () {
                        let response = await fetch(uri + "usuarios/viajes",
                        {
                            "method": "POST",
                            "headers": {
                                "Content-Type": "application/json",
                                "Authorization": Helper.getToken()
                            },
                            "body" : JSON.stringify(viajeSinPlanesJson)
                        });

                        if (response.ok) {
                            let jsonResponse = await response.json();
                            for (let i = 0; i < viajeJson.planes.length; i++) {
                                let planJson = viajeJson.planes[i];
                                crearPlan(planJson,jsonResponse.id);
                            }
                            location.reload();
                        }
                    }

                    document.querySelector("#btn-confirmar-importar")
                    .addEventListener("click",enviarViajeImportado);
                }
            } catch {
                document.querySelector("#error-importar-viaje")
                .classList.remove("oculto");
            }
        }
    }
    
    function comprobarViaje(json) {
        let comprobarValor = function(valor) {
            return valor != null && valor != undefined && valor != '';
        }
        let jsonResponse = {}

        let camposViaje = document.querySelectorAll(".campo-viaje");
        let valido = false;
        let valor;
        for (let i = 0; i < camposViaje.length; i++) {
            valor = camposViaje[i].getAttribute("name");
            if (valor == "fechaInicio" || valor == "fechaFin") {
                let formatDate = comprobarFecha(json[valor]);
                if (formatDate != false) {
                    jsonResponse[valor] = formatDate;
                } else {
                    return false;
                }
            } else {
                valido = comprobarValor(json[valor]);
                jsonResponse[valor] = json[valor];
            }
            
            if (!valido) {
                return false;
            }
        }
        return jsonResponse;
    }

    function comprobarFecha(fecha) {
        try {
            return new Date(Date.parse(fecha))
            .toISOString().replace(/T/, " ").replace(/:00.000Z/, "");
        } catch {
            return false;
        }
    }

    function comprobarPlanes(planes) {
        let tipos = document.querySelector(".tipos-de-planes").children;
        let encontrado;
        let plan;
        let datosPlan;
        let variablesPlan;
        let variablePlan;
        let variableValida;

        for (let i = 0; i < planes.length; i++) {
            plan = planes[i];
            encontrado = false;
            
            for (let i = 0; i < tipos.length; i++) {
                if (tipos[i].getAttribute("value") == plan.type) {
                    encontrado = true;
                    datosPlan = tipos[i];
                }
            }
            
            if (encontrado) {
                variablesPlan = datosPlan.querySelectorAll("input");
                for (let i = 0; i < variablesPlan.length; i++) {
                    variableValida = false;
                    variablePlan = variablesPlan[i].getAttribute("name");

                    if (plan[variablePlan] != "tiempoEscalas") {
                        if (variablePlan == "fechaInicio" || variablePlan == "fechaFin") {
                            let formatDate = comprobarFecha(plan[variablePlan]);
                            if (formatDate != false) {
                                variableValida = true;
                                plan[variablePlan] = formatDate;
                            } else {
                                return false;
                            }
                        } else {
                            variableValida = plan[variablePlan] != undefined
                            && plan[variablePlan] != null
                            && plan[variablePlan] != '';
                        }
                        
                        if (!variableValida) {
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        }
        return true;
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
            let value = inputs[i].value;
            if (inputs[i].getAttribute("type") == "datetime-local") {
                value = value.replace("T", " ");
            }
            json[inputs[i].getAttribute("name")] = value;
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
        try {
            planReturn.classList.remove("oculto");
        } catch {
            return -1;
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
                let li = selects[i].parentElement.parentElement.parentElement.parentElement;
                let btnCrear = li.querySelector(".btn-crear-plan");
                let divPlanes = li.querySelector(".tipos-de-planes");
                
                let planActivo = buscarPlanActivo(divPlanes,selects[i].value);
                
                if (planActivo != -1) {
                    planActivo.classList.add("tipo-plan-activo");
                    planActivo.classList.remove("oculto");
                    btnCrear.setAttribute("value",selects[i].value);
                }

                let btnReservaHotel = document.querySelector("#btn-importar-hotel");
                if (selects[i].value == "reservaHotel") {
                    btnReservaHotel.classList.remove("oculto");
                } else {
                    btnReservaHotel.classList.add("oculto");
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

    function cargarBtnsVerPlanes() {
        let btns = document.querySelectorAll(".btn-verplanes");
        for (let i = 0; i < btns.length; i++) {
            btns[i].addEventListener("click",async function(){
                getPlanes(btns[i].getAttribute("name"));
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

    function getPlanes(idViaje) {
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
        .catch(r => {
            Helper.quitarToken();
        });
    }

    async function crearViaje() {
        let nombreViaje = document.querySelector("#nombre-viaje").value;
        let descripcion = document.querySelector("#descripcion").value;
        let fechaInicio = document.querySelector("#fecha-inicio").value.replace("T", " ");
        let fechaFin = document.querySelector("#fecha-fin").value.replace("T", " ");
        let ciudadDestino = document.querySelector("#ciudad-destino").value;
        let dateNow = new Date();
        dateNow.setSeconds(0, 0);
        let now = dateNow.toISOString().replace(/T/, " ").replace(/:00.000Z/, "");
        let viajeYaRealizado = fechaFin < now;
        

        

        if (fechaInicio > fechaFin) {
            let mensajeError = document.querySelector("#fecha-inicio-incorrecta");
            let mensajeErrorVacio = mensajeError.previousElementSibling;
            mensajeErrorVacio.classList.add("oculto");
            mensajeError.classList.remove("oculto");
            let inputFechaInicio = document.querySelector("#fecha-inicio");
            inputFechaInicio.classList.add("is-invalid");
            inputFechaInicio.addEventListener("focus",()=>{
                inputFechaInicio.classList.remove("is-invalid");
                mensajeError.classList.add("oculto");
                mensajeErrorVacio.classList.remove("oculto");
            });
        } else {
            let agregar = async function() {
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

                if (response.ok) {
                    location.reload();
                }
            }

            if (viajeYaRealizado) {
                showPopUpViajeRealizado();
                document.querySelector("#btn-confirmar-viaje")
                .addEventListener("click",agregar);
            } else {
                agregar();
            }
        }
        
        
    }

    function showPopUpViajeRealizado() {
        let overlay = document.getElementById('overlay-viaje-realizado'),
            popup = document.getElementById('popup-viaje-realizado');
        let btnsCerrarPopUp = overlay.querySelectorAll(".btn-cerrar-popup-js");
        overlay.classList.add('active');
        popup.classList.add('active');

        btnsCerrarPopUp.forEach(btn => {
            btn.addEventListener("click",()=>{
                overlay.classList.remove('active');
                popup.classList.remove('active');
            });
        });
    }

    function showPopUpImportarViaje() {
        let overlay = document.getElementById('overlay-importar-viaje'),
            popup = document.getElementById('popup-importar-viaje');
        let btnsCerrarPopUp = overlay.querySelectorAll(".btn-cerrar-popup-js");
        overlay.classList.add('active');
        popup.classList.add('active');

        btnsCerrarPopUp.forEach(btn => {
            btn.addEventListener("click",()=>{
                overlay.classList.remove('active');
                popup.classList.remove('active');
            });
        });
    }

    function getViajes(callback) {
        fetch(uri + "usuarios/viajes"+"?criterio=pendientes",
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
            console.log(err)
            //token expired
            Helper.quitarToken();
        });
    }

    if (!Helper.sesion.logeado) {
        location.href = "../html/login.html";
    }
});