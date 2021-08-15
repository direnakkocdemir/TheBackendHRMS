package com.CCT.HRMS.business.concretes.ResumeInfos;

import java.util.List;

import com.CCT.HRMS.business.abstracts.ResumeInfos.SkillService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorDataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessDataResult;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.BusinessRules;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeInfos.SkillDao;
import com.CCT.HRMS.entities.DTOs.SkillForAddDto;
import com.CCT.HRMS.entities.concretes.ResumeInfos.Skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business layer to manage the Skill
 * 
 * @author diren
 *
 */

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class SkillManager implements SkillService {

    // Properties
    private SkillDao skillDao;
    private ResumeDao resumeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public SkillManager(SkillDao skillDao, ResumeDao resumeDao) {
        this.skillDao = skillDao;
        this.resumeDao = resumeDao;
    }

    /**
     * Adding Education class to the Resume
     */
    @Override
    public Result add(SkillForAddDto skillForAddDto) {
        Result result = BusinessRules.run(checkResumeExist(skillForAddDto.getJobseekerId()));
        if (result.isSuccess()) {
            Skill skill = new Skill();
            skill.setResume(resumeDao.getByJobseeker_Id(skillForAddDto.getJobseekerId()));
            skill.setSkillTitle(skillForAddDto.getSkillTitle());
            skillDao.save(skill);
            return new SuccessResult(Messages.SkillAdded);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Deleting Skill class from the Resume by id
     */
    @Override
    public Result delete(int id) {
        Skill skillToDelete = this.skillDao.getById(id);
        if (skillToDelete != null) {
            this.skillDao.delete(skillToDelete);
            return new SuccessResult(Messages.SkillDeleted);
        }
        return new ErrorResult(Messages.SkillNotFound);
    }

    /**
     * Updating Skill class from the Resume by id
     */
    @Override
    public Result update(Skill skill) {
        Result result = BusinessRules.run(checkSkillExist(skill.getId()));
        if (result.isSuccess()) {
            Skill skillToUpdate = this.skillDao.getById(skill.getId());
            skillToUpdate.setSkillTitle(skill.getSkillTitle());
            this.skillDao.saveAndFlush(skillToUpdate);
            return new SuccessResult(Messages.SkillUpdated);
        }
        return new ErrorResult(result.getMessage());
    }

    /**
     * Getting all the Skill information from the database
     */
    @Override
    public DataResult<List<Skill>> getAll() {
        List<Skill> skills = skillDao.findAll();
        if (skills.isEmpty()) {
            return new ErrorDataResult<>(Messages.SkillsAreNotFound);
        }
        return new SuccessDataResult<>(skills);
    }

    /**
     * Getting all the Skill information from the database by resumeId
     */
    @Override
    public DataResult<List<Skill>> getSkillByResumeId(int id) {
        List<Skill> skill = skillDao.getSkillByResumeId(id);
        return new SuccessDataResult<>(skill);
    }


    // Validations

    private Result checkResumeExist(int id) {

        if (resumeDao.getByJobseeker_Id(id) != null) {
            return new SuccessResult();
        }
        return new ErrorResult(Messages.ResumeNotExist);
    }

    private Result checkSkillExist(int id){

        if(skillDao.existsById(id)){
            return new SuccessResult();
        }
        return new ErrorResult(Messages.SkillNotExist);
    }
}
