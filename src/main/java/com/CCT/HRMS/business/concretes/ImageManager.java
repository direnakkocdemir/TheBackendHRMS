package com.CCT.HRMS.business.concretes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.CCT.HRMS.business.abstracts.ImageService;
import com.CCT.HRMS.business.constants.Messages;
import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.ErrorResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.core.Results.SuccessResult;
import com.CCT.HRMS.core.Utilities.Cloudinary.CloudinaryService;
import com.CCT.HRMS.dataAccess.abstracts.ImageDao;
import com.CCT.HRMS.dataAccess.abstracts.ResumeDao;
import com.CCT.HRMS.entities.DTOs.ImageForAddDto;
import com.CCT.HRMS.entities.concretes.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service // Spring bean annotation to indicate the layer that holding the business logic
public class ImageManager implements ImageService {

    // Properties
    private ImageDao imageDao;
    private CloudinaryService cloudinaryService;
    private ResumeDao resumeDao;

    // Constructor
    @Autowired // Spring bean annotation injects object dependency implicitly
    public ImageManager(ImageDao imageDao, CloudinaryService cloudinaryService, ResumeDao resumeDao) {
        this.imageDao = imageDao;
        this.cloudinaryService = cloudinaryService;
        this.resumeDao = resumeDao;
    }
    /**
     * Adding an Image to the database
     */
    @Override
    public Result add(ImageForAddDto imageForAddDto, MultipartFile multipartFile) {
        List<Image> i = imageDao.getByResume_Id(imageForAddDto.getJobseekerId());
        if (i.isEmpty()) {
            Image image = new Image();
            image.setResume(resumeDao.getById(imageForAddDto.getJobseekerId()));
            image.setImageType(imageForAddDto.getImageType());
            try {
                Map photoMap = cloudinaryService.upload(multipartFile);
                image.setImageUrl((String) photoMap.get("url"));
                imageDao.save(image);
            } catch (IOException e) {
                return new ErrorResult("Image is not added" + e);
            }

            return new SuccessResult("Image is added");
        } else if (i.size() >= 1) {
        	for (Image oldImage : i) {
				if(oldImage.getImageType()==imageForAddDto.getImageType()) {
					delete(oldImage.getId());
					Image image = new Image();
		            image.setResume(resumeDao.getById(imageForAddDto.getJobseekerId()));
		            image.setImageType(imageForAddDto.getImageType());
		            try {
		                Map photoMap = cloudinaryService.upload(multipartFile);
		                image.setImageUrl((String) photoMap.get("url"));
		                imageDao.save(image);
		            } catch (IOException e) {
		                return new ErrorResult("Image is not added" + e);
		            }
				}
			}
            return new SuccessResult("Image is changed.");
        }

        return new ErrorResult("Image is not added");
    }
    /**
     * 
     * Deleting an Image from the database
     */
    @Override
    public Result delete(int id) {
        Image imageToDelete = this.imageDao.getById(id);
        if(imageToDelete!=null) {
        	this.imageDao.delete(imageToDelete);
        	return new SuccessResult(Messages.ImageDeleted);
        }
        return new ErrorResult(Messages.ImageNotExist);
    }
    
    @Override
    public DataResult<List<Image>> getAll() {
        // TODO Auto-generated method stub
        return null;
    }
    /**
     * Getting an Image by userId from the database
     */
    @Override
    public DataResult<Image> getImageByUserId(int id) {
        // TODO Auto-generated method stub
        return null;
    }

}
