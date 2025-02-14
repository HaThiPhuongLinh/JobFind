package com.jobfind.dto.response;
import com.jobfind.dto.dto.ApplicationStatusDTO;
import com.jobfind.models.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ApplicationStatusResponse {
    private Application application;
    private List<ApplicationStatusDTO> statusDTOList;
}
