package com.nvc.scanbar.api;

import com.nvc.scanbar.beans.CommonSessionBean;
import com.nvc.scanbar.beans.redeemhistory.RedeemRequest;
import com.nvc.scanbar.exceptions.AuthException;
import com.nvc.scanbar.exceptions.DataConflictException;
import com.nvc.scanbar.exceptions.InternalServerError;
import com.nvc.scanbar.exceptions.NotFoundException;
import com.nvc.scanbar.services.RedeemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/redeem")
public class RedeemController {

    @Autowired
    RedeemService redeemService;

    @Autowired private CommonSessionBean commonSessionBean;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedeemController.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @PostMapping("/points")
    public ResponseEntity<?> redeemPoint(@RequestBody RedeemRequest redeemRequest) {
        try {
            if (commonSessionBean.getUser().getUserType().equals("Admin")) {
                redeemService.redeemPoints(redeemRequest);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
            throw new AuthException("You're not authorized for this action", traceIdentifier);
        }
        } catch (NotFoundException | DataConflictException | AuthException e) {
            LOGGER.error(traceIdentifier + " redeemPoint() " + e);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(traceIdentifier);
        }
    }
}
