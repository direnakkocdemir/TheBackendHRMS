package com.CCT.HRMS.business.concretes.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.AboutService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeInfos.AboutDao;
import com.CCT.HRMS.entities.DTOs.AboutForAddDto;
import com.CCT.HRMS.entities.concretes.Resume;
import com.CCT.HRMS.entities.concretes.ResumeInfos.About;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class AboutManager implements AboutService {

    // Properties
    private AboutDao aboutDao;
    private ResumeDao resumeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public AboutManager(AboutDao aboutDao, ResumeDao resumeDao) {
        this.aboutDao = aboutDao;
        this.resumeDao = resumeDao;
    }

    /**
     * Adding About class to the Resume
     */
    @Override
    public Result add(AboutForAddDto aboutForAddDto) {
        Result result = BusinessRules.run(checkResumeExist(aboutForAddDto.getJobseekerId()),
                checkJobseekerHasAbout(aboutForAddDto.getJobseekerId()));
        if (result.isSuccess()) {
            About about = new About();
            about.setResume(resumeDao.getByJobseeker_Id(aboutForAddDto.getJobseekerId()));
            about.setAbout(aboutForAddDto.getAbout());
            aboutDao.save(about);
            return new SuccessResult(Messages.AboutAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Deleting About class from the Resume by id
     */
    @Override
    public Result delete(int id) {
        About aboutToDelete = this.aboutDao.getById(id);
        if (aboutToDelete != null) {
            this.aboutDao.delete(aboutToDelete);
            return new SuccessResult(Messages.AboutDeleted);
        }
        return new ErrorResult(Messages.AboutNotFound);
    }

    /**
     * Updating About class from the Resume by id
     */
    @Override
    public Result update(About about) {
        Result result = BusinessRules.run(checkAboutExist(about.getId()));
        if (result.isSuccess()) {
            About aboutToUpdate = this.aboutDao.getById(about.getId());
            aboutToUpdate.setAbout(about.getAbout());
            this.aboutDao.save(aboutToUpdate);
            return new SuccessResult(Messages.AboutUpdated);
        }
        return new ErrorResult(result.getMessage());
    }

    /***
     * Getting all the About information from the database
     */
    @Override
    public DataResult<List<About>> getAll() {
        List<About> abouts = aboutDao.findAll();
        if (abouts.isEmpty()) {
            return new ErrorDataResult<List<About>>(Messages.AboutsAreNotFound);
        }
        return new SuccessDataResult<List<About>>(abouts);
    }

    /**
     * Getting About information from database by resumeId
     */
    @Override
    public DataResult<List<About>> getAboutByResumeId(int id) {
        List<About> abouts = aboutDao.getAboutByResumeId(id);
        if (abouts.isEmpty()) {
            return new ErrorDataResult<>(Messages.AboutsAreNotFound);
        }
        return new SuccessDataResult<>(abouts);
    }

    // Validations

    private Result checkJobseekerHasAbout(int jobseekerId) {

        Resume resume = resumeDao.getByJobseeker_Id(jobseekerId);
        if (resume.getAbouts().isEmpty()) {
            return new SuccessResult();
        }
        List<About> abouts = resume.getAbouts();
        for (About about : abouts) {
            About aboutToDelete = this.aboutDao.getById(about.getId());
            this.aboutDao.delete(aboutToDelete);
            return new SuccessResult();
        }
        return new ErrorResult();
    }

    private Result checkResumeExist(int id) {

        if (resumeDao.getByJobseeker_Id(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.ResumeNotExist);
    }

    private Result checkAboutExist(int id) {

        if (aboutDao.existsById(id)) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.AboutNotExist);
    }

}
