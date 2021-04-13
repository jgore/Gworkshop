package pl.goreit.blog.domain.model;

import java.time.LocalDateTime;

public class Mechanic {

    private String login;

    private String firstName;
    private String lastName;
    private String pesel;

    private Status status;
    private LocalDateTime creationDate;

    public Mechanic(String login, String firstName, String lastName, String pesel  ) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.status = Status.ACTIVE;
        this.creationDate = LocalDateTime.now();
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    enum Status {
        ACTIVE, IN_ACTIVE
    }
}
