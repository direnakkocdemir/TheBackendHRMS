package com.CCT.HRMS.business.concretes.ResumeInfos;

import java.time.LocalDate;
import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.EducationService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeInfos.EducationDao;
import com.CCT.HRMS.entities.DTOs.EducationForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Education;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Education
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class EducationManager implements EducationService {

	// Properties
	private EducationDao educationDao;
	private ResumeDao resumeDao;

	// Constructor
	@Autowired // Spring bean annotation injects object dependency implicitly
	public EducationManager(EducationDao educationDao, ResumeDao resumeDao) {
		this.educationDao = educationDao;
		this.resumeDao = resumeDao;
	}

	/**
	 * Adding Education class to the Resume
	 */
	@Override
	public Result add(EducationForAddDto educationForAddDto) {
		Result result = BusinessRules.run(checkNullInputs(educationForAddDto),
				checkStartDateIsValid(educationForAddDto.getStartDate()),
				checkResumeExist(educationForAddDto.getJobseekerId()));
		if (result.isSuccess()) {
			Education education = new Education();
			education.setResume(resumeDao.getByJobseeker_Id(educationForAddDto.getJobseekerId()));
			education.setSchoolName(educationForAddDto.getSchoolName());
			education.setDepartment(educationForAddDto.getDepartment());
			education.setStartDate(educationForAddDto.getStartDate());
			education.setEndDate(educationForAddDto.getEndDate());
			educationDao.save(education);
			return new SuccessResult(Messages.EducationAdded);
		}
		return new ErrorResult(result.getMessage());
	}

	/**
	 * Deleting Education class from the Resume by id
	 */
	@Override
	public Result delete(int id) {
		Education educationToDelete = this.educationDao.getById(id);
		if (educationToDelete != null) {
			this.educationDao.delete(educationToDelete);
			return new SuccessResult(Messages.EducationDeleted);
		}
		return new ErrorResult(Messages.EducationNotFound);
	}

	/**
	 * Updating Education class from the Resume by id
	 */
	@Override
	public Result update(Education education) {
		Result result = BusinessRules.run(checkStartDateIsValid(education.getStartDate()),
				checkEducationExist(education.getId()));
		if (result.isSuccess()) {
			Education educationToUpdate = this.educationDao.getById(education.getId());
			educationToUpdate.setDepartment(education.getDepartment());
			educationToUpdate.setEndDate(education.getEndDate());
			educationToUpdate.setSchoolName(education.getSchoolName());
			educationToUpdate.setStartDate(education.getStartDate());
			this.educationDao.saveAndFlush(educationToUpdate);
			return new SuccessResult();
		}

		return new ErrorResult(result.getMessage());
	}

	/**
	 * Getting all the Education information from the database
	 */
	@Override
	public DataResult<List<Education>> getAll() {
		List<Education> educations = educationDao.findAll();
		if (educations.isEmpty()) {
			return new ErrorDataResult<>(Messages.EducationsAreNotFound);
		}
		return new SuccessDataResult<>(educations);
	}

	/**
	 * Getting all the Education information from the database by resumeId
	 */
	@Override
	public DataResult<List<Education>> getEducationByResumeId(int id) {
		List<Education> educations = educationDao.getEducationByResumeId(id);
		return new SuccessDataResult<>(educations);
	}

	// Validations

	private Result checkStartDateIsValid(LocalDate startDate) {

		if (startDate.isBefore(LocalDate.now())) {
			return new SuccessResult();
		}
		return new ErrorResult("Start date must be valid.");
	}

	// Validations
	private Result checkEducationExist(int id) {
		if (educationDao.existsById(id)) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.EducationNotFound);
	}

	private Result checkResumeExist(int id) {

		if (resumeDao.getByJobseeker_Id(id) != null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.ResumeNotExist);
	}

	private Result checkNullInputs(EducationForAddDto educationForAddDto) {
		if (educationForAddDto.getDepartment() != null && educationForAddDto.getJobseekerId() > 0
				&& educationForAddDto.getSchoolName() != null && educationForAddDto.getStartDate() != null) {
			return new SuccessResult();
		}
		return new ErrorResult(Messages.NullInformation);
	}
}
