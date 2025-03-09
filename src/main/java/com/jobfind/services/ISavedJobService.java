package com.jobfind.services;

import com.jobfind.dto.response.SavedJobResponse;

import java.util.List;

public interface ISavedJobService {
    void saveJob(Integer jobId, Integer userId);
    void unsaveJob(Integer jobId, Integer userId);
    List<SavedJobResponse> getListSavedJobs(Integer userId);
}