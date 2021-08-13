package com.CCT.HRMS.business.concretes.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.LanguageService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeInfos.LanguageDao;
import com.CCT.HRMS.entities.DTOs.LanguageForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class LanguageManager implements LanguageService {

    // Properties
    private LanguageDao languageDao;
    private ResumeDao resumeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public LanguageManager(LanguageDao languageDao, ResumeDao resumeDao) {
        this.languageDao = languageDao;
        this.resumeDao = resumeDao;
    }

    /**
     * Adding Education class to the Resume
     */
    @Override
    public Result add(LanguageForAddDto languageForAddDto) {
        Result result = BusinessRules.run(checkLevelIsValid(languageForAddDto.getLevel()),
                checkResumeExist(languageForAddDto.getJobseekerId()));
        if (result.isSuccess()) {
            Language language = new Language();
            language.setResume(resumeDao.getByJobseeker_Id(languageForAddDto.getJobseekerId()));
            language.setLanguage(languageForAddDto.getLanguage());
            language.setLevel(languageForAddDto.getLevel());
            languageDao.save(language);
            return new SuccessResult(Messages.LanguageAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Deleting Language class from the Resume by id
     */
    @Override
    public Result delete(int id) {
        Language languageToDelete = this.languageDao.getById(id);
        if (languageToDelete != null) {
            this.languageDao.delete(languageToDelete);
            return new SuccessResult(Messages.LanguageDeleted);
        }
        return new ErrorResult(Messages.LanguageNotFound);
    }

    /**
     * Updating Language class from the Resume by id
     */
    @Override
    public Result update(Language language) {
        Result result = BusinessRules.run(checkLevelIsValid(language.getLevel()), checkLanguageExist(language.getId()));
        if (result.isSuccess()) {
            Language languageToUpdate = this.languageDao.getById(language.getId());
            languageToUpdate.setLanguage(language.getLanguage());
            languageToUpdate.setLevel(language.getLevel());
            this.languageDao.saveAndFlush(languageToUpdate);
            return new SuccessResult(Messages.LanguageUpdated);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Getting all the Language information from the database
     */
    @Override
    public DataResult<List<Language>> getAll() {
        List<Language> languages = languageDao.findAll();
        if (languages.isEmpty()) {
            return new ErrorDataResult<>(Messages.LanguagesAreNotFound);
        }
        return new SuccessDataResult<>(languages);
    }

    /**
     * Getting all the Language information from the database by resumeId
     */
    @Override
    public DataResult<List<Language>> getLanguageByResumeId(int id) {
        List<Language> languages = languageDao.getLanguageByResumeId(id);
        return new SuccessDataResult<>(languages);
    }

    // Validations

    private Result checkLevelIsValid(int level) {

        if (level <= 5 && level > 0) {
            return new SuccessResult();
        }
        return new ErrorResult("Level should be between 1 and 5");
    }

    private Result checkResumeExist(int id) {

        if (resumeDao.getByJobseeker_Id(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.ResumeNotExist);
    }

    private Result checkLanguageExist(int id) {
        if (languageDao.existsById(id)) {
            return new SuccessResult();
        }
        return new ErrorResult("Language does not exist.");
    }

}
