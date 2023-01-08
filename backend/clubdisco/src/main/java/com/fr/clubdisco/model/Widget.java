package com.fr.clubdisco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Table(name = "widget")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Widget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column()
    private String name;

    @NonNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_widget", joinColumns = { @JoinColumn(name = "widget_id") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
    private Set<User> users;

    public Widget(String widgetName, Set<User> users) {
        this.name = widgetName;
        this.users = users;
    }
}
