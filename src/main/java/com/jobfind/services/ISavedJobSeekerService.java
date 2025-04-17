package com.jobfind.services;

import com.jobfind.dto.dto.JobSeekerProfileDTO;

import java.util.List;

public interface ISavedJobSeekerService {
    void saveJobSeeker(Integer jobSeekerProfileId, Integer companyId);
    void unsaveJobSeeker(Integer jobSeekerProfileId, Integer companyId);
    List<JobSeekerProfileDTO> getListSavedJobSeekers(Integer companyId);
}
