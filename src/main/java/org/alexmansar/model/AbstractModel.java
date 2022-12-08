package org.alexmansar.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Setter
    @Column(name = "update_date")
    LocalDateTime updateDate;
    @CreationTimestamp
    @Column(name = "create_date")
    LocalDateTime createDate;


    public AbstractModel() {
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }
}
