package io.github.guiboava.bem_dosado.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks_types")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "tasks")
public class TaskType {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "describe", nullable = false, length = 20)
    private String describe;

    @OneToMany(mappedBy = "taskType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

}
