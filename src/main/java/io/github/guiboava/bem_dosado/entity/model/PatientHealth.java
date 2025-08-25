package io.github.guiboava.bem_dosado.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patient_healths")
@Data
@ToString(exclude = "patient")
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class PatientHealth {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "blood_pressure", nullable = false)
    private Integer bloodPressure;

    @Column(name = "heart_rate", nullable = false)
    private Integer heartRate;

    @Column(name = "oximetry", nullable = false)
    private Integer oximetry;

    @Column(name = "blood_glucose", nullable = false)
    private Integer bloodGlucose;

    @Column(name = "temperature", nullable = false)
    private BigDecimal temperature;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    @JsonIgnore
    private Patient patient;

}
