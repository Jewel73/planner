package com.jtech.planner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {

    @SequenceGenerator(name = "user_Seq", sequenceName = "user_seq", initialValue = 100, allocationSize = 100000)
    @GeneratedValue(generator = "user_seq")
    @Id
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String userName;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

	public User(String firstName, String lastName, String userName, String password, Collection<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
        this.password = password;
		this.roles = roles;
	}

    
}
