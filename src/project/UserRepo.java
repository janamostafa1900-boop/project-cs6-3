package project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

   
    User findByUsername(String username);

    
    User findByUsernameAndPassword(String username, String password);
}
