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
      const selectedCategories = [];
  
      // Seleccionamos todos los checkboxes que están marcados
      const checkboxes = document.querySelectorAll(
        'input[name="category"]:checked'
      );
  
      // Iteramos sobre los checkboxes marcados y añadimos sus valores al array
      checkboxes.forEach((checkbox) => {
        selectedCategories.push(checkbox.value);
      });

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
          },
          body: JSON.stringify(data), // Convertimos el objeto data a una cadena JSON
        })
          .then((response) => {
            // Verificamos si la respuesta fue exitosa
            if (response.ok) {
              return response.json(); // Convertimos la respuesta a JSON
            } else {
              return response.json().then((error) => {
                throw new Error(error.message); // Lanzamos un error con el mensaje del backend
              });
            }
          })
          .then((data) => {
            // Imprimimos en consola el objeto recibido en caso de éxito
            alert("Suscripción exitosa:", data);
            
            /* const subscriptionsContainer = document.getElementById("subscriptions-container");
            subscriptionsContainer.innerHTML = ''; // Limpiar el contenedor antes de agregar nuevo contenido
        
            let {subscriptions} = data.categories;

            subscriptions.forEach(subscription => {
                const subscriptionElement = document.createElement("div");
                subscriptionElement.className = 'subscription';
                
                const phoneText = document.createElement("p");
                phoneText.textContent = `Teléfono: ${subscription.phoneNumber}`;

                subscriptionElement.appendChild(phoneText);
                
                // Crear elementos para las categorías
                for (const category in subscription.categories) {
                    const categoryElement = document.createElement("p");
                    categoryElement.textContent = `Categoría: ${category} - ${subscription.categories[category]}`;
        
                    const modifyButton = createButton('Modificar', () => modifyCategory(subscription.id, category));
                    const cancelButton = createButton('Cancelar', () => cancelCategory(subscription.id, category));
        
                    // Añadir el texto de la categoría y los botones al elemento de la suscripción
                    subscriptionElement.appendChild(categoryElement);
                    subscriptionElement.appendChild(modifyButton);
                    subscriptionElement.appendChild(cancelButton);
                }
        
                subscriptionsContainer.appendChild(subscriptionElement);
            });
        
 */
          })
          .catch((error) => {
            // Imprimimos en consola el mensaje de error recibido del backend
            alert("Error en la suscripción:", error.message);
          });
      } else {
        alert("Número de teléfono inválido, introduzca un formato válido");
      }
    });
  });