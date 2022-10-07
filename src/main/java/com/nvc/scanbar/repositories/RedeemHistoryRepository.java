package com.nvc.scanbar.repositories;

import com.nvc.scanbar.beans.redeemhistory.RedeemHistoryResponse;
import com.nvc.scanbar.model.RedeemHistory;
import com.nvc.scanbar.model.RedeemHistoryPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

public interface RedeemHistoryRepository extends JpaRepository<RedeemHistory, RedeemHistoryPk> {
    @Query(value = "select new com.nvc.scanbar.beans.redeemhistory.RedeemHistoryResponse(concat(u.fname,' ', u.lname), " +
            "concat(u2.fname,' ', u2.lname), " +
            "u.organizationName, r )" +
            "from RedeemHistory r  " +
            "inner join User u " +
            "on u.userId = r.userId " +
            "inner join User u2 on u2.userId = r.createdBy " +
            "where r.userId = ?1 " +
            "order by r.createdDate desc")
    List<RedeemHistoryResponse> getRedeemHistoryByUserId(@NonNull String userId);

}
