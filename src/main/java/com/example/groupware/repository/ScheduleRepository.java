package com.example.groupware.repository;

import com.example.groupware.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 날짜에 해당하는 일정 조회
//    List<Schedule> findByStartDate(LocalDate date);
    // 특정 기간 사이 일정 조회
//    List<Schedule> findByStartDateBetween(LocalDate start, LocalDate end);
    // 키워드 포함된 일정 검색
//    List<Schedule> findByTitleContaining(String keyword);
    // 시작일 기준 일정 목록
//    List<Schedule> findAllByOrderByStartDateAsc();
    // 지난 일정 조회
//    List<Schedule> findByEndDateBefore(LocalDate date);
}
