package com.intern.book.services;


import com.intern.book.models.dao.Role;

public interface RoleService {
    Role findOneByName(String name);
}
