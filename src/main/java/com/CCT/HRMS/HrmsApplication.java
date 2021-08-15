package com.CCT.HRMS;

import java.time.LocalDate;
import java.util.Random;

import com.CCT.HRMS.business.abstracts.AdvertisementService;
import com.CCT.HRMS.business.abstracts.LocationService;
import com.CCT.HRMS.business.abstracts.WorkTimeService;
import com.CCT.HRMS.business.abstracts.Users.AuthService;
import com.CCT.HRMS.business.abstracts.Users.EmployerService;
import com.CCT.HRMS.business.abstracts.Users.JobseekerService;
import com.CCT.HRMS.dataAccess.abstracts.Users.EmployerDao;
import com.CCT.HRMS.entities.DTOs.AdvertisementDto;
import com.CCT.HRMS.entities.DTOs.EmployerRegisterDto;
import com.CCT.HRMS.entities.DTOs.JobseekerRegisterDto;
import com.CCT.HRMS.entities.concretes.Location;
import com.CCT.HRMS.entities.concretes.WorkTime;
import com.CCT.HRMS.entities.concretes.Users.Employer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class HrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmsApplication.class, args);
    }
    
    // Swagger-ui implementation
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.CCT.HRMS")).build();
    }

    @Bean
    CommandLineRunner initData(WorkTimeService workTimeService, LocationService locationService,
            JobseekerService jobseekerService, EmployerService employerService,
            AdvertisementService advertisementService, AuthService authService, EmployerDao employerDao) {

        return (String... args) -> {

            String[] cities = { "Carlow", "Cavan", "Clare", "Cork", "Donegal", "Dublin", "Galway", "Kerry", "Kildare",
                    "Kilkenny", "Laois", "Leitrim", "Limerick", "Longford", "Louth", "Mayo", "Meath", "Monaghan",
                    "Offaly", "Roscommon", "Sligo", "Tipperary", "Waterford", "Westmeath", "Wexford", "Wicklow" }; // Cities defined in the database

            String[] workTimes = { "Full-time", "Part-time", "Contract", "Temporary", "Internship", "Remotely" }; // Work times defined in the database

            for (String city : cities) {
                Location location = new Location();
                location.setName(city);
                locationService.add(location);
            }

            for (String wt : workTimes) {
                WorkTime workTime = new WorkTime();
                workTime.setName(wt);
                workTimeService.add(workTime);
            }
            
            // Defined a sample jobseeker in the database
            JobseekerRegisterDto jobseeker = new JobseekerRegisterDto();
            jobseeker.setEmail("diren@diren.com");
            jobseeker.setFirstName("Diren");
            jobseeker.setLastName("Akkoc Demir");
            jobseeker.setDateOfBirth(LocalDate.of(1994, 12, 10));
            jobseeker.setJobTitle("Software Developer");
            jobseeker.setPassword("password");
            jobseeker.setConfirmPassword("password");
            authService.jobseekerRegistration(jobseeker);
            
            /**
             * Defined samples job positions in the database system
             * Defined samples companies in the database system
             */
            String[] jobPositions = { "Java Developer", "Backend Developer", "Frontend Developer", "React Developer",
                    ".Net Developer", "Python Developer" };
            String[] companies = { "CCT", "Google", "Facebook", "Twitter", "Amazon", "Linkedin", "Indeed" };

            Random r = new Random();
            for (String company : companies) {
                EmployerRegisterDto employerRegisterDto = new EmployerRegisterDto();
                employerRegisterDto.setCompanyName(company);
                employerRegisterDto.setWebsite("www." + company + ".com");
                employerRegisterDto.setIndustry("Education");
                employerRegisterDto.setPublishedYear(r.nextInt(2021 - 1955 + 1) + 1955);
                employerRegisterDto.setEmail("info@" + company + ".com");
                employerRegisterDto.setPhone("+353" + r.nextInt(99999999));
                employerRegisterDto.setPassword("password");
                employerRegisterDto.setConfirmPassword("password");
                authService.employerRegistration(employerRegisterDto);
                Employer employer = employerDao.getUserByEmail("info@" + company + ".com");
                System.out.println("Employer : " + employer.getId());
                for (String jp : jobPositions) {
                    AdvertisementDto advertisement = new AdvertisementDto();
                    advertisement.setEmployerId(employer.getId());
                    advertisement.setJobTitle(jp);
                    advertisement.setOpenPosition(r.nextInt(5) + 1);
                    advertisement.setLocationId(r.nextInt(cities.length) + 1);
                    advertisement.setWorkTimeId(r.nextInt(workTimes.length) + 1);
                    advertisement.setDescription(
                            "Company: " + company + " Lorem ipsum dolor sit amet, consectetur adipiscing eli");
                    advertisementService.add(advertisement);
                }

            }

        };

    }

}
