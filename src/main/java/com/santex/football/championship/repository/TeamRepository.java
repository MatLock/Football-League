package com.santex.football.championship.repository;

import com.santex.football.championship.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,String> {

  @Query(value = "SELECT * FROM TEAM WHERE NAME IN (:names)",nativeQuery =true)
  List<Team> findByNameIn(@Param("names") List<String> names);

}
