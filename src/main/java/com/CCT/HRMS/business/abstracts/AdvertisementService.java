package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.AdvertisementDto;
import com.CCT.HRMS.entities.DTOs.AdvertisementFilter;
import com.CCT.HRMS.entities.DTOs.AdvertisementResultDto;
import com.CCT.HRMS.entities.concretes.Advertisement;

/**
 * This interface class is for business layer of Advertisement
 * 
 * @author diren
 *
 */

public interface AdvertisementService {

	Result add(AdvertisementDto advertisementDto);

	Result delete(int id);

	Result update(Advertisement advertisement);

	Result application(int advertisementId, int jobseekerId);

	Result closeAdvertisement(int id);

	DataResult<Advertisement> getById(int advertisementId);

	DataResult<List<Advertisement>> getAll();

	DataResult<List<Advertisement>> getAll(int pageNo, int pageSize);

	DataResult<Advertisement> getJobAdById(int id);

	DataResult<List<Advertisement>> getJobAdsByEmployerId(int id);

	DataResult<List<Advertisement>> getJobAdsByEmployerName(String name, int pageNo, int pageSize);

	DataResult<List<Advertisement>> getJobAdsByJobTitle(String jobTitle, int pageNo, int pageSize);

	DataResult<List<Advertisement>> getJobAdsByLocation(int locationId, int pageNo, int pageSize);

	DataResult<List<Advertisement>> getJobAdsByJobTitleAndLocation(String jobTitle, int locationId, int pageNo,
			int pageSize);

	DataResult<List<Advertisement>> getJobAdsByJobTitleAndLocationAndWorkTime(String jobTitle, int locationId,
			int workTimeId, int pageNo, int pageSize);

	DataResult<List<Advertisement>> getJobAdsByLocationAndWorkTime(int locationId, int workTimeId, int pageNo,
			int pageSize);

	DataResult<List<Advertisement>> getJobAdsByJobTitleAndWorkTime(String jobTitle, int workTimeId, int pageNo,
			int pageSize);

	DataResult<List<Advertisement>> getJobAdsByWorkTime(int workTimeId, int pageNo, int pageSize);
}
