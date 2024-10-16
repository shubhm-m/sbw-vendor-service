package openg2p.vendor.vendor.service;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public interface SupervisorRegistrationProjection {
    Long getId();

    @JsonProperty("userType")
    String getUser_type();

    @JsonProperty("firstName")
    String getFirst_name();

    @JsonProperty("lastName")
    String getLast_name();

    @JsonProperty("dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate getDate_of_birth();

    @JsonProperty("phoneNumber")
    String getPhone_number();

    @JsonProperty("supervisorEmail")
    String getSupervisor_email();

    @JsonProperty("eid")
    String getEid();
}


