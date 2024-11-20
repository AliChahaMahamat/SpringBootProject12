document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.querySelector('section');
    signupForm.style.opacity = 0;

    setTimeout(() => {
        signupForm.style.transition = 'opacity 1s ease-in-out';
        signupForm.style.opacity = 1;
    }, 500);

    const signupButton = document.querySelector('button');
    const emailInput = document.querySelector('input[type="email"]');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('passwordcon');
    const fullNameInput = document.getElementById('full-name');
    const dateOfBirthInput = document.getElementById('dob');
    const phoneNumberInput = document.getElementById('phone-number');
    const idTypeInput = document.getElementById('id-type');
    const idNumberInput = document.getElementById('id-number');
    const roleInput = document.getElementById('role'); // Role selection

    const gmailPattern = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
    const fullNamePattern = /^[a-zA-Z\s]+$/;  // Full name: only letters and spaces
    const phoneNumberPattern = /^\+\d{1,12}$/; // Phone number: starts with '+' and max 13 characters
    const idNumberPattern = /^\d{1,10}$/; // ID Number: max 10 digits

    signupButton.addEventListener('click', function (event) {
        event.preventDefault();  // Prevent form submission until validation passes

        const emailValue = emailInput.value;
        const passwordValue = passwordInput.value;
        const confirmPasswordValue = confirmPasswordInput.value;
        const fullNameValue = fullNameInput.value;
        const dateOfBirthValue = dateOfBirthInput.value;
        const phoneNumberValue = phoneNumberInput.value;
        const idNumberValue = idNumberInput.value;
        const selectedRole = roleInput.value; // Capture the selected role

        // Validate full name (no special characters)
        if (!fullNamePattern.test(fullNameValue)) {
            alert('Full name can only contain letters and spaces.');
            fullNameInput.classList.add('invalid');
            return;
        } else {
            fullNameInput.classList.remove('invalid');
        }

        // Validate date of birth
        if (!dateOfBirthValue) {
            alert('Please select a valid date of birth.');
            dateOfBirthInput.classList.add('invalid');
            return;
        } else {
            dateOfBirthInput.classList.remove('invalid');
        }

        // Validate phone number
        if (!phoneNumberPattern.test(phoneNumberValue)) {
            alert('Phone number must start with "+" and be at most 13 characters.');
            phoneNumberInput.classList.add('invalid');
            return;
        } else {
            phoneNumberInput.classList.remove('invalid');
        }

        // Validate ID type and ID number
        if (idTypeInput.value === '') {
            alert('Please select an ID type.');
            idTypeInput.classList.add('invalid');
            return;
        } else {
            idTypeInput.classList.remove('invalid');
        }

        if (!idNumberPattern.test(idNumberValue)) {
            alert('ID number must be at most 10 digits.');
            idNumberInput.classList.add('invalid');
            return;
        } else {
            idNumberInput.classList.remove('invalid');
        }

        // Validate Gmail format
        if (!gmailPattern.test(emailValue)) {
            alert('Please enter a valid Gmail address (e.g., example@gmail.com).');
            emailInput.classList.add('invalid');
            return;
        } else {
            emailInput.classList.remove('invalid');
        }

        // Validate password match
        if (passwordValue !== confirmPasswordValue) {
            alert('Passwords do not match.');
            passwordInput.classList.add('invalid');
            confirmPasswordInput.classList.add('invalid');
            return;
        } else {
            passwordInput.classList.remove('invalid');
            confirmPasswordInput.classList.remove('invalid');
        }

        // If all validations pass, prepare the data for submission
        const data = {
            username: document.getElementById('username').value,
            email: emailValue,
            password: passwordValue,
            fullName: fullNameValue,
            dateOfBirth: dateOfBirthValue,
            phoneNumber: phoneNumberValue,
            idType: idTypeInput.value,
            idNumber: idNumberValue,
            roles: [selectedRole] // Include the selected role in the request body
        };

        const jsonData = JSON.stringify(data);

        // Submit data using fetch
        fetch('/req/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
        .then(response => {
            if (response.ok) {
                alert('you have registred successful!');
                window.location.href = '/req/login';  // Redirect to login page
            } else {
                alert('Signup failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
    });
});
