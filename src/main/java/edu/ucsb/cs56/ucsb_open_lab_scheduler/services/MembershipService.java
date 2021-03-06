package edu.ucsb.cs56.ucsb_open_lab_scheduler.services;

import java.util.List;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface MembershipService {

    /** is current logged in user a member but NOT an admin
     * of the github org */
    public boolean isMember(OAuth2AuthenticationToken oAuth2AuthenticationToken);

    /** is current logged in user a member of the github org */
    public boolean isAdmin(OAuth2AuthenticationToken oAuth2AuthenticationToken);

    public boolean isTutor(OAuth2AuthenticationToken oAuth2AuthenticationToken);
    public boolean isInstructor(OAuth2AuthenticationToken oAuth2AuthenticationToken);

    /** is current logged in user a member or admin of the
     * github org */
    default public boolean isMemberOrAdmin(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return isMember(oAuth2AuthenticationToken) || isAdmin(oAuth2AuthenticationToken);
    }

    default public String role(OAuth2AuthenticationToken token) {
        if (token==null)
            return "Guest";
        if (isAdmin(token))
           return "Admin";
        if (isTutor(token))
            return "Tutor";
        if (isInstructor(token))
            return "Instructor";
        if (isMember(token))
           return "Member";
        
        return "Guest";
    }

    public List<String> getAdminEmails();

    public String name(OAuth2AuthenticationToken token);

    public String fname(OAuth2AuthenticationToken token);

    public String lname(OAuth2AuthenticationToken token);

    public String email(OAuth2AuthenticationToken token);


}
