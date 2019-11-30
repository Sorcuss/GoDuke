package com.goduke.validator;

import com.goduke.model.Candidate;
import com.goduke.model.Recruiter;

public class CandidateValidator {

    public static boolean validate(Candidate candidate){
        return CandidateValidator.checkNull(candidate)
                && EmailValidator.checkEmailFormat(candidate.getEmail())
                && EmailValidator.checkUniqueEmail(candidate.getEmail(), candidate.getId(), Candidate.class);
    }

    private static boolean checkNull(Candidate candidate){
        return candidate.getEmail() != null
                && candidate.getFirstname() != null
                && candidate.getUsername() != null
                && candidate.getLastname() != null
                && candidate.getPassword() != null;
    }
}
