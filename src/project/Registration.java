package project;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Registration")
public class Registration {

@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;
    private Long userId;
    private int eventId;

public Registration() {
}
public Registration(Long userId, int eventId) {
    this.userId = userId;
    this.eventId = eventId;
}


public Long getId() {
    return id;
}

public Long getUserId() {
    return userId;
}
public void setUserId(Long userId) {
    this.userId = userId;
}

public int getEventId() {

    return eventId;
}
public void setEventId(int eventId) {
    this.eventId = eventId;
}
}