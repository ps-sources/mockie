package com.interview.mockie.converter;

import com.interview.mockie.dto.UserDetailDTO;
import com.interview.mockie.models.UserDetail;
import com.interview.mockie.util.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
public class UserDetailsConverter implements IConverter<UserDetail, UserDetailDTO> {

    @Autowired
    QualificationConverter qualificationConverter;
    @Autowired
    UserCredDetailsConverter userCredDetailsConverter;


    @Override
    public UserDetail from(UserDetailDTO u) {
        UserDetail obj = new UserDetail();
        if(Objects.nonNull(u)){
            obj.setAddressLine1(u.getAddressLine1());
            obj.setAddressLine2(u.getAddressLine2());
            obj.setBatchName(u.getBatchName());
            obj.setBatchYear(u.getBatchYear());
            obj.setUserCredDetails(userCredDetailsConverter.from(u.getUserCredDetails()));
            obj.setDateOfBirth(u.getDateOfBirth());
            obj.setFirstName(u.getFirstName());
            obj.setLastName(u.getLastName());
            obj.setEmailId(u.getEmailId());
            obj.setMobileNumber(u.getMobileNumber());
            obj.setPostalCode(u.getPostalCode());
            obj.setRollNumber(u.getRollNumber());
            obj.setQualifications(u.getQualifications().stream().map(q -> qualificationConverter.from(q)).collect(Collectors.toSet()));
        }
        return obj;
    }

    @Override
    public UserDetailDTO to(UserDetail v) {
        UserDetailDTO dto = new UserDetailDTO();
        if(Objects.nonNull(v)){
            dto.setAddressLine1(v.getAddressLine1());
            dto.setAddressLine2(v.getAddressLine2());
            dto.setBatchName(v.getBatchName());
            dto.setBatchYear(v.getBatchYear());
            dto.setUserCredDetails(userCredDetailsConverter.to(v.getUserCredDetails()));
            dto.setDateOfBirth(v.getDateOfBirth());
            dto.setFirstName(v.getFirstName());
            dto.setLastName(v.getLastName());
            dto.setEmailId(v.getEmailId());
            dto.setMobileNumber(v.getMobileNumber());
            dto.setPostalCode(v.getPostalCode());
            dto.setRollNumber(v.getRollNumber());
            dto.setQualifications(v.getQualifications().stream().map(q -> qualificationConverter.to(q)).collect(Collectors.toSet()));
        }
        return dto;
    }
}
