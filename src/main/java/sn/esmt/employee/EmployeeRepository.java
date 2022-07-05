package sn.esmt.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value="select * from Employee e where e.name like %:keyword% or e.prenom like %:keyword% ", nativeQuery=true)
    List<Employee> findByKeyword(@Param("keyword") String Keyword);
}
