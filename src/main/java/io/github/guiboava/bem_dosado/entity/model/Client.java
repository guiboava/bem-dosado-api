package io.github.guiboava.bem_dosado.entity.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity
@Table
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id")
    @Length(max = 150)
    @NotNull
    private String clientId;

    @Column(name = "client_secret")
    @Length(max = 400)
    @NotNull
    private String clientSecret;

    @Column(name = "client_uri")
    @Length(max = 200)
    @NotNull
    private String redirectURI;

    @Column(name = "scope")
    @Length(max = 50)
    private String scope;

}
