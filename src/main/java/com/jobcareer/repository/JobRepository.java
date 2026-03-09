package com.jobcareer.repository;
import com.jobcareer.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(Job.JobStatus status);

    @Query("SELECT j FROM Job j WHERE j.status = 'ACTIVE' AND " +
            "(LOWER(j.title) LIKE LOWER(CONCAT('%',:k,'%')) OR " +
            " LOWER(j.company) LIKE LOWER(CONCAT('%',:k,'%')) OR " +
            " LOWER(j.location) LIKE LOWER(CONCAT('%',:k,'%')) OR " +
            " LOWER(j.category) LIKE LOWER(CONCAT('%',:k,'%')))")
    List<Job> searchJobs(@Param("k") String keyword);
}