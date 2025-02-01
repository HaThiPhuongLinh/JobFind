package com.jobfind.services;

import com.jobfind.dto.response.JobSeekerProfileResponse;

public interface IJobSeekerProfileService {
    JobSeekerProfileResponse getProfileByUserId(Integer userId);
}