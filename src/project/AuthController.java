package project;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
        @PostMapping("/register")
public String register(@RequestParam String username,
                       @RequestParam String email,
                       @RequestParam String password,
                       @RequestParam String role,
                       Model model) {
    
     if (username == null || username.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || role == null || role.trim().isEmpty()) {
            model.addAttribute("error", "All fields are required");
            return "register";
        }

        username = username.trim();
        email = email.trim();
        role = role.trim().toUpperCase();

    if (!role.equals("ADMIN") && !role.equals("USER")) {
            model.addAttribute("error", "Please choose a valid role");
            return "register";
        }
User oldUser = userRepo.findByUsername(username);

        if (oldUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        } 
     User user = new User(username, email, password, role);
        userRepo.save(user);

        model.addAttribute("message", "Account created successfully. Please login.");
        return "login";
    }  
    @GetMapping("/login")
    public String loginPage(@CookieValue(value = "username", required = false) String usernameCookie,
                            Model model) {
        if (usernameCookie != null && !usernameCookie.isEmpty()) {
            model.addAttribute("lastUsername", usernameCookie);
        }

        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        HttpServletResponse response,
                        Model model) {

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Username and password are required");
            return "login";
        }

        username = username.trim();

        User user = userRepo.findByUsernameAndPassword(username, password);
        if (user == null) {
            model.addAttribute("error", "Wrong username or password");
            return "login";
        }
        session.setAttribute("loggedUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());
        
 Cookie cookie = new Cookie("username", user.getUsername());
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
          if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/home";
        }

        return "redirect:/user/home";
    }

     @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
     @GetMapping("/admin/home")
    public String adminHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "admin_home";
    }

     @GetMapping("/user/home")
    public String userHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null || !"USER".equals(user.getRole())) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "user_home";
    }
}


        
    

