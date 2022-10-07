package com.nvc.scanbar.api;

import com.nvc.scanbar.beans.redeemhistory.RedeemRequest;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RedeemController.class);
    String traceIdentifier = UUID.randomUUID().toString();

    @PostMapping("/points")
    public ResponseEntity<?> redeemPoint(@RequestBody RedeemRequest redeemRequest) {
        try {
            redeemService.redeemPoints(redeemRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            throw e;
        } catch (DataConflictException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerError(traceIdentifier);
        }
    }
}
