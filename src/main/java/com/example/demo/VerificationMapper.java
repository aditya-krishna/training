package com.example.demo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

public class VerificationMapper implements ResultSetExtractor<Verification> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationMapper.class);
    private static final String ERROR_MSG = "Error occurred while reading Pin Verification Attempts from ResultSet";

    @Override
    public Verification extractData(ResultSet rs) throws SQLException {
        if (rs != null) {
            Verification pinVerification = null;
            while (rs.next()) {
                pinVerification = new Verification();

                pinVerification.setAttempts(rs.getInt("ATTEMPTS"));
                pinVerification.setChangedDateTime(rs.getTimestamp("CHANGED_DATE_TIME"));
                pinVerification.setCreatedDateTime(rs.getTimestamp("CREATED_DATE_TIME"));
                pinVerification.setDeviceId(rs.getString("DEVICE_ID"));

                char[] cbuf = new char[1];

                try {
                    rs.getCharacterStream("VERIFIED").read(cbuf);
                } catch (IOException e) {
                    LOGGER.error(ERROR_MSG, e);
                }

                pinVerification.setVerified(cbuf[0]);

                break;
            }
            System.out.println(pinVerification != null ? pinVerification.toString() : "");

            return pinVerification;
        }
        else {
            return null;
        }
    }
}
