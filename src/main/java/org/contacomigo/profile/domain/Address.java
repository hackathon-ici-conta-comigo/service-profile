package org.contacomigo.profile.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Address.
 */

@Document(collection = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("street")
    @TextIndexed
    private String street;

    @NotNull
    @Field("country")
    @TextIndexed
    private String country;

    @NotNull
    @Field("city")
    @TextIndexed
    private String city;

    @Field("number")
    private String number;

    @Field("complement")
    private String complement;

    @Field("latitude")
    private Double latitude;

    @Field("longitude")
    private Double longitude;

    @Field("profile_id")
    private String profileId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public Address street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public Address number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public Address complement(String complement) {
        this.complement = complement;
        return this;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Address latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Address longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getProfileId() {
        return profileId;
    }

    public Address profileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        if (address.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street='" + street + "'" +
            ", country='" + country + "'" +
            ", city='" + city + "'" +
            ", number='" + number + "'" +
            ", complement='" + complement + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", profileId='" + profileId + "'" +
            '}';
    }
}
