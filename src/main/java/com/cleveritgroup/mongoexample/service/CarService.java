package com.cleveritgroup.mongoexample.service;

import com.cleveritgroup.mongoexample.payload.request.CarRequest;
import com.cleveritgroup.mongoexample.payload.response.CarResponse;

import java.util.List;


public interface CarService {


    CarResponse findById(String id);

    void deleteById(String id);

    List<CarResponse> getAll();

    CarResponse update(CarRequest request);

    CarResponse create(CarRequest request);

}
