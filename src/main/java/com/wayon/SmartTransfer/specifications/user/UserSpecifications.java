package com.wayon.SmartTransfer.specifications.user;

import com.wayon.SmartTransfer.entity.user.Profile_;
import com.wayon.SmartTransfer.entity.user.User;
import com.wayon.SmartTransfer.entity.user.User_;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> search(UserSearchCriteria searchCriteria) {
        return searchByName(searchCriteria.getName())
                .and(searchByLastName(searchCriteria.getLastName()))
                .and(searchByEmail(searchCriteria.getEmail()))
                .and(searchByProfile(searchCriteria.getProfile()))
                .and(searchByStatus(searchCriteria.getStatus()));
    }

    public static Specification<User> searchByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return null;

            return criteriaBuilder.like(root.get(User_.FIRST_NAME), "%" + name + "%");
        };
    }

    public static Specification<User> searchByLastName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null) return null;

            return criteriaBuilder.like(root.get(User_.LAST_NAME), "%" + name + "%");
        };
    }

    public static Specification<User> searchByEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null) return null;

            return criteriaBuilder.like(root.get(User_.EMAIL), "%" + email + "%");
        };
    }

    public static Specification<User> searchByProfile(String profile) {
        return (root, query, criteriaBuilder) -> {
            if (profile == null) return null;

            return criteriaBuilder.like(root.get(User_.PROFILE).get(Profile_.NAME), "%" + profile + "%");
        };
    }

    public static Specification<User> searchByStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) return null;

            return criteriaBuilder.equal(root.get(User_.STATUS), status);
        };
    }
}
