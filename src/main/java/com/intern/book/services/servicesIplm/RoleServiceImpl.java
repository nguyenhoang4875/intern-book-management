package com.intern.book.services.servicesIplm;

import com.intern.book.models.dao.Role;
import com.intern.book.repositories.RoleRepository;
import com.intern.book.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findOneByName(String name) {
        return roleRepository.findByName(name);
    }
}
