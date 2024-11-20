
package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import rw.academics.OnlineBankingSystem.model.MyAppUser;
import rw.academics.OnlineBankingSystem.model.MyAppUserRepository;
import rw.academics.OnlineBankingSystem.service.UserExportService;
import rw.academics.OnlineBankingSystem.service.UserService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @Autowired
    private MyAppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserExportService userExportService;
    // Show the list of users
    @GetMapping("/adminUsers")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "adminUsers";  // Points to the Thymeleaf template "adminUsers.html"
    }

    // Show the add user form
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new MyAppUser());
        return "addUserForm";  // Points to a Thymeleaf template "addUserForm.html"
    }

    // Handle form submission to add a new user
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("Date of Birth: " + user.getDateOfBirth());

        return "redirect:/adminUsers";
    }

    // Show the edit user form
    @GetMapping("/editUser/{id}")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        MyAppUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "editUserForm";  // Reusing addUserForm.html for edit
    }

    // Handle form submission to edit a user
    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") MyAppUser user) {
        MyAppUser existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFullName(user.getFullName());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setIdType(user.getIdType());
        existingUser.setIdNumber(user.getIdNumber());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        // Only update the password if it's non-empty
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userRepository.save(existingUser);
        return "redirect:/adminUsers";
    }

    // Handle delete user request
    @PostMapping("/api/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/adminUsers";
    }

    // Download users as an Excel file
    @GetMapping("/admin/download-users")
    public ResponseEntity<InputStreamResource> downloadUsers() throws IOException {
        List<MyAppUser> users = userService.findAllUsers(); // Retrieve the list of users
        ByteArrayInputStream in = userExportService.exportUsersToExcel(users);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=users.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
