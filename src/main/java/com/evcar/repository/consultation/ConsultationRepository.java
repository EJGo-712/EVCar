package com.evcar.repository.consultation;

import com.evcar.domain.consultation.Consultation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, String> {

    boolean existsByUserUserIdAndConsultStatus(String userId, String consultStatus);

    List<Consultation> findByUserUserIdOrderByCreatedAtDesc(String userId);
}