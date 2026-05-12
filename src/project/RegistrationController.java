package project;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    private  final RegistrationRepository registrationrepo;
    public RegistrationController(RegistrationRepository registrationrepo) {

        this.registrationrepo = registrationrepo;
    }
    @PostMapping("/register-event")
    public String registerForEvent(@RequestParam Long eventId,
                                   HttpSession session) {


        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Long userId = loggedUser.getId();


        boolean alreadyRegistered =
registrationrepo.findByUserIdAndEventId(userId, eventId).isPresent();

        if (!alreadyRegistered) {

            Registration reg = new Registration(userId, eventId);
           registrationrepo.save(reg);
        }


        return "redirect:/my-events";
    }
}
