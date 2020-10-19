document.addEventListener("DOMContentLoaded",()=>{
    let btnGenerar = document.querySelector("#btn-generar");
    btnGenerar.addEventListener("click",()=>{
        fetch("http://localhost:8080/test/generar-datos").
        then(()=>location.href ="http://localhost:8080/index.html");
    });
});