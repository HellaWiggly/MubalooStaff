package com.matt_adshead.mubaloostaff.model;

import android.support.annotation.StringDef;

import static com.matt_adshead.mubaloostaff.model.Employee.Role.ANDROID_DEVELOPER;
import static com.matt_adshead.mubaloostaff.model.Employee.Role.ANDROID_TEAM_LEAD;
import static com.matt_adshead.mubaloostaff.model.Employee.Role.CEO;
import static com.matt_adshead.mubaloostaff.model.Employee.Role.IOS_DEVELOPER;
import static com.matt_adshead.mubaloostaff.model.Employee.Role.IOS_TEAM_LEAD;

/**
 * Employee model.
 *
 * @author matta
 * @date 04/03/2018
 */
public class Employee {
    @StringDef ({CEO, ANDROID_TEAM_LEAD, ANDROID_DEVELOPER, IOS_TEAM_LEAD, IOS_DEVELOPER})
    public @interface Role {
        String CEO               = "CEO",
               ANDROID_TEAM_LEAD = "Android Team Lead",
               ANDROID_DEVELOPER = "Android Developer",
               IOS_TEAM_LEAD     = "IOS Team Lead",
               IOS_DEVELOPER     = "IOS Developer";
    }

    private String  firstName;

    private String  lastName;

    @Role
    private String  role;

    private String  profileImageUrl;

    private boolean teamLead;

    public Employee(String firstName, String lastName, String role, String profileImageUrl,
                    boolean teamLead) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.profileImageUrl = profileImageUrl;
        this.teamLead = teamLead;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public boolean isTeamLead() {
        return teamLead;
    }

    public void setTeamLead(boolean teamLead) {
        this.teamLead = teamLead;
    }
}
