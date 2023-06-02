package br.com.schedulebarber.scheduleBarber.Controller;

import br.com.schedulebarber.scheduleBarber.Model.Role;
import br.com.schedulebarber.scheduleBarber.Service.RoleService;
import br.com.schedulebarber.scheduleBarber.Util.PaginationParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) throws Exception {
        Optional<Role> role = roleService.findById(id);
        return ResponseEntity.ok(role);
    }

    @PostMapping("/findAllRoles")
    public ResponseEntity<?> findAllScheduling(@RequestBody PaginationParams params){
        Page<Role> roles = roleService.findAllRoles(params);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/save")
    public ResponseEntity<?> createService(@RequestBody Role role) throws Exception {
        Role roleSave = roleService.createRole(role);
        return ResponseEntity.ok(roleSave);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") Long id, @RequestBody Role role) throws Exception {
        Role roleSaved = roleService.updateRole(id, role);
        return ResponseEntity.ok(roleSaved);
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) throws Exception {
        Role roleDeleted = roleService.deleteRole(id);
        return ResponseEntity.ok(roleDeleted);
    }
}
