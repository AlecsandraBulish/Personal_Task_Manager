package ru.bulish.personal_task_manager.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @NotBlank(message = "The title can't be empty")
    @Column
    private String name;


    @Column
    private String definition;

    @Column
    private Long start_time;
    @Column
    private Long end_time;

    @Column
    private String summary_time;

    @Column
    private String level;

    @Column
    private Boolean enabled = true;


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_card",
            joinColumns = @JoinColumn(name = "card_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> users;

    public Long getStart_time() {
        return start_time;
    }

    public void setStart_time(Long start_time) {
        this.start_time = start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getSummary_time() {
        return summary_time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setSummary_time(String summary_time) {
        this.summary_time = summary_time;
    }

    public List<UserEntity> getUsers() {
        return users;
    }
    public void addUser(UserEntity user){
        if (users==null){
            users = new ArrayList<>();
        }
        users.add(user);
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }



    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
