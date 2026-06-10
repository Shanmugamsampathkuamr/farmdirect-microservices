package com.order_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunnerResponseDTO {

    private Long id;
    private String fullName;
    private String phone;
    private String city;
    private String status;

}
