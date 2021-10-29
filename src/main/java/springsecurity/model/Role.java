package springsecurity.model;

import javafx.beans.property.adapter.JavaBeanBooleanPropertyBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.EnumSet.of;
import static java.util.Set.*;

public enum Role {
    USER(of(Permission.DEVELOPERS_READ)),
    ADMIN(of(Permission.DEVELOPERS_READ, Permission.DEVELOPERS_WRITE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
