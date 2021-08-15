package com.CCT.HRMS.business.concretes.ResumeInfos;

import java.time.LocalDate;
import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.ExperienceService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeInfos.ExperienceDao;
import com.CCT.HRMS.entities.DTOs.EducationForAddDto;
import com.CCT.HRMS.entities.DTOs.ExperienceForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Experience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Experience
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class ExperienceManager implements ExperienceService {

	// Properties
	private ExperienceDao experienceDao;
	private ResumeDao resumeDao;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public ExperienceManager(ExperienceDao experienceDao, ResumeDao resumeDao) {
		this.experienceDao = experienceDao;
		this.resumeDao = resumeDao;
	}

	/**
	 * Adding Experince class to the Resume
	 */
	@Override
	public Result add(ExperienceForAddDto experienceForAddDto) {
		Result result = BusinessRules.run(checkNullInputs(experienceForAddDto), checkStartDateIsValid(experienceForAddDto.getStartDate()),
				checkResumeExist(experienceForAddDto.getJobseekerId()));
		if (result.isSuccess()) {
			Experience experience = new Experience();
			experience.setResume(resumeDao.getByJobseeker_Id(experienceForAddDto.getJobseekerId()));
			experience.setCompany(experienceForAddDto.getCompany());
			experience.setJobTitle(experienceForAddDto.getJobTitle());
			experience.setDescription(experienceForAddDto.getDescription());
			experience.setStartDate(experienceForAddDto.getStartDate());
			experience.setEndDate(experienceForAddDto.getEndDate());
			experienceDao.save(experience);
			return new SuccessResult(Messages.ExperienceAdded);
		}
		return new ErrorResult(result.getMessage());

	}

	/**
	 * Deleting Experince class from the Resume by id
	 */
	@Override
	public Result delete(int id) {
		Experience experienceToDelete = this.experienceDao.getById(id);
		if (experienceToDelete != null) {
			this.experienceDao.delete(experienceToDelete);
			return new SuccessResult(Messages.ExperienceDeleted);
		}
		return new ErrorResult(Messages.ExperienceNotFound);
	}

	/**
	 * Updating Experience class from the Resume by id
	 */
	@Override
	public Result update(Experience experience) {
		Result result = BusinessRules.run(checkStartDateIsValid(experience.getStartDate()),
				checkExperienceExist(experience.getId()));
		if (result.isSuccess()) {
			Experience experienceToUpdate = this.experienceDao.getById(experience.getId());
			experienceToUpdate.setCompany(experience.getCompany());
			experienceToUpdate.setDescription(experience.getDescription());
			experienceToUpdate.setJobTitle(experience.getJobTitle());
			experienceToUpdate.setStartDate(experience.getStartDate());
			experienceToUpdate.setEndDate(experience.getEndDate());
			this.experienceDao.saveAndFlush(experienceToUpdate);
			return new SuccessResult(Messages.ExperienceUpdated);
		}
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Getting all the Experience information from the database
	 */
	@Override
	public DataResult<List<Experience>> getAll() {
		List<Experience> experiences = experienceDao.findAll();
		if (experiences.isEmpty()) {
			return new ErrorDataResult<>(Messages.ExperiencesAreNotFound);
		}
		return new SuccessDataResult<List<Experience>>(experiences);
	}

	/**
	 * Getting all the Experience information from the database by resumeId
	 */
	@Override
	public DataResult<List<Experience>> getExperienceByResumeId(int id) {
		List<Experience> experiences = experienceDao.getExperienceByResumeId(id);
		return new SuccessDataResult<List<Experience>>(experiences);
	}

	// Validations

	private Result checkStartDateIsValid(LocalDate startDate) {

		if (startDate.isBefore(LocalDate.now())) {
			return new SuccessResult();
		}
		return new ErrorResult("Start date must be valid.");
	}

	private Result checkExperienceExist(int id) {
		if (experienceDao.existsById(id)) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.ExperienceNotFound);
	}

	private Result checkResumeExist(int id) {

		if (resumeDao.getByJobseeker_Id(id) != null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.ResumeNotExist);
	}

	private Result checkNullInputs(ExperienceForAddDto experienceForAddDto) {
		if (experienceForAddDto.getCompany() != null && experienceForAddDto.getJobseekerId() > 0
				&& experienceForAddDto.getDescription() != null && experienceForAddDto.getStartDate() != null
				&& experienceForAddDto.getJobTitle() != null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.NullInformation);
	}
}
