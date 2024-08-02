<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .container h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .container label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .container input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .container button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .container button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Register Student</h1>
        <form action="studentServlet" method="post">
            <label for="id">Student ID</label>
            <input type="text" id="id" name="id" required>
            
            <label for="first_name">First Name</label>
            <input type="text" id="first_name" name="first_name" required>
            
            <label for="last_name">Last Name</label>
            <input type="text" id="last_name" name="last_name" required>
            
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>
