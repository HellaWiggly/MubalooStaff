package com.matt_adshead.mubaloostaff.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.StringDef;

import static android.arch.persistence.room.ForeignKey.CASCADE;
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
@Entity(
        foreignKeys = @ForeignKey(
                entity        = Team.class,
                parentColumns = "id",
                childColumns  = "team_id",
                onDelete      = CASCADE
        )
)
public class Employee {
    @StringDef ({CEO, ANDROID_TEAM_LEAD, ANDROID_DEVELOPER, IOS_TEAM_LEAD, IOS_DEVELOPER})
    public @interface Role {
        String CEO               = "CEO",
               ANDROID_TEAM_LEAD = "Android Team Lead",
               ANDROID_DEVELOPER = "Android Developer",
               IOS_TEAM_LEAD     = "IOS Team Lead",
               IOS_DEVELOPER     = "IOS Developer";
    }

    @PrimaryKey
    private int     id;

    @ColumnInfo(name = "team_id")
    private int     teamId;

    @ColumnInfo(name = "first_name")
    private String  firstName;

    @ColumnInfo(name = "last_name")
    private String  lastName;

    @Role
    @ColumnInfo(name = "role")
    private String  role;

    @ColumnInfo(name = "profile_image_url")
    private String  profileImageUrl;

    @ColumnInfo(name = "is_team_lead")
    private boolean teamLead;

    public Employee(int id, String firstName, String lastName, String role, String profileImageUrl,
                    boolean teamLead) {

        this.id              = id;
        this.firstName       = firstName;
        this.lastName        = lastName;
        this.role            = role;
        this.profileImageUrl = profileImageUrl;
        this.teamLead        = teamLead;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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

    public String getFullName() {
        return firstName + " " + lastName;
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
