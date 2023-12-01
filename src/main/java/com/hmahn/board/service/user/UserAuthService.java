package com.hmahn.board.service.user;

import com.hmahn.board.domain.user.User;
import com.hmahn.board.domain.user.UserRepository;
import com.hmahn.board.web.user.dto.UserAuthDto;
import com.hmahn.board.web.user.dto.UserSessionDto;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 *  소셜로그인 서비스
 */

@RequiredArgsConstructor
@Service
public class UserAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 연동 로그인 구분값 (구글/네이버/카카오..)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // 유저 PK키 값

        UserAuthDto attributes = UserAuthDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // oAuth2User의 데이터를 담을 DTO

        User user = merge(attributes);
        httpSession.setAttribute("user", new UserSessionDto(user)); // 세션에 담을 사용자 정보 DTO

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey()
        );
    }

    private User merge(UserAuthDto attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getUsername(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        // 가입여부 체크 후 있으면 업데이트 없으면, 신규로 저장
        return userRepository.save(user);
    }

}

