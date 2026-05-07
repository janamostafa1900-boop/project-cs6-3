package project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    // used to check if username already exists
    User findByUsername(String username);

    // used for login
    User findByUsernameAndPassword(String username, String password);
}
