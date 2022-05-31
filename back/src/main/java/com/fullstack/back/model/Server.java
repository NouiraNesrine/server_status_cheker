package com.fullstack.back.model;

import com.fullstack.back.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "IP cannot be null")
    private String ipAddress;
    private String name;
    private String memory;
    private String Type;
    private String imageURL;
    private Status status;

}
