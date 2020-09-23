package me.backendj.lboard.member;

import lombok.RequiredArgsConstructor;
import me.backendj.lboard.common.util.JwtTokenProvider;
import me.backendj.lboard.exception.NotExistEmailException;
import me.backendj.lboard.exception.WrongPasswordException;
import me.backendj.lboard.member.dto.LoginRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final JwtTokenProvider jwtUtil;
    private final MemberRepository memberRepository;

    public String createToken(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(NotExistEmailException::new);
        if (!member.checkPassword(loginRequest.getPassword())) {
            throw new WrongPasswordException();
        }
        return jwtUtil.createToken(loginRequest.getEmail());
    }

}
