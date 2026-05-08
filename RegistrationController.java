package project;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final RegistrationRepository registrationrepository;
    public RegistrationController(RegistrationRepository registrationrepository) {

        this.registrationrepository = registrationrepository;
    }
    @PostMapping("/register-event")
    public String registerForEvent(@RequestParam int eventId,
                                   HttpSession session) {


        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Long userId = loggedUser.getId();


        boolean alreadyRegistered =
registrationrepository.findByUserIdAndEventId(userId, eventId).isPresent();

        if (!alreadyRegistered) {

            Registration reg = new Registration(userId, eventId);
           registrationrepository.save(reg);
        }


        return "redirect:/my-events";
    }
}
