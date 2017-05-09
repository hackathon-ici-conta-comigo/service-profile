package org.contacomigo.profile.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfileDetails.
 */

@Document(collection = "profile_details")
public class ProfileDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("profile_id")
    private String profileId;

    @NotNull
    @Field("detail_id")
    private String detailId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public ProfileDetails profileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getDetailId() {
        return detailId;
    }

    public ProfileDetails detailId(String detailId) {
        this.detailId = detailId;
        return this;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProfileDetails profileDetails = (ProfileDetails) o;
        if (profileDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profileDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProfileDetails{" +
            "id=" + id +
            ", profileId='" + profileId + "'" +
            ", detailId='" + detailId + "'" +
            '}';
    }
}
