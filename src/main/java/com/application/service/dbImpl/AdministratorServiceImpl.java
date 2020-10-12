package com.application.service.dbImpl;


import com.application.dto.AdminDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.AdministratorMapper;
import com.application.mapper.NotificatorMappingContext;
import com.application.model.Administrator;
import com.application.repository.AdministratorRepository;
import com.application.service.AdministratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private final AdministratorRepository administratorRepository;

    @Override
    public List<AdminDTO> getAllAdministrators() {
        return administratorRepository.findAll().stream().
                map(administrator -> AdministratorMapper.INSTANCE.
                        toAdministratorDto(administrator, new NotificatorMappingContext())).collect(Collectors.toList());
    }

    @Override
    public AdminDTO getAdministratorById(int adminId) {
        final Administrator getAdministrator = administratorRepository.findById(adminId).
                orElseThrow(() -> new ApplicationException(ExceptionType.ADMINISTRATOR_NOT_FOUND));
        return AdministratorMapper.INSTANCE.toAdministratorDto(getAdministrator, new NotificatorMappingContext());
    }

    @Override
    public AdminDTO deleteAdministratorById(int adminId) {
        final Administrator deleteAdministrator = administratorRepository.findById(adminId).
                orElseThrow(() -> new ApplicationException(ExceptionType.ADMINISTRATOR_NOT_FOUND));
        return AdministratorMapper.INSTANCE.toAdministratorDto(deleteAdministrator, new NotificatorMappingContext());
    }

    @Override
    public AdminDTO createAdministrator(AdminDTO adminDTO) {
        final Administrator createAdministrator = AdministratorMapper.INSTANCE.fromAdministratorDto(adminDTO, new NotificatorMappingContext());
        final Administrator saveAdministrator = administratorRepository.save(createAdministrator);
        return AdministratorMapper.INSTANCE.toAdministratorDto(saveAdministrator, new NotificatorMappingContext());
    }

    @Override
    public AdminDTO update(AdminDTO adminDTO, int adminId) {
        final Administrator updateAdministrator = administratorRepository.findById(adminId).
                orElseThrow(() -> new ApplicationException(ExceptionType.ADMINISTRATOR_NOT_FOUND));
        updateAdministrator.setFirstName(adminDTO.getFirstName());
        updateAdministrator.setLastName(adminDTO.getLastName());
        updateAdministrator.setAddress(adminDTO.getAddress());
        updateAdministrator.setPhoneNumber(adminDTO.getPhoneNumber());
        updateAdministrator.setEmail(adminDTO.getEmail());
        administratorRepository.save(updateAdministrator);
        return AdministratorMapper.INSTANCE.toAdministratorDto(updateAdministrator, new NotificatorMappingContext());
    }

}
