package org.example.day12todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Todo {
    @Id
    private String id;
    private String text;
    private boolean done;

    @PrePersist
    public void ensureId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }
}
