document.addEventListener("DOMContentLoaded",()=>{
    const BASE_URL = "http://localhost:8080/";
    let cliente;
    let producto;
    let tablas = new Vue({
        el: "#comprar-productos",
        data: {
            clientes: [],
            productos: []
        }
    });

    getClientes();

    function getClientes() {
        fetch(BASE_URL + "clientes/")
        .then(response => response.json())
        .then(clientes => {
            tablas.clientes = clientes;
        })
        .then(()=>{

            let filasClientes = document.querySelectorAll(".fila-cliente");
            filasClientes.forEach(fila => {
                fila.addEventListener("click",()=>{
                    seleccionarCliente(fila);
                    ocultarClientes();
                    mostrarProductos();
                });
            });
        });
    }

    async function comprar() {
        let url = BASE_URL + "compras/clientes/"+cliente.id+"/productos/"+producto.id;
        let response = await fetch(url,{
            "method" :"POST",
            "body" : "{}",
            "headers": {"Content-Type": "application/json"}
        });
        if (response.ok) {
            location.reload();
        } else {
            mostrarErrorProductoMaximo();
        }
    }

    function mostrarProductos() {
        fetch(BASE_URL + "productos/")
        .then(response => response.json())
        .then(productos => {
            tablas.productos = productos;
            document.querySelector("#tabla-productos").classList.remove("none");
            document.querySelector("#paso2").classList.remove("none");
            document.querySelector("#paso1").classList.add("none");
        })
        .then(()=>{
            let filasProductos = document.querySelectorAll(".fila-producto");
            filasProductos.forEach(fila => {
                fila.addEventListener("click",()=>{
                    seleccionarProducto(fila);
                    mostrarPopup();
                });
            });
        });
    }

    function mostrarPopup() {
        let popup = document.querySelector("#popup");
        popup.classList.add("active");
        let overlay = document.querySelector("#overlay");
        overlay.classList.add("active");
        let spanCliente = document.querySelector("#span-cliente");
        let spanProducto = document.querySelector("#span-producto");
        spanCliente.innerHTML = cliente.nombre;
        spanProducto.innerHTML = producto.nombre;
        let btnCancelar = document.querySelector("#btn-cancelar");
        btnCancelar.addEventListener("click",cerrarPopup);
        let btnConfirmar = document.querySelector("#btn-confirmar");
        btnConfirmar.addEventListener("click",comprar);
    }

    function cerrarPopup() {
        let popup = document.querySelector("#popup");
        let overlay = document.querySelector("#overlay");
        popup.classList.remove("active");
        overlay.classList.remove("active");
    }

    function ocultarClientes() {
        document.querySelector("#tabla-compras").classList.add("none");
    }

    function seleccionarCliente(fila) {
        let id = fila.children[0].innerHTML;
        let nombre = fila.children[1].innerHTML;
        cliente = {
            "id" : id,
            "nombre":nombre
        };
    }

    function mostrarErrorProductoMaximo() {
        cerrarPopup();
        document.querySelector("#error").classList.remove("none");
    }

    function seleccionarProducto(fila) {
        let id = fila.children[0].innerHTML;
        let nombre = fila.children[1].innerHTML;
        let precio = fila.children[2].innerHTML;
        let stock = fila.children[3].innerHTML;
        producto = {
            "id" : id,
            "nombre":nombre,
            "precio" : precio,
            "stock" : stock
        };
    }
});