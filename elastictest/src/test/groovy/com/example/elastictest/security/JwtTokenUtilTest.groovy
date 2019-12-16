package com.example.elastictest.security

import com.example.elastictest.security.dto.JwtAuthenticationResponse
import com.example.elastictest.security.dto.JwtUser
import org.assertj.core.api.Assertions
import org.assertj.core.util.DateUtil
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.MockitoAnnotations
import org.springframework.security.core.userdetails.UserDetails

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@DisplayName("JwtTokenUtil tests")
class JwtTokenUtilTest {
    private static final String TEST_USERNAME = "testUser"
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil()

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtTokenUtil.setExpiration(3600L);
        jwtTokenUtil.setSecret("mySecret");
    }

    private JwtAuthenticationResponse createToken() {
        UserDetailsDummy detailsDummy = new UserDetailsDummy()
        detailsDummy.setUsername(TEST_USERNAME)
        return jwtTokenUtil.generateToken(detailsDummy)
    }

    @Test
    @DisplayName("should get name from the token.")
    void shouldGetUsernameFromToken() {
        final String token = createToken().getToken()
        final String username = jwtTokenUtil.getUserNameFromToken(token)
        Assertions.assertThat(username).isEqualToIgnoringCase(TEST_USERNAME)
    }

    @Test
    @DisplayName("should get issued date from token")
    void shouldGetIssuedDateFromToken() {
        final Date now = DateUtil.now();
        final String token = createToken().getToken();
        final Long test = 1000
        final Date issuedDate = jwtTokenUtil.getIssuedAtDateFromToken(token);
        Assertions.assertThat(Math.abs(now.getTime() - issuedDate.getTime())).isLessThan(test)
    }

    @Test
    @DisplayName("Should get expiration date from token.")
    void shouldGetExpirationDateFromToken() {
        final Date now = DateUtil.now()
        final Long test = 1000
        final String token = createToken().getToken()
        final Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token)
        Assertions.assertThat((Math.abs(now.getTime() - expirationDate.getTime()) - 604799055L)).isLessThan(test)
    }

    @Test
    @DisplayName("Should validate token.")
    void shouldValidateToken() {
        UserDetails userDetails = mock(JwtUser.class);
        when(((JwtUser) userDetails).getLastPasswordResetDate())
                .thenReturn(new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime() as String);
        when(userDetails.getUsername()).thenReturn(TEST_USERNAME);
        String token = createToken().getToken();

        boolean actual = jwtTokenUtil.validateToken(token, userDetails);

        Assertions.assertThat(actual).isTrue();
    }


}
