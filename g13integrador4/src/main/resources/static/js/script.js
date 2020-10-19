import Helper from './Helper.js';
document.addEventListener("DOMContentLoaded",()=>{
    const BASE_URL = "http://localhost:8080/";

    let tablas = new Vue({
        el: "#vue-tablas",
        data: {
            productos: [],
            clientes: []
        }
    });

    cargarTablas();

    let btnAgregarProducto = document.querySelector("#agregar-producto");
    btnAgregarProducto.addEventListener("click",async function(){
        let inputsVacios = Helper.comprobarInputsVacios();
        if (!inputsVacios) {
            let agregado = await agregarProducto(getProductos);
            if (agregado) {
                btnAgregarProducto.parentElement.classList.add("none");
                btnAgregarProducto.previousElementSibling.classList.add("none");
                Helper.asignarIconosEditar(abrirPopupEditarCliente,abrirPopupEditarProducto);
                asignarIconosBorrar();
            } else {
                btnAgregarProducto.previousElementSibling.classList.remove("none");
            }
        }
    });

    async function agregarProducto(callback) {
        let producto = {
            "nombre":document.querySelector("#agregar-nombre-producto").value,
            "stock": document.querySelector("#agregar-stock").value,
            "precio": document.querySelector("#agregar-precio").value
        }

        let response = await fetch(BASE_URL + "productos/",{
            "method": "POST",
            "headers": {"Content-Type": "application/json"},
            "body": JSON.stringify(producto)
        });

        if (response.ok) {
            Helper.vaciarInputs();
            await callback();
            return true;
        } else {
            return false;
        }

    }

    let btnAgregarCliente = document.querySelector("#agregar-cliente");
    btnAgregarCliente.addEventListener("click",agregarCliente);

    async function cargarTablas() {
        await getClientes();
        await getProductos();
        asignarAgregar();
        asignarIconosBorrar();
        Helper.asignarIconosEditar(abrirPopupEditarCliente,abrirPopupEditarProducto);
       /* Helper.asignarIconosEditar(abrirPopupEditarEstudiante,abrirPopupEditarCarrera);
        asignarMasDetalles();*/
    }

    async function getProductos() {
        let url = BASE_URL + "productos/";
        let response = await fetch(url);
        let productos = await response.json();
        tablas.productos = productos;
    }

    async function getClientes() {
        let response = await fetch(BASE_URL + "clientes/");
        let clientes = await response.json();
        tablas.clientes = clientes;
    }


    function asignarAgregar() {
        let btnAgregarProducto = document.querySelector("#btn-agregar-producto");
        btnAgregarProducto.addEventListener("click",()=> {
            let section = document.querySelector("#section-agregar-producto");
            Helper.mostrarSection(section);
        });

        let btnAgregarCliente = document.querySelector("#btn-agregar-cliente");
        btnAgregarCliente.addEventListener("click",()=>{
            let section = document.querySelector("#section-agregar-cliente");
            Helper.mostrarSection(section);
        });
    }

    

    async function agregarCliente() {
        let nombreCliente = document.querySelector("#agregar-nombre-cliente");
        if (nombreCliente.value != '') {
            let cliente = {
                "nombre" : nombreCliente.value
            }
            
            let response = await fetch(BASE_URL + "clientes/",{
                "method": "POST",
                "headers": {"Content-Type": "application/json"},
                "body": JSON.stringify(cliente)
            });
            
            if (response.ok){ 
                nombreCliente.value = '';
                Helper.cerrarSection();
                await getClientes();
            }
        } else {
            nombreCliente.classList.add("is-invalid");
            nombreCliente.addEventListener("click",()=>nombreCliente.classList.remove("is-invalid"));
        }
    }

    let btnsCancelar = document.querySelectorAll(".cerrar-section");
    btnsCancelar.forEach(btn => {
        btn.addEventListener("click",Helper.cerrarSection);
    });

    function asignarIconosBorrar() {
        let btnsBorrarClientes = document.querySelectorAll(".btn-eliminar-cliente");
        btnsBorrarClientes.forEach(btn => {
            btn.addEventListener("click",async function(){
                await borrarCliente(btn.getAttribute("name"),getClientes);
                Helper.asignarIconosEditar(abrirPopupEditarCliente,abrirPopupEditarProducto);
                asignarIconosBorrar();
            });
        });

        let btnsBorrarProductos = document.querySelectorAll(".btn-eliminar-producto");
        btnsBorrarProductos.forEach(btn => {
            btn.addEventListener("click",()=>{
                borrarProducto(btn.getAttribute("name"),getProductos);
            });
        });
    }

    async function borrarCliente(id,callback) {
        await fetch(BASE_URL + "clientes/" + id,{"method": "DELETE"});
        await callback();
    }

    async function borrarProducto(id,callback) {
        await fetch(BASE_URL + "productos/" + id,{"method": "DELETE"});
        await callback();
    }


    async function abrirPopupEditarCliente() {
        let btnEditar = document.querySelector("#btn-editar-cliente");
        let id = btnEditar.getAttribute("name");
        let cliente = await getCliente(id);
        document.querySelector("#editar-nombre-cliente").value = cliente.nombre;

        btnEditar.addEventListener("click",async function() {
            await editarCliente(id,Helper.cerrarPopup);
            await getClientes();
        });
    }

    async function abrirPopupEditarProducto() {
        let btnEditar = document.querySelector("#btn-editar-producto");
        let id = btnEditar.getAttribute("name");
        let producto = await getProducto(id);
        document.querySelector("#editar-nombre-producto").value = producto.nombre;
        document.querySelector("#editar-stock").value = producto.stock;
        document.querySelector("#editar-precio").value = producto.precio;

        btnEditar.addEventListener("click",async function() {
            await editarProducto(id,Helper.cerrarPopup);
            await getProductos();
        });
    }

    async function getCliente(id) {
        let response = await fetch(BASE_URL + "clientes/" + id);
        let cliente = await response.json();
        return cliente;
    }

    async function getProducto(id) {
        let response = await fetch(BASE_URL + "productos/" + id);
        let producto = await response.json();
        return producto;
    }


    async function editarCliente(id,callback) {
        let cliente = {
            "nombre" : document.querySelector("#editar-nombre-cliente").value
        }

        await fetch(BASE_URL + "clientes/" + id,{
            "method" : "PUT",
            "headers": {"Content-Type": "application/json"},
            "body": JSON.stringify(cliente)
        });
        await callback();
    }

    async function editarProducto(id,callback) {
        let producto = {
            "nombre" : document.querySelector("#editar-nombre-producto").value,
            "stock" : document.querySelector("#editar-stock").value,
            "precio" : document.querySelector("#editar-precio").value
        }

        await fetch(BASE_URL + "productos/" + id,{
            "method" : "PUT",
            "headers": {"Content-Type": "application/json"},
            "body": JSON.stringify(producto)
        });
        await callback();
    }

/*

    


    

    

    

    

    

    

    

    

    

    
    function asignarMasDetalles() {
        let btnsDetalles = document.querySelectorAll(".btn-mas-detalle");
        btnsDetalles.forEach(btn => {
            btn.addEventListener("click",()=>{
                abrirMasDetalles(btn.getAttribute("name"));
            });
        });
    }

    async function abrirMasDetalles(lu) {
        let overlay = document.querySelector("#overlay-mas-detalle");
        let popup = document.querySelector("#popup-mas-detalle");
        overlay.classList.add("active");
        popup.classList.add("active");
        let estudiante = await getEstudiante(lu);
        document.querySelector("#mas-detalle-dni").value = estudiante.dni;
        document.querySelector("#mas-detalle-lu").value = estudiante.lu;
        document.querySelector("#mas-detalle-edad").value = estudiante.edad;
        document.querySelector("#mas-detalle-ciudad").value = estudiante.ciudad;
        document.querySelector("#mas-detalle-nombre").value = estudiante.nombre;
        document.querySelector("#mas-detalle-apellido").value = estudiante.apellido;
        if (estudiante.masculino) {
            document.querySelector("#mas-detalle-genero-masculino").checked = true;
        } else {
            document.querySelector("#mas-detalle-genero-femenino").checked = true;
        }
    }
*/
});