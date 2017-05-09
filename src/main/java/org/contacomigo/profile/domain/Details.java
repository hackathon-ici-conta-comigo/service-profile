package org.contacomigo.profile.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import org.contacomigo.profile.domain.enumeration.DetailType;

/**
 * A Details.
 */

@Document(collection = "details")
public class Details implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("detail_id")
    private String detailId;

    @NotNull
    @Field("type")
    private DetailType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetailId() {
        return detailId;
    }

    public Details detailId(String detailId) {
        this.detailId = detailId;
        return this;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public DetailType getType() {
        return type;
    }

    public Details type(DetailType type) {
        this.type = type;
        return this;
    }

    public void setType(DetailType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Details details = (Details) o;
        if (details.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, details.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Details{" +
            "id=" + id +
            ", detailId='" + detailId + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
