package com.CCT.HRMS.business.concretes;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ImageDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.Users.JobseekerDao;
import com.CCT.HRMS.entities.DTOs.ResumeDto;
import com.CCT.HRMS.entities.concretes.Image;
import com.CCT.HRMS.entities.concretes.Resume;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class ResumeManager implements ResumeService {

    // Properties
    private ResumeDao resumeDao;
    private JobseekerDao jobseekerDao;
    private ImageDao imageDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public ResumeManager(ResumeDao resumeDao, JobseekerDao jobseekerDao, ImageDao imageDao) {
        this.resumeDao = resumeDao;
        this.jobseekerDao = jobseekerDao;
        this.imageDao = imageDao;
    }

    /**
     * 
     */
    @Override
    public Result add(int jobseekerId) {
        Result result = BusinessRules.run(checkJobseekerExist(jobseekerId));
        if (result.isSuccess()) {
            Resume resume = new Resume();
            resume.setJobseeker(jobseekerDao.getById(jobseekerId));
            resumeDao.save(resume);

            Image imageForProfile = new Image();
            imageForProfile.setResume(resume);
            imageForProfile.setImageType(1);
            imageForProfile.setImageUrl("https://i.pinimg.com/originals/a6/58/32/a65832155622ac173337874f02b218fb.png");
            imageDao.save(imageForProfile);

            Image imageForBackground = new Image();
            imageForBackground.setResume(resume);
            imageForBackground.setImageType(2);
            imageForBackground
                    .setImageUrl("https://i.pinimg.com/originals/a3/af/35/a3af356c5d57a46a1abdf37421ce3ac3.jpg");
            imageDao.save(imageForBackground);

            return new SuccessResult(Messages.ResumeAdded);
        }
        return new ErrorResult(result.getMessage());

    }

    // @Override
    // public Result delete(Resume resume) {
    // // TODO Auto-generated method stub
    // return null;
    // }
    /**
     * Updating Resume information from the database
     */
    @Override
    public Result update(Resume resume) {
        Result result = BusinessRules.run(checkResumeExist(resume.getId()));
        if (result.isSuccess()) {
            Resume res = resumeDao.getByJobseeker_Id(resume.getId());
            res.setAbouts(resume.getAbouts());
            res.setEducations(resume.getEducations());
            res.setExperiences(resume.getExperiences());
            res.setLanguages(resume.getLanguages());
            res.setSkills(resume.getSkills());
            return new SuccessResult(Messages.ResumeUpdated);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * 
     */
    @Override
    public DataResult<List<Resume>> getAll() {
        List<Resume> resumes = resumeDao.findAll();
        if (resumes.isEmpty()) {
            return new ErrorDataResult<>(Messages.ResumesAreNotFound);
        }
        return new SuccessDataResult<List<Resume>>(resumes);
    }

    /**
     * Getting Resume information by jobseekerId
     */
    @Override
    public DataResult<ResumeDto> getResumeByJobseekerId(int id) {
        Result result = BusinessRules.run(checkJobseekerExist(id));
        if (result.isSuccess()) {
            Resume resume = resumeDao.getByJobseeker_Id(id);
            ResumeDto resumeDto = new ResumeDto();
            resumeDto.setId(resume.getId());
            resumeDto.setJobseekerId(resume.getJobseeker().getId());
            resumeDto.setAbouts(resume.getAbouts());
            resumeDto.setEducations(resume.getEducations());
            resumeDto.setExperiences(resume.getExperiences());
            resumeDto.setLanguages(resume.getLanguages());
            resumeDto.setSkills(resume.getSkills());
            resumeDto.setImages(resume.getImages());
            return new SuccessDataResult<>(resumeDto);
        }
        return new ErrorDataResult<>(result.getMessage());
    }

    /**
     * 
     */
    @Override
    public DataResult<Integer> getIdByJobseekerId(int id) {
        int resumeId = resumeDao.getIdByJobseeker_Id(id);
        return new SuccessDataResult<>(resumeId);
    }

    // Validations

    private Result checkResumeExist(int id) {

        if (resumeDao.getByJobseeker_Id(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.ResumeNotExist);
    }

    private Result checkJobseekerExist(int id) {

        if (jobseekerDao.existsById(id)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.JobseekerNotExist);
    }
}
