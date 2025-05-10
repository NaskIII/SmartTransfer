package com.wayon.SmartTransfer.repository;

import com.wayon.SmartTransfer.entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String s);
}
