package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
}
