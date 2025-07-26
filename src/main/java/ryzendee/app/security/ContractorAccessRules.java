package ryzendee.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ryzendee.starter.jwt.auth.AbstractReferenceAccessRules;
import ryzendee.starter.jwt.decoder.AuthRole;

@Slf4j
@Component("contractorAccessRules")
public class ContractorAccessRules extends AbstractReferenceAccessRules {

    private static final String ALLOWED_COUNTRY = "RUS";

    public boolean canEditContractors(Authentication authentication) {
        return hasRole(authentication, getApplicationSuperUsers());
    }

    public boolean canReadContractors(Authentication authentication) {
        return hasRole(authentication, getApplicationSuperUsers());
    }

    public boolean canSearchContractors(Authentication authentication, String country) {
        return hasRole(authentication, getApplicationSuperUsers()) || isContractorFromAllowedCountry(authentication, country);
    }

    @Override
    protected boolean canEditReference(Authentication authentication) {
        return hasRole(authentication, getApplicationSuperUsers());
    }

    private AuthRole[] getApplicationSuperUsers() {
        return new AuthRole[]{AuthRole.SUPERUSER, AuthRole.CONTRACTOR_SUPERUSER};
    }

    private boolean isContractorFromAllowedCountry(Authentication authentication, String country) {
        return hasRole(authentication, AuthRole.CONTRACTOR_RUS) && ALLOWED_COUNTRY.equals(country);
    }
}
