package com.kookmin.pm.module.league.service;

import com.kookmin.pm.module.category.domain.Category;
import com.kookmin.pm.module.category.repository.CategoryRepository;
import com.kookmin.pm.module.league.domain.League;
import com.kookmin.pm.module.league.domain.LeagueType;
import com.kookmin.pm.module.league.domain.ParticipantType;
import com.kookmin.pm.module.league.dto.LeagueCreateInfo;
import com.kookmin.pm.module.league.repository.LeagueParticipantsRepository;
import com.kookmin.pm.module.league.repository.LeagueRepository;

import com.kookmin.pm.module.member.domain.Member;
import com.kookmin.pm.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final LeagueParticipantsRepository leagueParticipantsRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public Long openLeague(@NonNull Long usn, @NonNull LeagueCreateInfo leagueCreateInfo) {
        Member host = getMemberEntity(usn);

        League league = buildLeagueEntity(leagueCreateInfo, host);

        //TODO::현재 시간 보다 이전 시간을 시작 시간으로 잡을 경우
        if(league.getStartTime().isBefore(LocalDateTime.now()))
            throw new RuntimeException();

        league = leagueRepository.save(league);

        return league.getId();
    }

    private League buildLeagueEntity(@NonNull LeagueCreateInfo leagueCreateInfo, @NonNull Member host) {
        Category category = getCategoryEntity(leagueCreateInfo.getCategory());

        return League.builder()
                .title(leagueCreateInfo.getTitle())
                .description(leagueCreateInfo.getDescription())
                .activityArea(leagueCreateInfo.getActivityArea())
                .maxCount(leagueCreateInfo.getMaxCount())
                .startTime(leagueCreateInfo.getStartTime())
                .leagueType(LeagueType.valueOf(leagueCreateInfo.getLeagueType()))
                .participantType(ParticipantType.valueOf(leagueCreateInfo.getParticipantType()))
                .member(host)
                .category(category)
                .build();
    }

    private Category getCategoryEntity(String category) {
        return categoryRepository.findByName(category).orElseThrow(EntityNotFoundException::new);
    }

    private Member getMemberEntity(Long usn) {
        return memberRepository.findById(usn).orElseThrow(EntityNotFoundException::new);
    }
}