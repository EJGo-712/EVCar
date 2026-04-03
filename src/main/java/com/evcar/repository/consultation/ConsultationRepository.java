package com.evcar.repository.consultation;

import com.evcar.domain.consultation.Consultation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, String> {

    // 마이페이지용 - String userId 기준
    boolean existsByUserIdAndConsultStatus(String userId, String consultStatus);

    List<Consultation> findByUserIdOrderByCreatedAtDesc(String userId);
}
