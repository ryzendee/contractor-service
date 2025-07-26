package ryzendee.app.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ryzendee.starter.jwt.decoder.AuthRole;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ContractorAccessRulesTest {

    private static final String ALLOWED_COUNTRY = "RUS";

    private ContractorAccessRules accessRules;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        accessRules = new ContractorAccessRules();
        authentication = mock(Authentication.class);
    }

    @Test
    void canEditContractors_withSuperUserRole_returnsTrue() {
        doReturn(toGrantedAuthorities(AuthRole.SUPERUSER)).when(authentication).getAuthorities();

        boolean result = accessRules.canEditContractors(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isTrue();
    }

    @Test
    void canEditContractors_withoutSuperUserRole_returnsFalse() {
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_RUS)).when(authentication).getAuthorities();

        boolean result = accessRules.canEditContractors(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isFalse();
    }

    @Test
    void canReadContractors_withContractorSuperUserRole_returnsTrue() {
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_SUPERUSER)).when(authentication).getAuthorities();

        boolean result = accessRules.canReadContractors(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isTrue();
    }

    @Test
    void canReadContractors_withoutSuperUserRole_returnsFalse() {
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_RUS)).when(authentication).getAuthorities();

        boolean result = accessRules.canReadContractors(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isFalse();
    }

    @Test
    void canSearchContractors_withSuperUserRole_returnsTrue() {
        doReturn(toGrantedAuthorities(AuthRole.SUPERUSER)).when(authentication).getAuthorities();

        boolean result = accessRules.canSearchContractors(authentication, "ANY_COUNTRY");

        verify(authentication).getAuthorities();
        assertThat(result).isTrue();
    }

    @Test
    void canSearchContractors_withContractorRusAndAllowedCountry_returnsTrue() {
        int expectedInvocationTimes = 2;
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_RUS)).when(authentication).getAuthorities();

        boolean result = accessRules.canSearchContractors(authentication, ALLOWED_COUNTRY);

        verify(authentication, times(expectedInvocationTimes)).getAuthorities();
        assertThat(result).isTrue();
    }

    @Test
    void canSearchContractors_withContractorRusAndNotAllowedCountry_returnsFalse() {
        int expectedInvocationTimes = 2;
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_RUS)).when(authentication).getAuthorities();

        boolean result = accessRules.canSearchContractors(authentication, "USA");

        verify(authentication, times(expectedInvocationTimes)).getAuthorities();
        assertThat(result).isFalse();
    }

    @Test
    void canEditReference_withSuperUserRole_returnsTrue() {
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_SUPERUSER)).when(authentication).getAuthorities();

        boolean result = accessRules.canEditReference(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isTrue();
    }

    @Test
    void canEditReference_withoutSuperUserRole_returnsFalse() {
        doReturn(toGrantedAuthorities(AuthRole.CONTRACTOR_RUS)).when(authentication).getAuthorities();

        boolean result = accessRules.canEditReference(authentication);

        verify(authentication).getAuthorities();
        assertThat(result).isFalse();
    }

    private Collection<? extends GrantedAuthority> toGrantedAuthorities(AuthRole... roles) {
        return Stream.of(roles)
                .map(AuthRole::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
