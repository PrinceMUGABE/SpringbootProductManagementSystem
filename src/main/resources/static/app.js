function submitForm() {
    // Validate the form using JavaScript
    if (validateForm()) {
        // Form is valid, proceed to submit
        const formData = {
            name: document.getElementById('name').value,
            age: document.getElementById('age').value,
            gender: document.getElementById('gender').value,
            symptoms: document.getElementById('symptoms').value
        };

        // Assuming you have an API endpoint for submitting patient data
        fetch('/patients', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                // You can handle success response here (e.g., show a success message)
            })
            .catch((error) => {
                console.error('Error:', error);
                // Handle error (e.g., show an error message to the user)
            });
    }
}

function validateForm() {
    // Perform basic form validation using JavaScript
    const name = document.getElementById('name').value;
    const age = document.getElementById('age').value;
    const symptoms = document.getElementById('symptoms').value;

    if (name.trim() === '' || age.trim() === '' || symptoms.trim() === '') {
        alert('All fields are required');
        return false;
    }

    // You can add more specific validation logic if needed

    return true;
}
