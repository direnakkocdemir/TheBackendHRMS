package com.CCT.HRMS.dataAccess.abstracts;

import java.util.List;

import com.CCT.HRMS.entities.DTOs.AdvertisementResultDto;
import com.CCT.HRMS.entities.concretes.Advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository //Spring bean annotation to represent the database layer
public interface AdvertisementDao extends JpaRepository<Advertisement, Integer> {

    @Query("From Advertisement where employer_id=:id")
    List<Advertisement> getAdvertisementByEmployerId(int id);

    List<Advertisement> getByEmployer_Id(int id);

    Page<Advertisement> getByEmployer_CompanyName(String companyName, Pageable pageable);

    Page<Advertisement> getByJobTitleContainsIgnoreCase(String jobTitle, Pageable pageable);

    Page<Advertisement> getByLocation_Id(int locationId, Pageable pageable);

    Page<Advertisement> getByWorkTime_Id(int workTimeId, Pageable pageable);

    Page<Advertisement> getByJobTitleContainsIgnoreCaseAndLocation_Id(String jobTitle, int locationId,
            Pageable pageable);

    Page<Advertisement> getByJobTitleContainsIgnoreCaseAndLocation_IdAndWorkTime_Id(String jobTitle, int locationId,
            int workTimeId, Pageable pageable);

    Page<Advertisement> getByLocation_IdAndWorkTime_Id(int locationId,int workTimeId, Pageable pageable);

    Page<Advertisement> getByJobTitleContainsIgnoreCaseAndWorkTime_Id(String jobTitle,int workTimeId, Pageable pageable);

    Advertisement findById(int id);
    
//     @Query("Select new com.CCT.HRMS.entities.DTOs.AdvertisementResultDto(a.employerId,a.jobTitle,a.description,a.location,a.openPositions,a.workTime) From Advertisement a INNER JOIN Location l ON a.location.id = l.id INNER JOIN WorkTime w ON a.workTime.id = w.id INNER JOIN Employer e ON a.employer.id = e.id")
//     List<AdvertisementResultDto> findResult();
}
