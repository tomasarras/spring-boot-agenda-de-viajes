document.addEventListener("DOMContentLoaded",()=>{
    const BASE_URL = "http://localhost:8080/";
    let datos = new Vue({
        el: "#reportes",
        data: {
            productoMasVendido : "",
            facturaClientes: [],
            reportes : []
        }
    });
    let divClientes = document.querySelector("#clientes");
    let divGanancias = document.querySelector("#ganancias");
    let divProducto = document.querySelector("#producto");

    getProductoMasVendido();
    getReporteFacturasClientes();
    getReportesGanancias();

    function getReportesGanancias() {
        let url = BASE_URL + "compras/reportes";
        fetch(url).
        then(response => response.json()).
        then(reportes => datos.reportes = reportes);
    }

    function getReporteFacturasClientes() {
        let url = BASE_URL + "compras/clientes/reportes";
        fetch(url).
        then(response => response.json()).
        then(facturas => datos.facturaClientes = facturas);
    }


    function getProductoMasVendido() {
        let url = BASE_URL + "compras/productos/mas-vendido";
        fetch(url).
        then(response => response.json()).
        then(producto => datos.productoMasVendido = producto);
    }

    let btnReportesClientes = document.querySelector("#btn-reportes-clientes");
    btnReportesClientes.addEventListener("click",()=>{
        divClientes.classList.remove("none");
        divGanancias.classList.add("none");
        divProducto.classList.add("none");
    });

    let btnReportesGanancias = document.querySelector("#btn-reportes-ganancias");
    btnReportesGanancias.addEventListener("click",()=>{
        divGanancias.classList.remove("none");
        divClientes.classList.add("none");
        divProducto.classList.add("none");
    });

    let btnProducto = document.querySelector("#btn-producto-mas-facturado");
    btnProducto.addEventListener("click",()=>{
        divProducto.classList.remove("none");
        divGanancias.classList.add("none");
        divClientes.classList.add("none");
    });

});