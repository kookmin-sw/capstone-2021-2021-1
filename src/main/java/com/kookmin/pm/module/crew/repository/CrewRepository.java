package com.kookmin.pm.module.crew.repository;

import com.kookmin.pm.module.crew.domain.Crew;
import com.kookmin.pm.module.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrewRepository extends JpaRepository<Crew,Long>, CrewSearchRepository {
    public List<Crew> findByMember(Member member);
}
