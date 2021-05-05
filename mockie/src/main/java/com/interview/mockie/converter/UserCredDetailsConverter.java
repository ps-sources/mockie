package com.interview.mockie.converter;

import com.interview.mockie.dto.UserCredDetailsDTO;
import com.interview.mockie.models.Role;
import com.interview.mockie.models.UserCredDetails;
import com.interview.mockie.repository.UserDetailRepository;
import com.interview.mockie.util.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional(readOnly = true)
public class UserCredDetailsConverter implements IConverter<UserCredDetails, UserCredDetailsDTO> {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserCredDetails from(UserCredDetailsDTO u) {
        UserCredDetails obj = new UserCredDetails();
        if(Objects.nonNull(u)){
            obj.setCredId(u.getCredId());
            obj.setUsername(u.getUsername());
            obj.setPassword(u.getPassword());
            obj.setRoles(Role.valueOf(u.getRoles()));
            obj.setUserDetail(Objects.nonNull(u.getUserDetailId()) ? userDetailRepository.getOne(u.getUserDetailId())
                    : null);
        }
        return obj;
    }

    @Override
    public UserCredDetailsDTO to(UserCredDetails v) {
        UserCredDetailsDTO dto = new UserCredDetailsDTO();
        if(Objects.nonNull(v)){
            dto.setCredId(v.getCredId());
            dto.setUserDetailId(Objects.nonNull(v.getUserDetail()) ? v.getUserDetail().getUserId() : null);
            dto.setPassword(v.getPassword());
            dto.setUsername(v.getUsername());
            dto.setRoles(v.getRoles().toString());
        }
        return dto;
    }
}
