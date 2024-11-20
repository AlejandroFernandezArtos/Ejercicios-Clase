        // Mostrar la sección de inicio y ocultar las otras secciones al cargar la página
        document.addEventListener("DOMContentLoaded", function() {
            mostrarSeccion("inicio");
        });

        function mostrarSeccion(id) {
            // Ocultamos todas las secciones
            var secciones = document.getElementsByTagName("section");
            for (var i = 0; i < secciones.length; i++) {
                secciones[i].style.display = "none";
            }
            // Mostramos solo la sección seleccionada
            document.getElementById(id).style.display = "block";
        }