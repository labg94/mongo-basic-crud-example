package com.cleveritgroup.mongoexample.payload.mapper;


import com.cleveritgroup.mongoexample.payload.response.CarResponse;
import com.cleveritgroup.mongoexample.model.Car;
import com.cleveritgroup.mongoexample.payload.request.CarRequest;


/**
 * You can add your custom mappers here, or you can delete this class adding a mapper library like https://mapstruct.org/ or http://modelmapper.org/
 */
public class CarMapper {
    private CarMapper() {
    }


    public static Car mapRequest(CarRequest request) {
        return Car.builder()
                  // TODO: add all the properties to map using builder
                  .id(request.getId())
                  .brand(request.getBrand())
                  .model(request.getModel())
                  .build();
    }

    public static CarResponse mapResponse(Car entity) {

        return CarResponse.builder()
                          // TODO: add all the properties to map using builder
                          .id(entity.getId())
                          .brand(entity.getBrand())
                          .model(entity.getModel())
                          .build();
    }

}
