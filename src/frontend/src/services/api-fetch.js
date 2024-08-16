// Esperamos a que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Seleccionamos el formulario por su ID
  const form = document.getElementById("newsForm");

  // Añadimos un evento 'submit' al formulario
  form.addEventListener("submit", (event) => {
    // Prevenimos el comportamiento por defecto del formulario (recargar la página)
    event.preventDefault();

    // Obtenemos el valor del campo de número de teléfono
    const phoneNumber = document.getElementById("phone").value;

    // Creamos un array para almacenar las categorías seleccionadas
    const selectedCategories = Array.from(
      document.querySelectorAll('input[name="category"]:checked'),
      (checkbox) => checkbox.value
    );


    // Categoria personalizada
    /* const customCategory = document.getElementById('customCategory').value;
    if (customCategory) categories.push(customCategory); */

    // Llamamos a la función de validación
    if (validatePhoneNumber(phoneNumber)) {
      console.log("Número de teléfono válido desde el front");

      // Creamos un objeto con los datos que queremos enviar al backend
      const data = {
        phoneNumber: phoneNumber,
        categories: selectedCategories,
      };

      // Usamos fetch para enviar una solicitud POST al backend
      fetch("http://localhost:8080/subscriptions", {
        method: "POST", // Método HTTP
        headers: {
          "Content-Type": "application/json", // Indicamos que enviamos JSON
          "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify(data), // Convertimos el objeto data a una cadena JSON
      })

      .then(response => {
        if (!response.ok) {
          throw new Error('Respuesta del servidor no fue exitosa');
        }
        return response.json();
      })

        .then(data => {
        //localStorage.setItem("token", data.token);
        alert("Suscripción exitosa:" + data.phoneNumber);
        updateUIWithSubscriptions(data);
      })
      .catch((error) => {
        alert("Error en la suscripción: " + error.message);
      });


    } else {
      alert("Número de teléfono inválido, introduzca un formato válido");
    }
  });
});

function updateUIWithSubscriptions(data) {
  const subscriptionsContainer = document.getElementById("subscriptions-container");
  subscriptionsContainer.innerHTML = ""; // Limpiar el contenedor

  data.categories.forEach(category => {
    const categoryElement = document.createElement("p");
    categoryElement.textContent = `Categoría: ${category}`;
    subscriptionsContainer.appendChild(categoryElement);

    const modifyBtn = document.createElement("button");
    modifyBtn.textContent = "Modificar";
    modifyBtn.onclick = () => modifyCategory(category);

    const cancelBtn = document.createElement("button");
    cancelBtn.textContent = "Cancelar";
    cancelBtn.onclick = () => cancelCategory(category);

    subscriptionsContainer.appendChild(modifyBtn);
    subscriptionsContainer.appendChild(cancelBtn);
  });
}
