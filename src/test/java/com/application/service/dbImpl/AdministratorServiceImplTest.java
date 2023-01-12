package com.application.service.dbImpl;

import com.application.model.Administrator;
import com.application.dto.AdminDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.application.repository.AdministratorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdministratorServiceImplTest {
    private static final int ID_VALUE = 1;
    private Administrator firstAdministrator;
    private Administrator secondAdministrator;
    @InjectMocks
    private AdministratorServiceImpl administratorService;
    @Mock
    private AdministratorRepository administratorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        administratorService = new AdministratorServiceImpl(administratorRepository);

        firstAdministrator = new Administrator();
        firstAdministrator.setId(1);
        firstAdministrator.setFirstName("Max");
        firstAdministrator.setLastName("Cameron");
        firstAdministrator.setAddress("Douala");
        firstAdministrator.setPhoneNumber(3564789566L);
        firstAdministrator.setEmail("max@cameron.com");

        secondAdministrator = new Administrator();
        secondAdministrator.setId(2);
        secondAdministrator.setFirstName("Dean");
        secondAdministrator.setLastName("Fox");
        secondAdministrator.setAddress("Barcelona");
        secondAdministrator.setPhoneNumber(3569875486L);
        secondAdministrator.setEmail("dean@fox.com");
    }

    @Test
    void getAllAdministrators() {
        //given
        List<Administrator> getAllAdmins = new ArrayList<>();
        getAllAdmins.add(firstAdministrator);
        getAllAdmins.add(secondAdministrator);
        //when
        when(administratorRepository.findAll()).thenReturn(getAllAdmins);
        List<AdminDTO> adminDTOS = administratorService.getAllAdministrators();
        //then
        assertEquals(getAllAdmins.size(), adminDTOS.size());
        verify(administratorRepository, times(1)).findAll();
    }

    @Test
    void getAdministratorById() {
        Administrator getAdmin = new Administrator();
        getAdmin.setId(ID_VALUE);

        when(administratorRepository.findById(ID_VALUE)).thenReturn(Optional.of(getAdmin));
        AdminDTO adminDTO = administratorService.getAdministratorById(ID_VALUE);

        assertEquals(getAdmin.getId(), adminDTO.getId());
        verify(administratorRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteAdministratorById() {
        Administrator deleteAdmin = new Administrator();
        deleteAdmin.setId(ID_VALUE);

        when(administratorRepository.findById(ID_VALUE)).thenReturn(Optional.of(deleteAdmin));
        administratorRepository.deleteById(ID_VALUE);
        verify(administratorRepository, times(1)).deleteById(ID_VALUE);

        AdminDTO adminDTO = administratorService.deleteAdministratorById(ID_VALUE);
        assertEquals(deleteAdmin.getId(), adminDTO.getId());
    }

    @Test
    void createAdministrator() {
        Administrator createAdmin = new Administrator();
        createAdmin.setId(ID_VALUE);
        createAdmin.setFirstName("Greg");
        createAdmin.setLastName("Eagle");
        createAdmin.setAddress("Istanbul");
        createAdmin.setPhoneNumber(54789654785L);
        createAdmin.setEmail("greg@eagle.com");

        AdminDTO dto = new AdminDTO();
        dto.setId(ID_VALUE);
        dto.setFirstName("Greg");
        dto.setLastName("Eagle");
        dto.setAddress("Istanbul");
        dto.setPhoneNumber(54789654785L);
        dto.setEmail("greg@eagle.com");

        when(administratorRepository.save(createAdmin)).thenReturn(createAdmin);
        AdminDTO adminDTO = administratorService.createAdministrator(dto);

        assertNotNull(adminDTO);
        assertEquals(createAdmin.getId(), adminDTO.getId());
        assertEquals(createAdmin.getFirstName(), adminDTO.getFirstName());
        assertEquals(createAdmin.getLastName(), adminDTO.getLastName());
        assertEquals(createAdmin.getAddress(), adminDTO.getAddress());
        assertEquals(createAdmin.getPhoneNumber(), adminDTO.getPhoneNumber());
        assertEquals(createAdmin.getEmail(), adminDTO.getEmail());
    }

    @Test
    void updateAdministratorById() {
        Administrator updateAdministratorById = new Administrator();
        updateAdministratorById.setId(ID_VALUE);
        updateAdministratorById.setFirstName("Dave");
        updateAdministratorById.setLastName("Foster");
        updateAdministratorById.setAddress("Copenhagen");
        updateAdministratorById.setPhoneNumber(68956465963L);
        updateAdministratorById.setEmail("dave@foster.com");

        AdminDTO dto = new AdminDTO();
        dto.setId(2);
        dto.setFirstName("Alex");
        dto.setLastName("Stone");
        dto.setAddress("Larnaca");
        dto.setPhoneNumber(26548965475L);
        dto.setEmail("alex@stone.com");

        when(administratorRepository.findById(ID_VALUE)).thenReturn(Optional.of(updateAdministratorById));
        when(administratorRepository.save(updateAdministratorById)).thenReturn(updateAdministratorById);
        AdminDTO updatedAdmin = administratorService.update(dto, ID_VALUE);

        assertNotNull(updatedAdmin);
        assertEquals(updateAdministratorById.getId(), updatedAdmin.getId());
        assertEquals(updateAdministratorById.getFirstName(), updatedAdmin.getFirstName());
        assertEquals(updateAdministratorById.getLastName(), updatedAdmin.getLastName());
        assertEquals(updateAdministratorById.getAddress(), updatedAdmin.getAddress());
        assertEquals(updateAdministratorById.getPhoneNumber(), updatedAdmin.getPhoneNumber());
        assertEquals(updateAdministratorById.getEmail(), updatedAdmin.getEmail());
        verify(administratorRepository, times(1)).save(updateAdministratorById);
    }
}
