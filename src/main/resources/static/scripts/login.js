document.getElementById('login-form').addEventListener('submit', function(event) {
  event.preventDefault();
  const formData = new FormData(this);
  const data = {};
  formData.forEach((value, key) => {
    data[key] = value;
  });

  fetch('/api/loginUser', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then(response => response.json())
  .then(data => {
    if (data.success) {
      alert(data.message);
      // Redirect to dashboard or any other page upon successful login
      window.location.href = "/dashboard.html";
    } else {
      alert(data.message);
    }
  })
  .catch(error => {
    console.error('Error:', error);
    alert('An error occurred while logging in. Please try again later.');
  });
});
