package com.application.service;

import com.application.dto.AdminDTO;

import java.util.List;

public interface AdministratorService {
    List<AdminDTO> getAllAdministrators();

    AdminDTO getAdministratorById(int adminId);

    AdminDTO deleteAdministratorById(int adminId);

    AdminDTO createAdministrator(AdminDTO adminDTO);

    AdminDTO update(AdminDTO adminDTO, int adminId);
}
