import Helper from "./Helper.js";

document.addEventListener("DOMContentLoaded",()=> {
    const uri = "http://localhost:8080/";

    let data = new Vue({
        el: "#data",
        data: {
            viajes : []
        }
    });

    getViajes();

    Helper.start();
    let divsTitle = document.querySelectorAll("ul.acordeon li div.title");
    divsTitle.forEach(divTitle => {
        divTitle.addEventListener("click",()=>{
            divTitle.nextElementSibling.classList.toggle("active");
        });
    });

    async function getViajes() {
        let response = await fetch(uri + "viajes",
        {
            "method": "GET",
            "headers": {
                "Content-Type": "application/json",
                "Authorization": Helper.getToken()
            }
        });
        let viajes = await response.json();
        data.viajes = viajes;
        console.log(viajes)
    }
});