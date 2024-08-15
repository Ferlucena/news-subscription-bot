/*
Este script controla el formato del número antes de enviarlo al backend
Lo configuro asi como una seguridad extra para que ingrese al back sin errores
La verificación extra la dá el backend con la misma lógica
*/

/**
 * Función para validar un número de teléfono en formato internacional
 * @param {string} phoneNumber - El número de teléfono a validar
 * @returns {boolean} - true si el número es válido, false en caso contrario
 */

function validatePhoneNumber(phoneNumber) {
  // Expresión regular para validar números de teléfono internacionales
  const regex = /^\+[1-9]\d{1,14}$/;

  // Probamos el número de teléfono contra la expresión regular
  return regex.test(phoneNumber);
}
