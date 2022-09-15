package com.kidsnara.library;

import com.kidsnara.library.domain.user.Member;
import com.kidsnara.library.domain.user.MemberService;
import com.kidsnara.library.dto.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @Autowired
    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public String home(@RequestBody UserCreateRequest request){
        System.out.println("저장 실행");
        memberService.save(request);
        return "저장되었음";
    }

    @GetMapping("/test")
    public String getBarcode(){
        System.out.println("실행");
        return "바코드가 등록되었습니다.";
    }

    @GetMapping("/members")
    public Member find(){
        Member member = memberService.findById(1L);
        return member;
    }
}
