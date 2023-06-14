package com.example.apis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "CUSTOMER")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Customer id CANNOT be null")
    @Column(name = "CUSTOMER_ID")
    @JsonIgnore
    private Long customerId;

    @Embedded
    @Column(name = "FULL_NAME")
    private Name fullName;

    @Column(name = "EMAIL")
    @Email
    private String email;
}
