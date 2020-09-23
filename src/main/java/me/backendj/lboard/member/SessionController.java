package me.backendj.lboard.member;

import me.backendj.lboard.member.dto.LoginRequest;
import me.backendj.lboard.member.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final MemberService memberService;

    public SessionController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginRequest loginRequest) {
        String token = memberService.createToken(loginRequest);
        return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
    }

}
