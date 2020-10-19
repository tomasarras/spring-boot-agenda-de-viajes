export default class Helper {

    static asignarIconosEditar(callback1,callback2) {
        let btnsClientesAbrirPopup = document.querySelectorAll('.btn-abrir-popup-editar-cliente');
        let btnsProductoAbrirPopup = document.querySelectorAll(".btn-abrir-popup-editar-producto");
        let overlayE = document.getElementById('overlay-cliente'),
            popupE = document.getElementById('popup-cliente'),
            overlayC = document.getElementById('overlay-producto'),
            popupC = document.getElementById('popup-producto'),
            btnCerrarpopup = document.querySelectorAll('.js-cerrar');


        btnsClientesAbrirPopup.forEach(btnAbrirPopup => {

            btnAbrirPopup.addEventListener('click', function() {
                overlayE.classList.add('active');
                popupE.classList.add('active');
                let btnEditar = document.querySelector("#btn-editar-cliente");
                btnEditar.setAttribute("name", btnAbrirPopup.getAttribute("name"));
                callback1();
            });
        });

        btnsProductoAbrirPopup.forEach(btnAbrirPopup => {
            btnAbrirPopup.addEventListener('click', function() {
                overlayC.classList.add('active');
                popupC.classList.add('active');
                let btnEditar = document.querySelector("#btn-editar-producto");
                btnEditar.setAttribute("name", btnAbrirPopup.getAttribute("name"));
                callback2();
            });

        });

        btnCerrarpopup.forEach(btn => {
            btn.addEventListener('click', function() {
                overlayE.classList.remove('active');
                popupE.classList.remove('active');
                overlayC.classList.remove('active');
                popupC.classList.remove('active');
            });
        });
    }

    static cerrarPopup() {
        let overlays = document.querySelectorAll(".overlay");
        let popups = document.querySelectorAll(".popup");
        overlays.forEach(overlay => {
            overlay.classList.remove("active");
        });
        popups.forEach(popup => {
            popup.classList.remove("active");
        });
    }



    static cerrarSection() {
        let sections = document.querySelectorAll("section.agregar");
        sections.forEach(section => {
            section.classList.add("none");
        });
    }

    static mostrarSection(section) {
        section.classList.remove("none");
    }

    static comprobarInputsVacios() {
        let error = false;

        let inputs = document.querySelectorAll(".campo-vacio");
        inputs.forEach(input => {
            input.classList.remove("is-invalid");
            if (input.value == '') {
                input.classList.add("is-invalid");
                error = true;
            }

            input.addEventListener("click", () => input.classList.remove("is-invalid"));

        });

        return error;
    }

    static vaciarInputs() {
        let inputs = document.querySelectorAll(".campo-vacio");
        inputs.forEach(input => {
            input.value = '';
        });
    }
}
