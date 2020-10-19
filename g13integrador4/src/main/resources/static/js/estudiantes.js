document.addEventListener("DOMContentLoaded",()=>{
    const BASE_URL = "http://localhost:8080/Integrador3/";
    let tabla = new Vue({
        el: "#estudiantes",
        data: {
            estudiantes: []
        }
    });

    let btnBuscar = document.querySelector("#buscar-estudiante");
    btnBuscar.addEventListener("click",async function(){
        let lu = document.querySelector("#numero-lu").value;
        if (lu != '') {
            let estudiante = await getEstudiante(lu);
            let genero;
            if (estudiante.masculino) {
                genero = "masculino";
            } else {
                genero = "femenino";
            }

            let span = document.querySelector("#estudiante");
            span.innerHTML = "Numero de LU: " + estudiante.lu + ", dni: "
            + estudiante.dni + ", nombre: " + estudiante.nombre + ", apellido: "
            + estudiante.apellido + ", edad: " + estudiante.edad + ", genero: " +
            genero + ", ciudad: " + estudiante.ciudad;
        }
    });

    let btnBuscarGenero = document.querySelector("#btn-buscar-genero");
    btnBuscarGenero.addEventListener("click",()=>{
        let genero = document.querySelector("#genero").value;
        getEstudiantesPorGenero(genero);
    });

    let btnCiudadCarrera = document.querySelector("#btn-ciudad-carrera");
    btnCiudadCarrera.addEventListener("click",()=>{
        let ciudad = document.querySelector("#input-ciudad").value;
        let carrera = document.querySelector("#input-carrera").value;
        if (carrera != '' && ciudad != '') {
            getEstudiantesPorCiudadYCarrera(ciudad,carrera);
        }
    });

    async function getEstudiante(lu) {
        let response = await fetch(BASE_URL + "rest/estudiantes/" + lu);
        let estudiante = await response.json();
        return estudiante;
    }

    getEstudiantes();

    async function getEstudiantes(orden) {
        let url = BASE_URL + "rest/estudiantes";
        if (orden != undefined)
            url = url + "?atributo=" + orden + "&orden=asc";
        let response = await fetch(url);
        let estudiantes = await response.json();
        tabla.estudiantes = estudiantes;
    }

    async function getEstudiantesPorGenero(genero) {
        let url = BASE_URL + "rest/estudiantes?genero=" + genero;
        let response = await fetch(url);
        let estudiantes = await response.json();
        tabla.estudiantes = estudiantes;
    }

    async function getEstudiantesPorCiudadYCarrera(ciudad,carrera) {
        let url = BASE_URL + "rest/estudiantes/carrera/" + carrera + "?ciudad=" + ciudad;
        let response = await fetch(url);
        let estudiantes = await response.json();
        tabla.estudiantes = estudiantes;
    }

});