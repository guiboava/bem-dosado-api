package io.github.guiboava.bem_dosado.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(of = "id")
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "describe", length = 1500, nullable = false)
    private String describe;

    @Column(name = "scheduled_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledDate;

    /*FALTA AQUI A DEPENDENCIA, MAS DEVO TROCAR UMA IDEIA COM A ANA PRIMEIRO, PARA ALINHAR A COMPREENSÃO DA PERTINENCIA DESTE CAMPO NO PATIENTE E NÃO AQUI*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_task_type")
    @JsonIgnore
    private TaskType taskType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    @JsonIgnore
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;

}
