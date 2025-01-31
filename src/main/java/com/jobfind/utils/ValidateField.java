package com.jobfind.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidateField {
    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        }
        return errors;
    }

    public void getCompanyFieldErrors(Map<String, String> errors, String companyName, String industry, String logoPath) {
        if (StringUtils.isEmpty(companyName)) {
            errors.put("companyName", "Company name must be required.");
        }
        if (StringUtils.isEmpty(industry)) {
            errors.put("industry", "Industry must be specified.");
        }
        if (StringUtils.isEmpty(logoPath)) {
            errors.put("logoPath", "Logo Path must be specified.");
        }
    }

    public void getJobSeekerFieldErrors(Map<String, String> errors, String firstName, String lastName, String resumePath) {
        if (StringUtils.isEmpty(firstName)) {
            errors.put("firstName", "First name must be required.");
        }
        if (StringUtils.isEmpty(lastName)) {
            errors.put("lastName", "Last name must be required.");
        }
        if (StringUtils.isEmpty(resumePath)) {
            errors.put("resumePath", "Resume path must be required.");
        }
    }
}
