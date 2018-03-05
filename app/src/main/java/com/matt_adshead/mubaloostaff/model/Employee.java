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
 * Employee model, represents a Mubaloo employee.
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
    /**
     * String constants representing job roles at Mubaloo.
     */
    @StringDef ({CEO, ANDROID_TEAM_LEAD, ANDROID_DEVELOPER, IOS_TEAM_LEAD, IOS_DEVELOPER})
    public @interface Role {
        String CEO               = "CEO",
               ANDROID_TEAM_LEAD = "Android Team Lead",
               ANDROID_DEVELOPER = "Android Developer",
               IOS_TEAM_LEAD     = "IOS Team Lead",
               IOS_DEVELOPER     = "IOS Developer";
    }

    /**
     * Unique ID for database purposes.
     */
    @PrimaryKey
    private int     id;

    /**
     * Foreign key links employees to teams many to one.
     */
    @ColumnInfo(name = "team_id")
    private int     teamId;

    /**
     * Employee's first name.
     */
    @ColumnInfo(name = "first_name")
    private String  firstName;

    /**
     * Employee's last name.
     */
    @ColumnInfo(name = "last_name")
    private String  lastName;

    /**
     * Employee's job role.
     * See {@link Role}
     */
    @Role
    @ColumnInfo(name = "role")
    private String  role;

    /**
     * Employee's profile image URL.
     */
    @ColumnInfo(name = "profile_image_url")
    private String  profileImageUrl;

    /**
     * Boolean flag representing whether the employee is a team lead.
     */
    @ColumnInfo(name = "is_team_lead")
    private boolean teamLead;

    /**
     * Constructor.
     *
     * @param id              Unique ID for database purposes.
     * @param firstName       First name.
     * @param lastName        Last name.
     * @param role            Role, see {@link Role}.
     * @param profileImageUrl
     * @param teamLead
     */
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
