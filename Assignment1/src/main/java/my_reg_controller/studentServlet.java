package my_reg_controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my_reg_dao.studentDao;
import my_reg_model.student;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

@WebServlet("/studentServlet")
public class studentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private studentDao studentDao = new studentDao();
    private static final Logger LOGGER = Logger.getLogger(studentServlet.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            LogManager.getLogManager().readConfiguration(getServletContext().getResourceAsStream("/WEB-INF/classes/logging.properties"));
            LOGGER.info("Logging configuration loaded successfully");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not load logging configuration", e);
        }
    }

    public studentServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request");
        request.getRequestDispatcher("/WEB-INF/views/myStudent.jsp").forward(request, response);
        LOGGER.info("GET request processed successfully");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received POST request");

        String id = request.getParameter("id");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");

        if (id != null && !id.isEmpty() && firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            try {
                int studentId = Integer.parseInt(id);
                student student = new student(studentId, firstName, lastName);
                studentDao.insertStudent(student);
                response.getWriter().append("Student inserted successfully.");
                LOGGER.info("Student inserted successfully: " + student);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error inserting student", e);
                response.getWriter().append("Error: Unable to insert student. ").append(e.getMessage());
            }
        } else {
            LOGGER.warning("Invalid input provided");
            response.getWriter().append("Invalid input. Please provide valid ID, first name, and last name.");
        }

        LOGGER.info("POST request processed successfully");
    }
}
