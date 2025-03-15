package com.bid90.psm.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    UUID id;
    String firstName;
    String lastName;
    String email;
    String role;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}