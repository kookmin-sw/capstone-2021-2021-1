package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.CrewParticipantStatus;
import com.kookmin.pm.module.crew.dto.CrewDetails;
import com.kookmin.pm.module.crew.dto.CrewSearchCondition;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrewSearchRepository {
    public List<Member> findMemberInCrewParticipants(Long crewId, CrewParticipantStatus status);
    public List<CrewDetails> findParticipatedCrew(Long usn);
    public Page<CrewDetails> searchCrew(Pageable pageable, CrewSearchCondition searchCondition);
}
