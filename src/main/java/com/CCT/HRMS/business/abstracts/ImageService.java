package com.CCT.HRMS.business.abstracts;

import java.util.List;

import com.CCT.HRMS.core.Results.DataResult;
import com.CCT.HRMS.core.Results.Result;
import com.CCT.HRMS.entities.DTOs.ImageForAddDto;
import com.CCT.HRMS.entities.concretes.Image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    
    Result add(ImageForAddDto imageForAddDto,MultipartFile multipartFile);

    Result delete(int id);

    // Result update(Image image);

    DataResult<List<Image>> getAll();

    // DataResult<Image> getImageById(int id);

    DataResult<Image> getImageByUserId(int id);
}
