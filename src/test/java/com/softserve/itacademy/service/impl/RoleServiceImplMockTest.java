package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.ModelUtils;
import com.softserve.itacademy.exception.NullEntityReferenceException;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RoleServiceImplMockTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    Role role = ModelUtils.getRole();

    @Test
    void createRoleTest() {
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        assertEquals(role, roleService.create(role));
    }

    @Test
    void createRoleNullEntityReferenceExceptionTest() {
        Role role = null;
        Mockito.when(roleRepository.save(role)).thenThrow(IllegalArgumentException.class);
        assertThrows(NullEntityReferenceException.class, () -> roleService.create(role));
    }
    @Test
    void readByIdRoleTest() {
        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        assertEquals(role, roleService.readById(1L));
    }

    @Test
    void updateRoleTest() {
        Role beginRole = role;
        role.setName("My role");

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.of(beginRole));
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        assertEquals(role, roleService.update(beginRole));
    }

    @Test
    void updateRoleEntityNotFoundExceptionTest() {
        Role beginRole = role;
        role.setName("My role");

        Mockito.when(roleRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(roleRepository.save(role)).thenReturn(role);
        assertThrows(NullEntityReferenceException.class, () -> roleService.update(beginRole));
    }

    @Test
    void deleteRoleTest() {
        roleRepository.save(role);
        Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        roleService.delete(role.getId());
        Mockito.verify(roleRepository).delete(role);
    }

    @Test
    void deleteRoleEntityNotFoundExceptionTest() {
        roleRepository.save(role);
        Mockito.when(roleRepository.findById(100L)).thenReturn(Optional.ofNullable(null));
        assertThrows(NullEntityReferenceException.class, () -> roleService.delete(100L));
        Mockito.verify(roleRepository, Mockito.never()).delete(role);
    }

    @Test
    void getAllRoleTest() {
        Role role1 = role;
        Mockito.when(roleRepository.findAll()).thenReturn(Arrays.asList(role, role1));
        assertEquals(Arrays.asList(role, role1), roleService.getAll());
    }
}