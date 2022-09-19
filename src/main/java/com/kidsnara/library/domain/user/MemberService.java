package com.kidsnara.library.domain.user;

import com.kidsnara.library.dto.MemberDto;
import com.kidsnara.library.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ManyToOne;


@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {


    private final UserRepository userRepository;

    public Long save(UserCreateRequest request){
        Member member = new Member(request);
        userRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long userId){
        Member member = userRepository.findById(userId).orElse(null);
        MemberDto memberDto = new MemberDto(member.getName(), member.getEmail());
        return memberDto;
    }

    public Long createAndGetUserCount(UserCreateRequest request){
        Member user = new Member(request);
        userRepository.save(user);

        return userRepository.count();
    }

}
