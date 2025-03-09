package com.jobfind.services;

import com.jobfind.dto.response.SavedJobResponse;

import java.util.List;

public interface ISavedJobService {
    void saveJob(Integer jobId, Integer jobSeekerProfileId);
    void unsaveJob(Integer jobId, Integer jobSeekerProfileId);
    List<SavedJobResponse> getListSavedJobs(Integer jobSeekerProfileId);
}