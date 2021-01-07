package com.cleveritgroup.mongoexample.payload.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequest {

    //TODO: add all the field you need to request
    private String id;
    private String model;
    private String brand;
}