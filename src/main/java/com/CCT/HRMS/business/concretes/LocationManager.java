package com.CCT.HRMS.business.concretes;

import java.util.ArrayList;
import java.util.List;

import com.CCT.HRMS.entities.DTOs.*;
import com.CCT.HRMS.business.abstracts.LocationService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.dataAccess.abstracts.LocationDao;
import com.CCT.HRMS.entities.concretes.Location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class LocationManager implements LocationService {

    // Properties
    private LocationDao locationDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public LocationManager(LocationDao locationDao) {
        this.locationDao = locationDao;
    }
     /**
     * Adding a Location to the database
     */
    @Override
    public Result add(Location location) {
        locationDao.save(location);
        return new SuccessResult(Messages.LocationAdded);
    }
     /**
     *
     */
    @Override
    public Result delete(Location job) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Result update(Location job) {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Getting all Locations from the database
     */
    @Override
    public DataResult<List<Location>> getAll() {
        List<Location> locations = locationDao.findAll();
        if (locations.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(locations);
    }
    /**
     * Getting a Location by locationId from the database
     */
    @Override
    public DataResult<Location> getLocationById(int id) {
        Location location = locationDao.getById(id);
        if (location != null) {
            return new SuccessDataResult<>(location);
        }
        return new ErrorDataResult<>();
    }
    /**
     * 
     */
    @Override
    public DataResult<List<LocationDto>> getAllForDropdown() {
        List<Location> locations = locationDao.findAll(Sort.by("name"));
        List<LocationDto> locationDtos = new ArrayList<>();
        int key = 1;
        for (Location location : locations) {
            LocationDto locationDto = new LocationDto();
            locationDto.setKey(key);
            key++;
            locationDto.setText(location.getName());
            locationDto.setValue(location.getId());
            locationDtos.add(locationDto);
        }
        if (locations.isEmpty()) {
            return new ErrorDataResult<>();
        }
        return new SuccessDataResult<>(locationDtos);
    }

    //Validation

    

}
