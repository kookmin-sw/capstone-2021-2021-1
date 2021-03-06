package com.kookmin.pm.module.member.service;

import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.domain.MemberStats;
import com.kookmin.pm.module.member.domain.MemberStatus;
import com.kookmin.pm.module.member.dto.MemberCreateInfo;
import com.kookmin.pm.module.member.dto.MemberDetails;
import com.kookmin.pm.module.member.dto.MemberEditInfo;
import com.kookmin.pm.module.member.repository.MemberRepository;
import com.kookmin.pm.module.member.repository.MemberStatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberStatsRepository memberStatsRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn9412@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu2");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        MemberCreateInfo memberCreateInfo2 = new MemberCreateInfo();
        memberCreateInfo2.setUid("dlwlsrn9413@kookmin.ac.kr");
        memberCreateInfo2.setPassword("12345");
        memberCreateInfo2.setNickname("jingu2");
        memberCreateInfo2.setAddress("서울시~~~");
        memberCreateInfo2.setName("이진팔");
        memberCreateInfo2.setPhoneNumber("010-8784-3827");

        MemberCreateInfo memberCreateInfo3 = new MemberCreateInfo();
        memberCreateInfo3.setUid("dlwlsrn9414@kookmin.ac.kr");
        memberCreateInfo3.setPassword("12346");
        memberCreateInfo3.setNickname("jingu3");
        memberCreateInfo3.setAddress("서울시~~~");
        memberCreateInfo3.setName("이진칠");
        memberCreateInfo3.setPhoneNumber("010-8784-3827");

        memberService.joinMember(memberCreateInfo);
        memberService.joinMember(memberCreateInfo2);
        memberService.joinMember(memberCreateInfo3);
    }

    @Test
    @DisplayName("joinMember메소 회원가입 성공 테스트")
    public void joinMember_success_test() {
        MemberCreateInfo memberCreateInfo = new MemberCreateInfo();
        memberCreateInfo.setUid("dlwlsrn9411@kookmin.ac.kr");
        memberCreateInfo.setPassword("1234");
        memberCreateInfo.setNickname("jingu");
        memberCreateInfo.setAddress("서울시~~~");
        memberCreateInfo.setName("이진구");
        memberCreateInfo.setPhoneNumber("010-8784-3827");

        Long id = memberService.joinMember(memberCreateInfo);
        Member member = memberRepository.findByUid(memberCreateInfo.getUid()).orElseThrow(EntityNotFoundException::new);

        assertThat(member)
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("uid", memberCreateInfo.getUid())
                .hasFieldOrPropertyWithValue("nickname", memberCreateInfo.getNickname())
                .hasFieldOrPropertyWithValue("name", memberCreateInfo.getName())
                .hasFieldOrPropertyWithValue("phoneNumber", memberCreateInfo.getPhoneNumber())
                .hasFieldOrPropertyWithValue("address", memberCreateInfo.getAddress());

        boolean isPasswordMatches = passwordEncoder.matches(memberCreateInfo.getPassword()
                , member.getPassword());

        assertThat(isPasswordMatches).isTrue();

        int count = memberStatsRepository.findAll().size();
        assertThat(count).isEqualTo(4);

        MemberStats stats = memberStatsRepository.findByMember(member).orElseThrow(EntityNotFoundException::new);

        assertThat(stats)
                .hasFieldOrPropertyWithValue("manner", 0L)
                .hasFieldOrPropertyWithValue("affinity", 0L)
                .hasFieldOrPropertyWithValue("physical", 0L)
                .hasFieldOrPropertyWithValue("intellect", 0L)
                .hasFieldOrPropertyWithValue("comprehension", 0L)
                .hasFieldOrPropertyWithValue("evaluateCount", 0L);
    }

    @Test
    @DisplayName("editMemberInfo 메소드 회원정보 수정 성공 테스트")
    public void editMemberInfo_success_test() {
        MemberEditInfo memberEditInfo = new MemberEditInfo();
        memberEditInfo.setAddress("수정 주소");
        memberEditInfo.setName("수정 이름");
        memberEditInfo.setPhoneNumber("010-8888-8888");
        memberEditInfo.setNickname("수정 닉네임");
        memberEditInfo.setDescription("자기소개");

        Member member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        memberService.editMemberInfo(member.getId(), memberEditInfo);

        member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").orElseThrow(EntityNotFoundException::new);

        assertThat(member)
                .hasFieldOrPropertyWithValue("address", memberEditInfo.getAddress())
                .hasFieldOrPropertyWithValue("name", memberEditInfo.getName())
                .hasFieldOrPropertyWithValue("phoneNumber", memberEditInfo.getPhoneNumber())
                .hasFieldOrPropertyWithValue("nickname", memberEditInfo.getNickname());
    }

    @Test
    @DisplayName("lookupMemberDetails 메소드 성공 테스트")
    public void lookupMemberDetails_success_test() {
        Member member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();

        MemberDetails memberDetails = memberService
                .lookUpMemberDetails(member.getId(), LookupType.DEFAULT);

        System.out.println(memberDetails);

        memberDetails = memberService
                .lookUpMemberDetails(member.getId(), LookupType.WITHIMAGE);

        System.out.println(memberDetails);

        memberDetails = memberService
                .lookUpMemberDetails(member.getId(), LookupType.WITHALLINFOS);

        System.out.println(memberDetails);
        System.out.println(memberDetails.getMemberStats().getManner());
    }

    @Test
    @DisplayName("secessionMember 메소드 성공 테스트")
    public void secessionMember_success_test() {
        Member member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").get();
        boolean result = memberService.secessionMember(member.getId(), "1234");
        assertThat(result).isTrue();
        member = memberRepository.findByUid("dlwlsrn9412@kookmin.ac.kr").orElseThrow(EntityNotFoundException::new);
        assertThat(member)
                .hasFieldOrPropertyWithValue("status", MemberStatus.EXPIRED);
    }
}
