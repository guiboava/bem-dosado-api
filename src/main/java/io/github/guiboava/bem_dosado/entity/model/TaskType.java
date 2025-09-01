package io.github.guiboava.bem_dosado.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks_types")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "tasks")
@EntityListeners(AuditingEntityListener.class)
public class TaskType {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "describe", nullable = false, length = 20)
    private String describe;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "change_date", nullable = false)
    private LocalDateTime changeDate;

    @OneToMany(mappedBy = "taskType", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Task> tasks;

}
