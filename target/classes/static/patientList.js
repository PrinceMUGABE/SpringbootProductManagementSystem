// patientList.js

// ... Existing scripts ...

// Search patients by symptoms
function searchPatients() {
    const symptomInput = document.getElementById('symptom');
    const symptom = symptomInput.value.trim();

    if (symptom !== '') {
        fetch(`/patients/searchBySymptom?symptom=${symptom}`)
            .then(response => response.json())
            .then(data => {
                const patientTableBody = document.getElementById('patientTableBody');
                patientTableBody.innerHTML = ''; // Clear previous data

                data.forEach(patient => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${patient.id}</td>
                        <td>${patient.name}</td>
                        <td>${patient.age}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.symptoms}</td>
                        <td>
                            <a class="btn btn-primary btn-sm" href="/patients/edit/${patient.id}">Edit</a>
                            <a class="btn btn-danger" href="/patients/delete/${patient.id}">Delete</a>
                        </td>
                    `;
                    patientTableBody.appendChild(row);
                });
            })
            .catch(error => console.error('Error searching patients:', error));
    } else {
        // If the symptom input is empty, fetch all patients
        fetchPatients();
    }
}
