package com.cleveritgroup.mongoexample.service;


import com.cleveritgroup.mongoexample.model.Car;
import com.cleveritgroup.mongoexample.payload.mapper.CarMapper;
import com.cleveritgroup.mongoexample.payload.request.CarRequest;
import com.cleveritgroup.mongoexample.payload.response.CarResponse;
import com.cleveritgroup.mongoexample.repository.CarRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;


    @Override
    public CarResponse findById(String id) {
        return carRepository
                .findById(id)
                .map(CarMapper::mapResponse)
                .orElseThrow(() -> new NoSuchElementException("Car with id " + id + " not found"));
    }

    @Override
    public void deleteById(String id) {
        findById(id);
        carRepository.deleteById(id);
    }


    @Override
    public List<CarResponse> getAll() {
        return carRepository.findAll().stream().map(CarMapper::mapResponse).collect(Collectors.toList());
    }

    @Override
    public CarResponse update(CarRequest request) {
        findById(request.getId());
        Car updated = carRepository.save(CarMapper.mapRequest(request));
        return CarMapper.mapResponse(updated);
    }

    @Override
    public CarResponse create(CarRequest request) {

        Car example = CarMapper.mapRequest(request);
        Car save = carRepository.save(example);

        return CarMapper.mapResponse(save);

    }


}