package com.wayon.SmartTransfer.entity.user;

import com.wayon.SmartTransfer.entity.Auditable;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "smart_user")
public class User extends Auditable implements UserDetails {

    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String userId = UUID.randomUUID().toString();

    @NonNull
    @ManyToOne
    private Profile profile;


    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String status = UserStatus.ACTIVE.name();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(profile);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public @NonNull String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(UserStatus.ACTIVE.name());
    }
}
