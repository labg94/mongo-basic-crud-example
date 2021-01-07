package com.cleveritgroup.mongoexample.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Car {
    //TODO: This is the data model please add the rest of the fields
    @Id
    private String id;

    private String model;
    private String brand;


}