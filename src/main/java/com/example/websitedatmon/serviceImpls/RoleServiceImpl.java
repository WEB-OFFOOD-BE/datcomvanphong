package com.example.websitedatmon.serviceImpls;

import com.example.websitedatmon.entity.Role;
import com.example.websitedatmon.repositorys.RoleRepository;
import com.example.websitedatmon.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findRoleById(id);
    }
}
