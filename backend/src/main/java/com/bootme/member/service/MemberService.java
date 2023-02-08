package com.bootme.member.service;

import com.bootme.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isMemberRegistered(String email){
        return memberRepository.existsMemberByEmail(email);
    }

}
