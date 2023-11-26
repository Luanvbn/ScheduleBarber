package br.com.schedulebarber.scheduleBarber.Service;


import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Repository.RoleRepository;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findById(Long id) throws Exception {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            return roleOptional;
        } else {
            throw new Exception();
        }
    }

    public Page<Role> findAllRoles(PaginationParams params) {
        Sort sort = Sort.by(params.getSortOrder().equalsIgnoreCase("asc") ?
                Sort.Order.asc(params.getSortProperty()) : Sort.Order.desc(params.getSortProperty()));
        Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), sort);
        return roleRepository.findAll(pageable);
    }

    public Role createRole(Role role) throws Exception {
        if (roleRepository.existsByAuthority(role.getAuthority())) {
            throw new Exception("Role already exists");
        } else {
            return roleRepository.save(role);
        }
    }

    public Role updateRole(Long id, Role role) throws Exception {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role existingRole = optionalRole.orElseThrow(Exception::new);
        existingRole.setAuthority(role.getAuthority());
        return roleRepository.save(existingRole);
    }

    public Role deleteRole(Long id) throws Exception {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if(roleOptional.isPresent()) {
            Role existingRole = roleOptional.get();
            roleRepository.deleteById(id);
            return existingRole;
        } else {
            throw new Exception();
        }
    }
}
