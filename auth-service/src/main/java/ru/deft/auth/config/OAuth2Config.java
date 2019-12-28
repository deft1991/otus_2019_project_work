package ru.deft.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/*
 * Created by sgolitsyn on 12/11/19
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    //    @Value("${user.oauth.clientId}")
    private final String clientid = "auth-service";
    //    @Value("${user.oauth.clientSecret}")
    private final String clientSecret = "my-secret-key";
    //    @Value("${user.oauth.redirectUris}")
    private String redirectURLs;

    //    @Value("${security.private-key}")
    private final String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAv8c7eHlMeVcaXCArZLZtKWZpm61hqqVLtk3wMG+pkqSwoSw3\n" +
            "Nd3ryTrg4ERL+8aHEmTPRxxcOWirWVuXiVcOnmkaReNquJzX2xXf5lmkRAecBKdc\n" +
            "dJsyOADFSuKWs0XR1qCktLatbkZzlm8Rxt4smXhSGeKAUEicihIw4+OZvhpWaP4q\n" +
            "v/0b2A95v/+7NCx5VNZKNDFbzqkV0SLY9QWbZfUq1q0vP94XyAT6Z9mHM9m2qqca\n" +
            "R8gIJfz+g1OP3ABoDpa/xwSwNwF2EYdxk1DuFOpNN4slr1rq2vPtkZuwBADmXHGy\n" +
            "cVGvH83RifKBGv5B52ZJ52Uymne7dANgDLnoxwIDAQABAoIBABLGXnwc4++9J0d9\n" +
            "nsHtWiKDdDNZdgZOyGrAlpMnW2KP3PrqaT1MzJiWuw05onazqOWUGPbNP/oe1Sea\n" +
            "X+uCKlw3zeqpqPkJ4dgA/DqA7CP82rlZ6SNBTllNOgNqy3Yhj7mDYJyQhcoV+5ru\n" +
            "pL7jYVpk/SSrmAREF48s2Lx2u8ZwgkDykWWUJKdFS9LMaAG7qiraq6hJz1/Gvsj7\n" +
            "quMsWRllNg9kyvYWe/+R2EfoU3l4LM9Hwdl/ixSB30u963aj560uOMEko0On8VXQ\n" +
            "ukr4sC4qUCHwUgddePSL4uu+I9fhoibl/wuU+bU3Q0gjVUlFHWvgvjnza6vDLxil\n" +
            "wEQnGwECgYEA4ZNbZtzyMQbdDXX7bEA3ikbjRM58z8EY0HlOy9JA8EiTQ8sTrujC\n" +
            "Xsk9hYcVuZ17JKkF/3VK6Nfl1c/bLen44GRUbqKl7G+E9YucUMCu2YZxKKvZ0630\n" +
            "BfpblmeUbvJNq8UDwzdzU+znxlY0BmDYOzAW5U4OV0uS3xpBxPyjMGkCgYEA2aTs\n" +
            "ufxblheprlwzwfCeRkF3jJ7xhzOfpmQt6W5zGffItRDphu22xYWRhHLvZhgjli1/\n" +
            "OWpfkrHNlz16AqQrWQStVP9yYSJUuMrIpmu7K+duW5kUwMf3KRsik4IV9DhsmqZt\n" +
            "DxVRskDnnrKFw6QHHaizOB9WoeJpNyuZB9WzKa8CgYAfCwNqHX/rBVHId3MZS6EZ\n" +
            "E3ZVUsFUafN7RSZ14EJ1jtdNXhYgXQHav4EK7jMsLyLyQZyEsmSTtJp5mThFkkxg\n" +
            "vQ3th86jwhkfHY5ugoXNg7Xw7e11Nxw88l5GTYzc09WijONeqzPg2dpvrg9MzWkU\n" +
            "hCKyfdJ1av4UW+2vKUFf+QKBgEh9SetYRhjjoLxWMVbzEYRM4ciQV9m0NARzcWdC\n" +
            "Rkvr34mPLHioTCvVpPX8YggbGh824Bz7dQGi/trUuwKOM3HewOyCeFIp4RX7VIE5\n" +
            "eInS90rC3cqnz4Z9ZzLCLRW+hU2tCL3xV9iLDZop06upwkT6n1ad+XjJtEmP19Ro\n" +
            "NNg5AoGAL/9rXDj8dHlvgIn78+YIcIkG6L6dN2L95cfauWg/6KO6zmPvC2R7lc/e\n" +
            "aR1Xt8vW4DofYqbzTUSxuJBdooZpbU7Ra4jXpGRw2L3H5cbXgemmHYOwTRcsReKU\n" +
            "U4XgyPva2zHMQc5NJx+8XQooKn3dwlQYCXrx1YXPZuEvHENr3a0=\n" +
            "-----END RSA PRIVATE KEY-----\n";
    //    @Value("${security.public-key}")
    private final String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv8c7eHlMeVcaXCArZLZt\n" +
            "KWZpm61hqqVLtk3wMG+pkqSwoSw3Nd3ryTrg4ERL+8aHEmTPRxxcOWirWVuXiVcO\n" +
            "nmkaReNquJzX2xXf5lmkRAecBKdcdJsyOADFSuKWs0XR1qCktLatbkZzlm8Rxt4s\n" +
            "mXhSGeKAUEicihIw4+OZvhpWaP4qv/0b2A95v/+7NCx5VNZKNDFbzqkV0SLY9QWb\n" +
            "ZfUq1q0vP94XyAT6Z9mHM9m2qqcaR8gIJfz+g1OP3ABoDpa/xwSwNwF2EYdxk1Du\n" +
            "FOpNN4slr1rq2vPtkZuwBADmXHGycVGvH83RifKBGv5B52ZJ52Uymne7dANgDLno\n" +
            "xwIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(privateKey);
//        converter.setVerifierKey(publicKey);
        converter.setSigningKey("123");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("SampleClientId")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris(
                        "http://localhost:8082/ui/login", "http://localhost:8083/ui2/login");

    }
}
