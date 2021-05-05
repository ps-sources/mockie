package com.interview.mockie.converter;

import com.interview.mockie.dto.QualificationDTO;
import com.interview.mockie.models.Qualification;
import com.interview.mockie.repository.UserDetailRepository;
import com.interview.mockie.util.IConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional(readOnly = true)
public class QualificationConverter implements IConverter<Qualification, QualificationDTO> {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public Qualification from(QualificationDTO u) {
        Qualification obj = new Qualification();
        if (Objects.nonNull(u)) {
            obj.setUserDetail(Objects.nonNull(u.getUserDetailId()) ?
                    userDetailRepository.getOne(u.getQualificationId()) : null);
            obj.setQualificationId(u.getQualificationId());
            obj.setDegree(u.getDegree());
            obj.setInstitute(u.getInstitute());
            obj.setPercentage(u.getPercentage());
            obj.setYearOfPassing(u.getYearOfPassing());
        }
        return obj;
    }

    @Override
    public QualificationDTO to(Qualification v) {
        QualificationDTO dto = new QualificationDTO();
        if (Objects.nonNull(v)) {
            dto.setQualificationId(v.getQualificationId());
            dto.setDegree(v.getDegree());
            dto.setInstitute(v.getInstitute());
            dto.setPercentage(v.getPercentage());
            dto.setYearOfPassing(v.getYearOfPassing());
            dto.setUserDetailId(Objects.nonNull(v.getUserDetail()) ? v.getUserDetail().getUserId(): null);
        }
        return dto;
    }
}
