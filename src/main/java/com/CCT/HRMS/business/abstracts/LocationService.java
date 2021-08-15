package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.LocationDto;
import com.CCT.HRMS.entities.concretes.Location;

/**
 * This interface class is for business layer of Location
 * 
 * @author diren
 *
 */

public interface LocationService {
    
    Result add(Location location);

    Result delete(Location job);

    Result update(Location job);

    DataResult<List<Location>> getAll();

    DataResult<Location> getLocationById(int id);

    DataResult<List<LocationDto>> getAllForDropdown();
}
