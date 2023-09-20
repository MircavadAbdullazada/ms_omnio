package az.atl.ms_auth.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "loginTable")
public class LoginTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;
    private Date loginDate;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}