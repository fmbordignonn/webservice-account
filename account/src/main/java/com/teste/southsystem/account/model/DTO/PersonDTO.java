package com.teste.southsystem.account.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class PersonDTO {
    private Long personId;
    private String name;
    private String type;
    private String document;
    private int score;
}