package edu.fbansept.cda_2025_demo.mock;

import edu.fbansept.cda_2025_demo.security.AppUserDetails;
import edu.fbansept.cda_2025_demo.security.ISecuriteUtils;

public class MockSecuriteUtils implements ISecuriteUtils {

    private String role;

    public MockSecuriteUtils(String role) {
        this.role = role;
    }

    @Override
    public String getRole(AppUserDetails userDetails) {
        return role;
    }

    @Override
    public String generateToken(AppUserDetails userDetails) {
        return "";
    }

    @Override
    public String getSubjectFromJwt(String jwt) {
        return "";
    }
}
