package az.atl.ms_auth.dao.repository;

import az.atl.ms_auth.dao.entity.LoginTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginTableRepository extends JpaRepository<LoginTable, Long> {
}