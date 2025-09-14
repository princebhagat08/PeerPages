package com.example.enotes_api.config;

import com.example.enotes_api.entity.User;
import com.example.enotes_api.utils.CommonUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareConfig implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {
        User user = CommonUtil.getLoggedInUser();
        return Optional.of(user.getId());
    }
}
