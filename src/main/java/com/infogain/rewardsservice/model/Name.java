package com.infogain.rewardsservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Schema(hidden = true)
public class Name {
    @Size(min=2, message="First name should have at least 2 characters")
    private String firstName;

    @Size(min=2, message="Last name should have at least 2 characters")
    private String lastName;
}
