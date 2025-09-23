package io.github.guiboava.bem_dosado.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.guiboava.bem_dosado.entity.model.enums.Dependency;
import io.github.guiboava.bem_dosado.entity.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"users", "tasks", "patientHealths","contacts"})
@EntityListeners(AuditingEntityListener.class)
public class Patient {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "cpf", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "dependecy", nullable = false)
    @Enumerated(EnumType.STRING)
    private Dependency dependency;

    @Column(name = "health_plan",nullable = false,length = 50)
    private String healthPlan;

    @Column(name = "card_number",nullable = false,length = 20)
    private String cardNumber;

    @Column(name = "allergies",nullable = false,length = 500)
    private String allergies;

    @Column(name = "medications",nullable = false, length = 500)
    private String medicationsDescription;

    @Column(name = "note",nullable = false,length = 1000)
    private String note;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToMany(mappedBy = "patients", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> users;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PatientHealth> patientHealths;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PatientContact> contacts;

}
