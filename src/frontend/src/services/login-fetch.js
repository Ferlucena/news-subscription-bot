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
        .then(response => {
            if (response.ok) {
                return response.json();  // Intenta parsear como JSON solo si la respuesta fue exitosa
            } else {
                throw new Error('Fallo en la petición fetch');
            }
        })
        .then(data => {
            if(data.token) {
                localStorage.setItem('token', data.token);
                alert('Inicio de sesión exitoso');
                
            } 
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al procesar la solicitud. Inténtelo de nuevo.');
        });
});