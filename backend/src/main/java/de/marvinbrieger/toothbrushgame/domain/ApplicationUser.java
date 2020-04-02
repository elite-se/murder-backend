package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ApplicationUser {
    @Id
    @JsonIgnore
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String deviceId;

    private String password;

    private String pushToken;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private Set<Player> players;
}
