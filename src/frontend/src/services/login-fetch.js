document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => response.json())
        .then(data => {
            if(data.token) {
                localStorage.setItem('token', data.token);
                alert('Inicio de sesión exitoso');
                // Redireccionar al usuario a otra página o actualizar la interfaz de usuario
            } else {
                alert('Error al iniciar sesión. Por favor, revise sus credenciales.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al procesar la solicitud. Inténtelo de nuevo.');
        });
});